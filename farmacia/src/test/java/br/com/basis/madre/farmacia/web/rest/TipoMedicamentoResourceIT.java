package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.TipoMedicamento;
import br.com.basis.madre.farmacia.repository.TipoMedicamentoRepository;
import br.com.basis.madre.farmacia.repository.search.TipoMedicamentoSearchRepository;
import br.com.basis.madre.farmacia.service.TipoMedicamentoService;
import br.com.basis.madre.farmacia.service.dto.TipoMedicamentoDTO;
import br.com.basis.madre.farmacia.service.mapper.TipoMedicamentoMapper;
import br.com.basis.madre.farmacia.web.rest.errors.ExceptionTranslator;

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

import static br.com.basis.madre.farmacia.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoMedicamentoResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class TipoMedicamentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TipoMedicamentoRepository tipoMedicamentoRepository;

    @Autowired
    private TipoMedicamentoMapper tipoMedicamentoMapper;

    @Autowired
    private TipoMedicamentoService tipoMedicamentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.TipoMedicamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoMedicamentoSearchRepository mockTipoMedicamentoSearchRepository;

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

    private MockMvc restTipoMedicamentoMockMvc;

    private TipoMedicamento tipoMedicamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoMedicamentoResource tipoMedicamentoResource = new TipoMedicamentoResource(tipoMedicamentoService);
        this.restTipoMedicamentoMockMvc = MockMvcBuilders.standaloneSetup(tipoMedicamentoResource)
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
    public static TipoMedicamento createEntity(EntityManager em) {
        TipoMedicamento tipoMedicamento = new TipoMedicamento()
            .nome(DEFAULT_NOME);
        return tipoMedicamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoMedicamento createUpdatedEntity(EntityManager em) {
        TipoMedicamento tipoMedicamento = new TipoMedicamento()
            .nome(UPDATED_NOME);
        return tipoMedicamento;
    }

    @BeforeEach
    public void initTest() {
        tipoMedicamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoMedicamento() throws Exception {
        int databaseSizeBeforeCreate = tipoMedicamentoRepository.findAll().size();

        // Create the TipoMedicamento
        TipoMedicamentoDTO tipoMedicamentoDTO = tipoMedicamentoMapper.toDto(tipoMedicamento);
        restTipoMedicamentoMockMvc.perform(post("/api/tipo-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoMedicamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoMedicamento in the database
        List<TipoMedicamento> tipoMedicamentoList = tipoMedicamentoRepository.findAll();
        assertThat(tipoMedicamentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoMedicamento testTipoMedicamento = tipoMedicamentoList.get(tipoMedicamentoList.size() - 1);
        assertThat(testTipoMedicamento.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the TipoMedicamento in Elasticsearch
        verify(mockTipoMedicamentoSearchRepository, times(1)).save(testTipoMedicamento);
    }

    @Test
    @Transactional
    public void createTipoMedicamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoMedicamentoRepository.findAll().size();

        // Create the TipoMedicamento with an existing ID
        tipoMedicamento.setId(1L);
        TipoMedicamentoDTO tipoMedicamentoDTO = tipoMedicamentoMapper.toDto(tipoMedicamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoMedicamentoMockMvc.perform(post("/api/tipo-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoMedicamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMedicamento in the database
        List<TipoMedicamento> tipoMedicamentoList = tipoMedicamentoRepository.findAll();
        assertThat(tipoMedicamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoMedicamento in Elasticsearch
        verify(mockTipoMedicamentoSearchRepository, times(0)).save(tipoMedicamento);
    }


    @Test
    @Transactional
    public void getAllTipoMedicamentos() throws Exception {
        // Initialize the database
        tipoMedicamentoRepository.saveAndFlush(tipoMedicamento);

        // Get all the tipoMedicamentoList
        restTipoMedicamentoMockMvc.perform(get("/api/tipo-medicamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoMedicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTipoMedicamento() throws Exception {
        // Initialize the database
        tipoMedicamentoRepository.saveAndFlush(tipoMedicamento);

        // Get the tipoMedicamento
        restTipoMedicamentoMockMvc.perform(get("/api/tipo-medicamentos/{id}", tipoMedicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoMedicamento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingTipoMedicamento() throws Exception {
        // Get the tipoMedicamento
        restTipoMedicamentoMockMvc.perform(get("/api/tipo-medicamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoMedicamento() throws Exception {
        // Initialize the database
        tipoMedicamentoRepository.saveAndFlush(tipoMedicamento);

        int databaseSizeBeforeUpdate = tipoMedicamentoRepository.findAll().size();

        // Update the tipoMedicamento
        TipoMedicamento updatedTipoMedicamento = tipoMedicamentoRepository.findById(tipoMedicamento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoMedicamento are not directly saved in db
        em.detach(updatedTipoMedicamento);
        updatedTipoMedicamento
            .nome(UPDATED_NOME);
        TipoMedicamentoDTO tipoMedicamentoDTO = tipoMedicamentoMapper.toDto(updatedTipoMedicamento);

        restTipoMedicamentoMockMvc.perform(put("/api/tipo-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoMedicamentoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoMedicamento in the database
        List<TipoMedicamento> tipoMedicamentoList = tipoMedicamentoRepository.findAll();
        assertThat(tipoMedicamentoList).hasSize(databaseSizeBeforeUpdate);
        TipoMedicamento testTipoMedicamento = tipoMedicamentoList.get(tipoMedicamentoList.size() - 1);
        assertThat(testTipoMedicamento.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the TipoMedicamento in Elasticsearch
        verify(mockTipoMedicamentoSearchRepository, times(1)).save(testTipoMedicamento);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoMedicamento() throws Exception {
        int databaseSizeBeforeUpdate = tipoMedicamentoRepository.findAll().size();

        // Create the TipoMedicamento
        TipoMedicamentoDTO tipoMedicamentoDTO = tipoMedicamentoMapper.toDto(tipoMedicamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoMedicamentoMockMvc.perform(put("/api/tipo-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoMedicamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMedicamento in the database
        List<TipoMedicamento> tipoMedicamentoList = tipoMedicamentoRepository.findAll();
        assertThat(tipoMedicamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoMedicamento in Elasticsearch
        verify(mockTipoMedicamentoSearchRepository, times(0)).save(tipoMedicamento);
    }

    @Test
    @Transactional
    public void deleteTipoMedicamento() throws Exception {
        // Initialize the database
        tipoMedicamentoRepository.saveAndFlush(tipoMedicamento);

        int databaseSizeBeforeDelete = tipoMedicamentoRepository.findAll().size();

        // Delete the tipoMedicamento
        restTipoMedicamentoMockMvc.perform(delete("/api/tipo-medicamentos/{id}", tipoMedicamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoMedicamento> tipoMedicamentoList = tipoMedicamentoRepository.findAll();
        assertThat(tipoMedicamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoMedicamento in Elasticsearch
        verify(mockTipoMedicamentoSearchRepository, times(1)).deleteById(tipoMedicamento.getId());
    }

    @Test
    @Transactional
    public void searchTipoMedicamento() throws Exception {
        // Initialize the database
        tipoMedicamentoRepository.saveAndFlush(tipoMedicamento);
        when(mockTipoMedicamentoSearchRepository.search(queryStringQuery("id:" + tipoMedicamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoMedicamento), PageRequest.of(0, 1), 1));
        // Search the tipoMedicamento
        restTipoMedicamentoMockMvc.perform(get("/api/_search/tipo-medicamentos?query=id:" + tipoMedicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoMedicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoMedicamento.class);
        TipoMedicamento tipoMedicamento1 = new TipoMedicamento();
        tipoMedicamento1.setId(1L);
        TipoMedicamento tipoMedicamento2 = new TipoMedicamento();
        tipoMedicamento2.setId(tipoMedicamento1.getId());
        assertThat(tipoMedicamento1).isEqualTo(tipoMedicamento2);
        tipoMedicamento2.setId(2L);
        assertThat(tipoMedicamento1).isNotEqualTo(tipoMedicamento2);
        tipoMedicamento1.setId(null);
        assertThat(tipoMedicamento1).isNotEqualTo(tipoMedicamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoMedicamentoDTO.class);
        TipoMedicamentoDTO tipoMedicamentoDTO1 = new TipoMedicamentoDTO();
        tipoMedicamentoDTO1.setId(1L);
        TipoMedicamentoDTO tipoMedicamentoDTO2 = new TipoMedicamentoDTO();
        assertThat(tipoMedicamentoDTO1).isNotEqualTo(tipoMedicamentoDTO2);
        tipoMedicamentoDTO2.setId(tipoMedicamentoDTO1.getId());
        assertThat(tipoMedicamentoDTO1).isEqualTo(tipoMedicamentoDTO2);
        tipoMedicamentoDTO2.setId(2L);
        assertThat(tipoMedicamentoDTO1).isNotEqualTo(tipoMedicamentoDTO2);
        tipoMedicamentoDTO1.setId(null);
        assertThat(tipoMedicamentoDTO1).isNotEqualTo(tipoMedicamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoMedicamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoMedicamentoMapper.fromId(null)).isNull();
    }
}
