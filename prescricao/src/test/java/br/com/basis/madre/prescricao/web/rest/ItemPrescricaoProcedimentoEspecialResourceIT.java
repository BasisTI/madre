package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimentoEspecial;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoProcedimentoEspecialRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoProcedimentoEspecialSearchRepository;
import br.com.basis.madre.prescricao.service.ItemPrescricaoProcedimentoEspecialService;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoProcedimentoEspecialDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoProcedimentoEspecialMapper;
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
import java.util.Collections;
import java.util.List;

import static br.com.basis.madre.prescricao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;
/**
 * Integration tests for the {@link ItemPrescricaoProcedimentoEspecialResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class ItemPrescricaoProcedimentoEspecialResourceIT {

    private static final TipoProcedimentoEspecial DEFAULT_TIPO_PROCEDIMENTO = TipoProcedimentoEspecial.DIVERSOS;
    private static final TipoProcedimentoEspecial UPDATED_TIPO_PROCEDIMENTO = TipoProcedimentoEspecial.CIRURGIAS_LEITO;

    private static final Integer DEFAULT_QUANTIDADE_ORTESE_PROTESE = 0;
    private static final Integer UPDATED_QUANTIDADE_ORTESE_PROTESE = 1;

    private static final String DEFAULT_INFORMACOES = "AAAAAAAAAA";
    private static final String UPDATED_INFORMACOES = "BBBBBBBBBB";

    private static final String DEFAULT_JUSTIFICATIVA = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIVA = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURACAO_SOLICITADA = 0;
    private static final Integer UPDATED_DURACAO_SOLICITADA = 1;

    @Autowired
    private ItemPrescricaoProcedimentoEspecialRepository itemPrescricaoProcedimentoEspecialRepository;

    @Autowired
    private ItemPrescricaoProcedimentoEspecialMapper itemPrescricaoProcedimentoEspecialMapper;

    @Autowired
    private ItemPrescricaoProcedimentoEspecialService itemPrescricaoProcedimentoEspecialService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.ItemPrescricaoProcedimentoEspecialSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemPrescricaoProcedimentoEspecialSearchRepository mockItemPrescricaoProcedimentoEspecialSearchRepository;

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

    private MockMvc restItemPrescricaoProcedimentoEspecialMockMvc;

    private ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPrescricaoProcedimentoEspecialResource itemPrescricaoProcedimentoEspecialResource = new ItemPrescricaoProcedimentoEspecialResource(itemPrescricaoProcedimentoEspecialService);
        this.restItemPrescricaoProcedimentoEspecialMockMvc = MockMvcBuilders.standaloneSetup(itemPrescricaoProcedimentoEspecialResource)
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
    public static ItemPrescricaoProcedimentoEspecial createEntity(EntityManager em) {
        ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial = new ItemPrescricaoProcedimentoEspecial()
            .tipoProcedimento(DEFAULT_TIPO_PROCEDIMENTO)
            .quantidadeOrteseProtese(DEFAULT_QUANTIDADE_ORTESE_PROTESE)
            .informacoes(DEFAULT_INFORMACOES)
            .justificativa(DEFAULT_JUSTIFICATIVA)
            .duracaoSolicitada(DEFAULT_DURACAO_SOLICITADA);
        return itemPrescricaoProcedimentoEspecial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPrescricaoProcedimentoEspecial createUpdatedEntity(EntityManager em) {
        ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial = new ItemPrescricaoProcedimentoEspecial()
            .tipoProcedimento(UPDATED_TIPO_PROCEDIMENTO)
            .quantidadeOrteseProtese(UPDATED_QUANTIDADE_ORTESE_PROTESE)
            .informacoes(UPDATED_INFORMACOES)
            .justificativa(UPDATED_JUSTIFICATIVA)
            .duracaoSolicitada(UPDATED_DURACAO_SOLICITADA);
        return itemPrescricaoProcedimentoEspecial;
    }

    @BeforeEach
    public void initTest() {
        itemPrescricaoProcedimentoEspecial = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPrescricaoProcedimentoEspecial() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoProcedimentoEspecialRepository.findAll().size();

        // Create the ItemPrescricaoProcedimentoEspecial
        ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO = itemPrescricaoProcedimentoEspecialMapper.toDto(itemPrescricaoProcedimentoEspecial);
        restItemPrescricaoProcedimentoEspecialMockMvc.perform(post("/api/item-prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPrescricaoProcedimentoEspecial in the database
        List<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecialList = itemPrescricaoProcedimentoEspecialRepository.findAll();
        assertThat(itemPrescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPrescricaoProcedimentoEspecial testItemPrescricaoProcedimentoEspecial = itemPrescricaoProcedimentoEspecialList.get(itemPrescricaoProcedimentoEspecialList.size() - 1);
        assertThat(testItemPrescricaoProcedimentoEspecial.getTipoProcedimento()).isEqualTo(DEFAULT_TIPO_PROCEDIMENTO);
        assertThat(testItemPrescricaoProcedimentoEspecial.getQuantidadeOrteseProtese()).isEqualTo(DEFAULT_QUANTIDADE_ORTESE_PROTESE);
        assertThat(testItemPrescricaoProcedimentoEspecial.getInformacoes()).isEqualTo(DEFAULT_INFORMACOES);
        assertThat(testItemPrescricaoProcedimentoEspecial.getJustificativa()).isEqualTo(DEFAULT_JUSTIFICATIVA);
        assertThat(testItemPrescricaoProcedimentoEspecial.getDuracaoSolicitada()).isEqualTo(DEFAULT_DURACAO_SOLICITADA);

        // Validate the ItemPrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockItemPrescricaoProcedimentoEspecialSearchRepository, times(1)).save(testItemPrescricaoProcedimentoEspecial);
    }

    @Test
    @Transactional
    public void createItemPrescricaoProcedimentoEspecialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoProcedimentoEspecialRepository.findAll().size();

        // Create the ItemPrescricaoProcedimentoEspecial with an existing ID
        itemPrescricaoProcedimentoEspecial.setId(1L);
        ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO = itemPrescricaoProcedimentoEspecialMapper.toDto(itemPrescricaoProcedimentoEspecial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPrescricaoProcedimentoEspecialMockMvc.perform(post("/api/item-prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoProcedimentoEspecial in the database
        List<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecialList = itemPrescricaoProcedimentoEspecialRepository.findAll();
        assertThat(itemPrescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemPrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockItemPrescricaoProcedimentoEspecialSearchRepository, times(0)).save(itemPrescricaoProcedimentoEspecial);
    }


    @Test
    @Transactional
    public void getAllItemPrescricaoProcedimentoEspecials() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoEspecialRepository.saveAndFlush(itemPrescricaoProcedimentoEspecial);

        // Get all the itemPrescricaoProcedimentoEspecialList
        restItemPrescricaoProcedimentoEspecialMockMvc.perform(get("/api/item-prescricao-procedimento-especials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoProcedimentoEspecial.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoProcedimento").value(hasItem(DEFAULT_TIPO_PROCEDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].quantidadeOrteseProtese").value(hasItem(DEFAULT_QUANTIDADE_ORTESE_PROTESE)))
            .andExpect(jsonPath("$.[*].informacoes").value(hasItem(DEFAULT_INFORMACOES)))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)))
            .andExpect(jsonPath("$.[*].duracaoSolicitada").value(hasItem(DEFAULT_DURACAO_SOLICITADA)));
    }
    
    @Test
    @Transactional
    public void getItemPrescricaoProcedimentoEspecial() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoEspecialRepository.saveAndFlush(itemPrescricaoProcedimentoEspecial);

        // Get the itemPrescricaoProcedimentoEspecial
        restItemPrescricaoProcedimentoEspecialMockMvc.perform(get("/api/item-prescricao-procedimento-especials/{id}", itemPrescricaoProcedimentoEspecial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPrescricaoProcedimentoEspecial.getId().intValue()))
            .andExpect(jsonPath("$.tipoProcedimento").value(DEFAULT_TIPO_PROCEDIMENTO.toString()))
            .andExpect(jsonPath("$.quantidadeOrteseProtese").value(DEFAULT_QUANTIDADE_ORTESE_PROTESE))
            .andExpect(jsonPath("$.informacoes").value(DEFAULT_INFORMACOES))
            .andExpect(jsonPath("$.justificativa").value(DEFAULT_JUSTIFICATIVA))
            .andExpect(jsonPath("$.duracaoSolicitada").value(DEFAULT_DURACAO_SOLICITADA));
    }

    @Test
    @Transactional
    public void getNonExistingItemPrescricaoProcedimentoEspecial() throws Exception {
        // Get the itemPrescricaoProcedimentoEspecial
        restItemPrescricaoProcedimentoEspecialMockMvc.perform(get("/api/item-prescricao-procedimento-especials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPrescricaoProcedimentoEspecial() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoEspecialRepository.saveAndFlush(itemPrescricaoProcedimentoEspecial);

        int databaseSizeBeforeUpdate = itemPrescricaoProcedimentoEspecialRepository.findAll().size();

        // Update the itemPrescricaoProcedimentoEspecial
        ItemPrescricaoProcedimentoEspecial updatedItemPrescricaoProcedimentoEspecial = itemPrescricaoProcedimentoEspecialRepository.findById(itemPrescricaoProcedimentoEspecial.getId()).get();
        // Disconnect from session so that the updates on updatedItemPrescricaoProcedimentoEspecial are not directly saved in db
        em.detach(updatedItemPrescricaoProcedimentoEspecial);
        updatedItemPrescricaoProcedimentoEspecial
            .tipoProcedimento(UPDATED_TIPO_PROCEDIMENTO)
            .quantidadeOrteseProtese(UPDATED_QUANTIDADE_ORTESE_PROTESE)
            .informacoes(UPDATED_INFORMACOES)
            .justificativa(UPDATED_JUSTIFICATIVA)
            .duracaoSolicitada(UPDATED_DURACAO_SOLICITADA);
        ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO = itemPrescricaoProcedimentoEspecialMapper.toDto(updatedItemPrescricaoProcedimentoEspecial);

        restItemPrescricaoProcedimentoEspecialMockMvc.perform(put("/api/item-prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPrescricaoProcedimentoEspecial in the database
        List<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecialList = itemPrescricaoProcedimentoEspecialRepository.findAll();
        assertThat(itemPrescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeUpdate);
        ItemPrescricaoProcedimentoEspecial testItemPrescricaoProcedimentoEspecial = itemPrescricaoProcedimentoEspecialList.get(itemPrescricaoProcedimentoEspecialList.size() - 1);
        assertThat(testItemPrescricaoProcedimentoEspecial.getTipoProcedimento()).isEqualTo(UPDATED_TIPO_PROCEDIMENTO);
        assertThat(testItemPrescricaoProcedimentoEspecial.getQuantidadeOrteseProtese()).isEqualTo(UPDATED_QUANTIDADE_ORTESE_PROTESE);
        assertThat(testItemPrescricaoProcedimentoEspecial.getInformacoes()).isEqualTo(UPDATED_INFORMACOES);
        assertThat(testItemPrescricaoProcedimentoEspecial.getJustificativa()).isEqualTo(UPDATED_JUSTIFICATIVA);
        assertThat(testItemPrescricaoProcedimentoEspecial.getDuracaoSolicitada()).isEqualTo(UPDATED_DURACAO_SOLICITADA);

        // Validate the ItemPrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockItemPrescricaoProcedimentoEspecialSearchRepository, times(1)).save(testItemPrescricaoProcedimentoEspecial);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPrescricaoProcedimentoEspecial() throws Exception {
        int databaseSizeBeforeUpdate = itemPrescricaoProcedimentoEspecialRepository.findAll().size();

        // Create the ItemPrescricaoProcedimentoEspecial
        ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO = itemPrescricaoProcedimentoEspecialMapper.toDto(itemPrescricaoProcedimentoEspecial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPrescricaoProcedimentoEspecialMockMvc.perform(put("/api/item-prescricao-procedimento-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoProcedimentoEspecialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoProcedimentoEspecial in the database
        List<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecialList = itemPrescricaoProcedimentoEspecialRepository.findAll();
        assertThat(itemPrescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemPrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockItemPrescricaoProcedimentoEspecialSearchRepository, times(0)).save(itemPrescricaoProcedimentoEspecial);
    }

    @Test
    @Transactional
    public void deleteItemPrescricaoProcedimentoEspecial() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoEspecialRepository.saveAndFlush(itemPrescricaoProcedimentoEspecial);

        int databaseSizeBeforeDelete = itemPrescricaoProcedimentoEspecialRepository.findAll().size();

        // Delete the itemPrescricaoProcedimentoEspecial
        restItemPrescricaoProcedimentoEspecialMockMvc.perform(delete("/api/item-prescricao-procedimento-especials/{id}", itemPrescricaoProcedimentoEspecial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemPrescricaoProcedimentoEspecial> itemPrescricaoProcedimentoEspecialList = itemPrescricaoProcedimentoEspecialRepository.findAll();
        assertThat(itemPrescricaoProcedimentoEspecialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemPrescricaoProcedimentoEspecial in Elasticsearch
        verify(mockItemPrescricaoProcedimentoEspecialSearchRepository, times(1)).deleteById(itemPrescricaoProcedimentoEspecial.getId());
    }

    @Test
    @Transactional
    public void searchItemPrescricaoProcedimentoEspecial() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoEspecialRepository.saveAndFlush(itemPrescricaoProcedimentoEspecial);
        when(mockItemPrescricaoProcedimentoEspecialSearchRepository.search(queryStringQuery("id:" + itemPrescricaoProcedimentoEspecial.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(itemPrescricaoProcedimentoEspecial), PageRequest.of(0, 1), 1));
        // Search the itemPrescricaoProcedimentoEspecial
        restItemPrescricaoProcedimentoEspecialMockMvc.perform(get("/api/_search/item-prescricao-procedimento-especials?query=id:" + itemPrescricaoProcedimentoEspecial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoProcedimentoEspecial.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoProcedimento").value(hasItem(DEFAULT_TIPO_PROCEDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].quantidadeOrteseProtese").value(hasItem(DEFAULT_QUANTIDADE_ORTESE_PROTESE)))
            .andExpect(jsonPath("$.[*].informacoes").value(hasItem(DEFAULT_INFORMACOES)))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)))
            .andExpect(jsonPath("$.[*].duracaoSolicitada").value(hasItem(DEFAULT_DURACAO_SOLICITADA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoProcedimentoEspecial.class);
        ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial1 = new ItemPrescricaoProcedimentoEspecial();
        itemPrescricaoProcedimentoEspecial1.setId(1L);
        ItemPrescricaoProcedimentoEspecial itemPrescricaoProcedimentoEspecial2 = new ItemPrescricaoProcedimentoEspecial();
        itemPrescricaoProcedimentoEspecial2.setId(itemPrescricaoProcedimentoEspecial1.getId());
        assertThat(itemPrescricaoProcedimentoEspecial1).isEqualTo(itemPrescricaoProcedimentoEspecial2);
        itemPrescricaoProcedimentoEspecial2.setId(2L);
        assertThat(itemPrescricaoProcedimentoEspecial1).isNotEqualTo(itemPrescricaoProcedimentoEspecial2);
        itemPrescricaoProcedimentoEspecial1.setId(null);
        assertThat(itemPrescricaoProcedimentoEspecial1).isNotEqualTo(itemPrescricaoProcedimentoEspecial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoProcedimentoEspecialDTO.class);
        ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO1 = new ItemPrescricaoProcedimentoEspecialDTO();
        itemPrescricaoProcedimentoEspecialDTO1.setId(1L);
        ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO2 = new ItemPrescricaoProcedimentoEspecialDTO();
        assertThat(itemPrescricaoProcedimentoEspecialDTO1).isNotEqualTo(itemPrescricaoProcedimentoEspecialDTO2);
        itemPrescricaoProcedimentoEspecialDTO2.setId(itemPrescricaoProcedimentoEspecialDTO1.getId());
        assertThat(itemPrescricaoProcedimentoEspecialDTO1).isEqualTo(itemPrescricaoProcedimentoEspecialDTO2);
        itemPrescricaoProcedimentoEspecialDTO2.setId(2L);
        assertThat(itemPrescricaoProcedimentoEspecialDTO1).isNotEqualTo(itemPrescricaoProcedimentoEspecialDTO2);
        itemPrescricaoProcedimentoEspecialDTO1.setId(null);
        assertThat(itemPrescricaoProcedimentoEspecialDTO1).isNotEqualTo(itemPrescricaoProcedimentoEspecialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPrescricaoProcedimentoEspecialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPrescricaoProcedimentoEspecialMapper.fromId(null)).isNull();
    }
}
