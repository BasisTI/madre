package br.com.basis.madre.service;

import br.com.basis.madre.domain.Ala;
import br.com.basis.madre.repository.AlaRepository;
import br.com.basis.madre.repository.search.AlaSearchRepository;
import br.com.basis.madre.service.dto.AlaDTO;
import br.com.basis.madre.service.mapper.AlaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Ala}.
 */
@Service
@Transactional
public class AlaService {

    private final Logger log = LoggerFactory.getLogger(AlaService.class);

    private final AlaRepository alaRepository;

    private final AlaMapper alaMapper;

    private final AlaSearchRepository alaSearchRepository;

    public AlaService(AlaRepository alaRepository, AlaMapper alaMapper, AlaSearchRepository alaSearchRepository) {
        this.alaRepository = alaRepository;
        this.alaMapper = alaMapper;
        this.alaSearchRepository = alaSearchRepository;
    }

    /**
     * Save a ala.
     *
     * @param alaDTO the entity to save.
     * @return the persisted entity.
     */
    public AlaDTO save(AlaDTO alaDTO) {
        log.debug("Request to save Ala : {}", alaDTO);
        Ala ala = alaMapper.toEntity(alaDTO);
        ala = alaRepository.save(ala);
        AlaDTO result = alaMapper.toDto(ala);
        alaSearchRepository.save(ala);
        return result;
    }

    /**
     * Get all the alas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alas");
        return alaRepository.findAll(pageable)
            .map(alaMapper::toDto);
    }


    /**
     * Get one ala by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlaDTO> findOne(Long id) {
        log.debug("Request to get Ala : {}", id);
        return alaRepository.findById(id)
            .map(alaMapper::toDto);
    }

    /**
     * Delete the ala by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ala : {}", id);
        alaRepository.deleteById(id);
        alaSearchRepository.deleteById(id);
    }

    /**
     * Search for the ala corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Alas for query {}", query);
        return alaSearchRepository.search(queryStringQuery(query), pageable)
            .map(alaMapper::toDto);
    }
}
