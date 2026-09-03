package projeto.chamados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;

import java.util.List;
import java.util.UUID;

public interface ChamadoService {
    ChamadoResponse cadastrar(UUID usuarioId, ChamadoRequest chamadoRequest);
    Page<ChamadoResponse> listarChamadosPorUsuarioAberto(UUID id, Pageable pageable);
}
