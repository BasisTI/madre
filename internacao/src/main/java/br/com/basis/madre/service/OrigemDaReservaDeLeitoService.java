package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.OrigemDaReservaDeLeito;
import br.com.basis.madre.repository.OrigemDaReservaDeLeitoRepository;
import br.com.basis.madre.repository.search.OrigemDaReservaDeLeitoSearchRepository;
import br.com.basis.madre.service.dto.OrigemDaReservaDeLeitoDTO;
import br.com.basis.madre.service.mapper.OrigemDaReservaDeLeitoMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class OrigemDaReservaDeLeitoService {

    private final Logger log = LoggerFactory.getLogger(OrigemDaReservaDeLeitoService.class);

    private final OrigemDaReservaDeLeitoRepository origemDaReservaDeLeitoRepository;

    private final OrigemDaReservaDeLeitoMapper origemDaReservaDeLeitoMapper;

    private final OrigemDaReservaDeLeitoSearchRepository origemDaReservaDeLeitoSearchRepository;

    /**
     * Save a origemDaReservaDeLeito.
     *
     * @param origemDaReservaDeLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public OrigemDaReservaDeLeitoDTO save(OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO) {
        log.debug("Request to save OrigemDaReservaDeLeito : {}", origemDaReservaDeLeitoDTO);
        OrigemDaReservaDeLeito origemDaReservaDeLeito = origemDaReservaDeLeitoMapper
            .toEntity(origemDaReservaDeLeitoDTO);
        origemDaReservaDeLeito = origemDaReservaDeLeitoRepository.save(origemDaReservaDeLeito);
        OrigemDaReservaDeLeitoDTO result = origemDaReservaDeLeitoMapper
            .toDto(origemDaReservaDeLeito);
        origemDaReservaDeLeitoSearchRepository.save(origemDaReservaDeLeito);
        return result;
    }

    /**
     * Get all the origemDaReservaDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrigemDaReservaDeLeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrigemDaReservaDeLeitos");
        return origemDaReservaDeLeitoRepository.findAll(pageable)
            .map(origemDaReservaDeLeitoMapper::toDto);
    }


    /**
     * Get one origemDaReservaDeLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrigemDaReservaDeLeitoDTO> findOne(Long id) {
        log.debug("Request to get OrigemDaReservaDeLeito : {}", id);
        return origemDaReservaDeLeitoRepository.findById(id)
            .map(origemDaReservaDeLeitoMapper::toDto);
    }

    /**
     * Delete the origemDaReservaDeLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrigemDaReservaDeLeito : {}", id);
        origemDaReservaDeLeitoRepository.deleteById(id);
        origemDaReservaDeLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the origemDaReservaDeLeito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrigemDaReservaDeLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrigemDaReservaDeLeitos for query {}", query);
        return origemDaReservaDeLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(origemDaReservaDeLeitoMapper::toDto);
    }
}
