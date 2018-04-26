package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.repository.PreCadastroRepository;
import br.com.basis.madre.cadastros.service.PreCadastroService;
import br.com.basis.madre.cadastros.repository.search.PreCadastroSearchRepository;
import br.com.basis.madre.cadastros.web.rest.errors.ExceptionTranslator;

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
import java.time.ZoneId;
import java.util.List;

import static br.com.basis.madre.cadastros.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PreCadastroResource REST controller.
 *
 * @see PreCadastroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class PreCadastroResourceIntTest {

    private static final String DEFAULT_NOME_DO_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_DA_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DA_MAE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DE_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_N_CARTAO_SUS = "AAAAAAAAAAAAAAA";
    private static final String UPDATED_N_CARTAO_SUS = "BBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private PreCadastroRepository preCadastroRepository;

    @Autowired
    private PreCadastroService preCadastroService;

    @Autowired
    private PreCadastroSearchRepository preCadastroSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPreCadastroMockMvc;

    private PreCadastro preCadastro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreCadastroResource preCadastroResource = new PreCadastroResource(preCadastroService);
        this.restPreCadastroMockMvc = MockMvcBuilders.standaloneSetup(preCadastroResource)
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
    public static PreCadastro createEntity(EntityManager em) {
        PreCadastro preCadastro = new PreCadastro()
            .nome_do_paciente(DEFAULT_NOME_DO_PACIENTE)
            .nome_social(DEFAULT_NOME_SOCIAL)
            .nome_da_mae(DEFAULT_NOME_DA_MAE)
            .data_de_nascimento(DEFAULT_DATA_DE_NASCIMENTO)
            .n_cartao_sus(DEFAULT_N_CARTAO_SUS)
            .ativo(DEFAULT_ATIVO);
        return preCadastro;
    }

    @Before
    public void initTest() {
        preCadastroSearchRepository.deleteAll();
        preCadastro = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreCadastro() throws Exception {
        int databaseSizeBeforeCreate = preCadastroRepository.findAll().size();

        // Create the PreCadastro
        restPreCadastroMockMvc.perform(post("/api/pre-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastro)))
            .andExpect(status().isCreated());

        // Validate the PreCadastro in the database
        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeCreate + 1);
        PreCadastro testPreCadastro = preCadastroList.get(preCadastroList.size() - 1);
        assertThat(testPreCadastro.getNome_do_paciente()).isEqualTo(DEFAULT_NOME_DO_PACIENTE);
        assertThat(testPreCadastro.getNome_social()).isEqualTo(DEFAULT_NOME_SOCIAL);
        assertThat(testPreCadastro.getNome_da_mae()).isEqualTo(DEFAULT_NOME_DA_MAE);
        assertThat(testPreCadastro.getData_de_nascimento()).isEqualTo(DEFAULT_DATA_DE_NASCIMENTO);
        assertThat(testPreCadastro.getn_cartao_sus()).isEqualTo(DEFAULT_N_CARTAO_SUS);
        assertThat(testPreCadastro.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the PreCadastro in Elasticsearch
        PreCadastro preCadastroEs = preCadastroSearchRepository.findOne(testPreCadastro.getId());
        assertThat(preCadastroEs).isEqualToIgnoringGivenFields(testPreCadastro);
    }

    @Test
    @Transactional
    public void createPreCadastroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preCadastroRepository.findAll().size();

        // Create the PreCadastro with an existing ID
        preCadastro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreCadastroMockMvc.perform(post("/api/pre-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastro)))
            .andExpect(status().isBadRequest());

        // Validate the PreCadastro in the database
        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNome_do_pacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroRepository.findAll().size();
        // set the field null
        preCadastro.setNome_do_paciente(null);

        // Create the PreCadastro, which fails.

        restPreCadastroMockMvc.perform(post("/api/pre-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastro)))
            .andExpect(status().isBadRequest());

        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNome_da_maeIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroRepository.findAll().size();
        // set the field null
        preCadastro.setNome_da_mae(null);

        // Create the PreCadastro, which fails.

        restPreCadastroMockMvc.perform(post("/api/pre-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastro)))
            .andExpect(status().isBadRequest());

        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkData_de_nascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroRepository.findAll().size();
        // set the field null
        preCadastro.setData_de_nascimento(null);

        // Create the PreCadastro, which fails.

        restPreCadastroMockMvc.perform(post("/api/pre-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastro)))
            .andExpect(status().isBadRequest());

        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = preCadastroRepository.findAll().size();
        // set the field null
        preCadastro.setAtivo(null);

        // Create the PreCadastro, which fails.

        restPreCadastroMockMvc.perform(post("/api/pre-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastro)))
            .andExpect(status().isBadRequest());

        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPreCadastros() throws Exception {
        // Initialize the database
        preCadastroRepository.saveAndFlush(preCadastro);

        // Get all the preCadastroList
        restPreCadastroMockMvc.perform(get("/api/pre-cadastros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preCadastro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome_do_paciente").value(hasItem(DEFAULT_NOME_DO_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].nome_social").value(hasItem(DEFAULT_NOME_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].nome_da_mae").value(hasItem(DEFAULT_NOME_DA_MAE.toString())))
            .andExpect(jsonPath("$.[*].data_de_nascimento").value(hasItem(DEFAULT_DATA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].n_cartao_sus").value(hasItem(DEFAULT_N_CARTAO_SUS.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getPreCadastro() throws Exception {
        // Initialize the database
        preCadastroRepository.saveAndFlush(preCadastro);

        // Get the preCadastro
        restPreCadastroMockMvc.perform(get("/api/pre-cadastros/{id}", preCadastro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preCadastro.getId().intValue()))
            .andExpect(jsonPath("$.nome_do_paciente").value(DEFAULT_NOME_DO_PACIENTE.toString()))
            .andExpect(jsonPath("$.nome_social").value(DEFAULT_NOME_SOCIAL.toString()))
            .andExpect(jsonPath("$.nome_da_mae").value(DEFAULT_NOME_DA_MAE.toString()))
            .andExpect(jsonPath("$.data_de_nascimento").value(DEFAULT_DATA_DE_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.n_cartao_sus").value(DEFAULT_N_CARTAO_SUS.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPreCadastro() throws Exception {
        // Get the preCadastro
        restPreCadastroMockMvc.perform(get("/api/pre-cadastros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreCadastro() throws Exception {
        // Initialize the database
        preCadastroService.save(preCadastro);

        int databaseSizeBeforeUpdate = preCadastroRepository.findAll().size();

        // Update the preCadastro
        PreCadastro updatedPreCadastro = preCadastroRepository.findOne(preCadastro.getId());
        // Disconnect from session so that the updates on updatedPreCadastro are not directly saved in db
        em.detach(updatedPreCadastro);
        updatedPreCadastro
            .nome_do_paciente(UPDATED_NOME_DO_PACIENTE)
            .nome_social(UPDATED_NOME_SOCIAL)
            .nome_da_mae(UPDATED_NOME_DA_MAE)
            .data_de_nascimento(UPDATED_DATA_DE_NASCIMENTO)
            .n_cartao_sus(UPDATED_N_CARTAO_SUS)
            .ativo(UPDATED_ATIVO);

        restPreCadastroMockMvc.perform(put("/api/pre-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreCadastro)))
            .andExpect(status().isOk());

        // Validate the PreCadastro in the database
        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeUpdate);
        PreCadastro testPreCadastro = preCadastroList.get(preCadastroList.size() - 1);
        assertThat(testPreCadastro.getNome_do_paciente()).isEqualTo(UPDATED_NOME_DO_PACIENTE);
        assertThat(testPreCadastro.getNome_social()).isEqualTo(UPDATED_NOME_SOCIAL);
        assertThat(testPreCadastro.getNome_da_mae()).isEqualTo(UPDATED_NOME_DA_MAE);
        assertThat(testPreCadastro.getData_de_nascimento()).isEqualTo(UPDATED_DATA_DE_NASCIMENTO);
        assertThat(testPreCadastro.getn_cartao_sus()).isEqualTo(UPDATED_N_CARTAO_SUS);
        assertThat(testPreCadastro.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the PreCadastro in Elasticsearch
        PreCadastro preCadastroEs = preCadastroSearchRepository.findOne(testPreCadastro.getId());
        assertThat(preCadastroEs).isEqualToIgnoringGivenFields(testPreCadastro);
    }

    @Test
    @Transactional
    public void updateNonExistingPreCadastro() throws Exception {
        int databaseSizeBeforeUpdate = preCadastroRepository.findAll().size();

        // Create the PreCadastro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPreCadastroMockMvc.perform(put("/api/pre-cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preCadastro)))
            .andExpect(status().isCreated());

        // Validate the PreCadastro in the database
        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePreCadastro() throws Exception {
        // Initialize the database
        preCadastroService.save(preCadastro);

        int databaseSizeBeforeDelete = preCadastroRepository.findAll().size();

        // Get the preCadastro
        restPreCadastroMockMvc.perform(delete("/api/pre-cadastros/{id}", preCadastro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean preCadastroExistsInEs = preCadastroSearchRepository.exists(preCadastro.getId());
        assertThat(preCadastroExistsInEs).isFalse();

        // Validate the database is empty
        List<PreCadastro> preCadastroList = preCadastroRepository.findAll();
        assertThat(preCadastroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPreCadastro() throws Exception {
        // Initialize the database
        preCadastroService.save(preCadastro);

        // Search the preCadastro
        restPreCadastroMockMvc.perform(get("/api/_search/pre-cadastros?query=id:" + preCadastro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preCadastro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome_do_paciente").value(hasItem(DEFAULT_NOME_DO_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].nome_social").value(hasItem(DEFAULT_NOME_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].nome_da_mae").value(hasItem(DEFAULT_NOME_DA_MAE.toString())))
            .andExpect(jsonPath("$.[*].data_de_nascimento").value(hasItem(DEFAULT_DATA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].n_cartao_sus").value(hasItem(DEFAULT_N_CARTAO_SUS.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreCadastro.class);
        PreCadastro preCadastro1 = new PreCadastro();
        preCadastro1.setId(1L);
        PreCadastro preCadastro2 = new PreCadastro();
        preCadastro2.setId(preCadastro1.getId());
        assertThat(preCadastro1).isEqualTo(preCadastro2);
        preCadastro2.setId(2L);
        assertThat(preCadastro1).isNotEqualTo(preCadastro2);
        preCadastro1.setId(null);
        assertThat(preCadastro1).isNotEqualTo(preCadastro2);
    }
}
