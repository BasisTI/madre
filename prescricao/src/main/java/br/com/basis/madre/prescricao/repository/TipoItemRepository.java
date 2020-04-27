package br.com.basis.madre.prescricao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.prescricao.domain.TipoItem;

@Repository
public interface TipoItemRepository extends JpaRepository<TipoItem, Long> {

}
