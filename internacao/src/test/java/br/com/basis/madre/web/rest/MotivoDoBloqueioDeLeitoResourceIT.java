package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.MotivoDoBloqueioDeLeito;
import br.com.basis.madre.repository.MotivoDoBloqueioDeLeitoRepository;
import br.com.basis.madre.repository.search.MotivoDoBloqueioDeLeitoSearchRepository;
import br.com.basis.madre.service.MotivoDoBloqueioDeLeitoService;
import br.com.basis.madre.service.dto.MotivoDoBloqueioDeLeitoDTO;
import br.com.basis.madre.service.mapper.MotivoDoBloqueioDeLeitoMapper;
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
 * Integration tests for the {@link MotivoDoBloqueioDeLeitoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class MotivoDoBloqueioDeLeitoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private MotivoDoBloqueioDeLeitoRepository motivoDoBloqueioDeLeitoRepository;

    @Autowired
    private MotivoDoBloqueioDeLeitoMapper motivoDoBloqueioDeLeitoMapper;

    @Autowired
    private MotivoDoBloqueioDeLeitoService motivoDoBloqueioDeLeitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.MotivoDoBloqueioDeLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private MotivoDoBloqueioDeLeitoSearchRepository mockMotivoDoBloqueioDeLeitoSearchRepository;

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

    private MockMvc restMotivoDoBloqueioDeLeitoMockMvc;

    private MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotivoDoBloqueioDeLeitoResource motivoDoBloqueioDeLeitoResource = new MotivoDoBloqueioDeLeitoResource(motivoDoBloqueioDeLeitoService);
        this.restMotivoDoBloqueioDeLeitoMockMvc = MockMvcBuilders.standaloneSetup(motivoDoBloqueioDeLeitoResource)
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
    public static MotivoDoBloqueioDeLeito createEntity(EntityManager em) {
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito = new MotivoDoBloqueioDeLeito()
            .nome(DEFAULT_NOME);
        return motivoDoBloqueioDeLeito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivoDoBloqueioDeLeito createUpdatedEntity(EntityManager em) {
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito = new MotivoDoBloqueioDeLeito()
            .nome(UPDATED_NOME);
        return motivoDoBloqueioDeLeito;
    }

    @BeforeEach
    public void initTest() {
        motivoDoBloqueioDeLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotivoDoBloqueioDeLeito() throws Exception {
        int databaseSizeBeforeCreate = motivoDoBloqueioDeLeitoRepository.findAll().size();

        // Create the MotivoDoBloqueioDeLeito
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO = motivoDoBloqueioDeLeitoMapper.toDto(motivoDoBloqueioDeLeito);
        restMotivoDoBloqueioDeLeitoMockMvc.perform(post("/api/motivo-do-bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoBloqueioDeLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the MotivoDoBloqueioDeLeito in the database
        List<MotivoDoBloqueioDeLeito> motivoDoBloqueioDeLeitoList = motivoDoBloqueioDeLeitoRepository.findAll();
        assertThat(motivoDoBloqueioDeLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        MotivoDoBloqueioDeLeito testMotivoDoBloqueioDeLeito = motivoDoBloqueioDeLeitoList.get(motivoDoBloqueioDeLeitoList.size() - 1);
        assertThat(testMotivoDoBloqueioDeLeito.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the MotivoDoBloqueioDeLeito in Elasticsearch
        verify(mockMotivoDoBloqueioDeLeitoSearchRepository, times(1)).save(testMotivoDoBloqueioDeLeito);
    }

    @Test
    @Transactional
    public void createMotivoDoBloqueioDeLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motivoDoBloqueioDeLeitoRepository.findAll().size();

        // Create the MotivoDoBloqueioDeLeito with an existing ID
        motivoDoBloqueioDeLeito.setId(1L);
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO = motivoDoBloqueioDeLeitoMapper.toDto(motivoDoBloqueioDeLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotivoDoBloqueioDeLeitoMockMvc.perform(post("/api/motivo-do-bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoBloqueioDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MotivoDoBloqueioDeLeito in the database
        List<MotivoDoBloqueioDeLeito> motivoDoBloqueioDeLeitoList = motivoDoBloqueioDeLeitoRepository.findAll();
        assertThat(motivoDoBloqueioDeLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the MotivoDoBloqueioDeLeito in Elasticsearch
        verify(mockMotivoDoBloqueioDeLeitoSearchRepository, times(0)).save(motivoDoBloqueioDeLeito);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = motivoDoBloqueioDeLeitoRepository.findAll().size();
        // set the field null
        motivoDoBloqueioDeLeito.setNome(null);

        // Create the MotivoDoBloqueioDeLeito, which fails.
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO = motivoDoBloqueioDeLeitoMapper.toDto(motivoDoBloqueioDeLeito);

        restMotivoDoBloqueioDeLeitoMockMvc.perform(post("/api/motivo-do-bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoBloqueioDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<MotivoDoBloqueioDeLeito> motivoDoBloqueioDeLeitoList = motivoDoBloqueioDeLeitoRepository.findAll();
        assertThat(motivoDoBloqueioDeLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMotivoDoBloqueioDeLeitos() throws Exception {
        // Initialize the database
        motivoDoBloqueioDeLeitoRepository.saveAndFlush(motivoDoBloqueioDeLeito);

        // Get all the motivoDoBloqueioDeLeitoList
        restMotivoDoBloqueioDeLeitoMockMvc.perform(get("/api/motivo-do-bloqueio-de-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivoDoBloqueioDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getMotivoDoBloqueioDeLeito() throws Exception {
        // Initialize the database
        motivoDoBloqueioDeLeitoRepository.saveAndFlush(motivoDoBloqueioDeLeito);

        // Get the motivoDoBloqueioDeLeito
        restMotivoDoBloqueioDeLeitoMockMvc.perform(get("/api/motivo-do-bloqueio-de-leitos/{id}", motivoDoBloqueioDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motivoDoBloqueioDeLeito.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingMotivoDoBloqueioDeLeito() throws Exception {
        // Get the motivoDoBloqueioDeLeito
        restMotivoDoBloqueioDeLeitoMockMvc.perform(get("/api/motivo-do-bloqueio-de-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotivoDoBloqueioDeLeito() throws Exception {
        // Initialize the database
        motivoDoBloqueioDeLeitoRepository.saveAndFlush(motivoDoBloqueioDeLeito);

        int databaseSizeBeforeUpdate = motivoDoBloqueioDeLeitoRepository.findAll().size();

        // Update the motivoDoBloqueioDeLeito
        MotivoDoBloqueioDeLeito updatedMotivoDoBloqueioDeLeito = motivoDoBloqueioDeLeitoRepository.findById(motivoDoBloqueioDeLeito.getId()).get();
        // Disconnect from session so that the updates on updatedMotivoDoBloqueioDeLeito are not directly saved in db
        em.detach(updatedMotivoDoBloqueioDeLeito);
        updatedMotivoDoBloqueioDeLeito
            .nome(UPDATED_NOME);
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO = motivoDoBloqueioDeLeitoMapper.toDto(updatedMotivoDoBloqueioDeLeito);

        restMotivoDoBloqueioDeLeitoMockMvc.perform(put("/api/motivo-do-bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoBloqueioDeLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the MotivoDoBloqueioDeLeito in the database
        List<MotivoDoBloqueioDeLeito> motivoDoBloqueioDeLeitoList = motivoDoBloqueioDeLeitoRepository.findAll();
        assertThat(motivoDoBloqueioDeLeitoList).hasSize(databaseSizeBeforeUpdate);
        MotivoDoBloqueioDeLeito testMotivoDoBloqueioDeLeito = motivoDoBloqueioDeLeitoList.get(motivoDoBloqueioDeLeitoList.size() - 1);
        assertThat(testMotivoDoBloqueioDeLeito.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the MotivoDoBloqueioDeLeito in Elasticsearch
        verify(mockMotivoDoBloqueioDeLeitoSearchRepository, times(1)).save(testMotivoDoBloqueioDeLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingMotivoDoBloqueioDeLeito() throws Exception {
        int databaseSizeBeforeUpdate = motivoDoBloqueioDeLeitoRepository.findAll().size();

        // Create the MotivoDoBloqueioDeLeito
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO = motivoDoBloqueioDeLeitoMapper.toDto(motivoDoBloqueioDeLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivoDoBloqueioDeLeitoMockMvc.perform(put("/api/motivo-do-bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDoBloqueioDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MotivoDoBloqueioDeLeito in the database
        List<MotivoDoBloqueioDeLeito> motivoDoBloqueioDeLeitoList = motivoDoBloqueioDeLeitoRepository.findAll();
        assertThat(motivoDoBloqueioDeLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MotivoDoBloqueioDeLeito in Elasticsearch
        verify(mockMotivoDoBloqueioDeLeitoSearchRepository, times(0)).save(motivoDoBloqueioDeLeito);
    }

    @Test
    @Transactional
    public void deleteMotivoDoBloqueioDeLeito() throws Exception {
        // Initialize the database
        motivoDoBloqueioDeLeitoRepository.saveAndFlush(motivoDoBloqueioDeLeito);

        int databaseSizeBeforeDelete = motivoDoBloqueioDeLeitoRepository.findAll().size();

        // Delete the motivoDoBloqueioDeLeito
        restMotivoDoBloqueioDeLeitoMockMvc.perform(delete("/api/motivo-do-bloqueio-de-leitos/{id}", motivoDoBloqueioDeLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MotivoDoBloqueioDeLeito> motivoDoBloqueioDeLeitoList = motivoDoBloqueioDeLeitoRepository.findAll();
        assertThat(motivoDoBloqueioDeLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MotivoDoBloqueioDeLeito in Elasticsearch
        verify(mockMotivoDoBloqueioDeLeitoSearchRepository, times(1)).deleteById(motivoDoBloqueioDeLeito.getId());
    }

    @Test
    @Transactional
    public void searchMotivoDoBloqueioDeLeito() throws Exception {
        // Initialize the database
        motivoDoBloqueioDeLeitoRepository.saveAndFlush(motivoDoBloqueioDeLeito);
        when(mockMotivoDoBloqueioDeLeitoSearchRepository.search(queryStringQuery("id:" + motivoDoBloqueioDeLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(motivoDoBloqueioDeLeito), PageRequest.of(0, 1), 1));
        // Search the motivoDoBloqueioDeLeito
        restMotivoDoBloqueioDeLeitoMockMvc.perform(get("/api/_search/motivo-do-bloqueio-de-leitos?query=id:" + motivoDoBloqueioDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivoDoBloqueioDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoDoBloqueioDeLeito.class);
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito1 = new MotivoDoBloqueioDeLeito();
        motivoDoBloqueioDeLeito1.setId(1L);
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito2 = new MotivoDoBloqueioDeLeito();
        motivoDoBloqueioDeLeito2.setId(motivoDoBloqueioDeLeito1.getId());
        assertThat(motivoDoBloqueioDeLeito1).isEqualTo(motivoDoBloqueioDeLeito2);
        motivoDoBloqueioDeLeito2.setId(2L);
        assertThat(motivoDoBloqueioDeLeito1).isNotEqualTo(motivoDoBloqueioDeLeito2);
        motivoDoBloqueioDeLeito1.setId(null);
        assertThat(motivoDoBloqueioDeLeito1).isNotEqualTo(motivoDoBloqueioDeLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoDoBloqueioDeLeitoDTO.class);
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO1 = new MotivoDoBloqueioDeLeitoDTO();
        motivoDoBloqueioDeLeitoDTO1.setId(1L);
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO2 = new MotivoDoBloqueioDeLeitoDTO();
        assertThat(motivoDoBloqueioDeLeitoDTO1).isNotEqualTo(motivoDoBloqueioDeLeitoDTO2);
        motivoDoBloqueioDeLeitoDTO2.setId(motivoDoBloqueioDeLeitoDTO1.getId());
        assertThat(motivoDoBloqueioDeLeitoDTO1).isEqualTo(motivoDoBloqueioDeLeitoDTO2);
        motivoDoBloqueioDeLeitoDTO2.setId(2L);
        assertThat(motivoDoBloqueioDeLeitoDTO1).isNotEqualTo(motivoDoBloqueioDeLeitoDTO2);
        motivoDoBloqueioDeLeitoDTO1.setId(null);
        assertThat(motivoDoBloqueioDeLeitoDTO1).isNotEqualTo(motivoDoBloqueioDeLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(motivoDoBloqueioDeLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(motivoDoBloqueioDeLeitoMapper.fromId(null)).isNull();
    }
}
