package projeto.chamados.service;

import org.springframework.stereotype.Service;
import projeto.chamados.dao.UsuarioRepository;
import projeto.chamados.dto.UsuarioRequest;
import projeto.chamados.dto.UsuarioResponse;
import projeto.chamados.model.Papel;

import java.util.List;

@Service
public interface UsuarioService {
    UsuarioResponse salvar(UsuarioRequest request);
    UsuarioResponse salvarAdmin(UsuarioRequest request);
    List<UsuarioResponse> listar(Papel papel);
}
