package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.MadreexamesApp;
import br.com.basis.madre.exames.domain.InformacoesComplementares;
import br.com.basis.madre.exames.repository.InformacoesComplementaresRepository;
import br.com.basis.madre.exames.service.InformacoesComplementaresService;
import br.com.basis.madre.exames.service.dto.InformacoesComplementaresDTO;
import br.com.basis.madre.exames.service.mapper.InformacoesComplementaresMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InformacoesComplementaresResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InformacoesComplementaresResourceIT {

    private static final Integer DEFAULT_PRONTUARIO = 1;
    private static final Integer UPDATED_PRONTUARIO = 2;

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    @Autowired
    private InformacoesComplementaresRepository informacoesComplementaresRepository;

    @Autowired
    private InformacoesComplementaresMapper informacoesComplementaresMapper;

    @Autowired
    private InformacoesComplementaresService informacoesComplementaresService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformacoesComplementaresMockMvc;

    private InformacoesComplementares informacoesComplementares;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformacoesComplementares createEntity(EntityManager em) {
        InformacoesComplementares informacoesComplementares = new InformacoesComplementares()
            .prontuario(DEFAULT_PRONTUARIO)
            .codigo(DEFAULT_CODIGO);
        return informacoesComplementares;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformacoesComplementares createUpdatedEntity(EntityManager em) {
        InformacoesComplementares informacoesComplementares = new InformacoesComplementares()
            .prontuario(UPDATED_PRONTUARIO)
            .codigo(UPDATED_CODIGO);
        return informacoesComplementares;
    }

    @BeforeEach
    public void initTest() {
        informacoesComplementares = createEntity(em);
    }

    @Test
    @Transactional
    public void createInformacoesComplementares() throws Exception {
        int databaseSizeBeforeCreate = informacoesComplementaresRepository.findAll().size();
        // Create the InformacoesComplementares
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(informacoesComplementares);
        restInformacoesComplementaresMockMvc.perform(post("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isCreated());

        // Validate the InformacoesComplementares in the database
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeCreate + 1);
        InformacoesComplementares testInformacoesComplementares = informacoesComplementaresList.get(informacoesComplementaresList.size() - 1);
        assertThat(testInformacoesComplementares.getProntuario()).isEqualTo(DEFAULT_PRONTUARIO);
        assertThat(testInformacoesComplementares.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void createInformacoesComplementaresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = informacoesComplementaresRepository.findAll().size();

        // Create the InformacoesComplementares with an existing ID
        informacoesComplementares.setId(1L);
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(informacoesComplementares);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformacoesComplementaresMockMvc.perform(post("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InformacoesComplementares in the database
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProntuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = informacoesComplementaresRepository.findAll().size();
        // set the field null
        informacoesComplementares.setProntuario(null);

        // Create the InformacoesComplementares, which fails.
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(informacoesComplementares);


        restInformacoesComplementaresMockMvc.perform(post("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isBadRequest());

        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        // Get all the informacoesComplementaresList
        restInformacoesComplementaresMockMvc.perform(get("/api/informacoes-complementares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informacoesComplementares.getId().intValue())))
            .andExpect(jsonPath("$.[*].prontuario").value(hasItem(DEFAULT_PRONTUARIO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }
    
    @Test
    @Transactional
    public void getInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        // Get the informacoesComplementares
        restInformacoesComplementaresMockMvc.perform(get("/api/informacoes-complementares/{id}", informacoesComplementares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informacoesComplementares.getId().intValue()))
            .andExpect(jsonPath("$.prontuario").value(DEFAULT_PRONTUARIO))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }
    @Test
    @Transactional
    public void getNonExistingInformacoesComplementares() throws Exception {
        // Get the informacoesComplementares
        restInformacoesComplementaresMockMvc.perform(get("/api/informacoes-complementares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        int databaseSizeBeforeUpdate = informacoesComplementaresRepository.findAll().size();

        // Update the informacoesComplementares
        InformacoesComplementares updatedInformacoesComplementares = informacoesComplementaresRepository.findById(informacoesComplementares.getId()).get();
        // Disconnect from session so that the updates on updatedInformacoesComplementares are not directly saved in db
        em.detach(updatedInformacoesComplementares);
        updatedInformacoesComplementares
            .prontuario(UPDATED_PRONTUARIO)
            .codigo(UPDATED_CODIGO);
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(updatedInformacoesComplementares);

        restInformacoesComplementaresMockMvc.perform(put("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isOk());

        // Validate the InformacoesComplementares in the database
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeUpdate);
        InformacoesComplementares testInformacoesComplementares = informacoesComplementaresList.get(informacoesComplementaresList.size() - 1);
        assertThat(testInformacoesComplementares.getProntuario()).isEqualTo(UPDATED_PRONTUARIO);
        assertThat(testInformacoesComplementares.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void updateNonExistingInformacoesComplementares() throws Exception {
        int databaseSizeBeforeUpdate = informacoesComplementaresRepository.findAll().size();

        // Create the InformacoesComplementares
        InformacoesComplementaresDTO informacoesComplementaresDTO = informacoesComplementaresMapper.toDto(informacoesComplementares);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformacoesComplementaresMockMvc.perform(put("/api/informacoes-complementares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informacoesComplementaresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InformacoesComplementares in the database
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        int databaseSizeBeforeDelete = informacoesComplementaresRepository.findAll().size();

        // Delete the informacoesComplementares
        restInformacoesComplementaresMockMvc.perform(delete("/api/informacoes-complementares/{id}", informacoesComplementares.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InformacoesComplementares> informacoesComplementaresList = informacoesComplementaresRepository.findAll();
        assertThat(informacoesComplementaresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInformacoesComplementares() throws Exception {
        // Initialize the database
        informacoesComplementaresRepository.saveAndFlush(informacoesComplementares);

        // Search the informacoesComplementares
        restInformacoesComplementaresMockMvc.perform(get("/api/_search/informacoes-complementares?query=id:" + informacoesComplementares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informacoesComplementares.getId().intValue())))
            .andExpect(jsonPath("$.[*].prontuario").value(hasItem(DEFAULT_PRONTUARIO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }
}
