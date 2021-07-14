package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.GrupoAgendamentoExame;
import br.com.basis.madre.madreexames.repository.GrupoAgendamentoExameRepository;
import br.com.basis.madre.madreexames.repository.search.GrupoAgendamentoExameSearchRepository;
import br.com.basis.madre.madreexames.service.GrupoAgendamentoExameService;
import br.com.basis.madre.madreexames.service.dto.GrupoAgendamentoExameDTO;
import br.com.basis.madre.madreexames.service.mapper.GrupoAgendamentoExameMapper;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GrupoAgendamentoExameResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GrupoAgendamentoExameResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final Boolean DEFAULT_AGENDAR_EM_CONJUNTO = false;
    private static final Boolean UPDATED_AGENDAR_EM_CONJUNTO = true;

    private static final Boolean DEFAULT_CALCULAR_OCUPACAO = false;
    private static final Boolean UPDATED_CALCULAR_OCUPACAO = true;

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private GrupoAgendamentoExameRepository grupoAgendamentoExameRepository;

    @Mock
    private GrupoAgendamentoExameRepository grupoAgendamentoExameRepositoryMock;

    @Autowired
    private GrupoAgendamentoExameMapper grupoAgendamentoExameMapper;

    @Mock
    private GrupoAgendamentoExameService grupoAgendamentoExameServiceMock;

    @Autowired
    private GrupoAgendamentoExameService grupoAgendamentoExameService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.GrupoAgendamentoExameSearchRepositoryMockConfiguration
     */
    @Autowired
    private GrupoAgendamentoExameSearchRepository mockGrupoAgendamentoExameSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoAgendamentoExameMockMvc;

    private GrupoAgendamentoExame grupoAgendamentoExame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAgendamentoExame createEntity(EntityManager em) {
        GrupoAgendamentoExame grupoAgendamentoExame = new GrupoAgendamentoExame()
            .nome(DEFAULT_NOME)
            .codigo(DEFAULT_CODIGO)
            .agendarEmConjunto(DEFAULT_AGENDAR_EM_CONJUNTO)
            .calcularOcupacao(DEFAULT_CALCULAR_OCUPACAO)
            .ativo(DEFAULT_ATIVO);
        return grupoAgendamentoExame;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAgendamentoExame createUpdatedEntity(EntityManager em) {
        GrupoAgendamentoExame grupoAgendamentoExame = new GrupoAgendamentoExame()
            .nome(UPDATED_NOME)
            .codigo(UPDATED_CODIGO)
            .agendarEmConjunto(UPDATED_AGENDAR_EM_CONJUNTO)
            .calcularOcupacao(UPDATED_CALCULAR_OCUPACAO)
            .ativo(UPDATED_ATIVO);
        return grupoAgendamentoExame;
    }

    @BeforeEach
    public void initTest() {
        grupoAgendamentoExame = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupoAgendamentoExame() throws Exception {
        int databaseSizeBeforeCreate = grupoAgendamentoExameRepository.findAll().size();
        // Create the GrupoAgendamentoExame
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO = grupoAgendamentoExameMapper.toDto(grupoAgendamentoExame);
        restGrupoAgendamentoExameMockMvc.perform(post("/api/grupo-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoAgendamentoExameDTO)))
            .andExpect(status().isCreated());

        // Validate the GrupoAgendamentoExame in the database
        List<GrupoAgendamentoExame> grupoAgendamentoExameList = grupoAgendamentoExameRepository.findAll();
        assertThat(grupoAgendamentoExameList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoAgendamentoExame testGrupoAgendamentoExame = grupoAgendamentoExameList.get(grupoAgendamentoExameList.size() - 1);
        assertThat(testGrupoAgendamentoExame.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testGrupoAgendamentoExame.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testGrupoAgendamentoExame.isAgendarEmConjunto()).isEqualTo(DEFAULT_AGENDAR_EM_CONJUNTO);
        assertThat(testGrupoAgendamentoExame.isCalcularOcupacao()).isEqualTo(DEFAULT_CALCULAR_OCUPACAO);
        assertThat(testGrupoAgendamentoExame.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the GrupoAgendamentoExame in Elasticsearch
        verify(mockGrupoAgendamentoExameSearchRepository, times(1)).save(testGrupoAgendamentoExame);
    }

    @Test
    @Transactional
    public void createGrupoAgendamentoExameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupoAgendamentoExameRepository.findAll().size();

        // Create the GrupoAgendamentoExame with an existing ID
        grupoAgendamentoExame.setId(1L);
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO = grupoAgendamentoExameMapper.toDto(grupoAgendamentoExame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoAgendamentoExameMockMvc.perform(post("/api/grupo-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoAgendamentoExame in the database
        List<GrupoAgendamentoExame> grupoAgendamentoExameList = grupoAgendamentoExameRepository.findAll();
        assertThat(grupoAgendamentoExameList).hasSize(databaseSizeBeforeCreate);

        // Validate the GrupoAgendamentoExame in Elasticsearch
        verify(mockGrupoAgendamentoExameSearchRepository, times(0)).save(grupoAgendamentoExame);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoAgendamentoExameRepository.findAll().size();
        // set the field null
        grupoAgendamentoExame.setNome(null);

        // Create the GrupoAgendamentoExame, which fails.
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO = grupoAgendamentoExameMapper.toDto(grupoAgendamentoExame);


        restGrupoAgendamentoExameMockMvc.perform(post("/api/grupo-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GrupoAgendamentoExame> grupoAgendamentoExameList = grupoAgendamentoExameRepository.findAll();
        assertThat(grupoAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoAgendamentoExameRepository.findAll().size();
        // set the field null
        grupoAgendamentoExame.setCodigo(null);

        // Create the GrupoAgendamentoExame, which fails.
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO = grupoAgendamentoExameMapper.toDto(grupoAgendamentoExame);


        restGrupoAgendamentoExameMockMvc.perform(post("/api/grupo-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GrupoAgendamentoExame> grupoAgendamentoExameList = grupoAgendamentoExameRepository.findAll();
        assertThat(grupoAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoAgendamentoExameRepository.findAll().size();
        // set the field null
        grupoAgendamentoExame.setAtivo(null);

        // Create the GrupoAgendamentoExame, which fails.
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO = grupoAgendamentoExameMapper.toDto(grupoAgendamentoExame);


        restGrupoAgendamentoExameMockMvc.perform(post("/api/grupo-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GrupoAgendamentoExame> grupoAgendamentoExameList = grupoAgendamentoExameRepository.findAll();
        assertThat(grupoAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrupoAgendamentoExames() throws Exception {
        // Initialize the database
        grupoAgendamentoExameRepository.saveAndFlush(grupoAgendamentoExame);

        // Get all the grupoAgendamentoExameList
        restGrupoAgendamentoExameMockMvc.perform(get("/api/grupo-agendamento-exames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoAgendamentoExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].agendarEmConjunto").value(hasItem(DEFAULT_AGENDAR_EM_CONJUNTO.booleanValue())))
            .andExpect(jsonPath("$.[*].calcularOcupacao").value(hasItem(DEFAULT_CALCULAR_OCUPACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGrupoAgendamentoExamesWithEagerRelationshipsIsEnabled() throws Exception {
        when(grupoAgendamentoExameServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAgendamentoExameMockMvc.perform(get("/api/grupo-agendamento-exames?eagerload=true"))
            .andExpect(status().isOk());

        verify(grupoAgendamentoExameServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGrupoAgendamentoExamesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(grupoAgendamentoExameServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAgendamentoExameMockMvc.perform(get("/api/grupo-agendamento-exames?eagerload=true"))
            .andExpect(status().isOk());

        verify(grupoAgendamentoExameServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGrupoAgendamentoExame() throws Exception {
        // Initialize the database
        grupoAgendamentoExameRepository.saveAndFlush(grupoAgendamentoExame);

        // Get the grupoAgendamentoExame
        restGrupoAgendamentoExameMockMvc.perform(get("/api/grupo-agendamento-exames/{id}", grupoAgendamentoExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoAgendamentoExame.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.agendarEmConjunto").value(DEFAULT_AGENDAR_EM_CONJUNTO.booleanValue()))
            .andExpect(jsonPath("$.calcularOcupacao").value(DEFAULT_CALCULAR_OCUPACAO.booleanValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGrupoAgendamentoExame() throws Exception {
        // Get the grupoAgendamentoExame
        restGrupoAgendamentoExameMockMvc.perform(get("/api/grupo-agendamento-exames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupoAgendamentoExame() throws Exception {
        // Initialize the database
        grupoAgendamentoExameRepository.saveAndFlush(grupoAgendamentoExame);

        int databaseSizeBeforeUpdate = grupoAgendamentoExameRepository.findAll().size();

        // Update the grupoAgendamentoExame
        GrupoAgendamentoExame updatedGrupoAgendamentoExame = grupoAgendamentoExameRepository.findById(grupoAgendamentoExame.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoAgendamentoExame are not directly saved in db
        em.detach(updatedGrupoAgendamentoExame);
        updatedGrupoAgendamentoExame
            .nome(UPDATED_NOME)
            .codigo(UPDATED_CODIGO)
            .agendarEmConjunto(UPDATED_AGENDAR_EM_CONJUNTO)
            .calcularOcupacao(UPDATED_CALCULAR_OCUPACAO)
            .ativo(UPDATED_ATIVO);
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO = grupoAgendamentoExameMapper.toDto(updatedGrupoAgendamentoExame);

        restGrupoAgendamentoExameMockMvc.perform(put("/api/grupo-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoAgendamentoExameDTO)))
            .andExpect(status().isOk());

        // Validate the GrupoAgendamentoExame in the database
        List<GrupoAgendamentoExame> grupoAgendamentoExameList = grupoAgendamentoExameRepository.findAll();
        assertThat(grupoAgendamentoExameList).hasSize(databaseSizeBeforeUpdate);
        GrupoAgendamentoExame testGrupoAgendamentoExame = grupoAgendamentoExameList.get(grupoAgendamentoExameList.size() - 1);
        assertThat(testGrupoAgendamentoExame.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testGrupoAgendamentoExame.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testGrupoAgendamentoExame.isAgendarEmConjunto()).isEqualTo(UPDATED_AGENDAR_EM_CONJUNTO);
        assertThat(testGrupoAgendamentoExame.isCalcularOcupacao()).isEqualTo(UPDATED_CALCULAR_OCUPACAO);
        assertThat(testGrupoAgendamentoExame.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the GrupoAgendamentoExame in Elasticsearch
        verify(mockGrupoAgendamentoExameSearchRepository, times(1)).save(testGrupoAgendamentoExame);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupoAgendamentoExame() throws Exception {
        int databaseSizeBeforeUpdate = grupoAgendamentoExameRepository.findAll().size();

        // Create the GrupoAgendamentoExame
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO = grupoAgendamentoExameMapper.toDto(grupoAgendamentoExame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAgendamentoExameMockMvc.perform(put("/api/grupo-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoAgendamentoExame in the database
        List<GrupoAgendamentoExame> grupoAgendamentoExameList = grupoAgendamentoExameRepository.findAll();
        assertThat(grupoAgendamentoExameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GrupoAgendamentoExame in Elasticsearch
        verify(mockGrupoAgendamentoExameSearchRepository, times(0)).save(grupoAgendamentoExame);
    }

    @Test
    @Transactional
    public void deleteGrupoAgendamentoExame() throws Exception {
        // Initialize the database
        grupoAgendamentoExameRepository.saveAndFlush(grupoAgendamentoExame);

        int databaseSizeBeforeDelete = grupoAgendamentoExameRepository.findAll().size();

        // Delete the grupoAgendamentoExame
        restGrupoAgendamentoExameMockMvc.perform(delete("/api/grupo-agendamento-exames/{id}", grupoAgendamentoExame.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoAgendamentoExame> grupoAgendamentoExameList = grupoAgendamentoExameRepository.findAll();
        assertThat(grupoAgendamentoExameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GrupoAgendamentoExame in Elasticsearch
        verify(mockGrupoAgendamentoExameSearchRepository, times(1)).deleteById(grupoAgendamentoExame.getId());
    }

    @Test
    @Transactional
    public void searchGrupoAgendamentoExame() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        grupoAgendamentoExameRepository.saveAndFlush(grupoAgendamentoExame);
        when(mockGrupoAgendamentoExameSearchRepository.search(queryStringQuery("id:" + grupoAgendamentoExame.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(grupoAgendamentoExame), PageRequest.of(0, 1), 1));

        // Search the grupoAgendamentoExame
        restGrupoAgendamentoExameMockMvc.perform(get("/api/_search/grupo-agendamento-exames?query=id:" + grupoAgendamentoExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoAgendamentoExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].agendarEmConjunto").value(hasItem(DEFAULT_AGENDAR_EM_CONJUNTO.booleanValue())))
            .andExpect(jsonPath("$.[*].calcularOcupacao").value(hasItem(DEFAULT_CALCULAR_OCUPACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
}
