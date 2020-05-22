package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.EspeciaisDiversos;
import br.com.basis.madre.prescricao.repository.EspeciaisDiversosRepository;
import br.com.basis.madre.prescricao.repository.search.EspeciaisDiversosSearchRepository;
import br.com.basis.madre.prescricao.service.dto.EspeciaisDiversosDTO;
import br.com.basis.madre.prescricao.service.mapper.EspeciaisDiversosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EspeciaisDiversos}.
 */
@Service
@Transactional
public class EspeciaisDiversosService {

    private final Logger log = LoggerFactory.getLogger(EspeciaisDiversosService.class);

    private final EspeciaisDiversosRepository especiaisDiversosRepository;

    private final EspeciaisDiversosMapper especiaisDiversosMapper;

    private final EspeciaisDiversosSearchRepository especiaisDiversosSearchRepository;

    public EspeciaisDiversosService(EspeciaisDiversosRepository especiaisDiversosRepository, EspeciaisDiversosMapper especiaisDiversosMapper, EspeciaisDiversosSearchRepository especiaisDiversosSearchRepository) {
        this.especiaisDiversosRepository = especiaisDiversosRepository;
        this.especiaisDiversosMapper = especiaisDiversosMapper;
        this.especiaisDiversosSearchRepository = especiaisDiversosSearchRepository;
    }

    /**
     * Save a especiaisDiversos.
     *
     * @param especiaisDiversosDTO the entity to save.
     * @return the persisted entity.
     */
    public EspeciaisDiversosDTO save(EspeciaisDiversosDTO especiaisDiversosDTO) {
        log.debug("Request to save EspeciaisDiversos : {}", especiaisDiversosDTO);
        EspeciaisDiversos especiaisDiversos = especiaisDiversosMapper.toEntity(especiaisDiversosDTO);
        especiaisDiversos = especiaisDiversosRepository.save(especiaisDiversos);
        EspeciaisDiversosDTO result = especiaisDiversosMapper.toDto(especiaisDiversos);
        especiaisDiversosSearchRepository.save(especiaisDiversos);
        return result;
    }

    /**
     * Get all the especiaisDiversos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EspeciaisDiversosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EspeciaisDiversos");
        return especiaisDiversosRepository.findAll(pageable)
            .map(especiaisDiversosMapper::toDto);
    }


    /**
     * Get one especiaisDiversos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EspeciaisDiversosDTO> findOne(Long id) {
        log.debug("Request to get EspeciaisDiversos : {}", id);
        return especiaisDiversosRepository.findById(id)
            .map(especiaisDiversosMapper::toDto);
    }

    /**
     * Delete the especiaisDiversos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EspeciaisDiversos : {}", id);
        especiaisDiversosRepository.deleteById(id);
        especiaisDiversosSearchRepository.deleteById(id);
    }

    /**
     * Search for the especiaisDiversos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EspeciaisDiversosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EspeciaisDiversos for query {}", query);
        return especiaisDiversosSearchRepository.search(queryStringQuery(query), pageable)
            .map(especiaisDiversosMapper::toDto);
    }
}
