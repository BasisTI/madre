package br.com.basis.madre.service;

import br.com.basis.madre.domain.Justificativa;
import br.com.basis.madre.repository.JustificativaRepository;
import br.com.basis.madre.repository.search.JustificativaSearchRepository;
import br.com.basis.madre.service.dto.JustificativaDTO;
import br.com.basis.madre.service.mapper.JustificativaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Justificativa}.
 */
@Service
@Transactional
public class JustificativaService {

    private final Logger log = LoggerFactory.getLogger(JustificativaService.class);

    private final JustificativaRepository justificativaRepository;

    private final JustificativaMapper justificativaMapper;

    private final JustificativaSearchRepository justificativaSearchRepository;

    public JustificativaService(JustificativaRepository justificativaRepository, JustificativaMapper justificativaMapper, JustificativaSearchRepository justificativaSearchRepository) {
        this.justificativaRepository = justificativaRepository;
        this.justificativaMapper = justificativaMapper;
        this.justificativaSearchRepository = justificativaSearchRepository;
    }

    /**
     * Save a justificativa.
     *
     * @param justificativaDTO the entity to save.
     * @return the persisted entity.
     */
    public JustificativaDTO save(JustificativaDTO justificativaDTO) {
        log.debug("Request to save Justificativa : {}", justificativaDTO);
        Justificativa justificativa = justificativaMapper.toEntity(justificativaDTO);
        justificativa = justificativaRepository.save(justificativa);
        JustificativaDTO result = justificativaMapper.toDto(justificativa);
        justificativaSearchRepository.save(justificativa);
        return result;
    }

    /**
     * Get all the justificativas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JustificativaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Justificativas");
        return justificativaRepository.findAll(pageable)
            .map(justificativaMapper::toDto);
    }

    /**
     * Get one justificativa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JustificativaDTO> findOne(Long id) {
        log.debug("Request to get Justificativa : {}", id);
        return justificativaRepository.findById(id)
            .map(justificativaMapper::toDto);
    }

    /**
     * Delete the justificativa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Justificativa : {}", id);
        justificativaRepository.deleteById(id);
        justificativaSearchRepository.deleteById(id);
    }

    /**
     * Search for the justificativa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JustificativaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Justificativas for query {}", query);
        return justificativaSearchRepository.search(queryStringQuery(query), pageable)
            .map(justificativaMapper::toDto);
    }
}
