package br.com.basis.madre.cadastros.service;

import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.basis.madre.cadastros.domain.Especialidade;
import br.com.basis.madre.cadastros.service.exception.EspecialidadeException;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;

/**
 * Service Implementation for managing Especialidade.
 */

public interface EspecialidadeService {

    /**
     * Save a Especialidade.
     *
     * @param especialidade the entity to save
     * @return the persisted entity
     */
    Especialidade save(Especialidade especialidade) throws EspecialidadeException;

    /**
     * Get all the Especialidade.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Especialidade> findAll(Optional<String> query, Pageable pageable);

    /**
     * Get the "id" Especialidade.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Especialidade findOne(Long id);

    /**
     * Delete the "id" Especialidade.

     * @param id the id of the entity
     */
    void delete(Long id);


    Page<Especialidade> search(String query, Pageable pageable);


        ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio, String query) throws RelatorioException;
}
