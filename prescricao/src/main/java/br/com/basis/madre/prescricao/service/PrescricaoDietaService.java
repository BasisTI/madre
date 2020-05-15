package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.PrescricaoDieta;
import br.com.basis.madre.prescricao.repository.PrescricaoDietaRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoDietaSearchRepository;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDietaDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoDietaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PrescricaoDieta}.
 */
@Service
@Transactional
public class PrescricaoDietaService {

    private final Logger log = LoggerFactory.getLogger(PrescricaoDietaService.class);

    private final PrescricaoDietaRepository prescricaoDietaRepository;

    private final PrescricaoDietaMapper prescricaoDietaMapper;

    private final PrescricaoDietaSearchRepository prescricaoDietaSearchRepository;

    public PrescricaoDietaService(PrescricaoDietaRepository prescricaoDietaRepository, PrescricaoDietaMapper prescricaoDietaMapper, PrescricaoDietaSearchRepository prescricaoDietaSearchRepository) {
        this.prescricaoDietaRepository = prescricaoDietaRepository;
        this.prescricaoDietaMapper = prescricaoDietaMapper;
        this.prescricaoDietaSearchRepository = prescricaoDietaSearchRepository;
    }

    /**
     * Save a prescricaoDieta.
     *
     * @param prescricaoDietaDTO the entity to save.
     * @return the persisted entity.
     */
    public PrescricaoDietaDTO save(PrescricaoDietaDTO prescricaoDietaDTO) {
        log.debug("Request to save PrescricaoDieta : {}", prescricaoDietaDTO);
        PrescricaoDieta prescricaoDieta = prescricaoDietaMapper.toEntity(prescricaoDietaDTO);
        prescricaoDieta = prescricaoDietaRepository.save(prescricaoDieta);
        PrescricaoDietaDTO result = prescricaoDietaMapper.toDto(prescricaoDieta);
        prescricaoDietaSearchRepository.save(prescricaoDieta);
        return result;
    }

    /**
     * Get all the prescricaoDietas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoDietaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrescricaoDietas");
        return prescricaoDietaRepository.findAll(pageable)
            .map(prescricaoDietaMapper::toDto);
    }


    /**
     * Get one prescricaoDieta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrescricaoDietaDTO> findOne(Long id) {
        log.debug("Request to get PrescricaoDieta : {}", id);
        return prescricaoDietaRepository.findById(id)
            .map(prescricaoDietaMapper::toDto);
    }

    /**
     * Delete the prescricaoDieta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrescricaoDieta : {}", id);
        prescricaoDietaRepository.deleteById(id);
        prescricaoDietaSearchRepository.deleteById(id);
    }

    /**
     * Search for the prescricaoDieta corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoDietaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrescricaoDietas for query {}", query);
        return prescricaoDietaSearchRepository.search(queryStringQuery(query), pageable)
            .map(prescricaoDietaMapper::toDto);
    }
}
