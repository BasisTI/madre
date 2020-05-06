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
import br.com.basis.madre.domain.ConvenioDeSaude;
import br.com.basis.madre.repository.ConvenioDeSaudeRepository;
import br.com.basis.madre.repository.search.ConvenioDeSaudeSearchRepository;
import br.com.basis.madre.service.ConvenioDeSaudeService;
import br.com.basis.madre.service.dto.ConvenioDeSaudeDTO;
import br.com.basis.madre.service.mapper.ConvenioDeSaudeMapper;
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
 * Integration tests for the {@link ConvenioDeSaudeResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class ConvenioDeSaudeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ConvenioDeSaudeRepository convenioDeSaudeRepository;

    @Autowired
    private ConvenioDeSaudeMapper convenioDeSaudeMapper;

    @Autowired
    private ConvenioDeSaudeService convenioDeSaudeService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.ConvenioDeSaudeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConvenioDeSaudeSearchRepository mockConvenioDeSaudeSearchRepository;

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

    private MockMvc restConvenioDeSaudeMockMvc;

    private ConvenioDeSaude convenioDeSaude;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConvenioDeSaudeResource convenioDeSaudeResource = new ConvenioDeSaudeResource(
            convenioDeSaudeService);
        this.restConvenioDeSaudeMockMvc = MockMvcBuilders.standaloneSetup(convenioDeSaudeResource)
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
    public static ConvenioDeSaude createEntity(EntityManager em) {
        ConvenioDeSaude convenioDeSaude = new ConvenioDeSaude()
            .nome(DEFAULT_NOME);
        return convenioDeSaude;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static ConvenioDeSaude createUpdatedEntity(EntityManager em) {
        ConvenioDeSaude convenioDeSaude = new ConvenioDeSaude()
            .nome(UPDATED_NOME);
        return convenioDeSaude;
    }

    @BeforeEach
    public void initTest() {
        convenioDeSaude = createEntity(em);
    }

    @Test
    @Transactional
    public void createConvenioDeSaude() throws Exception {
        int databaseSizeBeforeCreate = convenioDeSaudeRepository.findAll().size();

        // Create the ConvenioDeSaude
        ConvenioDeSaudeDTO convenioDeSaudeDTO = convenioDeSaudeMapper.toDto(convenioDeSaude);
        restConvenioDeSaudeMockMvc.perform(post("/api/convenio-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDeSaudeDTO)))
            .andExpect(status().isCreated());

        // Validate the ConvenioDeSaude in the database
        List<ConvenioDeSaude> convenioDeSaudeList = convenioDeSaudeRepository.findAll();
        assertThat(convenioDeSaudeList).hasSize(databaseSizeBeforeCreate + 1);
        ConvenioDeSaude testConvenioDeSaude = convenioDeSaudeList
            .get(convenioDeSaudeList.size() - 1);
        assertThat(testConvenioDeSaude.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the ConvenioDeSaude in Elasticsearch
        verify(mockConvenioDeSaudeSearchRepository, times(1)).save(testConvenioDeSaude);
    }

    @Test
    @Transactional
    public void createConvenioDeSaudeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = convenioDeSaudeRepository.findAll().size();

        // Create the ConvenioDeSaude with an existing ID
        convenioDeSaude.setId(1L);
        ConvenioDeSaudeDTO convenioDeSaudeDTO = convenioDeSaudeMapper.toDto(convenioDeSaude);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConvenioDeSaudeMockMvc.perform(post("/api/convenio-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDeSaudeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConvenioDeSaude in the database
        List<ConvenioDeSaude> convenioDeSaudeList = convenioDeSaudeRepository.findAll();
        assertThat(convenioDeSaudeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ConvenioDeSaude in Elasticsearch
        verify(mockConvenioDeSaudeSearchRepository, times(0)).save(convenioDeSaude);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = convenioDeSaudeRepository.findAll().size();
        // set the field null
        convenioDeSaude.setNome(null);

        // Create the ConvenioDeSaude, which fails.
        ConvenioDeSaudeDTO convenioDeSaudeDTO = convenioDeSaudeMapper.toDto(convenioDeSaude);

        restConvenioDeSaudeMockMvc.perform(post("/api/convenio-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDeSaudeDTO)))
            .andExpect(status().isBadRequest());

        List<ConvenioDeSaude> convenioDeSaudeList = convenioDeSaudeRepository.findAll();
        assertThat(convenioDeSaudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConvenioDeSaudes() throws Exception {
        // Initialize the database
        convenioDeSaudeRepository.saveAndFlush(convenioDeSaude);

        // Get all the convenioDeSaudeList
        restConvenioDeSaudeMockMvc.perform(get("/api/convenio-de-saudes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(convenioDeSaude.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getConvenioDeSaude() throws Exception {
        // Initialize the database
        convenioDeSaudeRepository.saveAndFlush(convenioDeSaude);

        // Get the convenioDeSaude
        restConvenioDeSaudeMockMvc
            .perform(get("/api/convenio-de-saudes/{id}", convenioDeSaude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(convenioDeSaude.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingConvenioDeSaude() throws Exception {
        // Get the convenioDeSaude
        restConvenioDeSaudeMockMvc.perform(get("/api/convenio-de-saudes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConvenioDeSaude() throws Exception {
        // Initialize the database
        convenioDeSaudeRepository.saveAndFlush(convenioDeSaude);

        int databaseSizeBeforeUpdate = convenioDeSaudeRepository.findAll().size();

        // Update the convenioDeSaude
        ConvenioDeSaude updatedConvenioDeSaude = convenioDeSaudeRepository
            .findById(convenioDeSaude.getId()).get();
        // Disconnect from session so that the updates on updatedConvenioDeSaude are not directly saved in db
        em.detach(updatedConvenioDeSaude);
        updatedConvenioDeSaude
            .nome(UPDATED_NOME);
        ConvenioDeSaudeDTO convenioDeSaudeDTO = convenioDeSaudeMapper.toDto(updatedConvenioDeSaude);

        restConvenioDeSaudeMockMvc.perform(put("/api/convenio-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDeSaudeDTO)))
            .andExpect(status().isOk());

        // Validate the ConvenioDeSaude in the database
        List<ConvenioDeSaude> convenioDeSaudeList = convenioDeSaudeRepository.findAll();
        assertThat(convenioDeSaudeList).hasSize(databaseSizeBeforeUpdate);
        ConvenioDeSaude testConvenioDeSaude = convenioDeSaudeList
            .get(convenioDeSaudeList.size() - 1);
        assertThat(testConvenioDeSaude.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the ConvenioDeSaude in Elasticsearch
        verify(mockConvenioDeSaudeSearchRepository, times(1)).save(testConvenioDeSaude);
    }

    @Test
    @Transactional
    public void updateNonExistingConvenioDeSaude() throws Exception {
        int databaseSizeBeforeUpdate = convenioDeSaudeRepository.findAll().size();

        // Create the ConvenioDeSaude
        ConvenioDeSaudeDTO convenioDeSaudeDTO = convenioDeSaudeMapper.toDto(convenioDeSaude);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConvenioDeSaudeMockMvc.perform(put("/api/convenio-de-saudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(convenioDeSaudeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConvenioDeSaude in the database
        List<ConvenioDeSaude> convenioDeSaudeList = convenioDeSaudeRepository.findAll();
        assertThat(convenioDeSaudeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ConvenioDeSaude in Elasticsearch
        verify(mockConvenioDeSaudeSearchRepository, times(0)).save(convenioDeSaude);
    }

    @Test
    @Transactional
    public void deleteConvenioDeSaude() throws Exception {
        // Initialize the database
        convenioDeSaudeRepository.saveAndFlush(convenioDeSaude);

        int databaseSizeBeforeDelete = convenioDeSaudeRepository.findAll().size();

        // Delete the convenioDeSaude
        restConvenioDeSaudeMockMvc
            .perform(delete("/api/convenio-de-saudes/{id}", convenioDeSaude.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConvenioDeSaude> convenioDeSaudeList = convenioDeSaudeRepository.findAll();
        assertThat(convenioDeSaudeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ConvenioDeSaude in Elasticsearch
        verify(mockConvenioDeSaudeSearchRepository, times(1)).deleteById(convenioDeSaude.getId());
    }

    @Test
    @Transactional
    public void searchConvenioDeSaude() throws Exception {
        // Initialize the database
        convenioDeSaudeRepository.saveAndFlush(convenioDeSaude);
        when(mockConvenioDeSaudeSearchRepository
            .search(queryStringQuery("id:" + convenioDeSaude.getId()), PageRequest.of(0, 20)))
            .thenReturn(
                new PageImpl<>(Collections.singletonList(convenioDeSaude), PageRequest.of(0, 1),
                    1));
        // Search the convenioDeSaude
        restConvenioDeSaudeMockMvc
            .perform(get("/api/_search/convenio-de-saudes?query=id:" + convenioDeSaude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(convenioDeSaude.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConvenioDeSaude.class);
        ConvenioDeSaude convenioDeSaude1 = new ConvenioDeSaude();
        convenioDeSaude1.setId(1L);
        ConvenioDeSaude convenioDeSaude2 = new ConvenioDeSaude();
        convenioDeSaude2.setId(convenioDeSaude1.getId());
        assertThat(convenioDeSaude1).isEqualTo(convenioDeSaude2);
        convenioDeSaude2.setId(2L);
        assertThat(convenioDeSaude1).isNotEqualTo(convenioDeSaude2);
        convenioDeSaude1.setId(null);
        assertThat(convenioDeSaude1).isNotEqualTo(convenioDeSaude2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConvenioDeSaudeDTO.class);
        ConvenioDeSaudeDTO convenioDeSaudeDTO1 = new ConvenioDeSaudeDTO();
        convenioDeSaudeDTO1.setId(1L);
        ConvenioDeSaudeDTO convenioDeSaudeDTO2 = new ConvenioDeSaudeDTO();
        assertThat(convenioDeSaudeDTO1).isNotEqualTo(convenioDeSaudeDTO2);
        convenioDeSaudeDTO2.setId(convenioDeSaudeDTO1.getId());
        assertThat(convenioDeSaudeDTO1).isEqualTo(convenioDeSaudeDTO2);
        convenioDeSaudeDTO2.setId(2L);
        assertThat(convenioDeSaudeDTO1).isNotEqualTo(convenioDeSaudeDTO2);
        convenioDeSaudeDTO1.setId(null);
        assertThat(convenioDeSaudeDTO1).isNotEqualTo(convenioDeSaudeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(convenioDeSaudeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(convenioDeSaudeMapper.fromId(null)).isNull();
    }
}
