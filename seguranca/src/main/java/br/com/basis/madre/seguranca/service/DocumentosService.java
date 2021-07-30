package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.Documentos;
import br.com.basis.madre.seguranca.repository.DocumentosRepository;
import br.com.basis.madre.seguranca.repository.search.DocumentosSearchRepository;
import br.com.basis.madre.seguranca.service.dto.DocumentosDTO;
import br.com.basis.madre.seguranca.service.mapper.DocumentosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Documentos}.
 */
@Service
@Transactional
public class DocumentosService {

    private final Logger log = LoggerFactory.getLogger(DocumentosService.class);

    private final DocumentosRepository documentosRepository;

    private final DocumentosMapper documentosMapper;

    private final DocumentosSearchRepository documentosSearchRepository;

    public DocumentosService(DocumentosRepository documentosRepository, DocumentosMapper documentosMapper, DocumentosSearchRepository documentosSearchRepository) {
        this.documentosRepository = documentosRepository;
        this.documentosMapper = documentosMapper;
        this.documentosSearchRepository = documentosSearchRepository;
    }

    /**
     * Save a documentos.
     *
     * @param documentosDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentosDTO save(DocumentosDTO documentosDTO) {
        log.debug("Request to save Documentos : {}", documentosDTO);
        Documentos documentos = documentosMapper.toEntity(documentosDTO);
        documentos = documentosRepository.save(documentos);
        DocumentosDTO result = documentosMapper.toDto(documentos);
        documentosSearchRepository.save(documentos);
        return result;
    }

    /**
     * Get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Documentos");
        return documentosRepository.findAll(pageable)
            .map(documentosMapper::toDto);
    }


    /**
     * Get one documentos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentosDTO> findOne(Long id) {
        log.debug("Request to get Documentos : {}", id);
        return documentosRepository.findById(id)
            .map(documentosMapper::toDto);
    }

    /**
     * Delete the documentos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Documentos : {}", id);
        documentosRepository.deleteById(id);
        documentosSearchRepository.deleteById(id);
    }

    /**
     * Search for the documentos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Documentos for query {}", query);
        return documentosSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentosMapper::toDto);
    }
}
