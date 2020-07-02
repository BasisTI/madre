package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.InformacaoTransferencia;
import br.com.basis.suprimentos.repository.InformacaoTransferenciaRepository;
import br.com.basis.suprimentos.repository.search.InformacaoTransferenciaSearchRepository;
import br.com.basis.suprimentos.service.dto.InformacaoTransferenciaDTO;
import br.com.basis.suprimentos.service.mapper.InformacaoTransferenciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link InformacaoTransferencia}.
 */
@Service
@Transactional
public class InformacaoTransferenciaService {

    private final Logger log = LoggerFactory.getLogger(InformacaoTransferenciaService.class);

    private final InformacaoTransferenciaRepository informacaoTransferenciaRepository;

    private final InformacaoTransferenciaMapper informacaoTransferenciaMapper;

    private final InformacaoTransferenciaSearchRepository informacaoTransferenciaSearchRepository;

    public InformacaoTransferenciaService(InformacaoTransferenciaRepository informacaoTransferenciaRepository, InformacaoTransferenciaMapper informacaoTransferenciaMapper, InformacaoTransferenciaSearchRepository informacaoTransferenciaSearchRepository) {
        this.informacaoTransferenciaRepository = informacaoTransferenciaRepository;
        this.informacaoTransferenciaMapper = informacaoTransferenciaMapper;
        this.informacaoTransferenciaSearchRepository = informacaoTransferenciaSearchRepository;
    }

    /**
     * Save a informacaoTransferencia.
     *
     * @param informacaoTransferenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public InformacaoTransferenciaDTO save(InformacaoTransferenciaDTO informacaoTransferenciaDTO) {
        log.debug("Request to save InformacaoTransferencia : {}", informacaoTransferenciaDTO);
        InformacaoTransferencia informacaoTransferencia = informacaoTransferenciaMapper.toEntity(informacaoTransferenciaDTO);
        informacaoTransferencia = informacaoTransferenciaRepository.save(informacaoTransferencia);
        InformacaoTransferenciaDTO result = informacaoTransferenciaMapper.toDto(informacaoTransferencia);
        informacaoTransferenciaSearchRepository.save(informacaoTransferencia);
        return result;
    }

    /**
     * Get all the informacaoTransferencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InformacaoTransferenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InformacaoTransferencias");
        return informacaoTransferenciaRepository.findAll(pageable)
            .map(informacaoTransferenciaMapper::toDto);
    }


    /**
     * Get one informacaoTransferencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InformacaoTransferenciaDTO> findOne(Long id) {
        log.debug("Request to get InformacaoTransferencia : {}", id);
        return informacaoTransferenciaRepository.findById(id)
            .map(informacaoTransferenciaMapper::toDto);
    }

    /**
     * Delete the informacaoTransferencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InformacaoTransferencia : {}", id);
        informacaoTransferenciaRepository.deleteById(id);
        informacaoTransferenciaSearchRepository.deleteById(id);
    }

    /**
     * Search for the informacaoTransferencia corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InformacaoTransferenciaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InformacaoTransferencias for query {}", query);
        return informacaoTransferenciaSearchRepository.search(queryStringQuery(query), pageable)
            .map(informacaoTransferenciaMapper::toDto);
    }
}
