package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.PrescricaoProcedimentoEspecial;
import br.com.basis.madre.prescricao.repository.PrescricaoProcedimentoEspecialRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoProcedimentoEspecialSearchRepository;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoEspecialDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoProcedimentoEspecialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PrescricaoProcedimentoEspecial}.
 */
@Service
@Transactional
public class PrescricaoProcedimentoEspecialService {

    private final Logger log = LoggerFactory.getLogger(PrescricaoProcedimentoEspecialService.class);

    private final PrescricaoProcedimentoEspecialRepository prescricaoProcedimentoEspecialRepository;

    private final PrescricaoProcedimentoEspecialMapper prescricaoProcedimentoEspecialMapper;

    private final PrescricaoProcedimentoEspecialSearchRepository prescricaoProcedimentoEspecialSearchRepository;

    public PrescricaoProcedimentoEspecialService(PrescricaoProcedimentoEspecialRepository prescricaoProcedimentoEspecialRepository, PrescricaoProcedimentoEspecialMapper prescricaoProcedimentoEspecialMapper, PrescricaoProcedimentoEspecialSearchRepository prescricaoProcedimentoEspecialSearchRepository) {
        this.prescricaoProcedimentoEspecialRepository = prescricaoProcedimentoEspecialRepository;
        this.prescricaoProcedimentoEspecialMapper = prescricaoProcedimentoEspecialMapper;
        this.prescricaoProcedimentoEspecialSearchRepository = prescricaoProcedimentoEspecialSearchRepository;
    }

    /**
     * Save a prescricaoProcedimentoEspecial.
     *
     * @param prescricaoProcedimentoEspecialDTO the entity to save.
     * @return the persisted entity.
     */
    public PrescricaoProcedimentoEspecialDTO save(PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO) {
        log.debug("Request to save PrescricaoProcedimentoEspecial : {}", prescricaoProcedimentoEspecialDTO);
        PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial = prescricaoProcedimentoEspecialMapper.toEntity(prescricaoProcedimentoEspecialDTO);
        prescricaoProcedimentoEspecial = prescricaoProcedimentoEspecialRepository.save(prescricaoProcedimentoEspecial);
        PrescricaoProcedimentoEspecialDTO result = prescricaoProcedimentoEspecialMapper.toDto(prescricaoProcedimentoEspecial);
        prescricaoProcedimentoEspecialSearchRepository.save(prescricaoProcedimentoEspecial);
        return result;
    }

    /**
     * Get all the prescricaoProcedimentoEspecials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoProcedimentoEspecialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrescricaoProcedimentoEspecials");
        return prescricaoProcedimentoEspecialRepository.findAll(pageable)
            .map(prescricaoProcedimentoEspecialMapper::toDto);
    }


    /**
     * Get one prescricaoProcedimentoEspecial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrescricaoProcedimentoEspecialDTO> findOne(Long id) {
        log.debug("Request to get PrescricaoProcedimentoEspecial : {}", id);
        return prescricaoProcedimentoEspecialRepository.findById(id)
            .map(prescricaoProcedimentoEspecialMapper::toDto);
    }

    /**
     * Delete the prescricaoProcedimentoEspecial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrescricaoProcedimentoEspecial : {}", id);
        prescricaoProcedimentoEspecialRepository.deleteById(id);
        prescricaoProcedimentoEspecialSearchRepository.deleteById(id);
    }

    /**
     * Search for the prescricaoProcedimentoEspecial corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoProcedimentoEspecialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrescricaoProcedimentoEspecials for query {}", query);
        return prescricaoProcedimentoEspecialSearchRepository.search(queryStringQuery(query), pageable)
            .map(prescricaoProcedimentoEspecialMapper::toDto);
    }
}
