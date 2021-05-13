package br.com.basis.madre.service;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.domain.enumeration.TipoEvento;
import br.com.basis.madre.domain.evento.EventoTriagem;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.repository.TriagemRepository;
import br.com.basis.madre.repository.search.TriagemSearchRepository;
import br.com.basis.madre.service.dto.TriagemDTO;
import br.com.basis.madre.service.mapper.TriagemMapper;
import br.com.basis.madre.service.projection.TriagemProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Triagem.
 */
@Service
@Transactional
public class TriagemService {

    private final Logger log = LoggerFactory.getLogger(TriagemService.class);

    private final TriagemRepository triagemRepository;

    private final TriagemMapper triagemMapper;

    private final TriagemSearchRepository triagemSearchRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final AuthenticationPrincipalService authenticationPrincipalService;

    private final PacienteRepository pacienteRepository;


    @Value("${application.elasticSearchFuzzyParameter}")
    private static Integer elasticSearchFuzzyParam;

    public TriagemService(TriagemRepository triagemRepository, TriagemMapper triagemMapper, TriagemSearchRepository triagemSearchRepository, ApplicationEventPublisher applicationEventPublisher, AuthenticationPrincipalService authenticationPrincipalService, PacienteRepository pacienteRepository) {
        this.triagemRepository = triagemRepository;
        this.triagemMapper = triagemMapper;
        this.triagemSearchRepository = triagemSearchRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authenticationPrincipalService = authenticationPrincipalService;
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Save a triagem.
     *
     * @param triagemDTO the entity to save
     * @return the persisted entity
     */

    public TriagemDTO save(TriagemDTO triagemDTO) {
        log.debug("Request to save Triagem : {}", triagemDTO);
        Triagem triagem = triagemMapper.toEntity(triagemDTO);
        Optional<Paciente> paciente = pacienteRepository.findById(triagemDTO.getPacienteId());
        triagem.setPaciente(paciente.get());
        triagem = triagemRepository.save(triagem);
        triagemSearchRepository.save(triagem);
        TriagemDTO result = triagemMapper.toDto(triagem);
        applicationEventPublisher.publishEvent(
            EventoTriagem
                .builder()
                .login(authenticationPrincipalService.getLoginAtivo())
                .triagem(triagem)
                .dataDeLancamento(ZonedDateTime.now(ZoneId.systemDefault()))
                .tipoDoEvento(TipoEvento.CRIACAO)
                .build()
        );
        return result;
    }

    /**
     * Get all the triagems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TriagemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Triagems");
        return triagemRepository.findAll(pageable)
            .map(triagemMapper::toDto);
    }


    /**
     * Get one triagem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TriagemDTO> findOne(Long id) {
        log.debug("Request to get Triagem : {}", id);
        return triagemRepository.findById(id)
            .map(triagemMapper::toDto);
    }

    /**
     * Delete the triagem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Triagem : {}", id);
        triagemRepository.deleteById(id);
        triagemSearchRepository.deleteById(id);
    }

    /**
     * Search for the triagem corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TriagemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Triagems for query {}", query);
        return triagemSearchRepository.search(queryStringQuery(query), pageable)
            .map(triagemMapper::toDto);
    }

    public Page<Triagem> listarTriagem(String query, Pageable pageable) {
        Page<Triagem> page = triagemSearchRepository.search(queryStringQuery(query), pageable);
        List<Triagem> list = page.toList().stream().sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList());
        page = new PageImpl<Triagem>(list , pageable, page.getTotalElements());
        log.debug("Request to search for a page of Triagems for query {}", query);
        return page;
    }

    public Page<TriagemProjection> buscarResumoTriagem(Pageable pageable) {
        Page<TriagemProjection> page = triagemRepository.findAllResumoTriagemBy(pageable);
        List<TriagemProjection> list = page.stream().sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList());
        page = new PageImpl<>(list, pageable, list.size());
        return page;
    }
}
