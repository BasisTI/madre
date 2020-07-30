package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.EstoqueGeral;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Spring Data  repository for the EstoqueGeral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstoqueGeralRepository extends JpaRepository<EstoqueGeral, Long> {
    Optional<EstoqueGeral> findByMaterialId(@NotNull Long materialId);
}
