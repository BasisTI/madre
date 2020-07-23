package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.TipoTransacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoTransacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {
}
