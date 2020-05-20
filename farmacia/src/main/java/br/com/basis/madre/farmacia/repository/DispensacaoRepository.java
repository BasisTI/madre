package br.com.basis.madre.farmacia.repository;
import br.com.basis.madre.farmacia.domain.Dispensacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dispensacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispensacaoRepository extends JpaRepository<Dispensacao, Long> {

}
