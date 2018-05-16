package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.Especialidade;
import br.com.basis.madre.cadastros.repository.EspecialidadeRepository;
import br.com.basis.madre.cadastros.repository.search.EspecialidadeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Especialidade.
 */
@Service
@Transactional
public class EspecialidadeService {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeService.class);

    private final EspecialidadeRepository especialidadeRepository;

    private final EspecialidadeSearchRepository especialidadeSearchRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository, EspecialidadeSearchRepository especialidadeSearchRepository) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeSearchRepository = especialidadeSearchRepository;
    }

    /**
     * Save a especialidade.
     *
     * @param especialidade the entity to save
     * @return the persisted entity
     */
    public Especialidade save(Especialidade especialidade) {
        log.debug("Request to save Especialidade : {}", especialidade);
        Especialidade result = especialidadeRepository.save(especialidade);
        especialidadeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the especialidades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Especialidade> findAll(Pageable pageable) {
        log.debug("Request to get all Especialidades");
        return especialidadeRepository.findAll(pageable);
    }

    /**
     * Get one especialidade by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Especialidade findOne(Long id) {
        log.debug("Request to get Especialidade : {}", id);
        return especialidadeRepository.findOne(id);
    }

    /**
     * Delete the especialidade by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Especialidade : {}", id);
        especialidadeRepository.delete(id);
        especialidadeSearchRepository.delete(id);
    }

    /**
     * Search for the especialidade corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Especialidade> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Especialidades for query {}", query);
        return especialidadeSearchRepository.search(queryStringQuery(query), pageable);
    }
}
