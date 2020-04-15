package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Municipio;
import br.com.basis.madre.service.projection.MunicipioUF;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Municipio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
  List<MunicipioUF> findAllProjectedMunicipioUFBy();
}
