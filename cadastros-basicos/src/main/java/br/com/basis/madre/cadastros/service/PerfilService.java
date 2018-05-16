package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.repository.PerfilRepository;
import br.com.basis.madre.cadastros.repository.search.PerfilSearchRepository;
import br.com.basis.madre.cadastros.service.dto.PerfilDTO;
import br.com.basis.madre.cadastros.service.mapper.PerfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Perfil.
 */
@Service
@Transactional
public class PerfilService {

    private final Logger log = LoggerFactory.getLogger(PerfilService.class);

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    private final PerfilSearchRepository perfilSearchRepository;

    public PerfilService(PerfilRepository perfilRepository, PerfilMapper perfilMapper, PerfilSearchRepository perfilSearchRepository) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
        this.perfilSearchRepository = perfilSearchRepository;
    }

    /**
     * Save a perfil.
     *
     * @param perfilDTO the entity to save
     * @return the persisted entity
     */
    public PerfilDTO save(PerfilDTO perfilDTO) {
        log.debug("Request to save Perfil : {}", perfilDTO);
        Perfil perfil = perfilMapper.toEntity(perfilDTO);
        perfil = perfilRepository.save(perfil);
        PerfilDTO result = perfilMapper.toDto(perfil);
        perfilSearchRepository.save(perfil);
        return result;
    }

    /**
     * Get all the perfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PerfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Perfils");
        return perfilRepository.findAll(pageable)
            .map(perfilMapper::toDto);
    }

    /**
     * Get one perfil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PerfilDTO findOne(Long id) {
        log.debug("Request to get Perfil : {}", id);
        Perfil perfil = perfilRepository.findOne(id);
        return perfilMapper.toDto(perfil);
    }

    /**
     * Delete the perfil by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Perfil : {}", id);
        perfilRepository.delete(id);
        perfilSearchRepository.delete(id);
    }

    /**
     * Search for the perfil corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PerfilDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Perfils for query {}", query);
        Page<Perfil> result = perfilSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(perfilMapper::toDto);
    }
}
