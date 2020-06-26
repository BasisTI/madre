package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Almoxarifado;
import br.com.basis.suprimentos.domain.CentroDeAtividade;
import br.com.basis.suprimentos.repository.AlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.AlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.AlmoxarifadoService;
import br.com.basis.suprimentos.service.dto.AlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.AlmoxarifadoMapper;
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
 * Integration tests for the {@link AlmoxarifadoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class AlmoxarifadoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIAS_ESTOQUE = 0;
    private static final Integer UPDATED_DIAS_ESTOQUE = 1;

    private static final Boolean DEFAULT_CENTRAL = false;
    private static final Boolean UPDATED_CENTRAL = true;

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_CALCULA_MEDIA_PONDERADA = false;
    private static final Boolean UPDATED_CALCULA_MEDIA_PONDERADA = true;

    private static final Boolean DEFAULT_BLOQUEIA_ENTRADA_TRANSFERENCIA = false;
    private static final Boolean UPDATED_BLOQUEIA_ENTRADA_TRANSFERENCIA = true;

    @Autowired
    private AlmoxarifadoRepository almoxarifadoRepository;

    @Autowired
    private AlmoxarifadoMapper almoxarifadoMapper;

    @Autowired
    private AlmoxarifadoService almoxarifadoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.AlmoxarifadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlmoxarifadoSearchRepository mockAlmoxarifadoSearchRepository;

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

    private MockMvc restAlmoxarifadoMockMvc;

    private Almoxarifado almoxarifado;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Almoxarifado createEntity(EntityManager em) {
        Almoxarifado almoxarifado = new Almoxarifado();
        almoxarifado.setDescricao(DEFAULT_DESCRICAO);
        almoxarifado.setDiasEstoque(DEFAULT_DIAS_ESTOQUE);
        almoxarifado.setCentral(DEFAULT_CENTRAL);
        almoxarifado.setAtivo(DEFAULT_ATIVO);
        almoxarifado.setCalculaMediaPonderada(DEFAULT_CALCULA_MEDIA_PONDERADA);
        almoxarifado.setBloqueiaEntradaTransferencia(DEFAULT_BLOQUEIA_ENTRADA_TRANSFERENCIA);
        // Add required entity
        CentroDeAtividade centroDeAtividade;
        if (TestUtil.findAll(em, CentroDeAtividade.class).isEmpty()) {
            centroDeAtividade = CentroDeAtividadeResourceIT.createEntity(em);
            em.persist(centroDeAtividade);
            em.flush();
        } else {
            centroDeAtividade = TestUtil.findAll(em, CentroDeAtividade.class).get(0);
        }
        almoxarifado.setCentroDeAtividade(centroDeAtividade);
        return almoxarifado;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Almoxarifado createUpdatedEntity(EntityManager em) {
        Almoxarifado almoxarifado = new Almoxarifado();
        almoxarifado.setDescricao(UPDATED_DESCRICAO);
        almoxarifado.setDiasEstoque(UPDATED_DIAS_ESTOQUE);
        almoxarifado.setCentral(UPDATED_CENTRAL);
        almoxarifado.setAtivo(UPDATED_ATIVO);
        almoxarifado.setCalculaMediaPonderada(UPDATED_CALCULA_MEDIA_PONDERADA);
        almoxarifado.setBloqueiaEntradaTransferencia(UPDATED_BLOQUEIA_ENTRADA_TRANSFERENCIA);
        // Add required entity
        CentroDeAtividade centroDeAtividade;
        if (TestUtil.findAll(em, CentroDeAtividade.class).isEmpty()) {
            centroDeAtividade = CentroDeAtividadeResourceIT.createUpdatedEntity(em);
            em.persist(centroDeAtividade);
            em.flush();
        } else {
            centroDeAtividade = TestUtil.findAll(em, CentroDeAtividade.class).get(0);
        }
        almoxarifado.setCentroDeAtividade(centroDeAtividade);
        return almoxarifado;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlmoxarifadoResource almoxarifadoResource = new AlmoxarifadoResource(almoxarifadoService);
        this.restAlmoxarifadoMockMvc = MockMvcBuilders.standaloneSetup(almoxarifadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        almoxarifado = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlmoxarifado() throws Exception {
        int databaseSizeBeforeCreate = almoxarifadoRepository.findAll().size();

        // Create the Almoxarifado
        AlmoxarifadoDTO almoxarifadoDTO = almoxarifadoMapper.toDto(almoxarifado);
        restAlmoxarifadoMockMvc.perform(post("/api/almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(almoxarifadoDTO)))
            .andExpect(status().isCreated());

        // Validate the Almoxarifado in the database
        List<Almoxarifado> almoxarifadoList = almoxarifadoRepository.findAll();
        assertThat(almoxarifadoList).hasSize(databaseSizeBeforeCreate + 1);
        Almoxarifado testAlmoxarifado = almoxarifadoList.get(almoxarifadoList.size() - 1);
        assertThat(testAlmoxarifado.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAlmoxarifado.getDiasEstoque()).isEqualTo(DEFAULT_DIAS_ESTOQUE);
        assertThat(testAlmoxarifado.getCentral()).isEqualTo(DEFAULT_CENTRAL);
        assertThat(testAlmoxarifado.getAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testAlmoxarifado.getCalculaMediaPonderada()).isEqualTo(DEFAULT_CALCULA_MEDIA_PONDERADA);
        assertThat(testAlmoxarifado.getBloqueiaEntradaTransferencia()).isEqualTo(DEFAULT_BLOQUEIA_ENTRADA_TRANSFERENCIA);

        // Validate the Almoxarifado in Elasticsearch
        verify(mockAlmoxarifadoSearchRepository, times(1)).save(testAlmoxarifado);
    }

    @Test
    @Transactional
    public void createAlmoxarifadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = almoxarifadoRepository.findAll().size();

        // Create the Almoxarifado with an existing ID
        almoxarifado.setId(1L);
        AlmoxarifadoDTO almoxarifadoDTO = almoxarifadoMapper.toDto(almoxarifado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlmoxarifadoMockMvc.perform(post("/api/almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(almoxarifadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Almoxarifado in the database
        List<Almoxarifado> almoxarifadoList = almoxarifadoRepository.findAll();
        assertThat(almoxarifadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Almoxarifado in Elasticsearch
        verify(mockAlmoxarifadoSearchRepository, times(0)).save(almoxarifado);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = almoxarifadoRepository.findAll().size();
        // set the field null
        almoxarifado.setDescricao(null);

        // Create the Almoxarifado, which fails.
        AlmoxarifadoDTO almoxarifadoDTO = almoxarifadoMapper.toDto(almoxarifado);

        restAlmoxarifadoMockMvc.perform(post("/api/almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(almoxarifadoDTO)))
            .andExpect(status().isBadRequest());

        List<Almoxarifado> almoxarifadoList = almoxarifadoRepository.findAll();
        assertThat(almoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = almoxarifadoRepository.findAll().size();
        // set the field null
        almoxarifado.setAtivo(null);

        // Create the Almoxarifado, which fails.
        AlmoxarifadoDTO almoxarifadoDTO = almoxarifadoMapper.toDto(almoxarifado);

        restAlmoxarifadoMockMvc.perform(post("/api/almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(almoxarifadoDTO)))
            .andExpect(status().isBadRequest());

        List<Almoxarifado> almoxarifadoList = almoxarifadoRepository.findAll();
        assertThat(almoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCalculaMediaPonderadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = almoxarifadoRepository.findAll().size();
        // set the field null
        almoxarifado.setCalculaMediaPonderada(null);

        // Create the Almoxarifado, which fails.
        AlmoxarifadoDTO almoxarifadoDTO = almoxarifadoMapper.toDto(almoxarifado);

        restAlmoxarifadoMockMvc.perform(post("/api/almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(almoxarifadoDTO)))
            .andExpect(status().isBadRequest());

        List<Almoxarifado> almoxarifadoList = almoxarifadoRepository.findAll();
        assertThat(almoxarifadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlmoxarifados() throws Exception {
        // Initialize the database
        almoxarifadoRepository.saveAndFlush(almoxarifado);

        // Get all the almoxarifadoList
        restAlmoxarifadoMockMvc.perform(get("/api/almoxarifados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(almoxarifado.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].diasEstoque").value(hasItem(DEFAULT_DIAS_ESTOQUE)))
            .andExpect(jsonPath("$.[*].central").value(hasItem(DEFAULT_CENTRAL.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].calculaMediaPonderada").value(hasItem(DEFAULT_CALCULA_MEDIA_PONDERADA.booleanValue())))
            .andExpect(jsonPath("$.[*].bloqueiaEntradaTransferencia").value(hasItem(DEFAULT_BLOQUEIA_ENTRADA_TRANSFERENCIA.booleanValue())));
    }

    @Test
    @Transactional
    public void getAlmoxarifado() throws Exception {
        // Initialize the database
        almoxarifadoRepository.saveAndFlush(almoxarifado);

        // Get the almoxarifado
        restAlmoxarifadoMockMvc.perform(get("/api/almoxarifados/{id}", almoxarifado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(almoxarifado.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.diasEstoque").value(DEFAULT_DIAS_ESTOQUE))
            .andExpect(jsonPath("$.central").value(DEFAULT_CENTRAL.booleanValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.calculaMediaPonderada").value(DEFAULT_CALCULA_MEDIA_PONDERADA.booleanValue()))
            .andExpect(jsonPath("$.bloqueiaEntradaTransferencia").value(DEFAULT_BLOQUEIA_ENTRADA_TRANSFERENCIA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAlmoxarifado() throws Exception {
        // Get the almoxarifado
        restAlmoxarifadoMockMvc.perform(get("/api/almoxarifados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlmoxarifado() throws Exception {
        // Initialize the database
        almoxarifadoRepository.saveAndFlush(almoxarifado);

        int databaseSizeBeforeUpdate = almoxarifadoRepository.findAll().size();

        // Update the almoxarifado
        Almoxarifado updatedAlmoxarifado = almoxarifadoRepository.findById(almoxarifado.getId()).get();
        // Disconnect from session so that the updates on updatedAlmoxarifado are not directly saved in db
        em.detach(updatedAlmoxarifado);

        updatedAlmoxarifado.setDescricao(UPDATED_DESCRICAO);
        updatedAlmoxarifado.setDiasEstoque(UPDATED_DIAS_ESTOQUE);
        updatedAlmoxarifado.setCentral(UPDATED_CENTRAL);
        updatedAlmoxarifado.setAtivo(UPDATED_ATIVO);
        updatedAlmoxarifado.setCalculaMediaPonderada(UPDATED_CALCULA_MEDIA_PONDERADA);
        updatedAlmoxarifado.setBloqueiaEntradaTransferencia(UPDATED_BLOQUEIA_ENTRADA_TRANSFERENCIA);
        AlmoxarifadoDTO almoxarifadoDTO = almoxarifadoMapper.toDto(updatedAlmoxarifado);

        restAlmoxarifadoMockMvc.perform(put("/api/almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(almoxarifadoDTO)))
            .andExpect(status().isOk());

        // Validate the Almoxarifado in the database
        List<Almoxarifado> almoxarifadoList = almoxarifadoRepository.findAll();
        assertThat(almoxarifadoList).hasSize(databaseSizeBeforeUpdate);
        Almoxarifado testAlmoxarifado = almoxarifadoList.get(almoxarifadoList.size() - 1);
        assertThat(testAlmoxarifado.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAlmoxarifado.getDiasEstoque()).isEqualTo(UPDATED_DIAS_ESTOQUE);
        assertThat(testAlmoxarifado.getCentral()).isEqualTo(UPDATED_CENTRAL);
        assertThat(testAlmoxarifado.getAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testAlmoxarifado.getCalculaMediaPonderada()).isEqualTo(UPDATED_CALCULA_MEDIA_PONDERADA);
        assertThat(testAlmoxarifado.getBloqueiaEntradaTransferencia()).isEqualTo(UPDATED_BLOQUEIA_ENTRADA_TRANSFERENCIA);

        // Validate the Almoxarifado in Elasticsearch
        verify(mockAlmoxarifadoSearchRepository, times(1)).save(testAlmoxarifado);
    }

    @Test
    @Transactional
    public void updateNonExistingAlmoxarifado() throws Exception {
        int databaseSizeBeforeUpdate = almoxarifadoRepository.findAll().size();

        // Create the Almoxarifado
        AlmoxarifadoDTO almoxarifadoDTO = almoxarifadoMapper.toDto(almoxarifado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlmoxarifadoMockMvc.perform(put("/api/almoxarifados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(almoxarifadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Almoxarifado in the database
        List<Almoxarifado> almoxarifadoList = almoxarifadoRepository.findAll();
        assertThat(almoxarifadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Almoxarifado in Elasticsearch
        verify(mockAlmoxarifadoSearchRepository, times(0)).save(almoxarifado);
    }

    @Test
    @Transactional
    public void deleteAlmoxarifado() throws Exception {
        // Initialize the database
        almoxarifadoRepository.saveAndFlush(almoxarifado);

        int databaseSizeBeforeDelete = almoxarifadoRepository.findAll().size();

        // Delete the almoxarifado
        restAlmoxarifadoMockMvc.perform(delete("/api/almoxarifados/{id}", almoxarifado.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Almoxarifado> almoxarifadoList = almoxarifadoRepository.findAll();
        assertThat(almoxarifadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Almoxarifado in Elasticsearch
        verify(mockAlmoxarifadoSearchRepository, times(1)).deleteById(almoxarifado.getId());
    }

    @Test
    @Transactional
    public void searchAlmoxarifado() throws Exception {
        // Initialize the database
        almoxarifadoRepository.saveAndFlush(almoxarifado);
        when(mockAlmoxarifadoSearchRepository.search(queryStringQuery("id:" + almoxarifado.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(almoxarifado), PageRequest.of(0, 1), 1));
        // Search the almoxarifado
        restAlmoxarifadoMockMvc.perform(get("/api/_search/almoxarifados?query=id:" + almoxarifado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(almoxarifado.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].diasEstoque").value(hasItem(DEFAULT_DIAS_ESTOQUE)))
            .andExpect(jsonPath("$.[*].central").value(hasItem(DEFAULT_CENTRAL.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].calculaMediaPonderada").value(hasItem(DEFAULT_CALCULA_MEDIA_PONDERADA.booleanValue())))
            .andExpect(jsonPath("$.[*].bloqueiaEntradaTransferencia").value(hasItem(DEFAULT_BLOQUEIA_ENTRADA_TRANSFERENCIA.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Almoxarifado.class);
        Almoxarifado almoxarifado1 = new Almoxarifado();
        almoxarifado1.setId(1L);
        Almoxarifado almoxarifado2 = new Almoxarifado();
        almoxarifado2.setId(almoxarifado1.getId());
        assertThat(almoxarifado1).isEqualTo(almoxarifado2);
        almoxarifado2.setId(2L);
        assertThat(almoxarifado1).isNotEqualTo(almoxarifado2);
        almoxarifado1.setId(null);
        assertThat(almoxarifado1).isNotEqualTo(almoxarifado2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlmoxarifadoDTO.class);
        AlmoxarifadoDTO almoxarifadoDTO1 = new AlmoxarifadoDTO();
        almoxarifadoDTO1.setId(1L);
        AlmoxarifadoDTO almoxarifadoDTO2 = new AlmoxarifadoDTO();
        assertThat(almoxarifadoDTO1).isNotEqualTo(almoxarifadoDTO2);
        almoxarifadoDTO2.setId(almoxarifadoDTO1.getId());
        assertThat(almoxarifadoDTO1).isEqualTo(almoxarifadoDTO2);
        almoxarifadoDTO2.setId(2L);
        assertThat(almoxarifadoDTO1).isNotEqualTo(almoxarifadoDTO2);
        almoxarifadoDTO1.setId(null);
        assertThat(almoxarifadoDTO1).isNotEqualTo(almoxarifadoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(almoxarifadoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(almoxarifadoMapper.fromId(null)).isNull();
    }
}
