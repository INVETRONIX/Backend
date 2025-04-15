package com.invetronix.backend.registroUsuario.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.exceptions.UserAlreadyRegisteredException;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceRegister {
    private final RepositoryRegister repositoryRegister;

    public Client save(Client client){
        if(repositoryRegister.findByEmail(client.getEmail()).isPresent()){
            throw new UserAlreadyRegisteredException("el usuario con email: " + client.getEmail() + "ya esta registrado");
        }

        client.setName(client.getName().toLowerCase());
       
        EntityClient entityClient = MapperUser.toEntity(client);
        return MapperUser.toModel(repositoryRegister.save(entityClient));

    }

    public Optional<Client> findById(String id){

        Optional<EntityClient> entity = repositoryRegister.findById(id);
        if(!entity.isPresent()){
            throw new UserNotFoundException("El usuario con id: " + id + " no existe");
        }
        Client user = MapperUser.toModel(entity.get());
        return Optional.of(user);

    }

    public Optional<Client> findByEmail(String email) {
        Optional<EntityClient> entity = repositoryRegister.findByEmail(email);
        if(!entity.isPresent()) {
            throw new UserNotFoundException("El usuario con email: " + email + " no existe");
        }
        Client user = MapperUser.toModel(entity.get());
        return Optional.of(user);
    }

    public List<Client> findAll() {
        return repositoryRegister.findAll().stream()
                .map(MapperUser::toModel)
                .collect(Collectors.toList());
    }

    public List<Client> findByFilters(String name, String email){
        List<EntityClient> entities = repositoryRegister.findByFilters(
            name != null ? name.toLowerCase() : null,
            email != null ? email: null
        );

        return entities.stream()
                .map(MapperUser::toModel)
                .collect(Collectors.toList());
    }

    public Optional<Client> deleteById(String id){
        Optional<EntityClient> entity = repositoryRegister.findById(id);
        if(!entity.isPresent()){
            throw new UserNotFoundException("El usuario con id: " + id + " no existe");
        }
        repositoryRegister.delete(id);
        return Optional.of(MapperUser.toModel(entity.get()));
    }

    public Optional<Client> updateById(String id, Client client){
        Optional<EntityClient> entity = repositoryRegister.findById(id);
        if(!entity.isPresent()){
            throw new UserNotFoundException("El usuario con id: " + id + " no existe");
        }
      
        entity.get().setName(client.getName());
        entity.get().setEmail(client.getEmail());
        entity.get().setPhone(client.getPhone());
        entity.get().setAge(client.getAge());
        entity.get().setPassword(client.getPassword());

        Client userUpdated = MapperUser.toModel(repositoryRegister.update(entity.get()).get());
        return Optional.of(userUpdated);
    
    }

}
