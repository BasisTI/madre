package br.com.basis.madre.repository;
import br.com.basis.madre.domain.Internacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Internacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternacaoRepository extends JpaRepository<Internacao, Long> {
}
