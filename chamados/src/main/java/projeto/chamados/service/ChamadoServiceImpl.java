package projeto.chamados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projeto.chamados.dao.ChamadoRepository;
import projeto.chamados.dao.UsuarioRepository;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;
import projeto.chamados.exception.APIException;
import projeto.chamados.exception.APIExceptionType;
import projeto.chamados.model.Chamado;
import projeto.chamados.model.StatusChamado;
import projeto.chamados.model.Usuario;

import java.util.List;
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

        Chamado chamadoSalvo = chamadoRepository.save(chamado);

        return new ChamadoResponse(
                chamadoSalvo.getId(),
                chamadoSalvo.getTitulo(),
                chamadoSalvo.getDescricao(),
                chamadoSalvo.getPrioridade(),
                chamadoSalvo.getStatus(),
                chamadoSalvo.getCategoria(),
                chamadoSalvo.getUsuario().getId()
        );
    }

    @Override
    public Page<ChamadoResponse> listarChamadosPorUsuarioAberto(UUID id, Pageable pageable) {

        Page<Chamado> paginaChamados = chamadoRepository.findByUsuarioIdAndStatus(id, StatusChamado.ABERTO, pageable);

        return paginaChamados
                .map(chamado -> new ChamadoResponse(chamado.getId(),
                        chamado.getTitulo(),
                        chamado.getDescricao(),
                        chamado.getPrioridade(),
                        chamado.getStatus(),
                        chamado.getCategoria(),
                        chamado.getUsuario().getId()));
    }
}
