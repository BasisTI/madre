package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Especialidade;
import br.com.basis.madre.repository.EspecialidadeRepository;
import br.com.basis.madre.repository.search.EspecialidadeSearchRepository;
import br.com.basis.madre.service.EspecialidadeService;
import br.com.basis.madre.service.dto.EspecialidadeDTO;
import br.com.basis.madre.service.mapper.EspecialidadeMapper;
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
 * Integration tests for the {@link EspecialidadeResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class EspecialidadeResourceIT {

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDADE = "BBBBBBBBBB";

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private EspecialidadeMapper especialidadeMapper;

    @Autowired
    private EspecialidadeService especialidadeService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.EspecialidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EspecialidadeSearchRepository mockEspecialidadeSearchRepository;

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

    private MockMvc restEspecialidadeMockMvc;

    private Especialidade especialidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspecialidadeResource especialidadeResource = new EspecialidadeResource(
            especialidadeService);
        this.restEspecialidadeMockMvc = MockMvcBuilders.standaloneSetup(especialidadeResource)
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
    public static Especialidade createEntity(EntityManager em) {
        Especialidade especialidade = new Especialidade()
            .sigla(DEFAULT_SIGLA)
            .especialidade(DEFAULT_ESPECIALIDADE);
        return especialidade;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static Especialidade createUpdatedEntity(EntityManager em) {
        Especialidade especialidade = new Especialidade()
            .sigla(UPDATED_SIGLA)
            .especialidade(UPDATED_ESPECIALIDADE);
        return especialidade;
    }

    @BeforeEach
    public void initTest() {
        especialidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspecialidade() throws Exception {
        int databaseSizeBeforeCreate = especialidadeRepository.findAll().size();

        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);
        restEspecialidadeMockMvc.perform(post("/api/especialidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Especialidade testEspecialidade = especialidadeList.get(especialidadeList.size() - 1);
        assertThat(testEspecialidade.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testEspecialidade.getEspecialidade()).isEqualTo(DEFAULT_ESPECIALIDADE);

        // Validate the Especialidade in Elasticsearch
        verify(mockEspecialidadeSearchRepository, times(1)).save(testEspecialidade);
    }

    @Test
    @Transactional
    public void createEspecialidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = especialidadeRepository.findAll().size();

        // Create the Especialidade with an existing ID
        especialidade.setId(1L);
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecialidadeMockMvc.perform(post("/api/especialidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Especialidade in Elasticsearch
        verify(mockEspecialidadeSearchRepository, times(0)).save(especialidade);
    }


    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = especialidadeRepository.findAll().size();
        // set the field null
        especialidade.setSigla(null);

        // Create the Especialidade, which fails.
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        restEspecialidadeMockMvc.perform(post("/api/especialidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEspecialidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = especialidadeRepository.findAll().size();
        // set the field null
        especialidade.setEspecialidade(null);

        // Create the Especialidade, which fails.
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        restEspecialidadeMockMvc.perform(post("/api/especialidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEspecialidades() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidadeList
        restEspecialidadeMockMvc.perform(get("/api/especialidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].especialidade").value(hasItem(DEFAULT_ESPECIALIDADE)));
    }

    @Test
    @Transactional
    public void getEspecialidade() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get the especialidade
        restEspecialidadeMockMvc.perform(get("/api/especialidades/{id}", especialidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(especialidade.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.especialidade").value(DEFAULT_ESPECIALIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingEspecialidade() throws Exception {
        // Get the especialidade
        restEspecialidadeMockMvc.perform(get("/api/especialidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspecialidade() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();

        // Update the especialidade
        Especialidade updatedEspecialidade = especialidadeRepository.findById(especialidade.getId())
            .get();
        // Disconnect from session so that the updates on updatedEspecialidade are not directly saved in db
        em.detach(updatedEspecialidade);
        updatedEspecialidade
            .sigla(UPDATED_SIGLA)
            .especialidade(UPDATED_ESPECIALIDADE);
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(updatedEspecialidade);

        restEspecialidadeMockMvc.perform(put("/api/especialidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);
        Especialidade testEspecialidade = especialidadeList.get(especialidadeList.size() - 1);
        assertThat(testEspecialidade.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testEspecialidade.getEspecialidade()).isEqualTo(UPDATED_ESPECIALIDADE);

        // Validate the Especialidade in Elasticsearch
        verify(mockEspecialidadeSearchRepository, times(1)).save(testEspecialidade);
    }

    @Test
    @Transactional
    public void updateNonExistingEspecialidade() throws Exception {
        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();

        // Create the Especialidade
        EspecialidadeDTO especialidadeDTO = especialidadeMapper.toDto(especialidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialidadeMockMvc.perform(put("/api/especialidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Especialidade in the database
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Especialidade in Elasticsearch
        verify(mockEspecialidadeSearchRepository, times(0)).save(especialidade);
    }

    @Test
    @Transactional
    public void deleteEspecialidade() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        int databaseSizeBeforeDelete = especialidadeRepository.findAll().size();

        // Delete the especialidade
        restEspecialidadeMockMvc.perform(delete("/api/especialidades/{id}", especialidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        assertThat(especialidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Especialidade in Elasticsearch
        verify(mockEspecialidadeSearchRepository, times(1)).deleteById(especialidade.getId());
    }

    @Test
    @Transactional
    public void searchEspecialidade() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);
        when(mockEspecialidadeSearchRepository
            .search(queryStringQuery("id:" + especialidade.getId()), PageRequest.of(0, 20)))
            .thenReturn(
                new PageImpl<>(Collections.singletonList(especialidade), PageRequest.of(0, 1), 1));
        // Search the especialidade
        restEspecialidadeMockMvc
            .perform(get("/api/_search/especialidades?query=id:" + especialidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].especialidade").value(hasItem(DEFAULT_ESPECIALIDADE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especialidade.class);
        Especialidade especialidade1 = new Especialidade();
        especialidade1.setId(1L);
        Especialidade especialidade2 = new Especialidade();
        especialidade2.setId(especialidade1.getId());
        assertThat(especialidade1).isEqualTo(especialidade2);
        especialidade2.setId(2L);
        assertThat(especialidade1).isNotEqualTo(especialidade2);
        especialidade1.setId(null);
        assertThat(especialidade1).isNotEqualTo(especialidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspecialidadeDTO.class);
        EspecialidadeDTO especialidadeDTO1 = new EspecialidadeDTO();
        especialidadeDTO1.setId(1L);
        EspecialidadeDTO especialidadeDTO2 = new EspecialidadeDTO();
        assertThat(especialidadeDTO1).isNotEqualTo(especialidadeDTO2);
        especialidadeDTO2.setId(especialidadeDTO1.getId());
        assertThat(especialidadeDTO1).isEqualTo(especialidadeDTO2);
        especialidadeDTO2.setId(2L);
        assertThat(especialidadeDTO1).isNotEqualTo(especialidadeDTO2);
        especialidadeDTO1.setId(null);
        assertThat(especialidadeDTO1).isNotEqualTo(especialidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(especialidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(especialidadeMapper.fromId(null)).isNull();
    }
}
