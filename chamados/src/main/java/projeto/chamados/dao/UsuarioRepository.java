package projeto.chamados.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.chamados.model.Papel;
import projeto.chamados.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByPapel(Papel papel);
}
