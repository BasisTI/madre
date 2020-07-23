package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.PrescricaoDiagnostico;
import br.com.basis.madre.prescricao.repository.PrescricaoDiagnosticoRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoDiagnosticoSearchRepository;
import br.com.basis.madre.prescricao.service.PrescricaoDiagnosticoService;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDiagnosticoDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoDiagnosticoMapper;
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
 * Integration tests for the {@link PrescricaoDiagnosticoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class PrescricaoDiagnosticoResourceIT {

    @Autowired
    private PrescricaoDiagnosticoRepository prescricaoDiagnosticoRepository;

    @Autowired
    private PrescricaoDiagnosticoMapper prescricaoDiagnosticoMapper;

    @Autowired
    private PrescricaoDiagnosticoService prescricaoDiagnosticoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.PrescricaoDiagnosticoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescricaoDiagnosticoSearchRepository mockPrescricaoDiagnosticoSearchRepository;

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

    private MockMvc restPrescricaoDiagnosticoMockMvc;

    private PrescricaoDiagnostico prescricaoDiagnostico;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescricaoDiagnosticoResource prescricaoDiagnosticoResource = new PrescricaoDiagnosticoResource(prescricaoDiagnosticoService);
        this.restPrescricaoDiagnosticoMockMvc = MockMvcBuilders.standaloneSetup(prescricaoDiagnosticoResource)
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
    public static PrescricaoDiagnostico createEntity(EntityManager em) {
        PrescricaoDiagnostico prescricaoDiagnostico = new PrescricaoDiagnostico();
        return prescricaoDiagnostico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescricaoDiagnostico createUpdatedEntity(EntityManager em) {
        PrescricaoDiagnostico prescricaoDiagnostico = new PrescricaoDiagnostico();
        return prescricaoDiagnostico;
    }

    @BeforeEach
    public void initTest() {
        prescricaoDiagnostico = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescricaoDiagnostico() throws Exception {
        int databaseSizeBeforeCreate = prescricaoDiagnosticoRepository.findAll().size();

        // Create the PrescricaoDiagnostico
        PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO = prescricaoDiagnosticoMapper.toDto(prescricaoDiagnostico);
        restPrescricaoDiagnosticoMockMvc.perform(post("/api/prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDiagnosticoDTO)))
            .andExpect(status().isCreated());

        // Validate the PrescricaoDiagnostico in the database
        List<PrescricaoDiagnostico> prescricaoDiagnosticoList = prescricaoDiagnosticoRepository.findAll();
        assertThat(prescricaoDiagnosticoList).hasSize(databaseSizeBeforeCreate + 1);
        PrescricaoDiagnostico testPrescricaoDiagnostico = prescricaoDiagnosticoList.get(prescricaoDiagnosticoList.size() - 1);

        // Validate the PrescricaoDiagnostico in Elasticsearch
        verify(mockPrescricaoDiagnosticoSearchRepository, times(1)).save(testPrescricaoDiagnostico);
    }

    @Test
    @Transactional
    public void createPrescricaoDiagnosticoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescricaoDiagnosticoRepository.findAll().size();

        // Create the PrescricaoDiagnostico with an existing ID
        prescricaoDiagnostico.setId(1L);
        PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO = prescricaoDiagnosticoMapper.toDto(prescricaoDiagnostico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescricaoDiagnosticoMockMvc.perform(post("/api/prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDiagnosticoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoDiagnostico in the database
        List<PrescricaoDiagnostico> prescricaoDiagnosticoList = prescricaoDiagnosticoRepository.findAll();
        assertThat(prescricaoDiagnosticoList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrescricaoDiagnostico in Elasticsearch
        verify(mockPrescricaoDiagnosticoSearchRepository, times(0)).save(prescricaoDiagnostico);
    }


    @Test
    @Transactional
    public void getAllPrescricaoDiagnosticos() throws Exception {
        // Initialize the database
        prescricaoDiagnosticoRepository.saveAndFlush(prescricaoDiagnostico);

        // Get all the prescricaoDiagnosticoList
        restPrescricaoDiagnosticoMockMvc.perform(get("/api/prescricao-diagnosticos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoDiagnostico.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPrescricaoDiagnostico() throws Exception {
        // Initialize the database
        prescricaoDiagnosticoRepository.saveAndFlush(prescricaoDiagnostico);

        // Get the prescricaoDiagnostico
        restPrescricaoDiagnosticoMockMvc.perform(get("/api/prescricao-diagnosticos/{id}", prescricaoDiagnostico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescricaoDiagnostico.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrescricaoDiagnostico() throws Exception {
        // Get the prescricaoDiagnostico
        restPrescricaoDiagnosticoMockMvc.perform(get("/api/prescricao-diagnosticos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescricaoDiagnostico() throws Exception {
        // Initialize the database
        prescricaoDiagnosticoRepository.saveAndFlush(prescricaoDiagnostico);

        int databaseSizeBeforeUpdate = prescricaoDiagnosticoRepository.findAll().size();

        // Update the prescricaoDiagnostico
        PrescricaoDiagnostico updatedPrescricaoDiagnostico = prescricaoDiagnosticoRepository.findById(prescricaoDiagnostico.getId()).get();
        // Disconnect from session so that the updates on updatedPrescricaoDiagnostico are not directly saved in db
        em.detach(updatedPrescricaoDiagnostico);
        PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO = prescricaoDiagnosticoMapper.toDto(updatedPrescricaoDiagnostico);

        restPrescricaoDiagnosticoMockMvc.perform(put("/api/prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDiagnosticoDTO)))
            .andExpect(status().isOk());

        // Validate the PrescricaoDiagnostico in the database
        List<PrescricaoDiagnostico> prescricaoDiagnosticoList = prescricaoDiagnosticoRepository.findAll();
        assertThat(prescricaoDiagnosticoList).hasSize(databaseSizeBeforeUpdate);
        PrescricaoDiagnostico testPrescricaoDiagnostico = prescricaoDiagnosticoList.get(prescricaoDiagnosticoList.size() - 1);

        // Validate the PrescricaoDiagnostico in Elasticsearch
        verify(mockPrescricaoDiagnosticoSearchRepository, times(1)).save(testPrescricaoDiagnostico);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescricaoDiagnostico() throws Exception {
        int databaseSizeBeforeUpdate = prescricaoDiagnosticoRepository.findAll().size();

        // Create the PrescricaoDiagnostico
        PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO = prescricaoDiagnosticoMapper.toDto(prescricaoDiagnostico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescricaoDiagnosticoMockMvc.perform(put("/api/prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDiagnosticoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoDiagnostico in the database
        List<PrescricaoDiagnostico> prescricaoDiagnosticoList = prescricaoDiagnosticoRepository.findAll();
        assertThat(prescricaoDiagnosticoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrescricaoDiagnostico in Elasticsearch
        verify(mockPrescricaoDiagnosticoSearchRepository, times(0)).save(prescricaoDiagnostico);
    }

    @Test
    @Transactional
    public void deletePrescricaoDiagnostico() throws Exception {
        // Initialize the database
        prescricaoDiagnosticoRepository.saveAndFlush(prescricaoDiagnostico);

        int databaseSizeBeforeDelete = prescricaoDiagnosticoRepository.findAll().size();

        // Delete the prescricaoDiagnostico
        restPrescricaoDiagnosticoMockMvc.perform(delete("/api/prescricao-diagnosticos/{id}", prescricaoDiagnostico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrescricaoDiagnostico> prescricaoDiagnosticoList = prescricaoDiagnosticoRepository.findAll();
        assertThat(prescricaoDiagnosticoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrescricaoDiagnostico in Elasticsearch
        verify(mockPrescricaoDiagnosticoSearchRepository, times(1)).deleteById(prescricaoDiagnostico.getId());
    }

    @Test
    @Transactional
    public void searchPrescricaoDiagnostico() throws Exception {
        // Initialize the database
        prescricaoDiagnosticoRepository.saveAndFlush(prescricaoDiagnostico);
        when(mockPrescricaoDiagnosticoSearchRepository.search(queryStringQuery("id:" + prescricaoDiagnostico.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prescricaoDiagnostico), PageRequest.of(0, 1), 1));
        // Search the prescricaoDiagnostico
        restPrescricaoDiagnosticoMockMvc.perform(get("/api/_search/prescricao-diagnosticos?query=id:" + prescricaoDiagnostico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoDiagnostico.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoDiagnostico.class);
        PrescricaoDiagnostico prescricaoDiagnostico1 = new PrescricaoDiagnostico();
        prescricaoDiagnostico1.setId(1L);
        PrescricaoDiagnostico prescricaoDiagnostico2 = new PrescricaoDiagnostico();
        prescricaoDiagnostico2.setId(prescricaoDiagnostico1.getId());
        assertThat(prescricaoDiagnostico1).isEqualTo(prescricaoDiagnostico2);
        prescricaoDiagnostico2.setId(2L);
        assertThat(prescricaoDiagnostico1).isNotEqualTo(prescricaoDiagnostico2);
        prescricaoDiagnostico1.setId(null);
        assertThat(prescricaoDiagnostico1).isNotEqualTo(prescricaoDiagnostico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoDiagnosticoDTO.class);
        PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO1 = new PrescricaoDiagnosticoDTO();
        prescricaoDiagnosticoDTO1.setId(1L);
        PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO2 = new PrescricaoDiagnosticoDTO();
        assertThat(prescricaoDiagnosticoDTO1).isNotEqualTo(prescricaoDiagnosticoDTO2);
        prescricaoDiagnosticoDTO2.setId(prescricaoDiagnosticoDTO1.getId());
        assertThat(prescricaoDiagnosticoDTO1).isEqualTo(prescricaoDiagnosticoDTO2);
        prescricaoDiagnosticoDTO2.setId(2L);
        assertThat(prescricaoDiagnosticoDTO1).isNotEqualTo(prescricaoDiagnosticoDTO2);
        prescricaoDiagnosticoDTO1.setId(null);
        assertThat(prescricaoDiagnosticoDTO1).isNotEqualTo(prescricaoDiagnosticoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prescricaoDiagnosticoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prescricaoDiagnosticoMapper.fromId(null)).isNull();
    }
}
