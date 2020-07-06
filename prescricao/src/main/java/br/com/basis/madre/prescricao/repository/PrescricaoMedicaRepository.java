package br.com.basis.madre.prescricao.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.prescricao.domain.PrescricaoMedica;


/**
 * Spring Data  repository for the PrescricaoMedica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescricaoMedicaRepository extends JpaRepository<PrescricaoMedica, Long> {

}
