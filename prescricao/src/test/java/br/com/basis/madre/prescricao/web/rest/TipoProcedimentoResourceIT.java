package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.TipoProcedimento;
import br.com.basis.madre.prescricao.repository.TipoProcedimentoRepository;
import br.com.basis.madre.prescricao.repository.search.TipoProcedimentoSearchRepository;
import br.com.basis.madre.prescricao.service.TipoProcedimentoService;
import br.com.basis.madre.prescricao.service.dto.TipoProcedimentoDTO;
import br.com.basis.madre.prescricao.service.mapper.TipoProcedimentoMapper;
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
 * Integration tests for the {@link TipoProcedimentoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class TipoProcedimentoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoProcedimentoRepository tipoProcedimentoRepository;

    @Autowired
    private TipoProcedimentoMapper tipoProcedimentoMapper;

    @Autowired
    private TipoProcedimentoService tipoProcedimentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.TipoProcedimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoProcedimentoSearchRepository mockTipoProcedimentoSearchRepository;

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

    private MockMvc restTipoProcedimentoMockMvc;

    private TipoProcedimento tipoProcedimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoProcedimentoResource tipoProcedimentoResource = new TipoProcedimentoResource(tipoProcedimentoService);
        this.restTipoProcedimentoMockMvc = MockMvcBuilders.standaloneSetup(tipoProcedimentoResource)
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
    public static TipoProcedimento createEntity(EntityManager em) {
        TipoProcedimento tipoProcedimento = new TipoProcedimento()
            .descricao(DEFAULT_DESCRICAO);
        return tipoProcedimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProcedimento createUpdatedEntity(EntityManager em) {
        TipoProcedimento tipoProcedimento = new TipoProcedimento()
            .descricao(UPDATED_DESCRICAO);
        return tipoProcedimento;
    }

    @BeforeEach
    public void initTest() {
        tipoProcedimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoProcedimento() throws Exception {
        int databaseSizeBeforeCreate = tipoProcedimentoRepository.findAll().size();

        // Create the TipoProcedimento
        TipoProcedimentoDTO tipoProcedimentoDTO = tipoProcedimentoMapper.toDto(tipoProcedimento);
        restTipoProcedimentoMockMvc.perform(post("/api/tipo-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcedimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoProcedimento in the database
        List<TipoProcedimento> tipoProcedimentoList = tipoProcedimentoRepository.findAll();
        assertThat(tipoProcedimentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoProcedimento testTipoProcedimento = tipoProcedimentoList.get(tipoProcedimentoList.size() - 1);
        assertThat(testTipoProcedimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the TipoProcedimento in Elasticsearch
        verify(mockTipoProcedimentoSearchRepository, times(1)).save(testTipoProcedimento);
    }

    @Test
    @Transactional
    public void createTipoProcedimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoProcedimentoRepository.findAll().size();

        // Create the TipoProcedimento with an existing ID
        tipoProcedimento.setId(1L);
        TipoProcedimentoDTO tipoProcedimentoDTO = tipoProcedimentoMapper.toDto(tipoProcedimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoProcedimentoMockMvc.perform(post("/api/tipo-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProcedimento in the database
        List<TipoProcedimento> tipoProcedimentoList = tipoProcedimentoRepository.findAll();
        assertThat(tipoProcedimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoProcedimento in Elasticsearch
        verify(mockTipoProcedimentoSearchRepository, times(0)).save(tipoProcedimento);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoProcedimentoRepository.findAll().size();
        // set the field null
        tipoProcedimento.setDescricao(null);

        // Create the TipoProcedimento, which fails.
        TipoProcedimentoDTO tipoProcedimentoDTO = tipoProcedimentoMapper.toDto(tipoProcedimento);

        restTipoProcedimentoMockMvc.perform(post("/api/tipo-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcedimentoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoProcedimento> tipoProcedimentoList = tipoProcedimentoRepository.findAll();
        assertThat(tipoProcedimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoProcedimentos() throws Exception {
        // Initialize the database
        tipoProcedimentoRepository.saveAndFlush(tipoProcedimento);

        // Get all the tipoProcedimentoList
        restTipoProcedimentoMockMvc.perform(get("/api/tipo-procedimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProcedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTipoProcedimento() throws Exception {
        // Initialize the database
        tipoProcedimentoRepository.saveAndFlush(tipoProcedimento);

        // Get the tipoProcedimento
        restTipoProcedimentoMockMvc.perform(get("/api/tipo-procedimentos/{id}", tipoProcedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoProcedimento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingTipoProcedimento() throws Exception {
        // Get the tipoProcedimento
        restTipoProcedimentoMockMvc.perform(get("/api/tipo-procedimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoProcedimento() throws Exception {
        // Initialize the database
        tipoProcedimentoRepository.saveAndFlush(tipoProcedimento);

        int databaseSizeBeforeUpdate = tipoProcedimentoRepository.findAll().size();

        // Update the tipoProcedimento
        TipoProcedimento updatedTipoProcedimento = tipoProcedimentoRepository.findById(tipoProcedimento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoProcedimento are not directly saved in db
        em.detach(updatedTipoProcedimento);
        updatedTipoProcedimento
            .descricao(UPDATED_DESCRICAO);
        TipoProcedimentoDTO tipoProcedimentoDTO = tipoProcedimentoMapper.toDto(updatedTipoProcedimento);

        restTipoProcedimentoMockMvc.perform(put("/api/tipo-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcedimentoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoProcedimento in the database
        List<TipoProcedimento> tipoProcedimentoList = tipoProcedimentoRepository.findAll();
        assertThat(tipoProcedimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoProcedimento testTipoProcedimento = tipoProcedimentoList.get(tipoProcedimentoList.size() - 1);
        assertThat(testTipoProcedimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the TipoProcedimento in Elasticsearch
        verify(mockTipoProcedimentoSearchRepository, times(1)).save(testTipoProcedimento);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoProcedimentoRepository.findAll().size();

        // Create the TipoProcedimento
        TipoProcedimentoDTO tipoProcedimentoDTO = tipoProcedimentoMapper.toDto(tipoProcedimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProcedimentoMockMvc.perform(put("/api/tipo-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProcedimento in the database
        List<TipoProcedimento> tipoProcedimentoList = tipoProcedimentoRepository.findAll();
        assertThat(tipoProcedimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoProcedimento in Elasticsearch
        verify(mockTipoProcedimentoSearchRepository, times(0)).save(tipoProcedimento);
    }

    @Test
    @Transactional
    public void deleteTipoProcedimento() throws Exception {
        // Initialize the database
        tipoProcedimentoRepository.saveAndFlush(tipoProcedimento);

        int databaseSizeBeforeDelete = tipoProcedimentoRepository.findAll().size();

        // Delete the tipoProcedimento
        restTipoProcedimentoMockMvc.perform(delete("/api/tipo-procedimentos/{id}", tipoProcedimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoProcedimento> tipoProcedimentoList = tipoProcedimentoRepository.findAll();
        assertThat(tipoProcedimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoProcedimento in Elasticsearch
        verify(mockTipoProcedimentoSearchRepository, times(1)).deleteById(tipoProcedimento.getId());
    }

    @Test
    @Transactional
    public void searchTipoProcedimento() throws Exception {
        // Initialize the database
        tipoProcedimentoRepository.saveAndFlush(tipoProcedimento);
        when(mockTipoProcedimentoSearchRepository.search(queryStringQuery("id:" + tipoProcedimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoProcedimento), PageRequest.of(0, 1), 1));
        // Search the tipoProcedimento
        restTipoProcedimentoMockMvc.perform(get("/api/_search/tipo-procedimentos?query=id:" + tipoProcedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProcedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProcedimento.class);
        TipoProcedimento tipoProcedimento1 = new TipoProcedimento();
        tipoProcedimento1.setId(1L);
        TipoProcedimento tipoProcedimento2 = new TipoProcedimento();
        tipoProcedimento2.setId(tipoProcedimento1.getId());
        assertThat(tipoProcedimento1).isEqualTo(tipoProcedimento2);
        tipoProcedimento2.setId(2L);
        assertThat(tipoProcedimento1).isNotEqualTo(tipoProcedimento2);
        tipoProcedimento1.setId(null);
        assertThat(tipoProcedimento1).isNotEqualTo(tipoProcedimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProcedimentoDTO.class);
        TipoProcedimentoDTO tipoProcedimentoDTO1 = new TipoProcedimentoDTO();
        tipoProcedimentoDTO1.setId(1L);
        TipoProcedimentoDTO tipoProcedimentoDTO2 = new TipoProcedimentoDTO();
        assertThat(tipoProcedimentoDTO1).isNotEqualTo(tipoProcedimentoDTO2);
        tipoProcedimentoDTO2.setId(tipoProcedimentoDTO1.getId());
        assertThat(tipoProcedimentoDTO1).isEqualTo(tipoProcedimentoDTO2);
        tipoProcedimentoDTO2.setId(2L);
        assertThat(tipoProcedimentoDTO1).isNotEqualTo(tipoProcedimentoDTO2);
        tipoProcedimentoDTO1.setId(null);
        assertThat(tipoProcedimentoDTO1).isNotEqualTo(tipoProcedimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoProcedimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoProcedimentoMapper.fromId(null)).isNull();
    }
}
