package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.InformacoesComplementares;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InformacoesComplementares entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformacoesComplementaresRepository extends JpaRepository<InformacoesComplementares, Long> {
}
