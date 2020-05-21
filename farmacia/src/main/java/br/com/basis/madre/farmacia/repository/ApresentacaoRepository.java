package br.com.basis.madre.farmacia.repository;
import br.com.basis.madre.farmacia.domain.Apresentacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Apresentacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {

}
