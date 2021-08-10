package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.Exame;
import br.com.basis.madre.madreexames.repository.ExameRepository;
import br.com.basis.madre.madreexames.repository.search.ExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.ExameDTO;
import br.com.basis.madre.madreexames.service.mapper.ExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

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

    public ExameService(ExameRepository exameRepository, ExameMapper exameMapper, ExameSearchRepository exameSearchRepository) {
        this.exameRepository = exameRepository;
        this.exameMapper = exameMapper;
        this.exameSearchRepository = exameSearchRepository;
    }

    /**
     * Save a exame.
     *
     * @param exameDTO the entity to save.
     * @return the persisted entity.
     */
    public ExameDTO save(ExameDTO exameDTO) {
        log.debug("Request to save Exame : {}", exameDTO);
        Exame exame = exameMapper.toEntity(exameDTO);
        exame = exameRepository.save(exame);
        ExameDTO result = exameMapper.toDto(exame);
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

    public List<ExameDTO> buscaExamesPorGrupo(Long id) {
        return exameRepository.findByGrupoAgendamentoExamesId(id).stream()
            .map(exameMapper::toDto).collect(Collectors.toList());
    }
}
