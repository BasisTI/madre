package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.GrauDeParentesco;
import br.com.basis.madre.repository.GrauDeParentescoRepository;
import br.com.basis.madre.repository.search.GrauDeParentescoSearchRepository;
import br.com.basis.madre.service.GrauDeParentescoService;
import br.com.basis.madre.service.dto.GrauDeParentescoDTO;
import br.com.basis.madre.service.mapper.GrauDeParentescoMapper;

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
 * Integration tests for the {@link GrauDeParentescoResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GrauDeParentescoResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private GrauDeParentescoRepository grauDeParentescoRepository;

    @Autowired
    private GrauDeParentescoMapper grauDeParentescoMapper;

    @Autowired
    private GrauDeParentescoService grauDeParentescoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.GrauDeParentescoSearchRepositoryMockConfiguration
     */
    @Autowired
    private GrauDeParentescoSearchRepository mockGrauDeParentescoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrauDeParentescoMockMvc;

    private GrauDeParentesco grauDeParentesco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrauDeParentesco createEntity(EntityManager em) {
        GrauDeParentesco grauDeParentesco = new GrauDeParentesco()
            .valor(DEFAULT_VALOR);
        return grauDeParentesco;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrauDeParentesco createUpdatedEntity(EntityManager em) {
        GrauDeParentesco grauDeParentesco = new GrauDeParentesco()
            .valor(UPDATED_VALOR);
        return grauDeParentesco;
    }

    @BeforeEach
    public void initTest() {
        grauDeParentesco = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrauDeParentesco() throws Exception {
        int databaseSizeBeforeCreate = grauDeParentescoRepository.findAll().size();

        // Create the GrauDeParentesco
        GrauDeParentescoDTO grauDeParentescoDTO = grauDeParentescoMapper.toDto(grauDeParentesco);
        restGrauDeParentescoMockMvc.perform(post("/api/grau-de-parentescos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grauDeParentescoDTO)))
            .andExpect(status().isCreated());

        // Validate the GrauDeParentesco in the database
        List<GrauDeParentesco> grauDeParentescoList = grauDeParentescoRepository.findAll();
        assertThat(grauDeParentescoList).hasSize(databaseSizeBeforeCreate + 1);
        GrauDeParentesco testGrauDeParentesco = grauDeParentescoList.get(grauDeParentescoList.size() - 1);
        assertThat(testGrauDeParentesco.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the GrauDeParentesco in Elasticsearch
        verify(mockGrauDeParentescoSearchRepository, times(1)).save(testGrauDeParentesco);
    }

    @Test
    @Transactional
    public void createGrauDeParentescoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grauDeParentescoRepository.findAll().size();

        // Create the GrauDeParentesco with an existing ID
        grauDeParentesco.setId(1L);
        GrauDeParentescoDTO grauDeParentescoDTO = grauDeParentescoMapper.toDto(grauDeParentesco);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrauDeParentescoMockMvc.perform(post("/api/grau-de-parentescos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grauDeParentescoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrauDeParentesco in the database
        List<GrauDeParentesco> grauDeParentescoList = grauDeParentescoRepository.findAll();
        assertThat(grauDeParentescoList).hasSize(databaseSizeBeforeCreate);

        // Validate the GrauDeParentesco in Elasticsearch
        verify(mockGrauDeParentescoSearchRepository, times(0)).save(grauDeParentesco);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = grauDeParentescoRepository.findAll().size();
        // set the field null
        grauDeParentesco.setValor(null);

        // Create the GrauDeParentesco, which fails.
        GrauDeParentescoDTO grauDeParentescoDTO = grauDeParentescoMapper.toDto(grauDeParentesco);

        restGrauDeParentescoMockMvc.perform(post("/api/grau-de-parentescos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grauDeParentescoDTO)))
            .andExpect(status().isBadRequest());

        List<GrauDeParentesco> grauDeParentescoList = grauDeParentescoRepository.findAll();
        assertThat(grauDeParentescoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrauDeParentescos() throws Exception {
        // Initialize the database
        grauDeParentescoRepository.saveAndFlush(grauDeParentesco);

        // Get all the grauDeParentescoList
        restGrauDeParentescoMockMvc.perform(get("/api/grau-de-parentescos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grauDeParentesco.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getGrauDeParentesco() throws Exception {
        // Initialize the database
        grauDeParentescoRepository.saveAndFlush(grauDeParentesco);

        // Get the grauDeParentesco
        restGrauDeParentescoMockMvc.perform(get("/api/grau-de-parentescos/{id}", grauDeParentesco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grauDeParentesco.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingGrauDeParentesco() throws Exception {
        // Get the grauDeParentesco
        restGrauDeParentescoMockMvc.perform(get("/api/grau-de-parentescos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrauDeParentesco() throws Exception {
        // Initialize the database
        grauDeParentescoRepository.saveAndFlush(grauDeParentesco);

        int databaseSizeBeforeUpdate = grauDeParentescoRepository.findAll().size();

        // Update the grauDeParentesco
        GrauDeParentesco updatedGrauDeParentesco = grauDeParentescoRepository.findById(grauDeParentesco.getId()).get();
        // Disconnect from session so that the updates on updatedGrauDeParentesco are not directly saved in db
        em.detach(updatedGrauDeParentesco);
        updatedGrauDeParentesco
            .valor(UPDATED_VALOR);
        GrauDeParentescoDTO grauDeParentescoDTO = grauDeParentescoMapper.toDto(updatedGrauDeParentesco);

        restGrauDeParentescoMockMvc.perform(put("/api/grau-de-parentescos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grauDeParentescoDTO)))
            .andExpect(status().isOk());

        // Validate the GrauDeParentesco in the database
        List<GrauDeParentesco> grauDeParentescoList = grauDeParentescoRepository.findAll();
        assertThat(grauDeParentescoList).hasSize(databaseSizeBeforeUpdate);
        GrauDeParentesco testGrauDeParentesco = grauDeParentescoList.get(grauDeParentescoList.size() - 1);
        assertThat(testGrauDeParentesco.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the GrauDeParentesco in Elasticsearch
        verify(mockGrauDeParentescoSearchRepository, times(1)).save(testGrauDeParentesco);
    }

    @Test
    @Transactional
    public void updateNonExistingGrauDeParentesco() throws Exception {
        int databaseSizeBeforeUpdate = grauDeParentescoRepository.findAll().size();

        // Create the GrauDeParentesco
        GrauDeParentescoDTO grauDeParentescoDTO = grauDeParentescoMapper.toDto(grauDeParentesco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrauDeParentescoMockMvc.perform(put("/api/grau-de-parentescos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grauDeParentescoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrauDeParentesco in the database
        List<GrauDeParentesco> grauDeParentescoList = grauDeParentescoRepository.findAll();
        assertThat(grauDeParentescoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GrauDeParentesco in Elasticsearch
        verify(mockGrauDeParentescoSearchRepository, times(0)).save(grauDeParentesco);
    }

    @Test
    @Transactional
    public void deleteGrauDeParentesco() throws Exception {
        // Initialize the database
        grauDeParentescoRepository.saveAndFlush(grauDeParentesco);

        int databaseSizeBeforeDelete = grauDeParentescoRepository.findAll().size();

        // Delete the grauDeParentesco
        restGrauDeParentescoMockMvc.perform(delete("/api/grau-de-parentescos/{id}", grauDeParentesco.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrauDeParentesco> grauDeParentescoList = grauDeParentescoRepository.findAll();
        assertThat(grauDeParentescoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GrauDeParentesco in Elasticsearch
        verify(mockGrauDeParentescoSearchRepository, times(1)).deleteById(grauDeParentesco.getId());
    }

    @Test
    @Transactional
    public void searchGrauDeParentesco() throws Exception {
        // Initialize the database
        grauDeParentescoRepository.saveAndFlush(grauDeParentesco);
        when(mockGrauDeParentescoSearchRepository.search(queryStringQuery("id:" + grauDeParentesco.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(grauDeParentesco), PageRequest.of(0, 1), 1));
        // Search the grauDeParentesco
        restGrauDeParentescoMockMvc.perform(get("/api/_search/grau-de-parentescos?query=id:" + grauDeParentesco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grauDeParentesco.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
