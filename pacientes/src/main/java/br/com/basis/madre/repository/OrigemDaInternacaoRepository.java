package br.com.basis.madre.repository;
import br.com.basis.madre.domain.OrigemDaInternacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrigemDaInternacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrigemDaInternacaoRepository extends JpaRepository<OrigemDaInternacao, Long> {
}
