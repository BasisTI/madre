package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.MadresuprimentosApp;
import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import br.com.basis.suprimentos.domain.Lote;
import br.com.basis.suprimentos.repository.LoteRepository;
import br.com.basis.suprimentos.repository.search.LoteSearchRepository;
import br.com.basis.suprimentos.service.LoteService;
import br.com.basis.suprimentos.service.dto.LoteDTO;
import br.com.basis.suprimentos.service.mapper.LoteMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link LoteResource} REST controller.
 */
@SpringBootTest(classes = MadresuprimentosApp.class)
public class LoteResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTIDADE_DISPONIVEL = 0L;
    private static final Long UPDATED_QUANTIDADE_DISPONIVEL = 1L;

    private static final Long DEFAULT_QUANTIDADE_BLOQUEADA = 0L;
    private static final Long UPDATED_QUANTIDADE_BLOQUEADA = 1L;

    private static final Long DEFAULT_QUANTIDADE_PROBLEMA = 0L;
    private static final Long UPDATED_QUANTIDADE_PROBLEMA = 1L;

    private static final LocalDate DEFAULT_DATA_VALIDADE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VALIDADE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private LoteMapper loteMapper;

    @Autowired
    private LoteService loteService;

    /**
     * This repository is mocked in the br.com.basis.suprimentos.repository.search test package.
     *
     * @see br.com.basis.suprimentos.repository.search.LoteSearchRepositoryMockConfiguration
     */
    @Autowired
    private LoteSearchRepository mockLoteSearchRepository;

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

    private MockMvc restLoteMockMvc;

    private Lote lote;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lote createEntity(EntityManager em) {
        Lote lote = new Lote()
                .descricao(DEFAULT_DESCRICAO)
                .serie(DEFAULT_SERIE)
                .quantidadeDisponivel(DEFAULT_QUANTIDADE_DISPONIVEL)
                .quantidadeBloqueada(DEFAULT_QUANTIDADE_BLOQUEADA)
                .quantidadeProblema(DEFAULT_QUANTIDADE_PROBLEMA)
                .dataValidade(DEFAULT_DATA_VALIDADE);
        // Add required entity
        EstoqueAlmoxarifado estoqueAlmoxarifado;
        if (TestUtil.findAll(em, EstoqueAlmoxarifado.class).isEmpty()) {
            estoqueAlmoxarifado = EstoqueAlmoxarifadoResourceIT.createEntity(em);
            em.persist(estoqueAlmoxarifado);
            em.flush();
        } else {
            estoqueAlmoxarifado = TestUtil.findAll(em, EstoqueAlmoxarifado.class).get(0);
        }
        lote.setEstoque(estoqueAlmoxarifado);
        return lote;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lote createUpdatedEntity(EntityManager em) {
        Lote lote = new Lote()
                .descricao(UPDATED_DESCRICAO)
                .serie(UPDATED_SERIE)
                .quantidadeDisponivel(UPDATED_QUANTIDADE_DISPONIVEL)
                .quantidadeBloqueada(UPDATED_QUANTIDADE_BLOQUEADA)
                .quantidadeProblema(UPDATED_QUANTIDADE_PROBLEMA)
                .dataValidade(UPDATED_DATA_VALIDADE);
        // Add required entity
        EstoqueAlmoxarifado estoqueAlmoxarifado;
        if (TestUtil.findAll(em, EstoqueAlmoxarifado.class).isEmpty()) {
            estoqueAlmoxarifado = EstoqueAlmoxarifadoResourceIT.createUpdatedEntity(em);
            em.persist(estoqueAlmoxarifado);
            em.flush();
        } else {
            estoqueAlmoxarifado = TestUtil.findAll(em, EstoqueAlmoxarifado.class).get(0);
        }
        lote.setEstoque(estoqueAlmoxarifado);
        return lote;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoteResource loteResource = new LoteResource(loteService);
        this.restLoteMockMvc = MockMvcBuilders.standaloneSetup(loteResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        lote = createEntity(em);
    }

    @Test
    @Transactional
    public void createLote() throws Exception {
        int databaseSizeBeforeCreate = loteRepository.findAll().size();

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);
        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
                .andExpect(status().isCreated());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeCreate + 1);
        Lote testLote = loteList.get(loteList.size() - 1);
        assertThat(testLote.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testLote.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testLote.getQuantidadeDisponivel()).isEqualTo(DEFAULT_QUANTIDADE_DISPONIVEL);
        assertThat(testLote.getQuantidadeBloqueada()).isEqualTo(DEFAULT_QUANTIDADE_BLOQUEADA);
        assertThat(testLote.getQuantidadeProblema()).isEqualTo(DEFAULT_QUANTIDADE_PROBLEMA);
        assertThat(testLote.getDataValidade()).isEqualTo(DEFAULT_DATA_VALIDADE);

        // Validate the Lote in Elasticsearch
        verify(mockLoteSearchRepository, times(1)).save(testLote);
    }

    @Test
    @Transactional
    public void createLoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loteRepository.findAll().size();

        // Create the Lote with an existing ID
        lote.setId(1L);
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lote in Elasticsearch
        verify(mockLoteSearchRepository, times(0)).save(lote);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setDescricao(null);

        // Create the Lote, which fails.
        LoteDTO loteDTO = loteMapper.toDto(lote);

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
                .andExpect(status().isBadRequest());

        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataValidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setDataValidade(null);

        // Create the Lote, which fails.
        LoteDTO loteDTO = loteMapper.toDto(lote);

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
                .andExpect(status().isBadRequest());

        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLotes() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        // Get all the loteList
        restLoteMockMvc.perform(get("/api/lotes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
                .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
                .andExpect(jsonPath("$.[*].quantidadeDisponivel").value(hasItem(DEFAULT_QUANTIDADE_DISPONIVEL.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeBloqueada").value(hasItem(DEFAULT_QUANTIDADE_BLOQUEADA.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeProblema").value(hasItem(DEFAULT_QUANTIDADE_PROBLEMA.intValue())))
                .andExpect(jsonPath("$.[*].dataValidade").value(hasItem(DEFAULT_DATA_VALIDADE.toString())));
    }

    @Test
    @Transactional
    public void getLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        // Get the lote
        restLoteMockMvc.perform(get("/api/lotes/{id}", lote.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(lote.getId().intValue()))
                .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
                .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE))
                .andExpect(jsonPath("$.quantidadeDisponivel").value(DEFAULT_QUANTIDADE_DISPONIVEL.intValue()))
                .andExpect(jsonPath("$.quantidadeBloqueada").value(DEFAULT_QUANTIDADE_BLOQUEADA.intValue()))
                .andExpect(jsonPath("$.quantidadeProblema").value(DEFAULT_QUANTIDADE_PROBLEMA.intValue()))
                .andExpect(jsonPath("$.dataValidade").value(DEFAULT_DATA_VALIDADE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLote() throws Exception {
        // Get the lote
        restLoteMockMvc.perform(get("/api/lotes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        int databaseSizeBeforeUpdate = loteRepository.findAll().size();

        // Update the lote
        Lote updatedLote = loteRepository.findById(lote.getId()).get();
        // Disconnect from session so that the updates on updatedLote are not directly saved in db
        em.detach(updatedLote);
        updatedLote
                .descricao(UPDATED_DESCRICAO)
                .serie(UPDATED_SERIE)
                .quantidadeDisponivel(UPDATED_QUANTIDADE_DISPONIVEL)
                .quantidadeBloqueada(UPDATED_QUANTIDADE_BLOQUEADA)
                .quantidadeProblema(UPDATED_QUANTIDADE_PROBLEMA)
                .dataValidade(UPDATED_DATA_VALIDADE);
        LoteDTO loteDTO = loteMapper.toDto(updatedLote);

        restLoteMockMvc.perform(put("/api/lotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
                .andExpect(status().isOk());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
        Lote testLote = loteList.get(loteList.size() - 1);
        assertThat(testLote.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLote.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testLote.getQuantidadeDisponivel()).isEqualTo(UPDATED_QUANTIDADE_DISPONIVEL);
        assertThat(testLote.getQuantidadeBloqueada()).isEqualTo(UPDATED_QUANTIDADE_BLOQUEADA);
        assertThat(testLote.getQuantidadeProblema()).isEqualTo(UPDATED_QUANTIDADE_PROBLEMA);
        assertThat(testLote.getDataValidade()).isEqualTo(UPDATED_DATA_VALIDADE);

        // Validate the Lote in Elasticsearch
        verify(mockLoteSearchRepository, times(1)).save(testLote);
    }

    @Test
    @Transactional
    public void updateNonExistingLote() throws Exception {
        int databaseSizeBeforeUpdate = loteRepository.findAll().size();

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoteMockMvc.perform(put("/api/lotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lote in Elasticsearch
        verify(mockLoteSearchRepository, times(0)).save(lote);
    }

    @Test
    @Transactional
    public void deleteLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        int databaseSizeBeforeDelete = loteRepository.findAll().size();

        // Delete the lote
        restLoteMockMvc.perform(delete("/api/lotes/{id}", lote.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lote in Elasticsearch
        verify(mockLoteSearchRepository, times(1)).deleteById(lote.getId());
    }

    @Test
    @Transactional
    public void searchLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);
        when(mockLoteSearchRepository.search(queryStringQuery("id:" + lote.getId()), PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(Collections.singletonList(lote), PageRequest.of(0, 1), 1));
        // Search the lote
        restLoteMockMvc.perform(get("/api/_search/lotes?query=id:" + lote.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
                .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
                .andExpect(jsonPath("$.[*].quantidadeDisponivel").value(hasItem(DEFAULT_QUANTIDADE_DISPONIVEL.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeBloqueada").value(hasItem(DEFAULT_QUANTIDADE_BLOQUEADA.intValue())))
                .andExpect(jsonPath("$.[*].quantidadeProblema").value(hasItem(DEFAULT_QUANTIDADE_PROBLEMA.intValue())))
                .andExpect(jsonPath("$.[*].dataValidade").value(hasItem(DEFAULT_DATA_VALIDADE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lote.class);
        Lote lote1 = new Lote();
        lote1.setId(1L);
        Lote lote2 = new Lote();
        lote2.setId(lote1.getId());
        assertThat(lote1).isEqualTo(lote2);
        lote2.setId(2L);
        assertThat(lote1).isNotEqualTo(lote2);
        lote1.setId(null);
        assertThat(lote1).isNotEqualTo(lote2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoteDTO.class);
        LoteDTO loteDTO1 = new LoteDTO();
        loteDTO1.setId(1L);
        LoteDTO loteDTO2 = new LoteDTO();
        assertThat(loteDTO1).isNotEqualTo(loteDTO2);
        loteDTO2.setId(loteDTO1.getId());
        assertThat(loteDTO1).isEqualTo(loteDTO2);
        loteDTO2.setId(2L);
        assertThat(loteDTO1).isNotEqualTo(loteDTO2);
        loteDTO1.setId(null);
        assertThat(loteDTO1).isNotEqualTo(loteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loteMapper.fromId(null)).isNull();
    }
}
