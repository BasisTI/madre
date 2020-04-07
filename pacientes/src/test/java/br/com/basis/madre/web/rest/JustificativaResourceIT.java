package br.com.basis.madre.web.rest;

import br.com.basis.madre.SnffaturaApp;
import br.com.basis.madre.domain.Justificativa;
import br.com.basis.madre.repository.JustificativaRepository;
import br.com.basis.madre.repository.search.JustificativaSearchRepository;
import br.com.basis.madre.service.JustificativaService;
import br.com.basis.madre.service.dto.JustificativaDTO;
import br.com.basis.madre.service.mapper.JustificativaMapper;

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
 * Integration tests for the {@link JustificativaResource} REST controller.
 */
@SpringBootTest(classes = SnffaturaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class JustificativaResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private JustificativaRepository justificativaRepository;

    @Autowired
    private JustificativaMapper justificativaMapper;

    @Autowired
    private JustificativaService justificativaService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.JustificativaSearchRepositoryMockConfiguration
     */
    @Autowired
    private JustificativaSearchRepository mockJustificativaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJustificativaMockMvc;

    private Justificativa justificativa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Justificativa createEntity(EntityManager em) {
        Justificativa justificativa = new Justificativa()
            .valor(DEFAULT_VALOR);
        return justificativa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Justificativa createUpdatedEntity(EntityManager em) {
        Justificativa justificativa = new Justificativa()
            .valor(UPDATED_VALOR);
        return justificativa;
    }

    @BeforeEach
    public void initTest() {
        justificativa = createEntity(em);
    }

    @Test
    @Transactional
    public void createJustificativa() throws Exception {
        int databaseSizeBeforeCreate = justificativaRepository.findAll().size();

        // Create the Justificativa
        JustificativaDTO justificativaDTO = justificativaMapper.toDto(justificativa);
        restJustificativaMockMvc.perform(post("/api/justificativas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(justificativaDTO)))
            .andExpect(status().isCreated());

        // Validate the Justificativa in the database
        List<Justificativa> justificativaList = justificativaRepository.findAll();
        assertThat(justificativaList).hasSize(databaseSizeBeforeCreate + 1);
        Justificativa testJustificativa = justificativaList.get(justificativaList.size() - 1);
        assertThat(testJustificativa.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Justificativa in Elasticsearch
        verify(mockJustificativaSearchRepository, times(1)).save(testJustificativa);
    }

    @Test
    @Transactional
    public void createJustificativaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = justificativaRepository.findAll().size();

        // Create the Justificativa with an existing ID
        justificativa.setId(1L);
        JustificativaDTO justificativaDTO = justificativaMapper.toDto(justificativa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJustificativaMockMvc.perform(post("/api/justificativas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(justificativaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Justificativa in the database
        List<Justificativa> justificativaList = justificativaRepository.findAll();
        assertThat(justificativaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Justificativa in Elasticsearch
        verify(mockJustificativaSearchRepository, times(0)).save(justificativa);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = justificativaRepository.findAll().size();
        // set the field null
        justificativa.setValor(null);

        // Create the Justificativa, which fails.
        JustificativaDTO justificativaDTO = justificativaMapper.toDto(justificativa);

        restJustificativaMockMvc.perform(post("/api/justificativas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(justificativaDTO)))
            .andExpect(status().isBadRequest());

        List<Justificativa> justificativaList = justificativaRepository.findAll();
        assertThat(justificativaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJustificativas() throws Exception {
        // Initialize the database
        justificativaRepository.saveAndFlush(justificativa);

        // Get all the justificativaList
        restJustificativaMockMvc.perform(get("/api/justificativas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(justificativa.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getJustificativa() throws Exception {
        // Initialize the database
        justificativaRepository.saveAndFlush(justificativa);

        // Get the justificativa
        restJustificativaMockMvc.perform(get("/api/justificativas/{id}", justificativa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(justificativa.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingJustificativa() throws Exception {
        // Get the justificativa
        restJustificativaMockMvc.perform(get("/api/justificativas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJustificativa() throws Exception {
        // Initialize the database
        justificativaRepository.saveAndFlush(justificativa);

        int databaseSizeBeforeUpdate = justificativaRepository.findAll().size();

        // Update the justificativa
        Justificativa updatedJustificativa = justificativaRepository.findById(justificativa.getId()).get();
        // Disconnect from session so that the updates on updatedJustificativa are not directly saved in db
        em.detach(updatedJustificativa);
        updatedJustificativa
            .valor(UPDATED_VALOR);
        JustificativaDTO justificativaDTO = justificativaMapper.toDto(updatedJustificativa);

        restJustificativaMockMvc.perform(put("/api/justificativas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(justificativaDTO)))
            .andExpect(status().isOk());

        // Validate the Justificativa in the database
        List<Justificativa> justificativaList = justificativaRepository.findAll();
        assertThat(justificativaList).hasSize(databaseSizeBeforeUpdate);
        Justificativa testJustificativa = justificativaList.get(justificativaList.size() - 1);
        assertThat(testJustificativa.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Justificativa in Elasticsearch
        verify(mockJustificativaSearchRepository, times(1)).save(testJustificativa);
    }

    @Test
    @Transactional
    public void updateNonExistingJustificativa() throws Exception {
        int databaseSizeBeforeUpdate = justificativaRepository.findAll().size();

        // Create the Justificativa
        JustificativaDTO justificativaDTO = justificativaMapper.toDto(justificativa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJustificativaMockMvc.perform(put("/api/justificativas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(justificativaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Justificativa in the database
        List<Justificativa> justificativaList = justificativaRepository.findAll();
        assertThat(justificativaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Justificativa in Elasticsearch
        verify(mockJustificativaSearchRepository, times(0)).save(justificativa);
    }

    @Test
    @Transactional
    public void deleteJustificativa() throws Exception {
        // Initialize the database
        justificativaRepository.saveAndFlush(justificativa);

        int databaseSizeBeforeDelete = justificativaRepository.findAll().size();

        // Delete the justificativa
        restJustificativaMockMvc.perform(delete("/api/justificativas/{id}", justificativa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Justificativa> justificativaList = justificativaRepository.findAll();
        assertThat(justificativaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Justificativa in Elasticsearch
        verify(mockJustificativaSearchRepository, times(1)).deleteById(justificativa.getId());
    }

    @Test
    @Transactional
    public void searchJustificativa() throws Exception {
        // Initialize the database
        justificativaRepository.saveAndFlush(justificativa);
        when(mockJustificativaSearchRepository.search(queryStringQuery("id:" + justificativa.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(justificativa), PageRequest.of(0, 1), 1));
        // Search the justificativa
        restJustificativaMockMvc.perform(get("/api/_search/justificativas?query=id:" + justificativa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(justificativa.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
