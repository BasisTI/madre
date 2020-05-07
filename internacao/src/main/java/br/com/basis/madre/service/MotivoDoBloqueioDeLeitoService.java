package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.MotivoDoBloqueioDeLeito;
import br.com.basis.madre.repository.MotivoDoBloqueioDeLeitoRepository;
import br.com.basis.madre.repository.search.MotivoDoBloqueioDeLeitoSearchRepository;
import br.com.basis.madre.service.dto.MotivoDoBloqueioDeLeitoDTO;
import br.com.basis.madre.service.mapper.MotivoDoBloqueioDeLeitoMapper;
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
public class MotivoDoBloqueioDeLeitoService {

    private final Logger log = LoggerFactory.getLogger(MotivoDoBloqueioDeLeitoService.class);

    private final MotivoDoBloqueioDeLeitoRepository motivoDoBloqueioDeLeitoRepository;

    private final MotivoDoBloqueioDeLeitoMapper motivoDoBloqueioDeLeitoMapper;

    private final MotivoDoBloqueioDeLeitoSearchRepository motivoDoBloqueioDeLeitoSearchRepository;

    /**
     * Save a motivoDoBloqueioDeLeito.
     *
     * @param motivoDoBloqueioDeLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public MotivoDoBloqueioDeLeitoDTO save(MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO) {
        log.debug("Request to save MotivoDoBloqueioDeLeito : {}", motivoDoBloqueioDeLeitoDTO);
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito = motivoDoBloqueioDeLeitoMapper
            .toEntity(motivoDoBloqueioDeLeitoDTO);
        motivoDoBloqueioDeLeito = motivoDoBloqueioDeLeitoRepository.save(motivoDoBloqueioDeLeito);
        MotivoDoBloqueioDeLeitoDTO result = motivoDoBloqueioDeLeitoMapper
            .toDto(motivoDoBloqueioDeLeito);
        motivoDoBloqueioDeLeitoSearchRepository.save(motivoDoBloqueioDeLeito);
        return result;
    }

    /**
     * Get all the motivoDoBloqueioDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MotivoDoBloqueioDeLeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MotivoDoBloqueioDeLeitos");
        return motivoDoBloqueioDeLeitoRepository.findAll(pageable)
            .map(motivoDoBloqueioDeLeitoMapper::toDto);
    }


    /**
     * Get one motivoDoBloqueioDeLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MotivoDoBloqueioDeLeitoDTO> findOne(Long id) {
        log.debug("Request to get MotivoDoBloqueioDeLeito : {}", id);
        return motivoDoBloqueioDeLeitoRepository.findById(id)
            .map(motivoDoBloqueioDeLeitoMapper::toDto);
    }

    /**
     * Delete the motivoDoBloqueioDeLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MotivoDoBloqueioDeLeito : {}", id);
        motivoDoBloqueioDeLeitoRepository.deleteById(id);
        motivoDoBloqueioDeLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the motivoDoBloqueioDeLeito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MotivoDoBloqueioDeLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MotivoDoBloqueioDeLeitos for query {}", query);
        return motivoDoBloqueioDeLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(motivoDoBloqueioDeLeitoMapper::toDto);
    }
}
