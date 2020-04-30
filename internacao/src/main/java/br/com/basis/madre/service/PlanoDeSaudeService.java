package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.PlanoDeSaude;
import br.com.basis.madre.repository.PlanoDeSaudeRepository;
import br.com.basis.madre.repository.search.PlanoDeSaudeSearchRepository;
import br.com.basis.madre.service.dto.PlanoDeSaudeDTO;
import br.com.basis.madre.service.mapper.PlanoDeSaudeMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PlanoDeSaudeService {

    private final Logger log = LoggerFactory.getLogger(PlanoDeSaudeService.class);

    private final PlanoDeSaudeRepository planoDeSaudeRepository;

    private final PlanoDeSaudeMapper planoDeSaudeMapper;

    private final PlanoDeSaudeSearchRepository planoDeSaudeSearchRepository;

    /**
     * Save a planoDeSaude.
     *
     * @param planoDeSaudeDTO the entity to save.
     * @return the persisted entity.
     */
    public PlanoDeSaudeDTO save(PlanoDeSaudeDTO planoDeSaudeDTO) {
        log.debug("Request to save PlanoDeSaude : {}", planoDeSaudeDTO);
        PlanoDeSaude planoDeSaude = planoDeSaudeMapper.toEntity(planoDeSaudeDTO);
        planoDeSaude = planoDeSaudeRepository.save(planoDeSaude);
        PlanoDeSaudeDTO result = planoDeSaudeMapper.toDto(planoDeSaude);
        planoDeSaudeSearchRepository.save(planoDeSaude);
        return result;
    }

    /**
     * Get all the planoDeSaudes.
     *
     * @param planoDeSaudeDTO
     * @param pageable        the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoDeSaudeDTO> findAll(
        PlanoDeSaudeDTO planoDeSaudeDTO, Pageable pageable) {
        log.debug("Request to get all PlanoDeSaudes");
        return planoDeSaudeRepository.findAll(
            Example.of(planoDeSaudeMapper.toEntity(planoDeSaudeDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable)
            .map(planoDeSaudeMapper::toDto);
    }


    /**
     * Get one planoDeSaude by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoDeSaudeDTO> findOne(Long id) {
        log.debug("Request to get PlanoDeSaude : {}", id);
        return planoDeSaudeRepository.findById(id)
            .map(planoDeSaudeMapper::toDto);
    }

    /**
     * Delete the planoDeSaude by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanoDeSaude : {}", id);
        planoDeSaudeRepository.deleteById(id);
        planoDeSaudeSearchRepository.deleteById(id);
    }

    /**
     * Search for the planoDeSaude corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoDeSaudeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PlanoDeSaudes for query {}", query);
        return planoDeSaudeSearchRepository.search(queryStringQuery(query), pageable)
            .map(planoDeSaudeMapper::toDto);
    }

}
