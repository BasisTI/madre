package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.Unidade;
import br.com.basis.madre.farmacia.repository.UnidadeRepository;
import br.com.basis.madre.farmacia.repository.search.UnidadeSearchRepository;
import br.com.basis.madre.farmacia.service.UnidadeService;
import br.com.basis.madre.farmacia.service.dto.UnidadeDTO;
import br.com.basis.madre.farmacia.service.mapper.UnidadeMapper;
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
 * Integration tests for the {@link UnidadeResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class UnidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private UnidadeMapper unidadeMapper;

    @Autowired
    private UnidadeService unidadeService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.UnidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadeSearchRepository mockUnidadeSearchRepository;

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

    private MockMvc restUnidadeMockMvc;

    private Unidade unidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeResource unidadeResource = new UnidadeResource(unidadeService);
        this.restUnidadeMockMvc = MockMvcBuilders.standaloneSetup(unidadeResource)
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
    public static Unidade createEntity(EntityManager em) {
        Unidade unidade = new Unidade()
            .nome(DEFAULT_NOME);
        return unidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unidade createUpdatedEntity(EntityManager em) {
        Unidade unidade = new Unidade()
            .nome(UPDATED_NOME);
        return unidade;
    }

    @BeforeEach
    public void initTest() {
        unidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidade() throws Exception {
        int databaseSizeBeforeCreate = unidadeRepository.findAll().size();

        // Create the Unidade
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);
        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Unidade in the database
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Unidade testUnidade = unidadeList.get(unidadeList.size() - 1);
        assertThat(testUnidade.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(1)).save(testUnidade);
    }

    @Test
    @Transactional
    public void createUnidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeRepository.findAll().size();

        // Create the Unidade with an existing ID
        unidade.setId(1L);
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeMockMvc.perform(post("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unidade in the database
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(0)).save(unidade);
    }


    @Test
    @Transactional
    public void getAllUnidades() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        // Get all the unidadeList
        restUnidadeMockMvc.perform(get("/api/unidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        // Get the unidade
        restUnidadeMockMvc.perform(get("/api/unidades/{id}", unidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingUnidade() throws Exception {
        // Get the unidade
        restUnidadeMockMvc.perform(get("/api/unidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        int databaseSizeBeforeUpdate = unidadeRepository.findAll().size();

        // Update the unidade
        Unidade updatedUnidade = unidadeRepository.findById(unidade.getId()).get();
        // Disconnect from session so that the updates on updatedUnidade are not directly saved in db
        em.detach(updatedUnidade);
        updatedUnidade
            .nome(UPDATED_NOME);
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(updatedUnidade);

        restUnidadeMockMvc.perform(put("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Unidade in the database
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeUpdate);
        Unidade testUnidade = unidadeList.get(unidadeList.size() - 1);
        assertThat(testUnidade.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(1)).save(testUnidade);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidade() throws Exception {
        int databaseSizeBeforeUpdate = unidadeRepository.findAll().size();

        // Create the Unidade
        UnidadeDTO unidadeDTO = unidadeMapper.toDto(unidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeMockMvc.perform(put("/api/unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unidade in the database
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(0)).save(unidade);
    }

    @Test
    @Transactional
    public void deleteUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);

        int databaseSizeBeforeDelete = unidadeRepository.findAll().size();

        // Delete the unidade
        restUnidadeMockMvc.perform(delete("/api/unidades/{id}", unidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unidade> unidadeList = unidadeRepository.findAll();
        assertThat(unidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Unidade in Elasticsearch
        verify(mockUnidadeSearchRepository, times(1)).deleteById(unidade.getId());
    }

    @Test
    @Transactional
    public void searchUnidade() throws Exception {
        // Initialize the database
        unidadeRepository.saveAndFlush(unidade);
        when(mockUnidadeSearchRepository.search(queryStringQuery("id:" + unidade.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unidade), PageRequest.of(0, 1), 1));
        // Search the unidade
        restUnidadeMockMvc.perform(get("/api/_search/unidades?query=id:" + unidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unidade.class);
        Unidade unidade1 = new Unidade();
        unidade1.setId(1L);
        Unidade unidade2 = new Unidade();
        unidade2.setId(unidade1.getId());
        assertThat(unidade1).isEqualTo(unidade2);
        unidade2.setId(2L);
        assertThat(unidade1).isNotEqualTo(unidade2);
        unidade1.setId(null);
        assertThat(unidade1).isNotEqualTo(unidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeDTO.class);
        UnidadeDTO unidadeDTO1 = new UnidadeDTO();
        unidadeDTO1.setId(1L);
        UnidadeDTO unidadeDTO2 = new UnidadeDTO();
        assertThat(unidadeDTO1).isNotEqualTo(unidadeDTO2);
        unidadeDTO2.setId(unidadeDTO1.getId());
        assertThat(unidadeDTO1).isEqualTo(unidadeDTO2);
        unidadeDTO2.setId(2L);
        assertThat(unidadeDTO1).isNotEqualTo(unidadeDTO2);
        unidadeDTO1.setId(null);
        assertThat(unidadeDTO1).isNotEqualTo(unidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadeMapper.fromId(null)).isNull();
    }
}
