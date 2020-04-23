package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;

import br.com.basis.madre.domain.Equipe;
import br.com.basis.madre.repository.EquipeRepository;
import br.com.basis.madre.repository.search.EquipeSearchRepository;
import br.com.basis.madre.service.EquipeService;
import br.com.basis.madre.service.dto.EquipeDTO;
import br.com.basis.madre.service.mapper.EquipeMapper;

import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
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
 * Test class for the EquipeResource REST controller.
 *
 * @see EquipeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PacientesApp.class)
public class EquipeResourceIntTest {

    private static final Long DEFAULT_NUMERO_DO_CONSELHO = 1L;
    private static final Long UPDATED_NUMERO_DO_CONSELHO = 2L;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EquipeMapper equipeMapper;

    @Autowired
    private EquipeService equipeService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.EquipeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EquipeSearchRepository mockEquipeSearchRepository;

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

    private MockMvc restEquipeMockMvc;

    private Equipe equipe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipeResource equipeResource = new EquipeResource(equipeService);
        this.restEquipeMockMvc = MockMvcBuilders.standaloneSetup(equipeResource)
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
    public static Equipe createEntity(EntityManager em) {
        Equipe equipe = new Equipe()
            .numeroDoConselho(DEFAULT_NUMERO_DO_CONSELHO)
            .nome(DEFAULT_NOME);
        return equipe;
    }

    @Before
    public void initTest() {
        equipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipe() throws Exception {
        int databaseSizeBeforeCreate = equipeRepository.findAll().size();

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);
        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isCreated());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeCreate + 1);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getNumeroDoConselho()).isEqualTo(DEFAULT_NUMERO_DO_CONSELHO);
        assertThat(testEquipe.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Equipe in Elasticsearch
        verify(mockEquipeSearchRepository, times(1)).save(testEquipe);
    }

    @Test
    @Transactional
    public void createEquipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipeRepository.findAll().size();

        // Create the Equipe with an existing ID
        equipe.setId(1L);
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Equipe in Elasticsearch
        verify(mockEquipeSearchRepository, times(0)).save(equipe);
    }

    @Test
    @Transactional
    public void checkNumeroDoConselhoIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipeRepository.findAll().size();
        // set the field null
        equipe.setNumeroDoConselho(null);

        // Create the Equipe, which fails.
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipeRepository.findAll().size();
        // set the field null
        equipe.setNome(null);

        // Create the Equipe, which fails.
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        restEquipeMockMvc.perform(post("/api/equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipes() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        // Get all the equipeList
        restEquipeMockMvc.perform(get("/api/equipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDoConselho").value(hasItem(DEFAULT_NUMERO_DO_CONSELHO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        // Get the equipe
        restEquipeMockMvc.perform(get("/api/equipes/{id}", equipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipe.getId().intValue()))
            .andExpect(jsonPath("$.numeroDoConselho").value(DEFAULT_NUMERO_DO_CONSELHO.intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEquipe() throws Exception {
        // Get the equipe
        restEquipeMockMvc.perform(get("/api/equipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // Update the equipe
        Equipe updatedEquipe = equipeRepository.findById(equipe.getId()).get();
        // Disconnect from session so that the updates on updatedEquipe are not directly saved in db
        em.detach(updatedEquipe);
        updatedEquipe
            .numeroDoConselho(UPDATED_NUMERO_DO_CONSELHO)
            .nome(UPDATED_NOME);
        EquipeDTO equipeDTO = equipeMapper.toDto(updatedEquipe);

        restEquipeMockMvc.perform(put("/api/equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isOk());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);
        Equipe testEquipe = equipeList.get(equipeList.size() - 1);
        assertThat(testEquipe.getNumeroDoConselho()).isEqualTo(UPDATED_NUMERO_DO_CONSELHO);
        assertThat(testEquipe.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Equipe in Elasticsearch
        verify(mockEquipeSearchRepository, times(1)).save(testEquipe);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipe() throws Exception {
        int databaseSizeBeforeUpdate = equipeRepository.findAll().size();

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc.perform(put("/api/equipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Equipe in Elasticsearch
        verify(mockEquipeSearchRepository, times(0)).save(equipe);
    }

    @Test
    @Transactional
    public void deleteEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);

        int databaseSizeBeforeDelete = equipeRepository.findAll().size();

        // Get the equipe
        restEquipeMockMvc.perform(delete("/api/equipes/{id}", equipe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Equipe> equipeList = equipeRepository.findAll();
        assertThat(equipeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Equipe in Elasticsearch
        verify(mockEquipeSearchRepository, times(1)).deleteById(equipe.getId());
    }

    @Test
    @Transactional
    public void searchEquipe() throws Exception {
        // Initialize the database
        equipeRepository.saveAndFlush(equipe);
        when(mockEquipeSearchRepository.search(queryStringQuery("id:" + equipe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(equipe), PageRequest.of(0, 1), 1));
        // Search the equipe
        restEquipeMockMvc.perform(get("/api/_search/equipes?query=id:" + equipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDoConselho").value(hasItem(DEFAULT_NUMERO_DO_CONSELHO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipe.class);
        Equipe equipe1 = new Equipe();
        equipe1.setId(1L);
        Equipe equipe2 = new Equipe();
        equipe2.setId(equipe1.getId());
        assertThat(equipe1).isEqualTo(equipe2);
        equipe2.setId(2L);
        assertThat(equipe1).isNotEqualTo(equipe2);
        equipe1.setId(null);
        assertThat(equipe1).isNotEqualTo(equipe2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipeDTO.class);
        EquipeDTO equipeDTO1 = new EquipeDTO();
        equipeDTO1.setId(1L);
        EquipeDTO equipeDTO2 = new EquipeDTO();
        assertThat(equipeDTO1).isNotEqualTo(equipeDTO2);
        equipeDTO2.setId(equipeDTO1.getId());
        assertThat(equipeDTO1).isEqualTo(equipeDTO2);
        equipeDTO2.setId(2L);
        assertThat(equipeDTO1).isNotEqualTo(equipeDTO2);
        equipeDTO1.setId(null);
        assertThat(equipeDTO1).isNotEqualTo(equipeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(equipeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(equipeMapper.fromId(null)).isNull();
    }
}
