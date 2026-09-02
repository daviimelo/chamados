package projeto.chamados.service;

import org.springframework.stereotype.Service;
import projeto.chamados.dao.ChamadoRepository;
import projeto.chamados.dao.UsuarioRepository;
import projeto.chamados.dto.ChamadoRequest;
import projeto.chamados.dto.ChamadoResponse;
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
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        Chamado chamado = new Chamado(chamadoRequest.titulo(), chamadoRequest.descricao(), chamadoRequest.prioridade());
        chamado.setUsuario(usuario);
        chamado.setStatus(StatusChamado.ABERTO);

        Chamado chamadoSalvo = chamadoRepository.save(chamado);

        return new ChamadoResponse(
                chamadoSalvo.getId(),
                chamadoSalvo.getTitulo(),
                chamadoSalvo.getDescricao(),
                chamadoSalvo.getPrioridade(),
                chamadoSalvo.getStatus(),
                chamadoSalvo.getUsuario().getId()
        );
    }

    @Override
    public List<ChamadoResponse> listarChamadosPorUsuarioAberto(UUID id) {
        List<Chamado> chamados = chamadoRepository.findByUsuarioIdAndStatus(id, StatusChamado.ABERTO);

        return chamados
                .stream()
                .map(chamado -> new ChamadoResponse(chamado.getId(),
                        chamado.getTitulo(),
                        chamado.getDescricao(),
                        chamado.getPrioridade(),
                        chamado.getStatus(),
                        chamado.getUsuario().getId()))
                .toList();
    }
}
