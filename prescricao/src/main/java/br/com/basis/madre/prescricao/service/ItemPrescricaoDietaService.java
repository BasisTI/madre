package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoDietaRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoDietaSearchRepository;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDietaDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoDietaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ItemPrescricaoDieta}.
 */
@Service
@Transactional
public class ItemPrescricaoDietaService {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoDietaService.class);

    private final ItemPrescricaoDietaRepository itemPrescricaoDietaRepository;

    private final ItemPrescricaoDietaMapper itemPrescricaoDietaMapper;

    private final ItemPrescricaoDietaSearchRepository itemPrescricaoDietaSearchRepository;

    public ItemPrescricaoDietaService(ItemPrescricaoDietaRepository itemPrescricaoDietaRepository, ItemPrescricaoDietaMapper itemPrescricaoDietaMapper, ItemPrescricaoDietaSearchRepository itemPrescricaoDietaSearchRepository) {
        this.itemPrescricaoDietaRepository = itemPrescricaoDietaRepository;
        this.itemPrescricaoDietaMapper = itemPrescricaoDietaMapper;
        this.itemPrescricaoDietaSearchRepository = itemPrescricaoDietaSearchRepository;
    }

    /**
     * Save a itemPrescricaoDieta.
     *
     * @param itemPrescricaoDietaDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemPrescricaoDietaDTO save(ItemPrescricaoDietaDTO itemPrescricaoDietaDTO) {
        log.debug("Request to save ItemPrescricaoDieta : {}", itemPrescricaoDietaDTO);
        ItemPrescricaoDieta itemPrescricaoDieta = itemPrescricaoDietaMapper.toEntity(itemPrescricaoDietaDTO);
        itemPrescricaoDieta = itemPrescricaoDietaRepository.save(itemPrescricaoDieta);
        ItemPrescricaoDietaDTO result = itemPrescricaoDietaMapper.toDto(itemPrescricaoDieta);
        itemPrescricaoDietaSearchRepository.save(itemPrescricaoDieta);
        return result;
    }

    /**
     * Get all the itemPrescricaoDietas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoDietaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPrescricaoDietas");
        return itemPrescricaoDietaRepository.findAll(pageable)
            .map(itemPrescricaoDietaMapper::toDto);
    }


    /**
     * Get one itemPrescricaoDieta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemPrescricaoDietaDTO> findOne(Long id) {
        log.debug("Request to get ItemPrescricaoDieta : {}", id);
        return itemPrescricaoDietaRepository.findById(id)
            .map(itemPrescricaoDietaMapper::toDto);
    }

    /**
     * Delete the itemPrescricaoDieta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemPrescricaoDieta : {}", id);
        itemPrescricaoDietaRepository.deleteById(id);
        itemPrescricaoDietaSearchRepository.deleteById(id);
    }

    /**
     * Search for the itemPrescricaoDieta corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoDietaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemPrescricaoDietas for query {}", query);
        return itemPrescricaoDietaSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemPrescricaoDietaMapper::toDto);
    }
}
