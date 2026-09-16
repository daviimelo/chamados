package projeto.chamados.service;

import projeto.chamados.dto.LoginRequest;
import projeto.chamados.dto.LoginResponse;

public interface AuthService {
    String login(LoginRequest loginRequest);
}
