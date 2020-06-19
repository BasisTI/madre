package br.com.basis.consulta.web.rest;

import br.com.basis.consulta.MadreconsultaApp;
import br.com.basis.consulta.domain.Entidade;
import br.com.basis.consulta.repository.EntidadeRepository;
import br.com.basis.consulta.repository.search.EntidadeSearchRepository;
import br.com.basis.consulta.service.EntidadeService;
import br.com.basis.consulta.service.dto.EntidadeDTO;
import br.com.basis.consulta.service.mapper.EntidadeMapper;
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

import static br.com.basis.consulta.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EntidadeResource} REST controller.
 */
@SpringBootTest(classes = MadreconsultaApp.class)
public class EntidadeResourceIT {

    private static final Long DEFAULT_PACIENTE_ID = 1L;
    private static final Long UPDATED_PACIENTE_ID = 2L;

    @Autowired
    private EntidadeRepository entidadeRepository;

    @Autowired
    private EntidadeMapper entidadeMapper;

    @Autowired
    private EntidadeService entidadeService;

    /**
     * This repository is mocked in the br.com.basis.consulta.repository.search test package.
     *
     * @see br.com.basis.consulta.repository.search.EntidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EntidadeSearchRepository mockEntidadeSearchRepository;

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

    private MockMvc restEntidadeMockMvc;

    private Entidade entidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntidadeResource entidadeResource = new EntidadeResource(entidadeService);
        this.restEntidadeMockMvc = MockMvcBuilders.standaloneSetup(entidadeResource)
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
    public static Entidade createEntity(EntityManager em) {
        Entidade entidade = new Entidade()
            .pacienteId(DEFAULT_PACIENTE_ID);
        return entidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidade createUpdatedEntity(EntityManager em) {
        Entidade entidade = new Entidade()
            .pacienteId(UPDATED_PACIENTE_ID);
        return entidade;
    }

    @BeforeEach
    public void initTest() {
        entidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntidade() throws Exception {
        int databaseSizeBeforeCreate = entidadeRepository.findAll().size();

        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);
        restEntidadeMockMvc.perform(post("/api/entidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Entidade testEntidade = entidadeList.get(entidadeList.size() - 1);
        assertThat(testEntidade.getPacienteId()).isEqualTo(DEFAULT_PACIENTE_ID);

        // Validate the Entidade in Elasticsearch
        verify(mockEntidadeSearchRepository, times(1)).save(testEntidade);
    }

    @Test
    @Transactional
    public void createEntidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entidadeRepository.findAll().size();

        // Create the Entidade with an existing ID
        entidade.setId(1L);
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadeMockMvc.perform(post("/api/entidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Entidade in Elasticsearch
        verify(mockEntidadeSearchRepository, times(0)).save(entidade);
    }


    @Test
    @Transactional
    public void getAllEntidades() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        // Get all the entidadeList
        restEntidadeMockMvc.perform(get("/api/entidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacienteId").value(hasItem(DEFAULT_PACIENTE_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getEntidade() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        // Get the entidade
        restEntidadeMockMvc.perform(get("/api/entidades/{id}", entidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entidade.getId().intValue()))
            .andExpect(jsonPath("$.pacienteId").value(DEFAULT_PACIENTE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntidade() throws Exception {
        // Get the entidade
        restEntidadeMockMvc.perform(get("/api/entidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntidade() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();

        // Update the entidade
        Entidade updatedEntidade = entidadeRepository.findById(entidade.getId()).get();
        // Disconnect from session so that the updates on updatedEntidade are not directly saved in db
        em.detach(updatedEntidade);
        updatedEntidade
            .pacienteId(UPDATED_PACIENTE_ID);
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(updatedEntidade);

        restEntidadeMockMvc.perform(put("/api/entidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeDTO)))
            .andExpect(status().isOk());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
        Entidade testEntidade = entidadeList.get(entidadeList.size() - 1);
        assertThat(testEntidade.getPacienteId()).isEqualTo(UPDATED_PACIENTE_ID);

        // Validate the Entidade in Elasticsearch
        verify(mockEntidadeSearchRepository, times(1)).save(testEntidade);
    }

    @Test
    @Transactional
    public void updateNonExistingEntidade() throws Exception {
        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();

        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadeMockMvc.perform(put("/api/entidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Entidade in Elasticsearch
        verify(mockEntidadeSearchRepository, times(0)).save(entidade);
    }

    @Test
    @Transactional
    public void deleteEntidade() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        int databaseSizeBeforeDelete = entidadeRepository.findAll().size();

        // Delete the entidade
        restEntidadeMockMvc.perform(delete("/api/entidades/{id}", entidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Entidade in Elasticsearch
        verify(mockEntidadeSearchRepository, times(1)).deleteById(entidade.getId());
    }

    @Test
    @Transactional
    public void searchEntidade() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);
        when(mockEntidadeSearchRepository.search(queryStringQuery("id:" + entidade.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(entidade), PageRequest.of(0, 1), 1));
        // Search the entidade
        restEntidadeMockMvc.perform(get("/api/_search/entidades?query=id:" + entidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].pacienteId").value(hasItem(DEFAULT_PACIENTE_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entidade.class);
        Entidade entidade1 = new Entidade();
        entidade1.setId(1L);
        Entidade entidade2 = new Entidade();
        entidade2.setId(entidade1.getId());
        assertThat(entidade1).isEqualTo(entidade2);
        entidade2.setId(2L);
        assertThat(entidade1).isNotEqualTo(entidade2);
        entidade1.setId(null);
        assertThat(entidade1).isNotEqualTo(entidade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadeDTO.class);
        EntidadeDTO entidadeDTO1 = new EntidadeDTO();
        entidadeDTO1.setId(1L);
        EntidadeDTO entidadeDTO2 = new EntidadeDTO();
        assertThat(entidadeDTO1).isNotEqualTo(entidadeDTO2);
        entidadeDTO2.setId(entidadeDTO1.getId());
        assertThat(entidadeDTO1).isEqualTo(entidadeDTO2);
        entidadeDTO2.setId(2L);
        assertThat(entidadeDTO1).isNotEqualTo(entidadeDTO2);
        entidadeDTO1.setId(null);
        assertThat(entidadeDTO1).isNotEqualTo(entidadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entidadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entidadeMapper.fromId(null)).isNull();
    }
}
