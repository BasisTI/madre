package br.com.basis.madre.farmacia.repository;
import br.com.basis.madre.farmacia.domain.Estorno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Estorno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstornoRepository extends JpaRepository<Estorno, Long> {

}
