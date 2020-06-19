package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import br.com.basis.suprimentos.repository.TransferenciaAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.TransferenciaAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.TransferenciaAlmoxarifadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TransferenciaAlmoxarifado}.
 */
@Service
@Transactional
public class TransferenciaAlmoxarifadoService {

    private final Logger log = LoggerFactory.getLogger(TransferenciaAlmoxarifadoService.class);

    private final TransferenciaAlmoxarifadoRepository transferenciaAlmoxarifadoRepository;

    private final TransferenciaAlmoxarifadoMapper transferenciaAlmoxarifadoMapper;

    private final TransferenciaAlmoxarifadoSearchRepository transferenciaAlmoxarifadoSearchRepository;

    public TransferenciaAlmoxarifadoService(TransferenciaAlmoxarifadoRepository transferenciaAlmoxarifadoRepository, TransferenciaAlmoxarifadoMapper transferenciaAlmoxarifadoMapper, TransferenciaAlmoxarifadoSearchRepository transferenciaAlmoxarifadoSearchRepository) {
        this.transferenciaAlmoxarifadoRepository = transferenciaAlmoxarifadoRepository;
        this.transferenciaAlmoxarifadoMapper = transferenciaAlmoxarifadoMapper;
        this.transferenciaAlmoxarifadoSearchRepository = transferenciaAlmoxarifadoSearchRepository;
    }

    /**
     * Save a transferenciaAlmoxarifado.
     *
     * @param transferenciaAlmoxarifadoDTO the entity to save.
     * @return the persisted entity.
     */
    public TransferenciaAlmoxarifadoDTO save(TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) {
        log.debug("Request to save TransferenciaAlmoxarifado : {}", transferenciaAlmoxarifadoDTO);
        TransferenciaAlmoxarifado transferenciaAlmoxarifado = transferenciaAlmoxarifadoMapper.toEntity(transferenciaAlmoxarifadoDTO);
        transferenciaAlmoxarifado = transferenciaAlmoxarifadoRepository.save(transferenciaAlmoxarifado);
        TransferenciaAlmoxarifadoDTO result = transferenciaAlmoxarifadoMapper.toDto(transferenciaAlmoxarifado);
        transferenciaAlmoxarifadoSearchRepository.save(transferenciaAlmoxarifado);
        return result;
    }

    /**
     * Get all the transferenciaAlmoxarifados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferenciaAlmoxarifadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferenciaAlmoxarifados");
        return transferenciaAlmoxarifadoRepository.findAll(pageable)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }


    /**
     * Get one transferenciaAlmoxarifado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransferenciaAlmoxarifadoDTO> findOne(Long id) {
        log.debug("Request to get TransferenciaAlmoxarifado : {}", id);
        return transferenciaAlmoxarifadoRepository.findById(id)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }

    /**
     * Delete the transferenciaAlmoxarifado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransferenciaAlmoxarifado : {}", id);
        transferenciaAlmoxarifadoRepository.deleteById(id);
        transferenciaAlmoxarifadoSearchRepository.deleteById(id);
    }

    /**
     * Search for the transferenciaAlmoxarifado corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferenciaAlmoxarifadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransferenciaAlmoxarifados for query {}", query);
        return transferenciaAlmoxarifadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }
}
