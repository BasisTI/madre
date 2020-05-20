package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrescricaoMedicamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescricaoMedicamentoRepository extends JpaRepository<PrescricaoMedicamento, Long> {

}
