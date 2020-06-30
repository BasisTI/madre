package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

}
