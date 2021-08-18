package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.Material;
import br.com.basis.madre.madreexames.repository.MaterialRepository;
import br.com.basis.madre.madreexames.repository.search.MaterialSearchRepository;
import br.com.basis.madre.madreexames.service.MaterialService;
import br.com.basis.madre.madreexames.service.dto.MaterialDTO;
import br.com.basis.madre.madreexames.service.mapper.MaterialMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MaterialResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MaterialResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_COLETAVEL = false;
    private static final Boolean UPDATED_COLETAVEL = true;

    private static final Boolean DEFAULT_EXIGE_INFORMACAO = false;
    private static final Boolean UPDATED_EXIGE_INFORMACAO = true;

    private static final Boolean DEFAULT_URINA = false;
    private static final Boolean UPDATED_URINA = true;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.MaterialSearchRepositoryMockConfiguration
     */
    @Autowired
    private MaterialSearchRepository mockMaterialSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialMockMvc;

    private Material material;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createEntity(EntityManager em) {
        Material material = new Material()
            .nome(DEFAULT_NOME)
            .ativo(DEFAULT_ATIVO)
            .coletavel(DEFAULT_COLETAVEL)
            .exigeInformacao(DEFAULT_EXIGE_INFORMACAO)
            .urina(DEFAULT_URINA);
        return material;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createUpdatedEntity(EntityManager em) {
        Material material = new Material()
            .nome(UPDATED_NOME)
            .ativo(UPDATED_ATIVO)
            .coletavel(UPDATED_COLETAVEL)
            .exigeInformacao(UPDATED_EXIGE_INFORMACAO)
            .urina(UPDATED_URINA);
        return material;
    }

    @BeforeEach
    public void initTest() {
        material = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterial() throws Exception {
        int databaseSizeBeforeCreate = materialRepository.findAll().size();
        // Create the Material
        MaterialDTO materialDTO = materialMapper.toDto(material);
        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isCreated());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate + 1);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMaterial.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testMaterial.isColetavel()).isEqualTo(DEFAULT_COLETAVEL);
        assertThat(testMaterial.isExigeInformacao()).isEqualTo(DEFAULT_EXIGE_INFORMACAO);
        assertThat(testMaterial.isUrina()).isEqualTo(DEFAULT_URINA);

        // Validate the Material in Elasticsearch
        verify(mockMaterialSearchRepository, times(1)).save(testMaterial);
    }

    @Test
    @Transactional
    public void createMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialRepository.findAll().size();

        // Create the Material with an existing ID
        material.setId(1L);
        MaterialDTO materialDTO = materialMapper.toDto(material);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate);

        // Validate the Material in Elasticsearch
        verify(mockMaterialSearchRepository, times(0)).save(material);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setNome(null);

        // Create the Material, which fails.
        MaterialDTO materialDTO = materialMapper.toDto(material);


        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setAtivo(null);

        // Create the Material, which fails.
        MaterialDTO materialDTO = materialMapper.toDto(material);


        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColetavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setColetavel(null);

        // Create the Material, which fails.
        MaterialDTO materialDTO = materialMapper.toDto(material);


        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExigeInformacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setExigeInformacao(null);

        // Create the Material, which fails.
        MaterialDTO materialDTO = materialMapper.toDto(material);


        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrinaIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setUrina(null);

        // Create the Material, which fails.
        MaterialDTO materialDTO = materialMapper.toDto(material);


        restMaterialMockMvc.perform(post("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterials() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList
        restMaterialMockMvc.perform(get("/api/materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].coletavel").value(hasItem(DEFAULT_COLETAVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeInformacao").value(hasItem(DEFAULT_EXIGE_INFORMACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].urina").value(hasItem(DEFAULT_URINA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get the material
        restMaterialMockMvc.perform(get("/api/materials/{id}", material.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(material.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.coletavel").value(DEFAULT_COLETAVEL.booleanValue()))
            .andExpect(jsonPath("$.exigeInformacao").value(DEFAULT_EXIGE_INFORMACAO.booleanValue()))
            .andExpect(jsonPath("$.urina").value(DEFAULT_URINA.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMaterial() throws Exception {
        // Get the material
        restMaterialMockMvc.perform(get("/api/materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material
        Material updatedMaterial = materialRepository.findById(material.getId()).get();
        // Disconnect from session so that the updates on updatedMaterial are not directly saved in db
        em.detach(updatedMaterial);
        updatedMaterial
            .nome(UPDATED_NOME)
            .ativo(UPDATED_ATIVO)
            .coletavel(UPDATED_COLETAVEL)
            .exigeInformacao(UPDATED_EXIGE_INFORMACAO)
            .urina(UPDATED_URINA);
        MaterialDTO materialDTO = materialMapper.toDto(updatedMaterial);

        restMaterialMockMvc.perform(put("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMaterial.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testMaterial.isColetavel()).isEqualTo(UPDATED_COLETAVEL);
        assertThat(testMaterial.isExigeInformacao()).isEqualTo(UPDATED_EXIGE_INFORMACAO);
        assertThat(testMaterial.isUrina()).isEqualTo(UPDATED_URINA);

        // Validate the Material in Elasticsearch
        verify(mockMaterialSearchRepository, times(1)).save(testMaterial);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Create the Material
        MaterialDTO materialDTO = materialMapper.toDto(material);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc.perform(put("/api/materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Material in Elasticsearch
        verify(mockMaterialSearchRepository, times(0)).save(material);
    }

    @Test
    @Transactional
    public void deleteMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeDelete = materialRepository.findAll().size();

        // Delete the material
        restMaterialMockMvc.perform(delete("/api/materials/{id}", material.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Material in Elasticsearch
        verify(mockMaterialSearchRepository, times(1)).deleteById(material.getId());
    }

    @Test
    @Transactional
    public void searchMaterial() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        materialRepository.saveAndFlush(material);
        when(mockMaterialSearchRepository.search(queryStringQuery("id:" + material.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(material), PageRequest.of(0, 1), 1));

        // Search the material
        restMaterialMockMvc.perform(get("/api/_search/materials?query=id:" + material.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].coletavel").value(hasItem(DEFAULT_COLETAVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeInformacao").value(hasItem(DEFAULT_EXIGE_INFORMACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].urina").value(hasItem(DEFAULT_URINA.booleanValue())));
    }
}
