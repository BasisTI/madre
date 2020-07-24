package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.TipoTransacao;
import br.com.basis.suprimentos.repository.TipoTransacaoRepository;
import br.com.basis.suprimentos.repository.search.TipoTransacaoSearchRepository;
import br.com.basis.suprimentos.service.TipoTransacaoService;
import br.com.basis.suprimentos.service.dto.TipoTransacaoDTO;
import br.com.basis.suprimentos.service.mapper.TipoTransacaoMapper;
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
 * Integration tests for the {@link TipoTransacaoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoTransacaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoTransacaoRepository tipoTransacaoRepository;

    @Autowired
    private TipoTransacaoMapper tipoTransacaoMapper;

    @Autowired
    private TipoTransacaoService tipoTransacaoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.TipoTransacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoTransacaoSearchRepository mockTipoTransacaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoTransacaoMockMvc;

    private TipoTransacao tipoTransacao;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTransacao createEntity(EntityManager em) {
        TipoTransacao tipoTransacao = new TipoTransacao();
        tipoTransacao.setDescricao(DEFAULT_DESCRICAO);
        return tipoTransacao;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTransacao createUpdatedEntity(EntityManager em) {
        TipoTransacao tipoTransacao = new TipoTransacao();
        tipoTransacao.setDescricao(UPDATED_DESCRICAO);
        return tipoTransacao;
    }

    @BeforeEach
    public void initTest() {
        tipoTransacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoTransacao() throws Exception {
        int databaseSizeBeforeCreate = tipoTransacaoRepository.findAll().size();
        // Create the TipoTransacao
        TipoTransacaoDTO tipoTransacaoDTO = tipoTransacaoMapper.toDto(tipoTransacao);
        restTipoTransacaoMockMvc.perform(post("/api/tipo-transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoTransacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoTransacao testTipoTransacao = tipoTransacaoList.get(tipoTransacaoList.size() - 1);
        assertThat(testTipoTransacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the TipoTransacao in Elasticsearch
        verify(mockTipoTransacaoSearchRepository, times(1)).save(testTipoTransacao);
    }

    @Test
    @Transactional
    public void createTipoTransacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoTransacaoRepository.findAll().size();

        // Create the TipoTransacao with an existing ID
        tipoTransacao.setId(1L);
        TipoTransacaoDTO tipoTransacaoDTO = tipoTransacaoMapper.toDto(tipoTransacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoTransacaoMockMvc.perform(post("/api/tipo-transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoTransacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoTransacao in Elasticsearch
        verify(mockTipoTransacaoSearchRepository, times(0)).save(tipoTransacao);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoTransacaoRepository.findAll().size();
        // set the field null
        tipoTransacao.setDescricao(null);

        // Create the TipoTransacao, which fails.
        TipoTransacaoDTO tipoTransacaoDTO = tipoTransacaoMapper.toDto(tipoTransacao);


        restTipoTransacaoMockMvc.perform(post("/api/tipo-transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoTransacaoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoTransacaos() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        // Get all the tipoTransacaoList
        restTipoTransacaoMockMvc.perform(get("/api/tipo-transacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoTransacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getTipoTransacao() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        // Get the tipoTransacao
        restTipoTransacaoMockMvc.perform(get("/api/tipo-transacaos/{id}", tipoTransacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoTransacao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingTipoTransacao() throws Exception {
        // Get the tipoTransacao
        restTipoTransacaoMockMvc.perform(get("/api/tipo-transacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoTransacao() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();

        // Update the tipoTransacao
        TipoTransacao updatedTipoTransacao = tipoTransacaoRepository.findById(tipoTransacao.getId()).get();
        // Disconnect from session so that the updates on updatedTipoTransacao are not directly saved in db
        em.detach(updatedTipoTransacao);
        updatedTipoTransacao
            .setDescricao(UPDATED_DESCRICAO);
        TipoTransacaoDTO tipoTransacaoDTO = tipoTransacaoMapper.toDto(updatedTipoTransacao);

        restTipoTransacaoMockMvc.perform(put("/api/tipo-transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoTransacaoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);
        TipoTransacao testTipoTransacao = tipoTransacaoList.get(tipoTransacaoList.size() - 1);
        assertThat(testTipoTransacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the TipoTransacao in Elasticsearch
        verify(mockTipoTransacaoSearchRepository, times(1)).save(testTipoTransacao);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoTransacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoTransacaoRepository.findAll().size();

        // Create the TipoTransacao
        TipoTransacaoDTO tipoTransacaoDTO = tipoTransacaoMapper.toDto(tipoTransacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoTransacaoMockMvc.perform(put("/api/tipo-transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoTransacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoTransacao in the database
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoTransacao in Elasticsearch
        verify(mockTipoTransacaoSearchRepository, times(0)).save(tipoTransacao);
    }

    @Test
    @Transactional
    public void deleteTipoTransacao() throws Exception {
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);

        int databaseSizeBeforeDelete = tipoTransacaoRepository.findAll().size();

        // Delete the tipoTransacao
        restTipoTransacaoMockMvc.perform(delete("/api/tipo-transacaos/{id}", tipoTransacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        assertThat(tipoTransacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoTransacao in Elasticsearch
        verify(mockTipoTransacaoSearchRepository, times(1)).deleteById(tipoTransacao.getId());
    }

    @Test
    @Transactional
    public void searchTipoTransacao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        tipoTransacaoRepository.saveAndFlush(tipoTransacao);
        when(mockTipoTransacaoSearchRepository.search(queryStringQuery("id:" + tipoTransacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoTransacao), PageRequest.of(0, 1), 1));

        // Search the tipoTransacao
        restTipoTransacaoMockMvc.perform(get("/api/_search/tipo-transacaos?query=id:" + tipoTransacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoTransacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
}
