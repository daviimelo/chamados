package projeto.chamados.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projeto.chamados.dto.UsuarioRequest;
import projeto.chamados.dto.UsuarioResponse;
import projeto.chamados.model.Papel;
import projeto.chamados.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final UsuarioService usuarioService;

    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse salvarAdmin(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.salvarAdmin(request);
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listar(Papel.ADMINISTRADOR);
    }
}