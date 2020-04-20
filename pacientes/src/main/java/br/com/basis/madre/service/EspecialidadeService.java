package br.com.basis.madre.service;

import br.com.basis.madre.domain.Especialidade;
import br.com.basis.madre.repository.EspecialidadeRepository;
import br.com.basis.madre.repository.search.EspecialidadeSearchRepository;
import br.com.basis.madre.service.dto.EspecialidadeDTO;
import br.com.basis.madre.service.mapper.EspecialidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Especialidade.
 */
@Service
@Transactional
public class EspecialidadeService {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeService.class);

    private final EspecialidadeRepository especialidadeRepository;

    private final EspecialidadeMapper especialidadeMapper;

    private final EspecialidadeSearchRepository especialidadeSearchRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository, EspecialidadeMapper especialidadeMapper, EspecialidadeSearchRepository especialidadeSearchRepository) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeMapper = especialidadeMapper;
        this.especialidadeSearchRepository = especialidadeSearchRepository;
    }

    /**
     * Save a especialidade.
     *
     * @param especialidadeDTO the entity to save
     * @return the persisted entity
     */
    public EspecialidadeDTO save(EspecialidadeDTO especialidadeDTO) {
        log.debug("Request to save Especialidade : {}", especialidadeDTO);

        Especialidade especialidade = especialidadeMapper.toEntity(especialidadeDTO);
        especialidade = especialidadeRepository.save(especialidade);
        EspecialidadeDTO result = especialidadeMapper.toDto(especialidade);
        especialidadeSearchRepository.save(especialidade);
        return result;
    }

    /**
     * Get all the especialidades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EspecialidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Especialidades");
        return especialidadeRepository.findAll(pageable)
            .map(especialidadeMapper::toDto);
    }


    /**
     * Get one especialidade by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EspecialidadeDTO> findOne(Long id) {
        log.debug("Request to get Especialidade : {}", id);
        return especialidadeRepository.findById(id)
            .map(especialidadeMapper::toDto);
    }

    /**
     * Delete the especialidade by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Especialidade : {}", id);
        especialidadeRepository.deleteById(id);
        especialidadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the especialidade corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EspecialidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Especialidades for query {}", query);
        return especialidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(especialidadeMapper::toDto);
    }
}
