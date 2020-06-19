package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.DocumentoFiscalEntrada;
import br.com.basis.suprimentos.repository.DocumentoFiscalEntradaRepository;
import br.com.basis.suprimentos.repository.search.DocumentoFiscalEntradaSearchRepository;
import br.com.basis.suprimentos.service.dto.DocumentoFiscalEntradaDTO;
import br.com.basis.suprimentos.service.mapper.DocumentoFiscalEntradaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link DocumentoFiscalEntrada}.
 */
@Service
@Transactional
public class DocumentoFiscalEntradaService {

    private final Logger log = LoggerFactory.getLogger(DocumentoFiscalEntradaService.class);

    private final DocumentoFiscalEntradaRepository documentoFiscalEntradaRepository;

    private final DocumentoFiscalEntradaMapper documentoFiscalEntradaMapper;

    private final DocumentoFiscalEntradaSearchRepository documentoFiscalEntradaSearchRepository;

    public DocumentoFiscalEntradaService(DocumentoFiscalEntradaRepository documentoFiscalEntradaRepository, DocumentoFiscalEntradaMapper documentoFiscalEntradaMapper, DocumentoFiscalEntradaSearchRepository documentoFiscalEntradaSearchRepository) {
        this.documentoFiscalEntradaRepository = documentoFiscalEntradaRepository;
        this.documentoFiscalEntradaMapper = documentoFiscalEntradaMapper;
        this.documentoFiscalEntradaSearchRepository = documentoFiscalEntradaSearchRepository;
    }

    /**
     * Save a documentoFiscalEntrada.
     *
     * @param documentoFiscalEntradaDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentoFiscalEntradaDTO save(DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO) {
        log.debug("Request to save DocumentoFiscalEntrada : {}", documentoFiscalEntradaDTO);
        DocumentoFiscalEntrada documentoFiscalEntrada = documentoFiscalEntradaMapper.toEntity(documentoFiscalEntradaDTO);
        documentoFiscalEntrada = documentoFiscalEntradaRepository.save(documentoFiscalEntrada);
        DocumentoFiscalEntradaDTO result = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);
        documentoFiscalEntradaSearchRepository.save(documentoFiscalEntrada);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<DocumentoFiscalEntradaDTO> findAll(Pageable pageable, DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO) {
        log.debug("Request to get all DocumentoFiscalEntradas");
        return documentoFiscalEntradaRepository.findAll(Example.of(documentoFiscalEntradaMapper.toEntity(documentoFiscalEntradaDTO), ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)), pageable)
                .map(documentoFiscalEntradaMapper::toDto);
    }

    /**
     * Get one documentoFiscalEntrada by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentoFiscalEntradaDTO> findOne(Long id) {
        log.debug("Request to get DocumentoFiscalEntrada : {}", id);
        return documentoFiscalEntradaRepository.findById(id)
                .map(documentoFiscalEntradaMapper::toDto);
    }

    /**
     * Delete the documentoFiscalEntrada by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentoFiscalEntrada : {}", id);
        documentoFiscalEntradaRepository.deleteById(id);
        documentoFiscalEntradaSearchRepository.deleteById(id);
    }

    /**
     * Search for the documentoFiscalEntrada corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoFiscalEntradaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentoFiscalEntradas for query {}", query);
        return documentoFiscalEntradaSearchRepository.search(queryStringQuery(query), pageable)
                .map(documentoFiscalEntradaMapper::toDto);
    }
}
