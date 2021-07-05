package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.ControleQualidade;
import br.com.basis.madre.madreexames.repository.ControleQualidadeRepository;
import br.com.basis.madre.madreexames.repository.search.ControleQualidadeSearchRepository;
import br.com.basis.madre.madreexames.service.dto.ControleQualidadeDTO;
import br.com.basis.madre.madreexames.service.mapper.ControleQualidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ControleQualidade}.
 */
@Service
@Transactional
public class ControleQualidadeService {

    private final Logger log = LoggerFactory.getLogger(ControleQualidadeService.class);

    private final ControleQualidadeRepository controleQualidadeRepository;

    private final ControleQualidadeMapper controleQualidadeMapper;

    private final ControleQualidadeSearchRepository controleQualidadeSearchRepository;

    public ControleQualidadeService(ControleQualidadeRepository controleQualidadeRepository, ControleQualidadeMapper controleQualidadeMapper, ControleQualidadeSearchRepository controleQualidadeSearchRepository) {
        this.controleQualidadeRepository = controleQualidadeRepository;
        this.controleQualidadeMapper = controleQualidadeMapper;
        this.controleQualidadeSearchRepository = controleQualidadeSearchRepository;
    }

    /**
     * Save a controleQualidade.
     *
     * @param controleQualidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public ControleQualidadeDTO save(ControleQualidadeDTO controleQualidadeDTO) {
        log.debug("Request to save ControleQualidade : {}", controleQualidadeDTO);
        ControleQualidade controleQualidade = controleQualidadeMapper.toEntity(controleQualidadeDTO);
        controleQualidade = controleQualidadeRepository.save(controleQualidade);
        ControleQualidadeDTO result = controleQualidadeMapper.toDto(controleQualidade);
        controleQualidadeSearchRepository.save(controleQualidade);
        return result;
    }

    /**
     * Get all the controleQualidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ControleQualidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ControleQualidades");
        return controleQualidadeRepository.findAll(pageable)
            .map(controleQualidadeMapper::toDto);
    }


    /**
     * Get one controleQualidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ControleQualidadeDTO> findOne(Long id) {
        log.debug("Request to get ControleQualidade : {}", id);
        return controleQualidadeRepository.findById(id)
            .map(controleQualidadeMapper::toDto);
    }

    /**
     * Delete the controleQualidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ControleQualidade : {}", id);
        controleQualidadeRepository.deleteById(id);
        controleQualidadeSearchRepository.deleteById(id);
    }

    /**
     * Search for the controleQualidade corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ControleQualidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ControleQualidades for query {}", query);
        return controleQualidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(controleQualidadeMapper::toDto);
    }
}
