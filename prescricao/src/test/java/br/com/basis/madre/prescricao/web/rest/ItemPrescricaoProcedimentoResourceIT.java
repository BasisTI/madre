package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.PrescricaoApp;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimento;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoProcedimentoRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoProcedimentoSearchRepository;
import br.com.basis.madre.prescricao.service.ItemPrescricaoProcedimentoService;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoProcedimentoDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoProcedimentoMapper;
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

import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;
/**
 * Integration tests for the {@link ItemPrescricaoProcedimentoResource} REST controller.
 */
@SpringBootTest(classes = PrescricaoApp.class)
public class ItemPrescricaoProcedimentoResourceIT {

    private static final TipoProcedimentoEspecial DEFAULT_TIPO_PROCEDIMENTO_ESPECIAL = TipoProcedimentoEspecial.DIVERSOS;
    private static final TipoProcedimentoEspecial UPDATED_TIPO_PROCEDIMENTO_ESPECIAL = TipoProcedimentoEspecial.CIRURGIAS_LEITO;

    private static final Integer DEFAULT_QUANTIDADE_ORTESE_PROTESE = 0;
    private static final Integer UPDATED_QUANTIDADE_ORTESE_PROTESE = 1;

    private static final String DEFAULT_INFORMACOES = "AAAAAAAAAA";
    private static final String UPDATED_INFORMACOES = "BBBBBBBBBB";

    private static final String DEFAULT_JUSTIFICATIVA = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIVA = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURACAO_SOLICITADA = 0;
    private static final Integer UPDATED_DURACAO_SOLICITADA = 1;

    @Autowired
    private ItemPrescricaoProcedimentoRepository itemPrescricaoProcedimentoRepository;

    @Autowired
    private ItemPrescricaoProcedimentoMapper itemPrescricaoProcedimentoMapper;

    @Autowired
    private ItemPrescricaoProcedimentoService itemPrescricaoProcedimentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.prescricao.repository.search test package.
     *
     * @see br.com.basis.madre.prescricao.repository.search.ItemPrescricaoProcedimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemPrescricaoProcedimentoSearchRepository mockItemPrescricaoProcedimentoSearchRepository;

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

    private MockMvc restItemPrescricaoProcedimentoMockMvc;

