package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.Ramal;
import br.com.basis.madre.seguranca.repository.RamalRepository;
import br.com.basis.madre.seguranca.repository.search.RamalSearchRepository;
import br.com.basis.madre.seguranca.service.dto.RamalDTO;
import br.com.basis.madre.seguranca.service.mapper.RamalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Ramal}.
 */
@Service
@Transactional
public class RamalService {

    private final Logger log = LoggerFactory.getLogger(RamalService.class);

    private final RamalRepository ramalRepository;

    private final RamalMapper ramalMapper;

    private final RamalSearchRepository ramalSearchRepository;

    public RamalService(RamalRepository ramalRepository, RamalMapper ramalMapper, RamalSearchRepository ramalSearchRepository) {
        this.ramalRepository = ramalRepository;
        this.ramalMapper = ramalMapper;
        this.ramalSearchRepository = ramalSearchRepository;
    }

    /**
     * Save a ramal.
     *
     * @param ramalDTO the entity to save.
     * @return the persisted entity.
     */
    public RamalDTO save(RamalDTO ramalDTO) {
        log.debug("Request to save Ramal : {}", ramalDTO);
        Ramal ramal = ramalMapper.toEntity(ramalDTO);
        ramal = ramalRepository.save(ramal);
        RamalDTO result = ramalMapper.toDto(ramal);
        ramalSearchRepository.save(ramal);
        return result;
    }

    /**
     * Get all the ramals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RamalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ramals");
        return ramalRepository.findAll(pageable)
            .map(ramalMapper::toDto);
    }


    /**
     * Get one ramal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RamalDTO> findOne(Long id) {
        log.debug("Request to get Ramal : {}", id);
        return ramalRepository.findById(id)
            .map(ramalMapper::toDto);
    }

    /**
     * Delete the ramal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ramal : {}", id);
        ramalRepository.deleteById(id);
        ramalSearchRepository.deleteById(id);
    }

    /**
     * Search for the ramal corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RamalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ramals for query {}", query);
        return ramalSearchRepository.search(queryStringQuery(query), pageable)
            .map(ramalMapper::toDto);
    }
}
