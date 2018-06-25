package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.service.FuncionalidadeService;
import br.com.basis.madre.cadastros.domain.Funcionalidade;
import br.com.basis.madre.cadastros.repository.FuncionalidadeRepository;
import br.com.basis.madre.cadastros.repository.search.FuncionalidadeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Funcionalidade.
 */
@Service
@Transactional
public class FuncionalidadeServiceImpl implements FuncionalidadeService {

    private final Logger log = LoggerFactory.getLogger(FuncionalidadeServiceImpl.class);

    private final FuncionalidadeRepository funcionalidadeRepository;

    private final FuncionalidadeSearchRepository funcionalidadeSearchRepository;

    public FuncionalidadeServiceImpl(FuncionalidadeRepository funcionalidadeRepository, FuncionalidadeSearchRepository funcionalidadeSearchRepository) {
        this.funcionalidadeRepository = funcionalidadeRepository;
        this.funcionalidadeSearchRepository = funcionalidadeSearchRepository;
    }

    /**
     * Save a funcionalidade.
     *
     * @param funcionalidade the entity to save
     * @return the persisted entity
     */
    @Override
    public Funcionalidade save(Funcionalidade funcionalidade) {
        log.debug("Request to save Funcionalidade : {}", funcionalidade);
        Funcionalidade result = funcionalidadeRepository.save(funcionalidade);
        funcionalidadeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the funcionalidades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Funcionalidade> findAll(Pageable pageable) {
        log.debug("Request to get all Funcionalidades");
        return funcionalidadeRepository.findAll(pageable);
    }

    /**
     * Get one funcionalidade by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Funcionalidade findOne(Long id) {
        log.debug("Request to get Funcionalidade : {}", id);
        return funcionalidadeRepository.findOne(id);
    }

    /**
     * Delete the funcionalidade by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Funcionalidade : {}", id);
        funcionalidadeRepository.delete(id);
        funcionalidadeSearchRepository.delete(id);
    }

    /**
     * Search for the funcionalidade corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Funcionalidade> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Funcionalidades for query {}", query);
        Page<Funcionalidade> result = funcionalidadeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
