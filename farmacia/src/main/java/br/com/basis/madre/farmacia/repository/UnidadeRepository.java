package br.com.basis.madre.farmacia.repository;
import br.com.basis.madre.farmacia.domain.Unidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Unidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

}
