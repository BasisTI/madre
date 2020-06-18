package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TipoResiduo;
import br.com.basis.suprimentos.repository.TipoResiduoRepository;
import br.com.basis.suprimentos.repository.search.TipoResiduoSearchRepository;
import br.com.basis.suprimentos.service.dto.TipoResiduoDTO;
import br.com.basis.suprimentos.service.mapper.TipoResiduoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoResiduo}.
 */
@Service
@Transactional
public class TipoResiduoService {

    private final Logger log = LoggerFactory.getLogger(TipoResiduoService.class);

    private final TipoResiduoRepository tipoResiduoRepository;

    private final TipoResiduoMapper tipoResiduoMapper;

    private final TipoResiduoSearchRepository tipoResiduoSearchRepository;

    public TipoResiduoService(TipoResiduoRepository tipoResiduoRepository, TipoResiduoMapper tipoResiduoMapper, TipoResiduoSearchRepository tipoResiduoSearchRepository) {
        this.tipoResiduoRepository = tipoResiduoRepository;
        this.tipoResiduoMapper = tipoResiduoMapper;
        this.tipoResiduoSearchRepository = tipoResiduoSearchRepository;
    }

    /**
     * Save a tipoResiduo.
     *
     * @param tipoResiduoDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoResiduoDTO save(TipoResiduoDTO tipoResiduoDTO) {
        log.debug("Request to save TipoResiduo : {}", tipoResiduoDTO);
        TipoResiduo tipoResiduo = tipoResiduoMapper.toEntity(tipoResiduoDTO);
        tipoResiduo = tipoResiduoRepository.save(tipoResiduo);
        TipoResiduoDTO result = tipoResiduoMapper.toDto(tipoResiduo);
        tipoResiduoSearchRepository.save(tipoResiduo);
        return result;
    }

    /**
     * Get all the tipoResiduos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoResiduoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoResiduos");
        return tipoResiduoRepository.findAll(pageable)
            .map(tipoResiduoMapper::toDto);
    }


    /**
     * Get one tipoResiduo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoResiduoDTO> findOne(Long id) {
        log.debug("Request to get TipoResiduo : {}", id);
        return tipoResiduoRepository.findById(id)
            .map(tipoResiduoMapper::toDto);
    }

    /**
     * Delete the tipoResiduo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoResiduo : {}", id);
        tipoResiduoRepository.deleteById(id);
        tipoResiduoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoResiduo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoResiduoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoResiduos for query {}", query);
        return tipoResiduoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoResiduoMapper::toDto);
    }
}
