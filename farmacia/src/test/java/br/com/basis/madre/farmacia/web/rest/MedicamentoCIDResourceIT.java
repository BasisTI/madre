package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.MedicamentoCID;
import br.com.basis.madre.farmacia.repository.MedicamentoCIDRepository;
import br.com.basis.madre.farmacia.repository.search.MedicamentoCIDSearchRepository;
import br.com.basis.madre.farmacia.service.MedicamentoCIDService;
import br.com.basis.madre.farmacia.service.dto.MedicamentoCIDDTO;
import br.com.basis.madre.farmacia.service.mapper.MedicamentoCIDMapper;
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

import static br.com.basis.madre.farmacia.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MedicamentoCIDResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class MedicamentoCIDResourceIT {

    private static final String DEFAULT_CODIGO_MEDICAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_MEDICAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_CID = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_CID = "BBBBBBBBBB";

    @Autowired
    private MedicamentoCIDRepository medicamentoCIDRepository;

    @Autowired
    private MedicamentoCIDMapper medicamentoCIDMapper;

    @Autowired
    private MedicamentoCIDService medicamentoCIDService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.MedicamentoCIDSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicamentoCIDSearchRepository mockMedicamentoCIDSearchRepository;

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

    private MockMvc restMedicamentoCIDMockMvc;

    private MedicamentoCID medicamentoCID;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicamentoCIDResource medicamentoCIDResource = new MedicamentoCIDResource(medicamentoCIDService);
        this.restMedicamentoCIDMockMvc = MockMvcBuilders.standaloneSetup(medicamentoCIDResource)
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
    public static MedicamentoCID createEntity(EntityManager em) {
        MedicamentoCID medicamentoCID = new MedicamentoCID()
            .codigoMedicamento(DEFAULT_CODIGO_MEDICAMENTO)
            .codigoCID(DEFAULT_CODIGO_CID);
        return medicamentoCID;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicamentoCID createUpdatedEntity(EntityManager em) {
        MedicamentoCID medicamentoCID = new MedicamentoCID()
            .codigoMedicamento(UPDATED_CODIGO_MEDICAMENTO)
            .codigoCID(UPDATED_CODIGO_CID);
        return medicamentoCID;
    }

    @BeforeEach
    public void initTest() {
        medicamentoCID = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicamentoCID() throws Exception {
        int databaseSizeBeforeCreate = medicamentoCIDRepository.findAll().size();

        // Create the MedicamentoCID
        MedicamentoCIDDTO medicamentoCIDDTO = medicamentoCIDMapper.toDto(medicamentoCID);
        restMedicamentoCIDMockMvc.perform(post("/api/medicamento-cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicamentoCIDDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicamentoCID in the database
        List<MedicamentoCID> medicamentoCIDList = medicamentoCIDRepository.findAll();
        assertThat(medicamentoCIDList).hasSize(databaseSizeBeforeCreate + 1);
        MedicamentoCID testMedicamentoCID = medicamentoCIDList.get(medicamentoCIDList.size() - 1);
        assertThat(testMedicamentoCID.getCodigoMedicamento()).isEqualTo(DEFAULT_CODIGO_MEDICAMENTO);
        assertThat(testMedicamentoCID.getCodigoCID()).isEqualTo(DEFAULT_CODIGO_CID);

        // Validate the MedicamentoCID in Elasticsearch
        verify(mockMedicamentoCIDSearchRepository, times(1)).save(testMedicamentoCID);
    }

    @Test
    @Transactional
    public void createMedicamentoCIDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicamentoCIDRepository.findAll().size();

        // Create the MedicamentoCID with an existing ID
        medicamentoCID.setId(1L);
        MedicamentoCIDDTO medicamentoCIDDTO = medicamentoCIDMapper.toDto(medicamentoCID);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicamentoCIDMockMvc.perform(post("/api/medicamento-cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicamentoCIDDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicamentoCID in the database
        List<MedicamentoCID> medicamentoCIDList = medicamentoCIDRepository.findAll();
        assertThat(medicamentoCIDList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicamentoCID in Elasticsearch
        verify(mockMedicamentoCIDSearchRepository, times(0)).save(medicamentoCID);
    }


    @Test
    @Transactional
    public void getAllMedicamentoCIDS() throws Exception {
        // Initialize the database
        medicamentoCIDRepository.saveAndFlush(medicamentoCID);

        // Get all the medicamentoCIDList
        restMedicamentoCIDMockMvc.perform(get("/api/medicamento-cids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicamentoCID.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoMedicamento").value(hasItem(DEFAULT_CODIGO_MEDICAMENTO)))
            .andExpect(jsonPath("$.[*].codigoCID").value(hasItem(DEFAULT_CODIGO_CID)));
    }
    
    @Test
    @Transactional
    public void getMedicamentoCID() throws Exception {
        // Initialize the database
        medicamentoCIDRepository.saveAndFlush(medicamentoCID);

        // Get the medicamentoCID
        restMedicamentoCIDMockMvc.perform(get("/api/medicamento-cids/{id}", medicamentoCID.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicamentoCID.getId().intValue()))
            .andExpect(jsonPath("$.codigoMedicamento").value(DEFAULT_CODIGO_MEDICAMENTO))
            .andExpect(jsonPath("$.codigoCID").value(DEFAULT_CODIGO_CID));
    }

    @Test
    @Transactional
    public void getNonExistingMedicamentoCID() throws Exception {
        // Get the medicamentoCID
        restMedicamentoCIDMockMvc.perform(get("/api/medicamento-cids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicamentoCID() throws Exception {
        // Initialize the database
        medicamentoCIDRepository.saveAndFlush(medicamentoCID);

        int databaseSizeBeforeUpdate = medicamentoCIDRepository.findAll().size();

        // Update the medicamentoCID
        MedicamentoCID updatedMedicamentoCID = medicamentoCIDRepository.findById(medicamentoCID.getId()).get();
        // Disconnect from session so that the updates on updatedMedicamentoCID are not directly saved in db
        em.detach(updatedMedicamentoCID);
        updatedMedicamentoCID
            .codigoMedicamento(UPDATED_CODIGO_MEDICAMENTO)
            .codigoCID(UPDATED_CODIGO_CID);
        MedicamentoCIDDTO medicamentoCIDDTO = medicamentoCIDMapper.toDto(updatedMedicamentoCID);

        restMedicamentoCIDMockMvc.perform(put("/api/medicamento-cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicamentoCIDDTO)))
            .andExpect(status().isOk());

        // Validate the MedicamentoCID in the database
        List<MedicamentoCID> medicamentoCIDList = medicamentoCIDRepository.findAll();
        assertThat(medicamentoCIDList).hasSize(databaseSizeBeforeUpdate);
        MedicamentoCID testMedicamentoCID = medicamentoCIDList.get(medicamentoCIDList.size() - 1);
        assertThat(testMedicamentoCID.getCodigoMedicamento()).isEqualTo(UPDATED_CODIGO_MEDICAMENTO);
        assertThat(testMedicamentoCID.getCodigoCID()).isEqualTo(UPDATED_CODIGO_CID);

        // Validate the MedicamentoCID in Elasticsearch
        verify(mockMedicamentoCIDSearchRepository, times(1)).save(testMedicamentoCID);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicamentoCID() throws Exception {
        int databaseSizeBeforeUpdate = medicamentoCIDRepository.findAll().size();

        // Create the MedicamentoCID
        MedicamentoCIDDTO medicamentoCIDDTO = medicamentoCIDMapper.toDto(medicamentoCID);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicamentoCIDMockMvc.perform(put("/api/medicamento-cids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicamentoCIDDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicamentoCID in the database
        List<MedicamentoCID> medicamentoCIDList = medicamentoCIDRepository.findAll();
        assertThat(medicamentoCIDList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicamentoCID in Elasticsearch
        verify(mockMedicamentoCIDSearchRepository, times(0)).save(medicamentoCID);
    }

    @Test
    @Transactional
    public void deleteMedicamentoCID() throws Exception {
        // Initialize the database
        medicamentoCIDRepository.saveAndFlush(medicamentoCID);

        int databaseSizeBeforeDelete = medicamentoCIDRepository.findAll().size();

        // Delete the medicamentoCID
        restMedicamentoCIDMockMvc.perform(delete("/api/medicamento-cids/{id}", medicamentoCID.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicamentoCID> medicamentoCIDList = medicamentoCIDRepository.findAll();
        assertThat(medicamentoCIDList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicamentoCID in Elasticsearch
        verify(mockMedicamentoCIDSearchRepository, times(1)).deleteById(medicamentoCID.getId());
    }

    @Test
    @Transactional
    public void searchMedicamentoCID() throws Exception {
        // Initialize the database
        medicamentoCIDRepository.saveAndFlush(medicamentoCID);
        when(mockMedicamentoCIDSearchRepository.search(queryStringQuery("id:" + medicamentoCID.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(medicamentoCID), PageRequest.of(0, 1), 1));
        // Search the medicamentoCID
        restMedicamentoCIDMockMvc.perform(get("/api/_search/medicamento-cids?query=id:" + medicamentoCID.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicamentoCID.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoMedicamento").value(hasItem(DEFAULT_CODIGO_MEDICAMENTO)))
            .andExpect(jsonPath("$.[*].codigoCID").value(hasItem(DEFAULT_CODIGO_CID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicamentoCID.class);
        MedicamentoCID medicamentoCID1 = new MedicamentoCID();
        medicamentoCID1.setId(1L);
        MedicamentoCID medicamentoCID2 = new MedicamentoCID();
        medicamentoCID2.setId(medicamentoCID1.getId());
        assertThat(medicamentoCID1).isEqualTo(medicamentoCID2);
        medicamentoCID2.setId(2L);
        assertThat(medicamentoCID1).isNotEqualTo(medicamentoCID2);
        medicamentoCID1.setId(null);
        assertThat(medicamentoCID1).isNotEqualTo(medicamentoCID2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicamentoCIDDTO.class);
        MedicamentoCIDDTO medicamentoCIDDTO1 = new MedicamentoCIDDTO();
        medicamentoCIDDTO1.setId(1L);
        MedicamentoCIDDTO medicamentoCIDDTO2 = new MedicamentoCIDDTO();
        assertThat(medicamentoCIDDTO1).isNotEqualTo(medicamentoCIDDTO2);
        medicamentoCIDDTO2.setId(medicamentoCIDDTO1.getId());
        assertThat(medicamentoCIDDTO1).isEqualTo(medicamentoCIDDTO2);
        medicamentoCIDDTO2.setId(2L);
        assertThat(medicamentoCIDDTO1).isNotEqualTo(medicamentoCIDDTO2);
        medicamentoCIDDTO1.setId(null);
        assertThat(medicamentoCIDDTO1).isNotEqualTo(medicamentoCIDDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicamentoCIDMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicamentoCIDMapper.fromId(null)).isNull();
    }
}
