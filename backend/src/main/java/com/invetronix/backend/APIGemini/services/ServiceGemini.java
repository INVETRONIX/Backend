package com.invetronix.backend.APIGemini.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.invetronix.backend.APIproducts.models.Product;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ServiceGemini implements IServiceGemini{

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private String apiKey = "AIzaSyCQbeRycKQc-55JVfvEineJzvuJZzFI330";

    public String predecirProductosMasVendidos(List<Product> products) throws IOException {
        String prompt = "Según la siguiente lista de productos, dime cuáles productos son los que más y menos se van a vender, "
                      + "basándote en su precio, categoría y stock. Responde solo con el análisis solicitado, sin añadir información adicional:\n"
                      + gson.toJson(products);

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-latest:generateContent?key=" + apiKey;

        String requestBody = String.format("""
        {
          "contents": [{
            "parts": [{
              "text": "%s"
            }]
          }],
          "generationConfig": {
            "temperature": 0.9,
            "topK": 40,
            "topP": 0.95,
            "maxOutputTokens": 1024
          }
        }
        """, prompt.replace("\"", "\\\""));

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                    requestBody,
                    MediaType.parse("application/json; charset=utf-8")
                ))
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = Objects.requireNonNull(response.body()).string();
                throw new IOException("Error en la petición: " + response.code() + " - " + errorBody);
            }
            
            // Procesar la respuesta para extraer solo el texto
            String responseBody = Objects.requireNonNull(response.body()).string();
            return extractTextFromResponse(responseBody);
        }
    }

    public String extractTextFromResponse(String jsonResponse) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            return jsonObject.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();
        } catch (Exception e) {
            return "Error al procesar la respuesta de Gemini: " + e.getMessage();
        }
    }
}