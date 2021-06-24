package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.MadreexamesApp;
import br.com.basis.madre.exames.domain.LaboratorioExterno;
import br.com.basis.madre.exames.repository.LaboratorioExternoRepository;
import br.com.basis.madre.exames.service.LaboratorioExternoService;
import br.com.basis.madre.exames.service.dto.LaboratorioExternoDTO;
import br.com.basis.madre.exames.service.mapper.LaboratorioExternoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.exames.domain.enumeration.ConvenioPlano;
import br.com.basis.madre.exames.domain.enumeration.FormaEnvio;
/**
 * Integration tests for the {@link LaboratorioExternoResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LaboratorioExternoResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CEP = 1;
    private static final Integer UPDATED_CEP = 2;

    private static final Integer DEFAULT_TELEFONE = 1;
    private static final Integer UPDATED_TELEFONE = 2;

    private static final Integer DEFAULT_FAX = 1;
    private static final Integer UPDATED_FAX = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CGC = "AAAAAAAAAA";
    private static final String UPDATED_CGC = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_CONVENIO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_CONVENIO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_PLANO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_PLANO = "BBBBBBBBBB";

    private static final ConvenioPlano DEFAULT_CONVENIO_PLANO = ConvenioPlano.SUS_internacao;
    private static final ConvenioPlano UPDATED_CONVENIO_PLANO = ConvenioPlano.SUS_planoAmbulatorio;

    private static final FormaEnvio DEFAULT_FORMA_ENVIO = FormaEnvio.Correio;
    private static final FormaEnvio UPDATED_FORMA_ENVIO = FormaEnvio.Fax;

    @Autowired
    private LaboratorioExternoRepository laboratorioExternoRepository;

    @Autowired
    private LaboratorioExternoMapper laboratorioExternoMapper;

    @Autowired
    private LaboratorioExternoService laboratorioExternoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLaboratorioExternoMockMvc;

    private LaboratorioExterno laboratorioExterno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LaboratorioExterno createEntity(EntityManager em) {
        LaboratorioExterno laboratorioExterno = new LaboratorioExterno()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA)
            .endereco(DEFAULT_ENDERECO)
            .municipio(DEFAULT_MUNICIPIO)
            .cep(DEFAULT_CEP)
            .telefone(DEFAULT_TELEFONE)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .cgc(DEFAULT_CGC)
            .codigoConvenio(DEFAULT_CODIGO_CONVENIO)
            .codigoPlano(DEFAULT_CODIGO_PLANO)
            .convenioPlano(DEFAULT_CONVENIO_PLANO)
            .formaEnvio(DEFAULT_FORMA_ENVIO);
        return laboratorioExterno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LaboratorioExterno createUpdatedEntity(EntityManager em) {
        LaboratorioExterno laboratorioExterno = new LaboratorioExterno()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .endereco(UPDATED_ENDERECO)
            .municipio(UPDATED_MUNICIPIO)
            .cep(UPDATED_CEP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .cgc(UPDATED_CGC)
            .codigoConvenio(UPDATED_CODIGO_CONVENIO)
            .codigoPlano(UPDATED_CODIGO_PLANO)
            .convenioPlano(UPDATED_CONVENIO_PLANO)
            .formaEnvio(UPDATED_FORMA_ENVIO);
        return laboratorioExterno;
    }

    @BeforeEach
    public void initTest() {
        laboratorioExterno = createEntity(em);
    }

    @Test
    @Transactional
    public void createLaboratorioExterno() throws Exception {
        int databaseSizeBeforeCreate = laboratorioExternoRepository.findAll().size();
        // Create the LaboratorioExterno
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);
        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isCreated());

        // Validate the LaboratorioExterno in the database
        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeCreate + 1);
        LaboratorioExterno testLaboratorioExterno = laboratorioExternoList.get(laboratorioExternoList.size() - 1);
        assertThat(testLaboratorioExterno.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLaboratorioExterno.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLaboratorioExterno.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testLaboratorioExterno.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testLaboratorioExterno.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testLaboratorioExterno.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testLaboratorioExterno.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testLaboratorioExterno.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testLaboratorioExterno.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLaboratorioExterno.getCgc()).isEqualTo(DEFAULT_CGC);
        assertThat(testLaboratorioExterno.getCodigoConvenio()).isEqualTo(DEFAULT_CODIGO_CONVENIO);
        assertThat(testLaboratorioExterno.getCodigoPlano()).isEqualTo(DEFAULT_CODIGO_PLANO);
        assertThat(testLaboratorioExterno.getConvenioPlano()).isEqualTo(DEFAULT_CONVENIO_PLANO);
        assertThat(testLaboratorioExterno.getFormaEnvio()).isEqualTo(DEFAULT_FORMA_ENVIO);
    }

    @Test
    @Transactional
    public void createLaboratorioExternoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = laboratorioExternoRepository.findAll().size();

        // Create the LaboratorioExterno with an existing ID
        laboratorioExterno.setId(1L);
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LaboratorioExterno in the database
        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setCodigo(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setNome(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setSigla(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setEndereco(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMunicipioIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setMunicipio(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setCep(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setTelefone(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setFax(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setEmail(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCgcIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setCgc(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoConvenioIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setCodigoConvenio(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoPlanoIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioExternoRepository.findAll().size();
        // set the field null
        laboratorioExterno.setCodigoPlano(null);

        // Create the LaboratorioExterno, which fails.
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);


        restLaboratorioExternoMockMvc.perform(post("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLaboratorioExternos() throws Exception {
        // Initialize the database
        laboratorioExternoRepository.saveAndFlush(laboratorioExterno);

        // Get all the laboratorioExternoList
        restLaboratorioExternoMockMvc.perform(get("/api/laboratorio-externos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratorioExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cgc").value(hasItem(DEFAULT_CGC)))
            .andExpect(jsonPath("$.[*].codigoConvenio").value(hasItem(DEFAULT_CODIGO_CONVENIO)))
            .andExpect(jsonPath("$.[*].codigoPlano").value(hasItem(DEFAULT_CODIGO_PLANO)))
            .andExpect(jsonPath("$.[*].convenioPlano").value(hasItem(DEFAULT_CONVENIO_PLANO.toString())))
            .andExpect(jsonPath("$.[*].formaEnvio").value(hasItem(DEFAULT_FORMA_ENVIO.toString())));
    }
    
    @Test
    @Transactional
    public void getLaboratorioExterno() throws Exception {
        // Initialize the database
        laboratorioExternoRepository.saveAndFlush(laboratorioExterno);

        // Get the laboratorioExterno
        restLaboratorioExternoMockMvc.perform(get("/api/laboratorio-externos/{id}", laboratorioExterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(laboratorioExterno.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.cgc").value(DEFAULT_CGC))
            .andExpect(jsonPath("$.codigoConvenio").value(DEFAULT_CODIGO_CONVENIO))
            .andExpect(jsonPath("$.codigoPlano").value(DEFAULT_CODIGO_PLANO))
            .andExpect(jsonPath("$.convenioPlano").value(DEFAULT_CONVENIO_PLANO.toString()))
            .andExpect(jsonPath("$.formaEnvio").value(DEFAULT_FORMA_ENVIO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingLaboratorioExterno() throws Exception {
        // Get the laboratorioExterno
        restLaboratorioExternoMockMvc.perform(get("/api/laboratorio-externos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLaboratorioExterno() throws Exception {
        // Initialize the database
        laboratorioExternoRepository.saveAndFlush(laboratorioExterno);

        int databaseSizeBeforeUpdate = laboratorioExternoRepository.findAll().size();

        // Update the laboratorioExterno
        LaboratorioExterno updatedLaboratorioExterno = laboratorioExternoRepository.findById(laboratorioExterno.getId()).get();
        // Disconnect from session so that the updates on updatedLaboratorioExterno are not directly saved in db
        em.detach(updatedLaboratorioExterno);
        updatedLaboratorioExterno
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .endereco(UPDATED_ENDERECO)
            .municipio(UPDATED_MUNICIPIO)
            .cep(UPDATED_CEP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .cgc(UPDATED_CGC)
            .codigoConvenio(UPDATED_CODIGO_CONVENIO)
            .codigoPlano(UPDATED_CODIGO_PLANO)
            .convenioPlano(UPDATED_CONVENIO_PLANO)
            .formaEnvio(UPDATED_FORMA_ENVIO);
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(updatedLaboratorioExterno);

        restLaboratorioExternoMockMvc.perform(put("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isOk());

        // Validate the LaboratorioExterno in the database
        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeUpdate);
        LaboratorioExterno testLaboratorioExterno = laboratorioExternoList.get(laboratorioExternoList.size() - 1);
        assertThat(testLaboratorioExterno.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLaboratorioExterno.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLaboratorioExterno.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testLaboratorioExterno.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testLaboratorioExterno.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testLaboratorioExterno.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testLaboratorioExterno.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testLaboratorioExterno.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testLaboratorioExterno.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLaboratorioExterno.getCgc()).isEqualTo(UPDATED_CGC);
        assertThat(testLaboratorioExterno.getCodigoConvenio()).isEqualTo(UPDATED_CODIGO_CONVENIO);
        assertThat(testLaboratorioExterno.getCodigoPlano()).isEqualTo(UPDATED_CODIGO_PLANO);
        assertThat(testLaboratorioExterno.getConvenioPlano()).isEqualTo(UPDATED_CONVENIO_PLANO);
        assertThat(testLaboratorioExterno.getFormaEnvio()).isEqualTo(UPDATED_FORMA_ENVIO);
    }

    @Test
    @Transactional
    public void updateNonExistingLaboratorioExterno() throws Exception {
        int databaseSizeBeforeUpdate = laboratorioExternoRepository.findAll().size();

        // Create the LaboratorioExterno
        LaboratorioExternoDTO laboratorioExternoDTO = laboratorioExternoMapper.toDto(laboratorioExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratorioExternoMockMvc.perform(put("/api/laboratorio-externos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(laboratorioExternoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LaboratorioExterno in the database
        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLaboratorioExterno() throws Exception {
        // Initialize the database
        laboratorioExternoRepository.saveAndFlush(laboratorioExterno);

        int databaseSizeBeforeDelete = laboratorioExternoRepository.findAll().size();

        // Delete the laboratorioExterno
        restLaboratorioExternoMockMvc.perform(delete("/api/laboratorio-externos/{id}", laboratorioExterno.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LaboratorioExterno> laboratorioExternoList = laboratorioExternoRepository.findAll();
        assertThat(laboratorioExternoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLaboratorioExterno() throws Exception {
        // Initialize the database
        laboratorioExternoRepository.saveAndFlush(laboratorioExterno);

        // Search the laboratorioExterno
        restLaboratorioExternoMockMvc.perform(get("/api/_search/laboratorio-externos?query=id:" + laboratorioExterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratorioExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cgc").value(hasItem(DEFAULT_CGC)))
            .andExpect(jsonPath("$.[*].codigoConvenio").value(hasItem(DEFAULT_CODIGO_CONVENIO)))
            .andExpect(jsonPath("$.[*].codigoPlano").value(hasItem(DEFAULT_CODIGO_PLANO)))
            .andExpect(jsonPath("$.[*].convenioPlano").value(hasItem(DEFAULT_CONVENIO_PLANO.toString())))
            .andExpect(jsonPath("$.[*].formaEnvio").value(hasItem(DEFAULT_FORMA_ENVIO.toString())));
    }
}
