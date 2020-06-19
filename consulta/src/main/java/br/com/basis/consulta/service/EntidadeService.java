package br.com.basis.consulta.service;

import br.com.basis.consulta.domain.Entidade;
import br.com.basis.consulta.repository.EntidadeRepository;
import br.com.basis.consulta.repository.search.EntidadeSearchRepository;
import br.com.basis.consulta.service.dto.EntidadeDTO;
import br.com.basis.consulta.service.mapper.EntidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Entidade}.
 */
@Service
@Transactional
public class EntidadeService {

    private final Logger log = LoggerFactory.getLogger(EntidadeService.class);

    private final EntidadeRepository entidadeRepository;

    private final EntidadeMapper entidadeMapper;

    private final EntidadeSearchRepository entidadeSearchRepository;

    public EntidadeService(EntidadeRepository entidadeRepository, EntidadeMapper entidadeMapper, EntidadeSearchRepository entidadeSearchRepository) {
        this.entidadeRepository = entidadeRepository;
        this.entidadeMapper = entidadeMapper;
        this.entidadeSearchRepository = entidadeSearchRepository;
    }

    /**
     * Save a entidade.
     *
     * @param entidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public EntidadeDTO save(EntidadeDTO entidadeDTO) {
        log.debug("Request to save Entidade : {}", entidadeDTO);
        Entidade entidade = entidadeMapper.toEntity(entidadeDTO);
        entidade = entidadeRepository.save(entidade);
        EntidadeDTO result = entidadeMapper.toDto(entidade);
        entidadeSearchRepository.save(entidade);
        return result;
    }

    /**
     * Get all the entidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entidades");
        return entidadeRepository.findAll(pageable)
            .map(entidadeMapper::toDto);
    }


    /**
     * Get one entidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntidadeDTO> findOne(Long id) {
        log.debug("Request to get Entidade : {}", id);
        return entidadeRepository.findById(id)
            .map(entidadeMapper::toDto);
    }

    /**
     * Delete the entidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entidade : {}", id);
        entidadeRepository.deleteById(id);
        entidadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the entidade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Entidades for query {}", query);
        return entidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(entidadeMapper::toDto);
    }
}
