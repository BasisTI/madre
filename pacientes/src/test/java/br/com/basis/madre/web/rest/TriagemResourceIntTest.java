package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.repository.TriagemRepository;
import br.com.basis.madre.repository.search.TriagemSearchRepository;
import br.com.basis.madre.service.TriagemService;
import br.com.basis.madre.service.dto.TriagemDTO;
import br.com.basis.madre.service.mapper.TriagemMapper;
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
 * Test class for the TriagemResource REST controller.
 *
 * @see TriagemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PacientesApp.class)
public class TriagemResourceIntTest<ExceptionTranslator> {

    private static final String DEFAULT_NOME_DO_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_PACIENTE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRESSAO_ARTERIAL = 1L;
    private static final Long UPDATED_PRESSAO_ARTERIAL = 2L;

    private static final Long DEFAULT_FREQUENCIA_CARDIACA = 1L;
    private static final Long UPDATED_FREQUENCIA_CARDIACA = 2L;

    private static final Long DEFAULT_TEMPERATURA = 1L;
    private static final Long UPDATED_TEMPERATURA = 2L;

    private static final Long DEFAULT_PESO = 1L;
    private static final Long UPDATED_PESO = 2L;

    private static final String DEFAULT_SINAIS_SINTOMAS = "AAAAAAAAAA";
    private static final String UPDATED_SINAIS_SINTOMAS = "BBBBBBBBBB";

    private static final Instant DEFAULT_HORA_DO_ATENDIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_DO_ATENDIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DATA_DO_ATENDIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DO_ATENDIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_IDADE = 1;
    private static final Integer UPDATED_IDADE = 2;

    private static final String DEFAULT_DESCRICAO_QUEIXA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_QUEIXA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VITIMA_DE_ACIDENTE = false;
    private static final Boolean UPDATED_VITIMA_DE_ACIDENTE = true;

    private static final Boolean DEFAULT_REMOVIDO_DE_AMBULANCIA = false;
    private static final Boolean UPDATED_REMOVIDO_DE_AMBULANCIA = true;

    @Autowired
    private TriagemRepository triagemRepository;

    @Autowired
    private TriagemMapper triagemMapper;

    @Autowired
    private TriagemService triagemService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.TriagemSearchRepositoryMockConfiguration
     */
    @Autowired
    private TriagemSearchRepository mockTriagemSearchRepository;

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

    private MockMvc restTriagemMockMvc;

    private Triagem triagem;

