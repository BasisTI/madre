package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Ala;
import br.com.basis.madre.repository.AlaRepository;
import br.com.basis.madre.repository.search.AlaSearchRepository;
import br.com.basis.madre.service.AlaService;
import br.com.basis.madre.service.dto.AlaDTO;
import br.com.basis.madre.service.mapper.AlaMapper;
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
 * Integration tests for the {@link AlaResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class AlaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private AlaRepository alaRepository;

    @Autowired
    private AlaMapper alaMapper;

    @Autowired
    private AlaService alaService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.AlaSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlaSearchRepository mockAlaSearchRepository;

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

    private MockMvc restAlaMockMvc;

    private Ala ala;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlaResource alaResource = new AlaResource(alaService);
        this.restAlaMockMvc = MockMvcBuilders.standaloneSetup(alaResource)
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
    public static Ala createEntity(EntityManager em) {
        Ala ala = new Ala()
            .nome(DEFAULT_NOME);
        return ala;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ala createUpdatedEntity(EntityManager em) {
        Ala ala = new Ala()
            .nome(UPDATED_NOME);
        return ala;
    }

    @BeforeEach
    public void initTest() {
        ala = createEntity(em);
    }

    @Test
    @Transactional
    public void createAla() throws Exception {
        int databaseSizeBeforeCreate = alaRepository.findAll().size();

        // Create the Ala
        AlaDTO alaDTO = alaMapper.toDto(ala);
        restAlaMockMvc.perform(post("/api/alas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alaDTO)))
            .andExpect(status().isCreated());

        // Validate the Ala in the database
        List<Ala> alaList = alaRepository.findAll();
        assertThat(alaList).hasSize(databaseSizeBeforeCreate + 1);
        Ala testAla = alaList.get(alaList.size() - 1);
        assertThat(testAla.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Ala in Elasticsearch
        verify(mockAlaSearchRepository, times(1)).save(testAla);
    }

    @Test
    @Transactional
    public void createAlaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alaRepository.findAll().size();

        // Create the Ala with an existing ID
        ala.setId(1L);
        AlaDTO alaDTO = alaMapper.toDto(ala);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlaMockMvc.perform(post("/api/alas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ala in the database
        List<Ala> alaList = alaRepository.findAll();
        assertThat(alaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ala in Elasticsearch
        verify(mockAlaSearchRepository, times(0)).save(ala);
    }


    @Test
    @Transactional
    public void getAllAlas() throws Exception {
        // Initialize the database
        alaRepository.saveAndFlush(ala);

        // Get all the alaList
        restAlaMockMvc.perform(get("/api/alas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ala.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getAla() throws Exception {
        // Initialize the database
        alaRepository.saveAndFlush(ala);

        // Get the ala
        restAlaMockMvc.perform(get("/api/alas/{id}", ala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ala.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingAla() throws Exception {
        // Get the ala
        restAlaMockMvc.perform(get("/api/alas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAla() throws Exception {
        // Initialize the database
        alaRepository.saveAndFlush(ala);

        int databaseSizeBeforeUpdate = alaRepository.findAll().size();

        // Update the ala
        Ala updatedAla = alaRepository.findById(ala.getId()).get();
        // Disconnect from session so that the updates on updatedAla are not directly saved in db
        em.detach(updatedAla);
        updatedAla
            .nome(UPDATED_NOME);
        AlaDTO alaDTO = alaMapper.toDto(updatedAla);

        restAlaMockMvc.perform(put("/api/alas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alaDTO)))
            .andExpect(status().isOk());

        // Validate the Ala in the database
        List<Ala> alaList = alaRepository.findAll();
        assertThat(alaList).hasSize(databaseSizeBeforeUpdate);
        Ala testAla = alaList.get(alaList.size() - 1);
        assertThat(testAla.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Ala in Elasticsearch
        verify(mockAlaSearchRepository, times(1)).save(testAla);
    }

    @Test
    @Transactional
    public void updateNonExistingAla() throws Exception {
        int databaseSizeBeforeUpdate = alaRepository.findAll().size();

        // Create the Ala
        AlaDTO alaDTO = alaMapper.toDto(ala);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlaMockMvc.perform(put("/api/alas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ala in the database
        List<Ala> alaList = alaRepository.findAll();
        assertThat(alaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ala in Elasticsearch
        verify(mockAlaSearchRepository, times(0)).save(ala);
    }

    @Test
    @Transactional
    public void deleteAla() throws Exception {
        // Initialize the database
        alaRepository.saveAndFlush(ala);

        int databaseSizeBeforeDelete = alaRepository.findAll().size();

        // Delete the ala
        restAlaMockMvc.perform(delete("/api/alas/{id}", ala.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ala> alaList = alaRepository.findAll();
        assertThat(alaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ala in Elasticsearch
        verify(mockAlaSearchRepository, times(1)).deleteById(ala.getId());
    }

    @Test
    @Transactional
    public void searchAla() throws Exception {
        // Initialize the database
        alaRepository.saveAndFlush(ala);
        when(mockAlaSearchRepository.search(queryStringQuery("id:" + ala.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ala), PageRequest.of(0, 1), 1));
        // Search the ala
        restAlaMockMvc.perform(get("/api/_search/alas?query=id:" + ala.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ala.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ala.class);
        Ala ala1 = new Ala();
        ala1.setId(1L);
        Ala ala2 = new Ala();
        ala2.setId(ala1.getId());
        assertThat(ala1).isEqualTo(ala2);
        ala2.setId(2L);
        assertThat(ala1).isNotEqualTo(ala2);
        ala1.setId(null);
        assertThat(ala1).isNotEqualTo(ala2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlaDTO.class);
        AlaDTO alaDTO1 = new AlaDTO();
        alaDTO1.setId(1L);
        AlaDTO alaDTO2 = new AlaDTO();
        assertThat(alaDTO1).isNotEqualTo(alaDTO2);
        alaDTO2.setId(alaDTO1.getId());
        assertThat(alaDTO1).isEqualTo(alaDTO2);
        alaDTO2.setId(2L);
        assertThat(alaDTO1).isNotEqualTo(alaDTO2);
        alaDTO1.setId(null);
        assertThat(alaDTO1).isNotEqualTo(alaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(alaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(alaMapper.fromId(null)).isNull();
    }
}
