package br.com.basis.consulta.web.rest;

import br.com.basis.consulta.MadreconsultaApp;
import br.com.basis.consulta.domain.CondicaoDeAtendimento;
import br.com.basis.consulta.repository.CondicaoDeAtendimentoRepository;
import br.com.basis.consulta.repository.search.CondicaoDeAtendimentoSearchRepository;
import br.com.basis.consulta.service.CondicaoDeAtendimentoService;
import br.com.basis.consulta.service.dto.CondicaoDeAtendimentoDTO;
import br.com.basis.consulta.service.mapper.CondicaoDeAtendimentoMapper;
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

import static br.com.basis.consulta.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CondicaoDeAtendimentoResource} REST controller.
 */
@SpringBootTest(classes = MadreconsultaApp.class)
public class CondicaoDeAtendimentoResourceIT {

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDADE = "BBBBBBBBBB";

    @Autowired
    private CondicaoDeAtendimentoRepository condicaoDeAtendimentoRepository;

    @Autowired
    private CondicaoDeAtendimentoMapper condicaoDeAtendimentoMapper;

    @Autowired
    private CondicaoDeAtendimentoService condicaoDeAtendimentoService;

    /**
     * This repository is mocked in the br.com.basis.consulta.repository.search test package.
     *
     * @see br.com.basis.consulta.repository.search.CondicaoDeAtendimentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CondicaoDeAtendimentoSearchRepository mockCondicaoDeAtendimentoSearchRepository;

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

    private MockMvc restCondicaoDeAtendimentoMockMvc;

    private CondicaoDeAtendimento condicaoDeAtendimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CondicaoDeAtendimentoResource condicaoDeAtendimentoResource = new CondicaoDeAtendimentoResource(condicaoDeAtendimentoService);
        this.restCondicaoDeAtendimentoMockMvc = MockMvcBuilders.standaloneSetup(condicaoDeAtendimentoResource)
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
    public static CondicaoDeAtendimento createEntity(EntityManager em) {
        CondicaoDeAtendimento condicaoDeAtendimento = new CondicaoDeAtendimento()
            .sigla(DEFAULT_SIGLA)
            .especialidade(DEFAULT_ESPECIALIDADE);
        return condicaoDeAtendimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CondicaoDeAtendimento createUpdatedEntity(EntityManager em) {
        CondicaoDeAtendimento condicaoDeAtendimento = new CondicaoDeAtendimento()
            .sigla(UPDATED_SIGLA)
            .especialidade(UPDATED_ESPECIALIDADE);
        return condicaoDeAtendimento;
    }

    @BeforeEach
    public void initTest() {
        condicaoDeAtendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createCondicaoDeAtendimento() throws Exception {
        int databaseSizeBeforeCreate = condicaoDeAtendimentoRepository.findAll().size();

        // Create the CondicaoDeAtendimento
        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO = condicaoDeAtendimentoMapper.toDto(condicaoDeAtendimento);
        restCondicaoDeAtendimentoMockMvc.perform(post("/api/condicao-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(condicaoDeAtendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the CondicaoDeAtendimento in the database
        List<CondicaoDeAtendimento> condicaoDeAtendimentoList = condicaoDeAtendimentoRepository.findAll();
        assertThat(condicaoDeAtendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        CondicaoDeAtendimento testCondicaoDeAtendimento = condicaoDeAtendimentoList.get(condicaoDeAtendimentoList.size() - 1);
        assertThat(testCondicaoDeAtendimento.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testCondicaoDeAtendimento.getEspecialidade()).isEqualTo(DEFAULT_ESPECIALIDADE);

        // Validate the CondicaoDeAtendimento in Elasticsearch
        verify(mockCondicaoDeAtendimentoSearchRepository, times(1)).save(testCondicaoDeAtendimento);
    }

    @Test
    @Transactional
    public void createCondicaoDeAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = condicaoDeAtendimentoRepository.findAll().size();

        // Create the CondicaoDeAtendimento with an existing ID
        condicaoDeAtendimento.setId(1L);
        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO = condicaoDeAtendimentoMapper.toDto(condicaoDeAtendimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCondicaoDeAtendimentoMockMvc.perform(post("/api/condicao-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(condicaoDeAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CondicaoDeAtendimento in the database
        List<CondicaoDeAtendimento> condicaoDeAtendimentoList = condicaoDeAtendimentoRepository.findAll();
        assertThat(condicaoDeAtendimentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CondicaoDeAtendimento in Elasticsearch
        verify(mockCondicaoDeAtendimentoSearchRepository, times(0)).save(condicaoDeAtendimento);
    }


    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = condicaoDeAtendimentoRepository.findAll().size();
        // set the field null
        condicaoDeAtendimento.setSigla(null);

        // Create the CondicaoDeAtendimento, which fails.
        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO = condicaoDeAtendimentoMapper.toDto(condicaoDeAtendimento);

        restCondicaoDeAtendimentoMockMvc.perform(post("/api/condicao-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(condicaoDeAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        List<CondicaoDeAtendimento> condicaoDeAtendimentoList = condicaoDeAtendimentoRepository.findAll();
        assertThat(condicaoDeAtendimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEspecialidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = condicaoDeAtendimentoRepository.findAll().size();
        // set the field null
        condicaoDeAtendimento.setEspecialidade(null);

        // Create the CondicaoDeAtendimento, which fails.
        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO = condicaoDeAtendimentoMapper.toDto(condicaoDeAtendimento);

        restCondicaoDeAtendimentoMockMvc.perform(post("/api/condicao-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(condicaoDeAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        List<CondicaoDeAtendimento> condicaoDeAtendimentoList = condicaoDeAtendimentoRepository.findAll();
        assertThat(condicaoDeAtendimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCondicaoDeAtendimentos() throws Exception {
        // Initialize the database
        condicaoDeAtendimentoRepository.saveAndFlush(condicaoDeAtendimento);

        // Get all the condicaoDeAtendimentoList
        restCondicaoDeAtendimentoMockMvc.perform(get("/api/condicao-de-atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condicaoDeAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].especialidade").value(hasItem(DEFAULT_ESPECIALIDADE)));
    }
    
    @Test
    @Transactional
    public void getCondicaoDeAtendimento() throws Exception {
        // Initialize the database
        condicaoDeAtendimentoRepository.saveAndFlush(condicaoDeAtendimento);

        // Get the condicaoDeAtendimento
        restCondicaoDeAtendimentoMockMvc.perform(get("/api/condicao-de-atendimentos/{id}", condicaoDeAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(condicaoDeAtendimento.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.especialidade").value(DEFAULT_ESPECIALIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingCondicaoDeAtendimento() throws Exception {
        // Get the condicaoDeAtendimento
        restCondicaoDeAtendimentoMockMvc.perform(get("/api/condicao-de-atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCondicaoDeAtendimento() throws Exception {
        // Initialize the database
        condicaoDeAtendimentoRepository.saveAndFlush(condicaoDeAtendimento);

        int databaseSizeBeforeUpdate = condicaoDeAtendimentoRepository.findAll().size();

        // Update the condicaoDeAtendimento
        CondicaoDeAtendimento updatedCondicaoDeAtendimento = condicaoDeAtendimentoRepository.findById(condicaoDeAtendimento.getId()).get();
        // Disconnect from session so that the updates on updatedCondicaoDeAtendimento are not directly saved in db
        em.detach(updatedCondicaoDeAtendimento);
        updatedCondicaoDeAtendimento
            .sigla(UPDATED_SIGLA)
            .especialidade(UPDATED_ESPECIALIDADE);
        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO = condicaoDeAtendimentoMapper.toDto(updatedCondicaoDeAtendimento);

        restCondicaoDeAtendimentoMockMvc.perform(put("/api/condicao-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(condicaoDeAtendimentoDTO)))
            .andExpect(status().isOk());

        // Validate the CondicaoDeAtendimento in the database
        List<CondicaoDeAtendimento> condicaoDeAtendimentoList = condicaoDeAtendimentoRepository.findAll();
        assertThat(condicaoDeAtendimentoList).hasSize(databaseSizeBeforeUpdate);
        CondicaoDeAtendimento testCondicaoDeAtendimento = condicaoDeAtendimentoList.get(condicaoDeAtendimentoList.size() - 1);
        assertThat(testCondicaoDeAtendimento.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testCondicaoDeAtendimento.getEspecialidade()).isEqualTo(UPDATED_ESPECIALIDADE);

        // Validate the CondicaoDeAtendimento in Elasticsearch
        verify(mockCondicaoDeAtendimentoSearchRepository, times(1)).save(testCondicaoDeAtendimento);
    }

    @Test
    @Transactional
    public void updateNonExistingCondicaoDeAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = condicaoDeAtendimentoRepository.findAll().size();

        // Create the CondicaoDeAtendimento
        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO = condicaoDeAtendimentoMapper.toDto(condicaoDeAtendimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCondicaoDeAtendimentoMockMvc.perform(put("/api/condicao-de-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(condicaoDeAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CondicaoDeAtendimento in the database
        List<CondicaoDeAtendimento> condicaoDeAtendimentoList = condicaoDeAtendimentoRepository.findAll();
        assertThat(condicaoDeAtendimentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CondicaoDeAtendimento in Elasticsearch
        verify(mockCondicaoDeAtendimentoSearchRepository, times(0)).save(condicaoDeAtendimento);
    }

    @Test
    @Transactional
    public void deleteCondicaoDeAtendimento() throws Exception {
        // Initialize the database
        condicaoDeAtendimentoRepository.saveAndFlush(condicaoDeAtendimento);

        int databaseSizeBeforeDelete = condicaoDeAtendimentoRepository.findAll().size();

        // Delete the condicaoDeAtendimento
        restCondicaoDeAtendimentoMockMvc.perform(delete("/api/condicao-de-atendimentos/{id}", condicaoDeAtendimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CondicaoDeAtendimento> condicaoDeAtendimentoList = condicaoDeAtendimentoRepository.findAll();
        assertThat(condicaoDeAtendimentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CondicaoDeAtendimento in Elasticsearch
        verify(mockCondicaoDeAtendimentoSearchRepository, times(1)).deleteById(condicaoDeAtendimento.getId());
    }

    @Test
    @Transactional
    public void searchCondicaoDeAtendimento() throws Exception {
        // Initialize the database
        condicaoDeAtendimentoRepository.saveAndFlush(condicaoDeAtendimento);
        when(mockCondicaoDeAtendimentoSearchRepository.search(queryStringQuery("id:" + condicaoDeAtendimento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(condicaoDeAtendimento), PageRequest.of(0, 1), 1));
        // Search the condicaoDeAtendimento
        restCondicaoDeAtendimentoMockMvc.perform(get("/api/_search/condicao-de-atendimentos?query=id:" + condicaoDeAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condicaoDeAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].especialidade").value(hasItem(DEFAULT_ESPECIALIDADE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CondicaoDeAtendimento.class);
        CondicaoDeAtendimento condicaoDeAtendimento1 = new CondicaoDeAtendimento();
        condicaoDeAtendimento1.setId(1L);
        CondicaoDeAtendimento condicaoDeAtendimento2 = new CondicaoDeAtendimento();
        condicaoDeAtendimento2.setId(condicaoDeAtendimento1.getId());
        assertThat(condicaoDeAtendimento1).isEqualTo(condicaoDeAtendimento2);
        condicaoDeAtendimento2.setId(2L);
        assertThat(condicaoDeAtendimento1).isNotEqualTo(condicaoDeAtendimento2);
        condicaoDeAtendimento1.setId(null);
        assertThat(condicaoDeAtendimento1).isNotEqualTo(condicaoDeAtendimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CondicaoDeAtendimentoDTO.class);
        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO1 = new CondicaoDeAtendimentoDTO();
        condicaoDeAtendimentoDTO1.setId(1L);
        CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO2 = new CondicaoDeAtendimentoDTO();
        assertThat(condicaoDeAtendimentoDTO1).isNotEqualTo(condicaoDeAtendimentoDTO2);
        condicaoDeAtendimentoDTO2.setId(condicaoDeAtendimentoDTO1.getId());
        assertThat(condicaoDeAtendimentoDTO1).isEqualTo(condicaoDeAtendimentoDTO2);
        condicaoDeAtendimentoDTO2.setId(2L);
        assertThat(condicaoDeAtendimentoDTO1).isNotEqualTo(condicaoDeAtendimentoDTO2);
        condicaoDeAtendimentoDTO1.setId(null);
        assertThat(condicaoDeAtendimentoDTO1).isNotEqualTo(condicaoDeAtendimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(condicaoDeAtendimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(condicaoDeAtendimentoMapper.fromId(null)).isNull();
    }
}
