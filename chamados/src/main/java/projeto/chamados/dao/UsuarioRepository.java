package projeto.chamados.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.chamados.model.Usuario;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
}