    private ItemPrescricaoProcedimento itemPrescricaoProcedimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPrescricaoProcedimentoResource itemPrescricaoProcedimentoResource = new ItemPrescricaoProcedimentoResource(itemPrescricaoProcedimentoService);
        this.restItemPrescricaoProcedimentoMockMvc = MockMvcBuilders.standaloneSetup(itemPrescricaoProcedimentoResource)
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
    public static ItemPrescricaoProcedimento createEntity(EntityManager em) {
        ItemPrescricaoProcedimento itemPrescricaoProcedimento = new ItemPrescricaoProcedimento()
            .tipoProcedimentoEspecial(DEFAULT_TIPO_PROCEDIMENTO_ESPECIAL)
            .quantidadeOrteseProtese(DEFAULT_QUANTIDADE_ORTESE_PROTESE)
            .informacoes(DEFAULT_INFORMACOES)
            .justificativa(DEFAULT_JUSTIFICATIVA)
            .duracaoSolicitada(DEFAULT_DURACAO_SOLICITADA);
        return itemPrescricaoProcedimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPrescricaoProcedimento createUpdatedEntity(EntityManager em) {
        ItemPrescricaoProcedimento itemPrescricaoProcedimento = new ItemPrescricaoProcedimento()
            .tipoProcedimentoEspecial(UPDATED_TIPO_PROCEDIMENTO_ESPECIAL)
            .quantidadeOrteseProtese(UPDATED_QUANTIDADE_ORTESE_PROTESE)
            .informacoes(UPDATED_INFORMACOES)
            .justificativa(UPDATED_JUSTIFICATIVA)
            .duracaoSolicitada(UPDATED_DURACAO_SOLICITADA);
        return itemPrescricaoProcedimento;
    }

    @BeforeEach
    public void initTest() {
        itemPrescricaoProcedimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPrescricaoProcedimento() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoProcedimentoRepository.findAll().size();

        // Create the ItemPrescricaoProcedimento
        ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO = itemPrescricaoProcedimentoMapper.toDto(itemPrescricaoProcedimento);
        restItemPrescricaoProcedimentoMockMvc.perform(post("/api/item-prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoProcedimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPrescricaoProcedimento in the database
        List<ItemPrescricaoProcedimento> itemPrescricaoProcedimentoList = itemPrescricaoProcedimentoRepository.findAll();
        assertThat(itemPrescricaoProcedimentoList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPrescricaoProcedimento testItemPrescricaoProcedimento = itemPrescricaoProcedimentoList.get(itemPrescricaoProcedimentoList.size() - 1);
        assertThat(testItemPrescricaoProcedimento.getTipoProcedimentoEspecial()).isEqualTo(DEFAULT_TIPO_PROCEDIMENTO_ESPECIAL);
        assertThat(testItemPrescricaoProcedimento.getQuantidadeOrteseProtese()).isEqualTo(DEFAULT_QUANTIDADE_ORTESE_PROTESE);
        assertThat(testItemPrescricaoProcedimento.getInformacoes()).isEqualTo(DEFAULT_INFORMACOES);
        assertThat(testItemPrescricaoProcedimento.getJustificativa()).isEqualTo(DEFAULT_JUSTIFICATIVA);
        assertThat(testItemPrescricaoProcedimento.getDuracaoSolicitada()).isEqualTo(DEFAULT_DURACAO_SOLICITADA);

        // Validate the ItemPrescricaoProcedimento in Elasticsearch
        verify(mockItemPrescricaoProcedimentoSearchRepository, times(1)).save(testItemPrescricaoProcedimento);
    }

    @Test
    @Transactional
    public void createItemPrescricaoProcedimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPrescricaoProcedimentoRepository.findAll().size();

        // Create the ItemPrescricaoProcedimento with an existing ID
        itemPrescricaoProcedimento.setId(1L);
        ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO = itemPrescricaoProcedimentoMapper.toDto(itemPrescricaoProcedimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPrescricaoProcedimentoMockMvc.perform(post("/api/item-prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoProcedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoProcedimento in the database
        List<ItemPrescricaoProcedimento> itemPrescricaoProcedimentoList = itemPrescricaoProcedimentoRepository.findAll();
        assertThat(itemPrescricaoProcedimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemPrescricaoProcedimento in Elasticsearch
        verify(mockItemPrescricaoProcedimentoSearchRepository, times(0)).save(itemPrescricaoProcedimento);
    }


    @Test
    @Transactional
    public void getAllItemPrescricaoProcedimentos() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoRepository.saveAndFlush(itemPrescricaoProcedimento);

        // Get all the itemPrescricaoProcedimentoList
        restItemPrescricaoProcedimentoMockMvc.perform(get("/api/item-prescricao-procedimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoProcedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoProcedimentoEspecial").value(hasItem(DEFAULT_TIPO_PROCEDIMENTO_ESPECIAL.toString())))
            .andExpect(jsonPath("$.[*].quantidadeOrteseProtese").value(hasItem(DEFAULT_QUANTIDADE_ORTESE_PROTESE)))
            .andExpect(jsonPath("$.[*].informacoes").value(hasItem(DEFAULT_INFORMACOES)))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)))
            .andExpect(jsonPath("$.[*].duracaoSolicitada").value(hasItem(DEFAULT_DURACAO_SOLICITADA)));
    }
    
    @Test
    @Transactional
    public void getItemPrescricaoProcedimento() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoRepository.saveAndFlush(itemPrescricaoProcedimento);

        // Get the itemPrescricaoProcedimento
        restItemPrescricaoProcedimentoMockMvc.perform(get("/api/item-prescricao-procedimentos/{id}", itemPrescricaoProcedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPrescricaoProcedimento.getId().intValue()))
            .andExpect(jsonPath("$.tipoProcedimentoEspecial").value(DEFAULT_TIPO_PROCEDIMENTO_ESPECIAL.toString()))
            .andExpect(jsonPath("$.quantidadeOrteseProtese").value(DEFAULT_QUANTIDADE_ORTESE_PROTESE))
            .andExpect(jsonPath("$.informacoes").value(DEFAULT_INFORMACOES))
            .andExpect(jsonPath("$.justificativa").value(DEFAULT_JUSTIFICATIVA))
            .andExpect(jsonPath("$.duracaoSolicitada").value(DEFAULT_DURACAO_SOLICITADA));
    }

    @Test
    @Transactional
    public void getNonExistingItemPrescricaoProcedimento() throws Exception {
        // Get the itemPrescricaoProcedimento
        restItemPrescricaoProcedimentoMockMvc.perform(get("/api/item-prescricao-procedimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPrescricaoProcedimento() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoRepository.saveAndFlush(itemPrescricaoProcedimento);

        int databaseSizeBeforeUpdate = itemPrescricaoProcedimentoRepository.findAll().size();

        // Update the itemPrescricaoProcedimento
        ItemPrescricaoProcedimento updatedItemPrescricaoProcedimento = itemPrescricaoProcedimentoRepository.findById(itemPrescricaoProcedimento.getId()).get();
        // Disconnect from session so that the updates on updatedItemPrescricaoProcedimento are not directly saved in db
        em.detach(updatedItemPrescricaoProcedimento);
        updatedItemPrescricaoProcedimento
            .tipoProcedimentoEspecial(UPDATED_TIPO_PROCEDIMENTO_ESPECIAL)
            .quantidadeOrteseProtese(UPDATED_QUANTIDADE_ORTESE_PROTESE)
            .informacoes(UPDATED_INFORMACOES)
            .justificativa(UPDATED_JUSTIFICATIVA)
            .duracaoSolicitada(UPDATED_DURACAO_SOLICITADA);
        ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO = itemPrescricaoProcedimentoMapper.toDto(updatedItemPrescricaoProcedimento);

        restItemPrescricaoProcedimentoMockMvc.perform(put("/api/item-prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoProcedimentoDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPrescricaoProcedimento in the database
        List<ItemPrescricaoProcedimento> itemPrescricaoProcedimentoList = itemPrescricaoProcedimentoRepository.findAll();
        assertThat(itemPrescricaoProcedimentoList).hasSize(databaseSizeBeforeUpdate);
        ItemPrescricaoProcedimento testItemPrescricaoProcedimento = itemPrescricaoProcedimentoList.get(itemPrescricaoProcedimentoList.size() - 1);
        assertThat(testItemPrescricaoProcedimento.getTipoProcedimentoEspecial()).isEqualTo(UPDATED_TIPO_PROCEDIMENTO_ESPECIAL);
        assertThat(testItemPrescricaoProcedimento.getQuantidadeOrteseProtese()).isEqualTo(UPDATED_QUANTIDADE_ORTESE_PROTESE);
        assertThat(testItemPrescricaoProcedimento.getInformacoes()).isEqualTo(UPDATED_INFORMACOES);
        assertThat(testItemPrescricaoProcedimento.getJustificativa()).isEqualTo(UPDATED_JUSTIFICATIVA);
        assertThat(testItemPrescricaoProcedimento.getDuracaoSolicitada()).isEqualTo(UPDATED_DURACAO_SOLICITADA);

        // Validate the ItemPrescricaoProcedimento in Elasticsearch
        verify(mockItemPrescricaoProcedimentoSearchRepository, times(1)).save(testItemPrescricaoProcedimento);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPrescricaoProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = itemPrescricaoProcedimentoRepository.findAll().size();

        // Create the ItemPrescricaoProcedimento
        ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO = itemPrescricaoProcedimentoMapper.toDto(itemPrescricaoProcedimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPrescricaoProcedimentoMockMvc.perform(put("/api/item-prescricao-procedimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPrescricaoProcedimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPrescricaoProcedimento in the database
        List<ItemPrescricaoProcedimento> itemPrescricaoProcedimentoList = itemPrescricaoProcedimentoRepository.findAll();
        assertThat(itemPrescricaoProcedimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemPrescricaoProcedimento in Elasticsearch
        verify(mockItemPrescricaoProcedimentoSearchRepository, times(0)).save(itemPrescricaoProcedimento);
    }

    @Test
    @Transactional
    public void deleteItemPrescricaoProcedimento() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoRepository.saveAndFlush(itemPrescricaoProcedimento);

        int databaseSizeBeforeDelete = itemPrescricaoProcedimentoRepository.findAll().size();

        // Delete the itemPrescricaoProcedimento
        restItemPrescricaoProcedimentoMockMvc.perform(delete("/api/item-prescricao-procedimentos/{id}", itemPrescricaoProcedimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemPrescricaoProcedimento> itemPrescricaoProcedimentoList = itemPrescricaoProcedimentoRepository.findAll();
        assertThat(itemPrescricaoProcedimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemPrescricaoProcedimento in Elasticsearch
        verify(mockItemPrescricaoProcedimentoSearchRepository, times(1)).deleteById(itemPrescricaoProcedimento.getId());
    }

    @Test
    @Transactional
    public void searchItemPrescricaoProcedimento() throws Exception {
        // Initialize the database
        itemPrescricaoProcedimentoRepository.saveAndFlush(itemPrescricaoProcedimento);
        when(mockItemPrescricaoProcedimentoSearchRepository.search(queryStringQuery("id:" + itemPrescricaoProcedimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(itemPrescricaoProcedimento), PageRequest.of(0, 1), 1));
        // Search the itemPrescricaoProcedimento
        restItemPrescricaoProcedimentoMockMvc.perform(get("/api/_search/item-prescricao-procedimentos?query=id:" + itemPrescricaoProcedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrescricaoProcedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoProcedimentoEspecial").value(hasItem(DEFAULT_TIPO_PROCEDIMENTO_ESPECIAL.toString())))
            .andExpect(jsonPath("$.[*].quantidadeOrteseProtese").value(hasItem(DEFAULT_QUANTIDADE_ORTESE_PROTESE)))
            .andExpect(jsonPath("$.[*].informacoes").value(hasItem(DEFAULT_INFORMACOES)))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)))
            .andExpect(jsonPath("$.[*].duracaoSolicitada").value(hasItem(DEFAULT_DURACAO_SOLICITADA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoProcedimento.class);
        ItemPrescricaoProcedimento itemPrescricaoProcedimento1 = new ItemPrescricaoProcedimento();
        itemPrescricaoProcedimento1.setId(1L);
        ItemPrescricaoProcedimento itemPrescricaoProcedimento2 = new ItemPrescricaoProcedimento();
        itemPrescricaoProcedimento2.setId(itemPrescricaoProcedimento1.getId());
        assertThat(itemPrescricaoProcedimento1).isEqualTo(itemPrescricaoProcedimento2);
        itemPrescricaoProcedimento2.setId(2L);
        assertThat(itemPrescricaoProcedimento1).isNotEqualTo(itemPrescricaoProcedimento2);
        itemPrescricaoProcedimento1.setId(null);
        assertThat(itemPrescricaoProcedimento1).isNotEqualTo(itemPrescricaoProcedimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPrescricaoProcedimentoDTO.class);
        ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO1 = new ItemPrescricaoProcedimentoDTO();
        itemPrescricaoProcedimentoDTO1.setId(1L);
        ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO2 = new ItemPrescricaoProcedimentoDTO();
        assertThat(itemPrescricaoProcedimentoDTO1).isNotEqualTo(itemPrescricaoProcedimentoDTO2);
        itemPrescricaoProcedimentoDTO2.setId(itemPrescricaoProcedimentoDTO1.getId());
        assertThat(itemPrescricaoProcedimentoDTO1).isEqualTo(itemPrescricaoProcedimentoDTO2);
        itemPrescricaoProcedimentoDTO2.setId(2L);
        assertThat(itemPrescricaoProcedimentoDTO1).isNotEqualTo(itemPrescricaoProcedimentoDTO2);
        itemPrescricaoProcedimentoDTO1.setId(null);
        assertThat(itemPrescricaoProcedimentoDTO1).isNotEqualTo(itemPrescricaoProcedimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemPrescricaoProcedimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemPrescricaoProcedimentoMapper.fromId(null)).isNull();
    }
}
