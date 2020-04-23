package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.MotivoDoCadastro;
import br.com.basis.madre.repository.MotivoDoCadastroRepository;
import br.com.basis.madre.repository.search.MotivoDoCadastroSearchRepository;
import br.com.basis.madre.service.MotivoDoCadastroService;
import br.com.basis.madre.service.dto.MotivoDoCadastroDTO;
import br.com.basis.madre.service.mapper.MotivoDoCadastroMapper;

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
 * Integration tests for the {@link MotivoDoCadastroResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MotivoDoCadastroResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private MotivoDoCadastroRepository motivoDoCadastroRepository;

    @Autowired
    private MotivoDoCadastroMapper motivoDoCadastroMapper;

    @Autowired
    private MotivoDoCadastroService motivoDoCadastroService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.MotivoDoCadastroSearchRepositoryMockConfiguration
     */
    @Autowired
    private MotivoDoCadastroSearchRepository mockMotivoDoCadastroSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMotivoDoCadastroMockMvc;

    private MotivoDoCadastro motivoDoCadastro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivoDoCadastro createEntity(EntityManager em) {
        MotivoDoCadastro motivoDoCadastro = new MotivoDoCadastro()
            .valor(DEFAULT_VALOR);
        return motivoDoCadastro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivoDoCadastro createUpdatedEntity(EntityManager em) {
        MotivoDoCadastro motivoDoCadastro = new MotivoDoCadastro()
            .valor(UPDATED_VALOR);
        return motivoDoCadastro;
    }

    @BeforeEach
    public void initTest() {
        motivoDoCadastro = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotivoDoCadastro() throws Exception {
        int databaseSizeBeforeCreate = motivoDoCadastroRepository.findAll().size();

        // Create the MotivoDoCadastro
        MotivoDoCadastroDTO motivoDoCadastroDTO = motivoDoCadastroMapper.toDto(motivoDoCadastro);
        restMotivoDoCadastroMockMvc.perform(post("/api/motivo-do-cadastros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoCadastroDTO)))
            .andExpect(status().isCreated());

        // Validate the MotivoDoCadastro in the database
        List<MotivoDoCadastro> motivoDoCadastroList = motivoDoCadastroRepository.findAll();
        assertThat(motivoDoCadastroList).hasSize(databaseSizeBeforeCreate + 1);
        MotivoDoCadastro testMotivoDoCadastro = motivoDoCadastroList.get(motivoDoCadastroList.size() - 1);
        assertThat(testMotivoDoCadastro.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the MotivoDoCadastro in Elasticsearch
        verify(mockMotivoDoCadastroSearchRepository, times(1)).save(testMotivoDoCadastro);
    }

    @Test
    @Transactional
    public void createMotivoDoCadastroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motivoDoCadastroRepository.findAll().size();

        // Create the MotivoDoCadastro with an existing ID
        motivoDoCadastro.setId(1L);
        MotivoDoCadastroDTO motivoDoCadastroDTO = motivoDoCadastroMapper.toDto(motivoDoCadastro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotivoDoCadastroMockMvc.perform(post("/api/motivo-do-cadastros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoCadastroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MotivoDoCadastro in the database
        List<MotivoDoCadastro> motivoDoCadastroList = motivoDoCadastroRepository.findAll();
        assertThat(motivoDoCadastroList).hasSize(databaseSizeBeforeCreate);

        // Validate the MotivoDoCadastro in Elasticsearch
        verify(mockMotivoDoCadastroSearchRepository, times(0)).save(motivoDoCadastro);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = motivoDoCadastroRepository.findAll().size();
        // set the field null
        motivoDoCadastro.setValor(null);

        // Create the MotivoDoCadastro, which fails.
        MotivoDoCadastroDTO motivoDoCadastroDTO = motivoDoCadastroMapper.toDto(motivoDoCadastro);

        restMotivoDoCadastroMockMvc.perform(post("/api/motivo-do-cadastros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoCadastroDTO)))
            .andExpect(status().isBadRequest());

        List<MotivoDoCadastro> motivoDoCadastroList = motivoDoCadastroRepository.findAll();
        assertThat(motivoDoCadastroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMotivoDoCadastros() throws Exception {
        // Initialize the database
        motivoDoCadastroRepository.saveAndFlush(motivoDoCadastro);

        // Get all the motivoDoCadastroList
        restMotivoDoCadastroMockMvc.perform(get("/api/motivo-do-cadastros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivoDoCadastro.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getMotivoDoCadastro() throws Exception {
        // Initialize the database
        motivoDoCadastroRepository.saveAndFlush(motivoDoCadastro);

        // Get the motivoDoCadastro
        restMotivoDoCadastroMockMvc.perform(get("/api/motivo-do-cadastros/{id}", motivoDoCadastro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(motivoDoCadastro.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getNonExistingMotivoDoCadastro() throws Exception {
        // Get the motivoDoCadastro
        restMotivoDoCadastroMockMvc.perform(get("/api/motivo-do-cadastros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotivoDoCadastro() throws Exception {
        // Initialize the database
        motivoDoCadastroRepository.saveAndFlush(motivoDoCadastro);

        int databaseSizeBeforeUpdate = motivoDoCadastroRepository.findAll().size();

        // Update the motivoDoCadastro
        MotivoDoCadastro updatedMotivoDoCadastro = motivoDoCadastroRepository.findById(motivoDoCadastro.getId()).get();
        // Disconnect from session so that the updates on updatedMotivoDoCadastro are not directly saved in db
        em.detach(updatedMotivoDoCadastro);
        updatedMotivoDoCadastro
            .valor(UPDATED_VALOR);
        MotivoDoCadastroDTO motivoDoCadastroDTO = motivoDoCadastroMapper.toDto(updatedMotivoDoCadastro);

        restMotivoDoCadastroMockMvc.perform(put("/api/motivo-do-cadastros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoCadastroDTO)))
            .andExpect(status().isOk());

        // Validate the MotivoDoCadastro in the database
        List<MotivoDoCadastro> motivoDoCadastroList = motivoDoCadastroRepository.findAll();
        assertThat(motivoDoCadastroList).hasSize(databaseSizeBeforeUpdate);
        MotivoDoCadastro testMotivoDoCadastro = motivoDoCadastroList.get(motivoDoCadastroList.size() - 1);
        assertThat(testMotivoDoCadastro.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the MotivoDoCadastro in Elasticsearch
        verify(mockMotivoDoCadastroSearchRepository, times(1)).save(testMotivoDoCadastro);
    }

    @Test
    @Transactional
    public void updateNonExistingMotivoDoCadastro() throws Exception {
        int databaseSizeBeforeUpdate = motivoDoCadastroRepository.findAll().size();

        // Create the MotivoDoCadastro
        MotivoDoCadastroDTO motivoDoCadastroDTO = motivoDoCadastroMapper.toDto(motivoDoCadastro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivoDoCadastroMockMvc.perform(put("/api/motivo-do-cadastros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoCadastroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MotivoDoCadastro in the database
        List<MotivoDoCadastro> motivoDoCadastroList = motivoDoCadastroRepository.findAll();
        assertThat(motivoDoCadastroList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MotivoDoCadastro in Elasticsearch
        verify(mockMotivoDoCadastroSearchRepository, times(0)).save(motivoDoCadastro);
    }

    @Test
    @Transactional
    public void deleteMotivoDoCadastro() throws Exception {
        // Initialize the database
        motivoDoCadastroRepository.saveAndFlush(motivoDoCadastro);

        int databaseSizeBeforeDelete = motivoDoCadastroRepository.findAll().size();

        // Delete the motivoDoCadastro
        restMotivoDoCadastroMockMvc.perform(delete("/api/motivo-do-cadastros/{id}", motivoDoCadastro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MotivoDoCadastro> motivoDoCadastroList = motivoDoCadastroRepository.findAll();
        assertThat(motivoDoCadastroList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MotivoDoCadastro in Elasticsearch
        verify(mockMotivoDoCadastroSearchRepository, times(1)).deleteById(motivoDoCadastro.getId());
    }

    @Test
    @Transactional
    public void searchMotivoDoCadastro() throws Exception {
        // Initialize the database
        motivoDoCadastroRepository.saveAndFlush(motivoDoCadastro);
        when(mockMotivoDoCadastroSearchRepository.search(queryStringQuery("id:" + motivoDoCadastro.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(motivoDoCadastro), PageRequest.of(0, 1), 1));
        // Search the motivoDoCadastro
        restMotivoDoCadastroMockMvc.perform(get("/api/_search/motivo-do-cadastros?query=id:" + motivoDoCadastro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivoDoCadastro.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
}
