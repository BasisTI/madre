package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.Sala;
import br.com.basis.madre.madreexames.repository.SalaRepository;
import br.com.basis.madre.madreexames.repository.search.SalaSearchRepository;
import br.com.basis.madre.madreexames.service.SalaService;
import br.com.basis.madre.madreexames.service.dto.SalaDTO;
import br.com.basis.madre.madreexames.service.mapper.SalaMapper;

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
 * Integration tests for the {@link SalaResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCACAO = "AAAAAAAAAA";
    private static final String UPDATED_LOCACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Integer DEFAULT_UNIDADE_EXECUTORA_ID = 1;
    private static final Integer UPDATED_UNIDADE_EXECUTORA_ID = 2;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaMapper salaMapper;

    @Autowired
    private SalaService salaService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.SalaSearchRepositoryMockConfiguration
     */
    @Autowired
    private SalaSearchRepository mockSalaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalaMockMvc;

    private Sala sala;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sala createEntity(EntityManager em) {
        Sala sala = new Sala()
            .nome(DEFAULT_NOME)
            .locacao(DEFAULT_LOCACAO)
            .ativo(DEFAULT_ATIVO)
            .unidadeExecutoraId(DEFAULT_UNIDADE_EXECUTORA_ID);
        return sala;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sala createUpdatedEntity(EntityManager em) {
        Sala sala = new Sala()
            .nome(UPDATED_NOME)
            .locacao(UPDATED_LOCACAO)
            .ativo(UPDATED_ATIVO)
            .unidadeExecutoraId(UPDATED_UNIDADE_EXECUTORA_ID);
        return sala;
    }

    @BeforeEach
    public void initTest() {
        sala = createEntity(em);
    }

    @Test
    @Transactional
    public void createSala() throws Exception {
        int databaseSizeBeforeCreate = salaRepository.findAll().size();
        // Create the Sala
        SalaDTO salaDTO = salaMapper.toDto(sala);
        restSalaMockMvc.perform(post("/api/salas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isCreated());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeCreate + 1);
        Sala testSala = salaList.get(salaList.size() - 1);
        assertThat(testSala.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSala.getLocacao()).isEqualTo(DEFAULT_LOCACAO);
        assertThat(testSala.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testSala.getUnidadeExecutoraId()).isEqualTo(DEFAULT_UNIDADE_EXECUTORA_ID);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).save(testSala);
    }

    @Test
    @Transactional
    public void createSalaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salaRepository.findAll().size();

        // Create the Sala with an existing ID
        sala.setId(1L);
        SalaDTO salaDTO = salaMapper.toDto(sala);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaMockMvc.perform(post("/api/salas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(0)).save(sala);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaRepository.findAll().size();
        // set the field null
        sala.setNome(null);

        // Create the Sala, which fails.
        SalaDTO salaDTO = salaMapper.toDto(sala);


        restSalaMockMvc.perform(post("/api/salas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaRepository.findAll().size();
        // set the field null
        sala.setLocacao(null);

        // Create the Sala, which fails.
        SalaDTO salaDTO = salaMapper.toDto(sala);


        restSalaMockMvc.perform(post("/api/salas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaRepository.findAll().size();
        // set the field null
        sala.setAtivo(null);

        // Create the Sala, which fails.
        SalaDTO salaDTO = salaMapper.toDto(sala);


        restSalaMockMvc.perform(post("/api/salas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalas() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        // Get all the salaList
        restSalaMockMvc.perform(get("/api/salas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sala.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].locacao").value(hasItem(DEFAULT_LOCACAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].unidadeExecutoraId").value(hasItem(DEFAULT_UNIDADE_EXECUTORA_ID)));
    }
    
    @Test
    @Transactional
    public void getSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        // Get the sala
        restSalaMockMvc.perform(get("/api/salas/{id}", sala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sala.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.locacao").value(DEFAULT_LOCACAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.unidadeExecutoraId").value(DEFAULT_UNIDADE_EXECUTORA_ID));
    }
    @Test
    @Transactional
    public void getNonExistingSala() throws Exception {
        // Get the sala
        restSalaMockMvc.perform(get("/api/salas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        int databaseSizeBeforeUpdate = salaRepository.findAll().size();

        // Update the sala
        Sala updatedSala = salaRepository.findById(sala.getId()).get();
        // Disconnect from session so that the updates on updatedSala are not directly saved in db
        em.detach(updatedSala);
        updatedSala
            .nome(UPDATED_NOME)
            .locacao(UPDATED_LOCACAO)
            .ativo(UPDATED_ATIVO)
            .unidadeExecutoraId(UPDATED_UNIDADE_EXECUTORA_ID);
        SalaDTO salaDTO = salaMapper.toDto(updatedSala);

        restSalaMockMvc.perform(put("/api/salas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isOk());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeUpdate);
        Sala testSala = salaList.get(salaList.size() - 1);
        assertThat(testSala.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSala.getLocacao()).isEqualTo(UPDATED_LOCACAO);
        assertThat(testSala.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testSala.getUnidadeExecutoraId()).isEqualTo(UPDATED_UNIDADE_EXECUTORA_ID);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).save(testSala);
    }

    @Test
    @Transactional
    public void updateNonExistingSala() throws Exception {
        int databaseSizeBeforeUpdate = salaRepository.findAll().size();

        // Create the Sala
        SalaDTO salaDTO = salaMapper.toDto(sala);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaMockMvc.perform(put("/api/salas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sala in the database
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(0)).save(sala);
    }

    @Test
    @Transactional
    public void deleteSala() throws Exception {
        // Initialize the database
        salaRepository.saveAndFlush(sala);

        int databaseSizeBeforeDelete = salaRepository.findAll().size();

        // Delete the sala
        restSalaMockMvc.perform(delete("/api/salas/{id}", sala.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sala> salaList = salaRepository.findAll();
        assertThat(salaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sala in Elasticsearch
        verify(mockSalaSearchRepository, times(1)).deleteById(sala.getId());
    }

    @Test
    @Transactional
    public void searchSala() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        salaRepository.saveAndFlush(sala);
        when(mockSalaSearchRepository.search(queryStringQuery("id:" + sala.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sala), PageRequest.of(0, 1), 1));

        // Search the sala
        restSalaMockMvc.perform(get("/api/_search/salas?query=id:" + sala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sala.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].locacao").value(hasItem(DEFAULT_LOCACAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].unidadeExecutoraId").value(hasItem(DEFAULT_UNIDADE_EXECUTORA_ID)));
    }
}
