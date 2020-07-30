package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.TipoLancamento;
import br.com.basis.suprimentos.repository.TipoLancamentoRepository;
import br.com.basis.suprimentos.repository.search.TipoLancamentoSearchRepository;
import br.com.basis.suprimentos.service.TipoLancamentoService;
import br.com.basis.suprimentos.service.dto.TipoLancamentoDTO;
import br.com.basis.suprimentos.service.mapper.TipoLancamentoMapper;
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
 * Integration tests for the {@link TipoLancamentoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoLancamentoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoLancamentoRepository tipoLancamentoRepository;

    @Autowired
    private TipoLancamentoMapper tipoLancamentoMapper;

    @Autowired
    private TipoLancamentoService tipoLancamentoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.TipoLancamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoLancamentoSearchRepository mockTipoLancamentoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoLancamentoMockMvc;

    private TipoLancamento tipoLancamento;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoLancamento createEntity(EntityManager em) {
        TipoLancamento tipoLancamento = new TipoLancamento();
        tipoLancamento.setDescricao(DEFAULT_DESCRICAO);
        return tipoLancamento;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoLancamento createUpdatedEntity(EntityManager em) {
        TipoLancamento tipoLancamento = new TipoLancamento();
        tipoLancamento.setDescricao(UPDATED_DESCRICAO);
        return tipoLancamento;
    }

    @BeforeEach
    public void initTest() {
        tipoLancamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoLancamento() throws Exception {
        int databaseSizeBeforeCreate = tipoLancamentoRepository.findAll().size();
        // Create the TipoLancamento
        TipoLancamentoDTO tipoLancamentoDTO = tipoLancamentoMapper.toDto(tipoLancamento);
        restTipoLancamentoMockMvc.perform(post("/api/tipo-lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLancamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoLancamento in the database
        List<TipoLancamento> tipoLancamentoList = tipoLancamentoRepository.findAll();
        assertThat(tipoLancamentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoLancamento testTipoLancamento = tipoLancamentoList.get(tipoLancamentoList.size() - 1);
        assertThat(testTipoLancamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the TipoLancamento in Elasticsearch
        verify(mockTipoLancamentoSearchRepository, times(1)).save(testTipoLancamento);
    }

    @Test
    @Transactional
    public void createTipoLancamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoLancamentoRepository.findAll().size();

        // Create the TipoLancamento with an existing ID
        tipoLancamento.setId(1L);
        TipoLancamentoDTO tipoLancamentoDTO = tipoLancamentoMapper.toDto(tipoLancamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoLancamentoMockMvc.perform(post("/api/tipo-lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLancamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoLancamento in the database
        List<TipoLancamento> tipoLancamentoList = tipoLancamentoRepository.findAll();
        assertThat(tipoLancamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoLancamento in Elasticsearch
        verify(mockTipoLancamentoSearchRepository, times(0)).save(tipoLancamento);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoLancamentoRepository.findAll().size();
        // set the field null
        tipoLancamento.setDescricao(null);

        // Create the TipoLancamento, which fails.
        TipoLancamentoDTO tipoLancamentoDTO = tipoLancamentoMapper.toDto(tipoLancamento);


        restTipoLancamentoMockMvc.perform(post("/api/tipo-lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLancamentoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoLancamento> tipoLancamentoList = tipoLancamentoRepository.findAll();
        assertThat(tipoLancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoLancamentos() throws Exception {
        // Initialize the database
        tipoLancamentoRepository.saveAndFlush(tipoLancamento);

        // Get all the tipoLancamentoList
        restTipoLancamentoMockMvc.perform(get("/api/tipo-lancamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoLancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getTipoLancamento() throws Exception {
        // Initialize the database
        tipoLancamentoRepository.saveAndFlush(tipoLancamento);

        // Get the tipoLancamento
        restTipoLancamentoMockMvc.perform(get("/api/tipo-lancamentos/{id}", tipoLancamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoLancamento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingTipoLancamento() throws Exception {
        // Get the tipoLancamento
        restTipoLancamentoMockMvc.perform(get("/api/tipo-lancamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoLancamento() throws Exception {
        // Initialize the database
        tipoLancamentoRepository.saveAndFlush(tipoLancamento);

        int databaseSizeBeforeUpdate = tipoLancamentoRepository.findAll().size();

        // Update the tipoLancamento
        TipoLancamento updatedTipoLancamento = tipoLancamentoRepository.findById(tipoLancamento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoLancamento are not directly saved in db
        em.detach(updatedTipoLancamento);
        updatedTipoLancamento
            .setDescricao(UPDATED_DESCRICAO);
        TipoLancamentoDTO tipoLancamentoDTO = tipoLancamentoMapper.toDto(updatedTipoLancamento);

        restTipoLancamentoMockMvc.perform(put("/api/tipo-lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLancamentoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoLancamento in the database
        List<TipoLancamento> tipoLancamentoList = tipoLancamentoRepository.findAll();
        assertThat(tipoLancamentoList).hasSize(databaseSizeBeforeUpdate);
        TipoLancamento testTipoLancamento = tipoLancamentoList.get(tipoLancamentoList.size() - 1);
        assertThat(testTipoLancamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the TipoLancamento in Elasticsearch
        verify(mockTipoLancamentoSearchRepository, times(1)).save(testTipoLancamento);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoLancamento() throws Exception {
        int databaseSizeBeforeUpdate = tipoLancamentoRepository.findAll().size();

        // Create the TipoLancamento
        TipoLancamentoDTO tipoLancamentoDTO = tipoLancamentoMapper.toDto(tipoLancamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoLancamentoMockMvc.perform(put("/api/tipo-lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLancamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoLancamento in the database
        List<TipoLancamento> tipoLancamentoList = tipoLancamentoRepository.findAll();
        assertThat(tipoLancamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoLancamento in Elasticsearch
        verify(mockTipoLancamentoSearchRepository, times(0)).save(tipoLancamento);
    }

    @Test
    @Transactional
    public void deleteTipoLancamento() throws Exception {
        // Initialize the database
        tipoLancamentoRepository.saveAndFlush(tipoLancamento);

        int databaseSizeBeforeDelete = tipoLancamentoRepository.findAll().size();

        // Delete the tipoLancamento
        restTipoLancamentoMockMvc.perform(delete("/api/tipo-lancamentos/{id}", tipoLancamento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoLancamento> tipoLancamentoList = tipoLancamentoRepository.findAll();
        assertThat(tipoLancamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoLancamento in Elasticsearch
        verify(mockTipoLancamentoSearchRepository, times(1)).deleteById(tipoLancamento.getId());
    }

    @Test
    @Transactional
    public void searchTipoLancamento() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        tipoLancamentoRepository.saveAndFlush(tipoLancamento);
        when(mockTipoLancamentoSearchRepository.search(queryStringQuery("id:" + tipoLancamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoLancamento), PageRequest.of(0, 1), 1));

        // Search the tipoLancamento
        restTipoLancamentoMockMvc.perform(get("/api/_search/tipo-lancamentos?query=id:" + tipoLancamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoLancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
}
