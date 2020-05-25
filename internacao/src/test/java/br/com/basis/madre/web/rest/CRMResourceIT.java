package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.CRM;
import br.com.basis.madre.repository.CRMRepository;
import br.com.basis.madre.repository.search.CRMSearchRepository;
import br.com.basis.madre.service.CRMService;
import br.com.basis.madre.service.dto.CrmDTO;
import br.com.basis.madre.service.mapper.CRMMapper;
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
 * Integration tests for the {@link CRMResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class CRMResourceIT {

    private static final Long DEFAULT_CODIGO = 1L;
    private static final Long UPDATED_CODIGO = 2L;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private CRMRepository cRMRepository;

    @Autowired
    private CRMMapper cRMMapper;

    @Autowired
    private CRMService cRMService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.CRMSearchRepositoryMockConfiguration
     */
    @Autowired
    private CRMSearchRepository mockCRMSearchRepository;

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

    private MockMvc restCRMMockMvc;

    private CRM cRM;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CRMResource cRMResource = new CRMResource(cRMService);
        this.restCRMMockMvc = MockMvcBuilders.standaloneSetup(cRMResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static CRM createEntity(EntityManager em) {
        CRM cRM = new CRM()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME);
        return cRM;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static CRM createUpdatedEntity(EntityManager em) {
        CRM cRM = new CRM()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME);
        return cRM;
    }

    @BeforeEach
    public void initTest() {
        cRM = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRM() throws Exception {
        int databaseSizeBeforeCreate = cRMRepository.findAll().size();

        // Create the CRM
        CrmDTO cRMDTO = cRMMapper.toDto(cRM);
        restCRMMockMvc.perform(post("/api/crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMDTO)))
            .andExpect(status().isCreated());

        // Validate the CRM in the database
        List<CRM> cRMList = cRMRepository.findAll();
        assertThat(cRMList).hasSize(databaseSizeBeforeCreate + 1);
        CRM testCRM = cRMList.get(cRMList.size() - 1);
        assertThat(testCRM.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testCRM.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the CRM in Elasticsearch
        verify(mockCRMSearchRepository, times(1)).save(testCRM);
    }

    @Test
    @Transactional
    public void createCRMWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMRepository.findAll().size();

        // Create the CRM with an existing ID
        cRM.setId(1L);
        CrmDTO cRMDTO = cRMMapper.toDto(cRM);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMMockMvc.perform(post("/api/crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRM in the database
        List<CRM> cRMList = cRMRepository.findAll();
        assertThat(cRMList).hasSize(databaseSizeBeforeCreate);

        // Validate the CRM in Elasticsearch
        verify(mockCRMSearchRepository, times(0)).save(cRM);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cRMRepository.findAll().size();
        // set the field null
        cRM.setCodigo(null);

        // Create the CRM, which fails.
        CrmDTO cRMDTO = cRMMapper.toDto(cRM);

        restCRMMockMvc.perform(post("/api/crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMDTO)))
            .andExpect(status().isBadRequest());

        List<CRM> cRMList = cRMRepository.findAll();
        assertThat(cRMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cRMRepository.findAll().size();
        // set the field null
        cRM.setNome(null);

        // Create the CRM, which fails.
        CrmDTO cRMDTO = cRMMapper.toDto(cRM);

        restCRMMockMvc.perform(post("/api/crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMDTO)))
            .andExpect(status().isBadRequest());

        List<CRM> cRMList = cRMRepository.findAll();
        assertThat(cRMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCRMS() throws Exception {
        // Initialize the database
        cRMRepository.saveAndFlush(cRM);

        // Get all the cRMList
        restCRMMockMvc.perform(get("/api/crms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRM.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getCRM() throws Exception {
        // Initialize the database
        cRMRepository.saveAndFlush(cRM);

        // Get the cRM
        restCRMMockMvc.perform(get("/api/crms/{id}", cRM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRM.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingCRM() throws Exception {
        // Get the cRM
        restCRMMockMvc.perform(get("/api/crms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRM() throws Exception {
        // Initialize the database
        cRMRepository.saveAndFlush(cRM);

        int databaseSizeBeforeUpdate = cRMRepository.findAll().size();

        // Update the cRM
        CRM updatedCRM = cRMRepository.findById(cRM.getId()).get();
        // Disconnect from session so that the updates on updatedCRM are not directly saved in db
        em.detach(updatedCRM);
        updatedCRM
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME);
        CrmDTO cRMDTO = cRMMapper.toDto(updatedCRM);

        restCRMMockMvc.perform(put("/api/crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMDTO)))
            .andExpect(status().isOk());

        // Validate the CRM in the database
        List<CRM> cRMList = cRMRepository.findAll();
        assertThat(cRMList).hasSize(databaseSizeBeforeUpdate);
        CRM testCRM = cRMList.get(cRMList.size() - 1);
        assertThat(testCRM.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testCRM.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the CRM in Elasticsearch
        verify(mockCRMSearchRepository, times(1)).save(testCRM);
    }

    @Test
    @Transactional
    public void updateNonExistingCRM() throws Exception {
        int databaseSizeBeforeUpdate = cRMRepository.findAll().size();

        // Create the CRM
        CrmDTO cRMDTO = cRMMapper.toDto(cRM);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCRMMockMvc.perform(put("/api/crms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRM in the database
        List<CRM> cRMList = cRMRepository.findAll();
        assertThat(cRMList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CRM in Elasticsearch
        verify(mockCRMSearchRepository, times(0)).save(cRM);
    }

    @Test
    @Transactional
    public void deleteCRM() throws Exception {
        // Initialize the database
        cRMRepository.saveAndFlush(cRM);

        int databaseSizeBeforeDelete = cRMRepository.findAll().size();

        // Delete the cRM
        restCRMMockMvc.perform(delete("/api/crms/{id}", cRM.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CRM> cRMList = cRMRepository.findAll();
        assertThat(cRMList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CRM in Elasticsearch
        verify(mockCRMSearchRepository, times(1)).deleteById(cRM.getId());
    }

    @Test
    @Transactional
    public void searchCRM() throws Exception {
        // Initialize the database
        cRMRepository.saveAndFlush(cRM);
        when(mockCRMSearchRepository
            .search(queryStringQuery("id:" + cRM.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cRM), PageRequest.of(0, 1), 1));
        // Search the cRM
        restCRMMockMvc.perform(get("/api/_search/crms?query=id:" + cRM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRM.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRM.class);
        CRM cRM1 = new CRM();
        cRM1.setId(1L);
        CRM cRM2 = new CRM();
        cRM2.setId(cRM1.getId());
        assertThat(cRM1).isEqualTo(cRM2);
        cRM2.setId(2L);
        assertThat(cRM1).isNotEqualTo(cRM2);
        cRM1.setId(null);
        assertThat(cRM1).isNotEqualTo(cRM2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmDTO.class);
        CrmDTO cRMDTO1 = new CrmDTO();
        cRMDTO1.setId(1L);
        CrmDTO cRMDTO2 = new CrmDTO();
        assertThat(cRMDTO1).isNotEqualTo(cRMDTO2);
        cRMDTO2.setId(cRMDTO1.getId());
        assertThat(cRMDTO1).isEqualTo(cRMDTO2);
        cRMDTO2.setId(2L);
        assertThat(cRMDTO1).isNotEqualTo(cRMDTO2);
        cRMDTO1.setId(null);
        assertThat(cRMDTO1).isNotEqualTo(cRMDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cRMMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cRMMapper.fromId(null)).isNull();
    }
}
