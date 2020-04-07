package br.com.basis.madre.service;

import br.com.basis.madre.domain.MotivoDoCadastro;
import br.com.basis.madre.repository.MotivoDoCadastroRepository;
import br.com.basis.madre.repository.search.MotivoDoCadastroSearchRepository;
import br.com.basis.madre.service.dto.MotivoDoCadastroDTO;
import br.com.basis.madre.service.mapper.MotivoDoCadastroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MotivoDoCadastro}.
 */
@Service
@Transactional
public class MotivoDoCadastroService {

    private final Logger log = LoggerFactory.getLogger(MotivoDoCadastroService.class);

    private final MotivoDoCadastroRepository motivoDoCadastroRepository;

    private final MotivoDoCadastroMapper motivoDoCadastroMapper;

    private final MotivoDoCadastroSearchRepository motivoDoCadastroSearchRepository;

    public MotivoDoCadastroService(MotivoDoCadastroRepository motivoDoCadastroRepository, MotivoDoCadastroMapper motivoDoCadastroMapper, MotivoDoCadastroSearchRepository motivoDoCadastroSearchRepository) {
        this.motivoDoCadastroRepository = motivoDoCadastroRepository;
        this.motivoDoCadastroMapper = motivoDoCadastroMapper;
        this.motivoDoCadastroSearchRepository = motivoDoCadastroSearchRepository;
    }

    /**
     * Save a motivoDoCadastro.
     *
     * @param motivoDoCadastroDTO the entity to save.
     * @return the persisted entity.
     */
    public MotivoDoCadastroDTO save(MotivoDoCadastroDTO motivoDoCadastroDTO) {
        log.debug("Request to save MotivoDoCadastro : {}", motivoDoCadastroDTO);
        MotivoDoCadastro motivoDoCadastro = motivoDoCadastroMapper.toEntity(motivoDoCadastroDTO);
        motivoDoCadastro = motivoDoCadastroRepository.save(motivoDoCadastro);
        MotivoDoCadastroDTO result = motivoDoCadastroMapper.toDto(motivoDoCadastro);
        motivoDoCadastroSearchRepository.save(motivoDoCadastro);
        return result;
    }

    /**
     * Get all the motivoDoCadastros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MotivoDoCadastroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MotivoDoCadastros");
        return motivoDoCadastroRepository.findAll(pageable)
            .map(motivoDoCadastroMapper::toDto);
    }

    /**
     * Get one motivoDoCadastro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MotivoDoCadastroDTO> findOne(Long id) {
        log.debug("Request to get MotivoDoCadastro : {}", id);
        return motivoDoCadastroRepository.findById(id)
            .map(motivoDoCadastroMapper::toDto);
    }

    /**
     * Delete the motivoDoCadastro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MotivoDoCadastro : {}", id);
        motivoDoCadastroRepository.deleteById(id);
        motivoDoCadastroSearchRepository.deleteById(id);
    }

    /**
     * Search for the motivoDoCadastro corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MotivoDoCadastroDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MotivoDoCadastros for query {}", query);
        return motivoDoCadastroSearchRepository.search(queryStringQuery(query), pageable)
            .map(motivoDoCadastroMapper::toDto);
    }
}
