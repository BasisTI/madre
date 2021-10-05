package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;
import br.com.basis.madre.madreexames.domain.HorarioExame;
import br.com.basis.madre.madreexames.repository.HorarioExameRepository;
import br.com.basis.madre.madreexames.repository.search.HorarioExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;
import br.com.basis.madre.madreexames.service.dto.HorarioExameDTO;
import br.com.basis.madre.madreexames.service.mapper.HorarioExameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link HorarioExame}.
 */
@Service
@Transactional
public class HorarioExameService {

    private final Logger log = LoggerFactory.getLogger(HorarioExameService.class);

    private final HorarioExameRepository horarioExameRepository;

    private final HorarioExameMapper horarioExameMapper;

    private final HorarioExameSearchRepository horarioExameSearchRepository;

    public HorarioExameService(HorarioExameRepository horarioExameRepository, HorarioExameMapper horarioExameMapper, HorarioExameSearchRepository horarioExameSearchRepository) {
        this.horarioExameRepository = horarioExameRepository;
        this.horarioExameMapper = horarioExameMapper;
        this.horarioExameSearchRepository = horarioExameSearchRepository;
    }

    /**
     * Save a horarioExame.
     *
     * @param horarioExameDTO the entity to save.
     * @return the persisted entity.
     */
    public HorarioExameDTO save(HorarioExameDTO horarioExameDTO) {
        log.debug("Request to save HorarioExame : {}", horarioExameDTO);
        HorarioExame horarioExame = horarioExameMapper.toEntity(horarioExameDTO);
        horarioExame = horarioExameRepository.save(horarioExame);
        HorarioExameDTO result = horarioExameMapper.toDto(horarioExame);
        horarioExameSearchRepository.save(horarioExame);
        return result;
    }

    public void gerarHorariosDaGrade(GradeAgendamentoExame gradeAgendamentoExame) {
        Instant novaHoraInicio = null;

        for (int i = 0; i < gradeAgendamentoExame.getNumeroDeHorarios(); i++) {
            HorarioExame horarioExame = new HorarioExame();
            if (i == 0) {
                horarioExame.setHoraInicio(gradeAgendamentoExame.getHoraInicio());
            } else {
                horarioExame.setHoraInicio(novaHoraInicio);
            }
            horarioExame.setHoraFim(horarioExame.getHoraInicio().plus(gradeAgendamentoExame.getDuracao()));
            horarioExame.setAtivo(gradeAgendamentoExame.isAtivo());
            horarioExame.setLivre(true);
            horarioExame.setExclusivo(false);
            horarioExame.setGradeAgendamentoExame(gradeAgendamentoExame);
            novaHoraInicio = horarioExame.getHoraFim().plus(1, ChronoUnit.MINUTES);

            horarioExame = horarioExameRepository.save(horarioExame);
            horarioExameSearchRepository.save(horarioExame);

            log.debug("Request to save HorarioExame a partir da grade : {}", horarioExame);
        }
    }

    /**
     * Get all the horarioExames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HorarioExameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HorarioExames");
        return horarioExameRepository.findAll(pageable)
            .map(horarioExameMapper::toDto);
    }


    /**
     * Get one horarioExame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HorarioExameDTO> findOne(Long id) {
        log.debug("Request to get HorarioExame : {}", id);
        return horarioExameRepository.findById(id)
            .map(horarioExameMapper::toDto);
    }

    /**
     * Delete the horarioExame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HorarioExame : {}", id);
        horarioExameRepository.deleteById(id);
        horarioExameSearchRepository.deleteById(id);
    }

    /**
     * Search for the horarioExame corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HorarioExameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HorarioExames for query {}", query);
        return horarioExameSearchRepository.search(queryStringQuery(query), pageable)
            .map(horarioExameMapper::toDto);
    }
}
