package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.Anticoagulante;
import br.com.basis.madre.madreexames.repository.AnticoagulanteRepository;
import br.com.basis.madre.madreexames.repository.search.AnticoagulanteSearchRepository;
import br.com.basis.madre.madreexames.service.AnticoagulanteService;
import br.com.basis.madre.madreexames.service.dto.AnticoagulanteDTO;
import br.com.basis.madre.madreexames.service.mapper.AnticoagulanteMapper;

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
 * Integration tests for the {@link AnticoagulanteResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnticoagulanteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private AnticoagulanteRepository anticoagulanteRepository;

    @Autowired
    private AnticoagulanteMapper anticoagulanteMapper;

    @Autowired
    private AnticoagulanteService anticoagulanteService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.AnticoagulanteSearchRepositoryMockConfiguration
     */
    @Autowired
    private AnticoagulanteSearchRepository mockAnticoagulanteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnticoagulanteMockMvc;

    private Anticoagulante anticoagulante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anticoagulante createEntity(EntityManager em) {
        Anticoagulante anticoagulante = new Anticoagulante()
            .nome(DEFAULT_NOME)
            .ativo(DEFAULT_ATIVO);
        return anticoagulante;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anticoagulante createUpdatedEntity(EntityManager em) {
        Anticoagulante anticoagulante = new Anticoagulante()
            .nome(UPDATED_NOME)
            .ativo(UPDATED_ATIVO);
        return anticoagulante;
    }

    @BeforeEach
    public void initTest() {
        anticoagulante = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnticoagulante() throws Exception {
        int databaseSizeBeforeCreate = anticoagulanteRepository.findAll().size();
        // Create the Anticoagulante
        AnticoagulanteDTO anticoagulanteDTO = anticoagulanteMapper.toDto(anticoagulante);
        restAnticoagulanteMockMvc.perform(post("/api/anticoagulantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anticoagulanteDTO)))
            .andExpect(status().isCreated());

        // Validate the Anticoagulante in the database
        List<Anticoagulante> anticoagulanteList = anticoagulanteRepository.findAll();
        assertThat(anticoagulanteList).hasSize(databaseSizeBeforeCreate + 1);
        Anticoagulante testAnticoagulante = anticoagulanteList.get(anticoagulanteList.size() - 1);
        assertThat(testAnticoagulante.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAnticoagulante.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the Anticoagulante in Elasticsearch
        verify(mockAnticoagulanteSearchRepository, times(1)).save(testAnticoagulante);
    }

    @Test
    @Transactional
    public void createAnticoagulanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anticoagulanteRepository.findAll().size();

        // Create the Anticoagulante with an existing ID
        anticoagulante.setId(1L);
        AnticoagulanteDTO anticoagulanteDTO = anticoagulanteMapper.toDto(anticoagulante);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnticoagulanteMockMvc.perform(post("/api/anticoagulantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anticoagulanteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anticoagulante in the database
        List<Anticoagulante> anticoagulanteList = anticoagulanteRepository.findAll();
        assertThat(anticoagulanteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Anticoagulante in Elasticsearch
        verify(mockAnticoagulanteSearchRepository, times(0)).save(anticoagulante);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = anticoagulanteRepository.findAll().size();
        // set the field null
        anticoagulante.setNome(null);

        // Create the Anticoagulante, which fails.
        AnticoagulanteDTO anticoagulanteDTO = anticoagulanteMapper.toDto(anticoagulante);


        restAnticoagulanteMockMvc.perform(post("/api/anticoagulantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anticoagulanteDTO)))
            .andExpect(status().isBadRequest());

        List<Anticoagulante> anticoagulanteList = anticoagulanteRepository.findAll();
        assertThat(anticoagulanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = anticoagulanteRepository.findAll().size();
        // set the field null
        anticoagulante.setAtivo(null);

        // Create the Anticoagulante, which fails.
        AnticoagulanteDTO anticoagulanteDTO = anticoagulanteMapper.toDto(anticoagulante);


        restAnticoagulanteMockMvc.perform(post("/api/anticoagulantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anticoagulanteDTO)))
            .andExpect(status().isBadRequest());

        List<Anticoagulante> anticoagulanteList = anticoagulanteRepository.findAll();
        assertThat(anticoagulanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnticoagulantes() throws Exception {
        // Initialize the database
        anticoagulanteRepository.saveAndFlush(anticoagulante);

        // Get all the anticoagulanteList
        restAnticoagulanteMockMvc.perform(get("/api/anticoagulantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anticoagulante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAnticoagulante() throws Exception {
        // Initialize the database
        anticoagulanteRepository.saveAndFlush(anticoagulante);

        // Get the anticoagulante
        restAnticoagulanteMockMvc.perform(get("/api/anticoagulantes/{id}", anticoagulante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anticoagulante.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAnticoagulante() throws Exception {
        // Get the anticoagulante
        restAnticoagulanteMockMvc.perform(get("/api/anticoagulantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnticoagulante() throws Exception {
        // Initialize the database
        anticoagulanteRepository.saveAndFlush(anticoagulante);

        int databaseSizeBeforeUpdate = anticoagulanteRepository.findAll().size();

        // Update the anticoagulante
        Anticoagulante updatedAnticoagulante = anticoagulanteRepository.findById(anticoagulante.getId()).get();
        // Disconnect from session so that the updates on updatedAnticoagulante are not directly saved in db
        em.detach(updatedAnticoagulante);
        updatedAnticoagulante
            .nome(UPDATED_NOME)
            .ativo(UPDATED_ATIVO);
        AnticoagulanteDTO anticoagulanteDTO = anticoagulanteMapper.toDto(updatedAnticoagulante);

        restAnticoagulanteMockMvc.perform(put("/api/anticoagulantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anticoagulanteDTO)))
            .andExpect(status().isOk());

        // Validate the Anticoagulante in the database
        List<Anticoagulante> anticoagulanteList = anticoagulanteRepository.findAll();
        assertThat(anticoagulanteList).hasSize(databaseSizeBeforeUpdate);
        Anticoagulante testAnticoagulante = anticoagulanteList.get(anticoagulanteList.size() - 1);
        assertThat(testAnticoagulante.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAnticoagulante.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the Anticoagulante in Elasticsearch
        verify(mockAnticoagulanteSearchRepository, times(1)).save(testAnticoagulante);
    }

    @Test
    @Transactional
    public void updateNonExistingAnticoagulante() throws Exception {
        int databaseSizeBeforeUpdate = anticoagulanteRepository.findAll().size();

        // Create the Anticoagulante
        AnticoagulanteDTO anticoagulanteDTO = anticoagulanteMapper.toDto(anticoagulante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnticoagulanteMockMvc.perform(put("/api/anticoagulantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anticoagulanteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anticoagulante in the database
        List<Anticoagulante> anticoagulanteList = anticoagulanteRepository.findAll();
        assertThat(anticoagulanteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Anticoagulante in Elasticsearch
        verify(mockAnticoagulanteSearchRepository, times(0)).save(anticoagulante);
    }

    @Test
    @Transactional
    public void deleteAnticoagulante() throws Exception {
        // Initialize the database
        anticoagulanteRepository.saveAndFlush(anticoagulante);

        int databaseSizeBeforeDelete = anticoagulanteRepository.findAll().size();

        // Delete the anticoagulante
        restAnticoagulanteMockMvc.perform(delete("/api/anticoagulantes/{id}", anticoagulante.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anticoagulante> anticoagulanteList = anticoagulanteRepository.findAll();
        assertThat(anticoagulanteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Anticoagulante in Elasticsearch
        verify(mockAnticoagulanteSearchRepository, times(1)).deleteById(anticoagulante.getId());
    }

    @Test
    @Transactional
    public void searchAnticoagulante() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        anticoagulanteRepository.saveAndFlush(anticoagulante);
        when(mockAnticoagulanteSearchRepository.search(queryStringQuery("id:" + anticoagulante.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(anticoagulante), PageRequest.of(0, 1), 1));

        // Search the anticoagulante
        restAnticoagulanteMockMvc.perform(get("/api/_search/anticoagulantes?query=id:" + anticoagulante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anticoagulante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
}
