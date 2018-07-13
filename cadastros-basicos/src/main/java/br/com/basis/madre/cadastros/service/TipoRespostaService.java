package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.TipoResposta;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

/**
 * Service Interface for managing TipoResposta.
 */
public interface TipoRespostaService {

    /**
     * Save a tipoResposta.
     *
     * @param tipoResposta the entity to save
     * @return the persisted entity
     */
    TipoResposta save(TipoResposta tipoResposta);

    /**
     * Get all the tipoRespostas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoResposta> findAll(Pageable pageable);

    /**
     * Get the "id" tipoResposta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TipoResposta findOne(Long id);

    /**
     * Delete the "id" tipoResposta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoResposta corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoResposta> search(String query, Pageable pageable);

    ResponseEntity<InputStreamResource> gerarRelatorioExportacao (String tipoRelatorio, String querry) throws RelatorioException;
}
