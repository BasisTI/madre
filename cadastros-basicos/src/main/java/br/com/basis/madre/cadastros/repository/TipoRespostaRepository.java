package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.domain.PreCadastro_;
import br.com.basis.madre.cadastros.domain.TipoResposta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the TipoResposta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRespostaRepository extends JpaRepository<TipoResposta, Long> {

    Optional<TipoResposta> findOneByRespostaIgnoreCase (String enunciadoPergunta);
}
