package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.TipoAmostra;
import br.com.basis.madre.madreexames.repository.TipoAmostraRepository;
import br.com.basis.madre.madreexames.repository.search.TipoAmostraSearchRepository;
import br.com.basis.madre.madreexames.service.TipoAmostraService;
import br.com.basis.madre.madreexames.service.dto.TipoAmostraDTO;
import br.com.basis.madre.madreexames.service.mapper.TipoAmostraMapper;

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
 * Integration tests for the {@link TipoAmostraResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoAmostraResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TipoAmostraRepository tipoAmostraRepository;

    @Autowired
    private TipoAmostraMapper tipoAmostraMapper;

    @Autowired
    private TipoAmostraService tipoAmostraService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.TipoAmostraSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoAmostraSearchRepository mockTipoAmostraSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoAmostraMockMvc;

    private TipoAmostra tipoAmostra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoAmostra createEntity(EntityManager em) {
        TipoAmostra tipoAmostra = new TipoAmostra()
            .nome(DEFAULT_NOME);
        return tipoAmostra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoAmostra createUpdatedEntity(EntityManager em) {
        TipoAmostra tipoAmostra = new TipoAmostra()
            .nome(UPDATED_NOME);
        return tipoAmostra;
    }

    @BeforeEach
    public void initTest() {
        tipoAmostra = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoAmostra() throws Exception {
        int databaseSizeBeforeCreate = tipoAmostraRepository.findAll().size();
        // Create the TipoAmostra
        TipoAmostraDTO tipoAmostraDTO = tipoAmostraMapper.toDto(tipoAmostra);
        restTipoAmostraMockMvc.perform(post("/api/tipo-amostras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoAmostraDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoAmostra in the database
        List<TipoAmostra> tipoAmostraList = tipoAmostraRepository.findAll();
        assertThat(tipoAmostraList).hasSize(databaseSizeBeforeCreate + 1);
        TipoAmostra testTipoAmostra = tipoAmostraList.get(tipoAmostraList.size() - 1);
        assertThat(testTipoAmostra.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the TipoAmostra in Elasticsearch
        verify(mockTipoAmostraSearchRepository, times(1)).save(testTipoAmostra);
    }

    @Test
    @Transactional
    public void createTipoAmostraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoAmostraRepository.findAll().size();

        // Create the TipoAmostra with an existing ID
        tipoAmostra.setId(1L);
        TipoAmostraDTO tipoAmostraDTO = tipoAmostraMapper.toDto(tipoAmostra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoAmostraMockMvc.perform(post("/api/tipo-amostras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoAmostraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAmostra in the database
        List<TipoAmostra> tipoAmostraList = tipoAmostraRepository.findAll();
        assertThat(tipoAmostraList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoAmostra in Elasticsearch
        verify(mockTipoAmostraSearchRepository, times(0)).save(tipoAmostra);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAmostraRepository.findAll().size();
        // set the field null
        tipoAmostra.setNome(null);

        // Create the TipoAmostra, which fails.
        TipoAmostraDTO tipoAmostraDTO = tipoAmostraMapper.toDto(tipoAmostra);


        restTipoAmostraMockMvc.perform(post("/api/tipo-amostras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoAmostraDTO)))
            .andExpect(status().isBadRequest());

        List<TipoAmostra> tipoAmostraList = tipoAmostraRepository.findAll();
        assertThat(tipoAmostraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoAmostras() throws Exception {
        // Initialize the database
        tipoAmostraRepository.saveAndFlush(tipoAmostra);

        // Get all the tipoAmostraList
        restTipoAmostraMockMvc.perform(get("/api/tipo-amostras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAmostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTipoAmostra() throws Exception {
        // Initialize the database
        tipoAmostraRepository.saveAndFlush(tipoAmostra);

        // Get the tipoAmostra
        restTipoAmostraMockMvc.perform(get("/api/tipo-amostras/{id}", tipoAmostra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoAmostra.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }
    @Test
    @Transactional
    public void getNonExistingTipoAmostra() throws Exception {
        // Get the tipoAmostra
        restTipoAmostraMockMvc.perform(get("/api/tipo-amostras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoAmostra() throws Exception {
        // Initialize the database
        tipoAmostraRepository.saveAndFlush(tipoAmostra);

        int databaseSizeBeforeUpdate = tipoAmostraRepository.findAll().size();

        // Update the tipoAmostra
        TipoAmostra updatedTipoAmostra = tipoAmostraRepository.findById(tipoAmostra.getId()).get();
        // Disconnect from session so that the updates on updatedTipoAmostra are not directly saved in db
        em.detach(updatedTipoAmostra);
        updatedTipoAmostra
            .nome(UPDATED_NOME);
        TipoAmostraDTO tipoAmostraDTO = tipoAmostraMapper.toDto(updatedTipoAmostra);

        restTipoAmostraMockMvc.perform(put("/api/tipo-amostras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoAmostraDTO)))
            .andExpect(status().isOk());

        // Validate the TipoAmostra in the database
        List<TipoAmostra> tipoAmostraList = tipoAmostraRepository.findAll();
        assertThat(tipoAmostraList).hasSize(databaseSizeBeforeUpdate);
        TipoAmostra testTipoAmostra = tipoAmostraList.get(tipoAmostraList.size() - 1);
        assertThat(testTipoAmostra.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the TipoAmostra in Elasticsearch
        verify(mockTipoAmostraSearchRepository, times(1)).save(testTipoAmostra);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoAmostra() throws Exception {
        int databaseSizeBeforeUpdate = tipoAmostraRepository.findAll().size();

        // Create the TipoAmostra
        TipoAmostraDTO tipoAmostraDTO = tipoAmostraMapper.toDto(tipoAmostra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoAmostraMockMvc.perform(put("/api/tipo-amostras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoAmostraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAmostra in the database
        List<TipoAmostra> tipoAmostraList = tipoAmostraRepository.findAll();
        assertThat(tipoAmostraList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoAmostra in Elasticsearch
        verify(mockTipoAmostraSearchRepository, times(0)).save(tipoAmostra);
    }

    @Test
    @Transactional
    public void deleteTipoAmostra() throws Exception {
        // Initialize the database
        tipoAmostraRepository.saveAndFlush(tipoAmostra);

        int databaseSizeBeforeDelete = tipoAmostraRepository.findAll().size();

        // Delete the tipoAmostra
        restTipoAmostraMockMvc.perform(delete("/api/tipo-amostras/{id}", tipoAmostra.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoAmostra> tipoAmostraList = tipoAmostraRepository.findAll();
        assertThat(tipoAmostraList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoAmostra in Elasticsearch
        verify(mockTipoAmostraSearchRepository, times(1)).deleteById(tipoAmostra.getId());
    }

    @Test
    @Transactional
    public void searchTipoAmostra() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        tipoAmostraRepository.saveAndFlush(tipoAmostra);
        when(mockTipoAmostraSearchRepository.search(queryStringQuery("id:" + tipoAmostra.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoAmostra), PageRequest.of(0, 1), 1));

        // Search the tipoAmostra
        restTipoAmostraMockMvc.perform(get("/api/_search/tipo-amostras?query=id:" + tipoAmostra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAmostra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
}
