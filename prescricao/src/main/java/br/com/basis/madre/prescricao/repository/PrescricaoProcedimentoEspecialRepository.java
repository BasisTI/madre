package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.PrescricaoProcedimentoEspecial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrescricaoProcedimentoEspecial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescricaoProcedimentoEspecialRepository extends JpaRepository<PrescricaoProcedimentoEspecial, Long> {

}
