package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.MadresegurancaApp;
import br.com.basis.madre.seguranca.domain.Telefone;
import br.com.basis.madre.seguranca.repository.TelefoneRepository;
import br.com.basis.madre.seguranca.repository.search.TelefoneSearchRepository;
import br.com.basis.madre.seguranca.service.TelefoneService;
import br.com.basis.madre.seguranca.service.dto.TelefoneDTO;
import br.com.basis.madre.seguranca.service.mapper.TelefoneMapper;

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

import br.com.basis.madre.seguranca.domain.enumeration.TipoDoContato;
/**
 * Integration tests for the {@link TelefoneResource} REST controller.
 */
@SpringBootTest(classes = MadresegurancaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TelefoneResourceIT {

    private static final String DEFAULT_DDD = "AAAAAAAAAA";
    private static final String UPDATED_DDD = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final TipoDoContato DEFAULT_TIPO = TipoDoContato.CELULAR;
    private static final TipoDoContato UPDATED_TIPO = TipoDoContato.RESIDENCIAL;

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_RAMAL = "AAAAAAAAAA";
    private static final String UPDATED_RAMAL = "BBBBBBBBBB";

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private TelefoneService telefoneService;

    /**
     * This repository is mocked in the br.com.basis.madre.seguranca.repository.search test package.
     *
     * @see br.com.basis.madre.seguranca.repository.search.TelefoneSearchRepositoryMockConfiguration
     */
    @Autowired
    private TelefoneSearchRepository mockTelefoneSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoneMockMvc;

    private Telefone telefone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .ddd(DEFAULT_DDD)
            .numero(DEFAULT_NUMERO)
            .tipo(DEFAULT_TIPO)
            .observacao(DEFAULT_OBSERVACAO)
            .ramal(DEFAULT_RAMAL);
        return telefone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createUpdatedEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .ddd(UPDATED_DDD)
            .numero(UPDATED_NUMERO)
            .tipo(UPDATED_TIPO)
            .observacao(UPDATED_OBSERVACAO)
            .ramal(UPDATED_RAMAL);
        return telefone;
    }

    @BeforeEach
    public void initTest() {
        telefone = createEntity(em);
    }

    @Test
    @Transactional
    public void createTelefone() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();
        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);
        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate + 1);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getDdd()).isEqualTo(DEFAULT_DDD);
        assertThat(testTelefone.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTelefone.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTelefone.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testTelefone.getRamal()).isEqualTo(DEFAULT_RAMAL);

        // Validate the Telefone in Elasticsearch
        verify(mockTelefoneSearchRepository, times(1)).save(testTelefone);
    }

    @Test
    @Transactional
    public void createTelefoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();

        // Create the Telefone with an existing ID
        telefone.setId(1L);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate);

        // Validate the Telefone in Elasticsearch
        verify(mockTelefoneSearchRepository, times(0)).save(telefone);
    }


    @Test
    @Transactional
    public void getAllTelefones() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList
        restTelefoneMockMvc.perform(get("/api/telefones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefone.getId().intValue())))
            .andExpect(jsonPath("$.[*].ddd").value(hasItem(DEFAULT_DDD)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].ramal").value(hasItem(DEFAULT_RAMAL)));
    }
    
    @Test
    @Transactional
    public void getTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get the telefone
        restTelefoneMockMvc.perform(get("/api/telefones/{id}", telefone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefone.getId().intValue()))
            .andExpect(jsonPath("$.ddd").value(DEFAULT_DDD))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.ramal").value(DEFAULT_RAMAL));
    }
    @Test
    @Transactional
    public void getNonExistingTelefone() throws Exception {
        // Get the telefone
        restTelefoneMockMvc.perform(get("/api/telefones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone
        Telefone updatedTelefone = telefoneRepository.findById(telefone.getId()).get();
        // Disconnect from session so that the updates on updatedTelefone are not directly saved in db
        em.detach(updatedTelefone);
        updatedTelefone
            .ddd(UPDATED_DDD)
            .numero(UPDATED_NUMERO)
            .tipo(UPDATED_TIPO)
            .observacao(UPDATED_OBSERVACAO)
            .ramal(UPDATED_RAMAL);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(updatedTelefone);

        restTelefoneMockMvc.perform(put("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getDdd()).isEqualTo(UPDATED_DDD);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTelefone.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testTelefone.getRamal()).isEqualTo(UPDATED_RAMAL);

        // Validate the Telefone in Elasticsearch
        verify(mockTelefoneSearchRepository, times(1)).save(testTelefone);
    }

    @Test
    @Transactional
    public void updateNonExistingTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoneMockMvc.perform(put("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Telefone in Elasticsearch
        verify(mockTelefoneSearchRepository, times(0)).save(telefone);
    }

    @Test
    @Transactional
    public void deleteTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeDelete = telefoneRepository.findAll().size();

        // Delete the telefone
        restTelefoneMockMvc.perform(delete("/api/telefones/{id}", telefone.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Telefone in Elasticsearch
        verify(mockTelefoneSearchRepository, times(1)).deleteById(telefone.getId());
    }

    @Test
    @Transactional
    public void searchTelefone() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);
        when(mockTelefoneSearchRepository.search(queryStringQuery("id:" + telefone.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(telefone), PageRequest.of(0, 1), 1));

        // Search the telefone
        restTelefoneMockMvc.perform(get("/api/_search/telefones?query=id:" + telefone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefone.getId().intValue())))
            .andExpect(jsonPath("$.[*].ddd").value(hasItem(DEFAULT_DDD)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].ramal").value(hasItem(DEFAULT_RAMAL)));
    }
}
