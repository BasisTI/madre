package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimentoEspecial;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoProcedimentoEspecialRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoProcedimentoEspecialSearchRepository;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoProcedimentoEspecialDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoProcedimentoEspecialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ItemPrescricaoProcedimentoEspecial}.
 */
@Service
@Transactional
public class ItemPrescricaoProcedimentoEspecialService {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoProcedimentoEspecialService.class);

    private final ItemPrescricaoProcedimentoEspecialRepository itemPrescricaoProcedimentoEspecialRepository;

    private final ItemPrescricaoProcedimentoEspecialMapper itemPrescricaoProcedimentoEspecialMapper;

    private final ItemPrescricaoProcedimentoEspecialSearchRepository itemPrescricaoProcedimentoEspecialSearchRepository;

    public ItemPrescricaoProcedimentoEspecialService(ItemPrescricaoProcedimentoEspecialRepository itemPrescricaoProcedimentoEspecialRepository, ItemPrescricaoProcedimentoEspecialMapper itemPrescricaoProcedimentoEspecialMapper, ItemPrescricaoProcedimentoEspecialSearchRepository itemPrescricaoProcedimentoEspecialSearchRepository) {
        this.itemPrescricaoProcedimentoEspecialRepository = itemPrescricaoProcedimentoEspecialRepository;
        this.itemPrescricaoProcedimentoEspecialMapper = itemPrescricaoProcedimentoEspecialMapper;
        this.itemPrescricaoProcedimentoEspecialSearchRepository = itemPrescricaoProcedimentoEspecialSearchRepository;
    }

    /**
     * Save a itemPrescricaoProcedimentoEspecial.
     *
     * @param itemPrescricaoProcedimentoEspecialDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemPrescricaoProcedimentoEspecialDTO save(ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO) {
        log.debug("Request to save ItemPrescricaoProcedimentoEspecial : {}", itemPrescricaoProcedimentoEspecialDTO);
        ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial = itemPrescricaoProcedimentoEspecialMapper.toEntity(itemPrescricaoProcedimentoEspecialDTO);
        itemPrescricaoProcedimentoEspecial = itemPrescricaoProcedimentoEspecialRepository.save(itemPrescricaoProcedimentoEspecial);
        ItemPrescricaoProcedimentoEspecialDTO result = itemPrescricaoProcedimentoEspecialMapper.toDto(itemPrescricaoProcedimentoEspecial);
        itemPrescricaoProcedimentoEspecialSearchRepository.save(itemPrescricaoProcedimentoEspecial);
        return result;
    }

    /**
     * Get all the itemPrescricaoProcedimentoEspecials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoProcedimentoEspecialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPrescricaoProcedimentoEspecials");
        return itemPrescricaoProcedimentoEspecialRepository.findAll(pageable)
            .map(itemPrescricaoProcedimentoEspecialMapper::toDto);
    }


    /**
     * Get one itemPrescricaoProcedimentoEspecial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemPrescricaoProcedimentoEspecialDTO> findOne(Long id) {
        log.debug("Request to get ItemPrescricaoProcedimentoEspecial : {}", id);
        return itemPrescricaoProcedimentoEspecialRepository.findById(id)
            .map(itemPrescricaoProcedimentoEspecialMapper::toDto);
    }

    /**
     * Delete the itemPrescricaoProcedimentoEspecial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemPrescricaoProcedimentoEspecial : {}", id);
        itemPrescricaoProcedimentoEspecialRepository.deleteById(id);
        itemPrescricaoProcedimentoEspecialSearchRepository.deleteById(id);
    }

    /**
     * Search for the itemPrescricaoProcedimentoEspecial corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoProcedimentoEspecialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemPrescricaoProcedimentoEspecials for query {}", query);
        return itemPrescricaoProcedimentoEspecialSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemPrescricaoProcedimentoEspecialMapper::toDto);
    }
}
