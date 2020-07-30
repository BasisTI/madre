package br.com.basis.madre.prescricao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.prescricao.domain.TipoProcedimento;
import br.com.basis.madre.prescricao.service.dto.TipoProcedimentoDTO;

/**
 * Spring Data repository for the TipoProcedimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoProcedimentoRepository extends JpaRepository<TipoProcedimento, Long> {

	@Query("select tp from TipoProcedimento tp  where tp.tipoProcedimentoEspecial = 'DIVERSOS'")
	Page<TipoProcedimento> listarTipoProcedimentoDiversos(Pageable pageable);

	@Query("select tp from TipoProcedimento tp where tp.tipoProcedimentoEspecial = 'CIRURGIAS_LEITO'")
	Page<TipoProcedimento> listarTipoProcedimentoCirurgias(Pageable pageable);
	
	@Query("select tp from TipoProcedimento tp where tp.tipoProcedimentoEspecial = 'ORTESES_PROTESES'")
	Page<TipoProcedimento> listarTipoProcedimentoOsteseProtese(Pageable pageable);
	

}
