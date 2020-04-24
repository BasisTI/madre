package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.Documento;
import br.com.basis.madre.repository.DocumentoRepository;
import br.com.basis.madre.repository.search.DocumentoSearchRepository;
import br.com.basis.madre.service.DocumentoService;
import br.com.basis.madre.service.dto.DocumentoDTO;
import br.com.basis.madre.service.mapper.DocumentoMapper;

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

/**
 * Integration tests for the {@link DocumentoResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocumentoResourceIT {

    private static final String DEFAULT_NUMERO_DA_IDENTIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DA_IDENTIDADE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_PIS_PASEP = "AAAAAAAAAA";
    private static final String UPDATED_PIS_PASEP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALIDADE_DA_CNH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDADE_DA_CNH = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DOCUMENTOS_APRESENTADOS = false;
    private static final Boolean UPDATED_DOCUMENTOS_APRESENTADOS = true;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Autowired
    private DocumentoService documentoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.DocumentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentoSearchRepository mockDocumentoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentoMockMvc;

    private Documento documento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createEntity(EntityManager em) {
        Documento documento = new Documento()
            .numeroDaIdentidade(DEFAULT_NUMERO_DA_IDENTIDADE)
            .data(DEFAULT_DATA)
            .cpf(DEFAULT_CPF)
            .pisPasep(DEFAULT_PIS_PASEP)
            .validadeDaCnh(DEFAULT_VALIDADE_DA_CNH)
            .documentosApresentados(DEFAULT_DOCUMENTOS_APRESENTADOS);
        return documento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createUpdatedEntity(EntityManager em) {
        Documento documento = new Documento()
            .numeroDaIdentidade(UPDATED_NUMERO_DA_IDENTIDADE)
            .data(UPDATED_DATA)
            .cpf(UPDATED_CPF)
            .pisPasep(UPDATED_PIS_PASEP)
            .validadeDaCnh(UPDATED_VALIDADE_DA_CNH)
            .documentosApresentados(UPDATED_DOCUMENTOS_APRESENTADOS);
        return documento;
    }

    @BeforeEach
    public void initTest() {
        documento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumento() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);
        restDocumentoMockMvc.perform(post("/api/documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate + 1);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNumeroDaIdentidade()).isEqualTo(DEFAULT_NUMERO_DA_IDENTIDADE);
        assertThat(testDocumento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDocumento.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testDocumento.getPisPasep()).isEqualTo(DEFAULT_PIS_PASEP);
        assertThat(testDocumento.getValidadeDaCnh()).isEqualTo(DEFAULT_VALIDADE_DA_CNH);
        assertThat(testDocumento.isDocumentosApresentados()).isEqualTo(DEFAULT_DOCUMENTOS_APRESENTADOS);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(1)).save(testDocumento);
    }

    @Test
    @Transactional
    public void createDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento with an existing ID
        documento.setId(1L);
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoMockMvc.perform(post("/api/documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(0)).save(documento);
    }


    @Test
    @Transactional
    public void getAllDocumentos() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList
        restDocumentoMockMvc.perform(get("/api/documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDaIdentidade").value(hasItem(DEFAULT_NUMERO_DA_IDENTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].pisPasep").value(hasItem(DEFAULT_PIS_PASEP)))
            .andExpect(jsonPath("$.[*].validadeDaCnh").value(hasItem(DEFAULT_VALIDADE_DA_CNH.toString())))
            .andExpect(jsonPath("$.[*].documentosApresentados").value(hasItem(DEFAULT_DOCUMENTOS_APRESENTADOS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documento.getId().intValue()))
            .andExpect(jsonPath("$.numeroDaIdentidade").value(DEFAULT_NUMERO_DA_IDENTIDADE))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.pisPasep").value(DEFAULT_PIS_PASEP))
            .andExpect(jsonPath("$.validadeDaCnh").value(DEFAULT_VALIDADE_DA_CNH.toString()))
            .andExpect(jsonPath("$.documentosApresentados").value(DEFAULT_DOCUMENTOS_APRESENTADOS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumento() throws Exception {
        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento
        Documento updatedDocumento = documentoRepository.findById(documento.getId()).get();
        // Disconnect from session so that the updates on updatedDocumento are not directly saved in db
        em.detach(updatedDocumento);
        updatedDocumento
            .numeroDaIdentidade(UPDATED_NUMERO_DA_IDENTIDADE)
            .data(UPDATED_DATA)
            .cpf(UPDATED_CPF)
            .pisPasep(UPDATED_PIS_PASEP)
            .validadeDaCnh(UPDATED_VALIDADE_DA_CNH)
            .documentosApresentados(UPDATED_DOCUMENTOS_APRESENTADOS);
        DocumentoDTO documentoDTO = documentoMapper.toDto(updatedDocumento);

        restDocumentoMockMvc.perform(put("/api/documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
            .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNumeroDaIdentidade()).isEqualTo(UPDATED_NUMERO_DA_IDENTIDADE);
        assertThat(testDocumento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDocumento.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testDocumento.getPisPasep()).isEqualTo(UPDATED_PIS_PASEP);
        assertThat(testDocumento.getValidadeDaCnh()).isEqualTo(UPDATED_VALIDADE_DA_CNH);
        assertThat(testDocumento.isDocumentosApresentados()).isEqualTo(UPDATED_DOCUMENTOS_APRESENTADOS);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(1)).save(testDocumento);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoMockMvc.perform(put("/api/documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(0)).save(documento);
    }

    @Test
    @Transactional
    public void deleteDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeDelete = documentoRepository.findAll().size();

        // Delete the documento
        restDocumentoMockMvc.perform(delete("/api/documentos/{id}", documento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Documento in Elasticsearch
        verify(mockDocumentoSearchRepository, times(1)).deleteById(documento.getId());
    }

    @Test
    @Transactional
    public void searchDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);
        when(mockDocumentoSearchRepository.search(queryStringQuery("id:" + documento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documento), PageRequest.of(0, 1), 1));
        // Search the documento
        restDocumentoMockMvc.perform(get("/api/_search/documentos?query=id:" + documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDaIdentidade").value(hasItem(DEFAULT_NUMERO_DA_IDENTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].pisPasep").value(hasItem(DEFAULT_PIS_PASEP)))
            .andExpect(jsonPath("$.[*].validadeDaCnh").value(hasItem(DEFAULT_VALIDADE_DA_CNH.toString())))
            .andExpect(jsonPath("$.[*].documentosApresentados").value(hasItem(DEFAULT_DOCUMENTOS_APRESENTADOS.booleanValue())));
    }
}
