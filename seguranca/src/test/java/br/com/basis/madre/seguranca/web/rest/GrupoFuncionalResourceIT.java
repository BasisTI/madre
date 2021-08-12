package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.GrupoFuncional;
import br.com.basis.madre.seguranca.repository.GrupoFuncionalRepository;
import br.com.basis.madre.seguranca.repository.search.GrupoFuncionalSearchRepository;
import br.com.basis.madre.seguranca.service.GrupoFuncionalService;
import br.com.basis.madre.seguranca.service.dto.GrupoFuncionalDTO;
import br.com.basis.madre.seguranca.service.mapper.GrupoFuncionalMapper;

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
 * Integration tests for the {@link GrupoFuncionalResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GrupoFuncionalResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private GrupoFuncionalRepository grupoFuncionalRepository;

    @Autowired
    private GrupoFuncionalMapper grupoFuncionalMapper;

    @Autowired
    private GrupoFuncionalService grupoFuncionalService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.GrupoFuncionalSearchRepositoryMockConfiguration
     */
    @Autowired
    private GrupoFuncionalSearchRepository mockGrupoFuncionalSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoFuncionalMockMvc;

    private GrupoFuncional grupoFuncional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoFuncional createEntity(EntityManager em) {
        GrupoFuncional grupoFuncional = new GrupoFuncional()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO);
        return grupoFuncional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoFuncional createUpdatedEntity(EntityManager em) {
        GrupoFuncional grupoFuncional = new GrupoFuncional()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO);
        return grupoFuncional;
    }

    @BeforeEach
    public void initTest() {
        grupoFuncional = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupoFuncional() throws Exception {
        int databaseSizeBeforeCreate = grupoFuncionalRepository.findAll().size();
        // Create the GrupoFuncional
        GrupoFuncionalDTO grupoFuncionalDTO = grupoFuncionalMapper.toDto(grupoFuncional);
        restGrupoFuncionalMockMvc.perform(post("/api/grupo-funcionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoFuncionalDTO)))
            .andExpect(status().isCreated());

        // Validate the GrupoFuncional in the database
        List<GrupoFuncional> grupoFuncionalList = grupoFuncionalRepository.findAll();
        assertThat(grupoFuncionalList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoFuncional testGrupoFuncional = grupoFuncionalList.get(grupoFuncionalList.size() - 1);
        assertThat(testGrupoFuncional.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testGrupoFuncional.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the GrupoFuncional in Elasticsearch
        verify(mockGrupoFuncionalSearchRepository, times(1)).save(testGrupoFuncional);
    }

    @Test
    @Transactional
    public void createGrupoFuncionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupoFuncionalRepository.findAll().size();

        // Create the GrupoFuncional with an existing ID
        grupoFuncional.setId(1L);
        GrupoFuncionalDTO grupoFuncionalDTO = grupoFuncionalMapper.toDto(grupoFuncional);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoFuncionalMockMvc.perform(post("/api/grupo-funcionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoFuncionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoFuncional in the database
        List<GrupoFuncional> grupoFuncionalList = grupoFuncionalRepository.findAll();
        assertThat(grupoFuncionalList).hasSize(databaseSizeBeforeCreate);

        // Validate the GrupoFuncional in Elasticsearch
        verify(mockGrupoFuncionalSearchRepository, times(0)).save(grupoFuncional);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoFuncionalRepository.findAll().size();
        // set the field null
        grupoFuncional.setCodigo(null);

        // Create the GrupoFuncional, which fails.
        GrupoFuncionalDTO grupoFuncionalDTO = grupoFuncionalMapper.toDto(grupoFuncional);


        restGrupoFuncionalMockMvc.perform(post("/api/grupo-funcionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoFuncionalDTO)))
            .andExpect(status().isBadRequest());

        List<GrupoFuncional> grupoFuncionalList = grupoFuncionalRepository.findAll();
        assertThat(grupoFuncionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoFuncionalRepository.findAll().size();
        // set the field null
        grupoFuncional.setDescricao(null);

        // Create the GrupoFuncional, which fails.
        GrupoFuncionalDTO grupoFuncionalDTO = grupoFuncionalMapper.toDto(grupoFuncional);


        restGrupoFuncionalMockMvc.perform(post("/api/grupo-funcionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoFuncionalDTO)))
            .andExpect(status().isBadRequest());

        List<GrupoFuncional> grupoFuncionalList = grupoFuncionalRepository.findAll();
        assertThat(grupoFuncionalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrupoFuncionals() throws Exception {
        // Initialize the database
        grupoFuncionalRepository.saveAndFlush(grupoFuncional);

        // Get all the grupoFuncionalList
        restGrupoFuncionalMockMvc.perform(get("/api/grupo-funcionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoFuncional.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getGrupoFuncional() throws Exception {
        // Initialize the database
        grupoFuncionalRepository.saveAndFlush(grupoFuncional);

        // Get the grupoFuncional
        restGrupoFuncionalMockMvc.perform(get("/api/grupo-funcionals/{id}", grupoFuncional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoFuncional.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }
    @Test
    @Transactional
    public void getNonExistingGrupoFuncional() throws Exception {
        // Get the grupoFuncional
        restGrupoFuncionalMockMvc.perform(get("/api/grupo-funcionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupoFuncional() throws Exception {
        // Initialize the database
        grupoFuncionalRepository.saveAndFlush(grupoFuncional);

        int databaseSizeBeforeUpdate = grupoFuncionalRepository.findAll().size();

        // Update the grupoFuncional
        GrupoFuncional updatedGrupoFuncional = grupoFuncionalRepository.findById(grupoFuncional.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoFuncional are not directly saved in db
        em.detach(updatedGrupoFuncional);
        updatedGrupoFuncional
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO);
        GrupoFuncionalDTO grupoFuncionalDTO = grupoFuncionalMapper.toDto(updatedGrupoFuncional);

        restGrupoFuncionalMockMvc.perform(put("/api/grupo-funcionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoFuncionalDTO)))
            .andExpect(status().isOk());

        // Validate the GrupoFuncional in the database
        List<GrupoFuncional> grupoFuncionalList = grupoFuncionalRepository.findAll();
        assertThat(grupoFuncionalList).hasSize(databaseSizeBeforeUpdate);
        GrupoFuncional testGrupoFuncional = grupoFuncionalList.get(grupoFuncionalList.size() - 1);
        assertThat(testGrupoFuncional.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testGrupoFuncional.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the GrupoFuncional in Elasticsearch
        verify(mockGrupoFuncionalSearchRepository, times(1)).save(testGrupoFuncional);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupoFuncional() throws Exception {
        int databaseSizeBeforeUpdate = grupoFuncionalRepository.findAll().size();

        // Create the GrupoFuncional
        GrupoFuncionalDTO grupoFuncionalDTO = grupoFuncionalMapper.toDto(grupoFuncional);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoFuncionalMockMvc.perform(put("/api/grupo-funcionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoFuncionalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoFuncional in the database
        List<GrupoFuncional> grupoFuncionalList = grupoFuncionalRepository.findAll();
        assertThat(grupoFuncionalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GrupoFuncional in Elasticsearch
        verify(mockGrupoFuncionalSearchRepository, times(0)).save(grupoFuncional);
    }

    @Test
    @Transactional
    public void deleteGrupoFuncional() throws Exception {
        // Initialize the database
        grupoFuncionalRepository.saveAndFlush(grupoFuncional);

        int databaseSizeBeforeDelete = grupoFuncionalRepository.findAll().size();

        // Delete the grupoFuncional
        restGrupoFuncionalMockMvc.perform(delete("/api/grupo-funcionals/{id}", grupoFuncional.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoFuncional> grupoFuncionalList = grupoFuncionalRepository.findAll();
        assertThat(grupoFuncionalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GrupoFuncional in Elasticsearch
        verify(mockGrupoFuncionalSearchRepository, times(1)).deleteById(grupoFuncional.getId());
    }

    @Test
    @Transactional
    public void searchGrupoFuncional() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        grupoFuncionalRepository.saveAndFlush(grupoFuncional);
        when(mockGrupoFuncionalSearchRepository.search(queryStringQuery("id:" + grupoFuncional.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(grupoFuncional), PageRequest.of(0, 1), 1));

        // Search the grupoFuncional
        restGrupoFuncionalMockMvc.perform(get("/api/_search/grupo-funcionals?query=id:" + grupoFuncional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoFuncional.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
}
