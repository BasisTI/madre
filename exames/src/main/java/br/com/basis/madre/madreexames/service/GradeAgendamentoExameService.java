package br.com.basis.madre.madreexames.service;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;
import br.com.basis.madre.madreexames.repository.GradeAgendamentoExameRepository;
import br.com.basis.madre.madreexames.repository.search.GradeAgendamentoExameSearchRepository;
import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;
import br.com.basis.madre.madreexames.service.integration.InternacaoClient;
import br.com.basis.madre.madreexames.service.integration.SegurancaClient;
import br.com.basis.madre.madreexames.service.mapper.GradeAgendamentoExameMapper;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GradeAgendamentoExame}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class GradeAgendamentoExameService {

    private final Logger log = LoggerFactory.getLogger(GradeAgendamentoExameService.class);

    private final GradeAgendamentoExameRepository gradeAgendamentoExameRepository;

    private final GradeAgendamentoExameMapper gradeAgendamentoExameMapper;

    private final GradeAgendamentoExameSearchRepository gradeAgendamentoExameSearchRepository;

    private final InternacaoClient internacaoClient;

    private final SegurancaClient segurancaClient;


    /**
     * Save a gradeAgendamentoExame.
     *
     * @param gradeAgendamentoExameDTO the entity to save.
     * @return the persisted entity.
     */
    public GradeAgendamentoExameDTO save(GradeAgendamentoExameDTO gradeAgendamentoExameDTO) {
        log.debug("Request to save GradeAgendamentoExame : {}", gradeAgendamentoExameDTO);
        calcularDuracaoDeHorarios(gradeAgendamentoExameDTO);
        GradeAgendamentoExame gradeAgendamentoExame = gradeAgendamentoExameMapper.toEntity(gradeAgendamentoExameDTO);
        gradeAgendamentoExameRepository.save(gradeAgendamentoExame);
        GradeAgendamentoExameDTO result = gradeAgendamentoExameRepository.buscaPorId(gradeAgendamentoExame.getId());
        result.setResponsavelNome("calígula");
        passarDadosParaResult(result, gradeAgendamentoExame);
        result.setUnidadeNome(internacaoClient.getUnidadeFuncional(gradeAgendamentoExameDTO.getUnidadeExecutoraId()).getNome());
        result.setResponsavelNome(segurancaClient.getServidor(gradeAgendamentoExameDTO.getResponsavelId())
            .getPessoaNome());

        gradeAgendamentoExameSearchRepository.save(result);
        return result;
    }

    public void passarDadosParaResult(GradeAgendamentoExameDTO result, GradeAgendamentoExame fonte) {
        result.setHoraInicio(fonte.getHoraInicio());
        result.setHoraFim(fonte.getHoraFim());
        result.setDataInicio(fonte.getDataInicio());
        result.setDataFim(fonte.getDataFim());
        result.setSalaId(fonte.getSala().getId());
        result.setExameId(fonte.getExame().getId());
        result.setDias(fonte.getDias());
        result.setResponsavelId(fonte.getResponsavelId());
        result.setNumeroDeHorarios(fonte.getNumeroDeHorarios());
        result.setAtivo(fonte.isAtivo());
        result.setDuracao(fonte.getDuracao());
    }

    public void calcularDuracaoDeHorarios(GradeAgendamentoExameDTO gradeAgendamentoExameDTO) {
        LocalTime horaInicio = LocalTime.of(gradeAgendamentoExameDTO.getHoraInicio().atZone(ZoneId.systemDefault())
            .getHour(), gradeAgendamentoExameDTO.getHoraInicio().atZone(ZoneId.systemDefault()).getMinute());

        LocalTime horaFim = LocalTime.of(gradeAgendamentoExameDTO.getHoraFim().atZone(ZoneId.systemDefault())
            .getHour(), gradeAgendamentoExameDTO.getHoraFim().atZone(ZoneId.systemDefault()).getMinute());

        Duration intervaloInicioFim = Duration.between(horaInicio, horaFim);

        gradeAgendamentoExameDTO.setDuracao(intervaloInicioFim.dividedBy(gradeAgendamentoExameDTO
            .getNumeroDeHorarios()));
    }

    /**
     * Get all the gradeAgendamentoExames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GradeAgendamentoExameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GradeAgendamentoExames");
        return gradeAgendamentoExameRepository.findAll(pageable)
            .map(gradeAgendamentoExameMapper::toDto);
    }


    /**
     * Get all the gradeAgendamentoExames with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GradeAgendamentoExameDTO> findAllWithEagerRelationships(Pageable pageable) {
        return gradeAgendamentoExameRepository.findAllWithEagerRelationships(pageable).map(gradeAgendamentoExameMapper::toDto);
    }

    /**
     * Get one gradeAgendamentoExame by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GradeAgendamentoExameDTO> findOne(Long id) {
        log.debug("Request to get GradeAgendamentoExame : {}", id);
        return gradeAgendamentoExameRepository.findOneWithEagerRelationships(id)
            .map(gradeAgendamentoExameMapper::toDto);
    }

    /**
     * Delete the gradeAgendamentoExame by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GradeAgendamentoExame : {}", id);
        gradeAgendamentoExameRepository.deleteById(id);
        gradeAgendamentoExameSearchRepository.deleteById(id);
    }

    /**
     * Search for the gradeAgendamentoExame corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GradeAgendamentoExameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GradeAgendamentoExames for query {}", query);
        return gradeAgendamentoExameSearchRepository.search(queryStringQuery(query), pageable);
    }

    @Transactional(readOnly = true)
    public Page<GradeAgendamentoExameDTO> filtrarGradeAgendamento(Pageable pageable, String id,
        String unidadeExecutoraId, String ativo, String duracao, String responsavelId, String exameId, String salaId) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        filter(queryBuilder, "id", id);
        filter(queryBuilder, "unidadeExecutoraId", unidadeExecutoraId);
        filter(queryBuilder, "ativo", ativo);
        filter(queryBuilder, "duracao", duracao);
        filter(queryBuilder, "responsavelId", responsavelId);
        filter(queryBuilder, "exameId", exameId);
        filter(queryBuilder, "salaId", salaId);
        SearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable)
            .build();

        return gradeAgendamentoExameSearchRepository.search(query);
    }

    private void filter(BoolQueryBuilder queryBuilder, String name, String valueName) {
        if (!Strings.isNullOrEmpty(valueName)) {
            queryBuilder.must(QueryBuilders.matchQuery(name, valueName));
        }
    }
}
