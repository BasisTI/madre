package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.CaraterDaInternacao;
import br.com.basis.madre.repository.CaraterDaInternacaoRepository;
import br.com.basis.madre.repository.search.CaraterDaInternacaoSearchRepository;
import br.com.basis.madre.service.CaraterDaInternacaoService;
import br.com.basis.madre.service.dto.CaraterDaInternacaoDTO;
import br.com.basis.madre.service.mapper.CaraterDaInternacaoMapper;
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
 * Integration tests for the {@link CaraterDaInternacaoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class CaraterDaInternacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private CaraterDaInternacaoRepository caraterDaInternacaoRepository;

    @Autowired
    private CaraterDaInternacaoMapper caraterDaInternacaoMapper;

    @Autowired
    private CaraterDaInternacaoService caraterDaInternacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.CaraterDaInternacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CaraterDaInternacaoSearchRepository mockCaraterDaInternacaoSearchRepository;

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

    private MockMvc restCaraterDaInternacaoMockMvc;

    private CaraterDaInternacao caraterDaInternacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaraterDaInternacaoResource caraterDaInternacaoResource = new CaraterDaInternacaoResource(caraterDaInternacaoService);
        this.restCaraterDaInternacaoMockMvc = MockMvcBuilders.standaloneSetup(caraterDaInternacaoResource)
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
    public static CaraterDaInternacao createEntity(EntityManager em) {
        CaraterDaInternacao caraterDaInternacao = new CaraterDaInternacao()
            .nome(DEFAULT_NOME);
        return caraterDaInternacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaraterDaInternacao createUpdatedEntity(EntityManager em) {
        CaraterDaInternacao caraterDaInternacao = new CaraterDaInternacao()
            .nome(UPDATED_NOME);
        return caraterDaInternacao;
    }

    @BeforeEach
    public void initTest() {
        caraterDaInternacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaraterDaInternacao() throws Exception {
        int databaseSizeBeforeCreate = caraterDaInternacaoRepository.findAll().size();

        // Create the CaraterDaInternacao
        CaraterDaInternacaoDTO caraterDaInternacaoDTO = caraterDaInternacaoMapper.toDto(caraterDaInternacao);
        restCaraterDaInternacaoMockMvc.perform(post("/api/carater-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caraterDaInternacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the CaraterDaInternacao in the database
        List<CaraterDaInternacao> caraterDaInternacaoList = caraterDaInternacaoRepository.findAll();
        assertThat(caraterDaInternacaoList).hasSize(databaseSizeBeforeCreate + 1);
        CaraterDaInternacao testCaraterDaInternacao = caraterDaInternacaoList.get(caraterDaInternacaoList.size() - 1);
        assertThat(testCaraterDaInternacao.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the CaraterDaInternacao in Elasticsearch
        verify(mockCaraterDaInternacaoSearchRepository, times(1)).save(testCaraterDaInternacao);
    }

    @Test
    @Transactional
    public void createCaraterDaInternacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caraterDaInternacaoRepository.findAll().size();

        // Create the CaraterDaInternacao with an existing ID
        caraterDaInternacao.setId(1L);
        CaraterDaInternacaoDTO caraterDaInternacaoDTO = caraterDaInternacaoMapper.toDto(caraterDaInternacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaraterDaInternacaoMockMvc.perform(post("/api/carater-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caraterDaInternacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CaraterDaInternacao in the database
        List<CaraterDaInternacao> caraterDaInternacaoList = caraterDaInternacaoRepository.findAll();
        assertThat(caraterDaInternacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CaraterDaInternacao in Elasticsearch
        verify(mockCaraterDaInternacaoSearchRepository, times(0)).save(caraterDaInternacao);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = caraterDaInternacaoRepository.findAll().size();
        // set the field null
        caraterDaInternacao.setNome(null);

        // Create the CaraterDaInternacao, which fails.
        CaraterDaInternacaoDTO caraterDaInternacaoDTO = caraterDaInternacaoMapper.toDto(caraterDaInternacao);

        restCaraterDaInternacaoMockMvc.perform(post("/api/carater-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caraterDaInternacaoDTO)))
            .andExpect(status().isBadRequest());

        List<CaraterDaInternacao> caraterDaInternacaoList = caraterDaInternacaoRepository.findAll();
        assertThat(caraterDaInternacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCaraterDaInternacaos() throws Exception {
        // Initialize the database
        caraterDaInternacaoRepository.saveAndFlush(caraterDaInternacao);

        // Get all the caraterDaInternacaoList
        restCaraterDaInternacaoMockMvc.perform(get("/api/carater-da-internacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caraterDaInternacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getCaraterDaInternacao() throws Exception {
        // Initialize the database
        caraterDaInternacaoRepository.saveAndFlush(caraterDaInternacao);

        // Get the caraterDaInternacao
        restCaraterDaInternacaoMockMvc.perform(get("/api/carater-da-internacaos/{id}", caraterDaInternacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caraterDaInternacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingCaraterDaInternacao() throws Exception {
        // Get the caraterDaInternacao
        restCaraterDaInternacaoMockMvc.perform(get("/api/carater-da-internacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaraterDaInternacao() throws Exception {
        // Initialize the database
        caraterDaInternacaoRepository.saveAndFlush(caraterDaInternacao);

        int databaseSizeBeforeUpdate = caraterDaInternacaoRepository.findAll().size();

        // Update the caraterDaInternacao
        CaraterDaInternacao updatedCaraterDaInternacao = caraterDaInternacaoRepository.findById(caraterDaInternacao.getId()).get();
        // Disconnect from session so that the updates on updatedCaraterDaInternacao are not directly saved in db
        em.detach(updatedCaraterDaInternacao);
        updatedCaraterDaInternacao
            .nome(UPDATED_NOME);
        CaraterDaInternacaoDTO caraterDaInternacaoDTO = caraterDaInternacaoMapper.toDto(updatedCaraterDaInternacao);

        restCaraterDaInternacaoMockMvc.perform(put("/api/carater-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caraterDaInternacaoDTO)))
            .andExpect(status().isOk());

        // Validate the CaraterDaInternacao in the database
        List<CaraterDaInternacao> caraterDaInternacaoList = caraterDaInternacaoRepository.findAll();
        assertThat(caraterDaInternacaoList).hasSize(databaseSizeBeforeUpdate);
        CaraterDaInternacao testCaraterDaInternacao = caraterDaInternacaoList.get(caraterDaInternacaoList.size() - 1);
        assertThat(testCaraterDaInternacao.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the CaraterDaInternacao in Elasticsearch
        verify(mockCaraterDaInternacaoSearchRepository, times(1)).save(testCaraterDaInternacao);
    }

    @Test
    @Transactional
    public void updateNonExistingCaraterDaInternacao() throws Exception {
        int databaseSizeBeforeUpdate = caraterDaInternacaoRepository.findAll().size();

        // Create the CaraterDaInternacao
        CaraterDaInternacaoDTO caraterDaInternacaoDTO = caraterDaInternacaoMapper.toDto(caraterDaInternacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaraterDaInternacaoMockMvc.perform(put("/api/carater-da-internacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caraterDaInternacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CaraterDaInternacao in the database
        List<CaraterDaInternacao> caraterDaInternacaoList = caraterDaInternacaoRepository.findAll();
        assertThat(caraterDaInternacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CaraterDaInternacao in Elasticsearch
        verify(mockCaraterDaInternacaoSearchRepository, times(0)).save(caraterDaInternacao);
    }

    @Test
    @Transactional
    public void deleteCaraterDaInternacao() throws Exception {
        // Initialize the database
        caraterDaInternacaoRepository.saveAndFlush(caraterDaInternacao);

        int databaseSizeBeforeDelete = caraterDaInternacaoRepository.findAll().size();

        // Delete the caraterDaInternacao
        restCaraterDaInternacaoMockMvc.perform(delete("/api/carater-da-internacaos/{id}", caraterDaInternacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaraterDaInternacao> caraterDaInternacaoList = caraterDaInternacaoRepository.findAll();
        assertThat(caraterDaInternacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CaraterDaInternacao in Elasticsearch
        verify(mockCaraterDaInternacaoSearchRepository, times(1)).deleteById(caraterDaInternacao.getId());
    }

    @Test
    @Transactional
    public void searchCaraterDaInternacao() throws Exception {
        // Initialize the database
        caraterDaInternacaoRepository.saveAndFlush(caraterDaInternacao);
        when(mockCaraterDaInternacaoSearchRepository.search(queryStringQuery("id:" + caraterDaInternacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(caraterDaInternacao), PageRequest.of(0, 1), 1));
        // Search the caraterDaInternacao
        restCaraterDaInternacaoMockMvc.perform(get("/api/_search/carater-da-internacaos?query=id:" + caraterDaInternacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caraterDaInternacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaraterDaInternacao.class);
        CaraterDaInternacao caraterDaInternacao1 = new CaraterDaInternacao();
        caraterDaInternacao1.setId(1L);
        CaraterDaInternacao caraterDaInternacao2 = new CaraterDaInternacao();
        caraterDaInternacao2.setId(caraterDaInternacao1.getId());
        assertThat(caraterDaInternacao1).isEqualTo(caraterDaInternacao2);
        caraterDaInternacao2.setId(2L);
        assertThat(caraterDaInternacao1).isNotEqualTo(caraterDaInternacao2);
        caraterDaInternacao1.setId(null);
        assertThat(caraterDaInternacao1).isNotEqualTo(caraterDaInternacao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaraterDaInternacaoDTO.class);
        CaraterDaInternacaoDTO caraterDaInternacaoDTO1 = new CaraterDaInternacaoDTO();
        caraterDaInternacaoDTO1.setId(1L);
        CaraterDaInternacaoDTO caraterDaInternacaoDTO2 = new CaraterDaInternacaoDTO();
        assertThat(caraterDaInternacaoDTO1).isNotEqualTo(caraterDaInternacaoDTO2);
        caraterDaInternacaoDTO2.setId(caraterDaInternacaoDTO1.getId());
        assertThat(caraterDaInternacaoDTO1).isEqualTo(caraterDaInternacaoDTO2);
        caraterDaInternacaoDTO2.setId(2L);
        assertThat(caraterDaInternacaoDTO1).isNotEqualTo(caraterDaInternacaoDTO2);
        caraterDaInternacaoDTO1.setId(null);
        assertThat(caraterDaInternacaoDTO1).isNotEqualTo(caraterDaInternacaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(caraterDaInternacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(caraterDaInternacaoMapper.fromId(null)).isNull();
    }
}
