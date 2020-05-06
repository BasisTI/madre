package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.ReservaDeLeito;
import br.com.basis.madre.repository.ReservaDeLeitoRepository;
import br.com.basis.madre.repository.search.ReservaDeLeitoSearchRepository;
import br.com.basis.madre.service.dto.ReservaDeLeitoDTO;
import br.com.basis.madre.service.mapper.ReservaDeLeitoMapper;
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
public class ReservaDeLeitoService {

    private final Logger log = LoggerFactory.getLogger(ReservaDeLeitoService.class);

    private final ReservaDeLeitoRepository reservaDeLeitoRepository;

    private final ReservaDeLeitoMapper reservaDeLeitoMapper;

    private final ReservaDeLeitoSearchRepository reservaDeLeitoSearchRepository;

    /**
     * Save a reservaDeLeito.
     *
     * @param reservaDeLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public ReservaDeLeitoDTO save(ReservaDeLeitoDTO reservaDeLeitoDTO) {
        log.debug("Request to save ReservaDeLeito : {}", reservaDeLeitoDTO);
        ReservaDeLeito reservaDeLeito = reservaDeLeitoMapper.toEntity(reservaDeLeitoDTO);
        reservaDeLeito = reservaDeLeitoRepository.save(reservaDeLeito);
        ReservaDeLeitoDTO result = reservaDeLeitoMapper.toDto(reservaDeLeito);
        reservaDeLeitoSearchRepository.save(reservaDeLeito);
        return result;
    }

    /**
     * Get all the reservaDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReservaDeLeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReservaDeLeitos");
        return reservaDeLeitoRepository.findAll(pageable)
            .map(reservaDeLeitoMapper::toDto);
    }


    /**
     * Get one reservaDeLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReservaDeLeitoDTO> findOne(Long id) {
        log.debug("Request to get ReservaDeLeito : {}", id);
        return reservaDeLeitoRepository.findById(id)
            .map(reservaDeLeitoMapper::toDto);
    }

    /**
     * Delete the reservaDeLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReservaDeLeito : {}", id);
        reservaDeLeitoRepository.deleteById(id);
        reservaDeLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the reservaDeLeito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReservaDeLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReservaDeLeitos for query {}", query);
        return reservaDeLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(reservaDeLeitoMapper::toDto);
    }
}
