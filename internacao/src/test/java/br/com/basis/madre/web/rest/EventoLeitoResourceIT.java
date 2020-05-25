package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.EventoLeito;
import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.domain.MotivoDoBloqueioDeLeito;
import br.com.basis.madre.domain.TipoDoEventoLeito;
import br.com.basis.madre.repository.EventoLeitoRepository;
import br.com.basis.madre.repository.search.EventoLeitoSearchRepository;
import br.com.basis.madre.service.EventoLeitoService;
import br.com.basis.madre.service.LeitoService;
import br.com.basis.madre.service.dto.EventoLeitoDTO;
import br.com.basis.madre.service.mapper.EventoLeitoMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
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
 * Integration tests for the {@link EventoLeitoResource} REST controller.
 */
@RequiredArgsConstructor
@SpringBootTest(classes = InternacaoApp.class)
public class EventoLeitoResourceIT {

    private static final ZonedDateTime DEFAULT_DATA_DO_LANCAMENTO = ZonedDateTime.of(LocalDateTime.from(LocalDate.ofEpochDay(0L)), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATA_DO_LANCAMENTO = ZonedDateTime.of(LocalDateTime.from(LocalDate.ofEpochDay(1L)), ZoneId.systemDefault());

    private static final String DEFAULT_JUSTIFICATIVA = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIVA = "BBBBBBBBBB";

    @Autowired
    private EventoLeitoService eventoLeitoService;

    @Autowired
    private EventoLeitoRepository eventoLeitoRepository;

    @Autowired
    private EventoLeitoMapper eventoLeitoMapper;

    @Autowired
    private LeitoService leitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.EventoLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EventoLeitoSearchRepository mockEventoLeitoSearchRepository;

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

    private MockMvc restEventoLeitoMockMvc;

    private EventoLeito eventoLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventoLeitoResource eventoLeitoResource = new EventoLeitoResource(eventoLeitoService);
        this.restEventoLeitoMockMvc = MockMvcBuilders.standaloneSetup(eventoLeitoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static EventoLeito createEntity(EntityManager em) {
        EventoLeito eventoLeito = new EventoLeito()
            .dataDoLancamento(DEFAULT_DATA_DO_LANCAMENTO)
            .justificativa(DEFAULT_JUSTIFICATIVA);
        // Add required entity
        TipoDoEventoLeito tipoDoEventoLeito;
        if (TestUtil.findAll(em, TipoDoEventoLeito.class).isEmpty()) {
            tipoDoEventoLeito = TipoDoEventoLeitoResourceIT.createEntity(em);
            em.persist(tipoDoEventoLeito);
            em.flush();
        } else {
            tipoDoEventoLeito = TestUtil.findAll(em, TipoDoEventoLeito.class).get(0);
        }
        eventoLeito.setTipoDoEvento(tipoDoEventoLeito);
        // Add required entity
        Leito leito;
        if (TestUtil.findAll(em, Leito.class).isEmpty()) {
            leito = LeitoResourceIT.createEntity(em);
            em.persist(leito);
            em.flush();
        } else {
            leito = TestUtil.findAll(em, Leito.class).get(0);
        }
        eventoLeito.setLeito(leito);
        // Add required entity
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito;
        if (TestUtil.findAll(em, MotivoDoBloqueioDeLeito.class).isEmpty()) {
            motivoDoBloqueioDeLeito = MotivoDoBloqueioDeLeitoResourceIT.createEntity(em);
            em.persist(motivoDoBloqueioDeLeito);
            em.flush();
        } else {
            motivoDoBloqueioDeLeito = TestUtil.findAll(em, MotivoDoBloqueioDeLeito.class).get(0);
        }
        eventoLeito.setMotivo(motivoDoBloqueioDeLeito);
        return eventoLeito;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static EventoLeito createUpdatedEntity(EntityManager em) {
        EventoLeito eventoLeito = new EventoLeito()
            .dataDoLancamento(UPDATED_DATA_DO_LANCAMENTO)
            .justificativa(UPDATED_JUSTIFICATIVA);
        // Add required entity
        TipoDoEventoLeito tipoDoEventoLeito;
        if (TestUtil.findAll(em, TipoDoEventoLeito.class).isEmpty()) {
            tipoDoEventoLeito = TipoDoEventoLeitoResourceIT.createUpdatedEntity(em);
            em.persist(tipoDoEventoLeito);
            em.flush();
        } else {
            tipoDoEventoLeito = TestUtil.findAll(em, TipoDoEventoLeito.class).get(0);
        }
        eventoLeito.setTipoDoEvento(tipoDoEventoLeito);
        // Add required entity
        Leito leito;
        if (TestUtil.findAll(em, Leito.class).isEmpty()) {
            leito = LeitoResourceIT.createUpdatedEntity(em);
            em.persist(leito);
            em.flush();
        } else {
            leito = TestUtil.findAll(em, Leito.class).get(0);
        }
        eventoLeito.setLeito(leito);
        // Add required entity
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito;
        if (TestUtil.findAll(em, MotivoDoBloqueioDeLeito.class).isEmpty()) {
            motivoDoBloqueioDeLeito = MotivoDoBloqueioDeLeitoResourceIT.createUpdatedEntity(em);
            em.persist(motivoDoBloqueioDeLeito);
            em.flush();
        } else {
            motivoDoBloqueioDeLeito = TestUtil.findAll(em, MotivoDoBloqueioDeLeito.class).get(0);
        }
        eventoLeito.setMotivo(motivoDoBloqueioDeLeito);
        return eventoLeito;
    }

    @BeforeEach
    public void initTest() {
        eventoLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventoLeito() throws Exception {
        int databaseSizeBeforeCreate = eventoLeitoRepository.findAll().size();

        // Create the EventoLeito
        EventoLeitoDTO eventoLeitoDTO = eventoLeitoMapper.toDto(eventoLeito);
        restEventoLeitoMockMvc.perform(post("/api/evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the EventoLeito in the database
        List<EventoLeito> eventoLeitoList = eventoLeitoRepository.findAll();
        assertThat(eventoLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        EventoLeito testEventoLeito = eventoLeitoList.get(eventoLeitoList.size() - 1);
        assertThat(testEventoLeito.getDataDoLancamento()).isEqualTo(DEFAULT_DATA_DO_LANCAMENTO);
        assertThat(testEventoLeito.getJustificativa()).isEqualTo(DEFAULT_JUSTIFICATIVA);

        // Validate the EventoLeito in Elasticsearch
        verify(mockEventoLeitoSearchRepository, times(1)).save(testEventoLeito);
    }

    @Test
    @Transactional
    public void createEventoLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventoLeitoRepository.findAll().size();

        // Create the EventoLeito with an existing ID
        eventoLeito.setId(1L);
        EventoLeitoDTO eventoLeitoDTO = eventoLeitoMapper.toDto(eventoLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoLeitoMockMvc.perform(post("/api/evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventoLeito in the database
        List<EventoLeito> eventoLeitoList = eventoLeitoRepository.findAll();
        assertThat(eventoLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the EventoLeito in Elasticsearch
        verify(mockEventoLeitoSearchRepository, times(0)).save(eventoLeito);
    }


    @Test
    @Transactional
    public void checkDataDoLancamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoLeitoRepository.findAll().size();
        // set the field null
        eventoLeito.setDataDoLancamento(null);

        // Create the EventoLeito, which fails.
        EventoLeitoDTO eventoLeitoDTO = eventoLeitoMapper.toDto(eventoLeito);

        restEventoLeitoMockMvc.perform(post("/api/evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<EventoLeito> eventoLeitoList = eventoLeitoRepository.findAll();
        assertThat(eventoLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventoLeitos() throws Exception {
        // Initialize the database
        eventoLeitoRepository.saveAndFlush(eventoLeito);

        // Get all the eventoLeitoList
        restEventoLeitoMockMvc.perform(get("/api/evento-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventoLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDoLancamento")
                .value(hasItem(DEFAULT_DATA_DO_LANCAMENTO.toString())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)));
    }

    @Test
    @Transactional
    public void getEventoLeito() throws Exception {
        // Initialize the database
        eventoLeitoRepository.saveAndFlush(eventoLeito);

        // Get the eventoLeito
        restEventoLeitoMockMvc.perform(get("/api/evento-leitos/{id}", eventoLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventoLeito.getId().intValue()))
            .andExpect(jsonPath("$.dataDoLancamento").value(DEFAULT_DATA_DO_LANCAMENTO.toString()))
            .andExpect(jsonPath("$.justificativa").value(DEFAULT_JUSTIFICATIVA));
    }

    @Test
    @Transactional
    public void getNonExistingEventoLeito() throws Exception {
        // Get the eventoLeito
        restEventoLeitoMockMvc.perform(get("/api/evento-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventoLeito() throws Exception {
        // Initialize the database
        eventoLeitoRepository.saveAndFlush(eventoLeito);

        int databaseSizeBeforeUpdate = eventoLeitoRepository.findAll().size();

        // Update the eventoLeito
        EventoLeito updatedEventoLeito = eventoLeitoRepository.findById(eventoLeito.getId()).get();
        // Disconnect from session so that the updates on updatedEventoLeito are not directly saved in db
        em.detach(updatedEventoLeito);
        updatedEventoLeito
            .dataDoLancamento(UPDATED_DATA_DO_LANCAMENTO)
            .justificativa(UPDATED_JUSTIFICATIVA);
        EventoLeitoDTO eventoLeitoDTO = eventoLeitoMapper.toDto(updatedEventoLeito);

        restEventoLeitoMockMvc.perform(put("/api/evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the EventoLeito in the database
        List<EventoLeito> eventoLeitoList = eventoLeitoRepository.findAll();
        assertThat(eventoLeitoList).hasSize(databaseSizeBeforeUpdate);
        EventoLeito testEventoLeito = eventoLeitoList.get(eventoLeitoList.size() - 1);
        assertThat(testEventoLeito.getDataDoLancamento()).isEqualTo(UPDATED_DATA_DO_LANCAMENTO);
        assertThat(testEventoLeito.getJustificativa()).isEqualTo(UPDATED_JUSTIFICATIVA);

        // Validate the EventoLeito in Elasticsearch
        verify(mockEventoLeitoSearchRepository, times(1)).save(testEventoLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingEventoLeito() throws Exception {
        int databaseSizeBeforeUpdate = eventoLeitoRepository.findAll().size();

        // Create the EventoLeito
        EventoLeitoDTO eventoLeitoDTO = eventoLeitoMapper.toDto(eventoLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoLeitoMockMvc.perform(put("/api/evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventoLeito in the database
        List<EventoLeito> eventoLeitoList = eventoLeitoRepository.findAll();
        assertThat(eventoLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EventoLeito in Elasticsearch
        verify(mockEventoLeitoSearchRepository, times(0)).save(eventoLeito);
    }

    @Test
    @Transactional
    public void deleteEventoLeito() throws Exception {
        // Initialize the database
        eventoLeitoRepository.saveAndFlush(eventoLeito);

        int databaseSizeBeforeDelete = eventoLeitoRepository.findAll().size();

        // Delete the eventoLeito
        restEventoLeitoMockMvc.perform(delete("/api/evento-leitos/{id}", eventoLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventoLeito> eventoLeitoList = eventoLeitoRepository.findAll();
        assertThat(eventoLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EventoLeito in Elasticsearch
        verify(mockEventoLeitoSearchRepository, times(1)).deleteById(eventoLeito.getId());
    }

    @Test
    @Transactional
    public void searchEventoLeito() throws Exception {
        // Initialize the database
        eventoLeitoRepository.saveAndFlush(eventoLeito);
        when(mockEventoLeitoSearchRepository
            .search(queryStringQuery("id:" + eventoLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(
                new PageImpl<>(Collections.singletonList(eventoLeito), PageRequest.of(0, 1), 1));
        // Search the eventoLeito
        restEventoLeitoMockMvc
            .perform(get("/api/_search/evento-leitos?query=id:" + eventoLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventoLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDoLancamento")
                .value(hasItem(DEFAULT_DATA_DO_LANCAMENTO.toString())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoLeito.class);
        EventoLeito eventoLeito1 = new EventoLeito();
        eventoLeito1.setId(1L);
        EventoLeito eventoLeito2 = new EventoLeito();
        eventoLeito2.setId(eventoLeito1.getId());
        assertThat(eventoLeito1).isEqualTo(eventoLeito2);
        eventoLeito2.setId(2L);
        assertThat(eventoLeito1).isNotEqualTo(eventoLeito2);
        eventoLeito1.setId(null);
        assertThat(eventoLeito1).isNotEqualTo(eventoLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoLeitoDTO.class);
        EventoLeitoDTO eventoLeitoDTO1 = new EventoLeitoDTO();
        eventoLeitoDTO1.setId(1L);
        EventoLeitoDTO eventoLeitoDTO2 = new EventoLeitoDTO();
        assertThat(eventoLeitoDTO1).isNotEqualTo(eventoLeitoDTO2);
        eventoLeitoDTO2.setId(eventoLeitoDTO1.getId());
        assertThat(eventoLeitoDTO1).isEqualTo(eventoLeitoDTO2);
        eventoLeitoDTO2.setId(2L);
        assertThat(eventoLeitoDTO1).isNotEqualTo(eventoLeitoDTO2);
        eventoLeitoDTO1.setId(null);
        assertThat(eventoLeitoDTO1).isNotEqualTo(eventoLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventoLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventoLeitoMapper.fromId(null)).isNull();
    }
}
