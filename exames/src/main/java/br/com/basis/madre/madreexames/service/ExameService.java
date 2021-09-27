package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Exame;
import br.com.basis.madre.madreexames.domain.Sinonimo;
import br.com.basis.madre.madreexames.repository.ExameRepository;
import br.com.basis.madre.madreexames.repository.search.ExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.ExameCompletoDTO;
import br.com.basis.madre.madreexames.service.dto.ExameDTO;
import br.com.basis.madre.madreexames.service.mapper.ExameCompletoMapper;
import br.com.basis.madre.madreexames.service.mapper.ExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Exame}.
 */
@Service
@Transactional
public class ExameService {

    private final Logger log = LoggerFactory.getLogger(ExameService.class);

    private final ExameRepository exameRepository;

    private final ExameMapper exameMapper;

    private final ExameSearchRepository exameSearchRepository;

    private final ExameCompletoMapper exameCompletoMapper;

    public ExameService(ExameRepository exameRepository, ExameMapper exameMapper, ExameSearchRepository exameSearchRepository, ExameCompletoMapper exameCompletoMapper) {
        this.exameRepository = exameRepository;
        this.exameMapper = exameMapper;
        this.exameSearchRepository = exameSearchRepository;
        this.exameCompletoMapper = exameCompletoMapper;
    }

    /**
     * Save a exame.
     *
     * @param exameCompletoDTO the entity to save.
     * @return the persisted entity.
     */
    public ExameCompletoDTO save(ExameCompletoDTO exameCompletoDTO) {
        log.debug("Request to save Exame : {}", exameCompletoDTO);
        Exame exame = exameCompletoMapper.toEntity(exameCompletoDTO);
        // exame.getSinonimos().stream().map(sinonimo -> sinonimo.setExame(exame));
        for(Sinonimo sinonimo : exame.getSinonimos()){
            sinonimo.setExame(exame);
        }
        exame = exameRepository.save(exame);
        ExameCompletoDTO result = exameCompletoMapper.toDto(exame);
        exameSearchRepository.save(exame);
        return result;
    }

    /**
     * Get all the exames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Exames");
        return exameRepository.findAll(pageable)
            .map(exameMapper::toDto);
    }


    /**
     * Get one exame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExameDTO> findOne(Long id) {
        log.debug("Request to get Exame : {}", id);
        return exameRepository.findById(id)
            .map(exameMapper::toDto);
    }

    /**
     * Delete the exame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Exame : {}", id);
        exameRepository.deleteById(id);
        exameSearchRepository.deleteById(id);
    }

    /**
     * Search for the exame corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Exames for query {}", query);
        return exameSearchRepository.search(queryStringQuery(query), pageable)
            .map(exameMapper::toDto);
    }
}
