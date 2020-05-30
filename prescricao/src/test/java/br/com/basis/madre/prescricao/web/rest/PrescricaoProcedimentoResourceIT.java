package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.PrescricaoProcedimento;
import br.com.basis.madre.prescricao.repository.PrescricaoProcedimentoRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoProcedimentoSearchRepository;
import br.com.basis.madre.prescricao.service.PrescricaoProcedimentoService;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoProcedimentoMapper;
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
 * Integration tests for the {@link PrescricaoProcedimentoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class PrescricaoProcedimentoResourceIT {

    private static final Long DEFAULT_ID_PACIENTE = 1L;
    private static final Long UPDATED_ID_PACIENTE = 2L;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private PrescricaoProcedimentoRepository prescricaoProcedimentoRepository;

    @Autowired
    private PrescricaoProcedimentoMapper prescricaoProcedimentoMapper;

    @Autowired
    private PrescricaoProcedimentoService prescricaoProcedimentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.PrescricaoProcedimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescricaoProcedimentoSearchRepository mockPrescricaoProcedimentoSearchRepository;

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

    private MockMvc restPrescricaoProcedimentoMockMvc;

    private PrescricaoProcedimento prescricaoProcedimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescricaoProcedimentoResource prescricaoProcedimentoResource = new PrescricaoProcedimentoResource(prescricaoProcedimentoService);
        this.restPrescricaoProcedimentoMockMvc = MockMvcBuilders.standaloneSetup(prescricaoProcedimentoResource)
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
    public static PrescricaoProcedimento createEntity(EntityManager em) {
        PrescricaoProcedimento prescricaoProcedimento = new PrescricaoProcedimento()
            .idPaciente(DEFAULT_ID_PACIENTE)
            .observacao(DEFAULT_OBSERVACAO);
        return prescricaoProcedimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescricaoProcedimento createUpdatedEntity(EntityManager em) {
        PrescricaoProcedimento prescricaoProcedimento = new PrescricaoProcedimento()
            .idPaciente(UPDATED_ID_PACIENTE)
            .observacao(UPDATED_OBSERVACAO);
        return prescricaoProcedimento;
    }

    @BeforeEach
    public void initTest() {
        prescricaoProcedimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescricaoProcedimento() throws Exception {
        int databaseSizeBeforeCreate = prescricaoProcedimentoRepository.findAll().size();

        // Create the PrescricaoProcedimento
        PrescricaoProcedimentoDTO prescricaoProcedimentoDTO = prescricaoProcedimentoMapper.toDto(prescricaoProcedimento);
        restPrescricaoProcedimentoMockMvc.perform(post("/api/prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the PrescricaoProcedimento in the database
        List<PrescricaoProcedimento> prescricaoProcedimentoList = prescricaoProcedimentoRepository.findAll();
        assertThat(prescricaoProcedimentoList).hasSize(databaseSizeBeforeCreate + 1);
        PrescricaoProcedimento testPrescricaoProcedimento = prescricaoProcedimentoList.get(prescricaoProcedimentoList.size() - 1);
        assertThat(testPrescricaoProcedimento.getIdPaciente()).isEqualTo(DEFAULT_ID_PACIENTE);
        assertThat(testPrescricaoProcedimento.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the PrescricaoProcedimento in Elasticsearch
        verify(mockPrescricaoProcedimentoSearchRepository, times(1)).save(testPrescricaoProcedimento);
    }

    @Test
    @Transactional
    public void createPrescricaoProcedimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescricaoProcedimentoRepository.findAll().size();

        // Create the PrescricaoProcedimento with an existing ID
        prescricaoProcedimento.setId(1L);
        PrescricaoProcedimentoDTO prescricaoProcedimentoDTO = prescricaoProcedimentoMapper.toDto(prescricaoProcedimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescricaoProcedimentoMockMvc.perform(post("/api/prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoProcedimento in the database
        List<PrescricaoProcedimento> prescricaoProcedimentoList = prescricaoProcedimentoRepository.findAll();
        assertThat(prescricaoProcedimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrescricaoProcedimento in Elasticsearch
        verify(mockPrescricaoProcedimentoSearchRepository, times(0)).save(prescricaoProcedimento);
    }


    @Test
    @Transactional
    public void checkIdPacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = prescricaoProcedimentoRepository.findAll().size();
        // set the field null
        prescricaoProcedimento.setIdPaciente(null);

        // Create the PrescricaoProcedimento, which fails.
        PrescricaoProcedimentoDTO prescricaoProcedimentoDTO = prescricaoProcedimentoMapper.toDto(prescricaoProcedimento);

        restPrescricaoProcedimentoMockMvc.perform(post("/api/prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoDTO)))
            .andExpect(status().isBadRequest());

        List<PrescricaoProcedimento> prescricaoProcedimentoList = prescricaoProcedimentoRepository.findAll();
        assertThat(prescricaoProcedimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrescricaoProcedimentos() throws Exception {
        // Initialize the database
        prescricaoProcedimentoRepository.saveAndFlush(prescricaoProcedimento);

        // Get all the prescricaoProcedimentoList
        restPrescricaoProcedimentoMockMvc.perform(get("/api/prescricao-procedimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoProcedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }
    
    @Test
    @Transactional
    public void getPrescricaoProcedimento() throws Exception {
        // Initialize the database
        prescricaoProcedimentoRepository.saveAndFlush(prescricaoProcedimento);

        // Get the prescricaoProcedimento
        restPrescricaoProcedimentoMockMvc.perform(get("/api/prescricao-procedimentos/{id}", prescricaoProcedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescricaoProcedimento.getId().intValue()))
            .andExpect(jsonPath("$.idPaciente").value(DEFAULT_ID_PACIENTE.intValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    public void getNonExistingPrescricaoProcedimento() throws Exception {
        // Get the prescricaoProcedimento
        restPrescricaoProcedimentoMockMvc.perform(get("/api/prescricao-procedimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescricaoProcedimento() throws Exception {
        // Initialize the database
        prescricaoProcedimentoRepository.saveAndFlush(prescricaoProcedimento);

        int databaseSizeBeforeUpdate = prescricaoProcedimentoRepository.findAll().size();

        // Update the prescricaoProcedimento
        PrescricaoProcedimento updatedPrescricaoProcedimento = prescricaoProcedimentoRepository.findById(prescricaoProcedimento.getId()).get();
        // Disconnect from session so that the updates on updatedPrescricaoProcedimento are not directly saved in db
        em.detach(updatedPrescricaoProcedimento);
        updatedPrescricaoProcedimento
            .idPaciente(UPDATED_ID_PACIENTE)
            .observacao(UPDATED_OBSERVACAO);
        PrescricaoProcedimentoDTO prescricaoProcedimentoDTO = prescricaoProcedimentoMapper.toDto(updatedPrescricaoProcedimento);

        restPrescricaoProcedimentoMockMvc.perform(put("/api/prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoDTO)))
            .andExpect(status().isOk());

        // Validate the PrescricaoProcedimento in the database
        List<PrescricaoProcedimento> prescricaoProcedimentoList = prescricaoProcedimentoRepository.findAll();
        assertThat(prescricaoProcedimentoList).hasSize(databaseSizeBeforeUpdate);
        PrescricaoProcedimento testPrescricaoProcedimento = prescricaoProcedimentoList.get(prescricaoProcedimentoList.size() - 1);
        assertThat(testPrescricaoProcedimento.getIdPaciente()).isEqualTo(UPDATED_ID_PACIENTE);
        assertThat(testPrescricaoProcedimento.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the PrescricaoProcedimento in Elasticsearch
        verify(mockPrescricaoProcedimentoSearchRepository, times(1)).save(testPrescricaoProcedimento);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescricaoProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = prescricaoProcedimentoRepository.findAll().size();

        // Create the PrescricaoProcedimento
        PrescricaoProcedimentoDTO prescricaoProcedimentoDTO = prescricaoProcedimentoMapper.toDto(prescricaoProcedimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescricaoProcedimentoMockMvc.perform(put("/api/prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoProcedimento in the database
        List<PrescricaoProcedimento> prescricaoProcedimentoList = prescricaoProcedimentoRepository.findAll();
        assertThat(prescricaoProcedimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrescricaoProcedimento in Elasticsearch
        verify(mockPrescricaoProcedimentoSearchRepository, times(0)).save(prescricaoProcedimento);
    }

    @Test
    @Transactional
    public void deletePrescricaoProcedimento() throws Exception {
        // Initialize the database
        prescricaoProcedimentoRepository.saveAndFlush(prescricaoProcedimento);

        int databaseSizeBeforeDelete = prescricaoProcedimentoRepository.findAll().size();

        // Delete the prescricaoProcedimento
        restPrescricaoProcedimentoMockMvc.perform(delete("/api/prescricao-procedimentos/{id}", prescricaoProcedimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrescricaoProcedimento> prescricaoProcedimentoList = prescricaoProcedimentoRepository.findAll();
        assertThat(prescricaoProcedimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrescricaoProcedimento in Elasticsearch
        verify(mockPrescricaoProcedimentoSearchRepository, times(1)).deleteById(prescricaoProcedimento.getId());
    }

    @Test
    @Transactional
    public void searchPrescricaoProcedimento() throws Exception {
        // Initialize the database
        prescricaoProcedimentoRepository.saveAndFlush(prescricaoProcedimento);
        when(mockPrescricaoProcedimentoSearchRepository.search(queryStringQuery("id:" + prescricaoProcedimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prescricaoProcedimento), PageRequest.of(0, 1), 1));
        // Search the prescricaoProcedimento
        restPrescricaoProcedimentoMockMvc.perform(get("/api/_search/prescricao-procedimentos?query=id:" + prescricaoProcedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoProcedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoProcedimento.class);
        PrescricaoProcedimento prescricaoProcedimento1 = new PrescricaoProcedimento();
        prescricaoProcedimento1.setId(1L);
        PrescricaoProcedimento prescricaoProcedimento2 = new PrescricaoProcedimento();
        prescricaoProcedimento2.setId(prescricaoProcedimento1.getId());
        assertThat(prescricaoProcedimento1).isEqualTo(prescricaoProcedimento2);
        prescricaoProcedimento2.setId(2L);
        assertThat(prescricaoProcedimento1).isNotEqualTo(prescricaoProcedimento2);
        prescricaoProcedimento1.setId(null);
        assertThat(prescricaoProcedimento1).isNotEqualTo(prescricaoProcedimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoProcedimentoDTO.class);
        PrescricaoProcedimentoDTO prescricaoProcedimentoDTO1 = new PrescricaoProcedimentoDTO();
        prescricaoProcedimentoDTO1.setId(1L);
        PrescricaoProcedimentoDTO prescricaoProcedimentoDTO2 = new PrescricaoProcedimentoDTO();
        assertThat(prescricaoProcedimentoDTO1).isNotEqualTo(prescricaoProcedimentoDTO2);
        prescricaoProcedimentoDTO2.setId(prescricaoProcedimentoDTO1.getId());
        assertThat(prescricaoProcedimentoDTO1).isEqualTo(prescricaoProcedimentoDTO2);
        prescricaoProcedimentoDTO2.setId(2L);
        assertThat(prescricaoProcedimentoDTO1).isNotEqualTo(prescricaoProcedimentoDTO2);
        prescricaoProcedimentoDTO1.setId(null);
        assertThat(prescricaoProcedimentoDTO1).isNotEqualTo(prescricaoProcedimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prescricaoProcedimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prescricaoProcedimentoMapper.fromId(null)).isNull();
    }
}
