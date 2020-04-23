package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.EstadoCivil;
import br.com.basis.madre.repository.EstadoCivilRepository;
import br.com.basis.madre.repository.search.EstadoCivilSearchRepository;
import br.com.basis.madre.service.EstadoCivilService;
import br.com.basis.madre.service.dto.EstadoCivilDTO;
import br.com.basis.madre.service.mapper.EstadoCivilMapper;

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
 * Integration tests for the {@link EstadoCivilResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EstadoCivilResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    @Autowired
    private EstadoCivilMapper estadoCivilMapper;

    @Autowired
    private EstadoCivilService estadoCivilService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.EstadoCivilSearchRepositoryMockConfiguration
     */
    @Autowired
    private EstadoCivilSearchRepository mockEstadoCivilSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoCivilMockMvc;

    private EstadoCivil estadoCivil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoCivil createEntity(EntityManager em) {
        EstadoCivil estadoCivil = new EstadoCivil()
            .valor(DEFAULT_VALOR);
        return estadoCivil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoCivil createUpdatedEntity(EntityManager em) {
        EstadoCivil estadoCivil = new EstadoCivil()
            .valor(UPDATED_VALOR);
        return estadoCivil;
    }

    @BeforeEach
    public void initTest() {
        estadoCivil = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstadoCivil() throws Exception {
        int databaseSizeBeforeCreate = estadoCivilRepository.findAll().size();

        // Create the EstadoCivil
        EstadoCivilDTO estadoCivilDTO = estadoCivilMapper.toDto(estadoCivil);
        restEstadoCivilMockMvc.perform(post("/api/estado-civils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCivilDTO)))
            .andExpect(status().isCreated());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoCivil testEstadoCivil = estadoCivilList.get(estadoCivilList.size() - 1);
        assertThat(testEstadoCivil.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the EstadoCivil in Elasticsearch
        verify(mockEstadoCivilSearchRepository, times(1)).save(testEstadoCivil);
    }

    @Test
    @Transactional
    public void createEstadoCivilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadoCivilRepository.findAll().size();

        // Create the EstadoCivil with an existing ID
        estadoCivil.setId(1L);
        EstadoCivilDTO estadoCivilDTO = estadoCivilMapper.toDto(estadoCivil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoCivilMockMvc.perform(post("/api/estado-civils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCivilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeCreate);

        // Validate the EstadoCivil in Elasticsearch
        verify(mockEstadoCivilSearchRepository, times(0)).save(estadoCivil);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoCivilRepository.findAll().size();
        // set the field null
        estadoCivil.setValor(null);

        // Create the EstadoCivil, which fails.
        EstadoCivilDTO estadoCivilDTO = estadoCivilMapper.toDto(estadoCivil);

        restEstadoCivilMockMvc.perform(post("/api/estado-civils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCivilDTO)))
            .andExpect(status().isBadRequest());

        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstadoCivils() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        // Get all the estadoCivilList
        restEstadoCivilMockMvc.perform(get("/api/estado-civils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoCivil.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getEstadoCivil() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        // Get the estadoCivil
        restEstadoCivilMockMvc.perform(get("/api/estado-civils/{id}", estadoCivil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoCivil.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingEstadoCivil() throws Exception {
        // Get the estadoCivil
        restEstadoCivilMockMvc.perform(get("/api/estado-civils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadoCivil() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();

        // Update the estadoCivil
        EstadoCivil updatedEstadoCivil = estadoCivilRepository.findById(estadoCivil.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoCivil are not directly saved in db
        em.detach(updatedEstadoCivil);
        updatedEstadoCivil
            .valor(UPDATED_VALOR);
        EstadoCivilDTO estadoCivilDTO = estadoCivilMapper.toDto(updatedEstadoCivil);

        restEstadoCivilMockMvc.perform(put("/api/estado-civils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCivilDTO)))
            .andExpect(status().isOk());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);
        EstadoCivil testEstadoCivil = estadoCivilList.get(estadoCivilList.size() - 1);
        assertThat(testEstadoCivil.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the EstadoCivil in Elasticsearch
        verify(mockEstadoCivilSearchRepository, times(1)).save(testEstadoCivil);
    }

    @Test
    @Transactional
    public void updateNonExistingEstadoCivil() throws Exception {
        int databaseSizeBeforeUpdate = estadoCivilRepository.findAll().size();

        // Create the EstadoCivil
        EstadoCivilDTO estadoCivilDTO = estadoCivilMapper.toDto(estadoCivil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoCivilMockMvc.perform(put("/api/estado-civils")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoCivilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoCivil in the database
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EstadoCivil in Elasticsearch
        verify(mockEstadoCivilSearchRepository, times(0)).save(estadoCivil);
    }

    @Test
    @Transactional
    public void deleteEstadoCivil() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);

        int databaseSizeBeforeDelete = estadoCivilRepository.findAll().size();

        // Delete the estadoCivil
        restEstadoCivilMockMvc.perform(delete("/api/estado-civils/{id}", estadoCivil.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoCivil> estadoCivilList = estadoCivilRepository.findAll();
        assertThat(estadoCivilList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EstadoCivil in Elasticsearch
        verify(mockEstadoCivilSearchRepository, times(1)).deleteById(estadoCivil.getId());
    }

    @Test
    @Transactional
    public void searchEstadoCivil() throws Exception {
        // Initialize the database
        estadoCivilRepository.saveAndFlush(estadoCivil);
        when(mockEstadoCivilSearchRepository.search(queryStringQuery("id:" + estadoCivil.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(estadoCivil), PageRequest.of(0, 1), 1));
        // Search the estadoCivil
        restEstadoCivilMockMvc.perform(get("/api/_search/estado-civils?query=id:" + estadoCivil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoCivil.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
