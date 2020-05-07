package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.TipoDaReservaDeLeito;
import br.com.basis.madre.repository.TipoDaReservaDeLeitoRepository;
import br.com.basis.madre.repository.search.TipoDaReservaDeLeitoSearchRepository;
import br.com.basis.madre.service.dto.TipoDaReservaDeLeitoDTO;
import br.com.basis.madre.service.mapper.TipoDaReservaDeLeitoMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TipoDaReservaDeLeitoService {

    private final Logger log = LoggerFactory.getLogger(TipoDaReservaDeLeitoService.class);

    private final TipoDaReservaDeLeitoRepository tipoDaReservaDeLeitoRepository;

    private final TipoDaReservaDeLeitoMapper tipoDaReservaDeLeitoMapper;

    private final TipoDaReservaDeLeitoSearchRepository tipoDaReservaDeLeitoSearchRepository;

    /**
     * Save a tipoDaReservaDeLeito.
     *
     * @param tipoDaReservaDeLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoDaReservaDeLeitoDTO save(TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO) {
        log.debug("Request to save TipoDaReservaDeLeito : {}", tipoDaReservaDeLeitoDTO);
        TipoDaReservaDeLeito tipoDaReservaDeLeito = tipoDaReservaDeLeitoMapper
            .toEntity(tipoDaReservaDeLeitoDTO);
        tipoDaReservaDeLeito = tipoDaReservaDeLeitoRepository.save(tipoDaReservaDeLeito);
        TipoDaReservaDeLeitoDTO result = tipoDaReservaDeLeitoMapper.toDto(tipoDaReservaDeLeito);
        tipoDaReservaDeLeitoSearchRepository.save(tipoDaReservaDeLeito);
        return result;
    }

    /**
     * Get all the tipoDaReservaDeLeitos.
     *
     * @param tipoDaReservaDeLeitoDTO
     * @param pageable                the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TipoDaReservaDeLeitoDTO> findAll(
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO,
        Pageable pageable) {
        return tipoDaReservaDeLeitoRepository.findAll(
            Example.of(tipoDaReservaDeLeitoMapper.toEntity(tipoDaReservaDeLeitoDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable.getSort()).stream().map(tipoDaReservaDeLeitoMapper::toDto).collect(
            Collectors.toList());
    }


    /**
     * Get one tipoDaReservaDeLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoDaReservaDeLeitoDTO> findOne(Long id) {
        log.debug("Request to get TipoDaReservaDeLeito : {}", id);
        return tipoDaReservaDeLeitoRepository.findById(id)
            .map(tipoDaReservaDeLeitoMapper::toDto);
    }

    /**
     * Delete the tipoDaReservaDeLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoDaReservaDeLeito : {}", id);
        tipoDaReservaDeLeitoRepository.deleteById(id);
        tipoDaReservaDeLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDaReservaDeLeito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDaReservaDeLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDaReservaDeLeitos for query {}", query);
        return tipoDaReservaDeLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoDaReservaDeLeitoMapper::toDto);
    }
}
