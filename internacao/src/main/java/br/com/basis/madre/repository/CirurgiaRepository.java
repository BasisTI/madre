package br.com.basis.madre.repository;


import br.com.basis.madre.domain.Cirurgia;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cirurgia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CirurgiaRepository extends JpaRepository<Cirurgia, Long> {

}
