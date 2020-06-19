package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.MarcaComercial;
import br.com.basis.suprimentos.repository.MarcaComercialRepository;
import br.com.basis.suprimentos.repository.search.MarcaComercialSearchRepository;
import br.com.basis.suprimentos.service.MarcaComercialService;
import br.com.basis.suprimentos.service.dto.MarcaComercialDTO;
import br.com.basis.suprimentos.service.mapper.MarcaComercialMapper;
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
 * Integration tests for the {@link MarcaComercialResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class MarcaComercialResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private MarcaComercialRepository marcaComercialRepository;

    @Autowired
    private MarcaComercialMapper marcaComercialMapper;

    @Autowired
    private MarcaComercialService marcaComercialService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.MarcaComercialSearchRepositoryMockConfiguration
     */
    @Autowired
    private MarcaComercialSearchRepository mockMarcaComercialSearchRepository;

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

    private MockMvc restMarcaComercialMockMvc;

    private MarcaComercial marcaComercial;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarcaComercial createEntity(EntityManager em) {
        MarcaComercial marcaComercial = new MarcaComercial()
                .descricao(DEFAULT_DESCRICAO);
        return marcaComercial;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MarcaComercial createUpdatedEntity(EntityManager em) {
        MarcaComercial marcaComercial = new MarcaComercial()
                .descricao(UPDATED_DESCRICAO);
        return marcaComercial;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarcaComercialResource marcaComercialResource = new MarcaComercialResource(marcaComercialService);
        this.restMarcaComercialMockMvc = MockMvcBuilders.standaloneSetup(marcaComercialResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        marcaComercial = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarcaComercial() throws Exception {
        int databaseSizeBeforeCreate = marcaComercialRepository.findAll().size();

        // Create the MarcaComercial
        MarcaComercialDTO marcaComercialDTO = marcaComercialMapper.toDto(marcaComercial);
        restMarcaComercialMockMvc.perform(post("/api/marca-comercials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(marcaComercialDTO)))
                .andExpect(status().isCreated());

        // Validate the MarcaComercial in the database
        List<MarcaComercial> marcaComercialList = marcaComercialRepository.findAll();
        assertThat(marcaComercialList).hasSize(databaseSizeBeforeCreate + 1);
        MarcaComercial testMarcaComercial = marcaComercialList.get(marcaComercialList.size() - 1);
        assertThat(testMarcaComercial.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the MarcaComercial in Elasticsearch
        verify(mockMarcaComercialSearchRepository, times(1)).save(testMarcaComercial);
    }

    @Test
    @Transactional
    public void createMarcaComercialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marcaComercialRepository.findAll().size();

        // Create the MarcaComercial with an existing ID
        marcaComercial.setId(1L);
        MarcaComercialDTO marcaComercialDTO = marcaComercialMapper.toDto(marcaComercial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarcaComercialMockMvc.perform(post("/api/marca-comercials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(marcaComercialDTO)))
                .andExpect(status().isBadRequest());

        // Validate the MarcaComercial in the database
        List<MarcaComercial> marcaComercialList = marcaComercialRepository.findAll();
        assertThat(marcaComercialList).hasSize(databaseSizeBeforeCreate);

        // Validate the MarcaComercial in Elasticsearch
        verify(mockMarcaComercialSearchRepository, times(0)).save(marcaComercial);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = marcaComercialRepository.findAll().size();
        // set the field null
        marcaComercial.setDescricao(null);

        // Create the MarcaComercial, which fails.
        MarcaComercialDTO marcaComercialDTO = marcaComercialMapper.toDto(marcaComercial);

        restMarcaComercialMockMvc.perform(post("/api/marca-comercials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(marcaComercialDTO)))
                .andExpect(status().isBadRequest());

        List<MarcaComercial> marcaComercialList = marcaComercialRepository.findAll();
        assertThat(marcaComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarcaComercials() throws Exception {
        // Initialize the database
        marcaComercialRepository.saveAndFlush(marcaComercial);

        // Get all the marcaComercialList
        restMarcaComercialMockMvc.perform(get("/api/marca-comercials?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(marcaComercial.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getMarcaComercial() throws Exception {
        // Initialize the database
        marcaComercialRepository.saveAndFlush(marcaComercial);

        // Get the marcaComercial
        restMarcaComercialMockMvc.perform(get("/api/marca-comercials/{id}", marcaComercial.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(marcaComercial.getId().intValue()))
                .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingMarcaComercial() throws Exception {
        // Get the marcaComercial
        restMarcaComercialMockMvc.perform(get("/api/marca-comercials/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarcaComercial() throws Exception {
        // Initialize the database
        marcaComercialRepository.saveAndFlush(marcaComercial);

        int databaseSizeBeforeUpdate = marcaComercialRepository.findAll().size();

        // Update the marcaComercial
        MarcaComercial updatedMarcaComercial = marcaComercialRepository.findById(marcaComercial.getId()).get();
        // Disconnect from session so that the updates on updatedMarcaComercial are not directly saved in db
        em.detach(updatedMarcaComercial);
        updatedMarcaComercial
                .descricao(UPDATED_DESCRICAO);
        MarcaComercialDTO marcaComercialDTO = marcaComercialMapper.toDto(updatedMarcaComercial);

        restMarcaComercialMockMvc.perform(put("/api/marca-comercials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(marcaComercialDTO)))
                .andExpect(status().isOk());

        // Validate the MarcaComercial in the database
        List<MarcaComercial> marcaComercialList = marcaComercialRepository.findAll();
        assertThat(marcaComercialList).hasSize(databaseSizeBeforeUpdate);
        MarcaComercial testMarcaComercial = marcaComercialList.get(marcaComercialList.size() - 1);
        assertThat(testMarcaComercial.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the MarcaComercial in Elasticsearch
        verify(mockMarcaComercialSearchRepository, times(1)).save(testMarcaComercial);
    }

    @Test
    @Transactional
    public void updateNonExistingMarcaComercial() throws Exception {
        int databaseSizeBeforeUpdate = marcaComercialRepository.findAll().size();

        // Create the MarcaComercial
        MarcaComercialDTO marcaComercialDTO = marcaComercialMapper.toDto(marcaComercial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarcaComercialMockMvc.perform(put("/api/marca-comercials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(marcaComercialDTO)))
                .andExpect(status().isBadRequest());

        // Validate the MarcaComercial in the database
        List<MarcaComercial> marcaComercialList = marcaComercialRepository.findAll();
        assertThat(marcaComercialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MarcaComercial in Elasticsearch
        verify(mockMarcaComercialSearchRepository, times(0)).save(marcaComercial);
    }

    @Test
    @Transactional
    public void deleteMarcaComercial() throws Exception {
        // Initialize the database
        marcaComercialRepository.saveAndFlush(marcaComercial);

        int databaseSizeBeforeDelete = marcaComercialRepository.findAll().size();

        // Delete the marcaComercial
        restMarcaComercialMockMvc.perform(delete("/api/marca-comercials/{id}", marcaComercial.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MarcaComercial> marcaComercialList = marcaComercialRepository.findAll();
        assertThat(marcaComercialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MarcaComercial in Elasticsearch
        verify(mockMarcaComercialSearchRepository, times(1)).deleteById(marcaComercial.getId());
    }

    @Test
    @Transactional
    public void searchMarcaComercial() throws Exception {
        // Initialize the database
        marcaComercialRepository.saveAndFlush(marcaComercial);
        when(mockMarcaComercialSearchRepository.search(queryStringQuery("id:" + marcaComercial.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(marcaComercial), PageRequest.of(0, 1), 1));
        // Search the marcaComercial
        restMarcaComercialMockMvc.perform(get("/api/_search/marca-comercials?query=id:" + marcaComercial.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(marcaComercial.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarcaComercial.class);
        MarcaComercial marcaComercial1 = new MarcaComercial();
        marcaComercial1.setId(1L);
        MarcaComercial marcaComercial2 = new MarcaComercial();
        marcaComercial2.setId(marcaComercial1.getId());
        assertThat(marcaComercial1).isEqualTo(marcaComercial2);
        marcaComercial2.setId(2L);
        assertThat(marcaComercial1).isNotEqualTo(marcaComercial2);
        marcaComercial1.setId(null);
        assertThat(marcaComercial1).isNotEqualTo(marcaComercial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarcaComercialDTO.class);
        MarcaComercialDTO marcaComercialDTO1 = new MarcaComercialDTO();
        marcaComercialDTO1.setId(1L);
        MarcaComercialDTO marcaComercialDTO2 = new MarcaComercialDTO();
        assertThat(marcaComercialDTO1).isNotEqualTo(marcaComercialDTO2);
        marcaComercialDTO2.setId(marcaComercialDTO1.getId());
        assertThat(marcaComercialDTO1).isEqualTo(marcaComercialDTO2);
        marcaComercialDTO2.setId(2L);
        assertThat(marcaComercialDTO1).isNotEqualTo(marcaComercialDTO2);
        marcaComercialDTO1.setId(null);
        assertThat(marcaComercialDTO1).isNotEqualTo(marcaComercialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marcaComercialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marcaComercialMapper.fromId(null)).isNull();
    }
}
