package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Nacionalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Nacionalidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NacionalidadeRepository extends JpaRepository<Nacionalidade, Long> {
}
