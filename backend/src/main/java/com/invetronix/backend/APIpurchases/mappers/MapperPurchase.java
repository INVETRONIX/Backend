package com.invetronix.backend.APIpurchases.mappers;

import java.util.List;
import java.util.stream.Collectors;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIpurchases.dtos.DtoPurchase;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;

public class MapperPurchase {

     public static Purchase toModel(DtoPurchase dto){
        return Purchase.builder()
        .id(dto.getId())
        .client(MapperUser.toModel(dto.getClient()))
        .date(dto.getDate())
        .hour(dto.getHour())
        .total(dto.getTotal())
        .products(MapperProduct.toModelFromDto(dto.getProducts()))
        .build();
    }

    public static Purchase toModel(EntityPurchase entity){
        return Purchase.builder()
        .id(entity.getId())
        .client(MapperUser.toModel(entity.getClient()))
        .date(entity.getDate())
        .hour(entity.getHour())
        .total(entity.getTotal())
        .products(MapperProduct.toModelFromEntity(entity.getProducts()))
        .build();
    }

    public static EntityPurchase toEntity(Purchase model){

        return new EntityPurchase(
            MapperUser.toEntity(model.getClient()),
            MapperProduct.toEntityFromModel(model.getProducts()), 
            model.getTotal()
        );

    }

    public static DtoPurchase toDto(Purchase model){
        return DtoPurchase.builder()
        .id(model.getId())
        .client(MapperUser.toDto(model.getClient()))
        .date(model.getDate())
        .hour(model.getHour())
        .total(model.getTotal())
        .products(MapperProduct.toDtoFromModel(model.getProducts()))
        .build();
    }

    public static List<Purchase> toModelFromEntity(List<EntityPurchase> products) {
        return products.stream()
        .map(MapperPurchase::toModel)
        .collect(Collectors.toList());
    }

}