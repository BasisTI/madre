package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Caracteristica;
import br.com.basis.madre.repository.CaracteristicaRepository;
import br.com.basis.madre.repository.search.CaracteristicaSearchRepository;
import br.com.basis.madre.service.CaracteristicaService;
import br.com.basis.madre.service.dto.CaracteristicaDTO;
import br.com.basis.madre.service.mapper.CaracteristicaMapper;
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
 * Integration tests for the {@link CaracteristicaResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class CaracteristicaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    @Autowired
    private CaracteristicaMapper caracteristicaMapper;

    @Autowired
    private CaracteristicaService caracteristicaService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.CaracteristicaSearchRepositoryMockConfiguration
     */
    @Autowired
    private CaracteristicaSearchRepository mockCaracteristicaSearchRepository;

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

    private MockMvc restCaracteristicaMockMvc;

    private Caracteristica caracteristica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaracteristicaResource caracteristicaResource = new CaracteristicaResource(caracteristicaService);
        this.restCaracteristicaMockMvc = MockMvcBuilders.standaloneSetup(caracteristicaResource)
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
    public static Caracteristica createEntity(EntityManager em) {
        Caracteristica caracteristica = new Caracteristica()
            .nome(DEFAULT_NOME);
        return caracteristica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caracteristica createUpdatedEntity(EntityManager em) {
        Caracteristica caracteristica = new Caracteristica()
            .nome(UPDATED_NOME);
        return caracteristica;
    }

    @BeforeEach
    public void initTest() {
        caracteristica = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaracteristica() throws Exception {
        int databaseSizeBeforeCreate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);
        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeCreate + 1);
        Caracteristica testCaracteristica = caracteristicaList.get(caracteristicaList.size() - 1);
        assertThat(testCaracteristica.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Caracteristica in Elasticsearch
        verify(mockCaracteristicaSearchRepository, times(1)).save(testCaracteristica);
    }

    @Test
    @Transactional
    public void createCaracteristicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica with an existing ID
        caracteristica.setId(1L);
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Caracteristica in Elasticsearch
        verify(mockCaracteristicaSearchRepository, times(0)).save(caracteristica);
    }


    @Test
    @Transactional
    public void getAllCaracteristicas() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get the caracteristica
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas/{id}", caracteristica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caracteristica.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingCaracteristica() throws Exception {
        // Get the caracteristica
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        int databaseSizeBeforeUpdate = caracteristicaRepository.findAll().size();

        // Update the caracteristica
        Caracteristica updatedCaracteristica = caracteristicaRepository.findById(caracteristica.getId()).get();
        // Disconnect from session so that the updates on updatedCaracteristica are not directly saved in db
        em.detach(updatedCaracteristica);
        updatedCaracteristica
            .nome(UPDATED_NOME);
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(updatedCaracteristica);

        restCaracteristicaMockMvc.perform(put("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isOk());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeUpdate);
        Caracteristica testCaracteristica = caracteristicaList.get(caracteristicaList.size() - 1);
        assertThat(testCaracteristica.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Caracteristica in Elasticsearch
        verify(mockCaracteristicaSearchRepository, times(1)).save(testCaracteristica);
    }

    @Test
    @Transactional
    public void updateNonExistingCaracteristica() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristicaMockMvc.perform(put("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Caracteristica in Elasticsearch
        verify(mockCaracteristicaSearchRepository, times(0)).save(caracteristica);
    }

    @Test
    @Transactional
    public void deleteCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        int databaseSizeBeforeDelete = caracteristicaRepository.findAll().size();

        // Delete the caracteristica
        restCaracteristicaMockMvc.perform(delete("/api/caracteristicas/{id}", caracteristica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Caracteristica in Elasticsearch
        verify(mockCaracteristicaSearchRepository, times(1)).deleteById(caracteristica.getId());
    }

    @Test
    @Transactional
    public void searchCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);
        when(mockCaracteristicaSearchRepository.search(queryStringQuery("id:" + caracteristica.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(caracteristica), PageRequest.of(0, 1), 1));
        // Search the caracteristica
        restCaracteristicaMockMvc.perform(get("/api/_search/caracteristicas?query=id:" + caracteristica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caracteristica.class);
        Caracteristica caracteristica1 = new Caracteristica();
        caracteristica1.setId(1L);
        Caracteristica caracteristica2 = new Caracteristica();
        caracteristica2.setId(caracteristica1.getId());
        assertThat(caracteristica1).isEqualTo(caracteristica2);
        caracteristica2.setId(2L);
        assertThat(caracteristica1).isNotEqualTo(caracteristica2);
        caracteristica1.setId(null);
        assertThat(caracteristica1).isNotEqualTo(caracteristica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaracteristicaDTO.class);
        CaracteristicaDTO caracteristicaDTO1 = new CaracteristicaDTO();
        caracteristicaDTO1.setId(1L);
        CaracteristicaDTO caracteristicaDTO2 = new CaracteristicaDTO();
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
        caracteristicaDTO2.setId(caracteristicaDTO1.getId());
        assertThat(caracteristicaDTO1).isEqualTo(caracteristicaDTO2);
        caracteristicaDTO2.setId(2L);
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
        caracteristicaDTO1.setId(null);
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(caracteristicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(caracteristicaMapper.fromId(null)).isNull();
    }
}
