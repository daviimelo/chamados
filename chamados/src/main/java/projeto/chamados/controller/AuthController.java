package projeto.chamados.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projeto.chamados.dto.LoginRequest;
import projeto.chamados.dto.LoginResponse;
import projeto.chamados.dto.UsuarioRequest;
import projeto.chamados.dto.UsuarioResponse;
import projeto.chamados.service.AuthService;
import projeto.chamados.service.UsuarioService;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return new LoginResponse(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse signup(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.salvar(request);
    }
}