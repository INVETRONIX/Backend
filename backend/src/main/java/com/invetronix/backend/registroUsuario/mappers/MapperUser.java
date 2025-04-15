package com.invetronix.backend.registroUsuario.mappers;


import com.invetronix.backend.registroUsuario.dto.DtoClient;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.models.Client;

public class MapperUser {

    public static Client toModel(DtoClient dto){

        return Client.builder()
        .name(dto.getName())
        .email(dto.getEmail())
        .password(dto.getPassword())
        .age(dto.getAge())
        .phone(dto.getPhone())
        .build();
        
    }

    public static DtoClient toDto(Client model){

        return DtoClient.builder()
        .id(model.getId())
        .name(model.getName())
        .email(model.getEmail())
        .password(model.getPassword())
        .age(model.getAge())
        .phone(model.getPhone())
        .build();
        
    }

    public static EntityClient toEntity(Client model){

        return new EntityClient(model.getName(), model.getEmail(),model.getPassword(), model.getAge(), model.getPhone());
    
    }

    public static Client toModel(EntityClient entity){

        return Client.builder()
        .id(entity.getId())
        .name(entity.getName())
        .email(entity.getEmail())
        .password(entity.getPassword())
        .age(entity.getAge())
        .phone(entity.getPhone())
        .build();

    }

}