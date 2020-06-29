package br.com.basis.consulta.web.rest;

import br.com.basis.consulta.MadreconsultaApp;
import br.com.basis.consulta.domain.FormaDeAgendamento;
import br.com.basis.consulta.repository.FormaDeAgendamentoRepository;
import br.com.basis.consulta.repository.search.FormaDeAgendamentoSearchRepository;
import br.com.basis.consulta.service.FormaDeAgendamentoService;
import br.com.basis.consulta.service.dto.FormaDeAgendamentoDTO;
import br.com.basis.consulta.service.mapper.FormaDeAgendamentoMapper;
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
import java.util.Collections;
import java.util.List;

import static br.com.basis.consulta.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FormaDeAgendamentoResource} REST controller.
 */
@SpringBootTest(classes = MadreconsultaApp.class)
public class FormaDeAgendamentoResourceIT {

    private static final Long DEFAULT_NUMERO_AUTORIZACAO = 1L;
    private static final Long UPDATED_NUMERO_AUTORIZACAO = 2L;

    private static final String DEFAULT_AUTORIZACAO = "AAAAAAAAAA";
    private static final String UPDATED_AUTORIZACAO = "BBBBBBBBBB";

    @Autowired
    private FormaDeAgendamentoRepository formaDeAgendamentoRepository;

    @Autowired
    private FormaDeAgendamentoMapper formaDeAgendamentoMapper;

    @Autowired
    private FormaDeAgendamentoService formaDeAgendamentoService;

