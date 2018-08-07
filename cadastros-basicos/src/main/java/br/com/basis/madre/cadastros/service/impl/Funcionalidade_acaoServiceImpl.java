package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.domain.FuncionalidadeAcao;
import br.com.basis.madre.cadastros.service.Funcionalidade_acaoService;
import br.com.basis.madre.cadastros.repository.Funcionalidade_acaoRepository;
import br.com.basis.madre.cadastros.repository.search.Funcionalidade_acaoSearchRepository;
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
public class Funcionalidade_acaoServiceImpl implements Funcionalidade_acaoService {

    private final Logger log = LoggerFactory.getLogger(Funcionalidade_acaoServiceImpl.class);

    private final Funcionalidade_acaoRepository funcionalidade_acaoRepository;

    private final Funcionalidade_acaoSearchRepository funcionalidade_acaoSearchRepository;

    public Funcionalidade_acaoServiceImpl(Funcionalidade_acaoRepository funcionalidade_acaoRepository, Funcionalidade_acaoSearchRepository funcionalidade_acaoSearchRepository) {
        this.funcionalidade_acaoRepository = funcionalidade_acaoRepository;
        this.funcionalidade_acaoSearchRepository = funcionalidade_acaoSearchRepository;
    }

    /**
     * Save a funcionalidade_acao.
     *
     * @param funcionalidade_acao the entity to save
     * @return the persisted entity
     */
    @Override
    public FuncionalidadeAcao save(FuncionalidadeAcao funcionalidade_acao) {
        log.debug("Request to save FuncionalidadeAcao : {}", funcionalidade_acao);
        FuncionalidadeAcao result = funcionalidade_acaoRepository.save(funcionalidade_acao);
        funcionalidade_acaoSearchRepository.save(result);
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
        return funcionalidade_acaoRepository.findAll(pageable);
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
        return funcionalidade_acaoRepository.findOne(id);
    }

    /**
     * Delete the funcionalidade_acao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FuncionalidadeAcao : {}", id);
        funcionalidade_acaoRepository.delete(id);
        funcionalidade_acaoSearchRepository.delete(id);
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
        Page<FuncionalidadeAcao> result = funcionalidade_acaoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
