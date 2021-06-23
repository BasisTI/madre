package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.MadreexamesApp;
import br.com.basis.madre.exames.domain.AtendimentoDiverso;
import br.com.basis.madre.exames.repository.AtendimentoDiversoRepository;
import br.com.basis.madre.exames.service.AtendimentoDiversoService;
import br.com.basis.madre.exames.service.dto.AtendimentoDiversoDTO;
import br.com.basis.madre.exames.service.mapper.AtendimentoDiversoMapper;

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

/**
 * Integration tests for the {@link AtendimentoDiversoResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AtendimentoDiversoResourceIT {

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private AtendimentoDiversoRepository atendimentoDiversoRepository;

    @Autowired
    private AtendimentoDiversoMapper atendimentoDiversoMapper;

    @Autowired
    private AtendimentoDiversoService atendimentoDiversoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtendimentoDiversoMockMvc;

    private AtendimentoDiverso atendimentoDiverso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtendimentoDiverso createEntity(EntityManager em) {
        AtendimentoDiverso atendimentoDiverso = new AtendimentoDiverso()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO);
        return atendimentoDiverso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtendimentoDiverso createUpdatedEntity(EntityManager em) {
        AtendimentoDiverso atendimentoDiverso = new AtendimentoDiverso()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO);
        return atendimentoDiverso;
    }

    @BeforeEach
    public void initTest() {
        atendimentoDiverso = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtendimentoDiverso() throws Exception {
        int databaseSizeBeforeCreate = atendimentoDiversoRepository.findAll().size();
        // Create the AtendimentoDiverso
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);
        restAtendimentoDiversoMockMvc.perform(post("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isCreated());

        // Validate the AtendimentoDiverso in the database
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeCreate + 1);
        AtendimentoDiverso testAtendimentoDiverso = atendimentoDiversoList.get(atendimentoDiversoList.size() - 1);
        assertThat(testAtendimentoDiverso.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testAtendimentoDiverso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createAtendimentoDiversoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atendimentoDiversoRepository.findAll().size();

        // Create the AtendimentoDiverso with an existing ID
        atendimentoDiverso.setId(1L);
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtendimentoDiversoMockMvc.perform(post("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AtendimentoDiverso in the database
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = atendimentoDiversoRepository.findAll().size();
        // set the field null
        atendimentoDiverso.setCodigo(null);

        // Create the AtendimentoDiverso, which fails.
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);


        restAtendimentoDiversoMockMvc.perform(post("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isBadRequest());

        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = atendimentoDiversoRepository.findAll().size();
        // set the field null
        atendimentoDiverso.setDescricao(null);

        // Create the AtendimentoDiverso, which fails.
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);


        restAtendimentoDiversoMockMvc.perform(post("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isBadRequest());

        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAtendimentoDiversos() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        // Get all the atendimentoDiversoList
        restAtendimentoDiversoMockMvc.perform(get("/api/atendimento-diversos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimentoDiverso.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getAtendimentoDiverso() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        // Get the atendimentoDiverso
        restAtendimentoDiversoMockMvc.perform(get("/api/atendimento-diversos/{id}", atendimentoDiverso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atendimentoDiverso.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }
    @Test
    @Transactional
    public void getNonExistingAtendimentoDiverso() throws Exception {
        // Get the atendimentoDiverso
        restAtendimentoDiversoMockMvc.perform(get("/api/atendimento-diversos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtendimentoDiverso() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        int databaseSizeBeforeUpdate = atendimentoDiversoRepository.findAll().size();

        // Update the atendimentoDiverso
        AtendimentoDiverso updatedAtendimentoDiverso = atendimentoDiversoRepository.findById(atendimentoDiverso.getId()).get();
        // Disconnect from session so that the updates on updatedAtendimentoDiverso are not directly saved in db
        em.detach(updatedAtendimentoDiverso);
        updatedAtendimentoDiverso
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO);
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(updatedAtendimentoDiverso);

        restAtendimentoDiversoMockMvc.perform(put("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isOk());

        // Validate the AtendimentoDiverso in the database
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeUpdate);
        AtendimentoDiverso testAtendimentoDiverso = atendimentoDiversoList.get(atendimentoDiversoList.size() - 1);
        assertThat(testAtendimentoDiverso.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testAtendimentoDiverso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAtendimentoDiverso() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoDiversoRepository.findAll().size();

        // Create the AtendimentoDiverso
        AtendimentoDiversoDTO atendimentoDiversoDTO = atendimentoDiversoMapper.toDto(atendimentoDiverso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtendimentoDiversoMockMvc.perform(put("/api/atendimento-diversos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDiversoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AtendimentoDiverso in the database
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAtendimentoDiverso() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        int databaseSizeBeforeDelete = atendimentoDiversoRepository.findAll().size();

        // Delete the atendimentoDiverso
        restAtendimentoDiversoMockMvc.perform(delete("/api/atendimento-diversos/{id}", atendimentoDiverso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AtendimentoDiverso> atendimentoDiversoList = atendimentoDiversoRepository.findAll();
        assertThat(atendimentoDiversoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAtendimentoDiverso() throws Exception {
        // Initialize the database
        atendimentoDiversoRepository.saveAndFlush(atendimentoDiverso);

        // Search the atendimentoDiverso
        restAtendimentoDiversoMockMvc.perform(get("/api/_search/atendimento-diversos?query=id:" + atendimentoDiverso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimentoDiverso.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
}
