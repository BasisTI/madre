package br.com.basis.madre.service;

import br.com.basis.madre.domain.Ocupacao;
import br.com.basis.madre.repository.OcupacaoRepository;
import br.com.basis.madre.repository.search.OcupacaoSearchRepository;
import br.com.basis.madre.service.dto.OcupacaoDTO;
import br.com.basis.madre.service.mapper.OcupacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Ocupacao}.
 */
@Service
@Transactional
public class OcupacaoService {

    private final Logger log = LoggerFactory.getLogger(OcupacaoService.class);

    private final OcupacaoRepository ocupacaoRepository;

    private final OcupacaoMapper ocupacaoMapper;

    private final OcupacaoSearchRepository ocupacaoSearchRepository;

    public OcupacaoService(OcupacaoRepository ocupacaoRepository, OcupacaoMapper ocupacaoMapper, OcupacaoSearchRepository ocupacaoSearchRepository) {
        this.ocupacaoRepository = ocupacaoRepository;
        this.ocupacaoMapper = ocupacaoMapper;
        this.ocupacaoSearchRepository = ocupacaoSearchRepository;
    }

    /**
     * Save a ocupacao.
     *
     * @param ocupacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public OcupacaoDTO save(OcupacaoDTO ocupacaoDTO) {
        log.debug("Request to save Ocupacao : {}", ocupacaoDTO);
        Ocupacao ocupacao = ocupacaoMapper.toEntity(ocupacaoDTO);
        ocupacao = ocupacaoRepository.save(ocupacao);
        OcupacaoDTO result = ocupacaoMapper.toDto(ocupacao);
        ocupacaoSearchRepository.save(ocupacao);
        return result;
    }

    /**
     * Get all the ocupacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OcupacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ocupacaos");
        return ocupacaoRepository.findAll(pageable)
            .map(ocupacaoMapper::toDto);
    }

    /**
     * Get one ocupacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OcupacaoDTO> findOne(Long id) {
        log.debug("Request to get Ocupacao : {}", id);
        return ocupacaoRepository.findById(id)
            .map(ocupacaoMapper::toDto);
    }

    /**
     * Delete the ocupacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ocupacao : {}", id);
        ocupacaoRepository.deleteById(id);
        ocupacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the ocupacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OcupacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ocupacaos for query {}", query);
        return ocupacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(ocupacaoMapper::toDto);
    }
}
