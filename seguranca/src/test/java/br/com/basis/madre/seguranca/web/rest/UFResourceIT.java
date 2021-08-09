package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.UF;
import br.com.basis.madre.seguranca.repository.UFRepository;
import br.com.basis.madre.seguranca.repository.search.UFSearchRepository;
import br.com.basis.madre.seguranca.service.UFService;
import br.com.basis.madre.seguranca.service.dto.UFDTO;
import br.com.basis.madre.seguranca.service.mapper.UFMapper;

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
 * Integration tests for the {@link UFResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UFResourceIT {

    private static final String DEFAULT_UNIDADE_FEDERATIVA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_FEDERATIVA = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    @Autowired
    private UFRepository uFRepository;

    @Autowired
    private UFMapper uFMapper;

    @Autowired
    private UFService uFService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.UFSearchRepositoryMockConfiguration
     */
    @Autowired
    private UFSearchRepository mockUFSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUFMockMvc;

    private UF uF;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UF createEntity(EntityManager em) {
        UF uF = new UF()
            .unidadeFederativa(DEFAULT_UNIDADE_FEDERATIVA)
            .sigla(DEFAULT_SIGLA);
        return uF;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UF createUpdatedEntity(EntityManager em) {
        UF uF = new UF()
            .unidadeFederativa(UPDATED_UNIDADE_FEDERATIVA)
            .sigla(UPDATED_SIGLA);
        return uF;
    }

    @BeforeEach
    public void initTest() {
        uF = createEntity(em);
    }

    @Test
    @Transactional
    public void createUF() throws Exception {
        int databaseSizeBeforeCreate = uFRepository.findAll().size();
        // Create the UF
        UFDTO uFDTO = uFMapper.toDto(uF);
        restUFMockMvc.perform(post("/api/ufs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uFDTO)))
            .andExpect(status().isCreated());

        // Validate the UF in the database
        List<UF> uFList = uFRepository.findAll();
        assertThat(uFList).hasSize(databaseSizeBeforeCreate + 1);
        UF testUF = uFList.get(uFList.size() - 1);
        assertThat(testUF.getUnidadeFederativa()).isEqualTo(DEFAULT_UNIDADE_FEDERATIVA);
        assertThat(testUF.getSigla()).isEqualTo(DEFAULT_SIGLA);

        // Validate the UF in Elasticsearch
        verify(mockUFSearchRepository, times(1)).save(testUF);
    }

    @Test
    @Transactional
    public void createUFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uFRepository.findAll().size();

        // Create the UF with an existing ID
        uF.setId(1L);
        UFDTO uFDTO = uFMapper.toDto(uF);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUFMockMvc.perform(post("/api/ufs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uFDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UF in the database
        List<UF> uFList = uFRepository.findAll();
        assertThat(uFList).hasSize(databaseSizeBeforeCreate);

        // Validate the UF in Elasticsearch
        verify(mockUFSearchRepository, times(0)).save(uF);
    }


    @Test
    @Transactional
    public void getAllUFS() throws Exception {
        // Initialize the database
        uFRepository.saveAndFlush(uF);

        // Get all the uFList
        restUFMockMvc.perform(get("/api/ufs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uF.getId().intValue())))
            .andExpect(jsonPath("$.[*].unidadeFederativa").value(hasItem(DEFAULT_UNIDADE_FEDERATIVA)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getUF() throws Exception {
        // Initialize the database
        uFRepository.saveAndFlush(uF);

        // Get the uF
        restUFMockMvc.perform(get("/api/ufs/{id}", uF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uF.getId().intValue()))
            .andExpect(jsonPath("$.unidadeFederativa").value(DEFAULT_UNIDADE_FEDERATIVA))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }
    @Test
    @Transactional
    public void getNonExistingUF() throws Exception {
        // Get the uF
        restUFMockMvc.perform(get("/api/ufs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUF() throws Exception {
        // Initialize the database
        uFRepository.saveAndFlush(uF);

        int databaseSizeBeforeUpdate = uFRepository.findAll().size();

        // Update the uF
        UF updatedUF = uFRepository.findById(uF.getId()).get();
        // Disconnect from session so that the updates on updatedUF are not directly saved in db
        em.detach(updatedUF);
        updatedUF
            .unidadeFederativa(UPDATED_UNIDADE_FEDERATIVA)
            .sigla(UPDATED_SIGLA);
        UFDTO uFDTO = uFMapper.toDto(updatedUF);

        restUFMockMvc.perform(put("/api/ufs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uFDTO)))
            .andExpect(status().isOk());

        // Validate the UF in the database
        List<UF> uFList = uFRepository.findAll();
        assertThat(uFList).hasSize(databaseSizeBeforeUpdate);
        UF testUF = uFList.get(uFList.size() - 1);
        assertThat(testUF.getUnidadeFederativa()).isEqualTo(UPDATED_UNIDADE_FEDERATIVA);
        assertThat(testUF.getSigla()).isEqualTo(UPDATED_SIGLA);

        // Validate the UF in Elasticsearch
        verify(mockUFSearchRepository, times(1)).save(testUF);
    }

    @Test
    @Transactional
    public void updateNonExistingUF() throws Exception {
        int databaseSizeBeforeUpdate = uFRepository.findAll().size();

        // Create the UF
        UFDTO uFDTO = uFMapper.toDto(uF);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUFMockMvc.perform(put("/api/ufs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uFDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UF in the database
        List<UF> uFList = uFRepository.findAll();
        assertThat(uFList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UF in Elasticsearch
        verify(mockUFSearchRepository, times(0)).save(uF);
    }

    @Test
    @Transactional
    public void deleteUF() throws Exception {
        // Initialize the database
        uFRepository.saveAndFlush(uF);

        int databaseSizeBeforeDelete = uFRepository.findAll().size();

        // Delete the uF
        restUFMockMvc.perform(delete("/api/ufs/{id}", uF.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UF> uFList = uFRepository.findAll();
        assertThat(uFList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UF in Elasticsearch
        verify(mockUFSearchRepository, times(1)).deleteById(uF.getId());
    }

    @Test
    @Transactional
    public void searchUF() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        uFRepository.saveAndFlush(uF);
        when(mockUFSearchRepository.search(queryStringQuery("id:" + uF.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(uF), PageRequest.of(0, 1), 1));

        // Search the uF
        restUFMockMvc.perform(get("/api/_search/ufs?query=id:" + uF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uF.getId().intValue())))
            .andExpect(jsonPath("$.[*].unidadeFederativa").value(hasItem(DEFAULT_UNIDADE_FEDERATIVA)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
}
