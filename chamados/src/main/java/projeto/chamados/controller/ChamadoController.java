package projeto.chamados.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;
import projeto.chamados.dto.UsuarioLogadoDto;
import projeto.chamados.model.Categoria;
import projeto.chamados.model.Papel;
import projeto.chamados.model.StatusChamado;
import projeto.chamados.service.ChamadoService;

import java.util.UUID;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponse cadastrar(
            @Valid @RequestBody ChamadoRequest chamadoRequest,
            Authentication authentication) {
        UUID usuarioId = ((UsuarioLogadoDto) authentication.getPrincipal()).id();
        return chamadoService.cadastrar(usuarioId, chamadoRequest);
    }

    @GetMapping("/abertos")
    public Page<ChamadoResponse> listaChamadosPorUsuario(
            Pageable pageable,
            Authentication authentication) {
        UUID usuarioId = ((UsuarioLogadoDto) authentication.getPrincipal()).id();
        return chamadoService.listarChamadosPorUsuarioAberto(usuarioId, pageable);
    }

    @GetMapping("/me")
    public Page<ChamadoResponse> meusChamados(
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) String busca,
            Pageable pageable,
            Authentication authentication) {
        UUID usuarioId = ((UsuarioLogadoDto) authentication.getPrincipal()).id();
        return chamadoService.listarMeusChamados(usuarioId, status, busca, pageable);
    }

    @GetMapping("/{id}")
    public ChamadoResponse buscarPorId(
            @PathVariable UUID id,
            Authentication authentication) {
        UUID usuarioId = ((UsuarioLogadoDto) authentication.getPrincipal()).id();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + Papel.ADMINISTRADOR.name()));
        return chamadoService.buscarPorId(id, usuarioId, isAdmin);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Page<ChamadoResponse> listagemAdmin(
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) Categoria categoria,
            Pageable pageable) {
        return chamadoService.listarTodosAdmin(status, categoria, pageable);
    }
}
