package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.LaboratorioExterno;
import br.com.basis.madre.exames.repository.LaboratorioExternoRepository;
import br.com.basis.madre.exames.service.dto.LaboratorioExternoDTO;
import br.com.basis.madre.exames.service.mapper.LaboratorioExternoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LaboratorioExterno}.
 */
@Service
@Transactional
public class LaboratorioExternoService {

    private final Logger log = LoggerFactory.getLogger(LaboratorioExternoService.class);

    private final LaboratorioExternoRepository laboratorioExternoRepository;

    private final LaboratorioExternoMapper laboratorioExternoMapper;

    public LaboratorioExternoService(LaboratorioExternoRepository laboratorioExternoRepository, LaboratorioExternoMapper laboratorioExternoMapper) {
        this.laboratorioExternoRepository = laboratorioExternoRepository;
        this.laboratorioExternoMapper = laboratorioExternoMapper;
    }

    /**
     * Save a laboratorioExterno.
     *
     * @param laboratorioExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public LaboratorioExternoDTO save(LaboratorioExternoDTO laboratorioExternoDTO) {
        log.debug("Request to save LaboratorioExterno : {}", laboratorioExternoDTO);
        LaboratorioExterno laboratorioExterno = laboratorioExternoMapper.toEntity(laboratorioExternoDTO);
        laboratorioExterno = laboratorioExternoRepository.save(laboratorioExterno);
        return laboratorioExternoMapper.toDto(laboratorioExterno);
    }

    /**
     * Get all the laboratorioExternos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LaboratorioExternoDTO> findAll() {
        log.debug("Request to get all LaboratorioExternos");
        return laboratorioExternoRepository.findAll().stream()
            .map(laboratorioExternoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one laboratorioExterno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LaboratorioExternoDTO> findOne(Long id) {
        log.debug("Request to get LaboratorioExterno : {}", id);
        return laboratorioExternoRepository.findById(id)
            .map(laboratorioExternoMapper::toDto);
    }

    /**
     * Delete the laboratorioExterno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LaboratorioExterno : {}", id);
        laboratorioExternoRepository.deleteById(id);
    }

    /**
     * Search for the laboratorioExterno corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LaboratorioExternoDTO> search(String query) {
        log.debug("Request to search LaboratorioExternos for query {}", query);
            return laboratorioExternoRepository.search(LaboratorioExterno.PREFIX, query).stream()
            .map(laboratorioExternoMapper::toDto)
        .collect(Collectors.toList());
    }
}
