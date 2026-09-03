package projeto.chamados.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;
import projeto.chamados.service.ChamadoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping("/{usuarioId}/chamados")
    public ResponseEntity<ChamadoResponse> cadastrar(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody ChamadoRequest chamadoRequest) {
        ChamadoResponse chamadoCriado = chamadoService.cadastrar(usuarioId, chamadoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoCriado);
    }

    @GetMapping("/{usuarioId}/chamados/abertos")
    public ResponseEntity<Page<ChamadoResponse>> listaChamadosPorUsuario(@PathVariable UUID usuarioId, Pageable pageable) {
        Page<ChamadoResponse> response = chamadoService.listarChamadosPorUsuarioAberto(usuarioId, pageable);
        return ResponseEntity.ok(response);
    }
}
