package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.EstoqueGeral;
import br.com.basis.suprimentos.repository.EstoqueGeralRepository;
import br.com.basis.suprimentos.repository.search.EstoqueGeralSearchRepository;
import br.com.basis.suprimentos.service.EstoqueGeralService;
import br.com.basis.suprimentos.service.dto.EstoqueGeralDTO;
import br.com.basis.suprimentos.service.mapper.EstoqueGeralMapper;
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
 * Integration tests for the {@link EstoqueGeralResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EstoqueGeralResourceIT {

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    private static final Long DEFAULT_QUANTIDADE = 1L;
    private static final Long UPDATED_QUANTIDADE = 2L;

    @Autowired
    private EstoqueGeralRepository estoqueGeralRepository;

    @Autowired
    private EstoqueGeralMapper estoqueGeralMapper;

    @Autowired
    private EstoqueGeralService estoqueGeralService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.EstoqueGeralSearchRepositoryMockConfiguration
     */
    @Autowired
    private EstoqueGeralSearchRepository mockEstoqueGeralSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstoqueGeralMockMvc;

    private EstoqueGeral estoqueGeral;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstoqueGeral createEntity(EntityManager em) {
        EstoqueGeral estoqueGeral = new EstoqueGeral();
        estoqueGeral.setValor(DEFAULT_VALOR);
        estoqueGeral.setQuantidade(DEFAULT_QUANTIDADE);
        return estoqueGeral;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstoqueGeral createUpdatedEntity(EntityManager em) {
        EstoqueGeral estoqueGeral = new EstoqueGeral();
        estoqueGeral.setValor(UPDATED_VALOR);
        estoqueGeral.setQuantidade(UPDATED_QUANTIDADE);
        return estoqueGeral;
    }

    @BeforeEach
    public void initTest() {
        estoqueGeral = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstoqueGeral() throws Exception {
        int databaseSizeBeforeCreate = estoqueGeralRepository.findAll().size();
        // Create the EstoqueGeral
        EstoqueGeralDTO estoqueGeralDTO = estoqueGeralMapper.toDto(estoqueGeral);
        restEstoqueGeralMockMvc.perform(post("/api/estoque-gerals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estoqueGeralDTO)))
            .andExpect(status().isCreated());

        // Validate the EstoqueGeral in the database
        List<EstoqueGeral> estoqueGeralList = estoqueGeralRepository.findAll();
        assertThat(estoqueGeralList).hasSize(databaseSizeBeforeCreate + 1);
        EstoqueGeral testEstoqueGeral = estoqueGeralList.get(estoqueGeralList.size() - 1);
        assertThat(testEstoqueGeral.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testEstoqueGeral.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);

        // Validate the EstoqueGeral in Elasticsearch
        verify(mockEstoqueGeralSearchRepository, times(1)).save(testEstoqueGeral);
    }

    @Test
    @Transactional
    public void createEstoqueGeralWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estoqueGeralRepository.findAll().size();

        // Create the EstoqueGeral with an existing ID
        estoqueGeral.setId(1L);
        EstoqueGeralDTO estoqueGeralDTO = estoqueGeralMapper.toDto(estoqueGeral);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstoqueGeralMockMvc.perform(post("/api/estoque-gerals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estoqueGeralDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstoqueGeral in the database
        List<EstoqueGeral> estoqueGeralList = estoqueGeralRepository.findAll();
        assertThat(estoqueGeralList).hasSize(databaseSizeBeforeCreate);

        // Validate the EstoqueGeral in Elasticsearch
        verify(mockEstoqueGeralSearchRepository, times(0)).save(estoqueGeral);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueGeralRepository.findAll().size();
        // set the field null
        estoqueGeral.setValor(null);

        // Create the EstoqueGeral, which fails.
        EstoqueGeralDTO estoqueGeralDTO = estoqueGeralMapper.toDto(estoqueGeral);


        restEstoqueGeralMockMvc.perform(post("/api/estoque-gerals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estoqueGeralDTO)))
            .andExpect(status().isBadRequest());

        List<EstoqueGeral> estoqueGeralList = estoqueGeralRepository.findAll();
        assertThat(estoqueGeralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = estoqueGeralRepository.findAll().size();
        // set the field null
        estoqueGeral.setQuantidade(null);

        // Create the EstoqueGeral, which fails.
        EstoqueGeralDTO estoqueGeralDTO = estoqueGeralMapper.toDto(estoqueGeral);


        restEstoqueGeralMockMvc.perform(post("/api/estoque-gerals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estoqueGeralDTO)))
            .andExpect(status().isBadRequest());

        List<EstoqueGeral> estoqueGeralList = estoqueGeralRepository.findAll();
        assertThat(estoqueGeralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstoqueGerals() throws Exception {
        // Initialize the database
        estoqueGeralRepository.saveAndFlush(estoqueGeral);

        // Get all the estoqueGeralList
        restEstoqueGeralMockMvc.perform(get("/api/estoque-gerals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estoqueGeral.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())));
    }

    @Test
    @Transactional
    public void getEstoqueGeral() throws Exception {
        // Initialize the database
        estoqueGeralRepository.saveAndFlush(estoqueGeral);

        // Get the estoqueGeral
        restEstoqueGeralMockMvc.perform(get("/api/estoque-gerals/{id}", estoqueGeral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estoqueGeral.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEstoqueGeral() throws Exception {
        // Get the estoqueGeral
        restEstoqueGeralMockMvc.perform(get("/api/estoque-gerals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstoqueGeral() throws Exception {
        // Initialize the database
        estoqueGeralRepository.saveAndFlush(estoqueGeral);

        int databaseSizeBeforeUpdate = estoqueGeralRepository.findAll().size();

        // Update the estoqueGeral
        EstoqueGeral updatedEstoqueGeral = estoqueGeralRepository.findById(estoqueGeral.getId()).get();
        // Disconnect from session so that the updates on updatedEstoqueGeral are not directly saved in db
        em.detach(updatedEstoqueGeral);
        updatedEstoqueGeral
            .setValor(UPDATED_VALOR);
        updatedEstoqueGeral.setQuantidade(UPDATED_QUANTIDADE);
        EstoqueGeralDTO estoqueGeralDTO = estoqueGeralMapper.toDto(updatedEstoqueGeral);

        restEstoqueGeralMockMvc.perform(put("/api/estoque-gerals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estoqueGeralDTO)))
            .andExpect(status().isOk());

        // Validate the EstoqueGeral in the database
        List<EstoqueGeral> estoqueGeralList = estoqueGeralRepository.findAll();
        assertThat(estoqueGeralList).hasSize(databaseSizeBeforeUpdate);
        EstoqueGeral testEstoqueGeral = estoqueGeralList.get(estoqueGeralList.size() - 1);
        assertThat(testEstoqueGeral.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testEstoqueGeral.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);

        // Validate the EstoqueGeral in Elasticsearch
        verify(mockEstoqueGeralSearchRepository, times(1)).save(testEstoqueGeral);
    }

    @Test
    @Transactional
    public void updateNonExistingEstoqueGeral() throws Exception {
        int databaseSizeBeforeUpdate = estoqueGeralRepository.findAll().size();

        // Create the EstoqueGeral
        EstoqueGeralDTO estoqueGeralDTO = estoqueGeralMapper.toDto(estoqueGeral);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstoqueGeralMockMvc.perform(put("/api/estoque-gerals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estoqueGeralDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstoqueGeral in the database
        List<EstoqueGeral> estoqueGeralList = estoqueGeralRepository.findAll();
        assertThat(estoqueGeralList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EstoqueGeral in Elasticsearch
        verify(mockEstoqueGeralSearchRepository, times(0)).save(estoqueGeral);
    }

    @Test
    @Transactional
    public void deleteEstoqueGeral() throws Exception {
        // Initialize the database
        estoqueGeralRepository.saveAndFlush(estoqueGeral);

        int databaseSizeBeforeDelete = estoqueGeralRepository.findAll().size();

        // Delete the estoqueGeral
        restEstoqueGeralMockMvc.perform(delete("/api/estoque-gerals/{id}", estoqueGeral.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstoqueGeral> estoqueGeralList = estoqueGeralRepository.findAll();
        assertThat(estoqueGeralList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EstoqueGeral in Elasticsearch
        verify(mockEstoqueGeralSearchRepository, times(1)).deleteById(estoqueGeral.getId());
    }

    @Test
    @Transactional
    public void searchEstoqueGeral() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        estoqueGeralRepository.saveAndFlush(estoqueGeral);
        when(mockEstoqueGeralSearchRepository.search(queryStringQuery("id:" + estoqueGeral.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(estoqueGeral), PageRequest.of(0, 1), 1));

        // Search the estoqueGeral
        restEstoqueGeralMockMvc.perform(get("/api/_search/estoque-gerals?query=id:" + estoqueGeral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estoqueGeral.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())));
    }
}
