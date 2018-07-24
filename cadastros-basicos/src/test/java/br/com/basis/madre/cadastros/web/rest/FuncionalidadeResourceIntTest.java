package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import br.com.basis.madre.cadastros.domain.Funcionalidade;
import br.com.basis.madre.cadastros.repository.FuncionalidadeRepository;
import br.com.basis.madre.cadastros.service.FuncionalidadeService;
import br.com.basis.madre.cadastros.repository.search.FuncionalidadeSearchRepository;
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
import java.util.List;

import static br.com.basis.madre.cadastros.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FuncionalidadeResource REST controller.
 *
 * @see FuncionalidadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class FuncionalidadeResourceIntTest {

    private static final String DEFAULT_NM_FUNCIONALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NM_FUNCIONALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_CD_FUNCIONALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CD_FUNCIONALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ST_EXCLUIDO = "A";
    private static final String UPDATED_ST_EXCLUIDO = "B";

    @Autowired
    private FuncionalidadeRepository funcionalidadeRepository;

    @Autowired
    private FuncionalidadeService funcionalidadeService;

    @Autowired
    private FuncionalidadeSearchRepository funcionalidadeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFuncionalidadeMockMvc;

    private Funcionalidade funcionalidade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuncionalidadeResource funcionalidadeResource = new FuncionalidadeResource(funcionalidadeService);
        this.restFuncionalidadeMockMvc = MockMvcBuilders.standaloneSetup(funcionalidadeResource)
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
    public static Funcionalidade createEntity(EntityManager em) {
        Funcionalidade funcionalidade = new Funcionalidade()
            .nm_funcionalidade(DEFAULT_NM_FUNCIONALIDADE)
            .cd_funcionalidade(DEFAULT_CD_FUNCIONALIDADE)
            .st_excluido(DEFAULT_ST_EXCLUIDO);
        return funcionalidade;
    }

    @Before
    public void initTest() {
        funcionalidadeSearchRepository.deleteAll();
        funcionalidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuncionalidade() throws Exception {
        int databaseSizeBeforeCreate = funcionalidadeRepository.findAll().size();

        // Create the Funcionalidade
        restFuncionalidadeMockMvc.perform(post("/api/funcionalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionalidade)))
            .andExpect(status().isCreated());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll();
        assertThat(funcionalidades).hasSize(databaseSizeBeforeCreate + 1);
        Funcionalidade testFuncionalidade = funcionalidades.get(funcionalidades.size() - 1);
        assertThat(testFuncionalidade.getNm_funcionalidade()).isEqualTo(DEFAULT_NM_FUNCIONALIDADE);
        assertThat(testFuncionalidade.getCd_funcionalidade()).isEqualTo(DEFAULT_CD_FUNCIONALIDADE);
        assertThat(testFuncionalidade.getSt_excluido()).isEqualTo(DEFAULT_ST_EXCLUIDO);

        // Validate the Funcionalidade in Elasticsearch
        Funcionalidade funcionalidadeEs = funcionalidadeSearchRepository.findOne(testFuncionalidade.getId());
        assertThat(funcionalidadeEs).isEqualToIgnoringGivenFields(testFuncionalidade);
    }

    @Test
    @Transactional
    public void createFuncionalidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = funcionalidadeRepository.findAll().size();

        // Create the Funcionalidade with an existing ID
        funcionalidade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionalidadeMockMvc.perform(post("/api/funcionalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionalidade)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll();
        assertThat(funcionalidades).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNm_funcionalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionalidadeRepository.findAll().size();
        // set the field null
        funcionalidade.setNm_funcionalidade(null);

        // Create the Funcionalidade, which fails.

        restFuncionalidadeMockMvc.perform(post("/api/funcionalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionalidade)))
            .andExpect(status().isBadRequest());

        List<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll();
        assertThat(funcionalidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCd_funcionalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionalidadeRepository.findAll().size();
        // set the field null
        funcionalidade.setCd_funcionalidade(null);

        // Create the Funcionalidade, which fails.

        restFuncionalidadeMockMvc.perform(post("/api/funcionalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionalidade)))
            .andExpect(status().isBadRequest());

        List<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll();
        assertThat(funcionalidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSt_excluidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionalidadeRepository.findAll().size();
        // set the field null
        funcionalidade.setSt_excluido(null);

        // Create the Funcionalidade, which fails.

        restFuncionalidadeMockMvc.perform(post("/api/funcionalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionalidade)))
            .andExpect(status().isBadRequest());

        List<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll();
        assertThat(funcionalidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFuncionalidades() throws Exception {
        // Initialize the database
        funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidades
        restFuncionalidadeMockMvc.perform(get("/api/funcionalidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nm_funcionalidade").value(hasItem(DEFAULT_NM_FUNCIONALIDADE.toString())))
            .andExpect(jsonPath("$.[*].cd_funcionalidade").value(hasItem(DEFAULT_CD_FUNCIONALIDADE.toString())))
            .andExpect(jsonPath("$.[*].st_excluido").value(hasItem(DEFAULT_ST_EXCLUIDO.toString())));
    }

    @Test
    @Transactional
    public void getFuncionalidade() throws Exception {
        // Initialize the database
        funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get the funcionalidade
        restFuncionalidadeMockMvc.perform(get("/api/funcionalidades/{id}", funcionalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(funcionalidade.getId().intValue()))
            .andExpect(jsonPath("$.nm_funcionalidade").value(DEFAULT_NM_FUNCIONALIDADE.toString()))
            .andExpect(jsonPath("$.cd_funcionalidade").value(DEFAULT_CD_FUNCIONALIDADE.toString()))
            .andExpect(jsonPath("$.st_excluido").value(DEFAULT_ST_EXCLUIDO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFuncionalidade() throws Exception {
        // Get the funcionalidade
        restFuncionalidadeMockMvc.perform(get("/api/funcionalidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuncionalidade() throws Exception {
        // Initialize the database
        funcionalidadeService.save(funcionalidade);

        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();

        // Update the funcionalidade
        Funcionalidade updatedFuncionalidade = funcionalidadeRepository.findOne(funcionalidade.getId());
        // Disconnect from session so that the updates on updatedFuncionalidade are not directly saved in db
        em.detach(updatedFuncionalidade);
        updatedFuncionalidade
            .nm_funcionalidade(UPDATED_NM_FUNCIONALIDADE)
            .cd_funcionalidade(UPDATED_CD_FUNCIONALIDADE)
            .st_excluido(UPDATED_ST_EXCLUIDO);

        restFuncionalidadeMockMvc.perform(put("/api/funcionalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFuncionalidade)))
            .andExpect(status().isOk());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll();
        assertThat(funcionalidades).hasSize(databaseSizeBeforeUpdate);
        Funcionalidade testFuncionalidade = funcionalidades.get(funcionalidades.size() - 1);
        assertThat(testFuncionalidade.getNm_funcionalidade()).isEqualTo(UPDATED_NM_FUNCIONALIDADE);
        assertThat(testFuncionalidade.getCd_funcionalidade()).isEqualTo(UPDATED_CD_FUNCIONALIDADE);
        assertThat(testFuncionalidade.getSt_excluido()).isEqualTo(UPDATED_ST_EXCLUIDO);

        // Validate the Funcionalidade in Elasticsearch
        Funcionalidade funcionalidadeEs = funcionalidadeSearchRepository.findOne(testFuncionalidade.getId());
        assertThat(funcionalidadeEs).isEqualToIgnoringGivenFields(testFuncionalidade);
    }

    @Test
    @Transactional
    public void updateNonExistingFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();

        // Create the Funcionalidade

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFuncionalidadeMockMvc.perform(put("/api/funcionalidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funcionalidade)))
            .andExpect(status().isCreated());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll();
        assertThat(funcionalidades).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFuncionalidade() throws Exception {
        // Initialize the database
        funcionalidadeService.save(funcionalidade);

        int databaseSizeBeforeDelete = funcionalidadeRepository.findAll().size();

        // Get the funcionalidade
        restFuncionalidadeMockMvc.perform(delete("/api/funcionalidades/{id}", funcionalidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean funcionalidadeExistsInEs = funcionalidadeSearchRepository.exists(funcionalidade.getId());
        assertThat(funcionalidadeExistsInEs).isFalse();

        // Validate the database is empty
        List<Funcionalidade> funcionalidades = funcionalidadeRepository.findAll();
        assertThat(funcionalidades).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFuncionalidade() throws Exception {
        // Initialize the database
        funcionalidadeService.save(funcionalidade);

        // Search the funcionalidade
        restFuncionalidadeMockMvc.perform(get("/api/_search/funcionalidades?query=id:" + funcionalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nm_funcionalidade").value(hasItem(DEFAULT_NM_FUNCIONALIDADE.toString())))
            .andExpect(jsonPath("$.[*].cd_funcionalidade").value(hasItem(DEFAULT_CD_FUNCIONALIDADE.toString())))
            .andExpect(jsonPath("$.[*].st_excluido").value(hasItem(DEFAULT_ST_EXCLUIDO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionalidade.class);
        Funcionalidade funcionalidade1 = new Funcionalidade();
        funcionalidade1.setId(1L);
        Funcionalidade funcionalidade2 = new Funcionalidade();
        funcionalidade2.setId(funcionalidade1.getId());
        assertThat(funcionalidade1).isEqualTo(funcionalidade2);
        funcionalidade2.setId(2L);
        assertThat(funcionalidade1).isNotEqualTo(funcionalidade2);
        funcionalidade1.setId(null);
        assertThat(funcionalidade1).isNotEqualTo(funcionalidade2);
    }
}
