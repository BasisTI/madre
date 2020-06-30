package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Raca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {
}
