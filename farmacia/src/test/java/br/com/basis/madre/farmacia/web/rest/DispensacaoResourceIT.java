package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.Dispensacao;
import br.com.basis.madre.farmacia.repository.DispensacaoRepository;
import br.com.basis.madre.farmacia.repository.search.DispensacaoSearchRepository;
import br.com.basis.madre.farmacia.service.DispensacaoService;
import br.com.basis.madre.farmacia.service.dto.DispensacaoDTO;
import br.com.basis.madre.farmacia.service.mapper.DispensacaoMapper;
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

import static br.com.basis.madre.farmacia.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DispensacaoResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class DispensacaoResourceIT {

    private static final Long DEFAULT_ID_PRESCRICAO = 1L;
    private static final Long UPDATED_ID_PRESCRICAO = 2L;

    private static final Long DEFAULT_ID_UNIDADE = 1L;
    private static final Long UPDATED_ID_UNIDADE = 2L;

    private static final Long DEFAULT_USUARIO_QUE_CRIOU = 1L;
    private static final Long UPDATED_USUARIO_QUE_CRIOU = 2L;

    private static final LocalDate DEFAULT_DATA_DE_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USUARIO_QUE_ALTEROU = 1L;
    private static final Long UPDATED_USUARIO_QUE_ALTEROU = 2L;

    private static final LocalDate DEFAULT_DATA_DE_ALTERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_ALTERACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DispensacaoRepository dispensacaoRepository;

    @Autowired
    private DispensacaoMapper dispensacaoMapper;

    @Autowired
    private DispensacaoService dispensacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.DispensacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DispensacaoSearchRepository mockDispensacaoSearchRepository;

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

    private MockMvc restDispensacaoMockMvc;

    private Dispensacao dispensacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DispensacaoResource dispensacaoResource = new DispensacaoResource(dispensacaoService);
        this.restDispensacaoMockMvc = MockMvcBuilders.standaloneSetup(dispensacaoResource)
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
    public static Dispensacao createEntity(EntityManager em) {
        Dispensacao dispensacao = new Dispensacao()
            .idPrescricao(DEFAULT_ID_PRESCRICAO)
            .idUnidade(DEFAULT_ID_UNIDADE)
            .usuarioQueCriou(DEFAULT_USUARIO_QUE_CRIOU)
            .dataDeCriacao(DEFAULT_DATA_DE_CRIACAO)
            .usuarioQueAlterou(DEFAULT_USUARIO_QUE_ALTEROU)
            .dataDeAlteracao(DEFAULT_DATA_DE_ALTERACAO);
        return dispensacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dispensacao createUpdatedEntity(EntityManager em) {
        Dispensacao dispensacao = new Dispensacao()
            .idPrescricao(UPDATED_ID_PRESCRICAO)
            .idUnidade(UPDATED_ID_UNIDADE)
            .usuarioQueCriou(UPDATED_USUARIO_QUE_CRIOU)
            .dataDeCriacao(UPDATED_DATA_DE_CRIACAO)
            .usuarioQueAlterou(UPDATED_USUARIO_QUE_ALTEROU)
            .dataDeAlteracao(UPDATED_DATA_DE_ALTERACAO);
        return dispensacao;
    }

    @BeforeEach
    public void initTest() {
        dispensacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createDispensacao() throws Exception {
        int databaseSizeBeforeCreate = dispensacaoRepository.findAll().size();

        // Create the Dispensacao
        DispensacaoDTO dispensacaoDTO = dispensacaoMapper.toDto(dispensacao);
        restDispensacaoMockMvc.perform(post("/api/dispensacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispensacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Dispensacao in the database
        List<Dispensacao> dispensacaoList = dispensacaoRepository.findAll();
        assertThat(dispensacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Dispensacao testDispensacao = dispensacaoList.get(dispensacaoList.size() - 1);
        assertThat(testDispensacao.getIdPrescricao()).isEqualTo(DEFAULT_ID_PRESCRICAO);
        assertThat(testDispensacao.getIdUnidade()).isEqualTo(DEFAULT_ID_UNIDADE);
        assertThat(testDispensacao.getUsuarioQueCriou()).isEqualTo(DEFAULT_USUARIO_QUE_CRIOU);
        assertThat(testDispensacao.getDataDeCriacao()).isEqualTo(DEFAULT_DATA_DE_CRIACAO);
        assertThat(testDispensacao.getUsuarioQueAlterou()).isEqualTo(DEFAULT_USUARIO_QUE_ALTEROU);
        assertThat(testDispensacao.getDataDeAlteracao()).isEqualTo(DEFAULT_DATA_DE_ALTERACAO);

        // Validate the Dispensacao in Elasticsearch
        verify(mockDispensacaoSearchRepository, times(1)).save(testDispensacao);
    }

    @Test
    @Transactional
    public void createDispensacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dispensacaoRepository.findAll().size();

        // Create the Dispensacao with an existing ID
        dispensacao.setId(1L);
        DispensacaoDTO dispensacaoDTO = dispensacaoMapper.toDto(dispensacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispensacaoMockMvc.perform(post("/api/dispensacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispensacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dispensacao in the database
        List<Dispensacao> dispensacaoList = dispensacaoRepository.findAll();
        assertThat(dispensacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dispensacao in Elasticsearch
        verify(mockDispensacaoSearchRepository, times(0)).save(dispensacao);
    }


    @Test
    @Transactional
    public void getAllDispensacaos() throws Exception {
        // Initialize the database
        dispensacaoRepository.saveAndFlush(dispensacao);

        // Get all the dispensacaoList
        restDispensacaoMockMvc.perform(get("/api/dispensacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispensacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPrescricao").value(hasItem(DEFAULT_ID_PRESCRICAO.intValue())))
            .andExpect(jsonPath("$.[*].idUnidade").value(hasItem(DEFAULT_ID_UNIDADE.intValue())))
            .andExpect(jsonPath("$.[*].usuarioQueCriou").value(hasItem(DEFAULT_USUARIO_QUE_CRIOU.intValue())))
            .andExpect(jsonPath("$.[*].dataDeCriacao").value(hasItem(DEFAULT_DATA_DE_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].usuarioQueAlterou").value(hasItem(DEFAULT_USUARIO_QUE_ALTEROU.intValue())))
            .andExpect(jsonPath("$.[*].dataDeAlteracao").value(hasItem(DEFAULT_DATA_DE_ALTERACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getDispensacao() throws Exception {
        // Initialize the database
        dispensacaoRepository.saveAndFlush(dispensacao);

        // Get the dispensacao
        restDispensacaoMockMvc.perform(get("/api/dispensacaos/{id}", dispensacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dispensacao.getId().intValue()))
            .andExpect(jsonPath("$.idPrescricao").value(DEFAULT_ID_PRESCRICAO.intValue()))
            .andExpect(jsonPath("$.idUnidade").value(DEFAULT_ID_UNIDADE.intValue()))
            .andExpect(jsonPath("$.usuarioQueCriou").value(DEFAULT_USUARIO_QUE_CRIOU.intValue()))
            .andExpect(jsonPath("$.dataDeCriacao").value(DEFAULT_DATA_DE_CRIACAO.toString()))
            .andExpect(jsonPath("$.usuarioQueAlterou").value(DEFAULT_USUARIO_QUE_ALTEROU.intValue()))
            .andExpect(jsonPath("$.dataDeAlteracao").value(DEFAULT_DATA_DE_ALTERACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDispensacao() throws Exception {
        // Get the dispensacao
        restDispensacaoMockMvc.perform(get("/api/dispensacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDispensacao() throws Exception {
        // Initialize the database
        dispensacaoRepository.saveAndFlush(dispensacao);

        int databaseSizeBeforeUpdate = dispensacaoRepository.findAll().size();

        // Update the dispensacao
        Dispensacao updatedDispensacao = dispensacaoRepository.findById(dispensacao.getId()).get();
        // Disconnect from session so that the updates on updatedDispensacao are not directly saved in db
        em.detach(updatedDispensacao);
        updatedDispensacao
            .idPrescricao(UPDATED_ID_PRESCRICAO)
            .idUnidade(UPDATED_ID_UNIDADE)
            .usuarioQueCriou(UPDATED_USUARIO_QUE_CRIOU)
            .dataDeCriacao(UPDATED_DATA_DE_CRIACAO)
            .usuarioQueAlterou(UPDATED_USUARIO_QUE_ALTEROU)
            .dataDeAlteracao(UPDATED_DATA_DE_ALTERACAO);
        DispensacaoDTO dispensacaoDTO = dispensacaoMapper.toDto(updatedDispensacao);

        restDispensacaoMockMvc.perform(put("/api/dispensacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispensacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Dispensacao in the database
        List<Dispensacao> dispensacaoList = dispensacaoRepository.findAll();
        assertThat(dispensacaoList).hasSize(databaseSizeBeforeUpdate);
        Dispensacao testDispensacao = dispensacaoList.get(dispensacaoList.size() - 1);
        assertThat(testDispensacao.getIdPrescricao()).isEqualTo(UPDATED_ID_PRESCRICAO);
        assertThat(testDispensacao.getIdUnidade()).isEqualTo(UPDATED_ID_UNIDADE);
        assertThat(testDispensacao.getUsuarioQueCriou()).isEqualTo(UPDATED_USUARIO_QUE_CRIOU);
        assertThat(testDispensacao.getDataDeCriacao()).isEqualTo(UPDATED_DATA_DE_CRIACAO);
        assertThat(testDispensacao.getUsuarioQueAlterou()).isEqualTo(UPDATED_USUARIO_QUE_ALTEROU);
        assertThat(testDispensacao.getDataDeAlteracao()).isEqualTo(UPDATED_DATA_DE_ALTERACAO);

        // Validate the Dispensacao in Elasticsearch
        verify(mockDispensacaoSearchRepository, times(1)).save(testDispensacao);
    }

    @Test
    @Transactional
    public void updateNonExistingDispensacao() throws Exception {
        int databaseSizeBeforeUpdate = dispensacaoRepository.findAll().size();

        // Create the Dispensacao
        DispensacaoDTO dispensacaoDTO = dispensacaoMapper.toDto(dispensacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispensacaoMockMvc.perform(put("/api/dispensacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispensacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dispensacao in the database
        List<Dispensacao> dispensacaoList = dispensacaoRepository.findAll();
        assertThat(dispensacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dispensacao in Elasticsearch
        verify(mockDispensacaoSearchRepository, times(0)).save(dispensacao);
    }

    @Test
    @Transactional
    public void deleteDispensacao() throws Exception {
        // Initialize the database
        dispensacaoRepository.saveAndFlush(dispensacao);

        int databaseSizeBeforeDelete = dispensacaoRepository.findAll().size();

        // Delete the dispensacao
        restDispensacaoMockMvc.perform(delete("/api/dispensacaos/{id}", dispensacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dispensacao> dispensacaoList = dispensacaoRepository.findAll();
        assertThat(dispensacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dispensacao in Elasticsearch
        verify(mockDispensacaoSearchRepository, times(1)).deleteById(dispensacao.getId());
    }

    @Test
    @Transactional
    public void searchDispensacao() throws Exception {
        // Initialize the database
        dispensacaoRepository.saveAndFlush(dispensacao);
        when(mockDispensacaoSearchRepository.search(queryStringQuery("id:" + dispensacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dispensacao), PageRequest.of(0, 1), 1));
        // Search the dispensacao
        restDispensacaoMockMvc.perform(get("/api/_search/dispensacaos?query=id:" + dispensacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispensacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPrescricao").value(hasItem(DEFAULT_ID_PRESCRICAO.intValue())))
            .andExpect(jsonPath("$.[*].idUnidade").value(hasItem(DEFAULT_ID_UNIDADE.intValue())))
            .andExpect(jsonPath("$.[*].usuarioQueCriou").value(hasItem(DEFAULT_USUARIO_QUE_CRIOU.intValue())))
            .andExpect(jsonPath("$.[*].dataDeCriacao").value(hasItem(DEFAULT_DATA_DE_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].usuarioQueAlterou").value(hasItem(DEFAULT_USUARIO_QUE_ALTEROU.intValue())))
            .andExpect(jsonPath("$.[*].dataDeAlteracao").value(hasItem(DEFAULT_DATA_DE_ALTERACAO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dispensacao.class);
        Dispensacao dispensacao1 = new Dispensacao();
        dispensacao1.setId(1L);
        Dispensacao dispensacao2 = new Dispensacao();
        dispensacao2.setId(dispensacao1.getId());
        assertThat(dispensacao1).isEqualTo(dispensacao2);
        dispensacao2.setId(2L);
        assertThat(dispensacao1).isNotEqualTo(dispensacao2);
        dispensacao1.setId(null);
        assertThat(dispensacao1).isNotEqualTo(dispensacao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispensacaoDTO.class);
        DispensacaoDTO dispensacaoDTO1 = new DispensacaoDTO();
        dispensacaoDTO1.setId(1L);
        DispensacaoDTO dispensacaoDTO2 = new DispensacaoDTO();
        assertThat(dispensacaoDTO1).isNotEqualTo(dispensacaoDTO2);
        dispensacaoDTO2.setId(dispensacaoDTO1.getId());
        assertThat(dispensacaoDTO1).isEqualTo(dispensacaoDTO2);
        dispensacaoDTO2.setId(2L);
        assertThat(dispensacaoDTO1).isNotEqualTo(dispensacaoDTO2);
        dispensacaoDTO1.setId(null);
        assertThat(dispensacaoDTO1).isNotEqualTo(dispensacaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dispensacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dispensacaoMapper.fromId(null)).isNull();
    }
}
