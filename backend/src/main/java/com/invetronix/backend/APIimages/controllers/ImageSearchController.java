package com.invetronix.backend.APIimages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.invetronix.backend.APIimages.services.IImageSearchService;


@RestController
@RequestMapping("/api/images")
public class ImageSearchController {
    private final IImageSearchService imageSearchService;

    @Autowired
    public ImageSearchController(IImageSearchService imageSearchService) {
        this.imageSearchService = imageSearchService;
    }

    @GetMapping
    public String searchImage(@RequestParam String query) {
        return imageSearchService.searchImage(query);
    }
}