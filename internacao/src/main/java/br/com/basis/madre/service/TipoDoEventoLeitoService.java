package br.com.basis.madre.service;

import br.com.basis.madre.domain.TipoDoEventoLeito;
import br.com.basis.madre.repository.TipoDoEventoLeitoRepository;
import br.com.basis.madre.repository.search.TipoDoEventoLeitoSearchRepository;
import br.com.basis.madre.service.dto.TipoDoEventoLeitoDTO;
import br.com.basis.madre.service.mapper.TipoDoEventoLeitoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RequiredArgsConstructor
@Service
@Transactional
public class TipoDoEventoLeitoService {

    private final Logger log = LoggerFactory.getLogger(TipoDoEventoLeitoService.class);

    private final TipoDoEventoLeitoRepository tipoDoEventoLeitoRepository;

    private final TipoDoEventoLeitoMapper tipoDoEventoLeitoMapper;

    private final TipoDoEventoLeitoSearchRepository tipoDoEventoLeitoSearchRepository;

    /**
     * Save a tipoDoEventoLeito.
     *
     * @param tipoDoEventoLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoDoEventoLeitoDTO save(TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO) {
        log.debug("Request to save TipoDoEventoLeito : {}", tipoDoEventoLeitoDTO);
        TipoDoEventoLeito tipoDoEventoLeito = tipoDoEventoLeitoMapper
            .toEntity(tipoDoEventoLeitoDTO);
        tipoDoEventoLeito = tipoDoEventoLeitoRepository.save(tipoDoEventoLeito);
        TipoDoEventoLeitoDTO result = tipoDoEventoLeitoMapper.toDto(tipoDoEventoLeito);
        tipoDoEventoLeitoSearchRepository.save(tipoDoEventoLeito);
        return result;
    }

    /**
     * Get all the tipoDoEventoLeitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDoEventoLeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDoEventoLeitos");
        return tipoDoEventoLeitoRepository.findAll(pageable)
            .map(tipoDoEventoLeitoMapper::toDto);
    }


    /**
     * Get one tipoDoEventoLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoDoEventoLeitoDTO> findOne(Long id) {
        log.debug("Request to get TipoDoEventoLeito : {}", id);
        return tipoDoEventoLeitoRepository.findById(id)
            .map(tipoDoEventoLeitoMapper::toDto);
    }

    /**
     * Delete the tipoDoEventoLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoDoEventoLeito : {}", id);
        tipoDoEventoLeitoRepository.deleteById(id);
        tipoDoEventoLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDoEventoLeito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDoEventoLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDoEventoLeitos for query {}", query);
        return tipoDoEventoLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoDoEventoLeitoMapper::toDto);
    }
}
