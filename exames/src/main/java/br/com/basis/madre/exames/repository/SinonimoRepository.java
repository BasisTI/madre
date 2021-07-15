package br.com.basis.madre.exames.repository;

import br.com.basis.madre.exames.domain.Sinonimo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sinonimo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SinonimoRepository extends JpaRepository<Sinonimo, Long> {
}
