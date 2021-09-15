package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Recipiente;
import br.com.basis.madre.madreexames.repository.RecipienteRepository;
import br.com.basis.madre.madreexames.repository.search.RecipienteSearchRepository;
import br.com.basis.madre.madreexames.service.dto.RecipienteDTO;
import br.com.basis.madre.madreexames.service.mapper.RecipienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Recipiente}.
 */
@Service
@Transactional
public class RecipienteService {

    private final Logger log = LoggerFactory.getLogger(RecipienteService.class);

    private final RecipienteRepository recipienteRepository;

    private final RecipienteMapper recipienteMapper;

    private final RecipienteSearchRepository recipienteSearchRepository;

    public RecipienteService(RecipienteRepository recipienteRepository, RecipienteMapper recipienteMapper, RecipienteSearchRepository recipienteSearchRepository) {
        this.recipienteRepository = recipienteRepository;
        this.recipienteMapper = recipienteMapper;
        this.recipienteSearchRepository = recipienteSearchRepository;
    }

    /**
     * Save a recipiente.
     *
     * @param recipienteDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipienteDTO save(RecipienteDTO recipienteDTO) {
        log.debug("Request to save Recipiente : {}", recipienteDTO);
        Recipiente recipiente = recipienteMapper.toEntity(recipienteDTO);
        recipiente = recipienteRepository.save(recipiente);
        RecipienteDTO result = recipienteMapper.toDto(recipiente);
        recipienteSearchRepository.save(recipiente);
        return result;
    }

    /**
     * Get all the recipientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recipientes");
        return recipienteRepository.findAll(pageable)
            .map(recipienteMapper::toDto);
    }


    /**
     * Get one recipiente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipienteDTO> findOne(Long id) {
        log.debug("Request to get Recipiente : {}", id);
        return recipienteRepository.findById(id)
            .map(recipienteMapper::toDto);
    }

    /**
     * Delete the recipiente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recipiente : {}", id);
        recipienteRepository.deleteById(id);
        recipienteSearchRepository.deleteById(id);
    }

    /**
     * Search for the recipiente corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipienteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Recipientes for query {}", query);
        return recipienteSearchRepository.search(queryStringQuery(query), pageable)
            .map(recipienteMapper::toDto);
    }
}
