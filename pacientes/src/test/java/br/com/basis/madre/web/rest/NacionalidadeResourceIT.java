package br.com.basis.madre.web.rest;

import br.com.basis.madre.SnffaturaApp;
import br.com.basis.madre.domain.Nacionalidade;
import br.com.basis.madre.repository.NacionalidadeRepository;
import br.com.basis.madre.repository.search.NacionalidadeSearchRepository;
import br.com.basis.madre.service.NacionalidadeService;
import br.com.basis.madre.service.dto.NacionalidadeDTO;
import br.com.basis.madre.service.mapper.NacionalidadeMapper;

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
 * Integration tests for the {@link NacionalidadeResource} REST controller.
 */
@SpringBootTest(classes = SnffaturaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class NacionalidadeResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private NacionalidadeRepository nacionalidadeRepository;

    @Autowired
    private NacionalidadeMapper nacionalidadeMapper;

    @Autowired
    private NacionalidadeService nacionalidadeService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.NacionalidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private NacionalidadeSearchRepository mockNacionalidadeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNacionalidadeMockMvc;

    private Nacionalidade nacionalidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nacionalidade createEntity(EntityManager em) {
        Nacionalidade nacionalidade = new Nacionalidade()
            .valor(DEFAULT_VALOR);
        return nacionalidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nacionalidade createUpdatedEntity(EntityManager em) {
        Nacionalidade nacionalidade = new Nacionalidade()
            .valor(UPDATED_VALOR);
        return nacionalidade;
    }

    @BeforeEach
    public void initTest() {
        nacionalidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createNacionalidade() throws Exception {
        int databaseSizeBeforeCreate = nacionalidadeRepository.findAll().size();

        // Create the Nacionalidade
        NacionalidadeDTO nacionalidadeDTO = nacionalidadeMapper.toDto(nacionalidade);
        restNacionalidadeMockMvc.perform(post("/api/nacionalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Nacionalidade in the database
        List<Nacionalidade> nacionalidadeList = nacionalidadeRepository.findAll();
        assertThat(nacionalidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Nacionalidade testNacionalidade = nacionalidadeList.get(nacionalidadeList.size() - 1);
        assertThat(testNacionalidade.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Nacionalidade in Elasticsearch
        verify(mockNacionalidadeSearchRepository, times(1)).save(testNacionalidade);
    }

    @Test
    @Transactional
    public void createNacionalidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nacionalidadeRepository.findAll().size();

        // Create the Nacionalidade with an existing ID
        nacionalidade.setId(1L);
        NacionalidadeDTO nacionalidadeDTO = nacionalidadeMapper.toDto(nacionalidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNacionalidadeMockMvc.perform(post("/api/nacionalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nacionalidade in the database
        List<Nacionalidade> nacionalidadeList = nacionalidadeRepository.findAll();
        assertThat(nacionalidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Nacionalidade in Elasticsearch
        verify(mockNacionalidadeSearchRepository, times(0)).save(nacionalidade);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = nacionalidadeRepository.findAll().size();
        // set the field null
        nacionalidade.setValor(null);

        // Create the Nacionalidade, which fails.
        NacionalidadeDTO nacionalidadeDTO = nacionalidadeMapper.toDto(nacionalidade);

        restNacionalidadeMockMvc.perform(post("/api/nacionalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Nacionalidade> nacionalidadeList = nacionalidadeRepository.findAll();
        assertThat(nacionalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNacionalidades() throws Exception {
        // Initialize the database
        nacionalidadeRepository.saveAndFlush(nacionalidade);

        // Get all the nacionalidadeList
        restNacionalidadeMockMvc.perform(get("/api/nacionalidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nacionalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getNacionalidade() throws Exception {
        // Initialize the database
        nacionalidadeRepository.saveAndFlush(nacionalidade);

        // Get the nacionalidade
        restNacionalidadeMockMvc.perform(get("/api/nacionalidades/{id}", nacionalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nacionalidade.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingNacionalidade() throws Exception {
        // Get the nacionalidade
        restNacionalidadeMockMvc.perform(get("/api/nacionalidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNacionalidade() throws Exception {
        // Initialize the database
        nacionalidadeRepository.saveAndFlush(nacionalidade);

        int databaseSizeBeforeUpdate = nacionalidadeRepository.findAll().size();

        // Update the nacionalidade
        Nacionalidade updatedNacionalidade = nacionalidadeRepository.findById(nacionalidade.getId()).get();
        // Disconnect from session so that the updates on updatedNacionalidade are not directly saved in db
        em.detach(updatedNacionalidade);
        updatedNacionalidade
            .valor(UPDATED_VALOR);
        NacionalidadeDTO nacionalidadeDTO = nacionalidadeMapper.toDto(updatedNacionalidade);

        restNacionalidadeMockMvc.perform(put("/api/nacionalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Nacionalidade in the database
        List<Nacionalidade> nacionalidadeList = nacionalidadeRepository.findAll();
        assertThat(nacionalidadeList).hasSize(databaseSizeBeforeUpdate);
        Nacionalidade testNacionalidade = nacionalidadeList.get(nacionalidadeList.size() - 1);
        assertThat(testNacionalidade.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Nacionalidade in Elasticsearch
        verify(mockNacionalidadeSearchRepository, times(1)).save(testNacionalidade);
    }

    @Test
    @Transactional
    public void updateNonExistingNacionalidade() throws Exception {
        int databaseSizeBeforeUpdate = nacionalidadeRepository.findAll().size();

        // Create the Nacionalidade
        NacionalidadeDTO nacionalidadeDTO = nacionalidadeMapper.toDto(nacionalidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNacionalidadeMockMvc.perform(put("/api/nacionalidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nacionalidade in the database
        List<Nacionalidade> nacionalidadeList = nacionalidadeRepository.findAll();
        assertThat(nacionalidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Nacionalidade in Elasticsearch
        verify(mockNacionalidadeSearchRepository, times(0)).save(nacionalidade);
    }

    @Test
    @Transactional
    public void deleteNacionalidade() throws Exception {
        // Initialize the database
        nacionalidadeRepository.saveAndFlush(nacionalidade);

        int databaseSizeBeforeDelete = nacionalidadeRepository.findAll().size();

        // Delete the nacionalidade
        restNacionalidadeMockMvc.perform(delete("/api/nacionalidades/{id}", nacionalidade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nacionalidade> nacionalidadeList = nacionalidadeRepository.findAll();
        assertThat(nacionalidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Nacionalidade in Elasticsearch
        verify(mockNacionalidadeSearchRepository, times(1)).deleteById(nacionalidade.getId());
    }

    @Test
    @Transactional
    public void searchNacionalidade() throws Exception {
        // Initialize the database
        nacionalidadeRepository.saveAndFlush(nacionalidade);
        when(mockNacionalidadeSearchRepository.search(queryStringQuery("id:" + nacionalidade.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(nacionalidade), PageRequest.of(0, 1), 1));
        // Search the nacionalidade
        restNacionalidadeMockMvc.perform(get("/api/_search/nacionalidades?query=id:" + nacionalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nacionalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
