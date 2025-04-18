package com.invetronix.backend.APIproducts.mappers;

import com.invetronix.backend.APIproducts.dtos.DtoProduct;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.models.Product;

public interface MapperProduct {

    public static Product toModel(DtoProduct dto){
        return Product.builder()
        .id(dto.getId())
        .name(dto.getName())
        .description(dto.getDescription())
        .price(dto.getPrice())
        .category(dto.getCategory())
        .stockQuantity(dto.getStockQuantity())
        .supplier(MapperSupplier.toModel(dto.getSupplier()))
        .build();
    }

    public static Product toModel(EntityProduct entity){
        return Product.builder()
        .id(entity.getId())
        .name(entity.getName())
        .description(entity.getDescription())
        .price(entity.getPrice())
        .category(entity.getCategory())
        .stockQuantity(entity.getStockQuantity())
        .supplier(MapperSupplier.toModel(entity.getSupplier()))
        .build();
    }

    public static EntityProduct toEntity(Product model){

        return new EntityProduct(
            model.getName(), 
            model.getDescription(), 
            model.getPrice(), 
            model.getCategory(), 
            model.getStockQuantity(), 
            MapperSupplier.toEntity(model.getSupplier())
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