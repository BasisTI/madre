package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Sala;
import br.com.basis.madre.madreexames.repository.SalaRepository;
import br.com.basis.madre.madreexames.repository.search.SalaSearchRepository;
import br.com.basis.madre.madreexames.service.dto.SalaDTO;
import br.com.basis.madre.madreexames.service.mapper.SalaMapper;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Sala}.
 */
@Service
@Transactional
public class SalaService {

    private final Logger log = LoggerFactory.getLogger(SalaService.class);

    private final SalaRepository salaRepository;

    private final SalaMapper salaMapper;

    private final SalaSearchRepository salaSearchRepository;

    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper, SalaSearchRepository salaSearchRepository) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
        this.salaSearchRepository = salaSearchRepository;
    }

    /**
     * Save a sala.
     *
     * @param salaDTO the entity to save.
     * @return the persisted entity.
     */
    public SalaDTO save(SalaDTO salaDTO) {
        log.debug("Request to save Sala : {}", salaDTO);
        Sala sala = salaMapper.toEntity(salaDTO);
        sala = salaRepository.save(sala);
        SalaDTO result = salaMapper.toDto(sala);
        salaSearchRepository.save(sala);
        return result;
    }

    /**
     * Get all the salas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SalaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Salas");
        return salaRepository.findAll(pageable)
            .map(salaMapper::toDto);
    }


    /**
     * Get one sala by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalaDTO> findOne(Long id) {
        log.debug("Request to get Sala : {}", id);
        return salaRepository.findById(id)
            .map(salaMapper::toDto);
    }

    /**
     * Delete the sala by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sala : {}", id);
        salaRepository.deleteById(id);
        salaSearchRepository.deleteById(id);
    }

    /**
     * Search for the sala corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SalaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Salas for query {}", query);
        return salaSearchRepository.search(queryStringQuery(query), pageable)
            .map(salaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SalaDTO> filtrarSalasPorUnidade(Pageable pageable, String unidadeExecutoraId, String ativo) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        filter(queryBuilder, "unidadeExecutoraId", unidadeExecutoraId);
        filter(queryBuilder, "ativo", ativo);
        SearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable)
            .build();
        return salaSearchRepository.search(query).map(salaMapper::toDto);
    }

    private void filter(BoolQueryBuilder queryBuilder, String name, String valueName) {
        if (!Strings.isNullOrEmpty(valueName)) {
            queryBuilder.must(QueryBuilders.matchQuery(name, valueName));
        }
    }
}
