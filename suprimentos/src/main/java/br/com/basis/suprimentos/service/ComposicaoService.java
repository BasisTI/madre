package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Composicao;
import br.com.basis.suprimentos.repository.ComposicaoRepository;
import br.com.basis.suprimentos.repository.search.ComposicaoSearchRepository;
import br.com.basis.suprimentos.service.dto.ComposicaoDTO;
import br.com.basis.suprimentos.service.mapper.ComposicaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Composicao}.
 */
@Service
@Transactional
public class ComposicaoService {

    private final Logger log = LoggerFactory.getLogger(ComposicaoService.class);

    private final ComposicaoRepository composicaoRepository;

    private final ComposicaoMapper composicaoMapper;

    private final ComposicaoSearchRepository composicaoSearchRepository;

    public ComposicaoService(ComposicaoRepository composicaoRepository, ComposicaoMapper composicaoMapper, ComposicaoSearchRepository composicaoSearchRepository) {
        this.composicaoRepository = composicaoRepository;
        this.composicaoMapper = composicaoMapper;
        this.composicaoSearchRepository = composicaoSearchRepository;
    }

    /**
     * Save a composicao.
     *
     * @param composicaoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComposicaoDTO save(ComposicaoDTO composicaoDTO) {
        log.debug("Request to save Composicao : {}", composicaoDTO);
        Composicao composicao = composicaoMapper.toEntity(composicaoDTO);
        composicao = composicaoRepository.save(composicao);
        ComposicaoDTO result = composicaoMapper.toDto(composicao);
        composicaoSearchRepository.save(composicao);
        return result;
    }

    /**
     * Get all the composicaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComposicaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Composicaos");
        return composicaoRepository.findAll(pageable)
            .map(composicaoMapper::toDto);
    }


    /**
     * Get one composicao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComposicaoDTO> findOne(Long id) {
        log.debug("Request to get Composicao : {}", id);
        return composicaoRepository.findById(id)
            .map(composicaoMapper::toDto);
    }

    /**
     * Delete the composicao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Composicao : {}", id);
        composicaoRepository.deleteById(id);
        composicaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the composicao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComposicaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Composicaos for query {}", query);
        return composicaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(composicaoMapper::toDto);
    }
}
