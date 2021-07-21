package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.Medicamento;
import br.com.basis.madre.farmacia.repository.MedicamentoRepository;
import br.com.basis.madre.farmacia.repository.search.MedicamentoSearchRepository;
import br.com.basis.madre.farmacia.service.ExportarMedicamentoService;
import br.com.basis.madre.farmacia.service.MedicamentoService;
import br.com.basis.madre.farmacia.service.dto.MedicamentoDTO;
import br.com.basis.madre.farmacia.service.mapper.MedicamentoMapper;
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

import static br.com.basis.madre.farmacia.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MedicamentoResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class MedicamentoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CONCENTRACAO = "AAAAAAAAAA";
    private static final String UPDATED_CONCENTRACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private MedicamentoMapper medicamentoMapper;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private ExportarMedicamentoService exportarMedicamentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.MedicamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicamentoSearchRepository mockMedicamentoSearchRepository;

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

    private MockMvc restMedicamentoMockMvc;

    private Medicamento medicamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicamentoResource medicamentoResource = new MedicamentoResource(medicamentoService, exportarMedicamentoService);
        this.restMedicamentoMockMvc = MockMvcBuilders.standaloneSetup(medicamentoResource)
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
    public static Medicamento createEntity(EntityManager em) {
        Medicamento medicamento = new Medicamento()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .concentracao(DEFAULT_CONCENTRACAO)
            .ativo(DEFAULT_ATIVO);
        return medicamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicamento createUpdatedEntity(EntityManager em) {
        Medicamento medicamento = new Medicamento()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .concentracao(UPDATED_CONCENTRACAO)
            .ativo(UPDATED_ATIVO);
        return medicamento;
    }

    @BeforeEach
    public void initTest() {
        medicamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicamento() throws Exception {
        int databaseSizeBeforeCreate = medicamentoRepository.findAll().size();

        // Create the Medicamento
        MedicamentoDTO medicamentoDTO = medicamentoMapper.toDto(medicamento);
        restMedicamentoMockMvc.perform(post("/api/medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Medicamento in the database
        List<Medicamento> medicamentoList = medicamentoRepository.findAll();
        assertThat(medicamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Medicamento testMedicamento = medicamentoList.get(medicamentoList.size() - 1);
        assertThat(testMedicamento.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testMedicamento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMedicamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMedicamento.getConcentracao()).isEqualTo(DEFAULT_CONCENTRACAO);
        assertThat(testMedicamento.isAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the Medicamento in Elasticsearch
        verify(mockMedicamentoSearchRepository, times(1)).save(testMedicamento);
    }

    @Test
    @Transactional
    public void createMedicamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicamentoRepository.findAll().size();

        // Create the Medicamento with an existing ID
        medicamento.setId(1L);
        MedicamentoDTO medicamentoDTO = medicamentoMapper.toDto(medicamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicamentoMockMvc.perform(post("/api/medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medicamento in the database
        List<Medicamento> medicamentoList = medicamentoRepository.findAll();
        assertThat(medicamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Medicamento in Elasticsearch
        verify(mockMedicamentoSearchRepository, times(0)).save(medicamento);
    }


    @Test
    @Transactional
    public void getAllMedicamentos() throws Exception {
        // Initialize the database
        medicamentoRepository.saveAndFlush(medicamento);

        // Get all the medicamentoList
        restMedicamentoMockMvc.perform(get("/api/medicamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].concentracao").value(hasItem(DEFAULT_CONCENTRACAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getMedicamento() throws Exception {
        // Initialize the database
        medicamentoRepository.saveAndFlush(medicamento);

        // Get the medicamento
        restMedicamentoMockMvc.perform(get("/api/medicamentos/{id}", medicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicamento.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.concentracao").value(DEFAULT_CONCENTRACAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicamento() throws Exception {
        // Get the medicamento
        restMedicamentoMockMvc.perform(get("/api/medicamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicamento() throws Exception {
        // Initialize the database
        medicamentoRepository.saveAndFlush(medicamento);

        int databaseSizeBeforeUpdate = medicamentoRepository.findAll().size();

        // Update the medicamento
        Medicamento updatedMedicamento = medicamentoRepository.findById(medicamento.getId()).get();
        // Disconnect from session so that the updates on updatedMedicamento are not directly saved in db
        em.detach(updatedMedicamento);
        updatedMedicamento
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .concentracao(UPDATED_CONCENTRACAO)
            .ativo(UPDATED_ATIVO);
        MedicamentoDTO medicamentoDTO = medicamentoMapper.toDto(updatedMedicamento);

        restMedicamentoMockMvc.perform(put("/api/medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Medicamento in the database
        List<Medicamento> medicamentoList = medicamentoRepository.findAll();
        assertThat(medicamentoList).hasSize(databaseSizeBeforeUpdate);
        Medicamento testMedicamento = medicamentoList.get(medicamentoList.size() - 1);
        assertThat(testMedicamento.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testMedicamento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMedicamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMedicamento.getConcentracao()).isEqualTo(UPDATED_CONCENTRACAO);
        assertThat(testMedicamento.isAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the Medicamento in Elasticsearch
        verify(mockMedicamentoSearchRepository, times(1)).save(testMedicamento);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicamento() throws Exception {
        int databaseSizeBeforeUpdate = medicamentoRepository.findAll().size();

        // Create the Medicamento
        MedicamentoDTO medicamentoDTO = medicamentoMapper.toDto(medicamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicamentoMockMvc.perform(put("/api/medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medicamento in the database
        List<Medicamento> medicamentoList = medicamentoRepository.findAll();
        assertThat(medicamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Medicamento in Elasticsearch
        verify(mockMedicamentoSearchRepository, times(0)).save(medicamento);
    }

    @Test
    @Transactional
    public void deleteMedicamento() throws Exception {
        // Initialize the database
        medicamentoRepository.saveAndFlush(medicamento);

        int databaseSizeBeforeDelete = medicamentoRepository.findAll().size();

        // Delete the medicamento
        restMedicamentoMockMvc.perform(delete("/api/medicamentos/{id}", medicamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medicamento> medicamentoList = medicamentoRepository.findAll();
        assertThat(medicamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Medicamento in Elasticsearch
        verify(mockMedicamentoSearchRepository, times(1)).deleteById(medicamento.getId());
    }

    @Test
    @Transactional
    public void searchMedicamento() throws Exception {
        // Initialize the database
        medicamentoRepository.saveAndFlush(medicamento);
        when(mockMedicamentoSearchRepository.search(queryStringQuery("id:" + medicamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(medicamento), PageRequest.of(0, 1), 1));
        // Search the medicamento
        restMedicamentoMockMvc.perform(get("/api/_search/medicamentos?query=id:" + medicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].concentracao").value(hasItem(DEFAULT_CONCENTRACAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicamento.class);
        Medicamento medicamento1 = new Medicamento();
        medicamento1.setId(1L);
        Medicamento medicamento2 = new Medicamento();
        medicamento2.setId(medicamento1.getId());
        assertThat(medicamento1).isEqualTo(medicamento2);
        medicamento2.setId(2L);
        assertThat(medicamento1).isNotEqualTo(medicamento2);
        medicamento1.setId(null);
        assertThat(medicamento1).isNotEqualTo(medicamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicamentoDTO.class);
        MedicamentoDTO medicamentoDTO1 = new MedicamentoDTO();
        medicamentoDTO1.setId(1L);
        MedicamentoDTO medicamentoDTO2 = new MedicamentoDTO();
        assertThat(medicamentoDTO1).isNotEqualTo(medicamentoDTO2);
        medicamentoDTO2.setId(medicamentoDTO1.getId());
        assertThat(medicamentoDTO1).isEqualTo(medicamentoDTO2);
        medicamentoDTO2.setId(2L);
        assertThat(medicamentoDTO1).isNotEqualTo(medicamentoDTO2);
        medicamentoDTO1.setId(null);
        assertThat(medicamentoDTO1).isNotEqualTo(medicamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicamentoMapper.fromId(null)).isNull();
    }
}
