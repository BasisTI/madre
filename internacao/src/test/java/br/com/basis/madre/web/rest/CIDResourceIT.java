package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.CID;
import br.com.basis.madre.repository.CIDRepository;
import br.com.basis.madre.repository.search.CIDSearchRepository;
import br.com.basis.madre.service.CIDService;
import br.com.basis.madre.service.dto.CidDTO;
import br.com.basis.madre.service.mapper.CIDMapper;
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
 * Integration tests for the {@link CIDResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class CIDResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CIDRepository cIDRepository;

    @Autowired
    private CIDMapper cIDMapper;

    @Autowired
    private CIDService cIDService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.CIDSearchRepositoryMockConfiguration
     */
    @Autowired
    private CIDSearchRepository mockCIDSearchRepository;

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

    private MockMvc restCIDMockMvc;

    private CID cID;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CIDResource cIDResource = new CIDResource(cIDService);
        this.restCIDMockMvc = MockMvcBuilders.standaloneSetup(cIDResource)
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
    public static CID createEntity(EntityManager em) {
        CID cID = new CID()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO);
        return cID;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static CID createUpdatedEntity(EntityManager em) {
        CID cID = new CID()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO);
        return cID;
    }

    @BeforeEach
    public void initTest() {
        cID = createEntity(em);
    }

    @Test
    @Transactional
    public void createCID() throws Exception {
        int databaseSizeBeforeCreate = cIDRepository.findAll().size();

        // Create the CID
        CidDTO cIDDTO = cIDMapper.toDto(cID);
        restCIDMockMvc.perform(post("/api/cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cIDDTO)))
            .andExpect(status().isCreated());

        // Validate the CID in the database
        List<CID> cIDList = cIDRepository.findAll();
        assertThat(cIDList).hasSize(databaseSizeBeforeCreate + 1);
        CID testCID = cIDList.get(cIDList.size() - 1);
        assertThat(testCID.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testCID.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the CID in Elasticsearch
        verify(mockCIDSearchRepository, times(1)).save(testCID);
    }

    @Test
    @Transactional
    public void createCIDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cIDRepository.findAll().size();

        // Create the CID with an existing ID
        cID.setId(1L);
        CidDTO cIDDTO = cIDMapper.toDto(cID);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCIDMockMvc.perform(post("/api/cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cIDDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CID in the database
        List<CID> cIDList = cIDRepository.findAll();
        assertThat(cIDList).hasSize(databaseSizeBeforeCreate);

        // Validate the CID in Elasticsearch
        verify(mockCIDSearchRepository, times(0)).save(cID);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cIDRepository.findAll().size();
        // set the field null
        cID.setCodigo(null);

        // Create the CID, which fails.
        CidDTO cIDDTO = cIDMapper.toDto(cID);

        restCIDMockMvc.perform(post("/api/cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cIDDTO)))
            .andExpect(status().isBadRequest());

        List<CID> cIDList = cIDRepository.findAll();
        assertThat(cIDList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cIDRepository.findAll().size();
        // set the field null
        cID.setDescricao(null);

        // Create the CID, which fails.
        CidDTO cIDDTO = cIDMapper.toDto(cID);

        restCIDMockMvc.perform(post("/api/cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cIDDTO)))
            .andExpect(status().isBadRequest());

        List<CID> cIDList = cIDRepository.findAll();
        assertThat(cIDList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCIDS() throws Exception {
        // Initialize the database
        cIDRepository.saveAndFlush(cID);

        // Get all the cIDList
        restCIDMockMvc.perform(get("/api/cids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cID.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getCID() throws Exception {
        // Initialize the database
        cIDRepository.saveAndFlush(cID);

        // Get the cID
        restCIDMockMvc.perform(get("/api/cids/{id}", cID.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cID.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingCID() throws Exception {
        // Get the cID
        restCIDMockMvc.perform(get("/api/cids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCID() throws Exception {
        // Initialize the database
        cIDRepository.saveAndFlush(cID);

        int databaseSizeBeforeUpdate = cIDRepository.findAll().size();

        // Update the cID
        CID updatedCID = cIDRepository.findById(cID.getId()).get();
        // Disconnect from session so that the updates on updatedCID are not directly saved in db
        em.detach(updatedCID);

        updatedCID
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO);
        CidDTO cIDDTO = cIDMapper.toDto(updatedCID);

        restCIDMockMvc.perform(put("/api/cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cIDDTO)))
            .andExpect(status().isOk());

        // Validate the CID in the database
        List<CID> cIDList = cIDRepository.findAll();
        assertThat(cIDList).hasSize(databaseSizeBeforeUpdate);
        CID testCID = cIDList.get(cIDList.size() - 1);
        assertThat(testCID.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testCID.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the CID in Elasticsearch
        verify(mockCIDSearchRepository, times(1)).save(testCID);
    }

    @Test
    @Transactional
    public void updateNonExistingCID() throws Exception {
        int databaseSizeBeforeUpdate = cIDRepository.findAll().size();

        // Create the CID
        CidDTO cIDDTO = cIDMapper.toDto(cID);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIDMockMvc.perform(put("/api/cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cIDDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CID in the database
        List<CID> cIDList = cIDRepository.findAll();
        assertThat(cIDList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CID in Elasticsearch
        verify(mockCIDSearchRepository, times(0)).save(cID);
    }

    @Test
    @Transactional
    public void deleteCID() throws Exception {
        // Initialize the database
        cIDRepository.saveAndFlush(cID);

        int databaseSizeBeforeDelete = cIDRepository.findAll().size();

        // Delete the cID
        restCIDMockMvc.perform(delete("/api/cids/{id}", cID.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CID> cIDList = cIDRepository.findAll();
        assertThat(cIDList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CID in Elasticsearch
        verify(mockCIDSearchRepository, times(1)).deleteById(cID.getId());
    }

    @Test
    @Transactional
    public void searchCID() throws Exception {
        // Initialize the database
        cIDRepository.saveAndFlush(cID);
        when(mockCIDSearchRepository
            .search(queryStringQuery("id:" + cID.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cID), PageRequest.of(0, 1), 1));
        // Search the cID
        restCIDMockMvc.perform(get("/api/_search/cids?query=id:" + cID.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cID.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CID.class);
        CID cID1 = new CID();
        cID1.setId(1L);
        CID cID2 = new CID();
        cID2.setId(cID1.getId());
        assertThat(cID1).isEqualTo(cID2);
        cID2.setId(2L);
        assertThat(cID1).isNotEqualTo(cID2);
        cID1.setId(null);
        assertThat(cID1).isNotEqualTo(cID2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CidDTO.class);
        CidDTO cIDDTO1 = new CidDTO();
        cIDDTO1.setId(1L);
        CidDTO cIDDTO2 = new CidDTO();
        assertThat(cIDDTO1).isNotEqualTo(cIDDTO2);
        cIDDTO2.setId(cIDDTO1.getId());
        assertThat(cIDDTO1).isEqualTo(cIDDTO2);
        cIDDTO2.setId(2L);
        assertThat(cIDDTO1).isNotEqualTo(cIDDTO2);
        cIDDTO1.setId(null);
        assertThat(cIDDTO1).isNotEqualTo(cIDDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cIDMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cIDMapper.fromId(null)).isNull();
    }
}
