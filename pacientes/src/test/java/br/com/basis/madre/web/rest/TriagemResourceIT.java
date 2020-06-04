package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.repository.TriagemRepository;
import br.com.basis.madre.repository.search.TriagemSearchRepository;
import br.com.basis.madre.service.TriagemService;
import br.com.basis.madre.service.dto.TriagemDTO;
import br.com.basis.madre.service.mapper.TriagemMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;
import lombok.Data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TriagemResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
public class TriagemResourceIT {

    @Autowired
    private TriagemRepository triagemRepository;

    @Autowired
    private TriagemMapper triagemMapper;

    @Autowired
    private TriagemService triagemService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.TriagemSearchRepositoryMockConfiguration
     */
    @Autowired
    private TriagemSearchRepository mockTriagemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTriagemMockMvc;

    private Triagem triagem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TriagemResource triagemResource = new TriagemResource(triagemRepository, mockTriagemSearchRepository, triagemService);
        this.restTriagemMockMvc = MockMvcBuilders.standaloneSetup(triagemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Triagem createEntity(EntityManager em) {
        Triagem triagem = new Triagem();
        return triagem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Triagem createUpdatedEntity(EntityManager em) {
        Triagem triagem = new Triagem();
        return triagem;
    }

    @BeforeEach
    public void initTest() {
        triagem = createEntity(em);
    }

    @Test
    @Transactional
    public void createTriagem() throws Exception {
        int databaseSizeBeforeCreate = triagemRepository.findAll().size();

        // Create the Triagem
        TriagemDTO triagemDTO = triagemMapper.toDto(triagem);
        restTriagemMockMvc.perform(post("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isCreated());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeCreate + 1);
        Triagem testTriagem = triagemList.get(triagemList.size() - 1);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(1)).save(testTriagem);
    }

    @Test
    @Transactional
    public void createTriagemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = triagemRepository.findAll().size();

        // Create the Triagem with an existing ID
        triagem.setId(1L);
        TriagemDTO triagemDTO = triagemMapper.toDto(triagem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTriagemMockMvc.perform(post("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeCreate);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(0)).save(triagem);
    }


    @Test
    @Transactional
    public void getAllTriagems() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);

        // Get all the triagemList
        restTriagemMockMvc.perform(get("/api/triagems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(triagem.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTriagem() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);

        // Get the triagem
        restTriagemMockMvc.perform(get("/api/triagems/{id}", triagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(triagem.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTriagem() throws Exception {
        // Get the triagem
        restTriagemMockMvc.perform(get("/api/triagems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTriagem() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);

        int databaseSizeBeforeUpdate = triagemRepository.findAll().size();

        // Update the triagem
        Triagem updatedTriagem = triagemRepository.findById(triagem.getId()).get();
        // Disconnect from session so that the updates on updatedTriagem are not directly saved in db
        em.detach(updatedTriagem);
        TriagemDTO triagemDTO = triagemMapper.toDto(updatedTriagem);

        restTriagemMockMvc.perform(put("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isOk());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeUpdate);
        Triagem testTriagem = triagemList.get(triagemList.size() - 1);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(1)).save(testTriagem);
    }

    @Test
    @Transactional
    public void updateNonExistingTriagem() throws Exception {
        int databaseSizeBeforeUpdate = triagemRepository.findAll().size();

        // Create the Triagem
        TriagemDTO triagemDTO = triagemMapper.toDto(triagem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTriagemMockMvc.perform(put("/api/triagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(triagemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Triagem in the database
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(0)).save(triagem);
    }

    @Test
    @Transactional
    public void deleteTriagem() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);

        int databaseSizeBeforeDelete = triagemRepository.findAll().size();

        // Delete the triagem
        restTriagemMockMvc.perform(delete("/api/triagems/{id}", triagem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Triagem> triagemList = triagemRepository.findAll();
        assertThat(triagemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Triagem in Elasticsearch
        verify(mockTriagemSearchRepository, times(1)).deleteById(triagem.getId());
    }

    @Test
    @Transactional
    public void searchTriagem() throws Exception {
        // Initialize the database
        triagemRepository.saveAndFlush(triagem);
        when(mockTriagemSearchRepository.search(queryStringQuery("id:" + triagem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(triagem), PageRequest.of(0, 1), 1));
        // Search the triagem
        restTriagemMockMvc.perform(get("/api/_search/triagems?query=id:" + triagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(triagem.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Triagem.class);
        Triagem triagem1 = new Triagem();
        triagem1.setId(1L);
        Triagem triagem2 = new Triagem();
        triagem2.setId(triagem1.getId());
        assertThat(triagem1).isEqualTo(triagem2);
        triagem2.setId(2L);
        assertThat(triagem1).isNotEqualTo(triagem2);
        triagem1.setId(null);
        assertThat(triagem1).isNotEqualTo(triagem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TriagemDTO.class);
        TriagemDTO triagemDTO1 = new TriagemDTO();
        triagemDTO1.setId(1L);
        TriagemDTO triagemDTO2 = new TriagemDTO();
        assertThat(triagemDTO1).isNotEqualTo(triagemDTO2);
        triagemDTO2.setId(triagemDTO1.getId());
        assertThat(triagemDTO1).isEqualTo(triagemDTO2);
        triagemDTO2.setId(2L);
        assertThat(triagemDTO1).isNotEqualTo(triagemDTO2);
        triagemDTO1.setId(null);
        assertThat(triagemDTO1).isNotEqualTo(triagemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(triagemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(triagemMapper.fromId(null)).isNull();
    }
}
