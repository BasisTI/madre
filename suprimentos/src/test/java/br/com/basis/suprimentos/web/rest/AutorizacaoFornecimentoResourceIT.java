package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.AutorizacaoFornecimento;
import br.com.basis.suprimentos.domain.enumeration.TipoItemAf;
import br.com.basis.suprimentos.repository.AutorizacaoFornecimentoRepository;
import br.com.basis.suprimentos.repository.search.AutorizacaoFornecimentoSearchRepository;
import br.com.basis.suprimentos.service.AutorizacaoFornecimentoService;
import br.com.basis.suprimentos.service.dto.AutorizacaoFornecimentoDTO;
import br.com.basis.suprimentos.service.mapper.AutorizacaoFornecimentoMapper;
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
 * Integration tests for the {@link AutorizacaoFornecimentoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class AutorizacaoFornecimentoResourceIT {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final TipoItemAf DEFAULT_TIPO_ITEM = TipoItemAf.MATERIAL;
    private static final TipoItemAf UPDATED_TIPO_ITEM = TipoItemAf.SERVICO;

    @Autowired
    private AutorizacaoFornecimentoRepository autorizacaoFornecimentoRepository;

    @Autowired
    private AutorizacaoFornecimentoMapper autorizacaoFornecimentoMapper;

    @Autowired
    private AutorizacaoFornecimentoService autorizacaoFornecimentoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.AutorizacaoFornecimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AutorizacaoFornecimentoSearchRepository mockAutorizacaoFornecimentoSearchRepository;

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

    private MockMvc restAutorizacaoFornecimentoMockMvc;

    private AutorizacaoFornecimento autorizacaoFornecimento;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutorizacaoFornecimento createEntity(EntityManager em) {
        AutorizacaoFornecimento autorizacaoFornecimento = new AutorizacaoFornecimento();
        autorizacaoFornecimento.setNumero(DEFAULT_NUMERO);
        autorizacaoFornecimento.setComplemento(DEFAULT_COMPLEMENTO);
        autorizacaoFornecimento.setTipoItem(DEFAULT_TIPO_ITEM);
        return autorizacaoFornecimento;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutorizacaoFornecimento createUpdatedEntity(EntityManager em) {
        AutorizacaoFornecimento autorizacaoFornecimento = new AutorizacaoFornecimento();
        autorizacaoFornecimento.setNumero(UPDATED_NUMERO);
        autorizacaoFornecimento.setComplemento(UPDATED_COMPLEMENTO);
        autorizacaoFornecimento.setTipoItem(UPDATED_TIPO_ITEM);
        return autorizacaoFornecimento;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutorizacaoFornecimentoResource autorizacaoFornecimentoResource = new AutorizacaoFornecimentoResource(autorizacaoFornecimentoService);
        this.restAutorizacaoFornecimentoMockMvc = MockMvcBuilders.standaloneSetup(autorizacaoFornecimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        autorizacaoFornecimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutorizacaoFornecimento() throws Exception {
        int databaseSizeBeforeCreate = autorizacaoFornecimentoRepository.findAll().size();

        // Create the AutorizacaoFornecimento
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO = autorizacaoFornecimentoMapper.toDto(autorizacaoFornecimento);
        restAutorizacaoFornecimentoMockMvc.perform(post("/api/autorizacao-fornecimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autorizacaoFornecimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the AutorizacaoFornecimento in the database
        List<AutorizacaoFornecimento> autorizacaoFornecimentoList = autorizacaoFornecimentoRepository.findAll();
        assertThat(autorizacaoFornecimentoList).hasSize(databaseSizeBeforeCreate + 1);
        AutorizacaoFornecimento testAutorizacaoFornecimento = autorizacaoFornecimentoList.get(autorizacaoFornecimentoList.size() - 1);
        assertThat(testAutorizacaoFornecimento.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAutorizacaoFornecimento.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testAutorizacaoFornecimento.getTipoItem()).isEqualTo(DEFAULT_TIPO_ITEM);

        // Validate the AutorizacaoFornecimento in Elasticsearch
        verify(mockAutorizacaoFornecimentoSearchRepository, times(1)).save(testAutorizacaoFornecimento);
    }

    @Test
    @Transactional
    public void createAutorizacaoFornecimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autorizacaoFornecimentoRepository.findAll().size();

        // Create the AutorizacaoFornecimento with an existing ID
        autorizacaoFornecimento.setId(1L);
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO = autorizacaoFornecimentoMapper.toDto(autorizacaoFornecimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutorizacaoFornecimentoMockMvc.perform(post("/api/autorizacao-fornecimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autorizacaoFornecimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutorizacaoFornecimento in the database
        List<AutorizacaoFornecimento> autorizacaoFornecimentoList = autorizacaoFornecimentoRepository.findAll();
        assertThat(autorizacaoFornecimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the AutorizacaoFornecimento in Elasticsearch
        verify(mockAutorizacaoFornecimentoSearchRepository, times(0)).save(autorizacaoFornecimento);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacaoFornecimentoRepository.findAll().size();
        // set the field null
        autorizacaoFornecimento.setNumero(null);

        // Create the AutorizacaoFornecimento, which fails.
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO = autorizacaoFornecimentoMapper.toDto(autorizacaoFornecimento);

        restAutorizacaoFornecimentoMockMvc.perform(post("/api/autorizacao-fornecimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autorizacaoFornecimentoDTO)))
            .andExpect(status().isBadRequest());

        List<AutorizacaoFornecimento> autorizacaoFornecimentoList = autorizacaoFornecimentoRepository.findAll();
        assertThat(autorizacaoFornecimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComplementoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacaoFornecimentoRepository.findAll().size();
        // set the field null
        autorizacaoFornecimento.setComplemento(null);

        // Create the AutorizacaoFornecimento, which fails.
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO = autorizacaoFornecimentoMapper.toDto(autorizacaoFornecimento);

        restAutorizacaoFornecimentoMockMvc.perform(post("/api/autorizacao-fornecimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autorizacaoFornecimentoDTO)))
            .andExpect(status().isBadRequest());

        List<AutorizacaoFornecimento> autorizacaoFornecimentoList = autorizacaoFornecimentoRepository.findAll();
        assertThat(autorizacaoFornecimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoItemIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacaoFornecimentoRepository.findAll().size();
        // set the field null
        autorizacaoFornecimento.setTipoItem(null);

        // Create the AutorizacaoFornecimento, which fails.
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO = autorizacaoFornecimentoMapper.toDto(autorizacaoFornecimento);

        restAutorizacaoFornecimentoMockMvc.perform(post("/api/autorizacao-fornecimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autorizacaoFornecimentoDTO)))
            .andExpect(status().isBadRequest());

        List<AutorizacaoFornecimento> autorizacaoFornecimentoList = autorizacaoFornecimentoRepository.findAll();
        assertThat(autorizacaoFornecimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutorizacaoFornecimentos() throws Exception {
        // Initialize the database
        autorizacaoFornecimentoRepository.saveAndFlush(autorizacaoFornecimento);

        // Get all the autorizacaoFornecimentoList
        restAutorizacaoFornecimentoMockMvc.perform(get("/api/autorizacao-fornecimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autorizacaoFornecimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].tipoItem").value(hasItem(DEFAULT_TIPO_ITEM.toString())));
    }

    @Test
    @Transactional
    public void getAutorizacaoFornecimento() throws Exception {
        // Initialize the database
        autorizacaoFornecimentoRepository.saveAndFlush(autorizacaoFornecimento);

        // Get the autorizacaoFornecimento
        restAutorizacaoFornecimentoMockMvc.perform(get("/api/autorizacao-fornecimentos/{id}", autorizacaoFornecimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autorizacaoFornecimento.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.tipoItem").value(DEFAULT_TIPO_ITEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutorizacaoFornecimento() throws Exception {
        // Get the autorizacaoFornecimento
        restAutorizacaoFornecimentoMockMvc.perform(get("/api/autorizacao-fornecimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutorizacaoFornecimento() throws Exception {
        // Initialize the database
        autorizacaoFornecimentoRepository.saveAndFlush(autorizacaoFornecimento);

        int databaseSizeBeforeUpdate = autorizacaoFornecimentoRepository.findAll().size();

        // Update the autorizacaoFornecimento
        AutorizacaoFornecimento updatedAutorizacaoFornecimento = autorizacaoFornecimentoRepository.findById(autorizacaoFornecimento.getId()).get();
        // Disconnect from session so that the updates on updatedAutorizacaoFornecimento are not directly saved in db
        em.detach(updatedAutorizacaoFornecimento);

        updatedAutorizacaoFornecimento.setNumero(UPDATED_NUMERO);
        updatedAutorizacaoFornecimento.setComplemento(UPDATED_COMPLEMENTO);
        updatedAutorizacaoFornecimento.setTipoItem(UPDATED_TIPO_ITEM);
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO = autorizacaoFornecimentoMapper.toDto(updatedAutorizacaoFornecimento);

        restAutorizacaoFornecimentoMockMvc.perform(put("/api/autorizacao-fornecimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autorizacaoFornecimentoDTO)))
            .andExpect(status().isOk());

        // Validate the AutorizacaoFornecimento in the database
        List<AutorizacaoFornecimento> autorizacaoFornecimentoList = autorizacaoFornecimentoRepository.findAll();
        assertThat(autorizacaoFornecimentoList).hasSize(databaseSizeBeforeUpdate);
        AutorizacaoFornecimento testAutorizacaoFornecimento = autorizacaoFornecimentoList.get(autorizacaoFornecimentoList.size() - 1);
        assertThat(testAutorizacaoFornecimento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAutorizacaoFornecimento.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testAutorizacaoFornecimento.getTipoItem()).isEqualTo(UPDATED_TIPO_ITEM);

        // Validate the AutorizacaoFornecimento in Elasticsearch
        verify(mockAutorizacaoFornecimentoSearchRepository, times(1)).save(testAutorizacaoFornecimento);
    }

    @Test
    @Transactional
    public void updateNonExistingAutorizacaoFornecimento() throws Exception {
        int databaseSizeBeforeUpdate = autorizacaoFornecimentoRepository.findAll().size();

        // Create the AutorizacaoFornecimento
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO = autorizacaoFornecimentoMapper.toDto(autorizacaoFornecimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutorizacaoFornecimentoMockMvc.perform(put("/api/autorizacao-fornecimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autorizacaoFornecimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutorizacaoFornecimento in the database
        List<AutorizacaoFornecimento> autorizacaoFornecimentoList = autorizacaoFornecimentoRepository.findAll();
        assertThat(autorizacaoFornecimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AutorizacaoFornecimento in Elasticsearch
        verify(mockAutorizacaoFornecimentoSearchRepository, times(0)).save(autorizacaoFornecimento);
    }

    @Test
    @Transactional
    public void deleteAutorizacaoFornecimento() throws Exception {
        // Initialize the database
        autorizacaoFornecimentoRepository.saveAndFlush(autorizacaoFornecimento);

        int databaseSizeBeforeDelete = autorizacaoFornecimentoRepository.findAll().size();

        // Delete the autorizacaoFornecimento
        restAutorizacaoFornecimentoMockMvc.perform(delete("/api/autorizacao-fornecimentos/{id}", autorizacaoFornecimento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutorizacaoFornecimento> autorizacaoFornecimentoList = autorizacaoFornecimentoRepository.findAll();
        assertThat(autorizacaoFornecimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AutorizacaoFornecimento in Elasticsearch
        verify(mockAutorizacaoFornecimentoSearchRepository, times(1)).deleteById(autorizacaoFornecimento.getId());
    }

    @Test
    @Transactional
    public void searchAutorizacaoFornecimento() throws Exception {
        // Initialize the database
        autorizacaoFornecimentoRepository.saveAndFlush(autorizacaoFornecimento);
        when(mockAutorizacaoFornecimentoSearchRepository.search(queryStringQuery("id:" + autorizacaoFornecimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(autorizacaoFornecimento), PageRequest.of(0, 1), 1));
        // Search the autorizacaoFornecimento
        restAutorizacaoFornecimentoMockMvc.perform(get("/api/_search/autorizacao-fornecimentos?query=id:" + autorizacaoFornecimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autorizacaoFornecimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].tipoItem").value(hasItem(DEFAULT_TIPO_ITEM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutorizacaoFornecimento.class);
        AutorizacaoFornecimento autorizacaoFornecimento1 = new AutorizacaoFornecimento();
        autorizacaoFornecimento1.setId(1L);
        AutorizacaoFornecimento autorizacaoFornecimento2 = new AutorizacaoFornecimento();
        autorizacaoFornecimento2.setId(autorizacaoFornecimento1.getId());
        assertThat(autorizacaoFornecimento1).isEqualTo(autorizacaoFornecimento2);
        autorizacaoFornecimento2.setId(2L);
        assertThat(autorizacaoFornecimento1).isNotEqualTo(autorizacaoFornecimento2);
        autorizacaoFornecimento1.setId(null);
        assertThat(autorizacaoFornecimento1).isNotEqualTo(autorizacaoFornecimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutorizacaoFornecimentoDTO.class);
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO1 = new AutorizacaoFornecimentoDTO();
        autorizacaoFornecimentoDTO1.setId(1L);
        AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO2 = new AutorizacaoFornecimentoDTO();
        assertThat(autorizacaoFornecimentoDTO1).isNotEqualTo(autorizacaoFornecimentoDTO2);
        autorizacaoFornecimentoDTO2.setId(autorizacaoFornecimentoDTO1.getId());
        assertThat(autorizacaoFornecimentoDTO1).isEqualTo(autorizacaoFornecimentoDTO2);
        autorizacaoFornecimentoDTO2.setId(2L);
        assertThat(autorizacaoFornecimentoDTO1).isNotEqualTo(autorizacaoFornecimentoDTO2);
        autorizacaoFornecimentoDTO1.setId(null);
        assertThat(autorizacaoFornecimentoDTO1).isNotEqualTo(autorizacaoFornecimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autorizacaoFornecimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autorizacaoFornecimentoMapper.fromId(null)).isNull();
    }
}