    public TriagemResourceIntTest(TriagemRepository triagemRepository) {
        this.triagemRepository = triagemRepository;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TriagemResource triagemResource = new TriagemResource(triagemService);
        this.restTriagemMockMvc = MockMvcBuilders.standaloneSetup(triagemResource)
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
    public static Triagem createEntity(EntityManager em) {
        Triagem triagem = new Triagem()
            .nomeDoPaciente(DEFAULT_NOME_DO_PACIENTE)
            .pressaoArterial(DEFAULT_PRESSAO_ARTERIAL)
            .frequenciaCardiaca(DEFAULT_FREQUENCIA_CARDIACA)
            .temperatura(DEFAULT_TEMPERATURA)
            .peso(DEFAULT_PESO)
            .sinaisSintomas(DEFAULT_SINAIS_SINTOMAS)
            .horaDoAtendimento(DEFAULT_HORA_DO_ATENDIMENTO)
            .dataDoAtendimento(DEFAULT_DATA_DO_ATENDIMENTO)
            .idade(DEFAULT_IDADE)
            .descricaoQueixa(DEFAULT_DESCRICAO_QUEIXA)
            .vitimaDeAcidente(DEFAULT_VITIMA_DE_ACIDENTE)
            .removidoDeAmbulancia(DEFAULT_REMOVIDO_DE_AMBULANCIA);
        return triagem;
    }

    @Before
    public void initTest() {
        triagem = createEntity(em);
    }

    @Test
    @Transactional
    public void createTriagem() throws Exception {
        int databaseSizeBeforeCreate = triagemRepository.findAll().size();

        // Create the Triagem
        TriagemDTO triagemDTO = triagemMapper.toDto(triagem);
        restTriagemMockMvc.perform(post("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isCreated());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeCreate + 1);
        Triagem testTriagem = triagemList.get(triagemList.size() - 1);
        assertThat(testTriagem.getNomeDoPaciente()).isEqualTo(DEFAULT_NOME_DO_PACIENTE);
        assertThat(testTriagem.getPressaoArterial()).isEqualTo(DEFAULT_PRESSAO_ARTERIAL);
        assertThat(testTriagem.getFrequenciaCardiaca()).isEqualTo(DEFAULT_FREQUENCIA_CARDIACA);
        assertThat(testTriagem.getTemperatura()).isEqualTo(DEFAULT_TEMPERATURA);
        assertThat(testTriagem.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testTriagem.getSinaisSintomas()).isEqualTo(DEFAULT_SINAIS_SINTOMAS);
        assertThat(testTriagem.getHoraDoAtendimento()).isEqualTo(DEFAULT_HORA_DO_ATENDIMENTO);
        assertThat(testTriagem.getDataDoAtendimento()).isEqualTo(DEFAULT_DATA_DO_ATENDIMENTO);
        assertThat(testTriagem.getIdade()).isEqualTo(DEFAULT_IDADE);
        assertThat(testTriagem.getDescricaoQueixa()).isEqualTo(DEFAULT_DESCRICAO_QUEIXA);
        assertThat(testTriagem.isVitimaDeAcidente()).isEqualTo(DEFAULT_VITIMA_DE_ACIDENTE);
        assertThat(testTriagem.isRemovidoDeAmbulancia()).isEqualTo(DEFAULT_REMOVIDO_DE_AMBULANCIA);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(1)).save(testTriagem);
    }

    @Test
    @Transactional
    public void createTriagemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = triagemRepository.findAll().size();

        // Create the Triagem with an existing ID
        triagem.setId(1L);
        TriagemDTO triagemDTO = triagemMapper.toDto(triagem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTriagemMockMvc.perform(post("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeCreate);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(0)).save(triagem);
    }

    @Test
    @Transactional
    public void checkNomeDoPacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = triagemRepository.findAll().size();
        // set the field null
        triagem.setNomeDoPaciente(null);

        // Create the Triagem, which fails.
        TriagemDTO triagemDTO = triagemMapper.toDto(triagem);

        restTriagemMockMvc.perform(post("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isBadRequest());

        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoQueixaIsRequired() throws Exception {
        int databaseSizeBeforeTest = triagemRepository.findAll().size();
        // set the field null
        triagem.setDescricaoQueixa(null);

        // Create the Triagem, which fails.
        TriagemDTO triagemDTO = triagemMapper.toDto(triagem);

        restTriagemMockMvc.perform(post("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isBadRequest());

        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTriagems() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);

        // Get all the triagemList
        restTriagemMockMvc.perform(get("/api/triagems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(triagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoPaciente").value(hasItem(DEFAULT_NOME_DO_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].pressaoArterial").value(hasItem(DEFAULT_PRESSAO_ARTERIAL.intValue())))
            .andExpect(jsonPath("$.[*].frequenciaCardiaca").value(hasItem(DEFAULT_FREQUENCIA_CARDIACA.intValue())))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.intValue())))
            .andExpect(jsonPath("$.[*].sinaisSintomas").value(hasItem(DEFAULT_SINAIS_SINTOMAS.toString())))
            .andExpect(jsonPath("$.[*].horaDoAtendimento").value(hasItem(DEFAULT_HORA_DO_ATENDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataDoAtendimento").value(hasItem(DEFAULT_DATA_DO_ATENDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)))
            .andExpect(jsonPath("$.[*].descricaoQueixa").value(hasItem(DEFAULT_DESCRICAO_QUEIXA.toString())))
            .andExpect(jsonPath("$.[*].vitimaDeAcidente").value(hasItem(DEFAULT_VITIMA_DE_ACIDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].removidoDeAmbulancia").value(hasItem(DEFAULT_REMOVIDO_DE_AMBULANCIA.booleanValue())));
    }

    @Test
    @Transactional
    public void getTriagem() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);

        // Get the triagem
        restTriagemMockMvc.perform(get("/api/triagems/{id}", triagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(triagem.getId().intValue()))
            .andExpect(jsonPath("$.nomeDoPaciente").value(DEFAULT_NOME_DO_PACIENTE.toString()))
            .andExpect(jsonPath("$.pressaoArterial").value(DEFAULT_PRESSAO_ARTERIAL.intValue()))
            .andExpect(jsonPath("$.frequenciaCardiaca").value(DEFAULT_FREQUENCIA_CARDIACA.intValue()))
            .andExpect(jsonPath("$.temperatura").value(DEFAULT_TEMPERATURA.intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.intValue()))
            .andExpect(jsonPath("$.sinaisSintomas").value(DEFAULT_SINAIS_SINTOMAS.toString()))
            .andExpect(jsonPath("$.horaDoAtendimento").value(DEFAULT_HORA_DO_ATENDIMENTO.toString()))
            .andExpect(jsonPath("$.dataDoAtendimento").value(DEFAULT_DATA_DO_ATENDIMENTO.toString()))
            .andExpect(jsonPath("$.idade").value(DEFAULT_IDADE))
            .andExpect(jsonPath("$.descricaoQueixa").value(DEFAULT_DESCRICAO_QUEIXA.toString()))
            .andExpect(jsonPath("$.vitimaDeAcidente").value(DEFAULT_VITIMA_DE_ACIDENTE.booleanValue()))
            .andExpect(jsonPath("$.removidoDeAmbulancia").value(DEFAULT_REMOVIDO_DE_AMBULANCIA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTriagem() throws Exception {
        // Get the triagem
        restTriagemMockMvc.perform(get("/api/triagems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTriagem() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);

        int databaseSizeBeforeUpdate = triagemRepository.findAll().size();

        // Update the triagem
        Triagem updatedTriagem = triagemRepository.findById(triagem.getId()).get();
        // Disconnect from session so that the updates on updatedTriagem are not directly saved in db
        em.detach(updatedTriagem);
        updatedTriagem
            .nomeDoPaciente(UPDATED_NOME_DO_PACIENTE)
            .pressaoArterial(UPDATED_PRESSAO_ARTERIAL)
            .frequenciaCardiaca(UPDATED_FREQUENCIA_CARDIACA)
            .temperatura(UPDATED_TEMPERATURA)
            .peso(UPDATED_PESO)
            .sinaisSintomas(UPDATED_SINAIS_SINTOMAS)
            .horaDoAtendimento(UPDATED_HORA_DO_ATENDIMENTO)
            .dataDoAtendimento(UPDATED_DATA_DO_ATENDIMENTO)
            .idade(UPDATED_IDADE)
            .descricaoQueixa(UPDATED_DESCRICAO_QUEIXA)
            .vitimaDeAcidente(UPDATED_VITIMA_DE_ACIDENTE)
            .removidoDeAmbulancia(UPDATED_REMOVIDO_DE_AMBULANCIA);
        TriagemDTO triagemDTO = triagemMapper.toDto(updatedTriagem);

        restTriagemMockMvc.perform(put("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isOk());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeUpdate);
        Triagem testTriagem = triagemList.get(triagemList.size() - 1);
        assertThat(testTriagem.getNomeDoPaciente()).isEqualTo(UPDATED_NOME_DO_PACIENTE);
        assertThat(testTriagem.getPressaoArterial()).isEqualTo(UPDATED_PRESSAO_ARTERIAL);
        assertThat(testTriagem.getFrequenciaCardiaca()).isEqualTo(UPDATED_FREQUENCIA_CARDIACA);
        assertThat(testTriagem.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
        assertThat(testTriagem.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testTriagem.getSinaisSintomas()).isEqualTo(UPDATED_SINAIS_SINTOMAS);
        assertThat(testTriagem.getHoraDoAtendimento()).isEqualTo(UPDATED_HORA_DO_ATENDIMENTO);
        assertThat(testTriagem.getDataDoAtendimento()).isEqualTo(UPDATED_DATA_DO_ATENDIMENTO);
        assertThat(testTriagem.getIdade()).isEqualTo(UPDATED_IDADE);
        assertThat(testTriagem.getDescricaoQueixa()).isEqualTo(UPDATED_DESCRICAO_QUEIXA);
        assertThat(testTriagem.isVitimaDeAcidente()).isEqualTo(UPDATED_VITIMA_DE_ACIDENTE);
        assertThat(testTriagem.isRemovidoDeAmbulancia()).isEqualTo(UPDATED_REMOVIDO_DE_AMBULANCIA);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(1)).save(testTriagem);
    }

    @Test
    @Transactional
    public void updateNonExistingTriagem() throws Exception {
        int databaseSizeBeforeUpdate = triagemRepository.findAll().size();

        // Create the Triagem
        TriagemDTO triagemDTO = triagemMapper.toDto(triagem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTriagemMockMvc.perform(put("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(0)).save(triagem);
    }

    @Test
    @Transactional
    public void deleteTriagem() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);

        int databaseSizeBeforeDelete = triagemRepository.findAll().size();

        // Get the triagem
        restTriagemMockMvc.perform(delete("/api/triagems/{id}", triagem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(1)).deleteById(triagem.getId());
    }

    @Test
    @Transactional
    public void searchTriagem() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);
        when(mockTriagemSearchRepository.search(queryStringQuery("id:" + triagem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(triagem), PageRequest.of(0, 1), 1));
        // Search the triagem
        restTriagemMockMvc.perform(get("/api/_search/triagems?query=id:" + triagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(triagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoPaciente").value(hasItem(DEFAULT_NOME_DO_PACIENTE)))
            .andExpect(jsonPath("$.[*].pressaoArterial").value(hasItem(DEFAULT_PRESSAO_ARTERIAL.intValue())))
            .andExpect(jsonPath("$.[*].frequenciaCardiaca").value(hasItem(DEFAULT_FREQUENCIA_CARDIACA.intValue())))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.intValue())))
            .andExpect(jsonPath("$.[*].sinaisSintomas").value(hasItem(DEFAULT_SINAIS_SINTOMAS)))
            .andExpect(jsonPath("$.[*].horaDoAtendimento").value(hasItem(DEFAULT_HORA_DO_ATENDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataDoAtendimento").value(hasItem(DEFAULT_DATA_DO_ATENDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)))
            .andExpect(jsonPath("$.[*].descricaoQueixa").value(hasItem(DEFAULT_DESCRICAO_QUEIXA)))
            .andExpect(jsonPath("$.[*].vitimaDeAcidente").value(hasItem(DEFAULT_VITIMA_DE_ACIDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].removidoDeAmbulancia").value(hasItem(DEFAULT_REMOVIDO_DE_AMBULANCIA.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Triagem.class);
        Triagem triagem1 = new Triagem();
        triagem1.setId(1L);
        Triagem triagem2 = new Triagem();
        triagem2.setId(triagem1.getId());
        assertThat(triagem1).isEqualTo(triagem2);
        triagem2.setId(2L);
        assertThat(triagem1).isNotEqualTo(triagem2);
        triagem1.setId(null);
        assertThat(triagem1).isNotEqualTo(triagem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TriagemDTO.class);
        TriagemDTO triagemDTO1 = new TriagemDTO();
        triagemDTO1.setId(1L);
        TriagemDTO triagemDTO2 = new TriagemDTO();
        assertThat(triagemDTO1).isNotEqualTo(triagemDTO2);
        triagemDTO2.setId(triagemDTO1.getId());
        assertThat(triagemDTO1).isEqualTo(triagemDTO2);
        triagemDTO2.setId(2L);
        assertThat(triagemDTO1).isNotEqualTo(triagemDTO2);
        triagemDTO1.setId(null);
        assertThat(triagemDTO1).isNotEqualTo(triagemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(triagemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(triagemMapper.fromId(null)).isNull();
    }
}
