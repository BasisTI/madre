package br.com.basis.madre.cadastros.repository;


import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.search.UnidadeHospitalarSearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UnidadeHospitalar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeHospitalarRepository extends JpaRepository<UnidadeHospitalar, Long> {

    /*
    * Busca no elastich para verificação de duplicidade de dados
    *
    *
    * */

    Optional<UnidadeHospitalar> findOneByCnpj(String cnpj);
    Optional<UnidadeHospitalar> findOneByCnpjAndSiglaIgnoreCase(String cnpj, String sigla);
    Optional<UnidadeHospitalar> findOneByNomeIgnoreCase(String nome);
    Optional<UnidadeHospitalar> findOneBySiglaIgnoreCase(String sigla);
    Optional<UnidadeHospitalar> findOneByEnderecoIgnoreCase(String endereco);
}
