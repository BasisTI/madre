package br.com.basis.madre.exames.repository;

import br.com.basis.madre.exames.domain.ItemSolicitacaoExame;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ItemSolicitacaoExame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemSolicitacaoExameRepository extends JpaRepository<ItemSolicitacaoExame, Long> {
}
