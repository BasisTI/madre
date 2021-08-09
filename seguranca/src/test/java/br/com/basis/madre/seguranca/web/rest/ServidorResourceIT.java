package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Servidor;
import br.com.basis.madre.seguranca.repository.ServidorRepository;
import br.com.basis.madre.seguranca.repository.search.ServidorSearchRepository;
import br.com.basis.madre.seguranca.service.ServidorService;
import br.com.basis.madre.seguranca.service.dto.ServidorDTO;
import br.com.basis.madre.seguranca.service.mapper.ServidorMapper;

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

import br.com.basis.madre.seguranca.domain.enumeration.SituacaoDoServidor;
import br.com.basis.madre.seguranca.domain.enumeration.TipoDeRemuneracao;
/**
 * Integration tests for the {@link ServidorResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServidorResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULA = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_STARH = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_STARH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INICIO_DO_VINCULO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO_DO_VINCULO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIM_DO_VINCULO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIM_DO_VINCULO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_SITUACAO = false;
    private static final Boolean UPDATED_SITUACAO = true;

    private static final SituacaoDoServidor DEFAULT_SITUACAO_DO_SERVIDOR = SituacaoDoServidor.RESIDENTE;
    private static final SituacaoDoServidor UPDATED_SITUACAO_DO_SERVIDOR = SituacaoDoServidor.PROFESSOR;

    private static final Integer DEFAULT_CENTRO_DE_ATIVIDADE_ID_LOTACAO = 1;
    private static final Integer UPDATED_CENTRO_DE_ATIVIDADE_ID_LOTACAO = 2;

    private static final Integer DEFAULT_CENTRO_DE_ATIVIDADE_ID_ATUACAO = 1;
    private static final Integer UPDATED_CENTRO_DE_ATIVIDADE_ID_ATUACAO = 2;

    private static final String DEFAULT_OCUPACAO = "AAAAAAAAAA";
    private static final String UPDATED_OCUPACAO = "BBBBBBBBBB";

    private static final String DEFAULT_CARGA_HORARIA = "AAAAAAAAAA";
    private static final String UPDATED_CARGA_HORARIA = "BBBBBBBBBB";

    private static final TipoDeRemuneracao DEFAULT_TIPO_DE_REMUNERACAO = TipoDeRemuneracao.MENSALISTA;
    private static final TipoDeRemuneracao UPDATED_TIPO_DE_REMUNERACAO = TipoDeRemuneracao.HORISTA;

    private static final String DEFAULT_IDADE = "AAAAAAAAAA";
    private static final String UPDATED_IDADE = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPO_DE_CONTRATO = "AAAAAAAAAA";
    private static final String UPDATED_TEMPO_DE_CONTRATO = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCAO_DO_CRACHA = "AAAAAAAAAA";
    private static final String UPDATED_FUNCAO_DO_CRACHA = "BBBBBBBBBB";

    private static final String DEFAULT_CHEFE_DO_CENTRO_DE_ATIVIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CHEFE_DO_CENTRO_DE_ATIVIDADE = "BBBBBBBBBB";

    @Autowired
    private ServidorRepository servidorRepository;

    @Autowired
    private ServidorMapper servidorMapper;

    @Autowired
    private ServidorService servidorService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.ServidorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServidorSearchRepository mockServidorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServidorMockMvc;

    private Servidor servidor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servidor createEntity(EntityManager em) {
        Servidor servidor = new Servidor()
            .codigo(DEFAULT_CODIGO)
            .matricula(DEFAULT_MATRICULA)
            .codigoStarh(DEFAULT_CODIGO_STARH)
            .inicioDoVinculo(DEFAULT_INICIO_DO_VINCULO)
            .fimDoVinculo(DEFAULT_FIM_DO_VINCULO)
            .situacao(DEFAULT_SITUACAO)
            .situacaoDoServidor(DEFAULT_SITUACAO_DO_SERVIDOR)
            .centroDeAtividadeIdLotacao(DEFAULT_CENTRO_DE_ATIVIDADE_ID_LOTACAO)
            .centroDeAtividadeIdAtuacao(DEFAULT_CENTRO_DE_ATIVIDADE_ID_ATUACAO)
            .ocupacao(DEFAULT_OCUPACAO)
            .cargaHoraria(DEFAULT_CARGA_HORARIA)
            .tipoDeRemuneracao(DEFAULT_TIPO_DE_REMUNERACAO)
            .idade(DEFAULT_IDADE)
            .tempoDeContrato(DEFAULT_TEMPO_DE_CONTRATO)
            .funcaoDoCracha(DEFAULT_FUNCAO_DO_CRACHA)
            .chefeDoCentroDeAtividade(DEFAULT_CHEFE_DO_CENTRO_DE_ATIVIDADE);
        return servidor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servidor createUpdatedEntity(EntityManager em) {
        Servidor servidor = new Servidor()
            .codigo(UPDATED_CODIGO)
            .matricula(UPDATED_MATRICULA)
            .codigoStarh(UPDATED_CODIGO_STARH)
            .inicioDoVinculo(UPDATED_INICIO_DO_VINCULO)
            .fimDoVinculo(UPDATED_FIM_DO_VINCULO)
            .situacao(UPDATED_SITUACAO)
            .situacaoDoServidor(UPDATED_SITUACAO_DO_SERVIDOR)
            .centroDeAtividadeIdLotacao(UPDATED_CENTRO_DE_ATIVIDADE_ID_LOTACAO)
            .centroDeAtividadeIdAtuacao(UPDATED_CENTRO_DE_ATIVIDADE_ID_ATUACAO)
            .ocupacao(UPDATED_OCUPACAO)
            .cargaHoraria(UPDATED_CARGA_HORARIA)
            .tipoDeRemuneracao(UPDATED_TIPO_DE_REMUNERACAO)
            .idade(UPDATED_IDADE)
            .tempoDeContrato(UPDATED_TEMPO_DE_CONTRATO)
            .funcaoDoCracha(UPDATED_FUNCAO_DO_CRACHA)
            .chefeDoCentroDeAtividade(UPDATED_CHEFE_DO_CENTRO_DE_ATIVIDADE);
        return servidor;
    }

    @BeforeEach
    public void initTest() {
        servidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createServidor() throws Exception {
        int databaseSizeBeforeCreate = servidorRepository.findAll().size();
        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);
        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servidorDTO)))
            .andExpect(status().isCreated());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeCreate + 1);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testServidor.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testServidor.getCodigoStarh()).isEqualTo(DEFAULT_CODIGO_STARH);
        assertThat(testServidor.getInicioDoVinculo()).isEqualTo(DEFAULT_INICIO_DO_VINCULO);
        assertThat(testServidor.getFimDoVinculo()).isEqualTo(DEFAULT_FIM_DO_VINCULO);
        assertThat(testServidor.isSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testServidor.getSituacaoDoServidor()).isEqualTo(DEFAULT_SITUACAO_DO_SERVIDOR);
        assertThat(testServidor.getCentroDeAtividadeIdLotacao()).isEqualTo(DEFAULT_CENTRO_DE_ATIVIDADE_ID_LOTACAO);
        assertThat(testServidor.getCentroDeAtividadeIdAtuacao()).isEqualTo(DEFAULT_CENTRO_DE_ATIVIDADE_ID_ATUACAO);
        assertThat(testServidor.getOcupacao()).isEqualTo(DEFAULT_OCUPACAO);
        assertThat(testServidor.getCargaHoraria()).isEqualTo(DEFAULT_CARGA_HORARIA);
        assertThat(testServidor.getTipoDeRemuneracao()).isEqualTo(DEFAULT_TIPO_DE_REMUNERACAO);
        assertThat(testServidor.getIdade()).isEqualTo(DEFAULT_IDADE);
        assertThat(testServidor.getTempoDeContrato()).isEqualTo(DEFAULT_TEMPO_DE_CONTRATO);
        assertThat(testServidor.getFuncaoDoCracha()).isEqualTo(DEFAULT_FUNCAO_DO_CRACHA);
        assertThat(testServidor.getChefeDoCentroDeAtividade()).isEqualTo(DEFAULT_CHEFE_DO_CENTRO_DE_ATIVIDADE);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(1)).save(testServidor);
    }

    @Test
    @Transactional
    public void createServidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servidorRepository.findAll().size();

        // Create the Servidor with an existing ID
        servidor.setId(1L);
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(0)).save(servidor);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setCodigo(null);

        // Create the Servidor, which fails.
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);


        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servidorDTO)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInicioDoVinculoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setInicioDoVinculo(null);

        // Create the Servidor, which fails.
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);


        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servidorDTO)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServidors() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        // Get all the servidorList
        restServidorMockMvc.perform(get("/api/servidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].codigoStarh").value(hasItem(DEFAULT_CODIGO_STARH)))
            .andExpect(jsonPath("$.[*].inicioDoVinculo").value(hasItem(DEFAULT_INICIO_DO_VINCULO.toString())))
            .andExpect(jsonPath("$.[*].fimDoVinculo").value(hasItem(DEFAULT_FIM_DO_VINCULO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].situacaoDoServidor").value(hasItem(DEFAULT_SITUACAO_DO_SERVIDOR.toString())))
            .andExpect(jsonPath("$.[*].centroDeAtividadeIdLotacao").value(hasItem(DEFAULT_CENTRO_DE_ATIVIDADE_ID_LOTACAO)))
            .andExpect(jsonPath("$.[*].centroDeAtividadeIdAtuacao").value(hasItem(DEFAULT_CENTRO_DE_ATIVIDADE_ID_ATUACAO)))
            .andExpect(jsonPath("$.[*].ocupacao").value(hasItem(DEFAULT_OCUPACAO)))
            .andExpect(jsonPath("$.[*].cargaHoraria").value(hasItem(DEFAULT_CARGA_HORARIA)))
            .andExpect(jsonPath("$.[*].tipoDeRemuneracao").value(hasItem(DEFAULT_TIPO_DE_REMUNERACAO.toString())))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)))
            .andExpect(jsonPath("$.[*].tempoDeContrato").value(hasItem(DEFAULT_TEMPO_DE_CONTRATO)))
            .andExpect(jsonPath("$.[*].funcaoDoCracha").value(hasItem(DEFAULT_FUNCAO_DO_CRACHA)))
            .andExpect(jsonPath("$.[*].chefeDoCentroDeAtividade").value(hasItem(DEFAULT_CHEFE_DO_CENTRO_DE_ATIVIDADE)));
    }
    
    @Test
    @Transactional
    public void getServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        // Get the servidor
        restServidorMockMvc.perform(get("/api/servidors/{id}", servidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servidor.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.codigoStarh").value(DEFAULT_CODIGO_STARH))
            .andExpect(jsonPath("$.inicioDoVinculo").value(DEFAULT_INICIO_DO_VINCULO.toString()))
            .andExpect(jsonPath("$.fimDoVinculo").value(DEFAULT_FIM_DO_VINCULO.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.booleanValue()))
            .andExpect(jsonPath("$.situacaoDoServidor").value(DEFAULT_SITUACAO_DO_SERVIDOR.toString()))
            .andExpect(jsonPath("$.centroDeAtividadeIdLotacao").value(DEFAULT_CENTRO_DE_ATIVIDADE_ID_LOTACAO))
            .andExpect(jsonPath("$.centroDeAtividadeIdAtuacao").value(DEFAULT_CENTRO_DE_ATIVIDADE_ID_ATUACAO))
            .andExpect(jsonPath("$.ocupacao").value(DEFAULT_OCUPACAO))
            .andExpect(jsonPath("$.cargaHoraria").value(DEFAULT_CARGA_HORARIA))
            .andExpect(jsonPath("$.tipoDeRemuneracao").value(DEFAULT_TIPO_DE_REMUNERACAO.toString()))
            .andExpect(jsonPath("$.idade").value(DEFAULT_IDADE))
            .andExpect(jsonPath("$.tempoDeContrato").value(DEFAULT_TEMPO_DE_CONTRATO))
            .andExpect(jsonPath("$.funcaoDoCracha").value(DEFAULT_FUNCAO_DO_CRACHA))
            .andExpect(jsonPath("$.chefeDoCentroDeAtividade").value(DEFAULT_CHEFE_DO_CENTRO_DE_ATIVIDADE));
    }
    @Test
    @Transactional
    public void getNonExistingServidor() throws Exception {
        // Get the servidor
        restServidorMockMvc.perform(get("/api/servidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();

        // Update the servidor
        Servidor updatedServidor = servidorRepository.findById(servidor.getId()).get();
        // Disconnect from session so that the updates on updatedServidor are not directly saved in db
        em.detach(updatedServidor);
        updatedServidor
            .codigo(UPDATED_CODIGO)
            .matricula(UPDATED_MATRICULA)
            .codigoStarh(UPDATED_CODIGO_STARH)
            .inicioDoVinculo(UPDATED_INICIO_DO_VINCULO)
            .fimDoVinculo(UPDATED_FIM_DO_VINCULO)
            .situacao(UPDATED_SITUACAO)
            .situacaoDoServidor(UPDATED_SITUACAO_DO_SERVIDOR)
            .centroDeAtividadeIdLotacao(UPDATED_CENTRO_DE_ATIVIDADE_ID_LOTACAO)
            .centroDeAtividadeIdAtuacao(UPDATED_CENTRO_DE_ATIVIDADE_ID_ATUACAO)
            .ocupacao(UPDATED_OCUPACAO)
            .cargaHoraria(UPDATED_CARGA_HORARIA)
            .tipoDeRemuneracao(UPDATED_TIPO_DE_REMUNERACAO)
            .idade(UPDATED_IDADE)
            .tempoDeContrato(UPDATED_TEMPO_DE_CONTRATO)
            .funcaoDoCracha(UPDATED_FUNCAO_DO_CRACHA)
            .chefeDoCentroDeAtividade(UPDATED_CHEFE_DO_CENTRO_DE_ATIVIDADE);
        ServidorDTO servidorDTO = servidorMapper.toDto(updatedServidor);

        restServidorMockMvc.perform(put("/api/servidors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servidorDTO)))
            .andExpect(status().isOk());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testServidor.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testServidor.getCodigoStarh()).isEqualTo(UPDATED_CODIGO_STARH);
        assertThat(testServidor.getInicioDoVinculo()).isEqualTo(UPDATED_INICIO_DO_VINCULO);
        assertThat(testServidor.getFimDoVinculo()).isEqualTo(UPDATED_FIM_DO_VINCULO);
        assertThat(testServidor.isSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testServidor.getSituacaoDoServidor()).isEqualTo(UPDATED_SITUACAO_DO_SERVIDOR);
        assertThat(testServidor.getCentroDeAtividadeIdLotacao()).isEqualTo(UPDATED_CENTRO_DE_ATIVIDADE_ID_LOTACAO);
        assertThat(testServidor.getCentroDeAtividadeIdAtuacao()).isEqualTo(UPDATED_CENTRO_DE_ATIVIDADE_ID_ATUACAO);
        assertThat(testServidor.getOcupacao()).isEqualTo(UPDATED_OCUPACAO);
        assertThat(testServidor.getCargaHoraria()).isEqualTo(UPDATED_CARGA_HORARIA);
        assertThat(testServidor.getTipoDeRemuneracao()).isEqualTo(UPDATED_TIPO_DE_REMUNERACAO);
        assertThat(testServidor.getIdade()).isEqualTo(UPDATED_IDADE);
        assertThat(testServidor.getTempoDeContrato()).isEqualTo(UPDATED_TEMPO_DE_CONTRATO);
        assertThat(testServidor.getFuncaoDoCracha()).isEqualTo(UPDATED_FUNCAO_DO_CRACHA);
        assertThat(testServidor.getChefeDoCentroDeAtividade()).isEqualTo(UPDATED_CHEFE_DO_CENTRO_DE_ATIVIDADE);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(1)).save(testServidor);
    }

    @Test
    @Transactional
    public void updateNonExistingServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();

        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServidorMockMvc.perform(put("/api/servidors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(0)).save(servidor);
    }

    @Test
    @Transactional
    public void deleteServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        int databaseSizeBeforeDelete = servidorRepository.findAll().size();

        // Delete the servidor
        restServidorMockMvc.perform(delete("/api/servidors/{id}", servidor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(1)).deleteById(servidor.getId());
    }

    @Test
    @Transactional
    public void searchServidor() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);
        when(mockServidorSearchRepository.search(queryStringQuery("id:" + servidor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(servidor), PageRequest.of(0, 1), 1));

        // Search the servidor
        restServidorMockMvc.perform(get("/api/_search/servidors?query=id:" + servidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].codigoStarh").value(hasItem(DEFAULT_CODIGO_STARH)))
            .andExpect(jsonPath("$.[*].inicioDoVinculo").value(hasItem(DEFAULT_INICIO_DO_VINCULO.toString())))
            .andExpect(jsonPath("$.[*].fimDoVinculo").value(hasItem(DEFAULT_FIM_DO_VINCULO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].situacaoDoServidor").value(hasItem(DEFAULT_SITUACAO_DO_SERVIDOR.toString())))
            .andExpect(jsonPath("$.[*].centroDeAtividadeIdLotacao").value(hasItem(DEFAULT_CENTRO_DE_ATIVIDADE_ID_LOTACAO)))
            .andExpect(jsonPath("$.[*].centroDeAtividadeIdAtuacao").value(hasItem(DEFAULT_CENTRO_DE_ATIVIDADE_ID_ATUACAO)))
            .andExpect(jsonPath("$.[*].ocupacao").value(hasItem(DEFAULT_OCUPACAO)))
            .andExpect(jsonPath("$.[*].cargaHoraria").value(hasItem(DEFAULT_CARGA_HORARIA)))
            .andExpect(jsonPath("$.[*].tipoDeRemuneracao").value(hasItem(DEFAULT_TIPO_DE_REMUNERACAO.toString())))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)))
            .andExpect(jsonPath("$.[*].tempoDeContrato").value(hasItem(DEFAULT_TEMPO_DE_CONTRATO)))
            .andExpect(jsonPath("$.[*].funcaoDoCracha").value(hasItem(DEFAULT_FUNCAO_DO_CRACHA)))
            .andExpect(jsonPath("$.[*].chefeDoCentroDeAtividade").value(hasItem(DEFAULT_CHEFE_DO_CENTRO_DE_ATIVIDADE)));
    }
}
