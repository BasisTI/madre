package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.SolicitacaoCompras;
import br.com.basis.suprimentos.repository.SolicitacaoComprasRepository;
import br.com.basis.suprimentos.repository.search.SolicitacaoComprasSearchRepository;
import br.com.basis.suprimentos.service.SolicitacaoComprasService;
import br.com.basis.suprimentos.service.dto.SolicitacaoComprasDTO;
import br.com.basis.suprimentos.service.mapper.SolicitacaoComprasMapper;
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
 * Integration tests for the {@link SolicitacaoComprasResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class SolicitacaoComprasResourceIT {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    @Autowired
    private SolicitacaoComprasRepository solicitacaoComprasRepository;

    @Autowired
    private SolicitacaoComprasMapper solicitacaoComprasMapper;

    @Autowired
    private SolicitacaoComprasService solicitacaoComprasService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.SolicitacaoComprasSearchRepositoryMockConfiguration
     */
    @Autowired
    private SolicitacaoComprasSearchRepository mockSolicitacaoComprasSearchRepository;

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

    private MockMvc restSolicitacaoComprasMockMvc;

    private SolicitacaoCompras solicitacaoCompras;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitacaoCompras createEntity(EntityManager em) {
        SolicitacaoCompras solicitacaoCompras = new SolicitacaoCompras()
                .numero(DEFAULT_NUMERO);
        return solicitacaoCompras;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitacaoCompras createUpdatedEntity(EntityManager em) {
        SolicitacaoCompras solicitacaoCompras = new SolicitacaoCompras()
                .numero(UPDATED_NUMERO);
        return solicitacaoCompras;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolicitacaoComprasResource solicitacaoComprasResource = new SolicitacaoComprasResource(solicitacaoComprasService);
        this.restSolicitacaoComprasMockMvc = MockMvcBuilders.standaloneSetup(solicitacaoComprasResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        solicitacaoCompras = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitacaoCompras() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoComprasRepository.findAll().size();

        // Create the SolicitacaoCompras
        SolicitacaoComprasDTO solicitacaoComprasDTO = solicitacaoComprasMapper.toDto(solicitacaoCompras);
        restSolicitacaoComprasMockMvc.perform(post("/api/solicitacao-compras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(solicitacaoComprasDTO)))
                .andExpect(status().isCreated());

        // Validate the SolicitacaoCompras in the database
        List<SolicitacaoCompras> solicitacaoComprasList = solicitacaoComprasRepository.findAll();
        assertThat(solicitacaoComprasList).hasSize(databaseSizeBeforeCreate + 1);
        SolicitacaoCompras testSolicitacaoCompras = solicitacaoComprasList.get(solicitacaoComprasList.size() - 1);
        assertThat(testSolicitacaoCompras.getNumero()).isEqualTo(DEFAULT_NUMERO);

        // Validate the SolicitacaoCompras in Elasticsearch
        verify(mockSolicitacaoComprasSearchRepository, times(1)).save(testSolicitacaoCompras);
    }

    @Test
    @Transactional
    public void createSolicitacaoComprasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoComprasRepository.findAll().size();

        // Create the SolicitacaoCompras with an existing ID
        solicitacaoCompras.setId(1L);
        SolicitacaoComprasDTO solicitacaoComprasDTO = solicitacaoComprasMapper.toDto(solicitacaoCompras);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitacaoComprasMockMvc.perform(post("/api/solicitacao-compras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(solicitacaoComprasDTO)))
                .andExpect(status().isBadRequest());

        // Validate the SolicitacaoCompras in the database
        List<SolicitacaoCompras> solicitacaoComprasList = solicitacaoComprasRepository.findAll();
        assertThat(solicitacaoComprasList).hasSize(databaseSizeBeforeCreate);

        // Validate the SolicitacaoCompras in Elasticsearch
        verify(mockSolicitacaoComprasSearchRepository, times(0)).save(solicitacaoCompras);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoComprasRepository.findAll().size();
        // set the field null
        solicitacaoCompras.setNumero(null);

        // Create the SolicitacaoCompras, which fails.
        SolicitacaoComprasDTO solicitacaoComprasDTO = solicitacaoComprasMapper.toDto(solicitacaoCompras);

        restSolicitacaoComprasMockMvc.perform(post("/api/solicitacao-compras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(solicitacaoComprasDTO)))
                .andExpect(status().isBadRequest());

        List<SolicitacaoCompras> solicitacaoComprasList = solicitacaoComprasRepository.findAll();
        assertThat(solicitacaoComprasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoCompras() throws Exception {
        // Initialize the database
        solicitacaoComprasRepository.saveAndFlush(solicitacaoCompras);

        // Get all the solicitacaoComprasList
        restSolicitacaoComprasMockMvc.perform(get("/api/solicitacao-compras?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(solicitacaoCompras.getId().intValue())))
                .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())));
    }

    @Test
    @Transactional
    public void getSolicitacaoCompras() throws Exception {
        // Initialize the database
        solicitacaoComprasRepository.saveAndFlush(solicitacaoCompras);

        // Get the solicitacaoCompras
        restSolicitacaoComprasMockMvc.perform(get("/api/solicitacao-compras/{id}", solicitacaoCompras.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(solicitacaoCompras.getId().intValue()))
                .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSolicitacaoCompras() throws Exception {
        // Get the solicitacaoCompras
        restSolicitacaoComprasMockMvc.perform(get("/api/solicitacao-compras/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitacaoCompras() throws Exception {
        // Initialize the database
        solicitacaoComprasRepository.saveAndFlush(solicitacaoCompras);

        int databaseSizeBeforeUpdate = solicitacaoComprasRepository.findAll().size();

        // Update the solicitacaoCompras
        SolicitacaoCompras updatedSolicitacaoCompras = solicitacaoComprasRepository.findById(solicitacaoCompras.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitacaoCompras are not directly saved in db
        em.detach(updatedSolicitacaoCompras);
        updatedSolicitacaoCompras
                .numero(UPDATED_NUMERO);
        SolicitacaoComprasDTO solicitacaoComprasDTO = solicitacaoComprasMapper.toDto(updatedSolicitacaoCompras);

        restSolicitacaoComprasMockMvc.perform(put("/api/solicitacao-compras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(solicitacaoComprasDTO)))
                .andExpect(status().isOk());

        // Validate the SolicitacaoCompras in the database
        List<SolicitacaoCompras> solicitacaoComprasList = solicitacaoComprasRepository.findAll();
        assertThat(solicitacaoComprasList).hasSize(databaseSizeBeforeUpdate);
        SolicitacaoCompras testSolicitacaoCompras = solicitacaoComprasList.get(solicitacaoComprasList.size() - 1);
        assertThat(testSolicitacaoCompras.getNumero()).isEqualTo(UPDATED_NUMERO);

        // Validate the SolicitacaoCompras in Elasticsearch
        verify(mockSolicitacaoComprasSearchRepository, times(1)).save(testSolicitacaoCompras);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitacaoCompras() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoComprasRepository.findAll().size();

        // Create the SolicitacaoCompras
        SolicitacaoComprasDTO solicitacaoComprasDTO = solicitacaoComprasMapper.toDto(solicitacaoCompras);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitacaoComprasMockMvc.perform(put("/api/solicitacao-compras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(solicitacaoComprasDTO)))
                .andExpect(status().isBadRequest());

        // Validate the SolicitacaoCompras in the database
        List<SolicitacaoCompras> solicitacaoComprasList = solicitacaoComprasRepository.findAll();
        assertThat(solicitacaoComprasList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SolicitacaoCompras in Elasticsearch
        verify(mockSolicitacaoComprasSearchRepository, times(0)).save(solicitacaoCompras);
    }

    @Test
    @Transactional
    public void deleteSolicitacaoCompras() throws Exception {
        // Initialize the database
        solicitacaoComprasRepository.saveAndFlush(solicitacaoCompras);

        int databaseSizeBeforeDelete = solicitacaoComprasRepository.findAll().size();

        // Delete the solicitacaoCompras
        restSolicitacaoComprasMockMvc.perform(delete("/api/solicitacao-compras/{id}", solicitacaoCompras.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SolicitacaoCompras> solicitacaoComprasList = solicitacaoComprasRepository.findAll();
        assertThat(solicitacaoComprasList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SolicitacaoCompras in Elasticsearch
        verify(mockSolicitacaoComprasSearchRepository, times(1)).deleteById(solicitacaoCompras.getId());
    }

    @Test
    @Transactional
    public void searchSolicitacaoCompras() throws Exception {
        // Initialize the database
        solicitacaoComprasRepository.saveAndFlush(solicitacaoCompras);
        when(mockSolicitacaoComprasSearchRepository.search(queryStringQuery("id:" + solicitacaoCompras.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(solicitacaoCompras), PageRequest.of(0, 1), 1));
        // Search the solicitacaoCompras
        restSolicitacaoComprasMockMvc.perform(get("/api/_search/solicitacao-compras?query=id:" + solicitacaoCompras.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(solicitacaoCompras.getId().intValue())))
                .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoCompras.class);
        SolicitacaoCompras solicitacaoCompras1 = new SolicitacaoCompras();
        solicitacaoCompras1.setId(1L);
        SolicitacaoCompras solicitacaoCompras2 = new SolicitacaoCompras();
        solicitacaoCompras2.setId(solicitacaoCompras1.getId());
        assertThat(solicitacaoCompras1).isEqualTo(solicitacaoCompras2);
        solicitacaoCompras2.setId(2L);
        assertThat(solicitacaoCompras1).isNotEqualTo(solicitacaoCompras2);
        solicitacaoCompras1.setId(null);
        assertThat(solicitacaoCompras1).isNotEqualTo(solicitacaoCompras2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoComprasDTO.class);
        SolicitacaoComprasDTO solicitacaoComprasDTO1 = new SolicitacaoComprasDTO();
        solicitacaoComprasDTO1.setId(1L);
        SolicitacaoComprasDTO solicitacaoComprasDTO2 = new SolicitacaoComprasDTO();
        assertThat(solicitacaoComprasDTO1).isNotEqualTo(solicitacaoComprasDTO2);
        solicitacaoComprasDTO2.setId(solicitacaoComprasDTO1.getId());
        assertThat(solicitacaoComprasDTO1).isEqualTo(solicitacaoComprasDTO2);
        solicitacaoComprasDTO2.setId(2L);
        assertThat(solicitacaoComprasDTO1).isNotEqualTo(solicitacaoComprasDTO2);
        solicitacaoComprasDTO1.setId(null);
        assertThat(solicitacaoComprasDTO1).isNotEqualTo(solicitacaoComprasDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(solicitacaoComprasMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(solicitacaoComprasMapper.fromId(null)).isNull();
    }
}
