package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPrescricaoMedicamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPrescricaoMedicamentoRepository extends JpaRepository<ItemPrescricaoMedicamento, Long> {

}
