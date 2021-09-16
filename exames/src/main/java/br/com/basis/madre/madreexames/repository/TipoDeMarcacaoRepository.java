package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.TipoDeMarcacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoDeMarcacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDeMarcacaoRepository extends JpaRepository<TipoDeMarcacao, Long> {
}
