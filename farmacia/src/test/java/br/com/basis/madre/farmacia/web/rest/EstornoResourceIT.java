package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.FarmaciaApp;
import br.com.basis.madre.farmacia.domain.Estorno;
import br.com.basis.madre.farmacia.repository.EstornoRepository;
import br.com.basis.madre.farmacia.repository.search.EstornoSearchRepository;
import br.com.basis.madre.farmacia.service.EstornoService;
import br.com.basis.madre.farmacia.service.dto.EstornoDTO;
import br.com.basis.madre.farmacia.service.mapper.EstornoMapper;
import br.com.basis.madre.farmacia.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static br.com.basis.madre.farmacia.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EstornoResource} REST controller.
 */
@SpringBootTest(classes = FarmaciaApp.class)
public class EstornoResourceIT {

    private static final Boolean DEFAULT_ESTORNADO = false;
    private static final Boolean UPDATED_ESTORNADO = true;

    private static final Long DEFAULT_USUARIO_QUE_ESTORNOU = 1L;
    private static final Long UPDATED_USUARIO_QUE_ESTORNOU = 2L;

    private static final LocalDate DEFAULT_DATA_DE_ESTORNO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_ESTORNO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EstornoRepository estornoRepository;

    @Autowired
    private EstornoMapper estornoMapper;

    @Autowired
    private EstornoService estornoService;

