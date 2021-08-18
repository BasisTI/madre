package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.HorarioAgendado;
import br.com.basis.madre.madreexames.repository.HorarioAgendadoRepository;
import br.com.basis.madre.madreexames.repository.search.HorarioAgendadoSearchRepository;
import br.com.basis.madre.madreexames.service.dto.HorarioAgendadoDTO;
import br.com.basis.madre.madreexames.service.mapper.HorarioAgendadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link HorarioAgendado}.
 */
@Service
@Transactional
public class HorarioAgendadoService {

    private final Logger log = LoggerFactory.getLogger(HorarioAgendadoService.class);

    private final HorarioAgendadoRepository horarioAgendadoRepository;

    private final HorarioAgendadoMapper horarioAgendadoMapper;

    private final HorarioAgendadoSearchRepository horarioAgendadoSearchRepository;

    public HorarioAgendadoService(HorarioAgendadoRepository horarioAgendadoRepository, HorarioAgendadoMapper horarioAgendadoMapper, HorarioAgendadoSearchRepository horarioAgendadoSearchRepository) {
        this.horarioAgendadoRepository = horarioAgendadoRepository;
        this.horarioAgendadoMapper = horarioAgendadoMapper;
        this.horarioAgendadoSearchRepository = horarioAgendadoSearchRepository;
    }

    /**
     * Save a horarioAgendado.
     *
     * @param horarioAgendadoDTO the entity to save.
     * @return the persisted entity.
     */
    public HorarioAgendadoDTO save(HorarioAgendadoDTO horarioAgendadoDTO) {
        log.debug("Request to save HorarioAgendado : {}", horarioAgendadoDTO);
        HorarioAgendado horarioAgendado = horarioAgendadoMapper.toEntity(horarioAgendadoDTO);
        horarioAgendado = horarioAgendadoRepository.save(horarioAgendado);
        HorarioAgendadoDTO result = horarioAgendadoMapper.toDto(horarioAgendado);
        horarioAgendadoSearchRepository.save(horarioAgendado);
        return result;
    }

    /**
     * Get all the horarioAgendados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HorarioAgendadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HorarioAgendados");
        return horarioAgendadoRepository.findAll(pageable)
            .map(horarioAgendadoMapper::toDto);
    }


    /**
     * Get one horarioAgendado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HorarioAgendadoDTO> findOne(Long id) {
        log.debug("Request to get HorarioAgendado : {}", id);
        return horarioAgendadoRepository.findById(id)
            .map(horarioAgendadoMapper::toDto);
    }

    /**
     * Delete the horarioAgendado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HorarioAgendado : {}", id);
        horarioAgendadoRepository.deleteById(id);
        horarioAgendadoSearchRepository.deleteById(id);
    }

    /**
     * Search for the horarioAgendado corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HorarioAgendadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HorarioAgendados for query {}", query);
        return horarioAgendadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(horarioAgendadoMapper::toDto);
    }
}
