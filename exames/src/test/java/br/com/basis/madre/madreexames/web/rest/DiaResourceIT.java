package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.Dia;
import br.com.basis.madre.madreexames.repository.DiaRepository;
import br.com.basis.madre.madreexames.repository.search.DiaSearchRepository;
import br.com.basis.madre.madreexames.service.DiaService;
import br.com.basis.madre.madreexames.service.dto.DiaDTO;
import br.com.basis.madre.madreexames.service.mapper.DiaMapper;

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
 * Integration tests for the {@link DiaResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DiaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private DiaRepository diaRepository;

    @Autowired
    private DiaMapper diaMapper;

    @Autowired
    private DiaService diaService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.DiaSearchRepositoryMockConfiguration
     */
    @Autowired
    private DiaSearchRepository mockDiaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiaMockMvc;

    private Dia dia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dia createEntity(EntityManager em) {
        Dia dia = new Dia()
            .nome(DEFAULT_NOME);
        return dia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dia createUpdatedEntity(EntityManager em) {
        Dia dia = new Dia()
            .nome(UPDATED_NOME);
        return dia;
    }

    @BeforeEach
    public void initTest() {
        dia = createEntity(em);
    }

    @Test
    @Transactional
    public void createDia() throws Exception {
        int databaseSizeBeforeCreate = diaRepository.findAll().size();
        // Create the Dia
        DiaDTO diaDTO = diaMapper.toDto(dia);
        restDiaMockMvc.perform(post("/api/dias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaDTO)))
            .andExpect(status().isCreated());

        // Validate the Dia in the database
        List<Dia> diaList = diaRepository.findAll();
        assertThat(diaList).hasSize(databaseSizeBeforeCreate + 1);
        Dia testDia = diaList.get(diaList.size() - 1);
        assertThat(testDia.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Dia in Elasticsearch
        verify(mockDiaSearchRepository, times(1)).save(testDia);
    }

    @Test
    @Transactional
    public void createDiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diaRepository.findAll().size();

        // Create the Dia with an existing ID
        dia.setId(1L);
        DiaDTO diaDTO = diaMapper.toDto(dia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaMockMvc.perform(post("/api/dias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dia in the database
        List<Dia> diaList = diaRepository.findAll();
        assertThat(diaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dia in Elasticsearch
        verify(mockDiaSearchRepository, times(0)).save(dia);
    }


    @Test
    @Transactional
    public void getAllDias() throws Exception {
        // Initialize the database
        diaRepository.saveAndFlush(dia);

        // Get all the diaList
        restDiaMockMvc.perform(get("/api/dias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getDia() throws Exception {
        // Initialize the database
        diaRepository.saveAndFlush(dia);

        // Get the dia
        restDiaMockMvc.perform(get("/api/dias/{id}", dia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dia.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }
    @Test
    @Transactional
    public void getNonExistingDia() throws Exception {
        // Get the dia
        restDiaMockMvc.perform(get("/api/dias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDia() throws Exception {
        // Initialize the database
        diaRepository.saveAndFlush(dia);

        int databaseSizeBeforeUpdate = diaRepository.findAll().size();

        // Update the dia
        Dia updatedDia = diaRepository.findById(dia.getId()).get();
        // Disconnect from session so that the updates on updatedDia are not directly saved in db
        em.detach(updatedDia);
        updatedDia
            .nome(UPDATED_NOME);
        DiaDTO diaDTO = diaMapper.toDto(updatedDia);

        restDiaMockMvc.perform(put("/api/dias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaDTO)))
            .andExpect(status().isOk());

        // Validate the Dia in the database
        List<Dia> diaList = diaRepository.findAll();
        assertThat(diaList).hasSize(databaseSizeBeforeUpdate);
        Dia testDia = diaList.get(diaList.size() - 1);
        assertThat(testDia.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Dia in Elasticsearch
        verify(mockDiaSearchRepository, times(1)).save(testDia);
    }

    @Test
    @Transactional
    public void updateNonExistingDia() throws Exception {
        int databaseSizeBeforeUpdate = diaRepository.findAll().size();

        // Create the Dia
        DiaDTO diaDTO = diaMapper.toDto(dia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaMockMvc.perform(put("/api/dias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dia in the database
        List<Dia> diaList = diaRepository.findAll();
        assertThat(diaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dia in Elasticsearch
        verify(mockDiaSearchRepository, times(0)).save(dia);
    }

    @Test
    @Transactional
    public void deleteDia() throws Exception {
        // Initialize the database
        diaRepository.saveAndFlush(dia);

        int databaseSizeBeforeDelete = diaRepository.findAll().size();

        // Delete the dia
        restDiaMockMvc.perform(delete("/api/dias/{id}", dia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dia> diaList = diaRepository.findAll();
        assertThat(diaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dia in Elasticsearch
        verify(mockDiaSearchRepository, times(1)).deleteById(dia.getId());
    }

    @Test
    @Transactional
    public void searchDia() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        diaRepository.saveAndFlush(dia);
        when(mockDiaSearchRepository.search(queryStringQuery("id:" + dia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dia), PageRequest.of(0, 1), 1));

        // Search the dia
        restDiaMockMvc.perform(get("/api/_search/dias?query=id:" + dia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
}
