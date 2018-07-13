package br.com.basis.madre.cadastros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.cadastros.domain.TipoPergunta;


/**
 * Spring Data JPA repository for the TipoPergunta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPerguntaRepository extends JpaRepository<TipoPergunta, Long> {

    Optional<TipoPergunta> findOneByEnunciadoPerguntaIgnoreCase(String enunciadoPergunta);
}
