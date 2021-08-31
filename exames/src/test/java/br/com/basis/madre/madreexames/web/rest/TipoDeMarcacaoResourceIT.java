package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.TipoDeMarcacao;
import br.com.basis.madre.madreexames.repository.TipoDeMarcacaoRepository;
import br.com.basis.madre.madreexames.repository.search.TipoDeMarcacaoSearchRepository;
import br.com.basis.madre.madreexames.service.TipoDeMarcacaoService;
import br.com.basis.madre.madreexames.service.dto.TipoDeMarcacaoDTO;
import br.com.basis.madre.madreexames.service.mapper.TipoDeMarcacaoMapper;

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
 * Integration tests for the {@link TipoDeMarcacaoResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoDeMarcacaoResourceIT {

    private static final String DEFAULT_TIPO_DE_MARCACAO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DE_MARCACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private TipoDeMarcacaoRepository tipoDeMarcacaoRepository;

    @Autowired
    private TipoDeMarcacaoMapper tipoDeMarcacaoMapper;

    @Autowired
    private TipoDeMarcacaoService tipoDeMarcacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.TipoDeMarcacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDeMarcacaoSearchRepository mockTipoDeMarcacaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDeMarcacaoMockMvc;

    private TipoDeMarcacao tipoDeMarcacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDeMarcacao createEntity(EntityManager em) {
        TipoDeMarcacao tipoDeMarcacao = new TipoDeMarcacao()
            .tipoDeMarcacao(DEFAULT_TIPO_DE_MARCACAO)
            .ativo(DEFAULT_ATIVO);
        return tipoDeMarcacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDeMarcacao createUpdatedEntity(EntityManager em) {
        TipoDeMarcacao tipoDeMarcacao = new TipoDeMarcacao()
            .tipoDeMarcacao(UPDATED_TIPO_DE_MARCACAO)
            .ativo(UPDATED_ATIVO);
        return tipoDeMarcacao;
    }

    @BeforeEach
    public void initTest() {
        tipoDeMarcacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDeMarcacao() throws Exception {
        int databaseSizeBeforeCreate = tipoDeMarcacaoRepository.findAll().size();
        // Create the TipoDeMarcacao
        TipoDeMarcacaoDTO tipoDeMarcacaoDTO = tipoDeMarcacaoMapper.toDto(tipoDeMarcacao);
        restTipoDeMarcacaoMockMvc.perform(post("/api/tipo-de-marcacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeMarcacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDeMarcacao in the database
        List<TipoDeMarcacao> tipoDeMarcacaoList = tipoDeMarcacaoRepository.findAll();
        assertThat(tipoDeMarcacaoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeMarcacao testTipoDeMarcacao = tipoDeMarcacaoList.get(tipoDeMarcacaoList.size() - 1);
        assertThat(testTipoDeMarcacao.getTipoDeMarcacao()).isEqualTo(DEFAULT_TIPO_DE_MARCACAO);
        assertThat(testTipoDeMarcacao.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the TipoDeMarcacao in Elasticsearch
        verify(mockTipoDeMarcacaoSearchRepository, times(1)).save(testTipoDeMarcacao);
    }

    @Test
    @Transactional
    public void createTipoDeMarcacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDeMarcacaoRepository.findAll().size();

        // Create the TipoDeMarcacao with an existing ID
        tipoDeMarcacao.setId(1L);
        TipoDeMarcacaoDTO tipoDeMarcacaoDTO = tipoDeMarcacaoMapper.toDto(tipoDeMarcacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDeMarcacaoMockMvc.perform(post("/api/tipo-de-marcacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeMarcacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeMarcacao in the database
        List<TipoDeMarcacao> tipoDeMarcacaoList = tipoDeMarcacaoRepository.findAll();
        assertThat(tipoDeMarcacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDeMarcacao in Elasticsearch
        verify(mockTipoDeMarcacaoSearchRepository, times(0)).save(tipoDeMarcacao);
    }


    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeMarcacaoRepository.findAll().size();
        // set the field null
        tipoDeMarcacao.setAtivo(null);

        // Create the TipoDeMarcacao, which fails.
        TipoDeMarcacaoDTO tipoDeMarcacaoDTO = tipoDeMarcacaoMapper.toDto(tipoDeMarcacao);


        restTipoDeMarcacaoMockMvc.perform(post("/api/tipo-de-marcacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeMarcacaoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDeMarcacao> tipoDeMarcacaoList = tipoDeMarcacaoRepository.findAll();
        assertThat(tipoDeMarcacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDeMarcacaos() throws Exception {
        // Initialize the database
        tipoDeMarcacaoRepository.saveAndFlush(tipoDeMarcacao);

        // Get all the tipoDeMarcacaoList
        restTipoDeMarcacaoMockMvc.perform(get("/api/tipo-de-marcacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeMarcacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDeMarcacao").value(hasItem(DEFAULT_TIPO_DE_MARCACAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTipoDeMarcacao() throws Exception {
        // Initialize the database
        tipoDeMarcacaoRepository.saveAndFlush(tipoDeMarcacao);

        // Get the tipoDeMarcacao
        restTipoDeMarcacaoMockMvc.perform(get("/api/tipo-de-marcacaos/{id}", tipoDeMarcacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeMarcacao.getId().intValue()))
            .andExpect(jsonPath("$.tipoDeMarcacao").value(DEFAULT_TIPO_DE_MARCACAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTipoDeMarcacao() throws Exception {
        // Get the tipoDeMarcacao
        restTipoDeMarcacaoMockMvc.perform(get("/api/tipo-de-marcacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDeMarcacao() throws Exception {
        // Initialize the database
        tipoDeMarcacaoRepository.saveAndFlush(tipoDeMarcacao);

        int databaseSizeBeforeUpdate = tipoDeMarcacaoRepository.findAll().size();

        // Update the tipoDeMarcacao
        TipoDeMarcacao updatedTipoDeMarcacao = tipoDeMarcacaoRepository.findById(tipoDeMarcacao.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeMarcacao are not directly saved in db
        em.detach(updatedTipoDeMarcacao);
        updatedTipoDeMarcacao
            .tipoDeMarcacao(UPDATED_TIPO_DE_MARCACAO)
            .ativo(UPDATED_ATIVO);
        TipoDeMarcacaoDTO tipoDeMarcacaoDTO = tipoDeMarcacaoMapper.toDto(updatedTipoDeMarcacao);

        restTipoDeMarcacaoMockMvc.perform(put("/api/tipo-de-marcacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeMarcacaoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDeMarcacao in the database
        List<TipoDeMarcacao> tipoDeMarcacaoList = tipoDeMarcacaoRepository.findAll();
        assertThat(tipoDeMarcacaoList).hasSize(databaseSizeBeforeUpdate);
        TipoDeMarcacao testTipoDeMarcacao = tipoDeMarcacaoList.get(tipoDeMarcacaoList.size() - 1);
        assertThat(testTipoDeMarcacao.getTipoDeMarcacao()).isEqualTo(UPDATED_TIPO_DE_MARCACAO);
        assertThat(testTipoDeMarcacao.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the TipoDeMarcacao in Elasticsearch
        verify(mockTipoDeMarcacaoSearchRepository, times(1)).save(testTipoDeMarcacao);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDeMarcacao() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeMarcacaoRepository.findAll().size();

        // Create the TipoDeMarcacao
        TipoDeMarcacaoDTO tipoDeMarcacaoDTO = tipoDeMarcacaoMapper.toDto(tipoDeMarcacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeMarcacaoMockMvc.perform(put("/api/tipo-de-marcacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeMarcacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeMarcacao in the database
        List<TipoDeMarcacao> tipoDeMarcacaoList = tipoDeMarcacaoRepository.findAll();
        assertThat(tipoDeMarcacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDeMarcacao in Elasticsearch
        verify(mockTipoDeMarcacaoSearchRepository, times(0)).save(tipoDeMarcacao);
    }

    @Test
    @Transactional
    public void deleteTipoDeMarcacao() throws Exception {
        // Initialize the database
        tipoDeMarcacaoRepository.saveAndFlush(tipoDeMarcacao);

        int databaseSizeBeforeDelete = tipoDeMarcacaoRepository.findAll().size();

        // Delete the tipoDeMarcacao
        restTipoDeMarcacaoMockMvc.perform(delete("/api/tipo-de-marcacaos/{id}", tipoDeMarcacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDeMarcacao> tipoDeMarcacaoList = tipoDeMarcacaoRepository.findAll();
        assertThat(tipoDeMarcacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDeMarcacao in Elasticsearch
        verify(mockTipoDeMarcacaoSearchRepository, times(1)).deleteById(tipoDeMarcacao.getId());
    }

    @Test
    @Transactional
    public void searchTipoDeMarcacao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        tipoDeMarcacaoRepository.saveAndFlush(tipoDeMarcacao);
        when(mockTipoDeMarcacaoSearchRepository.search(queryStringQuery("id:" + tipoDeMarcacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDeMarcacao), PageRequest.of(0, 1), 1));

        // Search the tipoDeMarcacao
        restTipoDeMarcacaoMockMvc.perform(get("/api/_search/tipo-de-marcacaos?query=id:" + tipoDeMarcacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeMarcacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDeMarcacao").value(hasItem(DEFAULT_TIPO_DE_MARCACAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
}
