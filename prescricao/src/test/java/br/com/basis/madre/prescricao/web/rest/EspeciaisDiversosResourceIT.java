package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.EspeciaisDiversos;
import br.com.basis.madre.prescricao.repository.EspeciaisDiversosRepository;
import br.com.basis.madre.prescricao.repository.search.EspeciaisDiversosSearchRepository;
import br.com.basis.madre.prescricao.service.EspeciaisDiversosService;
import br.com.basis.madre.prescricao.service.dto.EspeciaisDiversosDTO;
import br.com.basis.madre.prescricao.service.mapper.EspeciaisDiversosMapper;
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
 * Integration tests for the {@link EspeciaisDiversosResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class EspeciaisDiversosResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private EspeciaisDiversosRepository especiaisDiversosRepository;

    @Autowired
    private EspeciaisDiversosMapper especiaisDiversosMapper;

    @Autowired
    private EspeciaisDiversosService especiaisDiversosService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.EspeciaisDiversosSearchRepositoryMockConfiguration
     */
    @Autowired
    private EspeciaisDiversosSearchRepository mockEspeciaisDiversosSearchRepository;

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

    private MockMvc restEspeciaisDiversosMockMvc;

    private EspeciaisDiversos especiaisDiversos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspeciaisDiversosResource especiaisDiversosResource = new EspeciaisDiversosResource(especiaisDiversosService);
        this.restEspeciaisDiversosMockMvc = MockMvcBuilders.standaloneSetup(especiaisDiversosResource)
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
    public static EspeciaisDiversos createEntity(EntityManager em) {
        EspeciaisDiversos especiaisDiversos = new EspeciaisDiversos()
            .descricao(DEFAULT_DESCRICAO);
        return especiaisDiversos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EspeciaisDiversos createUpdatedEntity(EntityManager em) {
        EspeciaisDiversos especiaisDiversos = new EspeciaisDiversos()
            .descricao(UPDATED_DESCRICAO);
        return especiaisDiversos;
    }

    @BeforeEach
    public void initTest() {
        especiaisDiversos = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspeciaisDiversos() throws Exception {
        int databaseSizeBeforeCreate = especiaisDiversosRepository.findAll().size();

        // Create the EspeciaisDiversos
        EspeciaisDiversosDTO especiaisDiversosDTO = especiaisDiversosMapper.toDto(especiaisDiversos);
        restEspeciaisDiversosMockMvc.perform(post("/api/especiais-diversos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especiaisDiversosDTO)))
            .andExpect(status().isCreated());

        // Validate the EspeciaisDiversos in the database
        List<EspeciaisDiversos> especiaisDiversosList = especiaisDiversosRepository.findAll();
        assertThat(especiaisDiversosList).hasSize(databaseSizeBeforeCreate + 1);
        EspeciaisDiversos testEspeciaisDiversos = especiaisDiversosList.get(especiaisDiversosList.size() - 1);
        assertThat(testEspeciaisDiversos.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the EspeciaisDiversos in Elasticsearch
        verify(mockEspeciaisDiversosSearchRepository, times(1)).save(testEspeciaisDiversos);
    }

    @Test
    @Transactional
    public void createEspeciaisDiversosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = especiaisDiversosRepository.findAll().size();

        // Create the EspeciaisDiversos with an existing ID
        especiaisDiversos.setId(1L);
        EspeciaisDiversosDTO especiaisDiversosDTO = especiaisDiversosMapper.toDto(especiaisDiversos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspeciaisDiversosMockMvc.perform(post("/api/especiais-diversos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especiaisDiversosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EspeciaisDiversos in the database
        List<EspeciaisDiversos> especiaisDiversosList = especiaisDiversosRepository.findAll();
        assertThat(especiaisDiversosList).hasSize(databaseSizeBeforeCreate);

        // Validate the EspeciaisDiversos in Elasticsearch
        verify(mockEspeciaisDiversosSearchRepository, times(0)).save(especiaisDiversos);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = especiaisDiversosRepository.findAll().size();
        // set the field null
        especiaisDiversos.setDescricao(null);

        // Create the EspeciaisDiversos, which fails.
        EspeciaisDiversosDTO especiaisDiversosDTO = especiaisDiversosMapper.toDto(especiaisDiversos);

        restEspeciaisDiversosMockMvc.perform(post("/api/especiais-diversos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especiaisDiversosDTO)))
            .andExpect(status().isBadRequest());

        List<EspeciaisDiversos> especiaisDiversosList = especiaisDiversosRepository.findAll();
        assertThat(especiaisDiversosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEspeciaisDiversos() throws Exception {
        // Initialize the database
        especiaisDiversosRepository.saveAndFlush(especiaisDiversos);

        // Get all the especiaisDiversosList
        restEspeciaisDiversosMockMvc.perform(get("/api/especiais-diversos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especiaisDiversos.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getEspeciaisDiversos() throws Exception {
        // Initialize the database
        especiaisDiversosRepository.saveAndFlush(especiaisDiversos);

        // Get the especiaisDiversos
        restEspeciaisDiversosMockMvc.perform(get("/api/especiais-diversos/{id}", especiaisDiversos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(especiaisDiversos.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingEspeciaisDiversos() throws Exception {
        // Get the especiaisDiversos
        restEspeciaisDiversosMockMvc.perform(get("/api/especiais-diversos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspeciaisDiversos() throws Exception {
        // Initialize the database
        especiaisDiversosRepository.saveAndFlush(especiaisDiversos);

        int databaseSizeBeforeUpdate = especiaisDiversosRepository.findAll().size();

        // Update the especiaisDiversos
        EspeciaisDiversos updatedEspeciaisDiversos = especiaisDiversosRepository.findById(especiaisDiversos.getId()).get();
        // Disconnect from session so that the updates on updatedEspeciaisDiversos are not directly saved in db
        em.detach(updatedEspeciaisDiversos);
        updatedEspeciaisDiversos
            .descricao(UPDATED_DESCRICAO);
        EspeciaisDiversosDTO especiaisDiversosDTO = especiaisDiversosMapper.toDto(updatedEspeciaisDiversos);

        restEspeciaisDiversosMockMvc.perform(put("/api/especiais-diversos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especiaisDiversosDTO)))
            .andExpect(status().isOk());

        // Validate the EspeciaisDiversos in the database
        List<EspeciaisDiversos> especiaisDiversosList = especiaisDiversosRepository.findAll();
        assertThat(especiaisDiversosList).hasSize(databaseSizeBeforeUpdate);
        EspeciaisDiversos testEspeciaisDiversos = especiaisDiversosList.get(especiaisDiversosList.size() - 1);
        assertThat(testEspeciaisDiversos.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the EspeciaisDiversos in Elasticsearch
        verify(mockEspeciaisDiversosSearchRepository, times(1)).save(testEspeciaisDiversos);
    }

    @Test
    @Transactional
    public void updateNonExistingEspeciaisDiversos() throws Exception {
        int databaseSizeBeforeUpdate = especiaisDiversosRepository.findAll().size();

        // Create the EspeciaisDiversos
        EspeciaisDiversosDTO especiaisDiversosDTO = especiaisDiversosMapper.toDto(especiaisDiversos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspeciaisDiversosMockMvc.perform(put("/api/especiais-diversos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especiaisDiversosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EspeciaisDiversos in the database
        List<EspeciaisDiversos> especiaisDiversosList = especiaisDiversosRepository.findAll();
        assertThat(especiaisDiversosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EspeciaisDiversos in Elasticsearch
        verify(mockEspeciaisDiversosSearchRepository, times(0)).save(especiaisDiversos);
    }

    @Test
    @Transactional
    public void deleteEspeciaisDiversos() throws Exception {
        // Initialize the database
        especiaisDiversosRepository.saveAndFlush(especiaisDiversos);

        int databaseSizeBeforeDelete = especiaisDiversosRepository.findAll().size();

        // Delete the especiaisDiversos
        restEspeciaisDiversosMockMvc.perform(delete("/api/especiais-diversos/{id}", especiaisDiversos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EspeciaisDiversos> especiaisDiversosList = especiaisDiversosRepository.findAll();
        assertThat(especiaisDiversosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EspeciaisDiversos in Elasticsearch
        verify(mockEspeciaisDiversosSearchRepository, times(1)).deleteById(especiaisDiversos.getId());
    }

    @Test
    @Transactional
    public void searchEspeciaisDiversos() throws Exception {
        // Initialize the database
        especiaisDiversosRepository.saveAndFlush(especiaisDiversos);
        when(mockEspeciaisDiversosSearchRepository.search(queryStringQuery("id:" + especiaisDiversos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(especiaisDiversos), PageRequest.of(0, 1), 1));
        // Search the especiaisDiversos
        restEspeciaisDiversosMockMvc.perform(get("/api/_search/especiais-diversos?query=id:" + especiaisDiversos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especiaisDiversos.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspeciaisDiversos.class);
        EspeciaisDiversos especiaisDiversos1 = new EspeciaisDiversos();
        especiaisDiversos1.setId(1L);
        EspeciaisDiversos especiaisDiversos2 = new EspeciaisDiversos();
        especiaisDiversos2.setId(especiaisDiversos1.getId());
        assertThat(especiaisDiversos1).isEqualTo(especiaisDiversos2);
        especiaisDiversos2.setId(2L);
        assertThat(especiaisDiversos1).isNotEqualTo(especiaisDiversos2);
        especiaisDiversos1.setId(null);
        assertThat(especiaisDiversos1).isNotEqualTo(especiaisDiversos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspeciaisDiversosDTO.class);
        EspeciaisDiversosDTO especiaisDiversosDTO1 = new EspeciaisDiversosDTO();
        especiaisDiversosDTO1.setId(1L);
        EspeciaisDiversosDTO especiaisDiversosDTO2 = new EspeciaisDiversosDTO();
        assertThat(especiaisDiversosDTO1).isNotEqualTo(especiaisDiversosDTO2);
        especiaisDiversosDTO2.setId(especiaisDiversosDTO1.getId());
        assertThat(especiaisDiversosDTO1).isEqualTo(especiaisDiversosDTO2);
        especiaisDiversosDTO2.setId(2L);
        assertThat(especiaisDiversosDTO1).isNotEqualTo(especiaisDiversosDTO2);
        especiaisDiversosDTO1.setId(null);
        assertThat(especiaisDiversosDTO1).isNotEqualTo(especiaisDiversosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(especiaisDiversosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(especiaisDiversosMapper.fromId(null)).isNull();
    }
}
