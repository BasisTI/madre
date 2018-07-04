//package br.com.basis.madre.pacientes.web.rest;
//
//import br.com.basis.madre.pacientes.PacientesApp;
//
//import br.com.basis.madre.pacientes.domain.Paciente;
//import br.com.basis.madre.pacientes.repository.PacienteRepository;
//import br.com.basis.madre.pacientes.service.PacienteService;
//import br.com.basis.madre.pacientes.repository.search.PacienteSearchRepository;
//import br.com.basis.madre.pacientes.web.rest.errors.ExceptionTranslator;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.List;
//
//import static br.com.basis.madre.pacientes.web.rest.TestUtil.createFormattingConversionService;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Test class for the PacienteResource REST controller.
// *
// * @see PacienteResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = PacientesApp.class)
//public class PacienteResourceIntTest {
//
//    private static final String DEFAULT_RG = "AAAAAAAAAA";
//    private static final String UPDATED_RG = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CPF = "AAAAAAAAAAA";
//    private static final String UPDATED_CPF = "BBBBBBBBBBB";
//
//    private static final String DEFAULT_SEXO = "A";
//    private static final String UPDATED_SEXO = "B";
//
//    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
//
//    private static final String DEFAULT_CEP = "AAAAAAAAAA";
//    private static final String UPDATED_CEP = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PRONTUARIO = "AAAAAAAAAA";
//    private static final String UPDATED_PRONTUARIO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NOME_PACIENTE = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_PACIENTE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NOME_SOCIAL = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_SOCIAL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_RACA_COR = "AAAAAAAAAA";
//    private static final String UPDATED_RACA_COR = "BBBBBBBBBB";
//
//    private static final String DEFAULT_ESTADO_CIVIL = "AAAAAAAAAA";
//    private static final String UPDATED_ESTADO_CIVIL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NOME_PAI = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_PAI = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NOME_MAE = "AAAAAAAAAA";
//    private static final String UPDATED_NOME_MAE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NACIONALIDADE = "AAAAAAAAAA";
//    private static final String UPDATED_NACIONALIDADE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CARTAO_SUS = "AAAAAAAAAAAAAAAAAAAA";
//    private static final String UPDATED_CARTAO_SUS = "BBBBBBBBBBBBBBBBBBBB";
//
//    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
//    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
//    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
//    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
//    private static final String UPDATED_CIDADE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_ESTADO = "AA";
//    private static final String UPDATED_ESTADO = "BB";
//
//    private static final String DEFAULT_TELEFONE_PRINCIPAL = "AAAAAAAAAA";
//    private static final String UPDATED_TELEFONE_PRINCIPAL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_TELEFONE_ALTERNATIVO = "AAAAAAAAAA";
//    private static final String UPDATED_TELEFONE_ALTERNATIVO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_EMAIL_PRINCIPAL = "AAAAAAAAAA";
//    private static final String UPDATED_EMAIL_PRINCIPAL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_EMAIL_ALTERNATIVO = "AAAAAAAAAA";
//    private static final String UPDATED_EMAIL_ALTERNATIVO = "BBBBBBBBBB";
//
//    @Autowired
//    private PacienteRepository pacienteRepository;
//
//    @Autowired
//    private PacienteService pacienteService;
//
//    @Autowired
//    private PacienteSearchRepository pacienteSearchRepository;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    private MockMvc restPacienteMockMvc;
//
//    private Paciente paciente;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final PacienteResource pacienteResource = new PacienteResource(pacienteService);
//        this.restPacienteMockMvc = MockMvcBuilders.standaloneSetup(pacienteResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Paciente createEntity(EntityManager em) {
//        Paciente paciente = new Paciente()
//            .rg(DEFAULT_RG)
//            .cpf(DEFAULT_CPF)
//            .sexo(DEFAULT_SEXO)
//            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
//            .cep(DEFAULT_CEP)
//            .prontuario(DEFAULT_PRONTUARIO)
//            .nomePaciente(DEFAULT_NOME_PACIENTE)
//            .nomeSocial(DEFAULT_NOME_SOCIAL)
//            .racaCor(DEFAULT_RACA_COR)
//            .estadoCivil(DEFAULT_ESTADO_CIVIL)
//            .nomePai(DEFAULT_NOME_PAI)
//            .nomeMae(DEFAULT_NOME_MAE)
//            .nacionalidade(DEFAULT_NACIONALIDADE)
//            .cartaoSus(DEFAULT_CARTAO_SUS)
//            .endereco(DEFAULT_ENDERECO)
//            .complemento(DEFAULT_COMPLEMENTO)
//            .bairro(DEFAULT_BAIRRO)
//            .cidade(DEFAULT_CIDADE)
//            .estado(DEFAULT_ESTADO)
//            .telefonePrincipal(DEFAULT_TELEFONE_PRINCIPAL)
//            .telefoneAlternativo(DEFAULT_TELEFONE_ALTERNATIVO)
//            .emailPrincipal(DEFAULT_EMAIL_PRINCIPAL)
//            .emailAlternativo(DEFAULT_EMAIL_ALTERNATIVO);
//        return paciente;
//    }
//
//    @Before
//    public void initTest() {
//        pacienteSearchRepository.deleteAll();
//        paciente = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createPaciente() throws Exception {
//        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();
//
//        // Create the Paciente
//        restPacienteMockMvc.perform(post("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isCreated());
//
//        // Validate the Paciente in the database
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
//        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
//        assertThat(testPaciente.getRg()).isEqualTo(DEFAULT_RG);
//        assertThat(testPaciente.getCpf()).isEqualTo(DEFAULT_CPF);
//        assertThat(testPaciente.getSexo()).isEqualTo(DEFAULT_SEXO);
//        assertThat(testPaciente.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
//        assertThat(testPaciente.getCep()).isEqualTo(DEFAULT_CEP);
//        assertThat(testPaciente.getProntuario()).isEqualTo(DEFAULT_PRONTUARIO);
//        assertThat(testPaciente.getNomePaciente()).isEqualTo(DEFAULT_NOME_PACIENTE);
//        assertThat(testPaciente.getNomeSocial()).isEqualTo(DEFAULT_NOME_SOCIAL);
//        assertThat(testPaciente.getRacaCor()).isEqualTo(DEFAULT_RACA_COR);
//        assertThat(testPaciente.getEstadoCivil()).isEqualTo(DEFAULT_ESTADO_CIVIL);
//        assertThat(testPaciente.getNomePai()).isEqualTo(DEFAULT_NOME_PAI);
//        assertThat(testPaciente.getNomeMae()).isEqualTo(DEFAULT_NOME_MAE);
//        assertThat(testPaciente.getNacionalidade()).isEqualTo(DEFAULT_NACIONALIDADE);
//        assertThat(testPaciente.getCartaoSus()).isEqualTo(DEFAULT_CARTAO_SUS);
//        assertThat(testPaciente.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
//        assertThat(testPaciente.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
//        assertThat(testPaciente.getBairro()).isEqualTo(DEFAULT_BAIRRO);
//        assertThat(testPaciente.getCidade()).isEqualTo(DEFAULT_CIDADE);
//        assertThat(testPaciente.getEstado()).isEqualTo(DEFAULT_ESTADO);
//        assertThat(testPaciente.getTelefonePrincipal()).isEqualTo(DEFAULT_TELEFONE_PRINCIPAL);
//        assertThat(testPaciente.getTelefoneAlternativo()).isEqualTo(DEFAULT_TELEFONE_ALTERNATIVO);
//        assertThat(testPaciente.getEmailPrincipal()).isEqualTo(DEFAULT_EMAIL_PRINCIPAL);
//        assertThat(testPaciente.getEmailAlternativo()).isEqualTo(DEFAULT_EMAIL_ALTERNATIVO);
//
//        // Validate the Paciente in Elasticsearch
//        Paciente pacienteEs = pacienteSearchRepository.findOne(testPaciente.getId());
//        assertThat(pacienteEs).isEqualToIgnoringGivenFields(testPaciente);
//    }
//
//    @Test
//    @Transactional
//    public void createPacienteWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();
//
//        // Create the Paciente with an existing ID
//        paciente.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restPacienteMockMvc.perform(post("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Paciente in the database
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void checkRgIsRequired() throws Exception {
//        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
//        // set the field null
//        paciente.setRg(null);
//
//        // Create the Paciente, which fails.
//
//        restPacienteMockMvc.perform(post("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isBadRequest());
//
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkCpfIsRequired() throws Exception {
//        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
//        // set the field null
//        paciente.setCpf(null);
//
//        // Create the Paciente, which fails.
//
//        restPacienteMockMvc.perform(post("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isBadRequest());
//
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDataNascimentoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
//        // set the field null
//        paciente.setDataNascimento(null);
//
//        // Create the Paciente, which fails.
//
//        restPacienteMockMvc.perform(post("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isBadRequest());
//
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkCepIsRequired() throws Exception {
//        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
//        // set the field null
//        paciente.setCep(null);
//
//        // Create the Paciente, which fails.
//
//        restPacienteMockMvc.perform(post("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isBadRequest());
//
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkNomeMaeIsRequired() throws Exception {
//        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
//        // set the field null
//        paciente.setNomeMae(null);
//
//        // Create the Paciente, which fails.
//
//        restPacienteMockMvc.perform(post("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isBadRequest());
//
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkCartaoSusIsRequired() throws Exception {
//        int databaseSizeBeforeTest = pacienteRepository.findAll().size();
//        // set the field null
//        paciente.setCartaoSus(null);
//
//        // Create the Paciente, which fails.
//
//        restPacienteMockMvc.perform(post("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isBadRequest());
//
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllPacientes() throws Exception {
//        // Initialize the database
//        pacienteRepository.saveAndFlush(paciente);
//
//        // Get all the pacienteList
//        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
//            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG.toString())))
//            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
//            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
//            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
//            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
//            .andExpect(jsonPath("$.[*].prontuario").value(hasItem(DEFAULT_PRONTUARIO.toString())))
//            .andExpect(jsonPath("$.[*].nomePaciente").value(hasItem(DEFAULT_NOME_PACIENTE.toString())))
//            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL.toString())))
//            .andExpect(jsonPath("$.[*].racaCor").value(hasItem(DEFAULT_RACA_COR.toString())))
//            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
//            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI.toString())))
//            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE.toString())))
//            .andExpect(jsonPath("$.[*].nacionalidade").value(hasItem(DEFAULT_NACIONALIDADE.toString())))
//            .andExpect(jsonPath("$.[*].cartaoSus").value(hasItem(DEFAULT_CARTAO_SUS.toString())))
//            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
//            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())))
//            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
//            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
//            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
//            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL.toString())))
//            .andExpect(jsonPath("$.[*].telefoneAlternativo").value(hasItem(DEFAULT_TELEFONE_ALTERNATIVO.toString())))
//            .andExpect(jsonPath("$.[*].emailPrincipal").value(hasItem(DEFAULT_EMAIL_PRINCIPAL.toString())))
//            .andExpect(jsonPath("$.[*].emailAlternativo").value(hasItem(DEFAULT_EMAIL_ALTERNATIVO.toString())));
//    }
//
//    @Test
//    @Transactional
//    public void getPaciente() throws Exception {
//        // Initialize the database
//        pacienteRepository.saveAndFlush(paciente);
//
//        // Get the paciente
//        restPacienteMockMvc.perform(get("/api/pacientes/{id}", paciente.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
//            .andExpect(jsonPath("$.rg").value(DEFAULT_RG.toString()))
//            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
//            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
//            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
//            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
//            .andExpect(jsonPath("$.prontuario").value(DEFAULT_PRONTUARIO.toString()))
//            .andExpect(jsonPath("$.nomePaciente").value(DEFAULT_NOME_PACIENTE.toString()))
//            .andExpect(jsonPath("$.nomeSocial").value(DEFAULT_NOME_SOCIAL.toString()))
//            .andExpect(jsonPath("$.racaCor").value(DEFAULT_RACA_COR.toString()))
//            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL.toString()))
//            .andExpect(jsonPath("$.nomePai").value(DEFAULT_NOME_PAI.toString()))
//            .andExpect(jsonPath("$.nomeMae").value(DEFAULT_NOME_MAE.toString()))
//            .andExpect(jsonPath("$.nacionalidade").value(DEFAULT_NACIONALIDADE.toString()))
//            .andExpect(jsonPath("$.cartaoSus").value(DEFAULT_CARTAO_SUS.toString()))
//            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
//            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO.toString()))
//            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO.toString()))
//            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE.toString()))
//            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
//            .andExpect(jsonPath("$.telefonePrincipal").value(DEFAULT_TELEFONE_PRINCIPAL.toString()))
//            .andExpect(jsonPath("$.telefoneAlternativo").value(DEFAULT_TELEFONE_ALTERNATIVO.toString()))
//            .andExpect(jsonPath("$.emailPrincipal").value(DEFAULT_EMAIL_PRINCIPAL.toString()))
//            .andExpect(jsonPath("$.emailAlternativo").value(DEFAULT_EMAIL_ALTERNATIVO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingPaciente() throws Exception {
//        // Get the paciente
//        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updatePaciente() throws Exception {
//        // Initialize the database
//        pacienteService.save(paciente);
//
//        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
//
//        // Update the paciente
//        Paciente updatedPaciente = pacienteRepository.findOne(paciente.getId());
//        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
//        em.detach(updatedPaciente);
//        updatedPaciente
//            .rg(UPDATED_RG)
//            .cpf(UPDATED_CPF)
//            .sexo(UPDATED_SEXO)
//            .dataNascimento(UPDATED_DATA_NASCIMENTO)
//            .cep(UPDATED_CEP)
//            .prontuario(UPDATED_PRONTUARIO)
//            .nomePaciente(UPDATED_NOME_PACIENTE)
//            .nomeSocial(UPDATED_NOME_SOCIAL)
//            .racaCor(UPDATED_RACA_COR)
//            .estadoCivil(UPDATED_ESTADO_CIVIL)
//            .nomePai(UPDATED_NOME_PAI)
//            .nomeMae(UPDATED_NOME_MAE)
//            .nacionalidade(UPDATED_NACIONALIDADE)
//            .cartaoSus(UPDATED_CARTAO_SUS)
//            .endereco(UPDATED_ENDERECO)
//            .complemento(UPDATED_COMPLEMENTO)
//            .bairro(UPDATED_BAIRRO)
//            .cidade(UPDATED_CIDADE)
//            .estado(UPDATED_ESTADO)
//            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
//            .telefoneAlternativo(UPDATED_TELEFONE_ALTERNATIVO)
//            .emailPrincipal(UPDATED_EMAIL_PRINCIPAL)
//            .emailAlternativo(UPDATED_EMAIL_ALTERNATIVO);
//
//        restPacienteMockMvc.perform(put("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedPaciente)))
//            .andExpect(status().isOk());
//
//        // Validate the Paciente in the database
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
//        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
//        assertThat(testPaciente.getRg()).isEqualTo(UPDATED_RG);
//        assertThat(testPaciente.getCpf()).isEqualTo(UPDATED_CPF);
//        assertThat(testPaciente.getSexo()).isEqualTo(UPDATED_SEXO);
//        assertThat(testPaciente.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
//        assertThat(testPaciente.getCep()).isEqualTo(UPDATED_CEP);
//        assertThat(testPaciente.getProntuario()).isEqualTo(UPDATED_PRONTUARIO);
//        assertThat(testPaciente.getNomePaciente()).isEqualTo(UPDATED_NOME_PACIENTE);
//        assertThat(testPaciente.getNomeSocial()).isEqualTo(UPDATED_NOME_SOCIAL);
//        assertThat(testPaciente.getRacaCor()).isEqualTo(UPDATED_RACA_COR);
//        assertThat(testPaciente.getEstadoCivil()).isEqualTo(UPDATED_ESTADO_CIVIL);
//        assertThat(testPaciente.getNomePai()).isEqualTo(UPDATED_NOME_PAI);
//        assertThat(testPaciente.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
//        assertThat(testPaciente.getNacionalidade()).isEqualTo(UPDATED_NACIONALIDADE);
//        assertThat(testPaciente.getCartaoSus()).isEqualTo(UPDATED_CARTAO_SUS);
//        assertThat(testPaciente.getEndereco()).isEqualTo(UPDATED_ENDERECO);
//        assertThat(testPaciente.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
//        assertThat(testPaciente.getBairro()).isEqualTo(UPDATED_BAIRRO);
//        assertThat(testPaciente.getCidade()).isEqualTo(UPDATED_CIDADE);
//        assertThat(testPaciente.getEstado()).isEqualTo(UPDATED_ESTADO);
//        assertThat(testPaciente.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
//        assertThat(testPaciente.getTelefoneAlternativo()).isEqualTo(UPDATED_TELEFONE_ALTERNATIVO);
//        assertThat(testPaciente.getEmailPrincipal()).isEqualTo(UPDATED_EMAIL_PRINCIPAL);
//        assertThat(testPaciente.getEmailAlternativo()).isEqualTo(UPDATED_EMAIL_ALTERNATIVO);
//
//        // Validate the Paciente in Elasticsearch
//        Paciente pacienteEs = pacienteSearchRepository.findOne(testPaciente.getId());
//        assertThat(pacienteEs).isEqualToIgnoringGivenFields(testPaciente);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingPaciente() throws Exception {
//        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();
//
//        // Create the Paciente
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restPacienteMockMvc.perform(put("/api/pacientes")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(paciente)))
//            .andExpect(status().isCreated());
//
//        // Validate the Paciente in the database
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    @Transactional
//    public void deletePaciente() throws Exception {
//        // Initialize the database
//        pacienteService.save(paciente);
//
//        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();
//
//        // Get the paciente
//        restPacienteMockMvc.perform(delete("/api/pacientes/{id}", paciente.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate Elasticsearch is empty
//        boolean pacienteExistsInEs = pacienteSearchRepository.exists(paciente.getId());
//        assertThat(pacienteExistsInEs).isFalse();
//
//        // Validate the database is empty
//        List<Paciente> pacienteList = pacienteRepository.findAll();
//        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void searchPaciente() throws Exception {
//        // Initialize the database
//        pacienteService.save(paciente);
//
//        // Search the paciente
//        restPacienteMockMvc.perform(get("/api/_search/pacientes?query=id:" + paciente.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
//            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG.toString())))
//            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
//            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
//            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
//            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
//            .andExpect(jsonPath("$.[*].prontuario").value(hasItem(DEFAULT_PRONTUARIO.toString())))
//            .andExpect(jsonPath("$.[*].nomePaciente").value(hasItem(DEFAULT_NOME_PACIENTE.toString())))
//            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL.toString())))
//            .andExpect(jsonPath("$.[*].racaCor").value(hasItem(DEFAULT_RACA_COR.toString())))
//            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
//            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI.toString())))
//            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE.toString())))
//            .andExpect(jsonPath("$.[*].nacionalidade").value(hasItem(DEFAULT_NACIONALIDADE.toString())))
//            .andExpect(jsonPath("$.[*].cartaoSus").value(hasItem(DEFAULT_CARTAO_SUS.toString())))
//            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
//            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())))
//            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
//            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
//            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
//            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL.toString())))
//            .andExpect(jsonPath("$.[*].telefoneAlternativo").value(hasItem(DEFAULT_TELEFONE_ALTERNATIVO.toString())))
//            .andExpect(jsonPath("$.[*].emailPrincipal").value(hasItem(DEFAULT_EMAIL_PRINCIPAL.toString())))
//            .andExpect(jsonPath("$.[*].emailAlternativo").value(hasItem(DEFAULT_EMAIL_ALTERNATIVO.toString())));
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Paciente.class);
//        Paciente paciente1 = new Paciente();
//        paciente1.setId(1L);
//        Paciente paciente2 = new Paciente();
//        paciente2.setId(paciente1.getId());
//        assertThat(paciente1).isEqualTo(paciente2);
//        paciente2.setId(2L);
//        assertThat(paciente1).isNotEqualTo(paciente2);
//        paciente1.setId(null);
//        assertThat(paciente1).isNotEqualTo(paciente2);
//    }
//}
