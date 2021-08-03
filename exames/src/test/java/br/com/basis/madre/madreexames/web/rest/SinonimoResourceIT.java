package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.Sinonimo;
import br.com.basis.madre.madreexames.repository.SinonimoRepository;
import br.com.basis.madre.madreexames.repository.search.SinonimoSearchRepository;
import br.com.basis.madre.madreexames.service.SinonimoService;
import br.com.basis.madre.madreexames.service.dto.SinonimoDTO;
import br.com.basis.madre.madreexames.service.mapper.SinonimoMapper;

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
 * Integration tests for the {@link SinonimoResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SinonimoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SITUACAO = false;
    private static final Boolean UPDATED_SITUACAO = true;

    @Autowired
    private SinonimoRepository sinonimoRepository;

    @Autowired
    private SinonimoMapper sinonimoMapper;

    @Autowired
    private SinonimoService sinonimoService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.SinonimoSearchRepositoryMockConfiguration
     */
    @Autowired
    private SinonimoSearchRepository mockSinonimoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSinonimoMockMvc;

    private Sinonimo sinonimo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sinonimo createEntity(EntityManager em) {
        Sinonimo sinonimo = new Sinonimo()
            .nome(DEFAULT_NOME)
            .situacao(DEFAULT_SITUACAO);
        return sinonimo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sinonimo createUpdatedEntity(EntityManager em) {
        Sinonimo sinonimo = new Sinonimo()
            .nome(UPDATED_NOME)
            .situacao(UPDATED_SITUACAO);
        return sinonimo;
    }

    @BeforeEach
    public void initTest() {
        sinonimo = createEntity(em);
    }

    @Test
    @Transactional
    public void createSinonimo() throws Exception {
        int databaseSizeBeforeCreate = sinonimoRepository.findAll().size();
        // Create the Sinonimo
        SinonimoDTO sinonimoDTO = sinonimoMapper.toDto(sinonimo);
        restSinonimoMockMvc.perform(post("/api/sinonimos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sinonimoDTO)))
            .andExpect(status().isCreated());

        // Validate the Sinonimo in the database
        List<Sinonimo> sinonimoList = sinonimoRepository.findAll();
        assertThat(sinonimoList).hasSize(databaseSizeBeforeCreate + 1);
        Sinonimo testSinonimo = sinonimoList.get(sinonimoList.size() - 1);
        assertThat(testSinonimo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSinonimo.isSituacao()).isEqualTo(DEFAULT_SITUACAO);

        // Validate the Sinonimo in Elasticsearch
        verify(mockSinonimoSearchRepository, times(1)).save(testSinonimo);
    }

    @Test
    @Transactional
    public void createSinonimoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sinonimoRepository.findAll().size();

        // Create the Sinonimo with an existing ID
        sinonimo.setId(1L);
        SinonimoDTO sinonimoDTO = sinonimoMapper.toDto(sinonimo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSinonimoMockMvc.perform(post("/api/sinonimos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sinonimoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sinonimo in the database
        List<Sinonimo> sinonimoList = sinonimoRepository.findAll();
        assertThat(sinonimoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sinonimo in Elasticsearch
        verify(mockSinonimoSearchRepository, times(0)).save(sinonimo);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sinonimoRepository.findAll().size();
        // set the field null
        sinonimo.setNome(null);

        // Create the Sinonimo, which fails.
        SinonimoDTO sinonimoDTO = sinonimoMapper.toDto(sinonimo);


        restSinonimoMockMvc.perform(post("/api/sinonimos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sinonimoDTO)))
            .andExpect(status().isBadRequest());

        List<Sinonimo> sinonimoList = sinonimoRepository.findAll();
        assertThat(sinonimoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSinonimos() throws Exception {
        // Initialize the database
        sinonimoRepository.saveAndFlush(sinonimo);

        // Get all the sinonimoList
        restSinonimoMockMvc.perform(get("/api/sinonimos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinonimo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSinonimo() throws Exception {
        // Initialize the database
        sinonimoRepository.saveAndFlush(sinonimo);

        // Get the sinonimo
        restSinonimoMockMvc.perform(get("/api/sinonimos/{id}", sinonimo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sinonimo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSinonimo() throws Exception {
        // Get the sinonimo
        restSinonimoMockMvc.perform(get("/api/sinonimos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSinonimo() throws Exception {
        // Initialize the database
        sinonimoRepository.saveAndFlush(sinonimo);

        int databaseSizeBeforeUpdate = sinonimoRepository.findAll().size();

        // Update the sinonimo
        Sinonimo updatedSinonimo = sinonimoRepository.findById(sinonimo.getId()).get();
        // Disconnect from session so that the updates on updatedSinonimo are not directly saved in db
        em.detach(updatedSinonimo);
        updatedSinonimo
            .nome(UPDATED_NOME)
            .situacao(UPDATED_SITUACAO);
        SinonimoDTO sinonimoDTO = sinonimoMapper.toDto(updatedSinonimo);

        restSinonimoMockMvc.perform(put("/api/sinonimos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sinonimoDTO)))
            .andExpect(status().isOk());

        // Validate the Sinonimo in the database
        List<Sinonimo> sinonimoList = sinonimoRepository.findAll();
        assertThat(sinonimoList).hasSize(databaseSizeBeforeUpdate);
        Sinonimo testSinonimo = sinonimoList.get(sinonimoList.size() - 1);
        assertThat(testSinonimo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSinonimo.isSituacao()).isEqualTo(UPDATED_SITUACAO);

        // Validate the Sinonimo in Elasticsearch
        verify(mockSinonimoSearchRepository, times(1)).save(testSinonimo);
    }

    @Test
    @Transactional
    public void updateNonExistingSinonimo() throws Exception {
        int databaseSizeBeforeUpdate = sinonimoRepository.findAll().size();

        // Create the Sinonimo
        SinonimoDTO sinonimoDTO = sinonimoMapper.toDto(sinonimo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSinonimoMockMvc.perform(put("/api/sinonimos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sinonimoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sinonimo in the database
        List<Sinonimo> sinonimoList = sinonimoRepository.findAll();
        assertThat(sinonimoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sinonimo in Elasticsearch
        verify(mockSinonimoSearchRepository, times(0)).save(sinonimo);
    }

    @Test
    @Transactional
    public void deleteSinonimo() throws Exception {
        // Initialize the database
        sinonimoRepository.saveAndFlush(sinonimo);

        int databaseSizeBeforeDelete = sinonimoRepository.findAll().size();

        // Delete the sinonimo
        restSinonimoMockMvc.perform(delete("/api/sinonimos/{id}", sinonimo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sinonimo> sinonimoList = sinonimoRepository.findAll();
        assertThat(sinonimoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sinonimo in Elasticsearch
        verify(mockSinonimoSearchRepository, times(1)).deleteById(sinonimo.getId());
    }

    @Test
    @Transactional
    public void searchSinonimo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        sinonimoRepository.saveAndFlush(sinonimo);
        when(mockSinonimoSearchRepository.search(queryStringQuery("id:" + sinonimo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sinonimo), PageRequest.of(0, 1), 1));

        // Search the sinonimo
        restSinonimoMockMvc.perform(get("/api/_search/sinonimos?query=id:" + sinonimo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinonimo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())));
    }
}
