package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.UF;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UFRepository extends JpaRepository<UF, Long> {
}
