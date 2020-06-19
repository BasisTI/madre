package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Sazonalidade;
import br.com.basis.suprimentos.repository.SazonalidadeRepository;
import br.com.basis.suprimentos.repository.search.SazonalidadeSearchRepository;
import br.com.basis.suprimentos.service.dto.SazonalidadeDTO;
import br.com.basis.suprimentos.service.mapper.SazonalidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Sazonalidade}.
 */
@Service
@Transactional
public class SazonalidadeService {

    private final Logger log = LoggerFactory.getLogger(SazonalidadeService.class);

    private final SazonalidadeRepository sazonalidadeRepository;

    private final SazonalidadeMapper sazonalidadeMapper;

    private final SazonalidadeSearchRepository sazonalidadeSearchRepository;

    public SazonalidadeService(SazonalidadeRepository sazonalidadeRepository, SazonalidadeMapper sazonalidadeMapper, SazonalidadeSearchRepository sazonalidadeSearchRepository) {
        this.sazonalidadeRepository = sazonalidadeRepository;
        this.sazonalidadeMapper = sazonalidadeMapper;
        this.sazonalidadeSearchRepository = sazonalidadeSearchRepository;
    }

    /**
     * Save a sazonalidade.
     *
     * @param sazonalidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public SazonalidadeDTO save(SazonalidadeDTO sazonalidadeDTO) {
        log.debug("Request to save Sazonalidade : {}", sazonalidadeDTO);
        Sazonalidade sazonalidade = sazonalidadeMapper.toEntity(sazonalidadeDTO);
        sazonalidade = sazonalidadeRepository.save(sazonalidade);
        SazonalidadeDTO result = sazonalidadeMapper.toDto(sazonalidade);
        sazonalidadeSearchRepository.save(sazonalidade);
        return result;
    }

    /**
     * Get all the sazonalidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SazonalidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sazonalidades");
        return sazonalidadeRepository.findAll(pageable)
            .map(sazonalidadeMapper::toDto);
    }


    /**
     * Get one sazonalidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SazonalidadeDTO> findOne(Long id) {
        log.debug("Request to get Sazonalidade : {}", id);
        return sazonalidadeRepository.findById(id)
            .map(sazonalidadeMapper::toDto);
    }

    /**
     * Delete the sazonalidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sazonalidade : {}", id);
        sazonalidadeRepository.deleteById(id);
        sazonalidadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the sazonalidade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SazonalidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sazonalidades for query {}", query);
        return sazonalidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(sazonalidadeMapper::toDto);
    }
}
