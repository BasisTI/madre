package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the UnidadeHospitalar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeHospitalarRepository extends JpaRepository<UnidadeHospitalar, Long> {

    /*
    * Busca para verificação de duplicidade de dados
    *
    *
    * */
    Optional<UnidadeHospitalar> findOneByNomeIgnoreCase(String nome);
    Optional<UnidadeHospitalar> findOneByIdAndNomeIgnoreCase(Long id, String nome);
    Optional<UnidadeHospitalar> findOneByIdAndSiglaIgnoreCase(Long id, String sigla);
    Optional<UnidadeHospitalar> findOneBySiglaIgnoreCase(String sigla);
}
