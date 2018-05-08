package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.search.UnidadeHospitalarSearchRepository;
import br.com.basis.madre.cadastros.service.dto.PreCadastroDTO;
import br.com.basis.madre.cadastros.service.dto.UnidadeHospitalarDTO;
import br.com.basis.madre.cadastros.service.exception.PreCadastroException;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.exception.UnidadeHospitalarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UnidadeHospitalar.
 */
public interface UnidadeHospitalarService {

    /**
     * Save a UnidadeHospitalar.
     *
     * @param unidadeHospitalarDTO the entity to save
     * @return the persisted entity
     */
    UnidadeHospitalarDTO save(UnidadeHospitalarDTO unidadeHospitalarDTO) throws UnidadeHospitalarException;

    /**
     * Get all the UnidadeHospitalar.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UnidadeHospitalarDTO> findAll(Optional<String> query, Pageable pageable);

    /**
     * Get the "id" UnidadeHospitalar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UnidadeHospitalarDTO findOne(Long id);

    /**
     * Delete the "id" UnidadeHospitalar.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    Page<UnidadeHospitalar> search(String query, Pageable pageable);



    ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio) throws RelatorioException;

}


