package br.com.basis.madre.repository;

import br.com.basis.madre.domain.EstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoCivil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {
}
