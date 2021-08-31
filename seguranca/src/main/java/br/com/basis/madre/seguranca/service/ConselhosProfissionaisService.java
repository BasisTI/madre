package br.com.basis.madre.seguranca.service;

import br.com.basis.madre.seguranca.domain.ConselhosProfissionais;
import br.com.basis.madre.seguranca.repository.ConselhosProfissionaisRepository;
import br.com.basis.madre.seguranca.repository.search.ConselhosProfissionaisSearchRepository;
import br.com.basis.madre.seguranca.service.dto.ConselhosProfissionaisDTO;
import br.com.basis.madre.seguranca.service.mapper.ConselhosProfissionaisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ConselhosProfissionais}.
 */
@Service
@Transactional
public class ConselhosProfissionaisService {

    private final Logger log = LoggerFactory.getLogger(ConselhosProfissionaisService.class);

    private final ConselhosProfissionaisRepository conselhosProfissionaisRepository;

    private final ConselhosProfissionaisMapper conselhosProfissionaisMapper;

    private final ConselhosProfissionaisSearchRepository conselhosProfissionaisSearchRepository;

    public ConselhosProfissionaisService(ConselhosProfissionaisRepository conselhosProfissionaisRepository, ConselhosProfissionaisMapper conselhosProfissionaisMapper, ConselhosProfissionaisSearchRepository conselhosProfissionaisSearchRepository) {
        this.conselhosProfissionaisRepository = conselhosProfissionaisRepository;
        this.conselhosProfissionaisMapper = conselhosProfissionaisMapper;
        this.conselhosProfissionaisSearchRepository = conselhosProfissionaisSearchRepository;
    }

    /**
     * Save a conselhosProfissionais.
     *
     * @param conselhosProfissionaisDTO the entity to save.
     * @return the persisted entity.
     */
    public ConselhosProfissionaisDTO save(ConselhosProfissionaisDTO conselhosProfissionaisDTO) {
        log.debug("Request to save ConselhosProfissionais : {}", conselhosProfissionaisDTO);
        ConselhosProfissionais conselhosProfissionais = conselhosProfissionaisMapper.toEntity(conselhosProfissionaisDTO);
        conselhosProfissionais = conselhosProfissionaisRepository.save(conselhosProfissionais);
        ConselhosProfissionaisDTO result = conselhosProfissionaisMapper.toDto(conselhosProfissionais);
        conselhosProfissionaisSearchRepository.save(conselhosProfissionais);
        return result;
    }

    /**
     * Get all the conselhosProfissionais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConselhosProfissionaisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConselhosProfissionais");
        return conselhosProfissionaisRepository.findAll(pageable)
            .map(conselhosProfissionaisMapper::toDto);
    }


    /**
     * Get one conselhosProfissionais by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConselhosProfissionaisDTO> findOne(Long id) {
        log.debug("Request to get ConselhosProfissionais : {}", id);
        return conselhosProfissionaisRepository.findById(id)
            .map(conselhosProfissionaisMapper::toDto);
    }

    /**
     * Delete the conselhosProfissionais by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConselhosProfissionais : {}", id);
        conselhosProfissionaisRepository.deleteById(id);
        conselhosProfissionaisSearchRepository.deleteById(id);
    }

    /**
     * Search for the conselhosProfissionais corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConselhosProfissionaisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConselhosProfissionais for query {}", query);
        return conselhosProfissionaisSearchRepository.search(queryStringQuery(query), pageable)
            .map(conselhosProfissionaisMapper::toDto);
    }
}
