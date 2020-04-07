package br.com.basis.madre.web.rest;

import br.com.basis.madre.SnffaturaApp;
import br.com.basis.madre.domain.Genitores;
import br.com.basis.madre.repository.GenitoresRepository;
import br.com.basis.madre.repository.search.GenitoresSearchRepository;
import br.com.basis.madre.service.GenitoresService;
import br.com.basis.madre.service.dto.GenitoresDTO;
import br.com.basis.madre.service.mapper.GenitoresMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GenitoresResource} REST controller.
 */
@SpringBootTest(classes = SnffaturaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GenitoresResourceIT {

    private static final String DEFAULT_PRONTUARIO_DA_MAE = "AAAAAAAAAA";
    private static final String UPDATED_PRONTUARIO_DA_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_DA_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DA_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_DO_PAI = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_PAI = "BBBBBBBBBB";

    @Autowired
    private GenitoresRepository genitoresRepository;

    @Autowired
    private GenitoresMapper genitoresMapper;

    @Autowired
    private GenitoresService genitoresService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.GenitoresSearchRepositoryMockConfiguration
     */
    @Autowired
    private GenitoresSearchRepository mockGenitoresSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGenitoresMockMvc;

    private Genitores genitores;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genitores createEntity(EntityManager em) {
        Genitores genitores = new Genitores()
            .prontuarioDaMae(DEFAULT_PRONTUARIO_DA_MAE)
            .nomeDaMae(DEFAULT_NOME_DA_MAE)
            .nomeDoPai(DEFAULT_NOME_DO_PAI);
        return genitores;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genitores createUpdatedEntity(EntityManager em) {
        Genitores genitores = new Genitores()
            .prontuarioDaMae(UPDATED_PRONTUARIO_DA_MAE)
            .nomeDaMae(UPDATED_NOME_DA_MAE)
            .nomeDoPai(UPDATED_NOME_DO_PAI);
        return genitores;
    }

    @BeforeEach
    public void initTest() {
        genitores = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenitores() throws Exception {
        int databaseSizeBeforeCreate = genitoresRepository.findAll().size();

        // Create the Genitores
        GenitoresDTO genitoresDTO = genitoresMapper.toDto(genitores);
        restGenitoresMockMvc.perform(post("/api/genitores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genitoresDTO)))
            .andExpect(status().isCreated());

        // Validate the Genitores in the database
        List<Genitores> genitoresList = genitoresRepository.findAll();
        assertThat(genitoresList).hasSize(databaseSizeBeforeCreate + 1);
        Genitores testGenitores = genitoresList.get(genitoresList.size() - 1);
        assertThat(testGenitores.getProntuarioDaMae()).isEqualTo(DEFAULT_PRONTUARIO_DA_MAE);
        assertThat(testGenitores.getNomeDaMae()).isEqualTo(DEFAULT_NOME_DA_MAE);
        assertThat(testGenitores.getNomeDoPai()).isEqualTo(DEFAULT_NOME_DO_PAI);

        // Validate the Genitores in Elasticsearch
        verify(mockGenitoresSearchRepository, times(1)).save(testGenitores);
    }

    @Test
    @Transactional
    public void createGenitoresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genitoresRepository.findAll().size();

        // Create the Genitores with an existing ID
        genitores.setId(1L);
        GenitoresDTO genitoresDTO = genitoresMapper.toDto(genitores);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenitoresMockMvc.perform(post("/api/genitores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genitoresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Genitores in the database
        List<Genitores> genitoresList = genitoresRepository.findAll();
        assertThat(genitoresList).hasSize(databaseSizeBeforeCreate);

        // Validate the Genitores in Elasticsearch
        verify(mockGenitoresSearchRepository, times(0)).save(genitores);
    }


    @Test
    @Transactional
    public void checkNomeDaMaeIsRequired() throws Exception {
        int databaseSizeBeforeTest = genitoresRepository.findAll().size();
        // set the field null
        genitores.setNomeDaMae(null);

        // Create the Genitores, which fails.
        GenitoresDTO genitoresDTO = genitoresMapper.toDto(genitores);

        restGenitoresMockMvc.perform(post("/api/genitores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genitoresDTO)))
            .andExpect(status().isBadRequest());

        List<Genitores> genitoresList = genitoresRepository.findAll();
        assertThat(genitoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeDoPaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = genitoresRepository.findAll().size();
        // set the field null
        genitores.setNomeDoPai(null);

        // Create the Genitores, which fails.
        GenitoresDTO genitoresDTO = genitoresMapper.toDto(genitores);

        restGenitoresMockMvc.perform(post("/api/genitores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genitoresDTO)))
            .andExpect(status().isBadRequest());

        List<Genitores> genitoresList = genitoresRepository.findAll();
        assertThat(genitoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenitores() throws Exception {
        // Initialize the database
        genitoresRepository.saveAndFlush(genitores);

        // Get all the genitoresList
        restGenitoresMockMvc.perform(get("/api/genitores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genitores.getId().intValue())))
            .andExpect(jsonPath("$.[*].prontuarioDaMae").value(hasItem(DEFAULT_PRONTUARIO_DA_MAE)))
            .andExpect(jsonPath("$.[*].nomeDaMae").value(hasItem(DEFAULT_NOME_DA_MAE)))
            .andExpect(jsonPath("$.[*].nomeDoPai").value(hasItem(DEFAULT_NOME_DO_PAI)));
    }
    
    @Test
    @Transactional
    public void getGenitores() throws Exception {
        // Initialize the database
        genitoresRepository.saveAndFlush(genitores);

        // Get the genitores
        restGenitoresMockMvc.perform(get("/api/genitores/{id}", genitores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genitores.getId().intValue()))
            .andExpect(jsonPath("$.prontuarioDaMae").value(DEFAULT_PRONTUARIO_DA_MAE))
            .andExpect(jsonPath("$.nomeDaMae").value(DEFAULT_NOME_DA_MAE))
            .andExpect(jsonPath("$.nomeDoPai").value(DEFAULT_NOME_DO_PAI));
    }

    @Test
    @Transactional
    public void getNonExistingGenitores() throws Exception {
        // Get the genitores
        restGenitoresMockMvc.perform(get("/api/genitores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenitores() throws Exception {
        // Initialize the database
        genitoresRepository.saveAndFlush(genitores);

        int databaseSizeBeforeUpdate = genitoresRepository.findAll().size();

        // Update the genitores
        Genitores updatedGenitores = genitoresRepository.findById(genitores.getId()).get();
        // Disconnect from session so that the updates on updatedGenitores are not directly saved in db
        em.detach(updatedGenitores);
        updatedGenitores
            .prontuarioDaMae(UPDATED_PRONTUARIO_DA_MAE)
            .nomeDaMae(UPDATED_NOME_DA_MAE)
            .nomeDoPai(UPDATED_NOME_DO_PAI);
        GenitoresDTO genitoresDTO = genitoresMapper.toDto(updatedGenitores);

        restGenitoresMockMvc.perform(put("/api/genitores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genitoresDTO)))
            .andExpect(status().isOk());

        // Validate the Genitores in the database
        List<Genitores> genitoresList = genitoresRepository.findAll();
        assertThat(genitoresList).hasSize(databaseSizeBeforeUpdate);
        Genitores testGenitores = genitoresList.get(genitoresList.size() - 1);
        assertThat(testGenitores.getProntuarioDaMae()).isEqualTo(UPDATED_PRONTUARIO_DA_MAE);
        assertThat(testGenitores.getNomeDaMae()).isEqualTo(UPDATED_NOME_DA_MAE);
        assertThat(testGenitores.getNomeDoPai()).isEqualTo(UPDATED_NOME_DO_PAI);

        // Validate the Genitores in Elasticsearch
        verify(mockGenitoresSearchRepository, times(1)).save(testGenitores);
    }

    @Test
    @Transactional
    public void updateNonExistingGenitores() throws Exception {
        int databaseSizeBeforeUpdate = genitoresRepository.findAll().size();

        // Create the Genitores
        GenitoresDTO genitoresDTO = genitoresMapper.toDto(genitores);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenitoresMockMvc.perform(put("/api/genitores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genitoresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Genitores in the database
        List<Genitores> genitoresList = genitoresRepository.findAll();
        assertThat(genitoresList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Genitores in Elasticsearch
        verify(mockGenitoresSearchRepository, times(0)).save(genitores);
    }

    @Test
    @Transactional
    public void deleteGenitores() throws Exception {
        // Initialize the database
        genitoresRepository.saveAndFlush(genitores);

        int databaseSizeBeforeDelete = genitoresRepository.findAll().size();

        // Delete the genitores
        restGenitoresMockMvc.perform(delete("/api/genitores/{id}", genitores.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Genitores> genitoresList = genitoresRepository.findAll();
        assertThat(genitoresList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Genitores in Elasticsearch
        verify(mockGenitoresSearchRepository, times(1)).deleteById(genitores.getId());
    }

    @Test
    @Transactional
    public void searchGenitores() throws Exception {
        // Initialize the database
        genitoresRepository.saveAndFlush(genitores);
        when(mockGenitoresSearchRepository.search(queryStringQuery("id:" + genitores.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(genitores), PageRequest.of(0, 1), 1));
        // Search the genitores
        restGenitoresMockMvc.perform(get("/api/_search/genitores?query=id:" + genitores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genitores.getId().intValue())))
            .andExpect(jsonPath("$.[*].prontuarioDaMae").value(hasItem(DEFAULT_PRONTUARIO_DA_MAE)))
            .andExpect(jsonPath("$.[*].nomeDaMae").value(hasItem(DEFAULT_NOME_DA_MAE)))
            .andExpect(jsonPath("$.[*].nomeDoPai").value(hasItem(DEFAULT_NOME_DO_PAI)));
    }
}
