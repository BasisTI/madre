package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Vinculo;
import br.com.basis.madre.seguranca.repository.VinculoRepository;
import br.com.basis.madre.seguranca.repository.search.VinculoSearchRepository;
import br.com.basis.madre.seguranca.service.VinculoService;
import br.com.basis.madre.seguranca.service.dto.VinculoDTO;
import br.com.basis.madre.seguranca.service.mapper.VinculoMapper;

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
 * Integration tests for the {@link VinculoResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VinculoResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SITUACAO = false;
    private static final Boolean UPDATED_SITUACAO = true;

    private static final Boolean DEFAULT_INF_DEPENDENTE = false;
    private static final Boolean UPDATED_INF_DEPENDENTE = true;

    private static final Boolean DEFAULT_EX_TERMINO = false;
    private static final Boolean UPDATED_EX_TERMINO = true;

    private static final Boolean DEFAULT_GERA_MATRICULA = false;
    private static final Boolean UPDATED_GERA_MATRICULA = true;

    private static final Boolean DEFAULT_EX_CENTRO_ATIVIDADE = false;
    private static final Boolean UPDATED_EX_CENTRO_ATIVIDADE = true;

    private static final Boolean DEFAULT_EX_OCUPACAO = false;
    private static final Boolean UPDATED_EX_OCUPACAO = true;

    private static final Boolean DEFAULT_TRANSFERE_STARH = false;
    private static final Boolean UPDATED_TRANSFERE_STARH = true;

    private static final Boolean DEFAULT_PERMITE_DUPLOS = false;
    private static final Boolean UPDATED_PERMITE_DUPLOS = true;

    private static final Boolean DEFAULT_EX_CPF_RG = false;
    private static final Boolean UPDATED_EX_CPF_RG = true;

    private static final Boolean DEFAULT_GESTAP_DESEMPENHO = false;
    private static final Boolean UPDATED_GESTAP_DESEMPENHO = true;

    private static final Boolean DEFAULT_EMITE_CONTRATO = false;
    private static final Boolean UPDATED_EMITE_CONTRATO = true;

    private static final String DEFAULT_NUMEROS_DE_DIAS_ADMISSAO = "AAAAAAAAAA";
    private static final String UPDATED_NUMEROS_DE_DIAS_ADMISSAO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_MASCULINO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_MASCULINO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_FEMININO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_FEMININO = "BBBBBBBBBB";

    @Autowired
    private VinculoRepository vinculoRepository;

    @Autowired
    private VinculoMapper vinculoMapper;

    @Autowired
    private VinculoService vinculoService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.VinculoSearchRepositoryMockConfiguration
     */
    @Autowired
    private VinculoSearchRepository mockVinculoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVinculoMockMvc;

    private Vinculo vinculo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vinculo createEntity(EntityManager em) {
        Vinculo vinculo = new Vinculo()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO)
            .situacao(DEFAULT_SITUACAO)
            .infDependente(DEFAULT_INF_DEPENDENTE)
            .exTermino(DEFAULT_EX_TERMINO)
            .geraMatricula(DEFAULT_GERA_MATRICULA)
            .exCentroAtividade(DEFAULT_EX_CENTRO_ATIVIDADE)
            .exOcupacao(DEFAULT_EX_OCUPACAO)
            .transfereStarh(DEFAULT_TRANSFERE_STARH)
            .permiteDuplos(DEFAULT_PERMITE_DUPLOS)
            .exCpfRg(DEFAULT_EX_CPF_RG)
            .gestapDesempenho(DEFAULT_GESTAP_DESEMPENHO)
            .emiteContrato(DEFAULT_EMITE_CONTRATO)
            .numerosDeDiasAdmissao(DEFAULT_NUMEROS_DE_DIAS_ADMISSAO)
            .tituloMasculino(DEFAULT_TITULO_MASCULINO)
            .tituloFeminino(DEFAULT_TITULO_FEMININO);
        return vinculo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vinculo createUpdatedEntity(EntityManager em) {
        Vinculo vinculo = new Vinculo()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .situacao(UPDATED_SITUACAO)
            .infDependente(UPDATED_INF_DEPENDENTE)
            .exTermino(UPDATED_EX_TERMINO)
            .geraMatricula(UPDATED_GERA_MATRICULA)
            .exCentroAtividade(UPDATED_EX_CENTRO_ATIVIDADE)
            .exOcupacao(UPDATED_EX_OCUPACAO)
            .transfereStarh(UPDATED_TRANSFERE_STARH)
            .permiteDuplos(UPDATED_PERMITE_DUPLOS)
            .exCpfRg(UPDATED_EX_CPF_RG)
            .gestapDesempenho(UPDATED_GESTAP_DESEMPENHO)
            .emiteContrato(UPDATED_EMITE_CONTRATO)
            .numerosDeDiasAdmissao(UPDATED_NUMEROS_DE_DIAS_ADMISSAO)
            .tituloMasculino(UPDATED_TITULO_MASCULINO)
            .tituloFeminino(UPDATED_TITULO_FEMININO);
        return vinculo;
    }

    @BeforeEach
    public void initTest() {
        vinculo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVinculo() throws Exception {
        int databaseSizeBeforeCreate = vinculoRepository.findAll().size();
        // Create the Vinculo
        VinculoDTO vinculoDTO = vinculoMapper.toDto(vinculo);
        restVinculoMockMvc.perform(post("/api/vinculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vinculoDTO)))
            .andExpect(status().isCreated());

        // Validate the Vinculo in the database
        List<Vinculo> vinculoList = vinculoRepository.findAll();
        assertThat(vinculoList).hasSize(databaseSizeBeforeCreate + 1);
        Vinculo testVinculo = vinculoList.get(vinculoList.size() - 1);
        assertThat(testVinculo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testVinculo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testVinculo.isSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testVinculo.isInfDependente()).isEqualTo(DEFAULT_INF_DEPENDENTE);
        assertThat(testVinculo.isExTermino()).isEqualTo(DEFAULT_EX_TERMINO);
        assertThat(testVinculo.isGeraMatricula()).isEqualTo(DEFAULT_GERA_MATRICULA);
        assertThat(testVinculo.isExCentroAtividade()).isEqualTo(DEFAULT_EX_CENTRO_ATIVIDADE);
        assertThat(testVinculo.isExOcupacao()).isEqualTo(DEFAULT_EX_OCUPACAO);
        assertThat(testVinculo.isTransfereStarh()).isEqualTo(DEFAULT_TRANSFERE_STARH);
        assertThat(testVinculo.isPermiteDuplos()).isEqualTo(DEFAULT_PERMITE_DUPLOS);
        assertThat(testVinculo.isExCpfRg()).isEqualTo(DEFAULT_EX_CPF_RG);
        assertThat(testVinculo.isGestapDesempenho()).isEqualTo(DEFAULT_GESTAP_DESEMPENHO);
        assertThat(testVinculo.isEmiteContrato()).isEqualTo(DEFAULT_EMITE_CONTRATO);
        assertThat(testVinculo.getNumerosDeDiasAdmissao()).isEqualTo(DEFAULT_NUMEROS_DE_DIAS_ADMISSAO);
        assertThat(testVinculo.getTituloMasculino()).isEqualTo(DEFAULT_TITULO_MASCULINO);
        assertThat(testVinculo.getTituloFeminino()).isEqualTo(DEFAULT_TITULO_FEMININO);

        // Validate the Vinculo in Elasticsearch
        verify(mockVinculoSearchRepository, times(1)).save(testVinculo);
    }

    @Test
    @Transactional
    public void createVinculoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vinculoRepository.findAll().size();

        // Create the Vinculo with an existing ID
        vinculo.setId(1L);
        VinculoDTO vinculoDTO = vinculoMapper.toDto(vinculo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVinculoMockMvc.perform(post("/api/vinculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vinculoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vinculo in the database
        List<Vinculo> vinculoList = vinculoRepository.findAll();
        assertThat(vinculoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Vinculo in Elasticsearch
        verify(mockVinculoSearchRepository, times(0)).save(vinculo);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vinculoRepository.findAll().size();
        // set the field null
        vinculo.setCodigo(null);

        // Create the Vinculo, which fails.
        VinculoDTO vinculoDTO = vinculoMapper.toDto(vinculo);


        restVinculoMockMvc.perform(post("/api/vinculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vinculoDTO)))
            .andExpect(status().isBadRequest());

        List<Vinculo> vinculoList = vinculoRepository.findAll();
        assertThat(vinculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vinculoRepository.findAll().size();
        // set the field null
        vinculo.setDescricao(null);

        // Create the Vinculo, which fails.
        VinculoDTO vinculoDTO = vinculoMapper.toDto(vinculo);


        restVinculoMockMvc.perform(post("/api/vinculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vinculoDTO)))
            .andExpect(status().isBadRequest());

        List<Vinculo> vinculoList = vinculoRepository.findAll();
        assertThat(vinculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vinculoRepository.findAll().size();
        // set the field null
        vinculo.setSituacao(null);

        // Create the Vinculo, which fails.
        VinculoDTO vinculoDTO = vinculoMapper.toDto(vinculo);


        restVinculoMockMvc.perform(post("/api/vinculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vinculoDTO)))
            .andExpect(status().isBadRequest());

        List<Vinculo> vinculoList = vinculoRepository.findAll();
        assertThat(vinculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVinculos() throws Exception {
        // Initialize the database
        vinculoRepository.saveAndFlush(vinculo);

        // Get all the vinculoList
        restVinculoMockMvc.perform(get("/api/vinculos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vinculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].infDependente").value(hasItem(DEFAULT_INF_DEPENDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].exTermino").value(hasItem(DEFAULT_EX_TERMINO.booleanValue())))
            .andExpect(jsonPath("$.[*].geraMatricula").value(hasItem(DEFAULT_GERA_MATRICULA.booleanValue())))
            .andExpect(jsonPath("$.[*].exCentroAtividade").value(hasItem(DEFAULT_EX_CENTRO_ATIVIDADE.booleanValue())))
            .andExpect(jsonPath("$.[*].exOcupacao").value(hasItem(DEFAULT_EX_OCUPACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].transfereStarh").value(hasItem(DEFAULT_TRANSFERE_STARH.booleanValue())))
            .andExpect(jsonPath("$.[*].permiteDuplos").value(hasItem(DEFAULT_PERMITE_DUPLOS.booleanValue())))
            .andExpect(jsonPath("$.[*].exCpfRg").value(hasItem(DEFAULT_EX_CPF_RG.booleanValue())))
            .andExpect(jsonPath("$.[*].gestapDesempenho").value(hasItem(DEFAULT_GESTAP_DESEMPENHO.booleanValue())))
            .andExpect(jsonPath("$.[*].emiteContrato").value(hasItem(DEFAULT_EMITE_CONTRATO.booleanValue())))
            .andExpect(jsonPath("$.[*].numerosDeDiasAdmissao").value(hasItem(DEFAULT_NUMEROS_DE_DIAS_ADMISSAO)))
            .andExpect(jsonPath("$.[*].tituloMasculino").value(hasItem(DEFAULT_TITULO_MASCULINO)))
            .andExpect(jsonPath("$.[*].tituloFeminino").value(hasItem(DEFAULT_TITULO_FEMININO)));
    }
    
    @Test
    @Transactional
    public void getVinculo() throws Exception {
        // Initialize the database
        vinculoRepository.saveAndFlush(vinculo);

        // Get the vinculo
        restVinculoMockMvc.perform(get("/api/vinculos/{id}", vinculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vinculo.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.booleanValue()))
            .andExpect(jsonPath("$.infDependente").value(DEFAULT_INF_DEPENDENTE.booleanValue()))
            .andExpect(jsonPath("$.exTermino").value(DEFAULT_EX_TERMINO.booleanValue()))
            .andExpect(jsonPath("$.geraMatricula").value(DEFAULT_GERA_MATRICULA.booleanValue()))
            .andExpect(jsonPath("$.exCentroAtividade").value(DEFAULT_EX_CENTRO_ATIVIDADE.booleanValue()))
            .andExpect(jsonPath("$.exOcupacao").value(DEFAULT_EX_OCUPACAO.booleanValue()))
            .andExpect(jsonPath("$.transfereStarh").value(DEFAULT_TRANSFERE_STARH.booleanValue()))
            .andExpect(jsonPath("$.permiteDuplos").value(DEFAULT_PERMITE_DUPLOS.booleanValue()))
            .andExpect(jsonPath("$.exCpfRg").value(DEFAULT_EX_CPF_RG.booleanValue()))
            .andExpect(jsonPath("$.gestapDesempenho").value(DEFAULT_GESTAP_DESEMPENHO.booleanValue()))
            .andExpect(jsonPath("$.emiteContrato").value(DEFAULT_EMITE_CONTRATO.booleanValue()))
            .andExpect(jsonPath("$.numerosDeDiasAdmissao").value(DEFAULT_NUMEROS_DE_DIAS_ADMISSAO))
            .andExpect(jsonPath("$.tituloMasculino").value(DEFAULT_TITULO_MASCULINO))
            .andExpect(jsonPath("$.tituloFeminino").value(DEFAULT_TITULO_FEMININO));
    }
    @Test
    @Transactional
    public void getNonExistingVinculo() throws Exception {
        // Get the vinculo
        restVinculoMockMvc.perform(get("/api/vinculos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVinculo() throws Exception {
        // Initialize the database
        vinculoRepository.saveAndFlush(vinculo);

        int databaseSizeBeforeUpdate = vinculoRepository.findAll().size();

        // Update the vinculo
        Vinculo updatedVinculo = vinculoRepository.findById(vinculo.getId()).get();
        // Disconnect from session so that the updates on updatedVinculo are not directly saved in db
        em.detach(updatedVinculo);
        updatedVinculo
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .situacao(UPDATED_SITUACAO)
            .infDependente(UPDATED_INF_DEPENDENTE)
            .exTermino(UPDATED_EX_TERMINO)
            .geraMatricula(UPDATED_GERA_MATRICULA)
            .exCentroAtividade(UPDATED_EX_CENTRO_ATIVIDADE)
            .exOcupacao(UPDATED_EX_OCUPACAO)
            .transfereStarh(UPDATED_TRANSFERE_STARH)
            .permiteDuplos(UPDATED_PERMITE_DUPLOS)
            .exCpfRg(UPDATED_EX_CPF_RG)
            .gestapDesempenho(UPDATED_GESTAP_DESEMPENHO)
            .emiteContrato(UPDATED_EMITE_CONTRATO)
            .numerosDeDiasAdmissao(UPDATED_NUMEROS_DE_DIAS_ADMISSAO)
            .tituloMasculino(UPDATED_TITULO_MASCULINO)
            .tituloFeminino(UPDATED_TITULO_FEMININO);
        VinculoDTO vinculoDTO = vinculoMapper.toDto(updatedVinculo);

        restVinculoMockMvc.perform(put("/api/vinculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vinculoDTO)))
            .andExpect(status().isOk());

        // Validate the Vinculo in the database
        List<Vinculo> vinculoList = vinculoRepository.findAll();
        assertThat(vinculoList).hasSize(databaseSizeBeforeUpdate);
        Vinculo testVinculo = vinculoList.get(vinculoList.size() - 1);
        assertThat(testVinculo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testVinculo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testVinculo.isSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testVinculo.isInfDependente()).isEqualTo(UPDATED_INF_DEPENDENTE);
        assertThat(testVinculo.isExTermino()).isEqualTo(UPDATED_EX_TERMINO);
        assertThat(testVinculo.isGeraMatricula()).isEqualTo(UPDATED_GERA_MATRICULA);
        assertThat(testVinculo.isExCentroAtividade()).isEqualTo(UPDATED_EX_CENTRO_ATIVIDADE);
        assertThat(testVinculo.isExOcupacao()).isEqualTo(UPDATED_EX_OCUPACAO);
        assertThat(testVinculo.isTransfereStarh()).isEqualTo(UPDATED_TRANSFERE_STARH);
        assertThat(testVinculo.isPermiteDuplos()).isEqualTo(UPDATED_PERMITE_DUPLOS);
        assertThat(testVinculo.isExCpfRg()).isEqualTo(UPDATED_EX_CPF_RG);
        assertThat(testVinculo.isGestapDesempenho()).isEqualTo(UPDATED_GESTAP_DESEMPENHO);
        assertThat(testVinculo.isEmiteContrato()).isEqualTo(UPDATED_EMITE_CONTRATO);
        assertThat(testVinculo.getNumerosDeDiasAdmissao()).isEqualTo(UPDATED_NUMEROS_DE_DIAS_ADMISSAO);
        assertThat(testVinculo.getTituloMasculino()).isEqualTo(UPDATED_TITULO_MASCULINO);
        assertThat(testVinculo.getTituloFeminino()).isEqualTo(UPDATED_TITULO_FEMININO);

        // Validate the Vinculo in Elasticsearch
        verify(mockVinculoSearchRepository, times(1)).save(testVinculo);
    }

    @Test
    @Transactional
    public void updateNonExistingVinculo() throws Exception {
        int databaseSizeBeforeUpdate = vinculoRepository.findAll().size();

        // Create the Vinculo
        VinculoDTO vinculoDTO = vinculoMapper.toDto(vinculo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVinculoMockMvc.perform(put("/api/vinculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vinculoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vinculo in the database
        List<Vinculo> vinculoList = vinculoRepository.findAll();
        assertThat(vinculoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Vinculo in Elasticsearch
        verify(mockVinculoSearchRepository, times(0)).save(vinculo);
    }

    @Test
    @Transactional
    public void deleteVinculo() throws Exception {
        // Initialize the database
        vinculoRepository.saveAndFlush(vinculo);

        int databaseSizeBeforeDelete = vinculoRepository.findAll().size();

        // Delete the vinculo
        restVinculoMockMvc.perform(delete("/api/vinculos/{id}", vinculo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vinculo> vinculoList = vinculoRepository.findAll();
        assertThat(vinculoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Vinculo in Elasticsearch
        verify(mockVinculoSearchRepository, times(1)).deleteById(vinculo.getId());
    }

    @Test
    @Transactional
    public void searchVinculo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        vinculoRepository.saveAndFlush(vinculo);
        when(mockVinculoSearchRepository.search(queryStringQuery("id:" + vinculo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(vinculo), PageRequest.of(0, 1), 1));

        // Search the vinculo
        restVinculoMockMvc.perform(get("/api/_search/vinculos?query=id:" + vinculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vinculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].infDependente").value(hasItem(DEFAULT_INF_DEPENDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].exTermino").value(hasItem(DEFAULT_EX_TERMINO.booleanValue())))
            .andExpect(jsonPath("$.[*].geraMatricula").value(hasItem(DEFAULT_GERA_MATRICULA.booleanValue())))
            .andExpect(jsonPath("$.[*].exCentroAtividade").value(hasItem(DEFAULT_EX_CENTRO_ATIVIDADE.booleanValue())))
            .andExpect(jsonPath("$.[*].exOcupacao").value(hasItem(DEFAULT_EX_OCUPACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].transfereStarh").value(hasItem(DEFAULT_TRANSFERE_STARH.booleanValue())))
            .andExpect(jsonPath("$.[*].permiteDuplos").value(hasItem(DEFAULT_PERMITE_DUPLOS.booleanValue())))
            .andExpect(jsonPath("$.[*].exCpfRg").value(hasItem(DEFAULT_EX_CPF_RG.booleanValue())))
            .andExpect(jsonPath("$.[*].gestapDesempenho").value(hasItem(DEFAULT_GESTAP_DESEMPENHO.booleanValue())))
            .andExpect(jsonPath("$.[*].emiteContrato").value(hasItem(DEFAULT_EMITE_CONTRATO.booleanValue())))
            .andExpect(jsonPath("$.[*].numerosDeDiasAdmissao").value(hasItem(DEFAULT_NUMEROS_DE_DIAS_ADMISSAO)))
            .andExpect(jsonPath("$.[*].tituloMasculino").value(hasItem(DEFAULT_TITULO_MASCULINO)))
            .andExpect(jsonPath("$.[*].tituloFeminino").value(hasItem(DEFAULT_TITULO_FEMININO)));
    }
}
