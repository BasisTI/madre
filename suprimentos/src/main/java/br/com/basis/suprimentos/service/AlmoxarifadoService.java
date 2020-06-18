package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Almoxarifado;
import br.com.basis.suprimentos.repository.AlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.AlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.AlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.AlmoxarifadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Almoxarifado}.
 */
@Service
@Transactional
public class AlmoxarifadoService {

    private final Logger log = LoggerFactory.getLogger(AlmoxarifadoService.class);

    private final AlmoxarifadoRepository almoxarifadoRepository;

    private final AlmoxarifadoMapper almoxarifadoMapper;

    private final AlmoxarifadoSearchRepository almoxarifadoSearchRepository;

    public AlmoxarifadoService(AlmoxarifadoRepository almoxarifadoRepository, AlmoxarifadoMapper almoxarifadoMapper, AlmoxarifadoSearchRepository almoxarifadoSearchRepository) {
        this.almoxarifadoRepository = almoxarifadoRepository;
        this.almoxarifadoMapper = almoxarifadoMapper;
        this.almoxarifadoSearchRepository = almoxarifadoSearchRepository;
    }

    /**
     * Save a almoxarifado.
     *
     * @param almoxarifadoDTO the entity to save.
     * @return the persisted entity.
     */
    public AlmoxarifadoDTO save(AlmoxarifadoDTO almoxarifadoDTO) {
        log.debug("Request to save Almoxarifado : {}", almoxarifadoDTO);
        Almoxarifado almoxarifado = almoxarifadoMapper.toEntity(almoxarifadoDTO);
        almoxarifado = almoxarifadoRepository.save(almoxarifado);
        AlmoxarifadoDTO result = almoxarifadoMapper.toDto(almoxarifado);
        almoxarifadoSearchRepository.save(almoxarifado);
        return result;
    }

    /**
     * Get all the almoxarifados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlmoxarifadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Almoxarifados");
        return almoxarifadoRepository.findAll(pageable)
            .map(almoxarifadoMapper::toDto);
    }


    /**
     * Get one almoxarifado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlmoxarifadoDTO> findOne(Long id) {
        log.debug("Request to get Almoxarifado : {}", id);
        return almoxarifadoRepository.findById(id)
            .map(almoxarifadoMapper::toDto);
    }

    /**
     * Delete the almoxarifado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Almoxarifado : {}", id);
        almoxarifadoRepository.deleteById(id);
        almoxarifadoSearchRepository.deleteById(id);
    }

    /**
     * Search for the almoxarifado corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlmoxarifadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Almoxarifados for query {}", query);
        return almoxarifadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(almoxarifadoMapper::toDto);
    }
}
