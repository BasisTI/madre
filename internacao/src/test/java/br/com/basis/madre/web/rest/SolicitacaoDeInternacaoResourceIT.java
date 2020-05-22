package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.SolicitacaoDeInternacao;
import br.com.basis.madre.domain.enumeration.Prioridade;
import br.com.basis.madre.repository.SolicitacaoDeInternacaoRepository;
import br.com.basis.madre.repository.search.SolicitacaoDeInternacaoSearchRepository;
import br.com.basis.madre.service.SolicitacaoDeInternacaoService;
import br.com.basis.madre.service.dto.SolicitacaoDeInternacaoDTO;
import br.com.basis.madre.service.mapper.SolicitacaoDeInternacaoMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link SolicitacaoDeInternacaoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class SolicitacaoDeInternacaoResourceIT {

    private static final LocalDate DEFAULT_DATA_PROVAVEL_DA_INTERNACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PROVAVEL_DA_INTERNACAO = LocalDate
        .now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_PROVAVEL_DA_CIRURGIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PROVAVEL_DA_CIRURGIA = LocalDate
        .now(ZoneId.systemDefault());

    private static final Prioridade DEFAULT_PRIORIDADE = Prioridade.ELETIVA;
    private static final Prioridade UPDATED_PRIORIDADE = Prioridade.URGENCIA;

    private static final String DEFAULT_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS = "AAAAAAAAAA";
    private static final String UPDATED_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS = "BBBBBBBBBB";

    private static final String DEFAULT_CONDICOES_QUE_JUSTIFICAM_INTERNACAO = "AAAAAAAAAA";
    private static final String UPDATED_CONDICOES_QUE_JUSTIFICAM_INTERNACAO = "BBBBBBBBBB";

    private static final String DEFAULT_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS = "AAAAAAAAAA";
    private static final String UPDATED_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS = "BBBBBBBBBB";

    @Autowired
    private SolicitacaoDeInternacaoRepository solicitacaoDeInternacaoRepository;

    @Autowired
    private SolicitacaoDeInternacaoMapper solicitacaoDeInternacaoMapper;

    @Autowired
    private SolicitacaoDeInternacaoService solicitacaoDeInternacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.SolicitacaoDeInternacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private SolicitacaoDeInternacaoSearchRepository mockSolicitacaoDeInternacaoSearchRepository;

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

    private MockMvc restSolicitacaoDeInternacaoMockMvc;

    private SolicitacaoDeInternacao solicitacaoDeInternacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolicitacaoDeInternacaoResource solicitacaoDeInternacaoResource = new SolicitacaoDeInternacaoResource(
            solicitacaoDeInternacaoService);
        this.restSolicitacaoDeInternacaoMockMvc = MockMvcBuilders
            .standaloneSetup(solicitacaoDeInternacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static SolicitacaoDeInternacao createEntity(EntityManager em) {
        SolicitacaoDeInternacao solicitacaoDeInternacao = new SolicitacaoDeInternacao()
            .dataProvavelDaInternacao(DEFAULT_DATA_PROVAVEL_DA_INTERNACAO)
            .dataProvavelDaCirurgia(DEFAULT_DATA_PROVAVEL_DA_CIRURGIA)
            .prioridade(DEFAULT_PRIORIDADE)
            .principaisSinaisESintomasClinicos(DEFAULT_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS)
            .condicoesQueJustificamInternacao(DEFAULT_CONDICOES_QUE_JUSTIFICAM_INTERNACAO)
            .principaisResultadosProvasDiagnosticas(
                DEFAULT_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS);
        return solicitacaoDeInternacao;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static SolicitacaoDeInternacao createUpdatedEntity(EntityManager em) {
        SolicitacaoDeInternacao solicitacaoDeInternacao = new SolicitacaoDeInternacao()
            .dataProvavelDaInternacao(UPDATED_DATA_PROVAVEL_DA_INTERNACAO)
            .dataProvavelDaCirurgia(UPDATED_DATA_PROVAVEL_DA_CIRURGIA)
            .prioridade(UPDATED_PRIORIDADE)
            .principaisSinaisESintomasClinicos(UPDATED_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS)
            .condicoesQueJustificamInternacao(UPDATED_CONDICOES_QUE_JUSTIFICAM_INTERNACAO)
            .principaisResultadosProvasDiagnosticas(
                UPDATED_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS);
        return solicitacaoDeInternacao;
    }

    @BeforeEach
    public void initTest() {
        solicitacaoDeInternacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitacaoDeInternacao() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoDeInternacaoRepository.findAll().size();

        // Create the SolicitacaoDeInternacao
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);
        restSolicitacaoDeInternacaoMockMvc.perform(post("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the SolicitacaoDeInternacao in the database
        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeCreate + 1);
        SolicitacaoDeInternacao testSolicitacaoDeInternacao = solicitacaoDeInternacaoList
            .get(solicitacaoDeInternacaoList.size() - 1);
        assertThat(testSolicitacaoDeInternacao.getDataProvavelDaInternacao())
            .isEqualTo(DEFAULT_DATA_PROVAVEL_DA_INTERNACAO);
        assertThat(testSolicitacaoDeInternacao.getDataProvavelDaCirurgia())
            .isEqualTo(DEFAULT_DATA_PROVAVEL_DA_CIRURGIA);
        assertThat(testSolicitacaoDeInternacao.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testSolicitacaoDeInternacao.getPrincipaisSinaisESintomasClinicos())
            .isEqualTo(DEFAULT_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS);
        assertThat(testSolicitacaoDeInternacao.getCondicoesQueJustificamInternacao())
            .isEqualTo(DEFAULT_CONDICOES_QUE_JUSTIFICAM_INTERNACAO);
        assertThat(testSolicitacaoDeInternacao.getPrincipaisResultadosProvasDiagnosticas())
            .isEqualTo(DEFAULT_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS);

        // Validate the SolicitacaoDeInternacao in Elasticsearch
        verify(mockSolicitacaoDeInternacaoSearchRepository, times(1))
            .save(testSolicitacaoDeInternacao);
    }

    @Test
    @Transactional
    public void createSolicitacaoDeInternacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoDeInternacaoRepository.findAll().size();

        // Create the SolicitacaoDeInternacao with an existing ID
        solicitacaoDeInternacao.setId(1L);
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitacaoDeInternacaoMockMvc.perform(post("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoDeInternacao in the database
        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the SolicitacaoDeInternacao in Elasticsearch
        verify(mockSolicitacaoDeInternacaoSearchRepository, times(0)).save(solicitacaoDeInternacao);
    }


    @Test
    @Transactional
    public void checkDataProvavelDaInternacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoDeInternacaoRepository.findAll().size();
        // set the field null
        solicitacaoDeInternacao.setDataProvavelDaInternacao(null);

        // Create the SolicitacaoDeInternacao, which fails.
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);

        restSolicitacaoDeInternacaoMockMvc.perform(post("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrioridadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoDeInternacaoRepository.findAll().size();
        // set the field null
        solicitacaoDeInternacao.setPrioridade(null);

        // Create the SolicitacaoDeInternacao, which fails.
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);

        restSolicitacaoDeInternacaoMockMvc.perform(post("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrincipaisSinaisESintomasClinicosIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoDeInternacaoRepository.findAll().size();
        // set the field null
        solicitacaoDeInternacao.setPrincipaisSinaisESintomasClinicos(null);

        // Create the SolicitacaoDeInternacao, which fails.
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);

        restSolicitacaoDeInternacaoMockMvc.perform(post("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCondicoesQueJustificamInternacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoDeInternacaoRepository.findAll().size();
        // set the field null
        solicitacaoDeInternacao.setCondicoesQueJustificamInternacao(null);

        // Create the SolicitacaoDeInternacao, which fails.
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);

        restSolicitacaoDeInternacaoMockMvc.perform(post("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrincipaisResultadosProvasDiagnosticasIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoDeInternacaoRepository.findAll().size();
        // set the field null
        solicitacaoDeInternacao.setPrincipaisResultadosProvasDiagnosticas(null);

        // Create the SolicitacaoDeInternacao, which fails.
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);

        restSolicitacaoDeInternacaoMockMvc.perform(post("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoDeInternacaos() throws Exception {
        // Initialize the database
        solicitacaoDeInternacaoRepository.saveAndFlush(solicitacaoDeInternacao);

        // Get all the solicitacaoDeInternacaoList
        restSolicitacaoDeInternacaoMockMvc
            .perform(get("/api/solicitacao-de-internacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(
                jsonPath("$.[*].id").value(hasItem(solicitacaoDeInternacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProvavelDaInternacao")
                .value(hasItem(DEFAULT_DATA_PROVAVEL_DA_INTERNACAO.toString())))
            .andExpect(jsonPath("$.[*].dataProvavelDaCirurgia")
                .value(hasItem(DEFAULT_DATA_PROVAVEL_DA_CIRURGIA.toString())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE.toString())))
            .andExpect(jsonPath("$.[*].principaisSinaisESintomasClinicos")
                .value(hasItem(DEFAULT_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS)))
            .andExpect(jsonPath("$.[*].condicoesQueJustificamInternacao")
                .value(hasItem(DEFAULT_CONDICOES_QUE_JUSTIFICAM_INTERNACAO)))
            .andExpect(jsonPath("$.[*].principaisResultadosProvasDiagnosticas")
                .value(hasItem(DEFAULT_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS)));
    }

    @Test
    @Transactional
    public void getSolicitacaoDeInternacao() throws Exception {
        // Initialize the database
        solicitacaoDeInternacaoRepository.saveAndFlush(solicitacaoDeInternacao);

        // Get the solicitacaoDeInternacao
        restSolicitacaoDeInternacaoMockMvc
            .perform(get("/api/solicitacao-de-internacaos/{id}", solicitacaoDeInternacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solicitacaoDeInternacao.getId().intValue()))
            .andExpect(jsonPath("$.dataProvavelDaInternacao")
                .value(DEFAULT_DATA_PROVAVEL_DA_INTERNACAO.toString()))
            .andExpect(jsonPath("$.dataProvavelDaCirurgia")
                .value(DEFAULT_DATA_PROVAVEL_DA_CIRURGIA.toString()))
            .andExpect(jsonPath("$.prioridade").value(DEFAULT_PRIORIDADE.toString()))
            .andExpect(jsonPath("$.principaisSinaisESintomasClinicos")
                .value(DEFAULT_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS))
            .andExpect(jsonPath("$.condicoesQueJustificamInternacao")
                .value(DEFAULT_CONDICOES_QUE_JUSTIFICAM_INTERNACAO))
            .andExpect(jsonPath("$.principaisResultadosProvasDiagnosticas")
                .value(DEFAULT_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS));
    }

    @Test
    @Transactional
    public void getNonExistingSolicitacaoDeInternacao() throws Exception {
        // Get the solicitacaoDeInternacao
        restSolicitacaoDeInternacaoMockMvc
            .perform(get("/api/solicitacao-de-internacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitacaoDeInternacao() throws Exception {
        // Initialize the database
        solicitacaoDeInternacaoRepository.saveAndFlush(solicitacaoDeInternacao);

        int databaseSizeBeforeUpdate = solicitacaoDeInternacaoRepository.findAll().size();

        // Update the solicitacaoDeInternacao
        SolicitacaoDeInternacao updatedSolicitacaoDeInternacao = solicitacaoDeInternacaoRepository
            .findById(solicitacaoDeInternacao.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitacaoDeInternacao are not directly saved in db
        em.detach(updatedSolicitacaoDeInternacao);
        updatedSolicitacaoDeInternacao
            .dataProvavelDaInternacao(UPDATED_DATA_PROVAVEL_DA_INTERNACAO)
            .dataProvavelDaCirurgia(UPDATED_DATA_PROVAVEL_DA_CIRURGIA)
            .prioridade(UPDATED_PRIORIDADE)
            .principaisSinaisESintomasClinicos(UPDATED_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS)
            .condicoesQueJustificamInternacao(UPDATED_CONDICOES_QUE_JUSTIFICAM_INTERNACAO)
            .principaisResultadosProvasDiagnosticas(
                UPDATED_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS);
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(updatedSolicitacaoDeInternacao);

        restSolicitacaoDeInternacaoMockMvc.perform(put("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isOk());

        // Validate the SolicitacaoDeInternacao in the database
        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeUpdate);
        SolicitacaoDeInternacao testSolicitacaoDeInternacao = solicitacaoDeInternacaoList
            .get(solicitacaoDeInternacaoList.size() - 1);
        assertThat(testSolicitacaoDeInternacao.getDataProvavelDaInternacao())
            .isEqualTo(UPDATED_DATA_PROVAVEL_DA_INTERNACAO);
        assertThat(testSolicitacaoDeInternacao.getDataProvavelDaCirurgia())
            .isEqualTo(UPDATED_DATA_PROVAVEL_DA_CIRURGIA);
        assertThat(testSolicitacaoDeInternacao.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testSolicitacaoDeInternacao.getPrincipaisSinaisESintomasClinicos())
            .isEqualTo(UPDATED_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS);
        assertThat(testSolicitacaoDeInternacao.getCondicoesQueJustificamInternacao())
            .isEqualTo(UPDATED_CONDICOES_QUE_JUSTIFICAM_INTERNACAO);
        assertThat(testSolicitacaoDeInternacao.getPrincipaisResultadosProvasDiagnosticas())
            .isEqualTo(UPDATED_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS);

        // Validate the SolicitacaoDeInternacao in Elasticsearch
        verify(mockSolicitacaoDeInternacaoSearchRepository, times(1))
            .save(testSolicitacaoDeInternacao);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitacaoDeInternacao() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoDeInternacaoRepository.findAll().size();

        // Create the SolicitacaoDeInternacao
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitacaoDeInternacaoMockMvc.perform(put("/api/solicitacao-de-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoDeInternacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoDeInternacao in the database
        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SolicitacaoDeInternacao in Elasticsearch
        verify(mockSolicitacaoDeInternacaoSearchRepository, times(0)).save(solicitacaoDeInternacao);
    }

    @Test
    @Transactional
    public void deleteSolicitacaoDeInternacao() throws Exception {
        // Initialize the database
        solicitacaoDeInternacaoRepository.saveAndFlush(solicitacaoDeInternacao);

        int databaseSizeBeforeDelete = solicitacaoDeInternacaoRepository.findAll().size();

        // Delete the solicitacaoDeInternacao
        restSolicitacaoDeInternacaoMockMvc
            .perform(delete("/api/solicitacao-de-internacaos/{id}", solicitacaoDeInternacao.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SolicitacaoDeInternacao> solicitacaoDeInternacaoList = solicitacaoDeInternacaoRepository
            .findAll();
        assertThat(solicitacaoDeInternacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SolicitacaoDeInternacao in Elasticsearch
        verify(mockSolicitacaoDeInternacaoSearchRepository, times(1))
            .deleteById(solicitacaoDeInternacao.getId());
    }

    @Test
    @Transactional
    public void searchSolicitacaoDeInternacao() throws Exception {
        // Initialize the database
        solicitacaoDeInternacaoRepository.saveAndFlush(solicitacaoDeInternacao);
        when(mockSolicitacaoDeInternacaoSearchRepository
            .search(queryStringQuery("id:" + solicitacaoDeInternacao.getId()),
                PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(solicitacaoDeInternacao),
                PageRequest.of(0, 1), 1));
        // Search the solicitacaoDeInternacao
        restSolicitacaoDeInternacaoMockMvc.perform(
            get("/api/_search/solicitacao-de-internacaos?query=id:" + solicitacaoDeInternacao
                .getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(
                jsonPath("$.[*].id").value(hasItem(solicitacaoDeInternacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProvavelDaInternacao")
                .value(hasItem(DEFAULT_DATA_PROVAVEL_DA_INTERNACAO.toString())))
            .andExpect(jsonPath("$.[*].dataProvavelDaCirurgia")
                .value(hasItem(DEFAULT_DATA_PROVAVEL_DA_CIRURGIA.toString())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE.toString())))
            .andExpect(jsonPath("$.[*].principaisSinaisESintomasClinicos")
                .value(hasItem(DEFAULT_PRINCIPAIS_SINAIS_E_SINTOMAS_CLINICOS)))
            .andExpect(jsonPath("$.[*].condicoesQueJustificamInternacao")
                .value(hasItem(DEFAULT_CONDICOES_QUE_JUSTIFICAM_INTERNACAO)))
            .andExpect(jsonPath("$.[*].principaisResultadosProvasDiagnosticas")
                .value(hasItem(DEFAULT_PRINCIPAIS_RESULTADOS_PROVAS_DIAGNOSTICAS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoDeInternacao.class);
        SolicitacaoDeInternacao solicitacaoDeInternacao1 = new SolicitacaoDeInternacao();
        solicitacaoDeInternacao1.setId(1L);
        SolicitacaoDeInternacao solicitacaoDeInternacao2 = new SolicitacaoDeInternacao();
        solicitacaoDeInternacao2.setId(solicitacaoDeInternacao1.getId());
        assertThat(solicitacaoDeInternacao1).isEqualTo(solicitacaoDeInternacao2);
        solicitacaoDeInternacao2.setId(2L);
        assertThat(solicitacaoDeInternacao1).isNotEqualTo(solicitacaoDeInternacao2);
        solicitacaoDeInternacao1.setId(null);
        assertThat(solicitacaoDeInternacao1).isNotEqualTo(solicitacaoDeInternacao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoDeInternacaoDTO.class);
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO1 = new SolicitacaoDeInternacaoDTO();
        solicitacaoDeInternacaoDTO1.setId(1L);
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO2 = new SolicitacaoDeInternacaoDTO();
        assertThat(solicitacaoDeInternacaoDTO1).isNotEqualTo(solicitacaoDeInternacaoDTO2);
        solicitacaoDeInternacaoDTO2.setId(solicitacaoDeInternacaoDTO1.getId());
        assertThat(solicitacaoDeInternacaoDTO1).isEqualTo(solicitacaoDeInternacaoDTO2);
        solicitacaoDeInternacaoDTO2.setId(2L);
        assertThat(solicitacaoDeInternacaoDTO1).isNotEqualTo(solicitacaoDeInternacaoDTO2);
        solicitacaoDeInternacaoDTO1.setId(null);
        assertThat(solicitacaoDeInternacaoDTO1).isNotEqualTo(solicitacaoDeInternacaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(solicitacaoDeInternacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(solicitacaoDeInternacaoMapper.fromId(null)).isNull();
    }
}
