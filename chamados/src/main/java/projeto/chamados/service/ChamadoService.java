package projeto.chamados.service;

import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;

import java.util.List;
import java.util.UUID;

public interface ChamadoService {
    ChamadoResponse cadastrar(UUID usuarioId, ChamadoRequest chamadoRequest);
    List<ChamadoResponse> listarChamadosPorUsuarioAberto(UUID id);
}
