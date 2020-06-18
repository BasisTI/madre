package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Lote;
import br.com.basis.suprimentos.repository.LoteRepository;
import br.com.basis.suprimentos.repository.search.LoteSearchRepository;
import br.com.basis.suprimentos.service.dto.LoteDTO;
import br.com.basis.suprimentos.service.mapper.LoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Lote}.
 */
@Service
@Transactional
public class LoteService {

    private final Logger log = LoggerFactory.getLogger(LoteService.class);

    private final LoteRepository loteRepository;

    private final LoteMapper loteMapper;

    private final LoteSearchRepository loteSearchRepository;

    public LoteService(LoteRepository loteRepository, LoteMapper loteMapper, LoteSearchRepository loteSearchRepository) {
        this.loteRepository = loteRepository;
        this.loteMapper = loteMapper;
        this.loteSearchRepository = loteSearchRepository;
    }

    /**
     * Save a lote.
     *
     * @param loteDTO the entity to save.
     * @return the persisted entity.
     */
    public LoteDTO save(LoteDTO loteDTO) {
        log.debug("Request to save Lote : {}", loteDTO);
        Lote lote = loteMapper.toEntity(loteDTO);
        lote = loteRepository.save(lote);
        LoteDTO result = loteMapper.toDto(lote);
        loteSearchRepository.save(lote);
        return result;
    }

    /**
     * Get all the lotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lotes");
        return loteRepository.findAll(pageable)
            .map(loteMapper::toDto);
    }


    /**
     * Get one lote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoteDTO> findOne(Long id) {
        log.debug("Request to get Lote : {}", id);
        return loteRepository.findById(id)
            .map(loteMapper::toDto);
    }

    /**
     * Delete the lote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lote : {}", id);
        loteRepository.deleteById(id);
        loteSearchRepository.deleteById(id);
    }

    /**
     * Search for the lote corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lotes for query {}", query);
        return loteSearchRepository.search(queryStringQuery(query), pageable)
            .map(loteMapper::toDto);
    }
}
