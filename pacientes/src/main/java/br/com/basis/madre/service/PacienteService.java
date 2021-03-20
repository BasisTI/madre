package br.com.basis.madre.service;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.enumeration.TipoEvento;
import br.com.basis.madre.domain.evento.EventoPaciente;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import br.com.basis.madre.service.dto.PacienteDTO;
import br.com.basis.madre.service.dto.PacienteInclusaoDTO;
import br.com.basis.madre.service.mapper.PacienteInclusaoMapper;
import br.com.basis.madre.service.mapper.PacienteMapper;
import br.com.basis.madre.service.projection.PacienteResumo;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

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

    private final ApplicationEventPublisher applicationEventPublisher;

    private final AuthenticationPrincipalService authenticationPrincipalService;

    private final ProntuarioService prontuarioService;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper, PacienteSearchRepository pacienteSearchRepository, PacienteInclusaoMapper pacienteInclusaoMapper, ApplicationEventPublisher applicationEventPublisher, AuthenticationPrincipalService authenticationPrincipalService, ProntuarioService prontuarioService) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
        this.pacienteSearchRepository = pacienteSearchRepository;
        this.pacienteInclusaoMapper = pacienteInclusaoMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authenticationPrincipalService = authenticationPrincipalService;
        this.prontuarioService = prontuarioService;
    }

    /**
     * TODO: Write documentation
     */
    public Page<PacienteResumo> findAllProjectedPacienteResumoBy(String nome,Pageable pageable) {
        return pacienteRepository.findAllProjectedPacienteResumoByNomeContainingIgnoreCase(nome, pageable);
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
        paciente.setProntuario(prontuarioService.gerarProntuario());
        pacienteSearchRepository.save(paciente);
        applicationEventPublisher.publishEvent(
            EventoPaciente
                .builder()
                .login(authenticationPrincipalService.getLoginAtivo())
                .paciente(paciente)
                .dataDeLancamento(ZonedDateTime.now(ZoneId.systemDefault()))
                .tipoDoEvento(TipoEvento.CRIACAO)
                .build()
        );
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

    @Transactional(readOnly = true)
    public Optional<PacienteInclusaoDTO> findOneProntuario(Long prontuario) {
        log.debug("Request to get Paciente : {}", prontuario);
        return pacienteRepository.findByProntuario(prontuario)
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

    public Page<Paciente> findPacienteByNomeOrProntuario(String nome, Long prontuario, Pageable pageable) {
        if(Strings.isNullOrEmpty(nome) && prontuario == null){
            log.debug("Request to find all Pacientes");
            return pacienteSearchRepository.findAll(pageable);
        }
        if(!Strings.isNullOrEmpty(nome) && prontuario == null){
            log.debug("Request to find Paciente by name: {}", nome);
            MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery(nome).field("nome").type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fuzziness(4);
            return pacienteSearchRepository.search(query,pageable);
        }
        log.debug("Request to find Paciente by prontuario: {}", prontuario);
        List<Paciente> list = pacienteSearchRepository.findByProntuario(prontuario);
        Page<Paciente> page = new PageImpl<>(list,pageable,list.size());
        return page;
    }
}

