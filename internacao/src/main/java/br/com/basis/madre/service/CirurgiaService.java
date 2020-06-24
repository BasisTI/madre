package br.com.basis.madre.service;

import br.com.basis.madre.domain.Cirurgia;
import br.com.basis.madre.repository.CirurgiaRepository;
import br.com.basis.madre.repository.search.CirurgiaSearchRepository;
import br.com.basis.madre.service.dto.CirurgiaDTO;
import br.com.basis.madre.service.mapper.CirurgiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Cirurgia}.
 */
@Service
@Transactional
public class CirurgiaService {

    private final Logger log = LoggerFactory.getLogger(CirurgiaService.class);

    private final CirurgiaRepository cirurgiaRepository;

    private final CirurgiaMapper cirurgiaMapper;

    private final CirurgiaSearchRepository cirurgiaSearchRepository;

    public CirurgiaService(CirurgiaRepository cirurgiaRepository, CirurgiaMapper cirurgiaMapper, CirurgiaSearchRepository cirurgiaSearchRepository) {
        this.cirurgiaRepository = cirurgiaRepository;
        this.cirurgiaMapper = cirurgiaMapper;
        this.cirurgiaSearchRepository = cirurgiaSearchRepository;
    }

    /**
     * Save a cirurgia.
     *
     * @param cirurgiaDTO the entity to save.
     * @return the persisted entity.
     */
    public CirurgiaDTO save(CirurgiaDTO cirurgiaDTO) {
        log.debug("Request to save Cirurgia : {}", cirurgiaDTO);
        Cirurgia cirurgia = cirurgiaMapper.toEntity(cirurgiaDTO);
        cirurgia = cirurgiaRepository.save(cirurgia);
        CirurgiaDTO result = cirurgiaMapper.toDto(cirurgia);
        cirurgiaSearchRepository.save(cirurgia);
        return result;
    }

    /**
     * Get all the cirurgias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CirurgiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cirurgias");
        return cirurgiaRepository.findAll(pageable)
            .map(cirurgiaMapper::toDto);
    }


    /**
     * Get one cirurgia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CirurgiaDTO> findOne(Long id) {
        log.debug("Request to get Cirurgia : {}", id);
        return cirurgiaRepository.findById(id)
            .map(cirurgiaMapper::toDto);
    }

    /**
     * Delete the cirurgia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cirurgia : {}", id);
        cirurgiaRepository.deleteById(id);
        cirurgiaSearchRepository.deleteById(id);
    }

    /**
     * Search for the cirurgia corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CirurgiaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cirurgias for query {}", query);
        return cirurgiaSearchRepository.search(queryStringQuery(query), pageable)
            .map(cirurgiaMapper::toDto);
    }
}
