package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.CEP;
import br.com.basis.madre.repository.CEPRepository;
import br.com.basis.madre.repository.search.CEPSearchRepository;
import br.com.basis.madre.service.CEPService;
import br.com.basis.madre.service.dto.CEPDTO;
import br.com.basis.madre.service.mapper.CEPMapper;
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
 * Integration tests for the {@link CEPResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
public class CEPResourceIT {

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    @Autowired
    private CEPRepository cEPRepository;

    @Autowired
    private CEPMapper cEPMapper;

    @Autowired
    private CEPService cEPService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.CEPSearchRepositoryMockConfiguration
     */
    @Autowired
    private CEPSearchRepository mockCEPSearchRepository;

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

    private MockMvc restCEPMockMvc;

    private CEP cEP;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CEPResource cEPResource = new CEPResource(cEPService);
        this.restCEPMockMvc = MockMvcBuilders.standaloneSetup(cEPResource)
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
    public static CEP createEntity(EntityManager em) {
        CEP cEP = new CEP()
            .cep(DEFAULT_CEP)
            .logradouro(DEFAULT_LOGRADOURO)
            .bairro(DEFAULT_BAIRRO);
        return cEP;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CEP createUpdatedEntity(EntityManager em) {
        CEP cEP = new CEP()
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .bairro(UPDATED_BAIRRO);
        return cEP;
    }

    @BeforeEach
    public void initTest() {
        cEP = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEP() throws Exception {
        int databaseSizeBeforeCreate = cEPRepository.findAll().size();

        // Create the CEP
        CEPDTO cEPDTO = cEPMapper.toDto(cEP);
        restCEPMockMvc.perform(post("/api/ceps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cEPDTO)))
            .andExpect(status().isCreated());

        // Validate the CEP in the database
        List<CEP> cEPList = cEPRepository.findAll();
        assertThat(cEPList).hasSize(databaseSizeBeforeCreate + 1);
        CEP testCEP = cEPList.get(cEPList.size() - 1);
        assertThat(testCEP.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testCEP.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testCEP.getBairro()).isEqualTo(DEFAULT_BAIRRO);

        // Validate the CEP in Elasticsearch
        verify(mockCEPSearchRepository, times(1)).save(testCEP);
    }

    @Test
    @Transactional
    public void createCEPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEPRepository.findAll().size();

        // Create the CEP with an existing ID
        cEP.setId(1L);
        CEPDTO cEPDTO = cEPMapper.toDto(cEP);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEPMockMvc.perform(post("/api/ceps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cEPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEP in the database
        List<CEP> cEPList = cEPRepository.findAll();
        assertThat(cEPList).hasSize(databaseSizeBeforeCreate);

        // Validate the CEP in Elasticsearch
        verify(mockCEPSearchRepository, times(0)).save(cEP);
    }


    @Test
    @Transactional
    public void getAllCEPS() throws Exception {
        // Initialize the database
        cEPRepository.saveAndFlush(cEP);

        // Get all the cEPList
        restCEPMockMvc.perform(get("/api/ceps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEP.getId().intValue())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)));
    }
    
    @Test
    @Transactional
    public void getCEP() throws Exception {
        // Initialize the database
        cEPRepository.saveAndFlush(cEP);

        // Get the cEP
        restCEPMockMvc.perform(get("/api/ceps/{id}", cEP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cEP.getId().intValue()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO));
    }

    @Test
    @Transactional
    public void getNonExistingCEP() throws Exception {
        // Get the cEP
        restCEPMockMvc.perform(get("/api/ceps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEP() throws Exception {
        // Initialize the database
        cEPRepository.saveAndFlush(cEP);

        int databaseSizeBeforeUpdate = cEPRepository.findAll().size();

        // Update the cEP
        CEP updatedCEP = cEPRepository.findById(cEP.getId()).get();
        // Disconnect from session so that the updates on updatedCEP are not directly saved in db
        em.detach(updatedCEP);
        updatedCEP
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .bairro(UPDATED_BAIRRO);
        CEPDTO cEPDTO = cEPMapper.toDto(updatedCEP);

        restCEPMockMvc.perform(put("/api/ceps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cEPDTO)))
            .andExpect(status().isOk());

        // Validate the CEP in the database
        List<CEP> cEPList = cEPRepository.findAll();
        assertThat(cEPList).hasSize(databaseSizeBeforeUpdate);
        CEP testCEP = cEPList.get(cEPList.size() - 1);
        assertThat(testCEP.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCEP.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCEP.getBairro()).isEqualTo(UPDATED_BAIRRO);

        // Validate the CEP in Elasticsearch
        verify(mockCEPSearchRepository, times(1)).save(testCEP);
    }

    @Test
    @Transactional
    public void updateNonExistingCEP() throws Exception {
        int databaseSizeBeforeUpdate = cEPRepository.findAll().size();

        // Create the CEP
        CEPDTO cEPDTO = cEPMapper.toDto(cEP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEPMockMvc.perform(put("/api/ceps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cEPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CEP in the database
        List<CEP> cEPList = cEPRepository.findAll();
        assertThat(cEPList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CEP in Elasticsearch
        verify(mockCEPSearchRepository, times(0)).save(cEP);
    }

    @Test
    @Transactional
    public void deleteCEP() throws Exception {
        // Initialize the database
        cEPRepository.saveAndFlush(cEP);

        int databaseSizeBeforeDelete = cEPRepository.findAll().size();

        // Delete the cEP
        restCEPMockMvc.perform(delete("/api/ceps/{id}", cEP.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CEP> cEPList = cEPRepository.findAll();
        assertThat(cEPList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CEP in Elasticsearch
        verify(mockCEPSearchRepository, times(1)).deleteById(cEP.getId());
    }

    @Test
    @Transactional
    public void searchCEP() throws Exception {
        // Initialize the database
        cEPRepository.saveAndFlush(cEP);
        when(mockCEPSearchRepository.search(queryStringQuery("id:" + cEP.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cEP), PageRequest.of(0, 1), 1));
        // Search the cEP
        restCEPMockMvc.perform(get("/api/_search/ceps?query=id:" + cEP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEP.getId().intValue())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEP.class);
        CEP cEP1 = new CEP();
        cEP1.setId(1L);
        CEP cEP2 = new CEP();
        cEP2.setId(cEP1.getId());
        assertThat(cEP1).isEqualTo(cEP2);
        cEP2.setId(2L);
        assertThat(cEP1).isNotEqualTo(cEP2);
        cEP1.setId(null);
        assertThat(cEP1).isNotEqualTo(cEP2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEPDTO.class);
        CEPDTO cEPDTO1 = new CEPDTO();
        cEPDTO1.setId(1L);
        CEPDTO cEPDTO2 = new CEPDTO();
        assertThat(cEPDTO1).isNotEqualTo(cEPDTO2);
        cEPDTO2.setId(cEPDTO1.getId());
        assertThat(cEPDTO1).isEqualTo(cEPDTO2);
        cEPDTO2.setId(2L);
        assertThat(cEPDTO1).isNotEqualTo(cEPDTO2);
        cEPDTO1.setId(null);
        assertThat(cEPDTO1).isNotEqualTo(cEPDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cEPMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cEPMapper.fromId(null)).isNull();
    }
}
