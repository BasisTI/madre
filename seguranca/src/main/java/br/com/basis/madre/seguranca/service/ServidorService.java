package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.Servidor;
import br.com.basis.madre.seguranca.repository.ServidorRepository;
import br.com.basis.madre.seguranca.repository.search.ServidorSearchRepository;
import br.com.basis.madre.seguranca.service.dto.ServidorDTO;
import br.com.basis.madre.seguranca.service.mapper.ServidorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Servidor}.
 */
@Service
@Transactional
public class ServidorService {

    private final Logger log = LoggerFactory.getLogger(ServidorService.class);

    private final ServidorRepository servidorRepository;

    private final ServidorMapper servidorMapper;

    private final ServidorSearchRepository servidorSearchRepository;

    public ServidorService(ServidorRepository servidorRepository, ServidorMapper servidorMapper, ServidorSearchRepository servidorSearchRepository) {
        this.servidorRepository = servidorRepository;
        this.servidorMapper = servidorMapper;
        this.servidorSearchRepository = servidorSearchRepository;
    }

    /**
     * Save a servidor.
     *
     * @param servidorDTO the entity to save.
     * @return the persisted entity.
     */
    public ServidorDTO save(ServidorDTO servidorDTO) {
        log.debug("Request to save Servidor : {}", servidorDTO);
        Servidor servidor = servidorMapper.toEntity(servidorDTO);
        servidor = servidorRepository.save(servidor);
        ServidorDTO result = servidorMapper.toDto(servidor);
        servidorSearchRepository.save(servidor);
        return result;
    }

    /**
     * Get all the servidors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServidorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Servidors");
        return servidorRepository.findAll(pageable)
            .map(servidorMapper::toDto);
    }


    /**
     * Get one servidor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServidorDTO> findOne(Long id) {
        log.debug("Request to get Servidor : {}", id);
        return servidorRepository.findById(id)
            .map(servidorMapper::toDto);
    }

    /**
     * Delete the servidor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Servidor : {}", id);
        servidorRepository.deleteById(id);
        servidorSearchRepository.deleteById(id);
    }

    /**
     * Search for the servidor corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServidorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Servidors for query {}", query);
        return servidorSearchRepository.search(queryStringQuery(query), pageable)
            .map(servidorMapper::toDto);
    }
}
