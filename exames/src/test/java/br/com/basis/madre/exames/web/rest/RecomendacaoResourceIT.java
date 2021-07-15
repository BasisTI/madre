package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.MadreexamesApp;
import br.com.basis.madre.exames.domain.Recomendacao;
import br.com.basis.madre.exames.repository.RecomendacaoRepository;
import br.com.basis.madre.exames.service.RecomendacaoService;
import br.com.basis.madre.exames.service.dto.RecomendacaoDTO;
import br.com.basis.madre.exames.service.mapper.RecomendacaoMapper;

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

import br.com.basis.madre.exames.domain.enumeration.Responsavel;
import br.com.basis.madre.exames.domain.enumeration.Abrangencia;
/**
 * Integration tests for the {@link RecomendacaoResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecomendacaoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVISO_RESPONSAVEL = false;
    private static final Boolean UPDATED_AVISO_RESPONSAVEL = true;

    private static final Responsavel DEFAULT_RESPONSAVEL = Responsavel.COLETADOR;
    private static final Responsavel UPDATED_RESPONSAVEL = Responsavel.SOLICITANTE;

    private static final Abrangencia DEFAULT_ABRANGENCIA = Abrangencia.AMBULATORIO;
    private static final Abrangencia UPDATED_ABRANGENCIA = Abrangencia.INTERNACAO;

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    @Autowired
    private RecomendacaoMapper recomendacaoMapper;

    @Autowired
    private RecomendacaoService recomendacaoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecomendacaoMockMvc;

    private Recomendacao recomendacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recomendacao createEntity(EntityManager em) {
        Recomendacao recomendacao = new Recomendacao()
            .descricao(DEFAULT_DESCRICAO)
            .avisoResponsavel(DEFAULT_AVISO_RESPONSAVEL)
            .responsavel(DEFAULT_RESPONSAVEL)
            .abrangencia(DEFAULT_ABRANGENCIA);
        return recomendacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recomendacao createUpdatedEntity(EntityManager em) {
        Recomendacao recomendacao = new Recomendacao()
            .descricao(UPDATED_DESCRICAO)
            .avisoResponsavel(UPDATED_AVISO_RESPONSAVEL)
            .responsavel(UPDATED_RESPONSAVEL)
            .abrangencia(UPDATED_ABRANGENCIA);
        return recomendacao;
    }

    @BeforeEach
    public void initTest() {
        recomendacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecomendacao() throws Exception {
        int databaseSizeBeforeCreate = recomendacaoRepository.findAll().size();
        // Create the Recomendacao
        RecomendacaoDTO recomendacaoDTO = recomendacaoMapper.toDto(recomendacao);
        restRecomendacaoMockMvc.perform(post("/api/recomendacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recomendacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Recomendacao in the database
        List<Recomendacao> recomendacaoList = recomendacaoRepository.findAll();
        assertThat(recomendacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Recomendacao testRecomendacao = recomendacaoList.get(recomendacaoList.size() - 1);
        assertThat(testRecomendacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRecomendacao.isAvisoResponsavel()).isEqualTo(DEFAULT_AVISO_RESPONSAVEL);
        assertThat(testRecomendacao.getResponsavel()).isEqualTo(DEFAULT_RESPONSAVEL);
        assertThat(testRecomendacao.getAbrangencia()).isEqualTo(DEFAULT_ABRANGENCIA);
    }

    @Test
    @Transactional
    public void createRecomendacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recomendacaoRepository.findAll().size();

        // Create the Recomendacao with an existing ID
        recomendacao.setId(1L);
        RecomendacaoDTO recomendacaoDTO = recomendacaoMapper.toDto(recomendacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecomendacaoMockMvc.perform(post("/api/recomendacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recomendacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recomendacao in the database
        List<Recomendacao> recomendacaoList = recomendacaoRepository.findAll();
        assertThat(recomendacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recomendacaoRepository.findAll().size();
        // set the field null
        recomendacao.setDescricao(null);

        // Create the Recomendacao, which fails.
        RecomendacaoDTO recomendacaoDTO = recomendacaoMapper.toDto(recomendacao);


        restRecomendacaoMockMvc.perform(post("/api/recomendacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recomendacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Recomendacao> recomendacaoList = recomendacaoRepository.findAll();
        assertThat(recomendacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvisoResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = recomendacaoRepository.findAll().size();
        // set the field null
        recomendacao.setAvisoResponsavel(null);

        // Create the Recomendacao, which fails.
        RecomendacaoDTO recomendacaoDTO = recomendacaoMapper.toDto(recomendacao);


        restRecomendacaoMockMvc.perform(post("/api/recomendacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recomendacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Recomendacao> recomendacaoList = recomendacaoRepository.findAll();
        assertThat(recomendacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecomendacaos() throws Exception {
        // Initialize the database
        recomendacaoRepository.saveAndFlush(recomendacao);

        // Get all the recomendacaoList
        restRecomendacaoMockMvc.perform(get("/api/recomendacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recomendacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].avisoResponsavel").value(hasItem(DEFAULT_AVISO_RESPONSAVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL.toString())))
            .andExpect(jsonPath("$.[*].abrangencia").value(hasItem(DEFAULT_ABRANGENCIA.toString())));
    }
    
    @Test
    @Transactional
    public void getRecomendacao() throws Exception {
        // Initialize the database
        recomendacaoRepository.saveAndFlush(recomendacao);

        // Get the recomendacao
        restRecomendacaoMockMvc.perform(get("/api/recomendacaos/{id}", recomendacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recomendacao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.avisoResponsavel").value(DEFAULT_AVISO_RESPONSAVEL.booleanValue()))
            .andExpect(jsonPath("$.responsavel").value(DEFAULT_RESPONSAVEL.toString()))
            .andExpect(jsonPath("$.abrangencia").value(DEFAULT_ABRANGENCIA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRecomendacao() throws Exception {
        // Get the recomendacao
        restRecomendacaoMockMvc.perform(get("/api/recomendacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecomendacao() throws Exception {
        // Initialize the database
        recomendacaoRepository.saveAndFlush(recomendacao);

        int databaseSizeBeforeUpdate = recomendacaoRepository.findAll().size();

        // Update the recomendacao
        Recomendacao updatedRecomendacao = recomendacaoRepository.findById(recomendacao.getId()).get();
        // Disconnect from session so that the updates on updatedRecomendacao are not directly saved in db
        em.detach(updatedRecomendacao);
        updatedRecomendacao
            .descricao(UPDATED_DESCRICAO)
            .avisoResponsavel(UPDATED_AVISO_RESPONSAVEL)
            .responsavel(UPDATED_RESPONSAVEL)
            .abrangencia(UPDATED_ABRANGENCIA);
        RecomendacaoDTO recomendacaoDTO = recomendacaoMapper.toDto(updatedRecomendacao);

        restRecomendacaoMockMvc.perform(put("/api/recomendacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recomendacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Recomendacao in the database
        List<Recomendacao> recomendacaoList = recomendacaoRepository.findAll();
        assertThat(recomendacaoList).hasSize(databaseSizeBeforeUpdate);
        Recomendacao testRecomendacao = recomendacaoList.get(recomendacaoList.size() - 1);
        assertThat(testRecomendacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRecomendacao.isAvisoResponsavel()).isEqualTo(UPDATED_AVISO_RESPONSAVEL);
        assertThat(testRecomendacao.getResponsavel()).isEqualTo(UPDATED_RESPONSAVEL);
        assertThat(testRecomendacao.getAbrangencia()).isEqualTo(UPDATED_ABRANGENCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingRecomendacao() throws Exception {
        int databaseSizeBeforeUpdate = recomendacaoRepository.findAll().size();

        // Create the Recomendacao
        RecomendacaoDTO recomendacaoDTO = recomendacaoMapper.toDto(recomendacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecomendacaoMockMvc.perform(put("/api/recomendacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recomendacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recomendacao in the database
        List<Recomendacao> recomendacaoList = recomendacaoRepository.findAll();
        assertThat(recomendacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecomendacao() throws Exception {
        // Initialize the database
        recomendacaoRepository.saveAndFlush(recomendacao);

        int databaseSizeBeforeDelete = recomendacaoRepository.findAll().size();

        // Delete the recomendacao
        restRecomendacaoMockMvc.perform(delete("/api/recomendacaos/{id}", recomendacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recomendacao> recomendacaoList = recomendacaoRepository.findAll();
        assertThat(recomendacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRecomendacao() throws Exception {
        // Initialize the database
        recomendacaoRepository.saveAndFlush(recomendacao);

        // Search the recomendacao
        restRecomendacaoMockMvc.perform(get("/api/_search/recomendacaos?query=id:" + recomendacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recomendacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].avisoResponsavel").value(hasItem(DEFAULT_AVISO_RESPONSAVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL.toString())))
            .andExpect(jsonPath("$.[*].abrangencia").value(hasItem(DEFAULT_ABRANGENCIA.toString())));
    }
}
