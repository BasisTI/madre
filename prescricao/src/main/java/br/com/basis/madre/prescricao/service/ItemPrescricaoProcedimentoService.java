package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimento;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoProcedimentoRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoProcedimentoSearchRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoProcedimentoDTOSearchRepository;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoProcedimentoDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoProcedimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ItemPrescricaoProcedimento}.
 */
@Service
@Transactional
public class ItemPrescricaoProcedimentoService {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoProcedimentoService.class);

    private final ItemPrescricaoProcedimentoRepository itemPrescricaoProcedimentoRepository;

    private final ItemPrescricaoProcedimentoMapper itemPrescricaoProcedimentoMapper;

    private final ItemPrescricaoProcedimentoSearchRepository itemPrescricaoProcedimentoSearchRepository;
    
    public ItemPrescricaoProcedimentoService(ItemPrescricaoProcedimentoRepository itemPrescricaoProcedimentoRepository, ItemPrescricaoProcedimentoMapper itemPrescricaoProcedimentoMapper, ItemPrescricaoProcedimentoSearchRepository itemPrescricaoProcedimentoSearchRepository) {
        this.itemPrescricaoProcedimentoRepository = itemPrescricaoProcedimentoRepository;
        this.itemPrescricaoProcedimentoMapper = itemPrescricaoProcedimentoMapper;
        this.itemPrescricaoProcedimentoSearchRepository = itemPrescricaoProcedimentoSearchRepository;
        
    }

    /**
     * Save a itemPrescricaoProcedimento.
     *
     * @param itemPrescricaoProcedimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemPrescricaoProcedimentoDTO save(ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO) {
        log.debug("Request to save ItemPrescricaoProcedimento : {}", itemPrescricaoProcedimentoDTO);
        ItemPrescricaoProcedimento itemPrescricaoProcedimento = itemPrescricaoProcedimentoMapper.toEntity(itemPrescricaoProcedimentoDTO);
        itemPrescricaoProcedimento = itemPrescricaoProcedimentoRepository.save(itemPrescricaoProcedimento);
        ItemPrescricaoProcedimentoDTO result = itemPrescricaoProcedimentoMapper.toDto(itemPrescricaoProcedimento);
        itemPrescricaoProcedimentoSearchRepository.save(itemPrescricaoProcedimento);
        return result;
    }

    /**
     * Get all the itemPrescricaoProcedimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoProcedimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPrescricaoProcedimentos");
        return itemPrescricaoProcedimentoRepository.findAll(pageable)
            .map(itemPrescricaoProcedimentoMapper::toDto);
    }


    /**
     * Get one itemPrescricaoProcedimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemPrescricaoProcedimentoDTO> findOne(Long id) {
        log.debug("Request to get ItemPrescricaoProcedimento : {}", id);
        return itemPrescricaoProcedimentoRepository.findById(id)
            .map(itemPrescricaoProcedimentoMapper::toDto);
    }

    /**
     * Delete the itemPrescricaoProcedimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemPrescricaoProcedimento : {}", id);
        itemPrescricaoProcedimentoRepository.deleteById(id);
        itemPrescricaoProcedimentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the itemPrescricaoProcedimento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoProcedimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemPrescricaoProcedimentos for query {}", query);
        return itemPrescricaoProcedimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemPrescricaoProcedimentoMapper::toDto);
    }
}
