package br.com.basis.madre.service;

import br.com.basis.madre.domain.GrauDeParentesco;
import br.com.basis.madre.repository.GrauDeParentescoRepository;
import br.com.basis.madre.repository.search.GrauDeParentescoSearchRepository;
import br.com.basis.madre.service.dto.GrauDeParentescoDTO;
import br.com.basis.madre.service.dto.OcupacaoDTO;
import br.com.basis.madre.service.mapper.GrauDeParentescoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GrauDeParentesco}.
 */
@Service
@Transactional
public class GrauDeParentescoService {

    private final Logger log = LoggerFactory.getLogger(GrauDeParentescoService.class);

    private final GrauDeParentescoRepository grauDeParentescoRepository;

    private final GrauDeParentescoMapper grauDeParentescoMapper;

    private final GrauDeParentescoSearchRepository grauDeParentescoSearchRepository;

    public GrauDeParentescoService(GrauDeParentescoRepository grauDeParentescoRepository, GrauDeParentescoMapper grauDeParentescoMapper, GrauDeParentescoSearchRepository grauDeParentescoSearchRepository) {
        this.grauDeParentescoRepository = grauDeParentescoRepository;
        this.grauDeParentescoMapper = grauDeParentescoMapper;
        this.grauDeParentescoSearchRepository = grauDeParentescoSearchRepository;
    }

    /**
     * Save a grauDeParentesco.
     *
     * @param grauDeParentescoDTO the entity to save.
     * @return the persisted entity.
     */
    public GrauDeParentescoDTO save(GrauDeParentescoDTO grauDeParentescoDTO) {
        log.debug("Request to save GrauDeParentesco : {}", grauDeParentescoDTO);
        GrauDeParentesco grauDeParentesco = grauDeParentescoMapper.toEntity(grauDeParentescoDTO);
        grauDeParentesco = grauDeParentescoRepository.save(grauDeParentesco);
        GrauDeParentescoDTO result = grauDeParentescoMapper.toDto(grauDeParentesco);
        grauDeParentescoSearchRepository.save(grauDeParentesco);
        return result;
    }

    /**
     * Get all the grauDeParentescos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrauDeParentescoDTO> findAll(GrauDeParentescoDTO grauDeParentescoDTO,Pageable pageable) {
        log.debug("Request to get all GrauDeParentescos");
        return grauDeParentescoRepository.findAll(
            Example.of(grauDeParentescoMapper.toEntity(grauDeParentescoDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    ExampleMatcher.StringMatcher.CONTAINING)),
                pageable)
            .map(grauDeParentescoMapper::toDto);

    }

    /**
     * Get one grauDeParentesco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrauDeParentescoDTO> findOne(Long id) {
        log.debug("Request to get GrauDeParentesco : {}", id);
        return grauDeParentescoRepository.findById(id)
            .map(grauDeParentescoMapper::toDto);
    }

    /**
     * Delete the grauDeParentesco by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrauDeParentesco : {}", id);
        grauDeParentescoRepository.deleteById(id);
        grauDeParentescoSearchRepository.deleteById(id);
    }

    /**
     * Search for the grauDeParentesco corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrauDeParentescoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GrauDeParentescos for query {}", query);
        return grauDeParentescoSearchRepository.search(queryStringQuery(query), pageable)
            .map(grauDeParentescoMapper::toDto);
    }
}
