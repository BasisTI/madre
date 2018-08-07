package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.FuncionalidadeAcao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FuncionalidadeAcao.
 */
public interface Funcionalidade_acaoService {

    /**
     * Save a funcionalidade_acao.
     *
     * @param funcionalidade_acao the entity to save
     * @return the persisted entity
     */
    FuncionalidadeAcao save(FuncionalidadeAcao funcionalidade_acao);

    /**
     * Get all the funcionalidade_acaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FuncionalidadeAcao> findAll(Pageable pageable);

    /**
     * Get the "id" funcionalidade_acao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FuncionalidadeAcao findOne(Long id);

    /**
     * Delete the "id" funcionalidade_acao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the funcionalidade_acao corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FuncionalidadeAcao> search(String query, Pageable pageable);
}
