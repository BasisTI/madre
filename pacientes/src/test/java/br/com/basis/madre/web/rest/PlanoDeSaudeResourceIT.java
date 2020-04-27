package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.PlanoDeSaude;
import br.com.basis.madre.repository.PlanoDeSaudeRepository;
import br.com.basis.madre.repository.search.PlanoDeSaudeSearchRepository;
import br.com.basis.madre.service.PlanoDeSaudeService;
import br.com.basis.madre.service.dto.PlanoDeSaudeDTO;
import br.com.basis.madre.service.mapper.PlanoDeSaudeMapper;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlanoDeSaudeResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
public class PlanoDeSaudeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private PlanoDeSaudeRepository planoDeSaudeRepository;

    @Autowired
    private PlanoDeSaudeMapper planoDeSaudeMapper;

    @Autowired
    private PlanoDeSaudeService planoDeSaudeService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.PlanoDeSaudeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlanoDeSaudeSearchRepository mockPlanoDeSaudeSearchRepository;

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

    private MockMvc restPlanoDeSaudeMockMvc;

    private PlanoDeSaude planoDeSaude;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanoDeSaudeResource planoDeSaudeResource = new PlanoDeSaudeResource(planoDeSaudeService);
        this.restPlanoDeSaudeMockMvc = MockMvcBuilders.standaloneSetup(planoDeSaudeResource)
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
    public static PlanoDeSaude createEntity(EntityManager em) {
        PlanoDeSaude planoDeSaude = new PlanoDeSaude()
            .nome(DEFAULT_NOME);
        return planoDeSaude;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoDeSaude createUpdatedEntity(EntityManager em) {
        PlanoDeSaude planoDeSaude = new PlanoDeSaude()
            .nome(UPDATED_NOME);
        return planoDeSaude;
    }

    @BeforeEach
    public void initTest() {
        planoDeSaude = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoDeSaude() throws Exception {
        int databaseSizeBeforeCreate = planoDeSaudeRepository.findAll().size();

        // Create the PlanoDeSaude
        PlanoDeSaudeDTO planoDeSaudeDTO = planoDeSaudeMapper.toDto(planoDeSaude);
        restPlanoDeSaudeMockMvc.perform(post("/api/plano-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoDeSaudeDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoDeSaude in the database
        List<PlanoDeSaude> planoDeSaudeList = planoDeSaudeRepository.findAll();
        assertThat(planoDeSaudeList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoDeSaude testPlanoDeSaude = planoDeSaudeList.get(planoDeSaudeList.size() - 1);
        assertThat(testPlanoDeSaude.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the PlanoDeSaude in Elasticsearch
        verify(mockPlanoDeSaudeSearchRepository, times(1)).save(testPlanoDeSaude);
    }

    @Test
    @Transactional
    public void createPlanoDeSaudeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoDeSaudeRepository.findAll().size();

        // Create the PlanoDeSaude with an existing ID
        planoDeSaude.setId(1L);
        PlanoDeSaudeDTO planoDeSaudeDTO = planoDeSaudeMapper.toDto(planoDeSaude);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoDeSaudeMockMvc.perform(post("/api/plano-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoDeSaudeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoDeSaude in the database
        List<PlanoDeSaude> planoDeSaudeList = planoDeSaudeRepository.findAll();
        assertThat(planoDeSaudeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlanoDeSaude in Elasticsearch
        verify(mockPlanoDeSaudeSearchRepository, times(0)).save(planoDeSaude);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoDeSaudeRepository.findAll().size();
        // set the field null
        planoDeSaude.setNome(null);

        // Create the PlanoDeSaude, which fails.
        PlanoDeSaudeDTO planoDeSaudeDTO = planoDeSaudeMapper.toDto(planoDeSaude);

        restPlanoDeSaudeMockMvc.perform(post("/api/plano-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoDeSaudeDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoDeSaude> planoDeSaudeList = planoDeSaudeRepository.findAll();
        assertThat(planoDeSaudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoDeSaudes() throws Exception {
        // Initialize the database
        planoDeSaudeRepository.saveAndFlush(planoDeSaude);

        // Get all the planoDeSaudeList
        restPlanoDeSaudeMockMvc.perform(get("/api/plano-de-saudes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoDeSaude.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getPlanoDeSaude() throws Exception {
        // Initialize the database
        planoDeSaudeRepository.saveAndFlush(planoDeSaude);

        // Get the planoDeSaude
        restPlanoDeSaudeMockMvc.perform(get("/api/plano-de-saudes/{id}", planoDeSaude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planoDeSaude.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoDeSaude() throws Exception {
        // Get the planoDeSaude
        restPlanoDeSaudeMockMvc.perform(get("/api/plano-de-saudes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoDeSaude() throws Exception {
        // Initialize the database
        planoDeSaudeRepository.saveAndFlush(planoDeSaude);

        int databaseSizeBeforeUpdate = planoDeSaudeRepository.findAll().size();

        // Update the planoDeSaude
        PlanoDeSaude updatedPlanoDeSaude = planoDeSaudeRepository.findById(planoDeSaude.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoDeSaude are not directly saved in db
        em.detach(updatedPlanoDeSaude);
        updatedPlanoDeSaude
            .nome(UPDATED_NOME);
        PlanoDeSaudeDTO planoDeSaudeDTO = planoDeSaudeMapper.toDto(updatedPlanoDeSaude);

        restPlanoDeSaudeMockMvc.perform(put("/api/plano-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoDeSaudeDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoDeSaude in the database
        List<PlanoDeSaude> planoDeSaudeList = planoDeSaudeRepository.findAll();
        assertThat(planoDeSaudeList).hasSize(databaseSizeBeforeUpdate);
        PlanoDeSaude testPlanoDeSaude = planoDeSaudeList.get(planoDeSaudeList.size() - 1);
        assertThat(testPlanoDeSaude.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the PlanoDeSaude in Elasticsearch
        verify(mockPlanoDeSaudeSearchRepository, times(1)).save(testPlanoDeSaude);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoDeSaude() throws Exception {
        int databaseSizeBeforeUpdate = planoDeSaudeRepository.findAll().size();

        // Create the PlanoDeSaude
        PlanoDeSaudeDTO planoDeSaudeDTO = planoDeSaudeMapper.toDto(planoDeSaude);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoDeSaudeMockMvc.perform(put("/api/plano-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoDeSaudeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoDeSaude in the database
        List<PlanoDeSaude> planoDeSaudeList = planoDeSaudeRepository.findAll();
        assertThat(planoDeSaudeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlanoDeSaude in Elasticsearch
        verify(mockPlanoDeSaudeSearchRepository, times(0)).save(planoDeSaude);
    }

    @Test
    @Transactional
    public void deletePlanoDeSaude() throws Exception {
        // Initialize the database
        planoDeSaudeRepository.saveAndFlush(planoDeSaude);

        int databaseSizeBeforeDelete = planoDeSaudeRepository.findAll().size();

        // Delete the planoDeSaude
        restPlanoDeSaudeMockMvc.perform(delete("/api/plano-de-saudes/{id}", planoDeSaude.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoDeSaude> planoDeSaudeList = planoDeSaudeRepository.findAll();
        assertThat(planoDeSaudeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlanoDeSaude in Elasticsearch
        verify(mockPlanoDeSaudeSearchRepository, times(1)).deleteById(planoDeSaude.getId());
    }

    @Test
    @Transactional
    public void searchPlanoDeSaude() throws Exception {
        // Initialize the database
        planoDeSaudeRepository.saveAndFlush(planoDeSaude);
        when(mockPlanoDeSaudeSearchRepository.search(queryStringQuery("id:" + planoDeSaude.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(planoDeSaude), PageRequest.of(0, 1), 1));
        // Search the planoDeSaude
        restPlanoDeSaudeMockMvc.perform(get("/api/_search/plano-de-saudes?query=id:" + planoDeSaude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoDeSaude.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoDeSaude.class);
        PlanoDeSaude planoDeSaude1 = new PlanoDeSaude();
        planoDeSaude1.setId(1L);
        PlanoDeSaude planoDeSaude2 = new PlanoDeSaude();
        planoDeSaude2.setId(planoDeSaude1.getId());
        assertThat(planoDeSaude1).isEqualTo(planoDeSaude2);
        planoDeSaude2.setId(2L);
        assertThat(planoDeSaude1).isNotEqualTo(planoDeSaude2);
        planoDeSaude1.setId(null);
        assertThat(planoDeSaude1).isNotEqualTo(planoDeSaude2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoDeSaudeDTO.class);
        PlanoDeSaudeDTO planoDeSaudeDTO1 = new PlanoDeSaudeDTO();
        planoDeSaudeDTO1.setId(1L);
        PlanoDeSaudeDTO planoDeSaudeDTO2 = new PlanoDeSaudeDTO();
        assertThat(planoDeSaudeDTO1).isNotEqualTo(planoDeSaudeDTO2);
        planoDeSaudeDTO2.setId(planoDeSaudeDTO1.getId());
        assertThat(planoDeSaudeDTO1).isEqualTo(planoDeSaudeDTO2);
        planoDeSaudeDTO2.setId(2L);
        assertThat(planoDeSaudeDTO1).isNotEqualTo(planoDeSaudeDTO2);
        planoDeSaudeDTO1.setId(null);
        assertThat(planoDeSaudeDTO1).isNotEqualTo(planoDeSaudeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(planoDeSaudeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(planoDeSaudeMapper.fromId(null)).isNull();
    }
}
