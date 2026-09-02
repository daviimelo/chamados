package projeto.chamados.controller;

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
            @RequestBody ChamadoRequest chamadoRequest) {
        ChamadoResponse chamadoCriado = chamadoService.cadastrar(usuarioId, chamadoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoCriado);
    }

    @GetMapping("/{usuarioId}/chamados/abertos")
    public ResponseEntity<List<ChamadoResponse>> listaChamadosPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(chamadoService.listarChamadosPorUsuarioAberto(usuarioId));
    }
}
