package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.AtendimentoDiverso;
import br.com.basis.madre.exames.repository.AtendimentoDiversoRepository;
import br.com.basis.madre.exames.service.dto.AtendimentoDiversoDTO;
import br.com.basis.madre.exames.service.mapper.AtendimentoDiversoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AtendimentoDiverso}.
 */
@Service
@Transactional
public class AtendimentoDiversoService {

    private final Logger log = LoggerFactory.getLogger(AtendimentoDiversoService.class);

    private final AtendimentoDiversoRepository atendimentoDiversoRepository;

    private final AtendimentoDiversoMapper atendimentoDiversoMapper;

    public AtendimentoDiversoService(AtendimentoDiversoRepository atendimentoDiversoRepository, AtendimentoDiversoMapper atendimentoDiversoMapper) {
        this.atendimentoDiversoRepository = atendimentoDiversoRepository;
        this.atendimentoDiversoMapper = atendimentoDiversoMapper;
    }

    /**
     * Save a atendimentoDiverso.
     *
     * @param atendimentoDiversoDTO the entity to save.
     * @return the persisted entity.
     */
    public AtendimentoDiversoDTO save(AtendimentoDiversoDTO atendimentoDiversoDTO) {
        log.debug("Request to save AtendimentoDiverso : {}", atendimentoDiversoDTO);
        AtendimentoDiverso atendimentoDiverso = atendimentoDiversoMapper.toEntity(atendimentoDiversoDTO);
        atendimentoDiverso = atendimentoDiversoRepository.save(atendimentoDiverso);
        return atendimentoDiversoMapper.toDto(atendimentoDiverso);
    }

    /**
     * Get all the atendimentoDiversos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AtendimentoDiversoDTO> findAll() {
        log.debug("Request to get all AtendimentoDiversos");
        return atendimentoDiversoRepository.findAll().stream()
            .map(atendimentoDiversoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one atendimentoDiverso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtendimentoDiversoDTO> findOne(Long id) {
        log.debug("Request to get AtendimentoDiverso : {}", id);
        return atendimentoDiversoRepository.findById(id)
            .map(atendimentoDiversoMapper::toDto);
    }

    /**
     * Delete the atendimentoDiverso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AtendimentoDiverso : {}", id);
        atendimentoDiversoRepository.deleteById(id);
    }

    /**
     * Search for the atendimentoDiverso corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AtendimentoDiversoDTO> search(String query) {
        log.debug("Request to search AtendimentoDiversos for query {}", query);
            return atendimentoDiversoRepository.search(AtendimentoDiverso.PREFIX, query).stream()
            .map(atendimentoDiversoMapper::toDto)
        .collect(Collectors.toList());
    }
}
