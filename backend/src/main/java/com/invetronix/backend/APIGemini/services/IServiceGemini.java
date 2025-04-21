package com.invetronix.backend.APIGemini.services;

import java.io.IOException;


public interface IServiceGemini {
    String predecirProductosMasVendidos() throws IOException;
    String extractTextFromResponse(String jsonResponse);
}
