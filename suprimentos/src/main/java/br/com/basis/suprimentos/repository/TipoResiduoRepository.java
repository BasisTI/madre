package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.TipoResiduo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoResiduo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoResiduoRepository extends JpaRepository<TipoResiduo, Long> {

}
