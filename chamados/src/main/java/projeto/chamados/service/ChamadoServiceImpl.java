package projeto.chamados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projeto.chamados.dao.ChamadoRepository;
import projeto.chamados.dao.UsuarioRepository;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;
import projeto.chamados.core.exception.APIException;
import projeto.chamados.core.exception.APIExceptionType;
import projeto.chamados.model.Categoria;
import projeto.chamados.model.Chamado;
import projeto.chamados.model.StatusChamado;
import projeto.chamados.model.Usuario;

import java.util.UUID;

@Service
public class ChamadoServiceImpl implements ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;

    public ChamadoServiceImpl(ChamadoRepository chamadoRepository, UsuarioRepository usuarioRepository) {
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ChamadoResponse cadastrar(UUID usuarioId, ChamadoRequest chamadoRequest) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new APIException(APIExceptionType.NOT_FOUND ,"Usuário não encontrado com o ID"));

        Chamado chamado = new Chamado(chamadoRequest.titulo(), chamadoRequest.descricao(), chamadoRequest.prioridade(), chamadoRequest.categoria());
        chamado.setUsuario(usuario);
        chamado.setStatus(StatusChamado.ABERTO);

        return toResponse(chamadoRepository.save(chamado));
    }

    @Override
    public Page<ChamadoResponse> listarChamadosPorUsuarioAberto(UUID id, Pageable pageable) {
        return chamadoRepository.findByUsuarioIdAndStatus(id, StatusChamado.ABERTO, pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<ChamadoResponse> listarMeusChamados(UUID id, StatusChamado status, String busca, Pageable pageable) {
        return chamadoRepository.buscarMeusChamados(id, status, busca, pageable)
                .map(this::toResponse);
    }

    @Override
    public ChamadoResponse buscarPorId(UUID chamadoId, UUID usuarioAutenticadoId, boolean isAdmin) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new APIException(APIExceptionType.NOT_FOUND, "Chamado não encontrado com o ID: " + chamadoId));

        if (!isAdmin && !chamado.getUsuario().getId().equals(usuarioAutenticadoId)) {
            throw new APIException(APIExceptionType.FORBIDDEN, "Você não tem permissão para acessar este chamado.");
        }

        return toResponse(chamado);
    }

    @Override
    public Page<ChamadoResponse> listarTodosAdmin(StatusChamado status, Categoria categoria, Pageable pageable) {
        return chamadoRepository.buscarChamadosAdmin(status, categoria, pageable).map(this::toResponse);
    }

    public ChamadoResponse toResponse(Chamado chamado) {
        return new ChamadoResponse(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getPrioridade(),
                chamado.getStatus(),
                chamado.getCategoria(),
                chamado.getUsuario().getId()
        );
    }
}
