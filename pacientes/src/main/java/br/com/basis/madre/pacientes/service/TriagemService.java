package br.com.basis.madre.pacientes.service;

import br.com.basis.madre.pacientes.domain.Triagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Triagem.
 */
public interface TriagemService {

    /**
     * Save a triagem.
     *
     * @param triagem the entity to save
     * @return the persisted entity
     */
    Triagem save(Triagem triagem);

    /**
     * Get all the triagems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Triagem> findAll(Pageable pageable);

    /**
     * Get the "id" triagem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Triagem findOne(Long id);

    /**
     * Delete the "id" triagem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the triagem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Triagem> search(String query, Pageable pageable);
}
