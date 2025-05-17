package com.invetronix.backend.APIGemini.service;

import com.invetronix.backend.APIGemini.model.GeminiResponse;
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GeminiService {
    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

    @Value("${gemini.api.key}")
    private String apiKey;

    @Autowired
    private CompraService compraService;

    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    public GeminiResponse analizarVentas() {
        try {
            List<Compra> compras = compraService.getAll();
            if (compras.isEmpty()) {
                GeminiResponse response = new GeminiResponse();
                response.setAnalisis("No hay datos de ventas para analizar");
                return response;
            }

            String prompt = construirPrompt(compras);
            logger.info("Prompt construido: {}", prompt);
            
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            List<Map<String, Object>> parts = new ArrayList<>();
            parts.add(Map.of("text", prompt));
            content.put("parts", parts);
            requestBody.put("contents", List.of(content));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            logger.info("Enviando petición a Gemini API");
            String url = GEMINI_API_URL + "?key=" + apiKey;
            logger.info("URL completa: {}", url);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(
                url,
                request,
                Map.class
            );

            return procesarRespuesta(response);
        } catch (Exception e) {
            logger.error("Error al analizar ventas: ", e);
            GeminiResponse response = new GeminiResponse();
            response.setAnalisis("Error al procesar la solicitud: " + e.getMessage());
            return response;
        }
    }

    private String construirPrompt(List<Compra> compras) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Analiza los siguientes datos de ventas y proporciona un análisis detallado:\n\n");
        
        // Agrupar compras por producto
        Map<Long, Integer> ventasPorProducto = new HashMap<>();
        for (Compra compra : compras) {
            Long productoId = compra.getProducto().getId();
            ventasPorProducto.put(productoId, ventasPorProducto.getOrDefault(productoId, 0) + 1);
        }

        // Agregar datos al prompt
        prompt.append("Datos de ventas por producto:\n");
        ventasPorProducto.forEach((productoId, cantidad) -> {
            prompt.append(String.format("Producto ID %d: %d ventas\n", productoId, cantidad));
        });

        prompt.append("\nPor favor, proporciona un análisis que incluya:\n");
        prompt.append("1. Productos más vendidos y su tendencia\n");
        prompt.append("2. Productos menos vendidos y posibles razones\n");
        prompt.append("3. Predicción de tendencias futuras basada en los datos actuales\n");
        prompt.append("4. Recomendaciones para mejorar las ventas\n");
        prompt.append("\nEstructura tu respuesta claramente en tres secciones:\n");
        prompt.append("- Análisis de ventas actuales\n");
        prompt.append("- Predicciones futuras\n");
        prompt.append("- Recomendaciones para mejorar\n");

        return prompt.toString();
    }

    private GeminiResponse procesarRespuesta(ResponseEntity<Map> response) {
        GeminiResponse geminiResponse = new GeminiResponse();
        
        try {
            Map<String, Object> responseBody = response.getBody();
            logger.info("Respuesta de Gemini: {}", responseBody);
            
            if (responseBody != null && responseBody.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                    String text = (String) parts.get(0).get("text");

                    // Dividir la respuesta en secciones
                    String[] secciones = text.split("\n\n");
                    if (secciones.length >= 3) {
                        StringBuilder analisis = new StringBuilder();
                        StringBuilder prediccion = new StringBuilder();
                        StringBuilder recomendaciones = new StringBuilder();
                        
                        boolean enAnalisis = true;
                        boolean enPrediccion = false;
                        boolean enRecomendaciones = false;
                        
                        for (String seccion : secciones) {
                            if (seccion.toLowerCase().contains("predicci") && !seccion.toLowerCase().contains("recomend")) {
                                enAnalisis = false;
                                enPrediccion = true;
                                enRecomendaciones = false;
                                prediccion.append(seccion).append("\n\n");
                            } else if (seccion.toLowerCase().contains("recomend")) {
                                enAnalisis = false;
                                enPrediccion = false;
                                enRecomendaciones = true;
                                recomendaciones.append(seccion).append("\n\n");
                            } else if (enAnalisis) {
                                analisis.append(seccion).append("\n\n");
                            } else if (enPrediccion) {
                                prediccion.append(seccion).append("\n\n");
                            } else if (enRecomendaciones) {
                                recomendaciones.append(seccion).append("\n\n");
                            }
                        }
                        
                        geminiResponse.setAnalisis(analisis.toString().trim());
                        geminiResponse.setPrediccion(prediccion.toString().trim());
                        geminiResponse.setRecomendaciones(recomendaciones.toString().trim());
                    } else {
                        geminiResponse.setAnalisis(text);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al procesar respuesta: ", e);
            geminiResponse.setAnalisis("Error al procesar la respuesta de Gemini: " + e.getMessage());
        }

        return geminiResponse;
    }
}