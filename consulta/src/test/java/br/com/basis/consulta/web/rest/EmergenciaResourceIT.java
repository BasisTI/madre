package br.com.basis.consulta.web.rest;

import br.com.basis.consulta.MadreconsultaApp;
import br.com.basis.consulta.domain.Emergencia;
import br.com.basis.consulta.repository.EmergenciaRepository;
import br.com.basis.consulta.repository.search.EmergenciaSearchRepository;
import br.com.basis.consulta.service.EmergenciaService;
import br.com.basis.consulta.service.dto.EmergenciaDTO;
import br.com.basis.consulta.service.mapper.EmergenciaMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static br.com.basis.consulta.web.rest.TestUtil.sameInstant;
import static br.com.basis.consulta.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.consulta.domain.enumeration.Turno;
import br.com.basis.consulta.domain.enumeration.TipoPagador;
/**
 * Integration tests for the {@link EmergenciaResource} REST controller.
 */
@SpringBootTest(classes = MadreconsultaApp.class)
public class EmergenciaResourceIT {

    private static final Long DEFAULT_NUMERO_CONSULTA = 1L;
    private static final Long UPDATED_NUMERO_CONSULTA = 2L;

    private static final ZonedDateTime DEFAULT_DATA_HORA_DA_CONSULTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_HORA_DA_CONSULTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_GRADE = 20L;
    private static final Long UPDATED_GRADE = 19L;

    private static final String DEFAULT_NUMERO_SALA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SALA = "BBBBBBBBBB";

    private static final Turno DEFAULT_TURNO = Turno.MATUTINO;
    private static final Turno UPDATED_TURNO = Turno.VESPERTINO;

    private static final TipoPagador DEFAULT_TIPO_PAGADOR = TipoPagador.SUS;
    private static final TipoPagador UPDATED_TIPO_PAGADOR = TipoPagador.OUTROS_CONVENIOS;

    private static final Boolean DEFAULT_GRADES_DISPONIVEIS = false;
    private static final Boolean UPDATED_GRADES_DISPONIVEIS = true;

    private static final Long DEFAULT_CLINICA_CENTRAL_ID = 1L;
    private static final Long UPDATED_CLINICA_CENTRAL_ID = 2L;

    private static final String DEFAULT_JUSTIFICATIVA = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIVA = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final Long DEFAULT_PACIENTE_ID = 1L;
    private static final Long UPDATED_PACIENTE_ID = 2L;

    @Autowired
    private EmergenciaRepository emergenciaRepository;

    @Autowired
    private EmergenciaMapper emergenciaMapper;

    @Autowired
    private EmergenciaService emergenciaService;

    @Autowired
    private EmergenciaSearchRepository emergenciaSearchRepository;



