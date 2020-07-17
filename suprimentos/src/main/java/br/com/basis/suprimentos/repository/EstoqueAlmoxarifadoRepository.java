package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the EstoqueAlmoxarifado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstoqueAlmoxarifadoRepository extends JpaRepository<EstoqueAlmoxarifado, Long> {
    Optional<EstoqueAlmoxarifado> findByAlmoxarifadoIdAndMaterialId(Long almoxarifadoId, Long materialId);
}
