package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.CirurgiasLeito;
import br.com.basis.madre.prescricao.repository.CirurgiasLeitoRepository;
import br.com.basis.madre.prescricao.repository.search.CirurgiasLeitoSearchRepository;
import br.com.basis.madre.prescricao.service.CirurgiasLeitoService;
import br.com.basis.madre.prescricao.service.dto.CirurgiasLeitoDTO;
import br.com.basis.madre.prescricao.service.mapper.CirurgiasLeitoMapper;
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
 * Integration tests for the {@link CirurgiasLeitoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class CirurgiasLeitoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CirurgiasLeitoRepository cirurgiasLeitoRepository;

    @Autowired
    private CirurgiasLeitoMapper cirurgiasLeitoMapper;

    @Autowired
    private CirurgiasLeitoService cirurgiasLeitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.CirurgiasLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CirurgiasLeitoSearchRepository mockCirurgiasLeitoSearchRepository;

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

    private MockMvc restCirurgiasLeitoMockMvc;

    private CirurgiasLeito cirurgiasLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CirurgiasLeitoResource cirurgiasLeitoResource = new CirurgiasLeitoResource(cirurgiasLeitoService);
        this.restCirurgiasLeitoMockMvc = MockMvcBuilders.standaloneSetup(cirurgiasLeitoResource)
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
    public static CirurgiasLeito createEntity(EntityManager em) {
        CirurgiasLeito cirurgiasLeito = new CirurgiasLeito()
            .descricao(DEFAULT_DESCRICAO);
        return cirurgiasLeito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CirurgiasLeito createUpdatedEntity(EntityManager em) {
        CirurgiasLeito cirurgiasLeito = new CirurgiasLeito()
            .descricao(UPDATED_DESCRICAO);
        return cirurgiasLeito;
    }

    @BeforeEach
    public void initTest() {
        cirurgiasLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createCirurgiasLeito() throws Exception {
        int databaseSizeBeforeCreate = cirurgiasLeitoRepository.findAll().size();

        // Create the CirurgiasLeito
        CirurgiasLeitoDTO cirurgiasLeitoDTO = cirurgiasLeitoMapper.toDto(cirurgiasLeito);
        restCirurgiasLeitoMockMvc.perform(post("/api/cirurgias-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiasLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the CirurgiasLeito in the database
        List<CirurgiasLeito> cirurgiasLeitoList = cirurgiasLeitoRepository.findAll();
        assertThat(cirurgiasLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        CirurgiasLeito testCirurgiasLeito = cirurgiasLeitoList.get(cirurgiasLeitoList.size() - 1);
        assertThat(testCirurgiasLeito.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the CirurgiasLeito in Elasticsearch
        verify(mockCirurgiasLeitoSearchRepository, times(1)).save(testCirurgiasLeito);
    }

    @Test
    @Transactional
    public void createCirurgiasLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cirurgiasLeitoRepository.findAll().size();

        // Create the CirurgiasLeito with an existing ID
        cirurgiasLeito.setId(1L);
        CirurgiasLeitoDTO cirurgiasLeitoDTO = cirurgiasLeitoMapper.toDto(cirurgiasLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCirurgiasLeitoMockMvc.perform(post("/api/cirurgias-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiasLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CirurgiasLeito in the database
        List<CirurgiasLeito> cirurgiasLeitoList = cirurgiasLeitoRepository.findAll();
        assertThat(cirurgiasLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CirurgiasLeito in Elasticsearch
        verify(mockCirurgiasLeitoSearchRepository, times(0)).save(cirurgiasLeito);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cirurgiasLeitoRepository.findAll().size();
        // set the field null
        cirurgiasLeito.setDescricao(null);

        // Create the CirurgiasLeito, which fails.
        CirurgiasLeitoDTO cirurgiasLeitoDTO = cirurgiasLeitoMapper.toDto(cirurgiasLeito);

        restCirurgiasLeitoMockMvc.perform(post("/api/cirurgias-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiasLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<CirurgiasLeito> cirurgiasLeitoList = cirurgiasLeitoRepository.findAll();
        assertThat(cirurgiasLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCirurgiasLeitos() throws Exception {
        // Initialize the database
        cirurgiasLeitoRepository.saveAndFlush(cirurgiasLeito);

        // Get all the cirurgiasLeitoList
        restCirurgiasLeitoMockMvc.perform(get("/api/cirurgias-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cirurgiasLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getCirurgiasLeito() throws Exception {
        // Initialize the database
        cirurgiasLeitoRepository.saveAndFlush(cirurgiasLeito);

        // Get the cirurgiasLeito
        restCirurgiasLeitoMockMvc.perform(get("/api/cirurgias-leitos/{id}", cirurgiasLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cirurgiasLeito.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingCirurgiasLeito() throws Exception {
        // Get the cirurgiasLeito
        restCirurgiasLeitoMockMvc.perform(get("/api/cirurgias-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCirurgiasLeito() throws Exception {
        // Initialize the database
        cirurgiasLeitoRepository.saveAndFlush(cirurgiasLeito);

        int databaseSizeBeforeUpdate = cirurgiasLeitoRepository.findAll().size();

        // Update the cirurgiasLeito
        CirurgiasLeito updatedCirurgiasLeito = cirurgiasLeitoRepository.findById(cirurgiasLeito.getId()).get();
        // Disconnect from session so that the updates on updatedCirurgiasLeito are not directly saved in db
        em.detach(updatedCirurgiasLeito);
        updatedCirurgiasLeito
            .descricao(UPDATED_DESCRICAO);
        CirurgiasLeitoDTO cirurgiasLeitoDTO = cirurgiasLeitoMapper.toDto(updatedCirurgiasLeito);

        restCirurgiasLeitoMockMvc.perform(put("/api/cirurgias-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiasLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the CirurgiasLeito in the database
        List<CirurgiasLeito> cirurgiasLeitoList = cirurgiasLeitoRepository.findAll();
        assertThat(cirurgiasLeitoList).hasSize(databaseSizeBeforeUpdate);
        CirurgiasLeito testCirurgiasLeito = cirurgiasLeitoList.get(cirurgiasLeitoList.size() - 1);
        assertThat(testCirurgiasLeito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the CirurgiasLeito in Elasticsearch
        verify(mockCirurgiasLeitoSearchRepository, times(1)).save(testCirurgiasLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingCirurgiasLeito() throws Exception {
        int databaseSizeBeforeUpdate = cirurgiasLeitoRepository.findAll().size();

        // Create the CirurgiasLeito
        CirurgiasLeitoDTO cirurgiasLeitoDTO = cirurgiasLeitoMapper.toDto(cirurgiasLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCirurgiasLeitoMockMvc.perform(put("/api/cirurgias-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cirurgiasLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CirurgiasLeito in the database
        List<CirurgiasLeito> cirurgiasLeitoList = cirurgiasLeitoRepository.findAll();
        assertThat(cirurgiasLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CirurgiasLeito in Elasticsearch
        verify(mockCirurgiasLeitoSearchRepository, times(0)).save(cirurgiasLeito);
    }

    @Test
    @Transactional
    public void deleteCirurgiasLeito() throws Exception {
        // Initialize the database
        cirurgiasLeitoRepository.saveAndFlush(cirurgiasLeito);

        int databaseSizeBeforeDelete = cirurgiasLeitoRepository.findAll().size();

        // Delete the cirurgiasLeito
        restCirurgiasLeitoMockMvc.perform(delete("/api/cirurgias-leitos/{id}", cirurgiasLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CirurgiasLeito> cirurgiasLeitoList = cirurgiasLeitoRepository.findAll();
        assertThat(cirurgiasLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CirurgiasLeito in Elasticsearch
        verify(mockCirurgiasLeitoSearchRepository, times(1)).deleteById(cirurgiasLeito.getId());
    }

    @Test
    @Transactional
    public void searchCirurgiasLeito() throws Exception {
        // Initialize the database
        cirurgiasLeitoRepository.saveAndFlush(cirurgiasLeito);
        when(mockCirurgiasLeitoSearchRepository.search(queryStringQuery("id:" + cirurgiasLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cirurgiasLeito), PageRequest.of(0, 1), 1));
        // Search the cirurgiasLeito
        restCirurgiasLeitoMockMvc.perform(get("/api/_search/cirurgias-leitos?query=id:" + cirurgiasLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cirurgiasLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CirurgiasLeito.class);
        CirurgiasLeito cirurgiasLeito1 = new CirurgiasLeito();
        cirurgiasLeito1.setId(1L);
        CirurgiasLeito cirurgiasLeito2 = new CirurgiasLeito();
        cirurgiasLeito2.setId(cirurgiasLeito1.getId());
        assertThat(cirurgiasLeito1).isEqualTo(cirurgiasLeito2);
        cirurgiasLeito2.setId(2L);
        assertThat(cirurgiasLeito1).isNotEqualTo(cirurgiasLeito2);
        cirurgiasLeito1.setId(null);
        assertThat(cirurgiasLeito1).isNotEqualTo(cirurgiasLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CirurgiasLeitoDTO.class);
        CirurgiasLeitoDTO cirurgiasLeitoDTO1 = new CirurgiasLeitoDTO();
        cirurgiasLeitoDTO1.setId(1L);
        CirurgiasLeitoDTO cirurgiasLeitoDTO2 = new CirurgiasLeitoDTO();
        assertThat(cirurgiasLeitoDTO1).isNotEqualTo(cirurgiasLeitoDTO2);
        cirurgiasLeitoDTO2.setId(cirurgiasLeitoDTO1.getId());
        assertThat(cirurgiasLeitoDTO1).isEqualTo(cirurgiasLeitoDTO2);
        cirurgiasLeitoDTO2.setId(2L);
        assertThat(cirurgiasLeitoDTO1).isNotEqualTo(cirurgiasLeitoDTO2);
        cirurgiasLeitoDTO1.setId(null);
        assertThat(cirurgiasLeitoDTO1).isNotEqualTo(cirurgiasLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cirurgiasLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cirurgiasLeitoMapper.fromId(null)).isNull();
    }
}
