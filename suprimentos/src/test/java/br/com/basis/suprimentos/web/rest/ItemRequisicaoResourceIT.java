package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.ItemRequisicao;
import br.com.basis.suprimentos.repository.ItemRequisicaoRepository;
import br.com.basis.suprimentos.repository.search.ItemRequisicaoSearchRepository;
import br.com.basis.suprimentos.service.ItemRequisicaoService;
import br.com.basis.suprimentos.service.dto.ItemRequisicaoDTO;
import br.com.basis.suprimentos.service.mapper.ItemRequisicaoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

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
 * Integration tests for the {@link ItemRequisicaoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ItemRequisicaoResourceIT {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    @Autowired
    private ItemRequisicaoRepository itemRequisicaoRepository;

    @Autowired
    private ItemRequisicaoMapper itemRequisicaoMapper;

    @Autowired
    private ItemRequisicaoService itemRequisicaoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.ItemRequisicaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemRequisicaoSearchRepository mockItemRequisicaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemRequisicaoMockMvc;

    private ItemRequisicao itemRequisicao;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemRequisicao createEntity(EntityManager em) {
        ItemRequisicao itemRequisicao = new ItemRequisicao();
        itemRequisicao.setQuantidade(DEFAULT_QUANTIDADE);
        return itemRequisicao;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemRequisicao createUpdatedEntity(EntityManager em) {
        ItemRequisicao itemRequisicao = new ItemRequisicao();
        itemRequisicao.setQuantidade(UPDATED_QUANTIDADE);
        return itemRequisicao;
    }

    @BeforeEach
    public void initTest() {
        itemRequisicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemRequisicao() throws Exception {
        int databaseSizeBeforeCreate = itemRequisicaoRepository.findAll().size();
        // Create the ItemRequisicao
        ItemRequisicaoDTO itemRequisicaoDTO = itemRequisicaoMapper.toDto(itemRequisicao);
        restItemRequisicaoMockMvc.perform(post("/api/item-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemRequisicaoDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemRequisicao in the database
        List<ItemRequisicao> itemRequisicaoList = itemRequisicaoRepository.findAll();
        assertThat(itemRequisicaoList).hasSize(databaseSizeBeforeCreate + 1);
        ItemRequisicao testItemRequisicao = itemRequisicaoList.get(itemRequisicaoList.size() - 1);
        assertThat(testItemRequisicao.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);

        // Validate the ItemRequisicao in Elasticsearch
        verify(mockItemRequisicaoSearchRepository, times(1)).save(testItemRequisicao);
    }

    @Test
    @Transactional
    public void createItemRequisicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemRequisicaoRepository.findAll().size();

        // Create the ItemRequisicao with an existing ID
        itemRequisicao.setId(1L);
        ItemRequisicaoDTO itemRequisicaoDTO = itemRequisicaoMapper.toDto(itemRequisicao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemRequisicaoMockMvc.perform(post("/api/item-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemRequisicaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemRequisicao in the database
        List<ItemRequisicao> itemRequisicaoList = itemRequisicaoRepository.findAll();
        assertThat(itemRequisicaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemRequisicao in Elasticsearch
        verify(mockItemRequisicaoSearchRepository, times(0)).save(itemRequisicao);
    }


    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRequisicaoRepository.findAll().size();
        // set the field null
        itemRequisicao.setQuantidade(null);

        // Create the ItemRequisicao, which fails.
        ItemRequisicaoDTO itemRequisicaoDTO = itemRequisicaoMapper.toDto(itemRequisicao);


        restItemRequisicaoMockMvc.perform(post("/api/item-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemRequisicaoDTO)))
            .andExpect(status().isBadRequest());

        List<ItemRequisicao> itemRequisicaoList = itemRequisicaoRepository.findAll();
        assertThat(itemRequisicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemRequisicaos() throws Exception {
        // Initialize the database
        itemRequisicaoRepository.saveAndFlush(itemRequisicao);

        // Get all the itemRequisicaoList
        restItemRequisicaoMockMvc.perform(get("/api/item-requisicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemRequisicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)));
    }

    @Test
    @Transactional
    public void getItemRequisicao() throws Exception {
        // Initialize the database
        itemRequisicaoRepository.saveAndFlush(itemRequisicao);

        // Get the itemRequisicao
        restItemRequisicaoMockMvc.perform(get("/api/item-requisicaos/{id}", itemRequisicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemRequisicao.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingItemRequisicao() throws Exception {
        // Get the itemRequisicao
        restItemRequisicaoMockMvc.perform(get("/api/item-requisicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemRequisicao() throws Exception {
        // Initialize the database
        itemRequisicaoRepository.saveAndFlush(itemRequisicao);

        int databaseSizeBeforeUpdate = itemRequisicaoRepository.findAll().size();

        // Update the itemRequisicao
        ItemRequisicao updatedItemRequisicao = itemRequisicaoRepository.findById(itemRequisicao.getId()).get();
        // Disconnect from session so that the updates on updatedItemRequisicao are not directly saved in db
        em.detach(updatedItemRequisicao);
        updatedItemRequisicao
            .setQuantidade(UPDATED_QUANTIDADE);
        ItemRequisicaoDTO itemRequisicaoDTO = itemRequisicaoMapper.toDto(updatedItemRequisicao);

        restItemRequisicaoMockMvc.perform(put("/api/item-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemRequisicaoDTO)))
            .andExpect(status().isOk());

        // Validate the ItemRequisicao in the database
        List<ItemRequisicao> itemRequisicaoList = itemRequisicaoRepository.findAll();
        assertThat(itemRequisicaoList).hasSize(databaseSizeBeforeUpdate);
        ItemRequisicao testItemRequisicao = itemRequisicaoList.get(itemRequisicaoList.size() - 1);
        assertThat(testItemRequisicao.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);

        // Validate the ItemRequisicao in Elasticsearch
        verify(mockItemRequisicaoSearchRepository, times(1)).save(testItemRequisicao);
    }

    @Test
    @Transactional
    public void updateNonExistingItemRequisicao() throws Exception {
        int databaseSizeBeforeUpdate = itemRequisicaoRepository.findAll().size();

        // Create the ItemRequisicao
        ItemRequisicaoDTO itemRequisicaoDTO = itemRequisicaoMapper.toDto(itemRequisicao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemRequisicaoMockMvc.perform(put("/api/item-requisicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemRequisicaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemRequisicao in the database
        List<ItemRequisicao> itemRequisicaoList = itemRequisicaoRepository.findAll();
        assertThat(itemRequisicaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemRequisicao in Elasticsearch
        verify(mockItemRequisicaoSearchRepository, times(0)).save(itemRequisicao);
    }

    @Test
    @Transactional
    public void deleteItemRequisicao() throws Exception {
        // Initialize the database
        itemRequisicaoRepository.saveAndFlush(itemRequisicao);

        int databaseSizeBeforeDelete = itemRequisicaoRepository.findAll().size();

        // Delete the itemRequisicao
        restItemRequisicaoMockMvc.perform(delete("/api/item-requisicaos/{id}", itemRequisicao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemRequisicao> itemRequisicaoList = itemRequisicaoRepository.findAll();
        assertThat(itemRequisicaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemRequisicao in Elasticsearch
        verify(mockItemRequisicaoSearchRepository, times(1)).deleteById(itemRequisicao.getId());
    }

    @Test
    @Transactional
    public void searchItemRequisicao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        itemRequisicaoRepository.saveAndFlush(itemRequisicao);
        when(mockItemRequisicaoSearchRepository.search(queryStringQuery("id:" + itemRequisicao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(itemRequisicao), PageRequest.of(0, 1), 1));

        // Search the itemRequisicao
        restItemRequisicaoMockMvc.perform(get("/api/_search/item-requisicaos?query=id:" + itemRequisicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemRequisicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)));
    }
}
