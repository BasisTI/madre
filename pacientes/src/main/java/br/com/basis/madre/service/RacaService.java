package br.com.basis.madre.service;

import br.com.basis.madre.domain.Raca;
import br.com.basis.madre.repository.RacaRepository;
import br.com.basis.madre.repository.search.RacaSearchRepository;
import br.com.basis.madre.service.dto.RacaDTO;
import br.com.basis.madre.service.mapper.RacaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Raca}.
 */
@Service
@Transactional
public class RacaService {

    private final Logger log = LoggerFactory.getLogger(RacaService.class);

    private final RacaRepository racaRepository;

    private final RacaMapper racaMapper;

    private final RacaSearchRepository racaSearchRepository;

    public RacaService(RacaRepository racaRepository, RacaMapper racaMapper, RacaSearchRepository racaSearchRepository) {
        this.racaRepository = racaRepository;
        this.racaMapper = racaMapper;
        this.racaSearchRepository = racaSearchRepository;
    }

    /**
     * Save a raca.
     *
     * @param racaDTO the entity to save.
     * @return the persisted entity.
     */
    public RacaDTO save(RacaDTO racaDTO) {
        log.debug("Request to save Raca : {}", racaDTO);
        Raca raca = racaMapper.toEntity(racaDTO);
        raca = racaRepository.save(raca);
        RacaDTO result = racaMapper.toDto(raca);
        racaSearchRepository.save(raca);
        return result;
    }

    /**
     * Get all the racas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RacaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Racas");
        return racaRepository.findAll(pageable)
            .map(racaMapper::toDto);
    }

    /**
     * Get one raca by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RacaDTO> findOne(Long id) {
        log.debug("Request to get Raca : {}", id);
        return racaRepository.findById(id)
            .map(racaMapper::toDto);
    }

    /**
     * Delete the raca by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Raca : {}", id);
        racaRepository.deleteById(id);
        racaSearchRepository.deleteById(id);
    }

    /**
     * Search for the raca corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RacaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Racas for query {}", query);
        return racaSearchRepository.search(queryStringQuery(query), pageable)
            .map(racaMapper::toDto);
    }
}
