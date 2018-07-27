package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import br.com.basis.madre.cadastros.domain.Perfil_funcionalidade_acao;
import br.com.basis.madre.cadastros.repository.Perfil_funcionalidade_acaoRepository;
import br.com.basis.madre.cadastros.service.Perfil_funcionalidade_acaoService;
import br.com.basis.madre.cadastros.repository.search.Perfil_funcionalidade_acaoSearchRepository;
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
 * Test class for the Perfil_funcionalidade_acaoResource REST controller.
 *
 * @see Perfil_funcionalidade_acaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class Perfil_funcionalidade_acaoResourceIntTest {

    private static final Integer DEFAULT_ID_PERFIL = 1;
    private static final Integer UPDATED_ID_PERFIL = 2;

    private static final Integer DEFAULT_ID_FUNCIONALIDADE_ACAO = 1;
    private static final Integer UPDATED_ID_FUNCIONALIDADE_ACAO = 2;

    @Autowired
    private Perfil_funcionalidade_acaoRepository perfil_funcionalidade_acaoRepository;

    @Autowired
    private Perfil_funcionalidade_acaoService perfil_funcionalidade_acaoService;

    @Autowired
    private Perfil_funcionalidade_acaoSearchRepository perfil_funcionalidade_acaoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPerfil_funcionalidade_acaoMockMvc;

    private Perfil_funcionalidade_acao perfil_funcionalidade_acao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Perfil_funcionalidade_acaoResource perfil_funcionalidade_acaoResource = new Perfil_funcionalidade_acaoResource(perfil_funcionalidade_acaoService);
        this.restPerfil_funcionalidade_acaoMockMvc = MockMvcBuilders.standaloneSetup(perfil_funcionalidade_acaoResource)
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
    public static Perfil_funcionalidade_acao createEntity(EntityManager em) {
        Perfil_funcionalidade_acao perfil_funcionalidade_acao = new Perfil_funcionalidade_acao()
            .id_perfil(DEFAULT_ID_PERFIL)
            .id_funcionalidade_acao(DEFAULT_ID_FUNCIONALIDADE_ACAO);
        return perfil_funcionalidade_acao;
    }

    @Before
    public void initTest() {
        perfil_funcionalidade_acaoSearchRepository.deleteAll();
        perfil_funcionalidade_acao = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfil_funcionalidade_acao() throws Exception {
        int databaseSizeBeforeCreate = perfil_funcionalidade_acaoRepository.findAll().size();

        // Create the Perfil_funcionalidade_acao
        restPerfil_funcionalidade_acaoMockMvc.perform(post("/api/perfil-funcionalidade-acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil_funcionalidade_acao)))
            .andExpect(status().isCreated());

        // Validate the Perfil_funcionalidade_acao in the database
        List<Perfil_funcionalidade_acao> perfil_funcionalidade_acaoList = perfil_funcionalidade_acaoRepository.findAll();
        assertThat(perfil_funcionalidade_acaoList).hasSize(databaseSizeBeforeCreate + 1);
        Perfil_funcionalidade_acao testPerfil_funcionalidade_acao = perfil_funcionalidade_acaoList.get(perfil_funcionalidade_acaoList.size() - 1);
        assertThat(testPerfil_funcionalidade_acao.getId_perfil()).isEqualTo(DEFAULT_ID_PERFIL);
        assertThat(testPerfil_funcionalidade_acao.getId_funcionalidade_acao()).isEqualTo(DEFAULT_ID_FUNCIONALIDADE_ACAO);

        // Validate the Perfil_funcionalidade_acao in Elasticsearch
        Perfil_funcionalidade_acao perfil_funcionalidade_acaoEs = perfil_funcionalidade_acaoSearchRepository.findOne(testPerfil_funcionalidade_acao.getId());
        assertThat(perfil_funcionalidade_acaoEs).isEqualToIgnoringGivenFields(testPerfil_funcionalidade_acao);
    }

    @Test
    @Transactional
    public void createPerfil_funcionalidade_acaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfil_funcionalidade_acaoRepository.findAll().size();

        // Create the Perfil_funcionalidade_acao with an existing ID
        perfil_funcionalidade_acao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfil_funcionalidade_acaoMockMvc.perform(post("/api/perfil-funcionalidade-acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil_funcionalidade_acao)))
            .andExpect(status().isBadRequest());

        // Validate the Perfil_funcionalidade_acao in the database
        List<Perfil_funcionalidade_acao> perfil_funcionalidade_acaoList = perfil_funcionalidade_acaoRepository.findAll();
        assertThat(perfil_funcionalidade_acaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkId_perfilIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfil_funcionalidade_acaoRepository.findAll().size();
        // set the field null
        perfil_funcionalidade_acao.setId_perfil(null);

        // Create the Perfil_funcionalidade_acao, which fails.

        restPerfil_funcionalidade_acaoMockMvc.perform(post("/api/perfil-funcionalidade-acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil_funcionalidade_acao)))
            .andExpect(status().isBadRequest());

        List<Perfil_funcionalidade_acao> perfil_funcionalidade_acaoList = perfil_funcionalidade_acaoRepository.findAll();
        assertThat(perfil_funcionalidade_acaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkId_funcionalidade_acaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfil_funcionalidade_acaoRepository.findAll().size();
        // set the field null
        perfil_funcionalidade_acao.setId_funcionalidade_acao(null);

        // Create the Perfil_funcionalidade_acao, which fails.

        restPerfil_funcionalidade_acaoMockMvc.perform(post("/api/perfil-funcionalidade-acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil_funcionalidade_acao)))
            .andExpect(status().isBadRequest());

        List<Perfil_funcionalidade_acao> perfil_funcionalidade_acaoList = perfil_funcionalidade_acaoRepository.findAll();
        assertThat(perfil_funcionalidade_acaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerfil_funcionalidade_acaos() throws Exception {
        // Initialize the database
        perfil_funcionalidade_acaoRepository.saveAndFlush(perfil_funcionalidade_acao);

        // Get all the perfil_funcionalidade_acaoList
        restPerfil_funcionalidade_acaoMockMvc.perform(get("/api/perfil-funcionalidade-acaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil_funcionalidade_acao.getId().intValue())))
            .andExpect(jsonPath("$.[*].id_perfil").value(hasItem(DEFAULT_ID_PERFIL)))
            .andExpect(jsonPath("$.[*].id_funcionalidade_acao").value(hasItem(DEFAULT_ID_FUNCIONALIDADE_ACAO)));
    }

    @Test
    @Transactional
    public void getPerfil_funcionalidade_acao() throws Exception {
        // Initialize the database
        perfil_funcionalidade_acaoRepository.saveAndFlush(perfil_funcionalidade_acao);

        // Get the perfil_funcionalidade_acao
        restPerfil_funcionalidade_acaoMockMvc.perform(get("/api/perfil-funcionalidade-acaos/{id}", perfil_funcionalidade_acao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perfil_funcionalidade_acao.getId().intValue()))
            .andExpect(jsonPath("$.id_perfil").value(DEFAULT_ID_PERFIL))
            .andExpect(jsonPath("$.id_funcionalidade_acao").value(DEFAULT_ID_FUNCIONALIDADE_ACAO));
    }

    @Test
    @Transactional
    public void getNonExistingPerfil_funcionalidade_acao() throws Exception {
        // Get the perfil_funcionalidade_acao
        restPerfil_funcionalidade_acaoMockMvc.perform(get("/api/perfil-funcionalidade-acaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfil_funcionalidade_acao() throws Exception {
        // Initialize the database
        perfil_funcionalidade_acaoService.save(perfil_funcionalidade_acao);

        int databaseSizeBeforeUpdate = perfil_funcionalidade_acaoRepository.findAll().size();

        // Update the perfil_funcionalidade_acao
        Perfil_funcionalidade_acao updatedPerfil_funcionalidade_acao = perfil_funcionalidade_acaoRepository.findOne(perfil_funcionalidade_acao.getId());
        // Disconnect from session so that the updates on updatedPerfil_funcionalidade_acao are not directly saved in db
        em.detach(updatedPerfil_funcionalidade_acao);
        updatedPerfil_funcionalidade_acao
            .id_perfil(UPDATED_ID_PERFIL)
            .id_funcionalidade_acao(UPDATED_ID_FUNCIONALIDADE_ACAO);

        restPerfil_funcionalidade_acaoMockMvc.perform(put("/api/perfil-funcionalidade-acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerfil_funcionalidade_acao)))
            .andExpect(status().isOk());

        // Validate the Perfil_funcionalidade_acao in the database
        List<Perfil_funcionalidade_acao> perfil_funcionalidade_acaoList = perfil_funcionalidade_acaoRepository.findAll();
        assertThat(perfil_funcionalidade_acaoList).hasSize(databaseSizeBeforeUpdate);
        Perfil_funcionalidade_acao testPerfil_funcionalidade_acao = perfil_funcionalidade_acaoList.get(perfil_funcionalidade_acaoList.size() - 1);
        assertThat(testPerfil_funcionalidade_acao.getId_perfil()).isEqualTo(UPDATED_ID_PERFIL);
        assertThat(testPerfil_funcionalidade_acao.getId_funcionalidade_acao()).isEqualTo(UPDATED_ID_FUNCIONALIDADE_ACAO);

        // Validate the Perfil_funcionalidade_acao in Elasticsearch
        Perfil_funcionalidade_acao perfil_funcionalidade_acaoEs = perfil_funcionalidade_acaoSearchRepository.findOne(testPerfil_funcionalidade_acao.getId());
        assertThat(perfil_funcionalidade_acaoEs).isEqualToIgnoringGivenFields(testPerfil_funcionalidade_acao);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfil_funcionalidade_acao() throws Exception {
        int databaseSizeBeforeUpdate = perfil_funcionalidade_acaoRepository.findAll().size();

        // Create the Perfil_funcionalidade_acao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerfil_funcionalidade_acaoMockMvc.perform(put("/api/perfil-funcionalidade-acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil_funcionalidade_acao)))
            .andExpect(status().isCreated());

        // Validate the Perfil_funcionalidade_acao in the database
        List<Perfil_funcionalidade_acao> perfil_funcionalidade_acaoList = perfil_funcionalidade_acaoRepository.findAll();
        assertThat(perfil_funcionalidade_acaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerfil_funcionalidade_acao() throws Exception {
        // Initialize the database
        perfil_funcionalidade_acaoService.save(perfil_funcionalidade_acao);

        int databaseSizeBeforeDelete = perfil_funcionalidade_acaoRepository.findAll().size();

        // Get the perfil_funcionalidade_acao
        restPerfil_funcionalidade_acaoMockMvc.perform(delete("/api/perfil-funcionalidade-acaos/{id}", perfil_funcionalidade_acao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean perfil_funcionalidade_acaoExistsInEs = perfil_funcionalidade_acaoSearchRepository.exists(perfil_funcionalidade_acao.getId());
        assertThat(perfil_funcionalidade_acaoExistsInEs).isFalse();

        // Validate the database is empty
        List<Perfil_funcionalidade_acao> perfil_funcionalidade_acaoList = perfil_funcionalidade_acaoRepository.findAll();
        assertThat(perfil_funcionalidade_acaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerfil_funcionalidade_acao() throws Exception {
        // Initialize the database
        perfil_funcionalidade_acaoService.save(perfil_funcionalidade_acao);

        // Search the perfil_funcionalidade_acao
        restPerfil_funcionalidade_acaoMockMvc.perform(get("/api/_search/perfil-funcionalidade-acaos?query=id:" + perfil_funcionalidade_acao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil_funcionalidade_acao.getId().intValue())))
            .andExpect(jsonPath("$.[*].id_perfil").value(hasItem(DEFAULT_ID_PERFIL)))
            .andExpect(jsonPath("$.[*].id_funcionalidade_acao").value(hasItem(DEFAULT_ID_FUNCIONALIDADE_ACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Perfil_funcionalidade_acao.class);
        Perfil_funcionalidade_acao perfil_funcionalidade_acao1 = new Perfil_funcionalidade_acao();
        perfil_funcionalidade_acao1.setId(1L);
        Perfil_funcionalidade_acao perfil_funcionalidade_acao2 = new Perfil_funcionalidade_acao();
        perfil_funcionalidade_acao2.setId(perfil_funcionalidade_acao1.getId());
        assertThat(perfil_funcionalidade_acao1).isEqualTo(perfil_funcionalidade_acao2);
        perfil_funcionalidade_acao2.setId(2L);
        assertThat(perfil_funcionalidade_acao1).isNotEqualTo(perfil_funcionalidade_acao2);
        perfil_funcionalidade_acao1.setId(null);
        assertThat(perfil_funcionalidade_acao1).isNotEqualTo(perfil_funcionalidade_acao2);
    }
}
