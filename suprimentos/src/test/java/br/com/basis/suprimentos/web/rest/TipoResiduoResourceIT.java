package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.TipoResiduo;
import br.com.basis.suprimentos.repository.TipoResiduoRepository;
import br.com.basis.suprimentos.repository.search.TipoResiduoSearchRepository;
import br.com.basis.suprimentos.service.TipoResiduoService;
import br.com.basis.suprimentos.service.dto.TipoResiduoDTO;
import br.com.basis.suprimentos.service.mapper.TipoResiduoMapper;
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
 * Integration tests for the {@link TipoResiduoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class TipoResiduoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoResiduoRepository tipoResiduoRepository;

    @Autowired
    private TipoResiduoMapper tipoResiduoMapper;

    @Autowired
    private TipoResiduoService tipoResiduoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.TipoResiduoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoResiduoSearchRepository mockTipoResiduoSearchRepository;

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

    private MockMvc restTipoResiduoMockMvc;

    private TipoResiduo tipoResiduo;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoResiduo createEntity(EntityManager em) {
        TipoResiduo tipoResiduo = new TipoResiduo();
        tipoResiduo.setDescricao(DEFAULT_DESCRICAO);
        return tipoResiduo;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoResiduo createUpdatedEntity(EntityManager em) {
        TipoResiduo tipoResiduo = new TipoResiduo();
        tipoResiduo.setDescricao(UPDATED_DESCRICAO);
        return tipoResiduo;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoResiduoResource tipoResiduoResource = new TipoResiduoResource(tipoResiduoService);
        this.restTipoResiduoMockMvc = MockMvcBuilders.standaloneSetup(tipoResiduoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        tipoResiduo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoResiduo() throws Exception {
        int databaseSizeBeforeCreate = tipoResiduoRepository.findAll().size();

        // Create the TipoResiduo
        TipoResiduoDTO tipoResiduoDTO = tipoResiduoMapper.toDto(tipoResiduo);
        restTipoResiduoMockMvc.perform(post("/api/tipo-residuos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoResiduoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoResiduo in the database
        List<TipoResiduo> tipoResiduoList = tipoResiduoRepository.findAll();
        assertThat(tipoResiduoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoResiduo testTipoResiduo = tipoResiduoList.get(tipoResiduoList.size() - 1);
        assertThat(testTipoResiduo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the TipoResiduo in Elasticsearch
        verify(mockTipoResiduoSearchRepository, times(1)).save(testTipoResiduo);
    }

    @Test
    @Transactional
    public void createTipoResiduoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoResiduoRepository.findAll().size();

        // Create the TipoResiduo with an existing ID
        tipoResiduo.setId(1L);
        TipoResiduoDTO tipoResiduoDTO = tipoResiduoMapper.toDto(tipoResiduo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoResiduoMockMvc.perform(post("/api/tipo-residuos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoResiduoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoResiduo in the database
        List<TipoResiduo> tipoResiduoList = tipoResiduoRepository.findAll();
        assertThat(tipoResiduoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoResiduo in Elasticsearch
        verify(mockTipoResiduoSearchRepository, times(0)).save(tipoResiduo);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoResiduoRepository.findAll().size();
        // set the field null
        tipoResiduo.setDescricao(null);

        // Create the TipoResiduo, which fails.
        TipoResiduoDTO tipoResiduoDTO = tipoResiduoMapper.toDto(tipoResiduo);

        restTipoResiduoMockMvc.perform(post("/api/tipo-residuos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoResiduoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoResiduo> tipoResiduoList = tipoResiduoRepository.findAll();
        assertThat(tipoResiduoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoResiduos() throws Exception {
        // Initialize the database
        tipoResiduoRepository.saveAndFlush(tipoResiduo);

        // Get all the tipoResiduoList
        restTipoResiduoMockMvc.perform(get("/api/tipo-residuos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoResiduo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getTipoResiduo() throws Exception {
        // Initialize the database
        tipoResiduoRepository.saveAndFlush(tipoResiduo);

        // Get the tipoResiduo
        restTipoResiduoMockMvc.perform(get("/api/tipo-residuos/{id}", tipoResiduo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoResiduo.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingTipoResiduo() throws Exception {
        // Get the tipoResiduo
        restTipoResiduoMockMvc.perform(get("/api/tipo-residuos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoResiduo() throws Exception {
        // Initialize the database
        tipoResiduoRepository.saveAndFlush(tipoResiduo);

        int databaseSizeBeforeUpdate = tipoResiduoRepository.findAll().size();

        // Update the tipoResiduo
        TipoResiduo updatedTipoResiduo = tipoResiduoRepository.findById(tipoResiduo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoResiduo are not directly saved in db
        em.detach(updatedTipoResiduo);
        updatedTipoResiduo
            .setDescricao(UPDATED_DESCRICAO);
        TipoResiduoDTO tipoResiduoDTO = tipoResiduoMapper.toDto(updatedTipoResiduo);

        restTipoResiduoMockMvc.perform(put("/api/tipo-residuos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoResiduoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoResiduo in the database
        List<TipoResiduo> tipoResiduoList = tipoResiduoRepository.findAll();
        assertThat(tipoResiduoList).hasSize(databaseSizeBeforeUpdate);
        TipoResiduo testTipoResiduo = tipoResiduoList.get(tipoResiduoList.size() - 1);
        assertThat(testTipoResiduo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the TipoResiduo in Elasticsearch
        verify(mockTipoResiduoSearchRepository, times(1)).save(testTipoResiduo);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoResiduo() throws Exception {
        int databaseSizeBeforeUpdate = tipoResiduoRepository.findAll().size();

        // Create the TipoResiduo
        TipoResiduoDTO tipoResiduoDTO = tipoResiduoMapper.toDto(tipoResiduo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoResiduoMockMvc.perform(put("/api/tipo-residuos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoResiduoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoResiduo in the database
        List<TipoResiduo> tipoResiduoList = tipoResiduoRepository.findAll();
        assertThat(tipoResiduoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoResiduo in Elasticsearch
        verify(mockTipoResiduoSearchRepository, times(0)).save(tipoResiduo);
    }

    @Test
    @Transactional
    public void deleteTipoResiduo() throws Exception {
        // Initialize the database
        tipoResiduoRepository.saveAndFlush(tipoResiduo);

        int databaseSizeBeforeDelete = tipoResiduoRepository.findAll().size();

        // Delete the tipoResiduo
        restTipoResiduoMockMvc.perform(delete("/api/tipo-residuos/{id}", tipoResiduo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoResiduo> tipoResiduoList = tipoResiduoRepository.findAll();
        assertThat(tipoResiduoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoResiduo in Elasticsearch
        verify(mockTipoResiduoSearchRepository, times(1)).deleteById(tipoResiduo.getId());
    }

    @Test
    @Transactional
    public void searchTipoResiduo() throws Exception {
        // Initialize the database
        tipoResiduoRepository.saveAndFlush(tipoResiduo);
        when(mockTipoResiduoSearchRepository.search(queryStringQuery("id:" + tipoResiduo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoResiduo), PageRequest.of(0, 1), 1));
        // Search the tipoResiduo
        restTipoResiduoMockMvc.perform(get("/api/_search/tipo-residuos?query=id:" + tipoResiduo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoResiduo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoResiduo.class);
        TipoResiduo tipoResiduo1 = new TipoResiduo();
        tipoResiduo1.setId(1L);
        TipoResiduo tipoResiduo2 = new TipoResiduo();
        tipoResiduo2.setId(tipoResiduo1.getId());
        assertThat(tipoResiduo1).isEqualTo(tipoResiduo2);
        tipoResiduo2.setId(2L);
        assertThat(tipoResiduo1).isNotEqualTo(tipoResiduo2);
        tipoResiduo1.setId(null);
        assertThat(tipoResiduo1).isNotEqualTo(tipoResiduo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoResiduoDTO.class);
        TipoResiduoDTO tipoResiduoDTO1 = new TipoResiduoDTO();
        tipoResiduoDTO1.setId(1L);
        TipoResiduoDTO tipoResiduoDTO2 = new TipoResiduoDTO();
        assertThat(tipoResiduoDTO1).isNotEqualTo(tipoResiduoDTO2);
        tipoResiduoDTO2.setId(tipoResiduoDTO1.getId());
        assertThat(tipoResiduoDTO1).isEqualTo(tipoResiduoDTO2);
        tipoResiduoDTO2.setId(2L);
        assertThat(tipoResiduoDTO1).isNotEqualTo(tipoResiduoDTO2);
        tipoResiduoDTO1.setId(null);
        assertThat(tipoResiduoDTO1).isNotEqualTo(tipoResiduoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoResiduoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoResiduoMapper.fromId(null)).isNull();
    }
}
