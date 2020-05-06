package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.TipoDaReservaDeLeito;
import br.com.basis.madre.repository.TipoDaReservaDeLeitoRepository;
import br.com.basis.madre.repository.search.TipoDaReservaDeLeitoSearchRepository;
import br.com.basis.madre.service.TipoDaReservaDeLeitoService;
import br.com.basis.madre.service.dto.TipoDaReservaDeLeitoDTO;
import br.com.basis.madre.service.mapper.TipoDaReservaDeLeitoMapper;
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

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoDaReservaDeLeitoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class TipoDaReservaDeLeitoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TipoDaReservaDeLeitoRepository tipoDaReservaDeLeitoRepository;

    @Autowired
    private TipoDaReservaDeLeitoMapper tipoDaReservaDeLeitoMapper;

    @Autowired
    private TipoDaReservaDeLeitoService tipoDaReservaDeLeitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.TipoDaReservaDeLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDaReservaDeLeitoSearchRepository mockTipoDaReservaDeLeitoSearchRepository;

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

    private MockMvc restTipoDaReservaDeLeitoMockMvc;

    private TipoDaReservaDeLeito tipoDaReservaDeLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDaReservaDeLeitoResource tipoDaReservaDeLeitoResource = new TipoDaReservaDeLeitoResource(tipoDaReservaDeLeitoService);
        this.restTipoDaReservaDeLeitoMockMvc = MockMvcBuilders.standaloneSetup(tipoDaReservaDeLeitoResource)
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
    public static TipoDaReservaDeLeito createEntity(EntityManager em) {
        TipoDaReservaDeLeito tipoDaReservaDeLeito = new TipoDaReservaDeLeito()
            .nome(DEFAULT_NOME);
        return tipoDaReservaDeLeito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDaReservaDeLeito createUpdatedEntity(EntityManager em) {
        TipoDaReservaDeLeito tipoDaReservaDeLeito = new TipoDaReservaDeLeito()
            .nome(UPDATED_NOME);
        return tipoDaReservaDeLeito;
    }

    @BeforeEach
    public void initTest() {
        tipoDaReservaDeLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDaReservaDeLeito() throws Exception {
        int databaseSizeBeforeCreate = tipoDaReservaDeLeitoRepository.findAll().size();

        // Create the TipoDaReservaDeLeito
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO = tipoDaReservaDeLeitoMapper.toDto(tipoDaReservaDeLeito);
        restTipoDaReservaDeLeitoMockMvc.perform(post("/api/tipo-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDaReservaDeLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDaReservaDeLeito in the database
        List<TipoDaReservaDeLeito> tipoDaReservaDeLeitoList = tipoDaReservaDeLeitoRepository.findAll();
        assertThat(tipoDaReservaDeLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDaReservaDeLeito testTipoDaReservaDeLeito = tipoDaReservaDeLeitoList.get(tipoDaReservaDeLeitoList.size() - 1);
        assertThat(testTipoDaReservaDeLeito.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the TipoDaReservaDeLeito in Elasticsearch
        verify(mockTipoDaReservaDeLeitoSearchRepository, times(1)).save(testTipoDaReservaDeLeito);
    }

    @Test
    @Transactional
    public void createTipoDaReservaDeLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDaReservaDeLeitoRepository.findAll().size();

        // Create the TipoDaReservaDeLeito with an existing ID
        tipoDaReservaDeLeito.setId(1L);
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO = tipoDaReservaDeLeitoMapper.toDto(tipoDaReservaDeLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDaReservaDeLeitoMockMvc.perform(post("/api/tipo-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDaReservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDaReservaDeLeito in the database
        List<TipoDaReservaDeLeito> tipoDaReservaDeLeitoList = tipoDaReservaDeLeitoRepository.findAll();
        assertThat(tipoDaReservaDeLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDaReservaDeLeito in Elasticsearch
        verify(mockTipoDaReservaDeLeitoSearchRepository, times(0)).save(tipoDaReservaDeLeito);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDaReservaDeLeitoRepository.findAll().size();
        // set the field null
        tipoDaReservaDeLeito.setNome(null);

        // Create the TipoDaReservaDeLeito, which fails.
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO = tipoDaReservaDeLeitoMapper.toDto(tipoDaReservaDeLeito);

        restTipoDaReservaDeLeitoMockMvc.perform(post("/api/tipo-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDaReservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDaReservaDeLeito> tipoDaReservaDeLeitoList = tipoDaReservaDeLeitoRepository.findAll();
        assertThat(tipoDaReservaDeLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDaReservaDeLeitos() throws Exception {
        // Initialize the database
        tipoDaReservaDeLeitoRepository.saveAndFlush(tipoDaReservaDeLeito);

        // Get all the tipoDaReservaDeLeitoList
        restTipoDaReservaDeLeitoMockMvc.perform(get("/api/tipo-da-reserva-de-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDaReservaDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTipoDaReservaDeLeito() throws Exception {
        // Initialize the database
        tipoDaReservaDeLeitoRepository.saveAndFlush(tipoDaReservaDeLeito);

        // Get the tipoDaReservaDeLeito
        restTipoDaReservaDeLeitoMockMvc.perform(get("/api/tipo-da-reserva-de-leitos/{id}", tipoDaReservaDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDaReservaDeLeito.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDaReservaDeLeito() throws Exception {
        // Get the tipoDaReservaDeLeito
        restTipoDaReservaDeLeitoMockMvc.perform(get("/api/tipo-da-reserva-de-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDaReservaDeLeito() throws Exception {
        // Initialize the database
        tipoDaReservaDeLeitoRepository.saveAndFlush(tipoDaReservaDeLeito);

        int databaseSizeBeforeUpdate = tipoDaReservaDeLeitoRepository.findAll().size();

        // Update the tipoDaReservaDeLeito
        TipoDaReservaDeLeito updatedTipoDaReservaDeLeito = tipoDaReservaDeLeitoRepository.findById(tipoDaReservaDeLeito.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDaReservaDeLeito are not directly saved in db
        em.detach(updatedTipoDaReservaDeLeito);
        updatedTipoDaReservaDeLeito
            .nome(UPDATED_NOME);
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO = tipoDaReservaDeLeitoMapper.toDto(updatedTipoDaReservaDeLeito);

        restTipoDaReservaDeLeitoMockMvc.perform(put("/api/tipo-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDaReservaDeLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDaReservaDeLeito in the database
        List<TipoDaReservaDeLeito> tipoDaReservaDeLeitoList = tipoDaReservaDeLeitoRepository.findAll();
        assertThat(tipoDaReservaDeLeitoList).hasSize(databaseSizeBeforeUpdate);
        TipoDaReservaDeLeito testTipoDaReservaDeLeito = tipoDaReservaDeLeitoList.get(tipoDaReservaDeLeitoList.size() - 1);
        assertThat(testTipoDaReservaDeLeito.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the TipoDaReservaDeLeito in Elasticsearch
        verify(mockTipoDaReservaDeLeitoSearchRepository, times(1)).save(testTipoDaReservaDeLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDaReservaDeLeito() throws Exception {
        int databaseSizeBeforeUpdate = tipoDaReservaDeLeitoRepository.findAll().size();

        // Create the TipoDaReservaDeLeito
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO = tipoDaReservaDeLeitoMapper.toDto(tipoDaReservaDeLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDaReservaDeLeitoMockMvc.perform(put("/api/tipo-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDaReservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDaReservaDeLeito in the database
        List<TipoDaReservaDeLeito> tipoDaReservaDeLeitoList = tipoDaReservaDeLeitoRepository.findAll();
        assertThat(tipoDaReservaDeLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDaReservaDeLeito in Elasticsearch
        verify(mockTipoDaReservaDeLeitoSearchRepository, times(0)).save(tipoDaReservaDeLeito);
    }

    @Test
    @Transactional
    public void deleteTipoDaReservaDeLeito() throws Exception {
        // Initialize the database
        tipoDaReservaDeLeitoRepository.saveAndFlush(tipoDaReservaDeLeito);

        int databaseSizeBeforeDelete = tipoDaReservaDeLeitoRepository.findAll().size();

        // Delete the tipoDaReservaDeLeito
        restTipoDaReservaDeLeitoMockMvc.perform(delete("/api/tipo-da-reserva-de-leitos/{id}", tipoDaReservaDeLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDaReservaDeLeito> tipoDaReservaDeLeitoList = tipoDaReservaDeLeitoRepository.findAll();
        assertThat(tipoDaReservaDeLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDaReservaDeLeito in Elasticsearch
        verify(mockTipoDaReservaDeLeitoSearchRepository, times(1)).deleteById(tipoDaReservaDeLeito.getId());
    }

    @Test
    @Transactional
    public void searchTipoDaReservaDeLeito() throws Exception {
        // Initialize the database
        tipoDaReservaDeLeitoRepository.saveAndFlush(tipoDaReservaDeLeito);
        when(mockTipoDaReservaDeLeitoSearchRepository.search(queryStringQuery("id:" + tipoDaReservaDeLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDaReservaDeLeito), PageRequest.of(0, 1), 1));
        // Search the tipoDaReservaDeLeito
        restTipoDaReservaDeLeitoMockMvc.perform(get("/api/_search/tipo-da-reserva-de-leitos?query=id:" + tipoDaReservaDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDaReservaDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDaReservaDeLeito.class);
        TipoDaReservaDeLeito tipoDaReservaDeLeito1 = new TipoDaReservaDeLeito();
        tipoDaReservaDeLeito1.setId(1L);
        TipoDaReservaDeLeito tipoDaReservaDeLeito2 = new TipoDaReservaDeLeito();
        tipoDaReservaDeLeito2.setId(tipoDaReservaDeLeito1.getId());
        assertThat(tipoDaReservaDeLeito1).isEqualTo(tipoDaReservaDeLeito2);
        tipoDaReservaDeLeito2.setId(2L);
        assertThat(tipoDaReservaDeLeito1).isNotEqualTo(tipoDaReservaDeLeito2);
        tipoDaReservaDeLeito1.setId(null);
        assertThat(tipoDaReservaDeLeito1).isNotEqualTo(tipoDaReservaDeLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDaReservaDeLeitoDTO.class);
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO1 = new TipoDaReservaDeLeitoDTO();
        tipoDaReservaDeLeitoDTO1.setId(1L);
        TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO2 = new TipoDaReservaDeLeitoDTO();
        assertThat(tipoDaReservaDeLeitoDTO1).isNotEqualTo(tipoDaReservaDeLeitoDTO2);
        tipoDaReservaDeLeitoDTO2.setId(tipoDaReservaDeLeitoDTO1.getId());
        assertThat(tipoDaReservaDeLeitoDTO1).isEqualTo(tipoDaReservaDeLeitoDTO2);
        tipoDaReservaDeLeitoDTO2.setId(2L);
        assertThat(tipoDaReservaDeLeitoDTO1).isNotEqualTo(tipoDaReservaDeLeitoDTO2);
        tipoDaReservaDeLeitoDTO1.setId(null);
        assertThat(tipoDaReservaDeLeitoDTO1).isNotEqualTo(tipoDaReservaDeLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDaReservaDeLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDaReservaDeLeitoMapper.fromId(null)).isNull();
    }
}
