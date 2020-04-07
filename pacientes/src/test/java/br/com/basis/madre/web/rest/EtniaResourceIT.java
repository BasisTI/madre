package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.Etnia;
import br.com.basis.madre.repository.EtniaRepository;
import br.com.basis.madre.repository.search.EtniaSearchRepository;
import br.com.basis.madre.service.EtniaService;
import br.com.basis.madre.service.dto.EtniaDTO;
import br.com.basis.madre.service.mapper.EtniaMapper;

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
 * Integration tests for the {@link EtniaResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EtniaResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private EtniaRepository etniaRepository;

    @Autowired
    private EtniaMapper etniaMapper;

    @Autowired
    private EtniaService etniaService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.EtniaSearchRepositoryMockConfiguration
     */
    @Autowired
    private EtniaSearchRepository mockEtniaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtniaMockMvc;

    private Etnia etnia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etnia createEntity(EntityManager em) {
        Etnia etnia = new Etnia()
            .valor(DEFAULT_VALOR);
        return etnia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etnia createUpdatedEntity(EntityManager em) {
        Etnia etnia = new Etnia()
            .valor(UPDATED_VALOR);
        return etnia;
    }

    @BeforeEach
    public void initTest() {
        etnia = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtnia() throws Exception {
        int databaseSizeBeforeCreate = etniaRepository.findAll().size();

        // Create the Etnia
        EtniaDTO etniaDTO = etniaMapper.toDto(etnia);
        restEtniaMockMvc.perform(post("/api/etnias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etniaDTO)))
            .andExpect(status().isCreated());

        // Validate the Etnia in the database
        List<Etnia> etniaList = etniaRepository.findAll();
        assertThat(etniaList).hasSize(databaseSizeBeforeCreate + 1);
        Etnia testEtnia = etniaList.get(etniaList.size() - 1);
        assertThat(testEtnia.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Etnia in Elasticsearch
        verify(mockEtniaSearchRepository, times(1)).save(testEtnia);
    }

    @Test
    @Transactional
    public void createEtniaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etniaRepository.findAll().size();

        // Create the Etnia with an existing ID
        etnia.setId(1L);
        EtniaDTO etniaDTO = etniaMapper.toDto(etnia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtniaMockMvc.perform(post("/api/etnias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etniaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etnia in the database
        List<Etnia> etniaList = etniaRepository.findAll();
        assertThat(etniaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Etnia in Elasticsearch
        verify(mockEtniaSearchRepository, times(0)).save(etnia);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = etniaRepository.findAll().size();
        // set the field null
        etnia.setValor(null);

        // Create the Etnia, which fails.
        EtniaDTO etniaDTO = etniaMapper.toDto(etnia);

        restEtniaMockMvc.perform(post("/api/etnias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etniaDTO)))
            .andExpect(status().isBadRequest());

        List<Etnia> etniaList = etniaRepository.findAll();
        assertThat(etniaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtnias() throws Exception {
        // Initialize the database
        etniaRepository.saveAndFlush(etnia);

        // Get all the etniaList
        restEtniaMockMvc.perform(get("/api/etnias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etnia.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getEtnia() throws Exception {
        // Initialize the database
        etniaRepository.saveAndFlush(etnia);

        // Get the etnia
        restEtniaMockMvc.perform(get("/api/etnias/{id}", etnia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etnia.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingEtnia() throws Exception {
        // Get the etnia
        restEtniaMockMvc.perform(get("/api/etnias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtnia() throws Exception {
        // Initialize the database
        etniaRepository.saveAndFlush(etnia);

        int databaseSizeBeforeUpdate = etniaRepository.findAll().size();

        // Update the etnia
        Etnia updatedEtnia = etniaRepository.findById(etnia.getId()).get();
        // Disconnect from session so that the updates on updatedEtnia are not directly saved in db
        em.detach(updatedEtnia);
        updatedEtnia
            .valor(UPDATED_VALOR);
        EtniaDTO etniaDTO = etniaMapper.toDto(updatedEtnia);

        restEtniaMockMvc.perform(put("/api/etnias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etniaDTO)))
            .andExpect(status().isOk());

        // Validate the Etnia in the database
        List<Etnia> etniaList = etniaRepository.findAll();
        assertThat(etniaList).hasSize(databaseSizeBeforeUpdate);
        Etnia testEtnia = etniaList.get(etniaList.size() - 1);
        assertThat(testEtnia.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Etnia in Elasticsearch
        verify(mockEtniaSearchRepository, times(1)).save(testEtnia);
    }

    @Test
    @Transactional
    public void updateNonExistingEtnia() throws Exception {
        int databaseSizeBeforeUpdate = etniaRepository.findAll().size();

        // Create the Etnia
        EtniaDTO etniaDTO = etniaMapper.toDto(etnia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtniaMockMvc.perform(put("/api/etnias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etniaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etnia in the database
        List<Etnia> etniaList = etniaRepository.findAll();
        assertThat(etniaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Etnia in Elasticsearch
        verify(mockEtniaSearchRepository, times(0)).save(etnia);
    }

    @Test
    @Transactional
    public void deleteEtnia() throws Exception {
        // Initialize the database
        etniaRepository.saveAndFlush(etnia);

        int databaseSizeBeforeDelete = etniaRepository.findAll().size();

        // Delete the etnia
        restEtniaMockMvc.perform(delete("/api/etnias/{id}", etnia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etnia> etniaList = etniaRepository.findAll();
        assertThat(etniaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Etnia in Elasticsearch
        verify(mockEtniaSearchRepository, times(1)).deleteById(etnia.getId());
    }

    @Test
    @Transactional
    public void searchEtnia() throws Exception {
        // Initialize the database
        etniaRepository.saveAndFlush(etnia);
        when(mockEtniaSearchRepository.search(queryStringQuery("id:" + etnia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(etnia), PageRequest.of(0, 1), 1));
        // Search the etnia
        restEtniaMockMvc.perform(get("/api/_search/etnias?query=id:" + etnia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etnia.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
