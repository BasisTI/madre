package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Anticoagulante;
import br.com.basis.madre.madreexames.repository.AnticoagulanteRepository;
import br.com.basis.madre.madreexames.repository.search.AnticoagulanteSearchRepository;
import br.com.basis.madre.madreexames.service.dto.AnticoagulanteDTO;
import br.com.basis.madre.madreexames.service.mapper.AnticoagulanteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Anticoagulante}.
 */
@Service
@Transactional
public class AnticoagulanteService {

    private final Logger log = LoggerFactory.getLogger(AnticoagulanteService.class);

    private final AnticoagulanteRepository anticoagulanteRepository;

    private final AnticoagulanteMapper anticoagulanteMapper;

    private final AnticoagulanteSearchRepository anticoagulanteSearchRepository;

    public AnticoagulanteService(AnticoagulanteRepository anticoagulanteRepository, AnticoagulanteMapper anticoagulanteMapper, AnticoagulanteSearchRepository anticoagulanteSearchRepository) {
        this.anticoagulanteRepository = anticoagulanteRepository;
        this.anticoagulanteMapper = anticoagulanteMapper;
        this.anticoagulanteSearchRepository = anticoagulanteSearchRepository;
    }

    /**
     * Save a anticoagulante.
     *
     * @param anticoagulanteDTO the entity to save.
     * @return the persisted entity.
     */
    public AnticoagulanteDTO save(AnticoagulanteDTO anticoagulanteDTO) {
        log.debug("Request to save Anticoagulante : {}", anticoagulanteDTO);
        Anticoagulante anticoagulante = anticoagulanteMapper.toEntity(anticoagulanteDTO);
        anticoagulante = anticoagulanteRepository.save(anticoagulante);
        AnticoagulanteDTO result = anticoagulanteMapper.toDto(anticoagulante);
        anticoagulanteSearchRepository.save(anticoagulante);
        return result;
    }

    /**
     * Get all the anticoagulantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnticoagulanteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Anticoagulantes");
        return anticoagulanteRepository.findAll(pageable)
            .map(anticoagulanteMapper::toDto);
    }


    /**
     * Get one anticoagulante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnticoagulanteDTO> findOne(Long id) {
        log.debug("Request to get Anticoagulante : {}", id);
        return anticoagulanteRepository.findById(id)
            .map(anticoagulanteMapper::toDto);
    }

    /**
     * Delete the anticoagulante by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Anticoagulante : {}", id);
        anticoagulanteRepository.deleteById(id);
        anticoagulanteSearchRepository.deleteById(id);
    }

    /**
     * Search for the anticoagulante corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnticoagulanteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Anticoagulantes for query {}", query);
        return anticoagulanteSearchRepository.search(queryStringQuery(query), pageable)
            .map(anticoagulanteMapper::toDto);
    }
}
