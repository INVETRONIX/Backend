package com.invetronix.backend.APIproducts.mappers;

import com.invetronix.backend.APIproducts.dtos.DtoProduct;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIsuppliers.mappers.MapperSupplier;

public interface MapperProduct {

    public static Product toModel(DtoProduct dtoProduct){
        return Product.builder()
        .id(dtoProduct.getId())
        .name(dtoProduct.getName())
        .description(dtoProduct.getDescription())
        .price(dtoProduct.getPrice())
        .category(dtoProduct.getCategory())
        .stockQuantity(dtoProduct.getStockQuantity())
        .supplier(MapperSupplier.toModel(dtoProduct.getSupplier()))
        .build();
    }

    public static Product toModel(EntityProduct entityProduct){
        return Product.builder()
        .id(entityProduct.getId())
        .name(entityProduct.getName())
        .description(entityProduct.getDescription())
        .price(entityProduct.getPrice())
        .category(entityProduct.getCategory())
        .stockQuantity(entityProduct.getStockQuantity())
        .supplier(MapperSupplier.toModel(entityProduct.getSupplier()))
        .build();
    }

    public static EntityProduct toEntity(Product product){

        return new EntityProduct(
            product.getName(), 
            product.getDescription(), 
            product.getPrice(), 
            product.getCategory(), 
            product.getStockQuantity(), 
            MapperSupplier.toEntity(product.getSupplier())
        );

    }

    public static DtoProduct toDto(Product product){
        return DtoProduct.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .category(product.getCategory())
        .stockQuantity(product.getStockQuantity())
        .supplier(MapperSupplier.toDto(product.getSupplier()))
        .build();
    }
    
}