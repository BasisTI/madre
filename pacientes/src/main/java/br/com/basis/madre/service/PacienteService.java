package br.com.basis.madre.service;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.events.EventoPaciente;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import br.com.basis.madre.service.dto.PacienteDTO;
import br.com.basis.madre.service.dto.PacienteInclusaoDTO;
import br.com.basis.madre.service.mapper.PacienteInclusaoMapper;
import br.com.basis.madre.service.mapper.PacienteMapper;
import br.com.basis.madre.service.projection.PacienteResumo;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.EmitterProcessor;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Paciente}.
 */
@Service
@Transactional
public class PacienteService {

    private final Logger log = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    private final PacienteSearchRepository pacienteSearchRepository;

    private final PacienteInclusaoMapper pacienteInclusaoMapper;

    private final EmitterProcessor<EventoPaciente> pacienteEmitterProcessor;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper, PacienteSearchRepository pacienteSearchRepository, PacienteInclusaoMapper pacienteInclusaoMapper, EmitterProcessor<EventoPaciente> pacienteEmitterProcessor) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
        this.pacienteSearchRepository = pacienteSearchRepository;
        this.pacienteInclusaoMapper = pacienteInclusaoMapper;
        this.pacienteEmitterProcessor = pacienteEmitterProcessor;
    }

    /**
     * TODO: Write documentation
     */
    public Page<PacienteResumo> findAllProjectedPacienteResumoBy(Pageable pageable) {
        return pacienteRepository.findAllProjectedPacienteResumoBy(pageable);
    }

    /*lista de pacientes com elasticsearch*/
    public Page<Paciente> findAllElasticPaciente(Pageable pageable) {
        return pacienteSearchRepository.findAll(pageable);

    }

    /**
     * Save a paciente.
     *
     * @param pacienteDTO the entity to save.
     * @return the persisted entity.
     */

    public PacienteInclusaoDTO save(PacienteInclusaoDTO pacienteDTO) {
        log.debug("Request to save Paciente : {}", pacienteDTO);
        Paciente paciente = pacienteInclusaoMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        PacienteInclusaoDTO result = pacienteInclusaoMapper.toDto(paciente);
        pacienteSearchRepository.save(paciente);
        EventoPaciente eventoPaciente = new EventoPaciente(paciente);
        pacienteEmitterProcessor.onNext(eventoPaciente);
        return result;
    }

    /**
     * Get all the pacientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PacienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pacientes");
        return pacienteRepository.findAll(pageable)
            .map(pacienteMapper::toDto);
    }

    /**
     * Get one paciente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PacienteInclusaoDTO> findOne(Long id) {
        log.debug("Request to get Paciente : {}", id);
        return pacienteRepository.findById(id)
            .map(pacienteInclusaoMapper::toDto);
    }

    /**
     * Delete the paciente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Paciente : {}", id);
        pacienteRepository.deleteById(id);
        pacienteSearchRepository.deleteById(id);
    }

    /**
     * Search for the paciente corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PacienteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pacientes for query {}", query);
        return pacienteSearchRepository.search(queryStringQuery(query), pageable)
            .map(pacienteMapper::toDto);
    }

    private ElasticsearchTemplate elasticsearchTemplate;

    public Page<Paciente> buscaPacientePorNome(String nome, Pageable pageable) {


        if (Strings.isNullOrEmpty(nome)) {
            return pacienteSearchRepository.search(new NativeSearchQueryBuilder()
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("nome", "dataDeNascimento", "genitores", "cartaoSUS").build())
                .build());
        }

        return pacienteSearchRepository.search(
            new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.fuzzyQuery("nome", nome))
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("nome", "dataDeNascimento", "genitores", "cartaoSUS").build())

                .build()
        );
    }

}
