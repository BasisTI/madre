package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Fornecedor;
import br.com.basis.suprimentos.repository.FornecedorRepository;
import br.com.basis.suprimentos.repository.search.FornecedorSearchRepository;
import br.com.basis.suprimentos.service.FornecedorService;
import br.com.basis.suprimentos.service.dto.FornecedorDTO;
import br.com.basis.suprimentos.service.mapper.FornecedorMapper;
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
 * Integration tests for the {@link FornecedorResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class FornecedorResourceIT {

    private static final String DEFAULT_CNPJ = "AAAAAAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBBBBBB";

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FANTASIA = "BBBBBBBBBB";

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Autowired
    private FornecedorService fornecedorService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.FornecedorSearchRepositoryMockConfiguration
     */
    @Autowired
    private FornecedorSearchRepository mockFornecedorSearchRepository;

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

    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCpfCnpj(DEFAULT_CNPJ);
        fornecedor.setRazaoSocial(DEFAULT_RAZAO_SOCIAL);
        fornecedor.setNomeFantasia(DEFAULT_NOME_FANTASIA);
        return fornecedor;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createUpdatedEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCpfCnpj(UPDATED_CNPJ);
        fornecedor.setRazaoSocial(UPDATED_RAZAO_SOCIAL);
        fornecedor.setNomeFantasia(UPDATED_NOME_FANTASIA);
        return fornecedor;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FornecedorResource fornecedorResource = new FornecedorResource(fornecedorService);
        this.restFornecedorMockMvc = MockMvcBuilders.standaloneSetup(fornecedorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFornecedor() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getCpfCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testFornecedor.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testFornecedor.getNomeFantasia()).isEqualTo(DEFAULT_NOME_FANTASIA);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(1)).save(testFornecedor);
    }

    @Test
    @Transactional
    public void createFornecedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(0)).save(fornecedor);
    }


    @Test
    @Transactional
    public void checkCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setCpfCnpj(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRazaoSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setRazaoSocial(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeFantasiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setNomeFantasia(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFornecedors() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA)));
    }

    @Test
    @Transactional
    public void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.nomeFantasia").value(DEFAULT_NOME_FANTASIA));
    }

    @Test
    @Transactional
    public void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor.setCpfCnpj(UPDATED_CNPJ);
        updatedFornecedor.setRazaoSocial(UPDATED_RAZAO_SOCIAL);
        updatedFornecedor.setNomeFantasia(UPDATED_NOME_FANTASIA);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(updatedFornecedor);

        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getCpfCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testFornecedor.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testFornecedor.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(1)).save(testFornecedor);
    }

    @Test
    @Transactional
    public void updateNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(0)).save(fornecedor);
    }

    @Test
    @Transactional
    public void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();

        // Delete the fornecedor
        restFornecedorMockMvc.perform(delete("/api/fornecedors/{id}", fornecedor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Fornecedor in Elasticsearch
        verify(mockFornecedorSearchRepository, times(1)).deleteById(fornecedor.getId());
    }

    @Test
    @Transactional
    public void searchFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);
        when(mockFornecedorSearchRepository.search(queryStringQuery("id:" + fornecedor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fornecedor), PageRequest.of(0, 1), 1));
        // Search the fornecedor
        restFornecedorMockMvc.perform(get("/api/_search/fornecedors?query=id:" + fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fornecedor.class);
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setId(1L);
        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setId(fornecedor1.getId());
        assertThat(fornecedor1).isEqualTo(fornecedor2);
        fornecedor2.setId(2L);
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
        fornecedor1.setId(null);
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorDTO.class);
        FornecedorDTO fornecedorDTO1 = new FornecedorDTO();
        fornecedorDTO1.setId(1L);
        FornecedorDTO fornecedorDTO2 = new FornecedorDTO();
        assertThat(fornecedorDTO1).isNotEqualTo(fornecedorDTO2);
        fornecedorDTO2.setId(fornecedorDTO1.getId());
        assertThat(fornecedorDTO1).isEqualTo(fornecedorDTO2);
        fornecedorDTO2.setId(2L);
        assertThat(fornecedorDTO1).isNotEqualTo(fornecedorDTO2);
        fornecedorDTO1.setId(null);
        assertThat(fornecedorDTO1).isNotEqualTo(fornecedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fornecedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fornecedorMapper.fromId(null)).isNull();
    }
}
