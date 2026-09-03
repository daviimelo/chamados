package projeto.chamados.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import projeto.chamados.model.Chamado;
import projeto.chamados.model.StatusChamado;

import java.util.List;
import java.util.UUID;

public interface ChamadoRepository extends JpaRepository<Chamado, UUID> {
    Page<Chamado> findByUsuarioIdAndStatus(UUID usuarioId, StatusChamado status, Pageable pageable);
}
