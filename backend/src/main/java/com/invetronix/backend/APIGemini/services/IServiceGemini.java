package com.invetronix.backend.APIGemini.services;

import java.io.IOException;
import java.util.List;

import com.invetronix.backend.APIproducts.models.Product;

public interface IServiceGemini {
    String predecirProductosMasVendidos(List<Product> products) throws IOException;
    String extractTextFromResponse(String jsonResponse);
}
