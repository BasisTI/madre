package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.Recebimento;
import br.com.basis.suprimentos.repository.RecebimentoRepository;
import br.com.basis.suprimentos.repository.search.RecebimentoSearchRepository;
import br.com.basis.suprimentos.service.RecebimentoService;
import br.com.basis.suprimentos.service.dto.RecebimentoDTO;
import br.com.basis.suprimentos.service.mapper.RecebimentoMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;
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

import static br.com.basis.suprimentos.web.rest.TestUtil.createFormattingConversionService;
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
 * Integration tests for the {@link RecebimentoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class RecebimentoResourceIT {

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private RecebimentoMapper recebimentoMapper;

    @Autowired
    private RecebimentoService recebimentoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.RecebimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private RecebimentoSearchRepository mockRecebimentoSearchRepository;

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

    private MockMvc restRecebimentoMockMvc;

    private Recebimento recebimento;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recebimento createEntity(EntityManager em) {
        Recebimento recebimento = new Recebimento();
        return recebimento;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recebimento createUpdatedEntity(EntityManager em) {
        Recebimento recebimento = new Recebimento();
        return recebimento;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecebimentoResource recebimentoResource = new RecebimentoResource(recebimentoService);
        this.restRecebimentoMockMvc = MockMvcBuilders.standaloneSetup(recebimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        recebimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecebimento() throws Exception {
        int databaseSizeBeforeCreate = recebimentoRepository.findAll().size();

        // Create the Recebimento
        RecebimentoDTO recebimentoDTO = recebimentoMapper.toDto(recebimento);
        restRecebimentoMockMvc.perform(post("/api/recebimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recebimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Recebimento in the database
        List<Recebimento> recebimentoList = recebimentoRepository.findAll();
        assertThat(recebimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Recebimento testRecebimento = recebimentoList.get(recebimentoList.size() - 1);

        // Validate the Recebimento in Elasticsearch
        verify(mockRecebimentoSearchRepository, times(1)).save(testRecebimento);
    }

    @Test
    @Transactional
    public void createRecebimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recebimentoRepository.findAll().size();

        // Create the Recebimento with an existing ID
        recebimento.setId(1L);
        RecebimentoDTO recebimentoDTO = recebimentoMapper.toDto(recebimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecebimentoMockMvc.perform(post("/api/recebimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recebimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recebimento in the database
        List<Recebimento> recebimentoList = recebimentoRepository.findAll();
        assertThat(recebimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Recebimento in Elasticsearch
        verify(mockRecebimentoSearchRepository, times(0)).save(recebimento);
    }


    @Test
    @Transactional
    public void getAllRecebimentos() throws Exception {
        // Initialize the database
        recebimentoRepository.saveAndFlush(recebimento);

        // Get all the recebimentoList
        restRecebimentoMockMvc.perform(get("/api/recebimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recebimento.getId().intValue())));
    }

    @Test
    @Transactional
    public void getRecebimento() throws Exception {
        // Initialize the database
        recebimentoRepository.saveAndFlush(recebimento);

        // Get the recebimento
        restRecebimentoMockMvc.perform(get("/api/recebimentos/{id}", recebimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recebimento.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRecebimento() throws Exception {
        // Get the recebimento
        restRecebimentoMockMvc.perform(get("/api/recebimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecebimento() throws Exception {
        // Initialize the database
        recebimentoRepository.saveAndFlush(recebimento);

        int databaseSizeBeforeUpdate = recebimentoRepository.findAll().size();

        // Update the recebimento
        Recebimento updatedRecebimento = recebimentoRepository.findById(recebimento.getId()).get();
        // Disconnect from session so that the updates on updatedRecebimento are not directly saved in db
        em.detach(updatedRecebimento);
        RecebimentoDTO recebimentoDTO = recebimentoMapper.toDto(updatedRecebimento);

        restRecebimentoMockMvc.perform(put("/api/recebimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recebimentoDTO)))
            .andExpect(status().isOk());

        // Validate the Recebimento in the database
        List<Recebimento> recebimentoList = recebimentoRepository.findAll();
        assertThat(recebimentoList).hasSize(databaseSizeBeforeUpdate);
        Recebimento testRecebimento = recebimentoList.get(recebimentoList.size() - 1);

        // Validate the Recebimento in Elasticsearch
        verify(mockRecebimentoSearchRepository, times(1)).save(testRecebimento);
    }

    @Test
    @Transactional
    public void updateNonExistingRecebimento() throws Exception {
        int databaseSizeBeforeUpdate = recebimentoRepository.findAll().size();

        // Create the Recebimento
        RecebimentoDTO recebimentoDTO = recebimentoMapper.toDto(recebimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecebimentoMockMvc.perform(put("/api/recebimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recebimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recebimento in the database
        List<Recebimento> recebimentoList = recebimentoRepository.findAll();
        assertThat(recebimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Recebimento in Elasticsearch
        verify(mockRecebimentoSearchRepository, times(0)).save(recebimento);
    }

    @Test
    @Transactional
    public void deleteRecebimento() throws Exception {
        // Initialize the database
        recebimentoRepository.saveAndFlush(recebimento);

        int databaseSizeBeforeDelete = recebimentoRepository.findAll().size();

        // Delete the recebimento
        restRecebimentoMockMvc.perform(delete("/api/recebimentos/{id}", recebimento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recebimento> recebimentoList = recebimentoRepository.findAll();
        assertThat(recebimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Recebimento in Elasticsearch
        verify(mockRecebimentoSearchRepository, times(1)).deleteById(recebimento.getId());
    }

    @Test
    @Transactional
    public void searchRecebimento() throws Exception {
        // Initialize the database
        recebimentoRepository.saveAndFlush(recebimento);
        when(mockRecebimentoSearchRepository.search(queryStringQuery("id:" + recebimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(recebimento), PageRequest.of(0, 1), 1));
        // Search the recebimento
        restRecebimentoMockMvc.perform(get("/api/_search/recebimentos?query=id:" + recebimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recebimento.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recebimento.class);
        Recebimento recebimento1 = new Recebimento();
        recebimento1.setId(1L);
        Recebimento recebimento2 = new Recebimento();
        recebimento2.setId(recebimento1.getId());
        assertThat(recebimento1).isEqualTo(recebimento2);
        recebimento2.setId(2L);
        assertThat(recebimento1).isNotEqualTo(recebimento2);
        recebimento1.setId(null);
        assertThat(recebimento1).isNotEqualTo(recebimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecebimentoDTO.class);
        RecebimentoDTO recebimentoDTO1 = new RecebimentoDTO();
        recebimentoDTO1.setId(1L);
        RecebimentoDTO recebimentoDTO2 = new RecebimentoDTO();
        assertThat(recebimentoDTO1).isNotEqualTo(recebimentoDTO2);
        recebimentoDTO2.setId(recebimentoDTO1.getId());
        assertThat(recebimentoDTO1).isEqualTo(recebimentoDTO2);
        recebimentoDTO2.setId(2L);
        assertThat(recebimentoDTO1).isNotEqualTo(recebimentoDTO2);
        recebimentoDTO1.setId(null);
        assertThat(recebimentoDTO1).isNotEqualTo(recebimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recebimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recebimentoMapper.fromId(null)).isNull();
    }
}
