package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.SolicitacaoExame;
import br.com.basis.madre.exames.repository.SolicitacaoExameRepository;
import br.com.basis.madre.exames.service.dto.SolicitacaoExameDTO;
import br.com.basis.madre.exames.service.mapper.SolicitacaoExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SolicitacaoExame}.
 */
@Service
@Transactional
public class SolicitacaoExameService {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoExameService.class);

    private final SolicitacaoExameRepository solicitacaoExameRepository;

    private final SolicitacaoExameMapper solicitacaoExameMapper;

    public SolicitacaoExameService(SolicitacaoExameRepository solicitacaoExameRepository, SolicitacaoExameMapper solicitacaoExameMapper) {
        this.solicitacaoExameRepository = solicitacaoExameRepository;
        this.solicitacaoExameMapper = solicitacaoExameMapper;
    }

    /**
     * Save a solicitacaoExame.
     *
     * @param solicitacaoExameDTO the entity to save.
     * @return the persisted entity.
     */
    public SolicitacaoExameDTO save(SolicitacaoExameDTO solicitacaoExameDTO) {
        log.debug("Request to save SolicitacaoExame : {}", solicitacaoExameDTO);
        SolicitacaoExame solicitacaoExame = solicitacaoExameMapper.toEntity(solicitacaoExameDTO);
        solicitacaoExame = solicitacaoExameRepository.save(solicitacaoExame);
        return solicitacaoExameMapper.toDto(solicitacaoExame);
    }

    /**
     * Get all the solicitacaoExames.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SolicitacaoExameDTO> findAll() {
        log.debug("Request to get all SolicitacaoExames");
        return solicitacaoExameRepository.findAll().stream()
            .map(solicitacaoExameMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one solicitacaoExame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SolicitacaoExameDTO> findOne(Long id) {
        log.debug("Request to get SolicitacaoExame : {}", id);
        return solicitacaoExameRepository.findById(id)
            .map(solicitacaoExameMapper::toDto);
    }

    /**
     * Delete the solicitacaoExame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SolicitacaoExame : {}", id);
        solicitacaoExameRepository.deleteById(id);
    }

    /**
     * Search for the solicitacaoExame corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SolicitacaoExameDTO> search(String query) {
        log.debug("Request to search SolicitacaoExames for query {}", query);
            return solicitacaoExameRepository.search(SolicitacaoExame.PREFIX, query).stream()
            .map(solicitacaoExameMapper::toDto)
        .collect(Collectors.toList());
    }
}
