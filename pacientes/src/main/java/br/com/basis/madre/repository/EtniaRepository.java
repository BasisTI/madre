package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Etnia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Etnia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtniaRepository extends JpaRepository<Etnia, Long> {
}
