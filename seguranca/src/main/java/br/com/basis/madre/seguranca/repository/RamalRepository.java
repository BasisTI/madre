package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Ramal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Ramal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RamalRepository extends JpaRepository<Ramal, Long> {
}
