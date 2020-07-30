package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.SituacaoRequisicao;
import br.com.basis.suprimentos.repository.SituacaoRequisicaoRepository;
import br.com.basis.suprimentos.repository.search.SituacaoRequisicaoSearchRepository;
import br.com.basis.suprimentos.service.SituacaoRequisicaoService;
import br.com.basis.suprimentos.service.dto.SituacaoRequisicaoDTO;
import br.com.basis.suprimentos.service.mapper.SituacaoRequisicaoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link SituacaoRequisicaoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SituacaoRequisicaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private SituacaoRequisicaoRepository situacaoRequisicaoRepository;

    @Autowired
    private SituacaoRequisicaoMapper situacaoRequisicaoMapper;

    @Autowired
    private SituacaoRequisicaoService situacaoRequisicaoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.SituacaoRequisicaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private SituacaoRequisicaoSearchRepository mockSituacaoRequisicaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSituacaoRequisicaoMockMvc;

    private SituacaoRequisicao situacaoRequisicao;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituacaoRequisicao createEntity(EntityManager em) {
        SituacaoRequisicao situacaoRequisicao = new SituacaoRequisicao();
        situacaoRequisicao.setDescricao(DEFAULT_DESCRICAO);
        return situacaoRequisicao;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituacaoRequisicao createUpdatedEntity(EntityManager em) {
        SituacaoRequisicao situacaoRequisicao = new SituacaoRequisicao();
        situacaoRequisicao.setDescricao(UPDATED_DESCRICAO);
        return situacaoRequisicao;
    }

    @BeforeEach
    public void initTest() {
        situacaoRequisicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createSituacaoRequisicao() throws Exception {
        int databaseSizeBeforeCreate = situacaoRequisicaoRepository.findAll().size();
        // Create the SituacaoRequisicao
        SituacaoRequisicaoDTO situacaoRequisicaoDTO = situacaoRequisicaoMapper.toDto(situacaoRequisicao);
        restSituacaoRequisicaoMockMvc.perform(post("/api/situacao-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situacaoRequisicaoDTO)))
            .andExpect(status().isCreated());

        // Validate the SituacaoRequisicao in the database
        List<SituacaoRequisicao> situacaoRequisicaoList = situacaoRequisicaoRepository.findAll();
        assertThat(situacaoRequisicaoList).hasSize(databaseSizeBeforeCreate + 1);
        SituacaoRequisicao testSituacaoRequisicao = situacaoRequisicaoList.get(situacaoRequisicaoList.size() - 1);
        assertThat(testSituacaoRequisicao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the SituacaoRequisicao in Elasticsearch
        verify(mockSituacaoRequisicaoSearchRepository, times(1)).save(testSituacaoRequisicao);
    }

    @Test
    @Transactional
    public void createSituacaoRequisicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = situacaoRequisicaoRepository.findAll().size();

        // Create the SituacaoRequisicao with an existing ID
        situacaoRequisicao.setId(1L);
        SituacaoRequisicaoDTO situacaoRequisicaoDTO = situacaoRequisicaoMapper.toDto(situacaoRequisicao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituacaoRequisicaoMockMvc.perform(post("/api/situacao-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situacaoRequisicaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SituacaoRequisicao in the database
        List<SituacaoRequisicao> situacaoRequisicaoList = situacaoRequisicaoRepository.findAll();
        assertThat(situacaoRequisicaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the SituacaoRequisicao in Elasticsearch
        verify(mockSituacaoRequisicaoSearchRepository, times(0)).save(situacaoRequisicao);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = situacaoRequisicaoRepository.findAll().size();
        // set the field null
        situacaoRequisicao.setDescricao(null);

        // Create the SituacaoRequisicao, which fails.
        SituacaoRequisicaoDTO situacaoRequisicaoDTO = situacaoRequisicaoMapper.toDto(situacaoRequisicao);


        restSituacaoRequisicaoMockMvc.perform(post("/api/situacao-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situacaoRequisicaoDTO)))
            .andExpect(status().isBadRequest());

        List<SituacaoRequisicao> situacaoRequisicaoList = situacaoRequisicaoRepository.findAll();
        assertThat(situacaoRequisicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSituacaoRequisicaos() throws Exception {
        // Initialize the database
        situacaoRequisicaoRepository.saveAndFlush(situacaoRequisicao);

        // Get all the situacaoRequisicaoList
        restSituacaoRequisicaoMockMvc.perform(get("/api/situacao-requisicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situacaoRequisicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getSituacaoRequisicao() throws Exception {
        // Initialize the database
        situacaoRequisicaoRepository.saveAndFlush(situacaoRequisicao);

        // Get the situacaoRequisicao
        restSituacaoRequisicaoMockMvc.perform(get("/api/situacao-requisicaos/{id}", situacaoRequisicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(situacaoRequisicao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingSituacaoRequisicao() throws Exception {
        // Get the situacaoRequisicao
        restSituacaoRequisicaoMockMvc.perform(get("/api/situacao-requisicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituacaoRequisicao() throws Exception {
        // Initialize the database
        situacaoRequisicaoRepository.saveAndFlush(situacaoRequisicao);

        int databaseSizeBeforeUpdate = situacaoRequisicaoRepository.findAll().size();

        // Update the situacaoRequisicao
        SituacaoRequisicao updatedSituacaoRequisicao = situacaoRequisicaoRepository.findById(situacaoRequisicao.getId()).get();
        // Disconnect from session so that the updates on updatedSituacaoRequisicao are not directly saved in db
        em.detach(updatedSituacaoRequisicao);
        updatedSituacaoRequisicao
            .setDescricao(UPDATED_DESCRICAO);
        SituacaoRequisicaoDTO situacaoRequisicaoDTO = situacaoRequisicaoMapper.toDto(updatedSituacaoRequisicao);

        restSituacaoRequisicaoMockMvc.perform(put("/api/situacao-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situacaoRequisicaoDTO)))
            .andExpect(status().isOk());

        // Validate the SituacaoRequisicao in the database
        List<SituacaoRequisicao> situacaoRequisicaoList = situacaoRequisicaoRepository.findAll();
        assertThat(situacaoRequisicaoList).hasSize(databaseSizeBeforeUpdate);
        SituacaoRequisicao testSituacaoRequisicao = situacaoRequisicaoList.get(situacaoRequisicaoList.size() - 1);
        assertThat(testSituacaoRequisicao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the SituacaoRequisicao in Elasticsearch
        verify(mockSituacaoRequisicaoSearchRepository, times(1)).save(testSituacaoRequisicao);
    }

    @Test
    @Transactional
    public void updateNonExistingSituacaoRequisicao() throws Exception {
        int databaseSizeBeforeUpdate = situacaoRequisicaoRepository.findAll().size();

        // Create the SituacaoRequisicao
        SituacaoRequisicaoDTO situacaoRequisicaoDTO = situacaoRequisicaoMapper.toDto(situacaoRequisicao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituacaoRequisicaoMockMvc.perform(put("/api/situacao-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situacaoRequisicaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SituacaoRequisicao in the database
        List<SituacaoRequisicao> situacaoRequisicaoList = situacaoRequisicaoRepository.findAll();
        assertThat(situacaoRequisicaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SituacaoRequisicao in Elasticsearch
        verify(mockSituacaoRequisicaoSearchRepository, times(0)).save(situacaoRequisicao);
    }

    @Test
    @Transactional
    public void deleteSituacaoRequisicao() throws Exception {
        // Initialize the database
        situacaoRequisicaoRepository.saveAndFlush(situacaoRequisicao);

        int databaseSizeBeforeDelete = situacaoRequisicaoRepository.findAll().size();

        // Delete the situacaoRequisicao
        restSituacaoRequisicaoMockMvc.perform(delete("/api/situacao-requisicaos/{id}", situacaoRequisicao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SituacaoRequisicao> situacaoRequisicaoList = situacaoRequisicaoRepository.findAll();
        assertThat(situacaoRequisicaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SituacaoRequisicao in Elasticsearch
        verify(mockSituacaoRequisicaoSearchRepository, times(1)).deleteById(situacaoRequisicao.getId());
    }

    @Test
    @Transactional
    public void searchSituacaoRequisicao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        situacaoRequisicaoRepository.saveAndFlush(situacaoRequisicao);
        when(mockSituacaoRequisicaoSearchRepository.search(queryStringQuery("id:" + situacaoRequisicao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(situacaoRequisicao), PageRequest.of(0, 1), 1));

        // Search the situacaoRequisicao
        restSituacaoRequisicaoMockMvc.perform(get("/api/_search/situacao-requisicaos?query=id:" + situacaoRequisicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situacaoRequisicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
}
