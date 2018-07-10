package br.com.basis.madre.pacientes.web.rest;

import br.com.basis.madre.pacientes.PacientesApp;

import br.com.basis.madre.pacientes.domain.Triagem;
import br.com.basis.madre.pacientes.repository.TriagemRepository;
import br.com.basis.madre.pacientes.service.TriagemService;
import br.com.basis.madre.pacientes.repository.search.TriagemSearchRepository;
import br.com.basis.madre.pacientes.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static br.com.basis.madre.pacientes.web.rest.TestUtil.sameInstant;
import static br.com.basis.madre.pacientes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TriagemResource REST controller.
 *
 * @see TriagemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PacientesApp.class)
public class TriagemResourceIntTest {

    private static final LocalDate DEFAULT_DATA_ATENDIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ATENDIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PRESSAO_ARTERIAL = 15;
    private static final Integer UPDATED_PRESSAO_ARTERIAL = 14;

    private static final Integer DEFAULT_FREQUENCIA_RESPIRATORIA = 15;
    private static final Integer UPDATED_FREQUENCIA_RESPIRATORIA = 14;

    private static final Integer DEFAULT_TEMPERATURA = 3;
    private static final Integer UPDATED_TEMPERATURA = 2;

    private static final Integer DEFAULT_PESO = 3;
    private static final Integer UPDATED_PESO = 2;

    private static final ZonedDateTime DEFAULT_HORA_ATENDIMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_ATENDIMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_FREQUENCIA_CARDIACA = 10;
    private static final Integer UPDATED_FREQUENCIA_CARDIACA = 9;

    private static final Integer DEFAULT_GLICEMIA = 5;
    private static final Integer UPDATED_GLICEMIA = 4;

    private static final Integer DEFAULT_SATURACAO = 10;
    private static final Integer UPDATED_SATURACAO = 9;

    private static final Float DEFAULT_ALTURA = 4F;
    private static final Float UPDATED_ALTURA = 3F;

    private static final String DEFAULT_MEDICACAO_CONTINUA = "AAAAAAAAAA";
    private static final String UPDATED_MEDICACAO_CONTINUA = "BBBBBBBBBB";

    private static final String DEFAULT_FERIDA_LESAO = "AAAAAAAAAA";
    private static final String UPDATED_FERIDA_LESAO = "BBBBBBBBBB";

    private static final String DEFAULT_ALERGIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALERGIAS = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_SINTOMA_ALERTA = "AAAAAAAAAA";
    private static final String UPDATED_SINTOMA_ALERTA = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORICO_SAUDE = "AAAAAAAAAA";
    private static final String UPDATED_HISTORICO_SAUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_GERAL = "BBBBBBBBBB";

    @Autowired
    private TriagemRepository triagemRepository;

    @Autowired
    private TriagemService triagemService;

    @Autowired
    private TriagemSearchRepository triagemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTriagemMockMvc;

