package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.GrupoMaterial;
import br.com.basis.suprimentos.repository.GrupoMaterialRepository;
import br.com.basis.suprimentos.repository.search.GrupoMaterialSearchRepository;
import br.com.basis.suprimentos.service.GrupoMaterialService;
import br.com.basis.suprimentos.service.dto.GrupoMaterialDTO;
import br.com.basis.suprimentos.service.mapper.GrupoMaterialMapper;
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
 * Integration tests for the {@link GrupoMaterialResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class GrupoMaterialResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private GrupoMaterialRepository grupoMaterialRepository;

    @Autowired
    private GrupoMaterialMapper grupoMaterialMapper;

    @Autowired
    private GrupoMaterialService grupoMaterialService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.GrupoMaterialSearchRepositoryMockConfiguration
     */
    @Autowired
    private GrupoMaterialSearchRepository mockGrupoMaterialSearchRepository;

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

    private MockMvc restGrupoMaterialMockMvc;

    private GrupoMaterial grupoMaterial;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoMaterial createEntity(EntityManager em) {
        GrupoMaterial grupoMaterial = new GrupoMaterial()
                .descricao(DEFAULT_DESCRICAO);
        return grupoMaterial;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoMaterial createUpdatedEntity(EntityManager em) {
        GrupoMaterial grupoMaterial = new GrupoMaterial()
                .descricao(UPDATED_DESCRICAO);
        return grupoMaterial;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrupoMaterialResource grupoMaterialResource = new GrupoMaterialResource(grupoMaterialService);
        this.restGrupoMaterialMockMvc = MockMvcBuilders.standaloneSetup(grupoMaterialResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        grupoMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupoMaterial() throws Exception {
        int databaseSizeBeforeCreate = grupoMaterialRepository.findAll().size();

        // Create the GrupoMaterial
        GrupoMaterialDTO grupoMaterialDTO = grupoMaterialMapper.toDto(grupoMaterial);
        restGrupoMaterialMockMvc.perform(post("/api/grupo-materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(grupoMaterialDTO)))
                .andExpect(status().isCreated());

        // Validate the GrupoMaterial in the database
        List<GrupoMaterial> grupoMaterialList = grupoMaterialRepository.findAll();
        assertThat(grupoMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoMaterial testGrupoMaterial = grupoMaterialList.get(grupoMaterialList.size() - 1);
        assertThat(testGrupoMaterial.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the GrupoMaterial in Elasticsearch
        verify(mockGrupoMaterialSearchRepository, times(1)).save(testGrupoMaterial);
    }

    @Test
    @Transactional
    public void createGrupoMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupoMaterialRepository.findAll().size();

        // Create the GrupoMaterial with an existing ID
        grupoMaterial.setId(1L);
        GrupoMaterialDTO grupoMaterialDTO = grupoMaterialMapper.toDto(grupoMaterial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoMaterialMockMvc.perform(post("/api/grupo-materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(grupoMaterialDTO)))
                .andExpect(status().isBadRequest());

        // Validate the GrupoMaterial in the database
        List<GrupoMaterial> grupoMaterialList = grupoMaterialRepository.findAll();
        assertThat(grupoMaterialList).hasSize(databaseSizeBeforeCreate);

        // Validate the GrupoMaterial in Elasticsearch
        verify(mockGrupoMaterialSearchRepository, times(0)).save(grupoMaterial);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoMaterialRepository.findAll().size();
        // set the field null
        grupoMaterial.setDescricao(null);

        // Create the GrupoMaterial, which fails.
        GrupoMaterialDTO grupoMaterialDTO = grupoMaterialMapper.toDto(grupoMaterial);

        restGrupoMaterialMockMvc.perform(post("/api/grupo-materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(grupoMaterialDTO)))
                .andExpect(status().isBadRequest());

        List<GrupoMaterial> grupoMaterialList = grupoMaterialRepository.findAll();
        assertThat(grupoMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrupoMaterials() throws Exception {
        // Initialize the database
        grupoMaterialRepository.saveAndFlush(grupoMaterial);

        // Get all the grupoMaterialList
        restGrupoMaterialMockMvc.perform(get("/api/grupo-materials?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(grupoMaterial.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getGrupoMaterial() throws Exception {
        // Initialize the database
        grupoMaterialRepository.saveAndFlush(grupoMaterial);

        // Get the grupoMaterial
        restGrupoMaterialMockMvc.perform(get("/api/grupo-materials/{id}", grupoMaterial.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(grupoMaterial.getId().intValue()))
                .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingGrupoMaterial() throws Exception {
        // Get the grupoMaterial
        restGrupoMaterialMockMvc.perform(get("/api/grupo-materials/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupoMaterial() throws Exception {
        // Initialize the database
        grupoMaterialRepository.saveAndFlush(grupoMaterial);

        int databaseSizeBeforeUpdate = grupoMaterialRepository.findAll().size();

        // Update the grupoMaterial
        GrupoMaterial updatedGrupoMaterial = grupoMaterialRepository.findById(grupoMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoMaterial are not directly saved in db
        em.detach(updatedGrupoMaterial);
        updatedGrupoMaterial
                .descricao(UPDATED_DESCRICAO);
        GrupoMaterialDTO grupoMaterialDTO = grupoMaterialMapper.toDto(updatedGrupoMaterial);

        restGrupoMaterialMockMvc.perform(put("/api/grupo-materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(grupoMaterialDTO)))
                .andExpect(status().isOk());

        // Validate the GrupoMaterial in the database
        List<GrupoMaterial> grupoMaterialList = grupoMaterialRepository.findAll();
        assertThat(grupoMaterialList).hasSize(databaseSizeBeforeUpdate);
        GrupoMaterial testGrupoMaterial = grupoMaterialList.get(grupoMaterialList.size() - 1);
        assertThat(testGrupoMaterial.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the GrupoMaterial in Elasticsearch
        verify(mockGrupoMaterialSearchRepository, times(1)).save(testGrupoMaterial);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupoMaterial() throws Exception {
        int databaseSizeBeforeUpdate = grupoMaterialRepository.findAll().size();

        // Create the GrupoMaterial
        GrupoMaterialDTO grupoMaterialDTO = grupoMaterialMapper.toDto(grupoMaterial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoMaterialMockMvc.perform(put("/api/grupo-materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(grupoMaterialDTO)))
                .andExpect(status().isBadRequest());

        // Validate the GrupoMaterial in the database
        List<GrupoMaterial> grupoMaterialList = grupoMaterialRepository.findAll();
        assertThat(grupoMaterialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GrupoMaterial in Elasticsearch
        verify(mockGrupoMaterialSearchRepository, times(0)).save(grupoMaterial);
    }

    @Test
    @Transactional
    public void deleteGrupoMaterial() throws Exception {
        // Initialize the database
        grupoMaterialRepository.saveAndFlush(grupoMaterial);

        int databaseSizeBeforeDelete = grupoMaterialRepository.findAll().size();

        // Delete the grupoMaterial
        restGrupoMaterialMockMvc.perform(delete("/api/grupo-materials/{id}", grupoMaterial.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoMaterial> grupoMaterialList = grupoMaterialRepository.findAll();
        assertThat(grupoMaterialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GrupoMaterial in Elasticsearch
        verify(mockGrupoMaterialSearchRepository, times(1)).deleteById(grupoMaterial.getId());
    }

    @Test
    @Transactional
    public void searchGrupoMaterial() throws Exception {
        // Initialize the database
        grupoMaterialRepository.saveAndFlush(grupoMaterial);
        when(mockGrupoMaterialSearchRepository.search(queryStringQuery("id:" + grupoMaterial.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(grupoMaterial), PageRequest.of(0, 1), 1));
        // Search the grupoMaterial
        restGrupoMaterialMockMvc.perform(get("/api/_search/grupo-materials?query=id:" + grupoMaterial.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(grupoMaterial.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoMaterial.class);
        GrupoMaterial grupoMaterial1 = new GrupoMaterial();
        grupoMaterial1.setId(1L);
        GrupoMaterial grupoMaterial2 = new GrupoMaterial();
        grupoMaterial2.setId(grupoMaterial1.getId());
        assertThat(grupoMaterial1).isEqualTo(grupoMaterial2);
        grupoMaterial2.setId(2L);
        assertThat(grupoMaterial1).isNotEqualTo(grupoMaterial2);
        grupoMaterial1.setId(null);
        assertThat(grupoMaterial1).isNotEqualTo(grupoMaterial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoMaterialDTO.class);
        GrupoMaterialDTO grupoMaterialDTO1 = new GrupoMaterialDTO();
        grupoMaterialDTO1.setId(1L);
        GrupoMaterialDTO grupoMaterialDTO2 = new GrupoMaterialDTO();
        assertThat(grupoMaterialDTO1).isNotEqualTo(grupoMaterialDTO2);
        grupoMaterialDTO2.setId(grupoMaterialDTO1.getId());
        assertThat(grupoMaterialDTO1).isEqualTo(grupoMaterialDTO2);
        grupoMaterialDTO2.setId(2L);
        assertThat(grupoMaterialDTO1).isNotEqualTo(grupoMaterialDTO2);
        grupoMaterialDTO1.setId(null);
        assertThat(grupoMaterialDTO1).isNotEqualTo(grupoMaterialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(grupoMaterialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(grupoMaterialMapper.fromId(null)).isNull();
    }
}
