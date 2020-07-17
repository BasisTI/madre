package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.UnidadeMedida;
import br.com.basis.suprimentos.repository.UnidadeMedidaRepository;
import br.com.basis.suprimentos.repository.search.UnidadeMedidaSearchRepository;
import br.com.basis.suprimentos.service.UnidadeMedidaService;
import br.com.basis.suprimentos.service.dto.UnidadeMedidaDTO;
import br.com.basis.suprimentos.service.mapper.UnidadeMedidaMapper;
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
 * Integration tests for the {@link UnidadeMedidaResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class UnidadeMedidaResourceIT {

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private UnidadeMedidaRepository unidadeMedidaRepository;

    @Autowired
    private UnidadeMedidaMapper unidadeMedidaMapper;

    @Autowired
    private UnidadeMedidaService unidadeMedidaService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.UnidadeMedidaSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadeMedidaSearchRepository mockUnidadeMedidaSearchRepository;

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

    private MockMvc restUnidadeMedidaMockMvc;

    private UnidadeMedida unidadeMedida;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeMedida createEntity(EntityManager em) {
        UnidadeMedida unidadeMedida = new UnidadeMedida();
        unidadeMedida.setSigla(DEFAULT_SIGLA);
        unidadeMedida.setDescricao(DEFAULT_DESCRICAO);
        return unidadeMedida;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeMedida createUpdatedEntity(EntityManager em) {
        UnidadeMedida unidadeMedida = new UnidadeMedida();
        unidadeMedida.setSigla(UPDATED_SIGLA);
        unidadeMedida.setDescricao(UPDATED_DESCRICAO);
        return unidadeMedida;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeMedidaResource unidadeMedidaResource = new UnidadeMedidaResource(unidadeMedidaService);
        this.restUnidadeMedidaMockMvc = MockMvcBuilders.standaloneSetup(unidadeMedidaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        unidadeMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeMedida() throws Exception {
        int databaseSizeBeforeCreate = unidadeMedidaRepository.findAll().size();

        // Create the UnidadeMedida
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);
        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadeMedida in the database
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeMedida testUnidadeMedida = unidadeMedidaList.get(unidadeMedidaList.size() - 1);
        assertThat(testUnidadeMedida.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testUnidadeMedida.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the UnidadeMedida in Elasticsearch
        verify(mockUnidadeMedidaSearchRepository, times(1)).save(testUnidadeMedida);
    }

    @Test
    @Transactional
    public void createUnidadeMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeMedidaRepository.findAll().size();

        // Create the UnidadeMedida with an existing ID
        unidadeMedida.setId(1L);
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeMedida in the database
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeCreate);

        // Validate the UnidadeMedida in Elasticsearch
        verify(mockUnidadeMedidaSearchRepository, times(0)).save(unidadeMedida);
    }


    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeMedidaRepository.findAll().size();
        // set the field null
        unidadeMedida.setSigla(null);

        // Create the UnidadeMedida, which fails.
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeMedidaRepository.findAll().size();
        // set the field null
        unidadeMedida.setDescricao(null);

        // Create the UnidadeMedida, which fails.
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeMedidas() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);

        // Get all the unidadeMedidaList
        restUnidadeMedidaMockMvc.perform(get("/api/unidade-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getUnidadeMedida() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);

        // Get the unidadeMedida
        restUnidadeMedidaMockMvc.perform(get("/api/unidade-medidas/{id}", unidadeMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeMedida.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeMedida() throws Exception {
        // Get the unidadeMedida
        restUnidadeMedidaMockMvc.perform(get("/api/unidade-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeMedida() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);

        int databaseSizeBeforeUpdate = unidadeMedidaRepository.findAll().size();

        // Update the unidadeMedida
        UnidadeMedida updatedUnidadeMedida = unidadeMedidaRepository.findById(unidadeMedida.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeMedida are not directly saved in db
        em.detach(updatedUnidadeMedida);
        updatedUnidadeMedida
            .setSigla(UPDATED_SIGLA);
        updatedUnidadeMedida.setDescricao(UPDATED_DESCRICAO);
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(updatedUnidadeMedida);

        restUnidadeMedidaMockMvc.perform(put("/api/unidade-medidas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadeMedida in the database
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadeMedida testUnidadeMedida = unidadeMedidaList.get(unidadeMedidaList.size() - 1);
        assertThat(testUnidadeMedida.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testUnidadeMedida.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the UnidadeMedida in Elasticsearch
        verify(mockUnidadeMedidaSearchRepository, times(1)).save(testUnidadeMedida);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadeMedidaRepository.findAll().size();

        // Create the UnidadeMedida
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeMedidaMockMvc.perform(put("/api/unidade-medidas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeMedida in the database
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UnidadeMedida in Elasticsearch
        verify(mockUnidadeMedidaSearchRepository, times(0)).save(unidadeMedida);
    }

    @Test
    @Transactional
    public void deleteUnidadeMedida() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);

        int databaseSizeBeforeDelete = unidadeMedidaRepository.findAll().size();

        // Delete the unidadeMedida
        restUnidadeMedidaMockMvc.perform(delete("/api/unidade-medidas/{id}", unidadeMedida.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UnidadeMedida in Elasticsearch
        verify(mockUnidadeMedidaSearchRepository, times(1)).deleteById(unidadeMedida.getId());
    }

    @Test
    @Transactional
    public void searchUnidadeMedida() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);
        when(mockUnidadeMedidaSearchRepository.search(queryStringQuery("id:" + unidadeMedida.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unidadeMedida), PageRequest.of(0, 1), 1));
        // Search the unidadeMedida
        restUnidadeMedidaMockMvc.perform(get("/api/_search/unidade-medidas?query=id:" + unidadeMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeMedida.class);
        UnidadeMedida unidadeMedida1 = new UnidadeMedida();
        unidadeMedida1.setId(1L);
        UnidadeMedida unidadeMedida2 = new UnidadeMedida();
        unidadeMedida2.setId(unidadeMedida1.getId());
        assertThat(unidadeMedida1).isEqualTo(unidadeMedida2);
        unidadeMedida2.setId(2L);
        assertThat(unidadeMedida1).isNotEqualTo(unidadeMedida2);
        unidadeMedida1.setId(null);
        assertThat(unidadeMedida1).isNotEqualTo(unidadeMedida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeMedidaDTO.class);
        UnidadeMedidaDTO unidadeMedidaDTO1 = new UnidadeMedidaDTO();
        unidadeMedidaDTO1.setId(1L);
        UnidadeMedidaDTO unidadeMedidaDTO2 = new UnidadeMedidaDTO();
        assertThat(unidadeMedidaDTO1).isNotEqualTo(unidadeMedidaDTO2);
        unidadeMedidaDTO2.setId(unidadeMedidaDTO1.getId());
        assertThat(unidadeMedidaDTO1).isEqualTo(unidadeMedidaDTO2);
        unidadeMedidaDTO2.setId(2L);
        assertThat(unidadeMedidaDTO1).isNotEqualTo(unidadeMedidaDTO2);
        unidadeMedidaDTO1.setId(null);
        assertThat(unidadeMedidaDTO1).isNotEqualTo(unidadeMedidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadeMedidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadeMedidaMapper.fromId(null)).isNull();
    }
}
