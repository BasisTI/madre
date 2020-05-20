package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.TipoUnidadeDieta;
import br.com.basis.madre.prescricao.repository.TipoUnidadeDietaRepository;
import br.com.basis.madre.prescricao.repository.search.TipoUnidadeDietaSearchRepository;
import br.com.basis.madre.prescricao.service.TipoUnidadeDietaService;
import br.com.basis.madre.prescricao.service.dto.TipoUnidadeDietaDTO;
import br.com.basis.madre.prescricao.service.mapper.TipoUnidadeDietaMapper;
import br.com.basis.madre.prescricao.web.rest.errors.ExceptionTranslator;

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

import static br.com.basis.madre.prescricao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoUnidadeDietaResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class TipoUnidadeDietaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAA";
    private static final String UPDATED_SIGLA = "BBB";

    @Autowired
    private TipoUnidadeDietaRepository tipoUnidadeDietaRepository;

    @Autowired
    private TipoUnidadeDietaMapper tipoUnidadeDietaMapper;

    @Autowired
    private TipoUnidadeDietaService tipoUnidadeDietaService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.TipoUnidadeDietaSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoUnidadeDietaSearchRepository mockTipoUnidadeDietaSearchRepository;

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

    private MockMvc restTipoUnidadeDietaMockMvc;

    private TipoUnidadeDieta tipoUnidadeDieta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoUnidadeDietaResource tipoUnidadeDietaResource = new TipoUnidadeDietaResource(tipoUnidadeDietaService);
        this.restTipoUnidadeDietaMockMvc = MockMvcBuilders.standaloneSetup(tipoUnidadeDietaResource)
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
    public static TipoUnidadeDieta createEntity(EntityManager em) {
        TipoUnidadeDieta tipoUnidadeDieta = new TipoUnidadeDieta()
            .descricao(DEFAULT_DESCRICAO)
            .sigla(DEFAULT_SIGLA);
        return tipoUnidadeDieta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoUnidadeDieta createUpdatedEntity(EntityManager em) {
        TipoUnidadeDieta tipoUnidadeDieta = new TipoUnidadeDieta()
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        return tipoUnidadeDieta;
    }

    @BeforeEach
    public void initTest() {
        tipoUnidadeDieta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoUnidadeDieta() throws Exception {
        int databaseSizeBeforeCreate = tipoUnidadeDietaRepository.findAll().size();

        // Create the TipoUnidadeDieta
        TipoUnidadeDietaDTO tipoUnidadeDietaDTO = tipoUnidadeDietaMapper.toDto(tipoUnidadeDieta);
        restTipoUnidadeDietaMockMvc.perform(post("/api/tipo-unidade-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDietaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoUnidadeDieta in the database
        List<TipoUnidadeDieta> tipoUnidadeDietaList = tipoUnidadeDietaRepository.findAll();
        assertThat(tipoUnidadeDietaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoUnidadeDieta testTipoUnidadeDieta = tipoUnidadeDietaList.get(tipoUnidadeDietaList.size() - 1);
        assertThat(testTipoUnidadeDieta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTipoUnidadeDieta.getSigla()).isEqualTo(DEFAULT_SIGLA);

        // Validate the TipoUnidadeDieta in Elasticsearch
        verify(mockTipoUnidadeDietaSearchRepository, times(1)).save(testTipoUnidadeDieta);
    }

    @Test
    @Transactional
    public void createTipoUnidadeDietaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoUnidadeDietaRepository.findAll().size();

        // Create the TipoUnidadeDieta with an existing ID
        tipoUnidadeDieta.setId(1L);
        TipoUnidadeDietaDTO tipoUnidadeDietaDTO = tipoUnidadeDietaMapper.toDto(tipoUnidadeDieta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoUnidadeDietaMockMvc.perform(post("/api/tipo-unidade-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDietaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoUnidadeDieta in the database
        List<TipoUnidadeDieta> tipoUnidadeDietaList = tipoUnidadeDietaRepository.findAll();
        assertThat(tipoUnidadeDietaList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoUnidadeDieta in Elasticsearch
        verify(mockTipoUnidadeDietaSearchRepository, times(0)).save(tipoUnidadeDieta);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoUnidadeDietaRepository.findAll().size();
        // set the field null
        tipoUnidadeDieta.setDescricao(null);

        // Create the TipoUnidadeDieta, which fails.
        TipoUnidadeDietaDTO tipoUnidadeDietaDTO = tipoUnidadeDietaMapper.toDto(tipoUnidadeDieta);

        restTipoUnidadeDietaMockMvc.perform(post("/api/tipo-unidade-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDietaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoUnidadeDieta> tipoUnidadeDietaList = tipoUnidadeDietaRepository.findAll();
        assertThat(tipoUnidadeDietaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoUnidadeDietaRepository.findAll().size();
        // set the field null
        tipoUnidadeDieta.setSigla(null);

        // Create the TipoUnidadeDieta, which fails.
        TipoUnidadeDietaDTO tipoUnidadeDietaDTO = tipoUnidadeDietaMapper.toDto(tipoUnidadeDieta);

        restTipoUnidadeDietaMockMvc.perform(post("/api/tipo-unidade-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDietaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoUnidadeDieta> tipoUnidadeDietaList = tipoUnidadeDietaRepository.findAll();
        assertThat(tipoUnidadeDietaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoUnidadeDietas() throws Exception {
        // Initialize the database
        tipoUnidadeDietaRepository.saveAndFlush(tipoUnidadeDieta);

        // Get all the tipoUnidadeDietaList
        restTipoUnidadeDietaMockMvc.perform(get("/api/tipo-unidade-dietas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoUnidadeDieta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getTipoUnidadeDieta() throws Exception {
        // Initialize the database
        tipoUnidadeDietaRepository.saveAndFlush(tipoUnidadeDieta);

        // Get the tipoUnidadeDieta
        restTipoUnidadeDietaMockMvc.perform(get("/api/tipo-unidade-dietas/{id}", tipoUnidadeDieta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoUnidadeDieta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    public void getNonExistingTipoUnidadeDieta() throws Exception {
        // Get the tipoUnidadeDieta
        restTipoUnidadeDietaMockMvc.perform(get("/api/tipo-unidade-dietas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoUnidadeDieta() throws Exception {
        // Initialize the database
        tipoUnidadeDietaRepository.saveAndFlush(tipoUnidadeDieta);

        int databaseSizeBeforeUpdate = tipoUnidadeDietaRepository.findAll().size();

        // Update the tipoUnidadeDieta
        TipoUnidadeDieta updatedTipoUnidadeDieta = tipoUnidadeDietaRepository.findById(tipoUnidadeDieta.getId()).get();
        // Disconnect from session so that the updates on updatedTipoUnidadeDieta are not directly saved in db
        em.detach(updatedTipoUnidadeDieta);
        updatedTipoUnidadeDieta
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        TipoUnidadeDietaDTO tipoUnidadeDietaDTO = tipoUnidadeDietaMapper.toDto(updatedTipoUnidadeDieta);

        restTipoUnidadeDietaMockMvc.perform(put("/api/tipo-unidade-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDietaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoUnidadeDieta in the database
        List<TipoUnidadeDieta> tipoUnidadeDietaList = tipoUnidadeDietaRepository.findAll();
        assertThat(tipoUnidadeDietaList).hasSize(databaseSizeBeforeUpdate);
        TipoUnidadeDieta testTipoUnidadeDieta = tipoUnidadeDietaList.get(tipoUnidadeDietaList.size() - 1);
        assertThat(testTipoUnidadeDieta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTipoUnidadeDieta.getSigla()).isEqualTo(UPDATED_SIGLA);

        // Validate the TipoUnidadeDieta in Elasticsearch
        verify(mockTipoUnidadeDietaSearchRepository, times(1)).save(testTipoUnidadeDieta);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoUnidadeDieta() throws Exception {
        int databaseSizeBeforeUpdate = tipoUnidadeDietaRepository.findAll().size();

        // Create the TipoUnidadeDieta
        TipoUnidadeDietaDTO tipoUnidadeDietaDTO = tipoUnidadeDietaMapper.toDto(tipoUnidadeDieta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoUnidadeDietaMockMvc.perform(put("/api/tipo-unidade-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDietaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoUnidadeDieta in the database
        List<TipoUnidadeDieta> tipoUnidadeDietaList = tipoUnidadeDietaRepository.findAll();
        assertThat(tipoUnidadeDietaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoUnidadeDieta in Elasticsearch
        verify(mockTipoUnidadeDietaSearchRepository, times(0)).save(tipoUnidadeDieta);
    }

    @Test
    @Transactional
    public void deleteTipoUnidadeDieta() throws Exception {
        // Initialize the database
        tipoUnidadeDietaRepository.saveAndFlush(tipoUnidadeDieta);

        int databaseSizeBeforeDelete = tipoUnidadeDietaRepository.findAll().size();

        // Delete the tipoUnidadeDieta
        restTipoUnidadeDietaMockMvc.perform(delete("/api/tipo-unidade-dietas/{id}", tipoUnidadeDieta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoUnidadeDieta> tipoUnidadeDietaList = tipoUnidadeDietaRepository.findAll();
        assertThat(tipoUnidadeDietaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoUnidadeDieta in Elasticsearch
        verify(mockTipoUnidadeDietaSearchRepository, times(1)).deleteById(tipoUnidadeDieta.getId());
    }

    @Test
    @Transactional
    public void searchTipoUnidadeDieta() throws Exception {
        // Initialize the database
        tipoUnidadeDietaRepository.saveAndFlush(tipoUnidadeDieta);
        when(mockTipoUnidadeDietaSearchRepository.search(queryStringQuery("id:" + tipoUnidadeDieta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoUnidadeDieta), PageRequest.of(0, 1), 1));
        // Search the tipoUnidadeDieta
        restTipoUnidadeDietaMockMvc.perform(get("/api/_search/tipo-unidade-dietas?query=id:" + tipoUnidadeDieta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoUnidadeDieta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoUnidadeDieta.class);
        TipoUnidadeDieta tipoUnidadeDieta1 = new TipoUnidadeDieta();
        tipoUnidadeDieta1.setId(1L);
        TipoUnidadeDieta tipoUnidadeDieta2 = new TipoUnidadeDieta();
        tipoUnidadeDieta2.setId(tipoUnidadeDieta1.getId());
        assertThat(tipoUnidadeDieta1).isEqualTo(tipoUnidadeDieta2);
        tipoUnidadeDieta2.setId(2L);
        assertThat(tipoUnidadeDieta1).isNotEqualTo(tipoUnidadeDieta2);
        tipoUnidadeDieta1.setId(null);
        assertThat(tipoUnidadeDieta1).isNotEqualTo(tipoUnidadeDieta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoUnidadeDietaDTO.class);
        TipoUnidadeDietaDTO tipoUnidadeDietaDTO1 = new TipoUnidadeDietaDTO();
        tipoUnidadeDietaDTO1.setId(1L);
        TipoUnidadeDietaDTO tipoUnidadeDietaDTO2 = new TipoUnidadeDietaDTO();
        assertThat(tipoUnidadeDietaDTO1).isNotEqualTo(tipoUnidadeDietaDTO2);
        tipoUnidadeDietaDTO2.setId(tipoUnidadeDietaDTO1.getId());
        assertThat(tipoUnidadeDietaDTO1).isEqualTo(tipoUnidadeDietaDTO2);
        tipoUnidadeDietaDTO2.setId(2L);
        assertThat(tipoUnidadeDietaDTO1).isNotEqualTo(tipoUnidadeDietaDTO2);
        tipoUnidadeDietaDTO1.setId(null);
        assertThat(tipoUnidadeDietaDTO1).isNotEqualTo(tipoUnidadeDietaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoUnidadeDietaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoUnidadeDietaMapper.fromId(null)).isNull();
    }
}
