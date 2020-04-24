package br.com.basis.madre.service;

import br.com.basis.madre.domain.CartaoSUS;
import br.com.basis.madre.repository.CartaoSUSRepository;
import br.com.basis.madre.repository.search.CartaoSUSSearchRepository;
import br.com.basis.madre.service.dto.CartaoSUSDTO;
import br.com.basis.madre.service.mapper.CartaoSUSMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CartaoSUS}.
 */
@Service
@Transactional
public class CartaoSUSService {

    private final Logger log = LoggerFactory.getLogger(CartaoSUSService.class);

    private final CartaoSUSRepository cartaoSUSRepository;

    private final CartaoSUSMapper cartaoSUSMapper;

    private final CartaoSUSSearchRepository cartaoSUSSearchRepository;

    public CartaoSUSService(CartaoSUSRepository cartaoSUSRepository, CartaoSUSMapper cartaoSUSMapper, CartaoSUSSearchRepository cartaoSUSSearchRepository) {
        this.cartaoSUSRepository = cartaoSUSRepository;
        this.cartaoSUSMapper = cartaoSUSMapper;
        this.cartaoSUSSearchRepository = cartaoSUSSearchRepository;
    }

    /**
     * Save a cartaoSUS.
     *
     * @param cartaoSUSDTO the entity to save.
     * @return the persisted entity.
     */
    public CartaoSUSDTO save(CartaoSUSDTO cartaoSUSDTO) {
        log.debug("Request to save CartaoSUS : {}", cartaoSUSDTO);
        CartaoSUS cartaoSUS = cartaoSUSMapper.toEntity(cartaoSUSDTO);
        cartaoSUS = cartaoSUSRepository.save(cartaoSUS);
        CartaoSUSDTO result = cartaoSUSMapper.toDto(cartaoSUS);
        cartaoSUSSearchRepository.save(cartaoSUS);
        return result;
    }

    /**
     * Get all the cartaoSUSES.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CartaoSUSDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CartaoSUSES");
        return cartaoSUSRepository.findAll(pageable)
            .map(cartaoSUSMapper::toDto);
    }


    /**
     *  Get all the cartaoSUSES where Paciente is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CartaoSUSDTO> findAllWherePacienteIsNull() {
        log.debug("Request to get all cartaoSUSES where Paciente is null");
        return StreamSupport
            .stream(cartaoSUSRepository.findAll().spliterator(), false)
            .filter(cartaoSUS -> cartaoSUS.getPaciente() == null)
            .map(cartaoSUSMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cartaoSUS by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CartaoSUSDTO> findOne(Long id) {
        log.debug("Request to get CartaoSUS : {}", id);
        return cartaoSUSRepository.findById(id)
            .map(cartaoSUSMapper::toDto);
    }

    /**
     * Delete the cartaoSUS by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CartaoSUS : {}", id);
        cartaoSUSRepository.deleteById(id);
        cartaoSUSSearchRepository.deleteById(id);
    }

    /**
     * Search for the cartaoSUS corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CartaoSUSDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CartaoSUSES for query {}", query);
        return cartaoSUSSearchRepository.search(queryStringQuery(query), pageable)
            .map(cartaoSUSMapper::toDto);
    }
}
