package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.domain.FuncionalidadeAcao;
import br.com.basis.madre.cadastros.service.FuncionalidadeAcaoService;
import br.com.basis.madre.cadastros.repository.FuncionalidadeAcaoRepository;
import br.com.basis.madre.cadastros.repository.search.FuncionalidadeAcaoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FuncionalidadeAcao.
 */
@Service
@Transactional
public class FuncionalidadeAcaoServiceImpl implements FuncionalidadeAcaoService {

    private final Logger log = LoggerFactory.getLogger(FuncionalidadeAcaoServiceImpl.class);

    private final FuncionalidadeAcaoRepository funcionalidadeAcaoRepository;

    private final FuncionalidadeAcaoSearchRepository funcionalidadeAcaoSearchRepository;

    public FuncionalidadeAcaoServiceImpl(FuncionalidadeAcaoRepository funcionalidadeAcaoRepository, FuncionalidadeAcaoSearchRepository funcionalidadeAcaoSearchRepository) {
        this.funcionalidadeAcaoRepository = funcionalidadeAcaoRepository;
        this.funcionalidadeAcaoSearchRepository = funcionalidadeAcaoSearchRepository;
    }

    /**
     * Save a funcionalidade_acao.
     *
     * @param funcionalidadeAcao the entity to save
     * @return the persisted entity
     */
    @Override
    public FuncionalidadeAcao save(FuncionalidadeAcao funcionalidadeAcao) {
        log.debug("Request to save FuncionalidadeAcao : {}", funcionalidadeAcao);
        FuncionalidadeAcao result = funcionalidadeAcaoRepository.save(funcionalidadeAcao);
        funcionalidadeAcaoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the funcionalidade_acaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FuncionalidadeAcao> findAll(Pageable pageable) {
        log.debug("Request to get all Funcionalidade_acaos");
        return funcionalidadeAcaoRepository.findAll(pageable);
    }

    /**
     * Get one funcionalidade_acao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FuncionalidadeAcao findOne(Long id) {
        log.debug("Request to get FuncionalidadeAcao : {}", id);
        return funcionalidadeAcaoRepository.findOne(id);
    }

    /**
     * Delete the funcionalidade_acao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FuncionalidadeAcao : {}", id);
        funcionalidadeAcaoRepository.delete(id);
        funcionalidadeAcaoSearchRepository.delete(id);
    }

    /**
     * Search for the funcionalidade_acao corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FuncionalidadeAcao> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Funcionalidade_acaos for query {}", query);
        return funcionalidadeAcaoSearchRepository.search(queryStringQuery(query), pageable);
    }
}
