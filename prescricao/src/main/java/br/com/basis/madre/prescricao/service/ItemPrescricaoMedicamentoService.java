package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoMedicamentoDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoMedicamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ItemPrescricaoMedicamento}.
 */
@Service
@Transactional
public class ItemPrescricaoMedicamentoService {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoMedicamentoService.class);

    private final ItemPrescricaoMedicamentoRepository itemPrescricaoMedicamentoRepository;

    private final ItemPrescricaoMedicamentoMapper itemPrescricaoMedicamentoMapper;

    private final ItemPrescricaoMedicamentoSearchRepository itemPrescricaoMedicamentoSearchRepository;

    public ItemPrescricaoMedicamentoService(ItemPrescricaoMedicamentoRepository itemPrescricaoMedicamentoRepository, ItemPrescricaoMedicamentoMapper itemPrescricaoMedicamentoMapper, ItemPrescricaoMedicamentoSearchRepository itemPrescricaoMedicamentoSearchRepository) {
        this.itemPrescricaoMedicamentoRepository = itemPrescricaoMedicamentoRepository;
        this.itemPrescricaoMedicamentoMapper = itemPrescricaoMedicamentoMapper;
        this.itemPrescricaoMedicamentoSearchRepository = itemPrescricaoMedicamentoSearchRepository;
    }

    /**
     * Save a itemPrescricaoMedicamento.
     *
     * @param itemPrescricaoMedicamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemPrescricaoMedicamentoDTO save(ItemPrescricaoMedicamentoDTO itemPrescricaoMedicamentoDTO) {
        log.debug("Request to save ItemPrescricaoMedicamento : {}", itemPrescricaoMedicamentoDTO);
        ItemPrescricaoMedicamento itemPrescricaoMedicamento = itemPrescricaoMedicamentoMapper.toEntity(itemPrescricaoMedicamentoDTO);
        itemPrescricaoMedicamento = itemPrescricaoMedicamentoRepository.save(itemPrescricaoMedicamento);
        ItemPrescricaoMedicamentoDTO result = itemPrescricaoMedicamentoMapper.toDto(itemPrescricaoMedicamento);
        itemPrescricaoMedicamentoSearchRepository.save(itemPrescricaoMedicamento);
        return result;
    }

    /**
     * Get all the itemPrescricaoMedicamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoMedicamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPrescricaoMedicamentos");
        return itemPrescricaoMedicamentoRepository.findAll(pageable)
            .map(itemPrescricaoMedicamentoMapper::toDto);
    }


    /**
     * Get one itemPrescricaoMedicamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemPrescricaoMedicamentoDTO> findOne(Long id) {
        log.debug("Request to get ItemPrescricaoMedicamento : {}", id);
        return itemPrescricaoMedicamentoRepository.findById(id)
            .map(itemPrescricaoMedicamentoMapper::toDto);
    }

    /**
     * Delete the itemPrescricaoMedicamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemPrescricaoMedicamento : {}", id);
        itemPrescricaoMedicamentoRepository.deleteById(id);
        itemPrescricaoMedicamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the itemPrescricaoMedicamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoMedicamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemPrescricaoMedicamentos for query {}", query);
        return itemPrescricaoMedicamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemPrescricaoMedicamentoMapper::toDto);
    }
}
