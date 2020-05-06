package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.repository.LeitoRepository;
import br.com.basis.madre.repository.search.LeitoSearchRepository;
import br.com.basis.madre.service.dto.LeitoDTO;
import br.com.basis.madre.service.mapper.LeitoMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LeitoService {

    private final Logger log = LoggerFactory.getLogger(LeitoService.class);

    private final LeitoRepository leitoRepository;

    private final LeitoMapper leitoMapper;

    private final LeitoSearchRepository leitoSearchRepository;

    /**
     * Save a leito.
     *
     * @param leitoDTO the entity to save.
     * @return the persisted entity.
     */
    public LeitoDTO save(LeitoDTO leitoDTO) {
        log.debug("Request to save Leito : {}", leitoDTO);
        Leito leito = leitoMapper.toEntity(leitoDTO);
        leito = leitoRepository.save(leito);
        LeitoDTO result = leitoMapper.toDto(leito);
        leitoSearchRepository.save(leito);
        return result;
    }

    /**
     * Get all the leitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Leitos");
        return leitoRepository.findAll(pageable)
            .map(leitoMapper::toDto);
    }


    /**
     * Get one leito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeitoDTO> findOne(Long id) {
        log.debug("Request to get Leito : {}", id);
        return leitoRepository.findById(id)
            .map(leitoMapper::toDto);
    }

    /**
     * Delete the leito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Leito : {}", id);
        leitoRepository.deleteById(id);
        leitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the leito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Leitos for query {}", query);
        return leitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(leitoMapper::toDto);
    }
}
