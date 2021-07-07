package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.ProjetoDePesquisa;
import br.com.basis.madre.madreexames.repository.ProjetoDePesquisaRepository;
import br.com.basis.madre.madreexames.repository.search.ProjetoDePesquisaSearchRepository;
import br.com.basis.madre.madreexames.service.ProjetoDePesquisaService;
import br.com.basis.madre.madreexames.service.dto.ProjetoDePesquisaDTO;
import br.com.basis.madre.madreexames.service.mapper.ProjetoDePesquisaMapper;

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
 * Integration tests for the {@link ProjetoDePesquisaResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjetoDePesquisaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ProjetoDePesquisaRepository projetoDePesquisaRepository;

    @Autowired
    private ProjetoDePesquisaMapper projetoDePesquisaMapper;

    @Autowired
    private ProjetoDePesquisaService projetoDePesquisaService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.ProjetoDePesquisaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProjetoDePesquisaSearchRepository mockProjetoDePesquisaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjetoDePesquisaMockMvc;

    private ProjetoDePesquisa projetoDePesquisa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjetoDePesquisa createEntity(EntityManager em) {
        ProjetoDePesquisa projetoDePesquisa = new ProjetoDePesquisa()
            .nome(DEFAULT_NOME);
        return projetoDePesquisa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjetoDePesquisa createUpdatedEntity(EntityManager em) {
        ProjetoDePesquisa projetoDePesquisa = new ProjetoDePesquisa()
            .nome(UPDATED_NOME);
        return projetoDePesquisa;
    }

    @BeforeEach
    public void initTest() {
        projetoDePesquisa = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjetoDePesquisa() throws Exception {
        int databaseSizeBeforeCreate = projetoDePesquisaRepository.findAll().size();
        // Create the ProjetoDePesquisa
        ProjetoDePesquisaDTO projetoDePesquisaDTO = projetoDePesquisaMapper.toDto(projetoDePesquisa);
        restProjetoDePesquisaMockMvc.perform(post("/api/projeto-de-pesquisas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projetoDePesquisaDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjetoDePesquisa in the database
        List<ProjetoDePesquisa> projetoDePesquisaList = projetoDePesquisaRepository.findAll();
        assertThat(projetoDePesquisaList).hasSize(databaseSizeBeforeCreate + 1);
        ProjetoDePesquisa testProjetoDePesquisa = projetoDePesquisaList.get(projetoDePesquisaList.size() - 1);
        assertThat(testProjetoDePesquisa.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the ProjetoDePesquisa in Elasticsearch
        verify(mockProjetoDePesquisaSearchRepository, times(1)).save(testProjetoDePesquisa);
    }

    @Test
    @Transactional
    public void createProjetoDePesquisaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projetoDePesquisaRepository.findAll().size();

        // Create the ProjetoDePesquisa with an existing ID
        projetoDePesquisa.setId(1L);
        ProjetoDePesquisaDTO projetoDePesquisaDTO = projetoDePesquisaMapper.toDto(projetoDePesquisa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetoDePesquisaMockMvc.perform(post("/api/projeto-de-pesquisas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projetoDePesquisaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjetoDePesquisa in the database
        List<ProjetoDePesquisa> projetoDePesquisaList = projetoDePesquisaRepository.findAll();
        assertThat(projetoDePesquisaList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProjetoDePesquisa in Elasticsearch
        verify(mockProjetoDePesquisaSearchRepository, times(0)).save(projetoDePesquisa);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetoDePesquisaRepository.findAll().size();
        // set the field null
        projetoDePesquisa.setNome(null);

        // Create the ProjetoDePesquisa, which fails.
        ProjetoDePesquisaDTO projetoDePesquisaDTO = projetoDePesquisaMapper.toDto(projetoDePesquisa);


        restProjetoDePesquisaMockMvc.perform(post("/api/projeto-de-pesquisas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projetoDePesquisaDTO)))
            .andExpect(status().isBadRequest());

        List<ProjetoDePesquisa> projetoDePesquisaList = projetoDePesquisaRepository.findAll();
        assertThat(projetoDePesquisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjetoDePesquisas() throws Exception {
        // Initialize the database
        projetoDePesquisaRepository.saveAndFlush(projetoDePesquisa);

        // Get all the projetoDePesquisaList
        restProjetoDePesquisaMockMvc.perform(get("/api/projeto-de-pesquisas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projetoDePesquisa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getProjetoDePesquisa() throws Exception {
        // Initialize the database
        projetoDePesquisaRepository.saveAndFlush(projetoDePesquisa);

        // Get the projetoDePesquisa
        restProjetoDePesquisaMockMvc.perform(get("/api/projeto-de-pesquisas/{id}", projetoDePesquisa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projetoDePesquisa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }
    @Test
    @Transactional
    public void getNonExistingProjetoDePesquisa() throws Exception {
        // Get the projetoDePesquisa
        restProjetoDePesquisaMockMvc.perform(get("/api/projeto-de-pesquisas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjetoDePesquisa() throws Exception {
        // Initialize the database
        projetoDePesquisaRepository.saveAndFlush(projetoDePesquisa);

        int databaseSizeBeforeUpdate = projetoDePesquisaRepository.findAll().size();

        // Update the projetoDePesquisa
        ProjetoDePesquisa updatedProjetoDePesquisa = projetoDePesquisaRepository.findById(projetoDePesquisa.getId()).get();
        // Disconnect from session so that the updates on updatedProjetoDePesquisa are not directly saved in db
        em.detach(updatedProjetoDePesquisa);
        updatedProjetoDePesquisa
            .nome(UPDATED_NOME);
        ProjetoDePesquisaDTO projetoDePesquisaDTO = projetoDePesquisaMapper.toDto(updatedProjetoDePesquisa);

        restProjetoDePesquisaMockMvc.perform(put("/api/projeto-de-pesquisas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projetoDePesquisaDTO)))
            .andExpect(status().isOk());

        // Validate the ProjetoDePesquisa in the database
        List<ProjetoDePesquisa> projetoDePesquisaList = projetoDePesquisaRepository.findAll();
        assertThat(projetoDePesquisaList).hasSize(databaseSizeBeforeUpdate);
        ProjetoDePesquisa testProjetoDePesquisa = projetoDePesquisaList.get(projetoDePesquisaList.size() - 1);
        assertThat(testProjetoDePesquisa.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the ProjetoDePesquisa in Elasticsearch
        verify(mockProjetoDePesquisaSearchRepository, times(1)).save(testProjetoDePesquisa);
    }

    @Test
    @Transactional
    public void updateNonExistingProjetoDePesquisa() throws Exception {
        int databaseSizeBeforeUpdate = projetoDePesquisaRepository.findAll().size();

        // Create the ProjetoDePesquisa
        ProjetoDePesquisaDTO projetoDePesquisaDTO = projetoDePesquisaMapper.toDto(projetoDePesquisa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjetoDePesquisaMockMvc.perform(put("/api/projeto-de-pesquisas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projetoDePesquisaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjetoDePesquisa in the database
        List<ProjetoDePesquisa> projetoDePesquisaList = projetoDePesquisaRepository.findAll();
        assertThat(projetoDePesquisaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProjetoDePesquisa in Elasticsearch
        verify(mockProjetoDePesquisaSearchRepository, times(0)).save(projetoDePesquisa);
    }

    @Test
    @Transactional
    public void deleteProjetoDePesquisa() throws Exception {
        // Initialize the database
        projetoDePesquisaRepository.saveAndFlush(projetoDePesquisa);

        int databaseSizeBeforeDelete = projetoDePesquisaRepository.findAll().size();

        // Delete the projetoDePesquisa
        restProjetoDePesquisaMockMvc.perform(delete("/api/projeto-de-pesquisas/{id}", projetoDePesquisa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjetoDePesquisa> projetoDePesquisaList = projetoDePesquisaRepository.findAll();
        assertThat(projetoDePesquisaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProjetoDePesquisa in Elasticsearch
        verify(mockProjetoDePesquisaSearchRepository, times(1)).deleteById(projetoDePesquisa.getId());
    }

    @Test
    @Transactional
    public void searchProjetoDePesquisa() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        projetoDePesquisaRepository.saveAndFlush(projetoDePesquisa);
        when(mockProjetoDePesquisaSearchRepository.search(queryStringQuery("id:" + projetoDePesquisa.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(projetoDePesquisa), PageRequest.of(0, 1), 1));

        // Search the projetoDePesquisa
        restProjetoDePesquisaMockMvc.perform(get("/api/_search/projeto-de-pesquisas?query=id:" + projetoDePesquisa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projetoDePesquisa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
}
