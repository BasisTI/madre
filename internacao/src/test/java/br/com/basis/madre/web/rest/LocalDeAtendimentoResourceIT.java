package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.LocalDeAtendimento;
import br.com.basis.madre.repository.LocalDeAtendimentoRepository;
import br.com.basis.madre.repository.search.LocalDeAtendimentoSearchRepository;
import br.com.basis.madre.service.LocalDeAtendimentoService;
import br.com.basis.madre.service.dto.LocalDeAtendimentoDTO;
import br.com.basis.madre.service.mapper.LocalDeAtendimentoMapper;
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
 * Integration tests for the {@link LocalDeAtendimentoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class LocalDeAtendimentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private LocalDeAtendimentoRepository localDeAtendimentoRepository;

    @Autowired
    private LocalDeAtendimentoMapper localDeAtendimentoMapper;

    @Autowired
    private LocalDeAtendimentoService localDeAtendimentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.LocalDeAtendimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private LocalDeAtendimentoSearchRepository mockLocalDeAtendimentoSearchRepository;

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

    private MockMvc restLocalDeAtendimentoMockMvc;

    private LocalDeAtendimento localDeAtendimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalDeAtendimentoResource localDeAtendimentoResource = new LocalDeAtendimentoResource(
            localDeAtendimentoService);
        this.restLocalDeAtendimentoMockMvc = MockMvcBuilders
            .standaloneSetup(localDeAtendimentoResource)
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
    public static LocalDeAtendimento createEntity(EntityManager em) {
        LocalDeAtendimento localDeAtendimento = new LocalDeAtendimento()
            .nome(DEFAULT_NOME);
        return localDeAtendimento;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static LocalDeAtendimento createUpdatedEntity(EntityManager em) {
        LocalDeAtendimento localDeAtendimento = new LocalDeAtendimento()
            .nome(UPDATED_NOME);
        return localDeAtendimento;
    }

    @BeforeEach
    public void initTest() {
        localDeAtendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalDeAtendimento() throws Exception {
        int databaseSizeBeforeCreate = localDeAtendimentoRepository.findAll().size();

        // Create the LocalDeAtendimento
        LocalDeAtendimentoDTO localDeAtendimentoDTO = localDeAtendimentoMapper
            .toDto(localDeAtendimento);
        restLocalDeAtendimentoMockMvc.perform(post("/api/local-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localDeAtendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalDeAtendimento in the database
        List<LocalDeAtendimento> localDeAtendimentoList = localDeAtendimentoRepository.findAll();
        assertThat(localDeAtendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        LocalDeAtendimento testLocalDeAtendimento = localDeAtendimentoList
            .get(localDeAtendimentoList.size() - 1);
        assertThat(testLocalDeAtendimento.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the LocalDeAtendimento in Elasticsearch
        verify(mockLocalDeAtendimentoSearchRepository, times(1)).save(testLocalDeAtendimento);
    }

    @Test
    @Transactional
    public void createLocalDeAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localDeAtendimentoRepository.findAll().size();

        // Create the LocalDeAtendimento with an existing ID
        localDeAtendimento.setId(1L);
        LocalDeAtendimentoDTO localDeAtendimentoDTO = localDeAtendimentoMapper
            .toDto(localDeAtendimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalDeAtendimentoMockMvc.perform(post("/api/local-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localDeAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalDeAtendimento in the database
        List<LocalDeAtendimento> localDeAtendimentoList = localDeAtendimentoRepository.findAll();
        assertThat(localDeAtendimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the LocalDeAtendimento in Elasticsearch
        verify(mockLocalDeAtendimentoSearchRepository, times(0)).save(localDeAtendimento);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localDeAtendimentoRepository.findAll().size();
        // set the field null
        localDeAtendimento.setNome(null);

        // Create the LocalDeAtendimento, which fails.
        LocalDeAtendimentoDTO localDeAtendimentoDTO = localDeAtendimentoMapper
            .toDto(localDeAtendimento);

        restLocalDeAtendimentoMockMvc.perform(post("/api/local-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localDeAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        List<LocalDeAtendimento> localDeAtendimentoList = localDeAtendimentoRepository.findAll();
        assertThat(localDeAtendimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalDeAtendimentos() throws Exception {
        // Initialize the database
        localDeAtendimentoRepository.saveAndFlush(localDeAtendimento);

        // Get all the localDeAtendimentoList
        restLocalDeAtendimentoMockMvc.perform(get("/api/local-de-atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localDeAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getLocalDeAtendimento() throws Exception {
        // Initialize the database
        localDeAtendimentoRepository.saveAndFlush(localDeAtendimento);

        // Get the localDeAtendimento
        restLocalDeAtendimentoMockMvc
            .perform(get("/api/local-de-atendimentos/{id}", localDeAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localDeAtendimento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingLocalDeAtendimento() throws Exception {
        // Get the localDeAtendimento
        restLocalDeAtendimentoMockMvc
            .perform(get("/api/local-de-atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalDeAtendimento() throws Exception {
        // Initialize the database
        localDeAtendimentoRepository.saveAndFlush(localDeAtendimento);

        int databaseSizeBeforeUpdate = localDeAtendimentoRepository.findAll().size();

        // Update the localDeAtendimento
        LocalDeAtendimento updatedLocalDeAtendimento = localDeAtendimentoRepository
            .findById(localDeAtendimento.getId()).get();
        // Disconnect from session so that the updates on updatedLocalDeAtendimento are not directly saved in db
        em.detach(updatedLocalDeAtendimento);
        updatedLocalDeAtendimento
            .nome(UPDATED_NOME);
        LocalDeAtendimentoDTO localDeAtendimentoDTO = localDeAtendimentoMapper
            .toDto(updatedLocalDeAtendimento);

        restLocalDeAtendimentoMockMvc.perform(put("/api/local-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localDeAtendimentoDTO)))
            .andExpect(status().isOk());

        // Validate the LocalDeAtendimento in the database
        List<LocalDeAtendimento> localDeAtendimentoList = localDeAtendimentoRepository.findAll();
        assertThat(localDeAtendimentoList).hasSize(databaseSizeBeforeUpdate);
        LocalDeAtendimento testLocalDeAtendimento = localDeAtendimentoList
            .get(localDeAtendimentoList.size() - 1);
        assertThat(testLocalDeAtendimento.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the LocalDeAtendimento in Elasticsearch
        verify(mockLocalDeAtendimentoSearchRepository, times(1)).save(testLocalDeAtendimento);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalDeAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = localDeAtendimentoRepository.findAll().size();

        // Create the LocalDeAtendimento
        LocalDeAtendimentoDTO localDeAtendimentoDTO = localDeAtendimentoMapper
            .toDto(localDeAtendimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalDeAtendimentoMockMvc.perform(put("/api/local-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localDeAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalDeAtendimento in the database
        List<LocalDeAtendimento> localDeAtendimentoList = localDeAtendimentoRepository.findAll();
        assertThat(localDeAtendimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LocalDeAtendimento in Elasticsearch
        verify(mockLocalDeAtendimentoSearchRepository, times(0)).save(localDeAtendimento);
    }

    @Test
    @Transactional
    public void deleteLocalDeAtendimento() throws Exception {
        // Initialize the database
        localDeAtendimentoRepository.saveAndFlush(localDeAtendimento);

        int databaseSizeBeforeDelete = localDeAtendimentoRepository.findAll().size();

        // Delete the localDeAtendimento
        restLocalDeAtendimentoMockMvc
            .perform(delete("/api/local-de-atendimentos/{id}", localDeAtendimento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocalDeAtendimento> localDeAtendimentoList = localDeAtendimentoRepository.findAll();
        assertThat(localDeAtendimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LocalDeAtendimento in Elasticsearch
        verify(mockLocalDeAtendimentoSearchRepository, times(1))
            .deleteById(localDeAtendimento.getId());
    }

    @Test
    @Transactional
    public void searchLocalDeAtendimento() throws Exception {
        // Initialize the database
        localDeAtendimentoRepository.saveAndFlush(localDeAtendimento);
        when(mockLocalDeAtendimentoSearchRepository
            .search(queryStringQuery("id:" + localDeAtendimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(
                new PageImpl<>(Collections.singletonList(localDeAtendimento), PageRequest.of(0, 1),
                    1));
        // Search the localDeAtendimento
        restLocalDeAtendimentoMockMvc.perform(
            get("/api/_search/local-de-atendimentos?query=id:" + localDeAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localDeAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalDeAtendimento.class);
        LocalDeAtendimento localDeAtendimento1 = new LocalDeAtendimento();
        localDeAtendimento1.setId(1L);
        LocalDeAtendimento localDeAtendimento2 = new LocalDeAtendimento();
        localDeAtendimento2.setId(localDeAtendimento1.getId());
        assertThat(localDeAtendimento1).isEqualTo(localDeAtendimento2);
        localDeAtendimento2.setId(2L);
        assertThat(localDeAtendimento1).isNotEqualTo(localDeAtendimento2);
        localDeAtendimento1.setId(null);
        assertThat(localDeAtendimento1).isNotEqualTo(localDeAtendimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalDeAtendimentoDTO.class);
        LocalDeAtendimentoDTO localDeAtendimentoDTO1 = new LocalDeAtendimentoDTO();
        localDeAtendimentoDTO1.setId(1L);
        LocalDeAtendimentoDTO localDeAtendimentoDTO2 = new LocalDeAtendimentoDTO();
        assertThat(localDeAtendimentoDTO1).isNotEqualTo(localDeAtendimentoDTO2);
        localDeAtendimentoDTO2.setId(localDeAtendimentoDTO1.getId());
        assertThat(localDeAtendimentoDTO1).isEqualTo(localDeAtendimentoDTO2);
        localDeAtendimentoDTO2.setId(2L);
        assertThat(localDeAtendimentoDTO1).isNotEqualTo(localDeAtendimentoDTO2);
        localDeAtendimentoDTO1.setId(null);
        assertThat(localDeAtendimentoDTO1).isNotEqualTo(localDeAtendimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(localDeAtendimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(localDeAtendimentoMapper.fromId(null)).isNull();
    }
}
