package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.Motivo;
import br.com.basis.madre.farmacia.repository.MotivoRepository;
import br.com.basis.madre.farmacia.repository.search.MotivoSearchRepository;
import br.com.basis.madre.farmacia.service.MotivoService;
import br.com.basis.madre.farmacia.service.dto.MotivoDTO;
import br.com.basis.madre.farmacia.service.mapper.MotivoMapper;

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
 * Integration tests for the {@link MotivoResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class MotivoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private MotivoRepository motivoRepository;

    @Autowired
    private MotivoMapper motivoMapper;

    @Autowired
    private MotivoService motivoService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.MotivoSearchRepositoryMockConfiguration
     */
    @Autowired
    private MotivoSearchRepository mockMotivoSearchRepository;

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

    private MockMvc restMotivoMockMvc;

    private Motivo motivo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotivoResource motivoResource = new MotivoResource(motivoService);
        this.restMotivoMockMvc = MockMvcBuilders.standaloneSetup(motivoResource)
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
    public static Motivo createEntity(EntityManager em) {
        Motivo motivo = new Motivo()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO);
        return motivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Motivo createUpdatedEntity(EntityManager em) {
        Motivo motivo = new Motivo()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO);
        return motivo;
    }

    @BeforeEach
    public void initTest() {
        motivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotivo() throws Exception {
        int databaseSizeBeforeCreate = motivoRepository.findAll().size();

        // Create the Motivo
        MotivoDTO motivoDTO = motivoMapper.toDto(motivo);
        restMotivoMockMvc.perform(post("/api/motivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDTO)))
            .andExpect(status().isCreated());

        // Validate the Motivo in the database
        List<Motivo> motivoList = motivoRepository.findAll();
        assertThat(motivoList).hasSize(databaseSizeBeforeCreate + 1);
        Motivo testMotivo = motivoList.get(motivoList.size() - 1);
        assertThat(testMotivo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testMotivo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Motivo in Elasticsearch
        verify(mockMotivoSearchRepository, times(1)).save(testMotivo);
    }

    @Test
    @Transactional
    public void createMotivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motivoRepository.findAll().size();

        // Create the Motivo with an existing ID
        motivo.setId(1L);
        MotivoDTO motivoDTO = motivoMapper.toDto(motivo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotivoMockMvc.perform(post("/api/motivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Motivo in the database
        List<Motivo> motivoList = motivoRepository.findAll();
        assertThat(motivoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Motivo in Elasticsearch
        verify(mockMotivoSearchRepository, times(0)).save(motivo);
    }


    @Test
    @Transactional
    public void getAllMotivos() throws Exception {
        // Initialize the database
        motivoRepository.saveAndFlush(motivo);

        // Get all the motivoList
        restMotivoMockMvc.perform(get("/api/motivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getMotivo() throws Exception {
        // Initialize the database
        motivoRepository.saveAndFlush(motivo);

        // Get the motivo
        restMotivoMockMvc.perform(get("/api/motivos/{id}", motivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motivo.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingMotivo() throws Exception {
        // Get the motivo
        restMotivoMockMvc.perform(get("/api/motivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotivo() throws Exception {
        // Initialize the database
        motivoRepository.saveAndFlush(motivo);

        int databaseSizeBeforeUpdate = motivoRepository.findAll().size();

        // Update the motivo
        Motivo updatedMotivo = motivoRepository.findById(motivo.getId()).get();
        // Disconnect from session so that the updates on updatedMotivo are not directly saved in db
        em.detach(updatedMotivo);
        updatedMotivo
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO);
        MotivoDTO motivoDTO = motivoMapper.toDto(updatedMotivo);

        restMotivoMockMvc.perform(put("/api/motivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDTO)))
            .andExpect(status().isOk());

        // Validate the Motivo in the database
        List<Motivo> motivoList = motivoRepository.findAll();
        assertThat(motivoList).hasSize(databaseSizeBeforeUpdate);
        Motivo testMotivo = motivoList.get(motivoList.size() - 1);
        assertThat(testMotivo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testMotivo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Motivo in Elasticsearch
        verify(mockMotivoSearchRepository, times(1)).save(testMotivo);
    }

    @Test
    @Transactional
    public void updateNonExistingMotivo() throws Exception {
        int databaseSizeBeforeUpdate = motivoRepository.findAll().size();

        // Create the Motivo
        MotivoDTO motivoDTO = motivoMapper.toDto(motivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivoMockMvc.perform(put("/api/motivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Motivo in the database
        List<Motivo> motivoList = motivoRepository.findAll();
        assertThat(motivoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Motivo in Elasticsearch
        verify(mockMotivoSearchRepository, times(0)).save(motivo);
    }

    @Test
    @Transactional
    public void deleteMotivo() throws Exception {
        // Initialize the database
        motivoRepository.saveAndFlush(motivo);

        int databaseSizeBeforeDelete = motivoRepository.findAll().size();

        // Delete the motivo
        restMotivoMockMvc.perform(delete("/api/motivos/{id}", motivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Motivo> motivoList = motivoRepository.findAll();
        assertThat(motivoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Motivo in Elasticsearch
        verify(mockMotivoSearchRepository, times(1)).deleteById(motivo.getId());
    }

    @Test
    @Transactional
    public void searchMotivo() throws Exception {
        // Initialize the database
        motivoRepository.saveAndFlush(motivo);
        when(mockMotivoSearchRepository.search(queryStringQuery("id:" + motivo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(motivo), PageRequest.of(0, 1), 1));
        // Search the motivo
        restMotivoMockMvc.perform(get("/api/_search/motivos?query=id:" + motivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motivo.class);
        Motivo motivo1 = new Motivo();
        motivo1.setId(1L);
        Motivo motivo2 = new Motivo();
        motivo2.setId(motivo1.getId());
        assertThat(motivo1).isEqualTo(motivo2);
        motivo2.setId(2L);
        assertThat(motivo1).isNotEqualTo(motivo2);
        motivo1.setId(null);
        assertThat(motivo1).isNotEqualTo(motivo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoDTO.class);
        MotivoDTO motivoDTO1 = new MotivoDTO();
        motivoDTO1.setId(1L);
        MotivoDTO motivoDTO2 = new MotivoDTO();
        assertThat(motivoDTO1).isNotEqualTo(motivoDTO2);
        motivoDTO2.setId(motivoDTO1.getId());
        assertThat(motivoDTO1).isEqualTo(motivoDTO2);
        motivoDTO2.setId(2L);
        assertThat(motivoDTO1).isNotEqualTo(motivoDTO2);
        motivoDTO1.setId(null);
        assertThat(motivoDTO1).isNotEqualTo(motivoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(motivoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(motivoMapper.fromId(null)).isNull();
    }
}
