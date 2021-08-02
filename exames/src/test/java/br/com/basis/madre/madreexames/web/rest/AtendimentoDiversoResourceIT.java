package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.AtendimentoDiverso;
import br.com.basis.madre.madreexames.repository.AtendimentoDiversoRepository;
import br.com.basis.madre.madreexames.repository.search.AtendimentoDiversoSearchRepository;
import br.com.basis.madre.madreexames.service.AtendimentoDiversoService;
import br.com.basis.madre.madreexames.service.dto.AtendimentoDiversoDTO;
import br.com.basis.madre.madreexames.service.mapper.AtendimentoDiversoMapper;

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
 * Integration tests for the {@link AtendimentoDiversoResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AtendimentoDiversoResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;


    @Autowired
    private AtendimentoDiversoRepository atendimentoDiversoRepository;

    @Autowired
    private AtendimentoDiversoMapper atendimentoDiversoMapper;

    @Autowired
    private AtendimentoDiversoService atendimentoDiversoService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.AtendimentoDiversoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AtendimentoDiversoSearchRepository mockAtendimentoDiversoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtendimentoDiversoMockMvc;

    private AtendimentoDiverso atendimentoDiverso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtendimentoDiverso createEntity(EntityManager em) {
        AtendimentoDiverso atendimentoDiverso = new AtendimentoDiverso()
            .codigo(DEFAULT_CODIGO);
        return atendimentoDiverso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtendimentoDiverso createUpdatedEntity(EntityManager em) {
        AtendimentoDiverso atendimentoDiverso = new AtendimentoDiverso()
            .codigo(UPDATED_CODIGO);
        return atendimentoDiverso;
    }

    @BeforeEach
    public void initTest() {
        atendimentoDiverso = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtendimentoDiverso() throws Exception {
        int databaseSizeBeforeCreate = atendimentoDiversoRepository.findAll().size();
        // Create the AtendimentoDiverso
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);
        restAtendimentoDiversoMockMvc.perform(post("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isCreated());

        // Validate the AtendimentoDiverso in the database
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeCreate + 1);
        AtendimentoDiverso testAtendimentoDiverso = atendimentoDiversoList.get(atendimentoDiversoList.size() - 1);
        assertThat(testAtendimentoDiverso.getCodigo()).isEqualTo(DEFAULT_CODIGO);

        // Validate the AtendimentoDiverso in Elasticsearch
        verify(mockAtendimentoDiversoSearchRepository, times(1)).save(testAtendimentoDiverso);
    }

    @Test
    @Transactional
    public void createAtendimentoDiversoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atendimentoDiversoRepository.findAll().size();

        // Create the AtendimentoDiverso with an existing ID
        atendimentoDiverso.setId(1L);
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtendimentoDiversoMockMvc.perform(post("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AtendimentoDiverso in the database
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeCreate);

        // Validate the AtendimentoDiverso in Elasticsearch
        verify(mockAtendimentoDiversoSearchRepository, times(0)).save(atendimentoDiverso);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = atendimentoDiversoRepository.findAll().size();
        // set the field null
        atendimentoDiverso.setCodigo(null);

        // Create the AtendimentoDiverso, which fails.
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);


        restAtendimentoDiversoMockMvc.perform(post("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isBadRequest());

        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllAtendimentoDiversos() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        // Get all the atendimentoDiversoList
        restAtendimentoDiversoMockMvc.perform(get("/api/atendimento-diversos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimentoDiverso.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }

    @Test
    @Transactional
    public void getAtendimentoDiverso() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        // Get the atendimentoDiverso
        restAtendimentoDiversoMockMvc.perform(get("/api/atendimento-diversos/{id}", atendimentoDiverso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atendimentoDiverso.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }
    @Test
    @Transactional
    public void getNonExistingAtendimentoDiverso() throws Exception {
        // Get the atendimentoDiverso
        restAtendimentoDiversoMockMvc.perform(get("/api/atendimento-diversos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtendimentoDiverso() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        int databaseSizeBeforeUpdate = atendimentoDiversoRepository.findAll().size();

        // Update the atendimentoDiverso
        AtendimentoDiverso updatedAtendimentoDiverso = atendimentoDiversoRepository.findById(atendimentoDiverso.getId()).get();
        // Disconnect from session so that the updates on updatedAtendimentoDiverso are not directly saved in db
        em.detach(updatedAtendimentoDiverso);
        updatedAtendimentoDiverso
            .codigo(UPDATED_CODIGO);
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(updatedAtendimentoDiverso);

        restAtendimentoDiversoMockMvc.perform(put("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isOk());

        // Validate the AtendimentoDiverso in the database
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeUpdate);
        AtendimentoDiverso testAtendimentoDiverso = atendimentoDiversoList.get(atendimentoDiversoList.size() - 1);
        assertThat(testAtendimentoDiverso.getCodigo()).isEqualTo(UPDATED_CODIGO);

        // Validate the AtendimentoDiverso in Elasticsearch
        verify(mockAtendimentoDiversoSearchRepository, times(1)).save(testAtendimentoDiverso);
    }

    @Test
    @Transactional
    public void updateNonExistingAtendimentoDiverso() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoDiversoRepository.findAll().size();

        // Create the AtendimentoDiverso
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtendimentoDiversoMockMvc.perform(put("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AtendimentoDiverso in the database
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AtendimentoDiverso in Elasticsearch
        verify(mockAtendimentoDiversoSearchRepository, times(0)).save(atendimentoDiverso);
    }

    @Test
    @Transactional
    public void deleteAtendimentoDiverso() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        int databaseSizeBeforeDelete = atendimentoDiversoRepository.findAll().size();

        // Delete the atendimentoDiverso
        restAtendimentoDiversoMockMvc.perform(delete("/api/atendimento-diversos/{id}", atendimentoDiverso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AtendimentoDiverso in Elasticsearch
        verify(mockAtendimentoDiversoSearchRepository, times(1)).deleteById(atendimentoDiverso.getId());
    }

    @Test
    @Transactional
    public void searchAtendimentoDiverso() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);
        when(mockAtendimentoDiversoSearchRepository.search(queryStringQuery("id:" + atendimentoDiverso.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(atendimentoDiverso), PageRequest.of(0, 1), 1));

        // Search the atendimentoDiverso
        restAtendimentoDiversoMockMvc.perform(get("/api/_search/atendimento-diversos?query=id:" + atendimentoDiverso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimentoDiverso.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }
}
