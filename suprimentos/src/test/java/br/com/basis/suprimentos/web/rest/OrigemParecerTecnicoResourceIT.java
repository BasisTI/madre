package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.OrigemParecerTecnico;
import br.com.basis.suprimentos.repository.OrigemParecerTecnicoRepository;
import br.com.basis.suprimentos.repository.search.OrigemParecerTecnicoSearchRepository;
import br.com.basis.suprimentos.service.OrigemParecerTecnicoService;
import br.com.basis.suprimentos.service.dto.OrigemParecerTecnicoDTO;
import br.com.basis.suprimentos.service.mapper.OrigemParecerTecnicoMapper;
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
 * Integration tests for the {@link OrigemParecerTecnicoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class OrigemParecerTecnicoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private OrigemParecerTecnicoRepository origemParecerTecnicoRepository;

    @Autowired
    private OrigemParecerTecnicoMapper origemParecerTecnicoMapper;

    @Autowired
    private OrigemParecerTecnicoService origemParecerTecnicoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.OrigemParecerTecnicoSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrigemParecerTecnicoSearchRepository mockOrigemParecerTecnicoSearchRepository;

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

    private MockMvc restOrigemParecerTecnicoMockMvc;

    private OrigemParecerTecnico origemParecerTecnico;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrigemParecerTecnico createEntity(EntityManager em) {
        OrigemParecerTecnico origemParecerTecnico = new OrigemParecerTecnico();
        origemParecerTecnico.setDescricao(DEFAULT_DESCRICAO);
        return origemParecerTecnico;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrigemParecerTecnico createUpdatedEntity(EntityManager em) {
        OrigemParecerTecnico origemParecerTecnico = new OrigemParecerTecnico();
        origemParecerTecnico.setDescricao(UPDATED_DESCRICAO);
        return origemParecerTecnico;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrigemParecerTecnicoResource origemParecerTecnicoResource = new OrigemParecerTecnicoResource(origemParecerTecnicoService);
        this.restOrigemParecerTecnicoMockMvc = MockMvcBuilders.standaloneSetup(origemParecerTecnicoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        origemParecerTecnico = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrigemParecerTecnico() throws Exception {
        int databaseSizeBeforeCreate = origemParecerTecnicoRepository.findAll().size();

        // Create the OrigemParecerTecnico
        OrigemParecerTecnicoDTO origemParecerTecnicoDTO = origemParecerTecnicoMapper.toDto(origemParecerTecnico);
        restOrigemParecerTecnicoMockMvc.perform(post("/api/origem-parecer-tecnicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(origemParecerTecnicoDTO)))
            .andExpect(status().isCreated());

        // Validate the OrigemParecerTecnico in the database
        List<OrigemParecerTecnico> origemParecerTecnicoList = origemParecerTecnicoRepository.findAll();
        assertThat(origemParecerTecnicoList).hasSize(databaseSizeBeforeCreate + 1);
        OrigemParecerTecnico testOrigemParecerTecnico = origemParecerTecnicoList.get(origemParecerTecnicoList.size() - 1);
        assertThat(testOrigemParecerTecnico.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the OrigemParecerTecnico in Elasticsearch
        verify(mockOrigemParecerTecnicoSearchRepository, times(1)).save(testOrigemParecerTecnico);
    }

    @Test
    @Transactional
    public void createOrigemParecerTecnicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = origemParecerTecnicoRepository.findAll().size();

        // Create the OrigemParecerTecnico with an existing ID
        origemParecerTecnico.setId(1L);
        OrigemParecerTecnicoDTO origemParecerTecnicoDTO = origemParecerTecnicoMapper.toDto(origemParecerTecnico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrigemParecerTecnicoMockMvc.perform(post("/api/origem-parecer-tecnicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(origemParecerTecnicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrigemParecerTecnico in the database
        List<OrigemParecerTecnico> origemParecerTecnicoList = origemParecerTecnicoRepository.findAll();
        assertThat(origemParecerTecnicoList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrigemParecerTecnico in Elasticsearch
        verify(mockOrigemParecerTecnicoSearchRepository, times(0)).save(origemParecerTecnico);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = origemParecerTecnicoRepository.findAll().size();
        // set the field null
        origemParecerTecnico.setDescricao(null);

        // Create the OrigemParecerTecnico, which fails.
        OrigemParecerTecnicoDTO origemParecerTecnicoDTO = origemParecerTecnicoMapper.toDto(origemParecerTecnico);

        restOrigemParecerTecnicoMockMvc.perform(post("/api/origem-parecer-tecnicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(origemParecerTecnicoDTO)))
            .andExpect(status().isBadRequest());

        List<OrigemParecerTecnico> origemParecerTecnicoList = origemParecerTecnicoRepository.findAll();
        assertThat(origemParecerTecnicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrigemParecerTecnicos() throws Exception {
        // Initialize the database
        origemParecerTecnicoRepository.saveAndFlush(origemParecerTecnico);

        // Get all the origemParecerTecnicoList
        restOrigemParecerTecnicoMockMvc.perform(get("/api/origem-parecer-tecnicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origemParecerTecnico.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getOrigemParecerTecnico() throws Exception {
        // Initialize the database
        origemParecerTecnicoRepository.saveAndFlush(origemParecerTecnico);

        // Get the origemParecerTecnico
        restOrigemParecerTecnicoMockMvc.perform(get("/api/origem-parecer-tecnicos/{id}", origemParecerTecnico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(origemParecerTecnico.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingOrigemParecerTecnico() throws Exception {
        // Get the origemParecerTecnico
        restOrigemParecerTecnicoMockMvc.perform(get("/api/origem-parecer-tecnicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrigemParecerTecnico() throws Exception {
        // Initialize the database
        origemParecerTecnicoRepository.saveAndFlush(origemParecerTecnico);

        int databaseSizeBeforeUpdate = origemParecerTecnicoRepository.findAll().size();

        // Update the origemParecerTecnico
        OrigemParecerTecnico updatedOrigemParecerTecnico = origemParecerTecnicoRepository.findById(origemParecerTecnico.getId()).get();
        // Disconnect from session so that the updates on updatedOrigemParecerTecnico are not directly saved in db
        em.detach(updatedOrigemParecerTecnico);
        updatedOrigemParecerTecnico
            .setDescricao(UPDATED_DESCRICAO);
        OrigemParecerTecnicoDTO origemParecerTecnicoDTO = origemParecerTecnicoMapper.toDto(updatedOrigemParecerTecnico);

        restOrigemParecerTecnicoMockMvc.perform(put("/api/origem-parecer-tecnicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(origemParecerTecnicoDTO)))
            .andExpect(status().isOk());

        // Validate the OrigemParecerTecnico in the database
        List<OrigemParecerTecnico> origemParecerTecnicoList = origemParecerTecnicoRepository.findAll();
        assertThat(origemParecerTecnicoList).hasSize(databaseSizeBeforeUpdate);
        OrigemParecerTecnico testOrigemParecerTecnico = origemParecerTecnicoList.get(origemParecerTecnicoList.size() - 1);
        assertThat(testOrigemParecerTecnico.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the OrigemParecerTecnico in Elasticsearch
        verify(mockOrigemParecerTecnicoSearchRepository, times(1)).save(testOrigemParecerTecnico);
    }

    @Test
    @Transactional
    public void updateNonExistingOrigemParecerTecnico() throws Exception {
        int databaseSizeBeforeUpdate = origemParecerTecnicoRepository.findAll().size();

        // Create the OrigemParecerTecnico
        OrigemParecerTecnicoDTO origemParecerTecnicoDTO = origemParecerTecnicoMapper.toDto(origemParecerTecnico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrigemParecerTecnicoMockMvc.perform(put("/api/origem-parecer-tecnicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(origemParecerTecnicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrigemParecerTecnico in the database
        List<OrigemParecerTecnico> origemParecerTecnicoList = origemParecerTecnicoRepository.findAll();
        assertThat(origemParecerTecnicoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrigemParecerTecnico in Elasticsearch
        verify(mockOrigemParecerTecnicoSearchRepository, times(0)).save(origemParecerTecnico);
    }

    @Test
    @Transactional
    public void deleteOrigemParecerTecnico() throws Exception {
        // Initialize the database
        origemParecerTecnicoRepository.saveAndFlush(origemParecerTecnico);

        int databaseSizeBeforeDelete = origemParecerTecnicoRepository.findAll().size();

        // Delete the origemParecerTecnico
        restOrigemParecerTecnicoMockMvc.perform(delete("/api/origem-parecer-tecnicos/{id}", origemParecerTecnico.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrigemParecerTecnico> origemParecerTecnicoList = origemParecerTecnicoRepository.findAll();
        assertThat(origemParecerTecnicoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrigemParecerTecnico in Elasticsearch
        verify(mockOrigemParecerTecnicoSearchRepository, times(1)).deleteById(origemParecerTecnico.getId());
    }

    @Test
    @Transactional
    public void searchOrigemParecerTecnico() throws Exception {
        // Initialize the database
        origemParecerTecnicoRepository.saveAndFlush(origemParecerTecnico);
        when(mockOrigemParecerTecnicoSearchRepository.search(queryStringQuery("id:" + origemParecerTecnico.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(origemParecerTecnico), PageRequest.of(0, 1), 1));
        // Search the origemParecerTecnico
        restOrigemParecerTecnicoMockMvc.perform(get("/api/_search/origem-parecer-tecnicos?query=id:" + origemParecerTecnico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origemParecerTecnico.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemParecerTecnico.class);
        OrigemParecerTecnico origemParecerTecnico1 = new OrigemParecerTecnico();
        origemParecerTecnico1.setId(1L);
        OrigemParecerTecnico origemParecerTecnico2 = new OrigemParecerTecnico();
        origemParecerTecnico2.setId(origemParecerTecnico1.getId());
        assertThat(origemParecerTecnico1).isEqualTo(origemParecerTecnico2);
        origemParecerTecnico2.setId(2L);
        assertThat(origemParecerTecnico1).isNotEqualTo(origemParecerTecnico2);
        origemParecerTecnico1.setId(null);
        assertThat(origemParecerTecnico1).isNotEqualTo(origemParecerTecnico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemParecerTecnicoDTO.class);
        OrigemParecerTecnicoDTO origemParecerTecnicoDTO1 = new OrigemParecerTecnicoDTO();
        origemParecerTecnicoDTO1.setId(1L);
        OrigemParecerTecnicoDTO origemParecerTecnicoDTO2 = new OrigemParecerTecnicoDTO();
        assertThat(origemParecerTecnicoDTO1).isNotEqualTo(origemParecerTecnicoDTO2);
        origemParecerTecnicoDTO2.setId(origemParecerTecnicoDTO1.getId());
        assertThat(origemParecerTecnicoDTO1).isEqualTo(origemParecerTecnicoDTO2);
        origemParecerTecnicoDTO2.setId(2L);
        assertThat(origemParecerTecnicoDTO1).isNotEqualTo(origemParecerTecnicoDTO2);
        origemParecerTecnicoDTO1.setId(null);
        assertThat(origemParecerTecnicoDTO1).isNotEqualTo(origemParecerTecnicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(origemParecerTecnicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(origemParecerTecnicoMapper.fromId(null)).isNull();
    }
}
