package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.BloqueioDeLeito;
import br.com.basis.madre.repository.BloqueioDeLeitoRepository;
import br.com.basis.madre.repository.search.BloqueioDeLeitoSearchRepository;
import br.com.basis.madre.service.dto.BloqueioDeLeitoDTO;
import br.com.basis.madre.service.mapper.BloqueioDeLeitoMapper;
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
public class BloqueioDeLeitoService {

    private final Logger log = LoggerFactory.getLogger(BloqueioDeLeitoService.class);

    private final BloqueioDeLeitoRepository bloqueioDeLeitoRepository;

    private final BloqueioDeLeitoMapper bloqueioDeLeitoMapper;

    private final BloqueioDeLeitoSearchRepository bloqueioDeLeitoSearchRepository;

    /**
     * Save a bloqueioDeLeito.
     *
     * @param bloqueioDeLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public BloqueioDeLeitoDTO save(BloqueioDeLeitoDTO bloqueioDeLeitoDTO) {
        log.debug("Request to save BloqueioDeLeito : {}", bloqueioDeLeitoDTO);
        BloqueioDeLeito bloqueioDeLeito = bloqueioDeLeitoMapper.toEntity(bloqueioDeLeitoDTO);
        bloqueioDeLeito = bloqueioDeLeitoRepository.save(bloqueioDeLeito);
        BloqueioDeLeitoDTO result = bloqueioDeLeitoMapper.toDto(bloqueioDeLeito);
        bloqueioDeLeitoSearchRepository.save(bloqueioDeLeito);
        return result;
    }

    /**
     * Get all the bloqueioDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BloqueioDeLeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BloqueioDeLeitos");
        return bloqueioDeLeitoRepository.findAll(pageable)
            .map(bloqueioDeLeitoMapper::toDto);
    }


    /**
     * Get one bloqueioDeLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BloqueioDeLeitoDTO> findOne(Long id) {
        log.debug("Request to get BloqueioDeLeito : {}", id);
        return bloqueioDeLeitoRepository.findById(id)
            .map(bloqueioDeLeitoMapper::toDto);
    }

    /**
     * Delete the bloqueioDeLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BloqueioDeLeito : {}", id);
        bloqueioDeLeitoRepository.deleteById(id);
        bloqueioDeLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the bloqueioDeLeito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BloqueioDeLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BloqueioDeLeitos for query {}", query);
        return bloqueioDeLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(bloqueioDeLeitoMapper::toDto);
    }
}
