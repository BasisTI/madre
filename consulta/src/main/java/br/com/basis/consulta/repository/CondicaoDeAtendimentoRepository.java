package br.com.basis.consulta.repository;

import br.com.basis.consulta.domain.CondicaoDeAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CondicaoDeAtendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CondicaoDeAtendimentoRepository extends JpaRepository<CondicaoDeAtendimento, Long> {

}
