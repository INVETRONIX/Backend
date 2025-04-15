package com.invetronix.backend.APIsuppliers.mappers;

import com.invetronix.backend.APIsuppliers.dtos.DtoSupplier;
import com.invetronix.backend.APIsuppliers.entities.EntitySupplier;
import com.invetronix.backend.APIsuppliers.models.Supplier;

public class MapperSupplier {
    
     public static Supplier toModel(DtoSupplier supplierDto){
        return Supplier.builder()
        .id(supplierDto.getId())
        .name(supplierDto.getName())
        .company(supplierDto.getCompany())
        .phone(supplierDto.getPhone())
        .email(supplierDto.getEmail())
        .build();
    }

    public static Supplier toModel(EntitySupplier entitySupplier){
        return Supplier.builder()
        .id(entitySupplier.getId())
        .name(entitySupplier.getName())
        .company(entitySupplier.getCompany())
        .phone(entitySupplier.getPhone())
        .email(entitySupplier.getEmail())
        .build();
    }

    public static EntitySupplier toEntity(Supplier supplier){
        return new EntitySupplier(supplier.getName(), supplier.getCompany(), supplier.getPhone(), supplier.getEmail());
    }

    public static DtoSupplier toDto(Supplier supplier){
        return DtoSupplier.builder()
        .id(supplier.getId())
        .name(supplier.getName())
        .company(supplier.getCompany())
        .phone(supplier.getPhone())
        .email(supplier.getEmail())
        .build();
    }
}