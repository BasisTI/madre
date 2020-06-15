package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.TempoPorClasse;
import br.com.basis.suprimentos.domain.enumeration.TempoPorClasseTipo;
import br.com.basis.suprimentos.repository.TempoPorClasseRepository;
import br.com.basis.suprimentos.repository.search.TempoPorClasseSearchRepository;
import br.com.basis.suprimentos.service.TempoPorClasseService;
import br.com.basis.suprimentos.service.dto.TempoPorClasseDTO;
import br.com.basis.suprimentos.service.mapper.TempoPorClasseMapper;
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

import static br.com.basis.suprimentos.web.rest.TestUtil.createFormattingConversionService;
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
 * Integration tests for the {@link TempoPorClasseResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class TempoPorClasseResourceIT {

    private static final TempoPorClasseTipo DEFAULT_TIPO = TempoPorClasseTipo.SALDO;
    private static final TempoPorClasseTipo UPDATED_TIPO = TempoPorClasseTipo.PARCELA;

    private static final Integer DEFAULT_QUANTIDADE_CLASSE_A = 1;
    private static final Integer UPDATED_QUANTIDADE_CLASSE_A = 2;

    private static final Integer DEFAULT_QUANTIDADE_CLASSE_B = 1;
    private static final Integer UPDATED_QUANTIDADE_CLASSE_B = 2;

    private static final Integer DEFAULT_QUANTIDADE_CLASSE_C = 1;
    private static final Integer UPDATED_QUANTIDADE_CLASSE_C = 2;

    @Autowired
    private TempoPorClasseRepository tempoPorClasseRepository;

    @Autowired
    private TempoPorClasseMapper tempoPorClasseMapper;

    @Autowired
    private TempoPorClasseService tempoPorClasseService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.TempoPorClasseSearchRepositoryMockConfiguration
     */
    @Autowired
    private TempoPorClasseSearchRepository mockTempoPorClasseSearchRepository;

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

    private MockMvc restTempoPorClasseMockMvc;

    private TempoPorClasse tempoPorClasse;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempoPorClasse createEntity(EntityManager em) {
        TempoPorClasse tempoPorClasse = TempoPorClasse.builder()
            .tipo(DEFAULT_TIPO)
            .quantidadeClasseA(DEFAULT_QUANTIDADE_CLASSE_A)
            .quantidadeClasseB(DEFAULT_QUANTIDADE_CLASSE_B)
            .quantidadeClasseC(DEFAULT_QUANTIDADE_CLASSE_C).build();
        return tempoPorClasse;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TempoPorClasse createUpdatedEntity(EntityManager em) {
        TempoPorClasse tempoPorClasse = TempoPorClasse.builder()
            .tipo(UPDATED_TIPO)
            .quantidadeClasseA(UPDATED_QUANTIDADE_CLASSE_A)
            .quantidadeClasseB(UPDATED_QUANTIDADE_CLASSE_B)
            .quantidadeClasseC(UPDATED_QUANTIDADE_CLASSE_C).build();
        return tempoPorClasse;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TempoPorClasseResource tempoPorClasseResource = new TempoPorClasseResource(tempoPorClasseService);
        this.restTempoPorClasseMockMvc = MockMvcBuilders.standaloneSetup(tempoPorClasseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        tempoPorClasse = createEntity(em);
    }

    @Test
    @Transactional
    public void createTempoPorClasse() throws Exception {
        int databaseSizeBeforeCreate = tempoPorClasseRepository.findAll().size();

        // Create the TempoPorClasse
        TempoPorClasseDTO tempoPorClasseDTO = tempoPorClasseMapper.toDto(tempoPorClasse);
        restTempoPorClasseMockMvc.perform(post("/api/tempo-por-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempoPorClasseDTO)))
            .andExpect(status().isCreated());

        // Validate the TempoPorClasse in the database
        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeCreate + 1);
        TempoPorClasse testTempoPorClasse = tempoPorClasseList.get(tempoPorClasseList.size() - 1);
        assertThat(testTempoPorClasse.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTempoPorClasse.getQuantidadeClasseA()).isEqualTo(DEFAULT_QUANTIDADE_CLASSE_A);
        assertThat(testTempoPorClasse.getQuantidadeClasseB()).isEqualTo(DEFAULT_QUANTIDADE_CLASSE_B);
        assertThat(testTempoPorClasse.getQuantidadeClasseC()).isEqualTo(DEFAULT_QUANTIDADE_CLASSE_C);

        // Validate the TempoPorClasse in Elasticsearch
        verify(mockTempoPorClasseSearchRepository, times(1)).save(testTempoPorClasse);
    }

    @Test
    @Transactional
    public void createTempoPorClasseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tempoPorClasseRepository.findAll().size();

        // Create the TempoPorClasse with an existing ID
        tempoPorClasse.setId(1L);
        TempoPorClasseDTO tempoPorClasseDTO = tempoPorClasseMapper.toDto(tempoPorClasse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTempoPorClasseMockMvc.perform(post("/api/tempo-por-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempoPorClasseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempoPorClasse in the database
        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeCreate);

        // Validate the TempoPorClasse in Elasticsearch
        verify(mockTempoPorClasseSearchRepository, times(0)).save(tempoPorClasse);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempoPorClasseRepository.findAll().size();
        // set the field null
        tempoPorClasse.setTipo(null);

        // Create the TempoPorClasse, which fails.
        TempoPorClasseDTO tempoPorClasseDTO = tempoPorClasseMapper.toDto(tempoPorClasse);

        restTempoPorClasseMockMvc.perform(post("/api/tempo-por-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempoPorClasseDTO)))
            .andExpect(status().isBadRequest());

        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeClasseAIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempoPorClasseRepository.findAll().size();
        // set the field null
        tempoPorClasse.setQuantidadeClasseA(null);

        // Create the TempoPorClasse, which fails.
        TempoPorClasseDTO tempoPorClasseDTO = tempoPorClasseMapper.toDto(tempoPorClasse);

        restTempoPorClasseMockMvc.perform(post("/api/tempo-por-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempoPorClasseDTO)))
            .andExpect(status().isBadRequest());

        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeClasseBIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempoPorClasseRepository.findAll().size();
        // set the field null
        tempoPorClasse.setQuantidadeClasseB(null);

        // Create the TempoPorClasse, which fails.
        TempoPorClasseDTO tempoPorClasseDTO = tempoPorClasseMapper.toDto(tempoPorClasse);

        restTempoPorClasseMockMvc.perform(post("/api/tempo-por-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempoPorClasseDTO)))
            .andExpect(status().isBadRequest());

        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeClasseCIsRequired() throws Exception {
        int databaseSizeBeforeTest = tempoPorClasseRepository.findAll().size();
        // set the field null
        tempoPorClasse.setQuantidadeClasseC(null);

        // Create the TempoPorClasse, which fails.
        TempoPorClasseDTO tempoPorClasseDTO = tempoPorClasseMapper.toDto(tempoPorClasse);

        restTempoPorClasseMockMvc.perform(post("/api/tempo-por-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempoPorClasseDTO)))
            .andExpect(status().isBadRequest());

        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTempoPorClasses() throws Exception {
        // Initialize the database
        tempoPorClasseRepository.saveAndFlush(tempoPorClasse);

        // Get all the tempoPorClasseList
        restTempoPorClasseMockMvc.perform(get("/api/tempo-por-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempoPorClasse.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].quantidadeClasseA").value(hasItem(DEFAULT_QUANTIDADE_CLASSE_A)))
            .andExpect(jsonPath("$.[*].quantidadeClasseB").value(hasItem(DEFAULT_QUANTIDADE_CLASSE_B)))
            .andExpect(jsonPath("$.[*].quantidadeClasseC").value(hasItem(DEFAULT_QUANTIDADE_CLASSE_C)));
    }

    @Test
    @Transactional
    public void getTempoPorClasse() throws Exception {
        // Initialize the database
        tempoPorClasseRepository.saveAndFlush(tempoPorClasse);

        // Get the tempoPorClasse
        restTempoPorClasseMockMvc.perform(get("/api/tempo-por-classes/{id}", tempoPorClasse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tempoPorClasse.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.quantidadeClasseA").value(DEFAULT_QUANTIDADE_CLASSE_A))
            .andExpect(jsonPath("$.quantidadeClasseB").value(DEFAULT_QUANTIDADE_CLASSE_B))
            .andExpect(jsonPath("$.quantidadeClasseC").value(DEFAULT_QUANTIDADE_CLASSE_C));
    }

    @Test
    @Transactional
    public void getNonExistingTempoPorClasse() throws Exception {
        // Get the tempoPorClasse
        restTempoPorClasseMockMvc.perform(get("/api/tempo-por-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempoPorClasse() throws Exception {
        // Initialize the database
        tempoPorClasseRepository.saveAndFlush(tempoPorClasse);

        int databaseSizeBeforeUpdate = tempoPorClasseRepository.findAll().size();

        // Update the tempoPorClasse
        TempoPorClasse updatedTempoPorClasse = tempoPorClasseRepository.findById(tempoPorClasse.getId()).get();
        // Disconnect from session so that the updates on updatedTempoPorClasse are not directly saved in db
        em.detach(updatedTempoPorClasse);

        updatedTempoPorClasse.setTipo(UPDATED_TIPO);
        updatedTempoPorClasse.setQuantidadeClasseA(UPDATED_QUANTIDADE_CLASSE_A);
        updatedTempoPorClasse.setQuantidadeClasseB(UPDATED_QUANTIDADE_CLASSE_B);
        updatedTempoPorClasse.setQuantidadeClasseC(UPDATED_QUANTIDADE_CLASSE_C);

        TempoPorClasseDTO tempoPorClasseDTO = tempoPorClasseMapper.toDto(updatedTempoPorClasse);

        restTempoPorClasseMockMvc.perform(put("/api/tempo-por-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempoPorClasseDTO)))
            .andExpect(status().isOk());

        // Validate the TempoPorClasse in the database
        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeUpdate);
        TempoPorClasse testTempoPorClasse = tempoPorClasseList.get(tempoPorClasseList.size() - 1);
        assertThat(testTempoPorClasse.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTempoPorClasse.getQuantidadeClasseA()).isEqualTo(UPDATED_QUANTIDADE_CLASSE_A);
        assertThat(testTempoPorClasse.getQuantidadeClasseB()).isEqualTo(UPDATED_QUANTIDADE_CLASSE_B);
        assertThat(testTempoPorClasse.getQuantidadeClasseC()).isEqualTo(UPDATED_QUANTIDADE_CLASSE_C);

        // Validate the TempoPorClasse in Elasticsearch
        verify(mockTempoPorClasseSearchRepository, times(1)).save(testTempoPorClasse);
    }

    @Test
    @Transactional
    public void updateNonExistingTempoPorClasse() throws Exception {
        int databaseSizeBeforeUpdate = tempoPorClasseRepository.findAll().size();

        // Create the TempoPorClasse
        TempoPorClasseDTO tempoPorClasseDTO = tempoPorClasseMapper.toDto(tempoPorClasse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTempoPorClasseMockMvc.perform(put("/api/tempo-por-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tempoPorClasseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TempoPorClasse in the database
        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TempoPorClasse in Elasticsearch
        verify(mockTempoPorClasseSearchRepository, times(0)).save(tempoPorClasse);
    }

    @Test
    @Transactional
    public void deleteTempoPorClasse() throws Exception {
        // Initialize the database
        tempoPorClasseRepository.saveAndFlush(tempoPorClasse);

        int databaseSizeBeforeDelete = tempoPorClasseRepository.findAll().size();

        // Delete the tempoPorClasse
        restTempoPorClasseMockMvc.perform(delete("/api/tempo-por-classes/{id}", tempoPorClasse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TempoPorClasse> tempoPorClasseList = tempoPorClasseRepository.findAll();
        assertThat(tempoPorClasseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TempoPorClasse in Elasticsearch
        verify(mockTempoPorClasseSearchRepository, times(1)).deleteById(tempoPorClasse.getId());
    }

    @Test
    @Transactional
    public void searchTempoPorClasse() throws Exception {
        // Initialize the database
        tempoPorClasseRepository.saveAndFlush(tempoPorClasse);
        when(mockTempoPorClasseSearchRepository.search(queryStringQuery("id:" + tempoPorClasse.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tempoPorClasse), PageRequest.of(0, 1), 1));
        // Search the tempoPorClasse
        restTempoPorClasseMockMvc.perform(get("/api/_search/tempo-por-classes?query=id:" + tempoPorClasse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempoPorClasse.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].quantidadeClasseA").value(hasItem(DEFAULT_QUANTIDADE_CLASSE_A)))
            .andExpect(jsonPath("$.[*].quantidadeClasseB").value(hasItem(DEFAULT_QUANTIDADE_CLASSE_B)))
            .andExpect(jsonPath("$.[*].quantidadeClasseC").value(hasItem(DEFAULT_QUANTIDADE_CLASSE_C)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TempoPorClasse.class);
        TempoPorClasse tempoPorClasse1 = new TempoPorClasse();
        tempoPorClasse1.setId(1L);
        TempoPorClasse tempoPorClasse2 = new TempoPorClasse();
        tempoPorClasse2.setId(tempoPorClasse1.getId());
        assertThat(tempoPorClasse1).isEqualTo(tempoPorClasse2);
        tempoPorClasse2.setId(2L);
        assertThat(tempoPorClasse1).isNotEqualTo(tempoPorClasse2);
        tempoPorClasse1.setId(null);
        assertThat(tempoPorClasse1).isNotEqualTo(tempoPorClasse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TempoPorClasseDTO.class);
        TempoPorClasseDTO tempoPorClasseDTO1 = new TempoPorClasseDTO();
        tempoPorClasseDTO1.setId(1L);
        TempoPorClasseDTO tempoPorClasseDTO2 = new TempoPorClasseDTO();
        assertThat(tempoPorClasseDTO1).isNotEqualTo(tempoPorClasseDTO2);
        tempoPorClasseDTO2.setId(tempoPorClasseDTO1.getId());
        assertThat(tempoPorClasseDTO1).isEqualTo(tempoPorClasseDTO2);
        tempoPorClasseDTO2.setId(2L);
        assertThat(tempoPorClasseDTO1).isNotEqualTo(tempoPorClasseDTO2);
        tempoPorClasseDTO1.setId(null);
        assertThat(tempoPorClasseDTO1).isNotEqualTo(tempoPorClasseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tempoPorClasseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tempoPorClasseMapper.fromId(null)).isNull();
    }
}
