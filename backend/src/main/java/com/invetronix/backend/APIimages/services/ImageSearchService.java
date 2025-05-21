package com.invetronix.backend.APIimages.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImageSearchService implements IImageSearchService{
    private static final String UNSPLASH_API_URL = "https://api.unsplash.com/search/photos";
    private static final String ACCESS_KEY = "3yAZuNj-U4vRFgp5qRI1axcYsguLvZh14wb0MPX0zIs"; // Necesitarás registrarte en Unsplash

    public String searchImage(String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                return new JSONObject()
                    .put("status", "error")
                    .put("message", "La consulta no puede estar vacía")
                    .toString();
            }

            RestTemplate restTemplate = new RestTemplate();
            
            // Configurar headers y parámetros
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", "Client-ID " + ACCESS_KEY);
            
            // Crear la URL completa con parámetros codificados
            String encodedQuery = java.net.URLEncoder.encode(query.trim(), "UTF-8");
            String url = UNSPLASH_API_URL + "?query=" + encodedQuery + "&per_page=1";
            
            System.out.println("Buscando imagen con URL: " + url); // Para depuración
            
            // Realizar la petición
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
            org.springframework.http.ResponseEntity<String> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                entity,
                String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Procesar la respuesta
                JSONObject jsonResponse = new JSONObject(response.getBody());
                JSONArray results = jsonResponse.getJSONArray("results");
                
                if (results.length() > 0) {
                    JSONObject firstImage = results.getJSONObject(0);
                    String imageUrl = firstImage.getJSONObject("urls").getString("regular");
                    
                    System.out.println("Imagen encontrada: " + imageUrl); // Para depuración
                    
                    return new JSONObject()
                        .put("status", "success")
                        .put("imageUrl", imageUrl)
                        .toString();
                }
            }
            
            return new JSONObject()
                .put("status", "error")
                .put("message", "No se encontraron imágenes para: " + query)
                .toString();
            
        } catch (Exception e) {
            System.err.println("Error al buscar la imagen: " + e.getMessage());
            e.printStackTrace();
            return new JSONObject()
                .put("status", "error")
                .put("message", "Error al buscar la imagen: " + e.getMessage())
                .toString();
        }
    }
}