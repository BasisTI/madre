package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import br.com.basis.madre.cadastros.domain.Acao;
import br.com.basis.madre.cadastros.repository.AcaoRepository;
import br.com.basis.madre.cadastros.service.AcaoService;
import br.com.basis.madre.cadastros.repository.search.AcaoSearchRepository;
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
 * Test class for the AcaoResource REST controller.
 *
 * @see AcaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class AcaoResourceIntTest {

    private static final String DEFAULT_NM_ACAO = "AAAAAAAAAA";
    private static final String UPDATED_NM_ACAO = "BBBBBBBBBB";

    private static final String DEFAULT_CD_ACAO = "AAAAAAAA";
    private static final String UPDATED_CD_ACAO = "BBBBBBBB";

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private AcaoService acaoService;

    @Autowired
    private AcaoSearchRepository acaoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAcaoMockMvc;

    private Acao acao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcaoResource acaoResource = new AcaoResource(acaoService);
        this.restAcaoMockMvc = MockMvcBuilders.standaloneSetup(acaoResource)
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
    public static Acao createEntity(EntityManager em) {
        Acao acao = new Acao()
            .nm_acao(DEFAULT_NM_ACAO)
            .cd_acao(DEFAULT_CD_ACAO);
        return acao;
    }

    @Before
    public void initTest() {
        acaoSearchRepository.deleteAll();
        acao = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcao() throws Exception {
        int databaseSizeBeforeCreate = acaoRepository.findAll().size();

        // Create the Acao
        restAcaoMockMvc.perform(post("/api/acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isCreated());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeCreate + 1);
        Acao testAcao = acaoList.get(acaoList.size() - 1);
        assertThat(testAcao.getNm_acao()).isEqualTo(DEFAULT_NM_ACAO);
        assertThat(testAcao.getCd_acao()).isEqualTo(DEFAULT_CD_ACAO);

        // Validate the Acao in Elasticsearch
        Acao acaoEs = acaoSearchRepository.findOne(testAcao.getId());
        assertThat(acaoEs).isEqualToIgnoringGivenFields(testAcao);
    }

    @Test
    @Transactional
    public void createAcaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acaoRepository.findAll().size();

        // Create the Acao with an existing ID
        acao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcaoMockMvc.perform(post("/api/acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isBadRequest());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNm_acaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoRepository.findAll().size();
        // set the field null
        acao.setNm_acao(null);

        // Create the Acao, which fails.

        restAcaoMockMvc.perform(post("/api/acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isBadRequest());

        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCd_acaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acaoRepository.findAll().size();
        // set the field null
        acao.setCd_acao(null);

        // Create the Acao, which fails.

        restAcaoMockMvc.perform(post("/api/acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isBadRequest());

        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcaos() throws Exception {
        // Initialize the database
        acaoRepository.saveAndFlush(acao);

        // Get all the acaoList
        restAcaoMockMvc.perform(get("/api/acaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nm_acao").value(hasItem(DEFAULT_NM_ACAO.toString())))
            .andExpect(jsonPath("$.[*].cd_acao").value(hasItem(DEFAULT_CD_ACAO.toString())));
    }

    @Test
    @Transactional
    public void getAcao() throws Exception {
        // Initialize the database
        acaoRepository.saveAndFlush(acao);

        // Get the acao
        restAcaoMockMvc.perform(get("/api/acaos/{id}", acao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acao.getId().intValue()))
            .andExpect(jsonPath("$.nm_acao").value(DEFAULT_NM_ACAO.toString()))
            .andExpect(jsonPath("$.cd_acao").value(DEFAULT_CD_ACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAcao() throws Exception {
        // Get the acao
        restAcaoMockMvc.perform(get("/api/acaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcao() throws Exception {
        // Initialize the database
        acaoService.save(acao);

        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();

        // Update the acao
        Acao updatedAcao = acaoRepository.findOne(acao.getId());
        // Disconnect from session so that the updates on updatedAcao are not directly saved in db
        em.detach(updatedAcao);
        updatedAcao
            .nm_acao(UPDATED_NM_ACAO)
            .cd_acao(UPDATED_CD_ACAO);

        restAcaoMockMvc.perform(put("/api/acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcao)))
            .andExpect(status().isOk());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
        Acao testAcao = acaoList.get(acaoList.size() - 1);
        assertThat(testAcao.getNm_acao()).isEqualTo(UPDATED_NM_ACAO);
        assertThat(testAcao.getCd_acao()).isEqualTo(UPDATED_CD_ACAO);

        // Validate the Acao in Elasticsearch
        Acao acaoEs = acaoSearchRepository.findOne(testAcao.getId());
        assertThat(acaoEs).isEqualToIgnoringGivenFields(testAcao);
    }

    @Test
    @Transactional
    public void updateNonExistingAcao() throws Exception {
        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();

        // Create the Acao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAcaoMockMvc.perform(put("/api/acaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isCreated());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAcao() throws Exception {
        // Initialize the database
        acaoService.save(acao);

        int databaseSizeBeforeDelete = acaoRepository.findAll().size();

        // Get the acao
        restAcaoMockMvc.perform(delete("/api/acaos/{id}", acao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean acaoExistsInEs = acaoSearchRepository.exists(acao.getId());
        assertThat(acaoExistsInEs).isFalse();

        // Validate the database is empty
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAcao() throws Exception {
        // Initialize the database
        acaoService.save(acao);

        // Search the acao
        restAcaoMockMvc.perform(get("/api/_search/acaos?query=id:" + acao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nm_acao").value(hasItem(DEFAULT_NM_ACAO.toString())))
            .andExpect(jsonPath("$.[*].cd_acao").value(hasItem(DEFAULT_CD_ACAO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acao.class);
        Acao acao1 = new Acao();
        acao1.setId(1L);
        Acao acao2 = new Acao();
        acao2.setId(acao1.getId());
        assertThat(acao1).isEqualTo(acao2);
        acao2.setId(2L);
        assertThat(acao1).isNotEqualTo(acao2);
        acao1.setId(null);
        assertThat(acao1).isNotEqualTo(acao2);
    }
}
