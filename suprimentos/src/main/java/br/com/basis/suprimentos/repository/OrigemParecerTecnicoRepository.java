package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.OrigemParecerTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrigemParecerTecnico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrigemParecerTecnicoRepository extends JpaRepository<OrigemParecerTecnico, Long> {

}
