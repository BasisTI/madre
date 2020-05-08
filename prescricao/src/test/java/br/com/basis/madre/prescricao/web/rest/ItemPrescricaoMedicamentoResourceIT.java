package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import static br.com.basis.madre.prescricao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.prescricao.domain.enumeration.UnidadeTempo;
/**
 * Integration tests for the {@link ItemPrescricaoMedicamentoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class ItemPrescricaoMedicamentoResourceIT {

    private static final Long DEFAULT_ID_MEDICAMENTO = 1L;
    private static final Long UPDATED_ID_MEDICAMENTO = 2L;

    private static final BigDecimal DEFAULT_DOSE = new BigDecimal(0);
    private static final BigDecimal UPDATED_DOSE = new BigDecimal(1);

    private static final Integer DEFAULT_FREQUENCIA = 0;
    private static final Integer UPDATED_FREQUENCIA = 1;

    private static final Boolean DEFAULT_TODAS_VIAS = false;
    private static final Boolean UPDATED_TODAS_VIAS = true;

    private static final Boolean DEFAULT_BOMBA_INFUSAO = false;
    private static final Boolean UPDATED_BOMBA_INFUSAO = true;

    private static final BigDecimal DEFAULT_VELOCIDADE_INFUSAO = new BigDecimal(0);
    private static final BigDecimal UPDATED_VELOCIDADE_INFUSAO = new BigDecimal(1);

    private static final Integer DEFAULT_TEMPO_INFUSAO = 0;
    private static final Integer UPDATED_TEMPO_INFUSAO = 1;

    private static final UnidadeTempo DEFAULT_UNIDADE_TEMPO = UnidadeTempo.HORAS;
    private static final UnidadeTempo UPDATED_UNIDADE_TEMPO = UnidadeTempo.MINUTOS;

    private static final LocalDate DEFAULT_INICIO_ADMINISTRACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO_ADMINISTRACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_CONDICAO_NECESSARIA = false;
    private static final Boolean UPDATED_CONDICAO_NECESSARIA = true;

    private static final String DEFAULT_OBSERVACAO_CONDICAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_CONDICAO = "BBBBBBBBBB";

    @Autowired
    private ItemPrescricaoMedicamentoRepository itemPrescricaoMedicamentoRepository;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.ItemPrescricaoMedicamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemPrescricaoMedicamentoSearchRepository mockItemPrescricaoMedicamentoSearchRepository;

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

    private MockMvc restItemPrescricaoMedicamentoMockMvc;

    private ItemPrescricaoMedicamento itemPrescricaoMedicamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPrescricaoMedicamentoResource itemPrescricaoMedicamentoResource = new ItemPrescricaoMedicamentoResource(itemPrescricaoMedicamentoRepository, mockItemPrescricaoMedicamentoSearchRepository, null);
        this.restItemPrescricaoMedicamentoMockMvc = MockMvcBuilders.standaloneSetup(itemPrescricaoMedicamentoResource)
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
    public static ItemPrescricaoMedicamento createEntity(EntityManager em) {
        ItemPrescricaoMedicamento itemPrescricaoMedicamento = new ItemPrescricaoMedicamento()
            .idMedicamento(DEFAULT_ID_MEDICAMENTO)
            .dose(DEFAULT_DOSE)
            .frequencia(DEFAULT_FREQUENCIA)
            .todasVias(DEFAULT_TODAS_VIAS)
            .bombaInfusao(DEFAULT_BOMBA_INFUSAO)
            .velocidadeInfusao(DEFAULT_VELOCIDADE_INFUSAO)
            .tempoInfusao(DEFAULT_TEMPO_INFUSAO)
            .unidadeTempo(DEFAULT_UNIDADE_TEMPO)
            .inicioAdministracao(DEFAULT_INICIO_ADMINISTRACAO)
            .condicaoNecessaria(DEFAULT_CONDICAO_NECESSARIA)
            .observacaoCondicao(DEFAULT_OBSERVACAO_CONDICAO);
        return itemPrescricaoMedicamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPrescricaoMedicamento createUpdatedEntity(EntityManager em) {
        ItemPrescricaoMedicamento itemPrescricaoMedicamento = new ItemPrescricaoMedicamento()
            .idMedicamento(UPDATED_ID_MEDICAMENTO)
            .dose(UPDATED_DOSE)
            .frequencia(UPDATED_FREQUENCIA)
            .todasVias(UPDATED_TODAS_VIAS)
            .bombaInfusao(UPDATED_BOMBA_INFUSAO)
            .velocidadeInfusao(UPDATED_VELOCIDADE_INFUSAO)
            .tempoInfusao(UPDATED_TEMPO_INFUSAO)
            .unidadeTempo(UPDATED_UNIDADE_TEMPO)
            .inicioAdministracao(UPDATED_INICIO_ADMINISTRACAO)
            .condicaoNecessaria(UPDATED_CONDICAO_NECESSARIA)
            .observacaoCondicao(UPDATED_OBSERVACAO_CONDICAO);
        return itemPrescricaoMedicamento;
    }

    @BeforeEach
    public void initTest() {
        itemPrescricaoMedicamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPrescricaoMedicamento() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoMedicamentoRepository.findAll().size();

        // Create the ItemPrescricaoMedicamento
        restItemPrescricaoMedicamentoMockMvc.perform(post("/api/item-prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoMedicamento)))
            .andExpect(status().isCreated());

        // Validate the ItemPrescricaoMedicamento in the database
        List<ItemPrescricaoMedicamento> itemPrescricaoMedicamentoList = itemPrescricaoMedicamentoRepository.findAll();
        assertThat(itemPrescricaoMedicamentoList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPrescricaoMedicamento testItemPrescricaoMedicamento = itemPrescricaoMedicamentoList.get(itemPrescricaoMedicamentoList.size() - 1);
        assertThat(testItemPrescricaoMedicamento.getIdMedicamento()).isEqualTo(DEFAULT_ID_MEDICAMENTO);
        assertThat(testItemPrescricaoMedicamento.getDose()).isEqualTo(DEFAULT_DOSE);
        assertThat(testItemPrescricaoMedicamento.getFrequencia()).isEqualTo(DEFAULT_FREQUENCIA);
        assertThat(testItemPrescricaoMedicamento.isTodasVias()).isEqualTo(DEFAULT_TODAS_VIAS);
        assertThat(testItemPrescricaoMedicamento.isBombaInfusao()).isEqualTo(DEFAULT_BOMBA_INFUSAO);
        assertThat(testItemPrescricaoMedicamento.getVelocidadeInfusao()).isEqualTo(DEFAULT_VELOCIDADE_INFUSAO);
        assertThat(testItemPrescricaoMedicamento.getTempoInfusao()).isEqualTo(DEFAULT_TEMPO_INFUSAO);
        assertThat(testItemPrescricaoMedicamento.getUnidadeTempo()).isEqualTo(DEFAULT_UNIDADE_TEMPO);
        assertThat(testItemPrescricaoMedicamento.getInicioAdministracao()).isEqualTo(DEFAULT_INICIO_ADMINISTRACAO);
        assertThat(testItemPrescricaoMedicamento.isCondicaoNecessaria()).isEqualTo(DEFAULT_CONDICAO_NECESSARIA);
        assertThat(testItemPrescricaoMedicamento.getObservacaoCondicao()).isEqualTo(DEFAULT_OBSERVACAO_CONDICAO);

        // Validate the ItemPrescricaoMedicamento in Elasticsearch
        verify(mockItemPrescricaoMedicamentoSearchRepository, times(1)).save(testItemPrescricaoMedicamento);
    }

    @Test
    @Transactional
    public void createItemPrescricaoMedicamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoMedicamentoRepository.findAll().size();

        // Create the ItemPrescricaoMedicamento with an existing ID
        itemPrescricaoMedicamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPrescricaoMedicamentoMockMvc.perform(post("/api/item-prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoMedicamento)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoMedicamento in the database
        List<ItemPrescricaoMedicamento> itemPrescricaoMedicamentoList = itemPrescricaoMedicamentoRepository.findAll();
        assertThat(itemPrescricaoMedicamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemPrescricaoMedicamento in Elasticsearch
        verify(mockItemPrescricaoMedicamentoSearchRepository, times(0)).save(itemPrescricaoMedicamento);
    }


    @Test
    @Transactional
    public void checkIdMedicamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPrescricaoMedicamentoRepository.findAll().size();
        // set the field null
        itemPrescricaoMedicamento.setIdMedicamento(null);

        // Create the ItemPrescricaoMedicamento, which fails.

        restItemPrescricaoMedicamentoMockMvc.perform(post("/api/item-prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoMedicamento)))
            .andExpect(status().isBadRequest());

        List<ItemPrescricaoMedicamento> itemPrescricaoMedicamentoList = itemPrescricaoMedicamentoRepository.findAll();
        assertThat(itemPrescricaoMedicamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDoseIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPrescricaoMedicamentoRepository.findAll().size();
        // set the field null
        itemPrescricaoMedicamento.setDose(null);

        // Create the ItemPrescricaoMedicamento, which fails.

        restItemPrescricaoMedicamentoMockMvc.perform(post("/api/item-prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoMedicamento)))
            .andExpect(status().isBadRequest());

        List<ItemPrescricaoMedicamento> itemPrescricaoMedicamentoList = itemPrescricaoMedicamentoRepository.findAll();
        assertThat(itemPrescricaoMedicamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemPrescricaoMedicamentos() throws Exception {
        // Initialize the database
        itemPrescricaoMedicamentoRepository.saveAndFlush(itemPrescricaoMedicamento);

        // Get all the itemPrescricaoMedicamentoList
        restItemPrescricaoMedicamentoMockMvc.perform(get("/api/item-prescricao-medicamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoMedicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMedicamento").value(hasItem(DEFAULT_ID_MEDICAMENTO.intValue())))
            .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE.intValue())))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA)))
            .andExpect(jsonPath("$.[*].todasVias").value(hasItem(DEFAULT_TODAS_VIAS.booleanValue())))
            .andExpect(jsonPath("$.[*].bombaInfusao").value(hasItem(DEFAULT_BOMBA_INFUSAO.booleanValue())))
            .andExpect(jsonPath("$.[*].velocidadeInfusao").value(hasItem(DEFAULT_VELOCIDADE_INFUSAO.intValue())))
            .andExpect(jsonPath("$.[*].tempoInfusao").value(hasItem(DEFAULT_TEMPO_INFUSAO)))
            .andExpect(jsonPath("$.[*].unidadeTempo").value(hasItem(DEFAULT_UNIDADE_TEMPO.toString())))
            .andExpect(jsonPath("$.[*].inicioAdministracao").value(hasItem(DEFAULT_INICIO_ADMINISTRACAO.toString())))
            .andExpect(jsonPath("$.[*].condicaoNecessaria").value(hasItem(DEFAULT_CONDICAO_NECESSARIA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacaoCondicao").value(hasItem(DEFAULT_OBSERVACAO_CONDICAO)));
    }
    
    @Test
    @Transactional
    public void getItemPrescricaoMedicamento() throws Exception {
        // Initialize the database
        itemPrescricaoMedicamentoRepository.saveAndFlush(itemPrescricaoMedicamento);

        // Get the itemPrescricaoMedicamento
        restItemPrescricaoMedicamentoMockMvc.perform(get("/api/item-prescricao-medicamentos/{id}", itemPrescricaoMedicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPrescricaoMedicamento.getId().intValue()))
            .andExpect(jsonPath("$.idMedicamento").value(DEFAULT_ID_MEDICAMENTO.intValue()))
            .andExpect(jsonPath("$.dose").value(DEFAULT_DOSE.intValue()))
            .andExpect(jsonPath("$.frequencia").value(DEFAULT_FREQUENCIA))
            .andExpect(jsonPath("$.todasVias").value(DEFAULT_TODAS_VIAS.booleanValue()))
            .andExpect(jsonPath("$.bombaInfusao").value(DEFAULT_BOMBA_INFUSAO.booleanValue()))
            .andExpect(jsonPath("$.velocidadeInfusao").value(DEFAULT_VELOCIDADE_INFUSAO.intValue()))
            .andExpect(jsonPath("$.tempoInfusao").value(DEFAULT_TEMPO_INFUSAO))
            .andExpect(jsonPath("$.unidadeTempo").value(DEFAULT_UNIDADE_TEMPO.toString()))
            .andExpect(jsonPath("$.inicioAdministracao").value(DEFAULT_INICIO_ADMINISTRACAO.toString()))
            .andExpect(jsonPath("$.condicaoNecessaria").value(DEFAULT_CONDICAO_NECESSARIA.booleanValue()))
            .andExpect(jsonPath("$.observacaoCondicao").value(DEFAULT_OBSERVACAO_CONDICAO));
    }

    @Test
    @Transactional
    public void getNonExistingItemPrescricaoMedicamento() throws Exception {
        // Get the itemPrescricaoMedicamento
        restItemPrescricaoMedicamentoMockMvc.perform(get("/api/item-prescricao-medicamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPrescricaoMedicamento() throws Exception {
        // Initialize the database
        itemPrescricaoMedicamentoRepository.saveAndFlush(itemPrescricaoMedicamento);

        int databaseSizeBeforeUpdate = itemPrescricaoMedicamentoRepository.findAll().size();

        // Update the itemPrescricaoMedicamento
        ItemPrescricaoMedicamento updatedItemPrescricaoMedicamento = itemPrescricaoMedicamentoRepository.findById(itemPrescricaoMedicamento.getId()).get();
        // Disconnect from session so that the updates on updatedItemPrescricaoMedicamento are not directly saved in db
        em.detach(updatedItemPrescricaoMedicamento);
        updatedItemPrescricaoMedicamento
            .idMedicamento(UPDATED_ID_MEDICAMENTO)
            .dose(UPDATED_DOSE)
            .frequencia(UPDATED_FREQUENCIA)
            .todasVias(UPDATED_TODAS_VIAS)
            .bombaInfusao(UPDATED_BOMBA_INFUSAO)
            .velocidadeInfusao(UPDATED_VELOCIDADE_INFUSAO)
            .tempoInfusao(UPDATED_TEMPO_INFUSAO)
            .unidadeTempo(UPDATED_UNIDADE_TEMPO)
            .inicioAdministracao(UPDATED_INICIO_ADMINISTRACAO)
            .condicaoNecessaria(UPDATED_CONDICAO_NECESSARIA)
            .observacaoCondicao(UPDATED_OBSERVACAO_CONDICAO);

        restItemPrescricaoMedicamentoMockMvc.perform(put("/api/item-prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemPrescricaoMedicamento)))
            .andExpect(status().isOk());

        // Validate the ItemPrescricaoMedicamento in the database
        List<ItemPrescricaoMedicamento> itemPrescricaoMedicamentoList = itemPrescricaoMedicamentoRepository.findAll();
        assertThat(itemPrescricaoMedicamentoList).hasSize(databaseSizeBeforeUpdate);
        ItemPrescricaoMedicamento testItemPrescricaoMedicamento = itemPrescricaoMedicamentoList.get(itemPrescricaoMedicamentoList.size() - 1);
        assertThat(testItemPrescricaoMedicamento.getIdMedicamento()).isEqualTo(UPDATED_ID_MEDICAMENTO);
        assertThat(testItemPrescricaoMedicamento.getDose()).isEqualTo(UPDATED_DOSE);
        assertThat(testItemPrescricaoMedicamento.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
        assertThat(testItemPrescricaoMedicamento.isTodasVias()).isEqualTo(UPDATED_TODAS_VIAS);
        assertThat(testItemPrescricaoMedicamento.isBombaInfusao()).isEqualTo(UPDATED_BOMBA_INFUSAO);
        assertThat(testItemPrescricaoMedicamento.getVelocidadeInfusao()).isEqualTo(UPDATED_VELOCIDADE_INFUSAO);
        assertThat(testItemPrescricaoMedicamento.getTempoInfusao()).isEqualTo(UPDATED_TEMPO_INFUSAO);
        assertThat(testItemPrescricaoMedicamento.getUnidadeTempo()).isEqualTo(UPDATED_UNIDADE_TEMPO);
        assertThat(testItemPrescricaoMedicamento.getInicioAdministracao()).isEqualTo(UPDATED_INICIO_ADMINISTRACAO);
        assertThat(testItemPrescricaoMedicamento.isCondicaoNecessaria()).isEqualTo(UPDATED_CONDICAO_NECESSARIA);
        assertThat(testItemPrescricaoMedicamento.getObservacaoCondicao()).isEqualTo(UPDATED_OBSERVACAO_CONDICAO);

        // Validate the ItemPrescricaoMedicamento in Elasticsearch
        verify(mockItemPrescricaoMedicamentoSearchRepository, times(1)).save(testItemPrescricaoMedicamento);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPrescricaoMedicamento() throws Exception {
        int databaseSizeBeforeUpdate = itemPrescricaoMedicamentoRepository.findAll().size();

        // Create the ItemPrescricaoMedicamento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPrescricaoMedicamentoMockMvc.perform(put("/api/item-prescricao-medicamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoMedicamento)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoMedicamento in the database
        List<ItemPrescricaoMedicamento> itemPrescricaoMedicamentoList = itemPrescricaoMedicamentoRepository.findAll();
        assertThat(itemPrescricaoMedicamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemPrescricaoMedicamento in Elasticsearch
        verify(mockItemPrescricaoMedicamentoSearchRepository, times(0)).save(itemPrescricaoMedicamento);
    }

    @Test
    @Transactional
    public void deleteItemPrescricaoMedicamento() throws Exception {
        // Initialize the database
        itemPrescricaoMedicamentoRepository.saveAndFlush(itemPrescricaoMedicamento);

        int databaseSizeBeforeDelete = itemPrescricaoMedicamentoRepository.findAll().size();

        // Delete the itemPrescricaoMedicamento
        restItemPrescricaoMedicamentoMockMvc.perform(delete("/api/item-prescricao-medicamentos/{id}", itemPrescricaoMedicamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemPrescricaoMedicamento> itemPrescricaoMedicamentoList = itemPrescricaoMedicamentoRepository.findAll();
        assertThat(itemPrescricaoMedicamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemPrescricaoMedicamento in Elasticsearch
        verify(mockItemPrescricaoMedicamentoSearchRepository, times(1)).deleteById(itemPrescricaoMedicamento.getId());
    }

    @Test
    @Transactional
    public void searchItemPrescricaoMedicamento() throws Exception {
        // Initialize the database
        itemPrescricaoMedicamentoRepository.saveAndFlush(itemPrescricaoMedicamento);
        when(mockItemPrescricaoMedicamentoSearchRepository.search(queryStringQuery("id:" + itemPrescricaoMedicamento.getId())))
            .thenReturn(Collections.singletonList(itemPrescricaoMedicamento));
        // Search the itemPrescricaoMedicamento
        restItemPrescricaoMedicamentoMockMvc.perform(get("/api/_search/item-prescricao-medicamentos?query=id:" + itemPrescricaoMedicamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoMedicamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMedicamento").value(hasItem(DEFAULT_ID_MEDICAMENTO.intValue())))
            .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE.intValue())))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA)))
            .andExpect(jsonPath("$.[*].todasVias").value(hasItem(DEFAULT_TODAS_VIAS.booleanValue())))
            .andExpect(jsonPath("$.[*].bombaInfusao").value(hasItem(DEFAULT_BOMBA_INFUSAO.booleanValue())))
            .andExpect(jsonPath("$.[*].velocidadeInfusao").value(hasItem(DEFAULT_VELOCIDADE_INFUSAO.intValue())))
            .andExpect(jsonPath("$.[*].tempoInfusao").value(hasItem(DEFAULT_TEMPO_INFUSAO)))
            .andExpect(jsonPath("$.[*].unidadeTempo").value(hasItem(DEFAULT_UNIDADE_TEMPO.toString())))
            .andExpect(jsonPath("$.[*].inicioAdministracao").value(hasItem(DEFAULT_INICIO_ADMINISTRACAO.toString())))
            .andExpect(jsonPath("$.[*].condicaoNecessaria").value(hasItem(DEFAULT_CONDICAO_NECESSARIA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacaoCondicao").value(hasItem(DEFAULT_OBSERVACAO_CONDICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoMedicamento.class);
        ItemPrescricaoMedicamento itemPrescricaoMedicamento1 = new ItemPrescricaoMedicamento();
        itemPrescricaoMedicamento1.setId(1L);
        ItemPrescricaoMedicamento itemPrescricaoMedicamento2 = new ItemPrescricaoMedicamento();
        itemPrescricaoMedicamento2.setId(itemPrescricaoMedicamento1.getId());
        assertThat(itemPrescricaoMedicamento1).isEqualTo(itemPrescricaoMedicamento2);
        itemPrescricaoMedicamento2.setId(2L);
        assertThat(itemPrescricaoMedicamento1).isNotEqualTo(itemPrescricaoMedicamento2);
        itemPrescricaoMedicamento1.setId(null);
        assertThat(itemPrescricaoMedicamento1).isNotEqualTo(itemPrescricaoMedicamento2);
    }
}
