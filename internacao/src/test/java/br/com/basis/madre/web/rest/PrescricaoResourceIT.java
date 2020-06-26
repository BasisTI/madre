package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Prescricao;
import br.com.basis.madre.repository.PrescricaoRepository;
import br.com.basis.madre.repository.search.PrescricaoSearchRepository;
import br.com.basis.madre.service.PrescricaoService;
import br.com.basis.madre.service.dto.PrescricaoDTO;
import br.com.basis.madre.service.mapper.PrescricaoMapper;
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

import br.com.basis.madre.domain.enumeration.UnidadeTempo;
/**
 * Integration tests for the {@link PrescricaoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class PrescricaoResourceIT {

    private static final Instant DEFAULT_HORARIO_VALIDADE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORARIO_VALIDADE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TEMPO_ADIANTAMENTO = 1;
    private static final Integer UPDATED_TEMPO_ADIANTAMENTO = 2;

    private static final UnidadeTempo DEFAULT_UNIDADE_TEMPO = UnidadeTempo.DIA;
    private static final UnidadeTempo UPDATED_UNIDADE_TEMPO = UnidadeTempo.HORAS;

    private static final Integer DEFAULT_NUMERO_VIAS = 1;
    private static final Integer UPDATED_NUMERO_VIAS = 2;

    @Autowired
    private PrescricaoRepository prescricaoRepository;

    @Autowired
    private PrescricaoMapper prescricaoMapper;

    @Autowired
    private PrescricaoService prescricaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.PrescricaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescricaoSearchRepository mockPrescricaoSearchRepository;

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

    private MockMvc restPrescricaoMockMvc;

    private Prescricao prescricao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescricaoResource prescricaoResource = new PrescricaoResource(prescricaoService);
        this.restPrescricaoMockMvc = MockMvcBuilders.standaloneSetup(prescricaoResource)
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
    public static Prescricao createEntity(EntityManager em) {
        Prescricao prescricao = new Prescricao()
            .horarioValidade(DEFAULT_HORARIO_VALIDADE)
            .tempoAdiantamento(DEFAULT_TEMPO_ADIANTAMENTO)
            .unidadeTempo(DEFAULT_UNIDADE_TEMPO)
            .numeroVias(DEFAULT_NUMERO_VIAS);
        return prescricao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prescricao createUpdatedEntity(EntityManager em) {
        Prescricao prescricao = new Prescricao()
            .horarioValidade(UPDATED_HORARIO_VALIDADE)
            .tempoAdiantamento(UPDATED_TEMPO_ADIANTAMENTO)
            .unidadeTempo(UPDATED_UNIDADE_TEMPO)
            .numeroVias(UPDATED_NUMERO_VIAS);
        return prescricao;
    }

    @BeforeEach
    public void initTest() {
        prescricao = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescricao() throws Exception {
        int databaseSizeBeforeCreate = prescricaoRepository.findAll().size();

        // Create the Prescricao
        PrescricaoDTO prescricaoDTO = prescricaoMapper.toDto(prescricao);
        restPrescricaoMockMvc.perform(post("/api/prescricaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Prescricao in the database
        List<Prescricao> prescricaoList = prescricaoRepository.findAll();
        assertThat(prescricaoList).hasSize(databaseSizeBeforeCreate + 1);
        Prescricao testPrescricao = prescricaoList.get(prescricaoList.size() - 1);
        assertThat(testPrescricao.getHorarioValidade()).isEqualTo(DEFAULT_HORARIO_VALIDADE);
        assertThat(testPrescricao.getTempoAdiantamento()).isEqualTo(DEFAULT_TEMPO_ADIANTAMENTO);
        assertThat(testPrescricao.getUnidadeTempo()).isEqualTo(DEFAULT_UNIDADE_TEMPO);
        assertThat(testPrescricao.getNumeroVias()).isEqualTo(DEFAULT_NUMERO_VIAS);

        // Validate the Prescricao in Elasticsearch
        verify(mockPrescricaoSearchRepository, times(1)).save(testPrescricao);
    }

    @Test
    @Transactional
    public void createPrescricaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescricaoRepository.findAll().size();

        // Create the Prescricao with an existing ID
        prescricao.setId(1L);
        PrescricaoDTO prescricaoDTO = prescricaoMapper.toDto(prescricao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescricaoMockMvc.perform(post("/api/prescricaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prescricao in the database
        List<Prescricao> prescricaoList = prescricaoRepository.findAll();
        assertThat(prescricaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Prescricao in Elasticsearch
        verify(mockPrescricaoSearchRepository, times(0)).save(prescricao);
    }


    @Test
    @Transactional
    public void getAllPrescricaos() throws Exception {
        // Initialize the database
        prescricaoRepository.saveAndFlush(prescricao);

        // Get all the prescricaoList
        restPrescricaoMockMvc.perform(get("/api/prescricaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricao.getId().intValue())))
            .andExpect(jsonPath("$.[*].horarioValidade").value(hasItem(DEFAULT_HORARIO_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].tempoAdiantamento").value(hasItem(DEFAULT_TEMPO_ADIANTAMENTO)))
            .andExpect(jsonPath("$.[*].unidadeTempo").value(hasItem(DEFAULT_UNIDADE_TEMPO.toString())))
            .andExpect(jsonPath("$.[*].numeroVias").value(hasItem(DEFAULT_NUMERO_VIAS)));
    }
    
    @Test
    @Transactional
    public void getPrescricao() throws Exception {
        // Initialize the database
        prescricaoRepository.saveAndFlush(prescricao);

        // Get the prescricao
        restPrescricaoMockMvc.perform(get("/api/prescricaos/{id}", prescricao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescricao.getId().intValue()))
            .andExpect(jsonPath("$.horarioValidade").value(DEFAULT_HORARIO_VALIDADE.toString()))
            .andExpect(jsonPath("$.tempoAdiantamento").value(DEFAULT_TEMPO_ADIANTAMENTO))
            .andExpect(jsonPath("$.unidadeTempo").value(DEFAULT_UNIDADE_TEMPO.toString()))
            .andExpect(jsonPath("$.numeroVias").value(DEFAULT_NUMERO_VIAS));
    }

    @Test
    @Transactional
    public void getNonExistingPrescricao() throws Exception {
        // Get the prescricao
        restPrescricaoMockMvc.perform(get("/api/prescricaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescricao() throws Exception {
        // Initialize the database
        prescricaoRepository.saveAndFlush(prescricao);

        int databaseSizeBeforeUpdate = prescricaoRepository.findAll().size();

        // Update the prescricao
        Prescricao updatedPrescricao = prescricaoRepository.findById(prescricao.getId()).get();
        // Disconnect from session so that the updates on updatedPrescricao are not directly saved in db
        em.detach(updatedPrescricao);
        updatedPrescricao
            .horarioValidade(UPDATED_HORARIO_VALIDADE)
            .tempoAdiantamento(UPDATED_TEMPO_ADIANTAMENTO)
            .unidadeTempo(UPDATED_UNIDADE_TEMPO)
            .numeroVias(UPDATED_NUMERO_VIAS);
        PrescricaoDTO prescricaoDTO = prescricaoMapper.toDto(updatedPrescricao);

        restPrescricaoMockMvc.perform(put("/api/prescricaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDTO)))
            .andExpect(status().isOk());

        // Validate the Prescricao in the database
        List<Prescricao> prescricaoList = prescricaoRepository.findAll();
        assertThat(prescricaoList).hasSize(databaseSizeBeforeUpdate);
        Prescricao testPrescricao = prescricaoList.get(prescricaoList.size() - 1);
        assertThat(testPrescricao.getHorarioValidade()).isEqualTo(UPDATED_HORARIO_VALIDADE);
        assertThat(testPrescricao.getTempoAdiantamento()).isEqualTo(UPDATED_TEMPO_ADIANTAMENTO);
        assertThat(testPrescricao.getUnidadeTempo()).isEqualTo(UPDATED_UNIDADE_TEMPO);
        assertThat(testPrescricao.getNumeroVias()).isEqualTo(UPDATED_NUMERO_VIAS);

        // Validate the Prescricao in Elasticsearch
        verify(mockPrescricaoSearchRepository, times(1)).save(testPrescricao);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescricao() throws Exception {
        int databaseSizeBeforeUpdate = prescricaoRepository.findAll().size();

        // Create the Prescricao
        PrescricaoDTO prescricaoDTO = prescricaoMapper.toDto(prescricao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescricaoMockMvc.perform(put("/api/prescricaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prescricao in the database
        List<Prescricao> prescricaoList = prescricaoRepository.findAll();
        assertThat(prescricaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Prescricao in Elasticsearch
        verify(mockPrescricaoSearchRepository, times(0)).save(prescricao);
    }

    @Test
    @Transactional
    public void deletePrescricao() throws Exception {
        // Initialize the database
        prescricaoRepository.saveAndFlush(prescricao);

        int databaseSizeBeforeDelete = prescricaoRepository.findAll().size();

        // Delete the prescricao
        restPrescricaoMockMvc.perform(delete("/api/prescricaos/{id}", prescricao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prescricao> prescricaoList = prescricaoRepository.findAll();
        assertThat(prescricaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Prescricao in Elasticsearch
        verify(mockPrescricaoSearchRepository, times(1)).deleteById(prescricao.getId());
    }

    @Test
    @Transactional
    public void searchPrescricao() throws Exception {
        // Initialize the database
        prescricaoRepository.saveAndFlush(prescricao);
        when(mockPrescricaoSearchRepository.search(queryStringQuery("id:" + prescricao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prescricao), PageRequest.of(0, 1), 1));
        // Search the prescricao
        restPrescricaoMockMvc.perform(get("/api/_search/prescricaos?query=id:" + prescricao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricao.getId().intValue())))
            .andExpect(jsonPath("$.[*].horarioValidade").value(hasItem(DEFAULT_HORARIO_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].tempoAdiantamento").value(hasItem(DEFAULT_TEMPO_ADIANTAMENTO)))
            .andExpect(jsonPath("$.[*].unidadeTempo").value(hasItem(DEFAULT_UNIDADE_TEMPO.toString())))
            .andExpect(jsonPath("$.[*].numeroVias").value(hasItem(DEFAULT_NUMERO_VIAS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prescricao.class);
        Prescricao prescricao1 = new Prescricao();
        prescricao1.setId(1L);
        Prescricao prescricao2 = new Prescricao();
        prescricao2.setId(prescricao1.getId());
        assertThat(prescricao1).isEqualTo(prescricao2);
        prescricao2.setId(2L);
        assertThat(prescricao1).isNotEqualTo(prescricao2);
        prescricao1.setId(null);
        assertThat(prescricao1).isNotEqualTo(prescricao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoDTO.class);
        PrescricaoDTO prescricaoDTO1 = new PrescricaoDTO();
        prescricaoDTO1.setId(1L);
        PrescricaoDTO prescricaoDTO2 = new PrescricaoDTO();
        assertThat(prescricaoDTO1).isNotEqualTo(prescricaoDTO2);
        prescricaoDTO2.setId(prescricaoDTO1.getId());
        assertThat(prescricaoDTO1).isEqualTo(prescricaoDTO2);
        prescricaoDTO2.setId(2L);
        assertThat(prescricaoDTO1).isNotEqualTo(prescricaoDTO2);
        prescricaoDTO1.setId(null);
        assertThat(prescricaoDTO1).isNotEqualTo(prescricaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prescricaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prescricaoMapper.fromId(null)).isNull();
    }
}
