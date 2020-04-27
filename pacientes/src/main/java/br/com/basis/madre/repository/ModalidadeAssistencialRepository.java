package br.com.basis.madre.repository;
import br.com.basis.madre.domain.ModalidadeAssistencial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ModalidadeAssistencial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModalidadeAssistencialRepository extends JpaRepository<ModalidadeAssistencial, Long> {
}
