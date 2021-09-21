package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.TipoDeMarcacao;
import br.com.basis.madre.madreexames.repository.TipoDeMarcacaoRepository;
import br.com.basis.madre.madreexames.repository.search.TipoDeMarcacaoSearchRepository;
import br.com.basis.madre.madreexames.service.dto.TipoDeMarcacaoDTO;
import br.com.basis.madre.madreexames.service.mapper.TipoDeMarcacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoDeMarcacao}.
 */
@Service
@Transactional
public class TipoDeMarcacaoService {

    private final Logger log = LoggerFactory.getLogger(TipoDeMarcacaoService.class);

    private final TipoDeMarcacaoRepository tipoDeMarcacaoRepository;

    private final TipoDeMarcacaoMapper tipoDeMarcacaoMapper;

    private final TipoDeMarcacaoSearchRepository tipoDeMarcacaoSearchRepository;

    public TipoDeMarcacaoService(TipoDeMarcacaoRepository tipoDeMarcacaoRepository, TipoDeMarcacaoMapper tipoDeMarcacaoMapper, TipoDeMarcacaoSearchRepository tipoDeMarcacaoSearchRepository) {
        this.tipoDeMarcacaoRepository = tipoDeMarcacaoRepository;
        this.tipoDeMarcacaoMapper = tipoDeMarcacaoMapper;
        this.tipoDeMarcacaoSearchRepository = tipoDeMarcacaoSearchRepository;
    }

    /**
     * Save a tipoDeMarcacao.
     *
     * @param tipoDeMarcacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoDeMarcacaoDTO save(TipoDeMarcacaoDTO tipoDeMarcacaoDTO) {
        log.debug("Request to save TipoDeMarcacao : {}", tipoDeMarcacaoDTO);
        TipoDeMarcacao tipoDeMarcacao = tipoDeMarcacaoMapper.toEntity(tipoDeMarcacaoDTO);
        tipoDeMarcacao = tipoDeMarcacaoRepository.save(tipoDeMarcacao);
        TipoDeMarcacaoDTO result = tipoDeMarcacaoMapper.toDto(tipoDeMarcacao);
        tipoDeMarcacaoSearchRepository.save(tipoDeMarcacao);
        return result;
    }

    /**
     * Get all the tipoDeMarcacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDeMarcacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDeMarcacaos");
        return tipoDeMarcacaoRepository.findAll(pageable)
            .map(tipoDeMarcacaoMapper::toDto);
    }


    /**
     * Get one tipoDeMarcacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoDeMarcacaoDTO> findOne(Long id) {
        log.debug("Request to get TipoDeMarcacao : {}", id);
        return tipoDeMarcacaoRepository.findById(id)
            .map(tipoDeMarcacaoMapper::toDto);
    }

    /**
     * Delete the tipoDeMarcacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoDeMarcacao : {}", id);
        tipoDeMarcacaoRepository.deleteById(id);
        tipoDeMarcacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDeMarcacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDeMarcacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDeMarcacaos for query {}", query);
        return tipoDeMarcacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoDeMarcacaoMapper::toDto);
    }
}
