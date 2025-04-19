package com.invetronix.backend.registroUsuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.ServiceRegister;
import com.invetronix.backend.registroUsuario.services.in.IValidationServiceRegister;
import com.invetronix.backend.registroUsuario.services.usecases.DeleteUserByEmail;
import com.invetronix.backend.registroUsuario.services.usecases.DeleteUserById;
import com.invetronix.backend.registroUsuario.services.usecases.FindAllUsers;
import com.invetronix.backend.registroUsuario.services.usecases.FindUserByAuthToken;
import com.invetronix.backend.registroUsuario.services.usecases.FindUserByEmail;
import com.invetronix.backend.registroUsuario.services.usecases.FindUserById;
import com.invetronix.backend.registroUsuario.services.usecases.FindUsersByFilters;
import com.invetronix.backend.registroUsuario.services.usecases.SaveUser;
import com.invetronix.backend.registroUsuario.services.usecases.UpdateUserByEmail;
import com.invetronix.backend.registroUsuario.services.usecases.UpdateUserById;

@Configuration
public class RegisterConfig {
    
    @Bean
    public ServiceRegister serviceRegister(RepositoryRegister repositoryRegister, IValidationServiceRegister validationServiceRegister, PasswordEncoder passwordEncoder) {
        return new ServiceRegister(new SaveUser(repositoryRegister, validationServiceRegister, passwordEncoder), 
        new FindUserById(repositoryRegister, validationServiceRegister), new FindUserByEmail(repositoryRegister, validationServiceRegister), 
        new DeleteUserById(repositoryRegister, validationServiceRegister), new DeleteUserByEmail(repositoryRegister, validationServiceRegister), 
        new UpdateUserById(repositoryRegister, validationServiceRegister), new UpdateUserByEmail(repositoryRegister, validationServiceRegister), new FindAllUsers(repositoryRegister), 
        new FindUsersByFilters(repositoryRegister), new FindUserByAuthToken(repositoryRegister));
    }
}
