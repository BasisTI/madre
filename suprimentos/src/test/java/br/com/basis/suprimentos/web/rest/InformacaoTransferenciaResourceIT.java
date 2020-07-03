package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.InformacaoTransferencia;
import br.com.basis.suprimentos.repository.InformacaoTransferenciaRepository;
import br.com.basis.suprimentos.repository.search.InformacaoTransferenciaSearchRepository;
import br.com.basis.suprimentos.service.InformacaoTransferenciaService;
import br.com.basis.suprimentos.service.dto.InformacaoTransferenciaDTO;
import br.com.basis.suprimentos.service.mapper.InformacaoTransferenciaMapper;
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
 * Integration tests for the {@link InformacaoTransferenciaResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InformacaoTransferenciaResourceIT {

    private static final Boolean DEFAULT_ATIVA = false;
    private static final Boolean UPDATED_ATIVA = true;

    private static final Boolean DEFAULT_EFETIVADA = false;
    private static final Boolean UPDATED_EFETIVADA = true;

    @Autowired
    private InformacaoTransferenciaRepository informacaoTransferenciaRepository;

    @Autowired
    private InformacaoTransferenciaMapper informacaoTransferenciaMapper;

    @Autowired
    private InformacaoTransferenciaService informacaoTransferenciaService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.InformacaoTransferenciaSearchRepositoryMockConfiguration
     */
    @Autowired
    private InformacaoTransferenciaSearchRepository mockInformacaoTransferenciaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformacaoTransferenciaMockMvc;

    private InformacaoTransferencia informacaoTransferencia;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformacaoTransferencia createEntity(EntityManager em) {
        InformacaoTransferencia informacaoTransferencia = new InformacaoTransferencia();
        informacaoTransferencia.setAtiva(DEFAULT_ATIVA);
        informacaoTransferencia.setEfetivada(DEFAULT_EFETIVADA);
        return informacaoTransferencia;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformacaoTransferencia createUpdatedEntity(EntityManager em) {
        InformacaoTransferencia informacaoTransferencia = new InformacaoTransferencia();
        informacaoTransferencia.setAtiva(UPDATED_ATIVA);
        informacaoTransferencia.setEfetivada(UPDATED_EFETIVADA);
        return informacaoTransferencia;
    }

    @BeforeEach
    public void initTest() {
        informacaoTransferencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createInformacaoTransferencia() throws Exception {
        int databaseSizeBeforeCreate = informacaoTransferenciaRepository.findAll().size();
        // Create the InformacaoTransferencia
        InformacaoTransferenciaDTO informacaoTransferenciaDTO = informacaoTransferenciaMapper.toDto(informacaoTransferencia);
        restInformacaoTransferenciaMockMvc.perform(post("/api/informacao-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacaoTransferenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the InformacaoTransferencia in the database
        List<InformacaoTransferencia> informacaoTransferenciaList = informacaoTransferenciaRepository.findAll();
        assertThat(informacaoTransferenciaList).hasSize(databaseSizeBeforeCreate + 1);
        InformacaoTransferencia testInformacaoTransferencia = informacaoTransferenciaList.get(informacaoTransferenciaList.size() - 1);
        assertThat(testInformacaoTransferencia.getAtiva()).isEqualTo(DEFAULT_ATIVA);
        assertThat(testInformacaoTransferencia.getEfetivada()).isEqualTo(DEFAULT_EFETIVADA);

        // Validate the InformacaoTransferencia in Elasticsearch
        verify(mockInformacaoTransferenciaSearchRepository, times(1)).save(testInformacaoTransferencia);
    }

    @Test
    @Transactional
    public void createInformacaoTransferenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = informacaoTransferenciaRepository.findAll().size();

        // Create the InformacaoTransferencia with an existing ID
        informacaoTransferencia.setId(1L);
        InformacaoTransferenciaDTO informacaoTransferenciaDTO = informacaoTransferenciaMapper.toDto(informacaoTransferencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformacaoTransferenciaMockMvc.perform(post("/api/informacao-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacaoTransferenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InformacaoTransferencia in the database
        List<InformacaoTransferencia> informacaoTransferenciaList = informacaoTransferenciaRepository.findAll();
        assertThat(informacaoTransferenciaList).hasSize(databaseSizeBeforeCreate);

        // Validate the InformacaoTransferencia in Elasticsearch
        verify(mockInformacaoTransferenciaSearchRepository, times(0)).save(informacaoTransferencia);
    }


    @Test
    @Transactional
    public void getAllInformacaoTransferencias() throws Exception {
        // Initialize the database
        informacaoTransferenciaRepository.saveAndFlush(informacaoTransferencia);

        // Get all the informacaoTransferenciaList
        restInformacaoTransferenciaMockMvc.perform(get("/api/informacao-transferencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informacaoTransferencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA.booleanValue())))
            .andExpect(jsonPath("$.[*].efetivada").value(hasItem(DEFAULT_EFETIVADA.booleanValue())));
    }

    @Test
    @Transactional
    public void getInformacaoTransferencia() throws Exception {
        // Initialize the database
        informacaoTransferenciaRepository.saveAndFlush(informacaoTransferencia);

        // Get the informacaoTransferencia
        restInformacaoTransferenciaMockMvc.perform(get("/api/informacao-transferencias/{id}", informacaoTransferencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informacaoTransferencia.getId().intValue()))
            .andExpect(jsonPath("$.ativa").value(DEFAULT_ATIVA.booleanValue()))
            .andExpect(jsonPath("$.efetivada").value(DEFAULT_EFETIVADA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInformacaoTransferencia() throws Exception {
        // Get the informacaoTransferencia
        restInformacaoTransferenciaMockMvc.perform(get("/api/informacao-transferencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformacaoTransferencia() throws Exception {
        // Initialize the database
        informacaoTransferenciaRepository.saveAndFlush(informacaoTransferencia);

        int databaseSizeBeforeUpdate = informacaoTransferenciaRepository.findAll().size();

        // Update the informacaoTransferencia
        InformacaoTransferencia updatedInformacaoTransferencia = informacaoTransferenciaRepository.findById(informacaoTransferencia.getId()).get();
        // Disconnect from session so that the updates on updatedInformacaoTransferencia are not directly saved in db
        em.detach(updatedInformacaoTransferencia);
        updatedInformacaoTransferencia
            .setAtiva(UPDATED_ATIVA);
        updatedInformacaoTransferencia.setEfetivada(UPDATED_EFETIVADA);
        InformacaoTransferenciaDTO informacaoTransferenciaDTO = informacaoTransferenciaMapper.toDto(updatedInformacaoTransferencia);

        restInformacaoTransferenciaMockMvc.perform(put("/api/informacao-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacaoTransferenciaDTO)))
            .andExpect(status().isOk());

        // Validate the InformacaoTransferencia in the database
        List<InformacaoTransferencia> informacaoTransferenciaList = informacaoTransferenciaRepository.findAll();
        assertThat(informacaoTransferenciaList).hasSize(databaseSizeBeforeUpdate);
        InformacaoTransferencia testInformacaoTransferencia = informacaoTransferenciaList.get(informacaoTransferenciaList.size() - 1);
        assertThat(testInformacaoTransferencia.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testInformacaoTransferencia.getEfetivada()).isEqualTo(UPDATED_EFETIVADA);

        // Validate the InformacaoTransferencia in Elasticsearch
        verify(mockInformacaoTransferenciaSearchRepository, times(1)).save(testInformacaoTransferencia);
    }

    @Test
    @Transactional
    public void updateNonExistingInformacaoTransferencia() throws Exception {
        int databaseSizeBeforeUpdate = informacaoTransferenciaRepository.findAll().size();

        // Create the InformacaoTransferencia
        InformacaoTransferenciaDTO informacaoTransferenciaDTO = informacaoTransferenciaMapper.toDto(informacaoTransferencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformacaoTransferenciaMockMvc.perform(put("/api/informacao-transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacaoTransferenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InformacaoTransferencia in the database
        List<InformacaoTransferencia> informacaoTransferenciaList = informacaoTransferenciaRepository.findAll();
        assertThat(informacaoTransferenciaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InformacaoTransferencia in Elasticsearch
        verify(mockInformacaoTransferenciaSearchRepository, times(0)).save(informacaoTransferencia);
    }

    @Test
    @Transactional
    public void deleteInformacaoTransferencia() throws Exception {
        // Initialize the database
        informacaoTransferenciaRepository.saveAndFlush(informacaoTransferencia);

        int databaseSizeBeforeDelete = informacaoTransferenciaRepository.findAll().size();

        // Delete the informacaoTransferencia
        restInformacaoTransferenciaMockMvc.perform(delete("/api/informacao-transferencias/{id}", informacaoTransferencia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InformacaoTransferencia> informacaoTransferenciaList = informacaoTransferenciaRepository.findAll();
        assertThat(informacaoTransferenciaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InformacaoTransferencia in Elasticsearch
        verify(mockInformacaoTransferenciaSearchRepository, times(1)).deleteById(informacaoTransferencia.getId());
    }

    @Test
    @Transactional
    public void searchInformacaoTransferencia() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        informacaoTransferenciaRepository.saveAndFlush(informacaoTransferencia);
        when(mockInformacaoTransferenciaSearchRepository.search(queryStringQuery("id:" + informacaoTransferencia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(informacaoTransferencia), PageRequest.of(0, 1), 1));

        // Search the informacaoTransferencia
        restInformacaoTransferenciaMockMvc.perform(get("/api/_search/informacao-transferencias?query=id:" + informacaoTransferencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informacaoTransferencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA.booleanValue())))
            .andExpect(jsonPath("$.[*].efetivada").value(hasItem(DEFAULT_EFETIVADA.booleanValue())));
    }
}
