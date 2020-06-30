package br.com.basis.madre.repository;

import br.com.basis.madre.domain.GrauDeParentesco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GrauDeParentesco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrauDeParentescoRepository extends JpaRepository<GrauDeParentesco, Long> {
}
