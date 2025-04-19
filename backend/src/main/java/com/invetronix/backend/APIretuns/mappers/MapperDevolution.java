package com.invetronix.backend.APIretuns.mappers;

import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIretuns.dtos.DtoDevolution;
import com.invetronix.backend.APIretuns.entities.EntityDevolution;
import com.invetronix.backend.APIretuns.models.Devolution;

public class MapperDevolution {
    public static Devolution toModel(DtoDevolution dto){
        return Devolution.builder()
        .id(dto.getId())
        .purchase(MapperPurchase.toModel(dto.getPurchase()))
        .build();
    }

    public static Devolution toModel(EntityDevolution entity){
        return Devolution.builder()
        .id(entity.getId())
        .purchase(MapperPurchase.toModel(entity.getPurchase()))
        .build();
    }

    public static EntityDevolution toEntity(Devolution model){

        return new EntityDevolution(MapperPurchase.toEntity(model.getPurchase()));

    }

    public static DtoDevolution toDto(Devolution model){
        return DtoDevolution.builder()
        .id(model.getId())
        .purchase(MapperPurchase.toDto(model.getPurchase()))
        .build();
    }
}