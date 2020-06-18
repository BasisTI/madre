package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.Composicao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Composicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComposicaoRepository extends JpaRepository<Composicao, Long> {

}
