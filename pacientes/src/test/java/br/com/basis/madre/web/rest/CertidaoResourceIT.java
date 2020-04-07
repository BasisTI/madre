package br.com.basis.madre.web.rest;

import br.com.basis.madre.SnffaturaApp;
import br.com.basis.madre.domain.Certidao;
import br.com.basis.madre.repository.CertidaoRepository;
import br.com.basis.madre.repository.search.CertidaoSearchRepository;
import br.com.basis.madre.service.CertidaoService;
import br.com.basis.madre.service.dto.CertidaoDTO;
import br.com.basis.madre.service.mapper.CertidaoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.domain.enumeration.TipoDaCertidao;
/**
 * Integration tests for the {@link CertidaoResource} REST controller.
 */
@SpringBootTest(classes = SnffaturaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CertidaoResourceIT {

    private static final String DEFAULT_REGISTRO_DE_NASCIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRO_DE_NASCIMENTO = "BBBBBBBBBB";

    private static final TipoDaCertidao DEFAULT_TIPO_DA_CERTIDAO = TipoDaCertidao.NASCIMENTO;
    private static final TipoDaCertidao UPDATED_TIPO_DA_CERTIDAO = TipoDaCertidao.CASAMENTO;

    private static final String DEFAULT_NOME_DO_CARTORIO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_CARTORIO = "BBBBBBBBBB";

    private static final String DEFAULT_LIVRO = "AAAAAAAAAA";
    private static final String UPDATED_LIVRO = "BBBBBBBBBB";

    private static final String DEFAULT_FOLHAS = "AAAAAAAAAA";
    private static final String UPDATED_FOLHAS = "BBBBBBBBBB";

    private static final String DEFAULT_TERMO = "AAAAAAAAAA";
    private static final String UPDATED_TERMO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DE_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_EMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_DA_DECLARACAO_DE_NASCIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DA_DECLARACAO_DE_NASCIMENTO = "BBBBBBBBBB";

    @Autowired
    private CertidaoRepository certidaoRepository;

    @Autowired
    private CertidaoMapper certidaoMapper;

    @Autowired
    private CertidaoService certidaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.CertidaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CertidaoSearchRepository mockCertidaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertidaoMockMvc;

    private Certidao certidao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certidao createEntity(EntityManager em) {
        Certidao certidao = new Certidao()
            .registroDeNascimento(DEFAULT_REGISTRO_DE_NASCIMENTO)
            .tipoDaCertidao(DEFAULT_TIPO_DA_CERTIDAO)
            .nomeDoCartorio(DEFAULT_NOME_DO_CARTORIO)
            .livro(DEFAULT_LIVRO)
            .folhas(DEFAULT_FOLHAS)
            .termo(DEFAULT_TERMO)
            .dataDeEmissao(DEFAULT_DATA_DE_EMISSAO)
            .numeroDaDeclaracaoDeNascimento(DEFAULT_NUMERO_DA_DECLARACAO_DE_NASCIMENTO);
        return certidao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certidao createUpdatedEntity(EntityManager em) {
        Certidao certidao = new Certidao()
            .registroDeNascimento(UPDATED_REGISTRO_DE_NASCIMENTO)
            .tipoDaCertidao(UPDATED_TIPO_DA_CERTIDAO)
            .nomeDoCartorio(UPDATED_NOME_DO_CARTORIO)
            .livro(UPDATED_LIVRO)
            .folhas(UPDATED_FOLHAS)
            .termo(UPDATED_TERMO)
            .dataDeEmissao(UPDATED_DATA_DE_EMISSAO)
            .numeroDaDeclaracaoDeNascimento(UPDATED_NUMERO_DA_DECLARACAO_DE_NASCIMENTO);
        return certidao;
    }

    @BeforeEach
    public void initTest() {
        certidao = createEntity(em);
    }

    @Test
    @Transactional
    public void createCertidao() throws Exception {
        int databaseSizeBeforeCreate = certidaoRepository.findAll().size();

        // Create the Certidao
        CertidaoDTO certidaoDTO = certidaoMapper.toDto(certidao);
        restCertidaoMockMvc.perform(post("/api/certidaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certidaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Certidao in the database
        List<Certidao> certidaoList = certidaoRepository.findAll();
        assertThat(certidaoList).hasSize(databaseSizeBeforeCreate + 1);
        Certidao testCertidao = certidaoList.get(certidaoList.size() - 1);
        assertThat(testCertidao.getRegistroDeNascimento()).isEqualTo(DEFAULT_REGISTRO_DE_NASCIMENTO);
        assertThat(testCertidao.getTipoDaCertidao()).isEqualTo(DEFAULT_TIPO_DA_CERTIDAO);
        assertThat(testCertidao.getNomeDoCartorio()).isEqualTo(DEFAULT_NOME_DO_CARTORIO);
        assertThat(testCertidao.getLivro()).isEqualTo(DEFAULT_LIVRO);
        assertThat(testCertidao.getFolhas()).isEqualTo(DEFAULT_FOLHAS);
        assertThat(testCertidao.getTermo()).isEqualTo(DEFAULT_TERMO);
        assertThat(testCertidao.getDataDeEmissao()).isEqualTo(DEFAULT_DATA_DE_EMISSAO);
        assertThat(testCertidao.getNumeroDaDeclaracaoDeNascimento()).isEqualTo(DEFAULT_NUMERO_DA_DECLARACAO_DE_NASCIMENTO);

        // Validate the Certidao in Elasticsearch
        verify(mockCertidaoSearchRepository, times(1)).save(testCertidao);
    }

    @Test
    @Transactional
    public void createCertidaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = certidaoRepository.findAll().size();

        // Create the Certidao with an existing ID
        certidao.setId(1L);
        CertidaoDTO certidaoDTO = certidaoMapper.toDto(certidao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertidaoMockMvc.perform(post("/api/certidaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certidaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certidao in the database
        List<Certidao> certidaoList = certidaoRepository.findAll();
        assertThat(certidaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Certidao in Elasticsearch
        verify(mockCertidaoSearchRepository, times(0)).save(certidao);
    }


    @Test
    @Transactional
    public void getAllCertidaos() throws Exception {
        // Initialize the database
        certidaoRepository.saveAndFlush(certidao);

        // Get all the certidaoList
        restCertidaoMockMvc.perform(get("/api/certidaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certidao.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroDeNascimento").value(hasItem(DEFAULT_REGISTRO_DE_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].tipoDaCertidao").value(hasItem(DEFAULT_TIPO_DA_CERTIDAO.toString())))
            .andExpect(jsonPath("$.[*].nomeDoCartorio").value(hasItem(DEFAULT_NOME_DO_CARTORIO)))
            .andExpect(jsonPath("$.[*].livro").value(hasItem(DEFAULT_LIVRO)))
            .andExpect(jsonPath("$.[*].folhas").value(hasItem(DEFAULT_FOLHAS)))
            .andExpect(jsonPath("$.[*].termo").value(hasItem(DEFAULT_TERMO)))
            .andExpect(jsonPath("$.[*].dataDeEmissao").value(hasItem(DEFAULT_DATA_DE_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].numeroDaDeclaracaoDeNascimento").value(hasItem(DEFAULT_NUMERO_DA_DECLARACAO_DE_NASCIMENTO)));
    }
    
    @Test
    @Transactional
    public void getCertidao() throws Exception {
        // Initialize the database
        certidaoRepository.saveAndFlush(certidao);

        // Get the certidao
        restCertidaoMockMvc.perform(get("/api/certidaos/{id}", certidao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certidao.getId().intValue()))
            .andExpect(jsonPath("$.registroDeNascimento").value(DEFAULT_REGISTRO_DE_NASCIMENTO))
            .andExpect(jsonPath("$.tipoDaCertidao").value(DEFAULT_TIPO_DA_CERTIDAO.toString()))
            .andExpect(jsonPath("$.nomeDoCartorio").value(DEFAULT_NOME_DO_CARTORIO))
            .andExpect(jsonPath("$.livro").value(DEFAULT_LIVRO))
            .andExpect(jsonPath("$.folhas").value(DEFAULT_FOLHAS))
            .andExpect(jsonPath("$.termo").value(DEFAULT_TERMO))
            .andExpect(jsonPath("$.dataDeEmissao").value(DEFAULT_DATA_DE_EMISSAO.toString()))
            .andExpect(jsonPath("$.numeroDaDeclaracaoDeNascimento").value(DEFAULT_NUMERO_DA_DECLARACAO_DE_NASCIMENTO));
    }

    @Test
    @Transactional
    public void getNonExistingCertidao() throws Exception {
        // Get the certidao
        restCertidaoMockMvc.perform(get("/api/certidaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCertidao() throws Exception {
        // Initialize the database
        certidaoRepository.saveAndFlush(certidao);

        int databaseSizeBeforeUpdate = certidaoRepository.findAll().size();

        // Update the certidao
        Certidao updatedCertidao = certidaoRepository.findById(certidao.getId()).get();
        // Disconnect from session so that the updates on updatedCertidao are not directly saved in db
        em.detach(updatedCertidao);
        updatedCertidao
            .registroDeNascimento(UPDATED_REGISTRO_DE_NASCIMENTO)
            .tipoDaCertidao(UPDATED_TIPO_DA_CERTIDAO)
            .nomeDoCartorio(UPDATED_NOME_DO_CARTORIO)
            .livro(UPDATED_LIVRO)
            .folhas(UPDATED_FOLHAS)
            .termo(UPDATED_TERMO)
            .dataDeEmissao(UPDATED_DATA_DE_EMISSAO)
            .numeroDaDeclaracaoDeNascimento(UPDATED_NUMERO_DA_DECLARACAO_DE_NASCIMENTO);
        CertidaoDTO certidaoDTO = certidaoMapper.toDto(updatedCertidao);

        restCertidaoMockMvc.perform(put("/api/certidaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certidaoDTO)))
            .andExpect(status().isOk());

        // Validate the Certidao in the database
        List<Certidao> certidaoList = certidaoRepository.findAll();
        assertThat(certidaoList).hasSize(databaseSizeBeforeUpdate);
        Certidao testCertidao = certidaoList.get(certidaoList.size() - 1);
        assertThat(testCertidao.getRegistroDeNascimento()).isEqualTo(UPDATED_REGISTRO_DE_NASCIMENTO);
        assertThat(testCertidao.getTipoDaCertidao()).isEqualTo(UPDATED_TIPO_DA_CERTIDAO);
        assertThat(testCertidao.getNomeDoCartorio()).isEqualTo(UPDATED_NOME_DO_CARTORIO);
        assertThat(testCertidao.getLivro()).isEqualTo(UPDATED_LIVRO);
        assertThat(testCertidao.getFolhas()).isEqualTo(UPDATED_FOLHAS);
        assertThat(testCertidao.getTermo()).isEqualTo(UPDATED_TERMO);
        assertThat(testCertidao.getDataDeEmissao()).isEqualTo(UPDATED_DATA_DE_EMISSAO);
        assertThat(testCertidao.getNumeroDaDeclaracaoDeNascimento()).isEqualTo(UPDATED_NUMERO_DA_DECLARACAO_DE_NASCIMENTO);

        // Validate the Certidao in Elasticsearch
        verify(mockCertidaoSearchRepository, times(1)).save(testCertidao);
    }

    @Test
    @Transactional
    public void updateNonExistingCertidao() throws Exception {
        int databaseSizeBeforeUpdate = certidaoRepository.findAll().size();

        // Create the Certidao
        CertidaoDTO certidaoDTO = certidaoMapper.toDto(certidao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertidaoMockMvc.perform(put("/api/certidaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(certidaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certidao in the database
        List<Certidao> certidaoList = certidaoRepository.findAll();
        assertThat(certidaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Certidao in Elasticsearch
        verify(mockCertidaoSearchRepository, times(0)).save(certidao);
    }

    @Test
    @Transactional
    public void deleteCertidao() throws Exception {
        // Initialize the database
        certidaoRepository.saveAndFlush(certidao);

        int databaseSizeBeforeDelete = certidaoRepository.findAll().size();

        // Delete the certidao
        restCertidaoMockMvc.perform(delete("/api/certidaos/{id}", certidao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Certidao> certidaoList = certidaoRepository.findAll();
        assertThat(certidaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Certidao in Elasticsearch
        verify(mockCertidaoSearchRepository, times(1)).deleteById(certidao.getId());
    }

    @Test
    @Transactional
    public void searchCertidao() throws Exception {
        // Initialize the database
        certidaoRepository.saveAndFlush(certidao);
        when(mockCertidaoSearchRepository.search(queryStringQuery("id:" + certidao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(certidao), PageRequest.of(0, 1), 1));
        // Search the certidao
        restCertidaoMockMvc.perform(get("/api/_search/certidaos?query=id:" + certidao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certidao.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroDeNascimento").value(hasItem(DEFAULT_REGISTRO_DE_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].tipoDaCertidao").value(hasItem(DEFAULT_TIPO_DA_CERTIDAO.toString())))
            .andExpect(jsonPath("$.[*].nomeDoCartorio").value(hasItem(DEFAULT_NOME_DO_CARTORIO)))
            .andExpect(jsonPath("$.[*].livro").value(hasItem(DEFAULT_LIVRO)))
            .andExpect(jsonPath("$.[*].folhas").value(hasItem(DEFAULT_FOLHAS)))
            .andExpect(jsonPath("$.[*].termo").value(hasItem(DEFAULT_TERMO)))
            .andExpect(jsonPath("$.[*].dataDeEmissao").value(hasItem(DEFAULT_DATA_DE_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].numeroDaDeclaracaoDeNascimento").value(hasItem(DEFAULT_NUMERO_DA_DECLARACAO_DE_NASCIMENTO)));
    }
}
