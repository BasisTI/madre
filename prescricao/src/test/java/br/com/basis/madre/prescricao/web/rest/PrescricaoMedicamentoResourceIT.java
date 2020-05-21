package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.PrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.service.PrescricaoMedicamentoService;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicamentoDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoMedicamentoMapper;
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
 * Integration tests for the {@link PrescricaoMedicamentoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class PrescricaoMedicamentoResourceIT {

    private static final Long DEFAULT_ID_PACIENTE = 1L;
    private static final Long UPDATED_ID_PACIENTE = 2L;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private PrescricaoMedicamentoRepository prescricaoMedicamentoRepository;

    @Autowired
    private PrescricaoMedicamentoMapper prescricaoMedicamentoMapper;

    @Autowired
    private PrescricaoMedicamentoService prescricaoMedicamentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.PrescricaoMedicamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescricaoMedicamentoSearchRepository mockPrescricaoMedicamentoSearchRepository;

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

    private MockMvc restPrescricaoMedicamentoMockMvc;

    private PrescricaoMedicamento prescricaoMedicamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescricaoMedicamentoResource prescricaoMedicamentoResource = new PrescricaoMedicamentoResource(prescricaoMedicamentoService);
        this.restPrescricaoMedicamentoMockMvc = MockMvcBuilders.standaloneSetup(prescricaoMedicamentoResource)
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
    public static PrescricaoMedicamento createEntity(EntityManager em) {
        PrescricaoMedicamento prescricaoMedicamento = new PrescricaoMedicamento()
            .idPaciente(DEFAULT_ID_PACIENTE)
            .observacao(DEFAULT_OBSERVACAO);
        return prescricaoMedicamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescricaoMedicamento createUpdatedEntity(EntityManager em) {
        PrescricaoMedicamento prescricaoMedicamento = new PrescricaoMedicamento()
            .idPaciente(UPDATED_ID_PACIENTE)
            .observacao(UPDATED_OBSERVACAO);
        return prescricaoMedicamento;
    }

    @BeforeEach
    public void initTest() {
        prescricaoMedicamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescricaoMedicamento() throws Exception {
        int databaseSizeBeforeCreate = prescricaoMedicamentoRepository.findAll().size();

        // Create the PrescricaoMedicamento
        PrescricaoMedicamentoDTO prescricaoMedicamentoDTO = prescricaoMedicamentoMapper.toDto(prescricaoMedicamento);
        restPrescricaoMedicamentoMockMvc.perform(post("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the PrescricaoMedicamento in the database
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeCreate + 1);
        PrescricaoMedicamento testPrescricaoMedicamento = prescricaoMedicamentoList.get(prescricaoMedicamentoList.size() - 1);
        assertThat(testPrescricaoMedicamento.getIdPaciente()).isEqualTo(DEFAULT_ID_PACIENTE);
        assertThat(testPrescricaoMedicamento.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(1)).save(testPrescricaoMedicamento);
    }

    @Test
    @Transactional
    public void createPrescricaoMedicamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescricaoMedicamentoRepository.findAll().size();

        // Create the PrescricaoMedicamento with an existing ID
        prescricaoMedicamento.setId(1L);
        PrescricaoMedicamentoDTO prescricaoMedicamentoDTO = prescricaoMedicamentoMapper.toDto(prescricaoMedicamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescricaoMedicamentoMockMvc.perform(post("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoMedicamento in the database
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(0)).save(prescricaoMedicamento);
    }


    @Test
    @Transactional
    public void getAllPrescricaoMedicamentos() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);

        // Get all the prescricaoMedicamentoList
        restPrescricaoMedicamentoMockMvc.perform(get("/api/prescricao-medicamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoMedicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }
    
    @Test
    @Transactional
    public void getPrescricaoMedicamento() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);

        // Get the prescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(get("/api/prescricao-medicamentos/{id}", prescricaoMedicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescricaoMedicamento.getId().intValue()))
            .andExpect(jsonPath("$.idPaciente").value(DEFAULT_ID_PACIENTE.intValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    public void getNonExistingPrescricaoMedicamento() throws Exception {
        // Get the prescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(get("/api/prescricao-medicamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescricaoMedicamento() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);

        int databaseSizeBeforeUpdate = prescricaoMedicamentoRepository.findAll().size();

        // Update the prescricaoMedicamento
        PrescricaoMedicamento updatedPrescricaoMedicamento = prescricaoMedicamentoRepository.findById(prescricaoMedicamento.getId()).get();
        // Disconnect from session so that the updates on updatedPrescricaoMedicamento are not directly saved in db
        em.detach(updatedPrescricaoMedicamento);
        updatedPrescricaoMedicamento
            .idPaciente(UPDATED_ID_PACIENTE)
            .observacao(UPDATED_OBSERVACAO);
        PrescricaoMedicamentoDTO prescricaoMedicamentoDTO = prescricaoMedicamentoMapper.toDto(updatedPrescricaoMedicamento);

        restPrescricaoMedicamentoMockMvc.perform(put("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamentoDTO)))
            .andExpect(status().isOk());

        // Validate the PrescricaoMedicamento in the database
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeUpdate);
        PrescricaoMedicamento testPrescricaoMedicamento = prescricaoMedicamentoList.get(prescricaoMedicamentoList.size() - 1);
        assertThat(testPrescricaoMedicamento.getIdPaciente()).isEqualTo(UPDATED_ID_PACIENTE);
        assertThat(testPrescricaoMedicamento.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(1)).save(testPrescricaoMedicamento);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescricaoMedicamento() throws Exception {
        int databaseSizeBeforeUpdate = prescricaoMedicamentoRepository.findAll().size();

        // Create the PrescricaoMedicamento
        PrescricaoMedicamentoDTO prescricaoMedicamentoDTO = prescricaoMedicamentoMapper.toDto(prescricaoMedicamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescricaoMedicamentoMockMvc.perform(put("/api/prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoMedicamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoMedicamento in the database
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(0)).save(prescricaoMedicamento);
    }

    @Test
    @Transactional
    public void deletePrescricaoMedicamento() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);

        int databaseSizeBeforeDelete = prescricaoMedicamentoRepository.findAll().size();

        // Delete the prescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(delete("/api/prescricao-medicamentos/{id}", prescricaoMedicamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrescricaoMedicamento> prescricaoMedicamentoList = prescricaoMedicamentoRepository.findAll();
        assertThat(prescricaoMedicamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrescricaoMedicamento in Elasticsearch
        verify(mockPrescricaoMedicamentoSearchRepository, times(1)).deleteById(prescricaoMedicamento.getId());
    }

    @Test
    @Transactional
    public void searchPrescricaoMedicamento() throws Exception {
        // Initialize the database
        prescricaoMedicamentoRepository.saveAndFlush(prescricaoMedicamento);
        when(mockPrescricaoMedicamentoSearchRepository.search(queryStringQuery("id:" + prescricaoMedicamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prescricaoMedicamento), PageRequest.of(0, 1), 1));
        // Search the prescricaoMedicamento
        restPrescricaoMedicamentoMockMvc.perform(get("/api/_search/prescricao-medicamentos?query=id:" + prescricaoMedicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoMedicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoMedicamento.class);
        PrescricaoMedicamento prescricaoMedicamento1 = new PrescricaoMedicamento();
        prescricaoMedicamento1.setId(1L);
        PrescricaoMedicamento prescricaoMedicamento2 = new PrescricaoMedicamento();
        prescricaoMedicamento2.setId(prescricaoMedicamento1.getId());
        assertThat(prescricaoMedicamento1).isEqualTo(prescricaoMedicamento2);
        prescricaoMedicamento2.setId(2L);
        assertThat(prescricaoMedicamento1).isNotEqualTo(prescricaoMedicamento2);
        prescricaoMedicamento1.setId(null);
        assertThat(prescricaoMedicamento1).isNotEqualTo(prescricaoMedicamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoMedicamentoDTO.class);
        PrescricaoMedicamentoDTO prescricaoMedicamentoDTO1 = new PrescricaoMedicamentoDTO();
        prescricaoMedicamentoDTO1.setId(1L);
        PrescricaoMedicamentoDTO prescricaoMedicamentoDTO2 = new PrescricaoMedicamentoDTO();
        assertThat(prescricaoMedicamentoDTO1).isNotEqualTo(prescricaoMedicamentoDTO2);
        prescricaoMedicamentoDTO2.setId(prescricaoMedicamentoDTO1.getId());
        assertThat(prescricaoMedicamentoDTO1).isEqualTo(prescricaoMedicamentoDTO2);
        prescricaoMedicamentoDTO2.setId(2L);
        assertThat(prescricaoMedicamentoDTO1).isNotEqualTo(prescricaoMedicamentoDTO2);
        prescricaoMedicamentoDTO1.setId(null);
        assertThat(prescricaoMedicamentoDTO1).isNotEqualTo(prescricaoMedicamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prescricaoMedicamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prescricaoMedicamentoMapper.fromId(null)).isNull();
    }
}
