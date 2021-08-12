package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Cargo;
import br.com.basis.madre.seguranca.repository.CargoRepository;
import br.com.basis.madre.seguranca.repository.search.CargoSearchRepository;
import br.com.basis.madre.seguranca.service.CargoService;
import br.com.basis.madre.seguranca.service.dto.CargoDTO;
import br.com.basis.madre.seguranca.service.mapper.CargoMapper;

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
 * Integration tests for the {@link CargoResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CargoResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SITUACAO = false;
    private static final Boolean UPDATED_SITUACAO = true;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private CargoMapper cargoMapper;

    @Autowired
    private CargoService cargoService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.CargoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CargoSearchRepository mockCargoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCargoMockMvc;

    private Cargo cargo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cargo createEntity(EntityManager em) {
        Cargo cargo = new Cargo()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO)
            .situacao(DEFAULT_SITUACAO);
        return cargo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cargo createUpdatedEntity(EntityManager em) {
        Cargo cargo = new Cargo()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .situacao(UPDATED_SITUACAO);
        return cargo;
    }

    @BeforeEach
    public void initTest() {
        cargo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCargo() throws Exception {
        int databaseSizeBeforeCreate = cargoRepository.findAll().size();
        // Create the Cargo
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);
        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isCreated());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeCreate + 1);
        Cargo testCargo = cargoList.get(cargoList.size() - 1);
        assertThat(testCargo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testCargo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCargo.isSituacao()).isEqualTo(DEFAULT_SITUACAO);

        // Validate the Cargo in Elasticsearch
        verify(mockCargoSearchRepository, times(1)).save(testCargo);
    }

    @Test
    @Transactional
    public void createCargoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cargoRepository.findAll().size();

        // Create the Cargo with an existing ID
        cargo.setId(1L);
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cargo in Elasticsearch
        verify(mockCargoSearchRepository, times(0)).save(cargo);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cargoRepository.findAll().size();
        // set the field null
        cargo.setCodigo(null);

        // Create the Cargo, which fails.
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);


        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isBadRequest());

        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cargoRepository.findAll().size();
        // set the field null
        cargo.setDescricao(null);

        // Create the Cargo, which fails.
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);


        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isBadRequest());

        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cargoRepository.findAll().size();
        // set the field null
        cargo.setSituacao(null);

        // Create the Cargo, which fails.
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);


        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isBadRequest());

        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCargos() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        // Get all the cargoList
        restCargoMockMvc.perform(get("/api/cargos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        // Get the cargo
        restCargoMockMvc.perform(get("/api/cargos/{id}", cargo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cargo.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCargo() throws Exception {
        // Get the cargo
        restCargoMockMvc.perform(get("/api/cargos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        int databaseSizeBeforeUpdate = cargoRepository.findAll().size();

        // Update the cargo
        Cargo updatedCargo = cargoRepository.findById(cargo.getId()).get();
        // Disconnect from session so that the updates on updatedCargo are not directly saved in db
        em.detach(updatedCargo);
        updatedCargo
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .situacao(UPDATED_SITUACAO);
        CargoDTO cargoDTO = cargoMapper.toDto(updatedCargo);

        restCargoMockMvc.perform(put("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isOk());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeUpdate);
        Cargo testCargo = cargoList.get(cargoList.size() - 1);
        assertThat(testCargo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testCargo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCargo.isSituacao()).isEqualTo(UPDATED_SITUACAO);

        // Validate the Cargo in Elasticsearch
        verify(mockCargoSearchRepository, times(1)).save(testCargo);
    }

    @Test
    @Transactional
    public void updateNonExistingCargo() throws Exception {
        int databaseSizeBeforeUpdate = cargoRepository.findAll().size();

        // Create the Cargo
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCargoMockMvc.perform(put("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cargo in Elasticsearch
        verify(mockCargoSearchRepository, times(0)).save(cargo);
    }

    @Test
    @Transactional
    public void deleteCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        int databaseSizeBeforeDelete = cargoRepository.findAll().size();

        // Delete the cargo
        restCargoMockMvc.perform(delete("/api/cargos/{id}", cargo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cargo in Elasticsearch
        verify(mockCargoSearchRepository, times(1)).deleteById(cargo.getId());
    }

    @Test
    @Transactional
    public void searchCargo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);
        when(mockCargoSearchRepository.search(queryStringQuery("id:" + cargo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cargo), PageRequest.of(0, 1), 1));

        // Search the cargo
        restCargoMockMvc.perform(get("/api/_search/cargos?query=id:" + cargo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())));
    }
}
