package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Genitores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Genitores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenitoresRepository extends JpaRepository<Genitores, Long> {
}
