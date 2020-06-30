package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.Apresentacao;
import br.com.basis.madre.farmacia.repository.ApresentacaoRepository;
import br.com.basis.madre.farmacia.repository.search.ApresentacaoSearchRepository;
import br.com.basis.madre.farmacia.service.ApresentacaoService;
import br.com.basis.madre.farmacia.service.dto.ApresentacaoDTO;
import br.com.basis.madre.farmacia.service.mapper.ApresentacaoMapper;
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
 * Integration tests for the {@link ApresentacaoResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class ApresentacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ApresentacaoRepository apresentacaoRepository;

    @Autowired
    private ApresentacaoMapper apresentacaoMapper;

    @Autowired
    private ApresentacaoService apresentacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.ApresentacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ApresentacaoSearchRepository mockApresentacaoSearchRepository;

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

    private MockMvc restApresentacaoMockMvc;

    private Apresentacao apresentacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApresentacaoResource apresentacaoResource = new ApresentacaoResource(apresentacaoService);
        this.restApresentacaoMockMvc = MockMvcBuilders.standaloneSetup(apresentacaoResource)
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
    public static Apresentacao createEntity(EntityManager em) {
        Apresentacao apresentacao = new Apresentacao()
            .nome(DEFAULT_NOME);
        return apresentacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apresentacao createUpdatedEntity(EntityManager em) {
        Apresentacao apresentacao = new Apresentacao()
            .nome(UPDATED_NOME);
        return apresentacao;
    }

    @BeforeEach
    public void initTest() {
        apresentacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createApresentacao() throws Exception {
        int databaseSizeBeforeCreate = apresentacaoRepository.findAll().size();

        // Create the Apresentacao
        ApresentacaoDTO apresentacaoDTO = apresentacaoMapper.toDto(apresentacao);
        restApresentacaoMockMvc.perform(post("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apresentacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Apresentacao in the database
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Apresentacao testApresentacao = apresentacaoList.get(apresentacaoList.size() - 1);
        assertThat(testApresentacao.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Apresentacao in Elasticsearch
        verify(mockApresentacaoSearchRepository, times(1)).save(testApresentacao);
    }

    @Test
    @Transactional
    public void createApresentacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apresentacaoRepository.findAll().size();

        // Create the Apresentacao with an existing ID
        apresentacao.setId(1L);
        ApresentacaoDTO apresentacaoDTO = apresentacaoMapper.toDto(apresentacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApresentacaoMockMvc.perform(post("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apresentacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Apresentacao in the database
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Apresentacao in Elasticsearch
        verify(mockApresentacaoSearchRepository, times(0)).save(apresentacao);
    }


    @Test
    @Transactional
    public void getAllApresentacaos() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);

        // Get all the apresentacaoList
        restApresentacaoMockMvc.perform(get("/api/apresentacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apresentacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getApresentacao() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);

        // Get the apresentacao
        restApresentacaoMockMvc.perform(get("/api/apresentacaos/{id}", apresentacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apresentacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingApresentacao() throws Exception {
        // Get the apresentacao
        restApresentacaoMockMvc.perform(get("/api/apresentacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApresentacao() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);

        int databaseSizeBeforeUpdate = apresentacaoRepository.findAll().size();

        // Update the apresentacao
        Apresentacao updatedApresentacao = apresentacaoRepository.findById(apresentacao.getId()).get();
        // Disconnect from session so that the updates on updatedApresentacao are not directly saved in db
        em.detach(updatedApresentacao);
        updatedApresentacao
            .nome(UPDATED_NOME);
        ApresentacaoDTO apresentacaoDTO = apresentacaoMapper.toDto(updatedApresentacao);

        restApresentacaoMockMvc.perform(put("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apresentacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Apresentacao in the database
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeUpdate);
        Apresentacao testApresentacao = apresentacaoList.get(apresentacaoList.size() - 1);
        assertThat(testApresentacao.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Apresentacao in Elasticsearch
        verify(mockApresentacaoSearchRepository, times(1)).save(testApresentacao);
    }

    @Test
    @Transactional
    public void updateNonExistingApresentacao() throws Exception {
        int databaseSizeBeforeUpdate = apresentacaoRepository.findAll().size();

        // Create the Apresentacao
        ApresentacaoDTO apresentacaoDTO = apresentacaoMapper.toDto(apresentacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApresentacaoMockMvc.perform(put("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apresentacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Apresentacao in the database
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Apresentacao in Elasticsearch
        verify(mockApresentacaoSearchRepository, times(0)).save(apresentacao);
    }

    @Test
    @Transactional
    public void deleteApresentacao() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);

        int databaseSizeBeforeDelete = apresentacaoRepository.findAll().size();

        // Delete the apresentacao
        restApresentacaoMockMvc.perform(delete("/api/apresentacaos/{id}", apresentacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Apresentacao in Elasticsearch
        verify(mockApresentacaoSearchRepository, times(1)).deleteById(apresentacao.getId());
    }

    @Test
    @Transactional
    public void searchApresentacao() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);
        when(mockApresentacaoSearchRepository.search(queryStringQuery("id:" + apresentacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(apresentacao), PageRequest.of(0, 1), 1));
        // Search the apresentacao
        restApresentacaoMockMvc.perform(get("/api/_search/apresentacaos?query=id:" + apresentacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apresentacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apresentacao.class);
        Apresentacao apresentacao1 = new Apresentacao();
        apresentacao1.setId(1L);
        Apresentacao apresentacao2 = new Apresentacao();
        apresentacao2.setId(apresentacao1.getId());
        assertThat(apresentacao1).isEqualTo(apresentacao2);
        apresentacao2.setId(2L);
        assertThat(apresentacao1).isNotEqualTo(apresentacao2);
        apresentacao1.setId(null);
        assertThat(apresentacao1).isNotEqualTo(apresentacao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApresentacaoDTO.class);
        ApresentacaoDTO apresentacaoDTO1 = new ApresentacaoDTO();
        apresentacaoDTO1.setId(1L);
        ApresentacaoDTO apresentacaoDTO2 = new ApresentacaoDTO();
        assertThat(apresentacaoDTO1).isNotEqualTo(apresentacaoDTO2);
        apresentacaoDTO2.setId(apresentacaoDTO1.getId());
        assertThat(apresentacaoDTO1).isEqualTo(apresentacaoDTO2);
        apresentacaoDTO2.setId(2L);
        assertThat(apresentacaoDTO1).isNotEqualTo(apresentacaoDTO2);
        apresentacaoDTO1.setId(null);
        assertThat(apresentacaoDTO1).isNotEqualTo(apresentacaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apresentacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apresentacaoMapper.fromId(null)).isNull();
    }
}
