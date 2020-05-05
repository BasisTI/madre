package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.UnidadeInfusao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadeInfusao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeInfusaoRepository extends JpaRepository<UnidadeInfusao, Long> {

}
