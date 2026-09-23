package projeto.chamados.service;

import org.springframework.stereotype.Service;
import projeto.chamados.dao.UsuarioRepository;
import projeto.chamados.dto.UsuarioRequest;
import projeto.chamados.dto.UsuarioResponse;
import projeto.chamados.core.exception.APIException;
import projeto.chamados.core.exception.APIExceptionType;
import projeto.chamados.model.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse salvar(UsuarioRequest usuarioRequest) {
        boolean existeUsuarioComEsseEmail = usuarioRepository.existsByEmail(usuarioRequest.email());
        if (existeUsuarioComEsseEmail) {
            throw new APIException(APIExceptionType.CONFLICT, "Já existe um usuário com esse email cadastrado!");
        }

        Usuario usuario = new Usuario(usuarioRequest.nome(), usuarioRequest.email(), usuarioRequest.senha());
        Usuario usuarioCriado = usuarioRepository.save(usuario);
        return new UsuarioResponse(usuarioCriado.getId(), usuarioCriado.getNome(), usuarioCriado.getEmail());
    }
}