    /**
     * This repository is mocked in the br.com.basis.consulta.repository.search test package.
     *
     * @see br.com.basis.consulta.repository.search.EmergenciaSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmergenciaSearchRepository mockEmergenciaSearchRepository;

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

    private MockMvc restEmergenciaMockMvc;

    private Emergencia emergencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmergenciaResource emergenciaResource = new EmergenciaResource(emergenciaService, emergenciaSearchRepository);
        this.restEmergenciaMockMvc = MockMvcBuilders.standaloneSetup(emergenciaResource)
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
    public static Emergencia createEntity(EntityManager em) {
        Emergencia emergencia = new Emergencia()
            .numeroConsulta(DEFAULT_NUMERO_CONSULTA)
            .dataHoraDaConsulta(DEFAULT_DATA_HORA_DA_CONSULTA)
            .grade(DEFAULT_GRADE)
            .numeroSala(DEFAULT_NUMERO_SALA)
            .turno(DEFAULT_TURNO)
            .tipoPagador(DEFAULT_TIPO_PAGADOR)
            .gradesDisponiveis(DEFAULT_GRADES_DISPONIVEIS)
            .clinicaCentralId(DEFAULT_CLINICA_CENTRAL_ID)
            .justificativa(DEFAULT_JUSTIFICATIVA)
            .observacoes(DEFAULT_OBSERVACOES)
            .pacienteId(DEFAULT_PACIENTE_ID);
        return emergencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emergencia createUpdatedEntity(EntityManager em) {
        Emergencia emergencia = new Emergencia()
            .numeroConsulta(UPDATED_NUMERO_CONSULTA)
            .dataHoraDaConsulta(UPDATED_DATA_HORA_DA_CONSULTA)
            .grade(UPDATED_GRADE)
            .numeroSala(UPDATED_NUMERO_SALA)
            .turno(UPDATED_TURNO)
            .tipoPagador(UPDATED_TIPO_PAGADOR)
            .gradesDisponiveis(UPDATED_GRADES_DISPONIVEIS)
            .clinicaCentralId(UPDATED_CLINICA_CENTRAL_ID)
            .justificativa(UPDATED_JUSTIFICATIVA)
            .observacoes(UPDATED_OBSERVACOES)
            .pacienteId(UPDATED_PACIENTE_ID);
        return emergencia;
    }

    @BeforeEach
    public void initTest() {
        emergencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmergencia() throws Exception {
        int databaseSizeBeforeCreate = emergenciaRepository.findAll().size();

        // Create the Emergencia
        EmergenciaDTO emergenciaDTO = emergenciaMapper.toDto(emergencia);
        restEmergenciaMockMvc.perform(post("/api/emergencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Emergencia in the database
        List<Emergencia> emergenciaList = emergenciaRepository.findAll();
        assertThat(emergenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Emergencia testEmergencia = emergenciaList.get(emergenciaList.size() - 1);
        assertThat(testEmergencia.getNumeroConsulta()).isEqualTo(DEFAULT_NUMERO_CONSULTA);
        assertThat(testEmergencia.getDataHoraDaConsulta()).isEqualTo(DEFAULT_DATA_HORA_DA_CONSULTA);
        assertThat(testEmergencia.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmergencia.getNumeroSala()).isEqualTo(DEFAULT_NUMERO_SALA);
        assertThat(testEmergencia.getTurno()).isEqualTo(DEFAULT_TURNO);
        assertThat(testEmergencia.getTipoPagador()).isEqualTo(DEFAULT_TIPO_PAGADOR);
        assertThat(testEmergencia.isGradesDisponiveis()).isEqualTo(DEFAULT_GRADES_DISPONIVEIS);
        assertThat(testEmergencia.getClinicaCentralId()).isEqualTo(DEFAULT_CLINICA_CENTRAL_ID);
        assertThat(testEmergencia.getJustificativa()).isEqualTo(DEFAULT_JUSTIFICATIVA);
        assertThat(testEmergencia.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testEmergencia.getPacienteId()).isEqualTo(DEFAULT_PACIENTE_ID);

        // Validate the Emergencia in Elasticsearch
        verify(mockEmergenciaSearchRepository, times(1)).save(testEmergencia);
    }

    @Test
    @Transactional
    public void createEmergenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emergenciaRepository.findAll().size();

        // Create the Emergencia with an existing ID
        emergencia.setId(1L);
        EmergenciaDTO emergenciaDTO = emergenciaMapper.toDto(emergencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmergenciaMockMvc.perform(post("/api/emergencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Emergencia in the database
        List<Emergencia> emergenciaList = emergenciaRepository.findAll();
        assertThat(emergenciaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Emergencia in Elasticsearch
        verify(mockEmergenciaSearchRepository, times(0)).save(emergencia);
    }


    @Test
    @Transactional
    public void checkDataHoraDaConsultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = emergenciaRepository.findAll().size();
        // set the field null
        emergencia.setDataHoraDaConsulta(null);

        // Create the Emergencia, which fails.
        EmergenciaDTO emergenciaDTO = emergenciaMapper.toDto(emergencia);

        restEmergenciaMockMvc.perform(post("/api/emergencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Emergencia> emergenciaList = emergenciaRepository.findAll();
        assertThat(emergenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmergencias() throws Exception {
        // Initialize the database
        emergenciaRepository.saveAndFlush(emergencia);

        // Get all the emergenciaList
        restEmergenciaMockMvc.perform(get("/api/emergencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroConsulta").value(hasItem(DEFAULT_NUMERO_CONSULTA.intValue())))
            .andExpect(jsonPath("$.[*].dataHoraDaConsulta").value(hasItem(sameInstant(DEFAULT_DATA_HORA_DA_CONSULTA))))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.intValue())))
            .andExpect(jsonPath("$.[*].numeroSala").value(hasItem(DEFAULT_NUMERO_SALA)))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO.toString())))
            .andExpect(jsonPath("$.[*].tipoPagador").value(hasItem(DEFAULT_TIPO_PAGADOR.toString())))
            .andExpect(jsonPath("$.[*].gradesDisponiveis").value(hasItem(DEFAULT_GRADES_DISPONIVEIS.booleanValue())))
            .andExpect(jsonPath("$.[*].clinicaCentralId").value(hasItem(DEFAULT_CLINICA_CENTRAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].pacienteId").value(hasItem(DEFAULT_PACIENTE_ID.intValue())));
    }
    @Test
    @Transactional
    public void getEmergencia() throws Exception {
        // Initialize the database
        emergenciaRepository.saveAndFlush(emergencia);

        // Get the emergencia
        restEmergenciaMockMvc.perform(get("/api/emergencias/{id}", emergencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emergencia.getId().intValue()))
            .andExpect(jsonPath("$.numeroConsulta").value(DEFAULT_NUMERO_CONSULTA.intValue()))
            .andExpect(jsonPath("$.dataHoraDaConsulta").value(sameInstant(DEFAULT_DATA_HORA_DA_CONSULTA)))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.intValue()))
            .andExpect(jsonPath("$.numeroSala").value(DEFAULT_NUMERO_SALA))
            .andExpect(jsonPath("$.turno").value(DEFAULT_TURNO.toString()))
            .andExpect(jsonPath("$.tipoPagador").value(DEFAULT_TIPO_PAGADOR.toString()))
            .andExpect(jsonPath("$.gradesDisponiveis").value(DEFAULT_GRADES_DISPONIVEIS.booleanValue()))
            .andExpect(jsonPath("$.clinicaCentralId").value(DEFAULT_CLINICA_CENTRAL_ID.intValue()))
            .andExpect(jsonPath("$.justificativa").value(DEFAULT_JUSTIFICATIVA))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES))
            .andExpect(jsonPath("$.pacienteId").value(DEFAULT_PACIENTE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmergencia() throws Exception {
        // Get the emergencia
        restEmergenciaMockMvc.perform(get("/api/emergencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmergencia() throws Exception {
        // Initialize the database
        emergenciaRepository.saveAndFlush(emergencia);

        int databaseSizeBeforeUpdate = emergenciaRepository.findAll().size();

        // Update the emergencia
        Emergencia updatedEmergencia = emergenciaRepository.findById(emergencia.getId()).get();
        // Disconnect from session so that the updates on updatedEmergencia are not directly saved in db
        em.detach(updatedEmergencia);
        updatedEmergencia
            .numeroConsulta(UPDATED_NUMERO_CONSULTA)
            .dataHoraDaConsulta(UPDATED_DATA_HORA_DA_CONSULTA)
            .grade(UPDATED_GRADE)
            .numeroSala(UPDATED_NUMERO_SALA)
            .turno(UPDATED_TURNO)
            .tipoPagador(UPDATED_TIPO_PAGADOR)
            .gradesDisponiveis(UPDATED_GRADES_DISPONIVEIS)
            .clinicaCentralId(UPDATED_CLINICA_CENTRAL_ID)
            .justificativa(UPDATED_JUSTIFICATIVA)
            .observacoes(UPDATED_OBSERVACOES)
            .pacienteId(UPDATED_PACIENTE_ID);
        EmergenciaDTO emergenciaDTO = emergenciaMapper.toDto(updatedEmergencia);

        restEmergenciaMockMvc.perform(put("/api/emergencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergenciaDTO)))
            .andExpect(status().isOk());

        // Validate the Emergencia in the database
        List<Emergencia> emergenciaList = emergenciaRepository.findAll();
        assertThat(emergenciaList).hasSize(databaseSizeBeforeUpdate);
        Emergencia testEmergencia = emergenciaList.get(emergenciaList.size() - 1);
        assertThat(testEmergencia.getNumeroConsulta()).isEqualTo(UPDATED_NUMERO_CONSULTA);
        assertThat(testEmergencia.getDataHoraDaConsulta()).isEqualTo(UPDATED_DATA_HORA_DA_CONSULTA);
        assertThat(testEmergencia.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmergencia.getNumeroSala()).isEqualTo(UPDATED_NUMERO_SALA);
        assertThat(testEmergencia.getTurno()).isEqualTo(UPDATED_TURNO);
        assertThat(testEmergencia.getTipoPagador()).isEqualTo(UPDATED_TIPO_PAGADOR);
        assertThat(testEmergencia.isGradesDisponiveis()).isEqualTo(UPDATED_GRADES_DISPONIVEIS);
        assertThat(testEmergencia.getClinicaCentralId()).isEqualTo(UPDATED_CLINICA_CENTRAL_ID);
        assertThat(testEmergencia.getJustificativa()).isEqualTo(UPDATED_JUSTIFICATIVA);
        assertThat(testEmergencia.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testEmergencia.getPacienteId()).isEqualTo(UPDATED_PACIENTE_ID);

        // Validate the Emergencia in Elasticsearch
        verify(mockEmergenciaSearchRepository, times(1)).save(testEmergencia);
    }

    @Test
    @Transactional
    public void updateNonExistingEmergencia() throws Exception {
        int databaseSizeBeforeUpdate = emergenciaRepository.findAll().size();

        // Create the Emergencia
        EmergenciaDTO emergenciaDTO = emergenciaMapper.toDto(emergencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmergenciaMockMvc.perform(put("/api/emergencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emergenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Emergencia in the database
        List<Emergencia> emergenciaList = emergenciaRepository.findAll();
        assertThat(emergenciaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Emergencia in Elasticsearch
        verify(mockEmergenciaSearchRepository, times(0)).save(emergencia);
    }

    @Test
    @Transactional
    public void deleteEmergencia() throws Exception {
        // Initialize the database
        emergenciaRepository.saveAndFlush(emergencia);

        int databaseSizeBeforeDelete = emergenciaRepository.findAll().size();

        // Delete the emergencia
        restEmergenciaMockMvc.perform(delete("/api/emergencias/{id}", emergencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emergencia> emergenciaList = emergenciaRepository.findAll();
        assertThat(emergenciaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Emergencia in Elasticsearch
        verify(mockEmergenciaSearchRepository, times(1)).deleteById(emergencia.getId());
    }

    @Test
    @Transactional
    public void searchEmergencia() throws Exception {
        // Initialize the database
        emergenciaRepository.saveAndFlush(emergencia);
        when(mockEmergenciaSearchRepository.search(queryStringQuery("id:" + emergencia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(emergencia), PageRequest.of(0, 1), 1));
        // Search the emergencia
        restEmergenciaMockMvc.perform(get("/api/_search/emergencias?query=id:" + emergencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emergencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroConsulta").value(hasItem(DEFAULT_NUMERO_CONSULTA.intValue())))
            .andExpect(jsonPath("$.[*].dataHoraDaConsulta").value(hasItem(sameInstant(DEFAULT_DATA_HORA_DA_CONSULTA))))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.intValue())))
            .andExpect(jsonPath("$.[*].numeroSala").value(hasItem(DEFAULT_NUMERO_SALA)))
            .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO.toString())))
            .andExpect(jsonPath("$.[*].tipoPagador").value(hasItem(DEFAULT_TIPO_PAGADOR.toString())))
            .andExpect(jsonPath("$.[*].gradesDisponiveis").value(hasItem(DEFAULT_GRADES_DISPONIVEIS.booleanValue())))
            .andExpect(jsonPath("$.[*].clinicaCentralId").value(hasItem(DEFAULT_CLINICA_CENTRAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].pacienteId").value(hasItem(DEFAULT_PACIENTE_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emergencia.class);
        Emergencia emergencia1 = new Emergencia();
        emergencia1.setId(1L);
        Emergencia emergencia2 = new Emergencia();
        emergencia2.setId(emergencia1.getId());
        assertThat(emergencia1).isEqualTo(emergencia2);
        emergencia2.setId(2L);
        assertThat(emergencia1).isNotEqualTo(emergencia2);
        emergencia1.setId(null);
        assertThat(emergencia1).isNotEqualTo(emergencia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergenciaDTO.class);
        EmergenciaDTO emergenciaDTO1 = new EmergenciaDTO();
        emergenciaDTO1.setId(1L);
        EmergenciaDTO emergenciaDTO2 = new EmergenciaDTO();
        assertThat(emergenciaDTO1).isNotEqualTo(emergenciaDTO2);
        emergenciaDTO2.setId(emergenciaDTO1.getId());
        assertThat(emergenciaDTO1).isEqualTo(emergenciaDTO2);
        emergenciaDTO2.setId(2L);
        assertThat(emergenciaDTO1).isNotEqualTo(emergenciaDTO2);
        emergenciaDTO1.setId(null);
        assertThat(emergenciaDTO1).isNotEqualTo(emergenciaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emergenciaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emergenciaMapper.fromId(null)).isNull();
    }
}
