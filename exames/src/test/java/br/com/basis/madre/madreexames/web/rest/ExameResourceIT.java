package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.Exame;
import br.com.basis.madre.madreexames.repository.ExameRepository;
import br.com.basis.madre.madreexames.repository.search.ExameSearchRepository;
import br.com.basis.madre.madreexames.service.ExameService;
import br.com.basis.madre.madreexames.service.dto.ExameDTO;
import br.com.basis.madre.madreexames.service.mapper.ExameMapper;

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
 * Integration tests for the {@link ExameResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExameResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_USUAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_USUAL = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_IMPRESSAO = false;
    private static final Boolean UPDATED_IMPRESSAO = true;

    private static final Boolean DEFAULT_CONSISTE_INTERFACEAMENTO = false;
    private static final Boolean UPDATED_CONSISTE_INTERFACEAMENTO = true;

    private static final Boolean DEFAULT_ANEXA_DOCUMENTOS = false;
    private static final Boolean UPDATED_ANEXA_DOCUMENTOS = true;

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private ExameMapper exameMapper;

    @Autowired
    private ExameService exameService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.ExameSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExameSearchRepository mockExameSearchRepository;

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
            .sigla(DEFAULT_SIGLA)
            .ativo(DEFAULT_ATIVO)
            .impressao(DEFAULT_IMPRESSAO)
            .consisteInterfaceamento(DEFAULT_CONSISTE_INTERFACEAMENTO)
            .anexaDocumentos(DEFAULT_ANEXA_DOCUMENTOS);
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
            .sigla(UPDATED_SIGLA)
            .ativo(UPDATED_ATIVO)
            .impressao(UPDATED_IMPRESSAO)
            .consisteInterfaceamento(UPDATED_CONSISTE_INTERFACEAMENTO)
            .anexaDocumentos(UPDATED_ANEXA_DOCUMENTOS);
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
        assertThat(testExame.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testExame.isImpressao()).isEqualTo(DEFAULT_IMPRESSAO);
        assertThat(testExame.isConsisteInterfaceamento()).isEqualTo(DEFAULT_CONSISTE_INTERFACEAMENTO);
        assertThat(testExame.isAnexaDocumentos()).isEqualTo(DEFAULT_ANEXA_DOCUMENTOS);

        // Validate the Exame in Elasticsearch
        verify(mockExameSearchRepository, times(1)).save(testExame);
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

        // Validate the Exame in Elasticsearch
        verify(mockExameSearchRepository, times(0)).save(exame);
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
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = exameRepository.findAll().size();
        // set the field null
        exame.setAtivo(null);

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
    public void checkImpressaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = exameRepository.findAll().size();
        // set the field null
        exame.setImpressao(null);

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
    public void checkConsisteInterfaceamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = exameRepository.findAll().size();
        // set the field null
        exame.setConsisteInterfaceamento(null);

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
    public void checkAnexaDocumentosIsRequired() throws Exception {
        int databaseSizeBeforeTest = exameRepository.findAll().size();
        // set the field null
        exame.setAnexaDocumentos(null);

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
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].impressao").value(hasItem(DEFAULT_IMPRESSAO.booleanValue())))
            .andExpect(jsonPath("$.[*].consisteInterfaceamento").value(hasItem(DEFAULT_CONSISTE_INTERFACEAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].anexaDocumentos").value(hasItem(DEFAULT_ANEXA_DOCUMENTOS.booleanValue())));
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
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.impressao").value(DEFAULT_IMPRESSAO.booleanValue()))
            .andExpect(jsonPath("$.consisteInterfaceamento").value(DEFAULT_CONSISTE_INTERFACEAMENTO.booleanValue()))
            .andExpect(jsonPath("$.anexaDocumentos").value(DEFAULT_ANEXA_DOCUMENTOS.booleanValue()));
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
            .sigla(UPDATED_SIGLA)
            .ativo(UPDATED_ATIVO)
            .impressao(UPDATED_IMPRESSAO)
            .consisteInterfaceamento(UPDATED_CONSISTE_INTERFACEAMENTO)
            .anexaDocumentos(UPDATED_ANEXA_DOCUMENTOS);
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
        assertThat(testExame.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testExame.isImpressao()).isEqualTo(UPDATED_IMPRESSAO);
        assertThat(testExame.isConsisteInterfaceamento()).isEqualTo(UPDATED_CONSISTE_INTERFACEAMENTO);
        assertThat(testExame.isAnexaDocumentos()).isEqualTo(UPDATED_ANEXA_DOCUMENTOS);

        // Validate the Exame in Elasticsearch
        verify(mockExameSearchRepository, times(1)).save(testExame);
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

        // Validate the Exame in Elasticsearch
        verify(mockExameSearchRepository, times(0)).save(exame);
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

        // Validate the Exame in Elasticsearch
        verify(mockExameSearchRepository, times(1)).deleteById(exame.getId());
    }

    @Test
    @Transactional
    public void searchExame() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        exameRepository.saveAndFlush(exame);
        when(mockExameSearchRepository.search(queryStringQuery("id:" + exame.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(exame), PageRequest.of(0, 1), 1));

        // Search the exame
        restExameMockMvc.perform(get("/api/_search/exames?query=id:" + exame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeUsual").value(hasItem(DEFAULT_NOME_USUAL)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].impressao").value(hasItem(DEFAULT_IMPRESSAO.booleanValue())))
            .andExpect(jsonPath("$.[*].consisteInterfaceamento").value(hasItem(DEFAULT_CONSISTE_INTERFACEAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].anexaDocumentos").value(hasItem(DEFAULT_ANEXA_DOCUMENTOS.booleanValue())));
    }
}
