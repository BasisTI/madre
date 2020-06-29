package br.com.basis.madre.service;

import br.com.basis.madre.domain.Etnia;
import br.com.basis.madre.repository.EtniaRepository;
import br.com.basis.madre.repository.search.EtniaSearchRepository;
import br.com.basis.madre.service.dto.EtniaDTO;
import br.com.basis.madre.service.mapper.EtniaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Etnia}.
 */
@Service
@Transactional
public class EtniaService {

    private final Logger log = LoggerFactory.getLogger(EtniaService.class);

    private final EtniaRepository etniaRepository;

    private final EtniaMapper etniaMapper;

    private final EtniaSearchRepository etniaSearchRepository;

    public EtniaService(EtniaRepository etniaRepository, EtniaMapper etniaMapper,
        EtniaSearchRepository etniaSearchRepository) {
        this.etniaRepository = etniaRepository;
        this.etniaMapper = etniaMapper;
        this.etniaSearchRepository = etniaSearchRepository;
    }

    /**
     * Save a etnia.
     *
     * @param etniaDTO the entity to save.
     * @return the persisted entity.
     */
    public EtniaDTO save(EtniaDTO etniaDTO) {
        log.debug("Request to save Etnia : {}", etniaDTO);
        Etnia etnia = etniaMapper.toEntity(etniaDTO);
        etnia = etniaRepository.save(etnia);
        EtniaDTO result = etniaMapper.toDto(etnia);
        etniaSearchRepository.save(etnia);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<EtniaDTO> findAll(EtniaDTO etniaDTO, Pageable pageable) {
        log.debug("Request to get all Etnias");
        return etniaRepository.findAll(
            Example.of(etniaMapper.toEntity(etniaDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING)
            ),
            pageable
        ).map(etniaMapper::toDto);
    }

    /**
     * Get all the etnias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EtniaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Etnias");
        return etniaRepository.findAll(pageable)
            .map(etniaMapper::toDto);
    }

    /**
     * Get one etnia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtniaDTO> findOne(Long id) {
        log.debug("Request to get Etnia : {}", id);
        return etniaRepository.findById(id)
            .map(etniaMapper::toDto);
    }

    /**
     * Delete the etnia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Etnia : {}", id);
        etniaRepository.deleteById(id);
        etniaSearchRepository.deleteById(id);
    }

    /**
     * Search for the etnia corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EtniaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Etnias for query {}", query);
        return etniaSearchRepository.search(queryStringQuery(query), pageable)
            .map(etniaMapper::toDto);
    }
}
