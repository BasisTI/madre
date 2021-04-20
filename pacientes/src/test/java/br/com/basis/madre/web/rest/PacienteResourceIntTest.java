package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.enumeration.GrauDeInstrucao;
import br.com.basis.madre.domain.enumeration.Sexo;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import br.com.basis.madre.service.PacienteService;
import br.com.basis.madre.service.PdfPacienteService;
import br.com.basis.madre.service.dto.PacienteDTO;
import br.com.basis.madre.service.mapper.PacienteMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the PacienteResource REST controller.
 *
 * @see PacienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PacientesApp.class)
public class PacienteResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_SOCIAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DE_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HORA_DE_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_DE_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final GrauDeInstrucao DEFAULT_GRAU_DE_INSTRUCAO = GrauDeInstrucao.NENHUM;
    private static final GrauDeInstrucao UPDATED_GRAU_DE_INSTRUCAO = GrauDeInstrucao.IGNORADO;

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMININO;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private PacienteService pacienteService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.PacienteSearchRepositoryMockConfiguration
     */
    @Autowired
    private PacienteSearchRepository mockPacienteSearchRepository;

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

    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    @Autowired
    private PdfPacienteService pdfPacienteService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacienteResource pacienteResource = new PacienteResource(pacienteService, pdfPacienteService);
        this.restPacienteMockMvc = MockMvcBuilders.standaloneSetup(pacienteResource)
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
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nome(DEFAULT_NOME)
            .nomeSocial(DEFAULT_NOME_SOCIAL)
            .dataDeNascimento(DEFAULT_DATA_DE_NASCIMENTO)
            .horaDeNascimento(DEFAULT_HORA_DE_NASCIMENTO)
            .email(DEFAULT_EMAIL)
            .observacao(DEFAULT_OBSERVACAO)
            .grauDeInstrucao(DEFAULT_GRAU_DE_INSTRUCAO)
            .sexo(DEFAULT_SEXO);
        return paciente;
    }

    @Before
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPaciente.getNomeSocial()).isEqualTo(DEFAULT_NOME_SOCIAL);
        assertThat(testPaciente.getDataDeNascimento()).isEqualTo(DEFAULT_DATA_DE_NASCIMENTO);
        assertThat(testPaciente.getHoraDeNascimento()).isEqualTo(DEFAULT_HORA_DE_NASCIMENTO);
        assertThat(testPaciente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPaciente.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testPaciente.getGrauDeInstrucao()).isEqualTo(DEFAULT_GRAU_DE_INSTRUCAO);
        assertThat(testPaciente.getSexo()).isEqualTo(DEFAULT_SEXO);

        // Validate the Paciente in Elasticsearch
        verify(mockPacienteSearchRepository, times(1)).save(testPaciente);
    }

    @Test
    @Transactional
    public void createPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente with an existing ID
        paciente.setId(1L);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Paciente in Elasticsearch
        verify(mockPacienteSearchRepository, times(0)).save(paciente);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setNome(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDeNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setDataDeNascimento(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrauDeInstrucaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setGrauDeInstrucao(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
        // set the field null
        paciente.setSexo(null);

        // Create the Paciente, which fails.
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].dataDeNascimento").value(hasItem(DEFAULT_DATA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].horaDeNascimento").value(hasItem(DEFAULT_HORA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].grauDeInstrucao").value(hasItem(DEFAULT_GRAU_DE_INSTRUCAO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())));
    }

    @Test
    @Transactional
    public void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.nomeSocial").value(DEFAULT_NOME_SOCIAL.toString()))
            .andExpect(jsonPath("$.dataDeNascimento").value(DEFAULT_DATA_DE_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.horaDeNascimento").value(DEFAULT_HORA_DE_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.grauDeInstrucao").value(DEFAULT_GRAU_DE_INSTRUCAO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .nome(UPDATED_NOME)
            .nomeSocial(UPDATED_NOME_SOCIAL)
            .dataDeNascimento(UPDATED_DATA_DE_NASCIMENTO)
            .horaDeNascimento(UPDATED_HORA_DE_NASCIMENTO)
            .email(UPDATED_EMAIL)
            .observacao(UPDATED_OBSERVACAO)
            .grauDeInstrucao(UPDATED_GRAU_DE_INSTRUCAO)
            .sexo(UPDATED_SEXO);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(updatedPaciente);

        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPaciente.getNomeSocial()).isEqualTo(UPDATED_NOME_SOCIAL);
        assertThat(testPaciente.getDataDeNascimento()).isEqualTo(UPDATED_DATA_DE_NASCIMENTO);
        assertThat(testPaciente.getHoraDeNascimento()).isEqualTo(UPDATED_HORA_DE_NASCIMENTO);
        assertThat(testPaciente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaciente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testPaciente.getGrauDeInstrucao()).isEqualTo(UPDATED_GRAU_DE_INSTRUCAO);
        assertThat(testPaciente.getSexo()).isEqualTo(UPDATED_SEXO);

        // Validate the Paciente in Elasticsearch
        verify(mockPacienteSearchRepository, times(1)).save(testPaciente);
    }

    @Test
    @Transactional
    public void updateNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Paciente in Elasticsearch
        verify(mockPacienteSearchRepository, times(0)).save(paciente);
    }

    @Test
    @Transactional
    public void deletePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Get the paciente
        restPacienteMockMvc.perform(delete("/api/pacientes/{id}", paciente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Paciente in Elasticsearch
        verify(mockPacienteSearchRepository, times(1)).deleteById(paciente.getId());
    }

    @Test
    @Transactional
    public void searchPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);
        when(mockPacienteSearchRepository.search(queryStringQuery("id:" + paciente.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paciente), PageRequest.of(0, 1), 1));
        // Search the paciente
        restPacienteMockMvc.perform(get("/api/_search/pacientes?query=id:" + paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL)))
            .andExpect(jsonPath("$.[*].dataDeNascimento").value(hasItem(DEFAULT_DATA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].horaDeNascimento").value(hasItem(DEFAULT_HORA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].grauDeInstrucao").value(hasItem(DEFAULT_GRAU_DE_INSTRUCAO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        Paciente paciente2 = new Paciente();
        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);
        paciente2.setId(2L);
        assertThat(paciente1).isNotEqualTo(paciente2);
        paciente1.setId(null);
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacienteDTO.class);
        PacienteDTO pacienteDTO1 = new PacienteDTO();
        pacienteDTO1.setId(1L);
        PacienteDTO pacienteDTO2 = new PacienteDTO();
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO2.setId(pacienteDTO1.getId());
        assertThat(pacienteDTO1).isEqualTo(pacienteDTO2);
        pacienteDTO2.setId(2L);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO1.setId(null);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pacienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pacienteMapper.fromId(null)).isNull();
    }
}
