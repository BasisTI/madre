package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.PreCadastroPaciente;
import br.com.basis.madre.repository.PreCadastroPacienteRepository;
import br.com.basis.madre.repository.search.PreCadastroPacienteSearchRepository;
import br.com.basis.madre.service.PreCadastroPacienteService;
import br.com.basis.madre.service.dto.PreCadastroPacienteDTO;
import br.com.basis.madre.service.mapper.PreCadastroPacienteMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PreCadastroPacienteResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
public class PreCadastroPacienteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_DA_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DA_MAE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DE_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CARTAO_SUS = "AAAAAAAAAA";
    private static final String UPDATED_CARTAO_SUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private PreCadastroPacienteRepository preCadastroPacienteRepository;

    @Autowired
    private PreCadastroPacienteMapper preCadastroPacienteMapper;

    @Autowired
    private PreCadastroPacienteService preCadastroPacienteService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.PreCadastroPacienteSearchRepositoryMockConfiguration
     */
    @Autowired
    private PreCadastroPacienteSearchRepository mockPreCadastroPacienteSearchRepository;

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

    private MockMvc restPreCadastroPacienteMockMvc;

    private PreCadastroPaciente preCadastroPaciente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreCadastroPacienteResource preCadastroPacienteResource = new PreCadastroPacienteResource(preCadastroPacienteService);
        this.restPreCadastroPacienteMockMvc = MockMvcBuilders.standaloneSetup(preCadastroPacienteResource)
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
    public static PreCadastroPaciente createEntity(EntityManager em) {
        PreCadastroPaciente preCadastroPaciente = new PreCadastroPaciente()
            .nome(DEFAULT_NOME)
            .nomeSocial(DEFAULT_NOME_SOCIAL)
            .nomeDaMae(DEFAULT_NOME_DA_MAE)
            .dataDeNascimento(DEFAULT_DATA_DE_NASCIMENTO)
            .cartaoSus(DEFAULT_CARTAO_SUS)
            .status(DEFAULT_STATUS);
        return preCadastroPaciente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreCadastroPaciente createUpdatedEntity(EntityManager em) {
        PreCadastroPaciente preCadastroPaciente = new PreCadastroPaciente()
            .nome(UPDATED_NOME)
            .nomeSocial(UPDATED_NOME_SOCIAL)
            .nomeDaMae(UPDATED_NOME_DA_MAE)
            .dataDeNascimento(UPDATED_DATA_DE_NASCIMENTO)
            .cartaoSus(UPDATED_CARTAO_SUS)
            .status(UPDATED_STATUS);
        return preCadastroPaciente;
    }

    @BeforeEach
    public void initTest() {
        preCadastroPaciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreCadastroPaciente() throws Exception {
        int databaseSizeBeforeCreate = preCadastroPacienteRepository.findAll().size();

        // Create the PreCadastroPaciente
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(preCadastroPaciente);
        restPreCadastroPacienteMockMvc.perform(post("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isCreated());

        // Validate the PreCadastroPaciente in the database
        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeCreate + 1);
        PreCadastroPaciente testPreCadastroPaciente = preCadastroPacienteList.get(preCadastroPacienteList.size() - 1);
        assertThat(testPreCadastroPaciente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPreCadastroPaciente.getNomeSocial()).isEqualTo(DEFAULT_NOME_SOCIAL);
        assertThat(testPreCadastroPaciente.getNomeDaMae()).isEqualTo(DEFAULT_NOME_DA_MAE);
        assertThat(testPreCadastroPaciente.getDataDeNascimento()).isEqualTo(DEFAULT_DATA_DE_NASCIMENTO);
        assertThat(testPreCadastroPaciente.getCartaoSus()).isEqualTo(DEFAULT_CARTAO_SUS);
        assertThat(testPreCadastroPaciente.isStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the PreCadastroPaciente in Elasticsearch
        verify(mockPreCadastroPacienteSearchRepository, times(1)).save(testPreCadastroPaciente);
    }

    @Test
    @Transactional
    public void createPreCadastroPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preCadastroPacienteRepository.findAll().size();

        // Create the PreCadastroPaciente with an existing ID
        preCadastroPaciente.setId(1L);
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(preCadastroPaciente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreCadastroPacienteMockMvc.perform(post("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PreCadastroPaciente in the database
        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeCreate);

        // Validate the PreCadastroPaciente in Elasticsearch
        verify(mockPreCadastroPacienteSearchRepository, times(0)).save(preCadastroPaciente);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroPacienteRepository.findAll().size();
        // set the field null
        preCadastroPaciente.setNome(null);

        // Create the PreCadastroPaciente, which fails.
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(preCadastroPaciente);

        restPreCadastroPacienteMockMvc.perform(post("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isBadRequest());

        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroPacienteRepository.findAll().size();
        // set the field null
        preCadastroPaciente.setNomeSocial(null);

        // Create the PreCadastroPaciente, which fails.
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(preCadastroPaciente);

        restPreCadastroPacienteMockMvc.perform(post("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isBadRequest());

        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeDaMaeIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroPacienteRepository.findAll().size();
        // set the field null
        preCadastroPaciente.setNomeDaMae(null);

        // Create the PreCadastroPaciente, which fails.
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(preCadastroPaciente);

        restPreCadastroPacienteMockMvc.perform(post("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isBadRequest());

        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDeNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroPacienteRepository.findAll().size();
        // set the field null
        preCadastroPaciente.setDataDeNascimento(null);

        // Create the PreCadastroPaciente, which fails.
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(preCadastroPaciente);

        restPreCadastroPacienteMockMvc.perform(post("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isBadRequest());

        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCartaoSusIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroPacienteRepository.findAll().size();
        // set the field null
        preCadastroPaciente.setCartaoSus(null);

        // Create the PreCadastroPaciente, which fails.
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(preCadastroPaciente);

        restPreCadastroPacienteMockMvc.perform(post("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isBadRequest());

        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPreCadastroPacientes() throws Exception {
        // Initialize the database
        preCadastroPacienteRepository.saveAndFlush(preCadastroPaciente);

        // Get all the preCadastroPacienteList
        restPreCadastroPacienteMockMvc.perform(get("/api/pre-cadastro-pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preCadastroPaciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeDaMae").value(hasItem(DEFAULT_NOME_DA_MAE)))
            .andExpect(jsonPath("$.[*].dataDeNascimento").value(hasItem(DEFAULT_DATA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cartaoSus").value(hasItem(DEFAULT_CARTAO_SUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getPreCadastroPaciente() throws Exception {
        // Initialize the database
        preCadastroPacienteRepository.saveAndFlush(preCadastroPaciente);

        // Get the preCadastroPaciente
        restPreCadastroPacienteMockMvc.perform(get("/api/pre-cadastro-pacientes/{id}", preCadastroPaciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preCadastroPaciente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nomeSocial").value(DEFAULT_NOME_SOCIAL))
            .andExpect(jsonPath("$.nomeDaMae").value(DEFAULT_NOME_DA_MAE))
            .andExpect(jsonPath("$.dataDeNascimento").value(DEFAULT_DATA_DE_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.cartaoSus").value(DEFAULT_CARTAO_SUS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPreCadastroPaciente() throws Exception {
        // Get the preCadastroPaciente
        restPreCadastroPacienteMockMvc.perform(get("/api/pre-cadastro-pacientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreCadastroPaciente() throws Exception {
        // Initialize the database
        preCadastroPacienteRepository.saveAndFlush(preCadastroPaciente);

        int databaseSizeBeforeUpdate = preCadastroPacienteRepository.findAll().size();

        // Update the preCadastroPaciente
        PreCadastroPaciente updatedPreCadastroPaciente = preCadastroPacienteRepository.findById(preCadastroPaciente.getId()).get();
        // Disconnect from session so that the updates on updatedPreCadastroPaciente are not directly saved in db
        em.detach(updatedPreCadastroPaciente);
        updatedPreCadastroPaciente
            .nome(UPDATED_NOME)
            .nomeSocial(UPDATED_NOME_SOCIAL)
            .nomeDaMae(UPDATED_NOME_DA_MAE)
            .dataDeNascimento(UPDATED_DATA_DE_NASCIMENTO)
            .cartaoSus(UPDATED_CARTAO_SUS)
            .status(UPDATED_STATUS);
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(updatedPreCadastroPaciente);

        restPreCadastroPacienteMockMvc.perform(put("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isOk());

        // Validate the PreCadastroPaciente in the database
        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeUpdate);
        PreCadastroPaciente testPreCadastroPaciente = preCadastroPacienteList.get(preCadastroPacienteList.size() - 1);
        assertThat(testPreCadastroPaciente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPreCadastroPaciente.getNomeSocial()).isEqualTo(UPDATED_NOME_SOCIAL);
        assertThat(testPreCadastroPaciente.getNomeDaMae()).isEqualTo(UPDATED_NOME_DA_MAE);
        assertThat(testPreCadastroPaciente.getDataDeNascimento()).isEqualTo(UPDATED_DATA_DE_NASCIMENTO);
        assertThat(testPreCadastroPaciente.getCartaoSus()).isEqualTo(UPDATED_CARTAO_SUS);
        assertThat(testPreCadastroPaciente.isStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the PreCadastroPaciente in Elasticsearch
        verify(mockPreCadastroPacienteSearchRepository, times(1)).save(testPreCadastroPaciente);
    }

    @Test
    @Transactional
    public void updateNonExistingPreCadastroPaciente() throws Exception {
        int databaseSizeBeforeUpdate = preCadastroPacienteRepository.findAll().size();

        // Create the PreCadastroPaciente
        PreCadastroPacienteDTO preCadastroPacienteDTO = preCadastroPacienteMapper.toDto(preCadastroPaciente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreCadastroPacienteMockMvc.perform(put("/api/pre-cadastro-pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastroPacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PreCadastroPaciente in the database
        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PreCadastroPaciente in Elasticsearch
        verify(mockPreCadastroPacienteSearchRepository, times(0)).save(preCadastroPaciente);
    }

    @Test
    @Transactional
    public void deletePreCadastroPaciente() throws Exception {
        // Initialize the database
        preCadastroPacienteRepository.saveAndFlush(preCadastroPaciente);

        int databaseSizeBeforeDelete = preCadastroPacienteRepository.findAll().size();

        // Delete the preCadastroPaciente
        restPreCadastroPacienteMockMvc.perform(delete("/api/pre-cadastro-pacientes/{id}", preCadastroPaciente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreCadastroPaciente> preCadastroPacienteList = preCadastroPacienteRepository.findAll();
        assertThat(preCadastroPacienteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PreCadastroPaciente in Elasticsearch
        verify(mockPreCadastroPacienteSearchRepository, times(1)).deleteById(preCadastroPaciente.getId());
    }

    @Test
    @Transactional
    public void searchPreCadastroPaciente() throws Exception {
        // Initialize the database
        preCadastroPacienteRepository.saveAndFlush(preCadastroPaciente);
        when(mockPreCadastroPacienteSearchRepository.search(queryStringQuery("id:" + preCadastroPaciente.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(preCadastroPaciente), PageRequest.of(0, 1), 1));
        // Search the preCadastroPaciente
        restPreCadastroPacienteMockMvc.perform(get("/api/_search/pre-cadastro-pacientes?query=id:" + preCadastroPaciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preCadastroPaciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeDaMae").value(hasItem(DEFAULT_NOME_DA_MAE)))
            .andExpect(jsonPath("$.[*].dataDeNascimento").value(hasItem(DEFAULT_DATA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cartaoSus").value(hasItem(DEFAULT_CARTAO_SUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreCadastroPaciente.class);
        PreCadastroPaciente preCadastroPaciente1 = new PreCadastroPaciente();
        preCadastroPaciente1.setId(1L);
        PreCadastroPaciente preCadastroPaciente2 = new PreCadastroPaciente();
        preCadastroPaciente2.setId(preCadastroPaciente1.getId());
        assertThat(preCadastroPaciente1).isEqualTo(preCadastroPaciente2);
        preCadastroPaciente2.setId(2L);
        assertThat(preCadastroPaciente1).isNotEqualTo(preCadastroPaciente2);
        preCadastroPaciente1.setId(null);
        assertThat(preCadastroPaciente1).isNotEqualTo(preCadastroPaciente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreCadastroPacienteDTO.class);
        PreCadastroPacienteDTO preCadastroPacienteDTO1 = new PreCadastroPacienteDTO();
        preCadastroPacienteDTO1.setId(1L);
        PreCadastroPacienteDTO preCadastroPacienteDTO2 = new PreCadastroPacienteDTO();
        assertThat(preCadastroPacienteDTO1).isNotEqualTo(preCadastroPacienteDTO2);
        preCadastroPacienteDTO2.setId(preCadastroPacienteDTO1.getId());
        assertThat(preCadastroPacienteDTO1).isEqualTo(preCadastroPacienteDTO2);
        preCadastroPacienteDTO2.setId(2L);
        assertThat(preCadastroPacienteDTO1).isNotEqualTo(preCadastroPacienteDTO2);
        preCadastroPacienteDTO1.setId(null);
        assertThat(preCadastroPacienteDTO1).isNotEqualTo(preCadastroPacienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(preCadastroPacienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(preCadastroPacienteMapper.fromId(null)).isNull();
    }
}
