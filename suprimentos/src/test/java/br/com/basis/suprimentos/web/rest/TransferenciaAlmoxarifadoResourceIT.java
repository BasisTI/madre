package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import br.com.basis.suprimentos.repository.TransferenciaAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.TransferenciaAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.TransferenciaAlmoxarifadoService;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.TransferenciaAlmoxarifadoMapper;
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
 * Integration tests for the {@link TransferenciaAlmoxarifadoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class TransferenciaAlmoxarifadoResourceIT {

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private TransferenciaAlmoxarifadoRepository transferenciaAlmoxarifadoRepository;

    @Autowired
    private TransferenciaAlmoxarifadoMapper transferenciaAlmoxarifadoMapper;

    @Autowired
    private TransferenciaAlmoxarifadoService transferenciaAlmoxarifadoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.TransferenciaAlmoxarifadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransferenciaAlmoxarifadoSearchRepository mockTransferenciaAlmoxarifadoSearchRepository;

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

    private MockMvc restTransferenciaAlmoxarifadoMockMvc;

    private TransferenciaAlmoxarifado transferenciaAlmoxarifado;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferenciaAlmoxarifado createEntity(EntityManager em) {
        TransferenciaAlmoxarifado transferenciaAlmoxarifado = TransferenciaAlmoxarifado.builder()
            .ativo(DEFAULT_ATIVO).build();
        return transferenciaAlmoxarifado;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferenciaAlmoxarifado createUpdatedEntity(EntityManager em) {
        TransferenciaAlmoxarifado transferenciaAlmoxarifado = TransferenciaAlmoxarifado.builder()
            .ativo(UPDATED_ATIVO).build();
        return transferenciaAlmoxarifado;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransferenciaAlmoxarifadoResource transferenciaAlmoxarifadoResource = new TransferenciaAlmoxarifadoResource(transferenciaAlmoxarifadoService);
        this.restTransferenciaAlmoxarifadoMockMvc = MockMvcBuilders.standaloneSetup(transferenciaAlmoxarifadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        transferenciaAlmoxarifado = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransferenciaAlmoxarifado() throws Exception {
        int databaseSizeBeforeCreate = transferenciaAlmoxarifadoRepository.findAll().size();

        // Create the TransferenciaAlmoxarifado
        TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO = transferenciaAlmoxarifadoMapper.toDto(transferenciaAlmoxarifado);
        restTransferenciaAlmoxarifadoMockMvc.perform(post("/api/transferencia-almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaAlmoxarifadoDTO)))
            .andExpect(status().isCreated());

        // Validate the TransferenciaAlmoxarifado in the database
        List<TransferenciaAlmoxarifado> transferenciaAlmoxarifadoList = transferenciaAlmoxarifadoRepository.findAll();
        assertThat(transferenciaAlmoxarifadoList).hasSize(databaseSizeBeforeCreate + 1);
        TransferenciaAlmoxarifado testTransferenciaAlmoxarifado = transferenciaAlmoxarifadoList.get(transferenciaAlmoxarifadoList.size() - 1);
        assertThat(testTransferenciaAlmoxarifado.getAtivo()).isEqualTo(DEFAULT_ATIVO);

        // Validate the TransferenciaAlmoxarifado in Elasticsearch
        verify(mockTransferenciaAlmoxarifadoSearchRepository, times(1)).save(testTransferenciaAlmoxarifado);
    }

    @Test
    @Transactional
    public void createTransferenciaAlmoxarifadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transferenciaAlmoxarifadoRepository.findAll().size();

        // Create the TransferenciaAlmoxarifado with an existing ID
        transferenciaAlmoxarifado.setId(1L);
        TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO = transferenciaAlmoxarifadoMapper.toDto(transferenciaAlmoxarifado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferenciaAlmoxarifadoMockMvc.perform(post("/api/transferencia-almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaAlmoxarifadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaAlmoxarifado in the database
        List<TransferenciaAlmoxarifado> transferenciaAlmoxarifadoList = transferenciaAlmoxarifadoRepository.findAll();
        assertThat(transferenciaAlmoxarifadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TransferenciaAlmoxarifado in Elasticsearch
        verify(mockTransferenciaAlmoxarifadoSearchRepository, times(0)).save(transferenciaAlmoxarifado);
    }


    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferenciaAlmoxarifadoRepository.findAll().size();
        // set the field null
        transferenciaAlmoxarifado.setAtivo(null);

        // Create the TransferenciaAlmoxarifado, which fails.
        TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO = transferenciaAlmoxarifadoMapper.toDto(transferenciaAlmoxarifado);

        restTransferenciaAlmoxarifadoMockMvc.perform(post("/api/transferencia-almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaAlmoxarifadoDTO)))
            .andExpect(status().isBadRequest());

        List<TransferenciaAlmoxarifado> transferenciaAlmoxarifadoList = transferenciaAlmoxarifadoRepository.findAll();
        assertThat(transferenciaAlmoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransferenciaAlmoxarifados() throws Exception {
        // Initialize the database
        transferenciaAlmoxarifadoRepository.saveAndFlush(transferenciaAlmoxarifado);

        // Get all the transferenciaAlmoxarifadoList
        restTransferenciaAlmoxarifadoMockMvc.perform(get("/api/transferencia-almoxarifados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferenciaAlmoxarifado.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTransferenciaAlmoxarifado() throws Exception {
        // Initialize the database
        transferenciaAlmoxarifadoRepository.saveAndFlush(transferenciaAlmoxarifado);

        // Get the transferenciaAlmoxarifado
        restTransferenciaAlmoxarifadoMockMvc.perform(get("/api/transferencia-almoxarifados/{id}", transferenciaAlmoxarifado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(transferenciaAlmoxarifado.getId().intValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransferenciaAlmoxarifado() throws Exception {
        // Get the transferenciaAlmoxarifado
        restTransferenciaAlmoxarifadoMockMvc.perform(get("/api/transferencia-almoxarifados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransferenciaAlmoxarifado() throws Exception {
        // Initialize the database
        transferenciaAlmoxarifadoRepository.saveAndFlush(transferenciaAlmoxarifado);

        int databaseSizeBeforeUpdate = transferenciaAlmoxarifadoRepository.findAll().size();

        // Update the transferenciaAlmoxarifado
        TransferenciaAlmoxarifado updatedTransferenciaAlmoxarifado = transferenciaAlmoxarifadoRepository.findById(transferenciaAlmoxarifado.getId()).get();
        // Disconnect from session so that the updates on updatedTransferenciaAlmoxarifado are not directly saved in db
        em.detach(updatedTransferenciaAlmoxarifado);

        updatedTransferenciaAlmoxarifado.setAtivo(UPDATED_ATIVO);

        TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO = transferenciaAlmoxarifadoMapper.toDto(updatedTransferenciaAlmoxarifado);

        restTransferenciaAlmoxarifadoMockMvc.perform(put("/api/transferencia-almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaAlmoxarifadoDTO)))
            .andExpect(status().isOk());

        // Validate the TransferenciaAlmoxarifado in the database
        List<TransferenciaAlmoxarifado> transferenciaAlmoxarifadoList = transferenciaAlmoxarifadoRepository.findAll();
        assertThat(transferenciaAlmoxarifadoList).hasSize(databaseSizeBeforeUpdate);
        TransferenciaAlmoxarifado testTransferenciaAlmoxarifado = transferenciaAlmoxarifadoList.get(transferenciaAlmoxarifadoList.size() - 1);
        assertThat(testTransferenciaAlmoxarifado.getAtivo()).isEqualTo(UPDATED_ATIVO);

        // Validate the TransferenciaAlmoxarifado in Elasticsearch
        verify(mockTransferenciaAlmoxarifadoSearchRepository, times(1)).save(testTransferenciaAlmoxarifado);
    }

    @Test
    @Transactional
    public void updateNonExistingTransferenciaAlmoxarifado() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaAlmoxarifadoRepository.findAll().size();

        // Create the TransferenciaAlmoxarifado
        TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO = transferenciaAlmoxarifadoMapper.toDto(transferenciaAlmoxarifado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferenciaAlmoxarifadoMockMvc.perform(put("/api/transferencia-almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaAlmoxarifadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransferenciaAlmoxarifado in the database
        List<TransferenciaAlmoxarifado> transferenciaAlmoxarifadoList = transferenciaAlmoxarifadoRepository.findAll();
        assertThat(transferenciaAlmoxarifadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TransferenciaAlmoxarifado in Elasticsearch
        verify(mockTransferenciaAlmoxarifadoSearchRepository, times(0)).save(transferenciaAlmoxarifado);
    }

    @Test
    @Transactional
    public void deleteTransferenciaAlmoxarifado() throws Exception {
        // Initialize the database
        transferenciaAlmoxarifadoRepository.saveAndFlush(transferenciaAlmoxarifado);

        int databaseSizeBeforeDelete = transferenciaAlmoxarifadoRepository.findAll().size();

        // Delete the transferenciaAlmoxarifado
        restTransferenciaAlmoxarifadoMockMvc.perform(delete("/api/transferencia-almoxarifados/{id}", transferenciaAlmoxarifado.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransferenciaAlmoxarifado> transferenciaAlmoxarifadoList = transferenciaAlmoxarifadoRepository.findAll();
        assertThat(transferenciaAlmoxarifadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TransferenciaAlmoxarifado in Elasticsearch
        verify(mockTransferenciaAlmoxarifadoSearchRepository, times(1)).deleteById(transferenciaAlmoxarifado.getId());
    }

    @Test
    @Transactional
    public void searchTransferenciaAlmoxarifado() throws Exception {
        // Initialize the database
        transferenciaAlmoxarifadoRepository.saveAndFlush(transferenciaAlmoxarifado);
        when(mockTransferenciaAlmoxarifadoSearchRepository.search(queryStringQuery("id:" + transferenciaAlmoxarifado.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(transferenciaAlmoxarifado), PageRequest.of(0, 1), 1));
        // Search the transferenciaAlmoxarifado
        restTransferenciaAlmoxarifadoMockMvc.perform(get("/api/_search/transferencia-almoxarifados?query=id:" + transferenciaAlmoxarifado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferenciaAlmoxarifado.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferenciaAlmoxarifado.class);
        TransferenciaAlmoxarifado transferenciaAlmoxarifado1 = new TransferenciaAlmoxarifado();
        transferenciaAlmoxarifado1.setId(1L);
        TransferenciaAlmoxarifado transferenciaAlmoxarifado2 = new TransferenciaAlmoxarifado();
        transferenciaAlmoxarifado2.setId(transferenciaAlmoxarifado1.getId());
        assertThat(transferenciaAlmoxarifado1).isEqualTo(transferenciaAlmoxarifado2);
        transferenciaAlmoxarifado2.setId(2L);
        assertThat(transferenciaAlmoxarifado1).isNotEqualTo(transferenciaAlmoxarifado2);
        transferenciaAlmoxarifado1.setId(null);
        assertThat(transferenciaAlmoxarifado1).isNotEqualTo(transferenciaAlmoxarifado2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferenciaAlmoxarifadoDTO.class);
        TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO1 = new TransferenciaAlmoxarifadoDTO();
        transferenciaAlmoxarifadoDTO1.setId(1L);
        TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO2 = new TransferenciaAlmoxarifadoDTO();
        assertThat(transferenciaAlmoxarifadoDTO1).isNotEqualTo(transferenciaAlmoxarifadoDTO2);
        transferenciaAlmoxarifadoDTO2.setId(transferenciaAlmoxarifadoDTO1.getId());
        assertThat(transferenciaAlmoxarifadoDTO1).isEqualTo(transferenciaAlmoxarifadoDTO2);
        transferenciaAlmoxarifadoDTO2.setId(2L);
        assertThat(transferenciaAlmoxarifadoDTO1).isNotEqualTo(transferenciaAlmoxarifadoDTO2);
        transferenciaAlmoxarifadoDTO1.setId(null);
        assertThat(transferenciaAlmoxarifadoDTO1).isNotEqualTo(transferenciaAlmoxarifadoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transferenciaAlmoxarifadoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transferenciaAlmoxarifadoMapper.fromId(null)).isNull();
    }
}
