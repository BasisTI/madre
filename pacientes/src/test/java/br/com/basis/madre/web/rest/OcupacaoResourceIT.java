package br.com.basis.madre.web.rest;

import br.com.basis.madre.SnffaturaApp;
import br.com.basis.madre.domain.Ocupacao;
import br.com.basis.madre.repository.OcupacaoRepository;
import br.com.basis.madre.repository.search.OcupacaoSearchRepository;
import br.com.basis.madre.service.OcupacaoService;
import br.com.basis.madre.service.dto.OcupacaoDTO;
import br.com.basis.madre.service.mapper.OcupacaoMapper;

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
 * Integration tests for the {@link OcupacaoResource} REST controller.
 */
@SpringBootTest(classes = SnffaturaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OcupacaoResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private OcupacaoRepository ocupacaoRepository;

    @Autowired
    private OcupacaoMapper ocupacaoMapper;

    @Autowired
    private OcupacaoService ocupacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.OcupacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private OcupacaoSearchRepository mockOcupacaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOcupacaoMockMvc;

    private Ocupacao ocupacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocupacao createEntity(EntityManager em) {
        Ocupacao ocupacao = new Ocupacao()
            .valor(DEFAULT_VALOR);
        return ocupacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocupacao createUpdatedEntity(EntityManager em) {
        Ocupacao ocupacao = new Ocupacao()
            .valor(UPDATED_VALOR);
        return ocupacao;
    }

    @BeforeEach
    public void initTest() {
        ocupacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createOcupacao() throws Exception {
        int databaseSizeBeforeCreate = ocupacaoRepository.findAll().size();

        // Create the Ocupacao
        OcupacaoDTO ocupacaoDTO = ocupacaoMapper.toDto(ocupacao);
        restOcupacaoMockMvc.perform(post("/api/ocupacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Ocupacao in the database
        List<Ocupacao> ocupacaoList = ocupacaoRepository.findAll();
        assertThat(ocupacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Ocupacao testOcupacao = ocupacaoList.get(ocupacaoList.size() - 1);
        assertThat(testOcupacao.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Ocupacao in Elasticsearch
        verify(mockOcupacaoSearchRepository, times(1)).save(testOcupacao);
    }

    @Test
    @Transactional
    public void createOcupacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ocupacaoRepository.findAll().size();

        // Create the Ocupacao with an existing ID
        ocupacao.setId(1L);
        OcupacaoDTO ocupacaoDTO = ocupacaoMapper.toDto(ocupacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcupacaoMockMvc.perform(post("/api/ocupacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ocupacao in the database
        List<Ocupacao> ocupacaoList = ocupacaoRepository.findAll();
        assertThat(ocupacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ocupacao in Elasticsearch
        verify(mockOcupacaoSearchRepository, times(0)).save(ocupacao);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocupacaoRepository.findAll().size();
        // set the field null
        ocupacao.setValor(null);

        // Create the Ocupacao, which fails.
        OcupacaoDTO ocupacaoDTO = ocupacaoMapper.toDto(ocupacao);

        restOcupacaoMockMvc.perform(post("/api/ocupacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Ocupacao> ocupacaoList = ocupacaoRepository.findAll();
        assertThat(ocupacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOcupacaos() throws Exception {
        // Initialize the database
        ocupacaoRepository.saveAndFlush(ocupacao);

        // Get all the ocupacaoList
        restOcupacaoMockMvc.perform(get("/api/ocupacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocupacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getOcupacao() throws Exception {
        // Initialize the database
        ocupacaoRepository.saveAndFlush(ocupacao);

        // Get the ocupacao
        restOcupacaoMockMvc.perform(get("/api/ocupacaos/{id}", ocupacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ocupacao.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingOcupacao() throws Exception {
        // Get the ocupacao
        restOcupacaoMockMvc.perform(get("/api/ocupacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOcupacao() throws Exception {
        // Initialize the database
        ocupacaoRepository.saveAndFlush(ocupacao);

        int databaseSizeBeforeUpdate = ocupacaoRepository.findAll().size();

        // Update the ocupacao
        Ocupacao updatedOcupacao = ocupacaoRepository.findById(ocupacao.getId()).get();
        // Disconnect from session so that the updates on updatedOcupacao are not directly saved in db
        em.detach(updatedOcupacao);
        updatedOcupacao
            .valor(UPDATED_VALOR);
        OcupacaoDTO ocupacaoDTO = ocupacaoMapper.toDto(updatedOcupacao);

        restOcupacaoMockMvc.perform(put("/api/ocupacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Ocupacao in the database
        List<Ocupacao> ocupacaoList = ocupacaoRepository.findAll();
        assertThat(ocupacaoList).hasSize(databaseSizeBeforeUpdate);
        Ocupacao testOcupacao = ocupacaoList.get(ocupacaoList.size() - 1);
        assertThat(testOcupacao.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Ocupacao in Elasticsearch
        verify(mockOcupacaoSearchRepository, times(1)).save(testOcupacao);
    }

    @Test
    @Transactional
    public void updateNonExistingOcupacao() throws Exception {
        int databaseSizeBeforeUpdate = ocupacaoRepository.findAll().size();

        // Create the Ocupacao
        OcupacaoDTO ocupacaoDTO = ocupacaoMapper.toDto(ocupacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcupacaoMockMvc.perform(put("/api/ocupacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ocupacao in the database
        List<Ocupacao> ocupacaoList = ocupacaoRepository.findAll();
        assertThat(ocupacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ocupacao in Elasticsearch
        verify(mockOcupacaoSearchRepository, times(0)).save(ocupacao);
    }

    @Test
    @Transactional
    public void deleteOcupacao() throws Exception {
        // Initialize the database
        ocupacaoRepository.saveAndFlush(ocupacao);

        int databaseSizeBeforeDelete = ocupacaoRepository.findAll().size();

        // Delete the ocupacao
        restOcupacaoMockMvc.perform(delete("/api/ocupacaos/{id}", ocupacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ocupacao> ocupacaoList = ocupacaoRepository.findAll();
        assertThat(ocupacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ocupacao in Elasticsearch
        verify(mockOcupacaoSearchRepository, times(1)).deleteById(ocupacao.getId());
    }

    @Test
    @Transactional
    public void searchOcupacao() throws Exception {
        // Initialize the database
        ocupacaoRepository.saveAndFlush(ocupacao);
        when(mockOcupacaoSearchRepository.search(queryStringQuery("id:" + ocupacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ocupacao), PageRequest.of(0, 1), 1));
        // Search the ocupacao
        restOcupacaoMockMvc.perform(get("/api/_search/ocupacaos?query=id:" + ocupacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocupacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
