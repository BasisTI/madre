package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.Recipiente;
import br.com.basis.madre.madreexames.repository.RecipienteRepository;
import br.com.basis.madre.madreexames.repository.search.RecipienteSearchRepository;
import br.com.basis.madre.madreexames.service.RecipienteService;
import br.com.basis.madre.madreexames.service.dto.RecipienteDTO;
import br.com.basis.madre.madreexames.service.mapper.RecipienteMapper;

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
 * Integration tests for the {@link RecipienteResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecipienteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANTICOAGULANTE = false;
    private static final Boolean UPDATED_ANTICOAGULANTE = true;

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private RecipienteRepository recipienteRepository;

    @Autowired
    private RecipienteMapper recipienteMapper;

    @Autowired
    private RecipienteService recipienteService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.RecipienteSearchRepositoryMockConfiguration
     */
    @Autowired
    private RecipienteSearchRepository mockRecipienteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipienteMockMvc;

    private Recipiente recipiente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipiente createEntity(EntityManager em) {
        Recipiente recipiente = new Recipiente()
            .nome(DEFAULT_NOME)
            .anticoagulante(DEFAULT_ANTICOAGULANTE)
            .ativo(DEFAULT_ATIVO);
        return recipiente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipiente createUpdatedEntity(EntityManager em) {
        Recipiente recipiente = new Recipiente()
            .nome(UPDATED_NOME)
            .anticoagulante(UPDATED_ANTICOAGULANTE)
            .ativo(UPDATED_ATIVO);
        return recipiente;
    }

    @BeforeEach
    public void initTest() {
        recipiente = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipiente() throws Exception {
        int databaseSizeBeforeCreate = recipienteRepository.findAll().size();
        // Create the Recipiente
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);
        restRecipienteMockMvc.perform(post("/api/recipientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Recipiente in the database
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeCreate + 1);
        Recipiente testRecipiente = recipienteList.get(recipienteList.size() - 1);
        assertThat(testRecipiente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testRecipiente.isAnticoagulante()).isEqualTo(DEFAULT_ANTICOAGULANTE);
        assertThat(testRecipiente.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the Recipiente in Elasticsearch
        verify(mockRecipienteSearchRepository, times(1)).save(testRecipiente);
    }

    @Test
    @Transactional
    public void createRecipienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipienteRepository.findAll().size();

        // Create the Recipiente with an existing ID
        recipiente.setId(1L);
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipienteMockMvc.perform(post("/api/recipientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipiente in the database
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Recipiente in Elasticsearch
        verify(mockRecipienteSearchRepository, times(0)).save(recipiente);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipienteRepository.findAll().size();
        // set the field null
        recipiente.setNome(null);

        // Create the Recipiente, which fails.
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);


        restRecipienteMockMvc.perform(post("/api/recipientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isBadRequest());

        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnticoagulanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipienteRepository.findAll().size();
        // set the field null
        recipiente.setAnticoagulante(null);

        // Create the Recipiente, which fails.
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);


        restRecipienteMockMvc.perform(post("/api/recipientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isBadRequest());

        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipienteRepository.findAll().size();
        // set the field null
        recipiente.setAtivo(null);

        // Create the Recipiente, which fails.
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);


        restRecipienteMockMvc.perform(post("/api/recipientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isBadRequest());

        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipientes() throws Exception {
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);

        // Get all the recipienteList
        restRecipienteMockMvc.perform(get("/api/recipientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipiente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].anticoagulante").value(hasItem(DEFAULT_ANTICOAGULANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRecipiente() throws Exception {
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);

        // Get the recipiente
        restRecipienteMockMvc.perform(get("/api/recipientes/{id}", recipiente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipiente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.anticoagulante").value(DEFAULT_ANTICOAGULANTE.booleanValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingRecipiente() throws Exception {
        // Get the recipiente
        restRecipienteMockMvc.perform(get("/api/recipientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipiente() throws Exception {
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);

        int databaseSizeBeforeUpdate = recipienteRepository.findAll().size();

        // Update the recipiente
        Recipiente updatedRecipiente = recipienteRepository.findById(recipiente.getId()).get();
        // Disconnect from session so that the updates on updatedRecipiente are not directly saved in db
        em.detach(updatedRecipiente);
        updatedRecipiente
            .nome(UPDATED_NOME)
            .anticoagulante(UPDATED_ANTICOAGULANTE)
            .ativo(UPDATED_ATIVO);
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(updatedRecipiente);

        restRecipienteMockMvc.perform(put("/api/recipientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isOk());

        // Validate the Recipiente in the database
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeUpdate);
        Recipiente testRecipiente = recipienteList.get(recipienteList.size() - 1);
        assertThat(testRecipiente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testRecipiente.isAnticoagulante()).isEqualTo(UPDATED_ANTICOAGULANTE);
        assertThat(testRecipiente.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the Recipiente in Elasticsearch
        verify(mockRecipienteSearchRepository, times(1)).save(testRecipiente);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipiente() throws Exception {
        int databaseSizeBeforeUpdate = recipienteRepository.findAll().size();

        // Create the Recipiente
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipienteMockMvc.perform(put("/api/recipientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipiente in the database
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Recipiente in Elasticsearch
        verify(mockRecipienteSearchRepository, times(0)).save(recipiente);
    }

    @Test
    @Transactional
    public void deleteRecipiente() throws Exception {
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);

        int databaseSizeBeforeDelete = recipienteRepository.findAll().size();

        // Delete the recipiente
        restRecipienteMockMvc.perform(delete("/api/recipientes/{id}", recipiente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Recipiente in Elasticsearch
        verify(mockRecipienteSearchRepository, times(1)).deleteById(recipiente.getId());
    }

    @Test
    @Transactional
    public void searchRecipiente() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);
        when(mockRecipienteSearchRepository.search(queryStringQuery("id:" + recipiente.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(recipiente), PageRequest.of(0, 1), 1));

        // Search the recipiente
        restRecipienteMockMvc.perform(get("/api/_search/recipientes?query=id:" + recipiente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipiente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].anticoagulante").value(hasItem(DEFAULT_ANTICOAGULANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
}
