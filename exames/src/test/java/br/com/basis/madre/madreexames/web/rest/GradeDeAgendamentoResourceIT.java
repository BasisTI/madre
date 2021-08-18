package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.GradeDeAgendamento;
import br.com.basis.madre.madreexames.repository.GradeDeAgendamentoRepository;
import br.com.basis.madre.madreexames.repository.search.GradeDeAgendamentoSearchRepository;
import br.com.basis.madre.madreexames.service.GradeDeAgendamentoService;
import br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO;
import br.com.basis.madre.madreexames.service.mapper.GradeDeAgendamentoMapper;

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
 * Integration tests for the {@link GradeDeAgendamentoResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GradeDeAgendamentoResourceIT {

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final Integer DEFAULT_UNIDADE_EXECUTORA_ID = 1;
    private static final Integer UPDATED_UNIDADE_EXECUTORA_ID = 2;

    private static final Integer DEFAULT_RESPONSAVEL_ID = 1;
    private static final Integer UPDATED_RESPONSAVEL_ID = 2;

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private GradeDeAgendamentoRepository gradeDeAgendamentoRepository;

    @Autowired
    private GradeDeAgendamentoMapper gradeDeAgendamentoMapper;

    @Autowired
    private GradeDeAgendamentoService gradeDeAgendamentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.GradeDeAgendamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private GradeDeAgendamentoSearchRepository mockGradeDeAgendamentoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGradeDeAgendamentoMockMvc;

    private GradeDeAgendamento gradeDeAgendamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GradeDeAgendamento createEntity(EntityManager em) {
        GradeDeAgendamento gradeDeAgendamento = new GradeDeAgendamento()
            .grade(DEFAULT_GRADE)
            .unidadeExecutoraId(DEFAULT_UNIDADE_EXECUTORA_ID)
            .responsavelId(DEFAULT_RESPONSAVEL_ID)
            .ativo(DEFAULT_ATIVO);
        return gradeDeAgendamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GradeDeAgendamento createUpdatedEntity(EntityManager em) {
        GradeDeAgendamento gradeDeAgendamento = new GradeDeAgendamento()
            .grade(UPDATED_GRADE)
            .unidadeExecutoraId(UPDATED_UNIDADE_EXECUTORA_ID)
            .responsavelId(UPDATED_RESPONSAVEL_ID)
            .ativo(UPDATED_ATIVO);
        return gradeDeAgendamento;
    }

    @BeforeEach
    public void initTest() {
        gradeDeAgendamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createGradeDeAgendamento() throws Exception {
        int databaseSizeBeforeCreate = gradeDeAgendamentoRepository.findAll().size();
        // Create the GradeDeAgendamento
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO = gradeDeAgendamentoMapper.toDto(gradeDeAgendamento);
        restGradeDeAgendamentoMockMvc.perform(post("/api/grade-de-agendamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeDeAgendamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the GradeDeAgendamento in the database
        List<GradeDeAgendamento> gradeDeAgendamentoList = gradeDeAgendamentoRepository.findAll();
        assertThat(gradeDeAgendamentoList).hasSize(databaseSizeBeforeCreate + 1);
        GradeDeAgendamento testGradeDeAgendamento = gradeDeAgendamentoList.get(gradeDeAgendamentoList.size() - 1);
        assertThat(testGradeDeAgendamento.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testGradeDeAgendamento.getUnidadeExecutoraId()).isEqualTo(DEFAULT_UNIDADE_EXECUTORA_ID);
        assertThat(testGradeDeAgendamento.getResponsavelId()).isEqualTo(DEFAULT_RESPONSAVEL_ID);
        assertThat(testGradeDeAgendamento.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the GradeDeAgendamento in Elasticsearch
        verify(mockGradeDeAgendamentoSearchRepository, times(1)).save(testGradeDeAgendamento);
    }

    @Test
    @Transactional
    public void createGradeDeAgendamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradeDeAgendamentoRepository.findAll().size();

        // Create the GradeDeAgendamento with an existing ID
        gradeDeAgendamento.setId(1L);
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO = gradeDeAgendamentoMapper.toDto(gradeDeAgendamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradeDeAgendamentoMockMvc.perform(post("/api/grade-de-agendamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GradeDeAgendamento in the database
        List<GradeDeAgendamento> gradeDeAgendamentoList = gradeDeAgendamentoRepository.findAll();
        assertThat(gradeDeAgendamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the GradeDeAgendamento in Elasticsearch
        verify(mockGradeDeAgendamentoSearchRepository, times(0)).save(gradeDeAgendamento);
    }


    @Test
    @Transactional
    public void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeDeAgendamentoRepository.findAll().size();
        // set the field null
        gradeDeAgendamento.setGrade(null);

        // Create the GradeDeAgendamento, which fails.
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO = gradeDeAgendamentoMapper.toDto(gradeDeAgendamento);


        restGradeDeAgendamentoMockMvc.perform(post("/api/grade-de-agendamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        List<GradeDeAgendamento> gradeDeAgendamentoList = gradeDeAgendamentoRepository.findAll();
        assertThat(gradeDeAgendamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadeExecutoraIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeDeAgendamentoRepository.findAll().size();
        // set the field null
        gradeDeAgendamento.setUnidadeExecutoraId(null);

        // Create the GradeDeAgendamento, which fails.
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO = gradeDeAgendamentoMapper.toDto(gradeDeAgendamento);


        restGradeDeAgendamentoMockMvc.perform(post("/api/grade-de-agendamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        List<GradeDeAgendamento> gradeDeAgendamentoList = gradeDeAgendamentoRepository.findAll();
        assertThat(gradeDeAgendamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeDeAgendamentoRepository.findAll().size();
        // set the field null
        gradeDeAgendamento.setAtivo(null);

        // Create the GradeDeAgendamento, which fails.
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO = gradeDeAgendamentoMapper.toDto(gradeDeAgendamento);


        restGradeDeAgendamentoMockMvc.perform(post("/api/grade-de-agendamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        List<GradeDeAgendamento> gradeDeAgendamentoList = gradeDeAgendamentoRepository.findAll();
        assertThat(gradeDeAgendamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGradeDeAgendamentos() throws Exception {
        // Initialize the database
        gradeDeAgendamentoRepository.saveAndFlush(gradeDeAgendamento);

        // Get all the gradeDeAgendamentoList
        restGradeDeAgendamentoMockMvc.perform(get("/api/grade-de-agendamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gradeDeAgendamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].unidadeExecutoraId").value(hasItem(DEFAULT_UNIDADE_EXECUTORA_ID)))
            .andExpect(jsonPath("$.[*].responsavelId").value(hasItem(DEFAULT_RESPONSAVEL_ID)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getGradeDeAgendamento() throws Exception {
        // Initialize the database
        gradeDeAgendamentoRepository.saveAndFlush(gradeDeAgendamento);

        // Get the gradeDeAgendamento
        restGradeDeAgendamentoMockMvc.perform(get("/api/grade-de-agendamentos/{id}", gradeDeAgendamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gradeDeAgendamento.getId().intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.unidadeExecutoraId").value(DEFAULT_UNIDADE_EXECUTORA_ID))
            .andExpect(jsonPath("$.responsavelId").value(DEFAULT_RESPONSAVEL_ID))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGradeDeAgendamento() throws Exception {
        // Get the gradeDeAgendamento
        restGradeDeAgendamentoMockMvc.perform(get("/api/grade-de-agendamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGradeDeAgendamento() throws Exception {
        // Initialize the database
        gradeDeAgendamentoRepository.saveAndFlush(gradeDeAgendamento);

        int databaseSizeBeforeUpdate = gradeDeAgendamentoRepository.findAll().size();

        // Update the gradeDeAgendamento
        GradeDeAgendamento updatedGradeDeAgendamento = gradeDeAgendamentoRepository.findById(gradeDeAgendamento.getId()).get();
        // Disconnect from session so that the updates on updatedGradeDeAgendamento are not directly saved in db
        em.detach(updatedGradeDeAgendamento);
        updatedGradeDeAgendamento
            .grade(UPDATED_GRADE)
            .unidadeExecutoraId(UPDATED_UNIDADE_EXECUTORA_ID)
            .responsavelId(UPDATED_RESPONSAVEL_ID)
            .ativo(UPDATED_ATIVO);
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO = gradeDeAgendamentoMapper.toDto(updatedGradeDeAgendamento);

        restGradeDeAgendamentoMockMvc.perform(put("/api/grade-de-agendamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeDeAgendamentoDTO)))
            .andExpect(status().isOk());

        // Validate the GradeDeAgendamento in the database
        List<GradeDeAgendamento> gradeDeAgendamentoList = gradeDeAgendamentoRepository.findAll();
        assertThat(gradeDeAgendamentoList).hasSize(databaseSizeBeforeUpdate);
        GradeDeAgendamento testGradeDeAgendamento = gradeDeAgendamentoList.get(gradeDeAgendamentoList.size() - 1);
        assertThat(testGradeDeAgendamento.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testGradeDeAgendamento.getUnidadeExecutoraId()).isEqualTo(UPDATED_UNIDADE_EXECUTORA_ID);
        assertThat(testGradeDeAgendamento.getResponsavelId()).isEqualTo(UPDATED_RESPONSAVEL_ID);
        assertThat(testGradeDeAgendamento.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the GradeDeAgendamento in Elasticsearch
        verify(mockGradeDeAgendamentoSearchRepository, times(1)).save(testGradeDeAgendamento);
    }

    @Test
    @Transactional
    public void updateNonExistingGradeDeAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = gradeDeAgendamentoRepository.findAll().size();

        // Create the GradeDeAgendamento
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO = gradeDeAgendamentoMapper.toDto(gradeDeAgendamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradeDeAgendamentoMockMvc.perform(put("/api/grade-de-agendamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GradeDeAgendamento in the database
        List<GradeDeAgendamento> gradeDeAgendamentoList = gradeDeAgendamentoRepository.findAll();
        assertThat(gradeDeAgendamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GradeDeAgendamento in Elasticsearch
        verify(mockGradeDeAgendamentoSearchRepository, times(0)).save(gradeDeAgendamento);
    }

    @Test
    @Transactional
    public void deleteGradeDeAgendamento() throws Exception {
        // Initialize the database
        gradeDeAgendamentoRepository.saveAndFlush(gradeDeAgendamento);

        int databaseSizeBeforeDelete = gradeDeAgendamentoRepository.findAll().size();

        // Delete the gradeDeAgendamento
        restGradeDeAgendamentoMockMvc.perform(delete("/api/grade-de-agendamentos/{id}", gradeDeAgendamento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GradeDeAgendamento> gradeDeAgendamentoList = gradeDeAgendamentoRepository.findAll();
        assertThat(gradeDeAgendamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GradeDeAgendamento in Elasticsearch
        verify(mockGradeDeAgendamentoSearchRepository, times(1)).deleteById(gradeDeAgendamento.getId());
    }

    @Test
    @Transactional
    public void searchGradeDeAgendamento() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        gradeDeAgendamentoRepository.saveAndFlush(gradeDeAgendamento);
        when(mockGradeDeAgendamentoSearchRepository.search(queryStringQuery("id:" + gradeDeAgendamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(gradeDeAgendamento), PageRequest.of(0, 1), 1));

        // Search the gradeDeAgendamento
        restGradeDeAgendamentoMockMvc.perform(get("/api/_search/grade-de-agendamentos?query=id:" + gradeDeAgendamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gradeDeAgendamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].unidadeExecutoraId").value(hasItem(DEFAULT_UNIDADE_EXECUTORA_ID)))
            .andExpect(jsonPath("$.[*].responsavelId").value(hasItem(DEFAULT_RESPONSAVEL_ID)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
}
