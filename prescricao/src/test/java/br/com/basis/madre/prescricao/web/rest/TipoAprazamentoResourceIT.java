package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.TipoAprazamento;
import br.com.basis.madre.prescricao.repository.TipoAprazamentoRepository;
import br.com.basis.madre.prescricao.repository.search.TipoAprazamentoSearchRepository;
import br.com.basis.madre.prescricao.service.TipoAprazamentoService;
import br.com.basis.madre.prescricao.service.dto.TipoAprazamentoDTO;
import br.com.basis.madre.prescricao.service.mapper.TipoAprazamentoMapper;
import br.com.basis.madre.prescricao.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static br.com.basis.madre.prescricao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoAprazamentoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class TipoAprazamentoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAA";
    private static final String UPDATED_SIGLA = "BBB";

    @Autowired
    private TipoAprazamentoRepository tipoAprazamentoRepository;

    @Autowired
    private TipoAprazamentoMapper tipoAprazamentoMapper;

    @Autowired
    private TipoAprazamentoService tipoAprazamentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.TipoAprazamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoAprazamentoSearchRepository mockTipoAprazamentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTipoAprazamentoMockMvc;

    private TipoAprazamento tipoAprazamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoAprazamentoResource tipoAprazamentoResource = new TipoAprazamentoResource(tipoAprazamentoService);
        this.restTipoAprazamentoMockMvc = MockMvcBuilders.standaloneSetup(tipoAprazamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoAprazamento createEntity(EntityManager em) {
        TipoAprazamento tipoAprazamento = new TipoAprazamento()
            .descricao(DEFAULT_DESCRICAO)
            .sigla(DEFAULT_SIGLA);
        return tipoAprazamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoAprazamento createUpdatedEntity(EntityManager em) {
        TipoAprazamento tipoAprazamento = new TipoAprazamento()
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        return tipoAprazamento;
    }

    @BeforeEach
    public void initTest() {
        tipoAprazamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoAprazamento() throws Exception {
        int databaseSizeBeforeCreate = tipoAprazamentoRepository.findAll().size();

        // Create the TipoAprazamento
        TipoAprazamentoDTO tipoAprazamentoDTO = tipoAprazamentoMapper.toDto(tipoAprazamento);
        restTipoAprazamentoMockMvc.perform(post("/api/tipo-aprazamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAprazamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoAprazamento in the database
        List<TipoAprazamento> tipoAprazamentoList = tipoAprazamentoRepository.findAll();
        assertThat(tipoAprazamentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoAprazamento testTipoAprazamento = tipoAprazamentoList.get(tipoAprazamentoList.size() - 1);
        assertThat(testTipoAprazamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTipoAprazamento.getSigla()).isEqualTo(DEFAULT_SIGLA);

        // Validate the TipoAprazamento in Elasticsearch
        verify(mockTipoAprazamentoSearchRepository, times(1)).save(testTipoAprazamento);
    }

    @Test
    @Transactional
    public void createTipoAprazamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoAprazamentoRepository.findAll().size();

        // Create the TipoAprazamento with an existing ID
        tipoAprazamento.setId(1L);
        TipoAprazamentoDTO tipoAprazamentoDTO = tipoAprazamentoMapper.toDto(tipoAprazamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoAprazamentoMockMvc.perform(post("/api/tipo-aprazamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAprazamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAprazamento in the database
        List<TipoAprazamento> tipoAprazamentoList = tipoAprazamentoRepository.findAll();
        assertThat(tipoAprazamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoAprazamento in Elasticsearch
        verify(mockTipoAprazamentoSearchRepository, times(0)).save(tipoAprazamento);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAprazamentoRepository.findAll().size();
        // set the field null
        tipoAprazamento.setDescricao(null);

        // Create the TipoAprazamento, which fails.
        TipoAprazamentoDTO tipoAprazamentoDTO = tipoAprazamentoMapper.toDto(tipoAprazamento);

        restTipoAprazamentoMockMvc.perform(post("/api/tipo-aprazamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAprazamentoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoAprazamento> tipoAprazamentoList = tipoAprazamentoRepository.findAll();
        assertThat(tipoAprazamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAprazamentoRepository.findAll().size();
        // set the field null
        tipoAprazamento.setSigla(null);

        // Create the TipoAprazamento, which fails.
        TipoAprazamentoDTO tipoAprazamentoDTO = tipoAprazamentoMapper.toDto(tipoAprazamento);

        restTipoAprazamentoMockMvc.perform(post("/api/tipo-aprazamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAprazamentoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoAprazamento> tipoAprazamentoList = tipoAprazamentoRepository.findAll();
        assertThat(tipoAprazamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoAprazamentos() throws Exception {
        // Initialize the database
        tipoAprazamentoRepository.saveAndFlush(tipoAprazamento);

        // Get all the tipoAprazamentoList
        restTipoAprazamentoMockMvc.perform(get("/api/tipo-aprazamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAprazamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getTipoAprazamento() throws Exception {
        // Initialize the database
        tipoAprazamentoRepository.saveAndFlush(tipoAprazamento);

        // Get the tipoAprazamento
        restTipoAprazamentoMockMvc.perform(get("/api/tipo-aprazamentos/{id}", tipoAprazamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoAprazamento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    public void getNonExistingTipoAprazamento() throws Exception {
        // Get the tipoAprazamento
        restTipoAprazamentoMockMvc.perform(get("/api/tipo-aprazamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoAprazamento() throws Exception {
        // Initialize the database
        tipoAprazamentoRepository.saveAndFlush(tipoAprazamento);

        int databaseSizeBeforeUpdate = tipoAprazamentoRepository.findAll().size();

        // Update the tipoAprazamento
        TipoAprazamento updatedTipoAprazamento = tipoAprazamentoRepository.findById(tipoAprazamento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoAprazamento are not directly saved in db
        em.detach(updatedTipoAprazamento);
        updatedTipoAprazamento
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        TipoAprazamentoDTO tipoAprazamentoDTO = tipoAprazamentoMapper.toDto(updatedTipoAprazamento);

        restTipoAprazamentoMockMvc.perform(put("/api/tipo-aprazamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAprazamentoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoAprazamento in the database
        List<TipoAprazamento> tipoAprazamentoList = tipoAprazamentoRepository.findAll();
        assertThat(tipoAprazamentoList).hasSize(databaseSizeBeforeUpdate);
        TipoAprazamento testTipoAprazamento = tipoAprazamentoList.get(tipoAprazamentoList.size() - 1);
        assertThat(testTipoAprazamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTipoAprazamento.getSigla()).isEqualTo(UPDATED_SIGLA);

        // Validate the TipoAprazamento in Elasticsearch
        verify(mockTipoAprazamentoSearchRepository, times(1)).save(testTipoAprazamento);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoAprazamento() throws Exception {
        int databaseSizeBeforeUpdate = tipoAprazamentoRepository.findAll().size();

        // Create the TipoAprazamento
        TipoAprazamentoDTO tipoAprazamentoDTO = tipoAprazamentoMapper.toDto(tipoAprazamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoAprazamentoMockMvc.perform(put("/api/tipo-aprazamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAprazamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAprazamento in the database
        List<TipoAprazamento> tipoAprazamentoList = tipoAprazamentoRepository.findAll();
        assertThat(tipoAprazamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoAprazamento in Elasticsearch
        verify(mockTipoAprazamentoSearchRepository, times(0)).save(tipoAprazamento);
    }

    @Test
    @Transactional
    public void deleteTipoAprazamento() throws Exception {
        // Initialize the database
        tipoAprazamentoRepository.saveAndFlush(tipoAprazamento);

        int databaseSizeBeforeDelete = tipoAprazamentoRepository.findAll().size();

        // Delete the tipoAprazamento
        restTipoAprazamentoMockMvc.perform(delete("/api/tipo-aprazamentos/{id}", tipoAprazamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoAprazamento> tipoAprazamentoList = tipoAprazamentoRepository.findAll();
        assertThat(tipoAprazamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoAprazamento in Elasticsearch
        verify(mockTipoAprazamentoSearchRepository, times(1)).deleteById(tipoAprazamento.getId());
    }

    @Test
    @Transactional
    public void searchTipoAprazamento() throws Exception {
        // Initialize the database
        tipoAprazamentoRepository.saveAndFlush(tipoAprazamento);
        when(mockTipoAprazamentoSearchRepository.search(queryStringQuery("id:" + tipoAprazamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoAprazamento), PageRequest.of(0, 1), 1));
        // Search the tipoAprazamento
        restTipoAprazamentoMockMvc.perform(get("/api/_search/tipo-aprazamentos?query=id:" + tipoAprazamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAprazamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAprazamento.class);
        TipoAprazamento tipoAprazamento1 = new TipoAprazamento();
        tipoAprazamento1.setId(1L);
        TipoAprazamento tipoAprazamento2 = new TipoAprazamento();
        tipoAprazamento2.setId(tipoAprazamento1.getId());
        assertThat(tipoAprazamento1).isEqualTo(tipoAprazamento2);
        tipoAprazamento2.setId(2L);
        assertThat(tipoAprazamento1).isNotEqualTo(tipoAprazamento2);
        tipoAprazamento1.setId(null);
        assertThat(tipoAprazamento1).isNotEqualTo(tipoAprazamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAprazamentoDTO.class);
        TipoAprazamentoDTO tipoAprazamentoDTO1 = new TipoAprazamentoDTO();
        tipoAprazamentoDTO1.setId(1L);
        TipoAprazamentoDTO tipoAprazamentoDTO2 = new TipoAprazamentoDTO();
        assertThat(tipoAprazamentoDTO1).isNotEqualTo(tipoAprazamentoDTO2);
        tipoAprazamentoDTO2.setId(tipoAprazamentoDTO1.getId());
        assertThat(tipoAprazamentoDTO1).isEqualTo(tipoAprazamentoDTO2);
        tipoAprazamentoDTO2.setId(2L);
        assertThat(tipoAprazamentoDTO1).isNotEqualTo(tipoAprazamentoDTO2);
        tipoAprazamentoDTO1.setId(null);
        assertThat(tipoAprazamentoDTO1).isNotEqualTo(tipoAprazamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoAprazamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoAprazamentoMapper.fromId(null)).isNull();
    }
}
