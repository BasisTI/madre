package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Documentos;
import br.com.basis.madre.seguranca.repository.DocumentosRepository;
import br.com.basis.madre.seguranca.repository.search.DocumentosSearchRepository;
import br.com.basis.madre.seguranca.service.DocumentosService;
import br.com.basis.madre.seguranca.service.dto.DocumentosDTO;
import br.com.basis.madre.seguranca.service.mapper.DocumentosMapper;

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
 * Integration tests for the {@link DocumentosResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocumentosResourceIT {

    private static final Integer DEFAULT_NUMERO_DA_IDENTIDADE = 1;
    private static final Integer UPDATED_NUMERO_DA_IDENTIDADE = 2;

    private static final LocalDate DEFAULT_DATA_DE_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_EMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PIS_PASEP = "AAAAAAAAAA";
    private static final String UPDATED_PIS_PASEP = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_PAC = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_PAC = "BBBBBBBBBB";

    private static final String DEFAULT_CTPS = "AAAAAAAAAA";
    private static final String UPDATED_CTPS = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_ELEITORAL = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_ELEITORAL = "BBBBBBBBBB";

    private static final String DEFAULT_ZONA = "AAAAAAAAAA";
    private static final String UPDATED_ZONA = "BBBBBBBBBB";

    private static final String DEFAULT_SECAO = "AAAAAAAAAA";
    private static final String UPDATED_SECAO = "BBBBBBBBBB";

    @Autowired
    private DocumentosRepository documentosRepository;

    @Autowired
    private DocumentosMapper documentosMapper;

    @Autowired
    private DocumentosService documentosService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.DocumentosSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentosSearchRepository mockDocumentosSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentosMockMvc;

    private Documentos documentos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documentos createEntity(EntityManager em) {
        Documentos documentos = new Documentos()
            .numeroDaIdentidade(DEFAULT_NUMERO_DA_IDENTIDADE)
            .dataDeEmissao(DEFAULT_DATA_DE_EMISSAO)
            .pisPasep(DEFAULT_PIS_PASEP)
            .codigoPac(DEFAULT_CODIGO_PAC)
            .ctps(DEFAULT_CTPS)
            .serie(DEFAULT_SERIE)
            .tituloEleitoral(DEFAULT_TITULO_ELEITORAL)
            .zona(DEFAULT_ZONA)
            .secao(DEFAULT_SECAO);
        return documentos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documentos createUpdatedEntity(EntityManager em) {
        Documentos documentos = new Documentos()
            .numeroDaIdentidade(UPDATED_NUMERO_DA_IDENTIDADE)
            .dataDeEmissao(UPDATED_DATA_DE_EMISSAO)
            .pisPasep(UPDATED_PIS_PASEP)
            .codigoPac(UPDATED_CODIGO_PAC)
            .ctps(UPDATED_CTPS)
            .serie(UPDATED_SERIE)
            .tituloEleitoral(UPDATED_TITULO_ELEITORAL)
            .zona(UPDATED_ZONA)
            .secao(UPDATED_SECAO);
        return documentos;
    }

    @BeforeEach
    public void initTest() {
        documentos = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentos() throws Exception {
        int databaseSizeBeforeCreate = documentosRepository.findAll().size();
        // Create the Documentos
        DocumentosDTO documentosDTO = documentosMapper.toDto(documentos);
        restDocumentosMockMvc.perform(post("/api/documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentosDTO)))
            .andExpect(status().isCreated());

        // Validate the Documentos in the database
        List<Documentos> documentosList = documentosRepository.findAll();
        assertThat(documentosList).hasSize(databaseSizeBeforeCreate + 1);
        Documentos testDocumentos = documentosList.get(documentosList.size() - 1);
        assertThat(testDocumentos.getNumeroDaIdentidade()).isEqualTo(DEFAULT_NUMERO_DA_IDENTIDADE);
        assertThat(testDocumentos.getDataDeEmissao()).isEqualTo(DEFAULT_DATA_DE_EMISSAO);
        assertThat(testDocumentos.getPisPasep()).isEqualTo(DEFAULT_PIS_PASEP);
        assertThat(testDocumentos.getCodigoPac()).isEqualTo(DEFAULT_CODIGO_PAC);
        assertThat(testDocumentos.getCtps()).isEqualTo(DEFAULT_CTPS);
        assertThat(testDocumentos.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testDocumentos.getTituloEleitoral()).isEqualTo(DEFAULT_TITULO_ELEITORAL);
        assertThat(testDocumentos.getZona()).isEqualTo(DEFAULT_ZONA);
        assertThat(testDocumentos.getSecao()).isEqualTo(DEFAULT_SECAO);

        // Validate the Documentos in Elasticsearch
        verify(mockDocumentosSearchRepository, times(1)).save(testDocumentos);
    }

    @Test
    @Transactional
    public void createDocumentosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentosRepository.findAll().size();

        // Create the Documentos with an existing ID
        documentos.setId(1L);
        DocumentosDTO documentosDTO = documentosMapper.toDto(documentos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentosMockMvc.perform(post("/api/documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Documentos in the database
        List<Documentos> documentosList = documentosRepository.findAll();
        assertThat(documentosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Documentos in Elasticsearch
        verify(mockDocumentosSearchRepository, times(0)).save(documentos);
    }


    @Test
    @Transactional
    public void getAllDocumentos() throws Exception {
        // Initialize the database
        documentosRepository.saveAndFlush(documentos);

        // Get all the documentosList
        restDocumentosMockMvc.perform(get("/api/documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDaIdentidade").value(hasItem(DEFAULT_NUMERO_DA_IDENTIDADE)))
            .andExpect(jsonPath("$.[*].dataDeEmissao").value(hasItem(DEFAULT_DATA_DE_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].pisPasep").value(hasItem(DEFAULT_PIS_PASEP)))
            .andExpect(jsonPath("$.[*].codigoPac").value(hasItem(DEFAULT_CODIGO_PAC)))
            .andExpect(jsonPath("$.[*].ctps").value(hasItem(DEFAULT_CTPS)))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].tituloEleitoral").value(hasItem(DEFAULT_TITULO_ELEITORAL)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA)))
            .andExpect(jsonPath("$.[*].secao").value(hasItem(DEFAULT_SECAO)));
    }
    
    @Test
    @Transactional
    public void getDocumentos() throws Exception {
        // Initialize the database
        documentosRepository.saveAndFlush(documentos);

        // Get the documentos
        restDocumentosMockMvc.perform(get("/api/documentos/{id}", documentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentos.getId().intValue()))
            .andExpect(jsonPath("$.numeroDaIdentidade").value(DEFAULT_NUMERO_DA_IDENTIDADE))
            .andExpect(jsonPath("$.dataDeEmissao").value(DEFAULT_DATA_DE_EMISSAO.toString()))
            .andExpect(jsonPath("$.pisPasep").value(DEFAULT_PIS_PASEP))
            .andExpect(jsonPath("$.codigoPac").value(DEFAULT_CODIGO_PAC))
            .andExpect(jsonPath("$.ctps").value(DEFAULT_CTPS))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE))
            .andExpect(jsonPath("$.tituloEleitoral").value(DEFAULT_TITULO_ELEITORAL))
            .andExpect(jsonPath("$.zona").value(DEFAULT_ZONA))
            .andExpect(jsonPath("$.secao").value(DEFAULT_SECAO));
    }
    @Test
    @Transactional
    public void getNonExistingDocumentos() throws Exception {
        // Get the documentos
        restDocumentosMockMvc.perform(get("/api/documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentos() throws Exception {
        // Initialize the database
        documentosRepository.saveAndFlush(documentos);

        int databaseSizeBeforeUpdate = documentosRepository.findAll().size();

        // Update the documentos
        Documentos updatedDocumentos = documentosRepository.findById(documentos.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentos are not directly saved in db
        em.detach(updatedDocumentos);
        updatedDocumentos
            .numeroDaIdentidade(UPDATED_NUMERO_DA_IDENTIDADE)
            .dataDeEmissao(UPDATED_DATA_DE_EMISSAO)
            .pisPasep(UPDATED_PIS_PASEP)
            .codigoPac(UPDATED_CODIGO_PAC)
            .ctps(UPDATED_CTPS)
            .serie(UPDATED_SERIE)
            .tituloEleitoral(UPDATED_TITULO_ELEITORAL)
            .zona(UPDATED_ZONA)
            .secao(UPDATED_SECAO);
        DocumentosDTO documentosDTO = documentosMapper.toDto(updatedDocumentos);

        restDocumentosMockMvc.perform(put("/api/documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentosDTO)))
            .andExpect(status().isOk());

        // Validate the Documentos in the database
        List<Documentos> documentosList = documentosRepository.findAll();
        assertThat(documentosList).hasSize(databaseSizeBeforeUpdate);
        Documentos testDocumentos = documentosList.get(documentosList.size() - 1);
        assertThat(testDocumentos.getNumeroDaIdentidade()).isEqualTo(UPDATED_NUMERO_DA_IDENTIDADE);
        assertThat(testDocumentos.getDataDeEmissao()).isEqualTo(UPDATED_DATA_DE_EMISSAO);
        assertThat(testDocumentos.getPisPasep()).isEqualTo(UPDATED_PIS_PASEP);
        assertThat(testDocumentos.getCodigoPac()).isEqualTo(UPDATED_CODIGO_PAC);
        assertThat(testDocumentos.getCtps()).isEqualTo(UPDATED_CTPS);
        assertThat(testDocumentos.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testDocumentos.getTituloEleitoral()).isEqualTo(UPDATED_TITULO_ELEITORAL);
        assertThat(testDocumentos.getZona()).isEqualTo(UPDATED_ZONA);
        assertThat(testDocumentos.getSecao()).isEqualTo(UPDATED_SECAO);

        // Validate the Documentos in Elasticsearch
        verify(mockDocumentosSearchRepository, times(1)).save(testDocumentos);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentos() throws Exception {
        int databaseSizeBeforeUpdate = documentosRepository.findAll().size();

        // Create the Documentos
        DocumentosDTO documentosDTO = documentosMapper.toDto(documentos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentosMockMvc.perform(put("/api/documentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Documentos in the database
        List<Documentos> documentosList = documentosRepository.findAll();
        assertThat(documentosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Documentos in Elasticsearch
        verify(mockDocumentosSearchRepository, times(0)).save(documentos);
    }

    @Test
    @Transactional
    public void deleteDocumentos() throws Exception {
        // Initialize the database
        documentosRepository.saveAndFlush(documentos);

        int databaseSizeBeforeDelete = documentosRepository.findAll().size();

        // Delete the documentos
        restDocumentosMockMvc.perform(delete("/api/documentos/{id}", documentos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Documentos> documentosList = documentosRepository.findAll();
        assertThat(documentosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Documentos in Elasticsearch
        verify(mockDocumentosSearchRepository, times(1)).deleteById(documentos.getId());
    }

    @Test
    @Transactional
    public void searchDocumentos() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        documentosRepository.saveAndFlush(documentos);
        when(mockDocumentosSearchRepository.search(queryStringQuery("id:" + documentos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documentos), PageRequest.of(0, 1), 1));

        // Search the documentos
        restDocumentosMockMvc.perform(get("/api/_search/documentos?query=id:" + documentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDaIdentidade").value(hasItem(DEFAULT_NUMERO_DA_IDENTIDADE)))
            .andExpect(jsonPath("$.[*].dataDeEmissao").value(hasItem(DEFAULT_DATA_DE_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].pisPasep").value(hasItem(DEFAULT_PIS_PASEP)))
            .andExpect(jsonPath("$.[*].codigoPac").value(hasItem(DEFAULT_CODIGO_PAC)))
            .andExpect(jsonPath("$.[*].ctps").value(hasItem(DEFAULT_CTPS)))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].tituloEleitoral").value(hasItem(DEFAULT_TITULO_ELEITORAL)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA)))
            .andExpect(jsonPath("$.[*].secao").value(hasItem(DEFAULT_SECAO)));
    }
}
