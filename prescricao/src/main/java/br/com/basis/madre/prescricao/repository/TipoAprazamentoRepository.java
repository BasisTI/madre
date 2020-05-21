package br.com.basis.madre.prescricao.repository;
import br.com.basis.madre.prescricao.domain.TipoAprazamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoAprazamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoAprazamentoRepository extends JpaRepository<TipoAprazamento, Long> {

}
