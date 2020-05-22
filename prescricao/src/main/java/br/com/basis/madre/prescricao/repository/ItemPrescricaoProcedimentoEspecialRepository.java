package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimentoEspecial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPrescricaoProcedimentoEspecial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPrescricaoProcedimentoEspecialRepository extends JpaRepository<ItemPrescricaoProcedimentoEspecial, Long> {

}
