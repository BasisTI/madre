package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Justificativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Justificativa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JustificativaRepository extends JpaRepository<Justificativa, Long> {
}
