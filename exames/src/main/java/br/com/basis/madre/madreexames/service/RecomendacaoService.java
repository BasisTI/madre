package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Recomendacao;
import br.com.basis.madre.madreexames.repository.RecomendacaoRepository;
import br.com.basis.madre.madreexames.repository.search.RecomendacaoSearchRepository;
import br.com.basis.madre.madreexames.service.dto.RecomendacaoDTO;
import br.com.basis.madre.madreexames.service.mapper.RecomendacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Recomendacao}.
 */
@Service
@Transactional
public class RecomendacaoService {

    private final Logger log = LoggerFactory.getLogger(RecomendacaoService.class);

    private final RecomendacaoRepository recomendacaoRepository;

    private final RecomendacaoMapper recomendacaoMapper;

    private final RecomendacaoSearchRepository recomendacaoSearchRepository;

    public RecomendacaoService(RecomendacaoRepository recomendacaoRepository, RecomendacaoMapper recomendacaoMapper, RecomendacaoSearchRepository recomendacaoSearchRepository) {
        this.recomendacaoRepository = recomendacaoRepository;
        this.recomendacaoMapper = recomendacaoMapper;
        this.recomendacaoSearchRepository = recomendacaoSearchRepository;
    }

    /**
     * Save a recomendacao.
     *
     * @param recomendacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public RecomendacaoDTO save(RecomendacaoDTO recomendacaoDTO) {
        log.debug("Request to save Recomendacao : {}", recomendacaoDTO);
        Recomendacao recomendacao = recomendacaoMapper.toEntity(recomendacaoDTO);
        recomendacao = recomendacaoRepository.save(recomendacao);
        RecomendacaoDTO result = recomendacaoMapper.toDto(recomendacao);
        recomendacaoSearchRepository.save(recomendacao);
        return result;
    }

    /**
     * Get all the recomendacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecomendacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recomendacaos");
        return recomendacaoRepository.findAll(pageable)
            .map(recomendacaoMapper::toDto);
    }


    /**
     * Get one recomendacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecomendacaoDTO> findOne(Long id) {
        log.debug("Request to get Recomendacao : {}", id);
        return recomendacaoRepository.findById(id)
            .map(recomendacaoMapper::toDto);
    }

    /**
     * Delete the recomendacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recomendacao : {}", id);
        recomendacaoRepository.deleteById(id);
        recomendacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the recomendacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecomendacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Recomendacaos for query {}", query);
        return recomendacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(recomendacaoMapper::toDto);
    }
}
