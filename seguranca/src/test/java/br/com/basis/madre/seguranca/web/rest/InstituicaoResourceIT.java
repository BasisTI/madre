package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Instituicao;
import br.com.basis.madre.seguranca.repository.InstituicaoRepository;
import br.com.basis.madre.seguranca.repository.search.InstituicaoSearchRepository;
import br.com.basis.madre.seguranca.service.InstituicaoService;
import br.com.basis.madre.seguranca.service.dto.InstituicaoDTO;
import br.com.basis.madre.seguranca.service.mapper.InstituicaoMapper;

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
 * Integration tests for the {@link InstituicaoResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstituicaoResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INTERNO = false;
    private static final Boolean UPDATED_INTERNO = true;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private InstituicaoMapper instituicaoMapper;

    @Autowired
    private InstituicaoService instituicaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.InstituicaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private InstituicaoSearchRepository mockInstituicaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstituicaoMockMvc;

    private Instituicao instituicao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instituicao createEntity(EntityManager em) {
        Instituicao instituicao = new Instituicao()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO)
            .interno(DEFAULT_INTERNO);
        return instituicao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instituicao createUpdatedEntity(EntityManager em) {
        Instituicao instituicao = new Instituicao()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .interno(UPDATED_INTERNO);
        return instituicao;
    }

    @BeforeEach
    public void initTest() {
        instituicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstituicao() throws Exception {
        int databaseSizeBeforeCreate = instituicaoRepository.findAll().size();
        // Create the Instituicao
        InstituicaoDTO instituicaoDTO = instituicaoMapper.toDto(instituicao);
        restInstituicaoMockMvc.perform(post("/api/instituicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Instituicao in the database
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeCreate + 1);
        Instituicao testInstituicao = instituicaoList.get(instituicaoList.size() - 1);
        assertThat(testInstituicao.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testInstituicao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testInstituicao.isInterno()).isEqualTo(DEFAULT_INTERNO);

        // Validate the Instituicao in Elasticsearch
        verify(mockInstituicaoSearchRepository, times(1)).save(testInstituicao);
    }

    @Test
    @Transactional
    public void createInstituicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instituicaoRepository.findAll().size();

        // Create the Instituicao with an existing ID
        instituicao.setId(1L);
        InstituicaoDTO instituicaoDTO = instituicaoMapper.toDto(instituicao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituicaoMockMvc.perform(post("/api/instituicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Instituicao in the database
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Instituicao in Elasticsearch
        verify(mockInstituicaoSearchRepository, times(0)).save(instituicao);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoRepository.findAll().size();
        // set the field null
        instituicao.setDescricao(null);

        // Create the Instituicao, which fails.
        InstituicaoDTO instituicaoDTO = instituicaoMapper.toDto(instituicao);


        restInstituicaoMockMvc.perform(post("/api/instituicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoDTO)))
            .andExpect(status().isBadRequest());

        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoRepository.findAll().size();
        // set the field null
        instituicao.setInterno(null);

        // Create the Instituicao, which fails.
        InstituicaoDTO instituicaoDTO = instituicaoMapper.toDto(instituicao);


        restInstituicaoMockMvc.perform(post("/api/instituicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoDTO)))
            .andExpect(status().isBadRequest());

        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstituicaos() throws Exception {
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);

        // Get all the instituicaoList
        restInstituicaoMockMvc.perform(get("/api/instituicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instituicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].interno").value(hasItem(DEFAULT_INTERNO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInstituicao() throws Exception {
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);

        // Get the instituicao
        restInstituicaoMockMvc.perform(get("/api/instituicaos/{id}", instituicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instituicao.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.interno").value(DEFAULT_INTERNO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingInstituicao() throws Exception {
        // Get the instituicao
        restInstituicaoMockMvc.perform(get("/api/instituicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituicao() throws Exception {
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);

        int databaseSizeBeforeUpdate = instituicaoRepository.findAll().size();

        // Update the instituicao
        Instituicao updatedInstituicao = instituicaoRepository.findById(instituicao.getId()).get();
        // Disconnect from session so that the updates on updatedInstituicao are not directly saved in db
        em.detach(updatedInstituicao);
        updatedInstituicao
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .interno(UPDATED_INTERNO);
        InstituicaoDTO instituicaoDTO = instituicaoMapper.toDto(updatedInstituicao);

        restInstituicaoMockMvc.perform(put("/api/instituicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoDTO)))
            .andExpect(status().isOk());

        // Validate the Instituicao in the database
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeUpdate);
        Instituicao testInstituicao = instituicaoList.get(instituicaoList.size() - 1);
        assertThat(testInstituicao.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testInstituicao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testInstituicao.isInterno()).isEqualTo(UPDATED_INTERNO);

        // Validate the Instituicao in Elasticsearch
        verify(mockInstituicaoSearchRepository, times(1)).save(testInstituicao);
    }

    @Test
    @Transactional
    public void updateNonExistingInstituicao() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoRepository.findAll().size();

        // Create the Instituicao
        InstituicaoDTO instituicaoDTO = instituicaoMapper.toDto(instituicao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituicaoMockMvc.perform(put("/api/instituicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituicaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Instituicao in the database
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Instituicao in Elasticsearch
        verify(mockInstituicaoSearchRepository, times(0)).save(instituicao);
    }

    @Test
    @Transactional
    public void deleteInstituicao() throws Exception {
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);

        int databaseSizeBeforeDelete = instituicaoRepository.findAll().size();

        // Delete the instituicao
        restInstituicaoMockMvc.perform(delete("/api/instituicaos/{id}", instituicao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Instituicao in Elasticsearch
        verify(mockInstituicaoSearchRepository, times(1)).deleteById(instituicao.getId());
    }

    @Test
    @Transactional
    public void searchInstituicao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);
        when(mockInstituicaoSearchRepository.search(queryStringQuery("id:" + instituicao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(instituicao), PageRequest.of(0, 1), 1));

        // Search the instituicao
        restInstituicaoMockMvc.perform(get("/api/_search/instituicaos?query=id:" + instituicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instituicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].interno").value(hasItem(DEFAULT_INTERNO.booleanValue())));
    }
}
