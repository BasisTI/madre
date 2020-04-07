package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.Naturalidade;
import br.com.basis.madre.repository.NaturalidadeRepository;
import br.com.basis.madre.repository.search.NaturalidadeSearchRepository;
import br.com.basis.madre.service.NaturalidadeService;
import br.com.basis.madre.service.dto.NaturalidadeDTO;
import br.com.basis.madre.service.mapper.NaturalidadeMapper;

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
 * Integration tests for the {@link NaturalidadeResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class NaturalidadeResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private NaturalidadeRepository naturalidadeRepository;

    @Autowired
    private NaturalidadeMapper naturalidadeMapper;

    @Autowired
    private NaturalidadeService naturalidadeService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.NaturalidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private NaturalidadeSearchRepository mockNaturalidadeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaturalidadeMockMvc;

    private Naturalidade naturalidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naturalidade createEntity(EntityManager em) {
        Naturalidade naturalidade = new Naturalidade()
            .valor(DEFAULT_VALOR);
        return naturalidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naturalidade createUpdatedEntity(EntityManager em) {
        Naturalidade naturalidade = new Naturalidade()
            .valor(UPDATED_VALOR);
        return naturalidade;
    }

    @BeforeEach
    public void initTest() {
        naturalidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createNaturalidade() throws Exception {
        int databaseSizeBeforeCreate = naturalidadeRepository.findAll().size();

        // Create the Naturalidade
        NaturalidadeDTO naturalidadeDTO = naturalidadeMapper.toDto(naturalidade);
        restNaturalidadeMockMvc.perform(post("/api/naturalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(naturalidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Naturalidade in the database
        List<Naturalidade> naturalidadeList = naturalidadeRepository.findAll();
        assertThat(naturalidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Naturalidade testNaturalidade = naturalidadeList.get(naturalidadeList.size() - 1);
        assertThat(testNaturalidade.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Naturalidade in Elasticsearch
        verify(mockNaturalidadeSearchRepository, times(1)).save(testNaturalidade);
    }

    @Test
    @Transactional
    public void createNaturalidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = naturalidadeRepository.findAll().size();

        // Create the Naturalidade with an existing ID
        naturalidade.setId(1L);
        NaturalidadeDTO naturalidadeDTO = naturalidadeMapper.toDto(naturalidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaturalidadeMockMvc.perform(post("/api/naturalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(naturalidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Naturalidade in the database
        List<Naturalidade> naturalidadeList = naturalidadeRepository.findAll();
        assertThat(naturalidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Naturalidade in Elasticsearch
        verify(mockNaturalidadeSearchRepository, times(0)).save(naturalidade);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = naturalidadeRepository.findAll().size();
        // set the field null
        naturalidade.setValor(null);

        // Create the Naturalidade, which fails.
        NaturalidadeDTO naturalidadeDTO = naturalidadeMapper.toDto(naturalidade);

        restNaturalidadeMockMvc.perform(post("/api/naturalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(naturalidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Naturalidade> naturalidadeList = naturalidadeRepository.findAll();
        assertThat(naturalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNaturalidades() throws Exception {
        // Initialize the database
        naturalidadeRepository.saveAndFlush(naturalidade);

        // Get all the naturalidadeList
        restNaturalidadeMockMvc.perform(get("/api/naturalidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naturalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getNaturalidade() throws Exception {
        // Initialize the database
        naturalidadeRepository.saveAndFlush(naturalidade);

        // Get the naturalidade
        restNaturalidadeMockMvc.perform(get("/api/naturalidades/{id}", naturalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naturalidade.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingNaturalidade() throws Exception {
        // Get the naturalidade
        restNaturalidadeMockMvc.perform(get("/api/naturalidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNaturalidade() throws Exception {
        // Initialize the database
        naturalidadeRepository.saveAndFlush(naturalidade);

        int databaseSizeBeforeUpdate = naturalidadeRepository.findAll().size();

        // Update the naturalidade
        Naturalidade updatedNaturalidade = naturalidadeRepository.findById(naturalidade.getId()).get();
        // Disconnect from session so that the updates on updatedNaturalidade are not directly saved in db
        em.detach(updatedNaturalidade);
        updatedNaturalidade
            .valor(UPDATED_VALOR);
        NaturalidadeDTO naturalidadeDTO = naturalidadeMapper.toDto(updatedNaturalidade);

        restNaturalidadeMockMvc.perform(put("/api/naturalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(naturalidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Naturalidade in the database
        List<Naturalidade> naturalidadeList = naturalidadeRepository.findAll();
        assertThat(naturalidadeList).hasSize(databaseSizeBeforeUpdate);
        Naturalidade testNaturalidade = naturalidadeList.get(naturalidadeList.size() - 1);
        assertThat(testNaturalidade.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Naturalidade in Elasticsearch
        verify(mockNaturalidadeSearchRepository, times(1)).save(testNaturalidade);
    }

    @Test
    @Transactional
    public void updateNonExistingNaturalidade() throws Exception {
        int databaseSizeBeforeUpdate = naturalidadeRepository.findAll().size();

        // Create the Naturalidade
        NaturalidadeDTO naturalidadeDTO = naturalidadeMapper.toDto(naturalidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaturalidadeMockMvc.perform(put("/api/naturalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(naturalidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Naturalidade in the database
        List<Naturalidade> naturalidadeList = naturalidadeRepository.findAll();
        assertThat(naturalidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Naturalidade in Elasticsearch
        verify(mockNaturalidadeSearchRepository, times(0)).save(naturalidade);
    }

    @Test
    @Transactional
    public void deleteNaturalidade() throws Exception {
        // Initialize the database
        naturalidadeRepository.saveAndFlush(naturalidade);

        int databaseSizeBeforeDelete = naturalidadeRepository.findAll().size();

        // Delete the naturalidade
        restNaturalidadeMockMvc.perform(delete("/api/naturalidades/{id}", naturalidade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Naturalidade> naturalidadeList = naturalidadeRepository.findAll();
        assertThat(naturalidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Naturalidade in Elasticsearch
        verify(mockNaturalidadeSearchRepository, times(1)).deleteById(naturalidade.getId());
    }

    @Test
    @Transactional
    public void searchNaturalidade() throws Exception {
        // Initialize the database
        naturalidadeRepository.saveAndFlush(naturalidade);
        when(mockNaturalidadeSearchRepository.search(queryStringQuery("id:" + naturalidade.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(naturalidade), PageRequest.of(0, 1), 1));
        // Search the naturalidade
        restNaturalidadeMockMvc.perform(get("/api/_search/naturalidades?query=id:" + naturalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naturalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
