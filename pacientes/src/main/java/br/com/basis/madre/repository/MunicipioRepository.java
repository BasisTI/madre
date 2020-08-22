package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Municipio;
import br.com.basis.madre.domain.UF;
import br.com.basis.madre.service.projection.MunicipioUF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Municipio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
  Page<MunicipioUF> findAllProjectedMunicipioUFBy(String nome,UF uf,Pageable pageable);

  Page<MunicipioUF> findByNomeContainsIgnoreCaseAndUf(String nome,UF uf,Pageable pageable);
}
