package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.HorarioExame;
import br.com.basis.madre.madreexames.repository.HorarioExameRepository;
import br.com.basis.madre.madreexames.repository.search.HorarioExameSearchRepository;
import br.com.basis.madre.madreexames.service.HorarioExameService;
import br.com.basis.madre.madreexames.service.dto.HorarioExameDTO;
import br.com.basis.madre.madreexames.service.mapper.HorarioExameMapper;

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

/**
 * Integration tests for the {@link HorarioExameResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HorarioExameResourceIT {

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_LIVRE = false;
    private static final Boolean UPDATED_LIVRE = true;

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_EXCLUSIVO = false;
    private static final Boolean UPDATED_EXCLUSIVO = true;

    @Autowired
    private HorarioExameRepository horarioExameRepository;

    @Autowired
    private HorarioExameMapper horarioExameMapper;

    @Autowired
    private HorarioExameService horarioExameService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.HorarioExameSearchRepositoryMockConfiguration
     */
    @Autowired
    private HorarioExameSearchRepository mockHorarioExameSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHorarioExameMockMvc;

    private HorarioExame horarioExame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioExame createEntity(EntityManager em) {
        HorarioExame horarioExame = new HorarioExame()
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFim(DEFAULT_HORA_FIM)
            .livre(DEFAULT_LIVRE)
            .ativo(DEFAULT_ATIVO)
            .exclusivo(DEFAULT_EXCLUSIVO);
        return horarioExame;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioExame createUpdatedEntity(EntityManager em) {
        HorarioExame horarioExame = new HorarioExame()
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .livre(UPDATED_LIVRE)
            .ativo(UPDATED_ATIVO)
            .exclusivo(UPDATED_EXCLUSIVO);
        return horarioExame;
    }

    @BeforeEach
    public void initTest() {
        horarioExame = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorarioExame() throws Exception {
        int databaseSizeBeforeCreate = horarioExameRepository.findAll().size();
        // Create the HorarioExame
        HorarioExameDTO horarioExameDTO = horarioExameMapper.toDto(horarioExame);
        restHorarioExameMockMvc.perform(post("/api/horario-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioExameDTO)))
            .andExpect(status().isCreated());

        // Validate the HorarioExame in the database
        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeCreate + 1);
        HorarioExame testHorarioExame = horarioExameList.get(horarioExameList.size() - 1);
        assertThat(testHorarioExame.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testHorarioExame.getHoraFim()).isEqualTo(DEFAULT_HORA_FIM);
        assertThat(testHorarioExame.isLivre()).isEqualTo(DEFAULT_LIVRE);
        assertThat(testHorarioExame.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testHorarioExame.isExclusivo()).isEqualTo(DEFAULT_EXCLUSIVO);

        // Validate the HorarioExame in Elasticsearch
        verify(mockHorarioExameSearchRepository, times(1)).save(testHorarioExame);
    }

    @Test
    @Transactional
    public void createHorarioExameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioExameRepository.findAll().size();

        // Create the HorarioExame with an existing ID
        horarioExame.setId(1L);
        HorarioExameDTO horarioExameDTO = horarioExameMapper.toDto(horarioExame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioExameMockMvc.perform(post("/api/horario-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioExame in the database
        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeCreate);

        // Validate the HorarioExame in Elasticsearch
        verify(mockHorarioExameSearchRepository, times(0)).save(horarioExame);
    }


    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioExameRepository.findAll().size();
        // set the field null
        horarioExame.setHoraInicio(null);

        // Create the HorarioExame, which fails.
        HorarioExameDTO horarioExameDTO = horarioExameMapper.toDto(horarioExame);


        restHorarioExameMockMvc.perform(post("/api/horario-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioExameDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLivreIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioExameRepository.findAll().size();
        // set the field null
        horarioExame.setLivre(null);

        // Create the HorarioExame, which fails.
        HorarioExameDTO horarioExameDTO = horarioExameMapper.toDto(horarioExame);


        restHorarioExameMockMvc.perform(post("/api/horario-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioExameDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioExameRepository.findAll().size();
        // set the field null
        horarioExame.setAtivo(null);

        // Create the HorarioExame, which fails.
        HorarioExameDTO horarioExameDTO = horarioExameMapper.toDto(horarioExame);


        restHorarioExameMockMvc.perform(post("/api/horario-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioExameDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExclusivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioExameRepository.findAll().size();
        // set the field null
        horarioExame.setExclusivo(null);

        // Create the HorarioExame, which fails.
        HorarioExameDTO horarioExameDTO = horarioExameMapper.toDto(horarioExame);


        restHorarioExameMockMvc.perform(post("/api/horario-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioExameDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorarioExames() throws Exception {
        // Initialize the database
        horarioExameRepository.saveAndFlush(horarioExame);

        // Get all the horarioExameList
        restHorarioExameMockMvc.perform(get("/api/horario-exames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].livre").value(hasItem(DEFAULT_LIVRE.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].exclusivo").value(hasItem(DEFAULT_EXCLUSIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getHorarioExame() throws Exception {
        // Initialize the database
        horarioExameRepository.saveAndFlush(horarioExame);

        // Get the horarioExame
        restHorarioExameMockMvc.perform(get("/api/horario-exames/{id}", horarioExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horarioExame.getId().intValue()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFim").value(DEFAULT_HORA_FIM.toString()))
            .andExpect(jsonPath("$.livre").value(DEFAULT_LIVRE.booleanValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.exclusivo").value(DEFAULT_EXCLUSIVO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingHorarioExame() throws Exception {
        // Get the horarioExame
        restHorarioExameMockMvc.perform(get("/api/horario-exames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorarioExame() throws Exception {
        // Initialize the database
        horarioExameRepository.saveAndFlush(horarioExame);

        int databaseSizeBeforeUpdate = horarioExameRepository.findAll().size();

        // Update the horarioExame
        HorarioExame updatedHorarioExame = horarioExameRepository.findById(horarioExame.getId()).get();
        // Disconnect from session so that the updates on updatedHorarioExame are not directly saved in db
        em.detach(updatedHorarioExame);
        updatedHorarioExame
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .livre(UPDATED_LIVRE)
            .ativo(UPDATED_ATIVO)
            .exclusivo(UPDATED_EXCLUSIVO);
        HorarioExameDTO horarioExameDTO = horarioExameMapper.toDto(updatedHorarioExame);

        restHorarioExameMockMvc.perform(put("/api/horario-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioExameDTO)))
            .andExpect(status().isOk());

        // Validate the HorarioExame in the database
        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeUpdate);
        HorarioExame testHorarioExame = horarioExameList.get(horarioExameList.size() - 1);
        assertThat(testHorarioExame.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testHorarioExame.getHoraFim()).isEqualTo(UPDATED_HORA_FIM);
        assertThat(testHorarioExame.isLivre()).isEqualTo(UPDATED_LIVRE);
        assertThat(testHorarioExame.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testHorarioExame.isExclusivo()).isEqualTo(UPDATED_EXCLUSIVO);

        // Validate the HorarioExame in Elasticsearch
        verify(mockHorarioExameSearchRepository, times(1)).save(testHorarioExame);
    }

    @Test
    @Transactional
    public void updateNonExistingHorarioExame() throws Exception {
        int databaseSizeBeforeUpdate = horarioExameRepository.findAll().size();

        // Create the HorarioExame
        HorarioExameDTO horarioExameDTO = horarioExameMapper.toDto(horarioExame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioExameMockMvc.perform(put("/api/horario-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioExame in the database
        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HorarioExame in Elasticsearch
        verify(mockHorarioExameSearchRepository, times(0)).save(horarioExame);
    }

    @Test
    @Transactional
    public void deleteHorarioExame() throws Exception {
        // Initialize the database
        horarioExameRepository.saveAndFlush(horarioExame);

        int databaseSizeBeforeDelete = horarioExameRepository.findAll().size();

        // Delete the horarioExame
        restHorarioExameMockMvc.perform(delete("/api/horario-exames/{id}", horarioExame.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HorarioExame> horarioExameList = horarioExameRepository.findAll();
        assertThat(horarioExameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HorarioExame in Elasticsearch
        verify(mockHorarioExameSearchRepository, times(1)).deleteById(horarioExame.getId());
    }

    @Test
    @Transactional
    public void searchHorarioExame() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        horarioExameRepository.saveAndFlush(horarioExame);
        when(mockHorarioExameSearchRepository.search(queryStringQuery("id:" + horarioExame.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(horarioExame), PageRequest.of(0, 1), 1));

        // Search the horarioExame
        restHorarioExameMockMvc.perform(get("/api/_search/horario-exames?query=id:" + horarioExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].livre").value(hasItem(DEFAULT_LIVRE.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].exclusivo").value(hasItem(DEFAULT_EXCLUSIVO.booleanValue())));
    }
}
