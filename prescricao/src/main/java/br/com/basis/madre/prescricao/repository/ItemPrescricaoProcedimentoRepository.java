package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPrescricaoProcedimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPrescricaoProcedimentoRepository extends JpaRepository<ItemPrescricaoProcedimento, Long> {

}
