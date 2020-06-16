package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.PrescricaoMedica;
import br.com.basis.madre.prescricao.repository.PrescricaoMedicaRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoMedicaSearchRepository;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicaDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoMedicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PrescricaoMedica}.
 */
@Service
@Transactional
public class PrescricaoMedicaService {

    private final Logger log = LoggerFactory.getLogger(PrescricaoMedicaService.class);

    private final PrescricaoMedicaRepository prescricaoMedicaRepository;

    private final PrescricaoMedicaMapper prescricaoMedicaMapper;

    private final PrescricaoMedicaSearchRepository prescricaoMedicaSearchRepository;

    public PrescricaoMedicaService(PrescricaoMedicaRepository prescricaoMedicaRepository, PrescricaoMedicaMapper prescricaoMedicaMapper, PrescricaoMedicaSearchRepository prescricaoMedicaSearchRepository) {
        this.prescricaoMedicaRepository = prescricaoMedicaRepository;
        this.prescricaoMedicaMapper = prescricaoMedicaMapper;
        this.prescricaoMedicaSearchRepository = prescricaoMedicaSearchRepository;
    }

    /**
     * Save a prescricaoMedica.
     *
     * @param prescricaoMedicaDTO the entity to save.
     * @return the persisted entity.
     */
    public PrescricaoMedicaDTO save(PrescricaoMedicaDTO prescricaoMedicaDTO) {
        log.debug("Request to save PrescricaoMedica : {}", prescricaoMedicaDTO);
        PrescricaoMedica prescricaoMedica = prescricaoMedicaMapper.toEntity(prescricaoMedicaDTO);
        prescricaoMedica = prescricaoMedicaRepository.save(prescricaoMedica);
        PrescricaoMedicaDTO result = prescricaoMedicaMapper.toDto(prescricaoMedica);
        prescricaoMedicaSearchRepository.save(prescricaoMedica);
        return result;
    }

    /**
     * Get all the prescricaoMedicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoMedicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrescricaoMedicas");
        return prescricaoMedicaRepository.findAll(pageable)
            .map(prescricaoMedicaMapper::toDto);
    }


    /**
     * Get one prescricaoMedica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrescricaoMedicaDTO> findOne(Long id) {
        log.debug("Request to get PrescricaoMedica : {}", id);
        return prescricaoMedicaRepository.findById(id)
            .map(prescricaoMedicaMapper::toDto);
    }

    /**
     * Delete the prescricaoMedica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrescricaoMedica : {}", id);
        prescricaoMedicaRepository.deleteById(id);
        prescricaoMedicaSearchRepository.deleteById(id);
    }

    /**
     * Search for the prescricaoMedica corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoMedicaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrescricaoMedicas for query {}", query);
        return prescricaoMedicaSearchRepository.search(queryStringQuery(query), pageable)
            .map(prescricaoMedicaMapper::toDto);
    }
}
