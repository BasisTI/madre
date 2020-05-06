package br.com.basis.madre.web.rest;

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

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Procedimento;
import br.com.basis.madre.repository.ProcedimentoRepository;
import br.com.basis.madre.repository.search.ProcedimentoSearchRepository;
import br.com.basis.madre.service.ProcedimentoService;
import br.com.basis.madre.service.dto.ProcedimentoDTO;
import br.com.basis.madre.service.mapper.ProcedimentoMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
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

/**
 * Integration tests for the {@link ProcedimentoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class ProcedimentoResourceIT {

    private static final Long DEFAULT_CODIGO = 1L;
    private static final Long UPDATED_CODIGO = 2L;

    private static final String DEFAULT_PROCEDIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDIMENTO = "BBBBBBBBBB";

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    @Autowired
    private ProcedimentoMapper procedimentoMapper;

    @Autowired
    private ProcedimentoService procedimentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.ProcedimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProcedimentoSearchRepository mockProcedimentoSearchRepository;

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

    private MockMvc restProcedimentoMockMvc;

    private Procedimento procedimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcedimentoResource procedimentoResource = new ProcedimentoResource(
            procedimentoService);
        this.restProcedimentoMockMvc = MockMvcBuilders.standaloneSetup(procedimentoResource)
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
    public static Procedimento createEntity(EntityManager em) {
        Procedimento procedimento = new Procedimento()
            .codigo(DEFAULT_CODIGO)
            .procedimento(DEFAULT_PROCEDIMENTO);
        return procedimento;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static Procedimento createUpdatedEntity(EntityManager em) {
        Procedimento procedimento = new Procedimento()
            .codigo(UPDATED_CODIGO)
            .procedimento(UPDATED_PROCEDIMENTO);
        return procedimento;
    }

    @BeforeEach
    public void initTest() {
        procedimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedimento() throws Exception {
        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);
        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testProcedimento.getProcedimento()).isEqualTo(DEFAULT_PROCEDIMENTO);

        // Validate the Procedimento in Elasticsearch
        verify(mockProcedimentoSearchRepository, times(1)).save(testProcedimento);
    }

    @Test
    @Transactional
    public void createProcedimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();

        // Create the Procedimento with an existing ID
        procedimento.setId(1L);
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Procedimento in Elasticsearch
        verify(mockProcedimentoSearchRepository, times(0)).save(procedimento);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedimentoRepository.findAll().size();
        // set the field null
        procedimento.setCodigo(null);

        // Create the Procedimento, which fails.
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isBadRequest());

        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcedimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedimentoRepository.findAll().size();
        // set the field null
        procedimento.setProcedimento(null);

        // Create the Procedimento, which fails.
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isBadRequest());

        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcedimentos() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get all the procedimentoList
        restProcedimentoMockMvc.perform(get("/api/procedimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].procedimento").value(hasItem(DEFAULT_PROCEDIMENTO)));
    }

    @Test
    @Transactional
    public void getProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get the procedimento
        restProcedimentoMockMvc.perform(get("/api/procedimentos/{id}", procedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedimento.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.intValue()))
            .andExpect(jsonPath("$.procedimento").value(DEFAULT_PROCEDIMENTO));
    }

    @Test
    @Transactional
    public void getNonExistingProcedimento() throws Exception {
        // Get the procedimento
        restProcedimentoMockMvc.perform(get("/api/procedimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Update the procedimento
        Procedimento updatedProcedimento = procedimentoRepository.findById(procedimento.getId())
            .get();
        // Disconnect from session so that the updates on updatedProcedimento are not directly saved in db
        em.detach(updatedProcedimento);
        updatedProcedimento
            .codigo(UPDATED_CODIGO)
            .procedimento(UPDATED_PROCEDIMENTO);
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(updatedProcedimento);

        restProcedimentoMockMvc.perform(put("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isOk());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testProcedimento.getProcedimento()).isEqualTo(UPDATED_PROCEDIMENTO);

        // Validate the Procedimento in Elasticsearch
        verify(mockProcedimentoSearchRepository, times(1)).save(testProcedimento);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedimentoMockMvc.perform(put("/api/procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Procedimento in Elasticsearch
        verify(mockProcedimentoSearchRepository, times(0)).save(procedimento);
    }

    @Test
    @Transactional
    public void deleteProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        int databaseSizeBeforeDelete = procedimentoRepository.findAll().size();

        // Delete the procedimento
        restProcedimentoMockMvc.perform(delete("/api/procedimentos/{id}", procedimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Procedimento in Elasticsearch
        verify(mockProcedimentoSearchRepository, times(1)).deleteById(procedimento.getId());
    }

    @Test
    @Transactional
    public void searchProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);
        when(mockProcedimentoSearchRepository
            .search(queryStringQuery("id:" + procedimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(
                new PageImpl<>(Collections.singletonList(procedimento), PageRequest.of(0, 1), 1));
        // Search the procedimento
        restProcedimentoMockMvc
            .perform(get("/api/_search/procedimentos?query=id:" + procedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].procedimento").value(hasItem(DEFAULT_PROCEDIMENTO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedimento.class);
        Procedimento procedimento1 = new Procedimento();
        procedimento1.setId(1L);
        Procedimento procedimento2 = new Procedimento();
        procedimento2.setId(procedimento1.getId());
        assertThat(procedimento1).isEqualTo(procedimento2);
        procedimento2.setId(2L);
        assertThat(procedimento1).isNotEqualTo(procedimento2);
        procedimento1.setId(null);
        assertThat(procedimento1).isNotEqualTo(procedimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedimentoDTO.class);
        ProcedimentoDTO procedimentoDTO1 = new ProcedimentoDTO();
        procedimentoDTO1.setId(1L);
        ProcedimentoDTO procedimentoDTO2 = new ProcedimentoDTO();
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
        procedimentoDTO2.setId(procedimentoDTO1.getId());
        assertThat(procedimentoDTO1).isEqualTo(procedimentoDTO2);
        procedimentoDTO2.setId(2L);
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
        procedimentoDTO1.setId(null);
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(procedimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(procedimentoMapper.fromId(null)).isNull();
    }
}
