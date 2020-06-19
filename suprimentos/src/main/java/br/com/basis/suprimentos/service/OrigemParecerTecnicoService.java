package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.OrigemParecerTecnico;
import br.com.basis.suprimentos.repository.OrigemParecerTecnicoRepository;
import br.com.basis.suprimentos.repository.search.OrigemParecerTecnicoSearchRepository;
import br.com.basis.suprimentos.service.dto.OrigemParecerTecnicoDTO;
import br.com.basis.suprimentos.service.mapper.OrigemParecerTecnicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link OrigemParecerTecnico}.
 */
@Service
@Transactional
public class OrigemParecerTecnicoService {

    private final Logger log = LoggerFactory.getLogger(OrigemParecerTecnicoService.class);

    private final OrigemParecerTecnicoRepository origemParecerTecnicoRepository;

    private final OrigemParecerTecnicoMapper origemParecerTecnicoMapper;

    private final OrigemParecerTecnicoSearchRepository origemParecerTecnicoSearchRepository;

    public OrigemParecerTecnicoService(OrigemParecerTecnicoRepository origemParecerTecnicoRepository, OrigemParecerTecnicoMapper origemParecerTecnicoMapper, OrigemParecerTecnicoSearchRepository origemParecerTecnicoSearchRepository) {
        this.origemParecerTecnicoRepository = origemParecerTecnicoRepository;
        this.origemParecerTecnicoMapper = origemParecerTecnicoMapper;
        this.origemParecerTecnicoSearchRepository = origemParecerTecnicoSearchRepository;
    }

    /**
     * Save a origemParecerTecnico.
     *
     * @param origemParecerTecnicoDTO the entity to save.
     * @return the persisted entity.
     */
    public OrigemParecerTecnicoDTO save(OrigemParecerTecnicoDTO origemParecerTecnicoDTO) {
        log.debug("Request to save OrigemParecerTecnico : {}", origemParecerTecnicoDTO);
        OrigemParecerTecnico origemParecerTecnico = origemParecerTecnicoMapper.toEntity(origemParecerTecnicoDTO);
        origemParecerTecnico = origemParecerTecnicoRepository.save(origemParecerTecnico);
        OrigemParecerTecnicoDTO result = origemParecerTecnicoMapper.toDto(origemParecerTecnico);
        origemParecerTecnicoSearchRepository.save(origemParecerTecnico);
        return result;
    }

    /**
     * Get all the origemParecerTecnicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrigemParecerTecnicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrigemParecerTecnicos");
        return origemParecerTecnicoRepository.findAll(pageable)
            .map(origemParecerTecnicoMapper::toDto);
    }


    /**
     * Get one origemParecerTecnico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrigemParecerTecnicoDTO> findOne(Long id) {
        log.debug("Request to get OrigemParecerTecnico : {}", id);
        return origemParecerTecnicoRepository.findById(id)
            .map(origemParecerTecnicoMapper::toDto);
    }

    /**
     * Delete the origemParecerTecnico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrigemParecerTecnico : {}", id);
        origemParecerTecnicoRepository.deleteById(id);
        origemParecerTecnicoSearchRepository.deleteById(id);
    }

    /**
     * Search for the origemParecerTecnico corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrigemParecerTecnicoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrigemParecerTecnicos for query {}", query);
        return origemParecerTecnicoSearchRepository.search(queryStringQuery(query), pageable)
            .map(origemParecerTecnicoMapper::toDto);
    }
}
