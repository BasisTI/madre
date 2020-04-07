package br.com.basis.madre.web.rest;

import br.com.basis.madre.SnffaturaApp;
import br.com.basis.madre.domain.Responsavel;
import br.com.basis.madre.repository.ResponsavelRepository;
import br.com.basis.madre.repository.search.ResponsavelSearchRepository;
import br.com.basis.madre.service.ResponsavelService;
import br.com.basis.madre.service.dto.ResponsavelDTO;
import br.com.basis.madre.service.mapper.ResponsavelMapper;

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
 * Integration tests for the {@link ResponsavelResource} REST controller.
 */
@SpringBootTest(classes = SnffaturaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResponsavelResourceIT {

    private static final String DEFAULT_NOME_DO_RESPONSAVEL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_RESPONSAVEL = "BBBBBBBBBB";

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private ResponsavelMapper responsavelMapper;

    @Autowired
    private ResponsavelService responsavelService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.ResponsavelSearchRepositoryMockConfiguration
     */
    @Autowired
    private ResponsavelSearchRepository mockResponsavelSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsavelMockMvc;

    private Responsavel responsavel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsavel createEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .nomeDoResponsavel(DEFAULT_NOME_DO_RESPONSAVEL);
        return responsavel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsavel createUpdatedEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .nomeDoResponsavel(UPDATED_NOME_DO_RESPONSAVEL);
        return responsavel;
    }

    @BeforeEach
    public void initTest() {
        responsavel = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsavel() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);
        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsavelDTO)))
            .andExpect(status().isCreated());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate + 1);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getNomeDoResponsavel()).isEqualTo(DEFAULT_NOME_DO_RESPONSAVEL);

        // Validate the Responsavel in Elasticsearch
        verify(mockResponsavelSearchRepository, times(1)).save(testResponsavel);
    }

    @Test
    @Transactional
    public void createResponsavelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // Create the Responsavel with an existing ID
        responsavel.setId(1L);
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsavelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate);

        // Validate the Responsavel in Elasticsearch
        verify(mockResponsavelSearchRepository, times(0)).save(responsavel);
    }


    @Test
    @Transactional
    public void getAllResponsavels() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList
        restResponsavelMockMvc.perform(get("/api/responsavels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoResponsavel").value(hasItem(DEFAULT_NOME_DO_RESPONSAVEL)));
    }
    
    @Test
    @Transactional
    public void getResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get the responsavel
        restResponsavelMockMvc.perform(get("/api/responsavels/{id}", responsavel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsavel.getId().intValue()))
            .andExpect(jsonPath("$.nomeDoResponsavel").value(DEFAULT_NOME_DO_RESPONSAVEL));
    }

    @Test
    @Transactional
    public void getNonExistingResponsavel() throws Exception {
        // Get the responsavel
        restResponsavelMockMvc.perform(get("/api/responsavels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Update the responsavel
        Responsavel updatedResponsavel = responsavelRepository.findById(responsavel.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavel are not directly saved in db
        em.detach(updatedResponsavel);
        updatedResponsavel
            .nomeDoResponsavel(UPDATED_NOME_DO_RESPONSAVEL);
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(updatedResponsavel);

        restResponsavelMockMvc.perform(put("/api/responsavels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsavelDTO)))
            .andExpect(status().isOk());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getNomeDoResponsavel()).isEqualTo(UPDATED_NOME_DO_RESPONSAVEL);

        // Validate the Responsavel in Elasticsearch
        verify(mockResponsavelSearchRepository, times(1)).save(testResponsavel);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Create the Responsavel
        ResponsavelDTO responsavelDTO = responsavelMapper.toDto(responsavel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelMockMvc.perform(put("/api/responsavels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsavelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Responsavel in Elasticsearch
        verify(mockResponsavelSearchRepository, times(0)).save(responsavel);
    }

    @Test
    @Transactional
    public void deleteResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        int databaseSizeBeforeDelete = responsavelRepository.findAll().size();

        // Delete the responsavel
        restResponsavelMockMvc.perform(delete("/api/responsavels/{id}", responsavel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Responsavel in Elasticsearch
        verify(mockResponsavelSearchRepository, times(1)).deleteById(responsavel.getId());
    }

    @Test
    @Transactional
    public void searchResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);
        when(mockResponsavelSearchRepository.search(queryStringQuery("id:" + responsavel.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(responsavel), PageRequest.of(0, 1), 1));
        // Search the responsavel
        restResponsavelMockMvc.perform(get("/api/_search/responsavels?query=id:" + responsavel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoResponsavel").value(hasItem(DEFAULT_NOME_DO_RESPONSAVEL)));
    }
}
