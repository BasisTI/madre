package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.DocumentoFiscalEntrada;
import br.com.basis.suprimentos.domain.Fornecedor;
import br.com.basis.suprimentos.domain.enumeration.TipoDocumento;
import br.com.basis.suprimentos.domain.enumeration.TipoDocumentoFiscal;
import br.com.basis.suprimentos.repository.DocumentoFiscalEntradaRepository;
import br.com.basis.suprimentos.repository.search.DocumentoFiscalEntradaSearchRepository;
import br.com.basis.suprimentos.service.DocumentoFiscalEntradaService;
import br.com.basis.suprimentos.service.dto.DocumentoFiscalEntradaDTO;
import br.com.basis.suprimentos.service.mapper.DocumentoFiscalEntradaMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DocumentoFiscalEntradaResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class DocumentoFiscalEntradaResourceIT {

    private static final Long DEFAULT_NUMERO_DOCUMENTO = 1L;
    private static final Long UPDATED_NUMERO_DOCUMENTO = 2L;

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTA_EMPENHO = "AAAAAAAAAA";
    private static final String UPDATED_NOTA_EMPENHO = "BBBBBBBBBB";

    private static final String DEFAULT_CPF_CNPJ = "AAAAAAAAAAA";
    private static final String UPDATED_CPF_CNPJ = "BBBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_GERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_GERACAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_ENTRADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ENTRADA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);

    private static final TipoDocumento DEFAULT_TIPO_DOCUMENTO = TipoDocumento.NOTA_FISCAL;
    private static final TipoDocumento UPDATED_TIPO_DOCUMENTO = TipoDocumento.TERMO_DE_TRANSFERENCIA;

    private static final TipoDocumentoFiscal DEFAULT_TIPO_DOCUMENTO_FISCAL = TipoDocumentoFiscal.NFS;
    private static final TipoDocumentoFiscal UPDATED_TIPO_DOCUMENTO_FISCAL = TipoDocumentoFiscal.DFE;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private DocumentoFiscalEntradaRepository documentoFiscalEntradaRepository;

    @Autowired
    private DocumentoFiscalEntradaMapper documentoFiscalEntradaMapper;

    @Autowired
    private DocumentoFiscalEntradaService documentoFiscalEntradaService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.DocumentoFiscalEntradaSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentoFiscalEntradaSearchRepository mockDocumentoFiscalEntradaSearchRepository;

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

    private MockMvc restDocumentoFiscalEntradaMockMvc;

    private DocumentoFiscalEntrada documentoFiscalEntrada;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentoFiscalEntrada createEntity(EntityManager em) {
        DocumentoFiscalEntrada documentoFiscalEntrada = new DocumentoFiscalEntrada();
        documentoFiscalEntrada.setNumeroDocumento(DEFAULT_NUMERO_DOCUMENTO);
        documentoFiscalEntrada.setSerie(DEFAULT_SERIE);
        documentoFiscalEntrada.setNotaEmpenho(DEFAULT_NOTA_EMPENHO);
        documentoFiscalEntrada.setDataGeracao(DEFAULT_DATA_GERACAO);
        documentoFiscalEntrada.setDataEmissao(DEFAULT_DATA_EMISSAO);
        documentoFiscalEntrada.setDataEntrada(DEFAULT_DATA_ENTRADA);
        documentoFiscalEntrada.setDataVencimento(DEFAULT_DATA_VENCIMENTO);
        documentoFiscalEntrada.setValorTotal(DEFAULT_VALOR_TOTAL);
        documentoFiscalEntrada.setTipoDocumento(DEFAULT_TIPO_DOCUMENTO);
        documentoFiscalEntrada.setTipoDocumentoFiscal(DEFAULT_TIPO_DOCUMENTO_FISCAL);
        documentoFiscalEntrada.setObservacao(DEFAULT_OBSERVACAO);
        // Add required entity
        Fornecedor fornecedor;
        if (TestUtil.findAll(em, Fornecedor.class).isEmpty()) {
            fornecedor = FornecedorResourceIT.createEntity(em);
            em.persist(fornecedor);
            em.flush();
        } else {
            fornecedor = TestUtil.findAll(em, Fornecedor.class).get(0);
        }
        documentoFiscalEntrada.setFornecedor(fornecedor);
        return documentoFiscalEntrada;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentoFiscalEntrada createUpdatedEntity(EntityManager em) {
        DocumentoFiscalEntrada documentoFiscalEntrada = new DocumentoFiscalEntrada();
        documentoFiscalEntrada.setNumeroDocumento(UPDATED_NUMERO_DOCUMENTO);
        documentoFiscalEntrada.setSerie(UPDATED_SERIE);
        documentoFiscalEntrada.setNotaEmpenho(UPDATED_NOTA_EMPENHO);
        documentoFiscalEntrada.setDataGeracao(UPDATED_DATA_GERACAO);
        documentoFiscalEntrada.setDataEmissao(UPDATED_DATA_EMISSAO);
        documentoFiscalEntrada.setDataGeracao(UPDATED_DATA_ENTRADA);
        documentoFiscalEntrada.setDataVencimento(UPDATED_DATA_VENCIMENTO);
        documentoFiscalEntrada.setValorTotal(UPDATED_VALOR_TOTAL);
        documentoFiscalEntrada.setTipoDocumento(UPDATED_TIPO_DOCUMENTO);
        documentoFiscalEntrada.setTipoDocumentoFiscal(UPDATED_TIPO_DOCUMENTO_FISCAL);
        documentoFiscalEntrada.setObservacao(UPDATED_OBSERVACAO);
        // Add required entity
        Fornecedor fornecedor;
        if (TestUtil.findAll(em, Fornecedor.class).isEmpty()) {
            fornecedor = FornecedorResourceIT.createUpdatedEntity(em);
            em.persist(fornecedor);
            em.flush();
        } else {
            fornecedor = TestUtil.findAll(em, Fornecedor.class).get(0);
        }
        documentoFiscalEntrada.setFornecedor(fornecedor);
        return documentoFiscalEntrada;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentoFiscalEntradaResource documentoFiscalEntradaResource = new DocumentoFiscalEntradaResource(documentoFiscalEntradaService);
        this.restDocumentoFiscalEntradaMockMvc = MockMvcBuilders.standaloneSetup(documentoFiscalEntradaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        documentoFiscalEntrada = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentoFiscalEntrada() throws Exception {
        int databaseSizeBeforeCreate = documentoFiscalEntradaRepository.findAll().size();

        // Create the DocumentoFiscalEntrada
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);
        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentoFiscalEntrada in the database
        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentoFiscalEntrada testDocumentoFiscalEntrada = documentoFiscalEntradaList.get(documentoFiscalEntradaList.size() - 1);
        assertThat(testDocumentoFiscalEntrada.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
        assertThat(testDocumentoFiscalEntrada.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testDocumentoFiscalEntrada.getNotaEmpenho()).isEqualTo(DEFAULT_NOTA_EMPENHO);
        assertThat(testDocumentoFiscalEntrada.getDataGeracao()).isEqualTo(DEFAULT_DATA_GERACAO);
        assertThat(testDocumentoFiscalEntrada.getDataEmissao()).isEqualTo(DEFAULT_DATA_EMISSAO);
        assertThat(testDocumentoFiscalEntrada.getDataEntrada()).isEqualTo(DEFAULT_DATA_ENTRADA);
        assertThat(testDocumentoFiscalEntrada.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testDocumentoFiscalEntrada.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);
        assertThat(testDocumentoFiscalEntrada.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testDocumentoFiscalEntrada.getTipoDocumentoFiscal()).isEqualTo(DEFAULT_TIPO_DOCUMENTO_FISCAL);
        assertThat(testDocumentoFiscalEntrada.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the DocumentoFiscalEntrada in Elasticsearch
        verify(mockDocumentoFiscalEntradaSearchRepository, times(1)).save(testDocumentoFiscalEntrada);
    }

    @Test
    @Transactional
    public void createDocumentoFiscalEntradaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentoFiscalEntradaRepository.findAll().size();

        // Create the DocumentoFiscalEntrada with an existing ID
        documentoFiscalEntrada.setId(1L);
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentoFiscalEntrada in the database
        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeCreate);

        // Validate the DocumentoFiscalEntrada in Elasticsearch
        verify(mockDocumentoFiscalEntradaSearchRepository, times(0)).save(documentoFiscalEntrada);
    }


    @Test
    @Transactional
    public void checkNumeroDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        documentoFiscalEntrada.setNumeroDocumento(null);

        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        documentoFiscalEntrada.setSerie(null);

        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataGeracaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        documentoFiscalEntrada.setDataGeracao(null);

        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataEmissaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        documentoFiscalEntrada.setDataEmissao(null);

        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        documentoFiscalEntrada.setDataEntrada(null);

        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataVencimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        documentoFiscalEntrada.setDataVencimento(null);

        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        documentoFiscalEntrada.setValorTotal(null);

        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoDocumentoFiscalIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoFiscalEntradaRepository.findAll().size();
        // set the field null
        documentoFiscalEntrada.setTipoDocumentoFiscal(null);

        // Create the DocumentoFiscalEntrada, which fails.
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(post("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentoFiscalEntradas() throws Exception {
        // Initialize the database
        documentoFiscalEntradaRepository.saveAndFlush(documentoFiscalEntrada);

        // Get all the documentoFiscalEntradaList
        restDocumentoFiscalEntradaMockMvc.perform(get("/api/documento-fiscal-entradas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoFiscalEntrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO.intValue())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].notaEmpenho").value(hasItem(DEFAULT_NOTA_EMPENHO)))
            .andExpect(jsonPath("$.[*].cpfCnpj").value(hasItem(DEFAULT_CPF_CNPJ)))
            .andExpect(jsonPath("$.[*].dataGeracao").value(hasItem(DEFAULT_DATA_GERACAO.toString())))
            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].dataEntrada").value(hasItem(DEFAULT_DATA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoDocumentoFiscal").value(hasItem(DEFAULT_TIPO_DOCUMENTO_FISCAL.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void getDocumentoFiscalEntrada() throws Exception {
        // Initialize the database
        documentoFiscalEntradaRepository.saveAndFlush(documentoFiscalEntrada);

        // Get the documentoFiscalEntrada
        restDocumentoFiscalEntradaMockMvc.perform(get("/api/documento-fiscal-entradas/{id}", documentoFiscalEntrada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentoFiscalEntrada.getId().intValue()))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO.intValue()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE))
            .andExpect(jsonPath("$.notaEmpenho").value(DEFAULT_NOTA_EMPENHO))
            .andExpect(jsonPath("$.cpfCnpj").value(DEFAULT_CPF_CNPJ))
            .andExpect(jsonPath("$.dataGeracao").value(DEFAULT_DATA_GERACAO.toString()))
            .andExpect(jsonPath("$.dataEmissao").value(DEFAULT_DATA_EMISSAO.toString()))
            .andExpect(jsonPath("$.dataEntrada").value(DEFAULT_DATA_ENTRADA.toString()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL.intValue()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.tipoDocumentoFiscal").value(DEFAULT_TIPO_DOCUMENTO_FISCAL.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentoFiscalEntrada() throws Exception {
        // Get the documentoFiscalEntrada
        restDocumentoFiscalEntradaMockMvc.perform(get("/api/documento-fiscal-entradas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentoFiscalEntrada() throws Exception {
        // Initialize the database
        documentoFiscalEntradaRepository.saveAndFlush(documentoFiscalEntrada);

        int databaseSizeBeforeUpdate = documentoFiscalEntradaRepository.findAll().size();

        // Update the documentoFiscalEntrada
        DocumentoFiscalEntrada updatedDocumentoFiscalEntrada = documentoFiscalEntradaRepository.findById(documentoFiscalEntrada.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentoFiscalEntrada are not directly saved in db
        em.detach(updatedDocumentoFiscalEntrada);
        updatedDocumentoFiscalEntrada.setNumeroDocumento(UPDATED_NUMERO_DOCUMENTO);
        updatedDocumentoFiscalEntrada.setSerie(UPDATED_SERIE);
        updatedDocumentoFiscalEntrada.setNotaEmpenho(UPDATED_NOTA_EMPENHO);
        updatedDocumentoFiscalEntrada.setDataGeracao(UPDATED_DATA_GERACAO);
        updatedDocumentoFiscalEntrada.setDataEmissao(UPDATED_DATA_EMISSAO);
        updatedDocumentoFiscalEntrada.setDataEntrada(UPDATED_DATA_ENTRADA);
        updatedDocumentoFiscalEntrada.setDataVencimento(UPDATED_DATA_VENCIMENTO);
        updatedDocumentoFiscalEntrada.setValorTotal(UPDATED_VALOR_TOTAL);
        updatedDocumentoFiscalEntrada.setTipoDocumento(UPDATED_TIPO_DOCUMENTO);
        updatedDocumentoFiscalEntrada.setTipoDocumentoFiscal(UPDATED_TIPO_DOCUMENTO_FISCAL);
        updatedDocumentoFiscalEntrada.setObservacao(UPDATED_OBSERVACAO);
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(updatedDocumentoFiscalEntrada);

        restDocumentoFiscalEntradaMockMvc.perform(put("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentoFiscalEntrada in the database
        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeUpdate);
        DocumentoFiscalEntrada testDocumentoFiscalEntrada = documentoFiscalEntradaList.get(documentoFiscalEntradaList.size() - 1);
        assertThat(testDocumentoFiscalEntrada.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
        assertThat(testDocumentoFiscalEntrada.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testDocumentoFiscalEntrada.getNotaEmpenho()).isEqualTo(UPDATED_NOTA_EMPENHO);
        assertThat(testDocumentoFiscalEntrada.getDataGeracao()).isEqualTo(UPDATED_DATA_GERACAO);
        assertThat(testDocumentoFiscalEntrada.getDataEmissao()).isEqualTo(UPDATED_DATA_EMISSAO);
        assertThat(testDocumentoFiscalEntrada.getDataEntrada()).isEqualTo(UPDATED_DATA_ENTRADA);
        assertThat(testDocumentoFiscalEntrada.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testDocumentoFiscalEntrada.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
        assertThat(testDocumentoFiscalEntrada.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testDocumentoFiscalEntrada.getTipoDocumentoFiscal()).isEqualTo(UPDATED_TIPO_DOCUMENTO_FISCAL);
        assertThat(testDocumentoFiscalEntrada.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the DocumentoFiscalEntrada in Elasticsearch
        verify(mockDocumentoFiscalEntradaSearchRepository, times(1)).save(testDocumentoFiscalEntrada);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentoFiscalEntrada() throws Exception {
        int databaseSizeBeforeUpdate = documentoFiscalEntradaRepository.findAll().size();

        // Create the DocumentoFiscalEntrada
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoFiscalEntradaMockMvc.perform(put("/api/documento-fiscal-entradas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoFiscalEntradaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentoFiscalEntrada in the database
        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DocumentoFiscalEntrada in Elasticsearch
        verify(mockDocumentoFiscalEntradaSearchRepository, times(0)).save(documentoFiscalEntrada);
    }

    @Test
    @Transactional
    public void deleteDocumentoFiscalEntrada() throws Exception {
        // Initialize the database
        documentoFiscalEntradaRepository.saveAndFlush(documentoFiscalEntrada);

        int databaseSizeBeforeDelete = documentoFiscalEntradaRepository.findAll().size();

        // Delete the documentoFiscalEntrada
        restDocumentoFiscalEntradaMockMvc.perform(delete("/api/documento-fiscal-entradas/{id}", documentoFiscalEntrada.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentoFiscalEntrada> documentoFiscalEntradaList = documentoFiscalEntradaRepository.findAll();
        assertThat(documentoFiscalEntradaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DocumentoFiscalEntrada in Elasticsearch
        verify(mockDocumentoFiscalEntradaSearchRepository, times(1)).deleteById(documentoFiscalEntrada.getId());
    }

    @Test
    @Transactional
    public void searchDocumentoFiscalEntrada() throws Exception {
        // Initialize the database
        documentoFiscalEntradaRepository.saveAndFlush(documentoFiscalEntrada);
        when(mockDocumentoFiscalEntradaSearchRepository.search(queryStringQuery("id:" + documentoFiscalEntrada.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documentoFiscalEntrada), PageRequest.of(0, 1), 1));
        // Search the documentoFiscalEntrada
        restDocumentoFiscalEntradaMockMvc.perform(get("/api/_search/documento-fiscal-entradas?query=id:" + documentoFiscalEntrada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoFiscalEntrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO.intValue())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].notaEmpenho").value(hasItem(DEFAULT_NOTA_EMPENHO)))
            .andExpect(jsonPath("$.[*].cpfCnpj").value(hasItem(DEFAULT_CPF_CNPJ)))
            .andExpect(jsonPath("$.[*].dataGeracao").value(hasItem(DEFAULT_DATA_GERACAO.toString())))
            .andExpect(jsonPath("$.[*].dataEmissao").value(hasItem(DEFAULT_DATA_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].dataEntrada").value(hasItem(DEFAULT_DATA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoDocumentoFiscal").value(hasItem(DEFAULT_TIPO_DOCUMENTO_FISCAL.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoFiscalEntrada.class);
        DocumentoFiscalEntrada documentoFiscalEntrada1 = new DocumentoFiscalEntrada();
        documentoFiscalEntrada1.setId(1L);
        DocumentoFiscalEntrada documentoFiscalEntrada2 = new DocumentoFiscalEntrada();
        documentoFiscalEntrada2.setId(documentoFiscalEntrada1.getId());
        assertThat(documentoFiscalEntrada1).isEqualTo(documentoFiscalEntrada2);
        documentoFiscalEntrada2.setId(2L);
        assertThat(documentoFiscalEntrada1).isNotEqualTo(documentoFiscalEntrada2);
        documentoFiscalEntrada1.setId(null);
        assertThat(documentoFiscalEntrada1).isNotEqualTo(documentoFiscalEntrada2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoFiscalEntradaDTO.class);
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO1 = new DocumentoFiscalEntradaDTO();
        documentoFiscalEntradaDTO1.setId(1L);
        DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO2 = new DocumentoFiscalEntradaDTO();
        assertThat(documentoFiscalEntradaDTO1).isNotEqualTo(documentoFiscalEntradaDTO2);
        documentoFiscalEntradaDTO2.setId(documentoFiscalEntradaDTO1.getId());
        assertThat(documentoFiscalEntradaDTO1).isEqualTo(documentoFiscalEntradaDTO2);
        documentoFiscalEntradaDTO2.setId(2L);
        assertThat(documentoFiscalEntradaDTO1).isNotEqualTo(documentoFiscalEntradaDTO2);
        documentoFiscalEntradaDTO1.setId(null);
        assertThat(documentoFiscalEntradaDTO1).isNotEqualTo(documentoFiscalEntradaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(documentoFiscalEntradaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentoFiscalEntradaMapper.fromId(null)).isNull();
    }
}
