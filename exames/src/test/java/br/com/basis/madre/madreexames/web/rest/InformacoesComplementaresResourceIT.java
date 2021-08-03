package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.InformacoesComplementares;
import br.com.basis.madre.madreexames.repository.InformacoesComplementaresRepository;
import br.com.basis.madre.madreexames.repository.search.InformacoesComplementaresSearchRepository;
import br.com.basis.madre.madreexames.service.InformacoesComplementaresService;
import br.com.basis.madre.madreexames.service.dto.InformacoesComplementaresDTO;
import br.com.basis.madre.madreexames.service.mapper.InformacoesComplementaresMapper;

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
 * Integration tests for the {@link InformacoesComplementaresResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InformacoesComplementaresResourceIT {

    private static final Integer DEFAULT_PRONTUARIO = 1;
    private static final Integer UPDATED_PRONTUARIO = 2;

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    @Autowired
    private InformacoesComplementaresRepository informacoesComplementaresRepository;

    @Autowired
    private InformacoesComplementaresMapper informacoesComplementaresMapper;

    @Autowired
    private InformacoesComplementaresService informacoesComplementaresService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.InformacoesComplementaresSearchRepositoryMockConfiguration
     */
    @Autowired
    private InformacoesComplementaresSearchRepository mockInformacoesComplementaresSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformacoesComplementaresMockMvc;

    private InformacoesComplementares informacoesComplementares;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformacoesComplementares createEntity(EntityManager em) {
        InformacoesComplementares informacoesComplementares = new InformacoesComplementares()
            .prontuario(DEFAULT_PRONTUARIO)
            .codigo(DEFAULT_CODIGO);
        return informacoesComplementares;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformacoesComplementares createUpdatedEntity(EntityManager em) {
        InformacoesComplementares informacoesComplementares = new InformacoesComplementares()
            .prontuario(UPDATED_PRONTUARIO)
            .codigo(UPDATED_CODIGO);
        return informacoesComplementares;
    }

    @BeforeEach
    public void initTest() {
        informacoesComplementares = createEntity(em);
    }

    @Test
    @Transactional
    public void createInformacoesComplementares() throws Exception {
        int databaseSizeBeforeCreate = informacoesComplementaresRepository.findAll().size();
        // Create the InformacoesComplementares
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(informacoesComplementares);
        restInformacoesComplementaresMockMvc.perform(post("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isCreated());

        // Validate the InformacoesComplementares in the database
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeCreate + 1);
        InformacoesComplementares testInformacoesComplementares = informacoesComplementaresList.get(informacoesComplementaresList.size() - 1);
        assertThat(testInformacoesComplementares.getProntuario()).isEqualTo(DEFAULT_PRONTUARIO);
        assertThat(testInformacoesComplementares.getCodigo()).isEqualTo(DEFAULT_CODIGO);

        // Validate the InformacoesComplementares in Elasticsearch
        verify(mockInformacoesComplementaresSearchRepository, times(1)).save(testInformacoesComplementares);
    }

    @Test
    @Transactional
    public void createInformacoesComplementaresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = informacoesComplementaresRepository.findAll().size();

        // Create the InformacoesComplementares with an existing ID
        informacoesComplementares.setId(1L);
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(informacoesComplementares);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformacoesComplementaresMockMvc.perform(post("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InformacoesComplementares in the database
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeCreate);

        // Validate the InformacoesComplementares in Elasticsearch
        verify(mockInformacoesComplementaresSearchRepository, times(0)).save(informacoesComplementares);
    }


    @Test
    @Transactional
    public void checkProntuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = informacoesComplementaresRepository.findAll().size();
        // set the field null
        informacoesComplementares.setProntuario(null);

        // Create the InformacoesComplementares, which fails.
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(informacoesComplementares);


        restInformacoesComplementaresMockMvc.perform(post("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isBadRequest());

        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        // Get all the informacoesComplementaresList
        restInformacoesComplementaresMockMvc.perform(get("/api/informacoes-complementares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informacoesComplementares.getId().intValue())))
            .andExpect(jsonPath("$.[*].prontuario").value(hasItem(DEFAULT_PRONTUARIO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }
    
    @Test
    @Transactional
    public void getInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        // Get the informacoesComplementares
        restInformacoesComplementaresMockMvc.perform(get("/api/informacoes-complementares/{id}", informacoesComplementares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informacoesComplementares.getId().intValue()))
            .andExpect(jsonPath("$.prontuario").value(DEFAULT_PRONTUARIO))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }
    @Test
    @Transactional
    public void getNonExistingInformacoesComplementares() throws Exception {
        // Get the informacoesComplementares
        restInformacoesComplementaresMockMvc.perform(get("/api/informacoes-complementares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        int databaseSizeBeforeUpdate = informacoesComplementaresRepository.findAll().size();

        // Update the informacoesComplementares
        InformacoesComplementares updatedInformacoesComplementares = informacoesComplementaresRepository.findById(informacoesComplementares.getId()).get();
        // Disconnect from session so that the updates on updatedInformacoesComplementares are not directly saved in db
        em.detach(updatedInformacoesComplementares);
        updatedInformacoesComplementares
            .prontuario(UPDATED_PRONTUARIO)
            .codigo(UPDATED_CODIGO);
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(updatedInformacoesComplementares);

        restInformacoesComplementaresMockMvc.perform(put("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isOk());

        // Validate the InformacoesComplementares in the database
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeUpdate);
        InformacoesComplementares testInformacoesComplementares = informacoesComplementaresList.get(informacoesComplementaresList.size() - 1);
        assertThat(testInformacoesComplementares.getProntuario()).isEqualTo(UPDATED_PRONTUARIO);
        assertThat(testInformacoesComplementares.getCodigo()).isEqualTo(UPDATED_CODIGO);

        // Validate the InformacoesComplementares in Elasticsearch
        verify(mockInformacoesComplementaresSearchRepository, times(1)).save(testInformacoesComplementares);
    }

    @Test
    @Transactional
    public void updateNonExistingInformacoesComplementares() throws Exception {
        int databaseSizeBeforeUpdate = informacoesComplementaresRepository.findAll().size();

        // Create the InformacoesComplementares
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(informacoesComplementares);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformacoesComplementaresMockMvc.perform(put("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InformacoesComplementares in the database
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InformacoesComplementares in Elasticsearch
        verify(mockInformacoesComplementaresSearchRepository, times(0)).save(informacoesComplementares);
    }

    @Test
    @Transactional
    public void deleteInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        int databaseSizeBeforeDelete = informacoesComplementaresRepository.findAll().size();

        // Delete the informacoesComplementares
        restInformacoesComplementaresMockMvc.perform(delete("/api/informacoes-complementares/{id}", informacoesComplementares.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InformacoesComplementares in Elasticsearch
        verify(mockInformacoesComplementaresSearchRepository, times(1)).deleteById(informacoesComplementares.getId());
    }

    @Test
    @Transactional
    public void searchInformacoesComplementares() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);
        when(mockInformacoesComplementaresSearchRepository.search(queryStringQuery("id:" + informacoesComplementares.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(informacoesComplementares), PageRequest.of(0, 1), 1));

        // Search the informacoesComplementares
        restInformacoesComplementaresMockMvc.perform(get("/api/_search/informacoes-complementares?query=id:" + informacoesComplementares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informacoesComplementares.getId().intValue())))
            .andExpect(jsonPath("$.[*].prontuario").value(hasItem(DEFAULT_PRONTUARIO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }
}
