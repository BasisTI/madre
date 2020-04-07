package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.Raca;
import br.com.basis.madre.repository.RacaRepository;
import br.com.basis.madre.repository.search.RacaSearchRepository;
import br.com.basis.madre.service.RacaService;
import br.com.basis.madre.service.dto.RacaDTO;
import br.com.basis.madre.service.mapper.RacaMapper;

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
 * Integration tests for the {@link RacaResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RacaResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private RacaMapper racaMapper;

    @Autowired
    private RacaService racaService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.RacaSearchRepositoryMockConfiguration
     */
    @Autowired
    private RacaSearchRepository mockRacaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRacaMockMvc;

    private Raca raca;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raca createEntity(EntityManager em) {
        Raca raca = new Raca()
            .valor(DEFAULT_VALOR);
        return raca;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raca createUpdatedEntity(EntityManager em) {
        Raca raca = new Raca()
            .valor(UPDATED_VALOR);
        return raca;
    }

    @BeforeEach
    public void initTest() {
        raca = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaca() throws Exception {
        int databaseSizeBeforeCreate = racaRepository.findAll().size();

        // Create the Raca
        RacaDTO racaDTO = racaMapper.toDto(raca);
        restRacaMockMvc.perform(post("/api/racas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isCreated());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeCreate + 1);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Raca in Elasticsearch
        verify(mockRacaSearchRepository, times(1)).save(testRaca);
    }

    @Test
    @Transactional
    public void createRacaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racaRepository.findAll().size();

        // Create the Raca with an existing ID
        raca.setId(1L);
        RacaDTO racaDTO = racaMapper.toDto(raca);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacaMockMvc.perform(post("/api/racas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Raca in Elasticsearch
        verify(mockRacaSearchRepository, times(0)).save(raca);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = racaRepository.findAll().size();
        // set the field null
        raca.setValor(null);

        // Create the Raca, which fails.
        RacaDTO racaDTO = racaMapper.toDto(raca);

        restRacaMockMvc.perform(post("/api/racas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isBadRequest());

        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRacas() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList
        restRacaMockMvc.perform(get("/api/racas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raca.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get the raca
        restRacaMockMvc.perform(get("/api/racas/{id}", raca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(raca.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingRaca() throws Exception {
        // Get the raca
        restRacaMockMvc.perform(get("/api/racas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Update the raca
        Raca updatedRaca = racaRepository.findById(raca.getId()).get();
        // Disconnect from session so that the updates on updatedRaca are not directly saved in db
        em.detach(updatedRaca);
        updatedRaca
            .valor(UPDATED_VALOR);
        RacaDTO racaDTO = racaMapper.toDto(updatedRaca);

        restRacaMockMvc.perform(put("/api/racas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isOk());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Raca in Elasticsearch
        verify(mockRacaSearchRepository, times(1)).save(testRaca);
    }

    @Test
    @Transactional
    public void updateNonExistingRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Create the Raca
        RacaDTO racaDTO = racaMapper.toDto(raca);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacaMockMvc.perform(put("/api/racas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(racaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Raca in Elasticsearch
        verify(mockRacaSearchRepository, times(0)).save(raca);
    }

    @Test
    @Transactional
    public void deleteRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        int databaseSizeBeforeDelete = racaRepository.findAll().size();

        // Delete the raca
        restRacaMockMvc.perform(delete("/api/racas/{id}", raca.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Raca in Elasticsearch
        verify(mockRacaSearchRepository, times(1)).deleteById(raca.getId());
    }

    @Test
    @Transactional
    public void searchRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);
        when(mockRacaSearchRepository.search(queryStringQuery("id:" + raca.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(raca), PageRequest.of(0, 1), 1));
        // Search the raca
        restRacaMockMvc.perform(get("/api/_search/racas?query=id:" + raca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raca.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
