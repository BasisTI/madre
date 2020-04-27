package br.com.basis.madre.service;

import br.com.basis.madre.domain.ConvenioDeSaude;
import br.com.basis.madre.repository.ConvenioDeSaudeRepository;
import br.com.basis.madre.repository.search.ConvenioDeSaudeSearchRepository;
import br.com.basis.madre.service.dto.ConvenioDeSaudeDTO;
import br.com.basis.madre.service.mapper.ConvenioDeSaudeMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ConvenioDeSaude}.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ConvenioDeSaudeService {
    private final Logger log = LoggerFactory.getLogger(ConvenioDeSaudeService.class);
    private final ConvenioDeSaudeRepository convenioDeSaudeRepository;
    private final ConvenioDeSaudeMapper convenioDeSaudeMapper;
    private final ConvenioDeSaudeSearchRepository convenioDeSaudeSearchRepository;

    /**
     * Save a convenioDeSaude.
     *
     * @param convenioDeSaudeDTO the entity to save.
     * @return the persisted entity.
     */
    public ConvenioDeSaudeDTO save(ConvenioDeSaudeDTO convenioDeSaudeDTO) {
        log.debug("Request to save ConvenioDeSaude : {}", convenioDeSaudeDTO);
        ConvenioDeSaude convenioDeSaude = convenioDeSaudeMapper.toEntity(convenioDeSaudeDTO);
        convenioDeSaude = convenioDeSaudeRepository.save(convenioDeSaude);
        ConvenioDeSaudeDTO result = convenioDeSaudeMapper.toDto(convenioDeSaude);
        convenioDeSaudeSearchRepository.save(convenioDeSaude);
        return result;
    }

    /**
     * Get all the convenioDeSaudes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConvenioDeSaudeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConvenioDeSaudes");
        return convenioDeSaudeRepository.findAll(pageable)
            .map(convenioDeSaudeMapper::toDto);
    }


    /**
     * Get one convenioDeSaude by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConvenioDeSaudeDTO> findOne(Long id) {
        log.debug("Request to get ConvenioDeSaude : {}", id);
        return convenioDeSaudeRepository.findById(id)
            .map(convenioDeSaudeMapper::toDto);
    }

    /**
     * Delete the convenioDeSaude by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConvenioDeSaude : {}", id);
        convenioDeSaudeRepository.deleteById(id);
        convenioDeSaudeSearchRepository.deleteById(id);
    }

    /**
     * Search for the convenioDeSaude corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConvenioDeSaudeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConvenioDeSaudes for query {}", query);
        return convenioDeSaudeSearchRepository.search(queryStringQuery(query), pageable)
            .map(convenioDeSaudeMapper::toDto);
    }
}
