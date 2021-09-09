package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Graduacao;
import br.com.basis.madre.seguranca.repository.GraduacaoRepository;
import br.com.basis.madre.seguranca.repository.search.GraduacaoSearchRepository;
import br.com.basis.madre.seguranca.service.GraduacaoService;
import br.com.basis.madre.seguranca.service.dto.GraduacaoDTO;
import br.com.basis.madre.seguranca.service.mapper.GraduacaoMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.seguranca.domain.enumeration.SituacaoGraduacao;
/**
 * Integration tests for the {@link GraduacaoResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GraduacaoResourceIT {

    private static final String DEFAULT_CURSO = "AAAAAAAAAA";
    private static final String UPDATED_CURSO = "BBBBBBBBBB";

    private static final SituacaoGraduacao DEFAULT_SITUACAO = SituacaoGraduacao.EM_ANDAMENTO;
    private static final SituacaoGraduacao UPDATED_SITUACAO = SituacaoGraduacao.CONCLUIDA;

    private static final LocalDate DEFAULT_ANO_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ANO_FIM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANO_FIM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEMESTRE = "AAAAAAAAAA";
    private static final String UPDATED_SEMESTRE = "BBBBBBBBBB";

    private static final String DEFAULT_NRO_REG_CONSELHO = "AAAAAAAAAA";
    private static final String UPDATED_NRO_REG_CONSELHO = "BBBBBBBBBB";

    @Autowired
    private GraduacaoRepository graduacaoRepository;

    @Autowired
    private GraduacaoMapper graduacaoMapper;

    @Autowired
    private GraduacaoService graduacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.GraduacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private GraduacaoSearchRepository mockGraduacaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGraduacaoMockMvc;

    private Graduacao graduacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Graduacao createEntity(EntityManager em) {
        Graduacao graduacao = new Graduacao()
            .curso(DEFAULT_CURSO)
            .situacao(DEFAULT_SITUACAO)
            .anoInicio(DEFAULT_ANO_INICIO)
            .anoFim(DEFAULT_ANO_FIM)
            .semestre(DEFAULT_SEMESTRE)
            .nroRegConselho(DEFAULT_NRO_REG_CONSELHO);
        return graduacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Graduacao createUpdatedEntity(EntityManager em) {
        Graduacao graduacao = new Graduacao()
            .curso(UPDATED_CURSO)
            .situacao(UPDATED_SITUACAO)
            .anoInicio(UPDATED_ANO_INICIO)
            .anoFim(UPDATED_ANO_FIM)
            .semestre(UPDATED_SEMESTRE)
            .nroRegConselho(UPDATED_NRO_REG_CONSELHO);
        return graduacao;
    }

    @BeforeEach
    public void initTest() {
        graduacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createGraduacao() throws Exception {
        int databaseSizeBeforeCreate = graduacaoRepository.findAll().size();
        // Create the Graduacao
        GraduacaoDTO graduacaoDTO = graduacaoMapper.toDto(graduacao);
        restGraduacaoMockMvc.perform(post("/api/graduacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(graduacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Graduacao in the database
        List<Graduacao> graduacaoList = graduacaoRepository.findAll();
        assertThat(graduacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Graduacao testGraduacao = graduacaoList.get(graduacaoList.size() - 1);
        assertThat(testGraduacao.getCurso()).isEqualTo(DEFAULT_CURSO);
        assertThat(testGraduacao.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testGraduacao.getAnoInicio()).isEqualTo(DEFAULT_ANO_INICIO);
        assertThat(testGraduacao.getAnoFim()).isEqualTo(DEFAULT_ANO_FIM);
        assertThat(testGraduacao.getSemestre()).isEqualTo(DEFAULT_SEMESTRE);
        assertThat(testGraduacao.getNroRegConselho()).isEqualTo(DEFAULT_NRO_REG_CONSELHO);

        // Validate the Graduacao in Elasticsearch
        verify(mockGraduacaoSearchRepository, times(1)).save(testGraduacao);
    }

    @Test
    @Transactional
    public void createGraduacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = graduacaoRepository.findAll().size();

        // Create the Graduacao with an existing ID
        graduacao.setId(1L);
        GraduacaoDTO graduacaoDTO = graduacaoMapper.toDto(graduacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGraduacaoMockMvc.perform(post("/api/graduacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(graduacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Graduacao in the database
        List<Graduacao> graduacaoList = graduacaoRepository.findAll();
        assertThat(graduacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Graduacao in Elasticsearch
        verify(mockGraduacaoSearchRepository, times(0)).save(graduacao);
    }


    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = graduacaoRepository.findAll().size();
        // set the field null
        graduacao.setSituacao(null);

        // Create the Graduacao, which fails.
        GraduacaoDTO graduacaoDTO = graduacaoMapper.toDto(graduacao);


        restGraduacaoMockMvc.perform(post("/api/graduacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(graduacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Graduacao> graduacaoList = graduacaoRepository.findAll();
        assertThat(graduacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnoInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = graduacaoRepository.findAll().size();
        // set the field null
        graduacao.setAnoInicio(null);

        // Create the Graduacao, which fails.
        GraduacaoDTO graduacaoDTO = graduacaoMapper.toDto(graduacao);


        restGraduacaoMockMvc.perform(post("/api/graduacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(graduacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Graduacao> graduacaoList = graduacaoRepository.findAll();
        assertThat(graduacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGraduacaos() throws Exception {
        // Initialize the database
        graduacaoRepository.saveAndFlush(graduacao);

        // Get all the graduacaoList
        restGraduacaoMockMvc.perform(get("/api/graduacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(graduacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].anoInicio").value(hasItem(DEFAULT_ANO_INICIO.toString())))
            .andExpect(jsonPath("$.[*].anoFim").value(hasItem(DEFAULT_ANO_FIM.toString())))
            .andExpect(jsonPath("$.[*].semestre").value(hasItem(DEFAULT_SEMESTRE)))
            .andExpect(jsonPath("$.[*].nroRegConselho").value(hasItem(DEFAULT_NRO_REG_CONSELHO)));
    }
    
    @Test
    @Transactional
    public void getGraduacao() throws Exception {
        // Initialize the database
        graduacaoRepository.saveAndFlush(graduacao);

        // Get the graduacao
        restGraduacaoMockMvc.perform(get("/api/graduacaos/{id}", graduacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(graduacao.getId().intValue()))
            .andExpect(jsonPath("$.curso").value(DEFAULT_CURSO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.anoInicio").value(DEFAULT_ANO_INICIO.toString()))
            .andExpect(jsonPath("$.anoFim").value(DEFAULT_ANO_FIM.toString()))
            .andExpect(jsonPath("$.semestre").value(DEFAULT_SEMESTRE))
            .andExpect(jsonPath("$.nroRegConselho").value(DEFAULT_NRO_REG_CONSELHO));
    }
    @Test
    @Transactional
    public void getNonExistingGraduacao() throws Exception {
        // Get the graduacao
        restGraduacaoMockMvc.perform(get("/api/graduacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGraduacao() throws Exception {
        // Initialize the database
        graduacaoRepository.saveAndFlush(graduacao);

        int databaseSizeBeforeUpdate = graduacaoRepository.findAll().size();

        // Update the graduacao
        Graduacao updatedGraduacao = graduacaoRepository.findById(graduacao.getId()).get();
        // Disconnect from session so that the updates on updatedGraduacao are not directly saved in db
        em.detach(updatedGraduacao);
        updatedGraduacao
            .curso(UPDATED_CURSO)
            .situacao(UPDATED_SITUACAO)
            .anoInicio(UPDATED_ANO_INICIO)
            .anoFim(UPDATED_ANO_FIM)
            .semestre(UPDATED_SEMESTRE)
            .nroRegConselho(UPDATED_NRO_REG_CONSELHO);
        GraduacaoDTO graduacaoDTO = graduacaoMapper.toDto(updatedGraduacao);

        restGraduacaoMockMvc.perform(put("/api/graduacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(graduacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Graduacao in the database
        List<Graduacao> graduacaoList = graduacaoRepository.findAll();
        assertThat(graduacaoList).hasSize(databaseSizeBeforeUpdate);
        Graduacao testGraduacao = graduacaoList.get(graduacaoList.size() - 1);
        assertThat(testGraduacao.getCurso()).isEqualTo(UPDATED_CURSO);
        assertThat(testGraduacao.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testGraduacao.getAnoInicio()).isEqualTo(UPDATED_ANO_INICIO);
        assertThat(testGraduacao.getAnoFim()).isEqualTo(UPDATED_ANO_FIM);
        assertThat(testGraduacao.getSemestre()).isEqualTo(UPDATED_SEMESTRE);
        assertThat(testGraduacao.getNroRegConselho()).isEqualTo(UPDATED_NRO_REG_CONSELHO);

        // Validate the Graduacao in Elasticsearch
        verify(mockGraduacaoSearchRepository, times(1)).save(testGraduacao);
    }

    @Test
    @Transactional
    public void updateNonExistingGraduacao() throws Exception {
        int databaseSizeBeforeUpdate = graduacaoRepository.findAll().size();

        // Create the Graduacao
        GraduacaoDTO graduacaoDTO = graduacaoMapper.toDto(graduacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGraduacaoMockMvc.perform(put("/api/graduacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(graduacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Graduacao in the database
        List<Graduacao> graduacaoList = graduacaoRepository.findAll();
        assertThat(graduacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Graduacao in Elasticsearch
        verify(mockGraduacaoSearchRepository, times(0)).save(graduacao);
    }

    @Test
    @Transactional
    public void deleteGraduacao() throws Exception {
        // Initialize the database
        graduacaoRepository.saveAndFlush(graduacao);

        int databaseSizeBeforeDelete = graduacaoRepository.findAll().size();

        // Delete the graduacao
        restGraduacaoMockMvc.perform(delete("/api/graduacaos/{id}", graduacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Graduacao> graduacaoList = graduacaoRepository.findAll();
        assertThat(graduacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Graduacao in Elasticsearch
        verify(mockGraduacaoSearchRepository, times(1)).deleteById(graduacao.getId());
    }

    @Test
    @Transactional
    public void searchGraduacao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        graduacaoRepository.saveAndFlush(graduacao);
        when(mockGraduacaoSearchRepository.search(queryStringQuery("id:" + graduacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(graduacao), PageRequest.of(0, 1), 1));

        // Search the graduacao
        restGraduacaoMockMvc.perform(get("/api/_search/graduacaos?query=id:" + graduacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(graduacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].curso").value(hasItem(DEFAULT_CURSO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].anoInicio").value(hasItem(DEFAULT_ANO_INICIO.toString())))
            .andExpect(jsonPath("$.[*].anoFim").value(hasItem(DEFAULT_ANO_FIM.toString())))
            .andExpect(jsonPath("$.[*].semestre").value(hasItem(DEFAULT_SEMESTRE)))
            .andExpect(jsonPath("$.[*].nroRegConselho").value(hasItem(DEFAULT_NRO_REG_CONSELHO)));
    }
}
