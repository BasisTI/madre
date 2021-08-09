package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.OcupacaoDeCargo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OcupacaoDeCargo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcupacaoDeCargoRepository extends JpaRepository<OcupacaoDeCargo, Long> {
}
