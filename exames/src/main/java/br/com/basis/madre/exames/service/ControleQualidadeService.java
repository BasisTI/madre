package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.ControleQualidade;
import br.com.basis.madre.exames.repository.ControleQualidadeRepository;
import br.com.basis.madre.exames.service.dto.ControleQualidadeDTO;
import br.com.basis.madre.exames.service.mapper.ControleQualidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ControleQualidade}.
 */
@Service
@Transactional
public class ControleQualidadeService {

    private final Logger log = LoggerFactory.getLogger(ControleQualidadeService.class);

    private final ControleQualidadeRepository controleQualidadeRepository;

    private final ControleQualidadeMapper controleQualidadeMapper;

    public ControleQualidadeService(ControleQualidadeRepository controleQualidadeRepository, ControleQualidadeMapper controleQualidadeMapper) {
        this.controleQualidadeRepository = controleQualidadeRepository;
        this.controleQualidadeMapper = controleQualidadeMapper;
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
        return controleQualidadeMapper.toDto(controleQualidade);
    }

    /**
     * Get all the controleQualidades.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ControleQualidadeDTO> findAll() {
        log.debug("Request to get all ControleQualidades");
        return controleQualidadeRepository.findAll().stream()
            .map(controleQualidadeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
    }

    /**
     * Search for the controleQualidade corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ControleQualidadeDTO> search(String query) {
        log.debug("Request to search ControleQualidades for query {}", query);
            return controleQualidadeRepository.search(ControleQualidade.PREFIX, query).stream()
            .map(controleQualidadeMapper::toDto)
        .collect(Collectors.toList());
    }
}
