package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.TipoUnidade;
import br.com.basis.madre.repository.TipoUnidadeRepository;
import br.com.basis.madre.repository.search.TipoUnidadeSearchRepository;
import br.com.basis.madre.service.TipoUnidadeService;
import br.com.basis.madre.service.dto.TipoUnidadeDTO;
import br.com.basis.madre.service.mapper.TipoUnidadeMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;

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

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoUnidadeResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class TipoUnidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TipoUnidadeRepository tipoUnidadeRepository;

    @Autowired
    private TipoUnidadeMapper tipoUnidadeMapper;

    @Autowired
    private TipoUnidadeService tipoUnidadeService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.TipoUnidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoUnidadeSearchRepository mockTipoUnidadeSearchRepository;

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

    private MockMvc restTipoUnidadeMockMvc;

    private TipoUnidade tipoUnidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoUnidadeResource tipoUnidadeResource = new TipoUnidadeResource(tipoUnidadeService);
        this.restTipoUnidadeMockMvc = MockMvcBuilders.standaloneSetup(tipoUnidadeResource)
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
    public static TipoUnidade createEntity(EntityManager em) {
        TipoUnidade tipoUnidade = new TipoUnidade()
            .nome(DEFAULT_NOME);
        return tipoUnidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoUnidade createUpdatedEntity(EntityManager em) {
        TipoUnidade tipoUnidade = new TipoUnidade()
            .nome(UPDATED_NOME);
        return tipoUnidade;
    }

    @BeforeEach
    public void initTest() {
        tipoUnidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoUnidade() throws Exception {
        int databaseSizeBeforeCreate = tipoUnidadeRepository.findAll().size();

        // Create the TipoUnidade
        TipoUnidadeDTO tipoUnidadeDTO = tipoUnidadeMapper.toDto(tipoUnidade);
        restTipoUnidadeMockMvc.perform(post("/api/tipo-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoUnidade in the database
        List<TipoUnidade> tipoUnidadeList = tipoUnidadeRepository.findAll();
        assertThat(tipoUnidadeList).hasSize(databaseSizeBeforeCreate + 1);
        TipoUnidade testTipoUnidade = tipoUnidadeList.get(tipoUnidadeList.size() - 1);
        assertThat(testTipoUnidade.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the TipoUnidade in Elasticsearch
        verify(mockTipoUnidadeSearchRepository, times(1)).save(testTipoUnidade);
    }

    @Test
    @Transactional
    public void createTipoUnidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoUnidadeRepository.findAll().size();

        // Create the TipoUnidade with an existing ID
        tipoUnidade.setId(1L);
        TipoUnidadeDTO tipoUnidadeDTO = tipoUnidadeMapper.toDto(tipoUnidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoUnidadeMockMvc.perform(post("/api/tipo-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoUnidade in the database
        List<TipoUnidade> tipoUnidadeList = tipoUnidadeRepository.findAll();
        assertThat(tipoUnidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoUnidade in Elasticsearch
        verify(mockTipoUnidadeSearchRepository, times(0)).save(tipoUnidade);
    }


    @Test
    @Transactional
    public void getAllTipoUnidades() throws Exception {
        // Initialize the database
        tipoUnidadeRepository.saveAndFlush(tipoUnidade);

        // Get all the tipoUnidadeList
        restTipoUnidadeMockMvc.perform(get("/api/tipo-unidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoUnidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTipoUnidade() throws Exception {
        // Initialize the database
        tipoUnidadeRepository.saveAndFlush(tipoUnidade);

        // Get the tipoUnidade
        restTipoUnidadeMockMvc.perform(get("/api/tipo-unidades/{id}", tipoUnidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoUnidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingTipoUnidade() throws Exception {
        // Get the tipoUnidade
        restTipoUnidadeMockMvc.perform(get("/api/tipo-unidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoUnidade() throws Exception {
        // Initialize the database
        tipoUnidadeRepository.saveAndFlush(tipoUnidade);

        int databaseSizeBeforeUpdate = tipoUnidadeRepository.findAll().size();

        // Update the tipoUnidade
        TipoUnidade updatedTipoUnidade = tipoUnidadeRepository.findById(tipoUnidade.getId()).get();
        // Disconnect from session so that the updates on updatedTipoUnidade are not directly saved in db
        em.detach(updatedTipoUnidade);
        updatedTipoUnidade
            .nome(UPDATED_NOME);
        TipoUnidadeDTO tipoUnidadeDTO = tipoUnidadeMapper.toDto(updatedTipoUnidade);

        restTipoUnidadeMockMvc.perform(put("/api/tipo-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDTO)))
            .andExpect(status().isOk());

        // Validate the TipoUnidade in the database
        List<TipoUnidade> tipoUnidadeList = tipoUnidadeRepository.findAll();
        assertThat(tipoUnidadeList).hasSize(databaseSizeBeforeUpdate);
        TipoUnidade testTipoUnidade = tipoUnidadeList.get(tipoUnidadeList.size() - 1);
        assertThat(testTipoUnidade.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the TipoUnidade in Elasticsearch
        verify(mockTipoUnidadeSearchRepository, times(1)).save(testTipoUnidade);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoUnidade() throws Exception {
        int databaseSizeBeforeUpdate = tipoUnidadeRepository.findAll().size();

        // Create the TipoUnidade
        TipoUnidadeDTO tipoUnidadeDTO = tipoUnidadeMapper.toDto(tipoUnidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoUnidadeMockMvc.perform(put("/api/tipo-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoUnidade in the database
        List<TipoUnidade> tipoUnidadeList = tipoUnidadeRepository.findAll();
        assertThat(tipoUnidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoUnidade in Elasticsearch
        verify(mockTipoUnidadeSearchRepository, times(0)).save(tipoUnidade);
    }

    @Test
    @Transactional
    public void deleteTipoUnidade() throws Exception {
        // Initialize the database
        tipoUnidadeRepository.saveAndFlush(tipoUnidade);

        int databaseSizeBeforeDelete = tipoUnidadeRepository.findAll().size();

        // Delete the tipoUnidade
        restTipoUnidadeMockMvc.perform(delete("/api/tipo-unidades/{id}", tipoUnidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoUnidade> tipoUnidadeList = tipoUnidadeRepository.findAll();
        assertThat(tipoUnidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoUnidade in Elasticsearch
        verify(mockTipoUnidadeSearchRepository, times(1)).deleteById(tipoUnidade.getId());
    }

    @Test
    @Transactional
    public void searchTipoUnidade() throws Exception {
        // Initialize the database
        tipoUnidadeRepository.saveAndFlush(tipoUnidade);
        when(mockTipoUnidadeSearchRepository.search(queryStringQuery("id:" + tipoUnidade.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoUnidade), PageRequest.of(0, 1), 1));
        // Search the tipoUnidade
        restTipoUnidadeMockMvc.perform(get("/api/_search/tipo-unidades?query=id:" + tipoUnidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoUnidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoUnidade.class);
        TipoUnidade tipoUnidade1 = new TipoUnidade();
        tipoUnidade1.setId(1L);
        TipoUnidade tipoUnidade2 = new TipoUnidade();
        tipoUnidade2.setId(tipoUnidade1.getId());
        assertThat(tipoUnidade1).isEqualTo(tipoUnidade2);
        tipoUnidade2.setId(2L);
        assertThat(tipoUnidade1).isNotEqualTo(tipoUnidade2);
        tipoUnidade1.setId(null);
        assertThat(tipoUnidade1).isNotEqualTo(tipoUnidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoUnidadeDTO.class);
        TipoUnidadeDTO tipoUnidadeDTO1 = new TipoUnidadeDTO();
        tipoUnidadeDTO1.setId(1L);
        TipoUnidadeDTO tipoUnidadeDTO2 = new TipoUnidadeDTO();
        assertThat(tipoUnidadeDTO1).isNotEqualTo(tipoUnidadeDTO2);
        tipoUnidadeDTO2.setId(tipoUnidadeDTO1.getId());
        assertThat(tipoUnidadeDTO1).isEqualTo(tipoUnidadeDTO2);
        tipoUnidadeDTO2.setId(2L);
        assertThat(tipoUnidadeDTO1).isNotEqualTo(tipoUnidadeDTO2);
        tipoUnidadeDTO1.setId(null);
        assertThat(tipoUnidadeDTO1).isNotEqualTo(tipoUnidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoUnidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoUnidadeMapper.fromId(null)).isNull();
    }
}
