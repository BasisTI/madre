package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Transacao;
import br.com.basis.suprimentos.repository.TransacaoRepository;
import br.com.basis.suprimentos.repository.search.TransacaoSearchRepository;
import br.com.basis.suprimentos.service.TransacaoService;
import br.com.basis.suprimentos.service.dto.TransacaoDTO;
import br.com.basis.suprimentos.service.mapper.TransacaoMapper;
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
 * Integration tests for the {@link TransacaoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransacaoResourceIT {

    private static final Long DEFAULT_QUANTIDADE_ITENS = 1L;
    private static final Long UPDATED_QUANTIDADE_ITENS = 2L;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoMapper transacaoMapper;

    @Autowired
    private TransacaoService transacaoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.TransacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransacaoSearchRepository mockTransacaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransacaoMockMvc;

    private Transacao transacao;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transacao createEntity(EntityManager em) {
        Transacao transacao = new Transacao();
        transacao.setQuantidadeItens(DEFAULT_QUANTIDADE_ITENS);
        return transacao;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transacao createUpdatedEntity(EntityManager em) {
        Transacao transacao = new Transacao();
        transacao.setQuantidadeItens(UPDATED_QUANTIDADE_ITENS);
        return transacao;
    }

    @BeforeEach
    public void initTest() {
        transacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransacao() throws Exception {
        int databaseSizeBeforeCreate = transacaoRepository.findAll().size();
        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);
        restTransacaoMockMvc.perform(post("/api/transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Transacao testTransacao = transacaoList.get(transacaoList.size() - 1);
        assertThat(testTransacao.getQuantidadeItens()).isEqualTo(DEFAULT_QUANTIDADE_ITENS);

        // Validate the Transacao in Elasticsearch
        verify(mockTransacaoSearchRepository, times(1)).save(testTransacao);
    }

    @Test
    @Transactional
    public void createTransacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transacaoRepository.findAll().size();

        // Create the Transacao with an existing ID
        transacao.setId(1L);
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransacaoMockMvc.perform(post("/api/transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Transacao in Elasticsearch
        verify(mockTransacaoSearchRepository, times(0)).save(transacao);
    }


    @Test
    @Transactional
    public void checkQuantidadeItensIsRequired() throws Exception {
        int databaseSizeBeforeTest = transacaoRepository.findAll().size();
        // set the field null
        transacao.setQuantidadeItens(null);

        // Create the Transacao, which fails.
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);


        restTransacaoMockMvc.perform(post("/api/transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransacaos() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get all the transacaoList
        restTransacaoMockMvc.perform(get("/api/transacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeItens").value(hasItem(DEFAULT_QUANTIDADE_ITENS.intValue())));
    }

    @Test
    @Transactional
    public void getTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        // Get the transacao
        restTransacaoMockMvc.perform(get("/api/transacaos/{id}", transacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transacao.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeItens").value(DEFAULT_QUANTIDADE_ITENS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransacao() throws Exception {
        // Get the transacao
        restTransacaoMockMvc.perform(get("/api/transacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();

        // Update the transacao
        Transacao updatedTransacao = transacaoRepository.findById(transacao.getId()).get();
        // Disconnect from session so that the updates on updatedTransacao are not directly saved in db
        em.detach(updatedTransacao);
        updatedTransacao
            .setQuantidadeItens(UPDATED_QUANTIDADE_ITENS);
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(updatedTransacao);

        restTransacaoMockMvc.perform(put("/api/transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);
        Transacao testTransacao = transacaoList.get(transacaoList.size() - 1);
        assertThat(testTransacao.getQuantidadeItens()).isEqualTo(UPDATED_QUANTIDADE_ITENS);

        // Validate the Transacao in Elasticsearch
        verify(mockTransacaoSearchRepository, times(1)).save(testTransacao);
    }

    @Test
    @Transactional
    public void updateNonExistingTransacao() throws Exception {
        int databaseSizeBeforeUpdate = transacaoRepository.findAll().size();

        // Create the Transacao
        TransacaoDTO transacaoDTO = transacaoMapper.toDto(transacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransacaoMockMvc.perform(put("/api/transacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transacao in the database
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Transacao in Elasticsearch
        verify(mockTransacaoSearchRepository, times(0)).save(transacao);
    }

    @Test
    @Transactional
    public void deleteTransacao() throws Exception {
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);

        int databaseSizeBeforeDelete = transacaoRepository.findAll().size();

        // Delete the transacao
        restTransacaoMockMvc.perform(delete("/api/transacaos/{id}", transacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transacao> transacaoList = transacaoRepository.findAll();
        assertThat(transacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Transacao in Elasticsearch
        verify(mockTransacaoSearchRepository, times(1)).deleteById(transacao.getId());
    }

    @Test
    @Transactional
    public void searchTransacao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        transacaoRepository.saveAndFlush(transacao);
        when(mockTransacaoSearchRepository.search(queryStringQuery("id:" + transacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(transacao), PageRequest.of(0, 1), 1));

        // Search the transacao
        restTransacaoMockMvc.perform(get("/api/_search/transacaos?query=id:" + transacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeItens").value(hasItem(DEFAULT_QUANTIDADE_ITENS.intValue())));
    }
}
