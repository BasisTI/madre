package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.SituacaoDeLeito;
import br.com.basis.madre.repository.SituacaoDeLeitoRepository;
import br.com.basis.madre.repository.search.SituacaoDeLeitoSearchRepository;
import br.com.basis.madre.service.SituacaoDeLeitoService;
import br.com.basis.madre.service.dto.SituacaoDeLeitoDTO;
import br.com.basis.madre.service.mapper.SituacaoDeLeitoMapper;
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
 * Integration tests for the {@link SituacaoDeLeitoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class SituacaoDeLeitoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private SituacaoDeLeitoRepository situacaoDeLeitoRepository;

    @Autowired
    private SituacaoDeLeitoMapper situacaoDeLeitoMapper;

    @Autowired
    private SituacaoDeLeitoService situacaoDeLeitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.SituacaoDeLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private SituacaoDeLeitoSearchRepository mockSituacaoDeLeitoSearchRepository;

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

    private MockMvc restSituacaoDeLeitoMockMvc;

    private SituacaoDeLeito situacaoDeLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SituacaoDeLeitoResource situacaoDeLeitoResource = new SituacaoDeLeitoResource(situacaoDeLeitoService);
        this.restSituacaoDeLeitoMockMvc = MockMvcBuilders.standaloneSetup(situacaoDeLeitoResource)
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
    public static SituacaoDeLeito createEntity(EntityManager em) {
        SituacaoDeLeito situacaoDeLeito = new SituacaoDeLeito()
            .nome(DEFAULT_NOME);
        return situacaoDeLeito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituacaoDeLeito createUpdatedEntity(EntityManager em) {
        SituacaoDeLeito situacaoDeLeito = new SituacaoDeLeito()
            .nome(UPDATED_NOME);
        return situacaoDeLeito;
    }

    @BeforeEach
    public void initTest() {
        situacaoDeLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createSituacaoDeLeito() throws Exception {
        int databaseSizeBeforeCreate = situacaoDeLeitoRepository.findAll().size();

        // Create the SituacaoDeLeito
        SituacaoDeLeitoDTO situacaoDeLeitoDTO = situacaoDeLeitoMapper.toDto(situacaoDeLeito);
        restSituacaoDeLeitoMockMvc.perform(post("/api/situacao-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoDeLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the SituacaoDeLeito in the database
        List<SituacaoDeLeito> situacaoDeLeitoList = situacaoDeLeitoRepository.findAll();
        assertThat(situacaoDeLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        SituacaoDeLeito testSituacaoDeLeito = situacaoDeLeitoList.get(situacaoDeLeitoList.size() - 1);
        assertThat(testSituacaoDeLeito.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the SituacaoDeLeito in Elasticsearch
        verify(mockSituacaoDeLeitoSearchRepository, times(1)).save(testSituacaoDeLeito);
    }

    @Test
    @Transactional
    public void createSituacaoDeLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = situacaoDeLeitoRepository.findAll().size();

        // Create the SituacaoDeLeito with an existing ID
        situacaoDeLeito.setId(1L);
        SituacaoDeLeitoDTO situacaoDeLeitoDTO = situacaoDeLeitoMapper.toDto(situacaoDeLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituacaoDeLeitoMockMvc.perform(post("/api/situacao-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SituacaoDeLeito in the database
        List<SituacaoDeLeito> situacaoDeLeitoList = situacaoDeLeitoRepository.findAll();
        assertThat(situacaoDeLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the SituacaoDeLeito in Elasticsearch
        verify(mockSituacaoDeLeitoSearchRepository, times(0)).save(situacaoDeLeito);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = situacaoDeLeitoRepository.findAll().size();
        // set the field null
        situacaoDeLeito.setNome(null);

        // Create the SituacaoDeLeito, which fails.
        SituacaoDeLeitoDTO situacaoDeLeitoDTO = situacaoDeLeitoMapper.toDto(situacaoDeLeito);

        restSituacaoDeLeitoMockMvc.perform(post("/api/situacao-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<SituacaoDeLeito> situacaoDeLeitoList = situacaoDeLeitoRepository.findAll();
        assertThat(situacaoDeLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSituacaoDeLeitos() throws Exception {
        // Initialize the database
        situacaoDeLeitoRepository.saveAndFlush(situacaoDeLeito);

        // Get all the situacaoDeLeitoList
        restSituacaoDeLeitoMockMvc.perform(get("/api/situacao-de-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situacaoDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getSituacaoDeLeito() throws Exception {
        // Initialize the database
        situacaoDeLeitoRepository.saveAndFlush(situacaoDeLeito);

        // Get the situacaoDeLeito
        restSituacaoDeLeitoMockMvc.perform(get("/api/situacao-de-leitos/{id}", situacaoDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(situacaoDeLeito.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingSituacaoDeLeito() throws Exception {
        // Get the situacaoDeLeito
        restSituacaoDeLeitoMockMvc.perform(get("/api/situacao-de-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituacaoDeLeito() throws Exception {
        // Initialize the database
        situacaoDeLeitoRepository.saveAndFlush(situacaoDeLeito);

        int databaseSizeBeforeUpdate = situacaoDeLeitoRepository.findAll().size();

        // Update the situacaoDeLeito
        SituacaoDeLeito updatedSituacaoDeLeito = situacaoDeLeitoRepository.findById(situacaoDeLeito.getId()).get();
        // Disconnect from session so that the updates on updatedSituacaoDeLeito are not directly saved in db
        em.detach(updatedSituacaoDeLeito);
        updatedSituacaoDeLeito
            .nome(UPDATED_NOME);
        SituacaoDeLeitoDTO situacaoDeLeitoDTO = situacaoDeLeitoMapper.toDto(updatedSituacaoDeLeito);

        restSituacaoDeLeitoMockMvc.perform(put("/api/situacao-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoDeLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the SituacaoDeLeito in the database
        List<SituacaoDeLeito> situacaoDeLeitoList = situacaoDeLeitoRepository.findAll();
        assertThat(situacaoDeLeitoList).hasSize(databaseSizeBeforeUpdate);
        SituacaoDeLeito testSituacaoDeLeito = situacaoDeLeitoList.get(situacaoDeLeitoList.size() - 1);
        assertThat(testSituacaoDeLeito.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the SituacaoDeLeito in Elasticsearch
        verify(mockSituacaoDeLeitoSearchRepository, times(1)).save(testSituacaoDeLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingSituacaoDeLeito() throws Exception {
        int databaseSizeBeforeUpdate = situacaoDeLeitoRepository.findAll().size();

        // Create the SituacaoDeLeito
        SituacaoDeLeitoDTO situacaoDeLeitoDTO = situacaoDeLeitoMapper.toDto(situacaoDeLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituacaoDeLeitoMockMvc.perform(put("/api/situacao-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situacaoDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SituacaoDeLeito in the database
        List<SituacaoDeLeito> situacaoDeLeitoList = situacaoDeLeitoRepository.findAll();
        assertThat(situacaoDeLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SituacaoDeLeito in Elasticsearch
        verify(mockSituacaoDeLeitoSearchRepository, times(0)).save(situacaoDeLeito);
    }

    @Test
    @Transactional
    public void deleteSituacaoDeLeito() throws Exception {
        // Initialize the database
        situacaoDeLeitoRepository.saveAndFlush(situacaoDeLeito);

        int databaseSizeBeforeDelete = situacaoDeLeitoRepository.findAll().size();

        // Delete the situacaoDeLeito
        restSituacaoDeLeitoMockMvc.perform(delete("/api/situacao-de-leitos/{id}", situacaoDeLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SituacaoDeLeito> situacaoDeLeitoList = situacaoDeLeitoRepository.findAll();
        assertThat(situacaoDeLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SituacaoDeLeito in Elasticsearch
        verify(mockSituacaoDeLeitoSearchRepository, times(1)).deleteById(situacaoDeLeito.getId());
    }

    @Test
    @Transactional
    public void searchSituacaoDeLeito() throws Exception {
        // Initialize the database
        situacaoDeLeitoRepository.saveAndFlush(situacaoDeLeito);
        when(mockSituacaoDeLeitoSearchRepository.search(queryStringQuery("id:" + situacaoDeLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(situacaoDeLeito), PageRequest.of(0, 1), 1));
        // Search the situacaoDeLeito
        restSituacaoDeLeitoMockMvc.perform(get("/api/_search/situacao-de-leitos?query=id:" + situacaoDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situacaoDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacaoDeLeito.class);
        SituacaoDeLeito situacaoDeLeito1 = new SituacaoDeLeito();
        situacaoDeLeito1.setId(1L);
        SituacaoDeLeito situacaoDeLeito2 = new SituacaoDeLeito();
        situacaoDeLeito2.setId(situacaoDeLeito1.getId());
        assertThat(situacaoDeLeito1).isEqualTo(situacaoDeLeito2);
        situacaoDeLeito2.setId(2L);
        assertThat(situacaoDeLeito1).isNotEqualTo(situacaoDeLeito2);
        situacaoDeLeito1.setId(null);
        assertThat(situacaoDeLeito1).isNotEqualTo(situacaoDeLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacaoDeLeitoDTO.class);
        SituacaoDeLeitoDTO situacaoDeLeitoDTO1 = new SituacaoDeLeitoDTO();
        situacaoDeLeitoDTO1.setId(1L);
        SituacaoDeLeitoDTO situacaoDeLeitoDTO2 = new SituacaoDeLeitoDTO();
        assertThat(situacaoDeLeitoDTO1).isNotEqualTo(situacaoDeLeitoDTO2);
        situacaoDeLeitoDTO2.setId(situacaoDeLeitoDTO1.getId());
        assertThat(situacaoDeLeitoDTO1).isEqualTo(situacaoDeLeitoDTO2);
        situacaoDeLeitoDTO2.setId(2L);
        assertThat(situacaoDeLeitoDTO1).isNotEqualTo(situacaoDeLeitoDTO2);
        situacaoDeLeitoDTO1.setId(null);
        assertThat(situacaoDeLeitoDTO1).isNotEqualTo(situacaoDeLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(situacaoDeLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(situacaoDeLeitoMapper.fromId(null)).isNull();
    }
}
