package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.MarcaComercial;
import br.com.basis.suprimentos.repository.MarcaComercialRepository;
import br.com.basis.suprimentos.repository.search.MarcaComercialSearchRepository;
import br.com.basis.suprimentos.service.dto.MarcaComercialDTO;
import br.com.basis.suprimentos.service.mapper.MarcaComercialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MarcaComercial}.
 */
@Service
@Transactional
public class MarcaComercialService {

    private final Logger log = LoggerFactory.getLogger(MarcaComercialService.class);

    private final MarcaComercialRepository marcaComercialRepository;

    private final MarcaComercialMapper marcaComercialMapper;

    private final MarcaComercialSearchRepository marcaComercialSearchRepository;

    public MarcaComercialService(MarcaComercialRepository marcaComercialRepository, MarcaComercialMapper marcaComercialMapper, MarcaComercialSearchRepository marcaComercialSearchRepository) {
        this.marcaComercialRepository = marcaComercialRepository;
        this.marcaComercialMapper = marcaComercialMapper;
        this.marcaComercialSearchRepository = marcaComercialSearchRepository;
    }

    /**
     * Save a marcaComercial.
     *
     * @param marcaComercialDTO the entity to save.
     * @return the persisted entity.
     */
    public MarcaComercialDTO save(MarcaComercialDTO marcaComercialDTO) {
        log.debug("Request to save MarcaComercial : {}", marcaComercialDTO);
        MarcaComercial marcaComercial = marcaComercialMapper.toEntity(marcaComercialDTO);
        marcaComercial = marcaComercialRepository.save(marcaComercial);
        MarcaComercialDTO result = marcaComercialMapper.toDto(marcaComercial);
        marcaComercialSearchRepository.save(marcaComercial);
        return result;
    }

    /**
     * Get all the marcaComercials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MarcaComercialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MarcaComercials");
        return marcaComercialRepository.findAll(pageable)
            .map(marcaComercialMapper::toDto);
    }


    /**
     * Get one marcaComercial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MarcaComercialDTO> findOne(Long id) {
        log.debug("Request to get MarcaComercial : {}", id);
        return marcaComercialRepository.findById(id)
            .map(marcaComercialMapper::toDto);
    }

    /**
     * Delete the marcaComercial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MarcaComercial : {}", id);
        marcaComercialRepository.deleteById(id);
        marcaComercialSearchRepository.deleteById(id);
    }

    /**
     * Search for the marcaComercial corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MarcaComercialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MarcaComercials for query {}", query);
        return marcaComercialSearchRepository.search(queryStringQuery(query), pageable)
            .map(marcaComercialMapper::toDto);
    }
}
