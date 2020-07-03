package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.Diluente;
import br.com.basis.madre.prescricao.repository.DiluenteRepository;
import br.com.basis.madre.prescricao.repository.search.DiluenteSearchRepository;
import br.com.basis.madre.prescricao.service.DiluenteService;
import br.com.basis.madre.prescricao.service.dto.DiluenteDTO;
import br.com.basis.madre.prescricao.service.mapper.DiluenteMapper;
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

import static br.com.basis.madre.prescricao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DiluenteResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class DiluenteResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private DiluenteRepository diluenteRepository;

    @Autowired
    private DiluenteMapper diluenteMapper;

    @Autowired
    private DiluenteService diluenteService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.DiluenteSearchRepositoryMockConfiguration
     */
    @Autowired
    private DiluenteSearchRepository mockDiluenteSearchRepository;

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

    private MockMvc restDiluenteMockMvc;

    private Diluente diluente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiluenteResource diluenteResource = new DiluenteResource(diluenteService);
        this.restDiluenteMockMvc = MockMvcBuilders.standaloneSetup(diluenteResource)
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
    public static Diluente createEntity(EntityManager em) {
        Diluente diluente = new Diluente()
            .descricao(DEFAULT_DESCRICAO);
        return diluente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diluente createUpdatedEntity(EntityManager em) {
        Diluente diluente = new Diluente()
            .descricao(UPDATED_DESCRICAO);
        return diluente;
    }

    @BeforeEach
    public void initTest() {
        diluente = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiluente() throws Exception {
        int databaseSizeBeforeCreate = diluenteRepository.findAll().size();

        // Create the Diluente
        DiluenteDTO diluenteDTO = diluenteMapper.toDto(diluente);
        restDiluenteMockMvc.perform(post("/api/diluentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diluenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Diluente in the database
        List<Diluente> diluenteList = diluenteRepository.findAll();
        assertThat(diluenteList).hasSize(databaseSizeBeforeCreate + 1);
        Diluente testDiluente = diluenteList.get(diluenteList.size() - 1);
        assertThat(testDiluente.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Diluente in Elasticsearch
        verify(mockDiluenteSearchRepository, times(1)).save(testDiluente);
    }

    @Test
    @Transactional
    public void createDiluenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diluenteRepository.findAll().size();

        // Create the Diluente with an existing ID
        diluente.setId(1L);
        DiluenteDTO diluenteDTO = diluenteMapper.toDto(diluente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiluenteMockMvc.perform(post("/api/diluentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diluenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diluente in the database
        List<Diluente> diluenteList = diluenteRepository.findAll();
        assertThat(diluenteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Diluente in Elasticsearch
        verify(mockDiluenteSearchRepository, times(0)).save(diluente);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = diluenteRepository.findAll().size();
        // set the field null
        diluente.setDescricao(null);

        // Create the Diluente, which fails.
        DiluenteDTO diluenteDTO = diluenteMapper.toDto(diluente);

        restDiluenteMockMvc.perform(post("/api/diluentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diluenteDTO)))
            .andExpect(status().isBadRequest());

        List<Diluente> diluenteList = diluenteRepository.findAll();
        assertThat(diluenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiluentes() throws Exception {
        // Initialize the database
        diluenteRepository.saveAndFlush(diluente);

        // Get all the diluenteList
        restDiluenteMockMvc.perform(get("/api/diluentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diluente.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getDiluente() throws Exception {
        // Initialize the database
        diluenteRepository.saveAndFlush(diluente);

        // Get the diluente
        restDiluenteMockMvc.perform(get("/api/diluentes/{id}", diluente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diluente.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingDiluente() throws Exception {
        // Get the diluente
        restDiluenteMockMvc.perform(get("/api/diluentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiluente() throws Exception {
        // Initialize the database
        diluenteRepository.saveAndFlush(diluente);

        int databaseSizeBeforeUpdate = diluenteRepository.findAll().size();

        // Update the diluente
        Diluente updatedDiluente = diluenteRepository.findById(diluente.getId()).get();
        // Disconnect from session so that the updates on updatedDiluente are not directly saved in db
        em.detach(updatedDiluente);
        updatedDiluente
            .descricao(UPDATED_DESCRICAO);
        DiluenteDTO diluenteDTO = diluenteMapper.toDto(updatedDiluente);

        restDiluenteMockMvc.perform(put("/api/diluentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diluenteDTO)))
            .andExpect(status().isOk());

        // Validate the Diluente in the database
        List<Diluente> diluenteList = diluenteRepository.findAll();
        assertThat(diluenteList).hasSize(databaseSizeBeforeUpdate);
        Diluente testDiluente = diluenteList.get(diluenteList.size() - 1);
        assertThat(testDiluente.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Diluente in Elasticsearch
        verify(mockDiluenteSearchRepository, times(1)).save(testDiluente);
    }

    @Test
    @Transactional
    public void updateNonExistingDiluente() throws Exception {
        int databaseSizeBeforeUpdate = diluenteRepository.findAll().size();

        // Create the Diluente
        DiluenteDTO diluenteDTO = diluenteMapper.toDto(diluente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiluenteMockMvc.perform(put("/api/diluentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diluenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diluente in the database
        List<Diluente> diluenteList = diluenteRepository.findAll();
        assertThat(diluenteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Diluente in Elasticsearch
        verify(mockDiluenteSearchRepository, times(0)).save(diluente);
    }

    @Test
    @Transactional
    public void deleteDiluente() throws Exception {
        // Initialize the database
        diluenteRepository.saveAndFlush(diluente);

        int databaseSizeBeforeDelete = diluenteRepository.findAll().size();

        // Delete the diluente
        restDiluenteMockMvc.perform(delete("/api/diluentes/{id}", diluente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Diluente> diluenteList = diluenteRepository.findAll();
        assertThat(diluenteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Diluente in Elasticsearch
        verify(mockDiluenteSearchRepository, times(1)).deleteById(diluente.getId());
    }

    @Test
    @Transactional
    public void searchDiluente() throws Exception {
        // Initialize the database
        diluenteRepository.saveAndFlush(diluente);
        when(mockDiluenteSearchRepository.search(queryStringQuery("id:" + diluente.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(diluente), PageRequest.of(0, 1), 1));
        // Search the diluente
        restDiluenteMockMvc.perform(get("/api/_search/diluentes?query=id:" + diluente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diluente.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diluente.class);
        Diluente diluente1 = new Diluente();
        diluente1.setId(1L);
        Diluente diluente2 = new Diluente();
        diluente2.setId(diluente1.getId());
        assertThat(diluente1).isEqualTo(diluente2);
        diluente2.setId(2L);
        assertThat(diluente1).isNotEqualTo(diluente2);
        diluente1.setId(null);
        assertThat(diluente1).isNotEqualTo(diluente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiluenteDTO.class);
        DiluenteDTO diluenteDTO1 = new DiluenteDTO();
        diluenteDTO1.setId(1L);
        DiluenteDTO diluenteDTO2 = new DiluenteDTO();
        assertThat(diluenteDTO1).isNotEqualTo(diluenteDTO2);
        diluenteDTO2.setId(diluenteDTO1.getId());
        assertThat(diluenteDTO1).isEqualTo(diluenteDTO2);
        diluenteDTO2.setId(2L);
        assertThat(diluenteDTO1).isNotEqualTo(diluenteDTO2);
        diluenteDTO1.setId(null);
        assertThat(diluenteDTO1).isNotEqualTo(diluenteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(diluenteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(diluenteMapper.fromId(null)).isNull();
    }
}
