package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.PrescricaoMedica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrescricaoMedica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescricaoMedicaRepository extends JpaRepository<PrescricaoMedica, Long> {

}
