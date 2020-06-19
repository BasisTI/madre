package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Almoxarifado;
import br.com.basis.suprimentos.domain.GrupoMaterial;
import br.com.basis.suprimentos.domain.Material;
import br.com.basis.suprimentos.domain.UnidadeMedida;
import br.com.basis.suprimentos.repository.MaterialRepository;
import br.com.basis.suprimentos.repository.search.MaterialSearchRepository;
import br.com.basis.suprimentos.service.MaterialService;
import br.com.basis.suprimentos.service.dto.MaterialDTO;
import br.com.basis.suprimentos.service.mapper.MaterialMapper;
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
 * Integration tests for the {@link MaterialResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class MaterialResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String DEFAULT_REGIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_REGIMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Long DEFAULT_UNIDADE_ID = 0L;
    private static final Long UPDATED_UNIDADE_ID = 1L;

    private static final Long DEFAULT_PROCEDIMENTO_ID = 0L;
    private static final Long UPDATED_PROCEDIMENTO_ID = 1L;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.MaterialSearchRepositoryMockConfiguration
     */
    @Autowired
    private MaterialSearchRepository mockMaterialSearchRepository;

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

    private MockMvc restMaterialMockMvc;

    private Material material;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createEntity(EntityManager em) {
        Material material = new Material()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO)
                .ativo(DEFAULT_ATIVO)
                .regimento(DEFAULT_REGIMENTO)
                .observacao(DEFAULT_OBSERVACAO)
                .unidadeId(DEFAULT_UNIDADE_ID)
                .procedimentoId(DEFAULT_PROCEDIMENTO_ID);
        // Add required entity
        UnidadeMedida unidadeMedida;
        if (TestUtil.findAll(em, UnidadeMedida.class).isEmpty()) {
            unidadeMedida = UnidadeMedidaResourceIT.createEntity(em);
            em.persist(unidadeMedida);
            em.flush();
        } else {
            unidadeMedida = TestUtil.findAll(em, UnidadeMedida.class).get(0);
        }
        material.setUnidadeMedida(unidadeMedida);
        // Add required entity
        GrupoMaterial grupoMaterial;
        if (TestUtil.findAll(em, GrupoMaterial.class).isEmpty()) {
            grupoMaterial = GrupoMaterialResourceIT.createEntity(em);
            em.persist(grupoMaterial);
            em.flush();
        } else {
            grupoMaterial = TestUtil.findAll(em, GrupoMaterial.class).get(0);
        }
        material.setGrupo(grupoMaterial);
        // Add required entity
        Almoxarifado almoxarifado;
        if (TestUtil.findAll(em, Almoxarifado.class).isEmpty()) {
            almoxarifado = AlmoxarifadoResourceIT.createEntity(em);
            em.persist(almoxarifado);
            em.flush();
        } else {
            almoxarifado = TestUtil.findAll(em, Almoxarifado.class).get(0);
        }
        material.setLocalEstoque(almoxarifado);
        return material;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createUpdatedEntity(EntityManager em) {
        Material material = new Material()
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO)
                .ativo(UPDATED_ATIVO)
                .regimento(UPDATED_REGIMENTO)
                .observacao(UPDATED_OBSERVACAO)
                .unidadeId(UPDATED_UNIDADE_ID)
                .procedimentoId(UPDATED_PROCEDIMENTO_ID);
        // Add required entity
        UnidadeMedida unidadeMedida;
        if (TestUtil.findAll(em, UnidadeMedida.class).isEmpty()) {
            unidadeMedida = UnidadeMedidaResourceIT.createUpdatedEntity(em);
            em.persist(unidadeMedida);
            em.flush();
        } else {
            unidadeMedida = TestUtil.findAll(em, UnidadeMedida.class).get(0);
        }
        material.setUnidadeMedida(unidadeMedida);
        // Add required entity
        GrupoMaterial grupoMaterial;
        if (TestUtil.findAll(em, GrupoMaterial.class).isEmpty()) {
            grupoMaterial = GrupoMaterialResourceIT.createUpdatedEntity(em);
            em.persist(grupoMaterial);
            em.flush();
        } else {
            grupoMaterial = TestUtil.findAll(em, GrupoMaterial.class).get(0);
        }
        material.setGrupo(grupoMaterial);
        // Add required entity
        Almoxarifado almoxarifado;
        if (TestUtil.findAll(em, Almoxarifado.class).isEmpty()) {
            almoxarifado = AlmoxarifadoResourceIT.createUpdatedEntity(em);
            em.persist(almoxarifado);
            em.flush();
        } else {
            almoxarifado = TestUtil.findAll(em, Almoxarifado.class).get(0);
        }
        material.setLocalEstoque(almoxarifado);
        return material;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterialResource materialResource = new MaterialResource(materialService);
        this.restMaterialMockMvc = MockMvcBuilders.standaloneSetup(materialResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
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
        assertThat(testMaterial.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMaterial.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testMaterial.getRegimento()).isEqualTo(DEFAULT_REGIMENTO);
        assertThat(testMaterial.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testMaterial.getUnidadeId()).isEqualTo(DEFAULT_UNIDADE_ID);
        assertThat(testMaterial.getProcedimentoId()).isEqualTo(DEFAULT_PROCEDIMENTO_ID);

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
    public void getAllMaterials() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList
        restMaterialMockMvc.perform(get("/api/materials?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
                .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].regimento").value(hasItem(DEFAULT_REGIMENTO)))
                .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
                .andExpect(jsonPath("$.[*].unidadeId").value(hasItem(DEFAULT_UNIDADE_ID.intValue())))
                .andExpect(jsonPath("$.[*].procedimentoId").value(hasItem(DEFAULT_PROCEDIMENTO_ID.intValue())));
    }

    @Test
    @Transactional
    public void getMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get the material
        restMaterialMockMvc.perform(get("/api/materials/{id}", material.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(material.getId().intValue()))
                .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
                .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
                .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
                .andExpect(jsonPath("$.regimento").value(DEFAULT_REGIMENTO))
                .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
                .andExpect(jsonPath("$.unidadeId").value(DEFAULT_UNIDADE_ID.intValue()))
                .andExpect(jsonPath("$.procedimentoId").value(DEFAULT_PROCEDIMENTO_ID.intValue()));
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
                .descricao(UPDATED_DESCRICAO)
                .ativo(UPDATED_ATIVO)
                .regimento(UPDATED_REGIMENTO)
                .observacao(UPDATED_OBSERVACAO)
                .unidadeId(UPDATED_UNIDADE_ID)
                .procedimentoId(UPDATED_PROCEDIMENTO_ID);
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
        assertThat(testMaterial.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMaterial.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testMaterial.getRegimento()).isEqualTo(UPDATED_REGIMENTO);
        assertThat(testMaterial.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testMaterial.getUnidadeId()).isEqualTo(UPDATED_UNIDADE_ID);
        assertThat(testMaterial.getProcedimentoId()).isEqualTo(UPDATED_PROCEDIMENTO_ID);

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
        // Initialize the database
        materialRepository.saveAndFlush(material);
        when(mockMaterialSearchRepository.search(queryStringQuery("id:" + material.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(material), PageRequest.of(0, 1), 1));
        // Search the material
        restMaterialMockMvc.perform(get("/api/_search/materials?query=id:" + material.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
                .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].regimento").value(hasItem(DEFAULT_REGIMENTO)))
                .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
                .andExpect(jsonPath("$.[*].unidadeId").value(hasItem(DEFAULT_UNIDADE_ID.intValue())))
                .andExpect(jsonPath("$.[*].procedimentoId").value(hasItem(DEFAULT_PROCEDIMENTO_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Material.class);
        Material material1 = new Material();
        material1.setId(1L);
        Material material2 = new Material();
        material2.setId(material1.getId());
        assertThat(material1).isEqualTo(material2);
        material2.setId(2L);
        assertThat(material1).isNotEqualTo(material2);
        material1.setId(null);
        assertThat(material1).isNotEqualTo(material2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialDTO.class);
        MaterialDTO materialDTO1 = new MaterialDTO();
        materialDTO1.setId(1L);
        MaterialDTO materialDTO2 = new MaterialDTO();
        assertThat(materialDTO1).isNotEqualTo(materialDTO2);
        materialDTO2.setId(materialDTO1.getId());
        assertThat(materialDTO1).isEqualTo(materialDTO2);
        materialDTO2.setId(2L);
        assertThat(materialDTO1).isNotEqualTo(materialDTO2);
        materialDTO1.setId(null);
        assertThat(materialDTO1).isNotEqualTo(materialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(materialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(materialMapper.fromId(null)).isNull();
    }
}
