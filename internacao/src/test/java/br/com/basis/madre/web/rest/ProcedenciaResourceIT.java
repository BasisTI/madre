package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Procedencia;
import br.com.basis.madre.repository.ProcedenciaRepository;
import br.com.basis.madre.repository.search.ProcedenciaSearchRepository;
import br.com.basis.madre.service.ProcedenciaService;
import br.com.basis.madre.service.dto.ProcedenciaDTO;
import br.com.basis.madre.service.mapper.ProcedenciaMapper;
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
 * Integration tests for the {@link ProcedenciaResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class ProcedenciaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ProcedenciaRepository procedenciaRepository;

    @Autowired
    private ProcedenciaMapper procedenciaMapper;

    @Autowired
    private ProcedenciaService procedenciaService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.ProcedenciaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProcedenciaSearchRepository mockProcedenciaSearchRepository;

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

    private MockMvc restProcedenciaMockMvc;

    private Procedencia procedencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcedenciaResource procedenciaResource = new ProcedenciaResource(procedenciaService);
        this.restProcedenciaMockMvc = MockMvcBuilders.standaloneSetup(procedenciaResource)
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
    public static Procedencia createEntity(EntityManager em) {
        Procedencia procedencia = new Procedencia()
            .descricao(DEFAULT_DESCRICAO);
        return procedencia;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static Procedencia createUpdatedEntity(EntityManager em) {
        Procedencia procedencia = new Procedencia()
            .descricao(UPDATED_DESCRICAO);
        return procedencia;
    }

    @BeforeEach
    public void initTest() {
        procedencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedencia() throws Exception {
        int databaseSizeBeforeCreate = procedenciaRepository.findAll().size();

        // Create the Procedencia
        ProcedenciaDTO procedenciaDTO = procedenciaMapper.toDto(procedencia);
        restProcedenciaMockMvc.perform(post("/api/procedencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Procedencia in the database
        List<Procedencia> procedenciaList = procedenciaRepository.findAll();
        assertThat(procedenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Procedencia testProcedencia = procedenciaList.get(procedenciaList.size() - 1);
        assertThat(testProcedencia.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Procedencia in Elasticsearch
        verify(mockProcedenciaSearchRepository, times(1)).save(testProcedencia);
    }

    @Test
    @Transactional
    public void createProcedenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedenciaRepository.findAll().size();

        // Create the Procedencia with an existing ID
        procedencia.setId(1L);
        ProcedenciaDTO procedenciaDTO = procedenciaMapper.toDto(procedencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedenciaMockMvc.perform(post("/api/procedencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Procedencia in the database
        List<Procedencia> procedenciaList = procedenciaRepository.findAll();
        assertThat(procedenciaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Procedencia in Elasticsearch
        verify(mockProcedenciaSearchRepository, times(0)).save(procedencia);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedenciaRepository.findAll().size();
        // set the field null
        procedencia.setDescricao(null);

        // Create the Procedencia, which fails.
        ProcedenciaDTO procedenciaDTO = procedenciaMapper.toDto(procedencia);

        restProcedenciaMockMvc.perform(post("/api/procedencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Procedencia> procedenciaList = procedenciaRepository.findAll();
        assertThat(procedenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcedencias() throws Exception {
        // Initialize the database
        procedenciaRepository.saveAndFlush(procedencia);

        // Get all the procedenciaList
        restProcedenciaMockMvc.perform(get("/api/procedencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getProcedencia() throws Exception {
        // Initialize the database
        procedenciaRepository.saveAndFlush(procedencia);

        // Get the procedencia
        restProcedenciaMockMvc.perform(get("/api/procedencias/{id}", procedencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedencia.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingProcedencia() throws Exception {
        // Get the procedencia
        restProcedenciaMockMvc.perform(get("/api/procedencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedencia() throws Exception {
        // Initialize the database
        procedenciaRepository.saveAndFlush(procedencia);

        int databaseSizeBeforeUpdate = procedenciaRepository.findAll().size();

        // Update the procedencia
        Procedencia updatedProcedencia = procedenciaRepository.findById(procedencia.getId()).get();
        // Disconnect from session so that the updates on updatedProcedencia are not directly saved in db
        em.detach(updatedProcedencia);
        updatedProcedencia
            .descricao(UPDATED_DESCRICAO);
        ProcedenciaDTO procedenciaDTO = procedenciaMapper.toDto(updatedProcedencia);

        restProcedenciaMockMvc.perform(put("/api/procedencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedenciaDTO)))
            .andExpect(status().isOk());

        // Validate the Procedencia in the database
        List<Procedencia> procedenciaList = procedenciaRepository.findAll();
        assertThat(procedenciaList).hasSize(databaseSizeBeforeUpdate);
        Procedencia testProcedencia = procedenciaList.get(procedenciaList.size() - 1);
        assertThat(testProcedencia.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Procedencia in Elasticsearch
        verify(mockProcedenciaSearchRepository, times(1)).save(testProcedencia);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedencia() throws Exception {
        int databaseSizeBeforeUpdate = procedenciaRepository.findAll().size();

        // Create the Procedencia
        ProcedenciaDTO procedenciaDTO = procedenciaMapper.toDto(procedencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedenciaMockMvc.perform(put("/api/procedencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Procedencia in the database
        List<Procedencia> procedenciaList = procedenciaRepository.findAll();
        assertThat(procedenciaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Procedencia in Elasticsearch
        verify(mockProcedenciaSearchRepository, times(0)).save(procedencia);
    }

    @Test
    @Transactional
    public void deleteProcedencia() throws Exception {
        // Initialize the database
        procedenciaRepository.saveAndFlush(procedencia);

        int databaseSizeBeforeDelete = procedenciaRepository.findAll().size();

        // Delete the procedencia
        restProcedenciaMockMvc.perform(delete("/api/procedencias/{id}", procedencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Procedencia> procedenciaList = procedenciaRepository.findAll();
        assertThat(procedenciaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Procedencia in Elasticsearch
        verify(mockProcedenciaSearchRepository, times(1)).deleteById(procedencia.getId());
    }

    @Test
    @Transactional
    public void searchProcedencia() throws Exception {
        // Initialize the database
        procedenciaRepository.saveAndFlush(procedencia);
        when(mockProcedenciaSearchRepository
            .search(queryStringQuery("id:" + procedencia.getId()), PageRequest.of(0, 20)))
            .thenReturn(
                new PageImpl<>(Collections.singletonList(procedencia), PageRequest.of(0, 1), 1));
        // Search the procedencia
        restProcedenciaMockMvc
            .perform(get("/api/_search/procedencias?query=id:" + procedencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedencia.class);
        Procedencia procedencia1 = new Procedencia();
        procedencia1.setId(1L);
        Procedencia procedencia2 = new Procedencia();
        procedencia2.setId(procedencia1.getId());
        assertThat(procedencia1).isEqualTo(procedencia2);
        procedencia2.setId(2L);
        assertThat(procedencia1).isNotEqualTo(procedencia2);
        procedencia1.setId(null);
        assertThat(procedencia1).isNotEqualTo(procedencia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedenciaDTO.class);
        ProcedenciaDTO procedenciaDTO1 = new ProcedenciaDTO();
        procedenciaDTO1.setId(1L);
        ProcedenciaDTO procedenciaDTO2 = new ProcedenciaDTO();
        assertThat(procedenciaDTO1).isNotEqualTo(procedenciaDTO2);
        procedenciaDTO2.setId(procedenciaDTO1.getId());
        assertThat(procedenciaDTO1).isEqualTo(procedenciaDTO2);
        procedenciaDTO2.setId(2L);
        assertThat(procedenciaDTO1).isNotEqualTo(procedenciaDTO2);
        procedenciaDTO1.setId(null);
        assertThat(procedenciaDTO1).isNotEqualTo(procedenciaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(procedenciaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(procedenciaMapper.fromId(null)).isNull();
    }
}
