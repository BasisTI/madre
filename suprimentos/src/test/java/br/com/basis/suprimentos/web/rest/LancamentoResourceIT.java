package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Lancamento;
import br.com.basis.suprimentos.repository.LancamentoRepository;
import br.com.basis.suprimentos.repository.search.LancamentoSearchRepository;
import br.com.basis.suprimentos.service.LancamentoService;
import br.com.basis.suprimentos.service.dto.LancamentoDTO;
import br.com.basis.suprimentos.service.mapper.LancamentoMapper;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static br.com.basis.suprimentos.web.rest.TestUtil.sameInstant;
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
 * Integration tests for the {@link LancamentoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LancamentoResourceIT {

    private static final ZonedDateTime DEFAULT_LANCADO_EM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LANCADO_EM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LANCADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_LANCADO_POR = "BBBBBBBBBB";

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoMapper lancamentoMapper;

    @Autowired
    private LancamentoService lancamentoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.LancamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private LancamentoSearchRepository mockLancamentoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLancamentoMockMvc;

    private Lancamento lancamento;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lancamento createEntity(EntityManager em) {
        Lancamento lancamento = new Lancamento();
        lancamento.setLancadoEm(DEFAULT_LANCADO_EM);
        lancamento.setLancadoPor(DEFAULT_LANCADO_POR);
        return lancamento;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lancamento createUpdatedEntity(EntityManager em) {
        Lancamento lancamento = new Lancamento();
        lancamento.setLancadoEm(UPDATED_LANCADO_EM);
        lancamento.setLancadoPor(UPDATED_LANCADO_POR);
        return lancamento;
    }

    @BeforeEach
    public void initTest() {
        lancamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createLancamento() throws Exception {
        int databaseSizeBeforeCreate = lancamentoRepository.findAll().size();
        // Create the Lancamento
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(lancamento);
        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Lancamento testLancamento = lancamentoList.get(lancamentoList.size() - 1);
        assertThat(testLancamento.getLancadoEm()).isEqualTo(DEFAULT_LANCADO_EM);
        assertThat(testLancamento.getLancadoPor()).isEqualTo(DEFAULT_LANCADO_POR);

        // Validate the Lancamento in Elasticsearch
        verify(mockLancamentoSearchRepository, times(1)).save(testLancamento);
    }

    @Test
    @Transactional
    public void createLancamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lancamentoRepository.findAll().size();

        // Create the Lancamento with an existing ID
        lancamento.setId(1L);
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(lancamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lancamento in Elasticsearch
        verify(mockLancamentoSearchRepository, times(0)).save(lancamento);
    }


    @Test
    @Transactional
    public void checkLancadoEmIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoRepository.findAll().size();
        // set the field null
        lancamento.setLancadoEm(null);

        // Create the Lancamento, which fails.
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(lancamento);


        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLancadoPorIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoRepository.findAll().size();
        // set the field null
        lancamento.setLancadoPor(null);

        // Create the Lancamento, which fails.
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(lancamento);


        restLancamentoMockMvc.perform(post("/api/lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLancamentos() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get all the lancamentoList
        restLancamentoMockMvc.perform(get("/api/lancamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].lancadoEm").value(hasItem(sameInstant(DEFAULT_LANCADO_EM))))
            .andExpect(jsonPath("$.[*].lancadoPor").value(hasItem(DEFAULT_LANCADO_POR)));
    }

    @Test
    @Transactional
    public void getLancamento() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        // Get the lancamento
        restLancamentoMockMvc.perform(get("/api/lancamentos/{id}", lancamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lancamento.getId().intValue()))
            .andExpect(jsonPath("$.lancadoEm").value(sameInstant(DEFAULT_LANCADO_EM)))
            .andExpect(jsonPath("$.lancadoPor").value(DEFAULT_LANCADO_POR));
    }

    @Test
    @Transactional
    public void getNonExistingLancamento() throws Exception {
        // Get the lancamento
        restLancamentoMockMvc.perform(get("/api/lancamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLancamento() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        int databaseSizeBeforeUpdate = lancamentoRepository.findAll().size();

        // Update the lancamento
        Lancamento updatedLancamento = lancamentoRepository.findById(lancamento.getId()).get();
        // Disconnect from session so that the updates on updatedLancamento are not directly saved in db
        em.detach(updatedLancamento);
        updatedLancamento
            .setLancadoEm(UPDATED_LANCADO_EM);
        updatedLancamento.setLancadoPor(UPDATED_LANCADO_POR);
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(updatedLancamento);

        restLancamentoMockMvc.perform(put("/api/lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeUpdate);
        Lancamento testLancamento = lancamentoList.get(lancamentoList.size() - 1);
        assertThat(testLancamento.getLancadoEm()).isEqualTo(UPDATED_LANCADO_EM);
        assertThat(testLancamento.getLancadoPor()).isEqualTo(UPDATED_LANCADO_POR);

        // Validate the Lancamento in Elasticsearch
        verify(mockLancamentoSearchRepository, times(1)).save(testLancamento);
    }

    @Test
    @Transactional
    public void updateNonExistingLancamento() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoRepository.findAll().size();

        // Create the Lancamento
        LancamentoDTO lancamentoDTO = lancamentoMapper.toDto(lancamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLancamentoMockMvc.perform(put("/api/lancamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lancamento in the database
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lancamento in Elasticsearch
        verify(mockLancamentoSearchRepository, times(0)).save(lancamento);
    }

    @Test
    @Transactional
    public void deleteLancamento() throws Exception {
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);

        int databaseSizeBeforeDelete = lancamentoRepository.findAll().size();

        // Delete the lancamento
        restLancamentoMockMvc.perform(delete("/api/lancamentos/{id}", lancamento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lancamento> lancamentoList = lancamentoRepository.findAll();
        assertThat(lancamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lancamento in Elasticsearch
        verify(mockLancamentoSearchRepository, times(1)).deleteById(lancamento.getId());
    }

    @Test
    @Transactional
    public void searchLancamento() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        lancamentoRepository.saveAndFlush(lancamento);
        when(mockLancamentoSearchRepository.search(queryStringQuery("id:" + lancamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lancamento), PageRequest.of(0, 1), 1));

        // Search the lancamento
        restLancamentoMockMvc.perform(get("/api/_search/lancamentos?query=id:" + lancamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].lancadoEm").value(hasItem(sameInstant(DEFAULT_LANCADO_EM))))
            .andExpect(jsonPath("$.[*].lancadoPor").value(hasItem(DEFAULT_LANCADO_POR)));
    }
}
