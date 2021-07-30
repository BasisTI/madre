package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.OcupacaoDeCargo;
import br.com.basis.madre.seguranca.repository.OcupacaoDeCargoRepository;
import br.com.basis.madre.seguranca.repository.search.OcupacaoDeCargoSearchRepository;
import br.com.basis.madre.seguranca.service.OcupacaoDeCargoService;
import br.com.basis.madre.seguranca.service.dto.OcupacaoDeCargoDTO;
import br.com.basis.madre.seguranca.service.mapper.OcupacaoDeCargoMapper;

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
 * Integration tests for the {@link OcupacaoDeCargoResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OcupacaoDeCargoResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SITUACAO = false;
    private static final Boolean UPDATED_SITUACAO = true;

    private static final Boolean DEFAULT_INFORMAR_CBO = false;
    private static final Boolean UPDATED_INFORMAR_CBO = true;

    private static final Boolean DEFAULT_INFORMAR_CNS = false;
    private static final Boolean UPDATED_INFORMAR_CNS = true;

    @Autowired
    private OcupacaoDeCargoRepository ocupacaoDeCargoRepository;

    @Autowired
    private OcupacaoDeCargoMapper ocupacaoDeCargoMapper;

    @Autowired
    private OcupacaoDeCargoService ocupacaoDeCargoService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.OcupacaoDeCargoSearchRepositoryMockConfiguration
     */
    @Autowired
    private OcupacaoDeCargoSearchRepository mockOcupacaoDeCargoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOcupacaoDeCargoMockMvc;

    private OcupacaoDeCargo ocupacaoDeCargo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OcupacaoDeCargo createEntity(EntityManager em) {
        OcupacaoDeCargo ocupacaoDeCargo = new OcupacaoDeCargo()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO)
            .situacao(DEFAULT_SITUACAO)
            .informarCbo(DEFAULT_INFORMAR_CBO)
            .informarCns(DEFAULT_INFORMAR_CNS);
        return ocupacaoDeCargo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OcupacaoDeCargo createUpdatedEntity(EntityManager em) {
        OcupacaoDeCargo ocupacaoDeCargo = new OcupacaoDeCargo()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .situacao(UPDATED_SITUACAO)
            .informarCbo(UPDATED_INFORMAR_CBO)
            .informarCns(UPDATED_INFORMAR_CNS);
        return ocupacaoDeCargo;
    }

    @BeforeEach
    public void initTest() {
        ocupacaoDeCargo = createEntity(em);
    }

    @Test
    @Transactional
    public void createOcupacaoDeCargo() throws Exception {
        int databaseSizeBeforeCreate = ocupacaoDeCargoRepository.findAll().size();
        // Create the OcupacaoDeCargo
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO = ocupacaoDeCargoMapper.toDto(ocupacaoDeCargo);
        restOcupacaoDeCargoMockMvc.perform(post("/api/ocupacao-de-cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDeCargoDTO)))
            .andExpect(status().isCreated());

        // Validate the OcupacaoDeCargo in the database
        List<OcupacaoDeCargo> ocupacaoDeCargoList = ocupacaoDeCargoRepository.findAll();
        assertThat(ocupacaoDeCargoList).hasSize(databaseSizeBeforeCreate + 1);
        OcupacaoDeCargo testOcupacaoDeCargo = ocupacaoDeCargoList.get(ocupacaoDeCargoList.size() - 1);
        assertThat(testOcupacaoDeCargo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testOcupacaoDeCargo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testOcupacaoDeCargo.isSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testOcupacaoDeCargo.isInformarCbo()).isEqualTo(DEFAULT_INFORMAR_CBO);
        assertThat(testOcupacaoDeCargo.isInformarCns()).isEqualTo(DEFAULT_INFORMAR_CNS);

        // Validate the OcupacaoDeCargo in Elasticsearch
        verify(mockOcupacaoDeCargoSearchRepository, times(1)).save(testOcupacaoDeCargo);
    }

    @Test
    @Transactional
    public void createOcupacaoDeCargoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ocupacaoDeCargoRepository.findAll().size();

        // Create the OcupacaoDeCargo with an existing ID
        ocupacaoDeCargo.setId(1L);
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO = ocupacaoDeCargoMapper.toDto(ocupacaoDeCargo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcupacaoDeCargoMockMvc.perform(post("/api/ocupacao-de-cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDeCargoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OcupacaoDeCargo in the database
        List<OcupacaoDeCargo> ocupacaoDeCargoList = ocupacaoDeCargoRepository.findAll();
        assertThat(ocupacaoDeCargoList).hasSize(databaseSizeBeforeCreate);

        // Validate the OcupacaoDeCargo in Elasticsearch
        verify(mockOcupacaoDeCargoSearchRepository, times(0)).save(ocupacaoDeCargo);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocupacaoDeCargoRepository.findAll().size();
        // set the field null
        ocupacaoDeCargo.setCodigo(null);

        // Create the OcupacaoDeCargo, which fails.
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO = ocupacaoDeCargoMapper.toDto(ocupacaoDeCargo);


        restOcupacaoDeCargoMockMvc.perform(post("/api/ocupacao-de-cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDeCargoDTO)))
            .andExpect(status().isBadRequest());

        List<OcupacaoDeCargo> ocupacaoDeCargoList = ocupacaoDeCargoRepository.findAll();
        assertThat(ocupacaoDeCargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocupacaoDeCargoRepository.findAll().size();
        // set the field null
        ocupacaoDeCargo.setDescricao(null);

        // Create the OcupacaoDeCargo, which fails.
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO = ocupacaoDeCargoMapper.toDto(ocupacaoDeCargo);


        restOcupacaoDeCargoMockMvc.perform(post("/api/ocupacao-de-cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDeCargoDTO)))
            .andExpect(status().isBadRequest());

        List<OcupacaoDeCargo> ocupacaoDeCargoList = ocupacaoDeCargoRepository.findAll();
        assertThat(ocupacaoDeCargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ocupacaoDeCargoRepository.findAll().size();
        // set the field null
        ocupacaoDeCargo.setSituacao(null);

        // Create the OcupacaoDeCargo, which fails.
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO = ocupacaoDeCargoMapper.toDto(ocupacaoDeCargo);


        restOcupacaoDeCargoMockMvc.perform(post("/api/ocupacao-de-cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDeCargoDTO)))
            .andExpect(status().isBadRequest());

        List<OcupacaoDeCargo> ocupacaoDeCargoList = ocupacaoDeCargoRepository.findAll();
        assertThat(ocupacaoDeCargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOcupacaoDeCargos() throws Exception {
        // Initialize the database
        ocupacaoDeCargoRepository.saveAndFlush(ocupacaoDeCargo);

        // Get all the ocupacaoDeCargoList
        restOcupacaoDeCargoMockMvc.perform(get("/api/ocupacao-de-cargos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocupacaoDeCargo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].informarCbo").value(hasItem(DEFAULT_INFORMAR_CBO.booleanValue())))
            .andExpect(jsonPath("$.[*].informarCns").value(hasItem(DEFAULT_INFORMAR_CNS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOcupacaoDeCargo() throws Exception {
        // Initialize the database
        ocupacaoDeCargoRepository.saveAndFlush(ocupacaoDeCargo);

        // Get the ocupacaoDeCargo
        restOcupacaoDeCargoMockMvc.perform(get("/api/ocupacao-de-cargos/{id}", ocupacaoDeCargo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ocupacaoDeCargo.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.booleanValue()))
            .andExpect(jsonPath("$.informarCbo").value(DEFAULT_INFORMAR_CBO.booleanValue()))
            .andExpect(jsonPath("$.informarCns").value(DEFAULT_INFORMAR_CNS.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOcupacaoDeCargo() throws Exception {
        // Get the ocupacaoDeCargo
        restOcupacaoDeCargoMockMvc.perform(get("/api/ocupacao-de-cargos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOcupacaoDeCargo() throws Exception {
        // Initialize the database
        ocupacaoDeCargoRepository.saveAndFlush(ocupacaoDeCargo);

        int databaseSizeBeforeUpdate = ocupacaoDeCargoRepository.findAll().size();

        // Update the ocupacaoDeCargo
        OcupacaoDeCargo updatedOcupacaoDeCargo = ocupacaoDeCargoRepository.findById(ocupacaoDeCargo.getId()).get();
        // Disconnect from session so that the updates on updatedOcupacaoDeCargo are not directly saved in db
        em.detach(updatedOcupacaoDeCargo);
        updatedOcupacaoDeCargo
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .situacao(UPDATED_SITUACAO)
            .informarCbo(UPDATED_INFORMAR_CBO)
            .informarCns(UPDATED_INFORMAR_CNS);
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO = ocupacaoDeCargoMapper.toDto(updatedOcupacaoDeCargo);

        restOcupacaoDeCargoMockMvc.perform(put("/api/ocupacao-de-cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDeCargoDTO)))
            .andExpect(status().isOk());

        // Validate the OcupacaoDeCargo in the database
        List<OcupacaoDeCargo> ocupacaoDeCargoList = ocupacaoDeCargoRepository.findAll();
        assertThat(ocupacaoDeCargoList).hasSize(databaseSizeBeforeUpdate);
        OcupacaoDeCargo testOcupacaoDeCargo = ocupacaoDeCargoList.get(ocupacaoDeCargoList.size() - 1);
        assertThat(testOcupacaoDeCargo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testOcupacaoDeCargo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testOcupacaoDeCargo.isSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testOcupacaoDeCargo.isInformarCbo()).isEqualTo(UPDATED_INFORMAR_CBO);
        assertThat(testOcupacaoDeCargo.isInformarCns()).isEqualTo(UPDATED_INFORMAR_CNS);

        // Validate the OcupacaoDeCargo in Elasticsearch
        verify(mockOcupacaoDeCargoSearchRepository, times(1)).save(testOcupacaoDeCargo);
    }

    @Test
    @Transactional
    public void updateNonExistingOcupacaoDeCargo() throws Exception {
        int databaseSizeBeforeUpdate = ocupacaoDeCargoRepository.findAll().size();

        // Create the OcupacaoDeCargo
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO = ocupacaoDeCargoMapper.toDto(ocupacaoDeCargo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcupacaoDeCargoMockMvc.perform(put("/api/ocupacao-de-cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ocupacaoDeCargoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OcupacaoDeCargo in the database
        List<OcupacaoDeCargo> ocupacaoDeCargoList = ocupacaoDeCargoRepository.findAll();
        assertThat(ocupacaoDeCargoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OcupacaoDeCargo in Elasticsearch
        verify(mockOcupacaoDeCargoSearchRepository, times(0)).save(ocupacaoDeCargo);
    }

    @Test
    @Transactional
    public void deleteOcupacaoDeCargo() throws Exception {
        // Initialize the database
        ocupacaoDeCargoRepository.saveAndFlush(ocupacaoDeCargo);

        int databaseSizeBeforeDelete = ocupacaoDeCargoRepository.findAll().size();

        // Delete the ocupacaoDeCargo
        restOcupacaoDeCargoMockMvc.perform(delete("/api/ocupacao-de-cargos/{id}", ocupacaoDeCargo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OcupacaoDeCargo> ocupacaoDeCargoList = ocupacaoDeCargoRepository.findAll();
        assertThat(ocupacaoDeCargoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OcupacaoDeCargo in Elasticsearch
        verify(mockOcupacaoDeCargoSearchRepository, times(1)).deleteById(ocupacaoDeCargo.getId());
    }

    @Test
    @Transactional
    public void searchOcupacaoDeCargo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        ocupacaoDeCargoRepository.saveAndFlush(ocupacaoDeCargo);
        when(mockOcupacaoDeCargoSearchRepository.search(queryStringQuery("id:" + ocupacaoDeCargo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ocupacaoDeCargo), PageRequest.of(0, 1), 1));

        // Search the ocupacaoDeCargo
        restOcupacaoDeCargoMockMvc.perform(get("/api/_search/ocupacao-de-cargos?query=id:" + ocupacaoDeCargo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocupacaoDeCargo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].informarCbo").value(hasItem(DEFAULT_INFORMAR_CBO.booleanValue())))
            .andExpect(jsonPath("$.[*].informarCns").value(hasItem(DEFAULT_INFORMAR_CNS.booleanValue())));
    }
}
