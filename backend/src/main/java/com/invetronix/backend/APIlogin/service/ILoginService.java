package com.invetronix.backend.APIlogin.service;

import com.invetronix.backend.APIlogin.model.LoginRequest;

public interface ILoginService {
    String login(LoginRequest loginRequest);
}
