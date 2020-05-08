package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.domain.SituacaoDeLeito;
import br.com.basis.madre.domain.UnidadeFuncional;
import br.com.basis.madre.repository.LeitoRepository;
import br.com.basis.madre.repository.search.LeitoSearchRepository;
import br.com.basis.madre.service.LeitoService;
import br.com.basis.madre.service.dto.LeitoDTO;
import br.com.basis.madre.service.mapper.LeitoMapper;
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
 * Integration tests for the {@link LeitoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class LeitoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALA = 1;
    private static final Integer UPDATED_ALA = 2;

    private static final Integer DEFAULT_ANDAR = 1;
    private static final Integer UPDATED_ANDAR = 2;

    @Autowired
    private LeitoRepository leitoRepository;

    @Autowired
    private LeitoMapper leitoMapper;

    @Autowired
    private LeitoService leitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.LeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private LeitoSearchRepository mockLeitoSearchRepository;

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

    private MockMvc restLeitoMockMvc;

    private Leito leito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeitoResource leitoResource = new LeitoResource(leitoService);
        this.restLeitoMockMvc = MockMvcBuilders.standaloneSetup(leitoResource)
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
    public static Leito createEntity(EntityManager em) {
        Leito leito = new Leito()
            .nome(DEFAULT_NOME)
            .ala(DEFAULT_ALA)
            .andar(DEFAULT_ANDAR);
        // Add required entity
        SituacaoDeLeito situacaoDeLeito;
        if (TestUtil.findAll(em, SituacaoDeLeito.class).isEmpty()) {
            situacaoDeLeito = SituacaoDeLeitoResourceIT.createEntity(em);
            em.persist(situacaoDeLeito);
            em.flush();
        } else {
            situacaoDeLeito = TestUtil.findAll(em, SituacaoDeLeito.class).get(0);
        }
        leito.setSituacao(situacaoDeLeito);
        // Add required entity
        UnidadeFuncional unidadeFuncional;
        if (TestUtil.findAll(em, UnidadeFuncional.class).isEmpty()) {
            unidadeFuncional = UnidadeFuncionalResourceIT.createEntity(em);
            em.persist(unidadeFuncional);
            em.flush();
        } else {
            unidadeFuncional = TestUtil.findAll(em, UnidadeFuncional.class).get(0);
        }
        leito.setUnidadeFuncional(unidadeFuncional);
        return leito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leito createUpdatedEntity(EntityManager em) {
        Leito leito = new Leito()
            .nome(UPDATED_NOME)
            .ala(UPDATED_ALA)
            .andar(UPDATED_ANDAR);
        // Add required entity
        SituacaoDeLeito situacaoDeLeito;
        if (TestUtil.findAll(em, SituacaoDeLeito.class).isEmpty()) {
            situacaoDeLeito = SituacaoDeLeitoResourceIT.createUpdatedEntity(em);
            em.persist(situacaoDeLeito);
            em.flush();
        } else {
            situacaoDeLeito = TestUtil.findAll(em, SituacaoDeLeito.class).get(0);
        }
        leito.setSituacao(situacaoDeLeito);
        // Add required entity
        UnidadeFuncional unidadeFuncional;
        if (TestUtil.findAll(em, UnidadeFuncional.class).isEmpty()) {
            unidadeFuncional = UnidadeFuncionalResourceIT.createUpdatedEntity(em);
            em.persist(unidadeFuncional);
            em.flush();
        } else {
            unidadeFuncional = TestUtil.findAll(em, UnidadeFuncional.class).get(0);
        }
        leito.setUnidadeFuncional(unidadeFuncional);
        return leito;
    }

    @BeforeEach
    public void initTest() {
        leito = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeito() throws Exception {
        int databaseSizeBeforeCreate = leitoRepository.findAll().size();

        // Create the Leito
        LeitoDTO leitoDTO = leitoMapper.toDto(leito);
        restLeitoMockMvc.perform(post("/api/leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leitoDTO)))
            .andExpect(status().isCreated());

        // Validate the Leito in the database
        List<Leito> leitoList = leitoRepository.findAll();
        assertThat(leitoList).hasSize(databaseSizeBeforeCreate + 1);
        Leito testLeito = leitoList.get(leitoList.size() - 1);
        assertThat(testLeito.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLeito.getAla()).isEqualTo(DEFAULT_ALA);
        assertThat(testLeito.getAndar()).isEqualTo(DEFAULT_ANDAR);

        // Validate the Leito in Elasticsearch
        verify(mockLeitoSearchRepository, times(1)).save(testLeito);
    }

    @Test
    @Transactional
    public void createLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leitoRepository.findAll().size();

        // Create the Leito with an existing ID
        leito.setId(1L);
        LeitoDTO leitoDTO = leitoMapper.toDto(leito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeitoMockMvc.perform(post("/api/leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Leito in the database
        List<Leito> leitoList = leitoRepository.findAll();
        assertThat(leitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Leito in Elasticsearch
        verify(mockLeitoSearchRepository, times(0)).save(leito);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = leitoRepository.findAll().size();
        // set the field null
        leito.setNome(null);

        // Create the Leito, which fails.
        LeitoDTO leitoDTO = leitoMapper.toDto(leito);

        restLeitoMockMvc.perform(post("/api/leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leitoDTO)))
            .andExpect(status().isBadRequest());

        List<Leito> leitoList = leitoRepository.findAll();
        assertThat(leitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAlaIsRequired() throws Exception {
        int databaseSizeBeforeTest = leitoRepository.findAll().size();
        // set the field null
        leito.setAla(null);

        // Create the Leito, which fails.
        LeitoDTO leitoDTO = leitoMapper.toDto(leito);

        restLeitoMockMvc.perform(post("/api/leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leitoDTO)))
            .andExpect(status().isBadRequest());

        List<Leito> leitoList = leitoRepository.findAll();
        assertThat(leitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAndarIsRequired() throws Exception {
        int databaseSizeBeforeTest = leitoRepository.findAll().size();
        // set the field null
        leito.setAndar(null);

        // Create the Leito, which fails.
        LeitoDTO leitoDTO = leitoMapper.toDto(leito);

        restLeitoMockMvc.perform(post("/api/leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leitoDTO)))
            .andExpect(status().isBadRequest());

        List<Leito> leitoList = leitoRepository.findAll();
        assertThat(leitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeitos() throws Exception {
        // Initialize the database
        leitoRepository.saveAndFlush(leito);

        // Get all the leitoList
        restLeitoMockMvc.perform(get("/api/leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ala").value(hasItem(DEFAULT_ALA)))
            .andExpect(jsonPath("$.[*].andar").value(hasItem(DEFAULT_ANDAR)));
    }
    
    @Test
    @Transactional
    public void getLeito() throws Exception {
        // Initialize the database
        leitoRepository.saveAndFlush(leito);

        // Get the leito
        restLeitoMockMvc.perform(get("/api/leitos/{id}", leito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leito.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.ala").value(DEFAULT_ALA))
            .andExpect(jsonPath("$.andar").value(DEFAULT_ANDAR));
    }

    @Test
    @Transactional
    public void getNonExistingLeito() throws Exception {
        // Get the leito
        restLeitoMockMvc.perform(get("/api/leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeito() throws Exception {
        // Initialize the database
        leitoRepository.saveAndFlush(leito);

        int databaseSizeBeforeUpdate = leitoRepository.findAll().size();

        // Update the leito
        Leito updatedLeito = leitoRepository.findById(leito.getId()).get();
        // Disconnect from session so that the updates on updatedLeito are not directly saved in db
        em.detach(updatedLeito);
        updatedLeito
            .nome(UPDATED_NOME)
            .ala(UPDATED_ALA)
            .andar(UPDATED_ANDAR);
        LeitoDTO leitoDTO = leitoMapper.toDto(updatedLeito);

        restLeitoMockMvc.perform(put("/api/leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leitoDTO)))
            .andExpect(status().isOk());

        // Validate the Leito in the database
        List<Leito> leitoList = leitoRepository.findAll();
        assertThat(leitoList).hasSize(databaseSizeBeforeUpdate);
        Leito testLeito = leitoList.get(leitoList.size() - 1);
        assertThat(testLeito.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLeito.getAla()).isEqualTo(UPDATED_ALA);
        assertThat(testLeito.getAndar()).isEqualTo(UPDATED_ANDAR);

        // Validate the Leito in Elasticsearch
        verify(mockLeitoSearchRepository, times(1)).save(testLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingLeito() throws Exception {
        int databaseSizeBeforeUpdate = leitoRepository.findAll().size();

        // Create the Leito
        LeitoDTO leitoDTO = leitoMapper.toDto(leito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeitoMockMvc.perform(put("/api/leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Leito in the database
        List<Leito> leitoList = leitoRepository.findAll();
        assertThat(leitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Leito in Elasticsearch
        verify(mockLeitoSearchRepository, times(0)).save(leito);
    }

    @Test
    @Transactional
    public void deleteLeito() throws Exception {
        // Initialize the database
        leitoRepository.saveAndFlush(leito);

        int databaseSizeBeforeDelete = leitoRepository.findAll().size();

        // Delete the leito
        restLeitoMockMvc.perform(delete("/api/leitos/{id}", leito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Leito> leitoList = leitoRepository.findAll();
        assertThat(leitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Leito in Elasticsearch
        verify(mockLeitoSearchRepository, times(1)).deleteById(leito.getId());
    }

    @Test
    @Transactional
    public void searchLeito() throws Exception {
        // Initialize the database
        leitoRepository.saveAndFlush(leito);
        when(mockLeitoSearchRepository.search(queryStringQuery("id:" + leito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(leito), PageRequest.of(0, 1), 1));
        // Search the leito
        restLeitoMockMvc.perform(get("/api/_search/leitos?query=id:" + leito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ala").value(hasItem(DEFAULT_ALA)))
            .andExpect(jsonPath("$.[*].andar").value(hasItem(DEFAULT_ANDAR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leito.class);
        Leito leito1 = new Leito();
        leito1.setId(1L);
        Leito leito2 = new Leito();
        leito2.setId(leito1.getId());
        assertThat(leito1).isEqualTo(leito2);
        leito2.setId(2L);
        assertThat(leito1).isNotEqualTo(leito2);
        leito1.setId(null);
        assertThat(leito1).isNotEqualTo(leito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeitoDTO.class);
        LeitoDTO leitoDTO1 = new LeitoDTO();
        leitoDTO1.setId(1L);
        LeitoDTO leitoDTO2 = new LeitoDTO();
        assertThat(leitoDTO1).isNotEqualTo(leitoDTO2);
        leitoDTO2.setId(leitoDTO1.getId());
        assertThat(leitoDTO1).isEqualTo(leitoDTO2);
        leitoDTO2.setId(2L);
        assertThat(leitoDTO1).isNotEqualTo(leitoDTO2);
        leitoDTO1.setId(null);
        assertThat(leitoDTO1).isNotEqualTo(leitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leitoMapper.fromId(null)).isNull();
    }
}
