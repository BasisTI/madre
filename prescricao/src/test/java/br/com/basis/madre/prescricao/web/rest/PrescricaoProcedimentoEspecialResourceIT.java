package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.PrescricaoProcedimentoEspecial;
import br.com.basis.madre.prescricao.repository.PrescricaoProcedimentoEspecialRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoProcedimentoEspecialSearchRepository;
import br.com.basis.madre.prescricao.service.PrescricaoProcedimentoEspecialService;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoEspecialDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoProcedimentoEspecialMapper;
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
 * Integration tests for the {@link PrescricaoProcedimentoEspecialResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class PrescricaoProcedimentoEspecialResourceIT {

    private static final Long DEFAULT_ID_PACIENTE = 1L;
    private static final Long UPDATED_ID_PACIENTE = 2L;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private PrescricaoProcedimentoEspecialRepository prescricaoProcedimentoEspecialRepository;

    @Autowired
    private PrescricaoProcedimentoEspecialMapper prescricaoProcedimentoEspecialMapper;

    @Autowired
    private PrescricaoProcedimentoEspecialService prescricaoProcedimentoEspecialService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.PrescricaoProcedimentoEspecialSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrescricaoProcedimentoEspecialSearchRepository mockPrescricaoProcedimentoEspecialSearchRepository;

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

    private MockMvc restPrescricaoProcedimentoEspecialMockMvc;

    private PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescricaoProcedimentoEspecialResource prescricaoProcedimentoEspecialResource = new PrescricaoProcedimentoEspecialResource(prescricaoProcedimentoEspecialService);
        this.restPrescricaoProcedimentoEspecialMockMvc = MockMvcBuilders.standaloneSetup(prescricaoProcedimentoEspecialResource)
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
    public static PrescricaoProcedimentoEspecial createEntity(EntityManager em) {
        PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial = new PrescricaoProcedimentoEspecial()
            .idPaciente(DEFAULT_ID_PACIENTE)
            .observacao(DEFAULT_OBSERVACAO);
        return prescricaoProcedimentoEspecial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescricaoProcedimentoEspecial createUpdatedEntity(EntityManager em) {
        PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial = new PrescricaoProcedimentoEspecial()
            .idPaciente(UPDATED_ID_PACIENTE)
            .observacao(UPDATED_OBSERVACAO);
        return prescricaoProcedimentoEspecial;
    }

    @BeforeEach
    public void initTest() {
        prescricaoProcedimentoEspecial = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescricaoProcedimentoEspecial() throws Exception {
        int databaseSizeBeforeCreate = prescricaoProcedimentoEspecialRepository.findAll().size();

        // Create the PrescricaoProcedimentoEspecial
        PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO = prescricaoProcedimentoEspecialMapper.toDto(prescricaoProcedimentoEspecial);
        restPrescricaoProcedimentoEspecialMockMvc.perform(post("/api/prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isCreated());

        // Validate the PrescricaoProcedimentoEspecial in the database
        List<PrescricaoProcedimentoEspecial> prescricaoProcedimentoEspecialList = prescricaoProcedimentoEspecialRepository.findAll();
        assertThat(prescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeCreate + 1);
        PrescricaoProcedimentoEspecial testPrescricaoProcedimentoEspecial = prescricaoProcedimentoEspecialList.get(prescricaoProcedimentoEspecialList.size() - 1);
        assertThat(testPrescricaoProcedimentoEspecial.getIdPaciente()).isEqualTo(DEFAULT_ID_PACIENTE);
        assertThat(testPrescricaoProcedimentoEspecial.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the PrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockPrescricaoProcedimentoEspecialSearchRepository, times(1)).save(testPrescricaoProcedimentoEspecial);
    }

    @Test
    @Transactional
    public void createPrescricaoProcedimentoEspecialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescricaoProcedimentoEspecialRepository.findAll().size();

        // Create the PrescricaoProcedimentoEspecial with an existing ID
        prescricaoProcedimentoEspecial.setId(1L);
        PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO = prescricaoProcedimentoEspecialMapper.toDto(prescricaoProcedimentoEspecial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescricaoProcedimentoEspecialMockMvc.perform(post("/api/prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoProcedimentoEspecial in the database
        List<PrescricaoProcedimentoEspecial> prescricaoProcedimentoEspecialList = prescricaoProcedimentoEspecialRepository.findAll();
        assertThat(prescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockPrescricaoProcedimentoEspecialSearchRepository, times(0)).save(prescricaoProcedimentoEspecial);
    }


    @Test
    @Transactional
    public void checkIdPacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = prescricaoProcedimentoEspecialRepository.findAll().size();
        // set the field null
        prescricaoProcedimentoEspecial.setIdPaciente(null);

        // Create the PrescricaoProcedimentoEspecial, which fails.
        PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO = prescricaoProcedimentoEspecialMapper.toDto(prescricaoProcedimentoEspecial);

        restPrescricaoProcedimentoEspecialMockMvc.perform(post("/api/prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isBadRequest());

        List<PrescricaoProcedimentoEspecial> prescricaoProcedimentoEspecialList = prescricaoProcedimentoEspecialRepository.findAll();
        assertThat(prescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrescricaoProcedimentoEspecials() throws Exception {
        // Initialize the database
        prescricaoProcedimentoEspecialRepository.saveAndFlush(prescricaoProcedimentoEspecial);

        // Get all the prescricaoProcedimentoEspecialList
        restPrescricaoProcedimentoEspecialMockMvc.perform(get("/api/prescricao-procedimento-especials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoProcedimentoEspecial.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }
    
    @Test
    @Transactional
    public void getPrescricaoProcedimentoEspecial() throws Exception {
        // Initialize the database
        prescricaoProcedimentoEspecialRepository.saveAndFlush(prescricaoProcedimentoEspecial);

        // Get the prescricaoProcedimentoEspecial
        restPrescricaoProcedimentoEspecialMockMvc.perform(get("/api/prescricao-procedimento-especials/{id}", prescricaoProcedimentoEspecial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescricaoProcedimentoEspecial.getId().intValue()))
            .andExpect(jsonPath("$.idPaciente").value(DEFAULT_ID_PACIENTE.intValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    public void getNonExistingPrescricaoProcedimentoEspecial() throws Exception {
        // Get the prescricaoProcedimentoEspecial
        restPrescricaoProcedimentoEspecialMockMvc.perform(get("/api/prescricao-procedimento-especials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescricaoProcedimentoEspecial() throws Exception {
        // Initialize the database
        prescricaoProcedimentoEspecialRepository.saveAndFlush(prescricaoProcedimentoEspecial);

        int databaseSizeBeforeUpdate = prescricaoProcedimentoEspecialRepository.findAll().size();

        // Update the prescricaoProcedimentoEspecial
        PrescricaoProcedimentoEspecial updatedPrescricaoProcedimentoEspecial = prescricaoProcedimentoEspecialRepository.findById(prescricaoProcedimentoEspecial.getId()).get();
        // Disconnect from session so that the updates on updatedPrescricaoProcedimentoEspecial are not directly saved in db
        em.detach(updatedPrescricaoProcedimentoEspecial);
        updatedPrescricaoProcedimentoEspecial
            .idPaciente(UPDATED_ID_PACIENTE)
            .observacao(UPDATED_OBSERVACAO);
        PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO = prescricaoProcedimentoEspecialMapper.toDto(updatedPrescricaoProcedimentoEspecial);

        restPrescricaoProcedimentoEspecialMockMvc.perform(put("/api/prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isOk());

        // Validate the PrescricaoProcedimentoEspecial in the database
        List<PrescricaoProcedimentoEspecial> prescricaoProcedimentoEspecialList = prescricaoProcedimentoEspecialRepository.findAll();
        assertThat(prescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeUpdate);
        PrescricaoProcedimentoEspecial testPrescricaoProcedimentoEspecial = prescricaoProcedimentoEspecialList.get(prescricaoProcedimentoEspecialList.size() - 1);
        assertThat(testPrescricaoProcedimentoEspecial.getIdPaciente()).isEqualTo(UPDATED_ID_PACIENTE);
        assertThat(testPrescricaoProcedimentoEspecial.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the PrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockPrescricaoProcedimentoEspecialSearchRepository, times(1)).save(testPrescricaoProcedimentoEspecial);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescricaoProcedimentoEspecial() throws Exception {
        int databaseSizeBeforeUpdate = prescricaoProcedimentoEspecialRepository.findAll().size();

        // Create the PrescricaoProcedimentoEspecial
        PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO = prescricaoProcedimentoEspecialMapper.toDto(prescricaoProcedimentoEspecial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescricaoProcedimentoEspecialMockMvc.perform(put("/api/prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrescricaoProcedimentoEspecial in the database
        List<PrescricaoProcedimentoEspecial> prescricaoProcedimentoEspecialList = prescricaoProcedimentoEspecialRepository.findAll();
        assertThat(prescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockPrescricaoProcedimentoEspecialSearchRepository, times(0)).save(prescricaoProcedimentoEspecial);
    }

    @Test
    @Transactional
    public void deletePrescricaoProcedimentoEspecial() throws Exception {
        // Initialize the database
        prescricaoProcedimentoEspecialRepository.saveAndFlush(prescricaoProcedimentoEspecial);

        int databaseSizeBeforeDelete = prescricaoProcedimentoEspecialRepository.findAll().size();

        // Delete the prescricaoProcedimentoEspecial
        restPrescricaoProcedimentoEspecialMockMvc.perform(delete("/api/prescricao-procedimento-especials/{id}", prescricaoProcedimentoEspecial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrescricaoProcedimentoEspecial> prescricaoProcedimentoEspecialList = prescricaoProcedimentoEspecialRepository.findAll();
        assertThat(prescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockPrescricaoProcedimentoEspecialSearchRepository, times(1)).deleteById(prescricaoProcedimentoEspecial.getId());
    }

    @Test
    @Transactional
    public void searchPrescricaoProcedimentoEspecial() throws Exception {
        // Initialize the database
        prescricaoProcedimentoEspecialRepository.saveAndFlush(prescricaoProcedimentoEspecial);
        when(mockPrescricaoProcedimentoEspecialSearchRepository.search(queryStringQuery("id:" + prescricaoProcedimentoEspecial.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prescricaoProcedimentoEspecial), PageRequest.of(0, 1), 1));
        // Search the prescricaoProcedimentoEspecial
        restPrescricaoProcedimentoEspecialMockMvc.perform(get("/api/_search/prescricao-procedimento-especials?query=id:" + prescricaoProcedimentoEspecial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescricaoProcedimentoEspecial.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoProcedimentoEspecial.class);
        PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial1 = new PrescricaoProcedimentoEspecial();
        prescricaoProcedimentoEspecial1.setId(1L);
        PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial2 = new PrescricaoProcedimentoEspecial();
        prescricaoProcedimentoEspecial2.setId(prescricaoProcedimentoEspecial1.getId());
        assertThat(prescricaoProcedimentoEspecial1).isEqualTo(prescricaoProcedimentoEspecial2);
        prescricaoProcedimentoEspecial2.setId(2L);
        assertThat(prescricaoProcedimentoEspecial1).isNotEqualTo(prescricaoProcedimentoEspecial2);
        prescricaoProcedimentoEspecial1.setId(null);
        assertThat(prescricaoProcedimentoEspecial1).isNotEqualTo(prescricaoProcedimentoEspecial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescricaoProcedimentoEspecialDTO.class);
        PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO1 = new PrescricaoProcedimentoEspecialDTO();
        prescricaoProcedimentoEspecialDTO1.setId(1L);
        PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO2 = new PrescricaoProcedimentoEspecialDTO();
        assertThat(prescricaoProcedimentoEspecialDTO1).isNotEqualTo(prescricaoProcedimentoEspecialDTO2);
        prescricaoProcedimentoEspecialDTO2.setId(prescricaoProcedimentoEspecialDTO1.getId());
        assertThat(prescricaoProcedimentoEspecialDTO1).isEqualTo(prescricaoProcedimentoEspecialDTO2);
        prescricaoProcedimentoEspecialDTO2.setId(2L);
        assertThat(prescricaoProcedimentoEspecialDTO1).isNotEqualTo(prescricaoProcedimentoEspecialDTO2);
        prescricaoProcedimentoEspecialDTO1.setId(null);
        assertThat(prescricaoProcedimentoEspecialDTO1).isNotEqualTo(prescricaoProcedimentoEspecialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prescricaoProcedimentoEspecialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prescricaoProcedimentoEspecialMapper.fromId(null)).isNull();
    }
}
