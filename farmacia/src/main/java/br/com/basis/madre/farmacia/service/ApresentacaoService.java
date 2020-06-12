package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.Apresentacao;
import br.com.basis.madre.farmacia.repository.ApresentacaoRepository;
import br.com.basis.madre.farmacia.repository.search.ApresentacaoSearchRepository;
import br.com.basis.madre.farmacia.service.dto.ApresentacaoDTO;
import br.com.basis.madre.farmacia.service.mapper.ApresentacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Apresentacao}.
 */
@Service
@Transactional
public class ApresentacaoService {

    private final Logger log = LoggerFactory.getLogger(ApresentacaoService.class);

    private final ApresentacaoRepository apresentacaoRepository;

    private final ApresentacaoMapper apresentacaoMapper;

    private final ApresentacaoSearchRepository apresentacaoSearchRepository;

    public ApresentacaoService(ApresentacaoRepository apresentacaoRepository, ApresentacaoMapper apresentacaoMapper, ApresentacaoSearchRepository apresentacaoSearchRepository) {
        this.apresentacaoRepository = apresentacaoRepository;
        this.apresentacaoMapper = apresentacaoMapper;
        this.apresentacaoSearchRepository = apresentacaoSearchRepository;
    }

    /**
     * Save a apresentacao.
     *
     * @param apresentacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public ApresentacaoDTO save(ApresentacaoDTO apresentacaoDTO) {
        log.debug("Request to save Apresentacao : {}", apresentacaoDTO);
        Apresentacao apresentacao = apresentacaoMapper.toEntity(apresentacaoDTO);
        apresentacao = apresentacaoRepository.save(apresentacao);
        ApresentacaoDTO result = apresentacaoMapper.toDto(apresentacao);
        apresentacaoSearchRepository.save(apresentacao);
        return result;
    }

    /**
     * Get all the apresentacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApresentacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Apresentacaos");
        return apresentacaoRepository.findAll(pageable)
            .map(apresentacaoMapper::toDto);
    }


    /**
     * Get one apresentacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApresentacaoDTO> findOne(Long id) {
        log.debug("Request to get Apresentacao : {}", id);
        return apresentacaoRepository.findById(id)
            .map(apresentacaoMapper::toDto);
    }

    /**
     * Delete the apresentacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Apresentacao : {}", id);
        apresentacaoRepository.deleteById(id);
        apresentacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the apresentacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApresentacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Apresentacaos for query {}", query);
        return apresentacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(apresentacaoMapper::toDto);
    }
    public Page<ApresentacaoDTO> findAll(
        ApresentacaoDTO apresentacaoDTO, Pageable pageable) {
        log.debug("Request to get all Especialidades");
        return apresentacaoRepository.findAll(
            Example.of(apresentacaoMapper.toEntity(apresentacaoDTO),
                ExampleMatcher.matching().withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING))
            , pageable
        )
            .map(apresentacaoMapper::toDto);
    }
}
