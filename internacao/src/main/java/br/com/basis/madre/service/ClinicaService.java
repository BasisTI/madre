package br.com.basis.madre.service;

import br.com.basis.madre.domain.Clinica;
import br.com.basis.madre.repository.ClinicaRepository;
import br.com.basis.madre.repository.search.ClinicaSearchRepository;
import br.com.basis.madre.service.dto.ClinicaDTO;
import br.com.basis.madre.service.mapper.ClinicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Clinica}.
 */
@Service
@Transactional
public class ClinicaService {

    private final Logger log = LoggerFactory.getLogger(ClinicaService.class);

    private final ClinicaRepository clinicaRepository;

    private final ClinicaMapper clinicaMapper;

    private final ClinicaSearchRepository clinicaSearchRepository;

    public ClinicaService(ClinicaRepository clinicaRepository, ClinicaMapper clinicaMapper, ClinicaSearchRepository clinicaSearchRepository) {
        this.clinicaRepository = clinicaRepository;
        this.clinicaMapper = clinicaMapper;
        this.clinicaSearchRepository = clinicaSearchRepository;
    }

    /**
     * Save a clinica.
     *
     * @param clinicaDTO the entity to save.
     * @return the persisted entity.
     */
    public ClinicaDTO save(ClinicaDTO clinicaDTO) {
        log.debug("Request to save Clinica : {}", clinicaDTO);
        Clinica clinica = clinicaMapper.toEntity(clinicaDTO);
        clinica = clinicaRepository.save(clinica);
        ClinicaDTO result = clinicaMapper.toDto(clinica);
        clinicaSearchRepository.save(clinica);
        return result;
    }

    /**
     * Get all the clinicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClinicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clinicas");
        return clinicaRepository.findAll(pageable)
            .map(clinicaMapper::toDto);
    }


    /**
     * Get one clinica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClinicaDTO> findOne(Long id) {
        log.debug("Request to get Clinica : {}", id);
        return clinicaRepository.findById(id)
            .map(clinicaMapper::toDto);
    }

    /**
     * Delete the clinica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Clinica : {}", id);
        clinicaRepository.deleteById(id);
        clinicaSearchRepository.deleteById(id);
    }

    /**
     * Search for the clinica corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClinicaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clinicas for query {}", query);
        return clinicaSearchRepository.search(queryStringQuery(query), pageable)
            .map(clinicaMapper::toDto);
    }
}
