package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.ProjetoDePesquisa;
import br.com.basis.madre.madreexames.repository.ProjetoDePesquisaRepository;
import br.com.basis.madre.madreexames.repository.search.ProjetoDePesquisaSearchRepository;
import br.com.basis.madre.madreexames.service.dto.ProjetoDePesquisaDTO;
import br.com.basis.madre.madreexames.service.mapper.ProjetoDePesquisaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProjetoDePesquisa}.
 */
@Service
@Transactional
public class ProjetoDePesquisaService {

    private final Logger log = LoggerFactory.getLogger(ProjetoDePesquisaService.class);

    private final ProjetoDePesquisaRepository projetoDePesquisaRepository;

    private final ProjetoDePesquisaMapper projetoDePesquisaMapper;

    private final ProjetoDePesquisaSearchRepository projetoDePesquisaSearchRepository;

    public ProjetoDePesquisaService(ProjetoDePesquisaRepository projetoDePesquisaRepository, ProjetoDePesquisaMapper projetoDePesquisaMapper, ProjetoDePesquisaSearchRepository projetoDePesquisaSearchRepository) {
        this.projetoDePesquisaRepository = projetoDePesquisaRepository;
        this.projetoDePesquisaMapper = projetoDePesquisaMapper;
        this.projetoDePesquisaSearchRepository = projetoDePesquisaSearchRepository;
    }

    /**
     * Save a projetoDePesquisa.
     *
     * @param projetoDePesquisaDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjetoDePesquisaDTO save(ProjetoDePesquisaDTO projetoDePesquisaDTO) {
        log.debug("Request to save ProjetoDePesquisa : {}", projetoDePesquisaDTO);
        ProjetoDePesquisa projetoDePesquisa = projetoDePesquisaMapper.toEntity(projetoDePesquisaDTO);
        projetoDePesquisa = projetoDePesquisaRepository.save(projetoDePesquisa);
        ProjetoDePesquisaDTO result = projetoDePesquisaMapper.toDto(projetoDePesquisa);
        projetoDePesquisaSearchRepository.save(projetoDePesquisa);
        return result;
    }

    /**
     * Get all the projetoDePesquisas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjetoDePesquisaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjetoDePesquisas");
        return projetoDePesquisaRepository.findAll(pageable)
            .map(projetoDePesquisaMapper::toDto);
    }


    /**
     * Get one projetoDePesquisa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjetoDePesquisaDTO> findOne(Long id) {
        log.debug("Request to get ProjetoDePesquisa : {}", id);
        return projetoDePesquisaRepository.findById(id)
            .map(projetoDePesquisaMapper::toDto);
    }

    /**
     * Delete the projetoDePesquisa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjetoDePesquisa : {}", id);
        projetoDePesquisaRepository.deleteById(id);
        projetoDePesquisaSearchRepository.deleteById(id);
    }

    /**
     * Search for the projetoDePesquisa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjetoDePesquisaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProjetoDePesquisas for query {}", query);
        return projetoDePesquisaSearchRepository.search(queryStringQuery(query), pageable)
            .map(projetoDePesquisaMapper::toDto);
    }
}
