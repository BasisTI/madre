package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.Cadaver;
import br.com.basis.madre.exames.repository.CadaverRepository;
import br.com.basis.madre.exames.service.dto.CadaverDTO;
import br.com.basis.madre.exames.service.mapper.CadaverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Cadaver}.
 */
@Service
@Transactional
public class CadaverService {

    private final Logger log = LoggerFactory.getLogger(CadaverService.class);

    private final CadaverRepository cadaverRepository;

    private final CadaverMapper cadaverMapper;

    public CadaverService(CadaverRepository cadaverRepository, CadaverMapper cadaverMapper) {
        this.cadaverRepository = cadaverRepository;
        this.cadaverMapper = cadaverMapper;
    }

    /**
     * Save a cadaver.
     *
     * @param cadaverDTO the entity to save.
     * @return the persisted entity.
     */
    public CadaverDTO save(CadaverDTO cadaverDTO) {
        log.debug("Request to save Cadaver : {}", cadaverDTO);
        Cadaver cadaver = cadaverMapper.toEntity(cadaverDTO);
        cadaver = cadaverRepository.save(cadaver);
        return cadaverMapper.toDto(cadaver);
    }

    /**
     * Get all the cadavers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CadaverDTO> findAll() {
        log.debug("Request to get all Cadavers");
        return cadaverRepository.findAll().stream()
            .map(cadaverMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cadaver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CadaverDTO> findOne(Long id) {
        log.debug("Request to get Cadaver : {}", id);
        return cadaverRepository.findById(id)
            .map(cadaverMapper::toDto);
    }

    /**
     * Delete the cadaver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cadaver : {}", id);
        cadaverRepository.deleteById(id);
    }

    /**
     * Search for the cadaver corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CadaverDTO> search(String query) {
        log.debug("Request to search Cadavers for query {}", query);
            return cadaverRepository.search(Cadaver.PREFIX, query).stream()
            .map(cadaverMapper::toDto)
        .collect(Collectors.toList());
    }
}
