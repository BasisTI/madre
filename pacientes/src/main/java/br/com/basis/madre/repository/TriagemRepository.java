package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Triagem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Triagem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TriagemRepository extends JpaRepository<Triagem, Long> {

}
