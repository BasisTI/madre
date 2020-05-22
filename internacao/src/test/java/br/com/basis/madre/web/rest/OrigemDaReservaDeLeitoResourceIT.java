package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.OrigemDaReservaDeLeito;
import br.com.basis.madre.repository.OrigemDaReservaDeLeitoRepository;
import br.com.basis.madre.repository.search.OrigemDaReservaDeLeitoSearchRepository;
import br.com.basis.madre.service.OrigemDaReservaDeLeitoService;
import br.com.basis.madre.service.dto.OrigemDaReservaDeLeitoDTO;
import br.com.basis.madre.service.mapper.OrigemDaReservaDeLeitoMapper;
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

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
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
 * Integration tests for the {@link OrigemDaReservaDeLeitoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class OrigemDaReservaDeLeitoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private OrigemDaReservaDeLeitoRepository origemDaReservaDeLeitoRepository;

    @Autowired
    private OrigemDaReservaDeLeitoMapper origemDaReservaDeLeitoMapper;

    @Autowired
    private OrigemDaReservaDeLeitoService origemDaReservaDeLeitoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.OrigemDaReservaDeLeitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrigemDaReservaDeLeitoSearchRepository mockOrigemDaReservaDeLeitoSearchRepository;

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

    private MockMvc restOrigemDaReservaDeLeitoMockMvc;

    private OrigemDaReservaDeLeito origemDaReservaDeLeito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrigemDaReservaDeLeitoResource origemDaReservaDeLeitoResource = new OrigemDaReservaDeLeitoResource(origemDaReservaDeLeitoService);
        this.restOrigemDaReservaDeLeitoMockMvc = MockMvcBuilders.standaloneSetup(origemDaReservaDeLeitoResource)
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
    public static OrigemDaReservaDeLeito createEntity(EntityManager em) {
        OrigemDaReservaDeLeito origemDaReservaDeLeito = new OrigemDaReservaDeLeito()
            .nome(DEFAULT_NOME);
        return origemDaReservaDeLeito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrigemDaReservaDeLeito createUpdatedEntity(EntityManager em) {
        OrigemDaReservaDeLeito origemDaReservaDeLeito = new OrigemDaReservaDeLeito()
            .nome(UPDATED_NOME);
        return origemDaReservaDeLeito;
    }

    @BeforeEach
    public void initTest() {
        origemDaReservaDeLeito = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrigemDaReservaDeLeito() throws Exception {
        int databaseSizeBeforeCreate = origemDaReservaDeLeitoRepository.findAll().size();

        // Create the OrigemDaReservaDeLeito
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO = origemDaReservaDeLeitoMapper.toDto(origemDaReservaDeLeito);
        restOrigemDaReservaDeLeitoMockMvc.perform(post("/api/origem-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaReservaDeLeitoDTO)))
            .andExpect(status().isCreated());

        // Validate the OrigemDaReservaDeLeito in the database
        List<OrigemDaReservaDeLeito> origemDaReservaDeLeitoList = origemDaReservaDeLeitoRepository.findAll();
        assertThat(origemDaReservaDeLeitoList).hasSize(databaseSizeBeforeCreate + 1);
        OrigemDaReservaDeLeito testOrigemDaReservaDeLeito = origemDaReservaDeLeitoList.get(origemDaReservaDeLeitoList.size() - 1);
        assertThat(testOrigemDaReservaDeLeito.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the OrigemDaReservaDeLeito in Elasticsearch
        verify(mockOrigemDaReservaDeLeitoSearchRepository, times(1)).save(testOrigemDaReservaDeLeito);
    }

    @Test
    @Transactional
    public void createOrigemDaReservaDeLeitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = origemDaReservaDeLeitoRepository.findAll().size();

        // Create the OrigemDaReservaDeLeito with an existing ID
        origemDaReservaDeLeito.setId(1L);
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO = origemDaReservaDeLeitoMapper.toDto(origemDaReservaDeLeito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrigemDaReservaDeLeitoMockMvc.perform(post("/api/origem-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaReservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrigemDaReservaDeLeito in the database
        List<OrigemDaReservaDeLeito> origemDaReservaDeLeitoList = origemDaReservaDeLeitoRepository.findAll();
        assertThat(origemDaReservaDeLeitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrigemDaReservaDeLeito in Elasticsearch
        verify(mockOrigemDaReservaDeLeitoSearchRepository, times(0)).save(origemDaReservaDeLeito);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = origemDaReservaDeLeitoRepository.findAll().size();
        // set the field null
        origemDaReservaDeLeito.setNome(null);

        // Create the OrigemDaReservaDeLeito, which fails.
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO = origemDaReservaDeLeitoMapper.toDto(origemDaReservaDeLeito);

        restOrigemDaReservaDeLeitoMockMvc.perform(post("/api/origem-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaReservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        List<OrigemDaReservaDeLeito> origemDaReservaDeLeitoList = origemDaReservaDeLeitoRepository.findAll();
        assertThat(origemDaReservaDeLeitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrigemDaReservaDeLeitos() throws Exception {
        // Initialize the database
        origemDaReservaDeLeitoRepository.saveAndFlush(origemDaReservaDeLeito);

        // Get all the origemDaReservaDeLeitoList
        restOrigemDaReservaDeLeitoMockMvc.perform(get("/api/origem-da-reserva-de-leitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origemDaReservaDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getOrigemDaReservaDeLeito() throws Exception {
        // Initialize the database
        origemDaReservaDeLeitoRepository.saveAndFlush(origemDaReservaDeLeito);

        // Get the origemDaReservaDeLeito
        restOrigemDaReservaDeLeitoMockMvc.perform(get("/api/origem-da-reserva-de-leitos/{id}", origemDaReservaDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(origemDaReservaDeLeito.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingOrigemDaReservaDeLeito() throws Exception {
        // Get the origemDaReservaDeLeito
        restOrigemDaReservaDeLeitoMockMvc.perform(get("/api/origem-da-reserva-de-leitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrigemDaReservaDeLeito() throws Exception {
        // Initialize the database
        origemDaReservaDeLeitoRepository.saveAndFlush(origemDaReservaDeLeito);

        int databaseSizeBeforeUpdate = origemDaReservaDeLeitoRepository.findAll().size();

        // Update the origemDaReservaDeLeito
        OrigemDaReservaDeLeito updatedOrigemDaReservaDeLeito = origemDaReservaDeLeitoRepository.findById(origemDaReservaDeLeito.getId()).get();
        // Disconnect from session so that the updates on updatedOrigemDaReservaDeLeito are not directly saved in db
        em.detach(updatedOrigemDaReservaDeLeito);
        updatedOrigemDaReservaDeLeito
            .nome(UPDATED_NOME);
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO = origemDaReservaDeLeitoMapper.toDto(updatedOrigemDaReservaDeLeito);

        restOrigemDaReservaDeLeitoMockMvc.perform(put("/api/origem-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaReservaDeLeitoDTO)))
            .andExpect(status().isOk());

        // Validate the OrigemDaReservaDeLeito in the database
        List<OrigemDaReservaDeLeito> origemDaReservaDeLeitoList = origemDaReservaDeLeitoRepository.findAll();
        assertThat(origemDaReservaDeLeitoList).hasSize(databaseSizeBeforeUpdate);
        OrigemDaReservaDeLeito testOrigemDaReservaDeLeito = origemDaReservaDeLeitoList.get(origemDaReservaDeLeitoList.size() - 1);
        assertThat(testOrigemDaReservaDeLeito.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the OrigemDaReservaDeLeito in Elasticsearch
        verify(mockOrigemDaReservaDeLeitoSearchRepository, times(1)).save(testOrigemDaReservaDeLeito);
    }

    @Test
    @Transactional
    public void updateNonExistingOrigemDaReservaDeLeito() throws Exception {
        int databaseSizeBeforeUpdate = origemDaReservaDeLeitoRepository.findAll().size();

        // Create the OrigemDaReservaDeLeito
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO = origemDaReservaDeLeitoMapper.toDto(origemDaReservaDeLeito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrigemDaReservaDeLeitoMockMvc.perform(put("/api/origem-da-reserva-de-leitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origemDaReservaDeLeitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrigemDaReservaDeLeito in the database
        List<OrigemDaReservaDeLeito> origemDaReservaDeLeitoList = origemDaReservaDeLeitoRepository.findAll();
        assertThat(origemDaReservaDeLeitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrigemDaReservaDeLeito in Elasticsearch
        verify(mockOrigemDaReservaDeLeitoSearchRepository, times(0)).save(origemDaReservaDeLeito);
    }

    @Test
    @Transactional
    public void deleteOrigemDaReservaDeLeito() throws Exception {
        // Initialize the database
        origemDaReservaDeLeitoRepository.saveAndFlush(origemDaReservaDeLeito);

        int databaseSizeBeforeDelete = origemDaReservaDeLeitoRepository.findAll().size();

        // Delete the origemDaReservaDeLeito
        restOrigemDaReservaDeLeitoMockMvc.perform(delete("/api/origem-da-reserva-de-leitos/{id}", origemDaReservaDeLeito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrigemDaReservaDeLeito> origemDaReservaDeLeitoList = origemDaReservaDeLeitoRepository.findAll();
        assertThat(origemDaReservaDeLeitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrigemDaReservaDeLeito in Elasticsearch
        verify(mockOrigemDaReservaDeLeitoSearchRepository, times(1)).deleteById(origemDaReservaDeLeito.getId());
    }

    @Test
    @Transactional
    public void searchOrigemDaReservaDeLeito() throws Exception {
        // Initialize the database
        origemDaReservaDeLeitoRepository.saveAndFlush(origemDaReservaDeLeito);
        when(mockOrigemDaReservaDeLeitoSearchRepository.search(queryStringQuery("id:" + origemDaReservaDeLeito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(origemDaReservaDeLeito), PageRequest.of(0, 1), 1));
        // Search the origemDaReservaDeLeito
        restOrigemDaReservaDeLeitoMockMvc.perform(get("/api/_search/origem-da-reserva-de-leitos?query=id:" + origemDaReservaDeLeito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origemDaReservaDeLeito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemDaReservaDeLeito.class);
        OrigemDaReservaDeLeito origemDaReservaDeLeito1 = new OrigemDaReservaDeLeito();
        origemDaReservaDeLeito1.setId(1L);
        OrigemDaReservaDeLeito origemDaReservaDeLeito2 = new OrigemDaReservaDeLeito();
        origemDaReservaDeLeito2.setId(origemDaReservaDeLeito1.getId());
        assertThat(origemDaReservaDeLeito1).isEqualTo(origemDaReservaDeLeito2);
        origemDaReservaDeLeito2.setId(2L);
        assertThat(origemDaReservaDeLeito1).isNotEqualTo(origemDaReservaDeLeito2);
        origemDaReservaDeLeito1.setId(null);
        assertThat(origemDaReservaDeLeito1).isNotEqualTo(origemDaReservaDeLeito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemDaReservaDeLeitoDTO.class);
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO1 = new OrigemDaReservaDeLeitoDTO();
        origemDaReservaDeLeitoDTO1.setId(1L);
        OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO2 = new OrigemDaReservaDeLeitoDTO();
        assertThat(origemDaReservaDeLeitoDTO1).isNotEqualTo(origemDaReservaDeLeitoDTO2);
        origemDaReservaDeLeitoDTO2.setId(origemDaReservaDeLeitoDTO1.getId());
        assertThat(origemDaReservaDeLeitoDTO1).isEqualTo(origemDaReservaDeLeitoDTO2);
        origemDaReservaDeLeitoDTO2.setId(2L);
        assertThat(origemDaReservaDeLeitoDTO1).isNotEqualTo(origemDaReservaDeLeitoDTO2);
        origemDaReservaDeLeitoDTO1.setId(null);
        assertThat(origemDaReservaDeLeitoDTO1).isNotEqualTo(origemDaReservaDeLeitoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(origemDaReservaDeLeitoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(origemDaReservaDeLeitoMapper.fromId(null)).isNull();
    }
}
