package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoDiagnosticoRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoDiagnosticoSearchRepository;
import br.com.basis.madre.prescricao.service.ItemPrescricaoDiagnosticoService;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDiagnosticoDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoDiagnosticoMapper;
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

import static br.com.basis.madre.prescricao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ItemPrescricaoDiagnosticoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class ItemPrescricaoDiagnosticoResourceIT {

    private static final Long DEFAULT_ID_CID = 1L;
    private static final Long UPDATED_ID_CID = 2L;

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    @Autowired
    private ItemPrescricaoDiagnosticoRepository itemPrescricaoDiagnosticoRepository;

    @Autowired
    private ItemPrescricaoDiagnosticoMapper itemPrescricaoDiagnosticoMapper;

    @Autowired
    private ItemPrescricaoDiagnosticoService itemPrescricaoDiagnosticoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.ItemPrescricaoDiagnosticoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemPrescricaoDiagnosticoSearchRepository mockItemPrescricaoDiagnosticoSearchRepository;

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

    private MockMvc restItemPrescricaoDiagnosticoMockMvc;

    private ItemPrescricaoDiagnostico itemPrescricaoDiagnostico;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPrescricaoDiagnosticoResource itemPrescricaoDiagnosticoResource = new ItemPrescricaoDiagnosticoResource(itemPrescricaoDiagnosticoService);
        this.restItemPrescricaoDiagnosticoMockMvc = MockMvcBuilders.standaloneSetup(itemPrescricaoDiagnosticoResource)
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
    public static ItemPrescricaoDiagnostico createEntity(EntityManager em) {
        ItemPrescricaoDiagnostico itemPrescricaoDiagnostico = new ItemPrescricaoDiagnostico()
            .idCid(DEFAULT_ID_CID)
            .complemento(DEFAULT_COMPLEMENTO);
        return itemPrescricaoDiagnostico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPrescricaoDiagnostico createUpdatedEntity(EntityManager em) {
        ItemPrescricaoDiagnostico itemPrescricaoDiagnostico = new ItemPrescricaoDiagnostico()
            .idCid(UPDATED_ID_CID)
            .complemento(UPDATED_COMPLEMENTO);
        return itemPrescricaoDiagnostico;
    }

    @BeforeEach
    public void initTest() {
        itemPrescricaoDiagnostico = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPrescricaoDiagnostico() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoDiagnosticoRepository.findAll().size();

        // Create the ItemPrescricaoDiagnostico
        ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO = itemPrescricaoDiagnosticoMapper.toDto(itemPrescricaoDiagnostico);
        restItemPrescricaoDiagnosticoMockMvc.perform(post("/api/item-prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDiagnosticoDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPrescricaoDiagnostico in the database
        List<ItemPrescricaoDiagnostico> itemPrescricaoDiagnosticoList = itemPrescricaoDiagnosticoRepository.findAll();
        assertThat(itemPrescricaoDiagnosticoList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPrescricaoDiagnostico testItemPrescricaoDiagnostico = itemPrescricaoDiagnosticoList.get(itemPrescricaoDiagnosticoList.size() - 1);
        assertThat(testItemPrescricaoDiagnostico.getIdCid()).isEqualTo(DEFAULT_ID_CID);
        assertThat(testItemPrescricaoDiagnostico.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);

        // Validate the ItemPrescricaoDiagnostico in Elasticsearch
        verify(mockItemPrescricaoDiagnosticoSearchRepository, times(1)).save(testItemPrescricaoDiagnostico);
    }

    @Test
    @Transactional
    public void createItemPrescricaoDiagnosticoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoDiagnosticoRepository.findAll().size();

        // Create the ItemPrescricaoDiagnostico with an existing ID
        itemPrescricaoDiagnostico.setId(1L);
        ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO = itemPrescricaoDiagnosticoMapper.toDto(itemPrescricaoDiagnostico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPrescricaoDiagnosticoMockMvc.perform(post("/api/item-prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDiagnosticoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoDiagnostico in the database
        List<ItemPrescricaoDiagnostico> itemPrescricaoDiagnosticoList = itemPrescricaoDiagnosticoRepository.findAll();
        assertThat(itemPrescricaoDiagnosticoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemPrescricaoDiagnostico in Elasticsearch
        verify(mockItemPrescricaoDiagnosticoSearchRepository, times(0)).save(itemPrescricaoDiagnostico);
    }


    @Test
    @Transactional
    public void checkIdCidIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPrescricaoDiagnosticoRepository.findAll().size();
        // set the field null
        itemPrescricaoDiagnostico.setIdCid(null);

        // Create the ItemPrescricaoDiagnostico, which fails.
        ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO = itemPrescricaoDiagnosticoMapper.toDto(itemPrescricaoDiagnostico);

        restItemPrescricaoDiagnosticoMockMvc.perform(post("/api/item-prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDiagnosticoDTO)))
            .andExpect(status().isBadRequest());

        List<ItemPrescricaoDiagnostico> itemPrescricaoDiagnosticoList = itemPrescricaoDiagnosticoRepository.findAll();
        assertThat(itemPrescricaoDiagnosticoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemPrescricaoDiagnosticos() throws Exception {
        // Initialize the database
        itemPrescricaoDiagnosticoRepository.saveAndFlush(itemPrescricaoDiagnostico);

        // Get all the itemPrescricaoDiagnosticoList
        restItemPrescricaoDiagnosticoMockMvc.perform(get("/api/item-prescricao-diagnosticos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoDiagnostico.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCid").value(hasItem(DEFAULT_ID_CID.intValue())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)));
    }
    
    @Test
    @Transactional
    public void getItemPrescricaoDiagnostico() throws Exception {
        // Initialize the database
        itemPrescricaoDiagnosticoRepository.saveAndFlush(itemPrescricaoDiagnostico);

        // Get the itemPrescricaoDiagnostico
        restItemPrescricaoDiagnosticoMockMvc.perform(get("/api/item-prescricao-diagnosticos/{id}", itemPrescricaoDiagnostico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPrescricaoDiagnostico.getId().intValue()))
            .andExpect(jsonPath("$.idCid").value(DEFAULT_ID_CID.intValue()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO));
    }

    @Test
    @Transactional
    public void getNonExistingItemPrescricaoDiagnostico() throws Exception {
        // Get the itemPrescricaoDiagnostico
        restItemPrescricaoDiagnosticoMockMvc.perform(get("/api/item-prescricao-diagnosticos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPrescricaoDiagnostico() throws Exception {
        // Initialize the database
        itemPrescricaoDiagnosticoRepository.saveAndFlush(itemPrescricaoDiagnostico);

        int databaseSizeBeforeUpdate = itemPrescricaoDiagnosticoRepository.findAll().size();

        // Update the itemPrescricaoDiagnostico
        ItemPrescricaoDiagnostico updatedItemPrescricaoDiagnostico = itemPrescricaoDiagnosticoRepository.findById(itemPrescricaoDiagnostico.getId()).get();
        // Disconnect from session so that the updates on updatedItemPrescricaoDiagnostico are not directly saved in db
        em.detach(updatedItemPrescricaoDiagnostico);
        updatedItemPrescricaoDiagnostico
            .idCid(UPDATED_ID_CID)
            .complemento(UPDATED_COMPLEMENTO);
        ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO = itemPrescricaoDiagnosticoMapper.toDto(updatedItemPrescricaoDiagnostico);

        restItemPrescricaoDiagnosticoMockMvc.perform(put("/api/item-prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDiagnosticoDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPrescricaoDiagnostico in the database
        List<ItemPrescricaoDiagnostico> itemPrescricaoDiagnosticoList = itemPrescricaoDiagnosticoRepository.findAll();
        assertThat(itemPrescricaoDiagnosticoList).hasSize(databaseSizeBeforeUpdate);
        ItemPrescricaoDiagnostico testItemPrescricaoDiagnostico = itemPrescricaoDiagnosticoList.get(itemPrescricaoDiagnosticoList.size() - 1);
        assertThat(testItemPrescricaoDiagnostico.getIdCid()).isEqualTo(UPDATED_ID_CID);
        assertThat(testItemPrescricaoDiagnostico.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);

        // Validate the ItemPrescricaoDiagnostico in Elasticsearch
        verify(mockItemPrescricaoDiagnosticoSearchRepository, times(1)).save(testItemPrescricaoDiagnostico);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPrescricaoDiagnostico() throws Exception {
        int databaseSizeBeforeUpdate = itemPrescricaoDiagnosticoRepository.findAll().size();

        // Create the ItemPrescricaoDiagnostico
        ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO = itemPrescricaoDiagnosticoMapper.toDto(itemPrescricaoDiagnostico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPrescricaoDiagnosticoMockMvc.perform(put("/api/item-prescricao-diagnosticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoDiagnosticoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoDiagnostico in the database
        List<ItemPrescricaoDiagnostico> itemPrescricaoDiagnosticoList = itemPrescricaoDiagnosticoRepository.findAll();
        assertThat(itemPrescricaoDiagnosticoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemPrescricaoDiagnostico in Elasticsearch
        verify(mockItemPrescricaoDiagnosticoSearchRepository, times(0)).save(itemPrescricaoDiagnostico);
    }

    @Test
    @Transactional
    public void deleteItemPrescricaoDiagnostico() throws Exception {
        // Initialize the database
        itemPrescricaoDiagnosticoRepository.saveAndFlush(itemPrescricaoDiagnostico);

        int databaseSizeBeforeDelete = itemPrescricaoDiagnosticoRepository.findAll().size();

        // Delete the itemPrescricaoDiagnostico
        restItemPrescricaoDiagnosticoMockMvc.perform(delete("/api/item-prescricao-diagnosticos/{id}", itemPrescricaoDiagnostico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemPrescricaoDiagnostico> itemPrescricaoDiagnosticoList = itemPrescricaoDiagnosticoRepository.findAll();
        assertThat(itemPrescricaoDiagnosticoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemPrescricaoDiagnostico in Elasticsearch
        verify(mockItemPrescricaoDiagnosticoSearchRepository, times(1)).deleteById(itemPrescricaoDiagnostico.getId());
    }

    @Test
    @Transactional
    public void searchItemPrescricaoDiagnostico() throws Exception {
        // Initialize the database
        itemPrescricaoDiagnosticoRepository.saveAndFlush(itemPrescricaoDiagnostico);
        when(mockItemPrescricaoDiagnosticoSearchRepository.search(queryStringQuery("id:" + itemPrescricaoDiagnostico.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(itemPrescricaoDiagnostico), PageRequest.of(0, 1), 1));
        // Search the itemPrescricaoDiagnostico
        restItemPrescricaoDiagnosticoMockMvc.perform(get("/api/_search/item-prescricao-diagnosticos?query=id:" + itemPrescricaoDiagnostico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoDiagnostico.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCid").value(hasItem(DEFAULT_ID_CID.intValue())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoDiagnostico.class);
        ItemPrescricaoDiagnostico itemPrescricaoDiagnostico1 = new ItemPrescricaoDiagnostico();
        itemPrescricaoDiagnostico1.setId(1L);
        ItemPrescricaoDiagnostico itemPrescricaoDiagnostico2 = new ItemPrescricaoDiagnostico();
        itemPrescricaoDiagnostico2.setId(itemPrescricaoDiagnostico1.getId());
        assertThat(itemPrescricaoDiagnostico1).isEqualTo(itemPrescricaoDiagnostico2);
        itemPrescricaoDiagnostico2.setId(2L);
        assertThat(itemPrescricaoDiagnostico1).isNotEqualTo(itemPrescricaoDiagnostico2);
        itemPrescricaoDiagnostico1.setId(null);
        assertThat(itemPrescricaoDiagnostico1).isNotEqualTo(itemPrescricaoDiagnostico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoDiagnosticoDTO.class);
        ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO1 = new ItemPrescricaoDiagnosticoDTO();
        itemPrescricaoDiagnosticoDTO1.setId(1L);
        ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO2 = new ItemPrescricaoDiagnosticoDTO();
        assertThat(itemPrescricaoDiagnosticoDTO1).isNotEqualTo(itemPrescricaoDiagnosticoDTO2);
        itemPrescricaoDiagnosticoDTO2.setId(itemPrescricaoDiagnosticoDTO1.getId());
        assertThat(itemPrescricaoDiagnosticoDTO1).isEqualTo(itemPrescricaoDiagnosticoDTO2);
        itemPrescricaoDiagnosticoDTO2.setId(2L);
        assertThat(itemPrescricaoDiagnosticoDTO1).isNotEqualTo(itemPrescricaoDiagnosticoDTO2);
        itemPrescricaoDiagnosticoDTO1.setId(null);
        assertThat(itemPrescricaoDiagnosticoDTO1).isNotEqualTo(itemPrescricaoDiagnosticoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPrescricaoDiagnosticoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPrescricaoDiagnosticoMapper.fromId(null)).isNull();
    }
}
