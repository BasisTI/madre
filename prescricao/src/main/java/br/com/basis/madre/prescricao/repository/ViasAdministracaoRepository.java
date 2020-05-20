package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.ViasAdministracao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ViasAdministracao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViasAdministracaoRepository extends JpaRepository<ViasAdministracao, Long> {

}
