package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Clinica;
import br.com.basis.madre.repository.ClinicaRepository;
import br.com.basis.madre.repository.search.ClinicaSearchRepository;
import br.com.basis.madre.service.ClinicaService;
import br.com.basis.madre.service.dto.ClinicaDTO;
import br.com.basis.madre.service.mapper.ClinicaMapper;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClinicaResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class ClinicaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ClinicaMapper clinicaMapper;

    @Autowired
    private ClinicaService clinicaService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.ClinicaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClinicaSearchRepository mockClinicaSearchRepository;

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

    private MockMvc restClinicaMockMvc;

    private Clinica clinica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClinicaResource clinicaResource = new ClinicaResource(clinicaService);
        this.restClinicaMockMvc = MockMvcBuilders.standaloneSetup(clinicaResource)
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
    public static Clinica createEntity(EntityManager em) {
        Clinica clinica = new Clinica()
            .descricao(DEFAULT_NOME);
        return clinica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinica createUpdatedEntity(EntityManager em) {
        Clinica clinica = new Clinica()
            .descricao(UPDATED_NOME);
        return clinica;
    }

    @BeforeEach
    public void initTest() {
        clinica = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinica() throws Exception {
        int databaseSizeBeforeCreate = clinicaRepository.findAll().size();

        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);
        restClinicaMockMvc.perform(post("/api/clinicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Clinica in the database
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeCreate + 1);
        Clinica testClinica = clinicaList.get(clinicaList.size() - 1);
        assertThat(testClinica.getDescricao()).isEqualTo(DEFAULT_NOME);

        // Validate the Clinica in Elasticsearch
        verify(mockClinicaSearchRepository, times(1)).save(testClinica);
    }

    @Test
    @Transactional
    public void createClinicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicaRepository.findAll().size();

        // Create the Clinica with an existing ID
        clinica.setId(1L);
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicaMockMvc.perform(post("/api/clinicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Clinica in Elasticsearch
        verify(mockClinicaSearchRepository, times(0)).save(clinica);
    }


    @Test
    @Transactional
    public void getAllClinicas() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);

        // Get all the clinicaList
        restClinicaMockMvc.perform(get("/api/clinicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinica.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getClinica() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);

        // Get the clinica
        restClinicaMockMvc.perform(get("/api/clinicas/{id}", clinica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinica.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingClinica() throws Exception {
        // Get the clinica
        restClinicaMockMvc.perform(get("/api/clinicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinica() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);

        int databaseSizeBeforeUpdate = clinicaRepository.findAll().size();

        // Update the clinica
        Clinica updatedClinica = clinicaRepository.findById(clinica.getId()).get();
        // Disconnect from session so that the updates on updatedClinica are not directly saved in db
        em.detach(updatedClinica);
        updatedClinica
            .descricao(UPDATED_NOME);
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(updatedClinica);

        restClinicaMockMvc.perform(put("/api/clinicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicaDTO)))
            .andExpect(status().isOk());

        // Validate the Clinica in the database
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeUpdate);
        Clinica testClinica = clinicaList.get(clinicaList.size() - 1);
        assertThat(testClinica.getDescricao()).isEqualTo(UPDATED_NOME);

        // Validate the Clinica in Elasticsearch
        verify(mockClinicaSearchRepository, times(1)).save(testClinica);
    }

    @Test
    @Transactional
    public void updateNonExistingClinica() throws Exception {
        int databaseSizeBeforeUpdate = clinicaRepository.findAll().size();

        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClinicaMockMvc.perform(put("/api/clinicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clinica in Elasticsearch
        verify(mockClinicaSearchRepository, times(0)).save(clinica);
    }

    @Test
    @Transactional
    public void deleteClinica() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);

        int databaseSizeBeforeDelete = clinicaRepository.findAll().size();

        // Delete the clinica
        restClinicaMockMvc.perform(delete("/api/clinicas/{id}", clinica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Clinica in Elasticsearch
        verify(mockClinicaSearchRepository, times(1)).deleteById(clinica.getId());
    }

    @Test
    @Transactional
    public void searchClinica() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);
        when(mockClinicaSearchRepository.search(queryStringQuery("id:" + clinica.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(clinica), PageRequest.of(0, 1), 1));
        // Search the clinica
        restClinicaMockMvc.perform(get("/api/_search/clinicas?query=id:" + clinica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinica.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clinica.class);
        Clinica clinica1 = new Clinica();
        clinica1.setId(1L);
        Clinica clinica2 = new Clinica();
        clinica2.setId(clinica1.getId());
        assertThat(clinica1).isEqualTo(clinica2);
        clinica2.setId(2L);
        assertThat(clinica1).isNotEqualTo(clinica2);
        clinica1.setId(null);
        assertThat(clinica1).isNotEqualTo(clinica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClinicaDTO.class);
        ClinicaDTO clinicaDTO1 = new ClinicaDTO();
        clinicaDTO1.setId(1L);
        ClinicaDTO clinicaDTO2 = new ClinicaDTO();
        assertThat(clinicaDTO1).isNotEqualTo(clinicaDTO2);
        clinicaDTO2.setId(clinicaDTO1.getId());
        assertThat(clinicaDTO1).isEqualTo(clinicaDTO2);
        clinicaDTO2.setId(2L);
        assertThat(clinicaDTO1).isNotEqualTo(clinicaDTO2);
        clinicaDTO1.setId(null);
        assertThat(clinicaDTO1).isNotEqualTo(clinicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clinicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clinicaMapper.fromId(null)).isNull();
    }
}
