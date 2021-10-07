package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;
import br.com.basis.madre.madreexames.domain.HorarioExame;
import br.com.basis.madre.madreexames.repository.HorarioExameRepository;
import br.com.basis.madre.madreexames.repository.search.HorarioExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;
import br.com.basis.madre.madreexames.service.dto.HorarioExameDTO;
import br.com.basis.madre.madreexames.service.mapper.HorarioExameMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link HorarioExame}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class HorarioExameService {

    private final Logger log = LoggerFactory.getLogger(HorarioExameService.class);

    private final HorarioExameRepository horarioExameRepository;

    private final HorarioExameMapper horarioExameMapper;

    private final HorarioExameSearchRepository horarioExameSearchRepository;

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
    Instant novaHoraInicio;

    public void gerarHorariosDaGrade(GradeAgendamentoExame gradeAgendamentoExame) {
    novaHoraInicio = gradeAgendamentoExame.getHoraInicio();

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
            novaHoraInicio = obterDataInicioHorario(novaData, novaHoraInicio);
            novaHoraInicio = obterDataInicioHorario(novaData, novaHoraInicio).plus(1, ChronoUnit.MILLIS);
            horarioExame.setHoraInicio(novaHoraInicio);

            horarioExame = horarioExameRepository.save(horarioExame);
            horarioExameSearchRepository.save(horarioExame);

            log.debug("Request to save HorarioExame a partir da grade : {}", horarioExame);
        }
    }

    LocalDate novaData;

    public Instant obterDataInicioHorario(LocalDate data, Instant hora) {
        String horaDaData = hora.toString().substring(hora.toString().indexOf("T"));

        Instant dataCompleta = Instant.parse(data.toString().concat(horaDaData));

        return dataCompleta;
    }

    public void buscarDiasCompativeis(GradeAgendamentoExame gradeAgendamentoExame) {
    novaData = gradeAgendamentoExame.getDataInicio();
        List<Long> comparacao = new ArrayList<>();

        if (gradeAgendamentoExame.getDias().toString().contains("SEGUNDA")) {
            comparacao.add(1L);
        }
        if (gradeAgendamentoExame.getDias().toString().contains("TERCA")) {
            comparacao.add(2L);
        }
        if (gradeAgendamentoExame.getDias().toString().contains("QUARTA")) {
            comparacao.add(3L);
        }
        if (gradeAgendamentoExame.getDias().toString().contains("QUINTA")) {
            comparacao.add(4L);
        }
        if (gradeAgendamentoExame.getDias().toString().contains("SEXTA")) {
            comparacao.add(5L);
        }
        if (gradeAgendamentoExame.getDias().toString().contains("SABADO")) {
            comparacao.add(6L);
        }
        if (gradeAgendamentoExame.getDias().toString().contains("DOMINGO")) {
            comparacao.add(7L);
        }

        LocalDate dataInicioCopia = gradeAgendamentoExame.getDataInicio();

        while (!gradeAgendamentoExame.getDataInicio().isAfter(gradeAgendamentoExame.getDataFim())) {
            Long numeroComparacao;

            for (Long nDias: comparacao) {
                numeroComparacao = nDias;

                if (gradeAgendamentoExame.getDataInicio().getDayOfWeek().getValue() == numeroComparacao) {
                    System.err.println("Dia: " + gradeAgendamentoExame.getDataInicio()
                        .getDayOfWeek() + " em: " + gradeAgendamentoExame.getDataInicio());
                    novaData = gradeAgendamentoExame.getDataInicio();
                    gerarHorariosDaGrade(gradeAgendamentoExame);
                }
            }
            gradeAgendamentoExame.setDataInicio(gradeAgendamentoExame.getDataInicio().plus(1, ChronoUnit.DAYS));

        }

        System.err.println(comparacao);

        gradeAgendamentoExame.setDataInicio(dataInicioCopia);
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

    @Transactional(readOnly = true)
    public Page<HorarioExameDTO> filtraHorariosExame(Pageable pageable, String id, String livre, String ativo,
                                                     String exclusivo, String tipoDeMarcacaoId, String gradeAgendamentoExameId) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        filter(queryBuilder, "id", id);
        filter(queryBuilder, "livre", livre);
        filter(queryBuilder, "ativo", ativo);
        filter(queryBuilder, "exclusivo", exclusivo);
        filter(queryBuilder, "tipoDeMarcacaoId", tipoDeMarcacaoId);
        filter(queryBuilder, "gradeAgendamentoExame.id", gradeAgendamentoExameId);
        SearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable)
            .build();
        return horarioExameSearchRepository.search(query).map(horarioExameMapper::toDto);
    }

    private void filter(BoolQueryBuilder queryBuilder, String name, String valueName) {
        if (!Strings.isNullOrEmpty(valueName)) {
            queryBuilder.must(QueryBuilders.matchQuery(name, valueName));
        }
    }
}