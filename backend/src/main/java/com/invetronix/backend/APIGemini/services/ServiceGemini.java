package com.invetronix.backend.APIGemini.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.invetronix.backend.APIGemini.model.ProductResumen;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIpurchases.data.Data;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ServiceGemini implements IServiceGemini{

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    Dotenv dotenv = Dotenv.load(); // Carga variables del .env
    private String apiKey =  dotenv.get("API_KEY");

    public String predecirProductosMasVendidos() throws IOException {

        List<ProductResumen> products = obtenerResumenProductosVendidos();



        String prompt = "Según la siguiente lista de productos con sus ventas totales, dime cuáles productos tienen más probabilidad de venderse y cuáles menos:\n"
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


    private  List<ProductResumen> obtenerResumenProductosVendidos(){
            // Leer ventas desde .dat
        List<EntityPurchase> ventas = new ArrayList<>(((Map<?, EntityPurchase>) Data.getInstance().read()).values());

        ProductResumen resumen = new ProductResumen();

        List<ProductResumen> listaResumen = new ArrayList<>();

        for (int i = 0; i < ventas.size(); i++) {
          for (int j = 0; j < ventas.get(i).getProducts().size(); j++) {
              if(ventas.get(i).getProducts().get(i)!=null){

                for (int k = 0; k < listaResumen.size(); k++) {
                    if(MapperProduct.toModel(ventas.get(i).getProducts().get(i)).equals(listaResumen.get(k).getProduct())){
                        listaResumen.get(k).setVentasTotales(listaResumen.get(k).getVentasTotales()+1);
                    }
                }



              }

              resumen.setProduct(MapperProduct.toModel(ventas.get(i).getProducts().get(i)));
              resumen.setVentasTotales(1);

              listaResumen.add(resumen);
          }
          
        }


        return listaResumen;
    }

    
}