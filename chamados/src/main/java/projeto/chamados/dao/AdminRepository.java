package projeto.chamados.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.chamados.model.Administrador;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Administrador, UUID> {
    boolean existsByEmail(String email);
}
