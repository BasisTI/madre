package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoDiagnosticoRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoDiagnosticoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDiagnosticoDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoDiagnosticoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ItemPrescricaoDiagnostico}.
 */
@Service
@Transactional
public class ItemPrescricaoDiagnosticoService {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoDiagnosticoService.class);

    private final ItemPrescricaoDiagnosticoRepository itemPrescricaoDiagnosticoRepository;

    private final ItemPrescricaoDiagnosticoMapper itemPrescricaoDiagnosticoMapper;

    private final ItemPrescricaoDiagnosticoSearchRepository itemPrescricaoDiagnosticoSearchRepository;

    public ItemPrescricaoDiagnosticoService(ItemPrescricaoDiagnosticoRepository itemPrescricaoDiagnosticoRepository, ItemPrescricaoDiagnosticoMapper itemPrescricaoDiagnosticoMapper, ItemPrescricaoDiagnosticoSearchRepository itemPrescricaoDiagnosticoSearchRepository) {
        this.itemPrescricaoDiagnosticoRepository = itemPrescricaoDiagnosticoRepository;
        this.itemPrescricaoDiagnosticoMapper = itemPrescricaoDiagnosticoMapper;
        this.itemPrescricaoDiagnosticoSearchRepository = itemPrescricaoDiagnosticoSearchRepository;
    }

    /**
     * Save a itemPrescricaoDiagnostico.
     *
     * @param itemPrescricaoDiagnosticoDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemPrescricaoDiagnosticoDTO save(ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO) {
        log.debug("Request to save ItemPrescricaoDiagnostico : {}", itemPrescricaoDiagnosticoDTO);
        ItemPrescricaoDiagnostico itemPrescricaoDiagnostico = itemPrescricaoDiagnosticoMapper.toEntity(itemPrescricaoDiagnosticoDTO);
        itemPrescricaoDiagnostico = itemPrescricaoDiagnosticoRepository.save(itemPrescricaoDiagnostico);
        ItemPrescricaoDiagnosticoDTO result = itemPrescricaoDiagnosticoMapper.toDto(itemPrescricaoDiagnostico);
        itemPrescricaoDiagnosticoSearchRepository.save(itemPrescricaoDiagnostico);
        return result;
    }

    /**
     * Get all the itemPrescricaoDiagnosticos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoDiagnosticoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemPrescricaoDiagnosticos");
        return itemPrescricaoDiagnosticoRepository.findAll(pageable)
            .map(itemPrescricaoDiagnosticoMapper::toDto);
    }


    /**
     * Get one itemPrescricaoDiagnostico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemPrescricaoDiagnosticoDTO> findOne(Long id) {
        log.debug("Request to get ItemPrescricaoDiagnostico : {}", id);
        return itemPrescricaoDiagnosticoRepository.findById(id)
            .map(itemPrescricaoDiagnosticoMapper::toDto);
    }

    /**
     * Delete the itemPrescricaoDiagnostico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemPrescricaoDiagnostico : {}", id);
        itemPrescricaoDiagnosticoRepository.deleteById(id);
        itemPrescricaoDiagnosticoSearchRepository.deleteById(id);
    }

    /**
     * Search for the itemPrescricaoDiagnostico corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemPrescricaoDiagnosticoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemPrescricaoDiagnosticos for query {}", query);
        return itemPrescricaoDiagnosticoSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemPrescricaoDiagnosticoMapper::toDto);
    }
}
