package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Almoxarifado;
import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import br.com.basis.suprimentos.domain.Fornecedor;
import br.com.basis.suprimentos.domain.Material;
import br.com.basis.suprimentos.repository.EstoqueAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.EstoqueAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.EstoqueAlmoxarifadoService;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.EstoqueAlmoxarifadoMapper;
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
 * Integration tests for the {@link EstoqueAlmoxarifadoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class EstoqueAlmoxarifadoResourceIT {

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String DEFAULT_ENDERECO = "AAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBB";

    private static final Long DEFAULT_QUANTIDADE_LIMITE_ARMAZENAMENTO = 1L;
    private static final Long UPDATED_QUANTIDADE_LIMITE_ARMAZENAMENTO = 2L;

    private static final Long DEFAULT_QUANTIDADE_ESTOQUE_MINIMO = 1L;
    private static final Long UPDATED_QUANTIDADE_ESTOQUE_MINIMO = 2L;

    private static final Long DEFAULT_QUANTIDADE_ESTOQUE_MAXIMO = 1L;
    private static final Long UPDATED_QUANTIDADE_ESTOQUE_MAXIMO = 2L;

    private static final Long DEFAULT_QUANTIDADE_PONTO_PEDIDO = 1L;
    private static final Long UPDATED_QUANTIDADE_PONTO_PEDIDO = 2L;

    private static final Integer DEFAULT_TEMPO_REPOSICAO = 1;
    private static final Integer UPDATED_TEMPO_REPOSICAO = 2;

    @Autowired
    private EstoqueAlmoxarifadoRepository estoqueAlmoxarifadoRepository;

    @Autowired
    private EstoqueAlmoxarifadoMapper estoqueAlmoxarifadoMapper;

    @Autowired
    private EstoqueAlmoxarifadoService estoqueAlmoxarifadoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.EstoqueAlmoxarifadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EstoqueAlmoxarifadoSearchRepository mockEstoqueAlmoxarifadoSearchRepository;

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

    private MockMvc restEstoqueAlmoxarifadoMockMvc;

    private EstoqueAlmoxarifado estoqueAlmoxarifado;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstoqueAlmoxarifado createEntity(EntityManager em) {
        EstoqueAlmoxarifado estoqueAlmoxarifado = new EstoqueAlmoxarifado()
                .ativo(DEFAULT_ATIVO)
                .endereco(DEFAULT_ENDERECO)
                .quantidadeLimiteArmazenamento(DEFAULT_QUANTIDADE_LIMITE_ARMAZENAMENTO)
                .quantidadeEstoqueMinimo(DEFAULT_QUANTIDADE_ESTOQUE_MINIMO)
                .quantidadeEstoqueMaximo(DEFAULT_QUANTIDADE_ESTOQUE_MAXIMO)
                .quantidadePontoPedido(DEFAULT_QUANTIDADE_PONTO_PEDIDO)
                .tempoReposicao(DEFAULT_TEMPO_REPOSICAO);
        // Add required entity
        Almoxarifado almoxarifado;
        if (TestUtil.findAll(em, Almoxarifado.class).isEmpty()) {
            almoxarifado = AlmoxarifadoResourceIT.createEntity(em);
            em.persist(almoxarifado);
            em.flush();
        } else {
            almoxarifado = TestUtil.findAll(em, Almoxarifado.class).get(0);
        }
        estoqueAlmoxarifado.setAlmoxarifado(almoxarifado);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        estoqueAlmoxarifado.setMaterial(material);
        // Add required entity
        Fornecedor fornecedor;
        if (TestUtil.findAll(em, Fornecedor.class).isEmpty()) {
            fornecedor = FornecedorResourceIT.createEntity(em);
            em.persist(fornecedor);
            em.flush();
        } else {
            fornecedor = TestUtil.findAll(em, Fornecedor.class).get(0);
        }
        estoqueAlmoxarifado.setFornecedor(fornecedor);
        return estoqueAlmoxarifado;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstoqueAlmoxarifado createUpdatedEntity(EntityManager em) {
        EstoqueAlmoxarifado estoqueAlmoxarifado = new EstoqueAlmoxarifado()
                .ativo(UPDATED_ATIVO)
                .endereco(UPDATED_ENDERECO)
                .quantidadeLimiteArmazenamento(UPDATED_QUANTIDADE_LIMITE_ARMAZENAMENTO)
                .quantidadeEstoqueMinimo(UPDATED_QUANTIDADE_ESTOQUE_MINIMO)
                .quantidadeEstoqueMaximo(UPDATED_QUANTIDADE_ESTOQUE_MAXIMO)
                .quantidadePontoPedido(UPDATED_QUANTIDADE_PONTO_PEDIDO)
                .tempoReposicao(UPDATED_TEMPO_REPOSICAO);
        // Add required entity
        Almoxarifado almoxarifado;
        if (TestUtil.findAll(em, Almoxarifado.class).isEmpty()) {
            almoxarifado = AlmoxarifadoResourceIT.createUpdatedEntity(em);
            em.persist(almoxarifado);
            em.flush();
        } else {
            almoxarifado = TestUtil.findAll(em, Almoxarifado.class).get(0);
        }
        estoqueAlmoxarifado.setAlmoxarifado(almoxarifado);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        estoqueAlmoxarifado.setMaterial(material);
        // Add required entity
        Fornecedor fornecedor;
        if (TestUtil.findAll(em, Fornecedor.class).isEmpty()) {
            fornecedor = FornecedorResourceIT.createUpdatedEntity(em);
            em.persist(fornecedor);
            em.flush();
        } else {
            fornecedor = TestUtil.findAll(em, Fornecedor.class).get(0);
        }
        estoqueAlmoxarifado.setFornecedor(fornecedor);
        return estoqueAlmoxarifado;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstoqueAlmoxarifadoResource estoqueAlmoxarifadoResource = new EstoqueAlmoxarifadoResource(estoqueAlmoxarifadoService);
        this.restEstoqueAlmoxarifadoMockMvc = MockMvcBuilders.standaloneSetup(estoqueAlmoxarifadoResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        estoqueAlmoxarifado = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstoqueAlmoxarifado() throws Exception {
        int databaseSizeBeforeCreate = estoqueAlmoxarifadoRepository.findAll().size();

        // Create the EstoqueAlmoxarifado
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);
        restEstoqueAlmoxarifadoMockMvc.perform(post("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isCreated());

        // Validate the EstoqueAlmoxarifado in the database
        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeCreate + 1);
        EstoqueAlmoxarifado testEstoqueAlmoxarifado = estoqueAlmoxarifadoList.get(estoqueAlmoxarifadoList.size() - 1);
        assertThat(testEstoqueAlmoxarifado.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testEstoqueAlmoxarifado.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testEstoqueAlmoxarifado.getQuantidadeLimiteArmazenamento()).isEqualTo(DEFAULT_QUANTIDADE_LIMITE_ARMAZENAMENTO);
        assertThat(testEstoqueAlmoxarifado.getQuantidadeEstoqueMinimo()).isEqualTo(DEFAULT_QUANTIDADE_ESTOQUE_MINIMO);
        assertThat(testEstoqueAlmoxarifado.getQuantidadeEstoqueMaximo()).isEqualTo(DEFAULT_QUANTIDADE_ESTOQUE_MAXIMO);
        assertThat(testEstoqueAlmoxarifado.getQuantidadePontoPedido()).isEqualTo(DEFAULT_QUANTIDADE_PONTO_PEDIDO);
        assertThat(testEstoqueAlmoxarifado.getTempoReposicao()).isEqualTo(DEFAULT_TEMPO_REPOSICAO);

        // Validate the EstoqueAlmoxarifado in Elasticsearch
        verify(mockEstoqueAlmoxarifadoSearchRepository, times(1)).save(testEstoqueAlmoxarifado);
    }

    @Test
    @Transactional
    public void createEstoqueAlmoxarifadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estoqueAlmoxarifadoRepository.findAll().size();

        // Create the EstoqueAlmoxarifado with an existing ID
        estoqueAlmoxarifado.setId(1L);
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstoqueAlmoxarifadoMockMvc.perform(post("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isBadRequest());

        // Validate the EstoqueAlmoxarifado in the database
        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the EstoqueAlmoxarifado in Elasticsearch
        verify(mockEstoqueAlmoxarifadoSearchRepository, times(0)).save(estoqueAlmoxarifado);
    }


    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueAlmoxarifadoRepository.findAll().size();
        // set the field null
        estoqueAlmoxarifado.setAtivo(null);

        // Create the EstoqueAlmoxarifado, which fails.
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);

        restEstoqueAlmoxarifadoMockMvc.perform(post("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isBadRequest());

        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeEstoqueMinimoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueAlmoxarifadoRepository.findAll().size();
        // set the field null
        estoqueAlmoxarifado.setQuantidadeEstoqueMinimo(null);

        // Create the EstoqueAlmoxarifado, which fails.
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);

        restEstoqueAlmoxarifadoMockMvc.perform(post("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isBadRequest());

        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeEstoqueMaximoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueAlmoxarifadoRepository.findAll().size();
        // set the field null
        estoqueAlmoxarifado.setQuantidadeEstoqueMaximo(null);

        // Create the EstoqueAlmoxarifado, which fails.
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);

        restEstoqueAlmoxarifadoMockMvc.perform(post("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isBadRequest());

        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadePontoPedidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueAlmoxarifadoRepository.findAll().size();
        // set the field null
        estoqueAlmoxarifado.setQuantidadePontoPedido(null);

        // Create the EstoqueAlmoxarifado, which fails.
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);

        restEstoqueAlmoxarifadoMockMvc.perform(post("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isBadRequest());

        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempoReposicaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueAlmoxarifadoRepository.findAll().size();
        // set the field null
        estoqueAlmoxarifado.setTempoReposicao(null);

        // Create the EstoqueAlmoxarifado, which fails.
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);

        restEstoqueAlmoxarifadoMockMvc.perform(post("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isBadRequest());

        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstoqueAlmoxarifados() throws Exception {
        // Initialize the database
        estoqueAlmoxarifadoRepository.saveAndFlush(estoqueAlmoxarifado);

        // Get all the estoqueAlmoxarifadoList
        restEstoqueAlmoxarifadoMockMvc.perform(get("/api/estoque-almoxarifados?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(estoqueAlmoxarifado.getId().intValue())))
                .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
                .andExpect(jsonPath("$.[*].quantidadeLimiteArmazenamento").value(hasItem(DEFAULT_QUANTIDADE_LIMITE_ARMAZENAMENTO.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeEstoqueMinimo").value(hasItem(DEFAULT_QUANTIDADE_ESTOQUE_MINIMO.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeEstoqueMaximo").value(hasItem(DEFAULT_QUANTIDADE_ESTOQUE_MAXIMO.intValue())))
                .andExpect(jsonPath("$.[*].quantidadePontoPedido").value(hasItem(DEFAULT_QUANTIDADE_PONTO_PEDIDO.intValue())))
                .andExpect(jsonPath("$.[*].tempoReposicao").value(hasItem(DEFAULT_TEMPO_REPOSICAO)));
    }

    @Test
    @Transactional
    public void getEstoqueAlmoxarifado() throws Exception {
        // Initialize the database
        estoqueAlmoxarifadoRepository.saveAndFlush(estoqueAlmoxarifado);

        // Get the estoqueAlmoxarifado
        restEstoqueAlmoxarifadoMockMvc.perform(get("/api/estoque-almoxarifados/{id}", estoqueAlmoxarifado.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(estoqueAlmoxarifado.getId().intValue()))
                .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
                .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
                .andExpect(jsonPath("$.quantidadeLimiteArmazenamento").value(DEFAULT_QUANTIDADE_LIMITE_ARMAZENAMENTO.intValue()))
                .andExpect(jsonPath("$.quantidadeEstoqueMinimo").value(DEFAULT_QUANTIDADE_ESTOQUE_MINIMO.intValue()))
                .andExpect(jsonPath("$.quantidadeEstoqueMaximo").value(DEFAULT_QUANTIDADE_ESTOQUE_MAXIMO.intValue()))
                .andExpect(jsonPath("$.quantidadePontoPedido").value(DEFAULT_QUANTIDADE_PONTO_PEDIDO.intValue()))
                .andExpect(jsonPath("$.tempoReposicao").value(DEFAULT_TEMPO_REPOSICAO));
    }

    @Test
    @Transactional
    public void getNonExistingEstoqueAlmoxarifado() throws Exception {
        // Get the estoqueAlmoxarifado
        restEstoqueAlmoxarifadoMockMvc.perform(get("/api/estoque-almoxarifados/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstoqueAlmoxarifado() throws Exception {
        // Initialize the database
        estoqueAlmoxarifadoRepository.saveAndFlush(estoqueAlmoxarifado);

        int databaseSizeBeforeUpdate = estoqueAlmoxarifadoRepository.findAll().size();

        // Update the estoqueAlmoxarifado
        EstoqueAlmoxarifado updatedEstoqueAlmoxarifado = estoqueAlmoxarifadoRepository.findById(estoqueAlmoxarifado.getId()).get();
        // Disconnect from session so that the updates on updatedEstoqueAlmoxarifado are not directly saved in db
        em.detach(updatedEstoqueAlmoxarifado);
        updatedEstoqueAlmoxarifado
                .ativo(UPDATED_ATIVO)
                .endereco(UPDATED_ENDERECO)
                .quantidadeLimiteArmazenamento(UPDATED_QUANTIDADE_LIMITE_ARMAZENAMENTO)
                .quantidadeEstoqueMinimo(UPDATED_QUANTIDADE_ESTOQUE_MINIMO)
                .quantidadeEstoqueMaximo(UPDATED_QUANTIDADE_ESTOQUE_MAXIMO)
                .quantidadePontoPedido(UPDATED_QUANTIDADE_PONTO_PEDIDO)
                .tempoReposicao(UPDATED_TEMPO_REPOSICAO);
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(updatedEstoqueAlmoxarifado);

        restEstoqueAlmoxarifadoMockMvc.perform(put("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isOk());

        // Validate the EstoqueAlmoxarifado in the database
        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeUpdate);
        EstoqueAlmoxarifado testEstoqueAlmoxarifado = estoqueAlmoxarifadoList.get(estoqueAlmoxarifadoList.size() - 1);
        assertThat(testEstoqueAlmoxarifado.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testEstoqueAlmoxarifado.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEstoqueAlmoxarifado.getQuantidadeLimiteArmazenamento()).isEqualTo(UPDATED_QUANTIDADE_LIMITE_ARMAZENAMENTO);
        assertThat(testEstoqueAlmoxarifado.getQuantidadeEstoqueMinimo()).isEqualTo(UPDATED_QUANTIDADE_ESTOQUE_MINIMO);
        assertThat(testEstoqueAlmoxarifado.getQuantidadeEstoqueMaximo()).isEqualTo(UPDATED_QUANTIDADE_ESTOQUE_MAXIMO);
        assertThat(testEstoqueAlmoxarifado.getQuantidadePontoPedido()).isEqualTo(UPDATED_QUANTIDADE_PONTO_PEDIDO);
        assertThat(testEstoqueAlmoxarifado.getTempoReposicao()).isEqualTo(UPDATED_TEMPO_REPOSICAO);

        // Validate the EstoqueAlmoxarifado in Elasticsearch
        verify(mockEstoqueAlmoxarifadoSearchRepository, times(1)).save(testEstoqueAlmoxarifado);
    }

    @Test
    @Transactional
    public void updateNonExistingEstoqueAlmoxarifado() throws Exception {
        int databaseSizeBeforeUpdate = estoqueAlmoxarifadoRepository.findAll().size();

        // Create the EstoqueAlmoxarifado
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoMapper.toDto(estoqueAlmoxarifado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstoqueAlmoxarifadoMockMvc.perform(put("/api/estoque-almoxarifados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(estoqueAlmoxarifadoDTO)))
                .andExpect(status().isBadRequest());

        // Validate the EstoqueAlmoxarifado in the database
        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EstoqueAlmoxarifado in Elasticsearch
        verify(mockEstoqueAlmoxarifadoSearchRepository, times(0)).save(estoqueAlmoxarifado);
    }

    @Test
    @Transactional
    public void deleteEstoqueAlmoxarifado() throws Exception {
        // Initialize the database
        estoqueAlmoxarifadoRepository.saveAndFlush(estoqueAlmoxarifado);

        int databaseSizeBeforeDelete = estoqueAlmoxarifadoRepository.findAll().size();

        // Delete the estoqueAlmoxarifado
        restEstoqueAlmoxarifadoMockMvc.perform(delete("/api/estoque-almoxarifados/{id}", estoqueAlmoxarifado.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstoqueAlmoxarifado> estoqueAlmoxarifadoList = estoqueAlmoxarifadoRepository.findAll();
        assertThat(estoqueAlmoxarifadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EstoqueAlmoxarifado in Elasticsearch
        verify(mockEstoqueAlmoxarifadoSearchRepository, times(1)).deleteById(estoqueAlmoxarifado.getId());
    }

    @Test
    @Transactional
    public void searchEstoqueAlmoxarifado() throws Exception {
        // Initialize the database
        estoqueAlmoxarifadoRepository.saveAndFlush(estoqueAlmoxarifado);
        when(mockEstoqueAlmoxarifadoSearchRepository.search(queryStringQuery("id:" + estoqueAlmoxarifado.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(estoqueAlmoxarifado), PageRequest.of(0, 1), 1));
        // Search the estoqueAlmoxarifado
        restEstoqueAlmoxarifadoMockMvc.perform(get("/api/_search/estoque-almoxarifados?query=id:" + estoqueAlmoxarifado.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(estoqueAlmoxarifado.getId().intValue())))
                .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
                .andExpect(jsonPath("$.[*].quantidadeLimiteArmazenamento").value(hasItem(DEFAULT_QUANTIDADE_LIMITE_ARMAZENAMENTO.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeEstoqueMinimo").value(hasItem(DEFAULT_QUANTIDADE_ESTOQUE_MINIMO.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeEstoqueMaximo").value(hasItem(DEFAULT_QUANTIDADE_ESTOQUE_MAXIMO.intValue())))
                .andExpect(jsonPath("$.[*].quantidadePontoPedido").value(hasItem(DEFAULT_QUANTIDADE_PONTO_PEDIDO.intValue())))
                .andExpect(jsonPath("$.[*].tempoReposicao").value(hasItem(DEFAULT_TEMPO_REPOSICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstoqueAlmoxarifado.class);
        EstoqueAlmoxarifado estoqueAlmoxarifado1 = new EstoqueAlmoxarifado();
        estoqueAlmoxarifado1.setId(1L);
        EstoqueAlmoxarifado estoqueAlmoxarifado2 = new EstoqueAlmoxarifado();
        estoqueAlmoxarifado2.setId(estoqueAlmoxarifado1.getId());
        assertThat(estoqueAlmoxarifado1).isEqualTo(estoqueAlmoxarifado2);
        estoqueAlmoxarifado2.setId(2L);
        assertThat(estoqueAlmoxarifado1).isNotEqualTo(estoqueAlmoxarifado2);
        estoqueAlmoxarifado1.setId(null);
        assertThat(estoqueAlmoxarifado1).isNotEqualTo(estoqueAlmoxarifado2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstoqueAlmoxarifadoDTO.class);
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO1 = new EstoqueAlmoxarifadoDTO();
        estoqueAlmoxarifadoDTO1.setId(1L);
        EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO2 = new EstoqueAlmoxarifadoDTO();
        assertThat(estoqueAlmoxarifadoDTO1).isNotEqualTo(estoqueAlmoxarifadoDTO2);
        estoqueAlmoxarifadoDTO2.setId(estoqueAlmoxarifadoDTO1.getId());
        assertThat(estoqueAlmoxarifadoDTO1).isEqualTo(estoqueAlmoxarifadoDTO2);
        estoqueAlmoxarifadoDTO2.setId(2L);
        assertThat(estoqueAlmoxarifadoDTO1).isNotEqualTo(estoqueAlmoxarifadoDTO2);
        estoqueAlmoxarifadoDTO1.setId(null);
        assertThat(estoqueAlmoxarifadoDTO1).isNotEqualTo(estoqueAlmoxarifadoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estoqueAlmoxarifadoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estoqueAlmoxarifadoMapper.fromId(null)).isNull();
    }
}
