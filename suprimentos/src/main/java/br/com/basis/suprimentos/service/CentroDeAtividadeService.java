package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.CentroDeAtividade;
import br.com.basis.suprimentos.repository.CentroDeAtividadeRepository;
import br.com.basis.suprimentos.repository.search.CentroDeAtividadeSearchRepository;
import br.com.basis.suprimentos.service.dto.CentroDeAtividadeDTO;
import br.com.basis.suprimentos.service.mapper.CentroDeAtividadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CentroDeAtividade}.
 */
@Service
@Transactional
public class CentroDeAtividadeService {

    private final Logger log = LoggerFactory.getLogger(CentroDeAtividadeService.class);

    private final CentroDeAtividadeRepository centroDeAtividadeRepository;

    private final CentroDeAtividadeMapper centroDeAtividadeMapper;

    private final CentroDeAtividadeSearchRepository centroDeAtividadeSearchRepository;

    public CentroDeAtividadeService(CentroDeAtividadeRepository centroDeAtividadeRepository, CentroDeAtividadeMapper centroDeAtividadeMapper, CentroDeAtividadeSearchRepository centroDeAtividadeSearchRepository) {
        this.centroDeAtividadeRepository = centroDeAtividadeRepository;
        this.centroDeAtividadeMapper = centroDeAtividadeMapper;
        this.centroDeAtividadeSearchRepository = centroDeAtividadeSearchRepository;
    }

    /**
     * Save a centroDeAtividade.
     *
     * @param centroDeAtividadeDTO the entity to save.
     * @return the persisted entity.
     */
    public CentroDeAtividadeDTO save(CentroDeAtividadeDTO centroDeAtividadeDTO) {
        log.debug("Request to save CentroDeAtividade : {}", centroDeAtividadeDTO);
        CentroDeAtividade centroDeAtividade = centroDeAtividadeMapper.toEntity(centroDeAtividadeDTO);
        centroDeAtividade = centroDeAtividadeRepository.save(centroDeAtividade);
        CentroDeAtividadeDTO result = centroDeAtividadeMapper.toDto(centroDeAtividade);
        centroDeAtividadeSearchRepository.save(centroDeAtividade);
        return result;
    }

    /**
     * Get all the centroDeAtividades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CentroDeAtividadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentroDeAtividades");
        return centroDeAtividadeRepository.findAll(pageable)
            .map(centroDeAtividadeMapper::toDto);
    }


    /**
     * Get one centroDeAtividade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CentroDeAtividadeDTO> findOne(Long id) {
        log.debug("Request to get CentroDeAtividade : {}", id);
        return centroDeAtividadeRepository.findById(id)
            .map(centroDeAtividadeMapper::toDto);
    }

    /**
     * Delete the centroDeAtividade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CentroDeAtividade : {}", id);
        centroDeAtividadeRepository.deleteById(id);
        centroDeAtividadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the centroDeAtividade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CentroDeAtividadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CentroDeAtividades for query {}", query);
        return centroDeAtividadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(centroDeAtividadeMapper::toDto);
    }
}
