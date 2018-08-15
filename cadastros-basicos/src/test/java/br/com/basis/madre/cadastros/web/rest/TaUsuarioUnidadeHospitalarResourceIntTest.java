package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import br.com.basis.madre.cadastros.domain.TaUsuarioUnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.TaUsuarioUnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.service.TaUsuarioUnidadeHospitalarService;
import br.com.basis.madre.cadastros.repository.search.TaUsuarioUnidadeHospitalarSearchRepository;
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
 * Test class for the TaUsuarioUnidadeHospitalarResource REST controller.
 *
 * @see TaUsuarioUnidadeHospitalarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class TaUsuarioUnidadeHospitalarResourceIntTest {

    @Autowired
    private TaUsuarioUnidadeHospitalarRepository taUsuarioUnidadeHospitalarRepository;

    @Autowired
    private TaUsuarioUnidadeHospitalarService taUsuarioUnidadeHospitalarService;

    @Autowired
    private TaUsuarioUnidadeHospitalarSearchRepository taUsuarioUnidadeHospitalarSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaUsuarioUnidadeHospitalarMockMvc;

    private TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaUsuarioUnidadeHospitalarResource taUsuarioUnidadeHospitalarResource = new TaUsuarioUnidadeHospitalarResource(taUsuarioUnidadeHospitalarService);
        this.restTaUsuarioUnidadeHospitalarMockMvc = MockMvcBuilders.standaloneSetup(taUsuarioUnidadeHospitalarResource)
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
    public static TaUsuarioUnidadeHospitalar createEntity(EntityManager em) {
        TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar = new TaUsuarioUnidadeHospitalar();
        return taUsuarioUnidadeHospitalar;
    }

    @Before
    public void initTest() {
        taUsuarioUnidadeHospitalarSearchRepository.deleteAll();
        taUsuarioUnidadeHospitalar = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaUsuarioUnidadeHospitalar() throws Exception {
        int databaseSizeBeforeCreate = taUsuarioUnidadeHospitalarRepository.findAll().size();

        // Create the TaUsuarioUnidadeHospitalar
        restTaUsuarioUnidadeHospitalarMockMvc.perform(post("/api/ta-usuario-unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taUsuarioUnidadeHospitalar)))
            .andExpect(status().isCreated());

        // Validate the TaUsuarioUnidadeHospitalar in the database
        List<TaUsuarioUnidadeHospitalar> taUsuarioUnidadeHospitalarList = taUsuarioUnidadeHospitalarRepository.findAll();
        assertThat(taUsuarioUnidadeHospitalarList).hasSize(databaseSizeBeforeCreate + 1);
        TaUsuarioUnidadeHospitalar testTaUsuarioUnidadeHospitalar = taUsuarioUnidadeHospitalarList.get(taUsuarioUnidadeHospitalarList.size() - 1);

        // Validate the TaUsuarioUnidadeHospitalar in Elasticsearch
        TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalarEs = taUsuarioUnidadeHospitalarSearchRepository.findOne(testTaUsuarioUnidadeHospitalar.getId());
        assertThat(taUsuarioUnidadeHospitalarEs).isEqualToIgnoringGivenFields(testTaUsuarioUnidadeHospitalar);
    }

    @Test
    @Transactional
    public void createTaUsuarioUnidadeHospitalarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taUsuarioUnidadeHospitalarRepository.findAll().size();

        // Create the TaUsuarioUnidadeHospitalar with an existing ID
        taUsuarioUnidadeHospitalar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaUsuarioUnidadeHospitalarMockMvc.perform(post("/api/ta-usuario-unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taUsuarioUnidadeHospitalar)))
            .andExpect(status().isBadRequest());

        // Validate the TaUsuarioUnidadeHospitalar in the database
        List<TaUsuarioUnidadeHospitalar> taUsuarioUnidadeHospitalarList = taUsuarioUnidadeHospitalarRepository.findAll();
        assertThat(taUsuarioUnidadeHospitalarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaUsuarioUnidadeHospitalars() throws Exception {
        // Initialize the database
        taUsuarioUnidadeHospitalarRepository.saveAndFlush(taUsuarioUnidadeHospitalar);

        // Get all the taUsuarioUnidadeHospitalarList
        restTaUsuarioUnidadeHospitalarMockMvc.perform(get("/api/ta-usuario-unidade-hospitalars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taUsuarioUnidadeHospitalar.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTaUsuarioUnidadeHospitalar() throws Exception {
        // Initialize the database
        taUsuarioUnidadeHospitalarRepository.saveAndFlush(taUsuarioUnidadeHospitalar);

        // Get the taUsuarioUnidadeHospitalar
        restTaUsuarioUnidadeHospitalarMockMvc.perform(get("/api/ta-usuario-unidade-hospitalars/{id}", taUsuarioUnidadeHospitalar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taUsuarioUnidadeHospitalar.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTaUsuarioUnidadeHospitalar() throws Exception {
        // Get the taUsuarioUnidadeHospitalar
        restTaUsuarioUnidadeHospitalarMockMvc.perform(get("/api/ta-usuario-unidade-hospitalars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaUsuarioUnidadeHospitalar() throws Exception {
        // Initialize the database
        taUsuarioUnidadeHospitalarService.save(taUsuarioUnidadeHospitalar);

        int databaseSizeBeforeUpdate = taUsuarioUnidadeHospitalarRepository.findAll().size();

        // Update the taUsuarioUnidadeHospitalar
        TaUsuarioUnidadeHospitalar updatedTaUsuarioUnidadeHospitalar = taUsuarioUnidadeHospitalarRepository.findOne(taUsuarioUnidadeHospitalar.getId());
        // Disconnect from session so that the updates on updatedTaUsuarioUnidadeHospitalar are not directly saved in db
        em.detach(updatedTaUsuarioUnidadeHospitalar);

        restTaUsuarioUnidadeHospitalarMockMvc.perform(put("/api/ta-usuario-unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaUsuarioUnidadeHospitalar)))
            .andExpect(status().isOk());

        // Validate the TaUsuarioUnidadeHospitalar in the database
        List<TaUsuarioUnidadeHospitalar> taUsuarioUnidadeHospitalarList = taUsuarioUnidadeHospitalarRepository.findAll();
        assertThat(taUsuarioUnidadeHospitalarList).hasSize(databaseSizeBeforeUpdate);
        TaUsuarioUnidadeHospitalar testTaUsuarioUnidadeHospitalar = taUsuarioUnidadeHospitalarList.get(taUsuarioUnidadeHospitalarList.size() - 1);

        // Validate the TaUsuarioUnidadeHospitalar in Elasticsearch
        TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalarEs = taUsuarioUnidadeHospitalarSearchRepository.findOne(testTaUsuarioUnidadeHospitalar.getId());
        assertThat(taUsuarioUnidadeHospitalarEs).isEqualToIgnoringGivenFields(testTaUsuarioUnidadeHospitalar);
    }

    @Test
    @Transactional
    public void updateNonExistingTaUsuarioUnidadeHospitalar() throws Exception {
        int databaseSizeBeforeUpdate = taUsuarioUnidadeHospitalarRepository.findAll().size();

        // Create the TaUsuarioUnidadeHospitalar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaUsuarioUnidadeHospitalarMockMvc.perform(put("/api/ta-usuario-unidade-hospitalars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taUsuarioUnidadeHospitalar)))
            .andExpect(status().isCreated());

        // Validate the TaUsuarioUnidadeHospitalar in the database
        List<TaUsuarioUnidadeHospitalar> taUsuarioUnidadeHospitalarList = taUsuarioUnidadeHospitalarRepository.findAll();
        assertThat(taUsuarioUnidadeHospitalarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaUsuarioUnidadeHospitalar() throws Exception {
        // Initialize the database
        taUsuarioUnidadeHospitalarService.save(taUsuarioUnidadeHospitalar);

        int databaseSizeBeforeDelete = taUsuarioUnidadeHospitalarRepository.findAll().size();

        // Get the taUsuarioUnidadeHospitalar
        restTaUsuarioUnidadeHospitalarMockMvc.perform(delete("/api/ta-usuario-unidade-hospitalars/{id}", taUsuarioUnidadeHospitalar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean taUsuarioUnidadeHospitalarExistsInEs = taUsuarioUnidadeHospitalarSearchRepository.exists(taUsuarioUnidadeHospitalar.getId());
        assertThat(taUsuarioUnidadeHospitalarExistsInEs).isFalse();

        // Validate the database is empty
        List<TaUsuarioUnidadeHospitalar> taUsuarioUnidadeHospitalarList = taUsuarioUnidadeHospitalarRepository.findAll();
        assertThat(taUsuarioUnidadeHospitalarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTaUsuarioUnidadeHospitalar() throws Exception {
        // Initialize the database
        taUsuarioUnidadeHospitalarService.save(taUsuarioUnidadeHospitalar);

        // Search the taUsuarioUnidadeHospitalar
        restTaUsuarioUnidadeHospitalarMockMvc.perform(get("/api/_search/ta-usuario-unidade-hospitalars?query=id:" + taUsuarioUnidadeHospitalar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taUsuarioUnidadeHospitalar.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaUsuarioUnidadeHospitalar.class);
        TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar1 = new TaUsuarioUnidadeHospitalar();
        taUsuarioUnidadeHospitalar1.setId(1L);
        TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar2 = new TaUsuarioUnidadeHospitalar();
        taUsuarioUnidadeHospitalar2.setId(taUsuarioUnidadeHospitalar1.getId());
        assertThat(taUsuarioUnidadeHospitalar1).isEqualTo(taUsuarioUnidadeHospitalar2);
        taUsuarioUnidadeHospitalar2.setId(2L);
        assertThat(taUsuarioUnidadeHospitalar1).isNotEqualTo(taUsuarioUnidadeHospitalar2);
        taUsuarioUnidadeHospitalar1.setId(null);
        assertThat(taUsuarioUnidadeHospitalar1).isNotEqualTo(taUsuarioUnidadeHospitalar2);
    }
}
