package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Naturalidade;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Naturalidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaturalidadeRepository extends JpaRepository<Naturalidade, Long> {
}
