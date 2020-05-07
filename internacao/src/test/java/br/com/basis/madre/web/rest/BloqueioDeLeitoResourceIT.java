package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.BloqueioDeLeito;
import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.domain.MotivoDoBloqueioDeLeito;
import br.com.basis.madre.repository.BloqueioDeLeitoRepository;
import br.com.basis.madre.repository.search.BloqueioDeLeitoSearchRepository;
import br.com.basis.madre.service.BloqueioDeLeitoService;
import br.com.basis.madre.service.dto.BloqueioDeLeitoDTO;
import br.com.basis.madre.service.mapper.BloqueioDeLeitoMapper;
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
 * Integration tests for the {@link BloqueioDeLeitoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class BloqueioDeLeitoResourceIT {

    private static final LocalDate DEFAULT_DATA_DO_LANCAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DO_LANCAMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JUSTIFICATIVA = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIVA = "BBBBBBBBBB";

    @Autowired
    private BloqueioDeLeitoRepository bloqueioDeLeitoRepository;

    @Autowired
    private BloqueioDeLeitoMapper bloqueioDeLeitoMapper;

    @Autowired
    private BloqueioDeLeitoService bloqueioDeLeitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.BloqueioDeLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private BloqueioDeLeitoSearchRepository mockBloqueioDeLeitoSearchRepository;

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

    private MockMvc restBloqueioDeLeitoMockMvc;

    private BloqueioDeLeito bloqueioDeLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BloqueioDeLeitoResource bloqueioDeLeitoResource = new BloqueioDeLeitoResource(bloqueioDeLeitoService);
        this.restBloqueioDeLeitoMockMvc = MockMvcBuilders.standaloneSetup(bloqueioDeLeitoResource)
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
    public static BloqueioDeLeito createEntity(EntityManager em) {
        BloqueioDeLeito bloqueioDeLeito = new BloqueioDeLeito()
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
        bloqueioDeLeito.setLeito(leito);
        // Add required entity
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito;
        if (TestUtil.findAll(em, MotivoDoBloqueioDeLeito.class).isEmpty()) {
            motivoDoBloqueioDeLeito = MotivoDoBloqueioDeLeitoResourceIT.createEntity(em);
            em.persist(motivoDoBloqueioDeLeito);
            em.flush();
        } else {
            motivoDoBloqueioDeLeito = TestUtil.findAll(em, MotivoDoBloqueioDeLeito.class).get(0);
        }
        bloqueioDeLeito.setMotivo(motivoDoBloqueioDeLeito);
        return bloqueioDeLeito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloqueioDeLeito createUpdatedEntity(EntityManager em) {
        BloqueioDeLeito bloqueioDeLeito = new BloqueioDeLeito()
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
        bloqueioDeLeito.setLeito(leito);
        // Add required entity
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito;
        if (TestUtil.findAll(em, MotivoDoBloqueioDeLeito.class).isEmpty()) {
            motivoDoBloqueioDeLeito = MotivoDoBloqueioDeLeitoResourceIT.createUpdatedEntity(em);
            em.persist(motivoDoBloqueioDeLeito);
            em.flush();
        } else {
            motivoDoBloqueioDeLeito = TestUtil.findAll(em, MotivoDoBloqueioDeLeito.class).get(0);
        }
        bloqueioDeLeito.setMotivo(motivoDoBloqueioDeLeito);
        return bloqueioDeLeito;
    }

    @BeforeEach
    public void initTest() {
        bloqueioDeLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createBloqueioDeLeito() throws Exception {
        int databaseSizeBeforeCreate = bloqueioDeLeitoRepository.findAll().size();

        // Create the BloqueioDeLeito
        BloqueioDeLeitoDTO bloqueioDeLeitoDTO = bloqueioDeLeitoMapper.toDto(bloqueioDeLeito);
        restBloqueioDeLeitoMockMvc.perform(post("/api/bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueioDeLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the BloqueioDeLeito in the database
        List<BloqueioDeLeito> bloqueioDeLeitoList = bloqueioDeLeitoRepository.findAll();
        assertThat(bloqueioDeLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        BloqueioDeLeito testBloqueioDeLeito = bloqueioDeLeitoList.get(bloqueioDeLeitoList.size() - 1);
        assertThat(testBloqueioDeLeito.getDataDoLancamento()).isEqualTo(DEFAULT_DATA_DO_LANCAMENTO);
        assertThat(testBloqueioDeLeito.getJustificativa()).isEqualTo(DEFAULT_JUSTIFICATIVA);

        // Validate the BloqueioDeLeito in Elasticsearch
        verify(mockBloqueioDeLeitoSearchRepository, times(1)).save(testBloqueioDeLeito);
    }

    @Test
    @Transactional
    public void createBloqueioDeLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bloqueioDeLeitoRepository.findAll().size();

        // Create the BloqueioDeLeito with an existing ID
        bloqueioDeLeito.setId(1L);
        BloqueioDeLeitoDTO bloqueioDeLeitoDTO = bloqueioDeLeitoMapper.toDto(bloqueioDeLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloqueioDeLeitoMockMvc.perform(post("/api/bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueioDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BloqueioDeLeito in the database
        List<BloqueioDeLeito> bloqueioDeLeitoList = bloqueioDeLeitoRepository.findAll();
        assertThat(bloqueioDeLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the BloqueioDeLeito in Elasticsearch
        verify(mockBloqueioDeLeitoSearchRepository, times(0)).save(bloqueioDeLeito);
    }


    @Test
    @Transactional
    public void checkDataDoLancamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = bloqueioDeLeitoRepository.findAll().size();
        // set the field null
        bloqueioDeLeito.setDataDoLancamento(null);

        // Create the BloqueioDeLeito, which fails.
        BloqueioDeLeitoDTO bloqueioDeLeitoDTO = bloqueioDeLeitoMapper.toDto(bloqueioDeLeito);

        restBloqueioDeLeitoMockMvc.perform(post("/api/bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueioDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<BloqueioDeLeito> bloqueioDeLeitoList = bloqueioDeLeitoRepository.findAll();
        assertThat(bloqueioDeLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBloqueioDeLeitos() throws Exception {
        // Initialize the database
        bloqueioDeLeitoRepository.saveAndFlush(bloqueioDeLeito);

        // Get all the bloqueioDeLeitoList
        restBloqueioDeLeitoMockMvc.perform(get("/api/bloqueio-de-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloqueioDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDoLancamento").value(hasItem(DEFAULT_DATA_DO_LANCAMENTO.toString())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)));
    }
    
    @Test
    @Transactional
    public void getBloqueioDeLeito() throws Exception {
        // Initialize the database
        bloqueioDeLeitoRepository.saveAndFlush(bloqueioDeLeito);

        // Get the bloqueioDeLeito
        restBloqueioDeLeitoMockMvc.perform(get("/api/bloqueio-de-leitos/{id}", bloqueioDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bloqueioDeLeito.getId().intValue()))
            .andExpect(jsonPath("$.dataDoLancamento").value(DEFAULT_DATA_DO_LANCAMENTO.toString()))
            .andExpect(jsonPath("$.justificativa").value(DEFAULT_JUSTIFICATIVA));
    }

    @Test
    @Transactional
    public void getNonExistingBloqueioDeLeito() throws Exception {
        // Get the bloqueioDeLeito
        restBloqueioDeLeitoMockMvc.perform(get("/api/bloqueio-de-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBloqueioDeLeito() throws Exception {
        // Initialize the database
        bloqueioDeLeitoRepository.saveAndFlush(bloqueioDeLeito);

        int databaseSizeBeforeUpdate = bloqueioDeLeitoRepository.findAll().size();

        // Update the bloqueioDeLeito
        BloqueioDeLeito updatedBloqueioDeLeito = bloqueioDeLeitoRepository.findById(bloqueioDeLeito.getId()).get();
        // Disconnect from session so that the updates on updatedBloqueioDeLeito are not directly saved in db
        em.detach(updatedBloqueioDeLeito);
        updatedBloqueioDeLeito
            .dataDoLancamento(UPDATED_DATA_DO_LANCAMENTO)
            .justificativa(UPDATED_JUSTIFICATIVA);
        BloqueioDeLeitoDTO bloqueioDeLeitoDTO = bloqueioDeLeitoMapper.toDto(updatedBloqueioDeLeito);

        restBloqueioDeLeitoMockMvc.perform(put("/api/bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueioDeLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the BloqueioDeLeito in the database
        List<BloqueioDeLeito> bloqueioDeLeitoList = bloqueioDeLeitoRepository.findAll();
        assertThat(bloqueioDeLeitoList).hasSize(databaseSizeBeforeUpdate);
        BloqueioDeLeito testBloqueioDeLeito = bloqueioDeLeitoList.get(bloqueioDeLeitoList.size() - 1);
        assertThat(testBloqueioDeLeito.getDataDoLancamento()).isEqualTo(UPDATED_DATA_DO_LANCAMENTO);
        assertThat(testBloqueioDeLeito.getJustificativa()).isEqualTo(UPDATED_JUSTIFICATIVA);

        // Validate the BloqueioDeLeito in Elasticsearch
        verify(mockBloqueioDeLeitoSearchRepository, times(1)).save(testBloqueioDeLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingBloqueioDeLeito() throws Exception {
        int databaseSizeBeforeUpdate = bloqueioDeLeitoRepository.findAll().size();

        // Create the BloqueioDeLeito
        BloqueioDeLeitoDTO bloqueioDeLeitoDTO = bloqueioDeLeitoMapper.toDto(bloqueioDeLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloqueioDeLeitoMockMvc.perform(put("/api/bloqueio-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloqueioDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BloqueioDeLeito in the database
        List<BloqueioDeLeito> bloqueioDeLeitoList = bloqueioDeLeitoRepository.findAll();
        assertThat(bloqueioDeLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BloqueioDeLeito in Elasticsearch
        verify(mockBloqueioDeLeitoSearchRepository, times(0)).save(bloqueioDeLeito);
    }

    @Test
    @Transactional
    public void deleteBloqueioDeLeito() throws Exception {
        // Initialize the database
        bloqueioDeLeitoRepository.saveAndFlush(bloqueioDeLeito);

        int databaseSizeBeforeDelete = bloqueioDeLeitoRepository.findAll().size();

        // Delete the bloqueioDeLeito
        restBloqueioDeLeitoMockMvc.perform(delete("/api/bloqueio-de-leitos/{id}", bloqueioDeLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BloqueioDeLeito> bloqueioDeLeitoList = bloqueioDeLeitoRepository.findAll();
        assertThat(bloqueioDeLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BloqueioDeLeito in Elasticsearch
        verify(mockBloqueioDeLeitoSearchRepository, times(1)).deleteById(bloqueioDeLeito.getId());
    }

    @Test
    @Transactional
    public void searchBloqueioDeLeito() throws Exception {
        // Initialize the database
        bloqueioDeLeitoRepository.saveAndFlush(bloqueioDeLeito);
        when(mockBloqueioDeLeitoSearchRepository.search(queryStringQuery("id:" + bloqueioDeLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(bloqueioDeLeito), PageRequest.of(0, 1), 1));
        // Search the bloqueioDeLeito
        restBloqueioDeLeitoMockMvc.perform(get("/api/_search/bloqueio-de-leitos?query=id:" + bloqueioDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloqueioDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDoLancamento").value(hasItem(DEFAULT_DATA_DO_LANCAMENTO.toString())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloqueioDeLeito.class);
        BloqueioDeLeito bloqueioDeLeito1 = new BloqueioDeLeito();
        bloqueioDeLeito1.setId(1L);
        BloqueioDeLeito bloqueioDeLeito2 = new BloqueioDeLeito();
        bloqueioDeLeito2.setId(bloqueioDeLeito1.getId());
        assertThat(bloqueioDeLeito1).isEqualTo(bloqueioDeLeito2);
        bloqueioDeLeito2.setId(2L);
        assertThat(bloqueioDeLeito1).isNotEqualTo(bloqueioDeLeito2);
        bloqueioDeLeito1.setId(null);
        assertThat(bloqueioDeLeito1).isNotEqualTo(bloqueioDeLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloqueioDeLeitoDTO.class);
        BloqueioDeLeitoDTO bloqueioDeLeitoDTO1 = new BloqueioDeLeitoDTO();
        bloqueioDeLeitoDTO1.setId(1L);
        BloqueioDeLeitoDTO bloqueioDeLeitoDTO2 = new BloqueioDeLeitoDTO();
        assertThat(bloqueioDeLeitoDTO1).isNotEqualTo(bloqueioDeLeitoDTO2);
        bloqueioDeLeitoDTO2.setId(bloqueioDeLeitoDTO1.getId());
        assertThat(bloqueioDeLeitoDTO1).isEqualTo(bloqueioDeLeitoDTO2);
        bloqueioDeLeitoDTO2.setId(2L);
        assertThat(bloqueioDeLeitoDTO1).isNotEqualTo(bloqueioDeLeitoDTO2);
        bloqueioDeLeitoDTO1.setId(null);
        assertThat(bloqueioDeLeitoDTO1).isNotEqualTo(bloqueioDeLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bloqueioDeLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bloqueioDeLeitoMapper.fromId(null)).isNull();
    }
}
