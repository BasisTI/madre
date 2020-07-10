package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.RequisicaoMaterial;
import br.com.basis.suprimentos.repository.RequisicaoMaterialRepository;
import br.com.basis.suprimentos.repository.search.RequisicaoMaterialSearchRepository;
import br.com.basis.suprimentos.service.RequisicaoMaterialService;
import br.com.basis.suprimentos.service.dto.RequisicaoMaterialDTO;
import br.com.basis.suprimentos.service.mapper.RequisicaoMaterialMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static br.com.basis.suprimentos.web.rest.TestUtil.sameInstant;
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
 * Integration tests for the {@link RequisicaoMaterialResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RequisicaoMaterialResourceIT {

    private static final ZonedDateTime DEFAULT_GERADO_EM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_GERADO_EM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_GERADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_GERADO_POR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CONFIRMADO_EM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CONFIRMADO_EM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CONFIRMADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMADO_POR = "BBBBBBBBBB";

    @Autowired
    private RequisicaoMaterialRepository requisicaoMaterialRepository;

    @Autowired
    private RequisicaoMaterialMapper requisicaoMaterialMapper;

    @Autowired
    private RequisicaoMaterialService requisicaoMaterialService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.RequisicaoMaterialSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequisicaoMaterialSearchRepository mockRequisicaoMaterialSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequisicaoMaterialMockMvc;

    private RequisicaoMaterial requisicaoMaterial;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequisicaoMaterial createEntity(EntityManager em) {
        RequisicaoMaterial requisicaoMaterial = new RequisicaoMaterial();
        requisicaoMaterial.setGeradoEm(DEFAULT_GERADO_EM);
        requisicaoMaterial.setGeradoPor(DEFAULT_GERADO_POR);
        requisicaoMaterial.setConfirmadoEm(DEFAULT_CONFIRMADO_EM);
        requisicaoMaterial.setConfirmadoPor(DEFAULT_CONFIRMADO_POR);
        return requisicaoMaterial;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequisicaoMaterial createUpdatedEntity(EntityManager em) {
        RequisicaoMaterial requisicaoMaterial = new RequisicaoMaterial();
        requisicaoMaterial.setGeradoEm(UPDATED_GERADO_EM);
        requisicaoMaterial.setGeradoPor(UPDATED_GERADO_POR);
        requisicaoMaterial.setConfirmadoEm(UPDATED_CONFIRMADO_EM);
        requisicaoMaterial.setConfirmadoPor(UPDATED_CONFIRMADO_POR);
        return requisicaoMaterial;
    }

    @BeforeEach
    public void initTest() {
        requisicaoMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequisicaoMaterial() throws Exception {
        int databaseSizeBeforeCreate = requisicaoMaterialRepository.findAll().size();
        // Create the RequisicaoMaterial
        RequisicaoMaterialDTO requisicaoMaterialDTO = requisicaoMaterialMapper.toDto(requisicaoMaterial);
        restRequisicaoMaterialMockMvc.perform(post("/api/requisicao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requisicaoMaterialDTO)))
            .andExpect(status().isCreated());

        // Validate the RequisicaoMaterial in the database
        List<RequisicaoMaterial> requisicaoMaterialList = requisicaoMaterialRepository.findAll();
        assertThat(requisicaoMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        RequisicaoMaterial testRequisicaoMaterial = requisicaoMaterialList.get(requisicaoMaterialList.size() - 1);
        assertThat(testRequisicaoMaterial.getGeradoEm()).isEqualTo(DEFAULT_GERADO_EM);
        assertThat(testRequisicaoMaterial.getGeradoPor()).isEqualTo(DEFAULT_GERADO_POR);
        assertThat(testRequisicaoMaterial.getConfirmadoEm()).isEqualTo(DEFAULT_CONFIRMADO_EM);
        assertThat(testRequisicaoMaterial.getConfirmadoPor()).isEqualTo(DEFAULT_CONFIRMADO_POR);

        // Validate the RequisicaoMaterial in Elasticsearch
        verify(mockRequisicaoMaterialSearchRepository, times(1)).save(testRequisicaoMaterial);
    }

    @Test
    @Transactional
    public void createRequisicaoMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requisicaoMaterialRepository.findAll().size();

        // Create the RequisicaoMaterial with an existing ID
        requisicaoMaterial.setId(1L);
        RequisicaoMaterialDTO requisicaoMaterialDTO = requisicaoMaterialMapper.toDto(requisicaoMaterial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisicaoMaterialMockMvc.perform(post("/api/requisicao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requisicaoMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequisicaoMaterial in the database
        List<RequisicaoMaterial> requisicaoMaterialList = requisicaoMaterialRepository.findAll();
        assertThat(requisicaoMaterialList).hasSize(databaseSizeBeforeCreate);

        // Validate the RequisicaoMaterial in Elasticsearch
        verify(mockRequisicaoMaterialSearchRepository, times(0)).save(requisicaoMaterial);
    }


    @Test
    @Transactional
    public void checkGeradoEmIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisicaoMaterialRepository.findAll().size();
        // set the field null
        requisicaoMaterial.setGeradoEm(null);

        // Create the RequisicaoMaterial, which fails.
        RequisicaoMaterialDTO requisicaoMaterialDTO = requisicaoMaterialMapper.toDto(requisicaoMaterial);


        restRequisicaoMaterialMockMvc.perform(post("/api/requisicao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requisicaoMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<RequisicaoMaterial> requisicaoMaterialList = requisicaoMaterialRepository.findAll();
        assertThat(requisicaoMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeradoPorIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisicaoMaterialRepository.findAll().size();
        // set the field null
        requisicaoMaterial.setGeradoPor(null);

        // Create the RequisicaoMaterial, which fails.
        RequisicaoMaterialDTO requisicaoMaterialDTO = requisicaoMaterialMapper.toDto(requisicaoMaterial);


        restRequisicaoMaterialMockMvc.perform(post("/api/requisicao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requisicaoMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<RequisicaoMaterial> requisicaoMaterialList = requisicaoMaterialRepository.findAll();
        assertThat(requisicaoMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequisicaoMaterials() throws Exception {
        // Initialize the database
        requisicaoMaterialRepository.saveAndFlush(requisicaoMaterial);

        // Get all the requisicaoMaterialList
        restRequisicaoMaterialMockMvc.perform(get("/api/requisicao-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisicaoMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].geradoEm").value(hasItem(sameInstant(DEFAULT_GERADO_EM))))
            .andExpect(jsonPath("$.[*].geradoPor").value(hasItem(DEFAULT_GERADO_POR)))
            .andExpect(jsonPath("$.[*].confirmadoEm").value(hasItem(sameInstant(DEFAULT_CONFIRMADO_EM))))
            .andExpect(jsonPath("$.[*].confirmadoPor").value(hasItem(DEFAULT_CONFIRMADO_POR)));
    }

    @Test
    @Transactional
    public void getRequisicaoMaterial() throws Exception {
        // Initialize the database
        requisicaoMaterialRepository.saveAndFlush(requisicaoMaterial);

        // Get the requisicaoMaterial
        restRequisicaoMaterialMockMvc.perform(get("/api/requisicao-materials/{id}", requisicaoMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requisicaoMaterial.getId().intValue()))
            .andExpect(jsonPath("$.geradoEm").value(sameInstant(DEFAULT_GERADO_EM)))
            .andExpect(jsonPath("$.geradoPor").value(DEFAULT_GERADO_POR))
            .andExpect(jsonPath("$.confirmadoEm").value(sameInstant(DEFAULT_CONFIRMADO_EM)))
            .andExpect(jsonPath("$.confirmadoPor").value(DEFAULT_CONFIRMADO_POR));
    }

    @Test
    @Transactional
    public void getNonExistingRequisicaoMaterial() throws Exception {
        // Get the requisicaoMaterial
        restRequisicaoMaterialMockMvc.perform(get("/api/requisicao-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequisicaoMaterial() throws Exception {
        // Initialize the database
        requisicaoMaterialRepository.saveAndFlush(requisicaoMaterial);

        int databaseSizeBeforeUpdate = requisicaoMaterialRepository.findAll().size();

        // Update the requisicaoMaterial
        RequisicaoMaterial updatedRequisicaoMaterial = requisicaoMaterialRepository.findById(requisicaoMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedRequisicaoMaterial are not directly saved in db
        em.detach(updatedRequisicaoMaterial);
        updatedRequisicaoMaterial
            .setGeradoEm(UPDATED_GERADO_EM);
        updatedRequisicaoMaterial.setGeradoPor(UPDATED_GERADO_POR);
        updatedRequisicaoMaterial.setConfirmadoEm(UPDATED_CONFIRMADO_EM);
        updatedRequisicaoMaterial.setConfirmadoPor(UPDATED_CONFIRMADO_POR);
        RequisicaoMaterialDTO requisicaoMaterialDTO = requisicaoMaterialMapper.toDto(updatedRequisicaoMaterial);

        restRequisicaoMaterialMockMvc.perform(put("/api/requisicao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requisicaoMaterialDTO)))
            .andExpect(status().isOk());

        // Validate the RequisicaoMaterial in the database
        List<RequisicaoMaterial> requisicaoMaterialList = requisicaoMaterialRepository.findAll();
        assertThat(requisicaoMaterialList).hasSize(databaseSizeBeforeUpdate);
        RequisicaoMaterial testRequisicaoMaterial = requisicaoMaterialList.get(requisicaoMaterialList.size() - 1);
        assertThat(testRequisicaoMaterial.getGeradoEm()).isEqualTo(UPDATED_GERADO_EM);
        assertThat(testRequisicaoMaterial.getGeradoPor()).isEqualTo(UPDATED_GERADO_POR);
        assertThat(testRequisicaoMaterial.getConfirmadoEm()).isEqualTo(UPDATED_CONFIRMADO_EM);
        assertThat(testRequisicaoMaterial.getConfirmadoPor()).isEqualTo(UPDATED_CONFIRMADO_POR);

        // Validate the RequisicaoMaterial in Elasticsearch
        verify(mockRequisicaoMaterialSearchRepository, times(1)).save(testRequisicaoMaterial);
    }

    @Test
    @Transactional
    public void updateNonExistingRequisicaoMaterial() throws Exception {
        int databaseSizeBeforeUpdate = requisicaoMaterialRepository.findAll().size();

        // Create the RequisicaoMaterial
        RequisicaoMaterialDTO requisicaoMaterialDTO = requisicaoMaterialMapper.toDto(requisicaoMaterial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisicaoMaterialMockMvc.perform(put("/api/requisicao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requisicaoMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequisicaoMaterial in the database
        List<RequisicaoMaterial> requisicaoMaterialList = requisicaoMaterialRepository.findAll();
        assertThat(requisicaoMaterialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RequisicaoMaterial in Elasticsearch
        verify(mockRequisicaoMaterialSearchRepository, times(0)).save(requisicaoMaterial);
    }

    @Test
    @Transactional
    public void deleteRequisicaoMaterial() throws Exception {
        // Initialize the database
        requisicaoMaterialRepository.saveAndFlush(requisicaoMaterial);

        int databaseSizeBeforeDelete = requisicaoMaterialRepository.findAll().size();

        // Delete the requisicaoMaterial
        restRequisicaoMaterialMockMvc.perform(delete("/api/requisicao-materials/{id}", requisicaoMaterial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequisicaoMaterial> requisicaoMaterialList = requisicaoMaterialRepository.findAll();
        assertThat(requisicaoMaterialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RequisicaoMaterial in Elasticsearch
        verify(mockRequisicaoMaterialSearchRepository, times(1)).deleteById(requisicaoMaterial.getId());
    }

    @Test
    @Transactional
    public void searchRequisicaoMaterial() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        requisicaoMaterialRepository.saveAndFlush(requisicaoMaterial);
        when(mockRequisicaoMaterialSearchRepository.search(queryStringQuery("id:" + requisicaoMaterial.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(requisicaoMaterial), PageRequest.of(0, 1), 1));

        // Search the requisicaoMaterial
        restRequisicaoMaterialMockMvc.perform(get("/api/_search/requisicao-materials?query=id:" + requisicaoMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisicaoMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].geradoEm").value(hasItem(sameInstant(DEFAULT_GERADO_EM))))
            .andExpect(jsonPath("$.[*].geradoPor").value(hasItem(DEFAULT_GERADO_POR)))
            .andExpect(jsonPath("$.[*].confirmadoEm").value(hasItem(sameInstant(DEFAULT_CONFIRMADO_EM))))
            .andExpect(jsonPath("$.[*].confirmadoPor").value(hasItem(DEFAULT_CONFIRMADO_POR)));
    }
}
