package br.com.basis.madre.service;

import br.com.basis.madre.domain.Equipe;
import br.com.basis.madre.repository.EquipeRepository;
import br.com.basis.madre.repository.search.EquipeSearchRepository;
import br.com.basis.madre.service.dto.EquipeDTO;
import br.com.basis.madre.service.mapper.EquipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Equipe.
 */
@Service
@Transactional
public class EquipeService {

    private final Logger log = LoggerFactory.getLogger(EquipeService.class);

    private final EquipeRepository equipeRepository;

    private final EquipeMapper equipeMapper;

    private final EquipeSearchRepository equipeSearchRepository;

    public EquipeService(EquipeRepository equipeRepository, EquipeMapper equipeMapper, EquipeSearchRepository equipeSearchRepository) {
        this.equipeRepository = equipeRepository;
        this.equipeMapper = equipeMapper;
        this.equipeSearchRepository = equipeSearchRepository;
    }

    /**
     * Save a equipe.
     *
     * @param equipeDTO the entity to save
     * @return the persisted entity
     */
    public EquipeDTO save(EquipeDTO equipeDTO) {
        log.debug("Request to save Equipe : {}", equipeDTO);

        Equipe equipe = equipeMapper.toEntity(equipeDTO);
        equipe = equipeRepository.save(equipe);
        EquipeDTO result = equipeMapper.toDto(equipe);
        equipeSearchRepository.save(equipe);
        return result;
    }

    /**
     * Get all the equipes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EquipeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipes");
        return equipeRepository.findAll(pageable)
            .map(equipeMapper::toDto);
    }


    /**
     * Get one equipe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EquipeDTO> findOne(Long id) {
        log.debug("Request to get Equipe : {}", id);
        return equipeRepository.findById(id)
            .map(equipeMapper::toDto);
    }

    /**
     * Delete the equipe by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Equipe : {}", id);
        equipeRepository.deleteById(id);
        equipeSearchRepository.deleteById(id);
    }

    /**
     * Search for the equipe corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EquipeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Equipes for query {}", query);
        return equipeSearchRepository.search(queryStringQuery(query), pageable)
            .map(equipeMapper::toDto);
    }
}
