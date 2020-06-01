package br.com.basis.madre.prescricao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.prescricao.domain.TipoProcedimento;

/**
 * Spring Data repository for the TipoProcedimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoProcedimentoRepository extends JpaRepository<TipoProcedimento, Long> {

	@Query("select distinct tp from TipoProcedimento tp join tp.itemPrescricaoProcedimento ip where ip.tipoProcedimentoEspecial = 'DIVERSOS'")
	Page<TipoProcedimento> listarTipoProcedimentoDiversos(Pageable pageable);

	@Query("select distinct tp from TipoProcedimento tp join tp.itemPrescricaoProcedimento ip where ip.tipoProcedimentoEspecial = 'CIRURGIAS_LEITO'")
	Page<TipoProcedimento> listarTipoProcedimentoCirurgias(Pageable pageable);
	
	@Query("select distinct tp from TipoProcedimento tp join tp.itemPrescricaoProcedimento ip where ip.tipoProcedimentoEspecial = 'ORTESES_PROTESES'")
	Page<TipoProcedimento> listarTipoProcedimentoOsteseProtese(Pageable pageable);

}
