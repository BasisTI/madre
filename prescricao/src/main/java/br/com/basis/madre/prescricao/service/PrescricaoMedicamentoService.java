package br.com.basis.madre.prescricao.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.Medicamento;
import br.com.basis.madre.prescricao.domain.Paciente;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.enumeration.TipoEvento;
import br.com.basis.madre.prescricao.domain.evento.EventoPrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.PrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.PacienteRepositorySearch;

import br.com.basis.madre.prescricao.repository.search.PrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicamentoDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoMedicamentoMapper;

/**
 * Service Implementation for managing {@link PrescricaoMedicamento}.
 */
@Service
@Transactional
public class PrescricaoMedicamentoService {

    private final Logger log = LoggerFactory.getLogger(PrescricaoMedicamentoService.class);

    private final PrescricaoMedicamentoRepository prescricaoMedicamentoRepository;
    
    private final MedicamentoService medicamentoService;

    private final PrescricaoMedicamentoMapper prescricaoMedicamentoMapper;

    private final PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository;

    
    private final PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository1;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final PacienteRepositorySearch pacienteRepositorySearch;

    private final AuthenticationPrincipalService authenticationPrincipalService;

    public PrescricaoMedicamentoService(PrescricaoMedicamentoRepository prescricaoMedicamentoRepository,
            PrescricaoMedicamentoMapper prescricaoMedicamentoMapper,
            PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository,
            ApplicationEventPublisher applicationEventPublisher,
            AuthenticationPrincipalService authenticationPrincipalService,
            PacienteRepositorySearch pacienteRepositorySearch,
            MedicamentoService medicamentoService,
            PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository1) {

        this.prescricaoMedicamentoRepository = prescricaoMedicamentoRepository;
        this.prescricaoMedicamentoMapper = prescricaoMedicamentoMapper;
        this.prescricaoMedicamentoSearchRepository = prescricaoMedicamentoSearchRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authenticationPrincipalService = authenticationPrincipalService;
        this.pacienteRepositorySearch = pacienteRepositorySearch;
        this.medicamentoService = medicamentoService;
        this.prescricaoMedicamentoSearchRepository1 = prescricaoMedicamentoSearchRepository1;
    }

    /**
     * Save a prescricaoMedicamento.
     *
     * @param prescricaoMedicamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PrescricaoMedicamentoDTO save(PrescricaoMedicamentoDTO prescricaoMedicamentoDTO) {
        log.debug("Request to save PrescricaoMedicamento : {}", prescricaoMedicamentoDTO);
        PrescricaoMedicamento prescricaoMedicamento = prescricaoMedicamentoMapper.toEntity(prescricaoMedicamentoDTO);
        Paciente pacienteId = pacienteRepositorySearch.findById(prescricaoMedicamento.getIdPaciente())
                .orElseThrow(EntityNotFoundException::new);
        
        for (ItemPrescricaoMedicamento item : prescricaoMedicamento.getItemPrescricaoMedicamentos()) {
            item.setPrescricaoMedicamento(prescricaoMedicamento);
            item.setMedicamento(medicamentoService.findById(item.getIdMedicamento()));

        }
        prescricaoMedicamento = prescricaoMedicamentoRepository.save(prescricaoMedicamento);
        PrescricaoMedicamentoDTO result = prescricaoMedicamentoMapper.toDto(prescricaoMedicamento);

        prescricaoMedicamentoSearchRepository.save(prescricaoMedicamento);

        applicationEventPublisher.publishEvent(EventoPrescricaoMedicamento.builder()
                .login(authenticationPrincipalService.getLoginAtivo()).prescricaoMedicamento(prescricaoMedicamento)
                .paciente(pacienteId).dataDeLancamento(ZonedDateTime.now(ZoneId.systemDefault()))
                .tipoDoEvento(TipoEvento.CRIACAO).build());
        return result;
    }

    /**
     * Get all the prescricaoMedicamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoMedicamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrescricaoMedicamentos");
        return prescricaoMedicamentoRepository.findAll(pageable).map(prescricaoMedicamentoMapper::toDto);
    }

    /**
     * Get one prescricaoMedicamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrescricaoMedicamentoDTO> findOne(Long id) {
        log.debug("Request to get PrescricaoMedicamento : {}", id);
        return prescricaoMedicamentoRepository.findById(id).map(prescricaoMedicamentoMapper::toDto);
    }

    /**
     * Delete the prescricaoMedicamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrescricaoMedicamento : {}", id);
        prescricaoMedicamentoRepository.deleteById(id);
        prescricaoMedicamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the prescricaoMedicamento corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoMedicamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrescricaoMedicamentos for query {}", query);
        return prescricaoMedicamentoSearchRepository.search(queryStringQuery(query), pageable)
                .map(prescricaoMedicamentoMapper::toDto);
    }
}
