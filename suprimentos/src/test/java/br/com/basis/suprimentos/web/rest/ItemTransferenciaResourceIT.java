package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.ItemTransferencia;
import br.com.basis.suprimentos.repository.ItemTransferenciaRepository;
import br.com.basis.suprimentos.repository.search.ItemTransferenciaSearchRepository;
import br.com.basis.suprimentos.service.ItemTransferenciaService;
import br.com.basis.suprimentos.service.dto.ItemTransferenciaDTO;
import br.com.basis.suprimentos.service.mapper.ItemTransferenciaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ItemTransferenciaResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ItemTransferenciaResourceIT {

    private static final Integer DEFAULT_QUANTIDADE_ENVIDADA = 1;
    private static final Integer UPDATED_QUANTIDADE_ENVIDADA = 2;

    @Autowired
    private ItemTransferenciaRepository itemTransferenciaRepository;

    @Autowired
    private ItemTransferenciaMapper itemTransferenciaMapper;

    @Autowired
    private ItemTransferenciaService itemTransferenciaService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.ItemTransferenciaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemTransferenciaSearchRepository mockItemTransferenciaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemTransferenciaMockMvc;

    private ItemTransferencia itemTransferencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemTransferencia createEntity(EntityManager em) {
        ItemTransferencia itemTransferencia = new ItemTransferencia();
        itemTransferencia.setQuantidadeEnvidada(DEFAULT_QUANTIDADE_ENVIDADA);
        return itemTransferencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemTransferencia createUpdatedEntity(EntityManager em) {
        ItemTransferencia itemTransferencia = new ItemTransferencia();
        itemTransferencia.setQuantidadeEnvidada(UPDATED_QUANTIDADE_ENVIDADA);
        return itemTransferencia;
    }

    @BeforeEach
    public void initTest() {
        itemTransferencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemTransferencia() throws Exception {
        int databaseSizeBeforeCreate = itemTransferenciaRepository.findAll().size();
        // Create the ItemTransferencia
        ItemTransferenciaDTO itemTransferenciaDTO = itemTransferenciaMapper.toDto(itemTransferencia);
        restItemTransferenciaMockMvc.perform(post("/api/item-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemTransferenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemTransferencia in the database
        List<ItemTransferencia> itemTransferenciaList = itemTransferenciaRepository.findAll();
        assertThat(itemTransferenciaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemTransferencia testItemTransferencia = itemTransferenciaList.get(itemTransferenciaList.size() - 1);
        assertThat(testItemTransferencia.getQuantidadeEnvidada()).isEqualTo(DEFAULT_QUANTIDADE_ENVIDADA);

        // Validate the ItemTransferencia in Elasticsearch
        verify(mockItemTransferenciaSearchRepository, times(1)).save(testItemTransferencia);
    }

    @Test
    @Transactional
    public void createItemTransferenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemTransferenciaRepository.findAll().size();

        // Create the ItemTransferencia with an existing ID
        itemTransferencia.setId(1L);
        ItemTransferenciaDTO itemTransferenciaDTO = itemTransferenciaMapper.toDto(itemTransferencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemTransferenciaMockMvc.perform(post("/api/item-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemTransferenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemTransferencia in the database
        List<ItemTransferencia> itemTransferenciaList = itemTransferenciaRepository.findAll();
        assertThat(itemTransferenciaList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemTransferencia in Elasticsearch
        verify(mockItemTransferenciaSearchRepository, times(0)).save(itemTransferencia);
    }


    @Test
    @Transactional
    public void checkQuantidadeEnvidadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemTransferenciaRepository.findAll().size();
        // set the field null
        itemTransferencia.setQuantidadeEnvidada(null);

        // Create the ItemTransferencia, which fails.
        ItemTransferenciaDTO itemTransferenciaDTO = itemTransferenciaMapper.toDto(itemTransferencia);


        restItemTransferenciaMockMvc.perform(post("/api/item-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemTransferenciaDTO)))
            .andExpect(status().isBadRequest());

        List<ItemTransferencia> itemTransferenciaList = itemTransferenciaRepository.findAll();
        assertThat(itemTransferenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemTransferencias() throws Exception {
        // Initialize the database
        itemTransferenciaRepository.saveAndFlush(itemTransferencia);

        // Get all the itemTransferenciaList
        restItemTransferenciaMockMvc.perform(get("/api/item-transferencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemTransferencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeEnvidada").value(hasItem(DEFAULT_QUANTIDADE_ENVIDADA)));
    }

    @Test
    @Transactional
    public void getItemTransferencia() throws Exception {
        // Initialize the database
        itemTransferenciaRepository.saveAndFlush(itemTransferencia);

        // Get the itemTransferencia
        restItemTransferenciaMockMvc.perform(get("/api/item-transferencias/{id}", itemTransferencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemTransferencia.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeEnvidada").value(DEFAULT_QUANTIDADE_ENVIDADA));
    }
    @Test
    @Transactional
    public void getNonExistingItemTransferencia() throws Exception {
        // Get the itemTransferencia
        restItemTransferenciaMockMvc.perform(get("/api/item-transferencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemTransferencia() throws Exception {
        // Initialize the database
        itemTransferenciaRepository.saveAndFlush(itemTransferencia);

        int databaseSizeBeforeUpdate = itemTransferenciaRepository.findAll().size();

        // Update the itemTransferencia
        ItemTransferencia updatedItemTransferencia = itemTransferenciaRepository.findById(itemTransferencia.getId()).get();
        // Disconnect from session so that the updates on updatedItemTransferencia are not directly saved in db
        em.detach(updatedItemTransferencia);
        updatedItemTransferencia
            .setQuantidadeEnvidada(UPDATED_QUANTIDADE_ENVIDADA);
        ItemTransferenciaDTO itemTransferenciaDTO = itemTransferenciaMapper.toDto(updatedItemTransferencia);

        restItemTransferenciaMockMvc.perform(put("/api/item-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemTransferenciaDTO)))
            .andExpect(status().isOk());

        // Validate the ItemTransferencia in the database
        List<ItemTransferencia> itemTransferenciaList = itemTransferenciaRepository.findAll();
        assertThat(itemTransferenciaList).hasSize(databaseSizeBeforeUpdate);
        ItemTransferencia testItemTransferencia = itemTransferenciaList.get(itemTransferenciaList.size() - 1);
        assertThat(testItemTransferencia.getQuantidadeEnvidada()).isEqualTo(UPDATED_QUANTIDADE_ENVIDADA);

        // Validate the ItemTransferencia in Elasticsearch
        verify(mockItemTransferenciaSearchRepository, times(1)).save(testItemTransferencia);
    }

    @Test
    @Transactional
    public void updateNonExistingItemTransferencia() throws Exception {
        int databaseSizeBeforeUpdate = itemTransferenciaRepository.findAll().size();

        // Create the ItemTransferencia
        ItemTransferenciaDTO itemTransferenciaDTO = itemTransferenciaMapper.toDto(itemTransferencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemTransferenciaMockMvc.perform(put("/api/item-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemTransferenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemTransferencia in the database
        List<ItemTransferencia> itemTransferenciaList = itemTransferenciaRepository.findAll();
        assertThat(itemTransferenciaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemTransferencia in Elasticsearch
        verify(mockItemTransferenciaSearchRepository, times(0)).save(itemTransferencia);
    }

    @Test
    @Transactional
    public void deleteItemTransferencia() throws Exception {
        // Initialize the database
        itemTransferenciaRepository.saveAndFlush(itemTransferencia);

        int databaseSizeBeforeDelete = itemTransferenciaRepository.findAll().size();

        // Delete the itemTransferencia
        restItemTransferenciaMockMvc.perform(delete("/api/item-transferencias/{id}", itemTransferencia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemTransferencia> itemTransferenciaList = itemTransferenciaRepository.findAll();
        assertThat(itemTransferenciaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemTransferencia in Elasticsearch
        verify(mockItemTransferenciaSearchRepository, times(1)).deleteById(itemTransferencia.getId());
    }

    @Test
    @Transactional
    public void searchItemTransferencia() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        itemTransferenciaRepository.saveAndFlush(itemTransferencia);
        when(mockItemTransferenciaSearchRepository.search(queryStringQuery("id:" + itemTransferencia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(itemTransferencia), PageRequest.of(0, 1), 1));

        // Search the itemTransferencia
        restItemTransferenciaMockMvc.perform(get("/api/_search/item-transferencias?query=id:" + itemTransferencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemTransferencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeEnvidada").value(hasItem(DEFAULT_QUANTIDADE_ENVIDADA)));
    }
}
