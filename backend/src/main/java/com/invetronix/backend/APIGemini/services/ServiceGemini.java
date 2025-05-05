package com.invetronix.backend.APIGemini.services;

import com.google.gson.Gson;
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.model.DetalleCompra;
import com.invetronix.backend.APIcompras.repository.CompraRepository;
import com.invetronix.backend.APIproducts.model.Producto;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ServiceGemini implements IServiceGemini {

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private final CompraRepository compraRepository;
    private final String apiKey;

    public ServiceGemini(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("GEMINI_API_KEY");
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new RuntimeException("La API key de Gemini no está configurada. Por favor, configura la variable de entorno GEMINI_API_KEY");
        }
    }

    @Override
    public String obtenerPrediccionVentas() {
        try {
            // Obtener datos de ventas
            List<Map<String, Object>> datosVentas = obtenerDatosVentas();
            
            // Si no hay datos, retornar mensaje
            if (datosVentas.isEmpty()) {
                return "No hay suficientes datos de ventas para generar una predicción.";
            }
            
            // Preparar prompt para Gemini
            String prompt = crearPromptParaGemini(datosVentas);
            
            // Obtener predicción de Gemini
            return obtenerPrediccionDeGemini(prompt);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar predicciones: " + e.getMessage(), e);
        }
    }

    private List<Map<String, Object>> obtenerDatosVentas() {
        LocalDate fechaInicio = LocalDate.now().minus(3, ChronoUnit.MONTHS);
        List<Compra> compras = compraRepository.findByFechaCompraAfter(fechaInicio);
        
        Map<Producto, Integer> ventasPorProducto = new HashMap<>();
        
        for (Compra compra : compras) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                Producto producto = detalle.getProducto();
                ventasPorProducto.merge(producto, detalle.getCantidad(), Integer::sum);
            }
        }
        
        List<Map<String, Object>> datos = new ArrayList<>();
        for (Map.Entry<Producto, Integer> entry : ventasPorProducto.entrySet()) {
            Map<String, Object> dato = new HashMap<>();
            dato.put("nombre", entry.getKey().getNombre());
            dato.put("ventas", entry.getValue());
            datos.add(dato);
        }
        
        return datos;
    }

    private String crearPromptParaGemini(List<Map<String, Object>> datosVentas) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Analiza los siguientes datos de ventas de los últimos 3 meses y genera un mensaje simple que indique:\n\n");
        prompt.append("1. Cuál es el producto más vendido actualmente\n");
        prompt.append("2. Cuál será el producto que más se venderá en el futuro\n");
        prompt.append("3. Una breve explicación del por qué\n\n");
        prompt.append("Datos de ventas:\n");
        
        for (Map<String, Object> dato : datosVentas) {
            prompt.append(String.format("- %s: %d unidades vendidas\n", 
                dato.get("nombre"), 
                dato.get("ventas")));
        }
        
        prompt.append("\nPor favor, responde con un mensaje simple y directo, sin formato JSON ni estructura compleja.");
        
        return prompt.toString();
    }

    private String obtenerPrediccionDeGemini(String prompt) throws IOException {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-latest:generateContent?key=" + apiKey;
        
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> contents = new HashMap<>();
        List<Map<String, Object>> parts = new ArrayList<>();
        Map<String, Object> part = new HashMap<>();
        
        part.put("text", prompt);
        parts.add(part);
        
        Map<String, Object> content = new HashMap<>();
        content.put("parts", parts);
        
        List<Map<String, Object>> contentsList = new ArrayList<>();
        contentsList.add(content);
        
        requestBody.put("contents", contentsList);
        
        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("temperature", 0.7);
        generationConfig.put("maxOutputTokens", 1024);
        
        requestBody.put("generationConfig", generationConfig);
        
        String jsonBody = gson.toJson(requestBody);
        
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "Sin detalles del error";
                throw new IOException("Error en la petición a Gemini: " + response.code() + " - " + errorBody);
            }
            
            String responseBody = response.body().string();
            return extraerTextoDeRespuesta(responseBody);
        }
    }

    private String extraerTextoDeRespuesta(String jsonResponse) {
        try {
            Map<String, Object> responseMap = gson.fromJson(jsonResponse, Map.class);
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseMap.get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                throw new RuntimeException("No se encontraron candidatos en la respuesta de Gemini");
            }
            
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            if (content == null) {
                throw new RuntimeException("No se encontró contenido en la respuesta de Gemini");
            }
            
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            if (parts == null || parts.isEmpty()) {
                throw new RuntimeException("No se encontraron partes en la respuesta de Gemini");
            }
            
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la respuesta de Gemini: " + e.getMessage());
        }
    }
}