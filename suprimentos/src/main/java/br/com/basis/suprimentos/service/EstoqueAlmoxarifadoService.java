package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import br.com.basis.suprimentos.repository.EstoqueAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.EstoqueAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.EstoqueAlmoxarifadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EstoqueAlmoxarifado}.
 */
@Service
@Transactional
public class EstoqueAlmoxarifadoService {

    private final Logger log = LoggerFactory.getLogger(EstoqueAlmoxarifadoService.class);

    private final EstoqueAlmoxarifadoRepository estoqueAlmoxarifadoRepository;

    private final EstoqueAlmoxarifadoMapper estoqueAlmoxarifadoMapper;

    private final EstoqueAlmoxarifadoSearchRepository estoqueAlmoxarifadoSearchRepository;

    public EstoqueAlmoxarifadoService(EstoqueAlmoxarifadoRepository estoqueAlmoxarifadoRepository, EstoqueAlmoxarifadoMapper estoqueAlmoxarifadoMapper, EstoqueAlmoxarifadoSearchRepository estoqueAlmoxarifadoSearchRepository) {
        this.estoqueAlmoxarifadoRepository = estoqueAlmoxarifadoRepository;
        this.estoqueAlmoxarifadoMapper = estoqueAlmoxarifadoMapper;
        this.estoqueAlmoxarifadoSearchRepository = estoqueAlmoxarifadoSearchRepository;
    }

    /**
     * Save a estoqueAlmoxarifado.
     *
     * @param estoqueAlmoxarifadoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstoqueAlmoxarifadoDTO save(EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO) {
        log.debug("Request to save EstoqueAlmoxarifado : {}", estoqueAlmoxarifadoDTO);
        EstoqueAlmoxarifado estoqueAlmoxarifado = estoqueAlmoxarifadoMapper.toEntity(estoqueAlmoxarifadoDTO);
        estoqueAlmoxarifado = estoqueAlmoxarifadoRepository.save(estoqueAlmoxarifado);
        EstoqueAlmoxarifadoDTO result = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);
        estoqueAlmoxarifadoSearchRepository.save(estoqueAlmoxarifado);
        return result;
    }

    /**
     * Get all the estoqueAlmoxarifados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstoqueAlmoxarifadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstoqueAlmoxarifados");
        return estoqueAlmoxarifadoRepository.findAll(pageable)
            .map(estoqueAlmoxarifadoMapper::toDto);
    }


    /**
     * Get one estoqueAlmoxarifado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstoqueAlmoxarifadoDTO> findOne(Long id) {
        log.debug("Request to get EstoqueAlmoxarifado : {}", id);
        return estoqueAlmoxarifadoRepository.findById(id)
            .map(estoqueAlmoxarifadoMapper::toDto);
    }

    /**
     * Delete the estoqueAlmoxarifado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EstoqueAlmoxarifado : {}", id);
        estoqueAlmoxarifadoRepository.deleteById(id);
        estoqueAlmoxarifadoSearchRepository.deleteById(id);
    }

    /**
     * Search for the estoqueAlmoxarifado corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstoqueAlmoxarifadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EstoqueAlmoxarifados for query {}", query);
        return estoqueAlmoxarifadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(estoqueAlmoxarifadoMapper::toDto);
    }
}
