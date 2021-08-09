package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.OrgaoEmissor;
import br.com.basis.madre.seguranca.repository.OrgaoEmissorRepository;
import br.com.basis.madre.seguranca.repository.search.OrgaoEmissorSearchRepository;
import br.com.basis.madre.seguranca.service.OrgaoEmissorService;
import br.com.basis.madre.seguranca.service.dto.OrgaoEmissorDTO;
import br.com.basis.madre.seguranca.service.mapper.OrgaoEmissorMapper;

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
 * Integration tests for the {@link OrgaoEmissorResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrgaoEmissorResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private OrgaoEmissorRepository orgaoEmissorRepository;

    @Autowired
    private OrgaoEmissorMapper orgaoEmissorMapper;

    @Autowired
    private OrgaoEmissorService orgaoEmissorService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.OrgaoEmissorSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrgaoEmissorSearchRepository mockOrgaoEmissorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgaoEmissorMockMvc;

    private OrgaoEmissor orgaoEmissor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgaoEmissor createEntity(EntityManager em) {
        OrgaoEmissor orgaoEmissor = new OrgaoEmissor()
            .valor(DEFAULT_VALOR);
        return orgaoEmissor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgaoEmissor createUpdatedEntity(EntityManager em) {
        OrgaoEmissor orgaoEmissor = new OrgaoEmissor()
            .valor(UPDATED_VALOR);
        return orgaoEmissor;
    }

    @BeforeEach
    public void initTest() {
        orgaoEmissor = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrgaoEmissor() throws Exception {
        int databaseSizeBeforeCreate = orgaoEmissorRepository.findAll().size();
        // Create the OrgaoEmissor
        OrgaoEmissorDTO orgaoEmissorDTO = orgaoEmissorMapper.toDto(orgaoEmissor);
        restOrgaoEmissorMockMvc.perform(post("/api/orgao-emissors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orgaoEmissorDTO)))
            .andExpect(status().isCreated());

        // Validate the OrgaoEmissor in the database
        List<OrgaoEmissor> orgaoEmissorList = orgaoEmissorRepository.findAll();
        assertThat(orgaoEmissorList).hasSize(databaseSizeBeforeCreate + 1);
        OrgaoEmissor testOrgaoEmissor = orgaoEmissorList.get(orgaoEmissorList.size() - 1);
        assertThat(testOrgaoEmissor.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the OrgaoEmissor in Elasticsearch
        verify(mockOrgaoEmissorSearchRepository, times(1)).save(testOrgaoEmissor);
    }

    @Test
    @Transactional
    public void createOrgaoEmissorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orgaoEmissorRepository.findAll().size();

        // Create the OrgaoEmissor with an existing ID
        orgaoEmissor.setId(1L);
        OrgaoEmissorDTO orgaoEmissorDTO = orgaoEmissorMapper.toDto(orgaoEmissor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgaoEmissorMockMvc.perform(post("/api/orgao-emissors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orgaoEmissorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrgaoEmissor in the database
        List<OrgaoEmissor> orgaoEmissorList = orgaoEmissorRepository.findAll();
        assertThat(orgaoEmissorList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrgaoEmissor in Elasticsearch
        verify(mockOrgaoEmissorSearchRepository, times(0)).save(orgaoEmissor);
    }


    @Test
    @Transactional
    public void getAllOrgaoEmissors() throws Exception {
        // Initialize the database
        orgaoEmissorRepository.saveAndFlush(orgaoEmissor);

        // Get all the orgaoEmissorList
        restOrgaoEmissorMockMvc.perform(get("/api/orgao-emissors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgaoEmissor.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getOrgaoEmissor() throws Exception {
        // Initialize the database
        orgaoEmissorRepository.saveAndFlush(orgaoEmissor);

        // Get the orgaoEmissor
        restOrgaoEmissorMockMvc.perform(get("/api/orgao-emissors/{id}", orgaoEmissor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orgaoEmissor.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }
    @Test
    @Transactional
    public void getNonExistingOrgaoEmissor() throws Exception {
        // Get the orgaoEmissor
        restOrgaoEmissorMockMvc.perform(get("/api/orgao-emissors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrgaoEmissor() throws Exception {
        // Initialize the database
        orgaoEmissorRepository.saveAndFlush(orgaoEmissor);

        int databaseSizeBeforeUpdate = orgaoEmissorRepository.findAll().size();

        // Update the orgaoEmissor
        OrgaoEmissor updatedOrgaoEmissor = orgaoEmissorRepository.findById(orgaoEmissor.getId()).get();
        // Disconnect from session so that the updates on updatedOrgaoEmissor are not directly saved in db
        em.detach(updatedOrgaoEmissor);
        updatedOrgaoEmissor
            .valor(UPDATED_VALOR);
        OrgaoEmissorDTO orgaoEmissorDTO = orgaoEmissorMapper.toDto(updatedOrgaoEmissor);

        restOrgaoEmissorMockMvc.perform(put("/api/orgao-emissors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orgaoEmissorDTO)))
            .andExpect(status().isOk());

        // Validate the OrgaoEmissor in the database
        List<OrgaoEmissor> orgaoEmissorList = orgaoEmissorRepository.findAll();
        assertThat(orgaoEmissorList).hasSize(databaseSizeBeforeUpdate);
        OrgaoEmissor testOrgaoEmissor = orgaoEmissorList.get(orgaoEmissorList.size() - 1);
        assertThat(testOrgaoEmissor.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the OrgaoEmissor in Elasticsearch
        verify(mockOrgaoEmissorSearchRepository, times(1)).save(testOrgaoEmissor);
    }

    @Test
    @Transactional
    public void updateNonExistingOrgaoEmissor() throws Exception {
        int databaseSizeBeforeUpdate = orgaoEmissorRepository.findAll().size();

        // Create the OrgaoEmissor
        OrgaoEmissorDTO orgaoEmissorDTO = orgaoEmissorMapper.toDto(orgaoEmissor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgaoEmissorMockMvc.perform(put("/api/orgao-emissors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orgaoEmissorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrgaoEmissor in the database
        List<OrgaoEmissor> orgaoEmissorList = orgaoEmissorRepository.findAll();
        assertThat(orgaoEmissorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrgaoEmissor in Elasticsearch
        verify(mockOrgaoEmissorSearchRepository, times(0)).save(orgaoEmissor);
    }

    @Test
    @Transactional
    public void deleteOrgaoEmissor() throws Exception {
        // Initialize the database
        orgaoEmissorRepository.saveAndFlush(orgaoEmissor);

        int databaseSizeBeforeDelete = orgaoEmissorRepository.findAll().size();

        // Delete the orgaoEmissor
        restOrgaoEmissorMockMvc.perform(delete("/api/orgao-emissors/{id}", orgaoEmissor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrgaoEmissor> orgaoEmissorList = orgaoEmissorRepository.findAll();
        assertThat(orgaoEmissorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrgaoEmissor in Elasticsearch
        verify(mockOrgaoEmissorSearchRepository, times(1)).deleteById(orgaoEmissor.getId());
    }

    @Test
    @Transactional
    public void searchOrgaoEmissor() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        orgaoEmissorRepository.saveAndFlush(orgaoEmissor);
        when(mockOrgaoEmissorSearchRepository.search(queryStringQuery("id:" + orgaoEmissor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(orgaoEmissor), PageRequest.of(0, 1), 1));

        // Search the orgaoEmissor
        restOrgaoEmissorMockMvc.perform(get("/api/_search/orgao-emissors?query=id:" + orgaoEmissor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgaoEmissor.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
