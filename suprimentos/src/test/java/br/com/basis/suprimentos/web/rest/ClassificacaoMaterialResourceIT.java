package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.ClassificacaoMaterial;
import br.com.basis.suprimentos.repository.ClassificacaoMaterialRepository;
import br.com.basis.suprimentos.repository.search.ClassificacaoMaterialSearchRepository;
import br.com.basis.suprimentos.service.ClassificacaoMaterialService;
import br.com.basis.suprimentos.service.dto.ClassificacaoMaterialDTO;
import br.com.basis.suprimentos.service.mapper.ClassificacaoMaterialMapper;
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
import java.util.Collections;
import java.util.List;

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
 * Integration tests for the {@link ClassificacaoMaterialResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClassificacaoMaterialResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ClassificacaoMaterialRepository classificacaoMaterialRepository;

    @Autowired
    private ClassificacaoMaterialMapper classificacaoMaterialMapper;

    @Autowired
    private ClassificacaoMaterialService classificacaoMaterialService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.ClassificacaoMaterialSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassificacaoMaterialSearchRepository mockClassificacaoMaterialSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassificacaoMaterialMockMvc;

    private ClassificacaoMaterial classificacaoMaterial;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassificacaoMaterial createEntity(EntityManager em) {
        ClassificacaoMaterial classificacaoMaterial = new ClassificacaoMaterial();
        classificacaoMaterial.setDescricao(DEFAULT_DESCRICAO);
        return classificacaoMaterial;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassificacaoMaterial createUpdatedEntity(EntityManager em) {
        ClassificacaoMaterial classificacaoMaterial = new ClassificacaoMaterial();
        classificacaoMaterial.setDescricao(UPDATED_DESCRICAO);
        return classificacaoMaterial;
    }

    @BeforeEach
    public void initTest() {
        classificacaoMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassificacaoMaterial() throws Exception {
        int databaseSizeBeforeCreate = classificacaoMaterialRepository.findAll().size();
        // Create the ClassificacaoMaterial
        ClassificacaoMaterialDTO classificacaoMaterialDTO = classificacaoMaterialMapper.toDto(classificacaoMaterial);
        restClassificacaoMaterialMockMvc.perform(post("/api/classificacao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificacaoMaterialDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassificacaoMaterial in the database
        List<ClassificacaoMaterial> classificacaoMaterialList = classificacaoMaterialRepository.findAll();
        assertThat(classificacaoMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        ClassificacaoMaterial testClassificacaoMaterial = classificacaoMaterialList.get(classificacaoMaterialList.size() - 1);
        assertThat(testClassificacaoMaterial.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the ClassificacaoMaterial in Elasticsearch
        verify(mockClassificacaoMaterialSearchRepository, times(1)).save(testClassificacaoMaterial);
    }

    @Test
    @Transactional
    public void createClassificacaoMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classificacaoMaterialRepository.findAll().size();

        // Create the ClassificacaoMaterial with an existing ID
        classificacaoMaterial.setId(1L);
        ClassificacaoMaterialDTO classificacaoMaterialDTO = classificacaoMaterialMapper.toDto(classificacaoMaterial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassificacaoMaterialMockMvc.perform(post("/api/classificacao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificacaoMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassificacaoMaterial in the database
        List<ClassificacaoMaterial> classificacaoMaterialList = classificacaoMaterialRepository.findAll();
        assertThat(classificacaoMaterialList).hasSize(databaseSizeBeforeCreate);

        // Validate the ClassificacaoMaterial in Elasticsearch
        verify(mockClassificacaoMaterialSearchRepository, times(0)).save(classificacaoMaterial);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificacaoMaterialRepository.findAll().size();
        // set the field null
        classificacaoMaterial.setDescricao(null);

        // Create the ClassificacaoMaterial, which fails.
        ClassificacaoMaterialDTO classificacaoMaterialDTO = classificacaoMaterialMapper.toDto(classificacaoMaterial);


        restClassificacaoMaterialMockMvc.perform(post("/api/classificacao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificacaoMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<ClassificacaoMaterial> classificacaoMaterialList = classificacaoMaterialRepository.findAll();
        assertThat(classificacaoMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassificacaoMaterials() throws Exception {
        // Initialize the database
        classificacaoMaterialRepository.saveAndFlush(classificacaoMaterial);

        // Get all the classificacaoMaterialList
        restClassificacaoMaterialMockMvc.perform(get("/api/classificacao-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificacaoMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getClassificacaoMaterial() throws Exception {
        // Initialize the database
        classificacaoMaterialRepository.saveAndFlush(classificacaoMaterial);

        // Get the classificacaoMaterial
        restClassificacaoMaterialMockMvc.perform(get("/api/classificacao-materials/{id}", classificacaoMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classificacaoMaterial.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingClassificacaoMaterial() throws Exception {
        // Get the classificacaoMaterial
        restClassificacaoMaterialMockMvc.perform(get("/api/classificacao-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassificacaoMaterial() throws Exception {
        // Initialize the database
        classificacaoMaterialRepository.saveAndFlush(classificacaoMaterial);

        int databaseSizeBeforeUpdate = classificacaoMaterialRepository.findAll().size();

        // Update the classificacaoMaterial
        ClassificacaoMaterial updatedClassificacaoMaterial = classificacaoMaterialRepository.findById(classificacaoMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedClassificacaoMaterial are not directly saved in db
        em.detach(updatedClassificacaoMaterial);
        updatedClassificacaoMaterial
            .setDescricao(UPDATED_DESCRICAO);
        ClassificacaoMaterialDTO classificacaoMaterialDTO = classificacaoMaterialMapper.toDto(updatedClassificacaoMaterial);

        restClassificacaoMaterialMockMvc.perform(put("/api/classificacao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificacaoMaterialDTO)))
            .andExpect(status().isOk());

        // Validate the ClassificacaoMaterial in the database
        List<ClassificacaoMaterial> classificacaoMaterialList = classificacaoMaterialRepository.findAll();
        assertThat(classificacaoMaterialList).hasSize(databaseSizeBeforeUpdate);
        ClassificacaoMaterial testClassificacaoMaterial = classificacaoMaterialList.get(classificacaoMaterialList.size() - 1);
        assertThat(testClassificacaoMaterial.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the ClassificacaoMaterial in Elasticsearch
        verify(mockClassificacaoMaterialSearchRepository, times(1)).save(testClassificacaoMaterial);
    }

    @Test
    @Transactional
    public void updateNonExistingClassificacaoMaterial() throws Exception {
        int databaseSizeBeforeUpdate = classificacaoMaterialRepository.findAll().size();

        // Create the ClassificacaoMaterial
        ClassificacaoMaterialDTO classificacaoMaterialDTO = classificacaoMaterialMapper.toDto(classificacaoMaterial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificacaoMaterialMockMvc.perform(put("/api/classificacao-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classificacaoMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassificacaoMaterial in the database
        List<ClassificacaoMaterial> classificacaoMaterialList = classificacaoMaterialRepository.findAll();
        assertThat(classificacaoMaterialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassificacaoMaterial in Elasticsearch
        verify(mockClassificacaoMaterialSearchRepository, times(0)).save(classificacaoMaterial);
    }

    @Test
    @Transactional
    public void deleteClassificacaoMaterial() throws Exception {
        // Initialize the database
        classificacaoMaterialRepository.saveAndFlush(classificacaoMaterial);

        int databaseSizeBeforeDelete = classificacaoMaterialRepository.findAll().size();

        // Delete the classificacaoMaterial
        restClassificacaoMaterialMockMvc.perform(delete("/api/classificacao-materials/{id}", classificacaoMaterial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassificacaoMaterial> classificacaoMaterialList = classificacaoMaterialRepository.findAll();
        assertThat(classificacaoMaterialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ClassificacaoMaterial in Elasticsearch
        verify(mockClassificacaoMaterialSearchRepository, times(1)).deleteById(classificacaoMaterial.getId());
    }

    @Test
    @Transactional
    public void searchClassificacaoMaterial() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        classificacaoMaterialRepository.saveAndFlush(classificacaoMaterial);
        when(mockClassificacaoMaterialSearchRepository.search(queryStringQuery("id:" + classificacaoMaterial.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classificacaoMaterial), PageRequest.of(0, 1), 1));

        // Search the classificacaoMaterial
        restClassificacaoMaterialMockMvc.perform(get("/api/_search/classificacao-materials?query=id:" + classificacaoMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificacaoMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
}
