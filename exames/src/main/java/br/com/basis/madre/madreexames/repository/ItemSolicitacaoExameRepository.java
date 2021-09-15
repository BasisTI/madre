package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.ItemSolicitacaoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ItemSolicitacaoExame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemSolicitacaoExameRepository extends JpaRepository<ItemSolicitacaoExame, Long> {
}
