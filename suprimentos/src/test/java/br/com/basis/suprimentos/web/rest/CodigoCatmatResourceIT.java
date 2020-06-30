package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.CodigoCatmat;
import br.com.basis.suprimentos.repository.CodigoCatmatRepository;
import br.com.basis.suprimentos.repository.search.CodigoCatmatSearchRepository;
import br.com.basis.suprimentos.service.CodigoCatmatService;
import br.com.basis.suprimentos.service.dto.CodigoCatmatDTO;
import br.com.basis.suprimentos.service.mapper.CodigoCatmatMapper;
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
 * Integration tests for the {@link CodigoCatmatResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class CodigoCatmatResourceIT {

    private static final Long DEFAULT_CODIGO = 1L;
    private static final Long UPDATED_CODIGO = 2L;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CodigoCatmatRepository codigoCatmatRepository;

    @Autowired
    private CodigoCatmatMapper codigoCatmatMapper;

    @Autowired
    private CodigoCatmatService codigoCatmatService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.CodigoCatmatSearchRepositoryMockConfiguration
     */
    @Autowired
    private CodigoCatmatSearchRepository mockCodigoCatmatSearchRepository;

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

    private MockMvc restCodigoCatmatMockMvc;

    private CodigoCatmat codigoCatmat;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodigoCatmat createEntity(EntityManager em) {
        CodigoCatmat codigoCatmat = new CodigoCatmat();
        codigoCatmat.setCodigo(DEFAULT_CODIGO);
        codigoCatmat.setDescricao(DEFAULT_DESCRICAO);
        return codigoCatmat;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodigoCatmat createUpdatedEntity(EntityManager em) {
        CodigoCatmat codigoCatmat = new CodigoCatmat();
        codigoCatmat.setCodigo(UPDATED_CODIGO);
        codigoCatmat.setDescricao(UPDATED_DESCRICAO);
        return codigoCatmat;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CodigoCatmatResource codigoCatmatResource = new CodigoCatmatResource(codigoCatmatService);
        this.restCodigoCatmatMockMvc = MockMvcBuilders.standaloneSetup(codigoCatmatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        codigoCatmat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodigoCatmat() throws Exception {
        int databaseSizeBeforeCreate = codigoCatmatRepository.findAll().size();

        // Create the CodigoCatmat
        CodigoCatmatDTO codigoCatmatDTO = codigoCatmatMapper.toDto(codigoCatmat);
        restCodigoCatmatMockMvc.perform(post("/api/codigo-catmats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codigoCatmatDTO)))
            .andExpect(status().isCreated());

        // Validate the CodigoCatmat in the database
        List<CodigoCatmat> codigoCatmatList = codigoCatmatRepository.findAll();
        assertThat(codigoCatmatList).hasSize(databaseSizeBeforeCreate + 1);
        CodigoCatmat testCodigoCatmat = codigoCatmatList.get(codigoCatmatList.size() - 1);
        assertThat(testCodigoCatmat.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testCodigoCatmat.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the CodigoCatmat in Elasticsearch
        verify(mockCodigoCatmatSearchRepository, times(1)).save(testCodigoCatmat);
    }

    @Test
    @Transactional
    public void createCodigoCatmatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codigoCatmatRepository.findAll().size();

        // Create the CodigoCatmat with an existing ID
        codigoCatmat.setId(1L);
        CodigoCatmatDTO codigoCatmatDTO = codigoCatmatMapper.toDto(codigoCatmat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodigoCatmatMockMvc.perform(post("/api/codigo-catmats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codigoCatmatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodigoCatmat in the database
        List<CodigoCatmat> codigoCatmatList = codigoCatmatRepository.findAll();
        assertThat(codigoCatmatList).hasSize(databaseSizeBeforeCreate);

        // Validate the CodigoCatmat in Elasticsearch
        verify(mockCodigoCatmatSearchRepository, times(0)).save(codigoCatmat);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = codigoCatmatRepository.findAll().size();
        // set the field null
        codigoCatmat.setCodigo(null);

        // Create the CodigoCatmat, which fails.
        CodigoCatmatDTO codigoCatmatDTO = codigoCatmatMapper.toDto(codigoCatmat);

        restCodigoCatmatMockMvc.perform(post("/api/codigo-catmats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codigoCatmatDTO)))
            .andExpect(status().isBadRequest());

        List<CodigoCatmat> codigoCatmatList = codigoCatmatRepository.findAll();
        assertThat(codigoCatmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = codigoCatmatRepository.findAll().size();
        // set the field null
        codigoCatmat.setDescricao(null);

        // Create the CodigoCatmat, which fails.
        CodigoCatmatDTO codigoCatmatDTO = codigoCatmatMapper.toDto(codigoCatmat);

        restCodigoCatmatMockMvc.perform(post("/api/codigo-catmats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codigoCatmatDTO)))
            .andExpect(status().isBadRequest());

        List<CodigoCatmat> codigoCatmatList = codigoCatmatRepository.findAll();
        assertThat(codigoCatmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCodigoCatmats() throws Exception {
        // Initialize the database
        codigoCatmatRepository.saveAndFlush(codigoCatmat);

        // Get all the codigoCatmatList
        restCodigoCatmatMockMvc.perform(get("/api/codigo-catmats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codigoCatmat.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getCodigoCatmat() throws Exception {
        // Initialize the database
        codigoCatmatRepository.saveAndFlush(codigoCatmat);

        // Get the codigoCatmat
        restCodigoCatmatMockMvc.perform(get("/api/codigo-catmats/{id}", codigoCatmat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(codigoCatmat.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingCodigoCatmat() throws Exception {
        // Get the codigoCatmat
        restCodigoCatmatMockMvc.perform(get("/api/codigo-catmats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodigoCatmat() throws Exception {
        // Initialize the database
        codigoCatmatRepository.saveAndFlush(codigoCatmat);

        int databaseSizeBeforeUpdate = codigoCatmatRepository.findAll().size();

        // Update the codigoCatmat
        CodigoCatmat updatedCodigoCatmat = codigoCatmatRepository.findById(codigoCatmat.getId()).get();
        // Disconnect from session so that the updates on updatedCodigoCatmat are not directly saved in db
        em.detach(updatedCodigoCatmat);
        updatedCodigoCatmat
            .setCodigo(UPDATED_CODIGO);
        updatedCodigoCatmat.setDescricao(UPDATED_DESCRICAO);
        CodigoCatmatDTO codigoCatmatDTO = codigoCatmatMapper.toDto(updatedCodigoCatmat);

        restCodigoCatmatMockMvc.perform(put("/api/codigo-catmats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codigoCatmatDTO)))
            .andExpect(status().isOk());

        // Validate the CodigoCatmat in the database
        List<CodigoCatmat> codigoCatmatList = codigoCatmatRepository.findAll();
        assertThat(codigoCatmatList).hasSize(databaseSizeBeforeUpdate);
        CodigoCatmat testCodigoCatmat = codigoCatmatList.get(codigoCatmatList.size() - 1);
        assertThat(testCodigoCatmat.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testCodigoCatmat.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the CodigoCatmat in Elasticsearch
        verify(mockCodigoCatmatSearchRepository, times(1)).save(testCodigoCatmat);
    }

    @Test
    @Transactional
    public void updateNonExistingCodigoCatmat() throws Exception {
        int databaseSizeBeforeUpdate = codigoCatmatRepository.findAll().size();

        // Create the CodigoCatmat
        CodigoCatmatDTO codigoCatmatDTO = codigoCatmatMapper.toDto(codigoCatmat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodigoCatmatMockMvc.perform(put("/api/codigo-catmats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codigoCatmatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodigoCatmat in the database
        List<CodigoCatmat> codigoCatmatList = codigoCatmatRepository.findAll();
        assertThat(codigoCatmatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CodigoCatmat in Elasticsearch
        verify(mockCodigoCatmatSearchRepository, times(0)).save(codigoCatmat);
    }

    @Test
    @Transactional
    public void deleteCodigoCatmat() throws Exception {
        // Initialize the database
        codigoCatmatRepository.saveAndFlush(codigoCatmat);

        int databaseSizeBeforeDelete = codigoCatmatRepository.findAll().size();

        // Delete the codigoCatmat
        restCodigoCatmatMockMvc.perform(delete("/api/codigo-catmats/{id}", codigoCatmat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodigoCatmat> codigoCatmatList = codigoCatmatRepository.findAll();
        assertThat(codigoCatmatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CodigoCatmat in Elasticsearch
        verify(mockCodigoCatmatSearchRepository, times(1)).deleteById(codigoCatmat.getId());
    }

    @Test
    @Transactional
    public void searchCodigoCatmat() throws Exception {
        // Initialize the database
        codigoCatmatRepository.saveAndFlush(codigoCatmat);
        when(mockCodigoCatmatSearchRepository.search(queryStringQuery("id:" + codigoCatmat.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(codigoCatmat), PageRequest.of(0, 1), 1));
        // Search the codigoCatmat
        restCodigoCatmatMockMvc.perform(get("/api/_search/codigo-catmats?query=id:" + codigoCatmat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codigoCatmat.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodigoCatmat.class);
        CodigoCatmat codigoCatmat1 = new CodigoCatmat();
        codigoCatmat1.setId(1L);
        CodigoCatmat codigoCatmat2 = new CodigoCatmat();
        codigoCatmat2.setId(codigoCatmat1.getId());
        assertThat(codigoCatmat1).isEqualTo(codigoCatmat2);
        codigoCatmat2.setId(2L);
        assertThat(codigoCatmat1).isNotEqualTo(codigoCatmat2);
        codigoCatmat1.setId(null);
        assertThat(codigoCatmat1).isNotEqualTo(codigoCatmat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodigoCatmatDTO.class);
        CodigoCatmatDTO codigoCatmatDTO1 = new CodigoCatmatDTO();
        codigoCatmatDTO1.setId(1L);
        CodigoCatmatDTO codigoCatmatDTO2 = new CodigoCatmatDTO();
        assertThat(codigoCatmatDTO1).isNotEqualTo(codigoCatmatDTO2);
        codigoCatmatDTO2.setId(codigoCatmatDTO1.getId());
        assertThat(codigoCatmatDTO1).isEqualTo(codigoCatmatDTO2);
        codigoCatmatDTO2.setId(2L);
        assertThat(codigoCatmatDTO1).isNotEqualTo(codigoCatmatDTO2);
        codigoCatmatDTO1.setId(null);
        assertThat(codigoCatmatDTO1).isNotEqualTo(codigoCatmatDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(codigoCatmatMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(codigoCatmatMapper.fromId(null)).isNull();
    }
}