    /**
     * This repository is mocked in the br.com.basis.madre.farmacia.repository.search test package.
     *
     * @see br.com.basis.madre.farmacia.repository.search.EstornoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EstornoSearchRepository mockEstornoSearchRepository;

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

    private MockMvc restEstornoMockMvc;

    private Estorno estorno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstornoResource estornoResource = new EstornoResource(estornoService);
        this.restEstornoMockMvc = MockMvcBuilders.standaloneSetup(estornoResource)
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
    public static Estorno createEntity(EntityManager em) {
        Estorno estorno = new Estorno()
            .estornado(DEFAULT_ESTORNADO)
            .usuarioQueEstornou(DEFAULT_USUARIO_QUE_ESTORNOU)
            .dataDeEstorno(DEFAULT_DATA_DE_ESTORNO);
        return estorno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estorno createUpdatedEntity(EntityManager em) {
        Estorno estorno = new Estorno()
            .estornado(UPDATED_ESTORNADO)
            .usuarioQueEstornou(UPDATED_USUARIO_QUE_ESTORNOU)
            .dataDeEstorno(UPDATED_DATA_DE_ESTORNO);
        return estorno;
    }

    @BeforeEach
    public void initTest() {
        estorno = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstorno() throws Exception {
        int databaseSizeBeforeCreate = estornoRepository.findAll().size();

        // Create the Estorno
        EstornoDTO estornoDTO = estornoMapper.toDto(estorno);
        restEstornoMockMvc.perform(post("/api/estornos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estornoDTO)))
            .andExpect(status().isCreated());

        // Validate the Estorno in the database
        List<Estorno> estornoList = estornoRepository.findAll();
        assertThat(estornoList).hasSize(databaseSizeBeforeCreate + 1);
        Estorno testEstorno = estornoList.get(estornoList.size() - 1);
        assertThat(testEstorno.isEstornado()).isEqualTo(DEFAULT_ESTORNADO);
        assertThat(testEstorno.getUsuarioQueEstornou()).isEqualTo(DEFAULT_USUARIO_QUE_ESTORNOU);
        assertThat(testEstorno.getDataDeEstorno()).isEqualTo(DEFAULT_DATA_DE_ESTORNO);

        // Validate the Estorno in Elasticsearch
        verify(mockEstornoSearchRepository, times(1)).save(testEstorno);
    }

    @Test
    @Transactional
    public void createEstornoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estornoRepository.findAll().size();

        // Create the Estorno with an existing ID
        estorno.setId(1L);
        EstornoDTO estornoDTO = estornoMapper.toDto(estorno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstornoMockMvc.perform(post("/api/estornos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estornoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estorno in the database
        List<Estorno> estornoList = estornoRepository.findAll();
        assertThat(estornoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Estorno in Elasticsearch
        verify(mockEstornoSearchRepository, times(0)).save(estorno);
    }


    @Test
    @Transactional
    public void getAllEstornos() throws Exception {
        // Initialize the database
        estornoRepository.saveAndFlush(estorno);

        // Get all the estornoList
        restEstornoMockMvc.perform(get("/api/estornos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estorno.getId().intValue())))
            .andExpect(jsonPath("$.[*].estornado").value(hasItem(DEFAULT_ESTORNADO.booleanValue())))
            .andExpect(jsonPath("$.[*].usuarioQueEstornou").value(hasItem(DEFAULT_USUARIO_QUE_ESTORNOU.intValue())))
            .andExpect(jsonPath("$.[*].dataDeEstorno").value(hasItem(DEFAULT_DATA_DE_ESTORNO.toString())));
    }
    
    @Test
    @Transactional
    public void getEstorno() throws Exception {
        // Initialize the database
        estornoRepository.saveAndFlush(estorno);

        // Get the estorno
        restEstornoMockMvc.perform(get("/api/estornos/{id}", estorno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estorno.getId().intValue()))
            .andExpect(jsonPath("$.estornado").value(DEFAULT_ESTORNADO.booleanValue()))
            .andExpect(jsonPath("$.usuarioQueEstornou").value(DEFAULT_USUARIO_QUE_ESTORNOU.intValue()))
            .andExpect(jsonPath("$.dataDeEstorno").value(DEFAULT_DATA_DE_ESTORNO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstorno() throws Exception {
        // Get the estorno
        restEstornoMockMvc.perform(get("/api/estornos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstorno() throws Exception {
        // Initialize the database
        estornoRepository.saveAndFlush(estorno);

        int databaseSizeBeforeUpdate = estornoRepository.findAll().size();

        // Update the estorno
        Estorno updatedEstorno = estornoRepository.findById(estorno.getId()).get();
        // Disconnect from session so that the updates on updatedEstorno are not directly saved in db
        em.detach(updatedEstorno);
        updatedEstorno
            .estornado(UPDATED_ESTORNADO)
            .usuarioQueEstornou(UPDATED_USUARIO_QUE_ESTORNOU)
            .dataDeEstorno(UPDATED_DATA_DE_ESTORNO);
        EstornoDTO estornoDTO = estornoMapper.toDto(updatedEstorno);

        restEstornoMockMvc.perform(put("/api/estornos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estornoDTO)))
            .andExpect(status().isOk());

        // Validate the Estorno in the database
        List<Estorno> estornoList = estornoRepository.findAll();
        assertThat(estornoList).hasSize(databaseSizeBeforeUpdate);
        Estorno testEstorno = estornoList.get(estornoList.size() - 1);
        assertThat(testEstorno.isEstornado()).isEqualTo(UPDATED_ESTORNADO);
        assertThat(testEstorno.getUsuarioQueEstornou()).isEqualTo(UPDATED_USUARIO_QUE_ESTORNOU);
        assertThat(testEstorno.getDataDeEstorno()).isEqualTo(UPDATED_DATA_DE_ESTORNO);

        // Validate the Estorno in Elasticsearch
        verify(mockEstornoSearchRepository, times(1)).save(testEstorno);
    }

    @Test
    @Transactional
    public void updateNonExistingEstorno() throws Exception {
        int databaseSizeBeforeUpdate = estornoRepository.findAll().size();

        // Create the Estorno
        EstornoDTO estornoDTO = estornoMapper.toDto(estorno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstornoMockMvc.perform(put("/api/estornos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estornoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estorno in the database
        List<Estorno> estornoList = estornoRepository.findAll();
        assertThat(estornoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Estorno in Elasticsearch
        verify(mockEstornoSearchRepository, times(0)).save(estorno);
    }

    @Test
    @Transactional
    public void deleteEstorno() throws Exception {
        // Initialize the database
        estornoRepository.saveAndFlush(estorno);

        int databaseSizeBeforeDelete = estornoRepository.findAll().size();

        // Delete the estorno
        restEstornoMockMvc.perform(delete("/api/estornos/{id}", estorno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estorno> estornoList = estornoRepository.findAll();
        assertThat(estornoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Estorno in Elasticsearch
        verify(mockEstornoSearchRepository, times(1)).deleteById(estorno.getId());
    }

    @Test
    @Transactional
    public void searchEstorno() throws Exception {
        // Initialize the database
        estornoRepository.saveAndFlush(estorno);
        when(mockEstornoSearchRepository.search(queryStringQuery("id:" + estorno.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(estorno), PageRequest.of(0, 1), 1));
        // Search the estorno
        restEstornoMockMvc.perform(get("/api/_search/estornos?query=id:" + estorno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estorno.getId().intValue())))
            .andExpect(jsonPath("$.[*].estornado").value(hasItem(DEFAULT_ESTORNADO.booleanValue())))
            .andExpect(jsonPath("$.[*].usuarioQueEstornou").value(hasItem(DEFAULT_USUARIO_QUE_ESTORNOU.intValue())))
            .andExpect(jsonPath("$.[*].dataDeEstorno").value(hasItem(DEFAULT_DATA_DE_ESTORNO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estorno.class);
        Estorno estorno1 = new Estorno();
        estorno1.setId(1L);
        Estorno estorno2 = new Estorno();
        estorno2.setId(estorno1.getId());
        assertThat(estorno1).isEqualTo(estorno2);
        estorno2.setId(2L);
        assertThat(estorno1).isNotEqualTo(estorno2);
        estorno1.setId(null);
        assertThat(estorno1).isNotEqualTo(estorno2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstornoDTO.class);
        EstornoDTO estornoDTO1 = new EstornoDTO();
        estornoDTO1.setId(1L);
        EstornoDTO estornoDTO2 = new EstornoDTO();
        assertThat(estornoDTO1).isNotEqualTo(estornoDTO2);
        estornoDTO2.setId(estornoDTO1.getId());
        assertThat(estornoDTO1).isEqualTo(estornoDTO2);
        estornoDTO2.setId(2L);
        assertThat(estornoDTO1).isNotEqualTo(estornoDTO2);
        estornoDTO1.setId(null);
        assertThat(estornoDTO1).isNotEqualTo(estornoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estornoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estornoMapper.fromId(null)).isNull();
    }
}
