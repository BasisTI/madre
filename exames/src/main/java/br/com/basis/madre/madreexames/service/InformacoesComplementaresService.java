package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.InformacoesComplementares;
import br.com.basis.madre.madreexames.repository.InformacoesComplementaresRepository;
import br.com.basis.madre.madreexames.repository.search.InformacoesComplementaresSearchRepository;
import br.com.basis.madre.madreexames.service.dto.InformacoesComplementaresDTO;
import br.com.basis.madre.madreexames.service.mapper.InformacoesComplementaresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link InformacoesComplementares}.
 */
@Service
@Transactional
public class InformacoesComplementaresService {

    private final Logger log = LoggerFactory.getLogger(InformacoesComplementaresService.class);

    private final InformacoesComplementaresRepository informacoesComplementaresRepository;

    private final InformacoesComplementaresMapper informacoesComplementaresMapper;

    private final InformacoesComplementaresSearchRepository informacoesComplementaresSearchRepository;

    public InformacoesComplementaresService(InformacoesComplementaresRepository informacoesComplementaresRepository, InformacoesComplementaresMapper informacoesComplementaresMapper, InformacoesComplementaresSearchRepository informacoesComplementaresSearchRepository) {
        this.informacoesComplementaresRepository = informacoesComplementaresRepository;
        this.informacoesComplementaresMapper = informacoesComplementaresMapper;
        this.informacoesComplementaresSearchRepository = informacoesComplementaresSearchRepository;
    }

    /**
     * Save a informacoesComplementares.
     *
     * @param informacoesComplementaresDTO the entity to save.
     * @return the persisted entity.
     */
    public InformacoesComplementaresDTO save(InformacoesComplementaresDTO informacoesComplementaresDTO) {
        log.debug("Request to save InformacoesComplementares : {}", informacoesComplementaresDTO);
        InformacoesComplementares informacoesComplementares = informacoesComplementaresMapper.toEntity(informacoesComplementaresDTO);
        informacoesComplementares = informacoesComplementaresRepository.save(informacoesComplementares);
        InformacoesComplementaresDTO result = informacoesComplementaresMapper.toDto(informacoesComplementares);
        informacoesComplementaresSearchRepository.save(informacoesComplementares);
        return result;
    }

    /**
     * Get all the informacoesComplementares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InformacoesComplementaresDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InformacoesComplementares");
        return informacoesComplementaresRepository.findAll(pageable)
            .map(informacoesComplementaresMapper::toDto);
    }


    /**
     * Get one informacoesComplementares by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InformacoesComplementaresDTO> findOne(Long id) {
        log.debug("Request to get InformacoesComplementares : {}", id);
        return informacoesComplementaresRepository.findById(id)
            .map(informacoesComplementaresMapper::toDto);
    }

    /**
     * Delete the informacoesComplementares by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InformacoesComplementares : {}", id);
        informacoesComplementaresRepository.deleteById(id);
        informacoesComplementaresSearchRepository.deleteById(id);
    }

    /**
     * Search for the informacoesComplementares corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InformacoesComplementaresDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InformacoesComplementares for query {}", query);
        return informacoesComplementaresSearchRepository.search(queryStringQuery(query), pageable)
            .map(informacoesComplementaresMapper::toDto);
    }
}
