package br.com.basis.madre.service;

import br.com.basis.madre.domain.UnidadeFuncional;
import br.com.basis.madre.repository.UnidadeFuncionalRepository;
import br.com.basis.madre.repository.search.UnidadeFuncionalSearchRepository;
import br.com.basis.madre.service.dto.UnidadeFuncionalDTO;
import br.com.basis.madre.service.mapper.UnidadeFuncionalMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RequiredArgsConstructor
@Service
@Transactional
public class UnidadeFuncionalService {

    private final Logger log = LoggerFactory.getLogger(UnidadeFuncionalService.class);

    private final UnidadeFuncionalRepository unidadeFuncionalRepository;

    private final UnidadeFuncionalMapper unidadeFuncionalMapper;

    private final UnidadeFuncionalSearchRepository unidadeFuncionalSearchRepository;

    /**
     * Save a unidadeFuncional.
     *
     * @param unidadeFuncionalDTO the entity to save.
     * @return the persisted entity.
     */
    public UnidadeFuncionalDTO save(UnidadeFuncionalDTO unidadeFuncionalDTO) {
        log.debug("Request to save UnidadeFuncional : {}", unidadeFuncionalDTO);
        UnidadeFuncional unidadeFuncional = unidadeFuncionalMapper.toEntity(unidadeFuncionalDTO);
        unidadeFuncional = unidadeFuncionalRepository.save(unidadeFuncional);
        UnidadeFuncionalDTO result = unidadeFuncionalMapper.toDto(unidadeFuncional);
        unidadeFuncionalSearchRepository.save(unidadeFuncional);
        return result;
    }

    /**
     * Get all the unidadeFuncionals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeFuncionalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadeFuncionals");
        return unidadeFuncionalRepository.findAll(pageable)
            .map(unidadeFuncionalMapper::toDto);
    }


    /**
     * Get one unidadeFuncional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnidadeFuncionalDTO> findOne(Long id) {
        log.debug("Request to get UnidadeFuncional : {}", id);
        return unidadeFuncionalRepository.findById(id)
            .map(unidadeFuncionalMapper::toDto);
    }

    /**
     * Delete the unidadeFuncional by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UnidadeFuncional : {}", id);
        unidadeFuncionalRepository.deleteById(id);
        unidadeFuncionalSearchRepository.deleteById(id);
    }

    /**
     * Search for the unidadeFuncional corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeFuncionalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadeFuncionals for query {}", query);
        return unidadeFuncionalSearchRepository.search(queryStringQuery(query), pageable)
            .map(unidadeFuncionalMapper::toDto);
    }
}
