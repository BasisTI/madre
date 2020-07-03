package br.com.basis.madre.service;

import br.com.basis.madre.domain.Documento;
import br.com.basis.madre.repository.DocumentoRepository;
import br.com.basis.madre.repository.search.DocumentoSearchRepository;
import br.com.basis.madre.service.dto.DocumentoDTO;
import br.com.basis.madre.service.mapper.DocumentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Documento}.
 */
@Service
@Transactional
public class DocumentoService {

    private final Logger log = LoggerFactory.getLogger(DocumentoService.class);

    private final DocumentoRepository documentoRepository;

    private final DocumentoMapper documentoMapper;

    private final DocumentoSearchRepository documentoSearchRepository;

    public DocumentoService(DocumentoRepository documentoRepository, DocumentoMapper documentoMapper, DocumentoSearchRepository documentoSearchRepository) {
        this.documentoRepository = documentoRepository;
        this.documentoMapper = documentoMapper;
        this.documentoSearchRepository = documentoSearchRepository;
    }

    /**
     * Save a documento.
     *
     * @param documentoDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentoDTO save(DocumentoDTO documentoDTO) {
        log.debug("Request to save Documento : {}", documentoDTO);
        Documento documento = documentoMapper.toEntity(documentoDTO);
        documento = documentoRepository.save(documento);
        DocumentoDTO result = documentoMapper.toDto(documento);
        documentoSearchRepository.save(documento);
        return result;
    }

    /**
     * Get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAll(pageable)
            .map(documentoMapper::toDto);
    }

    /**
     * Get one documento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentoDTO> findOne(Long id) {
        log.debug("Request to get Documento : {}", id);
        return documentoRepository.findById(id)
            .map(documentoMapper::toDto);
    }

    /**
     * Delete the documento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Documento : {}", id);
        documentoRepository.deleteById(id);
        documentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the documento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Documentos for query {}", query);
        return documentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentoMapper::toDto);
    }
}
