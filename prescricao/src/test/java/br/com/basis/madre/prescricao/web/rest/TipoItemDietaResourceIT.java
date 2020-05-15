package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.TipoItemDieta;
import br.com.basis.madre.prescricao.repository.TipoItemDietaRepository;
import br.com.basis.madre.prescricao.repository.search.TipoItemDietaSearchRepository;
import br.com.basis.madre.prescricao.service.TipoItemDietaService;
import br.com.basis.madre.prescricao.service.dto.TipoItemDietaDTO;
import br.com.basis.madre.prescricao.service.mapper.TipoItemDietaMapper;
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
 * Integration tests for the {@link TipoItemDietaResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class TipoItemDietaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoItemDietaRepository tipoItemDietaRepository;

    @Autowired
    private TipoItemDietaMapper tipoItemDietaMapper;

    @Autowired
    private TipoItemDietaService tipoItemDietaService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.TipoItemDietaSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoItemDietaSearchRepository mockTipoItemDietaSearchRepository;

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

    private MockMvc restTipoItemDietaMockMvc;

    private TipoItemDieta tipoItemDieta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoItemDietaResource tipoItemDietaResource = new TipoItemDietaResource(tipoItemDietaService);
        this.restTipoItemDietaMockMvc = MockMvcBuilders.standaloneSetup(tipoItemDietaResource)
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
    public static TipoItemDieta createEntity(EntityManager em) {
        TipoItemDieta tipoItemDieta = new TipoItemDieta()
            .descricao(DEFAULT_DESCRICAO);
        return tipoItemDieta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoItemDieta createUpdatedEntity(EntityManager em) {
        TipoItemDieta tipoItemDieta = new TipoItemDieta()
            .descricao(UPDATED_DESCRICAO);
        return tipoItemDieta;
    }

    @BeforeEach
    public void initTest() {
        tipoItemDieta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoItemDieta() throws Exception {
        int databaseSizeBeforeCreate = tipoItemDietaRepository.findAll().size();

        // Create the TipoItemDieta
        TipoItemDietaDTO tipoItemDietaDTO = tipoItemDietaMapper.toDto(tipoItemDieta);
        restTipoItemDietaMockMvc.perform(post("/api/tipo-item-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoItemDietaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoItemDieta in the database
        List<TipoItemDieta> tipoItemDietaList = tipoItemDietaRepository.findAll();
        assertThat(tipoItemDietaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoItemDieta testTipoItemDieta = tipoItemDietaList.get(tipoItemDietaList.size() - 1);
        assertThat(testTipoItemDieta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the TipoItemDieta in Elasticsearch
        verify(mockTipoItemDietaSearchRepository, times(1)).save(testTipoItemDieta);
    }

    @Test
    @Transactional
    public void createTipoItemDietaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoItemDietaRepository.findAll().size();

        // Create the TipoItemDieta with an existing ID
        tipoItemDieta.setId(1L);
        TipoItemDietaDTO tipoItemDietaDTO = tipoItemDietaMapper.toDto(tipoItemDieta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoItemDietaMockMvc.perform(post("/api/tipo-item-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoItemDietaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoItemDieta in the database
        List<TipoItemDieta> tipoItemDietaList = tipoItemDietaRepository.findAll();
        assertThat(tipoItemDietaList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoItemDieta in Elasticsearch
        verify(mockTipoItemDietaSearchRepository, times(0)).save(tipoItemDieta);
    }


    @Test
    @Transactional
    public void getAllTipoItemDietas() throws Exception {
        // Initialize the database
        tipoItemDietaRepository.saveAndFlush(tipoItemDieta);

        // Get all the tipoItemDietaList
        restTipoItemDietaMockMvc.perform(get("/api/tipo-item-dietas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoItemDieta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTipoItemDieta() throws Exception {
        // Initialize the database
        tipoItemDietaRepository.saveAndFlush(tipoItemDieta);

        // Get the tipoItemDieta
        restTipoItemDietaMockMvc.perform(get("/api/tipo-item-dietas/{id}", tipoItemDieta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoItemDieta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingTipoItemDieta() throws Exception {
        // Get the tipoItemDieta
        restTipoItemDietaMockMvc.perform(get("/api/tipo-item-dietas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoItemDieta() throws Exception {
        // Initialize the database
        tipoItemDietaRepository.saveAndFlush(tipoItemDieta);

        int databaseSizeBeforeUpdate = tipoItemDietaRepository.findAll().size();

        // Update the tipoItemDieta
        TipoItemDieta updatedTipoItemDieta = tipoItemDietaRepository.findById(tipoItemDieta.getId()).get();
        // Disconnect from session so that the updates on updatedTipoItemDieta are not directly saved in db
        em.detach(updatedTipoItemDieta);
        updatedTipoItemDieta
            .descricao(UPDATED_DESCRICAO);
        TipoItemDietaDTO tipoItemDietaDTO = tipoItemDietaMapper.toDto(updatedTipoItemDieta);

        restTipoItemDietaMockMvc.perform(put("/api/tipo-item-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoItemDietaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoItemDieta in the database
        List<TipoItemDieta> tipoItemDietaList = tipoItemDietaRepository.findAll();
        assertThat(tipoItemDietaList).hasSize(databaseSizeBeforeUpdate);
        TipoItemDieta testTipoItemDieta = tipoItemDietaList.get(tipoItemDietaList.size() - 1);
        assertThat(testTipoItemDieta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the TipoItemDieta in Elasticsearch
        verify(mockTipoItemDietaSearchRepository, times(1)).save(testTipoItemDieta);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoItemDieta() throws Exception {
        int databaseSizeBeforeUpdate = tipoItemDietaRepository.findAll().size();

        // Create the TipoItemDieta
        TipoItemDietaDTO tipoItemDietaDTO = tipoItemDietaMapper.toDto(tipoItemDieta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoItemDietaMockMvc.perform(put("/api/tipo-item-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoItemDietaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoItemDieta in the database
        List<TipoItemDieta> tipoItemDietaList = tipoItemDietaRepository.findAll();
        assertThat(tipoItemDietaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoItemDieta in Elasticsearch
        verify(mockTipoItemDietaSearchRepository, times(0)).save(tipoItemDieta);
    }

    @Test
    @Transactional
    public void deleteTipoItemDieta() throws Exception {
        // Initialize the database
        tipoItemDietaRepository.saveAndFlush(tipoItemDieta);

        int databaseSizeBeforeDelete = tipoItemDietaRepository.findAll().size();

        // Delete the tipoItemDieta
        restTipoItemDietaMockMvc.perform(delete("/api/tipo-item-dietas/{id}", tipoItemDieta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoItemDieta> tipoItemDietaList = tipoItemDietaRepository.findAll();
        assertThat(tipoItemDietaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoItemDieta in Elasticsearch
        verify(mockTipoItemDietaSearchRepository, times(1)).deleteById(tipoItemDieta.getId());
    }

    @Test
    @Transactional
    public void searchTipoItemDieta() throws Exception {
        // Initialize the database
        tipoItemDietaRepository.saveAndFlush(tipoItemDieta);
        when(mockTipoItemDietaSearchRepository.search(queryStringQuery("id:" + tipoItemDieta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoItemDieta), PageRequest.of(0, 1), 1));
        // Search the tipoItemDieta
        restTipoItemDietaMockMvc.perform(get("/api/_search/tipo-item-dietas?query=id:" + tipoItemDieta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoItemDieta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoItemDieta.class);
        TipoItemDieta tipoItemDieta1 = new TipoItemDieta();
        tipoItemDieta1.setId(1L);
        TipoItemDieta tipoItemDieta2 = new TipoItemDieta();
        tipoItemDieta2.setId(tipoItemDieta1.getId());
        assertThat(tipoItemDieta1).isEqualTo(tipoItemDieta2);
        tipoItemDieta2.setId(2L);
        assertThat(tipoItemDieta1).isNotEqualTo(tipoItemDieta2);
        tipoItemDieta1.setId(null);
        assertThat(tipoItemDieta1).isNotEqualTo(tipoItemDieta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoItemDietaDTO.class);
        TipoItemDietaDTO tipoItemDietaDTO1 = new TipoItemDietaDTO();
        tipoItemDietaDTO1.setId(1L);
        TipoItemDietaDTO tipoItemDietaDTO2 = new TipoItemDietaDTO();
        assertThat(tipoItemDietaDTO1).isNotEqualTo(tipoItemDietaDTO2);
        tipoItemDietaDTO2.setId(tipoItemDietaDTO1.getId());
        assertThat(tipoItemDietaDTO1).isEqualTo(tipoItemDietaDTO2);
        tipoItemDietaDTO2.setId(2L);
        assertThat(tipoItemDietaDTO1).isNotEqualTo(tipoItemDietaDTO2);
        tipoItemDietaDTO1.setId(null);
        assertThat(tipoItemDietaDTO1).isNotEqualTo(tipoItemDietaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoItemDietaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoItemDietaMapper.fromId(null)).isNull();
    }
}
