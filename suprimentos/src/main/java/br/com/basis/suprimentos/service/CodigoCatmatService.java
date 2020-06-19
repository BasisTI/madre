package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.CodigoCatmat;
import br.com.basis.suprimentos.repository.CodigoCatmatRepository;
import br.com.basis.suprimentos.repository.search.CodigoCatmatSearchRepository;
import br.com.basis.suprimentos.service.dto.CodigoCatmatDTO;
import br.com.basis.suprimentos.service.mapper.CodigoCatmatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CodigoCatmat}.
 */
@Service
@Transactional
public class CodigoCatmatService {

    private final Logger log = LoggerFactory.getLogger(CodigoCatmatService.class);

    private final CodigoCatmatRepository codigoCatmatRepository;

    private final CodigoCatmatMapper codigoCatmatMapper;

    private final CodigoCatmatSearchRepository codigoCatmatSearchRepository;

    public CodigoCatmatService(CodigoCatmatRepository codigoCatmatRepository, CodigoCatmatMapper codigoCatmatMapper, CodigoCatmatSearchRepository codigoCatmatSearchRepository) {
        this.codigoCatmatRepository = codigoCatmatRepository;
        this.codigoCatmatMapper = codigoCatmatMapper;
        this.codigoCatmatSearchRepository = codigoCatmatSearchRepository;
    }

    /**
     * Save a codigoCatmat.
     *
     * @param codigoCatmatDTO the entity to save.
     * @return the persisted entity.
     */
    public CodigoCatmatDTO save(CodigoCatmatDTO codigoCatmatDTO) {
        log.debug("Request to save CodigoCatmat : {}", codigoCatmatDTO);
        CodigoCatmat codigoCatmat = codigoCatmatMapper.toEntity(codigoCatmatDTO);
        codigoCatmat = codigoCatmatRepository.save(codigoCatmat);
        CodigoCatmatDTO result = codigoCatmatMapper.toDto(codigoCatmat);
        codigoCatmatSearchRepository.save(codigoCatmat);
        return result;
    }

    /**
     * Get all the codigoCatmats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CodigoCatmatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CodigoCatmats");
        return codigoCatmatRepository.findAll(pageable)
            .map(codigoCatmatMapper::toDto);
    }


    /**
     * Get one codigoCatmat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CodigoCatmatDTO> findOne(Long id) {
        log.debug("Request to get CodigoCatmat : {}", id);
        return codigoCatmatRepository.findById(id)
            .map(codigoCatmatMapper::toDto);
    }

    /**
     * Delete the codigoCatmat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CodigoCatmat : {}", id);
        codigoCatmatRepository.deleteById(id);
        codigoCatmatSearchRepository.deleteById(id);
    }

    /**
     * Search for the codigoCatmat corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CodigoCatmatDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CodigoCatmats for query {}", query);
        return codigoCatmatSearchRepository.search(queryStringQuery(query), pageable)
            .map(codigoCatmatMapper::toDto);
    }
}
