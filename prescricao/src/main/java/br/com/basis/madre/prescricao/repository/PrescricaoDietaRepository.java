package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.PrescricaoDieta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrescricaoDieta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescricaoDietaRepository extends JpaRepository<PrescricaoDieta, Long> {

}
