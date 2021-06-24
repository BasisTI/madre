package br.com.basis.madre.exames.repository;

import br.com.basis.madre.exames.domain.LaboratorioExterno;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LaboratorioExterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaboratorioExternoRepository extends JpaRepository<LaboratorioExterno, Long> {
}
