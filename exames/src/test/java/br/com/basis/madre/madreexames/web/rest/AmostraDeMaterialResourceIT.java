package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.AmostraDeMaterial;
import br.com.basis.madre.madreexames.repository.AmostraDeMaterialRepository;
import br.com.basis.madre.madreexames.repository.search.AmostraDeMaterialSearchRepository;
import br.com.basis.madre.madreexames.service.AmostraDeMaterialService;
import br.com.basis.madre.madreexames.service.dto.AmostraDeMaterialDTO;
import br.com.basis.madre.madreexames.service.mapper.AmostraDeMaterialMapper;

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

import br.com.basis.madre.madreexames.domain.enumeration.OrigemTipoAmostra;
import br.com.basis.madre.madreexames.domain.enumeration.UnidadeDeMedida;
import br.com.basis.madre.madreexames.domain.enumeration.Responsavel;
/**
 * Integration tests for the {@link AmostraDeMaterialResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AmostraDeMaterialResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final OrigemTipoAmostra DEFAULT_ORIGEM = OrigemTipoAmostra.NASCIMENTO;
    private static final OrigemTipoAmostra UPDATED_ORIGEM = OrigemTipoAmostra.AMBULATORIO;

    private static final Integer DEFAULT_NUMERO_DE_AMOSTRAS = 1;
    private static final Integer UPDATED_NUMERO_DE_AMOSTRAS = 2;

    private static final Integer DEFAULT_VOLUME_DA_AMOSTRA = 1;
    private static final Integer UPDATED_VOLUME_DA_AMOSTRA = 2;

    private static final UnidadeDeMedida DEFAULT_UNIDADE_DE_MEDIDA = UnidadeDeMedida.MILILITRO;
    private static final UnidadeDeMedida UPDATED_UNIDADE_DE_MEDIDA = UnidadeDeMedida.FRASCO;

    private static final Responsavel DEFAULT_RESPONSAVEL = Responsavel.COLETADOR;
    private static final Responsavel UPDATED_RESPONSAVEL = Responsavel.SOLICITANTE;

    private static final Boolean DEFAULT_CONGELADO = false;
    private static final Boolean UPDATED_CONGELADO = true;

    private static final Integer DEFAULT_UNIDADE_FUNCIONAL_ID = 1;
    private static final Integer UPDATED_UNIDADE_FUNCIONAL_ID = 2;

    @Autowired
    private AmostraDeMaterialRepository amostraDeMaterialRepository;

    @Autowired
    private AmostraDeMaterialMapper amostraDeMaterialMapper;

    @Autowired
    private AmostraDeMaterialService amostraDeMaterialService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.AmostraDeMaterialSearchRepositoryMockConfiguration
     */
    @Autowired
    private AmostraDeMaterialSearchRepository mockAmostraDeMaterialSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmostraDeMaterialMockMvc;

    private AmostraDeMaterial amostraDeMaterial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmostraDeMaterial createEntity(EntityManager em) {
        AmostraDeMaterial amostraDeMaterial = new AmostraDeMaterial()
            .nome(DEFAULT_NOME)
            .origem(DEFAULT_ORIGEM)
            .numeroDeAmostras(DEFAULT_NUMERO_DE_AMOSTRAS)
            .volumeDaAmostra(DEFAULT_VOLUME_DA_AMOSTRA)
            .unidadeDeMedida(DEFAULT_UNIDADE_DE_MEDIDA)
            .responsavel(DEFAULT_RESPONSAVEL)
            .congelado(DEFAULT_CONGELADO)
            .unidadeFuncionalId(DEFAULT_UNIDADE_FUNCIONAL_ID);
        return amostraDeMaterial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmostraDeMaterial createUpdatedEntity(EntityManager em) {
        AmostraDeMaterial amostraDeMaterial = new AmostraDeMaterial()
            .nome(UPDATED_NOME)
            .origem(UPDATED_ORIGEM)
            .numeroDeAmostras(UPDATED_NUMERO_DE_AMOSTRAS)
            .volumeDaAmostra(UPDATED_VOLUME_DA_AMOSTRA)
            .unidadeDeMedida(UPDATED_UNIDADE_DE_MEDIDA)
            .responsavel(UPDATED_RESPONSAVEL)
            .congelado(UPDATED_CONGELADO)
            .unidadeFuncionalId(UPDATED_UNIDADE_FUNCIONAL_ID);
        return amostraDeMaterial;
    }

    @BeforeEach
    public void initTest() {
        amostraDeMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmostraDeMaterial() throws Exception {
        int databaseSizeBeforeCreate = amostraDeMaterialRepository.findAll().size();
        // Create the AmostraDeMaterial
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);
        restAmostraDeMaterialMockMvc.perform(post("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isCreated());

        // Validate the AmostraDeMaterial in the database
        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        AmostraDeMaterial testAmostraDeMaterial = amostraDeMaterialList.get(amostraDeMaterialList.size() - 1);
        assertThat(testAmostraDeMaterial.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAmostraDeMaterial.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
        assertThat(testAmostraDeMaterial.getNumeroDeAmostras()).isEqualTo(DEFAULT_NUMERO_DE_AMOSTRAS);
        assertThat(testAmostraDeMaterial.getVolumeDaAmostra()).isEqualTo(DEFAULT_VOLUME_DA_AMOSTRA);
        assertThat(testAmostraDeMaterial.getUnidadeDeMedida()).isEqualTo(DEFAULT_UNIDADE_DE_MEDIDA);
        assertThat(testAmostraDeMaterial.getResponsavel()).isEqualTo(DEFAULT_RESPONSAVEL);
        assertThat(testAmostraDeMaterial.isCongelado()).isEqualTo(DEFAULT_CONGELADO);
        assertThat(testAmostraDeMaterial.getUnidadeFuncionalId()).isEqualTo(DEFAULT_UNIDADE_FUNCIONAL_ID);

        // Validate the AmostraDeMaterial in Elasticsearch
        verify(mockAmostraDeMaterialSearchRepository, times(1)).save(testAmostraDeMaterial);
    }

    @Test
    @Transactional
    public void createAmostraDeMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amostraDeMaterialRepository.findAll().size();

        // Create the AmostraDeMaterial with an existing ID
        amostraDeMaterial.setId(1L);
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmostraDeMaterialMockMvc.perform(post("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmostraDeMaterial in the database
        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeCreate);

        // Validate the AmostraDeMaterial in Elasticsearch
        verify(mockAmostraDeMaterialSearchRepository, times(0)).save(amostraDeMaterial);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = amostraDeMaterialRepository.findAll().size();
        // set the field null
        amostraDeMaterial.setNome(null);

        // Create the AmostraDeMaterial, which fails.
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);


        restAmostraDeMaterialMockMvc.perform(post("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrigemIsRequired() throws Exception {
        int databaseSizeBeforeTest = amostraDeMaterialRepository.findAll().size();
        // set the field null
        amostraDeMaterial.setOrigem(null);

        // Create the AmostraDeMaterial, which fails.
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);


        restAmostraDeMaterialMockMvc.perform(post("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroDeAmostrasIsRequired() throws Exception {
        int databaseSizeBeforeTest = amostraDeMaterialRepository.findAll().size();
        // set the field null
        amostraDeMaterial.setNumeroDeAmostras(null);

        // Create the AmostraDeMaterial, which fails.
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);


        restAmostraDeMaterialMockMvc.perform(post("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadeDeMedidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = amostraDeMaterialRepository.findAll().size();
        // set the field null
        amostraDeMaterial.setUnidadeDeMedida(null);

        // Create the AmostraDeMaterial, which fails.
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);


        restAmostraDeMaterialMockMvc.perform(post("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = amostraDeMaterialRepository.findAll().size();
        // set the field null
        amostraDeMaterial.setResponsavel(null);

        // Create the AmostraDeMaterial, which fails.
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);


        restAmostraDeMaterialMockMvc.perform(post("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCongeladoIsRequired() throws Exception {
        int databaseSizeBeforeTest = amostraDeMaterialRepository.findAll().size();
        // set the field null
        amostraDeMaterial.setCongelado(null);

        // Create the AmostraDeMaterial, which fails.
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);


        restAmostraDeMaterialMockMvc.perform(post("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmostraDeMaterials() throws Exception {
        // Initialize the database
        amostraDeMaterialRepository.saveAndFlush(amostraDeMaterial);

        // Get all the amostraDeMaterialList
        restAmostraDeMaterialMockMvc.perform(get("/api/amostra-de-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amostraDeMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM.toString())))
            .andExpect(jsonPath("$.[*].numeroDeAmostras").value(hasItem(DEFAULT_NUMERO_DE_AMOSTRAS)))
            .andExpect(jsonPath("$.[*].volumeDaAmostra").value(hasItem(DEFAULT_VOLUME_DA_AMOSTRA)))
            .andExpect(jsonPath("$.[*].unidadeDeMedida").value(hasItem(DEFAULT_UNIDADE_DE_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL.toString())))
            .andExpect(jsonPath("$.[*].congelado").value(hasItem(DEFAULT_CONGELADO.booleanValue())))
            .andExpect(jsonPath("$.[*].unidadeFuncionalId").value(hasItem(DEFAULT_UNIDADE_FUNCIONAL_ID)));
    }
    
    @Test
    @Transactional
    public void getAmostraDeMaterial() throws Exception {
        // Initialize the database
        amostraDeMaterialRepository.saveAndFlush(amostraDeMaterial);

        // Get the amostraDeMaterial
        restAmostraDeMaterialMockMvc.perform(get("/api/amostra-de-materials/{id}", amostraDeMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(amostraDeMaterial.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.origem").value(DEFAULT_ORIGEM.toString()))
            .andExpect(jsonPath("$.numeroDeAmostras").value(DEFAULT_NUMERO_DE_AMOSTRAS))
            .andExpect(jsonPath("$.volumeDaAmostra").value(DEFAULT_VOLUME_DA_AMOSTRA))
            .andExpect(jsonPath("$.unidadeDeMedida").value(DEFAULT_UNIDADE_DE_MEDIDA.toString()))
            .andExpect(jsonPath("$.responsavel").value(DEFAULT_RESPONSAVEL.toString()))
            .andExpect(jsonPath("$.congelado").value(DEFAULT_CONGELADO.booleanValue()))
            .andExpect(jsonPath("$.unidadeFuncionalId").value(DEFAULT_UNIDADE_FUNCIONAL_ID));
    }
    @Test
    @Transactional
    public void getNonExistingAmostraDeMaterial() throws Exception {
        // Get the amostraDeMaterial
        restAmostraDeMaterialMockMvc.perform(get("/api/amostra-de-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmostraDeMaterial() throws Exception {
        // Initialize the database
        amostraDeMaterialRepository.saveAndFlush(amostraDeMaterial);

        int databaseSizeBeforeUpdate = amostraDeMaterialRepository.findAll().size();

        // Update the amostraDeMaterial
        AmostraDeMaterial updatedAmostraDeMaterial = amostraDeMaterialRepository.findById(amostraDeMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedAmostraDeMaterial are not directly saved in db
        em.detach(updatedAmostraDeMaterial);
        updatedAmostraDeMaterial
            .nome(UPDATED_NOME)
            .origem(UPDATED_ORIGEM)
            .numeroDeAmostras(UPDATED_NUMERO_DE_AMOSTRAS)
            .volumeDaAmostra(UPDATED_VOLUME_DA_AMOSTRA)
            .unidadeDeMedida(UPDATED_UNIDADE_DE_MEDIDA)
            .responsavel(UPDATED_RESPONSAVEL)
            .congelado(UPDATED_CONGELADO)
            .unidadeFuncionalId(UPDATED_UNIDADE_FUNCIONAL_ID);
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(updatedAmostraDeMaterial);

        restAmostraDeMaterialMockMvc.perform(put("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isOk());

        // Validate the AmostraDeMaterial in the database
        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeUpdate);
        AmostraDeMaterial testAmostraDeMaterial = amostraDeMaterialList.get(amostraDeMaterialList.size() - 1);
        assertThat(testAmostraDeMaterial.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAmostraDeMaterial.getOrigem()).isEqualTo(UPDATED_ORIGEM);
        assertThat(testAmostraDeMaterial.getNumeroDeAmostras()).isEqualTo(UPDATED_NUMERO_DE_AMOSTRAS);
        assertThat(testAmostraDeMaterial.getVolumeDaAmostra()).isEqualTo(UPDATED_VOLUME_DA_AMOSTRA);
        assertThat(testAmostraDeMaterial.getUnidadeDeMedida()).isEqualTo(UPDATED_UNIDADE_DE_MEDIDA);
        assertThat(testAmostraDeMaterial.getResponsavel()).isEqualTo(UPDATED_RESPONSAVEL);
        assertThat(testAmostraDeMaterial.isCongelado()).isEqualTo(UPDATED_CONGELADO);
        assertThat(testAmostraDeMaterial.getUnidadeFuncionalId()).isEqualTo(UPDATED_UNIDADE_FUNCIONAL_ID);

        // Validate the AmostraDeMaterial in Elasticsearch
        verify(mockAmostraDeMaterialSearchRepository, times(1)).save(testAmostraDeMaterial);
    }

    @Test
    @Transactional
    public void updateNonExistingAmostraDeMaterial() throws Exception {
        int databaseSizeBeforeUpdate = amostraDeMaterialRepository.findAll().size();

        // Create the AmostraDeMaterial
        AmostraDeMaterialDTO amostraDeMaterialDTO = amostraDeMaterialMapper.toDto(amostraDeMaterial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmostraDeMaterialMockMvc.perform(put("/api/amostra-de-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amostraDeMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmostraDeMaterial in the database
        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AmostraDeMaterial in Elasticsearch
        verify(mockAmostraDeMaterialSearchRepository, times(0)).save(amostraDeMaterial);
    }

    @Test
    @Transactional
    public void deleteAmostraDeMaterial() throws Exception {
        // Initialize the database
        amostraDeMaterialRepository.saveAndFlush(amostraDeMaterial);

        int databaseSizeBeforeDelete = amostraDeMaterialRepository.findAll().size();

        // Delete the amostraDeMaterial
        restAmostraDeMaterialMockMvc.perform(delete("/api/amostra-de-materials/{id}", amostraDeMaterial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AmostraDeMaterial> amostraDeMaterialList = amostraDeMaterialRepository.findAll();
        assertThat(amostraDeMaterialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AmostraDeMaterial in Elasticsearch
        verify(mockAmostraDeMaterialSearchRepository, times(1)).deleteById(amostraDeMaterial.getId());
    }

    @Test
    @Transactional
    public void searchAmostraDeMaterial() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        amostraDeMaterialRepository.saveAndFlush(amostraDeMaterial);
        when(mockAmostraDeMaterialSearchRepository.search(queryStringQuery("id:" + amostraDeMaterial.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amostraDeMaterial), PageRequest.of(0, 1), 1));

        // Search the amostraDeMaterial
        restAmostraDeMaterialMockMvc.perform(get("/api/_search/amostra-de-materials?query=id:" + amostraDeMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amostraDeMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM.toString())))
            .andExpect(jsonPath("$.[*].numeroDeAmostras").value(hasItem(DEFAULT_NUMERO_DE_AMOSTRAS)))
            .andExpect(jsonPath("$.[*].volumeDaAmostra").value(hasItem(DEFAULT_VOLUME_DA_AMOSTRA)))
            .andExpect(jsonPath("$.[*].unidadeDeMedida").value(hasItem(DEFAULT_UNIDADE_DE_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL.toString())))
            .andExpect(jsonPath("$.[*].congelado").value(hasItem(DEFAULT_CONGELADO.booleanValue())))
            .andExpect(jsonPath("$.[*].unidadeFuncionalId").value(hasItem(DEFAULT_UNIDADE_FUNCIONAL_ID)));
    }
}
