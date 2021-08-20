package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.TiposDeQualificacao;
import br.com.basis.madre.seguranca.repository.TiposDeQualificacaoRepository;
import br.com.basis.madre.seguranca.repository.search.TiposDeQualificacaoSearchRepository;
import br.com.basis.madre.seguranca.service.dto.TiposDeQualificacaoDTO;
import br.com.basis.madre.seguranca.service.mapper.TiposDeQualificacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TiposDeQualificacao}.
 */
@Service
@Transactional
public class TiposDeQualificacaoService {

    private final Logger log = LoggerFactory.getLogger(TiposDeQualificacaoService.class);

    private final TiposDeQualificacaoRepository tiposDeQualificacaoRepository;

    private final TiposDeQualificacaoMapper tiposDeQualificacaoMapper;

    private final TiposDeQualificacaoSearchRepository tiposDeQualificacaoSearchRepository;

    public TiposDeQualificacaoService(TiposDeQualificacaoRepository tiposDeQualificacaoRepository, TiposDeQualificacaoMapper tiposDeQualificacaoMapper, TiposDeQualificacaoSearchRepository tiposDeQualificacaoSearchRepository) {
        this.tiposDeQualificacaoRepository = tiposDeQualificacaoRepository;
        this.tiposDeQualificacaoMapper = tiposDeQualificacaoMapper;
        this.tiposDeQualificacaoSearchRepository = tiposDeQualificacaoSearchRepository;
    }

    /**
     * Save a tiposDeQualificacao.
     *
     * @param tiposDeQualificacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public TiposDeQualificacaoDTO save(TiposDeQualificacaoDTO tiposDeQualificacaoDTO) {
        log.debug("Request to save TiposDeQualificacao : {}", tiposDeQualificacaoDTO);
        TiposDeQualificacao tiposDeQualificacao = tiposDeQualificacaoMapper.toEntity(tiposDeQualificacaoDTO);
        tiposDeQualificacao = tiposDeQualificacaoRepository.save(tiposDeQualificacao);
        TiposDeQualificacaoDTO result = tiposDeQualificacaoMapper.toDto(tiposDeQualificacao);
        tiposDeQualificacaoSearchRepository.save(tiposDeQualificacao);
        return result;
    }

    /**
     * Get all the tiposDeQualificacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TiposDeQualificacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TiposDeQualificacaos");
        return tiposDeQualificacaoRepository.findAll(pageable)
            .map(tiposDeQualificacaoMapper::toDto);
    }


    /**
     * Get one tiposDeQualificacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TiposDeQualificacaoDTO> findOne(Long id) {
        log.debug("Request to get TiposDeQualificacao : {}", id);
        return tiposDeQualificacaoRepository.findById(id)
            .map(tiposDeQualificacaoMapper::toDto);
    }

    /**
     * Delete the tiposDeQualificacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TiposDeQualificacao : {}", id);
        tiposDeQualificacaoRepository.deleteById(id);
        tiposDeQualificacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tiposDeQualificacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TiposDeQualificacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TiposDeQualificacaos for query {}", query);
        return tiposDeQualificacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tiposDeQualificacaoMapper::toDto);
    }
}
