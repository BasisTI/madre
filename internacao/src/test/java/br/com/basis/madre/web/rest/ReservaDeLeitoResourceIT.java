package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.ReservaDeLeito;
import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.repository.ReservaDeLeitoRepository;
import br.com.basis.madre.repository.search.ReservaDeLeitoSearchRepository;
import br.com.basis.madre.service.ReservaDeLeitoService;
import br.com.basis.madre.service.dto.ReservaDeLeitoDTO;
import br.com.basis.madre.service.mapper.ReservaDeLeitoMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReservaDeLeitoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class ReservaDeLeitoResourceIT {

    private static final LocalDate DEFAULT_DATA_DO_LANCAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DO_LANCAMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JUSTIFICATIVA = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIVA = "BBBBBBBBBB";

    @Autowired
    private ReservaDeLeitoRepository reservaDeLeitoRepository;

    @Autowired
    private ReservaDeLeitoMapper reservaDeLeitoMapper;

    @Autowired
    private ReservaDeLeitoService reservaDeLeitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.ReservaDeLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReservaDeLeitoSearchRepository mockReservaDeLeitoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReservaDeLeitoMockMvc;

    private ReservaDeLeito reservaDeLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReservaDeLeitoResource reservaDeLeitoResource = new ReservaDeLeitoResource(reservaDeLeitoService);
        this.restReservaDeLeitoMockMvc = MockMvcBuilders.standaloneSetup(reservaDeLeitoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservaDeLeito createEntity(EntityManager em) {
        ReservaDeLeito reservaDeLeito = new ReservaDeLeito()
            .dataDoLancamento(DEFAULT_DATA_DO_LANCAMENTO)
            .justificativa(DEFAULT_JUSTIFICATIVA);
        // Add required entity
        Leito leito;
        if (TestUtil.findAll(em, Leito.class).isEmpty()) {
            leito = LeitoResourceIT.createEntity(em);
            em.persist(leito);
            em.flush();
        } else {
            leito = TestUtil.findAll(em, Leito.class).get(0);
        }
        reservaDeLeito.setLeito(leito);
        return reservaDeLeito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservaDeLeito createUpdatedEntity(EntityManager em) {
        ReservaDeLeito reservaDeLeito = new ReservaDeLeito()
            .dataDoLancamento(UPDATED_DATA_DO_LANCAMENTO)
            .justificativa(UPDATED_JUSTIFICATIVA);
        // Add required entity
        Leito leito;
        if (TestUtil.findAll(em, Leito.class).isEmpty()) {
            leito = LeitoResourceIT.createUpdatedEntity(em);
            em.persist(leito);
            em.flush();
        } else {
            leito = TestUtil.findAll(em, Leito.class).get(0);
        }
        reservaDeLeito.setLeito(leito);
        return reservaDeLeito;
    }

    @BeforeEach
    public void initTest() {
        reservaDeLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservaDeLeito() throws Exception {
        int databaseSizeBeforeCreate = reservaDeLeitoRepository.findAll().size();

        // Create the ReservaDeLeito
        ReservaDeLeitoDTO reservaDeLeitoDTO = reservaDeLeitoMapper.toDto(reservaDeLeito);
        restReservaDeLeitoMockMvc.perform(post("/api/reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaDeLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the ReservaDeLeito in the database
        List<ReservaDeLeito> reservaDeLeitoList = reservaDeLeitoRepository.findAll();
        assertThat(reservaDeLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        ReservaDeLeito testReservaDeLeito = reservaDeLeitoList.get(reservaDeLeitoList.size() - 1);
        assertThat(testReservaDeLeito.getDataDoLancamento()).isEqualTo(DEFAULT_DATA_DO_LANCAMENTO);
        assertThat(testReservaDeLeito.getJustificativa()).isEqualTo(DEFAULT_JUSTIFICATIVA);

        // Validate the ReservaDeLeito in Elasticsearch
        verify(mockReservaDeLeitoSearchRepository, times(1)).save(testReservaDeLeito);
    }

    @Test
    @Transactional
    public void createReservaDeLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservaDeLeitoRepository.findAll().size();

        // Create the ReservaDeLeito with an existing ID
        reservaDeLeito.setId(1L);
        ReservaDeLeitoDTO reservaDeLeitoDTO = reservaDeLeitoMapper.toDto(reservaDeLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservaDeLeitoMockMvc.perform(post("/api/reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReservaDeLeito in the database
        List<ReservaDeLeito> reservaDeLeitoList = reservaDeLeitoRepository.findAll();
        assertThat(reservaDeLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReservaDeLeito in Elasticsearch
        verify(mockReservaDeLeitoSearchRepository, times(0)).save(reservaDeLeito);
    }


    @Test
    @Transactional
    public void checkDataDoLancamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservaDeLeitoRepository.findAll().size();
        // set the field null
        reservaDeLeito.setDataDoLancamento(null);

        // Create the ReservaDeLeito, which fails.
        ReservaDeLeitoDTO reservaDeLeitoDTO = reservaDeLeitoMapper.toDto(reservaDeLeito);

        restReservaDeLeitoMockMvc.perform(post("/api/reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<ReservaDeLeito> reservaDeLeitoList = reservaDeLeitoRepository.findAll();
        assertThat(reservaDeLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReservaDeLeitos() throws Exception {
        // Initialize the database
        reservaDeLeitoRepository.saveAndFlush(reservaDeLeito);

        // Get all the reservaDeLeitoList
        restReservaDeLeitoMockMvc.perform(get("/api/reserva-de-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservaDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDoLancamento").value(hasItem(DEFAULT_DATA_DO_LANCAMENTO.toString())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)));
    }
    
    @Test
    @Transactional
    public void getReservaDeLeito() throws Exception {
        // Initialize the database
        reservaDeLeitoRepository.saveAndFlush(reservaDeLeito);

        // Get the reservaDeLeito
        restReservaDeLeitoMockMvc.perform(get("/api/reserva-de-leitos/{id}", reservaDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reservaDeLeito.getId().intValue()))
            .andExpect(jsonPath("$.dataDoLancamento").value(DEFAULT_DATA_DO_LANCAMENTO.toString()))
            .andExpect(jsonPath("$.justificativa").value(DEFAULT_JUSTIFICATIVA));
    }

    @Test
    @Transactional
    public void getNonExistingReservaDeLeito() throws Exception {
        // Get the reservaDeLeito
        restReservaDeLeitoMockMvc.perform(get("/api/reserva-de-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservaDeLeito() throws Exception {
        // Initialize the database
        reservaDeLeitoRepository.saveAndFlush(reservaDeLeito);

        int databaseSizeBeforeUpdate = reservaDeLeitoRepository.findAll().size();

        // Update the reservaDeLeito
        ReservaDeLeito updatedReservaDeLeito = reservaDeLeitoRepository.findById(reservaDeLeito.getId()).get();
        // Disconnect from session so that the updates on updatedReservaDeLeito are not directly saved in db
        em.detach(updatedReservaDeLeito);
        updatedReservaDeLeito
            .dataDoLancamento(UPDATED_DATA_DO_LANCAMENTO)
            .justificativa(UPDATED_JUSTIFICATIVA);
        ReservaDeLeitoDTO reservaDeLeitoDTO = reservaDeLeitoMapper.toDto(updatedReservaDeLeito);

        restReservaDeLeitoMockMvc.perform(put("/api/reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaDeLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the ReservaDeLeito in the database
        List<ReservaDeLeito> reservaDeLeitoList = reservaDeLeitoRepository.findAll();
        assertThat(reservaDeLeitoList).hasSize(databaseSizeBeforeUpdate);
        ReservaDeLeito testReservaDeLeito = reservaDeLeitoList.get(reservaDeLeitoList.size() - 1);
        assertThat(testReservaDeLeito.getDataDoLancamento()).isEqualTo(UPDATED_DATA_DO_LANCAMENTO);
        assertThat(testReservaDeLeito.getJustificativa()).isEqualTo(UPDATED_JUSTIFICATIVA);

        // Validate the ReservaDeLeito in Elasticsearch
        verify(mockReservaDeLeitoSearchRepository, times(1)).save(testReservaDeLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingReservaDeLeito() throws Exception {
        int databaseSizeBeforeUpdate = reservaDeLeitoRepository.findAll().size();

        // Create the ReservaDeLeito
        ReservaDeLeitoDTO reservaDeLeitoDTO = reservaDeLeitoMapper.toDto(reservaDeLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservaDeLeitoMockMvc.perform(put("/api/reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReservaDeLeito in the database
        List<ReservaDeLeito> reservaDeLeitoList = reservaDeLeitoRepository.findAll();
        assertThat(reservaDeLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReservaDeLeito in Elasticsearch
        verify(mockReservaDeLeitoSearchRepository, times(0)).save(reservaDeLeito);
    }

    @Test
    @Transactional
    public void deleteReservaDeLeito() throws Exception {
        // Initialize the database
        reservaDeLeitoRepository.saveAndFlush(reservaDeLeito);

        int databaseSizeBeforeDelete = reservaDeLeitoRepository.findAll().size();

        // Delete the reservaDeLeito
        restReservaDeLeitoMockMvc.perform(delete("/api/reserva-de-leitos/{id}", reservaDeLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservaDeLeito> reservaDeLeitoList = reservaDeLeitoRepository.findAll();
        assertThat(reservaDeLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReservaDeLeito in Elasticsearch
        verify(mockReservaDeLeitoSearchRepository, times(1)).deleteById(reservaDeLeito.getId());
    }

    @Test
    @Transactional
    public void searchReservaDeLeito() throws Exception {
        // Initialize the database
        reservaDeLeitoRepository.saveAndFlush(reservaDeLeito);
        when(mockReservaDeLeitoSearchRepository.search(queryStringQuery("id:" + reservaDeLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(reservaDeLeito), PageRequest.of(0, 1), 1));
        // Search the reservaDeLeito
        restReservaDeLeitoMockMvc.perform(get("/api/_search/reserva-de-leitos?query=id:" + reservaDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservaDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDoLancamento").value(hasItem(DEFAULT_DATA_DO_LANCAMENTO.toString())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservaDeLeito.class);
        ReservaDeLeito reservaDeLeito1 = new ReservaDeLeito();
        reservaDeLeito1.setId(1L);
        ReservaDeLeito reservaDeLeito2 = new ReservaDeLeito();
        reservaDeLeito2.setId(reservaDeLeito1.getId());
        assertThat(reservaDeLeito1).isEqualTo(reservaDeLeito2);
        reservaDeLeito2.setId(2L);
        assertThat(reservaDeLeito1).isNotEqualTo(reservaDeLeito2);
        reservaDeLeito1.setId(null);
        assertThat(reservaDeLeito1).isNotEqualTo(reservaDeLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservaDeLeitoDTO.class);
        ReservaDeLeitoDTO reservaDeLeitoDTO1 = new ReservaDeLeitoDTO();
        reservaDeLeitoDTO1.setId(1L);
        ReservaDeLeitoDTO reservaDeLeitoDTO2 = new ReservaDeLeitoDTO();
        assertThat(reservaDeLeitoDTO1).isNotEqualTo(reservaDeLeitoDTO2);
        reservaDeLeitoDTO2.setId(reservaDeLeitoDTO1.getId());
        assertThat(reservaDeLeitoDTO1).isEqualTo(reservaDeLeitoDTO2);
        reservaDeLeitoDTO2.setId(2L);
        assertThat(reservaDeLeitoDTO1).isNotEqualTo(reservaDeLeitoDTO2);
        reservaDeLeitoDTO1.setId(null);
        assertThat(reservaDeLeitoDTO1).isNotEqualTo(reservaDeLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reservaDeLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reservaDeLeitoMapper.fromId(null)).isNull();
    }
}
