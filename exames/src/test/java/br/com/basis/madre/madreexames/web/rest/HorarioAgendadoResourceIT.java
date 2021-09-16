package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.HorarioAgendado;
import br.com.basis.madre.madreexames.domain.enumeration.Dia;
import br.com.basis.madre.madreexames.repository.HorarioAgendadoRepository;
import br.com.basis.madre.madreexames.repository.search.HorarioAgendadoSearchRepository;
import br.com.basis.madre.madreexames.service.HorarioAgendadoService;
import br.com.basis.madre.madreexames.service.dto.HorarioAgendadoDTO;
import br.com.basis.madre.madreexames.service.mapper.HorarioAgendadoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Integration tests for the {@link HorarioAgendadoResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HorarioAgendadoResourceIT {

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NUMERO_DE_HORARIOS = 1;
    private static final Integer UPDATED_NUMERO_DE_HORARIOS = 2;

    private static final Dia DEFAULT_DIA = Dia.DOMINGO;
    private static final Dia UPDATED_DIA = Dia.SEGUNDA_FEIRA;

    private static final Duration DEFAULT_DURACAO = Duration.ofHours(6);
    private static final Duration UPDATED_DURACAO = Duration.ofHours(12);

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_EXCLUSIVO = false;
    private static final Boolean UPDATED_EXCLUSIVO = true;

    @Autowired
    private HorarioAgendadoRepository horarioAgendadoRepository;

    @Autowired
    private HorarioAgendadoMapper horarioAgendadoMapper;

    @Autowired
    private HorarioAgendadoService horarioAgendadoService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.HorarioAgendadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private HorarioAgendadoSearchRepository mockHorarioAgendadoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHorarioAgendadoMockMvc;

    private HorarioAgendado horarioAgendado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioAgendado createEntity(EntityManager em) {
        HorarioAgendado horarioAgendado = new HorarioAgendado()
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFim(DEFAULT_HORA_FIM)
            .numeroDeHorarios(DEFAULT_NUMERO_DE_HORARIOS)
            .dia(DEFAULT_DIA)
            .duracao(DEFAULT_DURACAO)
            .ativo(DEFAULT_ATIVO)
            .exclusivo(DEFAULT_EXCLUSIVO);
        return horarioAgendado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioAgendado createUpdatedEntity(EntityManager em) {
        HorarioAgendado horarioAgendado = new HorarioAgendado()
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .numeroDeHorarios(UPDATED_NUMERO_DE_HORARIOS)
            .dia(UPDATED_DIA)
            .duracao(UPDATED_DURACAO)
            .ativo(UPDATED_ATIVO)
            .exclusivo(UPDATED_EXCLUSIVO);
        return horarioAgendado;
    }

    @BeforeEach
    public void initTest() {
        horarioAgendado = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorarioAgendado() throws Exception {
        int databaseSizeBeforeCreate = horarioAgendadoRepository.findAll().size();
        // Create the HorarioAgendado
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);
        restHorarioAgendadoMockMvc.perform(post("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isCreated());

        // Validate the HorarioAgendado in the database
        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeCreate + 1);
        HorarioAgendado testHorarioAgendado = horarioAgendadoList.get(horarioAgendadoList.size() - 1);
        assertThat(testHorarioAgendado.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testHorarioAgendado.getHoraFim()).isEqualTo(DEFAULT_HORA_FIM);
        assertThat(testHorarioAgendado.getNumeroDeHorarios()).isEqualTo(DEFAULT_NUMERO_DE_HORARIOS);
        assertThat(testHorarioAgendado.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testHorarioAgendado.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testHorarioAgendado.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testHorarioAgendado.isExclusivo()).isEqualTo(DEFAULT_EXCLUSIVO);

        // Validate the HorarioAgendado in Elasticsearch
        verify(mockHorarioAgendadoSearchRepository, times(1)).save(horarioAgendadoDTO);
    }

    @Test
    @Transactional
    public void createHorarioAgendadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioAgendadoRepository.findAll().size();

        // Create the HorarioAgendado with an existing ID
        horarioAgendado.setId(1L);
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioAgendadoMockMvc.perform(post("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioAgendado in the database
        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the HorarioAgendado in Elasticsearch
        verify(mockHorarioAgendadoSearchRepository, times(0)).save(horarioAgendadoDTO);
    }


    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioAgendadoRepository.findAll().size();
        // set the field null
        horarioAgendado.setHoraInicio(null);

        // Create the HorarioAgendado, which fails.
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);


        restHorarioAgendadoMockMvc.perform(post("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioAgendadoRepository.findAll().size();
        // set the field null
        horarioAgendado.setDia(null);

        // Create the HorarioAgendado, which fails.
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);


        restHorarioAgendadoMockMvc.perform(post("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDuracaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioAgendadoRepository.findAll().size();
        // set the field null
        horarioAgendado.setDuracao(null);

        // Create the HorarioAgendado, which fails.
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);


        restHorarioAgendadoMockMvc.perform(post("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioAgendadoRepository.findAll().size();
        // set the field null
        horarioAgendado.setAtivo(null);

        // Create the HorarioAgendado, which fails.
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);


        restHorarioAgendadoMockMvc.perform(post("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExclusivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioAgendadoRepository.findAll().size();
        // set the field null
        horarioAgendado.setExclusivo(null);

        // Create the HorarioAgendado, which fails.
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);


        restHorarioAgendadoMockMvc.perform(post("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorarioAgendados() throws Exception {
        // Initialize the database
        horarioAgendadoRepository.saveAndFlush(horarioAgendado);

        // Get all the horarioAgendadoList
        restHorarioAgendadoMockMvc.perform(get("/api/horario-agendados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioAgendado.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].numeroDeHorarios").value(hasItem(DEFAULT_NUMERO_DE_HORARIOS)))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].exclusivo").value(hasItem(DEFAULT_EXCLUSIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getHorarioAgendado() throws Exception {
        // Initialize the database
        horarioAgendadoRepository.saveAndFlush(horarioAgendado);

        // Get the horarioAgendado
        restHorarioAgendadoMockMvc.perform(get("/api/horario-agendados/{id}", horarioAgendado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horarioAgendado.getId().intValue()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFim").value(DEFAULT_HORA_FIM.toString()))
            .andExpect(jsonPath("$.numeroDeHorarios").value(DEFAULT_NUMERO_DE_HORARIOS))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.exclusivo").value(DEFAULT_EXCLUSIVO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingHorarioAgendado() throws Exception {
        // Get the horarioAgendado
        restHorarioAgendadoMockMvc.perform(get("/api/horario-agendados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorarioAgendado() throws Exception {
        // Initialize the database
        horarioAgendadoRepository.saveAndFlush(horarioAgendado);

        int databaseSizeBeforeUpdate = horarioAgendadoRepository.findAll().size();

        // Update the horarioAgendado
        HorarioAgendado updatedHorarioAgendado = horarioAgendadoRepository.findById(horarioAgendado.getId()).get();
        // Disconnect from session so that the updates on updatedHorarioAgendado are not directly saved in db
        em.detach(updatedHorarioAgendado);
        updatedHorarioAgendado
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .numeroDeHorarios(UPDATED_NUMERO_DE_HORARIOS)
            .dia(UPDATED_DIA)
            .duracao(UPDATED_DURACAO)
            .ativo(UPDATED_ATIVO)
            .exclusivo(UPDATED_EXCLUSIVO);
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(updatedHorarioAgendado);

        restHorarioAgendadoMockMvc.perform(put("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isOk());

        // Validate the HorarioAgendado in the database
        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeUpdate);
        HorarioAgendado testHorarioAgendado = horarioAgendadoList.get(horarioAgendadoList.size() - 1);
        assertThat(testHorarioAgendado.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testHorarioAgendado.getHoraFim()).isEqualTo(UPDATED_HORA_FIM);
        assertThat(testHorarioAgendado.getNumeroDeHorarios()).isEqualTo(UPDATED_NUMERO_DE_HORARIOS);
        assertThat(testHorarioAgendado.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testHorarioAgendado.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testHorarioAgendado.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testHorarioAgendado.isExclusivo()).isEqualTo(UPDATED_EXCLUSIVO);

        // Validate the HorarioAgendado in Elasticsearch
        verify(mockHorarioAgendadoSearchRepository, times(1)).save(horarioAgendadoDTO);
    }

    @Test
    @Transactional
    public void updateNonExistingHorarioAgendado() throws Exception {
        int databaseSizeBeforeUpdate = horarioAgendadoRepository.findAll().size();

        // Create the HorarioAgendado
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioAgendadoMockMvc.perform(put("/api/horario-agendados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioAgendadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioAgendado in the database
        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HorarioAgendado in Elasticsearch
        verify(mockHorarioAgendadoSearchRepository, times(0)).save(horarioAgendadoDTO);
    }

    @Test
    @Transactional
    public void deleteHorarioAgendado() throws Exception {
        // Initialize the database
        horarioAgendadoRepository.saveAndFlush(horarioAgendado);

        int databaseSizeBeforeDelete = horarioAgendadoRepository.findAll().size();

        // Delete the horarioAgendado
        restHorarioAgendadoMockMvc.perform(delete("/api/horario-agendados/{id}", horarioAgendado.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HorarioAgendado> horarioAgendadoList = horarioAgendadoRepository.findAll();
        assertThat(horarioAgendadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HorarioAgendado in Elasticsearch
        verify(mockHorarioAgendadoSearchRepository, times(1)).deleteById(horarioAgendado.getId());
    }

    @Test
    @Transactional
    public void searchHorarioAgendado() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        horarioAgendadoRepository.saveAndFlush(horarioAgendado);
        HorarioAgendadoDTO horarioAgendadoDTO = horarioAgendadoMapper.toDto(horarioAgendado);
        when(mockHorarioAgendadoSearchRepository.search(queryStringQuery("id:" + horarioAgendado.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(horarioAgendadoDTO), PageRequest.of(0, 1), 1));

        // Search the horarioAgendado
        restHorarioAgendadoMockMvc.perform(get("/api/_search/horario-agendados?query=id:" + horarioAgendado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioAgendado.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].numeroDeHorarios").value(hasItem(DEFAULT_NUMERO_DE_HORARIOS)))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].exclusivo").value(hasItem(DEFAULT_EXCLUSIVO.booleanValue())));
    }
}
