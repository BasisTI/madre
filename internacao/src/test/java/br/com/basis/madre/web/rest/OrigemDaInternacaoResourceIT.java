package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.OrigemDaInternacao;
import br.com.basis.madre.repository.OrigemDaInternacaoRepository;
import br.com.basis.madre.repository.search.OrigemDaInternacaoSearchRepository;
import br.com.basis.madre.service.OrigemDaInternacaoService;
import br.com.basis.madre.service.dto.OrigemDaInternacaoDTO;
import br.com.basis.madre.service.mapper.OrigemDaInternacaoMapper;
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
 * Integration tests for the {@link OrigemDaInternacaoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class OrigemDaInternacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private OrigemDaInternacaoRepository origemDaInternacaoRepository;

    @Autowired
    private OrigemDaInternacaoMapper origemDaInternacaoMapper;

    @Autowired
    private OrigemDaInternacaoService origemDaInternacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.OrigemDaInternacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrigemDaInternacaoSearchRepository mockOrigemDaInternacaoSearchRepository;

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

    private MockMvc restOrigemDaInternacaoMockMvc;

    private OrigemDaInternacao origemDaInternacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrigemDaInternacaoResource origemDaInternacaoResource = new OrigemDaInternacaoResource(
            origemDaInternacaoService);
        this.restOrigemDaInternacaoMockMvc = MockMvcBuilders
            .standaloneSetup(origemDaInternacaoResource)
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
    public static OrigemDaInternacao createEntity(EntityManager em) {
        OrigemDaInternacao origemDaInternacao = new OrigemDaInternacao()
            .nome(DEFAULT_NOME);
        return origemDaInternacao;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static OrigemDaInternacao createUpdatedEntity(EntityManager em) {
        OrigemDaInternacao origemDaInternacao = new OrigemDaInternacao()
            .nome(UPDATED_NOME);
        return origemDaInternacao;
    }

    @BeforeEach
    public void initTest() {
        origemDaInternacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrigemDaInternacao() throws Exception {
        int databaseSizeBeforeCreate = origemDaInternacaoRepository.findAll().size();

        // Create the OrigemDaInternacao
        OrigemDaInternacaoDTO origemDaInternacaoDTO = origemDaInternacaoMapper
            .toDto(origemDaInternacao);
        restOrigemDaInternacaoMockMvc.perform(post("/api/origem-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaInternacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the OrigemDaInternacao in the database
        List<OrigemDaInternacao> origemDaInternacaoList = origemDaInternacaoRepository.findAll();
        assertThat(origemDaInternacaoList).hasSize(databaseSizeBeforeCreate + 1);
        OrigemDaInternacao testOrigemDaInternacao = origemDaInternacaoList
            .get(origemDaInternacaoList.size() - 1);
        assertThat(testOrigemDaInternacao.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the OrigemDaInternacao in Elasticsearch
        verify(mockOrigemDaInternacaoSearchRepository, times(1)).save(testOrigemDaInternacao);
    }

    @Test
    @Transactional
    public void createOrigemDaInternacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = origemDaInternacaoRepository.findAll().size();

        // Create the OrigemDaInternacao with an existing ID
        origemDaInternacao.setId(1L);
        OrigemDaInternacaoDTO origemDaInternacaoDTO = origemDaInternacaoMapper
            .toDto(origemDaInternacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrigemDaInternacaoMockMvc.perform(post("/api/origem-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaInternacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrigemDaInternacao in the database
        List<OrigemDaInternacao> origemDaInternacaoList = origemDaInternacaoRepository.findAll();
        assertThat(origemDaInternacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrigemDaInternacao in Elasticsearch
        verify(mockOrigemDaInternacaoSearchRepository, times(0)).save(origemDaInternacao);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = origemDaInternacaoRepository.findAll().size();
        // set the field null
        origemDaInternacao.setNome(null);

        // Create the OrigemDaInternacao, which fails.
        OrigemDaInternacaoDTO origemDaInternacaoDTO = origemDaInternacaoMapper
            .toDto(origemDaInternacao);

        restOrigemDaInternacaoMockMvc.perform(post("/api/origem-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaInternacaoDTO)))
            .andExpect(status().isBadRequest());

        List<OrigemDaInternacao> origemDaInternacaoList = origemDaInternacaoRepository.findAll();
        assertThat(origemDaInternacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrigemDaInternacaos() throws Exception {
        // Initialize the database
        origemDaInternacaoRepository.saveAndFlush(origemDaInternacao);

        // Get all the origemDaInternacaoList
        restOrigemDaInternacaoMockMvc.perform(get("/api/origem-da-internacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origemDaInternacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getOrigemDaInternacao() throws Exception {
        // Initialize the database
        origemDaInternacaoRepository.saveAndFlush(origemDaInternacao);

        // Get the origemDaInternacao
        restOrigemDaInternacaoMockMvc
            .perform(get("/api/origem-da-internacaos/{id}", origemDaInternacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(origemDaInternacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingOrigemDaInternacao() throws Exception {
        // Get the origemDaInternacao
        restOrigemDaInternacaoMockMvc
            .perform(get("/api/origem-da-internacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrigemDaInternacao() throws Exception {
        // Initialize the database
        origemDaInternacaoRepository.saveAndFlush(origemDaInternacao);

        int databaseSizeBeforeUpdate = origemDaInternacaoRepository.findAll().size();

        // Update the origemDaInternacao
        OrigemDaInternacao updatedOrigemDaInternacao = origemDaInternacaoRepository
            .findById(origemDaInternacao.getId()).get();
        // Disconnect from session so that the updates on updatedOrigemDaInternacao are not directly saved in db
        em.detach(updatedOrigemDaInternacao);
        updatedOrigemDaInternacao
            .nome(UPDATED_NOME);
        OrigemDaInternacaoDTO origemDaInternacaoDTO = origemDaInternacaoMapper
            .toDto(updatedOrigemDaInternacao);

        restOrigemDaInternacaoMockMvc.perform(put("/api/origem-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaInternacaoDTO)))
            .andExpect(status().isOk());

        // Validate the OrigemDaInternacao in the database
        List<OrigemDaInternacao> origemDaInternacaoList = origemDaInternacaoRepository.findAll();
        assertThat(origemDaInternacaoList).hasSize(databaseSizeBeforeUpdate);
        OrigemDaInternacao testOrigemDaInternacao = origemDaInternacaoList
            .get(origemDaInternacaoList.size() - 1);
        assertThat(testOrigemDaInternacao.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the OrigemDaInternacao in Elasticsearch
        verify(mockOrigemDaInternacaoSearchRepository, times(1)).save(testOrigemDaInternacao);
    }

    @Test
    @Transactional
    public void updateNonExistingOrigemDaInternacao() throws Exception {
        int databaseSizeBeforeUpdate = origemDaInternacaoRepository.findAll().size();

        // Create the OrigemDaInternacao
        OrigemDaInternacaoDTO origemDaInternacaoDTO = origemDaInternacaoMapper
            .toDto(origemDaInternacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrigemDaInternacaoMockMvc.perform(put("/api/origem-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaInternacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrigemDaInternacao in the database
        List<OrigemDaInternacao> origemDaInternacaoList = origemDaInternacaoRepository.findAll();
        assertThat(origemDaInternacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrigemDaInternacao in Elasticsearch
        verify(mockOrigemDaInternacaoSearchRepository, times(0)).save(origemDaInternacao);
    }

    @Test
    @Transactional
    public void deleteOrigemDaInternacao() throws Exception {
        // Initialize the database
        origemDaInternacaoRepository.saveAndFlush(origemDaInternacao);

        int databaseSizeBeforeDelete = origemDaInternacaoRepository.findAll().size();

        // Delete the origemDaInternacao
        restOrigemDaInternacaoMockMvc
            .perform(delete("/api/origem-da-internacaos/{id}", origemDaInternacao.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrigemDaInternacao> origemDaInternacaoList = origemDaInternacaoRepository.findAll();
        assertThat(origemDaInternacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrigemDaInternacao in Elasticsearch
        verify(mockOrigemDaInternacaoSearchRepository, times(1))
            .deleteById(origemDaInternacao.getId());
    }

    @Test
    @Transactional
    public void searchOrigemDaInternacao() throws Exception {
        // Initialize the database
        origemDaInternacaoRepository.saveAndFlush(origemDaInternacao);
        when(mockOrigemDaInternacaoSearchRepository
            .search(queryStringQuery("id:" + origemDaInternacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(
                new PageImpl<>(Collections.singletonList(origemDaInternacao), PageRequest.of(0, 1),
                    1));
        // Search the origemDaInternacao
        restOrigemDaInternacaoMockMvc.perform(
            get("/api/_search/origem-da-internacaos?query=id:" + origemDaInternacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origemDaInternacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemDaInternacao.class);
        OrigemDaInternacao origemDaInternacao1 = new OrigemDaInternacao();
        origemDaInternacao1.setId(1L);
        OrigemDaInternacao origemDaInternacao2 = new OrigemDaInternacao();
        origemDaInternacao2.setId(origemDaInternacao1.getId());
        assertThat(origemDaInternacao1).isEqualTo(origemDaInternacao2);
        origemDaInternacao2.setId(2L);
        assertThat(origemDaInternacao1).isNotEqualTo(origemDaInternacao2);
        origemDaInternacao1.setId(null);
        assertThat(origemDaInternacao1).isNotEqualTo(origemDaInternacao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemDaInternacaoDTO.class);
        OrigemDaInternacaoDTO origemDaInternacaoDTO1 = new OrigemDaInternacaoDTO();
        origemDaInternacaoDTO1.setId(1L);
        OrigemDaInternacaoDTO origemDaInternacaoDTO2 = new OrigemDaInternacaoDTO();
        assertThat(origemDaInternacaoDTO1).isNotEqualTo(origemDaInternacaoDTO2);
        origemDaInternacaoDTO2.setId(origemDaInternacaoDTO1.getId());
        assertThat(origemDaInternacaoDTO1).isEqualTo(origemDaInternacaoDTO2);
        origemDaInternacaoDTO2.setId(2L);
        assertThat(origemDaInternacaoDTO1).isNotEqualTo(origemDaInternacaoDTO2);
        origemDaInternacaoDTO1.setId(null);
        assertThat(origemDaInternacaoDTO1).isNotEqualTo(origemDaInternacaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(origemDaInternacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(origemDaInternacaoMapper.fromId(null)).isNull();
    }
}
