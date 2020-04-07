package br.com.basis.madre.service;

import br.com.basis.madre.domain.Nacionalidade;
import br.com.basis.madre.repository.NacionalidadeRepository;
import br.com.basis.madre.repository.search.NacionalidadeSearchRepository;
import br.com.basis.madre.service.dto.NacionalidadeDTO;
import br.com.basis.madre.service.mapper.NacionalidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Nacionalidade}.
 */
@Service
@Transactional
public class NacionalidadeService {

    private final Logger log = LoggerFactory.getLogger(NacionalidadeService.class);

    private final NacionalidadeRepository nacionalidadeRepository;

    private final NacionalidadeMapper nacionalidadeMapper;

    private final NacionalidadeSearchRepository nacionalidadeSearchRepository;

    public NacionalidadeService(NacionalidadeRepository nacionalidadeRepository, NacionalidadeMapper nacionalidadeMapper, NacionalidadeSearchRepository nacionalidadeSearchRepository) {
        this.nacionalidadeRepository = nacionalidadeRepository;
        this.nacionalidadeMapper = nacionalidadeMapper;
        this.nacionalidadeSearchRepository = nacionalidadeSearchRepository;
    }

    /**
     * Save a nacionalidade.
     *
     * @param nacionalidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public NacionalidadeDTO save(NacionalidadeDTO nacionalidadeDTO) {
        log.debug("Request to save Nacionalidade : {}", nacionalidadeDTO);
        Nacionalidade nacionalidade = nacionalidadeMapper.toEntity(nacionalidadeDTO);
        nacionalidade = nacionalidadeRepository.save(nacionalidade);
        NacionalidadeDTO result = nacionalidadeMapper.toDto(nacionalidade);
        nacionalidadeSearchRepository.save(nacionalidade);
        return result;
    }

    /**
     * Get all the nacionalidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NacionalidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nacionalidades");
        return nacionalidadeRepository.findAll(pageable)
            .map(nacionalidadeMapper::toDto);
    }

    /**
     * Get one nacionalidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NacionalidadeDTO> findOne(Long id) {
        log.debug("Request to get Nacionalidade : {}", id);
        return nacionalidadeRepository.findById(id)
            .map(nacionalidadeMapper::toDto);
    }

    /**
     * Delete the nacionalidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Nacionalidade : {}", id);
        nacionalidadeRepository.deleteById(id);
        nacionalidadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the nacionalidade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NacionalidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Nacionalidades for query {}", query);
        return nacionalidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(nacionalidadeMapper::toDto);
    }
}
