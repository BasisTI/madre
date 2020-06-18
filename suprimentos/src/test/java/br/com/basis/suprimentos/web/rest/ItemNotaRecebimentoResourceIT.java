package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.ItemNotaRecebimento;
import br.com.basis.suprimentos.domain.MarcaComercial;
import br.com.basis.suprimentos.domain.Material;
import br.com.basis.suprimentos.domain.UnidadeMedida;
import br.com.basis.suprimentos.repository.ItemNotaRecebimentoRepository;
import br.com.basis.suprimentos.repository.search.ItemNotaRecebimentoSearchRepository;
import br.com.basis.suprimentos.service.ItemNotaRecebimentoService;
import br.com.basis.suprimentos.service.dto.ItemNotaRecebimentoDTO;
import br.com.basis.suprimentos.service.mapper.ItemNotaRecebimentoMapper;
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
 * Integration tests for the {@link ItemNotaRecebimentoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class ItemNotaRecebimentoResourceIT {

    private static final Long DEFAULT_QUANTIDADE_RECEBER = 0L;
    private static final Long UPDATED_QUANTIDADE_RECEBER = 1L;

    private static final String DEFAULT_QUANTIDADE_CONVERTIDA = "AAAAAAAAAA";
    private static final String UPDATED_QUANTIDADE_CONVERTIDA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(1);

    @Autowired
    private ItemNotaRecebimentoRepository itemNotaRecebimentoRepository;

    @Autowired
    private ItemNotaRecebimentoMapper itemNotaRecebimentoMapper;

    @Autowired
    private ItemNotaRecebimentoService itemNotaRecebimentoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.ItemNotaRecebimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemNotaRecebimentoSearchRepository mockItemNotaRecebimentoSearchRepository;

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

    private MockMvc restItemNotaRecebimentoMockMvc;

    private ItemNotaRecebimento itemNotaRecebimento;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemNotaRecebimento createEntity(EntityManager em) {
        ItemNotaRecebimento itemNotaRecebimento = new ItemNotaRecebimento()
                .quantidadeReceber(DEFAULT_QUANTIDADE_RECEBER)
                .quantidadeConvertida(DEFAULT_QUANTIDADE_CONVERTIDA)
                .valorTotal(DEFAULT_VALOR_TOTAL);
        // Add required entity
        MarcaComercial marcaComercial;
        if (TestUtil.findAll(em, MarcaComercial.class).isEmpty()) {
            marcaComercial = MarcaComercialResourceIT.createEntity(em);
            em.persist(marcaComercial);
            em.flush();
        } else {
            marcaComercial = TestUtil.findAll(em, MarcaComercial.class).get(0);
        }
        itemNotaRecebimento.setMarcaComercial(marcaComercial);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        itemNotaRecebimento.setMaterial(material);
        // Add required entity
        UnidadeMedida unidadeMedida;
        if (TestUtil.findAll(em, UnidadeMedida.class).isEmpty()) {
            unidadeMedida = UnidadeMedidaResourceIT.createEntity(em);
            em.persist(unidadeMedida);
            em.flush();
        } else {
            unidadeMedida = TestUtil.findAll(em, UnidadeMedida.class).get(0);
        }
        itemNotaRecebimento.setUnidadeMedida(unidadeMedida);
        return itemNotaRecebimento;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemNotaRecebimento createUpdatedEntity(EntityManager em) {
        ItemNotaRecebimento itemNotaRecebimento = new ItemNotaRecebimento()
                .quantidadeReceber(UPDATED_QUANTIDADE_RECEBER)
                .quantidadeConvertida(UPDATED_QUANTIDADE_CONVERTIDA)
                .valorTotal(UPDATED_VALOR_TOTAL);
        // Add required entity
        MarcaComercial marcaComercial;
        if (TestUtil.findAll(em, MarcaComercial.class).isEmpty()) {
            marcaComercial = MarcaComercialResourceIT.createUpdatedEntity(em);
            em.persist(marcaComercial);
            em.flush();
        } else {
            marcaComercial = TestUtil.findAll(em, MarcaComercial.class).get(0);
        }
        itemNotaRecebimento.setMarcaComercial(marcaComercial);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        itemNotaRecebimento.setMaterial(material);
        // Add required entity
        UnidadeMedida unidadeMedida;
        if (TestUtil.findAll(em, UnidadeMedida.class).isEmpty()) {
            unidadeMedida = UnidadeMedidaResourceIT.createUpdatedEntity(em);
            em.persist(unidadeMedida);
            em.flush();
        } else {
            unidadeMedida = TestUtil.findAll(em, UnidadeMedida.class).get(0);
        }
        itemNotaRecebimento.setUnidadeMedida(unidadeMedida);
        return itemNotaRecebimento;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemNotaRecebimentoResource itemNotaRecebimentoResource = new ItemNotaRecebimentoResource(itemNotaRecebimentoService);
        this.restItemNotaRecebimentoMockMvc = MockMvcBuilders.standaloneSetup(itemNotaRecebimentoResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        itemNotaRecebimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemNotaRecebimento() throws Exception {
        int databaseSizeBeforeCreate = itemNotaRecebimentoRepository.findAll().size();

        // Create the ItemNotaRecebimento
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO = itemNotaRecebimentoMapper.toDto(itemNotaRecebimento);
        restItemNotaRecebimentoMockMvc.perform(post("/api/item-nota-recebimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(itemNotaRecebimentoDTO)))
                .andExpect(status().isCreated());

        // Validate the ItemNotaRecebimento in the database
        List<ItemNotaRecebimento> itemNotaRecebimentoList = itemNotaRecebimentoRepository.findAll();
        assertThat(itemNotaRecebimentoList).hasSize(databaseSizeBeforeCreate + 1);
        ItemNotaRecebimento testItemNotaRecebimento = itemNotaRecebimentoList.get(itemNotaRecebimentoList.size() - 1);
        assertThat(testItemNotaRecebimento.getQuantidadeReceber()).isEqualTo(DEFAULT_QUANTIDADE_RECEBER);
        assertThat(testItemNotaRecebimento.getQuantidadeConvertida()).isEqualTo(DEFAULT_QUANTIDADE_CONVERTIDA);
        assertThat(testItemNotaRecebimento.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);

        // Validate the ItemNotaRecebimento in Elasticsearch
        verify(mockItemNotaRecebimentoSearchRepository, times(1)).save(testItemNotaRecebimento);
    }

    @Test
    @Transactional
    public void createItemNotaRecebimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemNotaRecebimentoRepository.findAll().size();

        // Create the ItemNotaRecebimento with an existing ID
        itemNotaRecebimento.setId(1L);
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO = itemNotaRecebimentoMapper.toDto(itemNotaRecebimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemNotaRecebimentoMockMvc.perform(post("/api/item-nota-recebimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(itemNotaRecebimentoDTO)))
                .andExpect(status().isBadRequest());

        // Validate the ItemNotaRecebimento in the database
        List<ItemNotaRecebimento> itemNotaRecebimentoList = itemNotaRecebimentoRepository.findAll();
        assertThat(itemNotaRecebimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemNotaRecebimento in Elasticsearch
        verify(mockItemNotaRecebimentoSearchRepository, times(0)).save(itemNotaRecebimento);
    }


    @Test
    @Transactional
    public void checkQuantidadeReceberIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemNotaRecebimentoRepository.findAll().size();
        // set the field null
        itemNotaRecebimento.setQuantidadeReceber(null);

        // Create the ItemNotaRecebimento, which fails.
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO = itemNotaRecebimentoMapper.toDto(itemNotaRecebimento);

        restItemNotaRecebimentoMockMvc.perform(post("/api/item-nota-recebimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(itemNotaRecebimentoDTO)))
                .andExpect(status().isBadRequest());

        List<ItemNotaRecebimento> itemNotaRecebimentoList = itemNotaRecebimentoRepository.findAll();
        assertThat(itemNotaRecebimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeConvertidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemNotaRecebimentoRepository.findAll().size();
        // set the field null
        itemNotaRecebimento.setQuantidadeConvertida(null);

        // Create the ItemNotaRecebimento, which fails.
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO = itemNotaRecebimentoMapper.toDto(itemNotaRecebimento);

        restItemNotaRecebimentoMockMvc.perform(post("/api/item-nota-recebimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(itemNotaRecebimentoDTO)))
                .andExpect(status().isBadRequest());

        List<ItemNotaRecebimento> itemNotaRecebimentoList = itemNotaRecebimentoRepository.findAll();
        assertThat(itemNotaRecebimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemNotaRecebimentoRepository.findAll().size();
        // set the field null
        itemNotaRecebimento.setValorTotal(null);

        // Create the ItemNotaRecebimento, which fails.
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO = itemNotaRecebimentoMapper.toDto(itemNotaRecebimento);

        restItemNotaRecebimentoMockMvc.perform(post("/api/item-nota-recebimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(itemNotaRecebimentoDTO)))
                .andExpect(status().isBadRequest());

        List<ItemNotaRecebimento> itemNotaRecebimentoList = itemNotaRecebimentoRepository.findAll();
        assertThat(itemNotaRecebimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemNotaRecebimentos() throws Exception {
        // Initialize the database
        itemNotaRecebimentoRepository.saveAndFlush(itemNotaRecebimento);

        // Get all the itemNotaRecebimentoList
        restItemNotaRecebimentoMockMvc.perform(get("/api/item-nota-recebimentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemNotaRecebimento.getId().intValue())))
                .andExpect(jsonPath("$.[*].quantidadeReceber").value(hasItem(DEFAULT_QUANTIDADE_RECEBER.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeConvertida").value(hasItem(DEFAULT_QUANTIDADE_CONVERTIDA)))
                .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getItemNotaRecebimento() throws Exception {
        // Initialize the database
        itemNotaRecebimentoRepository.saveAndFlush(itemNotaRecebimento);

        // Get the itemNotaRecebimento
        restItemNotaRecebimentoMockMvc.perform(get("/api/item-nota-recebimentos/{id}", itemNotaRecebimento.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(itemNotaRecebimento.getId().intValue()))
                .andExpect(jsonPath("$.quantidadeReceber").value(DEFAULT_QUANTIDADE_RECEBER.intValue()))
                .andExpect(jsonPath("$.quantidadeConvertida").value(DEFAULT_QUANTIDADE_CONVERTIDA))
                .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemNotaRecebimento() throws Exception {
        // Get the itemNotaRecebimento
        restItemNotaRecebimentoMockMvc.perform(get("/api/item-nota-recebimentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemNotaRecebimento() throws Exception {
        // Initialize the database
        itemNotaRecebimentoRepository.saveAndFlush(itemNotaRecebimento);

        int databaseSizeBeforeUpdate = itemNotaRecebimentoRepository.findAll().size();

        // Update the itemNotaRecebimento
        ItemNotaRecebimento updatedItemNotaRecebimento = itemNotaRecebimentoRepository.findById(itemNotaRecebimento.getId()).get();
        // Disconnect from session so that the updates on updatedItemNotaRecebimento are not directly saved in db
        em.detach(updatedItemNotaRecebimento);
        updatedItemNotaRecebimento
                .quantidadeReceber(UPDATED_QUANTIDADE_RECEBER)
                .quantidadeConvertida(UPDATED_QUANTIDADE_CONVERTIDA)
                .valorTotal(UPDATED_VALOR_TOTAL);
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO = itemNotaRecebimentoMapper.toDto(updatedItemNotaRecebimento);

        restItemNotaRecebimentoMockMvc.perform(put("/api/item-nota-recebimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(itemNotaRecebimentoDTO)))
                .andExpect(status().isOk());

        // Validate the ItemNotaRecebimento in the database
        List<ItemNotaRecebimento> itemNotaRecebimentoList = itemNotaRecebimentoRepository.findAll();
        assertThat(itemNotaRecebimentoList).hasSize(databaseSizeBeforeUpdate);
        ItemNotaRecebimento testItemNotaRecebimento = itemNotaRecebimentoList.get(itemNotaRecebimentoList.size() - 1);
        assertThat(testItemNotaRecebimento.getQuantidadeReceber()).isEqualTo(UPDATED_QUANTIDADE_RECEBER);
        assertThat(testItemNotaRecebimento.getQuantidadeConvertida()).isEqualTo(UPDATED_QUANTIDADE_CONVERTIDA);
        assertThat(testItemNotaRecebimento.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);

        // Validate the ItemNotaRecebimento in Elasticsearch
        verify(mockItemNotaRecebimentoSearchRepository, times(1)).save(testItemNotaRecebimento);
    }

    @Test
    @Transactional
    public void updateNonExistingItemNotaRecebimento() throws Exception {
        int databaseSizeBeforeUpdate = itemNotaRecebimentoRepository.findAll().size();

        // Create the ItemNotaRecebimento
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO = itemNotaRecebimentoMapper.toDto(itemNotaRecebimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemNotaRecebimentoMockMvc.perform(put("/api/item-nota-recebimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(itemNotaRecebimentoDTO)))
                .andExpect(status().isBadRequest());

        // Validate the ItemNotaRecebimento in the database
        List<ItemNotaRecebimento> itemNotaRecebimentoList = itemNotaRecebimentoRepository.findAll();
        assertThat(itemNotaRecebimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemNotaRecebimento in Elasticsearch
        verify(mockItemNotaRecebimentoSearchRepository, times(0)).save(itemNotaRecebimento);
    }

    @Test
    @Transactional
    public void deleteItemNotaRecebimento() throws Exception {
        // Initialize the database
        itemNotaRecebimentoRepository.saveAndFlush(itemNotaRecebimento);

        int databaseSizeBeforeDelete = itemNotaRecebimentoRepository.findAll().size();

        // Delete the itemNotaRecebimento
        restItemNotaRecebimentoMockMvc.perform(delete("/api/item-nota-recebimentos/{id}", itemNotaRecebimento.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemNotaRecebimento> itemNotaRecebimentoList = itemNotaRecebimentoRepository.findAll();
        assertThat(itemNotaRecebimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemNotaRecebimento in Elasticsearch
        verify(mockItemNotaRecebimentoSearchRepository, times(1)).deleteById(itemNotaRecebimento.getId());
    }

    @Test
    @Transactional
    public void searchItemNotaRecebimento() throws Exception {
        // Initialize the database
        itemNotaRecebimentoRepository.saveAndFlush(itemNotaRecebimento);
        when(mockItemNotaRecebimentoSearchRepository.search(queryStringQuery("id:" + itemNotaRecebimento.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(itemNotaRecebimento), PageRequest.of(0, 1), 1));
        // Search the itemNotaRecebimento
        restItemNotaRecebimentoMockMvc.perform(get("/api/_search/item-nota-recebimentos?query=id:" + itemNotaRecebimento.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemNotaRecebimento.getId().intValue())))
                .andExpect(jsonPath("$.[*].quantidadeReceber").value(hasItem(DEFAULT_QUANTIDADE_RECEBER.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeConvertida").value(hasItem(DEFAULT_QUANTIDADE_CONVERTIDA)))
                .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemNotaRecebimento.class);
        ItemNotaRecebimento itemNotaRecebimento1 = new ItemNotaRecebimento();
        itemNotaRecebimento1.setId(1L);
        ItemNotaRecebimento itemNotaRecebimento2 = new ItemNotaRecebimento();
        itemNotaRecebimento2.setId(itemNotaRecebimento1.getId());
        assertThat(itemNotaRecebimento1).isEqualTo(itemNotaRecebimento2);
        itemNotaRecebimento2.setId(2L);
        assertThat(itemNotaRecebimento1).isNotEqualTo(itemNotaRecebimento2);
        itemNotaRecebimento1.setId(null);
        assertThat(itemNotaRecebimento1).isNotEqualTo(itemNotaRecebimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemNotaRecebimentoDTO.class);
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO1 = new ItemNotaRecebimentoDTO();
        itemNotaRecebimentoDTO1.setId(1L);
        ItemNotaRecebimentoDTO itemNotaRecebimentoDTO2 = new ItemNotaRecebimentoDTO();
        assertThat(itemNotaRecebimentoDTO1).isNotEqualTo(itemNotaRecebimentoDTO2);
        itemNotaRecebimentoDTO2.setId(itemNotaRecebimentoDTO1.getId());
        assertThat(itemNotaRecebimentoDTO1).isEqualTo(itemNotaRecebimentoDTO2);
        itemNotaRecebimentoDTO2.setId(2L);
        assertThat(itemNotaRecebimentoDTO1).isNotEqualTo(itemNotaRecebimentoDTO2);
        itemNotaRecebimentoDTO1.setId(null);
        assertThat(itemNotaRecebimentoDTO1).isNotEqualTo(itemNotaRecebimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemNotaRecebimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemNotaRecebimentoMapper.fromId(null)).isNull();
    }
}
