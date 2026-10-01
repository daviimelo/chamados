package projeto.chamados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;
import projeto.chamados.model.Categoria;
import projeto.chamados.model.StatusChamado;

import java.util.List;
import java.util.UUID;

public interface ChamadoService {
    ChamadoResponse cadastrar(UUID usuarioId, ChamadoRequest chamadoRequest);
    Page<ChamadoResponse> listarChamadosPorUsuarioAberto(UUID id, Pageable pageable);

    Page<ChamadoResponse> listarMeusChamados(UUID id, StatusChamado status, String busca, Pageable pageable);
    ChamadoResponse buscarPorId(UUID chamadoId, UUID usuarioAutenticadoId, boolean isAdmin);
    Page<ChamadoResponse> listarTodosAdmin(StatusChamado status, Categoria categoria, Pageable pageable);
}
