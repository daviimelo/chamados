package projeto.chamados.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;
import projeto.chamados.dto.UsuarioLogadoDto;
import projeto.chamados.model.Papel;
import projeto.chamados.service.ChamadoService;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping("/{usuarioId}/chamados")
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponse cadastrar(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody ChamadoRequest chamadoRequest,
            Authentication authentication) {
        garantirAcesso(usuarioId, authentication);
        return chamadoService.cadastrar(usuarioId, chamadoRequest);
    }

    @GetMapping("/{usuarioId}/chamados/abertos")
    public Page<ChamadoResponse> listaChamadosPorUsuario(
            @PathVariable UUID usuarioId,
            Pageable pageable,
            Authentication authentication) {
        garantirAcesso(usuarioId, authentication);
        return chamadoService.listarChamadosPorUsuarioAberto(usuarioId, pageable);
    }

    /* Só o próprio usuário pode mexer nos chamados de {usuarioId}. */
    private void garantirAcesso(UUID usuarioId, Authentication authentication) {
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + Papel.ADMINISTRADOR.name()));
        boolean dono = authentication.getPrincipal() instanceof UsuarioLogadoDto logado
                && logado.id().equals(usuarioId);

        if (!admin && !dono) {
            throw new AccessDeniedException("Você não tem permissão para acessar os chamados de outro usuário");
        }
    }
}
