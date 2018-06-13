package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import br.com.basis.madre.cadastros.domain.Anexo;
import br.com.basis.madre.cadastros.repository.AnexoRepository;
import br.com.basis.madre.cadastros.service.AnexoService;
import br.com.basis.madre.cadastros.repository.search.AnexoSearchRepository;
import br.com.basis.madre.cadastros.service.dto.AnexoDTO;
import br.com.basis.madre.cadastros.service.mapper.AnexoMapper;
import br.com.basis.madre.cadastros.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.basis.madre.cadastros.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnexoResource REST controller.
 *
 * @see AnexoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class AnexoResourceIntTest {

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOME_ARQUIVO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_ARQUIVO = "BBBBBBBBBB";

    private static final Float DEFAULT_TAMANHO_ARQUIVO = 1F;
    private static final Float UPDATED_TAMANHO_ARQUIVO = 2F;

    private static final byte[] DEFAULT_ARQUIVO_ANEXO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARQUIVO_ANEXO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ARQUIVO_ANEXO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARQUIVO_ANEXO_CONTENT_TYPE = "image/png";

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private AnexoMapper anexoMapper;

    @Autowired
    private AnexoService anexoService;

    @Autowired
    private AnexoSearchRepository anexoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnexoMockMvc;

    private Anexo anexo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnexoResource anexoResource = new AnexoResource(anexoService);
        this.restAnexoMockMvc = MockMvcBuilders.standaloneSetup(anexoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anexo createEntity(EntityManager em) {
        Anexo anexo = new Anexo()
            .dataCriacao(DEFAULT_DATA_CRIACAO)
            .nomeArquivo(DEFAULT_NOME_ARQUIVO)
            .tamanhoArquivo(DEFAULT_TAMANHO_ARQUIVO)
            .arquivoAnexo(DEFAULT_ARQUIVO_ANEXO)
            .arquivoAnexoContentType(DEFAULT_ARQUIVO_ANEXO_CONTENT_TYPE);
        return anexo;
    }

    @Before
    public void initTest() {
        anexoSearchRepository.deleteAll();
        anexo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnexo() throws Exception {
        int databaseSizeBeforeCreate = anexoRepository.findAll().size();

        // Create the Anexo
        AnexoDTO anexoDTO = anexoMapper.toDto(anexo);
        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoDTO)))
            .andExpect(status().isCreated());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeCreate + 1);
        Anexo testAnexo = anexoList.get(anexoList.size() - 1);
        assertThat(testAnexo.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
        assertThat(testAnexo.getNomeArquivo()).isEqualTo(DEFAULT_NOME_ARQUIVO);
        assertThat(testAnexo.getTamanhoArquivo()).isEqualTo(DEFAULT_TAMANHO_ARQUIVO);
        assertThat(testAnexo.getArquivoAnexo()).isEqualTo(DEFAULT_ARQUIVO_ANEXO);
        assertThat(testAnexo.getArquivoAnexoContentType()).isEqualTo(DEFAULT_ARQUIVO_ANEXO_CONTENT_TYPE);

        // Validate the Anexo in Elasticsearch
        Anexo anexoEs = anexoSearchRepository.findOne(testAnexo.getId());
        assertThat(anexoEs).isEqualToIgnoringGivenFields(testAnexo);
    }

    @Test
    @Transactional
    public void createAnexoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anexoRepository.findAll().size();

        // Create the Anexo with an existing ID
        anexo.setId(1L);
        AnexoDTO anexoDTO = anexoMapper.toDto(anexo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoMockMvc.perform(post("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnexos() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get all the anexoList
        restAnexoMockMvc.perform(get("/api/anexos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].nomeArquivo").value(hasItem(DEFAULT_NOME_ARQUIVO.toString())))
            .andExpect(jsonPath("$.[*].tamanhoArquivo").value(hasItem(DEFAULT_TAMANHO_ARQUIVO.doubleValue())))
            .andExpect(jsonPath("$.[*].arquivoAnexoContentType").value(hasItem(DEFAULT_ARQUIVO_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].arquivoAnexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARQUIVO_ANEXO))));
    }

    @Test
    @Transactional
    public void getAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);

        // Get the anexo
        restAnexoMockMvc.perform(get("/api/anexos/{id}", anexo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anexo.getId().intValue()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()))
            .andExpect(jsonPath("$.nomeArquivo").value(DEFAULT_NOME_ARQUIVO.toString()))
            .andExpect(jsonPath("$.tamanhoArquivo").value(DEFAULT_TAMANHO_ARQUIVO.doubleValue()))
            .andExpect(jsonPath("$.arquivoAnexoContentType").value(DEFAULT_ARQUIVO_ANEXO_CONTENT_TYPE))
            .andExpect(jsonPath("$.arquivoAnexo").value(Base64Utils.encodeToString(DEFAULT_ARQUIVO_ANEXO)));
    }

    @Test
    @Transactional
    public void getNonExistingAnexo() throws Exception {
        // Get the anexo
        restAnexoMockMvc.perform(get("/api/anexos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        anexoSearchRepository.save(anexo);
        int databaseSizeBeforeUpdate = anexoRepository.findAll().size();

        // Update the anexo
        Anexo updatedAnexo = anexoRepository.findOne(anexo.getId());
        // Disconnect from session so that the updates on updatedAnexo are not directly saved in db
        em.detach(updatedAnexo);
        updatedAnexo
            .dataCriacao(UPDATED_DATA_CRIACAO)
            .nomeArquivo(UPDATED_NOME_ARQUIVO)
            .tamanhoArquivo(UPDATED_TAMANHO_ARQUIVO)
            .arquivoAnexo(UPDATED_ARQUIVO_ANEXO)
            .arquivoAnexoContentType(UPDATED_ARQUIVO_ANEXO_CONTENT_TYPE);
        AnexoDTO anexoDTO = anexoMapper.toDto(updatedAnexo);

        restAnexoMockMvc.perform(put("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoDTO)))
            .andExpect(status().isOk());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeUpdate);
        Anexo testAnexo = anexoList.get(anexoList.size() - 1);
        assertThat(testAnexo.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
        assertThat(testAnexo.getNomeArquivo()).isEqualTo(UPDATED_NOME_ARQUIVO);
        assertThat(testAnexo.getTamanhoArquivo()).isEqualTo(UPDATED_TAMANHO_ARQUIVO);
        assertThat(testAnexo.getArquivoAnexo()).isEqualTo(UPDATED_ARQUIVO_ANEXO);
        assertThat(testAnexo.getArquivoAnexoContentType()).isEqualTo(UPDATED_ARQUIVO_ANEXO_CONTENT_TYPE);

        // Validate the Anexo in Elasticsearch
        Anexo anexoEs = anexoSearchRepository.findOne(testAnexo.getId());
        assertThat(anexoEs).isEqualToIgnoringGivenFields(testAnexo);
    }

    @Test
    @Transactional
    public void updateNonExistingAnexo() throws Exception {
        int databaseSizeBeforeUpdate = anexoRepository.findAll().size();

        // Create the Anexo
        AnexoDTO anexoDTO = anexoMapper.toDto(anexo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnexoMockMvc.perform(put("/api/anexos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexoDTO)))
            .andExpect(status().isCreated());

        // Validate the Anexo in the database
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        anexoSearchRepository.save(anexo);
        int databaseSizeBeforeDelete = anexoRepository.findAll().size();

        // Get the anexo
        restAnexoMockMvc.perform(delete("/api/anexos/{id}", anexo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean anexoExistsInEs = anexoSearchRepository.exists(anexo.getId());
        assertThat(anexoExistsInEs).isFalse();

        // Validate the database is empty
        List<Anexo> anexoList = anexoRepository.findAll();
        assertThat(anexoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAnexo() throws Exception {
        // Initialize the database
        anexoRepository.saveAndFlush(anexo);
        anexoSearchRepository.save(anexo);

        // Search the anexo
        restAnexoMockMvc.perform(get("/api/_search/anexos?query=id:" + anexo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].nomeArquivo").value(hasItem(DEFAULT_NOME_ARQUIVO.toString())))
            .andExpect(jsonPath("$.[*].tamanhoArquivo").value(hasItem(DEFAULT_TAMANHO_ARQUIVO.doubleValue())))
            .andExpect(jsonPath("$.[*].arquivoAnexoContentType").value(hasItem(DEFAULT_ARQUIVO_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].arquivoAnexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARQUIVO_ANEXO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anexo.class);
        Anexo anexo1 = new Anexo();
        anexo1.setId(1L);
        Anexo anexo2 = new Anexo();
        anexo2.setId(anexo1.getId());
        assertThat(anexo1).isEqualTo(anexo2);
        anexo2.setId(2L);
        assertThat(anexo1).isNotEqualTo(anexo2);
        anexo1.setId(null);
        assertThat(anexo1).isNotEqualTo(anexo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoDTO.class);
        AnexoDTO anexoDTO1 = new AnexoDTO();
        anexoDTO1.setId(1L);
        AnexoDTO anexoDTO2 = new AnexoDTO();
        assertThat(anexoDTO1).isNotEqualTo(anexoDTO2);
        anexoDTO2.setId(anexoDTO1.getId());
        assertThat(anexoDTO1).isEqualTo(anexoDTO2);
        anexoDTO2.setId(2L);
        assertThat(anexoDTO1).isNotEqualTo(anexoDTO2);
        anexoDTO1.setId(null);
        assertThat(anexoDTO1).isNotEqualTo(anexoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(anexoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(anexoMapper.fromId(null)).isNull();
    }
}
