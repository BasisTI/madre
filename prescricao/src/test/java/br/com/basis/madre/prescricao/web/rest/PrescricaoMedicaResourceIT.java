package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.PrescricaoMedica;
import br.com.basis.madre.prescricao.repository.PrescricaoMedicaRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoMedicaSearchRepository;
import br.com.basis.madre.prescricao.service.PrescricaoMedicaService;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicaDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoMedicaMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PrescricaoMedicaResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class PrescricaoMedicaResourceIT {

    private static final Long DEFAULT_ID_LEITO = 1L;
    private static final Long UPDATED_ID_LEITO = 2L;

    private static final Long DEFAULT_ID_UNIDADE_FUNCIONAL = 1L;
    private static final Long UPDATED_ID_UNIDADE_FUNCIONAL = 2L;

    private static final Long DEFAULT_ID_ATENDIMENTO = 1L;
    private static final Long UPDATED_ID_ATENDIMENTO = 2L;

    private static final LocalDate DEFAULT_DATA_PRESCRICAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PRESCRICAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PrescricaoMedicaRepository prescricaoMedicaRepository;

    @Autowired
    private PrescricaoMedicaMapper prescricaoMedicaMapper;

    @Autowired
    private PrescricaoMedicaService prescricaoMedicaService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.PrescricaoMedicaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescricaoMedicaSearchRepository mockPrescricaoMedicaSearchRepository;

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

    private MockMvc restPrescricaoMedicaMockMvc;

    private PrescricaoMedica prescricaoMedica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescricaoMedicaResource prescricaoMedicaResource = new PrescricaoMedicaResource(prescricaoMedicaService);
        this.restPrescricaoMedicaMockMvc = MockMvcBuilders.standaloneSetup(prescricaoMedicaResource)
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
    public static PrescricaoMedica createEntity(EntityManager em) {
        PrescricaoMedica prescricaoMedica = new PrescricaoMedica()
            .idLeito(DEFAULT_ID_LEITO)
            .idUnidadeFuncional(DEFAULT_ID_UNIDADE_FUNCIONAL)
            .idAtendimento(DEFAULT_ID_ATENDIMENTO)
            .dataPrescricao(DEFAULT_DATA_PRESCRICAO);
        return prescricaoMedica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescricaoMedica createUpdatedEntity(EntityManager em) {
        PrescricaoMedica prescricaoMedica = new PrescricaoMedica()
            .idLeito(UPDATED_ID_LEITO)
            .idUnidadeFuncional(UPDATED_ID_UNIDADE_FUNCIONAL)
            .idAtendimento(UPDATED_ID_ATENDIMENTO)
            .dataPrescricao(UPDATED_DATA_PRESCRICAO);
        return prescricaoMedica;
    }

    @BeforeEach
    public void initTest() {
        prescricaoMedica = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescricaoMedica() throws Exception {
        int databaseSizeBeforeCreate = prescricaoMedicaRepository.findAll().size();

        // Create the PrescricaoMedica
        PrescricaoMedicaDTO prescricaoMedicaDTO = prescricaoMedicaMapper.toDto(prescricaoMedica);
        restPrescricaoMedicaMockMvc.perform(post("/api/prescricao-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicaDTO)))
            .andExpect(status().isCreated());

        // Validate the PrescricaoMedica in the database
        List<PrescricaoMedica> prescricaoMedicaList = prescricaoMedicaRepository.findAll();
        assertThat(prescricaoMedicaList).hasSize(databaseSizeBeforeCreate + 1);
        PrescricaoMedica testPrescricaoMedica = prescricaoMedicaList.get(prescricaoMedicaList.size() - 1);
        assertThat(testPrescricaoMedica.getIdLeito()).isEqualTo(DEFAULT_ID_LEITO);
        assertThat(testPrescricaoMedica.getIdUnidadeFuncional()).isEqualTo(DEFAULT_ID_UNIDADE_FUNCIONAL);
        assertThat(testPrescricaoMedica.getIdAtendimento()).isEqualTo(DEFAULT_ID_ATENDIMENTO);
        assertThat(testPrescricaoMedica.getDataPrescricao()).isEqualTo(DEFAULT_DATA_PRESCRICAO);

        // Validate the PrescricaoMedica in Elasticsearch
        verify(mockPrescricaoMedicaSearchRepository, times(1)).save(testPrescricaoMedica);
    }

    @Test
    @Transactional
    public void createPrescricaoMedicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescricaoMedicaRepository.findAll().size();

        // Create the PrescricaoMedica with an existing ID
        prescricaoMedica.setId(1L);
        PrescricaoMedicaDTO prescricaoMedicaDTO = prescricaoMedicaMapper.toDto(prescricaoMedica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescricaoMedicaMockMvc.perform(post("/api/prescricao-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoMedica in the database
        List<PrescricaoMedica> prescricaoMedicaList = prescricaoMedicaRepository.findAll();
        assertThat(prescricaoMedicaList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrescricaoMedica in Elasticsearch
        verify(mockPrescricaoMedicaSearchRepository, times(0)).save(prescricaoMedica);
    }


    @Test
    @Transactional
    public void getAllPrescricaoMedicas() throws Exception {
        // Initialize the database
        prescricaoMedicaRepository.saveAndFlush(prescricaoMedica);

        // Get all the prescricaoMedicaList
        restPrescricaoMedicaMockMvc.perform(get("/api/prescricao-medicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoMedica.getId().intValue())))
            .andExpect(jsonPath("$.[*].idLeito").value(hasItem(DEFAULT_ID_LEITO.intValue())))
            .andExpect(jsonPath("$.[*].idUnidadeFuncional").value(hasItem(DEFAULT_ID_UNIDADE_FUNCIONAL.intValue())))
            .andExpect(jsonPath("$.[*].idAtendimento").value(hasItem(DEFAULT_ID_ATENDIMENTO.intValue())))
            .andExpect(jsonPath("$.[*].dataPrescricao").value(hasItem(DEFAULT_DATA_PRESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getPrescricaoMedica() throws Exception {
        // Initialize the database
        prescricaoMedicaRepository.saveAndFlush(prescricaoMedica);

        // Get the prescricaoMedica
        restPrescricaoMedicaMockMvc.perform(get("/api/prescricao-medicas/{id}", prescricaoMedica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescricaoMedica.getId().intValue()))
            .andExpect(jsonPath("$.idLeito").value(DEFAULT_ID_LEITO.intValue()))
            .andExpect(jsonPath("$.idUnidadeFuncional").value(DEFAULT_ID_UNIDADE_FUNCIONAL.intValue()))
            .andExpect(jsonPath("$.idAtendimento").value(DEFAULT_ID_ATENDIMENTO.intValue()))
            .andExpect(jsonPath("$.dataPrescricao").value(DEFAULT_DATA_PRESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrescricaoMedica() throws Exception {
        // Get the prescricaoMedica
        restPrescricaoMedicaMockMvc.perform(get("/api/prescricao-medicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescricaoMedica() throws Exception {
        // Initialize the database
        prescricaoMedicaRepository.saveAndFlush(prescricaoMedica);

        int databaseSizeBeforeUpdate = prescricaoMedicaRepository.findAll().size();

        // Update the prescricaoMedica
        PrescricaoMedica updatedPrescricaoMedica = prescricaoMedicaRepository.findById(prescricaoMedica.getId()).get();
        // Disconnect from session so that the updates on updatedPrescricaoMedica are not directly saved in db
        em.detach(updatedPrescricaoMedica);
        updatedPrescricaoMedica
            .idLeito(UPDATED_ID_LEITO)
            .idUnidadeFuncional(UPDATED_ID_UNIDADE_FUNCIONAL)
            .idAtendimento(UPDATED_ID_ATENDIMENTO)
            .dataPrescricao(UPDATED_DATA_PRESCRICAO);
        PrescricaoMedicaDTO prescricaoMedicaDTO = prescricaoMedicaMapper.toDto(updatedPrescricaoMedica);

        restPrescricaoMedicaMockMvc.perform(put("/api/prescricao-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicaDTO)))
            .andExpect(status().isOk());

        // Validate the PrescricaoMedica in the database
        List<PrescricaoMedica> prescricaoMedicaList = prescricaoMedicaRepository.findAll();
        assertThat(prescricaoMedicaList).hasSize(databaseSizeBeforeUpdate);
        PrescricaoMedica testPrescricaoMedica = prescricaoMedicaList.get(prescricaoMedicaList.size() - 1);
        assertThat(testPrescricaoMedica.getIdLeito()).isEqualTo(UPDATED_ID_LEITO);
        assertThat(testPrescricaoMedica.getIdUnidadeFuncional()).isEqualTo(UPDATED_ID_UNIDADE_FUNCIONAL);
        assertThat(testPrescricaoMedica.getIdAtendimento()).isEqualTo(UPDATED_ID_ATENDIMENTO);
        assertThat(testPrescricaoMedica.getDataPrescricao()).isEqualTo(UPDATED_DATA_PRESCRICAO);

        // Validate the PrescricaoMedica in Elasticsearch
        verify(mockPrescricaoMedicaSearchRepository, times(1)).save(testPrescricaoMedica);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescricaoMedica() throws Exception {
        int databaseSizeBeforeUpdate = prescricaoMedicaRepository.findAll().size();

        // Create the PrescricaoMedica
        PrescricaoMedicaDTO prescricaoMedicaDTO = prescricaoMedicaMapper.toDto(prescricaoMedica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescricaoMedicaMockMvc.perform(put("/api/prescricao-medicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoMedica in the database
        List<PrescricaoMedica> prescricaoMedicaList = prescricaoMedicaRepository.findAll();
        assertThat(prescricaoMedicaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrescricaoMedica in Elasticsearch
        verify(mockPrescricaoMedicaSearchRepository, times(0)).save(prescricaoMedica);
    }

    @Test
    @Transactional
    public void deletePrescricaoMedica() throws Exception {
        // Initialize the database
        prescricaoMedicaRepository.saveAndFlush(prescricaoMedica);

        int databaseSizeBeforeDelete = prescricaoMedicaRepository.findAll().size();

        // Delete the prescricaoMedica
        restPrescricaoMedicaMockMvc.perform(delete("/api/prescricao-medicas/{id}", prescricaoMedica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrescricaoMedica> prescricaoMedicaList = prescricaoMedicaRepository.findAll();
        assertThat(prescricaoMedicaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrescricaoMedica in Elasticsearch
        verify(mockPrescricaoMedicaSearchRepository, times(1)).deleteById(prescricaoMedica.getId());
    }

    @Test
    @Transactional
    public void searchPrescricaoMedica() throws Exception {
        // Initialize the database
        prescricaoMedicaRepository.saveAndFlush(prescricaoMedica);
        when(mockPrescricaoMedicaSearchRepository.search(queryStringQuery("id:" + prescricaoMedica.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prescricaoMedica), PageRequest.of(0, 1), 1));
        // Search the prescricaoMedica
        restPrescricaoMedicaMockMvc.perform(get("/api/_search/prescricao-medicas?query=id:" + prescricaoMedica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoMedica.getId().intValue())))
            .andExpect(jsonPath("$.[*].idLeito").value(hasItem(DEFAULT_ID_LEITO.intValue())))
            .andExpect(jsonPath("$.[*].idUnidadeFuncional").value(hasItem(DEFAULT_ID_UNIDADE_FUNCIONAL.intValue())))
            .andExpect(jsonPath("$.[*].idAtendimento").value(hasItem(DEFAULT_ID_ATENDIMENTO.intValue())))
            .andExpect(jsonPath("$.[*].dataPrescricao").value(hasItem(DEFAULT_DATA_PRESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoMedica.class);
        PrescricaoMedica prescricaoMedica1 = new PrescricaoMedica();
        prescricaoMedica1.setId(1L);
        PrescricaoMedica prescricaoMedica2 = new PrescricaoMedica();
        prescricaoMedica2.setId(prescricaoMedica1.getId());
        assertThat(prescricaoMedica1).isEqualTo(prescricaoMedica2);
        prescricaoMedica2.setId(2L);
        assertThat(prescricaoMedica1).isNotEqualTo(prescricaoMedica2);
        prescricaoMedica1.setId(null);
        assertThat(prescricaoMedica1).isNotEqualTo(prescricaoMedica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoMedicaDTO.class);
        PrescricaoMedicaDTO prescricaoMedicaDTO1 = new PrescricaoMedicaDTO();
        prescricaoMedicaDTO1.setId(1L);
        PrescricaoMedicaDTO prescricaoMedicaDTO2 = new PrescricaoMedicaDTO();
        assertThat(prescricaoMedicaDTO1).isNotEqualTo(prescricaoMedicaDTO2);
        prescricaoMedicaDTO2.setId(prescricaoMedicaDTO1.getId());
        assertThat(prescricaoMedicaDTO1).isEqualTo(prescricaoMedicaDTO2);
        prescricaoMedicaDTO2.setId(2L);
        assertThat(prescricaoMedicaDTO1).isNotEqualTo(prescricaoMedicaDTO2);
        prescricaoMedicaDTO1.setId(null);
        assertThat(prescricaoMedicaDTO1).isNotEqualTo(prescricaoMedicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prescricaoMedicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prescricaoMedicaMapper.fromId(null)).isNull();
    }
}
