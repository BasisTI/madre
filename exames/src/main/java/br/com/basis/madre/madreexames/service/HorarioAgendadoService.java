package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.HorarioAgendado;
import br.com.basis.madre.madreexames.repository.HorarioAgendadoRepository;
import br.com.basis.madre.madreexames.repository.search.HorarioAgendadoSearchRepository;
import br.com.basis.madre.madreexames.service.dto.HorarioAgendadoDTO;
import br.com.basis.madre.madreexames.service.mapper.HorarioAgendadoMapper;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

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
        HorarioAgendadoDTO result = horarioAgendadoRepository.buscaPorId(horarioAgendado.getId());
        calcularHoraFinal(result, horarioAgendadoDTO);
        calcularNumeroDeHorarios(result, horarioAgendadoDTO);
        horarioAgendadoSearchRepository.save(result);
        return result;
    }

    public void calcularHoraFinal(HorarioAgendadoDTO result, HorarioAgendadoDTO horarioAgendadoDTO) {
        if (horarioAgendadoDTO.getHoraFim() == null) {
            result.setHoraFim(horarioAgendadoDTO.getHoraInicio()
                    .plus(horarioAgendadoDTO.getDuracao()
                        .toMinutes() * horarioAgendadoDTO.getNumeroDeHorarios(), ChronoUnit.MINUTES));
        }
    }

    public void calcularNumeroDeHorarios(HorarioAgendadoDTO result, HorarioAgendadoDTO horarioAgendadoDTO) {
        if (horarioAgendadoDTO.getNumeroDeHorarios() == null) {
            Duration intervaloInicioFim = Duration.between(horarioAgendadoDTO.getHoraInicio(),
                horarioAgendadoDTO.getHoraFim());
            result.setNumeroDeHorarios((int) ((int) intervaloInicioFim.minus(intervaloInicioFim
                .minusSeconds(intervaloInicioFim.getSeconds()))
                .toMinutes() / horarioAgendadoDTO.getDuracao().toMinutes()));

        }
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
        return horarioAgendadoSearchRepository.search(queryStringQuery(query), pageable);
    }

    @Transactional(readOnly = true)
    public Page<HorarioAgendadoDTO> filtraHorarioAgendado(Pageable pageable, String id, String horaInicio, String horaFim,
       String numeroDeHorarios, String dia, String duracao, String ativo, String exclusivo) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        filter(queryBuilder, "id", id);
        filter(queryBuilder, "horaInicio", horaInicio);
        filter(queryBuilder, "horaFim", horaFim);
        filter(queryBuilder, "numeroDeHorarios", numeroDeHorarios);
        filter(queryBuilder, "dia", dia);
        filter(queryBuilder, "duracao", duracao);
        filter(queryBuilder, "ativo", ativo);
        filter(queryBuilder, "exclusivo", exclusivo);
        SearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable)
            .build();
        return horarioAgendadoSearchRepository.search(query);
    }

    private void filter(BoolQueryBuilder queryBuilder, String name, String valueName) {
        if (!Strings.isNullOrEmpty(valueName)) {
            queryBuilder.must(QueryBuilders.matchQuery(name,valueName));
        }
    }
}
