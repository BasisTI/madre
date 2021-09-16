package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Sinonimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sinonimo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SinonimoRepository extends JpaRepository<Sinonimo, Long> {
}
