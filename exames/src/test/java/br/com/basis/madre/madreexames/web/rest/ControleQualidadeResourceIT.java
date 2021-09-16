package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.ControleQualidade;
import br.com.basis.madre.madreexames.repository.ControleQualidadeRepository;
import br.com.basis.madre.madreexames.repository.search.ControleQualidadeSearchRepository;
import br.com.basis.madre.madreexames.service.ControleQualidadeService;
import br.com.basis.madre.madreexames.service.dto.ControleQualidadeDTO;
import br.com.basis.madre.madreexames.service.mapper.ControleQualidadeMapper;

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

import br.com.basis.madre.madreexames.domain.enumeration.ConvenioPlano;
/**
 * Integration tests for the {@link ControleQualidadeResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ControleQualidadeResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_CONVENIO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_CONVENIO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_PLANO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_PLANO = "BBBBBBBBBB";

    private static final ConvenioPlano DEFAULT_CONVENIO_PLANO = ConvenioPlano.SUS_INTERNACAO;
    private static final ConvenioPlano UPDATED_CONVENIO_PLANO = ConvenioPlano.SUS_PLANO_AMBULATORIO;

    @Autowired
    private ControleQualidadeRepository controleQualidadeRepository;

    @Autowired
    private ControleQualidadeMapper controleQualidadeMapper;

    @Autowired
    private ControleQualidadeService controleQualidadeService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.ControleQualidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ControleQualidadeSearchRepository mockControleQualidadeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControleQualidadeMockMvc;

    private ControleQualidade controleQualidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleQualidade createEntity(EntityManager em) {
        ControleQualidade controleQualidade = new ControleQualidade()
            .codigo(DEFAULT_CODIGO)
            .material(DEFAULT_MATERIAL)
            .codigoConvenio(DEFAULT_CODIGO_CONVENIO)
            .codigoPlano(DEFAULT_CODIGO_PLANO)
            .convenioPlano(DEFAULT_CONVENIO_PLANO);
        return controleQualidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleQualidade createUpdatedEntity(EntityManager em) {
        ControleQualidade controleQualidade = new ControleQualidade()
            .codigo(UPDATED_CODIGO)
            .material(UPDATED_MATERIAL)
            .codigoConvenio(UPDATED_CODIGO_CONVENIO)
            .codigoPlano(UPDATED_CODIGO_PLANO)
            .convenioPlano(UPDATED_CONVENIO_PLANO);
        return controleQualidade;
    }

    @BeforeEach
    public void initTest() {
        controleQualidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createControleQualidade() throws Exception {
        int databaseSizeBeforeCreate = controleQualidadeRepository.findAll().size();
        // Create the ControleQualidade
        ControleQualidadeDTO controleQualidadeDTO = controleQualidadeMapper.toDto(controleQualidade);
        restControleQualidadeMockMvc.perform(post("/api/controle-qualidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controleQualidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the ControleQualidade in the database
        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeCreate + 1);
        ControleQualidade testControleQualidade = controleQualidadeList.get(controleQualidadeList.size() - 1);
        assertThat(testControleQualidade.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testControleQualidade.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testControleQualidade.getCodigoConvenio()).isEqualTo(DEFAULT_CODIGO_CONVENIO);
        assertThat(testControleQualidade.getCodigoPlano()).isEqualTo(DEFAULT_CODIGO_PLANO);
        assertThat(testControleQualidade.getConvenioPlano()).isEqualTo(DEFAULT_CONVENIO_PLANO);

        // Validate the ControleQualidade in Elasticsearch
        verify(mockControleQualidadeSearchRepository, times(1)).save(testControleQualidade);
    }

    @Test
    @Transactional
    public void createControleQualidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controleQualidadeRepository.findAll().size();

        // Create the ControleQualidade with an existing ID
        controleQualidade.setId(1L);
        ControleQualidadeDTO controleQualidadeDTO = controleQualidadeMapper.toDto(controleQualidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControleQualidadeMockMvc.perform(post("/api/controle-qualidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controleQualidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ControleQualidade in the database
        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ControleQualidade in Elasticsearch
        verify(mockControleQualidadeSearchRepository, times(0)).save(controleQualidade);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = controleQualidadeRepository.findAll().size();
        // set the field null
        controleQualidade.setCodigo(null);

        // Create the ControleQualidade, which fails.
        ControleQualidadeDTO controleQualidadeDTO = controleQualidadeMapper.toDto(controleQualidade);


        restControleQualidadeMockMvc.perform(post("/api/controle-qualidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controleQualidadeDTO)))
            .andExpect(status().isBadRequest());

        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaterialIsRequired() throws Exception {
        int databaseSizeBeforeTest = controleQualidadeRepository.findAll().size();
        // set the field null
        controleQualidade.setMaterial(null);

        // Create the ControleQualidade, which fails.
        ControleQualidadeDTO controleQualidadeDTO = controleQualidadeMapper.toDto(controleQualidade);


        restControleQualidadeMockMvc.perform(post("/api/controle-qualidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controleQualidadeDTO)))
            .andExpect(status().isBadRequest());

        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoConvenioIsRequired() throws Exception {
        int databaseSizeBeforeTest = controleQualidadeRepository.findAll().size();
        // set the field null
        controleQualidade.setCodigoConvenio(null);

        // Create the ControleQualidade, which fails.
        ControleQualidadeDTO controleQualidadeDTO = controleQualidadeMapper.toDto(controleQualidade);


        restControleQualidadeMockMvc.perform(post("/api/controle-qualidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controleQualidadeDTO)))
            .andExpect(status().isBadRequest());

        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoPlanoIsRequired() throws Exception {
        int databaseSizeBeforeTest = controleQualidadeRepository.findAll().size();
        // set the field null
        controleQualidade.setCodigoPlano(null);

        // Create the ControleQualidade, which fails.
        ControleQualidadeDTO controleQualidadeDTO = controleQualidadeMapper.toDto(controleQualidade);


        restControleQualidadeMockMvc.perform(post("/api/controle-qualidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controleQualidadeDTO)))
            .andExpect(status().isBadRequest());

        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllControleQualidades() throws Exception {
        // Initialize the database
        controleQualidadeRepository.saveAndFlush(controleQualidade);

        // Get all the controleQualidadeList
        restControleQualidadeMockMvc.perform(get("/api/controle-qualidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleQualidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].codigoConvenio").value(hasItem(DEFAULT_CODIGO_CONVENIO)))
            .andExpect(jsonPath("$.[*].codigoPlano").value(hasItem(DEFAULT_CODIGO_PLANO)))
            .andExpect(jsonPath("$.[*].convenioPlano").value(hasItem(DEFAULT_CONVENIO_PLANO.toString())));
    }

    @Test
    @Transactional
    public void getControleQualidade() throws Exception {
        // Initialize the database
        controleQualidadeRepository.saveAndFlush(controleQualidade);

        // Get the controleQualidade
        restControleQualidadeMockMvc.perform(get("/api/controle-qualidades/{id}", controleQualidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controleQualidade.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL))
            .andExpect(jsonPath("$.codigoConvenio").value(DEFAULT_CODIGO_CONVENIO))
            .andExpect(jsonPath("$.codigoPlano").value(DEFAULT_CODIGO_PLANO))
            .andExpect(jsonPath("$.convenioPlano").value(DEFAULT_CONVENIO_PLANO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingControleQualidade() throws Exception {
        // Get the controleQualidade
        restControleQualidadeMockMvc.perform(get("/api/controle-qualidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControleQualidade() throws Exception {
        // Initialize the database
        controleQualidadeRepository.saveAndFlush(controleQualidade);

        int databaseSizeBeforeUpdate = controleQualidadeRepository.findAll().size();

        // Update the controleQualidade
        ControleQualidade updatedControleQualidade = controleQualidadeRepository.findById(controleQualidade.getId()).get();
        // Disconnect from session so that the updates on updatedControleQualidade are not directly saved in db
        em.detach(updatedControleQualidade);
        updatedControleQualidade
            .codigo(UPDATED_CODIGO)
            .material(UPDATED_MATERIAL)
            .codigoConvenio(UPDATED_CODIGO_CONVENIO)
            .codigoPlano(UPDATED_CODIGO_PLANO)
            .convenioPlano(UPDATED_CONVENIO_PLANO);
        ControleQualidadeDTO controleQualidadeDTO = controleQualidadeMapper.toDto(updatedControleQualidade);

        restControleQualidadeMockMvc.perform(put("/api/controle-qualidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controleQualidadeDTO)))
            .andExpect(status().isOk());

        // Validate the ControleQualidade in the database
        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeUpdate);
        ControleQualidade testControleQualidade = controleQualidadeList.get(controleQualidadeList.size() - 1);
        assertThat(testControleQualidade.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testControleQualidade.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testControleQualidade.getCodigoConvenio()).isEqualTo(UPDATED_CODIGO_CONVENIO);
        assertThat(testControleQualidade.getCodigoPlano()).isEqualTo(UPDATED_CODIGO_PLANO);
        assertThat(testControleQualidade.getConvenioPlano()).isEqualTo(UPDATED_CONVENIO_PLANO);

        // Validate the ControleQualidade in Elasticsearch
        verify(mockControleQualidadeSearchRepository, times(1)).save(testControleQualidade);
    }

    @Test
    @Transactional
    public void updateNonExistingControleQualidade() throws Exception {
        int databaseSizeBeforeUpdate = controleQualidadeRepository.findAll().size();

        // Create the ControleQualidade
        ControleQualidadeDTO controleQualidadeDTO = controleQualidadeMapper.toDto(controleQualidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleQualidadeMockMvc.perform(put("/api/controle-qualidades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controleQualidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ControleQualidade in the database
        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ControleQualidade in Elasticsearch
        verify(mockControleQualidadeSearchRepository, times(0)).save(controleQualidade);
    }

    @Test
    @Transactional
    public void deleteControleQualidade() throws Exception {
        // Initialize the database
        controleQualidadeRepository.saveAndFlush(controleQualidade);

        int databaseSizeBeforeDelete = controleQualidadeRepository.findAll().size();

        // Delete the controleQualidade
        restControleQualidadeMockMvc.perform(delete("/api/controle-qualidades/{id}", controleQualidade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControleQualidade> controleQualidadeList = controleQualidadeRepository.findAll();
        assertThat(controleQualidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ControleQualidade in Elasticsearch
        verify(mockControleQualidadeSearchRepository, times(1)).deleteById(controleQualidade.getId());
    }

    @Test
    @Transactional
    public void searchControleQualidade() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        controleQualidadeRepository.saveAndFlush(controleQualidade);
        when(mockControleQualidadeSearchRepository.search(queryStringQuery("id:" + controleQualidade.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(controleQualidade), PageRequest.of(0, 1), 1));

        // Search the controleQualidade
        restControleQualidadeMockMvc.perform(get("/api/_search/controle-qualidades?query=id:" + controleQualidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleQualidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].codigoConvenio").value(hasItem(DEFAULT_CODIGO_CONVENIO)))
            .andExpect(jsonPath("$.[*].codigoPlano").value(hasItem(DEFAULT_CODIGO_PLANO)))
            .andExpect(jsonPath("$.[*].convenioPlano").value(hasItem(DEFAULT_CONVENIO_PLANO.toString())));
    }
}