    /**
     * This repository is mocked in the br.com.basis.consulta.repository.search test package.
     *
     * @see br.com.basis.consulta.repository.search.FormaDeAgendamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private FormaDeAgendamentoSearchRepository mockFormaDeAgendamentoSearchRepository;

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

    private MockMvc restFormaDeAgendamentoMockMvc;

    private FormaDeAgendamento formaDeAgendamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormaDeAgendamentoResource formaDeAgendamentoResource = new FormaDeAgendamentoResource(formaDeAgendamentoService);
        this.restFormaDeAgendamentoMockMvc = MockMvcBuilders.standaloneSetup(formaDeAgendamentoResource)
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
    public static FormaDeAgendamento createEntity(EntityManager em) {
        FormaDeAgendamento formaDeAgendamento = new FormaDeAgendamento()
            .numeroAutorizacao(DEFAULT_NUMERO_AUTORIZACAO)
            .autorizacao(DEFAULT_AUTORIZACAO);
        return formaDeAgendamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaDeAgendamento createUpdatedEntity(EntityManager em) {
        FormaDeAgendamento formaDeAgendamento = new FormaDeAgendamento()
            .numeroAutorizacao(UPDATED_NUMERO_AUTORIZACAO)
            .autorizacao(UPDATED_AUTORIZACAO);
        return formaDeAgendamento;
    }

    @BeforeEach
    public void initTest() {
        formaDeAgendamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormaDeAgendamento() throws Exception {
        int databaseSizeBeforeCreate = formaDeAgendamentoRepository.findAll().size();

        // Create the FormaDeAgendamento
        FormaDeAgendamentoDTO formaDeAgendamentoDTO = formaDeAgendamentoMapper.toDto(formaDeAgendamento);
        restFormaDeAgendamentoMockMvc.perform(post("/api/forma-de-agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeAgendamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the FormaDeAgendamento in the database
        List<FormaDeAgendamento> formaDeAgendamentoList = formaDeAgendamentoRepository.findAll();
        assertThat(formaDeAgendamentoList).hasSize(databaseSizeBeforeCreate + 1);
        FormaDeAgendamento testFormaDeAgendamento = formaDeAgendamentoList.get(formaDeAgendamentoList.size() - 1);
        assertThat(testFormaDeAgendamento.getNumeroAutorizacao()).isEqualTo(DEFAULT_NUMERO_AUTORIZACAO);
        assertThat(testFormaDeAgendamento.getAutorizacao()).isEqualTo(DEFAULT_AUTORIZACAO);

        // Validate the FormaDeAgendamento in Elasticsearch
        verify(mockFormaDeAgendamentoSearchRepository, times(1)).save(testFormaDeAgendamento);
    }

    @Test
    @Transactional
    public void createFormaDeAgendamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formaDeAgendamentoRepository.findAll().size();

        // Create the FormaDeAgendamento with an existing ID
        formaDeAgendamento.setId(1L);
        FormaDeAgendamentoDTO formaDeAgendamentoDTO = formaDeAgendamentoMapper.toDto(formaDeAgendamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaDeAgendamentoMockMvc.perform(post("/api/forma-de-agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormaDeAgendamento in the database
        List<FormaDeAgendamento> formaDeAgendamentoList = formaDeAgendamentoRepository.findAll();
        assertThat(formaDeAgendamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the FormaDeAgendamento in Elasticsearch
        verify(mockFormaDeAgendamentoSearchRepository, times(0)).save(formaDeAgendamento);
    }


    @Test
    @Transactional
    public void checkNumeroAutorizacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaDeAgendamentoRepository.findAll().size();
        // set the field null
        formaDeAgendamento.setNumeroAutorizacao(null);

        // Create the FormaDeAgendamento, which fails.
        FormaDeAgendamentoDTO formaDeAgendamentoDTO = formaDeAgendamentoMapper.toDto(formaDeAgendamento);

        restFormaDeAgendamentoMockMvc.perform(post("/api/forma-de-agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        List<FormaDeAgendamento> formaDeAgendamentoList = formaDeAgendamentoRepository.findAll();
        assertThat(formaDeAgendamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutorizacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaDeAgendamentoRepository.findAll().size();
        // set the field null
        formaDeAgendamento.setAutorizacao(null);

        // Create the FormaDeAgendamento, which fails.
        FormaDeAgendamentoDTO formaDeAgendamentoDTO = formaDeAgendamentoMapper.toDto(formaDeAgendamento);

        restFormaDeAgendamentoMockMvc.perform(post("/api/forma-de-agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        List<FormaDeAgendamento> formaDeAgendamentoList = formaDeAgendamentoRepository.findAll();
        assertThat(formaDeAgendamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormaDeAgendamentos() throws Exception {
        // Initialize the database
        formaDeAgendamentoRepository.saveAndFlush(formaDeAgendamento);

        // Get all the formaDeAgendamentoList
        restFormaDeAgendamentoMockMvc.perform(get("/api/forma-de-agendamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaDeAgendamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroAutorizacao").value(hasItem(DEFAULT_NUMERO_AUTORIZACAO.intValue())))
            .andExpect(jsonPath("$.[*].autorizacao").value(hasItem(DEFAULT_AUTORIZACAO)));
    }
    
    @Test
    @Transactional
    public void getFormaDeAgendamento() throws Exception {
        // Initialize the database
        formaDeAgendamentoRepository.saveAndFlush(formaDeAgendamento);

        // Get the formaDeAgendamento
        restFormaDeAgendamentoMockMvc.perform(get("/api/forma-de-agendamentos/{id}", formaDeAgendamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formaDeAgendamento.getId().intValue()))
            .andExpect(jsonPath("$.numeroAutorizacao").value(DEFAULT_NUMERO_AUTORIZACAO.intValue()))
            .andExpect(jsonPath("$.autorizacao").value(DEFAULT_AUTORIZACAO));
    }

    @Test
    @Transactional
    public void getNonExistingFormaDeAgendamento() throws Exception {
        // Get the formaDeAgendamento
        restFormaDeAgendamentoMockMvc.perform(get("/api/forma-de-agendamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormaDeAgendamento() throws Exception {
        // Initialize the database
        formaDeAgendamentoRepository.saveAndFlush(formaDeAgendamento);

        int databaseSizeBeforeUpdate = formaDeAgendamentoRepository.findAll().size();

        // Update the formaDeAgendamento
        FormaDeAgendamento updatedFormaDeAgendamento = formaDeAgendamentoRepository.findById(formaDeAgendamento.getId()).get();
        // Disconnect from session so that the updates on updatedFormaDeAgendamento are not directly saved in db
        em.detach(updatedFormaDeAgendamento);
        updatedFormaDeAgendamento
            .numeroAutorizacao(UPDATED_NUMERO_AUTORIZACAO)
            .autorizacao(UPDATED_AUTORIZACAO);
        FormaDeAgendamentoDTO formaDeAgendamentoDTO = formaDeAgendamentoMapper.toDto(updatedFormaDeAgendamento);

        restFormaDeAgendamentoMockMvc.perform(put("/api/forma-de-agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeAgendamentoDTO)))
            .andExpect(status().isOk());

        // Validate the FormaDeAgendamento in the database
        List<FormaDeAgendamento> formaDeAgendamentoList = formaDeAgendamentoRepository.findAll();
        assertThat(formaDeAgendamentoList).hasSize(databaseSizeBeforeUpdate);
        FormaDeAgendamento testFormaDeAgendamento = formaDeAgendamentoList.get(formaDeAgendamentoList.size() - 1);
        assertThat(testFormaDeAgendamento.getNumeroAutorizacao()).isEqualTo(UPDATED_NUMERO_AUTORIZACAO);
        assertThat(testFormaDeAgendamento.getAutorizacao()).isEqualTo(UPDATED_AUTORIZACAO);

        // Validate the FormaDeAgendamento in Elasticsearch
        verify(mockFormaDeAgendamentoSearchRepository, times(1)).save(testFormaDeAgendamento);
    }

    @Test
    @Transactional
    public void updateNonExistingFormaDeAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = formaDeAgendamentoRepository.findAll().size();

        // Create the FormaDeAgendamento
        FormaDeAgendamentoDTO formaDeAgendamentoDTO = formaDeAgendamentoMapper.toDto(formaDeAgendamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaDeAgendamentoMockMvc.perform(put("/api/forma-de-agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeAgendamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormaDeAgendamento in the database
        List<FormaDeAgendamento> formaDeAgendamentoList = formaDeAgendamentoRepository.findAll();
        assertThat(formaDeAgendamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FormaDeAgendamento in Elasticsearch
        verify(mockFormaDeAgendamentoSearchRepository, times(0)).save(formaDeAgendamento);
    }

    @Test
    @Transactional
    public void deleteFormaDeAgendamento() throws Exception {
        // Initialize the database
        formaDeAgendamentoRepository.saveAndFlush(formaDeAgendamento);

        int databaseSizeBeforeDelete = formaDeAgendamentoRepository.findAll().size();

        // Delete the formaDeAgendamento
        restFormaDeAgendamentoMockMvc.perform(delete("/api/forma-de-agendamentos/{id}", formaDeAgendamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormaDeAgendamento> formaDeAgendamentoList = formaDeAgendamentoRepository.findAll();
        assertThat(formaDeAgendamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FormaDeAgendamento in Elasticsearch
        verify(mockFormaDeAgendamentoSearchRepository, times(1)).deleteById(formaDeAgendamento.getId());
    }

    @Test
    @Transactional
    public void searchFormaDeAgendamento() throws Exception {
        // Initialize the database
        formaDeAgendamentoRepository.saveAndFlush(formaDeAgendamento);
        when(mockFormaDeAgendamentoSearchRepository.search(queryStringQuery("id:" + formaDeAgendamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(formaDeAgendamento), PageRequest.of(0, 1), 1));
        // Search the formaDeAgendamento
        restFormaDeAgendamentoMockMvc.perform(get("/api/_search/forma-de-agendamentos?query=id:" + formaDeAgendamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaDeAgendamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroAutorizacao").value(hasItem(DEFAULT_NUMERO_AUTORIZACAO.intValue())))
            .andExpect(jsonPath("$.[*].autorizacao").value(hasItem(DEFAULT_AUTORIZACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaDeAgendamento.class);
        FormaDeAgendamento formaDeAgendamento1 = new FormaDeAgendamento();
        formaDeAgendamento1.setId(1L);
        FormaDeAgendamento formaDeAgendamento2 = new FormaDeAgendamento();
        formaDeAgendamento2.setId(formaDeAgendamento1.getId());
        assertThat(formaDeAgendamento1).isEqualTo(formaDeAgendamento2);
        formaDeAgendamento2.setId(2L);
        assertThat(formaDeAgendamento1).isNotEqualTo(formaDeAgendamento2);
        formaDeAgendamento1.setId(null);
        assertThat(formaDeAgendamento1).isNotEqualTo(formaDeAgendamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaDeAgendamentoDTO.class);
        FormaDeAgendamentoDTO formaDeAgendamentoDTO1 = new FormaDeAgendamentoDTO();
        formaDeAgendamentoDTO1.setId(1L);
        FormaDeAgendamentoDTO formaDeAgendamentoDTO2 = new FormaDeAgendamentoDTO();
        assertThat(formaDeAgendamentoDTO1).isNotEqualTo(formaDeAgendamentoDTO2);
        formaDeAgendamentoDTO2.setId(formaDeAgendamentoDTO1.getId());
        assertThat(formaDeAgendamentoDTO1).isEqualTo(formaDeAgendamentoDTO2);
        formaDeAgendamentoDTO2.setId(2L);
        assertThat(formaDeAgendamentoDTO1).isNotEqualTo(formaDeAgendamentoDTO2);
        formaDeAgendamentoDTO1.setId(null);
        assertThat(formaDeAgendamentoDTO1).isNotEqualTo(formaDeAgendamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formaDeAgendamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formaDeAgendamentoMapper.fromId(null)).isNull();
    }
}
