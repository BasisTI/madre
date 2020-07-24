package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.PrescricaoProcedimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrescricaoProcedimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescricaoProcedimentoRepository extends JpaRepository<PrescricaoProcedimento, Long> {

}
