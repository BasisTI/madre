package br.com.basis.madre.repository;
import br.com.basis.madre.domain.LocalDeAtendimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LocalDeAtendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalDeAtendimentoRepository extends JpaRepository<LocalDeAtendimento, Long> {
}
