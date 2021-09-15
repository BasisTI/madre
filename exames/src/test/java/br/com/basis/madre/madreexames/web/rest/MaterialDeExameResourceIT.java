package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.MaterialDeExame;
import br.com.basis.madre.madreexames.repository.MaterialDeExameRepository;
import br.com.basis.madre.madreexames.repository.search.MaterialDeExameSearchRepository;
import br.com.basis.madre.madreexames.service.MaterialDeExameService;
import br.com.basis.madre.madreexames.service.dto.MaterialDeExameDTO;
import br.com.basis.madre.madreexames.service.mapper.MaterialDeExameMapper;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MaterialDeExameResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MaterialDeExameResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_NPO = false;
    private static final Boolean UPDATED_NPO = true;

    private static final Boolean DEFAULT_JEJUM = false;
    private static final Boolean UPDATED_JEJUM = true;

    private static final Boolean DEFAULT_EXIGE_PREPARO = false;
    private static final Boolean UPDATED_EXIGE_PREPARO = true;

    private static final Boolean DEFAULT_EXIGE_DIETA = false;
    private static final Boolean UPDATED_EXIGE_DIETA = true;

    private static final Boolean DEFAULT_INFORMA_NUMERO_DE_COLETAS = false;
    private static final Boolean UPDATED_INFORMA_NUMERO_DE_COLETAS = true;

    private static final Boolean DEFAULT_GERA_ITEM_DE_SOLICITACAO = false;
    private static final Boolean UPDATED_GERA_ITEM_DE_SOLICITACAO = true;

    private static final Boolean DEFAULT_EXIGE_INTERVALO_DE_COLETA = false;
    private static final Boolean UPDATED_EXIGE_INTERVALO_DE_COLETA = true;

    private static final Boolean DEFAULT_EXIGE_REGIAO_ANATOMICA = false;
    private static final Boolean UPDATED_EXIGE_REGIAO_ANATOMICA = true;

    private static final Boolean DEFAULT_INGESTAO_DE_MEDICAMENTO = false;
    private static final Boolean UPDATED_INGESTAO_DE_MEDICAMENTO = true;

    private static final Boolean DEFAULT_DEPENDENTE_DE_EXAME = false;
    private static final Boolean UPDATED_DEPENDENTE_DE_EXAME = true;

    private static final Boolean DEFAULT_ANALISADO_PELA_CII = false;
    private static final Boolean UPDATED_ANALISADO_PELA_CII = true;

    private static final Boolean DEFAULT_INTERESSE_DA_COMEDI = false;
    private static final Boolean UPDATED_INTERESSE_DA_COMEDI = true;

    private static final Boolean DEFAULT_EXIGE_IMPRESSAO = false;
    private static final Boolean UPDATED_EXIGE_IMPRESSAO = true;

    private static final Boolean DEFAULT_APARECE_RESULTADO = false;
    private static final Boolean UPDATED_APARECE_RESULTADO = true;

    private static final Boolean DEFAULT_CONTA_CELULAS = false;
    private static final Boolean UPDATED_CONTA_CELULAS = true;

    private static final Boolean DEFAULT_LIMITE_DE_SOLICITACAO = false;
    private static final Boolean UPDATED_LIMITE_DE_SOLICITACAO = true;

    private static final Boolean DEFAULT_FORMA_DE_RESPIRACAO = false;
    private static final Boolean UPDATED_FORMA_DE_RESPIRACAO = true;

    private static final Boolean DEFAULT_AUTOMATICO = false;
    private static final Boolean UPDATED_AUTOMATICO = true;

    private static final Boolean DEFAULT_EXIGE_DADOS_COMPLEMENTARES = false;
    private static final Boolean UPDATED_EXIGE_DADOS_COMPLEMENTARES = true;

    private static final String DEFAULT_NATUREZA = "AAAAAAAAAA";
    private static final String UPDATED_NATUREZA = "BBBBBBBBBB";

    private static final String DEFAULT_SUMARIO = "AAAAAAAAAA";
    private static final String UPDATED_SUMARIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEMPO_JEJUM = 1;
    private static final Integer UPDATED_TEMPO_JEJUM = 2;

    private static final Integer DEFAULT_INTERVALO_MINIMO = 1;
    private static final Integer UPDATED_INTERVALO_MINIMO = 2;

    private static final String DEFAULT_UNIDADE_DE_TEMPO = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_DE_TEMPO = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALIDADE = 1;
    private static final Integer UPDATED_VALIDADE = 2;

    private static final Integer DEFAULT_AGENDAMENTO_MINIMO = 1;
    private static final Integer UPDATED_AGENDAMENTO_MINIMO = 2;

    private static final Integer DEFAULT_TEMPO_LIMITE_DA_SOLICITACAO = 1;
    private static final Integer UPDATED_TEMPO_LIMITE_DA_SOLICITACAO = 2;

    private static final String DEFAULT_UNIDADE_DE_TEMPO_DA_SOLICITACAO = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_DE_TEMPO_DA_SOLICITACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DE_AMOSTRAS = 1;
    private static final Integer UPDATED_NUMERO_DE_AMOSTRAS = 2;

    private static final Integer DEFAULT_NUMERO_DE_AMOSTRAS_PADRAO = 1;
    private static final Integer UPDATED_NUMERO_DE_AMOSTRAS_PADRAO = 2;

    private static final Integer DEFAULT_DIAS_LIMITE_DEFAULT = 1;
    private static final Integer UPDATED_DIAS_LIMITE_DEFAULT = 2;

    private static final Integer DEFAULT_TEMPO_LIMITE_DEFAULT = 1;
    private static final Integer UPDATED_TEMPO_LIMITE_DEFAULT = 2;

    private static final Integer DEFAULT_NUMERO_DE_AMOSTRAR_POR_INTERVALO = 1;
    private static final Integer UPDATED_NUMERO_DE_AMOSTRAR_POR_INTERVALO = 2;

    private static final Integer DEFAULT_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO = 1;
    private static final Integer UPDATED_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO = 2;

    private static final String DEFAULT_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PERMITE_SOLICITACAO_POS_ALTA = false;
    private static final Boolean UPDATED_PERMITE_SOLICITACAO_POS_ALTA = true;

    private static final Integer DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA = 1;
    private static final Integer UPDATED_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA = 2;

    private static final Integer DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS = 1;
    private static final Integer UPDATED_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS = 2;

    private static final String DEFAULT_CARTA_DE_COLETA = "AAAAAAAAAA";
    private static final String UPDATED_CARTA_DE_COLETA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LABORATORIA_TERCERIZADO = false;
    private static final Boolean UPDATED_LABORATORIA_TERCERIZADO = true;

    private static final Boolean DEFAULT_NAO_CANCELA_EXAMA_APOS_ALTA = false;
    private static final Boolean UPDATED_NAO_CANCELA_EXAMA_APOS_ALTA = true;

    @Autowired
    private MaterialDeExameRepository materialDeExameRepository;

    @Autowired
    private MaterialDeExameMapper materialDeExameMapper;

    @Autowired
    private MaterialDeExameService materialDeExameService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.MaterialDeExameSearchRepositoryMockConfiguration
     */
    @Autowired
    private MaterialDeExameSearchRepository mockMaterialDeExameSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialDeExameMockMvc;

    private MaterialDeExame materialDeExame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialDeExame createEntity(EntityManager em) {
        MaterialDeExame materialDeExame = new MaterialDeExame()
            .nome(DEFAULT_NOME)
            .ativo(DEFAULT_ATIVO)
            .npo(DEFAULT_NPO)
            .jejum(DEFAULT_JEJUM)
            .exigePreparo(DEFAULT_EXIGE_PREPARO)
            .exigeDieta(DEFAULT_EXIGE_DIETA)
            .informaNumeroDeColetas(DEFAULT_INFORMA_NUMERO_DE_COLETAS)
            .geraItemDeSolicitacao(DEFAULT_GERA_ITEM_DE_SOLICITACAO)
            .exigeIntervaloDeColeta(DEFAULT_EXIGE_INTERVALO_DE_COLETA)
            .exigeRegiaoAnatomica(DEFAULT_EXIGE_REGIAO_ANATOMICA)
            .ingestaoDeMedicamento(DEFAULT_INGESTAO_DE_MEDICAMENTO)
            .dependenteDeExame(DEFAULT_DEPENDENTE_DE_EXAME)
            .analisadoPelaCII(DEFAULT_ANALISADO_PELA_CII)
            .interesseDaCOMEDI(DEFAULT_INTERESSE_DA_COMEDI)
            .exigeImpressao(DEFAULT_EXIGE_IMPRESSAO)
            .apareceResultado(DEFAULT_APARECE_RESULTADO)
            .contaCelulas(DEFAULT_CONTA_CELULAS)
            .limiteDeSolicitacao(DEFAULT_LIMITE_DE_SOLICITACAO)
            .formaDeRespiracao(DEFAULT_FORMA_DE_RESPIRACAO)
            .automatico(DEFAULT_AUTOMATICO)
            .exigeDadosComplementares(DEFAULT_EXIGE_DADOS_COMPLEMENTARES)
            .natureza(DEFAULT_NATUREZA)
            .sumario(DEFAULT_SUMARIO)
            .tempoJejum(DEFAULT_TEMPO_JEJUM)
            .intervaloMinimo(DEFAULT_INTERVALO_MINIMO)
            .unidadeDeTempo(DEFAULT_UNIDADE_DE_TEMPO)
            .validade(DEFAULT_VALIDADE)
            .agendamentoMinimo(DEFAULT_AGENDAMENTO_MINIMO)
            .tempoLimiteDaSolicitacao(DEFAULT_TEMPO_LIMITE_DA_SOLICITACAO)
            .unidadeDeTempoDaSolicitacao(DEFAULT_UNIDADE_DE_TEMPO_DA_SOLICITACAO)
            .numeroDeAmostras(DEFAULT_NUMERO_DE_AMOSTRAS)
            .numeroDeAmostrasPadrao(DEFAULT_NUMERO_DE_AMOSTRAS_PADRAO)
            .diasLimiteDefault(DEFAULT_DIAS_LIMITE_DEFAULT)
            .tempoLimiteDefault(DEFAULT_TEMPO_LIMITE_DEFAULT)
            .numeroDeAmostrarPorIntervalo(DEFAULT_NUMERO_DE_AMOSTRAR_POR_INTERVALO)
            .tempoLimiteDeAmostraPorIntervalo(DEFAULT_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO)
            .unidadeLimiteDeTempoDoPeriodo(DEFAULT_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO)
            .permiteSolicitacaoPosAlta(DEFAULT_PERMITE_SOLICITACAO_POS_ALTA)
            .tempoPermitidoParaSolicitarPosAlta(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA)
            .tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS)
            .cartaDeColeta(DEFAULT_CARTA_DE_COLETA)
            .laboratoriaTercerizado(DEFAULT_LABORATORIA_TERCERIZADO)
            .naoCancelaExamaAposAlta(DEFAULT_NAO_CANCELA_EXAMA_APOS_ALTA);
        return materialDeExame;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialDeExame createUpdatedEntity(EntityManager em) {
        MaterialDeExame materialDeExame = new MaterialDeExame()
            .nome(UPDATED_NOME)
            .ativo(UPDATED_ATIVO)
            .npo(UPDATED_NPO)
            .jejum(UPDATED_JEJUM)
            .exigePreparo(UPDATED_EXIGE_PREPARO)
            .exigeDieta(UPDATED_EXIGE_DIETA)
            .informaNumeroDeColetas(UPDATED_INFORMA_NUMERO_DE_COLETAS)
            .geraItemDeSolicitacao(UPDATED_GERA_ITEM_DE_SOLICITACAO)
            .exigeIntervaloDeColeta(UPDATED_EXIGE_INTERVALO_DE_COLETA)
            .exigeRegiaoAnatomica(UPDATED_EXIGE_REGIAO_ANATOMICA)
            .ingestaoDeMedicamento(UPDATED_INGESTAO_DE_MEDICAMENTO)
            .dependenteDeExame(UPDATED_DEPENDENTE_DE_EXAME)
            .analisadoPelaCII(UPDATED_ANALISADO_PELA_CII)
            .interesseDaCOMEDI(UPDATED_INTERESSE_DA_COMEDI)
            .exigeImpressao(UPDATED_EXIGE_IMPRESSAO)
            .apareceResultado(UPDATED_APARECE_RESULTADO)
            .contaCelulas(UPDATED_CONTA_CELULAS)
            .limiteDeSolicitacao(UPDATED_LIMITE_DE_SOLICITACAO)
            .formaDeRespiracao(UPDATED_FORMA_DE_RESPIRACAO)
            .automatico(UPDATED_AUTOMATICO)
            .exigeDadosComplementares(UPDATED_EXIGE_DADOS_COMPLEMENTARES)
            .natureza(UPDATED_NATUREZA)
            .sumario(UPDATED_SUMARIO)
            .tempoJejum(UPDATED_TEMPO_JEJUM)
            .intervaloMinimo(UPDATED_INTERVALO_MINIMO)
            .unidadeDeTempo(UPDATED_UNIDADE_DE_TEMPO)
            .validade(UPDATED_VALIDADE)
            .agendamentoMinimo(UPDATED_AGENDAMENTO_MINIMO)
            .tempoLimiteDaSolicitacao(UPDATED_TEMPO_LIMITE_DA_SOLICITACAO)
            .unidadeDeTempoDaSolicitacao(UPDATED_UNIDADE_DE_TEMPO_DA_SOLICITACAO)
            .numeroDeAmostras(UPDATED_NUMERO_DE_AMOSTRAS)
            .numeroDeAmostrasPadrao(UPDATED_NUMERO_DE_AMOSTRAS_PADRAO)
            .diasLimiteDefault(UPDATED_DIAS_LIMITE_DEFAULT)
            .tempoLimiteDefault(UPDATED_TEMPO_LIMITE_DEFAULT)
            .numeroDeAmostrarPorIntervalo(UPDATED_NUMERO_DE_AMOSTRAR_POR_INTERVALO)
            .tempoLimiteDeAmostraPorIntervalo(UPDATED_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO)
            .unidadeLimiteDeTempoDoPeriodo(UPDATED_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO)
            .permiteSolicitacaoPosAlta(UPDATED_PERMITE_SOLICITACAO_POS_ALTA)
            .tempoPermitidoParaSolicitarPosAlta(UPDATED_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA)
            .tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras(UPDATED_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS)
            .cartaDeColeta(UPDATED_CARTA_DE_COLETA)
            .laboratoriaTercerizado(UPDATED_LABORATORIA_TERCERIZADO)
            .naoCancelaExamaAposAlta(UPDATED_NAO_CANCELA_EXAMA_APOS_ALTA);
        return materialDeExame;
    }

    @BeforeEach
    public void initTest() {
        materialDeExame = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialDeExame() throws Exception {
        int databaseSizeBeforeCreate = materialDeExameRepository.findAll().size();
        // Create the MaterialDeExame
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(materialDeExame);
        restMaterialDeExameMockMvc.perform(post("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isCreated());

        // Validate the MaterialDeExame in the database
        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialDeExame testMaterialDeExame = materialDeExameList.get(materialDeExameList.size() - 1);
        assertThat(testMaterialDeExame.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMaterialDeExame.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testMaterialDeExame.isNpo()).isEqualTo(DEFAULT_NPO);
        assertThat(testMaterialDeExame.isJejum()).isEqualTo(DEFAULT_JEJUM);
        assertThat(testMaterialDeExame.isExigePreparo()).isEqualTo(DEFAULT_EXIGE_PREPARO);
        assertThat(testMaterialDeExame.isExigeDieta()).isEqualTo(DEFAULT_EXIGE_DIETA);
        assertThat(testMaterialDeExame.isInformaNumeroDeColetas()).isEqualTo(DEFAULT_INFORMA_NUMERO_DE_COLETAS);
        assertThat(testMaterialDeExame.isGeraItemDeSolicitacao()).isEqualTo(DEFAULT_GERA_ITEM_DE_SOLICITACAO);
        assertThat(testMaterialDeExame.isExigeIntervaloDeColeta()).isEqualTo(DEFAULT_EXIGE_INTERVALO_DE_COLETA);
        assertThat(testMaterialDeExame.isExigeRegiaoAnatomica()).isEqualTo(DEFAULT_EXIGE_REGIAO_ANATOMICA);
        assertThat(testMaterialDeExame.isIngestaoDeMedicamento()).isEqualTo(DEFAULT_INGESTAO_DE_MEDICAMENTO);
        assertThat(testMaterialDeExame.isDependenteDeExame()).isEqualTo(DEFAULT_DEPENDENTE_DE_EXAME);
        assertThat(testMaterialDeExame.isAnalisadoPelaCII()).isEqualTo(DEFAULT_ANALISADO_PELA_CII);
        assertThat(testMaterialDeExame.isInteresseDaCOMEDI()).isEqualTo(DEFAULT_INTERESSE_DA_COMEDI);
        assertThat(testMaterialDeExame.isExigeImpressao()).isEqualTo(DEFAULT_EXIGE_IMPRESSAO);
        assertThat(testMaterialDeExame.isApareceResultado()).isEqualTo(DEFAULT_APARECE_RESULTADO);
        assertThat(testMaterialDeExame.isContaCelulas()).isEqualTo(DEFAULT_CONTA_CELULAS);
        assertThat(testMaterialDeExame.isLimiteDeSolicitacao()).isEqualTo(DEFAULT_LIMITE_DE_SOLICITACAO);
        assertThat(testMaterialDeExame.isFormaDeRespiracao()).isEqualTo(DEFAULT_FORMA_DE_RESPIRACAO);
        assertThat(testMaterialDeExame.isAutomatico()).isEqualTo(DEFAULT_AUTOMATICO);
        assertThat(testMaterialDeExame.isExigeDadosComplementares()).isEqualTo(DEFAULT_EXIGE_DADOS_COMPLEMENTARES);
        assertThat(testMaterialDeExame.getNatureza()).isEqualTo(DEFAULT_NATUREZA);
        assertThat(testMaterialDeExame.getSumario()).isEqualTo(DEFAULT_SUMARIO);
        assertThat(testMaterialDeExame.getTempoJejum()).isEqualTo(DEFAULT_TEMPO_JEJUM);
        assertThat(testMaterialDeExame.getIntervaloMinimo()).isEqualTo(DEFAULT_INTERVALO_MINIMO);
        assertThat(testMaterialDeExame.getUnidadeDeTempo()).isEqualTo(DEFAULT_UNIDADE_DE_TEMPO);
        assertThat(testMaterialDeExame.getValidade()).isEqualTo(DEFAULT_VALIDADE);
        assertThat(testMaterialDeExame.getAgendamentoMinimo()).isEqualTo(DEFAULT_AGENDAMENTO_MINIMO);
        assertThat(testMaterialDeExame.getTempoLimiteDaSolicitacao()).isEqualTo(DEFAULT_TEMPO_LIMITE_DA_SOLICITACAO);
        assertThat(testMaterialDeExame.getUnidadeDeTempoDaSolicitacao()).isEqualTo(DEFAULT_UNIDADE_DE_TEMPO_DA_SOLICITACAO);
        assertThat(testMaterialDeExame.getNumeroDeAmostras()).isEqualTo(DEFAULT_NUMERO_DE_AMOSTRAS);
        assertThat(testMaterialDeExame.getNumeroDeAmostrasPadrao()).isEqualTo(DEFAULT_NUMERO_DE_AMOSTRAS_PADRAO);
        assertThat(testMaterialDeExame.getDiasLimiteDefault()).isEqualTo(DEFAULT_DIAS_LIMITE_DEFAULT);
        assertThat(testMaterialDeExame.getTempoLimiteDefault()).isEqualTo(DEFAULT_TEMPO_LIMITE_DEFAULT);
        assertThat(testMaterialDeExame.getNumeroDeAmostrarPorIntervalo()).isEqualTo(DEFAULT_NUMERO_DE_AMOSTRAR_POR_INTERVALO);
        assertThat(testMaterialDeExame.getTempoLimiteDeAmostraPorIntervalo()).isEqualTo(DEFAULT_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO);
        assertThat(testMaterialDeExame.getUnidadeLimiteDeTempoDoPeriodo()).isEqualTo(DEFAULT_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO);
        assertThat(testMaterialDeExame.isPermiteSolicitacaoPosAlta()).isEqualTo(DEFAULT_PERMITE_SOLICITACAO_POS_ALTA);
        assertThat(testMaterialDeExame.getTempoPermitidoParaSolicitarPosAlta()).isEqualTo(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA);
        assertThat(testMaterialDeExame.getTempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras()).isEqualTo(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS);
        assertThat(testMaterialDeExame.getCartaDeColeta()).isEqualTo(DEFAULT_CARTA_DE_COLETA);
        assertThat(testMaterialDeExame.isLaboratoriaTercerizado()).isEqualTo(DEFAULT_LABORATORIA_TERCERIZADO);
        assertThat(testMaterialDeExame.isNaoCancelaExamaAposAlta()).isEqualTo(DEFAULT_NAO_CANCELA_EXAMA_APOS_ALTA);

        // Validate the MaterialDeExame in Elasticsearch
        verify(mockMaterialDeExameSearchRepository, times(1)).save(testMaterialDeExame);
    }

    @Test
    @Transactional
    public void createMaterialDeExameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialDeExameRepository.findAll().size();

        // Create the MaterialDeExame with an existing ID
        materialDeExame.setId(1L);
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(materialDeExame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialDeExameMockMvc.perform(post("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialDeExame in the database
        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeCreate);

        // Validate the MaterialDeExame in Elasticsearch
        verify(mockMaterialDeExameSearchRepository, times(0)).save(materialDeExame);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialDeExameRepository.findAll().size();
        // set the field null
        materialDeExame.setNome(null);

        // Create the MaterialDeExame, which fails.
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(materialDeExame);


        restMaterialDeExameMockMvc.perform(post("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialDeExameRepository.findAll().size();
        // set the field null
        materialDeExame.setAtivo(null);

        // Create the MaterialDeExame, which fails.
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(materialDeExame);


        restMaterialDeExameMockMvc.perform(post("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaturezaIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialDeExameRepository.findAll().size();
        // set the field null
        materialDeExame.setNatureza(null);

        // Create the MaterialDeExame, which fails.
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(materialDeExame);


        restMaterialDeExameMockMvc.perform(post("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSumarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialDeExameRepository.findAll().size();
        // set the field null
        materialDeExame.setSumario(null);

        // Create the MaterialDeExame, which fails.
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(materialDeExame);


        restMaterialDeExameMockMvc.perform(post("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPermiteSolicitacaoPosAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialDeExameRepository.findAll().size();
        // set the field null
        materialDeExame.setPermiteSolicitacaoPosAlta(null);

        // Create the MaterialDeExame, which fails.
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(materialDeExame);


        restMaterialDeExameMockMvc.perform(post("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterialDeExames() throws Exception {
        // Initialize the database
        materialDeExameRepository.saveAndFlush(materialDeExame);

        // Get all the materialDeExameList
        restMaterialDeExameMockMvc.perform(get("/api/material-de-exames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialDeExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].npo").value(hasItem(DEFAULT_NPO.booleanValue())))
            .andExpect(jsonPath("$.[*].jejum").value(hasItem(DEFAULT_JEJUM.booleanValue())))
            .andExpect(jsonPath("$.[*].exigePreparo").value(hasItem(DEFAULT_EXIGE_PREPARO.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeDieta").value(hasItem(DEFAULT_EXIGE_DIETA.booleanValue())))
            .andExpect(jsonPath("$.[*].informaNumeroDeColetas").value(hasItem(DEFAULT_INFORMA_NUMERO_DE_COLETAS.booleanValue())))
            .andExpect(jsonPath("$.[*].geraItemDeSolicitacao").value(hasItem(DEFAULT_GERA_ITEM_DE_SOLICITACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeIntervaloDeColeta").value(hasItem(DEFAULT_EXIGE_INTERVALO_DE_COLETA.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeRegiaoAnatomica").value(hasItem(DEFAULT_EXIGE_REGIAO_ANATOMICA.booleanValue())))
            .andExpect(jsonPath("$.[*].ingestaoDeMedicamento").value(hasItem(DEFAULT_INGESTAO_DE_MEDICAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].dependenteDeExame").value(hasItem(DEFAULT_DEPENDENTE_DE_EXAME.booleanValue())))
            .andExpect(jsonPath("$.[*].analisadoPelaCII").value(hasItem(DEFAULT_ANALISADO_PELA_CII.booleanValue())))
            .andExpect(jsonPath("$.[*].interesseDaCOMEDI").value(hasItem(DEFAULT_INTERESSE_DA_COMEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeImpressao").value(hasItem(DEFAULT_EXIGE_IMPRESSAO.booleanValue())))
            .andExpect(jsonPath("$.[*].apareceResultado").value(hasItem(DEFAULT_APARECE_RESULTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].contaCelulas").value(hasItem(DEFAULT_CONTA_CELULAS.booleanValue())))
            .andExpect(jsonPath("$.[*].limiteDeSolicitacao").value(hasItem(DEFAULT_LIMITE_DE_SOLICITACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].formaDeRespiracao").value(hasItem(DEFAULT_FORMA_DE_RESPIRACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].automatico").value(hasItem(DEFAULT_AUTOMATICO.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeDadosComplementares").value(hasItem(DEFAULT_EXIGE_DADOS_COMPLEMENTARES.booleanValue())))
            .andExpect(jsonPath("$.[*].natureza").value(hasItem(DEFAULT_NATUREZA)))
            .andExpect(jsonPath("$.[*].sumario").value(hasItem(DEFAULT_SUMARIO)))
            .andExpect(jsonPath("$.[*].tempoJejum").value(hasItem(DEFAULT_TEMPO_JEJUM)))
            .andExpect(jsonPath("$.[*].intervaloMinimo").value(hasItem(DEFAULT_INTERVALO_MINIMO)))
            .andExpect(jsonPath("$.[*].unidadeDeTempo").value(hasItem(DEFAULT_UNIDADE_DE_TEMPO)))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE)))
            .andExpect(jsonPath("$.[*].agendamentoMinimo").value(hasItem(DEFAULT_AGENDAMENTO_MINIMO)))
            .andExpect(jsonPath("$.[*].tempoLimiteDaSolicitacao").value(hasItem(DEFAULT_TEMPO_LIMITE_DA_SOLICITACAO)))
            .andExpect(jsonPath("$.[*].unidadeDeTempoDaSolicitacao").value(hasItem(DEFAULT_UNIDADE_DE_TEMPO_DA_SOLICITACAO)))
            .andExpect(jsonPath("$.[*].numeroDeAmostras").value(hasItem(DEFAULT_NUMERO_DE_AMOSTRAS)))
            .andExpect(jsonPath("$.[*].numeroDeAmostrasPadrao").value(hasItem(DEFAULT_NUMERO_DE_AMOSTRAS_PADRAO)))
            .andExpect(jsonPath("$.[*].diasLimiteDefault").value(hasItem(DEFAULT_DIAS_LIMITE_DEFAULT)))
            .andExpect(jsonPath("$.[*].tempoLimiteDefault").value(hasItem(DEFAULT_TEMPO_LIMITE_DEFAULT)))
            .andExpect(jsonPath("$.[*].numeroDeAmostrarPorIntervalo").value(hasItem(DEFAULT_NUMERO_DE_AMOSTRAR_POR_INTERVALO)))
            .andExpect(jsonPath("$.[*].tempoLimiteDeAmostraPorIntervalo").value(hasItem(DEFAULT_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO)))
            .andExpect(jsonPath("$.[*].unidadeLimiteDeTempoDoPeriodo").value(hasItem(DEFAULT_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO)))
            .andExpect(jsonPath("$.[*].permiteSolicitacaoPosAlta").value(hasItem(DEFAULT_PERMITE_SOLICITACAO_POS_ALTA.booleanValue())))
            .andExpect(jsonPath("$.[*].tempoPermitidoParaSolicitarPosAlta").value(hasItem(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA)))
            .andExpect(jsonPath("$.[*].tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras").value(hasItem(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS)))
            .andExpect(jsonPath("$.[*].cartaDeColeta").value(hasItem(DEFAULT_CARTA_DE_COLETA)))
            .andExpect(jsonPath("$.[*].laboratoriaTercerizado").value(hasItem(DEFAULT_LABORATORIA_TERCERIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].naoCancelaExamaAposAlta").value(hasItem(DEFAULT_NAO_CANCELA_EXAMA_APOS_ALTA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMaterialDeExame() throws Exception {
        // Initialize the database
        materialDeExameRepository.saveAndFlush(materialDeExame);

        // Get the materialDeExame
        restMaterialDeExameMockMvc.perform(get("/api/material-de-exames/{id}", materialDeExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialDeExame.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.npo").value(DEFAULT_NPO.booleanValue()))
            .andExpect(jsonPath("$.jejum").value(DEFAULT_JEJUM.booleanValue()))
            .andExpect(jsonPath("$.exigePreparo").value(DEFAULT_EXIGE_PREPARO.booleanValue()))
            .andExpect(jsonPath("$.exigeDieta").value(DEFAULT_EXIGE_DIETA.booleanValue()))
            .andExpect(jsonPath("$.informaNumeroDeColetas").value(DEFAULT_INFORMA_NUMERO_DE_COLETAS.booleanValue()))
            .andExpect(jsonPath("$.geraItemDeSolicitacao").value(DEFAULT_GERA_ITEM_DE_SOLICITACAO.booleanValue()))
            .andExpect(jsonPath("$.exigeIntervaloDeColeta").value(DEFAULT_EXIGE_INTERVALO_DE_COLETA.booleanValue()))
            .andExpect(jsonPath("$.exigeRegiaoAnatomica").value(DEFAULT_EXIGE_REGIAO_ANATOMICA.booleanValue()))
            .andExpect(jsonPath("$.ingestaoDeMedicamento").value(DEFAULT_INGESTAO_DE_MEDICAMENTO.booleanValue()))
            .andExpect(jsonPath("$.dependenteDeExame").value(DEFAULT_DEPENDENTE_DE_EXAME.booleanValue()))
            .andExpect(jsonPath("$.analisadoPelaCII").value(DEFAULT_ANALISADO_PELA_CII.booleanValue()))
            .andExpect(jsonPath("$.interesseDaCOMEDI").value(DEFAULT_INTERESSE_DA_COMEDI.booleanValue()))
            .andExpect(jsonPath("$.exigeImpressao").value(DEFAULT_EXIGE_IMPRESSAO.booleanValue()))
            .andExpect(jsonPath("$.apareceResultado").value(DEFAULT_APARECE_RESULTADO.booleanValue()))
            .andExpect(jsonPath("$.contaCelulas").value(DEFAULT_CONTA_CELULAS.booleanValue()))
            .andExpect(jsonPath("$.limiteDeSolicitacao").value(DEFAULT_LIMITE_DE_SOLICITACAO.booleanValue()))
            .andExpect(jsonPath("$.formaDeRespiracao").value(DEFAULT_FORMA_DE_RESPIRACAO.booleanValue()))
            .andExpect(jsonPath("$.automatico").value(DEFAULT_AUTOMATICO.booleanValue()))
            .andExpect(jsonPath("$.exigeDadosComplementares").value(DEFAULT_EXIGE_DADOS_COMPLEMENTARES.booleanValue()))
            .andExpect(jsonPath("$.natureza").value(DEFAULT_NATUREZA))
            .andExpect(jsonPath("$.sumario").value(DEFAULT_SUMARIO))
            .andExpect(jsonPath("$.tempoJejum").value(DEFAULT_TEMPO_JEJUM))
            .andExpect(jsonPath("$.intervaloMinimo").value(DEFAULT_INTERVALO_MINIMO))
            .andExpect(jsonPath("$.unidadeDeTempo").value(DEFAULT_UNIDADE_DE_TEMPO))
            .andExpect(jsonPath("$.validade").value(DEFAULT_VALIDADE))
            .andExpect(jsonPath("$.agendamentoMinimo").value(DEFAULT_AGENDAMENTO_MINIMO))
            .andExpect(jsonPath("$.tempoLimiteDaSolicitacao").value(DEFAULT_TEMPO_LIMITE_DA_SOLICITACAO))
            .andExpect(jsonPath("$.unidadeDeTempoDaSolicitacao").value(DEFAULT_UNIDADE_DE_TEMPO_DA_SOLICITACAO))
            .andExpect(jsonPath("$.numeroDeAmostras").value(DEFAULT_NUMERO_DE_AMOSTRAS))
            .andExpect(jsonPath("$.numeroDeAmostrasPadrao").value(DEFAULT_NUMERO_DE_AMOSTRAS_PADRAO))
            .andExpect(jsonPath("$.diasLimiteDefault").value(DEFAULT_DIAS_LIMITE_DEFAULT))
            .andExpect(jsonPath("$.tempoLimiteDefault").value(DEFAULT_TEMPO_LIMITE_DEFAULT))
            .andExpect(jsonPath("$.numeroDeAmostrarPorIntervalo").value(DEFAULT_NUMERO_DE_AMOSTRAR_POR_INTERVALO))
            .andExpect(jsonPath("$.tempoLimiteDeAmostraPorIntervalo").value(DEFAULT_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO))
            .andExpect(jsonPath("$.unidadeLimiteDeTempoDoPeriodo").value(DEFAULT_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO))
            .andExpect(jsonPath("$.permiteSolicitacaoPosAlta").value(DEFAULT_PERMITE_SOLICITACAO_POS_ALTA.booleanValue()))
            .andExpect(jsonPath("$.tempoPermitidoParaSolicitarPosAlta").value(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA))
            .andExpect(jsonPath("$.tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras").value(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS))
            .andExpect(jsonPath("$.cartaDeColeta").value(DEFAULT_CARTA_DE_COLETA))
            .andExpect(jsonPath("$.laboratoriaTercerizado").value(DEFAULT_LABORATORIA_TERCERIZADO.booleanValue()))
            .andExpect(jsonPath("$.naoCancelaExamaAposAlta").value(DEFAULT_NAO_CANCELA_EXAMA_APOS_ALTA.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMaterialDeExame() throws Exception {
        // Get the materialDeExame
        restMaterialDeExameMockMvc.perform(get("/api/material-de-exames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialDeExame() throws Exception {
        // Initialize the database
        materialDeExameRepository.saveAndFlush(materialDeExame);

        int databaseSizeBeforeUpdate = materialDeExameRepository.findAll().size();

        // Update the materialDeExame
        MaterialDeExame updatedMaterialDeExame = materialDeExameRepository.findById(materialDeExame.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialDeExame are not directly saved in db
        em.detach(updatedMaterialDeExame);
        updatedMaterialDeExame
            .nome(UPDATED_NOME)
            .ativo(UPDATED_ATIVO)
            .npo(UPDATED_NPO)
            .jejum(UPDATED_JEJUM)
            .exigePreparo(UPDATED_EXIGE_PREPARO)
            .exigeDieta(UPDATED_EXIGE_DIETA)
            .informaNumeroDeColetas(UPDATED_INFORMA_NUMERO_DE_COLETAS)
            .geraItemDeSolicitacao(UPDATED_GERA_ITEM_DE_SOLICITACAO)
            .exigeIntervaloDeColeta(UPDATED_EXIGE_INTERVALO_DE_COLETA)
            .exigeRegiaoAnatomica(UPDATED_EXIGE_REGIAO_ANATOMICA)
            .ingestaoDeMedicamento(UPDATED_INGESTAO_DE_MEDICAMENTO)
            .dependenteDeExame(UPDATED_DEPENDENTE_DE_EXAME)
            .analisadoPelaCII(UPDATED_ANALISADO_PELA_CII)
            .interesseDaCOMEDI(UPDATED_INTERESSE_DA_COMEDI)
            .exigeImpressao(UPDATED_EXIGE_IMPRESSAO)
            .apareceResultado(UPDATED_APARECE_RESULTADO)
            .contaCelulas(UPDATED_CONTA_CELULAS)
            .limiteDeSolicitacao(UPDATED_LIMITE_DE_SOLICITACAO)
            .formaDeRespiracao(UPDATED_FORMA_DE_RESPIRACAO)
            .automatico(UPDATED_AUTOMATICO)
            .exigeDadosComplementares(UPDATED_EXIGE_DADOS_COMPLEMENTARES)
            .natureza(UPDATED_NATUREZA)
            .sumario(UPDATED_SUMARIO)
            .tempoJejum(UPDATED_TEMPO_JEJUM)
            .intervaloMinimo(UPDATED_INTERVALO_MINIMO)
            .unidadeDeTempo(UPDATED_UNIDADE_DE_TEMPO)
            .validade(UPDATED_VALIDADE)
            .agendamentoMinimo(UPDATED_AGENDAMENTO_MINIMO)
            .tempoLimiteDaSolicitacao(UPDATED_TEMPO_LIMITE_DA_SOLICITACAO)
            .unidadeDeTempoDaSolicitacao(UPDATED_UNIDADE_DE_TEMPO_DA_SOLICITACAO)
            .numeroDeAmostras(UPDATED_NUMERO_DE_AMOSTRAS)
            .numeroDeAmostrasPadrao(UPDATED_NUMERO_DE_AMOSTRAS_PADRAO)
            .diasLimiteDefault(UPDATED_DIAS_LIMITE_DEFAULT)
            .tempoLimiteDefault(UPDATED_TEMPO_LIMITE_DEFAULT)
            .numeroDeAmostrarPorIntervalo(UPDATED_NUMERO_DE_AMOSTRAR_POR_INTERVALO)
            .tempoLimiteDeAmostraPorIntervalo(UPDATED_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO)
            .unidadeLimiteDeTempoDoPeriodo(UPDATED_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO)
            .permiteSolicitacaoPosAlta(UPDATED_PERMITE_SOLICITACAO_POS_ALTA)
            .tempoPermitidoParaSolicitarPosAlta(UPDATED_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA)
            .tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras(UPDATED_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS)
            .cartaDeColeta(UPDATED_CARTA_DE_COLETA)
            .laboratoriaTercerizado(UPDATED_LABORATORIA_TERCERIZADO)
            .naoCancelaExamaAposAlta(UPDATED_NAO_CANCELA_EXAMA_APOS_ALTA);
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(updatedMaterialDeExame);

        restMaterialDeExameMockMvc.perform(put("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isOk());

        // Validate the MaterialDeExame in the database
        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeUpdate);
        MaterialDeExame testMaterialDeExame = materialDeExameList.get(materialDeExameList.size() - 1);
        assertThat(testMaterialDeExame.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMaterialDeExame.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testMaterialDeExame.isNpo()).isEqualTo(UPDATED_NPO);
        assertThat(testMaterialDeExame.isJejum()).isEqualTo(UPDATED_JEJUM);
        assertThat(testMaterialDeExame.isExigePreparo()).isEqualTo(UPDATED_EXIGE_PREPARO);
        assertThat(testMaterialDeExame.isExigeDieta()).isEqualTo(UPDATED_EXIGE_DIETA);
        assertThat(testMaterialDeExame.isInformaNumeroDeColetas()).isEqualTo(UPDATED_INFORMA_NUMERO_DE_COLETAS);
        assertThat(testMaterialDeExame.isGeraItemDeSolicitacao()).isEqualTo(UPDATED_GERA_ITEM_DE_SOLICITACAO);
        assertThat(testMaterialDeExame.isExigeIntervaloDeColeta()).isEqualTo(UPDATED_EXIGE_INTERVALO_DE_COLETA);
        assertThat(testMaterialDeExame.isExigeRegiaoAnatomica()).isEqualTo(UPDATED_EXIGE_REGIAO_ANATOMICA);
        assertThat(testMaterialDeExame.isIngestaoDeMedicamento()).isEqualTo(UPDATED_INGESTAO_DE_MEDICAMENTO);
        assertThat(testMaterialDeExame.isDependenteDeExame()).isEqualTo(UPDATED_DEPENDENTE_DE_EXAME);
        assertThat(testMaterialDeExame.isAnalisadoPelaCII()).isEqualTo(UPDATED_ANALISADO_PELA_CII);
        assertThat(testMaterialDeExame.isInteresseDaCOMEDI()).isEqualTo(UPDATED_INTERESSE_DA_COMEDI);
        assertThat(testMaterialDeExame.isExigeImpressao()).isEqualTo(UPDATED_EXIGE_IMPRESSAO);
        assertThat(testMaterialDeExame.isApareceResultado()).isEqualTo(UPDATED_APARECE_RESULTADO);
        assertThat(testMaterialDeExame.isContaCelulas()).isEqualTo(UPDATED_CONTA_CELULAS);
        assertThat(testMaterialDeExame.isLimiteDeSolicitacao()).isEqualTo(UPDATED_LIMITE_DE_SOLICITACAO);
        assertThat(testMaterialDeExame.isFormaDeRespiracao()).isEqualTo(UPDATED_FORMA_DE_RESPIRACAO);
        assertThat(testMaterialDeExame.isAutomatico()).isEqualTo(UPDATED_AUTOMATICO);
        assertThat(testMaterialDeExame.isExigeDadosComplementares()).isEqualTo(UPDATED_EXIGE_DADOS_COMPLEMENTARES);
        assertThat(testMaterialDeExame.getNatureza()).isEqualTo(UPDATED_NATUREZA);
        assertThat(testMaterialDeExame.getSumario()).isEqualTo(UPDATED_SUMARIO);
        assertThat(testMaterialDeExame.getTempoJejum()).isEqualTo(UPDATED_TEMPO_JEJUM);
        assertThat(testMaterialDeExame.getIntervaloMinimo()).isEqualTo(UPDATED_INTERVALO_MINIMO);
        assertThat(testMaterialDeExame.getUnidadeDeTempo()).isEqualTo(UPDATED_UNIDADE_DE_TEMPO);
        assertThat(testMaterialDeExame.getValidade()).isEqualTo(UPDATED_VALIDADE);
        assertThat(testMaterialDeExame.getAgendamentoMinimo()).isEqualTo(UPDATED_AGENDAMENTO_MINIMO);
        assertThat(testMaterialDeExame.getTempoLimiteDaSolicitacao()).isEqualTo(UPDATED_TEMPO_LIMITE_DA_SOLICITACAO);
        assertThat(testMaterialDeExame.getUnidadeDeTempoDaSolicitacao()).isEqualTo(UPDATED_UNIDADE_DE_TEMPO_DA_SOLICITACAO);
        assertThat(testMaterialDeExame.getNumeroDeAmostras()).isEqualTo(UPDATED_NUMERO_DE_AMOSTRAS);
        assertThat(testMaterialDeExame.getNumeroDeAmostrasPadrao()).isEqualTo(UPDATED_NUMERO_DE_AMOSTRAS_PADRAO);
        assertThat(testMaterialDeExame.getDiasLimiteDefault()).isEqualTo(UPDATED_DIAS_LIMITE_DEFAULT);
        assertThat(testMaterialDeExame.getTempoLimiteDefault()).isEqualTo(UPDATED_TEMPO_LIMITE_DEFAULT);
        assertThat(testMaterialDeExame.getNumeroDeAmostrarPorIntervalo()).isEqualTo(UPDATED_NUMERO_DE_AMOSTRAR_POR_INTERVALO);
        assertThat(testMaterialDeExame.getTempoLimiteDeAmostraPorIntervalo()).isEqualTo(UPDATED_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO);
        assertThat(testMaterialDeExame.getUnidadeLimiteDeTempoDoPeriodo()).isEqualTo(UPDATED_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO);
        assertThat(testMaterialDeExame.isPermiteSolicitacaoPosAlta()).isEqualTo(UPDATED_PERMITE_SOLICITACAO_POS_ALTA);
        assertThat(testMaterialDeExame.getTempoPermitidoParaSolicitarPosAlta()).isEqualTo(UPDATED_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA);
        assertThat(testMaterialDeExame.getTempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras()).isEqualTo(UPDATED_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS);
        assertThat(testMaterialDeExame.getCartaDeColeta()).isEqualTo(UPDATED_CARTA_DE_COLETA);
        assertThat(testMaterialDeExame.isLaboratoriaTercerizado()).isEqualTo(UPDATED_LABORATORIA_TERCERIZADO);
        assertThat(testMaterialDeExame.isNaoCancelaExamaAposAlta()).isEqualTo(UPDATED_NAO_CANCELA_EXAMA_APOS_ALTA);

        // Validate the MaterialDeExame in Elasticsearch
        verify(mockMaterialDeExameSearchRepository, times(1)).save(testMaterialDeExame);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialDeExame() throws Exception {
        int databaseSizeBeforeUpdate = materialDeExameRepository.findAll().size();

        // Create the MaterialDeExame
        MaterialDeExameDTO materialDeExameDTO = materialDeExameMapper.toDto(materialDeExame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialDeExameMockMvc.perform(put("/api/material-de-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialDeExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialDeExame in the database
        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MaterialDeExame in Elasticsearch
        verify(mockMaterialDeExameSearchRepository, times(0)).save(materialDeExame);
    }

    @Test
    @Transactional
    public void deleteMaterialDeExame() throws Exception {
        // Initialize the database
        materialDeExameRepository.saveAndFlush(materialDeExame);

        int databaseSizeBeforeDelete = materialDeExameRepository.findAll().size();

        // Delete the materialDeExame
        restMaterialDeExameMockMvc.perform(delete("/api/material-de-exames/{id}", materialDeExame.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialDeExame> materialDeExameList = materialDeExameRepository.findAll();
        assertThat(materialDeExameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MaterialDeExame in Elasticsearch
        verify(mockMaterialDeExameSearchRepository, times(1)).deleteById(materialDeExame.getId());
    }

    @Test
    @Transactional
    public void searchMaterialDeExame() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        materialDeExameRepository.saveAndFlush(materialDeExame);
        when(mockMaterialDeExameSearchRepository.search(queryStringQuery("id:" + materialDeExame.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(materialDeExame), PageRequest.of(0, 1), 1));

        // Search the materialDeExame
        restMaterialDeExameMockMvc.perform(get("/api/_search/material-de-exames?query=id:" + materialDeExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialDeExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].npo").value(hasItem(DEFAULT_NPO.booleanValue())))
            .andExpect(jsonPath("$.[*].jejum").value(hasItem(DEFAULT_JEJUM.booleanValue())))
            .andExpect(jsonPath("$.[*].exigePreparo").value(hasItem(DEFAULT_EXIGE_PREPARO.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeDieta").value(hasItem(DEFAULT_EXIGE_DIETA.booleanValue())))
            .andExpect(jsonPath("$.[*].informaNumeroDeColetas").value(hasItem(DEFAULT_INFORMA_NUMERO_DE_COLETAS.booleanValue())))
            .andExpect(jsonPath("$.[*].geraItemDeSolicitacao").value(hasItem(DEFAULT_GERA_ITEM_DE_SOLICITACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeIntervaloDeColeta").value(hasItem(DEFAULT_EXIGE_INTERVALO_DE_COLETA.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeRegiaoAnatomica").value(hasItem(DEFAULT_EXIGE_REGIAO_ANATOMICA.booleanValue())))
            .andExpect(jsonPath("$.[*].ingestaoDeMedicamento").value(hasItem(DEFAULT_INGESTAO_DE_MEDICAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].dependenteDeExame").value(hasItem(DEFAULT_DEPENDENTE_DE_EXAME.booleanValue())))
            .andExpect(jsonPath("$.[*].analisadoPelaCII").value(hasItem(DEFAULT_ANALISADO_PELA_CII.booleanValue())))
            .andExpect(jsonPath("$.[*].interesseDaCOMEDI").value(hasItem(DEFAULT_INTERESSE_DA_COMEDI.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeImpressao").value(hasItem(DEFAULT_EXIGE_IMPRESSAO.booleanValue())))
            .andExpect(jsonPath("$.[*].apareceResultado").value(hasItem(DEFAULT_APARECE_RESULTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].contaCelulas").value(hasItem(DEFAULT_CONTA_CELULAS.booleanValue())))
            .andExpect(jsonPath("$.[*].limiteDeSolicitacao").value(hasItem(DEFAULT_LIMITE_DE_SOLICITACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].formaDeRespiracao").value(hasItem(DEFAULT_FORMA_DE_RESPIRACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].automatico").value(hasItem(DEFAULT_AUTOMATICO.booleanValue())))
            .andExpect(jsonPath("$.[*].exigeDadosComplementares").value(hasItem(DEFAULT_EXIGE_DADOS_COMPLEMENTARES.booleanValue())))
            .andExpect(jsonPath("$.[*].natureza").value(hasItem(DEFAULT_NATUREZA)))
            .andExpect(jsonPath("$.[*].sumario").value(hasItem(DEFAULT_SUMARIO)))
            .andExpect(jsonPath("$.[*].tempoJejum").value(hasItem(DEFAULT_TEMPO_JEJUM)))
            .andExpect(jsonPath("$.[*].intervaloMinimo").value(hasItem(DEFAULT_INTERVALO_MINIMO)))
            .andExpect(jsonPath("$.[*].unidadeDeTempo").value(hasItem(DEFAULT_UNIDADE_DE_TEMPO)))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE)))
            .andExpect(jsonPath("$.[*].agendamentoMinimo").value(hasItem(DEFAULT_AGENDAMENTO_MINIMO)))
            .andExpect(jsonPath("$.[*].tempoLimiteDaSolicitacao").value(hasItem(DEFAULT_TEMPO_LIMITE_DA_SOLICITACAO)))
            .andExpect(jsonPath("$.[*].unidadeDeTempoDaSolicitacao").value(hasItem(DEFAULT_UNIDADE_DE_TEMPO_DA_SOLICITACAO)))
            .andExpect(jsonPath("$.[*].numeroDeAmostras").value(hasItem(DEFAULT_NUMERO_DE_AMOSTRAS)))
            .andExpect(jsonPath("$.[*].numeroDeAmostrasPadrao").value(hasItem(DEFAULT_NUMERO_DE_AMOSTRAS_PADRAO)))
            .andExpect(jsonPath("$.[*].diasLimiteDefault").value(hasItem(DEFAULT_DIAS_LIMITE_DEFAULT)))
            .andExpect(jsonPath("$.[*].tempoLimiteDefault").value(hasItem(DEFAULT_TEMPO_LIMITE_DEFAULT)))
            .andExpect(jsonPath("$.[*].numeroDeAmostrarPorIntervalo").value(hasItem(DEFAULT_NUMERO_DE_AMOSTRAR_POR_INTERVALO)))
            .andExpect(jsonPath("$.[*].tempoLimiteDeAmostraPorIntervalo").value(hasItem(DEFAULT_TEMPO_LIMITE_DE_AMOSTRA_POR_INTERVALO)))
            .andExpect(jsonPath("$.[*].unidadeLimiteDeTempoDoPeriodo").value(hasItem(DEFAULT_UNIDADE_LIMITE_DE_TEMPO_DO_PERIODO)))
            .andExpect(jsonPath("$.[*].permiteSolicitacaoPosAlta").value(hasItem(DEFAULT_PERMITE_SOLICITACAO_POS_ALTA.booleanValue())))
            .andExpect(jsonPath("$.[*].tempoPermitidoParaSolicitarPosAlta").value(hasItem(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA)))
            .andExpect(jsonPath("$.[*].tempoPermitidoParaSolicitarPosAltaPelasAreasExecutoras").value(hasItem(DEFAULT_TEMPO_PERMITIDO_PARA_SOLICITAR_POS_ALTA_PELAS_AREAS_EXECUTORAS)))
            .andExpect(jsonPath("$.[*].cartaDeColeta").value(hasItem(DEFAULT_CARTA_DE_COLETA)))
            .andExpect(jsonPath("$.[*].laboratoriaTercerizado").value(hasItem(DEFAULT_LABORATORIA_TERCERIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].naoCancelaExamaAposAlta").value(hasItem(DEFAULT_NAO_CANCELA_EXAMA_APOS_ALTA.booleanValue())));
    }
}
