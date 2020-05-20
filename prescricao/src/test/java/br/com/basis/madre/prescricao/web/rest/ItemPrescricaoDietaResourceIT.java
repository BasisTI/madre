package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoDietaRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoDietaSearchRepository;
import br.com.basis.madre.prescricao.service.ItemPrescricaoDietaService;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDietaDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoDietaMapper;
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
import java.math.BigDecimal;
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
 * Integration tests for the {@link ItemPrescricaoDietaResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class ItemPrescricaoDietaResourceIT {

    private static final BigDecimal DEFAULT_QUANTIDADE = new BigDecimal(0);
    private static final BigDecimal UPDATED_QUANTIDADE = new BigDecimal(1);

    private static final Integer DEFAULT_FREQUENCIA = 0;
    private static final Integer UPDATED_FREQUENCIA = 1;

    private static final Integer DEFAULT_NUMERO_VEZES = 1;
    private static final Integer UPDATED_NUMERO_VEZES = 2;

    @Autowired
    private ItemPrescricaoDietaRepository itemPrescricaoDietaRepository;

    @Autowired
    private ItemPrescricaoDietaMapper itemPrescricaoDietaMapper;

    @Autowired
    private ItemPrescricaoDietaService itemPrescricaoDietaService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.ItemPrescricaoDietaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemPrescricaoDietaSearchRepository mockItemPrescricaoDietaSearchRepository;

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

    private MockMvc restItemPrescricaoDietaMockMvc;

    private ItemPrescricaoDieta itemPrescricaoDieta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPrescricaoDietaResource itemPrescricaoDietaResource = new ItemPrescricaoDietaResource(itemPrescricaoDietaService);
        this.restItemPrescricaoDietaMockMvc = MockMvcBuilders.standaloneSetup(itemPrescricaoDietaResource)
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
    public static ItemPrescricaoDieta createEntity(EntityManager em) {
        ItemPrescricaoDieta itemPrescricaoDieta = new ItemPrescricaoDieta()
            .quantidade(DEFAULT_QUANTIDADE)
            .frequencia(DEFAULT_FREQUENCIA)
            .numeroVezes(DEFAULT_NUMERO_VEZES);
        return itemPrescricaoDieta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPrescricaoDieta createUpdatedEntity(EntityManager em) {
        ItemPrescricaoDieta itemPrescricaoDieta = new ItemPrescricaoDieta()
            .quantidade(UPDATED_QUANTIDADE)
            .frequencia(UPDATED_FREQUENCIA)
            .numeroVezes(UPDATED_NUMERO_VEZES);
        return itemPrescricaoDieta;
    }

    @BeforeEach
    public void initTest() {
        itemPrescricaoDieta = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPrescricaoDieta() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoDietaRepository.findAll().size();

        // Create the ItemPrescricaoDieta
        ItemPrescricaoDietaDTO itemPrescricaoDietaDTO = itemPrescricaoDietaMapper.toDto(itemPrescricaoDieta);
        restItemPrescricaoDietaMockMvc.perform(post("/api/item-prescricao-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDietaDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPrescricaoDieta in the database
        List<ItemPrescricaoDieta> itemPrescricaoDietaList = itemPrescricaoDietaRepository.findAll();
        assertThat(itemPrescricaoDietaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPrescricaoDieta testItemPrescricaoDieta = itemPrescricaoDietaList.get(itemPrescricaoDietaList.size() - 1);
        assertThat(testItemPrescricaoDieta.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testItemPrescricaoDieta.getFrequencia()).isEqualTo(DEFAULT_FREQUENCIA);
        assertThat(testItemPrescricaoDieta.getNumeroVezes()).isEqualTo(DEFAULT_NUMERO_VEZES);

        // Validate the ItemPrescricaoDieta in Elasticsearch
        verify(mockItemPrescricaoDietaSearchRepository, times(1)).save(testItemPrescricaoDieta);
    }

    @Test
    @Transactional
    public void createItemPrescricaoDietaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoDietaRepository.findAll().size();

        // Create the ItemPrescricaoDieta with an existing ID
        itemPrescricaoDieta.setId(1L);
        ItemPrescricaoDietaDTO itemPrescricaoDietaDTO = itemPrescricaoDietaMapper.toDto(itemPrescricaoDieta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPrescricaoDietaMockMvc.perform(post("/api/item-prescricao-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDietaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoDieta in the database
        List<ItemPrescricaoDieta> itemPrescricaoDietaList = itemPrescricaoDietaRepository.findAll();
        assertThat(itemPrescricaoDietaList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemPrescricaoDieta in Elasticsearch
        verify(mockItemPrescricaoDietaSearchRepository, times(0)).save(itemPrescricaoDieta);
    }


    @Test
    @Transactional
    public void getAllItemPrescricaoDietas() throws Exception {
        // Initialize the database
        itemPrescricaoDietaRepository.saveAndFlush(itemPrescricaoDieta);

        // Get all the itemPrescricaoDietaList
        restItemPrescricaoDietaMockMvc.perform(get("/api/item-prescricao-dietas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoDieta.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA)))
            .andExpect(jsonPath("$.[*].numeroVezes").value(hasItem(DEFAULT_NUMERO_VEZES)));
    }
    
    @Test
    @Transactional
    public void getItemPrescricaoDieta() throws Exception {
        // Initialize the database
        itemPrescricaoDietaRepository.saveAndFlush(itemPrescricaoDieta);

        // Get the itemPrescricaoDieta
        restItemPrescricaoDietaMockMvc.perform(get("/api/item-prescricao-dietas/{id}", itemPrescricaoDieta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPrescricaoDieta.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.intValue()))
            .andExpect(jsonPath("$.frequencia").value(DEFAULT_FREQUENCIA))
            .andExpect(jsonPath("$.numeroVezes").value(DEFAULT_NUMERO_VEZES));
    }

    @Test
    @Transactional
    public void getNonExistingItemPrescricaoDieta() throws Exception {
        // Get the itemPrescricaoDieta
        restItemPrescricaoDietaMockMvc.perform(get("/api/item-prescricao-dietas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPrescricaoDieta() throws Exception {
        // Initialize the database
        itemPrescricaoDietaRepository.saveAndFlush(itemPrescricaoDieta);

        int databaseSizeBeforeUpdate = itemPrescricaoDietaRepository.findAll().size();

        // Update the itemPrescricaoDieta
        ItemPrescricaoDieta updatedItemPrescricaoDieta = itemPrescricaoDietaRepository.findById(itemPrescricaoDieta.getId()).get();
        // Disconnect from session so that the updates on updatedItemPrescricaoDieta are not directly saved in db
        em.detach(updatedItemPrescricaoDieta);
        updatedItemPrescricaoDieta
            .quantidade(UPDATED_QUANTIDADE)
            .frequencia(UPDATED_FREQUENCIA)
            .numeroVezes(UPDATED_NUMERO_VEZES);
        ItemPrescricaoDietaDTO itemPrescricaoDietaDTO = itemPrescricaoDietaMapper.toDto(updatedItemPrescricaoDieta);

        restItemPrescricaoDietaMockMvc.perform(put("/api/item-prescricao-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDietaDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPrescricaoDieta in the database
        List<ItemPrescricaoDieta> itemPrescricaoDietaList = itemPrescricaoDietaRepository.findAll();
        assertThat(itemPrescricaoDietaList).hasSize(databaseSizeBeforeUpdate);
        ItemPrescricaoDieta testItemPrescricaoDieta = itemPrescricaoDietaList.get(itemPrescricaoDietaList.size() - 1);
        assertThat(testItemPrescricaoDieta.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemPrescricaoDieta.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
        assertThat(testItemPrescricaoDieta.getNumeroVezes()).isEqualTo(UPDATED_NUMERO_VEZES);

        // Validate the ItemPrescricaoDieta in Elasticsearch
        verify(mockItemPrescricaoDietaSearchRepository, times(1)).save(testItemPrescricaoDieta);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPrescricaoDieta() throws Exception {
        int databaseSizeBeforeUpdate = itemPrescricaoDietaRepository.findAll().size();

        // Create the ItemPrescricaoDieta
        ItemPrescricaoDietaDTO itemPrescricaoDietaDTO = itemPrescricaoDietaMapper.toDto(itemPrescricaoDieta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPrescricaoDietaMockMvc.perform(put("/api/item-prescricao-dietas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDietaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoDieta in the database
        List<ItemPrescricaoDieta> itemPrescricaoDietaList = itemPrescricaoDietaRepository.findAll();
        assertThat(itemPrescricaoDietaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemPrescricaoDieta in Elasticsearch
        verify(mockItemPrescricaoDietaSearchRepository, times(0)).save(itemPrescricaoDieta);
    }

    @Test
    @Transactional
    public void deleteItemPrescricaoDieta() throws Exception {
        // Initialize the database
        itemPrescricaoDietaRepository.saveAndFlush(itemPrescricaoDieta);

        int databaseSizeBeforeDelete = itemPrescricaoDietaRepository.findAll().size();

        // Delete the itemPrescricaoDieta
        restItemPrescricaoDietaMockMvc.perform(delete("/api/item-prescricao-dietas/{id}", itemPrescricaoDieta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemPrescricaoDieta> itemPrescricaoDietaList = itemPrescricaoDietaRepository.findAll();
        assertThat(itemPrescricaoDietaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemPrescricaoDieta in Elasticsearch
        verify(mockItemPrescricaoDietaSearchRepository, times(1)).deleteById(itemPrescricaoDieta.getId());
    }

    @Test
    @Transactional
    public void searchItemPrescricaoDieta() throws Exception {
        // Initialize the database
        itemPrescricaoDietaRepository.saveAndFlush(itemPrescricaoDieta);
        when(mockItemPrescricaoDietaSearchRepository.search(queryStringQuery("id:" + itemPrescricaoDieta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(itemPrescricaoDieta), PageRequest.of(0, 1), 1));
        // Search the itemPrescricaoDieta
        restItemPrescricaoDietaMockMvc.perform(get("/api/_search/item-prescricao-dietas?query=id:" + itemPrescricaoDieta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoDieta.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA)))
            .andExpect(jsonPath("$.[*].numeroVezes").value(hasItem(DEFAULT_NUMERO_VEZES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoDieta.class);
        ItemPrescricaoDieta itemPrescricaoDieta1 = new ItemPrescricaoDieta();
        itemPrescricaoDieta1.setId(1L);
        ItemPrescricaoDieta itemPrescricaoDieta2 = new ItemPrescricaoDieta();
        itemPrescricaoDieta2.setId(itemPrescricaoDieta1.getId());
        assertThat(itemPrescricaoDieta1).isEqualTo(itemPrescricaoDieta2);
        itemPrescricaoDieta2.setId(2L);
        assertThat(itemPrescricaoDieta1).isNotEqualTo(itemPrescricaoDieta2);
        itemPrescricaoDieta1.setId(null);
        assertThat(itemPrescricaoDieta1).isNotEqualTo(itemPrescricaoDieta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoDietaDTO.class);
        ItemPrescricaoDietaDTO itemPrescricaoDietaDTO1 = new ItemPrescricaoDietaDTO();
        itemPrescricaoDietaDTO1.setId(1L);
        ItemPrescricaoDietaDTO itemPrescricaoDietaDTO2 = new ItemPrescricaoDietaDTO();
        assertThat(itemPrescricaoDietaDTO1).isNotEqualTo(itemPrescricaoDietaDTO2);
        itemPrescricaoDietaDTO2.setId(itemPrescricaoDietaDTO1.getId());
        assertThat(itemPrescricaoDietaDTO1).isEqualTo(itemPrescricaoDietaDTO2);
        itemPrescricaoDietaDTO2.setId(2L);
        assertThat(itemPrescricaoDietaDTO1).isNotEqualTo(itemPrescricaoDietaDTO2);
        itemPrescricaoDietaDTO1.setId(null);
        assertThat(itemPrescricaoDietaDTO1).isNotEqualTo(itemPrescricaoDietaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPrescricaoDietaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPrescricaoDietaMapper.fromId(null)).isNull();
    }
}
