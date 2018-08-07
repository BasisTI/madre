package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.Acao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Acao.
 */
public interface AcaoService {

    /**
     * Save a acao.
     *
     * @param acao the entity to save
     * @return the persisted entity
     */
    Acao save(Acao acao);

    /**
     * Get all the acaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Acao> findAll(Pageable pageable);

    /**
     * Get the "id" acao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Acao findOne(Long id);

    /**
     * Delete the "id" acao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the acao corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Acao> search(String query, Pageable pageable);
}
