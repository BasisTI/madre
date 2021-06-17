package br.com.basis.madre.exames.service;

import br.com.basis.madre.exames.domain.ItemSolicitacaoExame;
import br.com.basis.madre.exames.repository.ItemSolicitacaoExameRepository;
import br.com.basis.madre.exames.service.dto.ItemSolicitacaoExameDTO;
import br.com.basis.madre.exames.service.mapper.ItemSolicitacaoExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ItemSolicitacaoExame}.
 */
@Service
@Transactional
public class ItemSolicitacaoExameService {

    private final Logger log = LoggerFactory.getLogger(ItemSolicitacaoExameService.class);

    private final ItemSolicitacaoExameRepository itemSolicitacaoExameRepository;

    private final ItemSolicitacaoExameMapper itemSolicitacaoExameMapper;

    public ItemSolicitacaoExameService(ItemSolicitacaoExameRepository itemSolicitacaoExameRepository, ItemSolicitacaoExameMapper itemSolicitacaoExameMapper) {
        this.itemSolicitacaoExameRepository = itemSolicitacaoExameRepository;
        this.itemSolicitacaoExameMapper = itemSolicitacaoExameMapper;
    }

    /**
     * Save a itemSolicitacaoExame.
     *
     * @param itemSolicitacaoExameDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemSolicitacaoExameDTO save(ItemSolicitacaoExameDTO itemSolicitacaoExameDTO) {
        log.debug("Request to save ItemSolicitacaoExame : {}", itemSolicitacaoExameDTO);
        ItemSolicitacaoExame itemSolicitacaoExame = itemSolicitacaoExameMapper.toEntity(itemSolicitacaoExameDTO);
        itemSolicitacaoExame = itemSolicitacaoExameRepository.save(itemSolicitacaoExame);
        return itemSolicitacaoExameMapper.toDto(itemSolicitacaoExame);
    }

    /**
     * Get all the itemSolicitacaoExames.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ItemSolicitacaoExameDTO> findAll() {
        log.debug("Request to get all ItemSolicitacaoExames");
        return itemSolicitacaoExameRepository.findAll().stream()
            .map(itemSolicitacaoExameMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one itemSolicitacaoExame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemSolicitacaoExameDTO> findOne(Long id) {
        log.debug("Request to get ItemSolicitacaoExame : {}", id);
        return itemSolicitacaoExameRepository.findById(id)
            .map(itemSolicitacaoExameMapper::toDto);
    }

    /**
     * Delete the itemSolicitacaoExame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemSolicitacaoExame : {}", id);
        itemSolicitacaoExameRepository.deleteById(id);
    }

    /**
     * Search for the itemSolicitacaoExame corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ItemSolicitacaoExameDTO> search(String query) {
        log.debug("Request to search ItemSolicitacaoExames for query {}", query);
            return itemSolicitacaoExameRepository.search(ItemSolicitacaoExame.PREFIX, query).stream()
            .map(itemSolicitacaoExameMapper::toDto)
        .collect(Collectors.toList());
    }
}
