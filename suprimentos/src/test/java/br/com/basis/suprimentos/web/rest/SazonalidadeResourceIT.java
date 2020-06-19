package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Sazonalidade;
import br.com.basis.suprimentos.repository.SazonalidadeRepository;
import br.com.basis.suprimentos.repository.search.SazonalidadeSearchRepository;
import br.com.basis.suprimentos.service.SazonalidadeService;
import br.com.basis.suprimentos.service.dto.SazonalidadeDTO;
import br.com.basis.suprimentos.service.mapper.SazonalidadeMapper;
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

import static br.com.basis.suprimentos.web.rest.TestUtil.createFormattingConversionService;
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
 * Integration tests for the {@link SazonalidadeResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class SazonalidadeResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private SazonalidadeRepository sazonalidadeRepository;

    @Autowired
    private SazonalidadeMapper sazonalidadeMapper;

    @Autowired
    private SazonalidadeService sazonalidadeService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.SazonalidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private SazonalidadeSearchRepository mockSazonalidadeSearchRepository;

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

    private MockMvc restSazonalidadeMockMvc;

    private Sazonalidade sazonalidade;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sazonalidade createEntity(EntityManager em) {
        Sazonalidade sazonalidade = new Sazonalidade()
                .descricao(DEFAULT_DESCRICAO);
        return sazonalidade;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sazonalidade createUpdatedEntity(EntityManager em) {
        Sazonalidade sazonalidade = new Sazonalidade()
                .descricao(UPDATED_DESCRICAO);
        return sazonalidade;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SazonalidadeResource sazonalidadeResource = new SazonalidadeResource(sazonalidadeService);
        this.restSazonalidadeMockMvc = MockMvcBuilders.standaloneSetup(sazonalidadeResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        sazonalidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createSazonalidade() throws Exception {
        int databaseSizeBeforeCreate = sazonalidadeRepository.findAll().size();

        // Create the Sazonalidade
        SazonalidadeDTO sazonalidadeDTO = sazonalidadeMapper.toDto(sazonalidade);
        restSazonalidadeMockMvc.perform(post("/api/sazonalidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(sazonalidadeDTO)))
                .andExpect(status().isCreated());

        // Validate the Sazonalidade in the database
        List<Sazonalidade> sazonalidadeList = sazonalidadeRepository.findAll();
        assertThat(sazonalidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Sazonalidade testSazonalidade = sazonalidadeList.get(sazonalidadeList.size() - 1);
        assertThat(testSazonalidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Sazonalidade in Elasticsearch
        verify(mockSazonalidadeSearchRepository, times(1)).save(testSazonalidade);
    }

    @Test
    @Transactional
    public void createSazonalidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sazonalidadeRepository.findAll().size();

        // Create the Sazonalidade with an existing ID
        sazonalidade.setId(1L);
        SazonalidadeDTO sazonalidadeDTO = sazonalidadeMapper.toDto(sazonalidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSazonalidadeMockMvc.perform(post("/api/sazonalidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(sazonalidadeDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Sazonalidade in the database
        List<Sazonalidade> sazonalidadeList = sazonalidadeRepository.findAll();
        assertThat(sazonalidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sazonalidade in Elasticsearch
        verify(mockSazonalidadeSearchRepository, times(0)).save(sazonalidade);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = sazonalidadeRepository.findAll().size();
        // set the field null
        sazonalidade.setDescricao(null);

        // Create the Sazonalidade, which fails.
        SazonalidadeDTO sazonalidadeDTO = sazonalidadeMapper.toDto(sazonalidade);

        restSazonalidadeMockMvc.perform(post("/api/sazonalidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(sazonalidadeDTO)))
                .andExpect(status().isBadRequest());

        List<Sazonalidade> sazonalidadeList = sazonalidadeRepository.findAll();
        assertThat(sazonalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSazonalidades() throws Exception {
        // Initialize the database
        sazonalidadeRepository.saveAndFlush(sazonalidade);

        // Get all the sazonalidadeList
        restSazonalidadeMockMvc.perform(get("/api/sazonalidades?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sazonalidade.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getSazonalidade() throws Exception {
        // Initialize the database
        sazonalidadeRepository.saveAndFlush(sazonalidade);

        // Get the sazonalidade
        restSazonalidadeMockMvc.perform(get("/api/sazonalidades/{id}", sazonalidade.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(sazonalidade.getId().intValue()))
                .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingSazonalidade() throws Exception {
        // Get the sazonalidade
        restSazonalidadeMockMvc.perform(get("/api/sazonalidades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSazonalidade() throws Exception {
        // Initialize the database
        sazonalidadeRepository.saveAndFlush(sazonalidade);

        int databaseSizeBeforeUpdate = sazonalidadeRepository.findAll().size();

        // Update the sazonalidade
        Sazonalidade updatedSazonalidade = sazonalidadeRepository.findById(sazonalidade.getId()).get();
        // Disconnect from session so that the updates on updatedSazonalidade are not directly saved in db
        em.detach(updatedSazonalidade);
        updatedSazonalidade
                .descricao(UPDATED_DESCRICAO);
        SazonalidadeDTO sazonalidadeDTO = sazonalidadeMapper.toDto(updatedSazonalidade);

        restSazonalidadeMockMvc.perform(put("/api/sazonalidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(sazonalidadeDTO)))
                .andExpect(status().isOk());

        // Validate the Sazonalidade in the database
        List<Sazonalidade> sazonalidadeList = sazonalidadeRepository.findAll();
        assertThat(sazonalidadeList).hasSize(databaseSizeBeforeUpdate);
        Sazonalidade testSazonalidade = sazonalidadeList.get(sazonalidadeList.size() - 1);
        assertThat(testSazonalidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Sazonalidade in Elasticsearch
        verify(mockSazonalidadeSearchRepository, times(1)).save(testSazonalidade);
    }

    @Test
    @Transactional
    public void updateNonExistingSazonalidade() throws Exception {
        int databaseSizeBeforeUpdate = sazonalidadeRepository.findAll().size();

        // Create the Sazonalidade
        SazonalidadeDTO sazonalidadeDTO = sazonalidadeMapper.toDto(sazonalidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSazonalidadeMockMvc.perform(put("/api/sazonalidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(sazonalidadeDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Sazonalidade in the database
        List<Sazonalidade> sazonalidadeList = sazonalidadeRepository.findAll();
        assertThat(sazonalidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sazonalidade in Elasticsearch
        verify(mockSazonalidadeSearchRepository, times(0)).save(sazonalidade);
    }

    @Test
    @Transactional
    public void deleteSazonalidade() throws Exception {
        // Initialize the database
        sazonalidadeRepository.saveAndFlush(sazonalidade);

        int databaseSizeBeforeDelete = sazonalidadeRepository.findAll().size();

        // Delete the sazonalidade
        restSazonalidadeMockMvc.perform(delete("/api/sazonalidades/{id}", sazonalidade.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sazonalidade> sazonalidadeList = sazonalidadeRepository.findAll();
        assertThat(sazonalidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sazonalidade in Elasticsearch
        verify(mockSazonalidadeSearchRepository, times(1)).deleteById(sazonalidade.getId());
    }

    @Test
    @Transactional
    public void searchSazonalidade() throws Exception {
        // Initialize the database
        sazonalidadeRepository.saveAndFlush(sazonalidade);
        when(mockSazonalidadeSearchRepository.search(queryStringQuery("id:" + sazonalidade.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(sazonalidade), PageRequest.of(0, 1), 1));
        // Search the sazonalidade
        restSazonalidadeMockMvc.perform(get("/api/_search/sazonalidades?query=id:" + sazonalidade.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sazonalidade.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sazonalidade.class);
        Sazonalidade sazonalidade1 = new Sazonalidade();
        sazonalidade1.setId(1L);
        Sazonalidade sazonalidade2 = new Sazonalidade();
        sazonalidade2.setId(sazonalidade1.getId());
        assertThat(sazonalidade1).isEqualTo(sazonalidade2);
        sazonalidade2.setId(2L);
        assertThat(sazonalidade1).isNotEqualTo(sazonalidade2);
        sazonalidade1.setId(null);
        assertThat(sazonalidade1).isNotEqualTo(sazonalidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SazonalidadeDTO.class);
        SazonalidadeDTO sazonalidadeDTO1 = new SazonalidadeDTO();
        sazonalidadeDTO1.setId(1L);
        SazonalidadeDTO sazonalidadeDTO2 = new SazonalidadeDTO();
        assertThat(sazonalidadeDTO1).isNotEqualTo(sazonalidadeDTO2);
        sazonalidadeDTO2.setId(sazonalidadeDTO1.getId());
        assertThat(sazonalidadeDTO1).isEqualTo(sazonalidadeDTO2);
        sazonalidadeDTO2.setId(2L);
        assertThat(sazonalidadeDTO1).isNotEqualTo(sazonalidadeDTO2);
        sazonalidadeDTO1.setId(null);
        assertThat(sazonalidadeDTO1).isNotEqualTo(sazonalidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sazonalidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sazonalidadeMapper.fromId(null)).isNull();
    }
}
