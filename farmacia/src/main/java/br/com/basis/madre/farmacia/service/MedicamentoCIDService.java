package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.MedicamentoCID;
import br.com.basis.madre.farmacia.repository.MedicamentoCIDRepository;
import br.com.basis.madre.farmacia.repository.search.MedicamentoCIDSearchRepository;
import br.com.basis.madre.farmacia.service.dto.MedicamentoCIDDTO;
import br.com.basis.madre.farmacia.service.mapper.MedicamentoCIDMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MedicamentoCID}.
 */
@Service
@Transactional
public class MedicamentoCIDService {

    private final Logger log = LoggerFactory.getLogger(MedicamentoCIDService.class);

    private final MedicamentoCIDRepository medicamentoCIDRepository;

    private final MedicamentoCIDMapper medicamentoCIDMapper;

    private final MedicamentoCIDSearchRepository medicamentoCIDSearchRepository;

    public MedicamentoCIDService(MedicamentoCIDRepository medicamentoCIDRepository, MedicamentoCIDMapper medicamentoCIDMapper, MedicamentoCIDSearchRepository medicamentoCIDSearchRepository) {
        this.medicamentoCIDRepository = medicamentoCIDRepository;
        this.medicamentoCIDMapper = medicamentoCIDMapper;
        this.medicamentoCIDSearchRepository = medicamentoCIDSearchRepository;
    }

    /**
     * Save a medicamentoCID.
     *
     * @param medicamentoCIDDTO the entity to save.
     * @return the persisted entity.
     */
    public MedicamentoCIDDTO save(MedicamentoCIDDTO medicamentoCIDDTO) {
        log.debug("Request to save MedicamentoCID : {}", medicamentoCIDDTO);
        MedicamentoCID medicamentoCID = medicamentoCIDMapper.toEntity(medicamentoCIDDTO);
        medicamentoCID = medicamentoCIDRepository.save(medicamentoCID);
        MedicamentoCIDDTO result = medicamentoCIDMapper.toDto(medicamentoCID);
        medicamentoCIDSearchRepository.save(medicamentoCID);
        return result;
    }

    /**
     * Get all the medicamentoCIDS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MedicamentoCIDDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicamentoCIDS");
        return medicamentoCIDRepository.findAll(pageable)
            .map(medicamentoCIDMapper::toDto);
    }


    /**
     * Get one medicamentoCID by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MedicamentoCIDDTO> findOne(Long id) {
        log.debug("Request to get MedicamentoCID : {}", id);
        return medicamentoCIDRepository.findById(id)
            .map(medicamentoCIDMapper::toDto);
    }

    /**
     * Delete the medicamentoCID by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MedicamentoCID : {}", id);
        medicamentoCIDRepository.deleteById(id);
        medicamentoCIDSearchRepository.deleteById(id);
    }

    /**
     * Search for the medicamentoCID corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MedicamentoCIDDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MedicamentoCIDS for query {}", query);
        return medicamentoCIDSearchRepository.search(queryStringQuery(query), pageable)
            .map(medicamentoCIDMapper::toDto);
    }
}
