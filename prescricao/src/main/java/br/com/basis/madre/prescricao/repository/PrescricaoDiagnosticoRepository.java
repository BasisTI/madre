package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.PrescricaoDiagnostico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrescricaoDiagnostico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescricaoDiagnosticoRepository extends JpaRepository<PrescricaoDiagnostico, Long> {

}
