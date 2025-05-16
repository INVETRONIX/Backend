package com.invetronix.backend.APIlogin.service;

import com.invetronix.backend.APIlogin.model.LoginRequest;
import com.invetronix.backend.APIlogin.model.LoginResponse;

public interface ILoginService {
    LoginResponse login(LoginRequest loginRequest);
}
