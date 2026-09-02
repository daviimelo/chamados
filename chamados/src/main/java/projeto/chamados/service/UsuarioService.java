package projeto.chamados.service;

import org.springframework.stereotype.Service;
import projeto.chamados.dao.UsuarioRepository;
import projeto.chamados.dto.UsuarioRequest;
import projeto.chamados.dto.UsuarioResponse;

@Service
public interface UsuarioService {
    UsuarioResponse salvar(UsuarioRequest usuarioRequest);
}
