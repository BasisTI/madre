package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Ramal;
import br.com.basis.madre.seguranca.repository.RamalRepository;
import br.com.basis.madre.seguranca.repository.search.RamalSearchRepository;
import br.com.basis.madre.seguranca.service.RamalService;
import br.com.basis.madre.seguranca.service.dto.RamalDTO;
import br.com.basis.madre.seguranca.service.mapper.RamalMapper;

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
 * Integration tests for the {@link RamalResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RamalResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_URGENCIA = false;
    private static final Boolean UPDATED_URGENCIA = true;

    @Autowired
    private RamalRepository ramalRepository;

    @Autowired
    private RamalMapper ramalMapper;

    @Autowired
    private RamalService ramalService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.RamalSearchRepositoryMockConfiguration
     */
    @Autowired
    private RamalSearchRepository mockRamalSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRamalMockMvc;

    private Ramal ramal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ramal createEntity(EntityManager em) {
        Ramal ramal = new Ramal()
            .numero(DEFAULT_NUMERO)
            .urgencia(DEFAULT_URGENCIA);
        return ramal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ramal createUpdatedEntity(EntityManager em) {
        Ramal ramal = new Ramal()
            .numero(UPDATED_NUMERO)
            .urgencia(UPDATED_URGENCIA);
        return ramal;
    }

    @BeforeEach
    public void initTest() {
        ramal = createEntity(em);
    }

    @Test
    @Transactional
    public void createRamal() throws Exception {
        int databaseSizeBeforeCreate = ramalRepository.findAll().size();
        // Create the Ramal
        RamalDTO ramalDTO = ramalMapper.toDto(ramal);
        restRamalMockMvc.perform(post("/api/ramals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ramalDTO)))
            .andExpect(status().isCreated());

        // Validate the Ramal in the database
        List<Ramal> ramalList = ramalRepository.findAll();
        assertThat(ramalList).hasSize(databaseSizeBeforeCreate + 1);
        Ramal testRamal = ramalList.get(ramalList.size() - 1);
        assertThat(testRamal.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testRamal.isUrgencia()).isEqualTo(DEFAULT_URGENCIA);

        // Validate the Ramal in Elasticsearch
        verify(mockRamalSearchRepository, times(1)).save(testRamal);
    }

    @Test
    @Transactional
    public void createRamalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ramalRepository.findAll().size();

        // Create the Ramal with an existing ID
        ramal.setId(1L);
        RamalDTO ramalDTO = ramalMapper.toDto(ramal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRamalMockMvc.perform(post("/api/ramals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ramalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ramal in the database
        List<Ramal> ramalList = ramalRepository.findAll();
        assertThat(ramalList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ramal in Elasticsearch
        verify(mockRamalSearchRepository, times(0)).save(ramal);
    }


    @Test
    @Transactional
    public void getAllRamals() throws Exception {
        // Initialize the database
        ramalRepository.saveAndFlush(ramal);

        // Get all the ramalList
        restRamalMockMvc.perform(get("/api/ramals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ramal.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].urgencia").value(hasItem(DEFAULT_URGENCIA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRamal() throws Exception {
        // Initialize the database
        ramalRepository.saveAndFlush(ramal);

        // Get the ramal
        restRamalMockMvc.perform(get("/api/ramals/{id}", ramal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ramal.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.urgencia").value(DEFAULT_URGENCIA.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingRamal() throws Exception {
        // Get the ramal
        restRamalMockMvc.perform(get("/api/ramals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRamal() throws Exception {
        // Initialize the database
        ramalRepository.saveAndFlush(ramal);

        int databaseSizeBeforeUpdate = ramalRepository.findAll().size();

        // Update the ramal
        Ramal updatedRamal = ramalRepository.findById(ramal.getId()).get();
        // Disconnect from session so that the updates on updatedRamal are not directly saved in db
        em.detach(updatedRamal);
        updatedRamal
            .numero(UPDATED_NUMERO)
            .urgencia(UPDATED_URGENCIA);
        RamalDTO ramalDTO = ramalMapper.toDto(updatedRamal);

        restRamalMockMvc.perform(put("/api/ramals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ramalDTO)))
            .andExpect(status().isOk());

        // Validate the Ramal in the database
        List<Ramal> ramalList = ramalRepository.findAll();
        assertThat(ramalList).hasSize(databaseSizeBeforeUpdate);
        Ramal testRamal = ramalList.get(ramalList.size() - 1);
        assertThat(testRamal.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRamal.isUrgencia()).isEqualTo(UPDATED_URGENCIA);

        // Validate the Ramal in Elasticsearch
        verify(mockRamalSearchRepository, times(1)).save(testRamal);
    }

    @Test
    @Transactional
    public void updateNonExistingRamal() throws Exception {
        int databaseSizeBeforeUpdate = ramalRepository.findAll().size();

        // Create the Ramal
        RamalDTO ramalDTO = ramalMapper.toDto(ramal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRamalMockMvc.perform(put("/api/ramals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ramalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ramal in the database
        List<Ramal> ramalList = ramalRepository.findAll();
        assertThat(ramalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ramal in Elasticsearch
        verify(mockRamalSearchRepository, times(0)).save(ramal);
    }

    @Test
    @Transactional
    public void deleteRamal() throws Exception {
        // Initialize the database
        ramalRepository.saveAndFlush(ramal);

        int databaseSizeBeforeDelete = ramalRepository.findAll().size();

        // Delete the ramal
        restRamalMockMvc.perform(delete("/api/ramals/{id}", ramal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ramal> ramalList = ramalRepository.findAll();
        assertThat(ramalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ramal in Elasticsearch
        verify(mockRamalSearchRepository, times(1)).deleteById(ramal.getId());
    }

    @Test
    @Transactional
    public void searchRamal() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        ramalRepository.saveAndFlush(ramal);
        when(mockRamalSearchRepository.search(queryStringQuery("id:" + ramal.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ramal), PageRequest.of(0, 1), 1));

        // Search the ramal
        restRamalMockMvc.perform(get("/api/_search/ramals?query=id:" + ramal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ramal.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].urgencia").value(hasItem(DEFAULT_URGENCIA.booleanValue())));
    }
}
