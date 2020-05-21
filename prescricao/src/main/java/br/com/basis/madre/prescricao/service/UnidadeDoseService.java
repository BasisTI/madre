package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.UnidadeDose;
import br.com.basis.madre.prescricao.repository.UnidadeDoseRepository;
import br.com.basis.madre.prescricao.repository.search.UnidadeDoseSearchRepository;
import br.com.basis.madre.prescricao.service.dto.UnidadeDoseDTO;
import br.com.basis.madre.prescricao.service.mapper.UnidadeDoseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UnidadeDose}.
 */
@Service
@Transactional
public class UnidadeDoseService {

    private final Logger log = LoggerFactory.getLogger(UnidadeDoseService.class);

    private final UnidadeDoseRepository unidadeDoseRepository;

    private final UnidadeDoseMapper unidadeDoseMapper;

    private final UnidadeDoseSearchRepository unidadeDoseSearchRepository;

    public UnidadeDoseService(UnidadeDoseRepository unidadeDoseRepository, UnidadeDoseMapper unidadeDoseMapper, UnidadeDoseSearchRepository unidadeDoseSearchRepository) {
        this.unidadeDoseRepository = unidadeDoseRepository;
        this.unidadeDoseMapper = unidadeDoseMapper;
        this.unidadeDoseSearchRepository = unidadeDoseSearchRepository;
    }

    /**
     * Save a unidadeDose.
     *
     * @param unidadeDoseDTO the entity to save.
     * @return the persisted entity.
     */
    public UnidadeDoseDTO save(UnidadeDoseDTO unidadeDoseDTO) {
        log.debug("Request to save UnidadeDose : {}", unidadeDoseDTO);
        UnidadeDose unidadeDose = unidadeDoseMapper.toEntity(unidadeDoseDTO);
        unidadeDose = unidadeDoseRepository.save(unidadeDose);
        UnidadeDoseDTO result = unidadeDoseMapper.toDto(unidadeDose);
        unidadeDoseSearchRepository.save(unidadeDose);
        return result;
    }

    /**
     * Get all the unidadeDoses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeDoseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadeDoses");
        return unidadeDoseRepository.findAll(pageable)
            .map(unidadeDoseMapper::toDto);
    }


    /**
     * Get one unidadeDose by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnidadeDoseDTO> findOne(Long id) {
        log.debug("Request to get UnidadeDose : {}", id);
        return unidadeDoseRepository.findById(id)
            .map(unidadeDoseMapper::toDto);
    }

    /**
     * Delete the unidadeDose by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UnidadeDose : {}", id);
        unidadeDoseRepository.deleteById(id);
        unidadeDoseSearchRepository.deleteById(id);
    }

    /**
     * Search for the unidadeDose corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeDoseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadeDoses for query {}", query);
        return unidadeDoseSearchRepository.search(queryStringQuery(query), pageable)
            .map(unidadeDoseMapper::toDto);
    }
}
