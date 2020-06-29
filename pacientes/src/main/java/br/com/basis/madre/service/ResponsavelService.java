package br.com.basis.madre.service;

import br.com.basis.madre.domain.Responsavel;
import br.com.basis.madre.repository.ResponsavelRepository;
import br.com.basis.madre.repository.search.ResponsavelSearchRepository;
import br.com.basis.madre.service.dto.ResponsavelDTO;
import br.com.basis.madre.service.mapper.ResponsavelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Responsavel}.
 */
@Service
@Transactional
public class ResponsavelService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelService.class);

    private final ResponsavelRepository responsavelRepository;

    private final ResponsavelMapper responsavelMapper;

    private final ResponsavelSearchRepository responsavelSearchRepository;

    public ResponsavelService(ResponsavelRepository responsavelRepository, ResponsavelMapper responsavelMapper, ResponsavelSearchRepository responsavelSearchRepository) {
        this.responsavelRepository = responsavelRepository;
        this.responsavelMapper = responsavelMapper;
        this.responsavelSearchRepository = responsavelSearchRepository;
    }

    /**
     * Save a responsavel.
     *
     * @param responsavelDTO the entity to save.
     * @return the persisted entity.
     */
    public ResponsavelDTO save(ResponsavelDTO responsavelDTO) {
        log.debug("Request to save Responsavel : {}", responsavelDTO);
        Responsavel responsavel = responsavelMapper.toEntity(responsavelDTO);
        responsavel = responsavelRepository.save(responsavel);
        ResponsavelDTO result = responsavelMapper.toDto(responsavel);
        responsavelSearchRepository.save(responsavel);
        return result;
    }

    /**
     * Get all the responsavels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Responsavels");
        return responsavelRepository.findAll(pageable)
            .map(responsavelMapper::toDto);
    }

    /**
     * Get one responsavel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResponsavelDTO> findOne(Long id) {
        log.debug("Request to get Responsavel : {}", id);
        return responsavelRepository.findById(id)
            .map(responsavelMapper::toDto);
    }

    /**
     * Delete the responsavel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Responsavel : {}", id);
        responsavelRepository.deleteById(id);
        responsavelSearchRepository.deleteById(id);
    }

    /**
     * Search for the responsavel corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Responsavels for query {}", query);
        return responsavelSearchRepository.search(queryStringQuery(query), pageable)
            .map(responsavelMapper::toDto);
    }
}
