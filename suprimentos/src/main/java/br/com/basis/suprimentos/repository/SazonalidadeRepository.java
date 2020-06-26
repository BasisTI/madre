package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.Sazonalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sazonalidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SazonalidadeRepository extends JpaRepository<Sazonalidade, Long> {

}
