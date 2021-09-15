package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.ConselhosProfissionais;
import br.com.basis.madre.seguranca.repository.ConselhosProfissionaisRepository;
import br.com.basis.madre.seguranca.repository.search.ConselhosProfissionaisSearchRepository;
import br.com.basis.madre.seguranca.service.ConselhosProfissionaisService;
import br.com.basis.madre.seguranca.service.dto.ConselhosProfissionaisDTO;
import br.com.basis.madre.seguranca.service.mapper.ConselhosProfissionaisMapper;

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
 * Integration tests for the {@link ConselhosProfissionaisResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConselhosProfissionaisResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_MASCULINO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_MASCULINO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_FEMININO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_FEMININO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SITUACAO = false;
    private static final Boolean UPDATED_SITUACAO = true;

    @Autowired
    private ConselhosProfissionaisRepository conselhosProfissionaisRepository;

    @Autowired
    private ConselhosProfissionaisMapper conselhosProfissionaisMapper;

    @Autowired
    private ConselhosProfissionaisService conselhosProfissionaisService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.ConselhosProfissionaisSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConselhosProfissionaisSearchRepository mockConselhosProfissionaisSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConselhosProfissionaisMockMvc;

    private ConselhosProfissionais conselhosProfissionais;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConselhosProfissionais createEntity(EntityManager em) {
        ConselhosProfissionais conselhosProfissionais = new ConselhosProfissionais()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA)
            .tituloMasculino(DEFAULT_TITULO_MASCULINO)
            .tituloFeminino(DEFAULT_TITULO_FEMININO)
            .situacao(DEFAULT_SITUACAO);
        return conselhosProfissionais;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConselhosProfissionais createUpdatedEntity(EntityManager em) {
        ConselhosProfissionais conselhosProfissionais = new ConselhosProfissionais()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .tituloMasculino(UPDATED_TITULO_MASCULINO)
            .tituloFeminino(UPDATED_TITULO_FEMININO)
            .situacao(UPDATED_SITUACAO);
        return conselhosProfissionais;
    }

    @BeforeEach
    public void initTest() {
        conselhosProfissionais = createEntity(em);
    }

    @Test
    @Transactional
    public void createConselhosProfissionais() throws Exception {
        int databaseSizeBeforeCreate = conselhosProfissionaisRepository.findAll().size();
        // Create the ConselhosProfissionais
        ConselhosProfissionaisDTO conselhosProfissionaisDTO = conselhosProfissionaisMapper.toDto(conselhosProfissionais);
        restConselhosProfissionaisMockMvc.perform(post("/api/conselhos-profissionais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conselhosProfissionaisDTO)))
            .andExpect(status().isCreated());

        // Validate the ConselhosProfissionais in the database
        List<ConselhosProfissionais> conselhosProfissionaisList = conselhosProfissionaisRepository.findAll();
        assertThat(conselhosProfissionaisList).hasSize(databaseSizeBeforeCreate + 1);
        ConselhosProfissionais testConselhosProfissionais = conselhosProfissionaisList.get(conselhosProfissionaisList.size() - 1);
        assertThat(testConselhosProfissionais.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testConselhosProfissionais.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConselhosProfissionais.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testConselhosProfissionais.getTituloMasculino()).isEqualTo(DEFAULT_TITULO_MASCULINO);
        assertThat(testConselhosProfissionais.getTituloFeminino()).isEqualTo(DEFAULT_TITULO_FEMININO);
        assertThat(testConselhosProfissionais.isSituacao()).isEqualTo(DEFAULT_SITUACAO);

        // Validate the ConselhosProfissionais in Elasticsearch
        verify(mockConselhosProfissionaisSearchRepository, times(1)).save(testConselhosProfissionais);
    }

    @Test
    @Transactional
    public void createConselhosProfissionaisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conselhosProfissionaisRepository.findAll().size();

        // Create the ConselhosProfissionais with an existing ID
        conselhosProfissionais.setId(1L);
        ConselhosProfissionaisDTO conselhosProfissionaisDTO = conselhosProfissionaisMapper.toDto(conselhosProfissionais);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConselhosProfissionaisMockMvc.perform(post("/api/conselhos-profissionais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conselhosProfissionaisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConselhosProfissionais in the database
        List<ConselhosProfissionais> conselhosProfissionaisList = conselhosProfissionaisRepository.findAll();
        assertThat(conselhosProfissionaisList).hasSize(databaseSizeBeforeCreate);

        // Validate the ConselhosProfissionais in Elasticsearch
        verify(mockConselhosProfissionaisSearchRepository, times(0)).save(conselhosProfissionais);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = conselhosProfissionaisRepository.findAll().size();
        // set the field null
        conselhosProfissionais.setNome(null);

        // Create the ConselhosProfissionais, which fails.
        ConselhosProfissionaisDTO conselhosProfissionaisDTO = conselhosProfissionaisMapper.toDto(conselhosProfissionais);


        restConselhosProfissionaisMockMvc.perform(post("/api/conselhos-profissionais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conselhosProfissionaisDTO)))
            .andExpect(status().isBadRequest());

        List<ConselhosProfissionais> conselhosProfissionaisList = conselhosProfissionaisRepository.findAll();
        assertThat(conselhosProfissionaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = conselhosProfissionaisRepository.findAll().size();
        // set the field null
        conselhosProfissionais.setSigla(null);

        // Create the ConselhosProfissionais, which fails.
        ConselhosProfissionaisDTO conselhosProfissionaisDTO = conselhosProfissionaisMapper.toDto(conselhosProfissionais);


        restConselhosProfissionaisMockMvc.perform(post("/api/conselhos-profissionais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conselhosProfissionaisDTO)))
            .andExpect(status().isBadRequest());

        List<ConselhosProfissionais> conselhosProfissionaisList = conselhosProfissionaisRepository.findAll();
        assertThat(conselhosProfissionaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConselhosProfissionais() throws Exception {
        // Initialize the database
        conselhosProfissionaisRepository.saveAndFlush(conselhosProfissionais);

        // Get all the conselhosProfissionaisList
        restConselhosProfissionaisMockMvc.perform(get("/api/conselhos-profissionais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conselhosProfissionais.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].tituloMasculino").value(hasItem(DEFAULT_TITULO_MASCULINO)))
            .andExpect(jsonPath("$.[*].tituloFeminino").value(hasItem(DEFAULT_TITULO_FEMININO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConselhosProfissionais() throws Exception {
        // Initialize the database
        conselhosProfissionaisRepository.saveAndFlush(conselhosProfissionais);

        // Get the conselhosProfissionais
        restConselhosProfissionaisMockMvc.perform(get("/api/conselhos-profissionais/{id}", conselhosProfissionais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conselhosProfissionais.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.tituloMasculino").value(DEFAULT_TITULO_MASCULINO))
            .andExpect(jsonPath("$.tituloFeminino").value(DEFAULT_TITULO_FEMININO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingConselhosProfissionais() throws Exception {
        // Get the conselhosProfissionais
        restConselhosProfissionaisMockMvc.perform(get("/api/conselhos-profissionais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConselhosProfissionais() throws Exception {
        // Initialize the database
        conselhosProfissionaisRepository.saveAndFlush(conselhosProfissionais);

        int databaseSizeBeforeUpdate = conselhosProfissionaisRepository.findAll().size();

        // Update the conselhosProfissionais
        ConselhosProfissionais updatedConselhosProfissionais = conselhosProfissionaisRepository.findById(conselhosProfissionais.getId()).get();
        // Disconnect from session so that the updates on updatedConselhosProfissionais are not directly saved in db
        em.detach(updatedConselhosProfissionais);
        updatedConselhosProfissionais
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .tituloMasculino(UPDATED_TITULO_MASCULINO)
            .tituloFeminino(UPDATED_TITULO_FEMININO)
            .situacao(UPDATED_SITUACAO);
        ConselhosProfissionaisDTO conselhosProfissionaisDTO = conselhosProfissionaisMapper.toDto(updatedConselhosProfissionais);

        restConselhosProfissionaisMockMvc.perform(put("/api/conselhos-profissionais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conselhosProfissionaisDTO)))
            .andExpect(status().isOk());

        // Validate the ConselhosProfissionais in the database
        List<ConselhosProfissionais> conselhosProfissionaisList = conselhosProfissionaisRepository.findAll();
        assertThat(conselhosProfissionaisList).hasSize(databaseSizeBeforeUpdate);
        ConselhosProfissionais testConselhosProfissionais = conselhosProfissionaisList.get(conselhosProfissionaisList.size() - 1);
        assertThat(testConselhosProfissionais.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testConselhosProfissionais.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConselhosProfissionais.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testConselhosProfissionais.getTituloMasculino()).isEqualTo(UPDATED_TITULO_MASCULINO);
        assertThat(testConselhosProfissionais.getTituloFeminino()).isEqualTo(UPDATED_TITULO_FEMININO);
        assertThat(testConselhosProfissionais.isSituacao()).isEqualTo(UPDATED_SITUACAO);

        // Validate the ConselhosProfissionais in Elasticsearch
        verify(mockConselhosProfissionaisSearchRepository, times(1)).save(testConselhosProfissionais);
    }

    @Test
    @Transactional
    public void updateNonExistingConselhosProfissionais() throws Exception {
        int databaseSizeBeforeUpdate = conselhosProfissionaisRepository.findAll().size();

        // Create the ConselhosProfissionais
        ConselhosProfissionaisDTO conselhosProfissionaisDTO = conselhosProfissionaisMapper.toDto(conselhosProfissionais);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConselhosProfissionaisMockMvc.perform(put("/api/conselhos-profissionais")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conselhosProfissionaisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConselhosProfissionais in the database
        List<ConselhosProfissionais> conselhosProfissionaisList = conselhosProfissionaisRepository.findAll();
        assertThat(conselhosProfissionaisList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ConselhosProfissionais in Elasticsearch
        verify(mockConselhosProfissionaisSearchRepository, times(0)).save(conselhosProfissionais);
    }

    @Test
    @Transactional
    public void deleteConselhosProfissionais() throws Exception {
        // Initialize the database
        conselhosProfissionaisRepository.saveAndFlush(conselhosProfissionais);

        int databaseSizeBeforeDelete = conselhosProfissionaisRepository.findAll().size();

        // Delete the conselhosProfissionais
        restConselhosProfissionaisMockMvc.perform(delete("/api/conselhos-profissionais/{id}", conselhosProfissionais.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConselhosProfissionais> conselhosProfissionaisList = conselhosProfissionaisRepository.findAll();
        assertThat(conselhosProfissionaisList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ConselhosProfissionais in Elasticsearch
        verify(mockConselhosProfissionaisSearchRepository, times(1)).deleteById(conselhosProfissionais.getId());
    }

    @Test
    @Transactional
    public void searchConselhosProfissionais() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        conselhosProfissionaisRepository.saveAndFlush(conselhosProfissionais);
        when(mockConselhosProfissionaisSearchRepository.search(queryStringQuery("id:" + conselhosProfissionais.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(conselhosProfissionais), PageRequest.of(0, 1), 1));

        // Search the conselhosProfissionais
        restConselhosProfissionaisMockMvc.perform(get("/api/_search/conselhos-profissionais?query=id:" + conselhosProfissionais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conselhosProfissionais.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].tituloMasculino").value(hasItem(DEFAULT_TITULO_MASCULINO)))
            .andExpect(jsonPath("$.[*].tituloFeminino").value(hasItem(DEFAULT_TITULO_FEMININO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())));
    }
}
