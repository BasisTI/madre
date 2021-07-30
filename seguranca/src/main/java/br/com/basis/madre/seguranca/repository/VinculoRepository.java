package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Vinculo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vinculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, Long> {
}
