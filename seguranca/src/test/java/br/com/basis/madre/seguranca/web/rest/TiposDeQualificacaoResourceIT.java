package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.TiposDeQualificacao;
import br.com.basis.madre.seguranca.repository.TiposDeQualificacaoRepository;
import br.com.basis.madre.seguranca.repository.search.TiposDeQualificacaoSearchRepository;
import br.com.basis.madre.seguranca.service.TiposDeQualificacaoService;
import br.com.basis.madre.seguranca.service.dto.TiposDeQualificacaoDTO;
import br.com.basis.madre.seguranca.service.mapper.TiposDeQualificacaoMapper;

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

import br.com.basis.madre.seguranca.domain.enumeration.TipoQualificacao;
/**
 * Integration tests for the {@link TiposDeQualificacaoResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TiposDeQualificacaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final TipoQualificacao DEFAULT_TIPO = TipoQualificacao.CCC;
    private static final TipoQualificacao UPDATED_TIPO = TipoQualificacao.CSC;

    private static final String DEFAULT_CONSELHO = "AAAAAAAAAA";
    private static final String UPDATED_CONSELHO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SITUACAO = false;
    private static final Boolean UPDATED_SITUACAO = true;

    @Autowired
    private TiposDeQualificacaoRepository tiposDeQualificacaoRepository;

    @Autowired
    private TiposDeQualificacaoMapper tiposDeQualificacaoMapper;

    @Autowired
    private TiposDeQualificacaoService tiposDeQualificacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.TiposDeQualificacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TiposDeQualificacaoSearchRepository mockTiposDeQualificacaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTiposDeQualificacaoMockMvc;

    private TiposDeQualificacao tiposDeQualificacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TiposDeQualificacao createEntity(EntityManager em) {
        TiposDeQualificacao tiposDeQualificacao = new TiposDeQualificacao()
            .descricao(DEFAULT_DESCRICAO)
            .tipo(DEFAULT_TIPO)
            .conselho(DEFAULT_CONSELHO)
            .situacao(DEFAULT_SITUACAO);
        return tiposDeQualificacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TiposDeQualificacao createUpdatedEntity(EntityManager em) {
        TiposDeQualificacao tiposDeQualificacao = new TiposDeQualificacao()
            .descricao(UPDATED_DESCRICAO)
            .tipo(UPDATED_TIPO)
            .conselho(UPDATED_CONSELHO)
            .situacao(UPDATED_SITUACAO);
        return tiposDeQualificacao;
    }

    @BeforeEach
    public void initTest() {
        tiposDeQualificacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiposDeQualificacao() throws Exception {
        int databaseSizeBeforeCreate = tiposDeQualificacaoRepository.findAll().size();
        // Create the TiposDeQualificacao
        TiposDeQualificacaoDTO tiposDeQualificacaoDTO = tiposDeQualificacaoMapper.toDto(tiposDeQualificacao);
        restTiposDeQualificacaoMockMvc.perform(post("/api/tipos-de-qualificacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiposDeQualificacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the TiposDeQualificacao in the database
        List<TiposDeQualificacao> tiposDeQualificacaoList = tiposDeQualificacaoRepository.findAll();
        assertThat(tiposDeQualificacaoList).hasSize(databaseSizeBeforeCreate + 1);
        TiposDeQualificacao testTiposDeQualificacao = tiposDeQualificacaoList.get(tiposDeQualificacaoList.size() - 1);
        assertThat(testTiposDeQualificacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTiposDeQualificacao.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTiposDeQualificacao.getConselho()).isEqualTo(DEFAULT_CONSELHO);
        assertThat(testTiposDeQualificacao.isSituacao()).isEqualTo(DEFAULT_SITUACAO);

        // Validate the TiposDeQualificacao in Elasticsearch
        verify(mockTiposDeQualificacaoSearchRepository, times(1)).save(testTiposDeQualificacao);
    }

    @Test
    @Transactional
    public void createTiposDeQualificacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiposDeQualificacaoRepository.findAll().size();

        // Create the TiposDeQualificacao with an existing ID
        tiposDeQualificacao.setId(1L);
        TiposDeQualificacaoDTO tiposDeQualificacaoDTO = tiposDeQualificacaoMapper.toDto(tiposDeQualificacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiposDeQualificacaoMockMvc.perform(post("/api/tipos-de-qualificacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiposDeQualificacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TiposDeQualificacao in the database
        List<TiposDeQualificacao> tiposDeQualificacaoList = tiposDeQualificacaoRepository.findAll();
        assertThat(tiposDeQualificacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TiposDeQualificacao in Elasticsearch
        verify(mockTiposDeQualificacaoSearchRepository, times(0)).save(tiposDeQualificacao);
    }


    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiposDeQualificacaoRepository.findAll().size();
        // set the field null
        tiposDeQualificacao.setSituacao(null);

        // Create the TiposDeQualificacao, which fails.
        TiposDeQualificacaoDTO tiposDeQualificacaoDTO = tiposDeQualificacaoMapper.toDto(tiposDeQualificacao);


        restTiposDeQualificacaoMockMvc.perform(post("/api/tipos-de-qualificacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiposDeQualificacaoDTO)))
            .andExpect(status().isBadRequest());

        List<TiposDeQualificacao> tiposDeQualificacaoList = tiposDeQualificacaoRepository.findAll();
        assertThat(tiposDeQualificacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTiposDeQualificacaos() throws Exception {
        // Initialize the database
        tiposDeQualificacaoRepository.saveAndFlush(tiposDeQualificacao);

        // Get all the tiposDeQualificacaoList
        restTiposDeQualificacaoMockMvc.perform(get("/api/tipos-de-qualificacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiposDeQualificacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].conselho").value(hasItem(DEFAULT_CONSELHO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTiposDeQualificacao() throws Exception {
        // Initialize the database
        tiposDeQualificacaoRepository.saveAndFlush(tiposDeQualificacao);

        // Get the tiposDeQualificacao
        restTiposDeQualificacaoMockMvc.perform(get("/api/tipos-de-qualificacaos/{id}", tiposDeQualificacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tiposDeQualificacao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.conselho").value(DEFAULT_CONSELHO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTiposDeQualificacao() throws Exception {
        // Get the tiposDeQualificacao
        restTiposDeQualificacaoMockMvc.perform(get("/api/tipos-de-qualificacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiposDeQualificacao() throws Exception {
        // Initialize the database
        tiposDeQualificacaoRepository.saveAndFlush(tiposDeQualificacao);

        int databaseSizeBeforeUpdate = tiposDeQualificacaoRepository.findAll().size();

        // Update the tiposDeQualificacao
        TiposDeQualificacao updatedTiposDeQualificacao = tiposDeQualificacaoRepository.findById(tiposDeQualificacao.getId()).get();
        // Disconnect from session so that the updates on updatedTiposDeQualificacao are not directly saved in db
        em.detach(updatedTiposDeQualificacao);
        updatedTiposDeQualificacao
            .descricao(UPDATED_DESCRICAO)
            .tipo(UPDATED_TIPO)
            .conselho(UPDATED_CONSELHO)
            .situacao(UPDATED_SITUACAO);
        TiposDeQualificacaoDTO tiposDeQualificacaoDTO = tiposDeQualificacaoMapper.toDto(updatedTiposDeQualificacao);

        restTiposDeQualificacaoMockMvc.perform(put("/api/tipos-de-qualificacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiposDeQualificacaoDTO)))
            .andExpect(status().isOk());

        // Validate the TiposDeQualificacao in the database
        List<TiposDeQualificacao> tiposDeQualificacaoList = tiposDeQualificacaoRepository.findAll();
        assertThat(tiposDeQualificacaoList).hasSize(databaseSizeBeforeUpdate);
        TiposDeQualificacao testTiposDeQualificacao = tiposDeQualificacaoList.get(tiposDeQualificacaoList.size() - 1);
        assertThat(testTiposDeQualificacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTiposDeQualificacao.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTiposDeQualificacao.getConselho()).isEqualTo(UPDATED_CONSELHO);
        assertThat(testTiposDeQualificacao.isSituacao()).isEqualTo(UPDATED_SITUACAO);

        // Validate the TiposDeQualificacao in Elasticsearch
        verify(mockTiposDeQualificacaoSearchRepository, times(1)).save(testTiposDeQualificacao);
    }

    @Test
    @Transactional
    public void updateNonExistingTiposDeQualificacao() throws Exception {
        int databaseSizeBeforeUpdate = tiposDeQualificacaoRepository.findAll().size();

        // Create the TiposDeQualificacao
        TiposDeQualificacaoDTO tiposDeQualificacaoDTO = tiposDeQualificacaoMapper.toDto(tiposDeQualificacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTiposDeQualificacaoMockMvc.perform(put("/api/tipos-de-qualificacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tiposDeQualificacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TiposDeQualificacao in the database
        List<TiposDeQualificacao> tiposDeQualificacaoList = tiposDeQualificacaoRepository.findAll();
        assertThat(tiposDeQualificacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TiposDeQualificacao in Elasticsearch
        verify(mockTiposDeQualificacaoSearchRepository, times(0)).save(tiposDeQualificacao);
    }

    @Test
    @Transactional
    public void deleteTiposDeQualificacao() throws Exception {
        // Initialize the database
        tiposDeQualificacaoRepository.saveAndFlush(tiposDeQualificacao);

        int databaseSizeBeforeDelete = tiposDeQualificacaoRepository.findAll().size();

        // Delete the tiposDeQualificacao
        restTiposDeQualificacaoMockMvc.perform(delete("/api/tipos-de-qualificacaos/{id}", tiposDeQualificacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TiposDeQualificacao> tiposDeQualificacaoList = tiposDeQualificacaoRepository.findAll();
        assertThat(tiposDeQualificacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TiposDeQualificacao in Elasticsearch
        verify(mockTiposDeQualificacaoSearchRepository, times(1)).deleteById(tiposDeQualificacao.getId());
    }

    @Test
    @Transactional
    public void searchTiposDeQualificacao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        tiposDeQualificacaoRepository.saveAndFlush(tiposDeQualificacao);
        when(mockTiposDeQualificacaoSearchRepository.search(queryStringQuery("id:" + tiposDeQualificacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tiposDeQualificacao), PageRequest.of(0, 1), 1));

        // Search the tiposDeQualificacao
        restTiposDeQualificacaoMockMvc.perform(get("/api/_search/tipos-de-qualificacaos?query=id:" + tiposDeQualificacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiposDeQualificacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].conselho").value(hasItem(DEFAULT_CONSELHO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())));
    }
}
