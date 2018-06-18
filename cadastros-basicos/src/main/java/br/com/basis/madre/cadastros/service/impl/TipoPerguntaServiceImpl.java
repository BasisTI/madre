package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.service.TipoPerguntaService;
import br.com.basis.madre.cadastros.domain.TipoPergunta;
import br.com.basis.madre.cadastros.repository.TipoPerguntaRepository;
import br.com.basis.madre.cadastros.repository.search.TipoPerguntaSearchRepository;
import br.com.basis.madre.cadastros.service.dto.TipoPerguntaDTO;
import br.com.basis.madre.cadastros.service.mapper.TipoPerguntaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoPergunta.
 */
@Service
@Transactional
public class TipoPerguntaServiceImpl implements TipoPerguntaService {

    private final Logger log = LoggerFactory.getLogger(TipoPerguntaServiceImpl.class);

    private final TipoPerguntaRepository tipoPerguntaRepository;

    private final TipoPerguntaMapper tipoPerguntaMapper;

    private final TipoPerguntaSearchRepository tipoPerguntaSearchRepository;

    public TipoPerguntaServiceImpl(TipoPerguntaRepository tipoPerguntaRepository, TipoPerguntaMapper tipoPerguntaMapper, TipoPerguntaSearchRepository tipoPerguntaSearchRepository) {
        this.tipoPerguntaRepository = tipoPerguntaRepository;
        this.tipoPerguntaMapper = tipoPerguntaMapper;
        this.tipoPerguntaSearchRepository = tipoPerguntaSearchRepository;
    }

    /**
     * Save a tipoPergunta.
     *
     * @param tipoPerguntaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoPerguntaDTO save(TipoPerguntaDTO tipoPerguntaDTO) {
        log.debug("Request to save TipoPergunta : {}", tipoPerguntaDTO);
        TipoPergunta tipoPergunta = tipoPerguntaMapper.toEntity(tipoPerguntaDTO);
        tipoPergunta = tipoPerguntaRepository.save(tipoPergunta);
        TipoPerguntaDTO result = tipoPerguntaMapper.toDto(tipoPergunta);
        tipoPerguntaSearchRepository.save(tipoPergunta);
        return result;
    }

    /**
     * Get all the tipoPerguntas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoPerguntaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoPerguntas");
        return tipoPerguntaRepository.findAll(pageable)
            .map(tipoPerguntaMapper::toDto);
    }

    /**
     * Get one tipoPergunta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoPerguntaDTO findOne(Long id) {
        log.debug("Request to get TipoPergunta : {}", id);
        TipoPergunta tipoPergunta = tipoPerguntaRepository.findOne(id);
        return tipoPerguntaMapper.toDto(tipoPergunta);
    }

    /**
     * Delete the tipoPergunta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPergunta : {}", id);
        tipoPerguntaRepository.delete(id);
        tipoPerguntaSearchRepository.delete(id);
    }

    /**
     * Search for the tipoPergunta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoPerguntaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoPerguntas for query {}", query);
        Page<TipoPergunta> result = tipoPerguntaSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(tipoPerguntaMapper::toDto);
    }
}
