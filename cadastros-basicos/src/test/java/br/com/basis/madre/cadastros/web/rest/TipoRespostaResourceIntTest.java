package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import br.com.basis.madre.cadastros.domain.TipoResposta;
import br.com.basis.madre.cadastros.repository.TipoRespostaRepository;
import br.com.basis.madre.cadastros.service.TipoRespostaService;
import br.com.basis.madre.cadastros.repository.search.TipoRespostaSearchRepository;
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
 * Test class for the TipoRespostaResource REST controller.
 *
 * @see TipoRespostaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class TipoRespostaResourceIntTest {

    private static final String DEFAULT_RESPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_RESPOSTA = "BBBBBBBBBB";

    @Autowired
    private TipoRespostaRepository tipoRespostaRepository;

    @Autowired
    private TipoRespostaService tipoRespostaService;

    @Autowired
    private TipoRespostaSearchRepository tipoRespostaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoRespostaMockMvc;

    private TipoResposta tipoResposta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoRespostaResource tipoRespostaResource = new TipoRespostaResource(tipoRespostaService, tipoRespostaRepository);
        this.restTipoRespostaMockMvc = MockMvcBuilders.standaloneSetup(tipoRespostaResource)
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
    public static TipoResposta createEntity(EntityManager em) {
        TipoResposta tipoResposta = new TipoResposta()
            .resposta(DEFAULT_RESPOSTA);
        return tipoResposta;
    }

    @Before
    public void initTest() {
        tipoRespostaSearchRepository.deleteAll();
        tipoResposta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoResposta() throws Exception {
        int databaseSizeBeforeCreate = tipoRespostaRepository.findAll().size();

        // Create the TipoResposta
        restTipoRespostaMockMvc.perform(post("/api/tipo-respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResposta)))
            .andExpect(status().isCreated());

        // Validate the TipoResposta in the database
        List<TipoResposta> tipoRespostaList = tipoRespostaRepository.findAll();
        assertThat(tipoRespostaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoResposta testTipoResposta = tipoRespostaList.get(tipoRespostaList.size() - 1);
        assertThat(testTipoResposta.getResposta()).isEqualTo(DEFAULT_RESPOSTA);

        // Validate the TipoResposta in Elasticsearch
        TipoResposta tipoRespostaEs = tipoRespostaSearchRepository.findOne(testTipoResposta.getId());
        assertThat(tipoRespostaEs).isEqualToIgnoringGivenFields(testTipoResposta);
    }

    @Test
    @Transactional
    public void createTipoRespostaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRespostaRepository.findAll().size();

        // Create the TipoResposta with an existing ID
        tipoResposta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRespostaMockMvc.perform(post("/api/tipo-respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResposta)))
            .andExpect(status().isBadRequest());

        // Validate the TipoResposta in the database
        List<TipoResposta> tipoRespostaList = tipoRespostaRepository.findAll();
        assertThat(tipoRespostaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRespostaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoRespostaRepository.findAll().size();
        // set the field null
        tipoResposta.setResposta(null);

        // Create the TipoResposta, which fails.

        restTipoRespostaMockMvc.perform(post("/api/tipo-respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResposta)))
            .andExpect(status().isBadRequest());

        List<TipoResposta> tipoRespostaList = tipoRespostaRepository.findAll();
        assertThat(tipoRespostaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoRespostas() throws Exception {
        // Initialize the database
        tipoRespostaRepository.saveAndFlush(tipoResposta);

        // Get all the tipoRespostaList
        restTipoRespostaMockMvc.perform(get("/api/tipo-respostas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoResposta.getId().intValue())))
            .andExpect(jsonPath("$.[*].resposta").value(hasItem(DEFAULT_RESPOSTA.toString())));
    }

    @Test
    @Transactional
    public void getTipoResposta() throws Exception {
        // Initialize the database
        tipoRespostaRepository.saveAndFlush(tipoResposta);

        // Get the tipoResposta
        restTipoRespostaMockMvc.perform(get("/api/tipo-respostas/{id}", tipoResposta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoResposta.getId().intValue()))
            .andExpect(jsonPath("$.resposta").value(DEFAULT_RESPOSTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoResposta() throws Exception {
        // Get the tipoResposta
        restTipoRespostaMockMvc.perform(get("/api/tipo-respostas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoResposta() throws Exception {
        // Initialize the database
        tipoRespostaService.save(tipoResposta);

        int databaseSizeBeforeUpdate = tipoRespostaRepository.findAll().size();

        // Update the tipoResposta
        TipoResposta updatedTipoResposta = tipoRespostaRepository.findOne(tipoResposta.getId());
        // Disconnect from session so that the updates on updatedTipoResposta are not directly saved in db
        em.detach(updatedTipoResposta);
        updatedTipoResposta
            .resposta(UPDATED_RESPOSTA);

        restTipoRespostaMockMvc.perform(put("/api/tipo-respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoResposta)))
            .andExpect(status().isOk());

        // Validate the TipoResposta in the database
        List<TipoResposta> tipoRespostaList = tipoRespostaRepository.findAll();
        assertThat(tipoRespostaList).hasSize(databaseSizeBeforeUpdate);
        TipoResposta testTipoResposta = tipoRespostaList.get(tipoRespostaList.size() - 1);
        assertThat(testTipoResposta.getResposta()).isEqualTo(UPDATED_RESPOSTA);

        // Validate the TipoResposta in Elasticsearch
        TipoResposta tipoRespostaEs = tipoRespostaSearchRepository.findOne(testTipoResposta.getId());
        assertThat(tipoRespostaEs).isEqualToIgnoringGivenFields(testTipoResposta);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoResposta() throws Exception {
        int databaseSizeBeforeUpdate = tipoRespostaRepository.findAll().size();

        // Create the TipoResposta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoRespostaMockMvc.perform(put("/api/tipo-respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoResposta)))
            .andExpect(status().isCreated());

        // Validate the TipoResposta in the database
        List<TipoResposta> tipoRespostaList = tipoRespostaRepository.findAll();
        assertThat(tipoRespostaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoResposta() throws Exception {
        // Initialize the database
        tipoRespostaService.save(tipoResposta);

        int databaseSizeBeforeDelete = tipoRespostaRepository.findAll().size();

        // Get the tipoResposta
        restTipoRespostaMockMvc.perform(delete("/api/tipo-respostas/{id}", tipoResposta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tipoRespostaExistsInEs = tipoRespostaSearchRepository.exists(tipoResposta.getId());
        assertThat(tipoRespostaExistsInEs).isFalse();

        // Validate the database is empty
        List<TipoResposta> tipoRespostaList = tipoRespostaRepository.findAll();
        assertThat(tipoRespostaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTipoResposta() throws Exception {
        // Initialize the database
        tipoRespostaService.save(tipoResposta);

        // Search the tipoResposta
        restTipoRespostaMockMvc.perform(get("/api/_search/tipo-respostas?query=id:" + tipoResposta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoResposta.getId().intValue())))
            .andExpect(jsonPath("$.[*].resposta").value(hasItem(DEFAULT_RESPOSTA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoResposta.class);
        TipoResposta tipoResposta1 = new TipoResposta();
        tipoResposta1.setId(1L);
        TipoResposta tipoResposta2 = new TipoResposta();
        tipoResposta2.setId(tipoResposta1.getId());
        assertThat(tipoResposta1).isEqualTo(tipoResposta2);
        tipoResposta2.setId(2L);
        assertThat(tipoResposta1).isNotEqualTo(tipoResposta2);
        tipoResposta1.setId(null);
        assertThat(tipoResposta1).isNotEqualTo(tipoResposta2);
    }
}