    private Triagem triagem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TriagemResource triagemResource = new TriagemResource(triagemService);
        this.restTriagemMockMvc = MockMvcBuilders.standaloneSetup(triagemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Triagem createEntity(EntityManager em) {
        Triagem triagem = new Triagem()
            .dataAtendimento(DEFAULT_DATA_ATENDIMENTO)
            .pressaoArterial(DEFAULT_PRESSAO_ARTERIAL)
            .frequenciaRespiratoria(DEFAULT_FREQUENCIA_RESPIRATORIA)
            .temperatura(DEFAULT_TEMPERATURA)
            .peso(DEFAULT_PESO)
            .horaAtendimento(DEFAULT_HORA_ATENDIMENTO)
            .frequenciaCardiaca(DEFAULT_FREQUENCIA_CARDIACA)
            .glicemia(DEFAULT_GLICEMIA)
            .saturacao(DEFAULT_SATURACAO)
            .altura(DEFAULT_ALTURA)
            .medicacaoContinua(DEFAULT_MEDICACAO_CONTINUA)
            .feridaLesao(DEFAULT_FERIDA_LESAO)
            .alergias(DEFAULT_ALERGIAS)
            .estadoPaciente(DEFAULT_ESTADO_PACIENTE)
            .sintomaAlerta(DEFAULT_SINTOMA_ALERTA)
            .historicoSaude(DEFAULT_HISTORICO_SAUDE)
            .estadoGeral(DEFAULT_ESTADO_GERAL);
        return triagem;
    }

    @Before
    public void initTest() {
        triagemSearchRepository.deleteAll();
        triagem = createEntity(em);
    }

    @Test
    @Transactional
    public void createTriagem() throws Exception {
        int databaseSizeBeforeCreate = triagemRepository.findAll().size();

        // Create the Triagem
        restTriagemMockMvc.perform(post("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagem)))
            .andExpect(status().isCreated());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeCreate + 1);
        Triagem testTriagem = triagemList.get(triagemList.size() - 1);
        assertThat(testTriagem.getDataAtendimento()).isEqualTo(DEFAULT_DATA_ATENDIMENTO);
        assertThat(testTriagem.getPressaoArterial()).isEqualTo(DEFAULT_PRESSAO_ARTERIAL);
        assertThat(testTriagem.getFrequenciaRespiratoria()).isEqualTo(DEFAULT_FREQUENCIA_RESPIRATORIA);
        assertThat(testTriagem.getTemperatura()).isEqualTo(DEFAULT_TEMPERATURA);
        assertThat(testTriagem.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testTriagem.getHoraAtendimento()).isEqualTo(DEFAULT_HORA_ATENDIMENTO);
        assertThat(testTriagem.getFrequenciaCardiaca()).isEqualTo(DEFAULT_FREQUENCIA_CARDIACA);
        assertThat(testTriagem.getGlicemia()).isEqualTo(DEFAULT_GLICEMIA);
        assertThat(testTriagem.getSaturacao()).isEqualTo(DEFAULT_SATURACAO);
        assertThat(testTriagem.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testTriagem.getMedicacaoContinua()).isEqualTo(DEFAULT_MEDICACAO_CONTINUA);
        assertThat(testTriagem.getFeridaLesao()).isEqualTo(DEFAULT_FERIDA_LESAO);
        assertThat(testTriagem.getAlergias()).isEqualTo(DEFAULT_ALERGIAS);
        assertThat(testTriagem.getEstadoPaciente()).isEqualTo(DEFAULT_ESTADO_PACIENTE);
        assertThat(testTriagem.getSintomaAlerta()).isEqualTo(DEFAULT_SINTOMA_ALERTA);
        assertThat(testTriagem.getHistoricoSaude()).isEqualTo(DEFAULT_HISTORICO_SAUDE);
        assertThat(testTriagem.getEstadoGeral()).isEqualTo(DEFAULT_ESTADO_GERAL);

        // Validate the Triagem in Elasticsearch
        Triagem triagemEs = triagemSearchRepository.findOne(testTriagem.getId());
        assertThat(testTriagem.getHoraAtendimento()).isEqualTo(testTriagem.getHoraAtendimento());
        assertThat(triagemEs).isEqualToIgnoringGivenFields(testTriagem, "horaAtendimento");
    }

    @Test
    @Transactional
    public void createTriagemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = triagemRepository.findAll().size();

        // Create the Triagem with an existing ID
        triagem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTriagemMockMvc.perform(post("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagem)))
            .andExpect(status().isBadRequest());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeCreate);
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
            .andExpect(jsonPath("$.[*].dataAtendimento").value(hasItem(DEFAULT_DATA_ATENDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].pressaoArterial").value(hasItem(DEFAULT_PRESSAO_ARTERIAL)))
            .andExpect(jsonPath("$.[*].frequenciaRespiratoria").value(hasItem(DEFAULT_FREQUENCIA_RESPIRATORIA)))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)))
            .andExpect(jsonPath("$.[*].horaAtendimento").value(hasItem(sameInstant(DEFAULT_HORA_ATENDIMENTO))))
            .andExpect(jsonPath("$.[*].frequenciaCardiaca").value(hasItem(DEFAULT_FREQUENCIA_CARDIACA)))
            .andExpect(jsonPath("$.[*].glicemia").value(hasItem(DEFAULT_GLICEMIA)))
            .andExpect(jsonPath("$.[*].saturacao").value(hasItem(DEFAULT_SATURACAO)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].medicacaoContinua").value(hasItem(DEFAULT_MEDICACAO_CONTINUA.toString())))
            .andExpect(jsonPath("$.[*].feridaLesao").value(hasItem(DEFAULT_FERIDA_LESAO.toString())))
            .andExpect(jsonPath("$.[*].alergias").value(hasItem(DEFAULT_ALERGIAS.toString())))
            .andExpect(jsonPath("$.[*].estadoPaciente").value(hasItem(DEFAULT_ESTADO_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].sintomaAlerta").value(hasItem(DEFAULT_SINTOMA_ALERTA.toString())))
            .andExpect(jsonPath("$.[*].historicoSaude").value(hasItem(DEFAULT_HISTORICO_SAUDE.toString())))
            .andExpect(jsonPath("$.[*].estadoGeral").value(hasItem(DEFAULT_ESTADO_GERAL.toString())));
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
            .andExpect(jsonPath("$.dataAtendimento").value(DEFAULT_DATA_ATENDIMENTO.toString()))
            .andExpect(jsonPath("$.pressaoArterial").value(DEFAULT_PRESSAO_ARTERIAL))
            .andExpect(jsonPath("$.frequenciaRespiratoria").value(DEFAULT_FREQUENCIA_RESPIRATORIA))
            .andExpect(jsonPath("$.temperatura").value(DEFAULT_TEMPERATURA))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO))
            .andExpect(jsonPath("$.horaAtendimento").value(sameInstant(DEFAULT_HORA_ATENDIMENTO)))
            .andExpect(jsonPath("$.frequenciaCardiaca").value(DEFAULT_FREQUENCIA_CARDIACA))
            .andExpect(jsonPath("$.glicemia").value(DEFAULT_GLICEMIA))
            .andExpect(jsonPath("$.saturacao").value(DEFAULT_SATURACAO))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.doubleValue()))
            .andExpect(jsonPath("$.medicacaoContinua").value(DEFAULT_MEDICACAO_CONTINUA.toString()))
            .andExpect(jsonPath("$.feridaLesao").value(DEFAULT_FERIDA_LESAO.toString()))
            .andExpect(jsonPath("$.alergias").value(DEFAULT_ALERGIAS.toString()))
            .andExpect(jsonPath("$.estadoPaciente").value(DEFAULT_ESTADO_PACIENTE.toString()))
            .andExpect(jsonPath("$.sintomaAlerta").value(DEFAULT_SINTOMA_ALERTA.toString()))
            .andExpect(jsonPath("$.historicoSaude").value(DEFAULT_HISTORICO_SAUDE.toString()))
            .andExpect(jsonPath("$.estadoGeral").value(DEFAULT_ESTADO_GERAL.toString()));
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
        triagemService.save(triagem);

        int databaseSizeBeforeUpdate = triagemRepository.findAll().size();

        // Update the triagem
        Triagem updatedTriagem = triagemRepository.findOne(triagem.getId());
        // Disconnect from session so that the updates on updatedTriagem are not directly saved in db
        em.detach(updatedTriagem);
        updatedTriagem
            .dataAtendimento(UPDATED_DATA_ATENDIMENTO)
            .pressaoArterial(UPDATED_PRESSAO_ARTERIAL)
            .frequenciaRespiratoria(UPDATED_FREQUENCIA_RESPIRATORIA)
            .temperatura(UPDATED_TEMPERATURA)
            .peso(UPDATED_PESO)
            .horaAtendimento(UPDATED_HORA_ATENDIMENTO)
            .frequenciaCardiaca(UPDATED_FREQUENCIA_CARDIACA)
            .glicemia(UPDATED_GLICEMIA)
            .saturacao(UPDATED_SATURACAO)
            .altura(UPDATED_ALTURA)
            .medicacaoContinua(UPDATED_MEDICACAO_CONTINUA)
            .feridaLesao(UPDATED_FERIDA_LESAO)
            .alergias(UPDATED_ALERGIAS)
            .estadoPaciente(UPDATED_ESTADO_PACIENTE)
            .sintomaAlerta(UPDATED_SINTOMA_ALERTA)
            .historicoSaude(UPDATED_HISTORICO_SAUDE)
            .estadoGeral(UPDATED_ESTADO_GERAL);

        restTriagemMockMvc.perform(put("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTriagem)))
            .andExpect(status().isOk());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeUpdate);
        Triagem testTriagem = triagemList.get(triagemList.size() - 1);
        assertThat(testTriagem.getDataAtendimento()).isEqualTo(UPDATED_DATA_ATENDIMENTO);
        assertThat(testTriagem.getPressaoArterial()).isEqualTo(UPDATED_PRESSAO_ARTERIAL);
        assertThat(testTriagem.getFrequenciaRespiratoria()).isEqualTo(UPDATED_FREQUENCIA_RESPIRATORIA);
        assertThat(testTriagem.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
        assertThat(testTriagem.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testTriagem.getHoraAtendimento()).isEqualTo(UPDATED_HORA_ATENDIMENTO);
        assertThat(testTriagem.getFrequenciaCardiaca()).isEqualTo(UPDATED_FREQUENCIA_CARDIACA);
        assertThat(testTriagem.getGlicemia()).isEqualTo(UPDATED_GLICEMIA);
        assertThat(testTriagem.getSaturacao()).isEqualTo(UPDATED_SATURACAO);
        assertThat(testTriagem.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testTriagem.getMedicacaoContinua()).isEqualTo(UPDATED_MEDICACAO_CONTINUA);
        assertThat(testTriagem.getFeridaLesao()).isEqualTo(UPDATED_FERIDA_LESAO);
        assertThat(testTriagem.getAlergias()).isEqualTo(UPDATED_ALERGIAS);
        assertThat(testTriagem.getEstadoPaciente()).isEqualTo(UPDATED_ESTADO_PACIENTE);
        assertThat(testTriagem.getSintomaAlerta()).isEqualTo(UPDATED_SINTOMA_ALERTA);
        assertThat(testTriagem.getHistoricoSaude()).isEqualTo(UPDATED_HISTORICO_SAUDE);
        assertThat(testTriagem.getEstadoGeral()).isEqualTo(UPDATED_ESTADO_GERAL);

        // Validate the Triagem in Elasticsearch
        Triagem triagemEs = triagemSearchRepository.findOne(testTriagem.getId());
        assertThat(testTriagem.getHoraAtendimento()).isEqualTo(testTriagem.getHoraAtendimento());
        assertThat(triagemEs).isEqualToIgnoringGivenFields(testTriagem, "horaAtendimento");
    }

    @Test
    @Transactional
    public void updateNonExistingTriagem() throws Exception {
        int databaseSizeBeforeUpdate = triagemRepository.findAll().size();

        // Create the Triagem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTriagemMockMvc.perform(put("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagem)))
            .andExpect(status().isCreated());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTriagem() throws Exception {
        // Initialize the database
        triagemService.save(triagem);

        int databaseSizeBeforeDelete = triagemRepository.findAll().size();

        // Get the triagem
        restTriagemMockMvc.perform(delete("/api/triagems/{id}", triagem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean triagemExistsInEs = triagemSearchRepository.exists(triagem.getId());
        assertThat(triagemExistsInEs).isFalse();

        // Validate the database is empty
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTriagem() throws Exception {
        // Initialize the database
        triagemService.save(triagem);

        // Search the triagem
        restTriagemMockMvc.perform(get("/api/_search/triagems?query=id:" + triagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(triagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAtendimento").value(hasItem(DEFAULT_DATA_ATENDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].pressaoArterial").value(hasItem(DEFAULT_PRESSAO_ARTERIAL)))
            .andExpect(jsonPath("$.[*].frequenciaRespiratoria").value(hasItem(DEFAULT_FREQUENCIA_RESPIRATORIA)))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)))
            .andExpect(jsonPath("$.[*].horaAtendimento").value(hasItem(sameInstant(DEFAULT_HORA_ATENDIMENTO))))
            .andExpect(jsonPath("$.[*].frequenciaCardiaca").value(hasItem(DEFAULT_FREQUENCIA_CARDIACA)))
            .andExpect(jsonPath("$.[*].glicemia").value(hasItem(DEFAULT_GLICEMIA)))
            .andExpect(jsonPath("$.[*].saturacao").value(hasItem(DEFAULT_SATURACAO)))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].medicacaoContinua").value(hasItem(DEFAULT_MEDICACAO_CONTINUA.toString())))
            .andExpect(jsonPath("$.[*].feridaLesao").value(hasItem(DEFAULT_FERIDA_LESAO.toString())))
            .andExpect(jsonPath("$.[*].alergias").value(hasItem(DEFAULT_ALERGIAS.toString())))
            .andExpect(jsonPath("$.[*].estadoPaciente").value(hasItem(DEFAULT_ESTADO_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].sintomaAlerta").value(hasItem(DEFAULT_SINTOMA_ALERTA.toString())))
            .andExpect(jsonPath("$.[*].historicoSaude").value(hasItem(DEFAULT_HISTORICO_SAUDE.toString())))
            .andExpect(jsonPath("$.[*].estadoGeral").value(hasItem(DEFAULT_ESTADO_GERAL.toString())));
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
}
