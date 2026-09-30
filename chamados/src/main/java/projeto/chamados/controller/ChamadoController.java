package projeto.chamados.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;
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
            @Valid @RequestBody ChamadoRequest chamadoRequest) {
        return chamadoService.cadastrar(usuarioId, chamadoRequest);
    }

    @GetMapping("/{usuarioId}/chamados/abertos")
    public Page<ChamadoResponse> listaChamadosPorUsuario(@PathVariable UUID usuarioId, Pageable pageable) {
        return chamadoService.listarChamadosPorUsuarioAberto(usuarioId, pageable);
    }
}