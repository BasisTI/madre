package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.MadreexamesApp;
import br.com.basis.madre.madreexames.domain.Cadaver;
import br.com.basis.madre.madreexames.repository.CadaverRepository;
import br.com.basis.madre.madreexames.repository.search.CadaverSearchRepository;
import br.com.basis.madre.madreexames.service.CadaverService;
import br.com.basis.madre.madreexames.service.dto.CadaverDTO;
import br.com.basis.madre.madreexames.service.mapper.CadaverMapper;

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

import br.com.basis.madre.madreexames.domain.enumeration.Raca;
import br.com.basis.madre.madreexames.domain.enumeration.GrupoSanguineo;
import br.com.basis.madre.madreexames.domain.enumeration.ConvenioPlano;
/**
 * Integration tests for the {@link CadaverResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CadaverResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Raca DEFAULT_RACA = Raca.Branca;
    private static final Raca UPDATED_RACA = Raca.Preta;

    private static final GrupoSanguineo DEFAULT_GRUPO_SANGUINEO = GrupoSanguineo.O;
    private static final GrupoSanguineo UPDATED_GRUPO_SANGUINEO = GrupoSanguineo.A;

    private static final LocalDate DEFAULT_DATA_REMOCAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_REMOCAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CAUSA_OBITO = "AAAAAAAAAA";
    private static final String UPDATED_CAUSA_OBITO = "BBBBBBBBBB";

    private static final String DEFAULT_REALIZADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_REALIZADO_POR = "BBBBBBBBBB";

    private static final String DEFAULT_LIDO_POR = "AAAAAAAAAA";
    private static final String UPDATED_LIDO_POR = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDENCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_RETIRADA = "AAAAAAAAAA";
    private static final String UPDATED_RETIRADA = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_PLANO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_PLANO = "BBBBBBBBBB";

    private static final ConvenioPlano DEFAULT_CONVENIO_PLANO = ConvenioPlano.SUS_internacao;
    private static final ConvenioPlano UPDATED_CONVENIO_PLANO = ConvenioPlano.SUS_planoAmbulatorio;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private CadaverRepository cadaverRepository;

    @Autowired
    private CadaverMapper cadaverMapper;

    @Autowired
    private CadaverService cadaverService;

    /**
     * This repository is mocked in the br.com.basis.madre.madreexames.repository.search test package.
     *
     * @see br.com.basis.madre.madreexames.repository.search.CadaverSearchRepositoryMockConfiguration
     */
    @Autowired
    private CadaverSearchRepository mockCadaverSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCadaverMockMvc;

    private Cadaver cadaver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cadaver createEntity(EntityManager em) {
        Cadaver cadaver = new Cadaver()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .raca(DEFAULT_RACA)
            .grupoSanguineo(DEFAULT_GRUPO_SANGUINEO)
            .dataRemocao(DEFAULT_DATA_REMOCAO)
            .causaObito(DEFAULT_CAUSA_OBITO)
            .realizadoPor(DEFAULT_REALIZADO_POR)
            .lidoPor(DEFAULT_LIDO_POR)
            .procedencia(DEFAULT_PROCEDENCIA)
            .retirada(DEFAULT_RETIRADA)
            .codigoPlano(DEFAULT_CODIGO_PLANO)
            .convenioPlano(DEFAULT_CONVENIO_PLANO)
            .observacao(DEFAULT_OBSERVACAO);
        return cadaver;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cadaver createUpdatedEntity(EntityManager em) {
        Cadaver cadaver = new Cadaver()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .raca(UPDATED_RACA)
            .grupoSanguineo(UPDATED_GRUPO_SANGUINEO)
            .dataRemocao(UPDATED_DATA_REMOCAO)
            .causaObito(UPDATED_CAUSA_OBITO)
            .realizadoPor(UPDATED_REALIZADO_POR)
            .lidoPor(UPDATED_LIDO_POR)
            .procedencia(UPDATED_PROCEDENCIA)
            .retirada(UPDATED_RETIRADA)
            .codigoPlano(UPDATED_CODIGO_PLANO)
            .convenioPlano(UPDATED_CONVENIO_PLANO)
            .observacao(UPDATED_OBSERVACAO);
        return cadaver;
    }

    @BeforeEach
    public void initTest() {
        cadaver = createEntity(em);
    }

    @Test
    @Transactional
    public void createCadaver() throws Exception {
        int databaseSizeBeforeCreate = cadaverRepository.findAll().size();
        // Create the Cadaver
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);
        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isCreated());

        // Validate the Cadaver in the database
        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeCreate + 1);
        Cadaver testCadaver = cadaverList.get(cadaverList.size() - 1);
        assertThat(testCadaver.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testCadaver.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCadaver.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testCadaver.getRaca()).isEqualTo(DEFAULT_RACA);
        assertThat(testCadaver.getGrupoSanguineo()).isEqualTo(DEFAULT_GRUPO_SANGUINEO);
        assertThat(testCadaver.getDataRemocao()).isEqualTo(DEFAULT_DATA_REMOCAO);
        assertThat(testCadaver.getCausaObito()).isEqualTo(DEFAULT_CAUSA_OBITO);
        assertThat(testCadaver.getRealizadoPor()).isEqualTo(DEFAULT_REALIZADO_POR);
        assertThat(testCadaver.getLidoPor()).isEqualTo(DEFAULT_LIDO_POR);
        assertThat(testCadaver.getProcedencia()).isEqualTo(DEFAULT_PROCEDENCIA);
        assertThat(testCadaver.getRetirada()).isEqualTo(DEFAULT_RETIRADA);
        assertThat(testCadaver.getCodigoPlano()).isEqualTo(DEFAULT_CODIGO_PLANO);
        assertThat(testCadaver.getConvenioPlano()).isEqualTo(DEFAULT_CONVENIO_PLANO);
        assertThat(testCadaver.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the Cadaver in Elasticsearch
        verify(mockCadaverSearchRepository, times(1)).save(testCadaver);
    }

    @Test
    @Transactional
    public void createCadaverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cadaverRepository.findAll().size();

        // Create the Cadaver with an existing ID
        cadaver.setId(1L);
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cadaver in the database
        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cadaver in Elasticsearch
        verify(mockCadaverSearchRepository, times(0)).save(cadaver);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setCodigo(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setNome(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setDataNascimento(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataRemocaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setDataRemocao(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCausaObitoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setCausaObito(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRealizadoPorIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setRealizadoPor(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLidoPorIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setLidoPor(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcedenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setProcedencia(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetiradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setRetirada(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoPlanoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setCodigoPlano(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObservacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadaverRepository.findAll().size();
        // set the field null
        cadaver.setObservacao(null);

        // Create the Cadaver, which fails.
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);


        restCadaverMockMvc.perform(post("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCadavers() throws Exception {
        // Initialize the database
        cadaverRepository.saveAndFlush(cadaver);

        // Get all the cadaverList
        restCadaverMockMvc.perform(get("/api/cadavers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cadaver.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].raca").value(hasItem(DEFAULT_RACA.toString())))
            .andExpect(jsonPath("$.[*].grupoSanguineo").value(hasItem(DEFAULT_GRUPO_SANGUINEO.toString())))
            .andExpect(jsonPath("$.[*].dataRemocao").value(hasItem(DEFAULT_DATA_REMOCAO.toString())))
            .andExpect(jsonPath("$.[*].causaObito").value(hasItem(DEFAULT_CAUSA_OBITO)))
            .andExpect(jsonPath("$.[*].realizadoPor").value(hasItem(DEFAULT_REALIZADO_POR)))
            .andExpect(jsonPath("$.[*].lidoPor").value(hasItem(DEFAULT_LIDO_POR)))
            .andExpect(jsonPath("$.[*].procedencia").value(hasItem(DEFAULT_PROCEDENCIA)))
            .andExpect(jsonPath("$.[*].retirada").value(hasItem(DEFAULT_RETIRADA)))
            .andExpect(jsonPath("$.[*].codigoPlano").value(hasItem(DEFAULT_CODIGO_PLANO)))
            .andExpect(jsonPath("$.[*].convenioPlano").value(hasItem(DEFAULT_CONVENIO_PLANO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }
    
    @Test
    @Transactional
    public void getCadaver() throws Exception {
        // Initialize the database
        cadaverRepository.saveAndFlush(cadaver);

        // Get the cadaver
        restCadaverMockMvc.perform(get("/api/cadavers/{id}", cadaver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cadaver.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.raca").value(DEFAULT_RACA.toString()))
            .andExpect(jsonPath("$.grupoSanguineo").value(DEFAULT_GRUPO_SANGUINEO.toString()))
            .andExpect(jsonPath("$.dataRemocao").value(DEFAULT_DATA_REMOCAO.toString()))
            .andExpect(jsonPath("$.causaObito").value(DEFAULT_CAUSA_OBITO))
            .andExpect(jsonPath("$.realizadoPor").value(DEFAULT_REALIZADO_POR))
            .andExpect(jsonPath("$.lidoPor").value(DEFAULT_LIDO_POR))
            .andExpect(jsonPath("$.procedencia").value(DEFAULT_PROCEDENCIA))
            .andExpect(jsonPath("$.retirada").value(DEFAULT_RETIRADA))
            .andExpect(jsonPath("$.codigoPlano").value(DEFAULT_CODIGO_PLANO))
            .andExpect(jsonPath("$.convenioPlano").value(DEFAULT_CONVENIO_PLANO.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }
    @Test
    @Transactional
    public void getNonExistingCadaver() throws Exception {
        // Get the cadaver
        restCadaverMockMvc.perform(get("/api/cadavers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCadaver() throws Exception {
        // Initialize the database
        cadaverRepository.saveAndFlush(cadaver);

        int databaseSizeBeforeUpdate = cadaverRepository.findAll().size();

        // Update the cadaver
        Cadaver updatedCadaver = cadaverRepository.findById(cadaver.getId()).get();
        // Disconnect from session so that the updates on updatedCadaver are not directly saved in db
        em.detach(updatedCadaver);
        updatedCadaver
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .raca(UPDATED_RACA)
            .grupoSanguineo(UPDATED_GRUPO_SANGUINEO)
            .dataRemocao(UPDATED_DATA_REMOCAO)
            .causaObito(UPDATED_CAUSA_OBITO)
            .realizadoPor(UPDATED_REALIZADO_POR)
            .lidoPor(UPDATED_LIDO_POR)
            .procedencia(UPDATED_PROCEDENCIA)
            .retirada(UPDATED_RETIRADA)
            .codigoPlano(UPDATED_CODIGO_PLANO)
            .convenioPlano(UPDATED_CONVENIO_PLANO)
            .observacao(UPDATED_OBSERVACAO);
        CadaverDTO cadaverDTO = cadaverMapper.toDto(updatedCadaver);

        restCadaverMockMvc.perform(put("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isOk());

        // Validate the Cadaver in the database
        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeUpdate);
        Cadaver testCadaver = cadaverList.get(cadaverList.size() - 1);
        assertThat(testCadaver.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testCadaver.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCadaver.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testCadaver.getRaca()).isEqualTo(UPDATED_RACA);
        assertThat(testCadaver.getGrupoSanguineo()).isEqualTo(UPDATED_GRUPO_SANGUINEO);
        assertThat(testCadaver.getDataRemocao()).isEqualTo(UPDATED_DATA_REMOCAO);
        assertThat(testCadaver.getCausaObito()).isEqualTo(UPDATED_CAUSA_OBITO);
        assertThat(testCadaver.getRealizadoPor()).isEqualTo(UPDATED_REALIZADO_POR);
        assertThat(testCadaver.getLidoPor()).isEqualTo(UPDATED_LIDO_POR);
        assertThat(testCadaver.getProcedencia()).isEqualTo(UPDATED_PROCEDENCIA);
        assertThat(testCadaver.getRetirada()).isEqualTo(UPDATED_RETIRADA);
        assertThat(testCadaver.getCodigoPlano()).isEqualTo(UPDATED_CODIGO_PLANO);
        assertThat(testCadaver.getConvenioPlano()).isEqualTo(UPDATED_CONVENIO_PLANO);
        assertThat(testCadaver.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the Cadaver in Elasticsearch
        verify(mockCadaverSearchRepository, times(1)).save(testCadaver);
    }

    @Test
    @Transactional
    public void updateNonExistingCadaver() throws Exception {
        int databaseSizeBeforeUpdate = cadaverRepository.findAll().size();

        // Create the Cadaver
        CadaverDTO cadaverDTO = cadaverMapper.toDto(cadaver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCadaverMockMvc.perform(put("/api/cadavers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cadaverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cadaver in the database
        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cadaver in Elasticsearch
        verify(mockCadaverSearchRepository, times(0)).save(cadaver);
    }

    @Test
    @Transactional
    public void deleteCadaver() throws Exception {
        // Initialize the database
        cadaverRepository.saveAndFlush(cadaver);

        int databaseSizeBeforeDelete = cadaverRepository.findAll().size();

        // Delete the cadaver
        restCadaverMockMvc.perform(delete("/api/cadavers/{id}", cadaver.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cadaver> cadaverList = cadaverRepository.findAll();
        assertThat(cadaverList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cadaver in Elasticsearch
        verify(mockCadaverSearchRepository, times(1)).deleteById(cadaver.getId());
    }

    @Test
    @Transactional
    public void searchCadaver() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        cadaverRepository.saveAndFlush(cadaver);
        when(mockCadaverSearchRepository.search(queryStringQuery("id:" + cadaver.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cadaver), PageRequest.of(0, 1), 1));

        // Search the cadaver
        restCadaverMockMvc.perform(get("/api/_search/cadavers?query=id:" + cadaver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cadaver.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].raca").value(hasItem(DEFAULT_RACA.toString())))
            .andExpect(jsonPath("$.[*].grupoSanguineo").value(hasItem(DEFAULT_GRUPO_SANGUINEO.toString())))
            .andExpect(jsonPath("$.[*].dataRemocao").value(hasItem(DEFAULT_DATA_REMOCAO.toString())))
            .andExpect(jsonPath("$.[*].causaObito").value(hasItem(DEFAULT_CAUSA_OBITO)))
            .andExpect(jsonPath("$.[*].realizadoPor").value(hasItem(DEFAULT_REALIZADO_POR)))
            .andExpect(jsonPath("$.[*].lidoPor").value(hasItem(DEFAULT_LIDO_POR)))
            .andExpect(jsonPath("$.[*].procedencia").value(hasItem(DEFAULT_PROCEDENCIA)))
            .andExpect(jsonPath("$.[*].retirada").value(hasItem(DEFAULT_RETIRADA)))
            .andExpect(jsonPath("$.[*].codigoPlano").value(hasItem(DEFAULT_CODIGO_PLANO)))
            .andExpect(jsonPath("$.[*].convenioPlano").value(hasItem(DEFAULT_CONVENIO_PLANO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }
}
