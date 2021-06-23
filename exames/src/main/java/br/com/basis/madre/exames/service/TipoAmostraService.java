package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.TipoAmostra;
import br.com.basis.madre.exames.repository.TipoAmostraRepository;
import br.com.basis.madre.exames.service.dto.TipoAmostraDTO;
import br.com.basis.madre.exames.service.mapper.TipoAmostraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TipoAmostra}.
 */
@Service
@Transactional
public class TipoAmostraService {

    private final Logger log = LoggerFactory.getLogger(TipoAmostraService.class);

    private final TipoAmostraRepository tipoAmostraRepository;

    private final TipoAmostraMapper tipoAmostraMapper;

    public TipoAmostraService(TipoAmostraRepository tipoAmostraRepository, TipoAmostraMapper tipoAmostraMapper) {
        this.tipoAmostraRepository = tipoAmostraRepository;
        this.tipoAmostraMapper = tipoAmostraMapper;
    }

    /**
     * Save a tipoAmostra.
     *
     * @param tipoAmostraDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoAmostraDTO save(TipoAmostraDTO tipoAmostraDTO) {
        log.debug("Request to save TipoAmostra : {}", tipoAmostraDTO);
        TipoAmostra tipoAmostra = tipoAmostraMapper.toEntity(tipoAmostraDTO);
        tipoAmostra = tipoAmostraRepository.save(tipoAmostra);
        return tipoAmostraMapper.toDto(tipoAmostra);
    }

    /**
     * Get all the tipoAmostras.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TipoAmostraDTO> findAll() {
        log.debug("Request to get all TipoAmostras");
        return tipoAmostraRepository.findAll().stream()
            .map(tipoAmostraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tipoAmostra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoAmostraDTO> findOne(Long id) {
        log.debug("Request to get TipoAmostra : {}", id);
        return tipoAmostraRepository.findById(id)
            .map(tipoAmostraMapper::toDto);
    }

    /**
     * Delete the tipoAmostra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoAmostra : {}", id);
        tipoAmostraRepository.deleteById(id);
    }

    /**
     * Search for the tipoAmostra corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TipoAmostraDTO> search(String query) {
        log.debug("Request to search TipoAmostras for query {}", query);
            return tipoAmostraRepository.search(TipoAmostra.PREFIX, query).stream()
            .map(tipoAmostraMapper::toDto)
        .collect(Collectors.toList());
    }
}
