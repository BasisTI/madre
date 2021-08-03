package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.TipoAmostra;
import br.com.basis.madre.madreexames.repository.TipoAmostraRepository;
import br.com.basis.madre.madreexames.repository.search.TipoAmostraSearchRepository;
import br.com.basis.madre.madreexames.service.dto.TipoAmostraDTO;
import br.com.basis.madre.madreexames.service.mapper.TipoAmostraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoAmostra}.
 */
@Service
@Transactional
public class TipoAmostraService {

    private final Logger log = LoggerFactory.getLogger(TipoAmostraService.class);

    private final TipoAmostraRepository tipoAmostraRepository;

    private final TipoAmostraMapper tipoAmostraMapper;

    private final TipoAmostraSearchRepository tipoAmostraSearchRepository;

    public TipoAmostraService(TipoAmostraRepository tipoAmostraRepository, TipoAmostraMapper tipoAmostraMapper, TipoAmostraSearchRepository tipoAmostraSearchRepository) {
        this.tipoAmostraRepository = tipoAmostraRepository;
        this.tipoAmostraMapper = tipoAmostraMapper;
        this.tipoAmostraSearchRepository = tipoAmostraSearchRepository;
    }

    /**
     * Save a tipoAmostra.
     *
     * @param tipoAmostraDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoAmostraDTO save(TipoAmostraDTO tipoAmostraDTO) {
        log.debug("Request to save TipoAmostra : {}", tipoAmostraDTO);
        TipoAmostra tipoAmostra = tipoAmostraMapper.toEntity(tipoAmostraDTO);
        tipoAmostra = tipoAmostraRepository.save(tipoAmostra);
        TipoAmostraDTO result = tipoAmostraMapper.toDto(tipoAmostra);
        tipoAmostraSearchRepository.save(tipoAmostra);
        return result;
    }

    /**
     * Get all the tipoAmostras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoAmostraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoAmostras");
        return tipoAmostraRepository.findAll(pageable)
            .map(tipoAmostraMapper::toDto);
    }


    /**
     * Get one tipoAmostra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoAmostraDTO> findOne(Long id) {
        log.debug("Request to get TipoAmostra : {}", id);
        return tipoAmostraRepository.findById(id)
            .map(tipoAmostraMapper::toDto);
    }

    /**
     * Delete the tipoAmostra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoAmostra : {}", id);
        tipoAmostraRepository.deleteById(id);
        tipoAmostraSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoAmostra corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoAmostraDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoAmostras for query {}", query);
        return tipoAmostraSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoAmostraMapper::toDto);
    }
}
