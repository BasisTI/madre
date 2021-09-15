package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.ItemSolicitacaoExame;
import br.com.basis.madre.madreexames.repository.ItemSolicitacaoExameRepository;
import br.com.basis.madre.madreexames.repository.search.ItemSolicitacaoExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.ItemSolicitacaoExameDTO;
import br.com.basis.madre.madreexames.service.mapper.ItemSolicitacaoExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link ItemSolicitacaoExame}.
 */
@Service
@Transactional
public class ItemSolicitacaoExameService {

    private final Logger log = LoggerFactory.getLogger(ItemSolicitacaoExameService.class);

    private final ItemSolicitacaoExameRepository itemSolicitacaoExameRepository;

    private final ItemSolicitacaoExameMapper itemSolicitacaoExameMapper;

    private final ItemSolicitacaoExameSearchRepository itemSolicitacaoExameSearchRepository;

    public ItemSolicitacaoExameService(ItemSolicitacaoExameRepository itemSolicitacaoExameRepository, ItemSolicitacaoExameMapper itemSolicitacaoExameMapper, ItemSolicitacaoExameSearchRepository itemSolicitacaoExameSearchRepository) {
        this.itemSolicitacaoExameRepository = itemSolicitacaoExameRepository;
        this.itemSolicitacaoExameMapper = itemSolicitacaoExameMapper;
        this.itemSolicitacaoExameSearchRepository = itemSolicitacaoExameSearchRepository;
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
        ItemSolicitacaoExameDTO result = itemSolicitacaoExameMapper.toDto(itemSolicitacaoExame);
        itemSolicitacaoExameSearchRepository.save(itemSolicitacaoExame);
        return result;
    }

    /**
     * Get all the itemSolicitacaoExames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemSolicitacaoExameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemSolicitacaoExames");
        return itemSolicitacaoExameRepository.findAll(pageable)
            .map(itemSolicitacaoExameMapper::toDto);
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
        itemSolicitacaoExameSearchRepository.deleteById(id);
    }

    /**
     * Search for the itemSolicitacaoExame corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemSolicitacaoExameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemSolicitacaoExames for query {}", query);
        return itemSolicitacaoExameSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemSolicitacaoExameMapper::toDto);
    }
}
