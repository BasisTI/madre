package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.TipoPergunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TipoPergunta.
 */
public interface TipoPerguntaService {

    /**
     * Save a tipoPergunta.
     *
     * @param tipoPergunta the entity to save
     * @return the persisted entity
     */
    TipoPergunta save(TipoPergunta tipoPergunta);

    /**
     * Get all the tipoPerguntas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoPergunta> findAll(Pageable pageable);

    /**
     * Get the "id" tipoPergunta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TipoPergunta findOne(Long id);

    /**
     * Delete the "id" tipoPergunta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoPergunta corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoPergunta> search(String query, Pageable pageable);
}
