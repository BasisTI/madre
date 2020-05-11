package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.ViasAdministracao;
import br.com.basis.madre.prescricao.repository.ViasAdministracaoRepository;
import br.com.basis.madre.prescricao.repository.search.ViasAdministracaoSearchRepository;
import br.com.basis.madre.prescricao.service.ViasAdministracaoService;
import br.com.basis.madre.prescricao.service.dto.ViasAdministracaoDTO;
import br.com.basis.madre.prescricao.service.mapper.ViasAdministracaoMapper;
import br.com.basis.madre.prescricao.web.rest.errors.ExceptionTranslator;

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

import static br.com.basis.madre.prescricao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ViasAdministracaoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class ViasAdministracaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    @Autowired
    private ViasAdministracaoRepository viasAdministracaoRepository;

    @Autowired
    private ViasAdministracaoMapper viasAdministracaoMapper;

    @Autowired
    private ViasAdministracaoService viasAdministracaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.ViasAdministracaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ViasAdministracaoSearchRepository mockViasAdministracaoSearchRepository;

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

    private MockMvc restViasAdministracaoMockMvc;

    private ViasAdministracao viasAdministracao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ViasAdministracaoResource viasAdministracaoResource = new ViasAdministracaoResource(viasAdministracaoService);
        this.restViasAdministracaoMockMvc = MockMvcBuilders.standaloneSetup(viasAdministracaoResource)
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
    public static ViasAdministracao createEntity(EntityManager em) {
        ViasAdministracao viasAdministracao = new ViasAdministracao()
            .descricao(DEFAULT_DESCRICAO)
            .sigla(DEFAULT_SIGLA);
        return viasAdministracao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViasAdministracao createUpdatedEntity(EntityManager em) {
        ViasAdministracao viasAdministracao = new ViasAdministracao()
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        return viasAdministracao;
    }

    @BeforeEach
    public void initTest() {
        viasAdministracao = createEntity(em);
    }

    @Test
    @Transactional
    public void createViasAdministracao() throws Exception {
        int databaseSizeBeforeCreate = viasAdministracaoRepository.findAll().size();

        // Create the ViasAdministracao
        ViasAdministracaoDTO viasAdministracaoDTO = viasAdministracaoMapper.toDto(viasAdministracao);
        restViasAdministracaoMockMvc.perform(post("/api/vias-administracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viasAdministracaoDTO)))
            .andExpect(status().isCreated());

        // Validate the ViasAdministracao in the database
        List<ViasAdministracao> viasAdministracaoList = viasAdministracaoRepository.findAll();
        assertThat(viasAdministracaoList).hasSize(databaseSizeBeforeCreate + 1);
        ViasAdministracao testViasAdministracao = viasAdministracaoList.get(viasAdministracaoList.size() - 1);
        assertThat(testViasAdministracao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testViasAdministracao.getSigla()).isEqualTo(DEFAULT_SIGLA);

        // Validate the ViasAdministracao in Elasticsearch
        verify(mockViasAdministracaoSearchRepository, times(1)).save(testViasAdministracao);
    }

    @Test
    @Transactional
    public void createViasAdministracaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = viasAdministracaoRepository.findAll().size();

        // Create the ViasAdministracao with an existing ID
        viasAdministracao.setId(1L);
        ViasAdministracaoDTO viasAdministracaoDTO = viasAdministracaoMapper.toDto(viasAdministracao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restViasAdministracaoMockMvc.perform(post("/api/vias-administracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viasAdministracaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ViasAdministracao in the database
        List<ViasAdministracao> viasAdministracaoList = viasAdministracaoRepository.findAll();
        assertThat(viasAdministracaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ViasAdministracao in Elasticsearch
        verify(mockViasAdministracaoSearchRepository, times(0)).save(viasAdministracao);
    }


    @Test
    @Transactional
    public void getAllViasAdministracaos() throws Exception {
        // Initialize the database
        viasAdministracaoRepository.saveAndFlush(viasAdministracao);

        // Get all the viasAdministracaoList
        restViasAdministracaoMockMvc.perform(get("/api/vias-administracaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viasAdministracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getViasAdministracao() throws Exception {
        // Initialize the database
        viasAdministracaoRepository.saveAndFlush(viasAdministracao);

        // Get the viasAdministracao
        restViasAdministracaoMockMvc.perform(get("/api/vias-administracaos/{id}", viasAdministracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(viasAdministracao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    public void getNonExistingViasAdministracao() throws Exception {
        // Get the viasAdministracao
        restViasAdministracaoMockMvc.perform(get("/api/vias-administracaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateViasAdministracao() throws Exception {
        // Initialize the database
        viasAdministracaoRepository.saveAndFlush(viasAdministracao);

        int databaseSizeBeforeUpdate = viasAdministracaoRepository.findAll().size();

        // Update the viasAdministracao
        ViasAdministracao updatedViasAdministracao = viasAdministracaoRepository.findById(viasAdministracao.getId()).get();
        // Disconnect from session so that the updates on updatedViasAdministracao are not directly saved in db
        em.detach(updatedViasAdministracao);
        updatedViasAdministracao
            .descricao(UPDATED_DESCRICAO)
            .sigla(UPDATED_SIGLA);
        ViasAdministracaoDTO viasAdministracaoDTO = viasAdministracaoMapper.toDto(updatedViasAdministracao);

        restViasAdministracaoMockMvc.perform(put("/api/vias-administracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viasAdministracaoDTO)))
            .andExpect(status().isOk());

        // Validate the ViasAdministracao in the database
        List<ViasAdministracao> viasAdministracaoList = viasAdministracaoRepository.findAll();
        assertThat(viasAdministracaoList).hasSize(databaseSizeBeforeUpdate);
        ViasAdministracao testViasAdministracao = viasAdministracaoList.get(viasAdministracaoList.size() - 1);
        assertThat(testViasAdministracao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testViasAdministracao.getSigla()).isEqualTo(UPDATED_SIGLA);

        // Validate the ViasAdministracao in Elasticsearch
        verify(mockViasAdministracaoSearchRepository, times(1)).save(testViasAdministracao);
    }

    @Test
    @Transactional
    public void updateNonExistingViasAdministracao() throws Exception {
        int databaseSizeBeforeUpdate = viasAdministracaoRepository.findAll().size();

        // Create the ViasAdministracao
        ViasAdministracaoDTO viasAdministracaoDTO = viasAdministracaoMapper.toDto(viasAdministracao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViasAdministracaoMockMvc.perform(put("/api/vias-administracaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viasAdministracaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ViasAdministracao in the database
        List<ViasAdministracao> viasAdministracaoList = viasAdministracaoRepository.findAll();
        assertThat(viasAdministracaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ViasAdministracao in Elasticsearch
        verify(mockViasAdministracaoSearchRepository, times(0)).save(viasAdministracao);
    }

    @Test
    @Transactional
    public void deleteViasAdministracao() throws Exception {
        // Initialize the database
        viasAdministracaoRepository.saveAndFlush(viasAdministracao);

        int databaseSizeBeforeDelete = viasAdministracaoRepository.findAll().size();

        // Delete the viasAdministracao
        restViasAdministracaoMockMvc.perform(delete("/api/vias-administracaos/{id}", viasAdministracao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ViasAdministracao> viasAdministracaoList = viasAdministracaoRepository.findAll();
        assertThat(viasAdministracaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ViasAdministracao in Elasticsearch
        verify(mockViasAdministracaoSearchRepository, times(1)).deleteById(viasAdministracao.getId());
    }

    @Test
    @Transactional
    public void searchViasAdministracao() throws Exception {
        // Initialize the database
        viasAdministracaoRepository.saveAndFlush(viasAdministracao);
        when(mockViasAdministracaoSearchRepository.search(queryStringQuery("id:" + viasAdministracao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(viasAdministracao), PageRequest.of(0, 1), 1));
        // Search the viasAdministracao
        restViasAdministracaoMockMvc.perform(get("/api/_search/vias-administracaos?query=id:" + viasAdministracao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viasAdministracao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViasAdministracao.class);
        ViasAdministracao viasAdministracao1 = new ViasAdministracao();
        viasAdministracao1.setId(1L);
        ViasAdministracao viasAdministracao2 = new ViasAdministracao();
        viasAdministracao2.setId(viasAdministracao1.getId());
        assertThat(viasAdministracao1).isEqualTo(viasAdministracao2);
        viasAdministracao2.setId(2L);
        assertThat(viasAdministracao1).isNotEqualTo(viasAdministracao2);
        viasAdministracao1.setId(null);
        assertThat(viasAdministracao1).isNotEqualTo(viasAdministracao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViasAdministracaoDTO.class);
        ViasAdministracaoDTO viasAdministracaoDTO1 = new ViasAdministracaoDTO();
        viasAdministracaoDTO1.setId(1L);
        ViasAdministracaoDTO viasAdministracaoDTO2 = new ViasAdministracaoDTO();
        assertThat(viasAdministracaoDTO1).isNotEqualTo(viasAdministracaoDTO2);
        viasAdministracaoDTO2.setId(viasAdministracaoDTO1.getId());
        assertThat(viasAdministracaoDTO1).isEqualTo(viasAdministracaoDTO2);
        viasAdministracaoDTO2.setId(2L);
        assertThat(viasAdministracaoDTO1).isNotEqualTo(viasAdministracaoDTO2);
        viasAdministracaoDTO1.setId(null);
        assertThat(viasAdministracaoDTO1).isNotEqualTo(viasAdministracaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(viasAdministracaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(viasAdministracaoMapper.fromId(null)).isNull();
    }
}
