package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.PrescricaoDieta;
import br.com.basis.madre.prescricao.repository.PrescricaoDietaRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoDietaSearchRepository;
import br.com.basis.madre.prescricao.service.PrescricaoDietaService;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDietaDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoDietaMapper;
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
 * Integration tests for the {@link PrescricaoDietaResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class PrescricaoDietaResourceIT {

    private static final Long DEFAULT_ID_PACIENTE = 1L;
    private static final Long UPDATED_ID_PACIENTE = 2L;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private PrescricaoDietaRepository prescricaoDietaRepository;

    @Autowired
    private PrescricaoDietaMapper prescricaoDietaMapper;

    @Autowired
    private PrescricaoDietaService prescricaoDietaService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.PrescricaoDietaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescricaoDietaSearchRepository mockPrescricaoDietaSearchRepository;

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

    private MockMvc restPrescricaoDietaMockMvc;

    private PrescricaoDieta prescricaoDieta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescricaoDietaResource prescricaoDietaResource = new PrescricaoDietaResource(prescricaoDietaService);
        this.restPrescricaoDietaMockMvc = MockMvcBuilders.standaloneSetup(prescricaoDietaResource)
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
    public static PrescricaoDieta createEntity(EntityManager em) {
        PrescricaoDieta prescricaoDieta = (PrescricaoDieta) new PrescricaoDieta()
            .idPaciente(DEFAULT_ID_PACIENTE)
            .observacao(DEFAULT_OBSERVACAO);
        return prescricaoDieta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescricaoDieta createUpdatedEntity(EntityManager em) {
        PrescricaoDieta prescricaoDieta = (PrescricaoDieta) new PrescricaoDieta()
            .idPaciente(UPDATED_ID_PACIENTE)
            .observacao(UPDATED_OBSERVACAO);
        return prescricaoDieta;
    }

    @BeforeEach
    public void initTest() {
        prescricaoDieta = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescricaoDieta() throws Exception {
        int databaseSizeBeforeCreate = prescricaoDietaRepository.findAll().size();

        // Create the PrescricaoDieta
        PrescricaoDietaDTO prescricaoDietaDTO = prescricaoDietaMapper.toDto(prescricaoDieta);
        restPrescricaoDietaMockMvc.perform(post("/api/prescricao-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDietaDTO)))
            .andExpect(status().isCreated());

        // Validate the PrescricaoDieta in the database
        List<PrescricaoDieta> prescricaoDietaList = prescricaoDietaRepository.findAll();
        assertThat(prescricaoDietaList).hasSize(databaseSizeBeforeCreate + 1);
        PrescricaoDieta testPrescricaoDieta = prescricaoDietaList.get(prescricaoDietaList.size() - 1);
        assertThat(testPrescricaoDieta.getIdPaciente()).isEqualTo(DEFAULT_ID_PACIENTE);
        assertThat(testPrescricaoDieta.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the PrescricaoDieta in Elasticsearch
        verify(mockPrescricaoDietaSearchRepository, times(1)).save(testPrescricaoDieta);
    }

    @Test
    @Transactional
    public void createPrescricaoDietaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescricaoDietaRepository.findAll().size();

        // Create the PrescricaoDieta with an existing ID
        prescricaoDieta.setId(1L);
        PrescricaoDietaDTO prescricaoDietaDTO = prescricaoDietaMapper.toDto(prescricaoDieta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescricaoDietaMockMvc.perform(post("/api/prescricao-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDietaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoDieta in the database
        List<PrescricaoDieta> prescricaoDietaList = prescricaoDietaRepository.findAll();
        assertThat(prescricaoDietaList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrescricaoDieta in Elasticsearch
        verify(mockPrescricaoDietaSearchRepository, times(0)).save(prescricaoDieta);
    }


    @Test
    @Transactional
    public void getAllPrescricaoDietas() throws Exception {
        // Initialize the database
        prescricaoDietaRepository.saveAndFlush(prescricaoDieta);

        // Get all the prescricaoDietaList
        restPrescricaoDietaMockMvc.perform(get("/api/prescricao-dietas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoDieta.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }
    
    @Test
    @Transactional
    public void getPrescricaoDieta() throws Exception {
        // Initialize the database
        prescricaoDietaRepository.saveAndFlush(prescricaoDieta);

        // Get the prescricaoDieta
        restPrescricaoDietaMockMvc.perform(get("/api/prescricao-dietas/{id}", prescricaoDieta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescricaoDieta.getId().intValue()))
            .andExpect(jsonPath("$.idPaciente").value(DEFAULT_ID_PACIENTE.intValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    public void getNonExistingPrescricaoDieta() throws Exception {
        // Get the prescricaoDieta
        restPrescricaoDietaMockMvc.perform(get("/api/prescricao-dietas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescricaoDieta() throws Exception {
        // Initialize the database
        prescricaoDietaRepository.saveAndFlush(prescricaoDieta);

        int databaseSizeBeforeUpdate = prescricaoDietaRepository.findAll().size();

        // Update the prescricaoDieta
        PrescricaoDieta updatedPrescricaoDieta = prescricaoDietaRepository.findById(prescricaoDieta.getId()).get();
        // Disconnect from session so that the updates on updatedPrescricaoDieta are not directly saved in db
        em.detach(updatedPrescricaoDieta);
        updatedPrescricaoDieta
            .idPaciente(UPDATED_ID_PACIENTE)
            .observacao(UPDATED_OBSERVACAO);
        PrescricaoDietaDTO prescricaoDietaDTO = prescricaoDietaMapper.toDto(updatedPrescricaoDieta);

        restPrescricaoDietaMockMvc.perform(put("/api/prescricao-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDietaDTO)))
            .andExpect(status().isOk());

        // Validate the PrescricaoDieta in the database
        List<PrescricaoDieta> prescricaoDietaList = prescricaoDietaRepository.findAll();
        assertThat(prescricaoDietaList).hasSize(databaseSizeBeforeUpdate);
        PrescricaoDieta testPrescricaoDieta = prescricaoDietaList.get(prescricaoDietaList.size() - 1);
        assertThat(testPrescricaoDieta.getIdPaciente()).isEqualTo(UPDATED_ID_PACIENTE);
        assertThat(testPrescricaoDieta.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the PrescricaoDieta in Elasticsearch
        verify(mockPrescricaoDietaSearchRepository, times(1)).save(testPrescricaoDieta);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescricaoDieta() throws Exception {
        int databaseSizeBeforeUpdate = prescricaoDietaRepository.findAll().size();

        // Create the PrescricaoDieta
        PrescricaoDietaDTO prescricaoDietaDTO = prescricaoDietaMapper.toDto(prescricaoDieta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescricaoDietaMockMvc.perform(put("/api/prescricao-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDietaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoDieta in the database
        List<PrescricaoDieta> prescricaoDietaList = prescricaoDietaRepository.findAll();
        assertThat(prescricaoDietaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrescricaoDieta in Elasticsearch
        verify(mockPrescricaoDietaSearchRepository, times(0)).save(prescricaoDieta);
    }

    @Test
    @Transactional
    public void deletePrescricaoDieta() throws Exception {
        // Initialize the database
        prescricaoDietaRepository.saveAndFlush(prescricaoDieta);

        int databaseSizeBeforeDelete = prescricaoDietaRepository.findAll().size();

        // Delete the prescricaoDieta
        restPrescricaoDietaMockMvc.perform(delete("/api/prescricao-dietas/{id}", prescricaoDieta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrescricaoDieta> prescricaoDietaList = prescricaoDietaRepository.findAll();
        assertThat(prescricaoDietaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrescricaoDieta in Elasticsearch
        verify(mockPrescricaoDietaSearchRepository, times(1)).deleteById(prescricaoDieta.getId());
    }

    @Test
    @Transactional
    public void searchPrescricaoDieta() throws Exception {
        // Initialize the database
        prescricaoDietaRepository.saveAndFlush(prescricaoDieta);
        when(mockPrescricaoDietaSearchRepository.search(queryStringQuery("id:" + prescricaoDieta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prescricaoDieta), PageRequest.of(0, 1), 1));
        // Search the prescricaoDieta
        restPrescricaoDietaMockMvc.perform(get("/api/_search/prescricao-dietas?query=id:" + prescricaoDieta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoDieta.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoDieta.class);
        PrescricaoDieta prescricaoDieta1 = new PrescricaoDieta();
        prescricaoDieta1.setId(1L);
        PrescricaoDieta prescricaoDieta2 = new PrescricaoDieta();
        prescricaoDieta2.setId(prescricaoDieta1.getId());
        assertThat(prescricaoDieta1).isEqualTo(prescricaoDieta2);
        prescricaoDieta2.setId(2L);
        assertThat(prescricaoDieta1).isNotEqualTo(prescricaoDieta2);
        prescricaoDieta1.setId(null);
        assertThat(prescricaoDieta1).isNotEqualTo(prescricaoDieta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoDietaDTO.class);
        PrescricaoDietaDTO prescricaoDietaDTO1 = new PrescricaoDietaDTO();
        prescricaoDietaDTO1.setId(1L);
        PrescricaoDietaDTO prescricaoDietaDTO2 = new PrescricaoDietaDTO();
        assertThat(prescricaoDietaDTO1).isNotEqualTo(prescricaoDietaDTO2);
        prescricaoDietaDTO2.setId(prescricaoDietaDTO1.getId());
        assertThat(prescricaoDietaDTO1).isEqualTo(prescricaoDietaDTO2);
        prescricaoDietaDTO2.setId(2L);
        assertThat(prescricaoDietaDTO1).isNotEqualTo(prescricaoDietaDTO2);
        prescricaoDietaDTO1.setId(null);
        assertThat(prescricaoDietaDTO1).isNotEqualTo(prescricaoDietaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prescricaoDietaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prescricaoDietaMapper.fromId(null)).isNull();
    }
}
