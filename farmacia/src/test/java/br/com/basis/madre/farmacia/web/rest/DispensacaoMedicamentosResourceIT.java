package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.DispensacaoMedicamentos;
import br.com.basis.madre.farmacia.repository.DispensacaoMedicamentosRepository;
import br.com.basis.madre.farmacia.repository.search.DispensacaoMedicamentosSearchRepository;
import br.com.basis.madre.farmacia.service.DispensacaoMedicamentosService;
import br.com.basis.madre.farmacia.service.dto.DispensacaoMedicamentosDTO;
import br.com.basis.madre.farmacia.service.mapper.DispensacaoMedicamentosMapper;
import br.com.basis.madre.farmacia.web.rest.errors.ExceptionTranslator;

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

import static br.com.basis.madre.farmacia.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DispensacaoMedicamentosResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class DispensacaoMedicamentosResourceIT {

    private static final Long DEFAULT_ID_FARMACIA = 1L;
    private static final Long UPDATED_ID_FARMACIA = 2L;

    private static final Boolean DEFAULT_DISPENSADO = false;
    private static final Boolean UPDATED_DISPENSADO = true;

    private static final Long DEFAULT_USUARIO_QUE_DISPENSOU = 1L;
    private static final Long UPDATED_USUARIO_QUE_DISPENSOU = 2L;

    @Autowired
    private DispensacaoMedicamentosRepository dispensacaoMedicamentosRepository;

    @Autowired
    private DispensacaoMedicamentosMapper dispensacaoMedicamentosMapper;

    @Autowired
    private DispensacaoMedicamentosService dispensacaoMedicamentosService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.DispensacaoMedicamentosSearchRepositoryMockConfiguration
     */
    @Autowired
    private DispensacaoMedicamentosSearchRepository mockDispensacaoMedicamentosSearchRepository;

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

    private MockMvc restDispensacaoMedicamentosMockMvc;

    private DispensacaoMedicamentos dispensacaoMedicamentos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DispensacaoMedicamentosResource dispensacaoMedicamentosResource = new DispensacaoMedicamentosResource(dispensacaoMedicamentosService);
        this.restDispensacaoMedicamentosMockMvc = MockMvcBuilders.standaloneSetup(dispensacaoMedicamentosResource)
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
    public static DispensacaoMedicamentos createEntity(EntityManager em) {
        DispensacaoMedicamentos dispensacaoMedicamentos = new DispensacaoMedicamentos()
            .idFarmacia(DEFAULT_ID_FARMACIA)
            .dispensado(DEFAULT_DISPENSADO)
            .usuarioQueDispensou(DEFAULT_USUARIO_QUE_DISPENSOU);
        return dispensacaoMedicamentos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispensacaoMedicamentos createUpdatedEntity(EntityManager em) {
        DispensacaoMedicamentos dispensacaoMedicamentos = new DispensacaoMedicamentos()
            .idFarmacia(UPDATED_ID_FARMACIA)
            .dispensado(UPDATED_DISPENSADO)
            .usuarioQueDispensou(UPDATED_USUARIO_QUE_DISPENSOU);
        return dispensacaoMedicamentos;
    }

    @BeforeEach
    public void initTest() {
        dispensacaoMedicamentos = createEntity(em);
    }

    @Test
    @Transactional
    public void createDispensacaoMedicamentos() throws Exception {
        int databaseSizeBeforeCreate = dispensacaoMedicamentosRepository.findAll().size();

        // Create the DispensacaoMedicamentos
        DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO = dispensacaoMedicamentosMapper.toDto(dispensacaoMedicamentos);
        restDispensacaoMedicamentosMockMvc.perform(post("/api/dispensacao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispensacaoMedicamentosDTO)))
            .andExpect(status().isCreated());

        // Validate the DispensacaoMedicamentos in the database
        List<DispensacaoMedicamentos> dispensacaoMedicamentosList = dispensacaoMedicamentosRepository.findAll();
        assertThat(dispensacaoMedicamentosList).hasSize(databaseSizeBeforeCreate + 1);
        DispensacaoMedicamentos testDispensacaoMedicamentos = dispensacaoMedicamentosList.get(dispensacaoMedicamentosList.size() - 1);
        assertThat(testDispensacaoMedicamentos.getIdFarmacia()).isEqualTo(DEFAULT_ID_FARMACIA);
        assertThat(testDispensacaoMedicamentos.isDispensado()).isEqualTo(DEFAULT_DISPENSADO);
        assertThat(testDispensacaoMedicamentos.getUsuarioQueDispensou()).isEqualTo(DEFAULT_USUARIO_QUE_DISPENSOU);

        // Validate the DispensacaoMedicamentos in Elasticsearch
        verify(mockDispensacaoMedicamentosSearchRepository, times(1)).save(testDispensacaoMedicamentos);
    }

    @Test
    @Transactional
    public void createDispensacaoMedicamentosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dispensacaoMedicamentosRepository.findAll().size();

        // Create the DispensacaoMedicamentos with an existing ID
        dispensacaoMedicamentos.setId(1L);
        DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO = dispensacaoMedicamentosMapper.toDto(dispensacaoMedicamentos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispensacaoMedicamentosMockMvc.perform(post("/api/dispensacao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispensacaoMedicamentosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DispensacaoMedicamentos in the database
        List<DispensacaoMedicamentos> dispensacaoMedicamentosList = dispensacaoMedicamentosRepository.findAll();
        assertThat(dispensacaoMedicamentosList).hasSize(databaseSizeBeforeCreate);

        // Validate the DispensacaoMedicamentos in Elasticsearch
        verify(mockDispensacaoMedicamentosSearchRepository, times(0)).save(dispensacaoMedicamentos);
    }


    @Test
    @Transactional
    public void getAllDispensacaoMedicamentos() throws Exception {
        // Initialize the database
        dispensacaoMedicamentosRepository.saveAndFlush(dispensacaoMedicamentos);

        // Get all the dispensacaoMedicamentosList
        restDispensacaoMedicamentosMockMvc.perform(get("/api/dispensacao-medicamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispensacaoMedicamentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFarmacia").value(hasItem(DEFAULT_ID_FARMACIA.intValue())))
            .andExpect(jsonPath("$.[*].dispensado").value(hasItem(DEFAULT_DISPENSADO.booleanValue())))
            .andExpect(jsonPath("$.[*].usuarioQueDispensou").value(hasItem(DEFAULT_USUARIO_QUE_DISPENSOU.intValue())));
    }
    
    @Test
    @Transactional
    public void getDispensacaoMedicamentos() throws Exception {
        // Initialize the database
        dispensacaoMedicamentosRepository.saveAndFlush(dispensacaoMedicamentos);

        // Get the dispensacaoMedicamentos
        restDispensacaoMedicamentosMockMvc.perform(get("/api/dispensacao-medicamentos/{id}", dispensacaoMedicamentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dispensacaoMedicamentos.getId().intValue()))
            .andExpect(jsonPath("$.idFarmacia").value(DEFAULT_ID_FARMACIA.intValue()))
            .andExpect(jsonPath("$.dispensado").value(DEFAULT_DISPENSADO.booleanValue()))
            .andExpect(jsonPath("$.usuarioQueDispensou").value(DEFAULT_USUARIO_QUE_DISPENSOU.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDispensacaoMedicamentos() throws Exception {
        // Get the dispensacaoMedicamentos
        restDispensacaoMedicamentosMockMvc.perform(get("/api/dispensacao-medicamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDispensacaoMedicamentos() throws Exception {
        // Initialize the database
        dispensacaoMedicamentosRepository.saveAndFlush(dispensacaoMedicamentos);

        int databaseSizeBeforeUpdate = dispensacaoMedicamentosRepository.findAll().size();

        // Update the dispensacaoMedicamentos
        DispensacaoMedicamentos updatedDispensacaoMedicamentos = dispensacaoMedicamentosRepository.findById(dispensacaoMedicamentos.getId()).get();
        // Disconnect from session so that the updates on updatedDispensacaoMedicamentos are not directly saved in db
        em.detach(updatedDispensacaoMedicamentos);
        updatedDispensacaoMedicamentos
            .idFarmacia(UPDATED_ID_FARMACIA)
            .dispensado(UPDATED_DISPENSADO)
            .usuarioQueDispensou(UPDATED_USUARIO_QUE_DISPENSOU);
        DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO = dispensacaoMedicamentosMapper.toDto(updatedDispensacaoMedicamentos);

        restDispensacaoMedicamentosMockMvc.perform(put("/api/dispensacao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispensacaoMedicamentosDTO)))
            .andExpect(status().isOk());

        // Validate the DispensacaoMedicamentos in the database
        List<DispensacaoMedicamentos> dispensacaoMedicamentosList = dispensacaoMedicamentosRepository.findAll();
        assertThat(dispensacaoMedicamentosList).hasSize(databaseSizeBeforeUpdate);
        DispensacaoMedicamentos testDispensacaoMedicamentos = dispensacaoMedicamentosList.get(dispensacaoMedicamentosList.size() - 1);
        assertThat(testDispensacaoMedicamentos.getIdFarmacia()).isEqualTo(UPDATED_ID_FARMACIA);
        assertThat(testDispensacaoMedicamentos.isDispensado()).isEqualTo(UPDATED_DISPENSADO);
        assertThat(testDispensacaoMedicamentos.getUsuarioQueDispensou()).isEqualTo(UPDATED_USUARIO_QUE_DISPENSOU);

        // Validate the DispensacaoMedicamentos in Elasticsearch
        verify(mockDispensacaoMedicamentosSearchRepository, times(1)).save(testDispensacaoMedicamentos);
    }

    @Test
    @Transactional
    public void updateNonExistingDispensacaoMedicamentos() throws Exception {
        int databaseSizeBeforeUpdate = dispensacaoMedicamentosRepository.findAll().size();

        // Create the DispensacaoMedicamentos
        DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO = dispensacaoMedicamentosMapper.toDto(dispensacaoMedicamentos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispensacaoMedicamentosMockMvc.perform(put("/api/dispensacao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispensacaoMedicamentosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DispensacaoMedicamentos in the database
        List<DispensacaoMedicamentos> dispensacaoMedicamentosList = dispensacaoMedicamentosRepository.findAll();
        assertThat(dispensacaoMedicamentosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DispensacaoMedicamentos in Elasticsearch
        verify(mockDispensacaoMedicamentosSearchRepository, times(0)).save(dispensacaoMedicamentos);
    }

    @Test
    @Transactional
    public void deleteDispensacaoMedicamentos() throws Exception {
        // Initialize the database
        dispensacaoMedicamentosRepository.saveAndFlush(dispensacaoMedicamentos);

        int databaseSizeBeforeDelete = dispensacaoMedicamentosRepository.findAll().size();

        // Delete the dispensacaoMedicamentos
        restDispensacaoMedicamentosMockMvc.perform(delete("/api/dispensacao-medicamentos/{id}", dispensacaoMedicamentos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DispensacaoMedicamentos> dispensacaoMedicamentosList = dispensacaoMedicamentosRepository.findAll();
        assertThat(dispensacaoMedicamentosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DispensacaoMedicamentos in Elasticsearch
        verify(mockDispensacaoMedicamentosSearchRepository, times(1)).deleteById(dispensacaoMedicamentos.getId());
    }

    @Test
    @Transactional
    public void searchDispensacaoMedicamentos() throws Exception {
        // Initialize the database
        dispensacaoMedicamentosRepository.saveAndFlush(dispensacaoMedicamentos);
        when(mockDispensacaoMedicamentosSearchRepository.search(queryStringQuery("id:" + dispensacaoMedicamentos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dispensacaoMedicamentos), PageRequest.of(0, 1), 1));
        // Search the dispensacaoMedicamentos
        restDispensacaoMedicamentosMockMvc.perform(get("/api/_search/dispensacao-medicamentos?query=id:" + dispensacaoMedicamentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispensacaoMedicamentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFarmacia").value(hasItem(DEFAULT_ID_FARMACIA.intValue())))
            .andExpect(jsonPath("$.[*].dispensado").value(hasItem(DEFAULT_DISPENSADO.booleanValue())))
            .andExpect(jsonPath("$.[*].usuarioQueDispensou").value(hasItem(DEFAULT_USUARIO_QUE_DISPENSOU.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispensacaoMedicamentos.class);
        DispensacaoMedicamentos dispensacaoMedicamentos1 = new DispensacaoMedicamentos();
        dispensacaoMedicamentos1.setId(1L);
        DispensacaoMedicamentos dispensacaoMedicamentos2 = new DispensacaoMedicamentos();
        dispensacaoMedicamentos2.setId(dispensacaoMedicamentos1.getId());
        assertThat(dispensacaoMedicamentos1).isEqualTo(dispensacaoMedicamentos2);
        dispensacaoMedicamentos2.setId(2L);
        assertThat(dispensacaoMedicamentos1).isNotEqualTo(dispensacaoMedicamentos2);
        dispensacaoMedicamentos1.setId(null);
        assertThat(dispensacaoMedicamentos1).isNotEqualTo(dispensacaoMedicamentos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispensacaoMedicamentosDTO.class);
        DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO1 = new DispensacaoMedicamentosDTO();
        dispensacaoMedicamentosDTO1.setId(1L);
        DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO2 = new DispensacaoMedicamentosDTO();
        assertThat(dispensacaoMedicamentosDTO1).isNotEqualTo(dispensacaoMedicamentosDTO2);
        dispensacaoMedicamentosDTO2.setId(dispensacaoMedicamentosDTO1.getId());
        assertThat(dispensacaoMedicamentosDTO1).isEqualTo(dispensacaoMedicamentosDTO2);
        dispensacaoMedicamentosDTO2.setId(2L);
        assertThat(dispensacaoMedicamentosDTO1).isNotEqualTo(dispensacaoMedicamentosDTO2);
        dispensacaoMedicamentosDTO1.setId(null);
        assertThat(dispensacaoMedicamentosDTO1).isNotEqualTo(dispensacaoMedicamentosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dispensacaoMedicamentosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dispensacaoMedicamentosMapper.fromId(null)).isNull();
    }
}
