package br.com.basis.madre.web.rest;

import br.com.basis.madre.SnffaturaApp;
import br.com.basis.madre.domain.Religiao;
import br.com.basis.madre.repository.ReligiaoRepository;
import br.com.basis.madre.repository.search.ReligiaoSearchRepository;
import br.com.basis.madre.service.ReligiaoService;
import br.com.basis.madre.service.dto.ReligiaoDTO;
import br.com.basis.madre.service.mapper.ReligiaoMapper;

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
 * Integration tests for the {@link ReligiaoResource} REST controller.
 */
@SpringBootTest(classes = SnffaturaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReligiaoResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private ReligiaoRepository religiaoRepository;

    @Autowired
    private ReligiaoMapper religiaoMapper;

    @Autowired
    private ReligiaoService religiaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.ReligiaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReligiaoSearchRepository mockReligiaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReligiaoMockMvc;

    private Religiao religiao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Religiao createEntity(EntityManager em) {
        Religiao religiao = new Religiao()
            .valor(DEFAULT_VALOR);
        return religiao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Religiao createUpdatedEntity(EntityManager em) {
        Religiao religiao = new Religiao()
            .valor(UPDATED_VALOR);
        return religiao;
    }

    @BeforeEach
    public void initTest() {
        religiao = createEntity(em);
    }

    @Test
    @Transactional
    public void createReligiao() throws Exception {
        int databaseSizeBeforeCreate = religiaoRepository.findAll().size();

        // Create the Religiao
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(religiao);
        restReligiaoMockMvc.perform(post("/api/religiaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeCreate + 1);
        Religiao testReligiao = religiaoList.get(religiaoList.size() - 1);
        assertThat(testReligiao.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Religiao in Elasticsearch
        verify(mockReligiaoSearchRepository, times(1)).save(testReligiao);
    }

    @Test
    @Transactional
    public void createReligiaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = religiaoRepository.findAll().size();

        // Create the Religiao with an existing ID
        religiao.setId(1L);
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(religiao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReligiaoMockMvc.perform(post("/api/religiaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Religiao in Elasticsearch
        verify(mockReligiaoSearchRepository, times(0)).save(religiao);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = religiaoRepository.findAll().size();
        // set the field null
        religiao.setValor(null);

        // Create the Religiao, which fails.
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(religiao);

        restReligiaoMockMvc.perform(post("/api/religiaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isBadRequest());

        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReligiaos() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        // Get all the religiaoList
        restReligiaoMockMvc.perform(get("/api/religiaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(religiao.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        // Get the religiao
        restReligiaoMockMvc.perform(get("/api/religiaos/{id}", religiao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(religiao.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingReligiao() throws Exception {
        // Get the religiao
        restReligiaoMockMvc.perform(get("/api/religiaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();

        // Update the religiao
        Religiao updatedReligiao = religiaoRepository.findById(religiao.getId()).get();
        // Disconnect from session so that the updates on updatedReligiao are not directly saved in db
        em.detach(updatedReligiao);
        updatedReligiao
            .valor(UPDATED_VALOR);
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(updatedReligiao);

        restReligiaoMockMvc.perform(put("/api/religiaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isOk());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);
        Religiao testReligiao = religiaoList.get(religiaoList.size() - 1);
        assertThat(testReligiao.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Religiao in Elasticsearch
        verify(mockReligiaoSearchRepository, times(1)).save(testReligiao);
    }

    @Test
    @Transactional
    public void updateNonExistingReligiao() throws Exception {
        int databaseSizeBeforeUpdate = religiaoRepository.findAll().size();

        // Create the Religiao
        ReligiaoDTO religiaoDTO = religiaoMapper.toDto(religiao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReligiaoMockMvc.perform(put("/api/religiaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(religiaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Religiao in the database
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Religiao in Elasticsearch
        verify(mockReligiaoSearchRepository, times(0)).save(religiao);
    }

    @Test
    @Transactional
    public void deleteReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);

        int databaseSizeBeforeDelete = religiaoRepository.findAll().size();

        // Delete the religiao
        restReligiaoMockMvc.perform(delete("/api/religiaos/{id}", religiao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Religiao> religiaoList = religiaoRepository.findAll();
        assertThat(religiaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Religiao in Elasticsearch
        verify(mockReligiaoSearchRepository, times(1)).deleteById(religiao.getId());
    }

    @Test
    @Transactional
    public void searchReligiao() throws Exception {
        // Initialize the database
        religiaoRepository.saveAndFlush(religiao);
        when(mockReligiaoSearchRepository.search(queryStringQuery("id:" + religiao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(religiao), PageRequest.of(0, 1), 1));
        // Search the religiao
        restReligiaoMockMvc.perform(get("/api/_search/religiaos?query=id:" + religiao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(religiao.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
