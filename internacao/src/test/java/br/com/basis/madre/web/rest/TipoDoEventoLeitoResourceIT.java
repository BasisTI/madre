package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.TipoDoEventoLeito;
import br.com.basis.madre.repository.TipoDoEventoLeitoRepository;
import br.com.basis.madre.repository.search.TipoDoEventoLeitoSearchRepository;
import br.com.basis.madre.service.TipoDoEventoLeitoService;
import br.com.basis.madre.service.dto.TipoDoEventoLeitoDTO;
import br.com.basis.madre.service.mapper.TipoDoEventoLeitoMapper;
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
 * Integration tests for the {@link TipoDoEventoLeitoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class TipoDoEventoLeitoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TipoDoEventoLeitoRepository tipoDoEventoLeitoRepository;

    @Autowired
    private TipoDoEventoLeitoMapper tipoDoEventoLeitoMapper;

    @Autowired
    private TipoDoEventoLeitoService tipoDoEventoLeitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.TipoDoEventoLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDoEventoLeitoSearchRepository mockTipoDoEventoLeitoSearchRepository;

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

    private MockMvc restTipoDoEventoLeitoMockMvc;

    private TipoDoEventoLeito tipoDoEventoLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDoEventoLeitoResource tipoDoEventoLeitoResource = new TipoDoEventoLeitoResource(tipoDoEventoLeitoService);
        this.restTipoDoEventoLeitoMockMvc = MockMvcBuilders.standaloneSetup(tipoDoEventoLeitoResource)
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
    public static TipoDoEventoLeito createEntity(EntityManager em) {
        TipoDoEventoLeito tipoDoEventoLeito = new TipoDoEventoLeito()
            .nome(DEFAULT_NOME);
        return tipoDoEventoLeito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDoEventoLeito createUpdatedEntity(EntityManager em) {
        TipoDoEventoLeito tipoDoEventoLeito = new TipoDoEventoLeito()
            .nome(UPDATED_NOME);
        return tipoDoEventoLeito;
    }

    @BeforeEach
    public void initTest() {
        tipoDoEventoLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDoEventoLeito() throws Exception {
        int databaseSizeBeforeCreate = tipoDoEventoLeitoRepository.findAll().size();

        // Create the TipoDoEventoLeito
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO = tipoDoEventoLeitoMapper.toDto(tipoDoEventoLeito);
        restTipoDoEventoLeitoMockMvc.perform(post("/api/tipo-do-evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDoEventoLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDoEventoLeito in the database
        List<TipoDoEventoLeito> tipoDoEventoLeitoList = tipoDoEventoLeitoRepository.findAll();
        assertThat(tipoDoEventoLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDoEventoLeito testTipoDoEventoLeito = tipoDoEventoLeitoList.get(tipoDoEventoLeitoList.size() - 1);
        assertThat(testTipoDoEventoLeito.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the TipoDoEventoLeito in Elasticsearch
        verify(mockTipoDoEventoLeitoSearchRepository, times(1)).save(testTipoDoEventoLeito);
    }

    @Test
    @Transactional
    public void createTipoDoEventoLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDoEventoLeitoRepository.findAll().size();

        // Create the TipoDoEventoLeito with an existing ID
        tipoDoEventoLeito.setId(1L);
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO = tipoDoEventoLeitoMapper.toDto(tipoDoEventoLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDoEventoLeitoMockMvc.perform(post("/api/tipo-do-evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDoEventoLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDoEventoLeito in the database
        List<TipoDoEventoLeito> tipoDoEventoLeitoList = tipoDoEventoLeitoRepository.findAll();
        assertThat(tipoDoEventoLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDoEventoLeito in Elasticsearch
        verify(mockTipoDoEventoLeitoSearchRepository, times(0)).save(tipoDoEventoLeito);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDoEventoLeitoRepository.findAll().size();
        // set the field null
        tipoDoEventoLeito.setNome(null);

        // Create the TipoDoEventoLeito, which fails.
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO = tipoDoEventoLeitoMapper.toDto(tipoDoEventoLeito);

        restTipoDoEventoLeitoMockMvc.perform(post("/api/tipo-do-evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDoEventoLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDoEventoLeito> tipoDoEventoLeitoList = tipoDoEventoLeitoRepository.findAll();
        assertThat(tipoDoEventoLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDoEventoLeitos() throws Exception {
        // Initialize the database
        tipoDoEventoLeitoRepository.saveAndFlush(tipoDoEventoLeito);

        // Get all the tipoDoEventoLeitoList
        restTipoDoEventoLeitoMockMvc.perform(get("/api/tipo-do-evento-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDoEventoLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getTipoDoEventoLeito() throws Exception {
        // Initialize the database
        tipoDoEventoLeitoRepository.saveAndFlush(tipoDoEventoLeito);

        // Get the tipoDoEventoLeito
        restTipoDoEventoLeitoMockMvc.perform(get("/api/tipo-do-evento-leitos/{id}", tipoDoEventoLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDoEventoLeito.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDoEventoLeito() throws Exception {
        // Get the tipoDoEventoLeito
        restTipoDoEventoLeitoMockMvc.perform(get("/api/tipo-do-evento-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDoEventoLeito() throws Exception {
        // Initialize the database
        tipoDoEventoLeitoRepository.saveAndFlush(tipoDoEventoLeito);

        int databaseSizeBeforeUpdate = tipoDoEventoLeitoRepository.findAll().size();

        // Update the tipoDoEventoLeito
        TipoDoEventoLeito updatedTipoDoEventoLeito = tipoDoEventoLeitoRepository.findById(tipoDoEventoLeito.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDoEventoLeito are not directly saved in db
        em.detach(updatedTipoDoEventoLeito);
        updatedTipoDoEventoLeito
            .nome(UPDATED_NOME);
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO = tipoDoEventoLeitoMapper.toDto(updatedTipoDoEventoLeito);

        restTipoDoEventoLeitoMockMvc.perform(put("/api/tipo-do-evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDoEventoLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDoEventoLeito in the database
        List<TipoDoEventoLeito> tipoDoEventoLeitoList = tipoDoEventoLeitoRepository.findAll();
        assertThat(tipoDoEventoLeitoList).hasSize(databaseSizeBeforeUpdate);
        TipoDoEventoLeito testTipoDoEventoLeito = tipoDoEventoLeitoList.get(tipoDoEventoLeitoList.size() - 1);
        assertThat(testTipoDoEventoLeito.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the TipoDoEventoLeito in Elasticsearch
        verify(mockTipoDoEventoLeitoSearchRepository, times(1)).save(testTipoDoEventoLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDoEventoLeito() throws Exception {
        int databaseSizeBeforeUpdate = tipoDoEventoLeitoRepository.findAll().size();

        // Create the TipoDoEventoLeito
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO = tipoDoEventoLeitoMapper.toDto(tipoDoEventoLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDoEventoLeitoMockMvc.perform(put("/api/tipo-do-evento-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDoEventoLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDoEventoLeito in the database
        List<TipoDoEventoLeito> tipoDoEventoLeitoList = tipoDoEventoLeitoRepository.findAll();
        assertThat(tipoDoEventoLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDoEventoLeito in Elasticsearch
        verify(mockTipoDoEventoLeitoSearchRepository, times(0)).save(tipoDoEventoLeito);
    }

    @Test
    @Transactional
    public void deleteTipoDoEventoLeito() throws Exception {
        // Initialize the database
        tipoDoEventoLeitoRepository.saveAndFlush(tipoDoEventoLeito);

        int databaseSizeBeforeDelete = tipoDoEventoLeitoRepository.findAll().size();

        // Delete the tipoDoEventoLeito
        restTipoDoEventoLeitoMockMvc.perform(delete("/api/tipo-do-evento-leitos/{id}", tipoDoEventoLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDoEventoLeito> tipoDoEventoLeitoList = tipoDoEventoLeitoRepository.findAll();
        assertThat(tipoDoEventoLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDoEventoLeito in Elasticsearch
        verify(mockTipoDoEventoLeitoSearchRepository, times(1)).deleteById(tipoDoEventoLeito.getId());
    }

    @Test
    @Transactional
    public void searchTipoDoEventoLeito() throws Exception {
        // Initialize the database
        tipoDoEventoLeitoRepository.saveAndFlush(tipoDoEventoLeito);
        when(mockTipoDoEventoLeitoSearchRepository.search(queryStringQuery("id:" + tipoDoEventoLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDoEventoLeito), PageRequest.of(0, 1), 1));
        // Search the tipoDoEventoLeito
        restTipoDoEventoLeitoMockMvc.perform(get("/api/_search/tipo-do-evento-leitos?query=id:" + tipoDoEventoLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDoEventoLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDoEventoLeito.class);
        TipoDoEventoLeito tipoDoEventoLeito1 = new TipoDoEventoLeito();
        tipoDoEventoLeito1.setId(1L);
        TipoDoEventoLeito tipoDoEventoLeito2 = new TipoDoEventoLeito();
        tipoDoEventoLeito2.setId(tipoDoEventoLeito1.getId());
        assertThat(tipoDoEventoLeito1).isEqualTo(tipoDoEventoLeito2);
        tipoDoEventoLeito2.setId(2L);
        assertThat(tipoDoEventoLeito1).isNotEqualTo(tipoDoEventoLeito2);
        tipoDoEventoLeito1.setId(null);
        assertThat(tipoDoEventoLeito1).isNotEqualTo(tipoDoEventoLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDoEventoLeitoDTO.class);
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO1 = new TipoDoEventoLeitoDTO();
        tipoDoEventoLeitoDTO1.setId(1L);
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO2 = new TipoDoEventoLeitoDTO();
        assertThat(tipoDoEventoLeitoDTO1).isNotEqualTo(tipoDoEventoLeitoDTO2);
        tipoDoEventoLeitoDTO2.setId(tipoDoEventoLeitoDTO1.getId());
        assertThat(tipoDoEventoLeitoDTO1).isEqualTo(tipoDoEventoLeitoDTO2);
        tipoDoEventoLeitoDTO2.setId(2L);
        assertThat(tipoDoEventoLeitoDTO1).isNotEqualTo(tipoDoEventoLeitoDTO2);
        tipoDoEventoLeitoDTO1.setId(null);
        assertThat(tipoDoEventoLeitoDTO1).isNotEqualTo(tipoDoEventoLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDoEventoLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDoEventoLeitoMapper.fromId(null)).isNull();
    }
}
