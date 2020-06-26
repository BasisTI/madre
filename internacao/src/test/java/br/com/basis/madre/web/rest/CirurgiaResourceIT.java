package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Cirurgia;
import br.com.basis.madre.repository.CirurgiaRepository;
import br.com.basis.madre.repository.search.CirurgiaSearchRepository;
import br.com.basis.madre.service.CirurgiaService;
import br.com.basis.madre.service.dto.CirurgiaDTO;
import br.com.basis.madre.service.mapper.CirurgiaMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CirurgiaResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class CirurgiaResourceIT {

    private static final Instant DEFAULT_TEMPO_MAX = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEMPO_MAX = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TEMPO_MIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEMPO_MIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LIMITE_DIAS = 1;
    private static final Integer UPDATED_LIMITE_DIAS = 2;

    private static final Integer DEFAULT_LIMTE_DIAS_CONVENIOS = 1;
    private static final Integer UPDATED_LIMTE_DIAS_CONVENIOS = 2;

    private static final Integer DEFAULT_INTERVALOCIRURGIA = 1;
    private static final Integer UPDATED_INTERVALOCIRURGIA = 2;

    private static final Integer DEFAULT_INTERVALO_PROCEDIMENTO = 1;
    private static final Integer UPDATED_INTERVALO_PROCEDIMENTO = 2;

    @Autowired
    private CirurgiaRepository cirurgiaRepository;

    @Autowired
    private CirurgiaMapper cirurgiaMapper;

    @Autowired
    private CirurgiaService cirurgiaService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.CirurgiaSearchRepositoryMockConfiguration
     */
    @Autowired
    private CirurgiaSearchRepository mockCirurgiaSearchRepository;

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

    private MockMvc restCirurgiaMockMvc;

    private Cirurgia cirurgia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CirurgiaResource cirurgiaResource = new CirurgiaResource(cirurgiaService);
        this.restCirurgiaMockMvc = MockMvcBuilders.standaloneSetup(cirurgiaResource)
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
    public static Cirurgia createEntity(EntityManager em) {
        Cirurgia cirurgia = new Cirurgia()
            .tempoMax(DEFAULT_TEMPO_MAX)
            .tempoMin(DEFAULT_TEMPO_MIN)
            .limiteDias(DEFAULT_LIMITE_DIAS)
            .limteDiasConvenios(DEFAULT_LIMTE_DIAS_CONVENIOS)
            .intervalocirurgia(DEFAULT_INTERVALOCIRURGIA)
            .intervaloProcedimento(DEFAULT_INTERVALO_PROCEDIMENTO);
        return cirurgia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cirurgia createUpdatedEntity(EntityManager em) {
        Cirurgia cirurgia = new Cirurgia()
            .tempoMax(UPDATED_TEMPO_MAX)
            .tempoMin(UPDATED_TEMPO_MIN)
            .limiteDias(UPDATED_LIMITE_DIAS)
            .limteDiasConvenios(UPDATED_LIMTE_DIAS_CONVENIOS)
            .intervalocirurgia(UPDATED_INTERVALOCIRURGIA)
            .intervaloProcedimento(UPDATED_INTERVALO_PROCEDIMENTO);
        return cirurgia;
    }

    @BeforeEach
    public void initTest() {
        cirurgia = createEntity(em);
    }

    @Test
    @Transactional
    public void createCirurgia() throws Exception {
        int databaseSizeBeforeCreate = cirurgiaRepository.findAll().size();

        // Create the Cirurgia
        CirurgiaDTO cirurgiaDTO = cirurgiaMapper.toDto(cirurgia);
        restCirurgiaMockMvc.perform(post("/api/cirurgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Cirurgia in the database
        List<Cirurgia> cirurgiaList = cirurgiaRepository.findAll();
        assertThat(cirurgiaList).hasSize(databaseSizeBeforeCreate + 1);
        Cirurgia testCirurgia = cirurgiaList.get(cirurgiaList.size() - 1);
        assertThat(testCirurgia.getTempoMax()).isEqualTo(DEFAULT_TEMPO_MAX);
        assertThat(testCirurgia.getTempoMin()).isEqualTo(DEFAULT_TEMPO_MIN);
        assertThat(testCirurgia.getLimiteDias()).isEqualTo(DEFAULT_LIMITE_DIAS);
        assertThat(testCirurgia.getLimteDiasConvenios()).isEqualTo(DEFAULT_LIMTE_DIAS_CONVENIOS);
        assertThat(testCirurgia.getIntervalocirurgia()).isEqualTo(DEFAULT_INTERVALOCIRURGIA);
        assertThat(testCirurgia.getIntervaloProcedimento()).isEqualTo(DEFAULT_INTERVALO_PROCEDIMENTO);

        // Validate the Cirurgia in Elasticsearch
        verify(mockCirurgiaSearchRepository, times(1)).save(testCirurgia);
    }

    @Test
    @Transactional
    public void createCirurgiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cirurgiaRepository.findAll().size();

        // Create the Cirurgia with an existing ID
        cirurgia.setId(1L);
        CirurgiaDTO cirurgiaDTO = cirurgiaMapper.toDto(cirurgia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCirurgiaMockMvc.perform(post("/api/cirurgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cirurgia in the database
        List<Cirurgia> cirurgiaList = cirurgiaRepository.findAll();
        assertThat(cirurgiaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cirurgia in Elasticsearch
        verify(mockCirurgiaSearchRepository, times(0)).save(cirurgia);
    }


    @Test
    @Transactional
    public void getAllCirurgias() throws Exception {
        // Initialize the database
        cirurgiaRepository.saveAndFlush(cirurgia);

        // Get all the cirurgiaList
        restCirurgiaMockMvc.perform(get("/api/cirurgias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cirurgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tempoMax").value(hasItem(DEFAULT_TEMPO_MAX.toString())))
            .andExpect(jsonPath("$.[*].tempoMin").value(hasItem(DEFAULT_TEMPO_MIN.toString())))
            .andExpect(jsonPath("$.[*].limiteDias").value(hasItem(DEFAULT_LIMITE_DIAS)))
            .andExpect(jsonPath("$.[*].limteDiasConvenios").value(hasItem(DEFAULT_LIMTE_DIAS_CONVENIOS)))
            .andExpect(jsonPath("$.[*].intervalocirurgia").value(hasItem(DEFAULT_INTERVALOCIRURGIA)))
            .andExpect(jsonPath("$.[*].intervaloProcedimento").value(hasItem(DEFAULT_INTERVALO_PROCEDIMENTO)));
    }
    
    @Test
    @Transactional
    public void getCirurgia() throws Exception {
        // Initialize the database
        cirurgiaRepository.saveAndFlush(cirurgia);

        // Get the cirurgia
        restCirurgiaMockMvc.perform(get("/api/cirurgias/{id}", cirurgia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cirurgia.getId().intValue()))
            .andExpect(jsonPath("$.tempoMax").value(DEFAULT_TEMPO_MAX.toString()))
            .andExpect(jsonPath("$.tempoMin").value(DEFAULT_TEMPO_MIN.toString()))
            .andExpect(jsonPath("$.limiteDias").value(DEFAULT_LIMITE_DIAS))
            .andExpect(jsonPath("$.limteDiasConvenios").value(DEFAULT_LIMTE_DIAS_CONVENIOS))
            .andExpect(jsonPath("$.intervalocirurgia").value(DEFAULT_INTERVALOCIRURGIA))
            .andExpect(jsonPath("$.intervaloProcedimento").value(DEFAULT_INTERVALO_PROCEDIMENTO));
    }

    @Test
    @Transactional
    public void getNonExistingCirurgia() throws Exception {
        // Get the cirurgia
        restCirurgiaMockMvc.perform(get("/api/cirurgias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCirurgia() throws Exception {
        // Initialize the database
        cirurgiaRepository.saveAndFlush(cirurgia);

        int databaseSizeBeforeUpdate = cirurgiaRepository.findAll().size();

        // Update the cirurgia
        Cirurgia updatedCirurgia = cirurgiaRepository.findById(cirurgia.getId()).get();
        // Disconnect from session so that the updates on updatedCirurgia are not directly saved in db
        em.detach(updatedCirurgia);
        updatedCirurgia
            .tempoMax(UPDATED_TEMPO_MAX)
            .tempoMin(UPDATED_TEMPO_MIN)
            .limiteDias(UPDATED_LIMITE_DIAS)
            .limteDiasConvenios(UPDATED_LIMTE_DIAS_CONVENIOS)
            .intervalocirurgia(UPDATED_INTERVALOCIRURGIA)
            .intervaloProcedimento(UPDATED_INTERVALO_PROCEDIMENTO);
        CirurgiaDTO cirurgiaDTO = cirurgiaMapper.toDto(updatedCirurgia);

        restCirurgiaMockMvc.perform(put("/api/cirurgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiaDTO)))
            .andExpect(status().isOk());

        // Validate the Cirurgia in the database
        List<Cirurgia> cirurgiaList = cirurgiaRepository.findAll();
        assertThat(cirurgiaList).hasSize(databaseSizeBeforeUpdate);
        Cirurgia testCirurgia = cirurgiaList.get(cirurgiaList.size() - 1);
        assertThat(testCirurgia.getTempoMax()).isEqualTo(UPDATED_TEMPO_MAX);
        assertThat(testCirurgia.getTempoMin()).isEqualTo(UPDATED_TEMPO_MIN);
        assertThat(testCirurgia.getLimiteDias()).isEqualTo(UPDATED_LIMITE_DIAS);
        assertThat(testCirurgia.getLimteDiasConvenios()).isEqualTo(UPDATED_LIMTE_DIAS_CONVENIOS);
        assertThat(testCirurgia.getIntervalocirurgia()).isEqualTo(UPDATED_INTERVALOCIRURGIA);
        assertThat(testCirurgia.getIntervaloProcedimento()).isEqualTo(UPDATED_INTERVALO_PROCEDIMENTO);

        // Validate the Cirurgia in Elasticsearch
        verify(mockCirurgiaSearchRepository, times(1)).save(testCirurgia);
    }

    @Test
    @Transactional
    public void updateNonExistingCirurgia() throws Exception {
        int databaseSizeBeforeUpdate = cirurgiaRepository.findAll().size();

        // Create the Cirurgia
        CirurgiaDTO cirurgiaDTO = cirurgiaMapper.toDto(cirurgia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCirurgiaMockMvc.perform(put("/api/cirurgias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cirurgia in the database
        List<Cirurgia> cirurgiaList = cirurgiaRepository.findAll();
        assertThat(cirurgiaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cirurgia in Elasticsearch
        verify(mockCirurgiaSearchRepository, times(0)).save(cirurgia);
    }

    @Test
    @Transactional
    public void deleteCirurgia() throws Exception {
        // Initialize the database
        cirurgiaRepository.saveAndFlush(cirurgia);

        int databaseSizeBeforeDelete = cirurgiaRepository.findAll().size();

        // Delete the cirurgia
        restCirurgiaMockMvc.perform(delete("/api/cirurgias/{id}", cirurgia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cirurgia> cirurgiaList = cirurgiaRepository.findAll();
        assertThat(cirurgiaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cirurgia in Elasticsearch
        verify(mockCirurgiaSearchRepository, times(1)).deleteById(cirurgia.getId());
    }

    @Test
    @Transactional
    public void searchCirurgia() throws Exception {
        // Initialize the database
        cirurgiaRepository.saveAndFlush(cirurgia);
        when(mockCirurgiaSearchRepository.search(queryStringQuery("id:" + cirurgia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cirurgia), PageRequest.of(0, 1), 1));
        // Search the cirurgia
        restCirurgiaMockMvc.perform(get("/api/_search/cirurgias?query=id:" + cirurgia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cirurgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tempoMax").value(hasItem(DEFAULT_TEMPO_MAX.toString())))
            .andExpect(jsonPath("$.[*].tempoMin").value(hasItem(DEFAULT_TEMPO_MIN.toString())))
            .andExpect(jsonPath("$.[*].limiteDias").value(hasItem(DEFAULT_LIMITE_DIAS)))
            .andExpect(jsonPath("$.[*].limteDiasConvenios").value(hasItem(DEFAULT_LIMTE_DIAS_CONVENIOS)))
            .andExpect(jsonPath("$.[*].intervalocirurgia").value(hasItem(DEFAULT_INTERVALOCIRURGIA)))
            .andExpect(jsonPath("$.[*].intervaloProcedimento").value(hasItem(DEFAULT_INTERVALO_PROCEDIMENTO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cirurgia.class);
        Cirurgia cirurgia1 = new Cirurgia();
        cirurgia1.setId(1L);
        Cirurgia cirurgia2 = new Cirurgia();
        cirurgia2.setId(cirurgia1.getId());
        assertThat(cirurgia1).isEqualTo(cirurgia2);
        cirurgia2.setId(2L);
        assertThat(cirurgia1).isNotEqualTo(cirurgia2);
        cirurgia1.setId(null);
        assertThat(cirurgia1).isNotEqualTo(cirurgia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CirurgiaDTO.class);
        CirurgiaDTO cirurgiaDTO1 = new CirurgiaDTO();
        cirurgiaDTO1.setId(1L);
        CirurgiaDTO cirurgiaDTO2 = new CirurgiaDTO();
        assertThat(cirurgiaDTO1).isNotEqualTo(cirurgiaDTO2);
        cirurgiaDTO2.setId(cirurgiaDTO1.getId());
        assertThat(cirurgiaDTO1).isEqualTo(cirurgiaDTO2);
        cirurgiaDTO2.setId(2L);
        assertThat(cirurgiaDTO1).isNotEqualTo(cirurgiaDTO2);
        cirurgiaDTO1.setId(null);
        assertThat(cirurgiaDTO1).isNotEqualTo(cirurgiaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cirurgiaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cirurgiaMapper.fromId(null)).isNull();
    }
}
