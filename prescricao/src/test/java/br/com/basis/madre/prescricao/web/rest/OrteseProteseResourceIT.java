package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.OrteseProtese;
import br.com.basis.madre.prescricao.repository.OrteseProteseRepository;
import br.com.basis.madre.prescricao.repository.search.OrteseProteseSearchRepository;
import br.com.basis.madre.prescricao.service.OrteseProteseService;
import br.com.basis.madre.prescricao.service.dto.OrteseProteseDTO;
import br.com.basis.madre.prescricao.service.mapper.OrteseProteseMapper;
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
 * Integration tests for the {@link OrteseProteseResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class OrteseProteseResourceIT {

    private static final String DEFAULT_DECRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DECRICAO = "BBBBBBBBBB";

    @Autowired
    private OrteseProteseRepository orteseProteseRepository;

    @Autowired
    private OrteseProteseMapper orteseProteseMapper;

    @Autowired
    private OrteseProteseService orteseProteseService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.OrteseProteseSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrteseProteseSearchRepository mockOrteseProteseSearchRepository;

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

    private MockMvc restOrteseProteseMockMvc;

    private OrteseProtese orteseProtese;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrteseProteseResource orteseProteseResource = new OrteseProteseResource(orteseProteseService);
        this.restOrteseProteseMockMvc = MockMvcBuilders.standaloneSetup(orteseProteseResource)
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
    public static OrteseProtese createEntity(EntityManager em) {
        OrteseProtese orteseProtese = new OrteseProtese()
            .decricao(DEFAULT_DECRICAO);
        return orteseProtese;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrteseProtese createUpdatedEntity(EntityManager em) {
        OrteseProtese orteseProtese = new OrteseProtese()
            .decricao(UPDATED_DECRICAO);
        return orteseProtese;
    }

    @BeforeEach
    public void initTest() {
        orteseProtese = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrteseProtese() throws Exception {
        int databaseSizeBeforeCreate = orteseProteseRepository.findAll().size();

        // Create the OrteseProtese
        OrteseProteseDTO orteseProteseDTO = orteseProteseMapper.toDto(orteseProtese);
        restOrteseProteseMockMvc.perform(post("/api/ortese-protese")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orteseProteseDTO)))
            .andExpect(status().isCreated());

        // Validate the OrteseProtese in the database
        List<OrteseProtese> orteseProteseList = orteseProteseRepository.findAll();
        assertThat(orteseProteseList).hasSize(databaseSizeBeforeCreate + 1);
        OrteseProtese testOrteseProtese = orteseProteseList.get(orteseProteseList.size() - 1);
        assertThat(testOrteseProtese.getDecricao()).isEqualTo(DEFAULT_DECRICAO);

        // Validate the OrteseProtese in Elasticsearch
        verify(mockOrteseProteseSearchRepository, times(1)).save(testOrteseProtese);
    }

    @Test
    @Transactional
    public void createOrteseProteseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orteseProteseRepository.findAll().size();

        // Create the OrteseProtese with an existing ID
        orteseProtese.setId(1L);
        OrteseProteseDTO orteseProteseDTO = orteseProteseMapper.toDto(orteseProtese);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrteseProteseMockMvc.perform(post("/api/ortese-protese")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orteseProteseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrteseProtese in the database
        List<OrteseProtese> orteseProteseList = orteseProteseRepository.findAll();
        assertThat(orteseProteseList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrteseProtese in Elasticsearch
        verify(mockOrteseProteseSearchRepository, times(0)).save(orteseProtese);
    }


    @Test
    @Transactional
    public void checkDecricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = orteseProteseRepository.findAll().size();
        // set the field null
        orteseProtese.setDecricao(null);

        // Create the OrteseProtese, which fails.
        OrteseProteseDTO orteseProteseDTO = orteseProteseMapper.toDto(orteseProtese);

        restOrteseProteseMockMvc.perform(post("/api/ortese-protese")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orteseProteseDTO)))
            .andExpect(status().isBadRequest());

        List<OrteseProtese> orteseProteseList = orteseProteseRepository.findAll();
        assertThat(orteseProteseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrteseProtese() throws Exception {
        // Initialize the database
        orteseProteseRepository.saveAndFlush(orteseProtese);

        // Get all the orteseProteseList
        restOrteseProteseMockMvc.perform(get("/api/ortese-protese?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orteseProtese.getId().intValue())))
            .andExpect(jsonPath("$.[*].decricao").value(hasItem(DEFAULT_DECRICAO)));
    }
    
    @Test
    @Transactional
    public void getOrteseProtese() throws Exception {
        // Initialize the database
        orteseProteseRepository.saveAndFlush(orteseProtese);

        // Get the orteseProtese
        restOrteseProteseMockMvc.perform(get("/api/ortese-protese/{id}", orteseProtese.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orteseProtese.getId().intValue()))
            .andExpect(jsonPath("$.decricao").value(DEFAULT_DECRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingOrteseProtese() throws Exception {
        // Get the orteseProtese
        restOrteseProteseMockMvc.perform(get("/api/ortese-protese/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrteseProtese() throws Exception {
        // Initialize the database
        orteseProteseRepository.saveAndFlush(orteseProtese);

        int databaseSizeBeforeUpdate = orteseProteseRepository.findAll().size();

        // Update the orteseProtese
        OrteseProtese updatedOrteseProtese = orteseProteseRepository.findById(orteseProtese.getId()).get();
        // Disconnect from session so that the updates on updatedOrteseProtese are not directly saved in db
        em.detach(updatedOrteseProtese);
        updatedOrteseProtese
            .decricao(UPDATED_DECRICAO);
        OrteseProteseDTO orteseProteseDTO = orteseProteseMapper.toDto(updatedOrteseProtese);

        restOrteseProteseMockMvc.perform(put("/api/ortese-protese")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orteseProteseDTO)))
            .andExpect(status().isOk());

        // Validate the OrteseProtese in the database
        List<OrteseProtese> orteseProteseList = orteseProteseRepository.findAll();
        assertThat(orteseProteseList).hasSize(databaseSizeBeforeUpdate);
        OrteseProtese testOrteseProtese = orteseProteseList.get(orteseProteseList.size() - 1);
        assertThat(testOrteseProtese.getDecricao()).isEqualTo(UPDATED_DECRICAO);

        // Validate the OrteseProtese in Elasticsearch
        verify(mockOrteseProteseSearchRepository, times(1)).save(testOrteseProtese);
    }

    @Test
    @Transactional
    public void updateNonExistingOrteseProtese() throws Exception {
        int databaseSizeBeforeUpdate = orteseProteseRepository.findAll().size();

        // Create the OrteseProtese
        OrteseProteseDTO orteseProteseDTO = orteseProteseMapper.toDto(orteseProtese);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrteseProteseMockMvc.perform(put("/api/ortese-protese")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orteseProteseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrteseProtese in the database
        List<OrteseProtese> orteseProteseList = orteseProteseRepository.findAll();
        assertThat(orteseProteseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrteseProtese in Elasticsearch
        verify(mockOrteseProteseSearchRepository, times(0)).save(orteseProtese);
    }

    @Test
    @Transactional
    public void deleteOrteseProtese() throws Exception {
        // Initialize the database
        orteseProteseRepository.saveAndFlush(orteseProtese);

        int databaseSizeBeforeDelete = orteseProteseRepository.findAll().size();

        // Delete the orteseProtese
        restOrteseProteseMockMvc.perform(delete("/api/ortese-protese/{id}", orteseProtese.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrteseProtese> orteseProteseList = orteseProteseRepository.findAll();
        assertThat(orteseProteseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrteseProtese in Elasticsearch
        verify(mockOrteseProteseSearchRepository, times(1)).deleteById(orteseProtese.getId());
    }

    @Test
    @Transactional
    public void searchOrteseProtese() throws Exception {
        // Initialize the database
        orteseProteseRepository.saveAndFlush(orteseProtese);
        when(mockOrteseProteseSearchRepository.search(queryStringQuery("id:" + orteseProtese.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(orteseProtese), PageRequest.of(0, 1), 1));
        // Search the orteseProtese
        restOrteseProteseMockMvc.perform(get("/api/_search/ortese-protese?query=id:" + orteseProtese.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orteseProtese.getId().intValue())))
            .andExpect(jsonPath("$.[*].decricao").value(hasItem(DEFAULT_DECRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrteseProtese.class);
        OrteseProtese orteseProtese1 = new OrteseProtese();
        orteseProtese1.setId(1L);
        OrteseProtese orteseProtese2 = new OrteseProtese();
        orteseProtese2.setId(orteseProtese1.getId());
        assertThat(orteseProtese1).isEqualTo(orteseProtese2);
        orteseProtese2.setId(2L);
        assertThat(orteseProtese1).isNotEqualTo(orteseProtese2);
        orteseProtese1.setId(null);
        assertThat(orteseProtese1).isNotEqualTo(orteseProtese2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrteseProteseDTO.class);
        OrteseProteseDTO orteseProteseDTO1 = new OrteseProteseDTO();
        orteseProteseDTO1.setId(1L);
        OrteseProteseDTO orteseProteseDTO2 = new OrteseProteseDTO();
        assertThat(orteseProteseDTO1).isNotEqualTo(orteseProteseDTO2);
        orteseProteseDTO2.setId(orteseProteseDTO1.getId());
        assertThat(orteseProteseDTO1).isEqualTo(orteseProteseDTO2);
        orteseProteseDTO2.setId(2L);
        assertThat(orteseProteseDTO1).isNotEqualTo(orteseProteseDTO2);
        orteseProteseDTO1.setId(null);
        assertThat(orteseProteseDTO1).isNotEqualTo(orteseProteseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orteseProteseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orteseProteseMapper.fromId(null)).isNull();
    }
}
