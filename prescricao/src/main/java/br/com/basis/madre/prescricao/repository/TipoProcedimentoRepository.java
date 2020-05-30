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

	@Query(value = "select tipo_procedimento.* from tipo_procedimento join item_prescricao_procedimento on item_prescricao_procedimento.tipo_procedimento_id=tipo_procedimento.id where tipo_procedimento_especial = 'DIVERSOS'", nativeQuery = true)
	Page<TipoProcedimento> listarTipoProcedimentoDiversos(Pageable pageable);

	@Query(value = "select tipo_procedimento.* from tipo_procedimento join item_prescricao_procedimento on item_prescricao_procedimento.tipo_procedimento_id=tipo_procedimento.id where tipo_procedimento_especial = 'CIRURGIAS_LEITO'", nativeQuery = true)
	Page<TipoProcedimento> listarTipoProcedimentoCirurgias(Pageable pageable);

//	@Query("select TipoProcedimento from TipoProcedimento join ItemPrescricaoProcedimento where ItemPrescricaoProcedimento.tipoProcedimentoEspecial = 'ORTESES_PROTESES'")
//	Page<TipoProcedimento> listarTipoProcedimentoOsteseProtese(Pageable pageable);

//	select tipo_procedimento.descricao tipo from item_prescricao_procedimento
//	join tipo_procedimento
//	on item_prescricao_procedimento.tipo_procedimento_id=tipo_procedimento.id where tipo_procedimento_especial = 'CIRURGIAS_LEITO';
}
