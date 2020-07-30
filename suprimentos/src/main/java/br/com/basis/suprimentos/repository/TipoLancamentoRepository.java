package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.TipoLancamento;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoLancamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoLancamentoRepository extends JpaRepository<TipoLancamento, Long> {
}
