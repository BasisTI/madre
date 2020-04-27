package br.com.basis.madre.repository;
import br.com.basis.madre.domain.ConvenioDeSaude;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConvenioDeSaude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConvenioDeSaudeRepository extends JpaRepository<ConvenioDeSaude, Long> {
}
