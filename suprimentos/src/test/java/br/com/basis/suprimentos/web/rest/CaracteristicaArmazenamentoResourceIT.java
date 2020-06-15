package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.CaracteristicaArmazenamento;
import br.com.basis.suprimentos.repository.CaracteristicaArmazenamentoRepository;
import br.com.basis.suprimentos.repository.search.CaracteristicaArmazenamentoSearchRepository;
import br.com.basis.suprimentos.service.CaracteristicaArmazenamentoService;
import br.com.basis.suprimentos.service.dto.CaracteristicaArmazenamentoDTO;
import br.com.basis.suprimentos.service.mapper.CaracteristicaArmazenamentoMapper;
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
 * Integration tests for the {@link CaracteristicaArmazenamentoResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class CaracteristicaArmazenamentoResourceIT {

    private static final Boolean DEFAULT_MISTURA_MATERIAIS_DIREITOS = false;
    private static final Boolean UPDATED_MISTURA_MATERIAIS_DIREITOS = true;

    private static final Boolean DEFAULT_MISTURA_GRUPO_MATERIAIS = false;
    private static final Boolean UPDATED_MISTURA_GRUPO_MATERIAIS = true;

    @Autowired
    private CaracteristicaArmazenamentoRepository caracteristicaArmazenamentoRepository;

    @Autowired
    private CaracteristicaArmazenamentoMapper caracteristicaArmazenamentoMapper;

    @Autowired
    private CaracteristicaArmazenamentoService caracteristicaArmazenamentoService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.CaracteristicaArmazenamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CaracteristicaArmazenamentoSearchRepository mockCaracteristicaArmazenamentoSearchRepository;

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

    private MockMvc restCaracteristicaArmazenamentoMockMvc;

    private CaracteristicaArmazenamento caracteristicaArmazenamento;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaracteristicaArmazenamento createEntity(EntityManager em) {
        CaracteristicaArmazenamento caracteristicaArmazenamento = CaracteristicaArmazenamento.builder()
            .misturaMateriaisDireitos(DEFAULT_MISTURA_MATERIAIS_DIREITOS)
            .misturaGrupoMateriais(DEFAULT_MISTURA_GRUPO_MATERIAIS).build();
        return caracteristicaArmazenamento;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaracteristicaArmazenamento createUpdatedEntity(EntityManager em) {
        CaracteristicaArmazenamento caracteristicaArmazenamento = CaracteristicaArmazenamento.builder()
            .misturaMateriaisDireitos(UPDATED_MISTURA_MATERIAIS_DIREITOS)
            .misturaGrupoMateriais(UPDATED_MISTURA_GRUPO_MATERIAIS).build();
        return caracteristicaArmazenamento;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaracteristicaArmazenamentoResource caracteristicaArmazenamentoResource = new CaracteristicaArmazenamentoResource(caracteristicaArmazenamentoService);
        this.restCaracteristicaArmazenamentoMockMvc = MockMvcBuilders.standaloneSetup(caracteristicaArmazenamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        caracteristicaArmazenamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaracteristicaArmazenamento() throws Exception {
        int databaseSizeBeforeCreate = caracteristicaArmazenamentoRepository.findAll().size();

        // Create the CaracteristicaArmazenamento
        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO = caracteristicaArmazenamentoMapper.toDto(caracteristicaArmazenamento);
        restCaracteristicaArmazenamentoMockMvc.perform(post("/api/caracteristica-armazenamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaArmazenamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the CaracteristicaArmazenamento in the database
        List<CaracteristicaArmazenamento> caracteristicaArmazenamentoList = caracteristicaArmazenamentoRepository.findAll();
        assertThat(caracteristicaArmazenamentoList).hasSize(databaseSizeBeforeCreate + 1);
        CaracteristicaArmazenamento testCaracteristicaArmazenamento = caracteristicaArmazenamentoList.get(caracteristicaArmazenamentoList.size() - 1);
        assertThat(testCaracteristicaArmazenamento.getMisturaMateriaisDireitos()).isEqualTo(DEFAULT_MISTURA_MATERIAIS_DIREITOS);
        assertThat(testCaracteristicaArmazenamento.getMisturaGrupoMateriais()).isEqualTo(DEFAULT_MISTURA_GRUPO_MATERIAIS);

        // Validate the CaracteristicaArmazenamento in Elasticsearch
        verify(mockCaracteristicaArmazenamentoSearchRepository, times(1)).save(testCaracteristicaArmazenamento);
    }

    @Test
    @Transactional
    public void createCaracteristicaArmazenamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caracteristicaArmazenamentoRepository.findAll().size();

        // Create the CaracteristicaArmazenamento with an existing ID
        caracteristicaArmazenamento.setId(1L);
        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO = caracteristicaArmazenamentoMapper.toDto(caracteristicaArmazenamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristicaArmazenamentoMockMvc.perform(post("/api/caracteristica-armazenamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaArmazenamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CaracteristicaArmazenamento in the database
        List<CaracteristicaArmazenamento> caracteristicaArmazenamentoList = caracteristicaArmazenamentoRepository.findAll();
        assertThat(caracteristicaArmazenamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CaracteristicaArmazenamento in Elasticsearch
        verify(mockCaracteristicaArmazenamentoSearchRepository, times(0)).save(caracteristicaArmazenamento);
    }


    @Test
    @Transactional
    public void checkMisturaMateriaisDireitosIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristicaArmazenamentoRepository.findAll().size();
        // set the field null
        caracteristicaArmazenamento.setMisturaMateriaisDireitos(null);

        // Create the CaracteristicaArmazenamento, which fails.
        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO = caracteristicaArmazenamentoMapper.toDto(caracteristicaArmazenamento);

        restCaracteristicaArmazenamentoMockMvc.perform(post("/api/caracteristica-armazenamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaArmazenamentoDTO)))
            .andExpect(status().isBadRequest());

        List<CaracteristicaArmazenamento> caracteristicaArmazenamentoList = caracteristicaArmazenamentoRepository.findAll();
        assertThat(caracteristicaArmazenamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMisturaGrupoMateriaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristicaArmazenamentoRepository.findAll().size();
        // set the field null
        caracteristicaArmazenamento.setMisturaGrupoMateriais(null);

        // Create the CaracteristicaArmazenamento, which fails.
        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO = caracteristicaArmazenamentoMapper.toDto(caracteristicaArmazenamento);

        restCaracteristicaArmazenamentoMockMvc.perform(post("/api/caracteristica-armazenamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaArmazenamentoDTO)))
            .andExpect(status().isBadRequest());

        List<CaracteristicaArmazenamento> caracteristicaArmazenamentoList = caracteristicaArmazenamentoRepository.findAll();
        assertThat(caracteristicaArmazenamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCaracteristicaArmazenamentos() throws Exception {
        // Initialize the database
        caracteristicaArmazenamentoRepository.saveAndFlush(caracteristicaArmazenamento);

        // Get all the caracteristicaArmazenamentoList
        restCaracteristicaArmazenamentoMockMvc.perform(get("/api/caracteristica-armazenamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristicaArmazenamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].misturaMateriaisDireitos").value(hasItem(DEFAULT_MISTURA_MATERIAIS_DIREITOS.booleanValue())))
            .andExpect(jsonPath("$.[*].misturaGrupoMateriais").value(hasItem(DEFAULT_MISTURA_GRUPO_MATERIAIS.booleanValue())));
    }

    @Test
    @Transactional
    public void getCaracteristicaArmazenamento() throws Exception {
        // Initialize the database
        caracteristicaArmazenamentoRepository.saveAndFlush(caracteristicaArmazenamento);

        // Get the caracteristicaArmazenamento
        restCaracteristicaArmazenamentoMockMvc.perform(get("/api/caracteristica-armazenamentos/{id}", caracteristicaArmazenamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(caracteristicaArmazenamento.getId().intValue()))
            .andExpect(jsonPath("$.misturaMateriaisDireitos").value(DEFAULT_MISTURA_MATERIAIS_DIREITOS.booleanValue()))
            .andExpect(jsonPath("$.misturaGrupoMateriais").value(DEFAULT_MISTURA_GRUPO_MATERIAIS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCaracteristicaArmazenamento() throws Exception {
        // Get the caracteristicaArmazenamento
        restCaracteristicaArmazenamentoMockMvc.perform(get("/api/caracteristica-armazenamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaracteristicaArmazenamento() throws Exception {
        // Initialize the database
        caracteristicaArmazenamentoRepository.saveAndFlush(caracteristicaArmazenamento);

        int databaseSizeBeforeUpdate = caracteristicaArmazenamentoRepository.findAll().size();

        // Update the caracteristicaArmazenamento
        CaracteristicaArmazenamento updatedCaracteristicaArmazenamento = caracteristicaArmazenamentoRepository.findById(caracteristicaArmazenamento.getId()).get();
        // Disconnect from session so that the updates on updatedCaracteristicaArmazenamento are not directly saved in db
        em.detach(updatedCaracteristicaArmazenamento);

        updatedCaracteristicaArmazenamento.setMisturaMateriaisDireitos(UPDATED_MISTURA_MATERIAIS_DIREITOS);
        updatedCaracteristicaArmazenamento.setMisturaGrupoMateriais(UPDATED_MISTURA_GRUPO_MATERIAIS);

        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO = caracteristicaArmazenamentoMapper.toDto(updatedCaracteristicaArmazenamento);

        restCaracteristicaArmazenamentoMockMvc.perform(put("/api/caracteristica-armazenamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaArmazenamentoDTO)))
            .andExpect(status().isOk());

        // Validate the CaracteristicaArmazenamento in the database
        List<CaracteristicaArmazenamento> caracteristicaArmazenamentoList = caracteristicaArmazenamentoRepository.findAll();
        assertThat(caracteristicaArmazenamentoList).hasSize(databaseSizeBeforeUpdate);
        CaracteristicaArmazenamento testCaracteristicaArmazenamento = caracteristicaArmazenamentoList.get(caracteristicaArmazenamentoList.size() - 1);
        assertThat(testCaracteristicaArmazenamento.getMisturaMateriaisDireitos()).isEqualTo(UPDATED_MISTURA_MATERIAIS_DIREITOS);
        assertThat(testCaracteristicaArmazenamento.getMisturaGrupoMateriais()).isEqualTo(UPDATED_MISTURA_GRUPO_MATERIAIS);

        // Validate the CaracteristicaArmazenamento in Elasticsearch
        verify(mockCaracteristicaArmazenamentoSearchRepository, times(1)).save(testCaracteristicaArmazenamento);
    }

    @Test
    @Transactional
    public void updateNonExistingCaracteristicaArmazenamento() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicaArmazenamentoRepository.findAll().size();

        // Create the CaracteristicaArmazenamento
        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO = caracteristicaArmazenamentoMapper.toDto(caracteristicaArmazenamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristicaArmazenamentoMockMvc.perform(put("/api/caracteristica-armazenamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaArmazenamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CaracteristicaArmazenamento in the database
        List<CaracteristicaArmazenamento> caracteristicaArmazenamentoList = caracteristicaArmazenamentoRepository.findAll();
        assertThat(caracteristicaArmazenamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CaracteristicaArmazenamento in Elasticsearch
        verify(mockCaracteristicaArmazenamentoSearchRepository, times(0)).save(caracteristicaArmazenamento);
    }

    @Test
    @Transactional
    public void deleteCaracteristicaArmazenamento() throws Exception {
        // Initialize the database
        caracteristicaArmazenamentoRepository.saveAndFlush(caracteristicaArmazenamento);

        int databaseSizeBeforeDelete = caracteristicaArmazenamentoRepository.findAll().size();

        // Delete the caracteristicaArmazenamento
        restCaracteristicaArmazenamentoMockMvc.perform(delete("/api/caracteristica-armazenamentos/{id}", caracteristicaArmazenamento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaracteristicaArmazenamento> caracteristicaArmazenamentoList = caracteristicaArmazenamentoRepository.findAll();
        assertThat(caracteristicaArmazenamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CaracteristicaArmazenamento in Elasticsearch
        verify(mockCaracteristicaArmazenamentoSearchRepository, times(1)).deleteById(caracteristicaArmazenamento.getId());
    }

    @Test
    @Transactional
    public void searchCaracteristicaArmazenamento() throws Exception {
        // Initialize the database
        caracteristicaArmazenamentoRepository.saveAndFlush(caracteristicaArmazenamento);
        when(mockCaracteristicaArmazenamentoSearchRepository.search(queryStringQuery("id:" + caracteristicaArmazenamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(caracteristicaArmazenamento), PageRequest.of(0, 1), 1));
        // Search the caracteristicaArmazenamento
        restCaracteristicaArmazenamentoMockMvc.perform(get("/api/_search/caracteristica-armazenamentos?query=id:" + caracteristicaArmazenamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristicaArmazenamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].misturaMateriaisDireitos").value(hasItem(DEFAULT_MISTURA_MATERIAIS_DIREITOS.booleanValue())))
            .andExpect(jsonPath("$.[*].misturaGrupoMateriais").value(hasItem(DEFAULT_MISTURA_GRUPO_MATERIAIS.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaracteristicaArmazenamento.class);
        CaracteristicaArmazenamento caracteristicaArmazenamento1 = new CaracteristicaArmazenamento();
        caracteristicaArmazenamento1.setId(1L);
        CaracteristicaArmazenamento caracteristicaArmazenamento2 = new CaracteristicaArmazenamento();
        caracteristicaArmazenamento2.setId(caracteristicaArmazenamento1.getId());
        assertThat(caracteristicaArmazenamento1).isEqualTo(caracteristicaArmazenamento2);
        caracteristicaArmazenamento2.setId(2L);
        assertThat(caracteristicaArmazenamento1).isNotEqualTo(caracteristicaArmazenamento2);
        caracteristicaArmazenamento1.setId(null);
        assertThat(caracteristicaArmazenamento1).isNotEqualTo(caracteristicaArmazenamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaracteristicaArmazenamentoDTO.class);
        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO1 = new CaracteristicaArmazenamentoDTO();
        caracteristicaArmazenamentoDTO1.setId(1L);
        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO2 = new CaracteristicaArmazenamentoDTO();
        assertThat(caracteristicaArmazenamentoDTO1).isNotEqualTo(caracteristicaArmazenamentoDTO2);
        caracteristicaArmazenamentoDTO2.setId(caracteristicaArmazenamentoDTO1.getId());
        assertThat(caracteristicaArmazenamentoDTO1).isEqualTo(caracteristicaArmazenamentoDTO2);
        caracteristicaArmazenamentoDTO2.setId(2L);
        assertThat(caracteristicaArmazenamentoDTO1).isNotEqualTo(caracteristicaArmazenamentoDTO2);
        caracteristicaArmazenamentoDTO1.setId(null);
        assertThat(caracteristicaArmazenamentoDTO1).isNotEqualTo(caracteristicaArmazenamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(caracteristicaArmazenamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(caracteristicaArmazenamentoMapper.fromId(null)).isNull();
    }
}
