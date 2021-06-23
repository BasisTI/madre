package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.InformacoesComplementares;
import br.com.basis.madre.exames.repository.InformacoesComplementaresRepository;
import br.com.basis.madre.exames.service.dto.InformacoesComplementaresDTO;
import br.com.basis.madre.exames.service.mapper.InformacoesComplementaresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InformacoesComplementares}.
 */
@Service
@Transactional
public class InformacoesComplementaresService {

    private final Logger log = LoggerFactory.getLogger(InformacoesComplementaresService.class);

    private final InformacoesComplementaresRepository informacoesComplementaresRepository;

    private final InformacoesComplementaresMapper informacoesComplementaresMapper;

    public InformacoesComplementaresService(InformacoesComplementaresRepository informacoesComplementaresRepository, InformacoesComplementaresMapper informacoesComplementaresMapper) {
        this.informacoesComplementaresRepository = informacoesComplementaresRepository;
        this.informacoesComplementaresMapper = informacoesComplementaresMapper;
    }

    /**
     * Save a informacoesComplementares.
     *
     * @param informacoesComplementaresDTO the entity to save.
     * @return the persisted entity.
     */
    public InformacoesComplementaresDTO save(InformacoesComplementaresDTO informacoesComplementaresDTO) {
        log.debug("Request to save InformacoesComplementares : {}", informacoesComplementaresDTO);
        InformacoesComplementares informacoesComplementares = informacoesComplementaresMapper.toEntity(informacoesComplementaresDTO);
        informacoesComplementares = informacoesComplementaresRepository.save(informacoesComplementares);
        return informacoesComplementaresMapper.toDto(informacoesComplementares);
    }

    /**
     * Get all the informacoesComplementares.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InformacoesComplementaresDTO> findAll() {
        log.debug("Request to get all InformacoesComplementares");
        return informacoesComplementaresRepository.findAll().stream()
            .map(informacoesComplementaresMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one informacoesComplementares by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InformacoesComplementaresDTO> findOne(Long id) {
        log.debug("Request to get InformacoesComplementares : {}", id);
        return informacoesComplementaresRepository.findById(id)
            .map(informacoesComplementaresMapper::toDto);
    }

    /**
     * Delete the informacoesComplementares by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InformacoesComplementares : {}", id);
        informacoesComplementaresRepository.deleteById(id);
    }

    /**
     * Search for the informacoesComplementares corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InformacoesComplementaresDTO> search(String query) {
        log.debug("Request to search InformacoesComplementares for query {}", query);
            return informacoesComplementaresRepository.search(InformacoesComplementares.PREFIX, query).stream()
            .map(informacoesComplementaresMapper::toDto)
        .collect(Collectors.toList());
    }
}
