package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.HorarioLivre;
import br.com.basis.madre.madreexames.repository.HorarioLivreRepository;
import br.com.basis.madre.madreexames.repository.search.HorarioLivreSearchRepository;
import br.com.basis.madre.madreexames.service.HorarioLivreService;
import br.com.basis.madre.madreexames.service.dto.HorarioLivreDTO;
import br.com.basis.madre.madreexames.service.mapper.HorarioLivreMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HorarioLivreResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HorarioLivreResourceIT {

    private static final LocalDate DEFAULT_DATA_HORA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_HORA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_HORA_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_HORA_FIM = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_OCUPADO = false;
    private static final Boolean UPDATED_OCUPADO = true;

    @Autowired
    private HorarioLivreRepository horarioLivreRepository;

    @Autowired
    private HorarioLivreMapper horarioLivreMapper;

    @Autowired
    private HorarioLivreService horarioLivreService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.HorarioLivreSearchRepositoryMockConfiguration
     */
    @Autowired
    private HorarioLivreSearchRepository mockHorarioLivreSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHorarioLivreMockMvc;

    private HorarioLivre horarioLivre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioLivre createEntity(EntityManager em) {
        HorarioLivre horarioLivre = new HorarioLivre()
            .dataHoraInicio(DEFAULT_DATA_HORA_INICIO)
            .dataHoraFim(DEFAULT_DATA_HORA_FIM)
            .ocupado(DEFAULT_OCUPADO);
        return horarioLivre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioLivre createUpdatedEntity(EntityManager em) {
        HorarioLivre horarioLivre = new HorarioLivre()
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM)
            .ocupado(UPDATED_OCUPADO);
        return horarioLivre;
    }

    @BeforeEach
    public void initTest() {
        horarioLivre = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorarioLivre() throws Exception {
        int databaseSizeBeforeCreate = horarioLivreRepository.findAll().size();
        // Create the HorarioLivre
        HorarioLivreDTO horarioLivreDTO = horarioLivreMapper.toDto(horarioLivre);
        restHorarioLivreMockMvc.perform(post("/api/horario-livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioLivreDTO)))
            .andExpect(status().isCreated());

        // Validate the HorarioLivre in the database
        List<HorarioLivre> horarioLivreList = horarioLivreRepository.findAll();
        assertThat(horarioLivreList).hasSize(databaseSizeBeforeCreate + 1);
        HorarioLivre testHorarioLivre = horarioLivreList.get(horarioLivreList.size() - 1);
        assertThat(testHorarioLivre.getDataHoraInicio()).isEqualTo(DEFAULT_DATA_HORA_INICIO);
        assertThat(testHorarioLivre.getDataHoraFim()).isEqualTo(DEFAULT_DATA_HORA_FIM);
        assertThat(testHorarioLivre.isOcupado()).isEqualTo(DEFAULT_OCUPADO);

        // Validate the HorarioLivre in Elasticsearch
        verify(mockHorarioLivreSearchRepository, times(1)).save(testHorarioLivre);
    }

    @Test
    @Transactional
    public void createHorarioLivreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioLivreRepository.findAll().size();

        // Create the HorarioLivre with an existing ID
        horarioLivre.setId(1L);
        HorarioLivreDTO horarioLivreDTO = horarioLivreMapper.toDto(horarioLivre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioLivreMockMvc.perform(post("/api/horario-livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioLivreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioLivre in the database
        List<HorarioLivre> horarioLivreList = horarioLivreRepository.findAll();
        assertThat(horarioLivreList).hasSize(databaseSizeBeforeCreate);

        // Validate the HorarioLivre in Elasticsearch
        verify(mockHorarioLivreSearchRepository, times(0)).save(horarioLivre);
    }


    @Test
    @Transactional
    public void checkDataHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioLivreRepository.findAll().size();
        // set the field null
        horarioLivre.setDataHoraInicio(null);

        // Create the HorarioLivre, which fails.
        HorarioLivreDTO horarioLivreDTO = horarioLivreMapper.toDto(horarioLivre);


        restHorarioLivreMockMvc.perform(post("/api/horario-livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioLivreDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioLivre> horarioLivreList = horarioLivreRepository.findAll();
        assertThat(horarioLivreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataHoraFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioLivreRepository.findAll().size();
        // set the field null
        horarioLivre.setDataHoraFim(null);

        // Create the HorarioLivre, which fails.
        HorarioLivreDTO horarioLivreDTO = horarioLivreMapper.toDto(horarioLivre);


        restHorarioLivreMockMvc.perform(post("/api/horario-livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioLivreDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioLivre> horarioLivreList = horarioLivreRepository.findAll();
        assertThat(horarioLivreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcupadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioLivreRepository.findAll().size();
        // set the field null
        horarioLivre.setOcupado(null);

        // Create the HorarioLivre, which fails.
        HorarioLivreDTO horarioLivreDTO = horarioLivreMapper.toDto(horarioLivre);


        restHorarioLivreMockMvc.perform(post("/api/horario-livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioLivreDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioLivre> horarioLivreList = horarioLivreRepository.findAll();
        assertThat(horarioLivreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorarioLivres() throws Exception {
        // Initialize the database
        horarioLivreRepository.saveAndFlush(horarioLivre);

        // Get all the horarioLivreList
        restHorarioLivreMockMvc.perform(get("/api/horario-livres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioLivre.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHoraInicio").value(hasItem(DEFAULT_DATA_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataHoraFim").value(hasItem(DEFAULT_DATA_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].ocupado").value(hasItem(DEFAULT_OCUPADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getHorarioLivre() throws Exception {
        // Initialize the database
        horarioLivreRepository.saveAndFlush(horarioLivre);

        // Get the horarioLivre
        restHorarioLivreMockMvc.perform(get("/api/horario-livres/{id}", horarioLivre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horarioLivre.getId().intValue()))
            .andExpect(jsonPath("$.dataHoraInicio").value(DEFAULT_DATA_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.dataHoraFim").value(DEFAULT_DATA_HORA_FIM.toString()))
            .andExpect(jsonPath("$.ocupado").value(DEFAULT_OCUPADO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingHorarioLivre() throws Exception {
        // Get the horarioLivre
        restHorarioLivreMockMvc.perform(get("/api/horario-livres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorarioLivre() throws Exception {
        // Initialize the database
        horarioLivreRepository.saveAndFlush(horarioLivre);

        int databaseSizeBeforeUpdate = horarioLivreRepository.findAll().size();

        // Update the horarioLivre
        HorarioLivre updatedHorarioLivre = horarioLivreRepository.findById(horarioLivre.getId()).get();
        // Disconnect from session so that the updates on updatedHorarioLivre are not directly saved in db
        em.detach(updatedHorarioLivre);
        updatedHorarioLivre
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM)
            .ocupado(UPDATED_OCUPADO);
        HorarioLivreDTO horarioLivreDTO = horarioLivreMapper.toDto(updatedHorarioLivre);

        restHorarioLivreMockMvc.perform(put("/api/horario-livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioLivreDTO)))
            .andExpect(status().isOk());

        // Validate the HorarioLivre in the database
        List<HorarioLivre> horarioLivreList = horarioLivreRepository.findAll();
        assertThat(horarioLivreList).hasSize(databaseSizeBeforeUpdate);
        HorarioLivre testHorarioLivre = horarioLivreList.get(horarioLivreList.size() - 1);
        assertThat(testHorarioLivre.getDataHoraInicio()).isEqualTo(UPDATED_DATA_HORA_INICIO);
        assertThat(testHorarioLivre.getDataHoraFim()).isEqualTo(UPDATED_DATA_HORA_FIM);
        assertThat(testHorarioLivre.isOcupado()).isEqualTo(UPDATED_OCUPADO);

        // Validate the HorarioLivre in Elasticsearch
        verify(mockHorarioLivreSearchRepository, times(1)).save(testHorarioLivre);
    }

    @Test
    @Transactional
    public void updateNonExistingHorarioLivre() throws Exception {
        int databaseSizeBeforeUpdate = horarioLivreRepository.findAll().size();

        // Create the HorarioLivre
        HorarioLivreDTO horarioLivreDTO = horarioLivreMapper.toDto(horarioLivre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioLivreMockMvc.perform(put("/api/horario-livres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horarioLivreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioLivre in the database
        List<HorarioLivre> horarioLivreList = horarioLivreRepository.findAll();
        assertThat(horarioLivreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HorarioLivre in Elasticsearch
        verify(mockHorarioLivreSearchRepository, times(0)).save(horarioLivre);
    }

    @Test
    @Transactional
    public void deleteHorarioLivre() throws Exception {
        // Initialize the database
        horarioLivreRepository.saveAndFlush(horarioLivre);

        int databaseSizeBeforeDelete = horarioLivreRepository.findAll().size();

        // Delete the horarioLivre
        restHorarioLivreMockMvc.perform(delete("/api/horario-livres/{id}", horarioLivre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HorarioLivre> horarioLivreList = horarioLivreRepository.findAll();
        assertThat(horarioLivreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HorarioLivre in Elasticsearch
        verify(mockHorarioLivreSearchRepository, times(1)).deleteById(horarioLivre.getId());
    }

    @Test
    @Transactional
    public void searchHorarioLivre() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        horarioLivreRepository.saveAndFlush(horarioLivre);
        when(mockHorarioLivreSearchRepository.search(queryStringQuery("id:" + horarioLivre.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(horarioLivre), PageRequest.of(0, 1), 1));

        // Search the horarioLivre
        restHorarioLivreMockMvc.perform(get("/api/_search/horario-livres?query=id:" + horarioLivre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioLivre.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHoraInicio").value(hasItem(DEFAULT_DATA_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataHoraFim").value(hasItem(DEFAULT_DATA_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].ocupado").value(hasItem(DEFAULT_OCUPADO.booleanValue())));
    }
}
