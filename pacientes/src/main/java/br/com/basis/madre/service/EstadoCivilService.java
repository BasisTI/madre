package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.EstadoCivil;
import br.com.basis.madre.repository.EstadoCivilRepository;
import br.com.basis.madre.repository.search.EstadoCivilSearchRepository;
import br.com.basis.madre.service.dto.EstadoCivilDTO;
import br.com.basis.madre.service.mapper.EstadoCivilMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstadoCivil}.
 */
@Service
@Transactional
public class EstadoCivilService {

    private final Logger log = LoggerFactory.getLogger(EstadoCivilService.class);

    private final EstadoCivilRepository estadoCivilRepository;

    private final EstadoCivilMapper estadoCivilMapper;

    private final EstadoCivilSearchRepository estadoCivilSearchRepository;

    public EstadoCivilService(EstadoCivilRepository estadoCivilRepository,
        EstadoCivilMapper estadoCivilMapper,
        EstadoCivilSearchRepository estadoCivilSearchRepository) {
        this.estadoCivilRepository = estadoCivilRepository;
        this.estadoCivilMapper = estadoCivilMapper;
        this.estadoCivilSearchRepository = estadoCivilSearchRepository;
    }

    /**
     * Save a estadoCivil.
     *
     * @param estadoCivilDTO the entity to save.
     * @return the persisted entity.
     */
    public EstadoCivilDTO save(EstadoCivilDTO estadoCivilDTO) {
        log.debug("Request to save EstadoCivil : {}", estadoCivilDTO);
        EstadoCivil estadoCivil = estadoCivilMapper.toEntity(estadoCivilDTO);
        estadoCivil = estadoCivilRepository.save(estadoCivil);
        EstadoCivilDTO result = estadoCivilMapper.toDto(estadoCivil);
        estadoCivilSearchRepository.save(estadoCivil);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<EstadoCivilDTO> findAll(EstadoCivilDTO estadoCivilDTO, Pageable pageable) {
        log.debug("Request to get all EstadoCivils");
        return estadoCivilRepository.findAll(
            Example.of(estadoCivilMapper.toEntity(estadoCivilDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable
        )
            .map(estadoCivilMapper::toDto);
    }

    /**
     * Get all the estadoCivils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstadoCivilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstadoCivils");
        return estadoCivilRepository.findAll(pageable)
            .map(estadoCivilMapper::toDto);
    }

    /**
     * Get one estadoCivil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstadoCivilDTO> findOne(Long id) {
        log.debug("Request to get EstadoCivil : {}", id);
        return estadoCivilRepository.findById(id)
            .map(estadoCivilMapper::toDto);
    }

    /**
     * Delete the estadoCivil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EstadoCivil : {}", id);
        estadoCivilRepository.deleteById(id);
        estadoCivilSearchRepository.deleteById(id);
    }

    /**
     * Search for the estadoCivil corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstadoCivilDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EstadoCivils for query {}", query);
        return estadoCivilSearchRepository.search(queryStringQuery(query), pageable)
            .map(estadoCivilMapper::toDto);
    }
}
