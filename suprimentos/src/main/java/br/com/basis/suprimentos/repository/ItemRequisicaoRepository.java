package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.ItemRequisicao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ItemRequisicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemRequisicaoRepository extends JpaRepository<ItemRequisicao, Long> {
}
