package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Pessoa;
import br.com.basis.madre.seguranca.repository.PessoaRepository;
import br.com.basis.madre.seguranca.repository.search.PessoaSearchRepository;
import br.com.basis.madre.seguranca.service.PessoaService;
import br.com.basis.madre.seguranca.service.dto.PessoaDTO;
import br.com.basis.madre.seguranca.service.mapper.PessoaMapper;

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

import br.com.basis.madre.seguranca.domain.enumeration.Sexo;
import br.com.basis.madre.seguranca.domain.enumeration.EstadoCivil;
import br.com.basis.madre.seguranca.domain.enumeration.GrauDeInstrucao;
/**
 * Integration tests for the {@link PessoaResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PessoaResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_DA_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DA_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_DO_PAI = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_PAI = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMININO;

    private static final EstadoCivil DEFAULT_ESTADO_CIVIL = EstadoCivil.CASADO;
    private static final EstadoCivil UPDATED_ESTADO_CIVIL = EstadoCivil.SOLTEIRO;

    private static final LocalDate DEFAULT_DATA_DE_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NASCIONALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NASCIONALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_NATURALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NATURALIDADE = "BBBBBBBBBB";

    private static final GrauDeInstrucao DEFAULT_GRAU_DE_INSTRUCAO = GrauDeInstrucao.NENHUM;
    private static final GrauDeInstrucao UPDATED_GRAU_DE_INSTRUCAO = GrauDeInstrucao.IGNORADO;

    private static final String DEFAULT_NOME_USUAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_USUAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Autowired
    private PessoaService pessoaService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.PessoaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PessoaSearchRepository mockPessoaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .nomeDaMae(DEFAULT_NOME_DA_MAE)
            .nomeDoPai(DEFAULT_NOME_DO_PAI)
            .sexo(DEFAULT_SEXO)
            .estadoCivil(DEFAULT_ESTADO_CIVIL)
            .dataDeNascimento(DEFAULT_DATA_DE_NASCIMENTO)
            .nascionalidade(DEFAULT_NASCIONALIDADE)
            .naturalidade(DEFAULT_NATURALIDADE)
            .grauDeInstrucao(DEFAULT_GRAU_DE_INSTRUCAO)
            .nomeUsual(DEFAULT_NOME_USUAL)
            .email(DEFAULT_EMAIL);
        return pessoa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createUpdatedEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .nomeDaMae(UPDATED_NOME_DA_MAE)
            .nomeDoPai(UPDATED_NOME_DO_PAI)
            .sexo(UPDATED_SEXO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .dataDeNascimento(UPDATED_DATA_DE_NASCIMENTO)
            .nascionalidade(UPDATED_NASCIONALIDADE)
            .naturalidade(UPDATED_NATURALIDADE)
            .grauDeInstrucao(UPDATED_GRAU_DE_INSTRUCAO)
            .nomeUsual(UPDATED_NOME_USUAL)
            .email(UPDATED_EMAIL);
        return pessoa;
    }

    @BeforeEach
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();
        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoa.getNomeDaMae()).isEqualTo(DEFAULT_NOME_DA_MAE);
        assertThat(testPessoa.getNomeDoPai()).isEqualTo(DEFAULT_NOME_DO_PAI);
        assertThat(testPessoa.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPessoa.getEstadoCivil()).isEqualTo(DEFAULT_ESTADO_CIVIL);
        assertThat(testPessoa.getDataDeNascimento()).isEqualTo(DEFAULT_DATA_DE_NASCIMENTO);
        assertThat(testPessoa.getNascionalidade()).isEqualTo(DEFAULT_NASCIONALIDADE);
        assertThat(testPessoa.getNaturalidade()).isEqualTo(DEFAULT_NATURALIDADE);
        assertThat(testPessoa.getGrauDeInstrucao()).isEqualTo(DEFAULT_GRAU_DE_INSTRUCAO);
        assertThat(testPessoa.getNomeUsual()).isEqualTo(DEFAULT_NOME_USUAL);
        assertThat(testPessoa.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Pessoa in Elasticsearch
        verify(mockPessoaSearchRepository, times(1)).save(testPessoa);
    }

    @Test
    @Transactional
    public void createPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa with an existing ID
        pessoa.setId(1L);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pessoa in Elasticsearch
        verify(mockPessoaSearchRepository, times(0)).save(pessoa);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setCodigo(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeDaMaeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNomeDaMae(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setSexo(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDeNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setDataDeNascimento(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNascionalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNascionalidade(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNaturalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNaturalidade(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);


        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeDaMae").value(hasItem(DEFAULT_NOME_DA_MAE)))
            .andExpect(jsonPath("$.[*].nomeDoPai").value(hasItem(DEFAULT_NOME_DO_PAI)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].dataDeNascimento").value(hasItem(DEFAULT_DATA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nascionalidade").value(hasItem(DEFAULT_NASCIONALIDADE)))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].grauDeInstrucao").value(hasItem(DEFAULT_GRAU_DE_INSTRUCAO.toString())))
            .andExpect(jsonPath("$.[*].nomeUsual").value(hasItem(DEFAULT_NOME_USUAL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nomeDaMae").value(DEFAULT_NOME_DA_MAE))
            .andExpect(jsonPath("$.nomeDoPai").value(DEFAULT_NOME_DO_PAI))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL.toString()))
            .andExpect(jsonPath("$.dataDeNascimento").value(DEFAULT_DATA_DE_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.nascionalidade").value(DEFAULT_NASCIONALIDADE))
            .andExpect(jsonPath("$.naturalidade").value(DEFAULT_NATURALIDADE))
            .andExpect(jsonPath("$.grauDeInstrucao").value(DEFAULT_GRAU_DE_INSTRUCAO.toString()))
            .andExpect(jsonPath("$.nomeUsual").value(DEFAULT_NOME_USUAL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findById(pessoa.getId()).get();
        // Disconnect from session so that the updates on updatedPessoa are not directly saved in db
        em.detach(updatedPessoa);
        updatedPessoa
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .nomeDaMae(UPDATED_NOME_DA_MAE)
            .nomeDoPai(UPDATED_NOME_DO_PAI)
            .sexo(UPDATED_SEXO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .dataDeNascimento(UPDATED_DATA_DE_NASCIMENTO)
            .nascionalidade(UPDATED_NASCIONALIDADE)
            .naturalidade(UPDATED_NATURALIDADE)
            .grauDeInstrucao(UPDATED_GRAU_DE_INSTRUCAO)
            .nomeUsual(UPDATED_NOME_USUAL)
            .email(UPDATED_EMAIL);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(updatedPessoa);

        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getNomeDaMae()).isEqualTo(UPDATED_NOME_DA_MAE);
        assertThat(testPessoa.getNomeDoPai()).isEqualTo(UPDATED_NOME_DO_PAI);
        assertThat(testPessoa.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPessoa.getEstadoCivil()).isEqualTo(UPDATED_ESTADO_CIVIL);
        assertThat(testPessoa.getDataDeNascimento()).isEqualTo(UPDATED_DATA_DE_NASCIMENTO);
        assertThat(testPessoa.getNascionalidade()).isEqualTo(UPDATED_NASCIONALIDADE);
        assertThat(testPessoa.getNaturalidade()).isEqualTo(UPDATED_NATURALIDADE);
        assertThat(testPessoa.getGrauDeInstrucao()).isEqualTo(UPDATED_GRAU_DE_INSTRUCAO);
        assertThat(testPessoa.getNomeUsual()).isEqualTo(UPDATED_NOME_USUAL);
        assertThat(testPessoa.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Pessoa in Elasticsearch
        verify(mockPessoaSearchRepository, times(1)).save(testPessoa);
    }

    @Test
    @Transactional
    public void updateNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pessoa in Elasticsearch
        verify(mockPessoaSearchRepository, times(0)).save(pessoa);
    }

    @Test
    @Transactional
    public void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Delete the pessoa
        restPessoaMockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pessoa in Elasticsearch
        verify(mockPessoaSearchRepository, times(1)).deleteById(pessoa.getId());
    }

    @Test
    @Transactional
    public void searchPessoa() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        when(mockPessoaSearchRepository.search(queryStringQuery("id:" + pessoa.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pessoa), PageRequest.of(0, 1), 1));

        // Search the pessoa
        restPessoaMockMvc.perform(get("/api/_search/pessoas?query=id:" + pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nomeDaMae").value(hasItem(DEFAULT_NOME_DA_MAE)))
            .andExpect(jsonPath("$.[*].nomeDoPai").value(hasItem(DEFAULT_NOME_DO_PAI)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].dataDeNascimento").value(hasItem(DEFAULT_DATA_DE_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nascionalidade").value(hasItem(DEFAULT_NASCIONALIDADE)))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].grauDeInstrucao").value(hasItem(DEFAULT_GRAU_DE_INSTRUCAO.toString())))
            .andExpect(jsonPath("$.[*].nomeUsual").value(hasItem(DEFAULT_NOME_USUAL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
}
