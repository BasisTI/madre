package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.UnidadeFuncional;
import br.com.basis.madre.repository.UnidadeFuncionalRepository;
import br.com.basis.madre.repository.search.UnidadeFuncionalSearchRepository;
import br.com.basis.madre.service.UnidadeFuncionalService;
import br.com.basis.madre.service.dto.UnidadeFuncionalDTO;
import br.com.basis.madre.service.mapper.UnidadeFuncionalMapper;
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
 * Integration tests for the {@link UnidadeFuncionalResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class UnidadeFuncionalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private UnidadeFuncionalRepository unidadeFuncionalRepository;

    @Autowired
    private UnidadeFuncionalMapper unidadeFuncionalMapper;

    @Autowired
    private UnidadeFuncionalService unidadeFuncionalService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.UnidadeFuncionalSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadeFuncionalSearchRepository mockUnidadeFuncionalSearchRepository;

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

    private MockMvc restUnidadeFuncionalMockMvc;

    private UnidadeFuncional unidadeFuncional;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeFuncionalResource unidadeFuncionalResource = new UnidadeFuncionalResource(unidadeFuncionalService);
        this.restUnidadeFuncionalMockMvc = MockMvcBuilders.standaloneSetup(unidadeFuncionalResource)
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
    public static UnidadeFuncional createEntity(EntityManager em) {
        UnidadeFuncional unidadeFuncional = new UnidadeFuncional()
            .nome(DEFAULT_NOME);
        return unidadeFuncional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeFuncional createUpdatedEntity(EntityManager em) {
        UnidadeFuncional unidadeFuncional = new UnidadeFuncional()
            .nome(UPDATED_NOME);
        return unidadeFuncional;
    }

    @BeforeEach
    public void initTest() {
        unidadeFuncional = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeFuncional() throws Exception {
        int databaseSizeBeforeCreate = unidadeFuncionalRepository.findAll().size();

        // Create the UnidadeFuncional
        UnidadeFuncionalDTO unidadeFuncionalDTO = unidadeFuncionalMapper.toDto(unidadeFuncional);
        restUnidadeFuncionalMockMvc.perform(post("/api/unidade-funcionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFuncionalDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadeFuncional in the database
        List<UnidadeFuncional> unidadeFuncionalList = unidadeFuncionalRepository.findAll();
        assertThat(unidadeFuncionalList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeFuncional testUnidadeFuncional = unidadeFuncionalList.get(unidadeFuncionalList.size() - 1);
        assertThat(testUnidadeFuncional.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the UnidadeFuncional in Elasticsearch
        verify(mockUnidadeFuncionalSearchRepository, times(1)).save(testUnidadeFuncional);
    }

    @Test
    @Transactional
    public void createUnidadeFuncionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeFuncionalRepository.findAll().size();

        // Create the UnidadeFuncional with an existing ID
        unidadeFuncional.setId(1L);
        UnidadeFuncionalDTO unidadeFuncionalDTO = unidadeFuncionalMapper.toDto(unidadeFuncional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeFuncionalMockMvc.perform(post("/api/unidade-funcionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFuncionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeFuncional in the database
        List<UnidadeFuncional> unidadeFuncionalList = unidadeFuncionalRepository.findAll();
        assertThat(unidadeFuncionalList).hasSize(databaseSizeBeforeCreate);

        // Validate the UnidadeFuncional in Elasticsearch
        verify(mockUnidadeFuncionalSearchRepository, times(0)).save(unidadeFuncional);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeFuncionalRepository.findAll().size();
        // set the field null
        unidadeFuncional.setNome(null);

        // Create the UnidadeFuncional, which fails.
        UnidadeFuncionalDTO unidadeFuncionalDTO = unidadeFuncionalMapper.toDto(unidadeFuncional);

        restUnidadeFuncionalMockMvc.perform(post("/api/unidade-funcionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFuncionalDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeFuncional> unidadeFuncionalList = unidadeFuncionalRepository.findAll();
        assertThat(unidadeFuncionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeFuncionals() throws Exception {
        // Initialize the database
        unidadeFuncionalRepository.saveAndFlush(unidadeFuncional);

        // Get all the unidadeFuncionalList
        restUnidadeFuncionalMockMvc.perform(get("/api/unidade-funcionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeFuncional.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getUnidadeFuncional() throws Exception {
        // Initialize the database
        unidadeFuncionalRepository.saveAndFlush(unidadeFuncional);

        // Get the unidadeFuncional
        restUnidadeFuncionalMockMvc.perform(get("/api/unidade-funcionals/{id}", unidadeFuncional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeFuncional.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeFuncional() throws Exception {
        // Get the unidadeFuncional
        restUnidadeFuncionalMockMvc.perform(get("/api/unidade-funcionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeFuncional() throws Exception {
        // Initialize the database
        unidadeFuncionalRepository.saveAndFlush(unidadeFuncional);

        int databaseSizeBeforeUpdate = unidadeFuncionalRepository.findAll().size();

        // Update the unidadeFuncional
        UnidadeFuncional updatedUnidadeFuncional = unidadeFuncionalRepository.findById(unidadeFuncional.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeFuncional are not directly saved in db
        em.detach(updatedUnidadeFuncional);
        updatedUnidadeFuncional
            .nome(UPDATED_NOME);
        UnidadeFuncionalDTO unidadeFuncionalDTO = unidadeFuncionalMapper.toDto(updatedUnidadeFuncional);

        restUnidadeFuncionalMockMvc.perform(put("/api/unidade-funcionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFuncionalDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadeFuncional in the database
        List<UnidadeFuncional> unidadeFuncionalList = unidadeFuncionalRepository.findAll();
        assertThat(unidadeFuncionalList).hasSize(databaseSizeBeforeUpdate);
        UnidadeFuncional testUnidadeFuncional = unidadeFuncionalList.get(unidadeFuncionalList.size() - 1);
        assertThat(testUnidadeFuncional.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the UnidadeFuncional in Elasticsearch
        verify(mockUnidadeFuncionalSearchRepository, times(1)).save(testUnidadeFuncional);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeFuncional() throws Exception {
        int databaseSizeBeforeUpdate = unidadeFuncionalRepository.findAll().size();

        // Create the UnidadeFuncional
        UnidadeFuncionalDTO unidadeFuncionalDTO = unidadeFuncionalMapper.toDto(unidadeFuncional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeFuncionalMockMvc.perform(put("/api/unidade-funcionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeFuncionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeFuncional in the database
        List<UnidadeFuncional> unidadeFuncionalList = unidadeFuncionalRepository.findAll();
        assertThat(unidadeFuncionalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UnidadeFuncional in Elasticsearch
        verify(mockUnidadeFuncionalSearchRepository, times(0)).save(unidadeFuncional);
    }

    @Test
    @Transactional
    public void deleteUnidadeFuncional() throws Exception {
        // Initialize the database
        unidadeFuncionalRepository.saveAndFlush(unidadeFuncional);

        int databaseSizeBeforeDelete = unidadeFuncionalRepository.findAll().size();

        // Delete the unidadeFuncional
        restUnidadeFuncionalMockMvc.perform(delete("/api/unidade-funcionals/{id}", unidadeFuncional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeFuncional> unidadeFuncionalList = unidadeFuncionalRepository.findAll();
        assertThat(unidadeFuncionalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UnidadeFuncional in Elasticsearch
        verify(mockUnidadeFuncionalSearchRepository, times(1)).deleteById(unidadeFuncional.getId());
    }

    @Test
    @Transactional
    public void searchUnidadeFuncional() throws Exception {
        // Initialize the database
        unidadeFuncionalRepository.saveAndFlush(unidadeFuncional);
        when(mockUnidadeFuncionalSearchRepository.search(queryStringQuery("id:" + unidadeFuncional.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unidadeFuncional), PageRequest.of(0, 1), 1));
        // Search the unidadeFuncional
        restUnidadeFuncionalMockMvc.perform(get("/api/_search/unidade-funcionals?query=id:" + unidadeFuncional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeFuncional.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeFuncional.class);
        UnidadeFuncional unidadeFuncional1 = new UnidadeFuncional();
        unidadeFuncional1.setId(1L);
        UnidadeFuncional unidadeFuncional2 = new UnidadeFuncional();
        unidadeFuncional2.setId(unidadeFuncional1.getId());
        assertThat(unidadeFuncional1).isEqualTo(unidadeFuncional2);
        unidadeFuncional2.setId(2L);
        assertThat(unidadeFuncional1).isNotEqualTo(unidadeFuncional2);
        unidadeFuncional1.setId(null);
        assertThat(unidadeFuncional1).isNotEqualTo(unidadeFuncional2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeFuncionalDTO.class);
        UnidadeFuncionalDTO unidadeFuncionalDTO1 = new UnidadeFuncionalDTO();
        unidadeFuncionalDTO1.setId(1L);
        UnidadeFuncionalDTO unidadeFuncionalDTO2 = new UnidadeFuncionalDTO();
        assertThat(unidadeFuncionalDTO1).isNotEqualTo(unidadeFuncionalDTO2);
        unidadeFuncionalDTO2.setId(unidadeFuncionalDTO1.getId());
        assertThat(unidadeFuncionalDTO1).isEqualTo(unidadeFuncionalDTO2);
        unidadeFuncionalDTO2.setId(2L);
        assertThat(unidadeFuncionalDTO1).isNotEqualTo(unidadeFuncionalDTO2);
        unidadeFuncionalDTO1.setId(null);
        assertThat(unidadeFuncionalDTO1).isNotEqualTo(unidadeFuncionalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadeFuncionalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadeFuncionalMapper.fromId(null)).isNull();
    }
}
