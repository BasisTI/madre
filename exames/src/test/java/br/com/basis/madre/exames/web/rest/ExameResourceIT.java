package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.MadreexamesApp;
import br.com.basis.madre.exames.domain.Exame;
import br.com.basis.madre.exames.repository.ExameRepository;
import br.com.basis.madre.exames.service.ExameService;
import br.com.basis.madre.exames.service.dto.ExameDTO;
import br.com.basis.madre.exames.service.mapper.ExameMapper;

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
 * Integration tests for the {@link ExameResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExameResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_USUAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_USUAL = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private ExameMapper exameMapper;

    @Autowired
    private ExameService exameService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExameMockMvc;

    private Exame exame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exame createEntity(EntityManager em) {
        Exame exame = new Exame()
            .nome(DEFAULT_NOME)
            .nomeUsual(DEFAULT_NOME_USUAL)
            .sigla(DEFAULT_SIGLA);
        return exame;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exame createUpdatedEntity(EntityManager em) {
        Exame exame = new Exame()
            .nome(UPDATED_NOME)
            .nomeUsual(UPDATED_NOME_USUAL)
            .sigla(UPDATED_SIGLA);
        return exame;
    }

    @BeforeEach
    public void initTest() {
        exame = createEntity(em);
    }

    @Test
    @Transactional
    public void createExame() throws Exception {
        int databaseSizeBeforeCreate = exameRepository.findAll().size();
        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);
        restExameMockMvc.perform(post("/api/exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isCreated());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeCreate + 1);
        Exame testExame = exameList.get(exameList.size() - 1);
        assertThat(testExame.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testExame.getNomeUsual()).isEqualTo(DEFAULT_NOME_USUAL);
        assertThat(testExame.getSigla()).isEqualTo(DEFAULT_SIGLA);
    }

    @Test
    @Transactional
    public void createExameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exameRepository.findAll().size();

        // Create the Exame with an existing ID
        exame.setId(1L);
        ExameDTO exameDTO = exameMapper.toDto(exame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExameMockMvc.perform(post("/api/exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = exameRepository.findAll().size();
        // set the field null
        exame.setNome(null);

        // Create the Exame, which fails.
        ExameDTO exameDTO = exameMapper.toDto(exame);


        restExameMockMvc.perform(post("/api/exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isBadRequest());

        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = exameRepository.findAll().size();
        // set the field null
        exame.setSigla(null);

        // Create the Exame, which fails.
        ExameDTO exameDTO = exameMapper.toDto(exame);


        restExameMockMvc.perform(post("/api/exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isBadRequest());

        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExames() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        // Get all the exameList
        restExameMockMvc.perform(get("/api/exames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeUsual").value(hasItem(DEFAULT_NOME_USUAL)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        // Get the exame
        restExameMockMvc.perform(get("/api/exames/{id}", exame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exame.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nomeUsual").value(DEFAULT_NOME_USUAL))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }
    @Test
    @Transactional
    public void getNonExistingExame() throws Exception {
        // Get the exame
        restExameMockMvc.perform(get("/api/exames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        int databaseSizeBeforeUpdate = exameRepository.findAll().size();

        // Update the exame
        Exame updatedExame = exameRepository.findById(exame.getId()).get();
        // Disconnect from session so that the updates on updatedExame are not directly saved in db
        em.detach(updatedExame);
        updatedExame
            .nome(UPDATED_NOME)
            .nomeUsual(UPDATED_NOME_USUAL)
            .sigla(UPDATED_SIGLA);
        ExameDTO exameDTO = exameMapper.toDto(updatedExame);

        restExameMockMvc.perform(put("/api/exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isOk());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
        Exame testExame = exameList.get(exameList.size() - 1);
        assertThat(testExame.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testExame.getNomeUsual()).isEqualTo(UPDATED_NOME_USUAL);
        assertThat(testExame.getSigla()).isEqualTo(UPDATED_SIGLA);
    }

    @Test
    @Transactional
    public void updateNonExistingExame() throws Exception {
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();

        // Create the Exame
        ExameDTO exameDTO = exameMapper.toDto(exame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExameMockMvc.perform(put("/api/exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        int databaseSizeBeforeDelete = exameRepository.findAll().size();

        // Delete the exame
        restExameMockMvc.perform(delete("/api/exames/{id}", exame.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        // Search the exame
        restExameMockMvc.perform(get("/api/_search/exames?query=id:" + exame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeUsual").value(hasItem(DEFAULT_NOME_USUAL)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
}
