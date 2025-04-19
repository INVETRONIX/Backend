package com.invetronix.backend.APIproducts.mappers;

import java.util.List;
import java.util.stream.Collectors;
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
        .supplier(dto.getSupplier())
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
        .supplier(entity.getSupplier())
        .build();
    }

    public static EntityProduct toEntity(Product model){

        return new EntityProduct(
            model.getName(), 
            model.getDescription(), 
            model.getPrice(), 
            model.getCategory(), 
            model.getStockQuantity(), 
            model.getSupplier()
        );

    }

    public static DtoProduct toDto(Product model){
        return DtoProduct.builder()
        .id(model.getId())
        .name(model.getName())
        .description(model.getDescription())
        .price(model.getPrice())
        .category(model.getCategory())
        .stockQuantity(model.getStockQuantity())
        .supplier(model.getSupplier())
        .build();
    }

    public static List<Product> toModelFromDto(List<DtoProduct> dtos) {
        return dtos.stream()
            .map(MapperProduct::toModel)
            .collect(Collectors.toList());
    }
    
    public static List<Product> toModelFromEntity(List<EntityProduct> entities) {
        return entities.stream()
            .map(MapperProduct::toModel)
            .collect(Collectors.toList());
    }

    public static List<EntityProduct> toEntityFromModel(List<Product> products) {
        return products.stream()
            .map(MapperProduct::toEntity)
            .collect(Collectors.toList());
    }

    public static List<DtoProduct> toDtoFromModel(List<Product> products) {
        return products.stream()
        .map(MapperProduct::toDto)
        .collect(Collectors.toList());
    }
    
}