package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.SolicitacaoExame;
import br.com.basis.madre.madreexames.repository.SolicitacaoExameRepository;
import br.com.basis.madre.madreexames.repository.search.SolicitacaoExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.SolicitacaoExameCompletoDTO;
import br.com.basis.madre.madreexames.service.dto.SolicitacaoExameDTO;
import br.com.basis.madre.madreexames.service.mapper.SolicitacaoExameCompletoMapper;
import br.com.basis.madre.madreexames.service.mapper.SolicitacaoExameMapper;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link SolicitacaoExame}.
 */
@Service
@Transactional
public class SolicitacaoExameService {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoExameService.class);

    private final SolicitacaoExameRepository solicitacaoExameRepository;

    private final SolicitacaoExameMapper solicitacaoExameMapper;

    private final SolicitacaoExameSearchRepository solicitacaoExameSearchRepository;

    private final SolicitacaoExameCompletoMapper solicitacaoExameCompletoMapper;

    private final String[] includes = new String[]{"id", "infoClinica", "pedidoPrimeiroExame", "usoAntimicrobianos24h"};

    public SolicitacaoExameService(SolicitacaoExameRepository solicitacaoExameRepository, SolicitacaoExameMapper solicitacaoExameMapper, SolicitacaoExameSearchRepository solicitacaoExameSearchRepository, SolicitacaoExameCompletoMapper solicitacaoExameCompletoMapper) {
        this.solicitacaoExameRepository = solicitacaoExameRepository;
        this.solicitacaoExameMapper = solicitacaoExameMapper;
        this.solicitacaoExameSearchRepository = solicitacaoExameSearchRepository;
        this.solicitacaoExameCompletoMapper = solicitacaoExameCompletoMapper;
    }

    /**
     * Save a solicitacaoExame.
     *
     * @param solicitacaoExameCompletoDTO the entity to save.
     * @return the persisted entity.
     */
    public SolicitacaoExameCompletoDTO save(SolicitacaoExameCompletoDTO solicitacaoExameCompletoDTO) {
        log.debug("Request to save SolicitacaoExame : {}", solicitacaoExameCompletoDTO);
        SolicitacaoExame solicitacaoExame = solicitacaoExameCompletoMapper.toEntity(solicitacaoExameCompletoDTO);
        solicitacaoExame = solicitacaoExameRepository.save(solicitacaoExame);
        SolicitacaoExameCompletoDTO result = solicitacaoExameCompletoMapper.toDto(solicitacaoExame);
        solicitacaoExameSearchRepository.save(solicitacaoExame);
        return result;
    }

    /**
     * Get all the solicitacaoExames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SolicitacaoExameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SolicitacaoExames");
        return solicitacaoExameRepository.findAll(pageable)
            .map(solicitacaoExameMapper::toDto);
    }


    /**
     * Get one solicitacaoExame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SolicitacaoExameDTO> findOne(Long id) {
        log.debug("Request to get SolicitacaoExame : {}", id);
        return solicitacaoExameRepository.findById(id)
            .map(solicitacaoExameMapper::toDto);
    }

    /**
     * Delete the solicitacaoExame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SolicitacaoExame : {}", id);
        solicitacaoExameRepository.deleteById(id);
        solicitacaoExameSearchRepository.deleteById(id);
    }

    /**
     * Search for the solicitacaoExame corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SolicitacaoExameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SolicitacaoExames for query {}", query);
        return solicitacaoExameSearchRepository.search(queryStringQuery(query), pageable)
            .map(solicitacaoExameMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SolicitacaoExame> filtraSolicitacaoExame(Pageable pageable, String id, String pedidoPrimeiroExame, String usoAntimicrobianos24h) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        filter(queryBuilder, "id", id);
        filter(queryBuilder, "pedidoPrimeiroExame", pedidoPrimeiroExame);
        filter(queryBuilder, "usoAntimicrobianos24h", usoAntimicrobianos24h);
        SearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable)
            .build();
        return solicitacaoExameSearchRepository.search(query);
    }

    public Page<SolicitacaoExameDTO> buscarTodasSolicitacoes(Pageable pageable) {
        NativeSearchQuery nativeSearchQueryBuilder = new NativeSearchQueryBuilder()

            .withSourceFilter(new FetchSourceFilterBuilder().withIncludes(includes).build())
            .withPageable(pageable)
            .build();

        return solicitacaoExameSearchRepository.search(nativeSearchQueryBuilder)
            .map(solicitacaoExameCompletoMapper::toDto);
    }

    private void filter(BoolQueryBuilder queryBuilder, String name, String valueName) {
        if (!Strings.isNullOrEmpty(valueName)) {
            queryBuilder.must(QueryBuilders.matchQuery(name,valueName));
        }
    }
}
