package br.com.basis.madre.web.rest;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.Internacao;
import br.com.basis.madre.repository.InternacaoRepository;
import br.com.basis.madre.repository.search.InternacaoSearchRepository;
import br.com.basis.madre.service.InternacaoService;
import br.com.basis.madre.service.dto.InternacaoDTO;
import br.com.basis.madre.service.mapper.InternacaoMapper;

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
import java.time.*;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.domain.enumeration.Prioridade;
import br.com.basis.madre.domain.enumeration.TipoDeAlta;
/**
 * Integration tests for the {@link InternacaoResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InternacaoResourceIT {

    private static final Prioridade DEFAULT_PRIORIDADE = Prioridade.ELETIVA;
    private static final Prioridade UPDATED_PRIORIDADE = Prioridade.URGENCIA;

    private static final String DEFAULT_JUSTIFICATIVA = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATIVA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_DA_INTERNACAO = ZonedDateTime.of(LocalDateTime.of(LocalDate.ofEpochDay(0L), LocalTime.now()), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATA_DA_INTERNACAO = ZonedDateTime.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DIFERENCA_DE_CLASSE = false;
    private static final Boolean UPDATED_DIFERENCA_DE_CLASSE = true;

    private static final Boolean DEFAULT_SOLICITAR_PRONTUARIO = false;
    private static final Boolean UPDATED_SOLICITAR_PRONTUARIO = true;

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final LocalDate DEFAULT_DATA_DA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PREVISAO_DE_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PREVISAO_DE_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final TipoDeAlta DEFAULT_TIPO_DE_ALTA = TipoDeAlta.OBITO;
    private static final TipoDeAlta UPDATED_TIPO_DE_ALTA = TipoDeAlta.ALTA_MEDICA;

    @Autowired
    private InternacaoRepository internacaoRepository;

    @Autowired
    private InternacaoMapper internacaoMapper;

    @Autowired
    private InternacaoService internacaoService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.InternacaoSearchRepositoryMockConfiguration
     */
    @Autowired
    private InternacaoSearchRepository mockInternacaoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInternacaoMockMvc;

    private Internacao internacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Internacao createEntity(EntityManager em) {
        Internacao internacao = new Internacao()
            .prioridade(DEFAULT_PRIORIDADE)
            .justificativa(DEFAULT_JUSTIFICATIVA)
            .dataDaInternacao(DEFAULT_DATA_DA_INTERNACAO)
            .diferencaDeClasse(DEFAULT_DIFERENCA_DE_CLASSE)
            .solicitarProntuario(DEFAULT_SOLICITAR_PRONTUARIO)
            .ativo(DEFAULT_ATIVO)
            .dataDaAlta(DEFAULT_DATA_DA_ALTA)
            .previsaoDeAlta(DEFAULT_PREVISAO_DE_ALTA)
            .tipoDeAlta(DEFAULT_TIPO_DE_ALTA);
        return internacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Internacao createUpdatedEntity(EntityManager em) {
        Internacao internacao = new Internacao()
            .prioridade(UPDATED_PRIORIDADE)
            .justificativa(UPDATED_JUSTIFICATIVA)
            .dataDaInternacao(UPDATED_DATA_DA_INTERNACAO)
            .diferencaDeClasse(UPDATED_DIFERENCA_DE_CLASSE)
            .solicitarProntuario(UPDATED_SOLICITAR_PRONTUARIO)
            .ativo(UPDATED_ATIVO)
            .dataDaAlta(UPDATED_DATA_DA_ALTA)
            .previsaoDeAlta(UPDATED_PREVISAO_DE_ALTA)
            .tipoDeAlta(UPDATED_TIPO_DE_ALTA);
        return internacao;
    }

    @BeforeEach
    public void initTest() {
        internacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternacao() throws Exception {
        int databaseSizeBeforeCreate = internacaoRepository.findAll().size();
        // Create the Internacao
        InternacaoDTO internacaoDTO = internacaoMapper.toDto(internacao);
        restInternacaoMockMvc.perform(post("/api/internacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(internacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Internacao in the database
        List<Internacao> internacaoList = internacaoRepository.findAll();
        assertThat(internacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Internacao testInternacao = internacaoList.get(internacaoList.size() - 1);
        assertThat(testInternacao.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testInternacao.getJustificativa()).isEqualTo(DEFAULT_JUSTIFICATIVA);
        assertThat(testInternacao.getDataDaInternacao()).isEqualTo(DEFAULT_DATA_DA_INTERNACAO);
        assertThat(testInternacao.isDiferencaDeClasse()).isEqualTo(DEFAULT_DIFERENCA_DE_CLASSE);
        assertThat(testInternacao.isSolicitarProntuario()).isEqualTo(DEFAULT_SOLICITAR_PRONTUARIO);
        assertThat(testInternacao.getAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testInternacao.getDataDaAlta()).isEqualTo(DEFAULT_DATA_DA_ALTA);
        assertThat(testInternacao.getPrevisaoDeAlta()).isEqualTo(DEFAULT_PREVISAO_DE_ALTA);
        assertThat(testInternacao.getTipoDeAlta()).isEqualTo(DEFAULT_TIPO_DE_ALTA);

        // Validate the Internacao in Elasticsearch
        verify(mockInternacaoSearchRepository, times(1)).save(testInternacao);
    }

    @Test
    @Transactional
    public void createInternacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internacaoRepository.findAll().size();

        // Create the Internacao with an existing ID
        internacao.setId(1L);
        InternacaoDTO internacaoDTO = internacaoMapper.toDto(internacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternacaoMockMvc.perform(post("/api/internacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(internacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Internacao in the database
        List<Internacao> internacaoList = internacaoRepository.findAll();
        assertThat(internacaoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Internacao in Elasticsearch
        verify(mockInternacaoSearchRepository, times(0)).save(internacao);
    }


    @Test
    @Transactional
    public void checkPrioridadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = internacaoRepository.findAll().size();
        // set the field null
        internacao.setPrioridade(null);

        // Create the Internacao, which fails.
        InternacaoDTO internacaoDTO = internacaoMapper.toDto(internacao);


        restInternacaoMockMvc.perform(post("/api/internacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(internacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Internacao> internacaoList = internacaoRepository.findAll();
        assertThat(internacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJustificativaIsRequired() throws Exception {
        int databaseSizeBeforeTest = internacaoRepository.findAll().size();
        // set the field null
        internacao.setJustificativa(null);

        // Create the Internacao, which fails.
        InternacaoDTO internacaoDTO = internacaoMapper.toDto(internacao);


        restInternacaoMockMvc.perform(post("/api/internacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(internacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Internacao> internacaoList = internacaoRepository.findAll();
        assertThat(internacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDaInternacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = internacaoRepository.findAll().size();
        // set the field null
        internacao.setDataDaInternacao(null);

        // Create the Internacao, which fails.
        InternacaoDTO internacaoDTO = internacaoMapper.toDto(internacao);


        restInternacaoMockMvc.perform(post("/api/internacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(internacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Internacao> internacaoList = internacaoRepository.findAll();
        assertThat(internacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInternacaos() throws Exception {
        // Initialize the database
        internacaoRepository.saveAndFlush(internacao);

        // Get all the internacaoList
        restInternacaoMockMvc.perform(get("/api/internacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE.toString())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)))
            .andExpect(jsonPath("$.[*].dataDaInternacao").value(hasItem(DEFAULT_DATA_DA_INTERNACAO.toString())))
            .andExpect(jsonPath("$.[*].diferencaDeClasse").value(hasItem(DEFAULT_DIFERENCA_DE_CLASSE.booleanValue())))
            .andExpect(jsonPath("$.[*].solicitarProntuario").value(hasItem(DEFAULT_SOLICITAR_PRONTUARIO.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataDaAlta").value(hasItem(DEFAULT_DATA_DA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].previsaoDeAlta").value(hasItem(DEFAULT_PREVISAO_DE_ALTA.toString())))
            .andExpect(jsonPath("$.[*].tipoDeAlta").value(hasItem(DEFAULT_TIPO_DE_ALTA.toString())));
    }

    @Test
    @Transactional
    public void getInternacao() throws Exception {
        // Initialize the database
        internacaoRepository.saveAndFlush(internacao);

        // Get the internacao
        restInternacaoMockMvc.perform(get("/api/internacaos/{id}", internacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(internacao.getId().intValue()))
            .andExpect(jsonPath("$.prioridade").value(DEFAULT_PRIORIDADE.toString()))
            .andExpect(jsonPath("$.justificativa").value(DEFAULT_JUSTIFICATIVA))
            .andExpect(jsonPath("$.dataDaInternacao").value(DEFAULT_DATA_DA_INTERNACAO.toString()))
            .andExpect(jsonPath("$.diferencaDeClasse").value(DEFAULT_DIFERENCA_DE_CLASSE.booleanValue()))
            .andExpect(jsonPath("$.solicitarProntuario").value(DEFAULT_SOLICITAR_PRONTUARIO.booleanValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.dataDaAlta").value(DEFAULT_DATA_DA_ALTA.toString()))
            .andExpect(jsonPath("$.previsaoDeAlta").value(DEFAULT_PREVISAO_DE_ALTA.toString()))
            .andExpect(jsonPath("$.tipoDeAlta").value(DEFAULT_TIPO_DE_ALTA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInternacao() throws Exception {
        // Get the internacao
        restInternacaoMockMvc.perform(get("/api/internacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternacao() throws Exception {
        // Initialize the database
        internacaoRepository.saveAndFlush(internacao);

        int databaseSizeBeforeUpdate = internacaoRepository.findAll().size();

        // Update the internacao
        Internacao updatedInternacao = internacaoRepository.findById(internacao.getId()).get();
        // Disconnect from session so that the updates on updatedInternacao are not directly saved in db
        em.detach(updatedInternacao);
        updatedInternacao
            .prioridade(UPDATED_PRIORIDADE)
            .justificativa(UPDATED_JUSTIFICATIVA)
            .dataDaInternacao(UPDATED_DATA_DA_INTERNACAO)
            .diferencaDeClasse(UPDATED_DIFERENCA_DE_CLASSE)
            .solicitarProntuario(UPDATED_SOLICITAR_PRONTUARIO)
            .ativo(UPDATED_ATIVO)
            .dataDaAlta(UPDATED_DATA_DA_ALTA)
            .previsaoDeAlta(UPDATED_PREVISAO_DE_ALTA)
            .tipoDeAlta(UPDATED_TIPO_DE_ALTA);
        InternacaoDTO internacaoDTO = internacaoMapper.toDto(updatedInternacao);

        restInternacaoMockMvc.perform(put("/api/internacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(internacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Internacao in the database
        List<Internacao> internacaoList = internacaoRepository.findAll();
        assertThat(internacaoList).hasSize(databaseSizeBeforeUpdate);
        Internacao testInternacao = internacaoList.get(internacaoList.size() - 1);
        assertThat(testInternacao.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testInternacao.getJustificativa()).isEqualTo(UPDATED_JUSTIFICATIVA);
        assertThat(testInternacao.getDataDaInternacao()).isEqualTo(UPDATED_DATA_DA_INTERNACAO);
        assertThat(testInternacao.isDiferencaDeClasse()).isEqualTo(UPDATED_DIFERENCA_DE_CLASSE);
        assertThat(testInternacao.isSolicitarProntuario()).isEqualTo(UPDATED_SOLICITAR_PRONTUARIO);
        assertThat(testInternacao.getAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testInternacao.getDataDaAlta()).isEqualTo(UPDATED_DATA_DA_ALTA);
        assertThat(testInternacao.getPrevisaoDeAlta()).isEqualTo(UPDATED_PREVISAO_DE_ALTA);
        assertThat(testInternacao.getTipoDeAlta()).isEqualTo(UPDATED_TIPO_DE_ALTA);

        // Validate the Internacao in Elasticsearch
        verify(mockInternacaoSearchRepository, times(1)).save(testInternacao);
    }

    @Test
    @Transactional
    public void updateNonExistingInternacao() throws Exception {
        int databaseSizeBeforeUpdate = internacaoRepository.findAll().size();

        // Create the Internacao
        InternacaoDTO internacaoDTO = internacaoMapper.toDto(internacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternacaoMockMvc.perform(put("/api/internacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(internacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Internacao in the database
        List<Internacao> internacaoList = internacaoRepository.findAll();
        assertThat(internacaoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Internacao in Elasticsearch
        verify(mockInternacaoSearchRepository, times(0)).save(internacao);
    }

    @Test
    @Transactional
    public void deleteInternacao() throws Exception {
        // Initialize the database
        internacaoRepository.saveAndFlush(internacao);

        int databaseSizeBeforeDelete = internacaoRepository.findAll().size();

        // Delete the internacao
        restInternacaoMockMvc.perform(delete("/api/internacaos/{id}", internacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Internacao> internacaoList = internacaoRepository.findAll();
        assertThat(internacaoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Internacao in Elasticsearch
        verify(mockInternacaoSearchRepository, times(1)).deleteById(internacao.getId());
    }

    @Test
    @Transactional
    public void searchInternacao() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        internacaoRepository.saveAndFlush(internacao);
        when(mockInternacaoSearchRepository.search(queryStringQuery("id:" + internacao.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(internacao), PageRequest.of(0, 1), 1));

        // Search the internacao
        restInternacaoMockMvc.perform(get("/api/_search/internacaos?query=id:" + internacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE.toString())))
            .andExpect(jsonPath("$.[*].justificativa").value(hasItem(DEFAULT_JUSTIFICATIVA)))
            .andExpect(jsonPath("$.[*].dataDaInternacao").value(hasItem(DEFAULT_DATA_DA_INTERNACAO.toString())))
            .andExpect(jsonPath("$.[*].diferencaDeClasse").value(hasItem(DEFAULT_DIFERENCA_DE_CLASSE.booleanValue())))
            .andExpect(jsonPath("$.[*].solicitarProntuario").value(hasItem(DEFAULT_SOLICITAR_PRONTUARIO.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataDaAlta").value(hasItem(DEFAULT_DATA_DA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].previsaoDeAlta").value(hasItem(DEFAULT_PREVISAO_DE_ALTA.toString())))
            .andExpect(jsonPath("$.[*].tipoDeAlta").value(hasItem(DEFAULT_TIPO_DE_ALTA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Internacao.class);
        Internacao internacao1 = new Internacao();
        internacao1.setId(1L);
        Internacao internacao2 = new Internacao();
        internacao2.setId(internacao1.getId());
        assertThat(internacao1).isEqualTo(internacao2);
        internacao2.setId(2L);
        assertThat(internacao1).isNotEqualTo(internacao2);
        internacao1.setId(null);
        assertThat(internacao1).isNotEqualTo(internacao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternacaoDTO.class);
        InternacaoDTO internacaoDTO1 = new InternacaoDTO();
        internacaoDTO1.setId(1L);
        InternacaoDTO internacaoDTO2 = new InternacaoDTO();
        assertThat(internacaoDTO1).isNotEqualTo(internacaoDTO2);
        internacaoDTO2.setId(internacaoDTO1.getId());
        assertThat(internacaoDTO1).isEqualTo(internacaoDTO2);
        internacaoDTO2.setId(2L);
        assertThat(internacaoDTO1).isNotEqualTo(internacaoDTO2);
        internacaoDTO1.setId(null);
        assertThat(internacaoDTO1).isNotEqualTo(internacaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(internacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(internacaoMapper.fromId(null)).isNull();
    }
}
