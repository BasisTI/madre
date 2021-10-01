package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;
import br.com.basis.madre.madreexames.repository.GradeAgendamentoExameRepository;
import br.com.basis.madre.madreexames.repository.search.GradeAgendamentoExameSearchRepository;
import br.com.basis.madre.madreexames.service.GradeAgendamentoExameService;
import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;
import br.com.basis.madre.madreexames.service.mapper.GradeAgendamentoExameMapper;

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
import java.time.Duration;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GradeAgendamentoExameResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GradeAgendamentoExameResourceIT {

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DIA = "AAAAAAAAAA";
    private static final String UPDATED_DIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DE_HORARIOS = 1;
    private static final Integer UPDATED_NUMERO_DE_HORARIOS = 2;

    private static final Duration DEFAULT_DURACAO = Duration.ofHours(6);
    private static final Duration UPDATED_DURACAO = Duration.ofHours(12);

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Integer DEFAULT_UNIDADE_EXECUTORA_ID = 1;
    private static final Integer UPDATED_UNIDADE_EXECUTORA_ID = 2;

    private static final Integer DEFAULT_RESPONSAVEL_ID = 1;
    private static final Integer UPDATED_RESPONSAVEL_ID = 2;

    @Autowired
    private GradeAgendamentoExameRepository gradeAgendamentoExameRepository;

    @Autowired
    private GradeAgendamentoExameMapper gradeAgendamentoExameMapper;

    @Autowired
    private GradeAgendamentoExameService gradeAgendamentoExameService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.GradeAgendamentoExameSearchRepositoryMockConfiguration
     */
    @Autowired
    private GradeAgendamentoExameSearchRepository mockGradeAgendamentoExameSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGradeAgendamentoExameMockMvc;

    private GradeAgendamentoExame gradeAgendamentoExame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GradeAgendamentoExame createEntity(EntityManager em) {
        GradeAgendamentoExame gradeAgendamentoExame = new GradeAgendamentoExame()
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM)
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFim(DEFAULT_HORA_FIM)
            .dia(DEFAULT_DIA)
            .numeroDeHorarios(DEFAULT_NUMERO_DE_HORARIOS)
            .duracao(DEFAULT_DURACAO)
            .ativo(DEFAULT_ATIVO)
            .unidadeExecutoraId(DEFAULT_UNIDADE_EXECUTORA_ID)
            .responsavelId(DEFAULT_RESPONSAVEL_ID);
        return gradeAgendamentoExame;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GradeAgendamentoExame createUpdatedEntity(EntityManager em) {
        GradeAgendamentoExame gradeAgendamentoExame = new GradeAgendamentoExame()
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .dia(UPDATED_DIA)
            .numeroDeHorarios(UPDATED_NUMERO_DE_HORARIOS)
            .duracao(UPDATED_DURACAO)
            .ativo(UPDATED_ATIVO)
            .unidadeExecutoraId(UPDATED_UNIDADE_EXECUTORA_ID)
            .responsavelId(UPDATED_RESPONSAVEL_ID);
        return gradeAgendamentoExame;
    }

    @BeforeEach
    public void initTest() {
        gradeAgendamentoExame = createEntity(em);
    }

    @Test
    @Transactional
    public void createGradeAgendamentoExame() throws Exception {
        int databaseSizeBeforeCreate = gradeAgendamentoExameRepository.findAll().size();
        // Create the GradeAgendamentoExame
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);
        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isCreated());

        // Validate the GradeAgendamentoExame in the database
        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeCreate + 1);
        GradeAgendamentoExame testGradeAgendamentoExame = gradeAgendamentoExameList.get(gradeAgendamentoExameList.size() - 1);
        assertThat(testGradeAgendamentoExame.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testGradeAgendamentoExame.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testGradeAgendamentoExame.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testGradeAgendamentoExame.getHoraFim()).isEqualTo(DEFAULT_HORA_FIM);
        assertThat(testGradeAgendamentoExame.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testGradeAgendamentoExame.getNumeroDeHorarios()).isEqualTo(DEFAULT_NUMERO_DE_HORARIOS);
        assertThat(testGradeAgendamentoExame.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testGradeAgendamentoExame.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testGradeAgendamentoExame.getUnidadeExecutoraId()).isEqualTo(DEFAULT_UNIDADE_EXECUTORA_ID);
        assertThat(testGradeAgendamentoExame.getResponsavelId()).isEqualTo(DEFAULT_RESPONSAVEL_ID);

        // Validate the GradeAgendamentoExame in Elasticsearch
        verify(mockGradeAgendamentoExameSearchRepository, times(1)).save(testGradeAgendamentoExame);
    }

    @Test
    @Transactional
    public void createGradeAgendamentoExameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradeAgendamentoExameRepository.findAll().size();

        // Create the GradeAgendamentoExame with an existing ID
        gradeAgendamentoExame.setId(1L);
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GradeAgendamentoExame in the database
        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeCreate);

        // Validate the GradeAgendamentoExame in Elasticsearch
        verify(mockGradeAgendamentoExameSearchRepository, times(0)).save(gradeAgendamentoExame);
    }


    @Test
    @Transactional
    public void checkDataInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeAgendamentoExameRepository.findAll().size();
        // set the field null
        gradeAgendamentoExame.setDataInicio(null);

        // Create the GradeAgendamentoExame, which fails.
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);


        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeAgendamentoExameRepository.findAll().size();
        // set the field null
        gradeAgendamentoExame.setDataFim(null);

        // Create the GradeAgendamentoExame, which fails.
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);


        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeAgendamentoExameRepository.findAll().size();
        // set the field null
        gradeAgendamentoExame.setHoraInicio(null);

        // Create the GradeAgendamentoExame, which fails.
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);


        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeAgendamentoExameRepository.findAll().size();
        // set the field null
        gradeAgendamentoExame.setDia(null);

        // Create the GradeAgendamentoExame, which fails.
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);


        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDuracaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeAgendamentoExameRepository.findAll().size();
        // set the field null
        gradeAgendamentoExame.setDuracao(null);

        // Create the GradeAgendamentoExame, which fails.
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);


        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeAgendamentoExameRepository.findAll().size();
        // set the field null
        gradeAgendamentoExame.setAtivo(null);

        // Create the GradeAgendamentoExame, which fails.
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);


        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadeExecutoraIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeAgendamentoExameRepository.findAll().size();
        // set the field null
        gradeAgendamentoExame.setUnidadeExecutoraId(null);

        // Create the GradeAgendamentoExame, which fails.
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);


        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsavelIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeAgendamentoExameRepository.findAll().size();
        // set the field null
        gradeAgendamentoExame.setResponsavelId(null);

        // Create the GradeAgendamentoExame, which fails.
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);


        restGradeAgendamentoExameMockMvc.perform(post("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGradeAgendamentoExames() throws Exception {
        // Initialize the database
        gradeAgendamentoExameRepository.saveAndFlush(gradeAgendamentoExame);

        // Get all the gradeAgendamentoExameList
        restGradeAgendamentoExameMockMvc.perform(get("/api/grade-agendamento-exames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gradeAgendamentoExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA)))
            .andExpect(jsonPath("$.[*].numeroDeHorarios").value(hasItem(DEFAULT_NUMERO_DE_HORARIOS)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].unidadeExecutoraId").value(hasItem(DEFAULT_UNIDADE_EXECUTORA_ID)))
            .andExpect(jsonPath("$.[*].responsavelId").value(hasItem(DEFAULT_RESPONSAVEL_ID)));
    }
    
    @Test
    @Transactional
    public void getGradeAgendamentoExame() throws Exception {
        // Initialize the database
        gradeAgendamentoExameRepository.saveAndFlush(gradeAgendamentoExame);

        // Get the gradeAgendamentoExame
        restGradeAgendamentoExameMockMvc.perform(get("/api/grade-agendamento-exames/{id}", gradeAgendamentoExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gradeAgendamentoExame.getId().intValue()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFim").value(DEFAULT_HORA_FIM.toString()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA))
            .andExpect(jsonPath("$.numeroDeHorarios").value(DEFAULT_NUMERO_DE_HORARIOS))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.unidadeExecutoraId").value(DEFAULT_UNIDADE_EXECUTORA_ID))
            .andExpect(jsonPath("$.responsavelId").value(DEFAULT_RESPONSAVEL_ID));
    }
    @Test
    @Transactional
    public void getNonExistingGradeAgendamentoExame() throws Exception {
        // Get the gradeAgendamentoExame
        restGradeAgendamentoExameMockMvc.perform(get("/api/grade-agendamento-exames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGradeAgendamentoExame() throws Exception {
        // Initialize the database
        gradeAgendamentoExameRepository.saveAndFlush(gradeAgendamentoExame);

        int databaseSizeBeforeUpdate = gradeAgendamentoExameRepository.findAll().size();

        // Update the gradeAgendamentoExame
        GradeAgendamentoExame updatedGradeAgendamentoExame = gradeAgendamentoExameRepository.findById(gradeAgendamentoExame.getId()).get();
        // Disconnect from session so that the updates on updatedGradeAgendamentoExame are not directly saved in db
        em.detach(updatedGradeAgendamentoExame);
        updatedGradeAgendamentoExame
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .dia(UPDATED_DIA)
            .numeroDeHorarios(UPDATED_NUMERO_DE_HORARIOS)
            .duracao(UPDATED_DURACAO)
            .ativo(UPDATED_ATIVO)
            .unidadeExecutoraId(UPDATED_UNIDADE_EXECUTORA_ID)
            .responsavelId(UPDATED_RESPONSAVEL_ID);
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(updatedGradeAgendamentoExame);

        restGradeAgendamentoExameMockMvc.perform(put("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isOk());

        // Validate the GradeAgendamentoExame in the database
        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeUpdate);
        GradeAgendamentoExame testGradeAgendamentoExame = gradeAgendamentoExameList.get(gradeAgendamentoExameList.size() - 1);
        assertThat(testGradeAgendamentoExame.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testGradeAgendamentoExame.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
        assertThat(testGradeAgendamentoExame.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testGradeAgendamentoExame.getHoraFim()).isEqualTo(UPDATED_HORA_FIM);
        assertThat(testGradeAgendamentoExame.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testGradeAgendamentoExame.getNumeroDeHorarios()).isEqualTo(UPDATED_NUMERO_DE_HORARIOS);
        assertThat(testGradeAgendamentoExame.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testGradeAgendamentoExame.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testGradeAgendamentoExame.getUnidadeExecutoraId()).isEqualTo(UPDATED_UNIDADE_EXECUTORA_ID);
        assertThat(testGradeAgendamentoExame.getResponsavelId()).isEqualTo(UPDATED_RESPONSAVEL_ID);

        // Validate the GradeAgendamentoExame in Elasticsearch
        verify(mockGradeAgendamentoExameSearchRepository, times(1)).save(testGradeAgendamentoExame);
    }

    @Test
    @Transactional
    public void updateNonExistingGradeAgendamentoExame() throws Exception {
        int databaseSizeBeforeUpdate = gradeAgendamentoExameRepository.findAll().size();

        // Create the GradeAgendamentoExame
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO = gradeAgendamentoExameMapper.toDto(gradeAgendamentoExame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradeAgendamentoExameMockMvc.perform(put("/api/grade-agendamento-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeAgendamentoExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GradeAgendamentoExame in the database
        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GradeAgendamentoExame in Elasticsearch
        verify(mockGradeAgendamentoExameSearchRepository, times(0)).save(gradeAgendamentoExame);
    }

    @Test
    @Transactional
    public void deleteGradeAgendamentoExame() throws Exception {
        // Initialize the database
        gradeAgendamentoExameRepository.saveAndFlush(gradeAgendamentoExame);

        int databaseSizeBeforeDelete = gradeAgendamentoExameRepository.findAll().size();

        // Delete the gradeAgendamentoExame
        restGradeAgendamentoExameMockMvc.perform(delete("/api/grade-agendamento-exames/{id}", gradeAgendamentoExame.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GradeAgendamentoExame> gradeAgendamentoExameList = gradeAgendamentoExameRepository.findAll();
        assertThat(gradeAgendamentoExameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GradeAgendamentoExame in Elasticsearch
        verify(mockGradeAgendamentoExameSearchRepository, times(1)).deleteById(gradeAgendamentoExame.getId());
    }

    @Test
    @Transactional
    public void searchGradeAgendamentoExame() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        gradeAgendamentoExameRepository.saveAndFlush(gradeAgendamentoExame);
        when(mockGradeAgendamentoExameSearchRepository.search(queryStringQuery("id:" + gradeAgendamentoExame.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(gradeAgendamentoExame), PageRequest.of(0, 1), 1));

        // Search the gradeAgendamentoExame
        restGradeAgendamentoExameMockMvc.perform(get("/api/_search/grade-agendamento-exames?query=id:" + gradeAgendamentoExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gradeAgendamentoExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA)))
            .andExpect(jsonPath("$.[*].numeroDeHorarios").value(hasItem(DEFAULT_NUMERO_DE_HORARIOS)))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].unidadeExecutoraId").value(hasItem(DEFAULT_UNIDADE_EXECUTORA_ID)))
            .andExpect(jsonPath("$.[*].responsavelId").value(hasItem(DEFAULT_RESPONSAVEL_ID)));
    }
}
