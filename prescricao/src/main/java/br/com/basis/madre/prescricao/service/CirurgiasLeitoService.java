package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.CirurgiasLeito;
import br.com.basis.madre.prescricao.repository.CirurgiasLeitoRepository;
import br.com.basis.madre.prescricao.repository.search.CirurgiasLeitoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.CirurgiasLeitoDTO;
import br.com.basis.madre.prescricao.service.mapper.CirurgiasLeitoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CirurgiasLeito}.
 */
@Service
@Transactional
public class CirurgiasLeitoService {

    private final Logger log = LoggerFactory.getLogger(CirurgiasLeitoService.class);

    private final CirurgiasLeitoRepository cirurgiasLeitoRepository;

    private final CirurgiasLeitoMapper cirurgiasLeitoMapper;

    private final CirurgiasLeitoSearchRepository cirurgiasLeitoSearchRepository;

    public CirurgiasLeitoService(CirurgiasLeitoRepository cirurgiasLeitoRepository, CirurgiasLeitoMapper cirurgiasLeitoMapper, CirurgiasLeitoSearchRepository cirurgiasLeitoSearchRepository) {
        this.cirurgiasLeitoRepository = cirurgiasLeitoRepository;
        this.cirurgiasLeitoMapper = cirurgiasLeitoMapper;
        this.cirurgiasLeitoSearchRepository = cirurgiasLeitoSearchRepository;
    }

    /**
     * Save a cirurgiasLeito.
     *
     * @param cirurgiasLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public CirurgiasLeitoDTO save(CirurgiasLeitoDTO cirurgiasLeitoDTO) {
        log.debug("Request to save CirurgiasLeito : {}", cirurgiasLeitoDTO);
        CirurgiasLeito cirurgiasLeito = cirurgiasLeitoMapper.toEntity(cirurgiasLeitoDTO);
        cirurgiasLeito = cirurgiasLeitoRepository.save(cirurgiasLeito);
        CirurgiasLeitoDTO result = cirurgiasLeitoMapper.toDto(cirurgiasLeito);
        cirurgiasLeitoSearchRepository.save(cirurgiasLeito);
        return result;
    }

    /**
     * Get all the cirurgiasLeitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CirurgiasLeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CirurgiasLeitos");
        return cirurgiasLeitoRepository.findAll(pageable)
            .map(cirurgiasLeitoMapper::toDto);
    }


    /**
     * Get one cirurgiasLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CirurgiasLeitoDTO> findOne(Long id) {
        log.debug("Request to get CirurgiasLeito : {}", id);
        return cirurgiasLeitoRepository.findById(id)
            .map(cirurgiasLeitoMapper::toDto);
    }

    /**
     * Delete the cirurgiasLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CirurgiasLeito : {}", id);
        cirurgiasLeitoRepository.deleteById(id);
        cirurgiasLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the cirurgiasLeito corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CirurgiasLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CirurgiasLeitos for query {}", query);
        return cirurgiasLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(cirurgiasLeitoMapper::toDto);
    }
}
