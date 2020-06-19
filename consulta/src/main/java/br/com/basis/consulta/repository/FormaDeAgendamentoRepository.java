package br.com.basis.consulta.repository;
import br.com.basis.consulta.domain.FormaDeAgendamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FormaDeAgendamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaDeAgendamentoRepository extends JpaRepository<FormaDeAgendamento, Long> {

}
