package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.ProjetoDePesquisa;
import br.com.basis.madre.exames.repository.ProjetoDePesquisaRepository;
import br.com.basis.madre.exames.service.dto.ProjetoDePesquisaDTO;
import br.com.basis.madre.exames.service.mapper.ProjetoDePesquisaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProjetoDePesquisa}.
 */
@Service
@Transactional
public class ProjetoDePesquisaService {

    private final Logger log = LoggerFactory.getLogger(ProjetoDePesquisaService.class);

    private final ProjetoDePesquisaRepository projetoDePesquisaRepository;

    private final ProjetoDePesquisaMapper projetoDePesquisaMapper;

    public ProjetoDePesquisaService(ProjetoDePesquisaRepository projetoDePesquisaRepository, ProjetoDePesquisaMapper projetoDePesquisaMapper) {
        this.projetoDePesquisaRepository = projetoDePesquisaRepository;
        this.projetoDePesquisaMapper = projetoDePesquisaMapper;
    }

    /**
     * Save a projetoDePesquisa.
     *
     * @param projetoDePesquisaDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjetoDePesquisaDTO save(ProjetoDePesquisaDTO projetoDePesquisaDTO) {
        log.debug("Request to save ProjetoDePesquisa : {}", projetoDePesquisaDTO);
        ProjetoDePesquisa projetoDePesquisa = projetoDePesquisaMapper.toEntity(projetoDePesquisaDTO);
        projetoDePesquisa = projetoDePesquisaRepository.save(projetoDePesquisa);
        return projetoDePesquisaMapper.toDto(projetoDePesquisa);
    }

    /**
     * Get all the projetoDePesquisas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjetoDePesquisaDTO> findAll() {
        log.debug("Request to get all ProjetoDePesquisas");
        return projetoDePesquisaRepository.findAll().stream()
            .map(projetoDePesquisaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one projetoDePesquisa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjetoDePesquisaDTO> findOne(Long id) {
        log.debug("Request to get ProjetoDePesquisa : {}", id);
        return projetoDePesquisaRepository.findById(id)
            .map(projetoDePesquisaMapper::toDto);
    }

    /**
     * Delete the projetoDePesquisa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjetoDePesquisa : {}", id);
        projetoDePesquisaRepository.deleteById(id);
    }

    /**
     * Search for the projetoDePesquisa corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjetoDePesquisaDTO> search(String query) {
        log.debug("Request to search ProjetoDePesquisas for query {}", query);
            return projetoDePesquisaRepository.search(ProjetoDePesquisa.PREFIX, query).stream()
            .map(projetoDePesquisaMapper::toDto)
        .collect(Collectors.toList());
    }
}
