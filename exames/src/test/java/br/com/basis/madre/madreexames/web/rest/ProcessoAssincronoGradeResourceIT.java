package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.ProcessoAssincronoGrade;
import br.com.basis.madre.madreexames.repository.ProcessoAssincronoGradeRepository;
import br.com.basis.madre.madreexames.repository.search.ProcessoAssincronoGradeSearchRepository;
import br.com.basis.madre.madreexames.service.ProcessoAssincronoGradeService;
import br.com.basis.madre.madreexames.service.dto.ProcessoAssincronoGradeDTO;
import br.com.basis.madre.madreexames.service.mapper.ProcessoAssincronoGradeMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.madreexames.domain.enumeration.Status;
/**
 * Integration tests for the {@link ProcessoAssincronoGradeResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcessoAssincronoGradeResourceIT {

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_TERMINO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_TERMINO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ULTIMA_ATUALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ULTIMA_ATUALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QTD_HORARIOS_PARA_CRIAR = 1;
    private static final Integer UPDATED_QTD_HORARIOS_PARA_CRIAR = 2;

    private static final Integer DEFAULT_TOTAL_DE_HORARIOS_CRIADOS = 1;
    private static final Integer UPDATED_TOTAL_DE_HORARIOS_CRIADOS = 2;

    private static final Double DEFAULT_TOTAL_COMPLETO = 1D;
    private static final Double UPDATED_TOTAL_COMPLETO = 2D;

    private static final Status DEFAULT_STATUS = Status.INICIADO;
    private static final Status UPDATED_STATUS = Status.COMPLETO;

    @Autowired
    private ProcessoAssincronoGradeRepository processoAssincronoGradeRepository;

    @Autowired
    private ProcessoAssincronoGradeMapper processoAssincronoGradeMapper;

    @Autowired
    private ProcessoAssincronoGradeService processoAssincronoGradeService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.ProcessoAssincronoGradeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProcessoAssincronoGradeSearchRepository mockProcessoAssincronoGradeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessoAssincronoGradeMockMvc;

    private ProcessoAssincronoGrade processoAssincronoGrade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessoAssincronoGrade createEntity(EntityManager em) {
        ProcessoAssincronoGrade processoAssincronoGrade = new ProcessoAssincronoGrade()
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataTermino(DEFAULT_DATA_TERMINO)
            .ultimaAtualizacao(DEFAULT_ULTIMA_ATUALIZACAO)
            .qtdHorariosParaCriar(DEFAULT_QTD_HORARIOS_PARA_CRIAR)
            .totalDeHorariosCriados(DEFAULT_TOTAL_DE_HORARIOS_CRIADOS)
            .totalCompleto(DEFAULT_TOTAL_COMPLETO)
            .status(DEFAULT_STATUS);
        return processoAssincronoGrade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessoAssincronoGrade createUpdatedEntity(EntityManager em) {
        ProcessoAssincronoGrade processoAssincronoGrade = new ProcessoAssincronoGrade()
            .dataInicio(UPDATED_DATA_INICIO)
            .dataTermino(UPDATED_DATA_TERMINO)
            .ultimaAtualizacao(UPDATED_ULTIMA_ATUALIZACAO)
            .qtdHorariosParaCriar(UPDATED_QTD_HORARIOS_PARA_CRIAR)
            .totalDeHorariosCriados(UPDATED_TOTAL_DE_HORARIOS_CRIADOS)
            .totalCompleto(UPDATED_TOTAL_COMPLETO)
            .status(UPDATED_STATUS);
        return processoAssincronoGrade;
    }

    @BeforeEach
    public void initTest() {
        processoAssincronoGrade = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessoAssincronoGrade() throws Exception {
        int databaseSizeBeforeCreate = processoAssincronoGradeRepository.findAll().size();
        // Create the ProcessoAssincronoGrade
        ProcessoAssincronoGradeDTO processoAssincronoGradeDTO = processoAssincronoGradeMapper.toDto(processoAssincronoGrade);
        restProcessoAssincronoGradeMockMvc.perform(post("/api/processo-assincrono-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoAssincronoGradeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcessoAssincronoGrade in the database
        List<ProcessoAssincronoGrade> processoAssincronoGradeList = processoAssincronoGradeRepository.findAll();
        assertThat(processoAssincronoGradeList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessoAssincronoGrade testProcessoAssincronoGrade = processoAssincronoGradeList.get(processoAssincronoGradeList.size() - 1);
        assertThat(testProcessoAssincronoGrade.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testProcessoAssincronoGrade.getDataTermino()).isEqualTo(DEFAULT_DATA_TERMINO);
        assertThat(testProcessoAssincronoGrade.getUltimaAtualizacao()).isEqualTo(DEFAULT_ULTIMA_ATUALIZACAO);
        assertThat(testProcessoAssincronoGrade.getQtdHorariosParaCriar()).isEqualTo(DEFAULT_QTD_HORARIOS_PARA_CRIAR);
        assertThat(testProcessoAssincronoGrade.getTotalDeHorariosCriados()).isEqualTo(DEFAULT_TOTAL_DE_HORARIOS_CRIADOS);
        assertThat(testProcessoAssincronoGrade.getTotalCompleto()).isEqualTo(DEFAULT_TOTAL_COMPLETO);
        assertThat(testProcessoAssincronoGrade.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the ProcessoAssincronoGrade in Elasticsearch
        verify(mockProcessoAssincronoGradeSearchRepository, times(1)).save(testProcessoAssincronoGrade);
    }

    @Test
    @Transactional
    public void createProcessoAssincronoGradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processoAssincronoGradeRepository.findAll().size();

        // Create the ProcessoAssincronoGrade with an existing ID
        processoAssincronoGrade.setId(processoAssincronoGrade.getId());
        ProcessoAssincronoGradeDTO processoAssincronoGradeDTO = processoAssincronoGradeMapper.toDto(processoAssincronoGrade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoAssincronoGradeMockMvc.perform(post("/api/processo-assincrono-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoAssincronoGradeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessoAssincronoGrade in the database
        List<ProcessoAssincronoGrade> processoAssincronoGradeList = processoAssincronoGradeRepository.findAll();
        assertThat(processoAssincronoGradeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProcessoAssincronoGrade in Elasticsearch
        verify(mockProcessoAssincronoGradeSearchRepository, times(0)).save(processoAssincronoGrade);
    }


    @Test
    @Transactional
    public void getAllProcessoAssincronoGrades() throws Exception {
        // Initialize the database
        processoAssincronoGradeRepository.saveAndFlush(processoAssincronoGrade);

        // Get all the processoAssincronoGradeList
        restProcessoAssincronoGradeMockMvc.perform(get("/api/processo-assincrono-grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processoAssincronoGrade.getId())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataTermino").value(hasItem(DEFAULT_DATA_TERMINO.toString())))
            .andExpect(jsonPath("$.[*].ultimaAtualizacao").value(hasItem(DEFAULT_ULTIMA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].qtdHorariosParaCriar").value(hasItem(DEFAULT_QTD_HORARIOS_PARA_CRIAR)))
            .andExpect(jsonPath("$.[*].totalDeHorariosCriados").value(hasItem(DEFAULT_TOTAL_DE_HORARIOS_CRIADOS)))
            .andExpect(jsonPath("$.[*].totalCompleto").value(hasItem(DEFAULT_TOTAL_COMPLETO.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getProcessoAssincronoGrade() throws Exception {
        // Initialize the database
        processoAssincronoGradeRepository.saveAndFlush(processoAssincronoGrade);

        // Get the processoAssincronoGrade
        restProcessoAssincronoGradeMockMvc.perform(get("/api/processo-assincrono-grades/{id}", processoAssincronoGrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processoAssincronoGrade.getId()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataTermino").value(DEFAULT_DATA_TERMINO.toString()))
            .andExpect(jsonPath("$.ultimaAtualizacao").value(DEFAULT_ULTIMA_ATUALIZACAO.toString()))
            .andExpect(jsonPath("$.qtdHorariosParaCriar").value(DEFAULT_QTD_HORARIOS_PARA_CRIAR))
            .andExpect(jsonPath("$.totalDeHorariosCriados").value(DEFAULT_TOTAL_DE_HORARIOS_CRIADOS))
            .andExpect(jsonPath("$.totalCompleto").value(DEFAULT_TOTAL_COMPLETO.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProcessoAssincronoGrade() throws Exception {
        // Get the processoAssincronoGrade
        restProcessoAssincronoGradeMockMvc.perform(get("/api/processo-assincrono-grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessoAssincronoGrade() throws Exception {
        // Initialize the database
        processoAssincronoGradeRepository.saveAndFlush(processoAssincronoGrade);

        int databaseSizeBeforeUpdate = processoAssincronoGradeRepository.findAll().size();

        // Update the processoAssincronoGrade
        ProcessoAssincronoGrade updatedProcessoAssincronoGrade = processoAssincronoGradeRepository.findById(processoAssincronoGrade.getId()).get();
        // Disconnect from session so that the updates on updatedProcessoAssincronoGrade are not directly saved in db
        em.detach(updatedProcessoAssincronoGrade);
        updatedProcessoAssincronoGrade
            .dataInicio(UPDATED_DATA_INICIO)
            .dataTermino(UPDATED_DATA_TERMINO)
            .ultimaAtualizacao(UPDATED_ULTIMA_ATUALIZACAO)
            .qtdHorariosParaCriar(UPDATED_QTD_HORARIOS_PARA_CRIAR)
            .totalDeHorariosCriados(UPDATED_TOTAL_DE_HORARIOS_CRIADOS)
            .totalCompleto(UPDATED_TOTAL_COMPLETO)
            .status(UPDATED_STATUS);
        ProcessoAssincronoGradeDTO processoAssincronoGradeDTO = processoAssincronoGradeMapper.toDto(updatedProcessoAssincronoGrade);

        restProcessoAssincronoGradeMockMvc.perform(put("/api/processo-assincrono-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoAssincronoGradeDTO)))
            .andExpect(status().isOk());

        // Validate the ProcessoAssincronoGrade in the database
        List<ProcessoAssincronoGrade> processoAssincronoGradeList = processoAssincronoGradeRepository.findAll();
        assertThat(processoAssincronoGradeList).hasSize(databaseSizeBeforeUpdate);
        ProcessoAssincronoGrade testProcessoAssincronoGrade = processoAssincronoGradeList.get(processoAssincronoGradeList.size() - 1);
        assertThat(testProcessoAssincronoGrade.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testProcessoAssincronoGrade.getDataTermino()).isEqualTo(UPDATED_DATA_TERMINO);
        assertThat(testProcessoAssincronoGrade.getUltimaAtualizacao()).isEqualTo(UPDATED_ULTIMA_ATUALIZACAO);
        assertThat(testProcessoAssincronoGrade.getQtdHorariosParaCriar()).isEqualTo(UPDATED_QTD_HORARIOS_PARA_CRIAR);
        assertThat(testProcessoAssincronoGrade.getTotalDeHorariosCriados()).isEqualTo(UPDATED_TOTAL_DE_HORARIOS_CRIADOS);
        assertThat(testProcessoAssincronoGrade.getTotalCompleto()).isEqualTo(UPDATED_TOTAL_COMPLETO);
        assertThat(testProcessoAssincronoGrade.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the ProcessoAssincronoGrade in Elasticsearch
        verify(mockProcessoAssincronoGradeSearchRepository, times(1)).save(testProcessoAssincronoGrade);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessoAssincronoGrade() throws Exception {
        int databaseSizeBeforeUpdate = processoAssincronoGradeRepository.findAll().size();

        // Create the ProcessoAssincronoGrade
        ProcessoAssincronoGradeDTO processoAssincronoGradeDTO = processoAssincronoGradeMapper.toDto(processoAssincronoGrade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoAssincronoGradeMockMvc.perform(put("/api/processo-assincrono-grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoAssincronoGradeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessoAssincronoGrade in the database
        List<ProcessoAssincronoGrade> processoAssincronoGradeList = processoAssincronoGradeRepository.findAll();
        assertThat(processoAssincronoGradeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProcessoAssincronoGrade in Elasticsearch
        verify(mockProcessoAssincronoGradeSearchRepository, times(0)).save(processoAssincronoGrade);
    }

    @Test
    @Transactional
    public void deleteProcessoAssincronoGrade() throws Exception {
        // Initialize the database
        processoAssincronoGradeRepository.saveAndFlush(processoAssincronoGrade);

        int databaseSizeBeforeDelete = processoAssincronoGradeRepository.findAll().size();

        // Delete the processoAssincronoGrade
        restProcessoAssincronoGradeMockMvc.perform(delete("/api/processo-assincrono-grades/{id}", processoAssincronoGrade.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessoAssincronoGrade> processoAssincronoGradeList = processoAssincronoGradeRepository.findAll();
        assertThat(processoAssincronoGradeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProcessoAssincronoGrade in Elasticsearch
        verify(mockProcessoAssincronoGradeSearchRepository, times(1)).deleteById(processoAssincronoGrade.getId());
    }

    @Test
    @Transactional
    public void searchProcessoAssincronoGrade() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        processoAssincronoGradeRepository.saveAndFlush(processoAssincronoGrade);
        when(mockProcessoAssincronoGradeSearchRepository.search(queryStringQuery("id:" + processoAssincronoGrade.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(processoAssincronoGrade), PageRequest.of(0, 1), 1));

        // Search the processoAssincronoGrade
        restProcessoAssincronoGradeMockMvc.perform(get("/api/_search/processo-assincrono-grades?query=id:" + processoAssincronoGrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processoAssincronoGrade.getId())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataTermino").value(hasItem(DEFAULT_DATA_TERMINO.toString())))
            .andExpect(jsonPath("$.[*].ultimaAtualizacao").value(hasItem(DEFAULT_ULTIMA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].qtdHorariosParaCriar").value(hasItem(DEFAULT_QTD_HORARIOS_PARA_CRIAR)))
            .andExpect(jsonPath("$.[*].totalDeHorariosCriados").value(hasItem(DEFAULT_TOTAL_DE_HORARIOS_CRIADOS)))
            .andExpect(jsonPath("$.[*].totalCompleto").value(hasItem(DEFAULT_TOTAL_COMPLETO.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
}
