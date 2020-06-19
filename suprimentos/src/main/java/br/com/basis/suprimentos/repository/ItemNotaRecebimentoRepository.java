package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.ItemNotaRecebimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemNotaRecebimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemNotaRecebimentoRepository extends JpaRepository<ItemNotaRecebimento, Long> {

}
