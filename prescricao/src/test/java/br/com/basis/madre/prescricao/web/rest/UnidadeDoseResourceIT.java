package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.UnidadeDose;
import br.com.basis.madre.prescricao.repository.UnidadeDoseRepository;
import br.com.basis.madre.prescricao.repository.search.UnidadeDoseSearchRepository;
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
 * Integration tests for the {@link UnidadeDoseResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class UnidadeDoseResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    @Autowired
    private UnidadeDoseRepository unidadeDoseRepository;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.UnidadeDoseSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadeDoseSearchRepository mockUnidadeDoseSearchRepository;

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

    private MockMvc restUnidadeDoseMockMvc;

    private UnidadeDose unidadeDose;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeDoseResource unidadeDoseResource = new UnidadeDoseResource(unidadeDoseRepository, mockUnidadeDoseSearchRepository);
        this.restUnidadeDoseMockMvc = MockMvcBuilders.standaloneSetup(unidadeDoseResource)
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
    public static UnidadeDose createEntity(EntityManager em) {
        UnidadeDose unidadeDose = new UnidadeDose()
            .descricao(DEFAULT_DESCRICAO)
            .sigla(DEFAULT_SIGLA);
        return unidadeDose;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeDose createUpdatedEntity(EntityManager em) {
        UnidadeDose unidadeDose = new UnidadeDose()
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        return unidadeDose;
    }

    @BeforeEach
    public void initTest() {
        unidadeDose = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeDose() throws Exception {
        int databaseSizeBeforeCreate = unidadeDoseRepository.findAll().size();

        // Create the UnidadeDose
        restUnidadeDoseMockMvc.perform(post("/api/unidade-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDose)))
            .andExpect(status().isCreated());

        // Validate the UnidadeDose in the database
        List<UnidadeDose> unidadeDoseList = unidadeDoseRepository.findAll();
        assertThat(unidadeDoseList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeDose testUnidadeDose = unidadeDoseList.get(unidadeDoseList.size() - 1);
        assertThat(testUnidadeDose.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testUnidadeDose.getSigla()).isEqualTo(DEFAULT_SIGLA);

        // Validate the UnidadeDose in Elasticsearch
        verify(mockUnidadeDoseSearchRepository, times(1)).save(testUnidadeDose);
    }

    @Test
    @Transactional
    public void createUnidadeDoseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeDoseRepository.findAll().size();

        // Create the UnidadeDose with an existing ID
        unidadeDose.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeDoseMockMvc.perform(post("/api/unidade-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDose)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeDose in the database
        List<UnidadeDose> unidadeDoseList = unidadeDoseRepository.findAll();
        assertThat(unidadeDoseList).hasSize(databaseSizeBeforeCreate);

        // Validate the UnidadeDose in Elasticsearch
        verify(mockUnidadeDoseSearchRepository, times(0)).save(unidadeDose);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeDoseRepository.findAll().size();
        // set the field null
        unidadeDose.setDescricao(null);

        // Create the UnidadeDose, which fails.

        restUnidadeDoseMockMvc.perform(post("/api/unidade-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDose)))
            .andExpect(status().isBadRequest());

        List<UnidadeDose> unidadeDoseList = unidadeDoseRepository.findAll();
        assertThat(unidadeDoseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeDoseRepository.findAll().size();
        // set the field null
        unidadeDose.setSigla(null);

        // Create the UnidadeDose, which fails.

        restUnidadeDoseMockMvc.perform(post("/api/unidade-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDose)))
            .andExpect(status().isBadRequest());

        List<UnidadeDose> unidadeDoseList = unidadeDoseRepository.findAll();
        assertThat(unidadeDoseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeDoses() throws Exception {
        // Initialize the database
        unidadeDoseRepository.saveAndFlush(unidadeDose);

        // Get all the unidadeDoseList
        restUnidadeDoseMockMvc.perform(get("/api/unidade-doses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeDose.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getUnidadeDose() throws Exception {
        // Initialize the database
        unidadeDoseRepository.saveAndFlush(unidadeDose);

        // Get the unidadeDose
        restUnidadeDoseMockMvc.perform(get("/api/unidade-doses/{id}", unidadeDose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeDose.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeDose() throws Exception {
        // Get the unidadeDose
        restUnidadeDoseMockMvc.perform(get("/api/unidade-doses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeDose() throws Exception {
        // Initialize the database
        unidadeDoseRepository.saveAndFlush(unidadeDose);

        int databaseSizeBeforeUpdate = unidadeDoseRepository.findAll().size();

        // Update the unidadeDose
        UnidadeDose updatedUnidadeDose = unidadeDoseRepository.findById(unidadeDose.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeDose are not directly saved in db
        em.detach(updatedUnidadeDose);
        updatedUnidadeDose
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);

        restUnidadeDoseMockMvc.perform(put("/api/unidade-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnidadeDose)))
            .andExpect(status().isOk());

        // Validate the UnidadeDose in the database
        List<UnidadeDose> unidadeDoseList = unidadeDoseRepository.findAll();
        assertThat(unidadeDoseList).hasSize(databaseSizeBeforeUpdate);
        UnidadeDose testUnidadeDose = unidadeDoseList.get(unidadeDoseList.size() - 1);
        assertThat(testUnidadeDose.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testUnidadeDose.getSigla()).isEqualTo(UPDATED_SIGLA);

        // Validate the UnidadeDose in Elasticsearch
        verify(mockUnidadeDoseSearchRepository, times(1)).save(testUnidadeDose);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeDose() throws Exception {
        int databaseSizeBeforeUpdate = unidadeDoseRepository.findAll().size();

        // Create the UnidadeDose

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeDoseMockMvc.perform(put("/api/unidade-doses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDose)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeDose in the database
        List<UnidadeDose> unidadeDoseList = unidadeDoseRepository.findAll();
        assertThat(unidadeDoseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UnidadeDose in Elasticsearch
        verify(mockUnidadeDoseSearchRepository, times(0)).save(unidadeDose);
    }

    @Test
    @Transactional
    public void deleteUnidadeDose() throws Exception {
        // Initialize the database
        unidadeDoseRepository.saveAndFlush(unidadeDose);

        int databaseSizeBeforeDelete = unidadeDoseRepository.findAll().size();

        // Delete the unidadeDose
        restUnidadeDoseMockMvc.perform(delete("/api/unidade-doses/{id}", unidadeDose.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeDose> unidadeDoseList = unidadeDoseRepository.findAll();
        assertThat(unidadeDoseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UnidadeDose in Elasticsearch
        verify(mockUnidadeDoseSearchRepository, times(1)).deleteById(unidadeDose.getId());
    }

    @Test
    @Transactional
    public void searchUnidadeDose() throws Exception {
        // Initialize the database
        unidadeDoseRepository.saveAndFlush(unidadeDose);
        when(mockUnidadeDoseSearchRepository.search(queryStringQuery("id:" + unidadeDose.getId())))
            .thenReturn(Collections.singletonList(unidadeDose));
        // Search the unidadeDose
        restUnidadeDoseMockMvc.perform(get("/api/_search/unidade-doses?query=id:" + unidadeDose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeDose.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeDose.class);
        UnidadeDose unidadeDose1 = new UnidadeDose();
        unidadeDose1.setId(1L);
        UnidadeDose unidadeDose2 = new UnidadeDose();
        unidadeDose2.setId(unidadeDose1.getId());
        assertThat(unidadeDose1).isEqualTo(unidadeDose2);
        unidadeDose2.setId(2L);
        assertThat(unidadeDose1).isNotEqualTo(unidadeDose2);
        unidadeDose1.setId(null);
        assertThat(unidadeDose1).isNotEqualTo(unidadeDose2);
    }
}
