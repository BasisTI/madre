package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Composicao;
import br.com.basis.suprimentos.repository.ComposicaoRepository;
import br.com.basis.suprimentos.repository.search.ComposicaoSearchRepository;
import br.com.basis.suprimentos.service.ComposicaoService;
import br.com.basis.suprimentos.service.dto.ComposicaoDTO;
import br.com.basis.suprimentos.service.mapper.ComposicaoMapper;
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
 * Integration tests for the {@link ComposicaoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class ComposicaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVIDOR = "AAAAAAAAAA";
    private static final String UPDATED_SERVIDOR = "BBBBBBBBBB";

    @Autowired
    private ComposicaoRepository composicaoRepository;

    @Autowired
    private ComposicaoMapper composicaoMapper;

    @Autowired
    private ComposicaoService composicaoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.ComposicaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ComposicaoSearchRepository mockComposicaoSearchRepository;

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

    private MockMvc restComposicaoMockMvc;

    private Composicao composicao;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Composicao createEntity(EntityManager em) {
        Composicao composicao = Composicao.builder()
            .nome(DEFAULT_NOME)
            .servidor(DEFAULT_SERVIDOR).build();
        return composicao;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Composicao createUpdatedEntity(EntityManager em) {
        Composicao composicao = Composicao.builder()
            .nome(UPDATED_NOME)
            .servidor(UPDATED_SERVIDOR).build();
        return composicao;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComposicaoResource composicaoResource = new ComposicaoResource(composicaoService);
        this.restComposicaoMockMvc = MockMvcBuilders.standaloneSetup(composicaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        composicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createComposicao() throws Exception {
        int databaseSizeBeforeCreate = composicaoRepository.findAll().size();

        // Create the Composicao
        ComposicaoDTO composicaoDTO = composicaoMapper.toDto(composicao);
        restComposicaoMockMvc.perform(post("/api/composicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composicaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Composicao in the database
        List<Composicao> composicaoList = composicaoRepository.findAll();
        assertThat(composicaoList).hasSize(databaseSizeBeforeCreate + 1);
        Composicao testComposicao = composicaoList.get(composicaoList.size() - 1);
        assertThat(testComposicao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testComposicao.getServidor()).isEqualTo(DEFAULT_SERVIDOR);

        // Validate the Composicao in Elasticsearch
        verify(mockComposicaoSearchRepository, times(1)).save(testComposicao);
    }

    @Test
    @Transactional
    public void createComposicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = composicaoRepository.findAll().size();

        // Create the Composicao with an existing ID
        composicao.setId(1L);
        ComposicaoDTO composicaoDTO = composicaoMapper.toDto(composicao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComposicaoMockMvc.perform(post("/api/composicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composicaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Composicao in the database
        List<Composicao> composicaoList = composicaoRepository.findAll();
        assertThat(composicaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Composicao in Elasticsearch
        verify(mockComposicaoSearchRepository, times(0)).save(composicao);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = composicaoRepository.findAll().size();
        // set the field null
        composicao.setNome(null);

        // Create the Composicao, which fails.
        ComposicaoDTO composicaoDTO = composicaoMapper.toDto(composicao);

        restComposicaoMockMvc.perform(post("/api/composicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composicaoDTO)))
            .andExpect(status().isBadRequest());

        List<Composicao> composicaoList = composicaoRepository.findAll();
        assertThat(composicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServidorIsRequired() throws Exception {
        int databaseSizeBeforeTest = composicaoRepository.findAll().size();
        // set the field null
        composicao.setServidor(null);

        // Create the Composicao, which fails.
        ComposicaoDTO composicaoDTO = composicaoMapper.toDto(composicao);

        restComposicaoMockMvc.perform(post("/api/composicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composicaoDTO)))
            .andExpect(status().isBadRequest());

        List<Composicao> composicaoList = composicaoRepository.findAll();
        assertThat(composicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComposicaos() throws Exception {
        // Initialize the database
        composicaoRepository.saveAndFlush(composicao);

        // Get all the composicaoList
        restComposicaoMockMvc.perform(get("/api/composicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(composicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].servidor").value(hasItem(DEFAULT_SERVIDOR)));
    }

    @Test
    @Transactional
    public void getComposicao() throws Exception {
        // Initialize the database
        composicaoRepository.saveAndFlush(composicao);

        // Get the composicao
        restComposicaoMockMvc.perform(get("/api/composicaos/{id}", composicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(composicao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.servidor").value(DEFAULT_SERVIDOR));
    }

    @Test
    @Transactional
    public void getNonExistingComposicao() throws Exception {
        // Get the composicao
        restComposicaoMockMvc.perform(get("/api/composicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComposicao() throws Exception {
        // Initialize the database
        composicaoRepository.saveAndFlush(composicao);

        int databaseSizeBeforeUpdate = composicaoRepository.findAll().size();

        // Update the composicao
        Composicao updatedComposicao = composicaoRepository.findById(composicao.getId()).get();
        // Disconnect from session so that the updates on updatedComposicao are not directly saved in db
        em.detach(updatedComposicao);

        updatedComposicao.setNome(UPDATED_NOME);
        updatedComposicao.setServidor(UPDATED_SERVIDOR);

        ComposicaoDTO composicaoDTO = composicaoMapper.toDto(updatedComposicao);

        restComposicaoMockMvc.perform(put("/api/composicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composicaoDTO)))
            .andExpect(status().isOk());

        // Validate the Composicao in the database
        List<Composicao> composicaoList = composicaoRepository.findAll();
        assertThat(composicaoList).hasSize(databaseSizeBeforeUpdate);
        Composicao testComposicao = composicaoList.get(composicaoList.size() - 1);
        assertThat(testComposicao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testComposicao.getServidor()).isEqualTo(UPDATED_SERVIDOR);

        // Validate the Composicao in Elasticsearch
        verify(mockComposicaoSearchRepository, times(1)).save(testComposicao);
    }

    @Test
    @Transactional
    public void updateNonExistingComposicao() throws Exception {
        int databaseSizeBeforeUpdate = composicaoRepository.findAll().size();

        // Create the Composicao
        ComposicaoDTO composicaoDTO = composicaoMapper.toDto(composicao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComposicaoMockMvc.perform(put("/api/composicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composicaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Composicao in the database
        List<Composicao> composicaoList = composicaoRepository.findAll();
        assertThat(composicaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Composicao in Elasticsearch
        verify(mockComposicaoSearchRepository, times(0)).save(composicao);
    }

    @Test
    @Transactional
    public void deleteComposicao() throws Exception {
        // Initialize the database
        composicaoRepository.saveAndFlush(composicao);

        int databaseSizeBeforeDelete = composicaoRepository.findAll().size();

        // Delete the composicao
        restComposicaoMockMvc.perform(delete("/api/composicaos/{id}", composicao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Composicao> composicaoList = composicaoRepository.findAll();
        assertThat(composicaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Composicao in Elasticsearch
        verify(mockComposicaoSearchRepository, times(1)).deleteById(composicao.getId());
    }

    @Test
    @Transactional
    public void searchComposicao() throws Exception {
        // Initialize the database
        composicaoRepository.saveAndFlush(composicao);
        when(mockComposicaoSearchRepository.search(queryStringQuery("id:" + composicao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(composicao), PageRequest.of(0, 1), 1));
        // Search the composicao
        restComposicaoMockMvc.perform(get("/api/_search/composicaos?query=id:" + composicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(composicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].servidor").value(hasItem(DEFAULT_SERVIDOR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Composicao.class);
        Composicao composicao1 = new Composicao();
        composicao1.setId(1L);
        Composicao composicao2 = new Composicao();
        composicao2.setId(composicao1.getId());
        assertThat(composicao1).isEqualTo(composicao2);
        composicao2.setId(2L);
        assertThat(composicao1).isNotEqualTo(composicao2);
        composicao1.setId(null);
        assertThat(composicao1).isNotEqualTo(composicao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComposicaoDTO.class);
        ComposicaoDTO composicaoDTO1 = new ComposicaoDTO();
        composicaoDTO1.setId(1L);
        ComposicaoDTO composicaoDTO2 = new ComposicaoDTO();
        assertThat(composicaoDTO1).isNotEqualTo(composicaoDTO2);
        composicaoDTO2.setId(composicaoDTO1.getId());
        assertThat(composicaoDTO1).isEqualTo(composicaoDTO2);
        composicaoDTO2.setId(2L);
        assertThat(composicaoDTO1).isNotEqualTo(composicaoDTO2);
        composicaoDTO1.setId(null);
        assertThat(composicaoDTO1).isNotEqualTo(composicaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(composicaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(composicaoMapper.fromId(null)).isNull();
    }
}
