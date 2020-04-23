package br.com.basis.madre.service;

import br.com.basis.madre.domain.Telefone;
import br.com.basis.madre.repository.TelefoneRepository;
import br.com.basis.madre.repository.search.TelefoneSearchRepository;
import br.com.basis.madre.service.dto.TelefoneDTO;
import br.com.basis.madre.service.mapper.TelefoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Telefone}.
 */
@Service
@Transactional
public class TelefoneService {

    private final Logger log = LoggerFactory.getLogger(TelefoneService.class);

    private final TelefoneRepository telefoneRepository;

    private final TelefoneMapper telefoneMapper;

    private final TelefoneSearchRepository telefoneSearchRepository;

    public TelefoneService(TelefoneRepository telefoneRepository, TelefoneMapper telefoneMapper, TelefoneSearchRepository telefoneSearchRepository) {
        this.telefoneRepository = telefoneRepository;
        this.telefoneMapper = telefoneMapper;
        this.telefoneSearchRepository = telefoneSearchRepository;
    }

    /**
     * Save a telefone.
     *
     * @param telefoneDTO the entity to save.
     * @return the persisted entity.
     */
    public TelefoneDTO save(TelefoneDTO telefoneDTO) {
        log.debug("Request to save Telefone : {}", telefoneDTO);
        Telefone telefone = telefoneMapper.toEntity(telefoneDTO);
        telefone = telefoneRepository.save(telefone);
        TelefoneDTO result = telefoneMapper.toDto(telefone);
        telefoneSearchRepository.save(telefone);
        return result;
    }

    /**
     * Get all the telefones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TelefoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Telefones");
        return telefoneRepository.findAll(pageable)
            .map(telefoneMapper::toDto);
    }

    /**
     * Get one telefone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TelefoneDTO> findOne(Long id) {
        log.debug("Request to get Telefone : {}", id);
        return telefoneRepository.findById(id)
            .map(telefoneMapper::toDto);
    }

    /**
     * Delete the telefone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Telefone : {}", id);
        telefoneRepository.deleteById(id);
        telefoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the telefone corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TelefoneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Telefones for query {}", query);
        return telefoneSearchRepository.search(queryStringQuery(query), pageable)
            .map(telefoneMapper::toDto);
    }
}
