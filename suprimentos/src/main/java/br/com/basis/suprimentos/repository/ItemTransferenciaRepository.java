package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.ItemTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ItemTransferencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemTransferenciaRepository extends JpaRepository<ItemTransferencia, Long> {
}
