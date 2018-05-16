package br.com.basis.madre.cadastros.service;

import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.service.exception.PreCadastroException;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;

/**
 * Service Implementation for managing PreCadastro.
 */

public interface PreCadastroService {

    /**
     * Save a PreCadastro.
     *
     * @param preCadastro the entity to save
     * @return the persisted entity
     */
    PreCadastro save(PreCadastro preCadastro) throws PreCadastroException;

    /**
     * Get all the PreCadastro.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PreCadastro> findAll(Optional<String> query, Pageable pageable);

    /**
     * Get the "id" PreCadastro.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PreCadastro findOne(Long id);

    /**
     * Delete the "id" PreCadastro.

     * @param id the id of the entity
     */
    void delete(Long id);


    Page<PreCadastro> search(String query, Pageable pageable);


        ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio, String query) throws RelatorioException;
}