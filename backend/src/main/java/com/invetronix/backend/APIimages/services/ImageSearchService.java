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
            RestTemplate restTemplate = new RestTemplate();
            
            // Configurar headers y parámetros
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", "Client-ID " + ACCESS_KEY);
            
            // Crear la URL completa
            String url = UNSPLASH_API_URL + "?query=" + query + "&per_page=1";
            
            // Realizar la petición
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
            org.springframework.http.ResponseEntity<String> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                entity,
                String.class
            );
            
            // Procesar la respuesta
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray results = jsonResponse.getJSONArray("results");
            
            if (results.length() > 0) {
                JSONObject firstImage = results.getJSONObject(0);
                String imageUrl = firstImage.getJSONObject("urls").getString("regular");
                
                return new JSONObject()
                    .put("status", "success")
                    .put("imageUrl", imageUrl)
                    .toString();
            } else {
                return new JSONObject()
                    .put("status", "error")
                    .put("message", "No se encontraron imágenes")
                    .toString();
            }
            
        } catch (Exception e) {
            return new JSONObject()
                .put("status", "error")
                .put("message", "Error al buscar la imagen: " + e.getMessage())
                .toString();
        }
    }
}