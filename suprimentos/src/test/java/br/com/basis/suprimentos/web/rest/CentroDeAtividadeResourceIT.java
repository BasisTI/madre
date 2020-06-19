package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.CentroDeAtividade;
import br.com.basis.suprimentos.repository.CentroDeAtividadeRepository;
import br.com.basis.suprimentos.repository.search.CentroDeAtividadeSearchRepository;
import br.com.basis.suprimentos.service.CentroDeAtividadeService;
import br.com.basis.suprimentos.service.dto.CentroDeAtividadeDTO;
import br.com.basis.suprimentos.service.mapper.CentroDeAtividadeMapper;
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
 * Integration tests for the {@link CentroDeAtividadeResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class CentroDeAtividadeResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CentroDeAtividadeRepository centroDeAtividadeRepository;

    @Autowired
    private CentroDeAtividadeMapper centroDeAtividadeMapper;

    @Autowired
    private CentroDeAtividadeService centroDeAtividadeService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.CentroDeAtividadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private CentroDeAtividadeSearchRepository mockCentroDeAtividadeSearchRepository;

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

    private MockMvc restCentroDeAtividadeMockMvc;

    private CentroDeAtividade centroDeAtividade;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroDeAtividade createEntity(EntityManager em) {
        CentroDeAtividade centroDeAtividade = new CentroDeAtividade()
                .descricao(DEFAULT_DESCRICAO);
        return centroDeAtividade;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroDeAtividade createUpdatedEntity(EntityManager em) {
        CentroDeAtividade centroDeAtividade = new CentroDeAtividade()
                .descricao(UPDATED_DESCRICAO);
        return centroDeAtividade;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CentroDeAtividadeResource centroDeAtividadeResource = new CentroDeAtividadeResource(centroDeAtividadeService);
        this.restCentroDeAtividadeMockMvc = MockMvcBuilders.standaloneSetup(centroDeAtividadeResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        centroDeAtividade = createEntity(em);
    }

    @Test
    @Transactional
    public void createCentroDeAtividade() throws Exception {
        int databaseSizeBeforeCreate = centroDeAtividadeRepository.findAll().size();

        // Create the CentroDeAtividade
        CentroDeAtividadeDTO centroDeAtividadeDTO = centroDeAtividadeMapper.toDto(centroDeAtividade);
        restCentroDeAtividadeMockMvc.perform(post("/api/centro-de-atividades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(centroDeAtividadeDTO)))
                .andExpect(status().isCreated());

        // Validate the CentroDeAtividade in the database
        List<CentroDeAtividade> centroDeAtividadeList = centroDeAtividadeRepository.findAll();
        assertThat(centroDeAtividadeList).hasSize(databaseSizeBeforeCreate + 1);
        CentroDeAtividade testCentroDeAtividade = centroDeAtividadeList.get(centroDeAtividadeList.size() - 1);
        assertThat(testCentroDeAtividade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the CentroDeAtividade in Elasticsearch
        verify(mockCentroDeAtividadeSearchRepository, times(1)).save(testCentroDeAtividade);
    }

    @Test
    @Transactional
    public void createCentroDeAtividadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centroDeAtividadeRepository.findAll().size();

        // Create the CentroDeAtividade with an existing ID
        centroDeAtividade.setId(1L);
        CentroDeAtividadeDTO centroDeAtividadeDTO = centroDeAtividadeMapper.toDto(centroDeAtividade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentroDeAtividadeMockMvc.perform(post("/api/centro-de-atividades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(centroDeAtividadeDTO)))
                .andExpect(status().isBadRequest());

        // Validate the CentroDeAtividade in the database
        List<CentroDeAtividade> centroDeAtividadeList = centroDeAtividadeRepository.findAll();
        assertThat(centroDeAtividadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the CentroDeAtividade in Elasticsearch
        verify(mockCentroDeAtividadeSearchRepository, times(0)).save(centroDeAtividade);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = centroDeAtividadeRepository.findAll().size();
        // set the field null
        centroDeAtividade.setDescricao(null);

        // Create the CentroDeAtividade, which fails.
        CentroDeAtividadeDTO centroDeAtividadeDTO = centroDeAtividadeMapper.toDto(centroDeAtividade);

        restCentroDeAtividadeMockMvc.perform(post("/api/centro-de-atividades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(centroDeAtividadeDTO)))
                .andExpect(status().isBadRequest());

        List<CentroDeAtividade> centroDeAtividadeList = centroDeAtividadeRepository.findAll();
        assertThat(centroDeAtividadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCentroDeAtividades() throws Exception {
        // Initialize the database
        centroDeAtividadeRepository.saveAndFlush(centroDeAtividade);

        // Get all the centroDeAtividadeList
        restCentroDeAtividadeMockMvc.perform(get("/api/centro-de-atividades?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(centroDeAtividade.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getCentroDeAtividade() throws Exception {
        // Initialize the database
        centroDeAtividadeRepository.saveAndFlush(centroDeAtividade);

        // Get the centroDeAtividade
        restCentroDeAtividadeMockMvc.perform(get("/api/centro-de-atividades/{id}", centroDeAtividade.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(centroDeAtividade.getId().intValue()))
                .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingCentroDeAtividade() throws Exception {
        // Get the centroDeAtividade
        restCentroDeAtividadeMockMvc.perform(get("/api/centro-de-atividades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCentroDeAtividade() throws Exception {
        // Initialize the database
        centroDeAtividadeRepository.saveAndFlush(centroDeAtividade);

        int databaseSizeBeforeUpdate = centroDeAtividadeRepository.findAll().size();

        // Update the centroDeAtividade
        CentroDeAtividade updatedCentroDeAtividade = centroDeAtividadeRepository.findById(centroDeAtividade.getId()).get();
        // Disconnect from session so that the updates on updatedCentroDeAtividade are not directly saved in db
        em.detach(updatedCentroDeAtividade);
        updatedCentroDeAtividade
                .descricao(UPDATED_DESCRICAO);
        CentroDeAtividadeDTO centroDeAtividadeDTO = centroDeAtividadeMapper.toDto(updatedCentroDeAtividade);

        restCentroDeAtividadeMockMvc.perform(put("/api/centro-de-atividades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(centroDeAtividadeDTO)))
                .andExpect(status().isOk());

        // Validate the CentroDeAtividade in the database
        List<CentroDeAtividade> centroDeAtividadeList = centroDeAtividadeRepository.findAll();
        assertThat(centroDeAtividadeList).hasSize(databaseSizeBeforeUpdate);
        CentroDeAtividade testCentroDeAtividade = centroDeAtividadeList.get(centroDeAtividadeList.size() - 1);
        assertThat(testCentroDeAtividade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the CentroDeAtividade in Elasticsearch
        verify(mockCentroDeAtividadeSearchRepository, times(1)).save(testCentroDeAtividade);
    }

    @Test
    @Transactional
    public void updateNonExistingCentroDeAtividade() throws Exception {
        int databaseSizeBeforeUpdate = centroDeAtividadeRepository.findAll().size();

        // Create the CentroDeAtividade
        CentroDeAtividadeDTO centroDeAtividadeDTO = centroDeAtividadeMapper.toDto(centroDeAtividade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroDeAtividadeMockMvc.perform(put("/api/centro-de-atividades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(centroDeAtividadeDTO)))
                .andExpect(status().isBadRequest());

        // Validate the CentroDeAtividade in the database
        List<CentroDeAtividade> centroDeAtividadeList = centroDeAtividadeRepository.findAll();
        assertThat(centroDeAtividadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CentroDeAtividade in Elasticsearch
        verify(mockCentroDeAtividadeSearchRepository, times(0)).save(centroDeAtividade);
    }

    @Test
    @Transactional
    public void deleteCentroDeAtividade() throws Exception {
        // Initialize the database
        centroDeAtividadeRepository.saveAndFlush(centroDeAtividade);

        int databaseSizeBeforeDelete = centroDeAtividadeRepository.findAll().size();

        // Delete the centroDeAtividade
        restCentroDeAtividadeMockMvc.perform(delete("/api/centro-de-atividades/{id}", centroDeAtividade.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CentroDeAtividade> centroDeAtividadeList = centroDeAtividadeRepository.findAll();
        assertThat(centroDeAtividadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CentroDeAtividade in Elasticsearch
        verify(mockCentroDeAtividadeSearchRepository, times(1)).deleteById(centroDeAtividade.getId());
    }

    @Test
    @Transactional
    public void searchCentroDeAtividade() throws Exception {
        // Initialize the database
        centroDeAtividadeRepository.saveAndFlush(centroDeAtividade);
        when(mockCentroDeAtividadeSearchRepository.search(queryStringQuery("id:" + centroDeAtividade.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(centroDeAtividade), PageRequest.of(0, 1), 1));
        // Search the centroDeAtividade
        restCentroDeAtividadeMockMvc.perform(get("/api/_search/centro-de-atividades?query=id:" + centroDeAtividade.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(centroDeAtividade.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroDeAtividade.class);
        CentroDeAtividade centroDeAtividade1 = new CentroDeAtividade();
        centroDeAtividade1.setId(1L);
        CentroDeAtividade centroDeAtividade2 = new CentroDeAtividade();
        centroDeAtividade2.setId(centroDeAtividade1.getId());
        assertThat(centroDeAtividade1).isEqualTo(centroDeAtividade2);
        centroDeAtividade2.setId(2L);
        assertThat(centroDeAtividade1).isNotEqualTo(centroDeAtividade2);
        centroDeAtividade1.setId(null);
        assertThat(centroDeAtividade1).isNotEqualTo(centroDeAtividade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroDeAtividadeDTO.class);
        CentroDeAtividadeDTO centroDeAtividadeDTO1 = new CentroDeAtividadeDTO();
        centroDeAtividadeDTO1.setId(1L);
        CentroDeAtividadeDTO centroDeAtividadeDTO2 = new CentroDeAtividadeDTO();
        assertThat(centroDeAtividadeDTO1).isNotEqualTo(centroDeAtividadeDTO2);
        centroDeAtividadeDTO2.setId(centroDeAtividadeDTO1.getId());
        assertThat(centroDeAtividadeDTO1).isEqualTo(centroDeAtividadeDTO2);
        centroDeAtividadeDTO2.setId(2L);
        assertThat(centroDeAtividadeDTO1).isNotEqualTo(centroDeAtividadeDTO2);
        centroDeAtividadeDTO1.setId(null);
        assertThat(centroDeAtividadeDTO1).isNotEqualTo(centroDeAtividadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(centroDeAtividadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(centroDeAtividadeMapper.fromId(null)).isNull();
    }
}
