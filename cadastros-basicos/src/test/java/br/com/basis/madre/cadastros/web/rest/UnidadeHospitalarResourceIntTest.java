package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;
import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.search.UnidadeHospitalarSearchRepository;
import br.com.basis.madre.cadastros.service.UnidadeHospitalarService;
import br.com.basis.madre.cadastros.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the UnidadeHospitalarResource REST controller.
 *
 * @see UnidadeHospitalarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class UnidadeHospitalarResourceIntTest {


    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private UnidadeHospitalarRepository unidadeHospitalarRepository;

    @Autowired
    private UnidadeHospitalarService unidadeHospitalarService;

    @Autowired
    private UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnidadeHospitalarMockMvc;

    private UnidadeHospitalar unidadeHospitalar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeHospitalarResource unidadeHospitalarResource = new UnidadeHospitalarResource(unidadeHospitalarService, unidadeHospitalarRepository); this.restUnidadeHospitalarMockMvc = MockMvcBuilders.standaloneSetup(unidadeHospitalarResource)
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
    public static UnidadeHospitalar createEntity(EntityManager em) {
        UnidadeHospitalar unidadeHospitalar = new UnidadeHospitalar()
            .sigla(DEFAULT_SIGLA)
            .nome(DEFAULT_NOME)
            .cnpj(DEFAULT_CNPJ)
            .endereco(DEFAULT_ENDERECO)
            .ativo(DEFAULT_ATIVO);
        return unidadeHospitalar;
    }

    @Before
    public void initTest() {
        unidadeHospitalarSearchRepository.deleteAll();
        unidadeHospitalar = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeHospitalar() throws Exception {
        int databaseSizeBeforeCreate = unidadeHospitalarRepository.findAll().size();

        // Create the UnidadeHospitalar
        restUnidadeHospitalarMockMvc.perform(post("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeHospitalar)))
            .andExpect(status().isCreated());

        // Validate the UnidadeHospitalar in the database
        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeHospitalar testUnidadeHospitalar = unidadeHospitalarList.get(unidadeHospitalarList.size() - 1);
        assertThat(testUnidadeHospitalar.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testUnidadeHospitalar.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testUnidadeHospitalar.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testUnidadeHospitalar.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testUnidadeHospitalar.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the UnidadeHospitalar in Elasticsearch
        UnidadeHospitalar unidadeHospitalarEs = unidadeHospitalarSearchRepository.findOne(testUnidadeHospitalar.getId());
        assertThat(unidadeHospitalarEs).isEqualToIgnoringGivenFields(testUnidadeHospitalar);
    }

    @Test
    @Transactional
    public void createUnidadeHospitalarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeHospitalarRepository.findAll().size();

        // Create the UnidadeHospitalar with an existing ID
        unidadeHospitalar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeHospitalarMockMvc.perform(post("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeHospitalar)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeHospitalar in the database
        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeHospitalarRepository.findAll().size();
        // set the field null
        unidadeHospitalar.setSigla(null);

        // Create the UnidadeHospitalar, which fails.

        restUnidadeHospitalarMockMvc.perform(post("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeHospitalar)))
            .andExpect(status().isBadRequest());

        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeHospitalarRepository.findAll().size();
        // set the field null
        unidadeHospitalar.setNome(null);

        // Create the UnidadeHospitalar, which fails.

        restUnidadeHospitalarMockMvc.perform(post("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeHospitalar)))
            .andExpect(status().isBadRequest());

        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeHospitalarRepository.findAll().size();
        // set the field null
        unidadeHospitalar.setCnpj(null);

        // Create the UnidadeHospitalar, which fails.

        restUnidadeHospitalarMockMvc.perform(post("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeHospitalar)))
            .andExpect(status().isBadRequest());

        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeHospitalarRepository.findAll().size();
        // set the field null
        unidadeHospitalar.setEndereco(null);

        // Create the UnidadeHospitalar, which fails.

        restUnidadeHospitalarMockMvc.perform(post("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeHospitalar)))
            .andExpect(status().isBadRequest());

        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeHospitalarRepository.findAll().size();
        // set the field null
        unidadeHospitalar.setAtivo(null);

        // Create the UnidadeHospitalar, which fails.

        restUnidadeHospitalarMockMvc.perform(post("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeHospitalar)))
            .andExpect(status().isBadRequest());

        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeHospitalars() throws Exception {
        // Initialize the database
        unidadeHospitalarRepository.saveAndFlush(unidadeHospitalar);

        // Get all the unidadeHospitalarList
        restUnidadeHospitalarMockMvc.perform(get("/api/unidade-hospitalars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeHospitalar.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getUnidadeHospitalar() throws Exception {
        // Initialize the database
        unidadeHospitalarRepository.saveAndFlush(unidadeHospitalar);

        // Get the unidadeHospitalar
        restUnidadeHospitalarMockMvc.perform(get("/api/unidade-hospitalars/{id}", unidadeHospitalar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeHospitalar.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeHospitalar() throws Exception {
        // Get the unidadeHospitalar
        restUnidadeHospitalarMockMvc.perform(get("/api/unidade-hospitalars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeHospitalar() throws Exception {
        // Initialize the database
        unidadeHospitalarRepository.saveAndFlush(unidadeHospitalar);
        int databaseSizeBeforeUpdate = unidadeHospitalarRepository.findAll().size();

        // Update the unidadeHospitalar
        UnidadeHospitalar updatedUnidadeHospitalar = unidadeHospitalarRepository.findOne(unidadeHospitalar.getId());
        // Disconnect from session so that the updates on updatedUnidadeHospitalar are not directly saved in db
        em.detach(updatedUnidadeHospitalar);
        updatedUnidadeHospitalar
            .sigla(UPDATED_SIGLA)
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .endereco(UPDATED_ENDERECO)
            .ativo(UPDATED_ATIVO);

        restUnidadeHospitalarMockMvc.perform(put("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnidadeHospitalar)))
            .andExpect(status().isOk());

        // Validate the UnidadeHospitalar in the database
        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeUpdate);
        UnidadeHospitalar testUnidadeHospitalar = unidadeHospitalarList.get(unidadeHospitalarList.size() - 1);
        assertThat(testUnidadeHospitalar.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testUnidadeHospitalar.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUnidadeHospitalar.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testUnidadeHospitalar.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testUnidadeHospitalar.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the UnidadeHospitalar in Elasticsearch
        UnidadeHospitalar unidadeHospitalarEs = unidadeHospitalarSearchRepository.findOne(testUnidadeHospitalar.getId());
        assertThat(unidadeHospitalarEs).isEqualToIgnoringGivenFields(testUnidadeHospitalar);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeHospitalar() throws Exception {
        int databaseSizeBeforeUpdate = unidadeHospitalarRepository.findAll().size();

        // Create the UnidadeHospitalar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUnidadeHospitalarMockMvc.perform(put("/api/unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeHospitalar)))
            .andExpect(status().isCreated());

        // Validate the UnidadeHospitalar in the database
        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUnidadeHospitalar() throws Exception {
        // Initialize the database
        unidadeHospitalarRepository.saveAndFlush(unidadeHospitalar);
        int databaseSizeBeforeDelete = unidadeHospitalarRepository.findAll().size();

        // Get the unidadeHospitalar
        restUnidadeHospitalarMockMvc.perform(delete("/api/unidade-hospitalars/{id}", unidadeHospitalar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean unidadeHospitalarExistsInEs = unidadeHospitalarSearchRepository.exists(unidadeHospitalar.getId());
        assertThat(unidadeHospitalarExistsInEs).isFalse();

        // Validate the database is empty
        List<UnidadeHospitalar> unidadeHospitalarList = unidadeHospitalarRepository.findAll();
        assertThat(unidadeHospitalarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUnidadeHospitalar() throws Exception {
        // Initialize the database
        unidadeHospitalarRepository.saveAndFlush(unidadeHospitalar);
        // Search the unidadeHospitalar
        restUnidadeHospitalarMockMvc.perform(get("/api/_search/unidade-hospitalars?query=id:" + unidadeHospitalar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeHospitalar.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeHospitalar.class);
        UnidadeHospitalar unidadeHospitalar1 = new UnidadeHospitalar();
        unidadeHospitalar1.setId(1L);
        UnidadeHospitalar unidadeHospitalar2 = new UnidadeHospitalar();
        unidadeHospitalar2.setId(unidadeHospitalar1.getId());
        assertThat(unidadeHospitalar1).isEqualTo(unidadeHospitalar2);
        unidadeHospitalar2.setId(2L);
        assertThat(unidadeHospitalar1).isNotEqualTo(unidadeHospitalar2);
        unidadeHospitalar1.setId(null);
        assertThat(unidadeHospitalar1).isNotEqualTo(unidadeHospitalar2);
    }
}
