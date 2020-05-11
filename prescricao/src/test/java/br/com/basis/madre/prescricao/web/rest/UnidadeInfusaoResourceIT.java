package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.UnidadeInfusao;
import br.com.basis.madre.prescricao.repository.UnidadeInfusaoRepository;
import br.com.basis.madre.prescricao.repository.search.UnidadeInfusaoSearchRepository;
import br.com.basis.madre.prescricao.service.UnidadeInfusaoService;
import br.com.basis.madre.prescricao.service.dto.UnidadeInfusaoDTO;
import br.com.basis.madre.prescricao.service.mapper.UnidadeInfusaoMapper;
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
 * Integration tests for the {@link UnidadeInfusaoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class UnidadeInfusaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    @Autowired
    private UnidadeInfusaoRepository unidadeInfusaoRepository;

    @Autowired
    private UnidadeInfusaoMapper unidadeInfusaoMapper;

    @Autowired
    private UnidadeInfusaoService unidadeInfusaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.UnidadeInfusaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadeInfusaoSearchRepository mockUnidadeInfusaoSearchRepository;

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

    private MockMvc restUnidadeInfusaoMockMvc;

    private UnidadeInfusao unidadeInfusao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeInfusaoResource unidadeInfusaoResource = new UnidadeInfusaoResource(unidadeInfusaoService);
        this.restUnidadeInfusaoMockMvc = MockMvcBuilders.standaloneSetup(unidadeInfusaoResource)
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
    public static UnidadeInfusao createEntity(EntityManager em) {
        UnidadeInfusao unidadeInfusao = new UnidadeInfusao()
            .descricao(DEFAULT_DESCRICAO)
            .sigla(DEFAULT_SIGLA);
        return unidadeInfusao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeInfusao createUpdatedEntity(EntityManager em) {
        UnidadeInfusao unidadeInfusao = new UnidadeInfusao()
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        return unidadeInfusao;
    }

    @BeforeEach
    public void initTest() {
        unidadeInfusao = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeInfusao() throws Exception {
        int databaseSizeBeforeCreate = unidadeInfusaoRepository.findAll().size();

        // Create the UnidadeInfusao
        UnidadeInfusaoDTO unidadeInfusaoDTO = unidadeInfusaoMapper.toDto(unidadeInfusao);
        restUnidadeInfusaoMockMvc.perform(post("/api/unidade-infusaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeInfusaoDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadeInfusao in the database
        List<UnidadeInfusao> unidadeInfusaoList = unidadeInfusaoRepository.findAll();
        assertThat(unidadeInfusaoList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeInfusao testUnidadeInfusao = unidadeInfusaoList.get(unidadeInfusaoList.size() - 1);
        assertThat(testUnidadeInfusao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testUnidadeInfusao.getSigla()).isEqualTo(DEFAULT_SIGLA);

        // Validate the UnidadeInfusao in Elasticsearch
        verify(mockUnidadeInfusaoSearchRepository, times(1)).save(testUnidadeInfusao);
    }

    @Test
    @Transactional
    public void createUnidadeInfusaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeInfusaoRepository.findAll().size();

        // Create the UnidadeInfusao with an existing ID
        unidadeInfusao.setId(1L);
        UnidadeInfusaoDTO unidadeInfusaoDTO = unidadeInfusaoMapper.toDto(unidadeInfusao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeInfusaoMockMvc.perform(post("/api/unidade-infusaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeInfusaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeInfusao in the database
        List<UnidadeInfusao> unidadeInfusaoList = unidadeInfusaoRepository.findAll();
        assertThat(unidadeInfusaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the UnidadeInfusao in Elasticsearch
        verify(mockUnidadeInfusaoSearchRepository, times(0)).save(unidadeInfusao);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeInfusaoRepository.findAll().size();
        // set the field null
        unidadeInfusao.setDescricao(null);

        // Create the UnidadeInfusao, which fails.
        UnidadeInfusaoDTO unidadeInfusaoDTO = unidadeInfusaoMapper.toDto(unidadeInfusao);

        restUnidadeInfusaoMockMvc.perform(post("/api/unidade-infusaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeInfusaoDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeInfusao> unidadeInfusaoList = unidadeInfusaoRepository.findAll();
        assertThat(unidadeInfusaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeInfusaoRepository.findAll().size();
        // set the field null
        unidadeInfusao.setSigla(null);

        // Create the UnidadeInfusao, which fails.
        UnidadeInfusaoDTO unidadeInfusaoDTO = unidadeInfusaoMapper.toDto(unidadeInfusao);

        restUnidadeInfusaoMockMvc.perform(post("/api/unidade-infusaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeInfusaoDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeInfusao> unidadeInfusaoList = unidadeInfusaoRepository.findAll();
        assertThat(unidadeInfusaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeInfusaos() throws Exception {
        // Initialize the database
        unidadeInfusaoRepository.saveAndFlush(unidadeInfusao);

        // Get all the unidadeInfusaoList
        restUnidadeInfusaoMockMvc.perform(get("/api/unidade-infusaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeInfusao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getUnidadeInfusao() throws Exception {
        // Initialize the database
        unidadeInfusaoRepository.saveAndFlush(unidadeInfusao);

        // Get the unidadeInfusao
        restUnidadeInfusaoMockMvc.perform(get("/api/unidade-infusaos/{id}", unidadeInfusao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeInfusao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeInfusao() throws Exception {
        // Get the unidadeInfusao
        restUnidadeInfusaoMockMvc.perform(get("/api/unidade-infusaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeInfusao() throws Exception {
        // Initialize the database
        unidadeInfusaoRepository.saveAndFlush(unidadeInfusao);

        int databaseSizeBeforeUpdate = unidadeInfusaoRepository.findAll().size();

        // Update the unidadeInfusao
        UnidadeInfusao updatedUnidadeInfusao = unidadeInfusaoRepository.findById(unidadeInfusao.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeInfusao are not directly saved in db
        em.detach(updatedUnidadeInfusao);
        updatedUnidadeInfusao
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        UnidadeInfusaoDTO unidadeInfusaoDTO = unidadeInfusaoMapper.toDto(updatedUnidadeInfusao);

        restUnidadeInfusaoMockMvc.perform(put("/api/unidade-infusaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeInfusaoDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadeInfusao in the database
        List<UnidadeInfusao> unidadeInfusaoList = unidadeInfusaoRepository.findAll();
        assertThat(unidadeInfusaoList).hasSize(databaseSizeBeforeUpdate);
        UnidadeInfusao testUnidadeInfusao = unidadeInfusaoList.get(unidadeInfusaoList.size() - 1);
        assertThat(testUnidadeInfusao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testUnidadeInfusao.getSigla()).isEqualTo(UPDATED_SIGLA);

        // Validate the UnidadeInfusao in Elasticsearch
        verify(mockUnidadeInfusaoSearchRepository, times(1)).save(testUnidadeInfusao);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeInfusao() throws Exception {
        int databaseSizeBeforeUpdate = unidadeInfusaoRepository.findAll().size();

        // Create the UnidadeInfusao
        UnidadeInfusaoDTO unidadeInfusaoDTO = unidadeInfusaoMapper.toDto(unidadeInfusao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeInfusaoMockMvc.perform(put("/api/unidade-infusaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeInfusaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeInfusao in the database
        List<UnidadeInfusao> unidadeInfusaoList = unidadeInfusaoRepository.findAll();
        assertThat(unidadeInfusaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UnidadeInfusao in Elasticsearch
        verify(mockUnidadeInfusaoSearchRepository, times(0)).save(unidadeInfusao);
    }

    @Test
    @Transactional
    public void deleteUnidadeInfusao() throws Exception {
        // Initialize the database
        unidadeInfusaoRepository.saveAndFlush(unidadeInfusao);

        int databaseSizeBeforeDelete = unidadeInfusaoRepository.findAll().size();

        // Delete the unidadeInfusao
        restUnidadeInfusaoMockMvc.perform(delete("/api/unidade-infusaos/{id}", unidadeInfusao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeInfusao> unidadeInfusaoList = unidadeInfusaoRepository.findAll();
        assertThat(unidadeInfusaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UnidadeInfusao in Elasticsearch
        verify(mockUnidadeInfusaoSearchRepository, times(1)).deleteById(unidadeInfusao.getId());
    }

    @Test
    @Transactional
    public void searchUnidadeInfusao() throws Exception {
        // Initialize the database
        unidadeInfusaoRepository.saveAndFlush(unidadeInfusao);
        when(mockUnidadeInfusaoSearchRepository.search(queryStringQuery("id:" + unidadeInfusao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unidadeInfusao), PageRequest.of(0, 1), 1));
        // Search the unidadeInfusao
        restUnidadeInfusaoMockMvc.perform(get("/api/_search/unidade-infusaos?query=id:" + unidadeInfusao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeInfusao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeInfusao.class);
        UnidadeInfusao unidadeInfusao1 = new UnidadeInfusao();
        unidadeInfusao1.setId(1L);
        UnidadeInfusao unidadeInfusao2 = new UnidadeInfusao();
        unidadeInfusao2.setId(unidadeInfusao1.getId());
        assertThat(unidadeInfusao1).isEqualTo(unidadeInfusao2);
        unidadeInfusao2.setId(2L);
        assertThat(unidadeInfusao1).isNotEqualTo(unidadeInfusao2);
        unidadeInfusao1.setId(null);
        assertThat(unidadeInfusao1).isNotEqualTo(unidadeInfusao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeInfusaoDTO.class);
        UnidadeInfusaoDTO unidadeInfusaoDTO1 = new UnidadeInfusaoDTO();
        unidadeInfusaoDTO1.setId(1L);
        UnidadeInfusaoDTO unidadeInfusaoDTO2 = new UnidadeInfusaoDTO();
        assertThat(unidadeInfusaoDTO1).isNotEqualTo(unidadeInfusaoDTO2);
        unidadeInfusaoDTO2.setId(unidadeInfusaoDTO1.getId());
        assertThat(unidadeInfusaoDTO1).isEqualTo(unidadeInfusaoDTO2);
        unidadeInfusaoDTO2.setId(2L);
        assertThat(unidadeInfusaoDTO1).isNotEqualTo(unidadeInfusaoDTO2);
        unidadeInfusaoDTO1.setId(null);
        assertThat(unidadeInfusaoDTO1).isNotEqualTo(unidadeInfusaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadeInfusaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadeInfusaoMapper.fromId(null)).isNull();
    }
}
