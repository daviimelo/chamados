package projeto.chamados.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projeto.chamados.model.Categoria;
import projeto.chamados.model.Chamado;
import projeto.chamados.model.StatusChamado;

import java.util.UUID;

public interface ChamadoRepository extends JpaRepository<Chamado, UUID> {
    Page<Chamado> findByUsuarioIdAndStatus(UUID usuarioId, StatusChamado status, Pageable pageable);

    @Query("SELECT c FROM Chamado c WHERE c.usuario.id = :usuarioId " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (cast(:busca as text) IS NULL OR LOWER(c.titulo) LIKE LOWER(CONCAT('%', cast(:busca as text), '%')) OR LOWER(c.descricao) LIKE LOWER(CONCAT('%', cast(:busca as text), '%')))")
    Page<Chamado> buscarMeusChamados(@Param("usuarioId") UUID usuarioId,
                                     @Param("status") StatusChamado status,
                                     @Param("busca") String busca,
                                     Pageable pageable);

    @Query("SELECT c FROM Chamado c WHERE (:status IS NULL OR c.status = :status) " +
            "AND (:categoria IS NULL OR c.categoria = :categoria)")
    Page<Chamado> buscarChamadosAdmin(@Param("status") StatusChamado status,
                                      @Param("categoria") Categoria categoria,
                                      Pageable pageable);
}
