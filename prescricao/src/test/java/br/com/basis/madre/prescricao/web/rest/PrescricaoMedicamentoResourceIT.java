package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.PrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static br.com.basis.madre.prescricao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.prescricao.domain.enumeration.UnidadeTempo;
/**
 * Integration tests for the {@link PrescricaoMedicamentoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class PrescricaoMedicamentoResourceIT {

    private static final Long DEFAULT_ID_PACIENTE = 1L;
    private static final Long UPDATED_ID_PACIENTE = 2L;

    private static final Long DEFAULT_ID_MEDICAMENTO = 1L;
    private static final Long UPDATED_ID_MEDICAMENTO = 2L;

    private static final Integer DEFAULT_DOSE = 0;
    private static final Integer UPDATED_DOSE = 1;

    private static final Integer DEFAULT_FREQUENCIA = 0;
    private static final Integer UPDATED_FREQUENCIA = 1;

    private static final Boolean DEFAULT_TODAS_VIAS = false;
    private static final Boolean UPDATED_TODAS_VIAS = true;

    private static final Integer DEFAULT_VELOCIDA_INFUSAO = 0;
    private static final Integer UPDATED_VELOCIDA_INFUSAO = 1;

    private static final UnidadeTempo DEFAULT_UNIDADE_TEMPO = UnidadeTempo.HORA;
    private static final UnidadeTempo UPDATED_UNIDADE_TEMPO = UnidadeTempo.MINUTO;

    private static final LocalDate DEFAULT_INICIO_ADMINISTRACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO_ADMINISTRACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_BOMBA_INFUSAO = false;
    private static final Boolean UPDATED_BOMBA_INFUSAO = true;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private PrescricaoMedicamentoRepository prescricaoMedicamentoRepository;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.PrescricaoMedicamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescricaoMedicamentoSearchRepository mockPrescricaoMedicamentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPrescricaoMedicamentoMockMvc;

    private PrescricaoMedicamento prescricaoMedicamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescricaoMedicamentoResource prescricaoMedicamentoResource = new PrescricaoMedicamentoResource(prescricaoMedicamentoRepository, mockPrescricaoMedicamentoSearchRepository);
        this.restPrescricaoMedicamentoMockMvc = MockMvcBuilders.standaloneSetup(prescricaoMedicamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescricaoMedicamento createEntity(EntityManager em) {
        PrescricaoMedicamento prescricaoMedicamento = new PrescricaoMedicamento()
            .idPaciente(DEFAULT_ID_PACIENTE)
            .idMedicamento(DEFAULT_ID_MEDICAMENTO)
            .dose(DEFAULT_DOSE)
            .frequencia(DEFAULT_FREQUENCIA)
            .todasVias(DEFAULT_TODAS_VIAS)
            .velocidaInfusao(DEFAULT_VELOCIDA_INFUSAO)
            .unidadeTempo(DEFAULT_UNIDADE_TEMPO)
            .inicioAdministracao(DEFAULT_INICIO_ADMINISTRACAO)
            .bombaInfusao(DEFAULT_BOMBA_INFUSAO)
            .observacao(DEFAULT_OBSERVACAO);
        return prescricaoMedicamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescricaoMedicamento createUpdatedEntity(EntityManager em) {
        PrescricaoMedicamento prescricaoMedicamento = new PrescricaoMedicamento()
            .idPaciente(UPDATED_ID_PACIENTE)
            .idMedicamento(UPDATED_ID_MEDICAMENTO)
            .dose(UPDATED_DOSE)
            .frequencia(UPDATED_FREQUENCIA)
            .todasVias(UPDATED_TODAS_VIAS)
            .velocidaInfusao(UPDATED_VELOCIDA_INFUSAO)
            .unidadeTempo(UPDATED_UNIDADE_TEMPO)
            .inicioAdministracao(UPDATED_INICIO_ADMINISTRACAO)
            .bombaInfusao(UPDATED_BOMBA_INFUSAO)
            .observacao(UPDATED_OBSERVACAO);
        return prescricaoMedicamento;
    }

    @BeforeEach
    public void initTest() {
        prescricaoMedicamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescricaoMedicamento() throws Exception {
        int databaseSizeBeforeCreate = prescricaoMedicamentoRepository.findAll().size();

        // Create the PrescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(post("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamento)))
            .andExpect(status().isCreated());

        // Validate the PrescricaoMedicamento in the database
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeCreate + 1);
        PrescricaoMedicamento testPrescricaoMedicamento = prescricaoMedicamentoList.get(prescricaoMedicamentoList.size() - 1);
        assertThat(testPrescricaoMedicamento.getIdPaciente()).isEqualTo(DEFAULT_ID_PACIENTE);
        assertThat(testPrescricaoMedicamento.getIdMedicamento()).isEqualTo(DEFAULT_ID_MEDICAMENTO);
        assertThat(testPrescricaoMedicamento.getDose()).isEqualTo(DEFAULT_DOSE);
        assertThat(testPrescricaoMedicamento.getFrequencia()).isEqualTo(DEFAULT_FREQUENCIA);
        assertThat(testPrescricaoMedicamento.isTodasVias()).isEqualTo(DEFAULT_TODAS_VIAS);
        assertThat(testPrescricaoMedicamento.getVelocidaInfusao()).isEqualTo(DEFAULT_VELOCIDA_INFUSAO);
        assertThat(testPrescricaoMedicamento.getUnidadeTempo()).isEqualTo(DEFAULT_UNIDADE_TEMPO);
        assertThat(testPrescricaoMedicamento.getInicioAdministracao()).isEqualTo(DEFAULT_INICIO_ADMINISTRACAO);
        assertThat(testPrescricaoMedicamento.isBombaInfusao()).isEqualTo(DEFAULT_BOMBA_INFUSAO);
        assertThat(testPrescricaoMedicamento.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(1)).save(testPrescricaoMedicamento);
    }

    @Test
    @Transactional
    public void createPrescricaoMedicamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescricaoMedicamentoRepository.findAll().size();

        // Create the PrescricaoMedicamento with an existing ID
        prescricaoMedicamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescricaoMedicamentoMockMvc.perform(post("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamento)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoMedicamento in the database
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(0)).save(prescricaoMedicamento);
    }


    @Test
    @Transactional
    public void checkIdMedicamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = prescricaoMedicamentoRepository.findAll().size();
        // set the field null
        prescricaoMedicamento.setIdMedicamento(null);

        // Create the PrescricaoMedicamento, which fails.

        restPrescricaoMedicamentoMockMvc.perform(post("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamento)))
            .andExpect(status().isBadRequest());

        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDoseIsRequired() throws Exception {
        int databaseSizeBeforeTest = prescricaoMedicamentoRepository.findAll().size();
        // set the field null
        prescricaoMedicamento.setDose(null);

        // Create the PrescricaoMedicamento, which fails.

        restPrescricaoMedicamentoMockMvc.perform(post("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamento)))
            .andExpect(status().isBadRequest());

        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrescricaoMedicamentos() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);

        // Get all the prescricaoMedicamentoList
        restPrescricaoMedicamentoMockMvc.perform(get("/api/prescricao-medicamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoMedicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].idMedicamento").value(hasItem(DEFAULT_ID_MEDICAMENTO.intValue())))
            .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE)))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA)))
            .andExpect(jsonPath("$.[*].todasVias").value(hasItem(DEFAULT_TODAS_VIAS.booleanValue())))
            .andExpect(jsonPath("$.[*].velocidaInfusao").value(hasItem(DEFAULT_VELOCIDA_INFUSAO)))
            .andExpect(jsonPath("$.[*].unidadeTempo").value(hasItem(DEFAULT_UNIDADE_TEMPO.toString())))
            .andExpect(jsonPath("$.[*].inicioAdministracao").value(hasItem(DEFAULT_INICIO_ADMINISTRACAO.toString())))
            .andExpect(jsonPath("$.[*].bombaInfusao").value(hasItem(DEFAULT_BOMBA_INFUSAO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }
    
    @Test
    @Transactional
    public void getPrescricaoMedicamento() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);

        // Get the prescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(get("/api/prescricao-medicamentos/{id}", prescricaoMedicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescricaoMedicamento.getId().intValue()))
            .andExpect(jsonPath("$.idPaciente").value(DEFAULT_ID_PACIENTE.intValue()))
            .andExpect(jsonPath("$.idMedicamento").value(DEFAULT_ID_MEDICAMENTO.intValue()))
            .andExpect(jsonPath("$.dose").value(DEFAULT_DOSE))
            .andExpect(jsonPath("$.frequencia").value(DEFAULT_FREQUENCIA))
            .andExpect(jsonPath("$.todasVias").value(DEFAULT_TODAS_VIAS.booleanValue()))
            .andExpect(jsonPath("$.velocidaInfusao").value(DEFAULT_VELOCIDA_INFUSAO))
            .andExpect(jsonPath("$.unidadeTempo").value(DEFAULT_UNIDADE_TEMPO.toString()))
            .andExpect(jsonPath("$.inicioAdministracao").value(DEFAULT_INICIO_ADMINISTRACAO.toString()))
            .andExpect(jsonPath("$.bombaInfusao").value(DEFAULT_BOMBA_INFUSAO.booleanValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    public void getNonExistingPrescricaoMedicamento() throws Exception {
        // Get the prescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(get("/api/prescricao-medicamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescricaoMedicamento() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);

        int databaseSizeBeforeUpdate = prescricaoMedicamentoRepository.findAll().size();

        // Update the prescricaoMedicamento
        PrescricaoMedicamento updatedPrescricaoMedicamento = prescricaoMedicamentoRepository.findById(prescricaoMedicamento.getId()).get();
        // Disconnect from session so that the updates on updatedPrescricaoMedicamento are not directly saved in db
        em.detach(updatedPrescricaoMedicamento);
        updatedPrescricaoMedicamento
            .idPaciente(UPDATED_ID_PACIENTE)
            .idMedicamento(UPDATED_ID_MEDICAMENTO)
            .dose(UPDATED_DOSE)
            .frequencia(UPDATED_FREQUENCIA)
            .todasVias(UPDATED_TODAS_VIAS)
            .velocidaInfusao(UPDATED_VELOCIDA_INFUSAO)
            .unidadeTempo(UPDATED_UNIDADE_TEMPO)
            .inicioAdministracao(UPDATED_INICIO_ADMINISTRACAO)
            .bombaInfusao(UPDATED_BOMBA_INFUSAO)
            .observacao(UPDATED_OBSERVACAO);

        restPrescricaoMedicamentoMockMvc.perform(put("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrescricaoMedicamento)))
            .andExpect(status().isOk());

        // Validate the PrescricaoMedicamento in the database
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeUpdate);
        PrescricaoMedicamento testPrescricaoMedicamento = prescricaoMedicamentoList.get(prescricaoMedicamentoList.size() - 1);
        assertThat(testPrescricaoMedicamento.getIdPaciente()).isEqualTo(UPDATED_ID_PACIENTE);
        assertThat(testPrescricaoMedicamento.getIdMedicamento()).isEqualTo(UPDATED_ID_MEDICAMENTO);
        assertThat(testPrescricaoMedicamento.getDose()).isEqualTo(UPDATED_DOSE);
        assertThat(testPrescricaoMedicamento.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
        assertThat(testPrescricaoMedicamento.isTodasVias()).isEqualTo(UPDATED_TODAS_VIAS);
        assertThat(testPrescricaoMedicamento.getVelocidaInfusao()).isEqualTo(UPDATED_VELOCIDA_INFUSAO);
        assertThat(testPrescricaoMedicamento.getUnidadeTempo()).isEqualTo(UPDATED_UNIDADE_TEMPO);
        assertThat(testPrescricaoMedicamento.getInicioAdministracao()).isEqualTo(UPDATED_INICIO_ADMINISTRACAO);
        assertThat(testPrescricaoMedicamento.isBombaInfusao()).isEqualTo(UPDATED_BOMBA_INFUSAO);
        assertThat(testPrescricaoMedicamento.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(1)).save(testPrescricaoMedicamento);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescricaoMedicamento() throws Exception {
        int databaseSizeBeforeUpdate = prescricaoMedicamentoRepository.findAll().size();

        // Create the PrescricaoMedicamento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescricaoMedicamentoMockMvc.perform(put("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamento)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoMedicamento in the database
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(0)).save(prescricaoMedicamento);
    }

    @Test
    @Transactional
    public void deletePrescricaoMedicamento() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);

        int databaseSizeBeforeDelete = prescricaoMedicamentoRepository.findAll().size();

        // Delete the prescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(delete("/api/prescricao-medicamentos/{id}", prescricaoMedicamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(1)).deleteById(prescricaoMedicamento.getId());
    }

    @Test
    @Transactional
    public void searchPrescricaoMedicamento() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);
        when(mockPrescricaoMedicamentoSearchRepository.search(queryStringQuery("id:" + prescricaoMedicamento.getId())))
            .thenReturn(Collections.singletonList(prescricaoMedicamento));
        // Search the prescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(get("/api/_search/prescricao-medicamentos?query=id:" + prescricaoMedicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoMedicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].idMedicamento").value(hasItem(DEFAULT_ID_MEDICAMENTO.intValue())))
            .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE)))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA)))
            .andExpect(jsonPath("$.[*].todasVias").value(hasItem(DEFAULT_TODAS_VIAS.booleanValue())))
            .andExpect(jsonPath("$.[*].velocidaInfusao").value(hasItem(DEFAULT_VELOCIDA_INFUSAO)))
            .andExpect(jsonPath("$.[*].unidadeTempo").value(hasItem(DEFAULT_UNIDADE_TEMPO.toString())))
            .andExpect(jsonPath("$.[*].inicioAdministracao").value(hasItem(DEFAULT_INICIO_ADMINISTRACAO.toString())))
            .andExpect(jsonPath("$.[*].bombaInfusao").value(hasItem(DEFAULT_BOMBA_INFUSAO.booleanValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoMedicamento.class);
        PrescricaoMedicamento prescricaoMedicamento1 = new PrescricaoMedicamento();
        prescricaoMedicamento1.setId(1L);
        PrescricaoMedicamento prescricaoMedicamento2 = new PrescricaoMedicamento();
        prescricaoMedicamento2.setId(prescricaoMedicamento1.getId());
        assertThat(prescricaoMedicamento1).isEqualTo(prescricaoMedicamento2);
        prescricaoMedicamento2.setId(2L);
        assertThat(prescricaoMedicamento1).isNotEqualTo(prescricaoMedicamento2);
        prescricaoMedicamento1.setId(null);
        assertThat(prescricaoMedicamento1).isNotEqualTo(prescricaoMedicamento2);
    }
}
