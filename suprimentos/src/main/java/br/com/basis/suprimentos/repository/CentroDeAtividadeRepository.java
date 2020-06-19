package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.CentroDeAtividade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CentroDeAtividade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentroDeAtividadeRepository extends JpaRepository<CentroDeAtividade, Long> {

}
