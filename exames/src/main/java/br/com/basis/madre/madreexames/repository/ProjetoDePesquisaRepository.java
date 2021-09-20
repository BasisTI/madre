package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.ProjetoDePesquisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjetoDePesquisa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetoDePesquisaRepository extends JpaRepository<ProjetoDePesquisa, Long> {
}
