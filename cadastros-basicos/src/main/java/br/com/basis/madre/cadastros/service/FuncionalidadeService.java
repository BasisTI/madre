package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.Funcionalidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Funcionalidade.
 */
public interface FuncionalidadeService {

    /**
     * Save a funcionalidade.
     *
     * @param funcionalidade the entity to save
     * @return the persisted entity
     */
    Funcionalidade save(Funcionalidade funcionalidade);

    /**
     * Get all the funcionalidades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Funcionalidade> findAll(Pageable pageable);

    /**
     * Get the "id" funcionalidade.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Funcionalidade findOne(Long id);

    /**
     * Delete the "id" funcionalidade.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the funcionalidade corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Funcionalidade> search(String query, Pageable pageable);
}
