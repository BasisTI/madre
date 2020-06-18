package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Unidade;
import br.com.basis.madre.domain.TipoUnidade;
import br.com.basis.madre.repository.UnidadeRepository;
import br.com.basis.madre.repository.search.UnidadeSearchRepository;
import br.com.basis.madre.service.UnidadeService;
import br.com.basis.madre.service.dto.UnidadeDTO;
import br.com.basis.madre.service.mapper.UnidadeMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.domain.enumeration.Situacao;
/**
 * Integration tests for the {@link UnidadeResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class UnidadeResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final Situacao DEFAULT_SITUACAO = Situacao.ATIVO;
    private static final Situacao UPDATED_SITUACAO = Situacao.INATIVO;

    private static final Boolean DEFAULT_CONTROLE_DE_ESTOQUE = false;
    private static final Boolean UPDATED_CONTROLE_DE_ESTOQUE = true;

    private static final Long DEFAULT_ID_ALMOXARIFADO = 1L;
    private static final Long UPDATED_ID_ALMOXARIFADO = 2L;

    private static final Integer DEFAULT_ANDAR = 1;
    private static final Integer UPDATED_ANDAR = 2;

    private static final Integer DEFAULT_CAPACIDADE = 1;
    private static final Integer UPDATED_CAPACIDADE = 2;

    private static final Instant DEFAULT_HORARIO_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORARIO_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORARIO_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORARIO_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOCAL_EXAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_EXAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROTINA_DE_FUNCIONAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ROTINA_DE_FUNCIONAMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANEXO_DOCUMENTO = false;
    private static final Boolean UPDATED_ANEXO_DOCUMENTO = true;

    private static final Long DEFAULT_SETOR = 1L;
    private static final Long UPDATED_SETOR = 2L;

    private static final Long DEFAULT_ID_CENTRO_DE_ATIVIDADE = 1L;
    private static final Long UPDATED_ID_CENTRO_DE_ATIVIDADE = 2L;

    private static final Long DEFAULT_ID_CHEFIA = 1L;
    private static final Long UPDATED_ID_CHEFIA = 2L;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private UnidadeMapper unidadeMapper;

    @Autowired
    private UnidadeService unidadeService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.UnidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadeSearchRepository mockUnidadeSearchRepository;

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

    private MockMvc restUnidadeMockMvc;

    private Unidade unidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeResource unidadeResource = new UnidadeResource(unidadeService);
        this.restUnidadeMockMvc = MockMvcBuilders.standaloneSetup(unidadeResource)
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
    public static Unidade createEntity(EntityManager em) {
        Unidade unidade = new Unidade()
            .descricao(DEFAULT_DESCRICAO)
            .sigla(DEFAULT_SIGLA)
            .situacao(DEFAULT_SITUACAO)
            .controleDeEstoque(DEFAULT_CONTROLE_DE_ESTOQUE)
            .idAlmoxarifado(DEFAULT_ID_ALMOXARIFADO)
            .andar(DEFAULT_ANDAR)
            .capacidade(DEFAULT_CAPACIDADE)
            .horarioInicio(DEFAULT_HORARIO_INICIO)
            .horarioFim(DEFAULT_HORARIO_FIM)
            .localExame(DEFAULT_LOCAL_EXAME)
            .rotinaDeFuncionamento(DEFAULT_ROTINA_DE_FUNCIONAMENTO)
            .anexoDocumento(DEFAULT_ANEXO_DOCUMENTO)
            .setor(DEFAULT_SETOR)
            .idCentroDeAtividade(DEFAULT_ID_CENTRO_DE_ATIVIDADE)
            .idChefia(DEFAULT_ID_CHEFIA);
        // Add required entity
        TipoUnidade tipoUnidade;
        if (TestUtil.findAll(em, TipoUnidade.class).isEmpty()) {
            tipoUnidade = TipoUnidadeResourceIT.createEntity(em);
            em.persist(tipoUnidade);
            em.flush();
        } else {
            tipoUnidade = TestUtil.findAll(em, TipoUnidade.class).get(0);
        }
        unidade.setTipoUnidade(tipoUnidade);
        return unidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unidade createUpdatedEntity(EntityManager em) {
        Unidade unidade = new Unidade()
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA)
            .situacao(UPDATED_SITUACAO)
            .controleDeEstoque(UPDATED_CONTROLE_DE_ESTOQUE)
            .idAlmoxarifado(UPDATED_ID_ALMOXARIFADO)
            .andar(UPDATED_ANDAR)
            .capacidade(UPDATED_CAPACIDADE)
            .horarioInicio(UPDATED_HORARIO_INICIO)
            .horarioFim(UPDATED_HORARIO_FIM)
            .localExame(UPDATED_LOCAL_EXAME)
            .rotinaDeFuncionamento(UPDATED_ROTINA_DE_FUNCIONAMENTO)
            .anexoDocumento(UPDATED_ANEXO_DOCUMENTO)
            .setor(UPDATED_SETOR)
            .idCentroDeAtividade(UPDATED_ID_CENTRO_DE_ATIVIDADE)
            .idChefia(UPDATED_ID_CHEFIA);
        // Add required entity
        TipoUnidade tipoUnidade;
        if (TestUtil.findAll(em, TipoUnidade.class).isEmpty()) {
            tipoUnidade = TipoUnidadeResourceIT.createUpdatedEntity(em);
            em.persist(tipoUnidade);
            em.flush();
        } else {
            tipoUnidade = TestUtil.findAll(em, TipoUnidade.class).get(0);
        }
        unidade.setTipoUnidade(tipoUnidade);
        return unidade;
    }

    @BeforeEach
    public void initTest() {
        unidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidade() throws Exception {
        int databaseSizeBeforeCreate = unidadeRepository.findAll().size();

        // Create the Unidade
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);
        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Unidade in the database
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Unidade testUnidade = unidadeList.get(unidadeList.size() - 1);
        assertThat(testUnidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testUnidade.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testUnidade.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testUnidade.isControleDeEstoque()).isEqualTo(DEFAULT_CONTROLE_DE_ESTOQUE);
        assertThat(testUnidade.getIdAlmoxarifado()).isEqualTo(DEFAULT_ID_ALMOXARIFADO);
        assertThat(testUnidade.getAndar()).isEqualTo(DEFAULT_ANDAR);
        assertThat(testUnidade.getCapacidade()).isEqualTo(DEFAULT_CAPACIDADE);
        assertThat(testUnidade.getHorarioInicio()).isEqualTo(DEFAULT_HORARIO_INICIO);
        assertThat(testUnidade.getHorarioFim()).isEqualTo(DEFAULT_HORARIO_FIM);
        assertThat(testUnidade.getLocalExame()).isEqualTo(DEFAULT_LOCAL_EXAME);
        assertThat(testUnidade.getRotinaDeFuncionamento()).isEqualTo(DEFAULT_ROTINA_DE_FUNCIONAMENTO);
        assertThat(testUnidade.isAnexoDocumento()).isEqualTo(DEFAULT_ANEXO_DOCUMENTO);
        assertThat(testUnidade.getSetor()).isEqualTo(DEFAULT_SETOR);
        assertThat(testUnidade.getIdCentroDeAtividade()).isEqualTo(DEFAULT_ID_CENTRO_DE_ATIVIDADE);
        assertThat(testUnidade.getIdChefia()).isEqualTo(DEFAULT_ID_CHEFIA);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(1)).save(testUnidade);
    }

    @Test
    @Transactional
    public void createUnidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeRepository.findAll().size();

        // Create the Unidade with an existing ID
        unidade.setId(1L);
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unidade in the database
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(0)).save(unidade);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeRepository.findAll().size();
        // set the field null
        unidade.setDescricao(null);

        // Create the Unidade, which fails.
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeRepository.findAll().size();
        // set the field null
        unidade.setSigla(null);

        // Create the Unidade, which fails.
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeRepository.findAll().size();
        // set the field null
        unidade.setSituacao(null);

        // Create the Unidade, which fails.
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAndarIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeRepository.findAll().size();
        // set the field null
        unidade.setAndar(null);

        // Create the Unidade, which fails.
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdCentroDeAtividadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeRepository.findAll().size();
        // set the field null
        unidade.setIdCentroDeAtividade(null);

        // Create the Unidade, which fails.
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidades() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        // Get all the unidadeList
        restUnidadeMockMvc.perform(get("/api/unidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].controleDeEstoque").value(hasItem(DEFAULT_CONTROLE_DE_ESTOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].idAlmoxarifado").value(hasItem(DEFAULT_ID_ALMOXARIFADO.intValue())))
            .andExpect(jsonPath("$.[*].andar").value(hasItem(DEFAULT_ANDAR)))
            .andExpect(jsonPath("$.[*].capacidade").value(hasItem(DEFAULT_CAPACIDADE)))
            .andExpect(jsonPath("$.[*].horarioInicio").value(hasItem(DEFAULT_HORARIO_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horarioFim").value(hasItem(DEFAULT_HORARIO_FIM.toString())))
            .andExpect(jsonPath("$.[*].localExame").value(hasItem(DEFAULT_LOCAL_EXAME)))
            .andExpect(jsonPath("$.[*].rotinaDeFuncionamento").value(hasItem(DEFAULT_ROTINA_DE_FUNCIONAMENTO)))
            .andExpect(jsonPath("$.[*].anexoDocumento").value(hasItem(DEFAULT_ANEXO_DOCUMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].setor").value(hasItem(DEFAULT_SETOR.intValue())))
            .andExpect(jsonPath("$.[*].idCentroDeAtividade").value(hasItem(DEFAULT_ID_CENTRO_DE_ATIVIDADE.intValue())))
            .andExpect(jsonPath("$.[*].idChefia").value(hasItem(DEFAULT_ID_CHEFIA.intValue())));
    }
    
    @Test
    @Transactional
    public void getUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        // Get the unidade
        restUnidadeMockMvc.perform(get("/api/unidades/{id}", unidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidade.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.controleDeEstoque").value(DEFAULT_CONTROLE_DE_ESTOQUE.booleanValue()))
            .andExpect(jsonPath("$.idAlmoxarifado").value(DEFAULT_ID_ALMOXARIFADO.intValue()))
            .andExpect(jsonPath("$.andar").value(DEFAULT_ANDAR))
            .andExpect(jsonPath("$.capacidade").value(DEFAULT_CAPACIDADE))
            .andExpect(jsonPath("$.horarioInicio").value(DEFAULT_HORARIO_INICIO.toString()))
            .andExpect(jsonPath("$.horarioFim").value(DEFAULT_HORARIO_FIM.toString()))
            .andExpect(jsonPath("$.localExame").value(DEFAULT_LOCAL_EXAME))
            .andExpect(jsonPath("$.rotinaDeFuncionamento").value(DEFAULT_ROTINA_DE_FUNCIONAMENTO))
            .andExpect(jsonPath("$.anexoDocumento").value(DEFAULT_ANEXO_DOCUMENTO.booleanValue()))
            .andExpect(jsonPath("$.setor").value(DEFAULT_SETOR.intValue()))
            .andExpect(jsonPath("$.idCentroDeAtividade").value(DEFAULT_ID_CENTRO_DE_ATIVIDADE.intValue()))
            .andExpect(jsonPath("$.idChefia").value(DEFAULT_ID_CHEFIA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidade() throws Exception {
        // Get the unidade
        restUnidadeMockMvc.perform(get("/api/unidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        int databaseSizeBeforeUpdate = unidadeRepository.findAll().size();

        // Update the unidade
        Unidade updatedUnidade = unidadeRepository.findById(unidade.getId()).get();
        // Disconnect from session so that the updates on updatedUnidade are not directly saved in db
        em.detach(updatedUnidade);
        updatedUnidade
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA)
            .situacao(UPDATED_SITUACAO)
            .controleDeEstoque(UPDATED_CONTROLE_DE_ESTOQUE)
            .idAlmoxarifado(UPDATED_ID_ALMOXARIFADO)
            .andar(UPDATED_ANDAR)
            .capacidade(UPDATED_CAPACIDADE)
            .horarioInicio(UPDATED_HORARIO_INICIO)
            .horarioFim(UPDATED_HORARIO_FIM)
            .localExame(UPDATED_LOCAL_EXAME)
            .rotinaDeFuncionamento(UPDATED_ROTINA_DE_FUNCIONAMENTO)
            .anexoDocumento(UPDATED_ANEXO_DOCUMENTO)
            .setor(UPDATED_SETOR)
            .idCentroDeAtividade(UPDATED_ID_CENTRO_DE_ATIVIDADE)
            .idChefia(UPDATED_ID_CHEFIA);
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(updatedUnidade);

        restUnidadeMockMvc.perform(put("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Unidade in the database
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeUpdate);
        Unidade testUnidade = unidadeList.get(unidadeList.size() - 1);
        assertThat(testUnidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testUnidade.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testUnidade.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testUnidade.isControleDeEstoque()).isEqualTo(UPDATED_CONTROLE_DE_ESTOQUE);
        assertThat(testUnidade.getIdAlmoxarifado()).isEqualTo(UPDATED_ID_ALMOXARIFADO);
        assertThat(testUnidade.getAndar()).isEqualTo(UPDATED_ANDAR);
        assertThat(testUnidade.getCapacidade()).isEqualTo(UPDATED_CAPACIDADE);
        assertThat(testUnidade.getHorarioInicio()).isEqualTo(UPDATED_HORARIO_INICIO);
        assertThat(testUnidade.getHorarioFim()).isEqualTo(UPDATED_HORARIO_FIM);
        assertThat(testUnidade.getLocalExame()).isEqualTo(UPDATED_LOCAL_EXAME);
        assertThat(testUnidade.getRotinaDeFuncionamento()).isEqualTo(UPDATED_ROTINA_DE_FUNCIONAMENTO);
        assertThat(testUnidade.isAnexoDocumento()).isEqualTo(UPDATED_ANEXO_DOCUMENTO);
        assertThat(testUnidade.getSetor()).isEqualTo(UPDATED_SETOR);
        assertThat(testUnidade.getIdCentroDeAtividade()).isEqualTo(UPDATED_ID_CENTRO_DE_ATIVIDADE);
        assertThat(testUnidade.getIdChefia()).isEqualTo(UPDATED_ID_CHEFIA);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(1)).save(testUnidade);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidade() throws Exception {
        int databaseSizeBeforeUpdate = unidadeRepository.findAll().size();

        // Create the Unidade
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeMockMvc.perform(put("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unidade in the database
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(0)).save(unidade);
    }

    @Test
    @Transactional
    public void deleteUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        int databaseSizeBeforeDelete = unidadeRepository.findAll().size();

        // Delete the unidade
        restUnidadeMockMvc.perform(delete("/api/unidades/{id}", unidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(1)).deleteById(unidade.getId());
    }

    @Test
    @Transactional
    public void searchUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);
        when(mockUnidadeSearchRepository.search(queryStringQuery("id:" + unidade.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unidade), PageRequest.of(0, 1), 1));
        // Search the unidade
        restUnidadeMockMvc.perform(get("/api/_search/unidades?query=id:" + unidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].controleDeEstoque").value(hasItem(DEFAULT_CONTROLE_DE_ESTOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].idAlmoxarifado").value(hasItem(DEFAULT_ID_ALMOXARIFADO.intValue())))
            .andExpect(jsonPath("$.[*].andar").value(hasItem(DEFAULT_ANDAR)))
            .andExpect(jsonPath("$.[*].capacidade").value(hasItem(DEFAULT_CAPACIDADE)))
            .andExpect(jsonPath("$.[*].horarioInicio").value(hasItem(DEFAULT_HORARIO_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horarioFim").value(hasItem(DEFAULT_HORARIO_FIM.toString())))
            .andExpect(jsonPath("$.[*].localExame").value(hasItem(DEFAULT_LOCAL_EXAME)))
            .andExpect(jsonPath("$.[*].rotinaDeFuncionamento").value(hasItem(DEFAULT_ROTINA_DE_FUNCIONAMENTO)))
            .andExpect(jsonPath("$.[*].anexoDocumento").value(hasItem(DEFAULT_ANEXO_DOCUMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].setor").value(hasItem(DEFAULT_SETOR.intValue())))
            .andExpect(jsonPath("$.[*].idCentroDeAtividade").value(hasItem(DEFAULT_ID_CENTRO_DE_ATIVIDADE.intValue())))
            .andExpect(jsonPath("$.[*].idChefia").value(hasItem(DEFAULT_ID_CHEFIA.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unidade.class);
        Unidade unidade1 = new Unidade();
        unidade1.setId(1L);
        Unidade unidade2 = new Unidade();
        unidade2.setId(unidade1.getId());
        assertThat(unidade1).isEqualTo(unidade2);
        unidade2.setId(2L);
        assertThat(unidade1).isNotEqualTo(unidade2);
        unidade1.setId(null);
        assertThat(unidade1).isNotEqualTo(unidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeDTO.class);
        UnidadeDTO unidadeDTO1 = new UnidadeDTO();
        unidadeDTO1.setId(1L);
        UnidadeDTO unidadeDTO2 = new UnidadeDTO();
        assertThat(unidadeDTO1).isNotEqualTo(unidadeDTO2);
        unidadeDTO2.setId(unidadeDTO1.getId());
        assertThat(unidadeDTO1).isEqualTo(unidadeDTO2);
        unidadeDTO2.setId(2L);
        assertThat(unidadeDTO1).isNotEqualTo(unidadeDTO2);
        unidadeDTO1.setId(null);
        assertThat(unidadeDTO1).isNotEqualTo(unidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadeMapper.fromId(null)).isNull();
    }
}
