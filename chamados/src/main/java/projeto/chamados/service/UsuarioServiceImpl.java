package projeto.chamados.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projeto.chamados.core.exception.APIException;
import projeto.chamados.core.exception.APIExceptionType;
import projeto.chamados.dao.UsuarioRepository;
import projeto.chamados.dto.UsuarioRequest;
import projeto.chamados.dto.UsuarioResponse;
import projeto.chamados.model.Papel;
import projeto.chamados.model.Usuario;

import java.util.List;
import java.util.Locale;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponse salvar(UsuarioRequest request) {
        return criar(request, Papel.USUARIO);
    }

    @Override
    public UsuarioResponse salvarAdmin(UsuarioRequest request) {
        return criar(request, Papel.ADMINISTRADOR);
    }

    @Override
    public List<UsuarioResponse> listar(Papel papel) {
        List<Usuario> usuarios = (papel == null)
                ? usuarioRepository.findAll()
                : usuarioRepository.findByPapel(papel);

        return usuarios.stream().map(this::toResponse).toList();
    }

    private UsuarioResponse criar(UsuarioRequest request, Papel papel) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);

        if (usuarioRepository.existsByEmail(email)) {
            throw new APIException(APIExceptionType.CONFLICT, "Já existe um usuário com esse email cadastrado!");
        }

        Usuario usuario = new Usuario(
                request.nome().trim(),
                email,
                passwordEncoder.encode(request.senha()),
                papel
        );

        try {
            return toResponse(usuarioRepository.saveAndFlush(usuario));
        } catch (DataIntegrityViolationException e) {
            throw new APIException(APIExceptionType.CONFLICT, "Já existe um usuário com esse email cadastrado!");
        }
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getPapel());
    }
}
