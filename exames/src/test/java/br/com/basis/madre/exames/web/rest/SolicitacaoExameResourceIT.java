package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.MadreexamesApp;
import br.com.basis.madre.exames.domain.SolicitacaoExame;
import br.com.basis.madre.exames.repository.SolicitacaoExameRepository;
import br.com.basis.madre.exames.service.SolicitacaoExameService;
import br.com.basis.madre.exames.service.dto.SolicitacaoExameDTO;
import br.com.basis.madre.exames.service.mapper.SolicitacaoExameMapper;

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
 * Integration tests for the {@link SolicitacaoExameResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SolicitacaoExameResourceIT {

    private static final String DEFAULT_INFO_CLINICA = "AAAAAAAAAA";
    private static final String UPDATED_INFO_CLINICA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USO_ANTIMICROBIANOS_24_H = false;
    private static final Boolean UPDATED_USO_ANTIMICROBIANOS_24_H = true;

    private static final Boolean DEFAULT_PEDIDO_PRIMEIRO_EXAME = false;
    private static final Boolean UPDATED_PEDIDO_PRIMEIRO_EXAME = true;

    @Autowired
    private SolicitacaoExameRepository solicitacaoExameRepository;

    @Autowired
    private SolicitacaoExameMapper solicitacaoExameMapper;

    @Autowired
    private SolicitacaoExameService solicitacaoExameService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolicitacaoExameMockMvc;

    private SolicitacaoExame solicitacaoExame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitacaoExame createEntity(EntityManager em) {
        SolicitacaoExame solicitacaoExame = new SolicitacaoExame()
            .infoClinica(DEFAULT_INFO_CLINICA)
            .usoAntimicrobianos24h(DEFAULT_USO_ANTIMICROBIANOS_24_H)
            .pedidoPrimeiroExame(DEFAULT_PEDIDO_PRIMEIRO_EXAME);
        return solicitacaoExame;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitacaoExame createUpdatedEntity(EntityManager em) {
        SolicitacaoExame solicitacaoExame = new SolicitacaoExame()
            .infoClinica(UPDATED_INFO_CLINICA)
            .usoAntimicrobianos24h(UPDATED_USO_ANTIMICROBIANOS_24_H)
            .pedidoPrimeiroExame(UPDATED_PEDIDO_PRIMEIRO_EXAME);
        return solicitacaoExame;
    }

    @BeforeEach
    public void initTest() {
        solicitacaoExame = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitacaoExame() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoExameRepository.findAll().size();
        // Create the SolicitacaoExame
        SolicitacaoExameDTO solicitacaoExameDTO = solicitacaoExameMapper.toDto(solicitacaoExame);
        restSolicitacaoExameMockMvc.perform(post("/api/solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoExameDTO)))
            .andExpect(status().isCreated());

        // Validate the SolicitacaoExame in the database
        List<SolicitacaoExame> solicitacaoExameList = solicitacaoExameRepository.findAll();
        assertThat(solicitacaoExameList).hasSize(databaseSizeBeforeCreate + 1);
        SolicitacaoExame testSolicitacaoExame = solicitacaoExameList.get(solicitacaoExameList.size() - 1);
        assertThat(testSolicitacaoExame.getInfoClinica()).isEqualTo(DEFAULT_INFO_CLINICA);
        assertThat(testSolicitacaoExame.isUsoAntimicrobianos24h()).isEqualTo(DEFAULT_USO_ANTIMICROBIANOS_24_H);
        assertThat(testSolicitacaoExame.isPedidoPrimeiroExame()).isEqualTo(DEFAULT_PEDIDO_PRIMEIRO_EXAME);
    }

    @Test
    @Transactional
    public void createSolicitacaoExameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoExameRepository.findAll().size();

        // Create the SolicitacaoExame with an existing ID
        solicitacaoExame.setId(1L);
        SolicitacaoExameDTO solicitacaoExameDTO = solicitacaoExameMapper.toDto(solicitacaoExame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitacaoExameMockMvc.perform(post("/api/solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoExame in the database
        List<SolicitacaoExame> solicitacaoExameList = solicitacaoExameRepository.findAll();
        assertThat(solicitacaoExameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInfoClinicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoExameRepository.findAll().size();
        // set the field null
        solicitacaoExame.setInfoClinica(null);

        // Create the SolicitacaoExame, which fails.
        SolicitacaoExameDTO solicitacaoExameDTO = solicitacaoExameMapper.toDto(solicitacaoExame);


        restSolicitacaoExameMockMvc.perform(post("/api/solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoExame> solicitacaoExameList = solicitacaoExameRepository.findAll();
        assertThat(solicitacaoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsoAntimicrobianos24hIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoExameRepository.findAll().size();
        // set the field null
        solicitacaoExame.setUsoAntimicrobianos24h(null);

        // Create the SolicitacaoExame, which fails.
        SolicitacaoExameDTO solicitacaoExameDTO = solicitacaoExameMapper.toDto(solicitacaoExame);


        restSolicitacaoExameMockMvc.perform(post("/api/solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoExame> solicitacaoExameList = solicitacaoExameRepository.findAll();
        assertThat(solicitacaoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPedidoPrimeiroExameIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicitacaoExameRepository.findAll().size();
        // set the field null
        solicitacaoExame.setPedidoPrimeiroExame(null);

        // Create the SolicitacaoExame, which fails.
        SolicitacaoExameDTO solicitacaoExameDTO = solicitacaoExameMapper.toDto(solicitacaoExame);


        restSolicitacaoExameMockMvc.perform(post("/api/solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        List<SolicitacaoExame> solicitacaoExameList = solicitacaoExameRepository.findAll();
        assertThat(solicitacaoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolicitacaoExames() throws Exception {
        // Initialize the database
        solicitacaoExameRepository.saveAndFlush(solicitacaoExame);

        // Get all the solicitacaoExameList
        restSolicitacaoExameMockMvc.perform(get("/api/solicitacao-exames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitacaoExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].infoClinica").value(hasItem(DEFAULT_INFO_CLINICA)))
            .andExpect(jsonPath("$.[*].usoAntimicrobianos24h").value(hasItem(DEFAULT_USO_ANTIMICROBIANOS_24_H.booleanValue())))
            .andExpect(jsonPath("$.[*].pedidoPrimeiroExame").value(hasItem(DEFAULT_PEDIDO_PRIMEIRO_EXAME.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSolicitacaoExame() throws Exception {
        // Initialize the database
        solicitacaoExameRepository.saveAndFlush(solicitacaoExame);

        // Get the solicitacaoExame
        restSolicitacaoExameMockMvc.perform(get("/api/solicitacao-exames/{id}", solicitacaoExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solicitacaoExame.getId().intValue()))
            .andExpect(jsonPath("$.infoClinica").value(DEFAULT_INFO_CLINICA))
            .andExpect(jsonPath("$.usoAntimicrobianos24h").value(DEFAULT_USO_ANTIMICROBIANOS_24_H.booleanValue()))
            .andExpect(jsonPath("$.pedidoPrimeiroExame").value(DEFAULT_PEDIDO_PRIMEIRO_EXAME.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSolicitacaoExame() throws Exception {
        // Get the solicitacaoExame
        restSolicitacaoExameMockMvc.perform(get("/api/solicitacao-exames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitacaoExame() throws Exception {
        // Initialize the database
        solicitacaoExameRepository.saveAndFlush(solicitacaoExame);

        int databaseSizeBeforeUpdate = solicitacaoExameRepository.findAll().size();

        // Update the solicitacaoExame
        SolicitacaoExame updatedSolicitacaoExame = solicitacaoExameRepository.findById(solicitacaoExame.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitacaoExame are not directly saved in db
        em.detach(updatedSolicitacaoExame);
        updatedSolicitacaoExame
            .infoClinica(UPDATED_INFO_CLINICA)
            .usoAntimicrobianos24h(UPDATED_USO_ANTIMICROBIANOS_24_H)
            .pedidoPrimeiroExame(UPDATED_PEDIDO_PRIMEIRO_EXAME);
        SolicitacaoExameDTO solicitacaoExameDTO = solicitacaoExameMapper.toDto(updatedSolicitacaoExame);

        restSolicitacaoExameMockMvc.perform(put("/api/solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoExameDTO)))
            .andExpect(status().isOk());

        // Validate the SolicitacaoExame in the database
        List<SolicitacaoExame> solicitacaoExameList = solicitacaoExameRepository.findAll();
        assertThat(solicitacaoExameList).hasSize(databaseSizeBeforeUpdate);
        SolicitacaoExame testSolicitacaoExame = solicitacaoExameList.get(solicitacaoExameList.size() - 1);
        assertThat(testSolicitacaoExame.getInfoClinica()).isEqualTo(UPDATED_INFO_CLINICA);
        assertThat(testSolicitacaoExame.isUsoAntimicrobianos24h()).isEqualTo(UPDATED_USO_ANTIMICROBIANOS_24_H);
        assertThat(testSolicitacaoExame.isPedidoPrimeiroExame()).isEqualTo(UPDATED_PEDIDO_PRIMEIRO_EXAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitacaoExame() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoExameRepository.findAll().size();

        // Create the SolicitacaoExame
        SolicitacaoExameDTO solicitacaoExameDTO = solicitacaoExameMapper.toDto(solicitacaoExame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitacaoExameMockMvc.perform(put("/api/solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolicitacaoExame in the database
        List<SolicitacaoExame> solicitacaoExameList = solicitacaoExameRepository.findAll();
        assertThat(solicitacaoExameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolicitacaoExame() throws Exception {
        // Initialize the database
        solicitacaoExameRepository.saveAndFlush(solicitacaoExame);

        int databaseSizeBeforeDelete = solicitacaoExameRepository.findAll().size();

        // Delete the solicitacaoExame
        restSolicitacaoExameMockMvc.perform(delete("/api/solicitacao-exames/{id}", solicitacaoExame.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SolicitacaoExame> solicitacaoExameList = solicitacaoExameRepository.findAll();
        assertThat(solicitacaoExameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSolicitacaoExame() throws Exception {
        // Initialize the database
        solicitacaoExameRepository.saveAndFlush(solicitacaoExame);

        // Search the solicitacaoExame
        restSolicitacaoExameMockMvc.perform(get("/api/_search/solicitacao-exames?query=id:" + solicitacaoExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitacaoExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].infoClinica").value(hasItem(DEFAULT_INFO_CLINICA)))
            .andExpect(jsonPath("$.[*].usoAntimicrobianos24h").value(hasItem(DEFAULT_USO_ANTIMICROBIANOS_24_H.booleanValue())))
            .andExpect(jsonPath("$.[*].pedidoPrimeiroExame").value(hasItem(DEFAULT_PEDIDO_PRIMEIRO_EXAME.booleanValue())));
    }
}
