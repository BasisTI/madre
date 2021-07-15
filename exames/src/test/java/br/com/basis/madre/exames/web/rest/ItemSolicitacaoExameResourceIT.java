package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.MadreexamesApp;
import br.com.basis.madre.exames.domain.ItemSolicitacaoExame;
import br.com.basis.madre.exames.repository.ItemSolicitacaoExameRepository;
import br.com.basis.madre.exames.service.ItemSolicitacaoExameService;
import br.com.basis.madre.exames.service.dto.ItemSolicitacaoExameDTO;
import br.com.basis.madre.exames.service.mapper.ItemSolicitacaoExameMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.basis.madre.exames.domain.enumeration.Situacao;
/**
 * Integration tests for the {@link ItemSolicitacaoExameResource} REST controller.
 */
@SpringBootTest(classes = MadreexamesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ItemSolicitacaoExameResourceIT {

    private static final Boolean DEFAULT_URGENTE = false;
    private static final Boolean UPDATED_URGENTE = true;

    private static final LocalDate DEFAULT_DATA_PROGRAMADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PROGRAMADA = LocalDate.now(ZoneId.systemDefault());

    private static final Situacao DEFAULT_SITUACAO = Situacao.A_COLETAR;
    private static final Situacao UPDATED_SITUACAO = Situacao.AREA_EXECUTORA;

    @Autowired
    private ItemSolicitacaoExameRepository itemSolicitacaoExameRepository;

    @Autowired
    private ItemSolicitacaoExameMapper itemSolicitacaoExameMapper;

    @Autowired
    private ItemSolicitacaoExameService itemSolicitacaoExameService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemSolicitacaoExameMockMvc;

    private ItemSolicitacaoExame itemSolicitacaoExame;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemSolicitacaoExame createEntity(EntityManager em) {
        ItemSolicitacaoExame itemSolicitacaoExame = new ItemSolicitacaoExame()
            .urgente(DEFAULT_URGENTE)
            .dataProgramada(DEFAULT_DATA_PROGRAMADA)
            .situacao(DEFAULT_SITUACAO);
        return itemSolicitacaoExame;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemSolicitacaoExame createUpdatedEntity(EntityManager em) {
        ItemSolicitacaoExame itemSolicitacaoExame = new ItemSolicitacaoExame()
            .urgente(UPDATED_URGENTE)
            .dataProgramada(UPDATED_DATA_PROGRAMADA)
            .situacao(UPDATED_SITUACAO);
        return itemSolicitacaoExame;
    }

    @BeforeEach
    public void initTest() {
        itemSolicitacaoExame = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemSolicitacaoExame() throws Exception {
        int databaseSizeBeforeCreate = itemSolicitacaoExameRepository.findAll().size();
        // Create the ItemSolicitacaoExame
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO = itemSolicitacaoExameMapper.toDto(itemSolicitacaoExame);
        restItemSolicitacaoExameMockMvc.perform(post("/api/item-solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemSolicitacaoExameDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemSolicitacaoExame in the database
        List<ItemSolicitacaoExame> itemSolicitacaoExameList = itemSolicitacaoExameRepository.findAll();
        assertThat(itemSolicitacaoExameList).hasSize(databaseSizeBeforeCreate + 1);
        ItemSolicitacaoExame testItemSolicitacaoExame = itemSolicitacaoExameList.get(itemSolicitacaoExameList.size() - 1);
        assertThat(testItemSolicitacaoExame.isUrgente()).isEqualTo(DEFAULT_URGENTE);
        assertThat(testItemSolicitacaoExame.getDataProgramada()).isEqualTo(DEFAULT_DATA_PROGRAMADA);
        assertThat(testItemSolicitacaoExame.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
    }

    @Test
    @Transactional
    public void createItemSolicitacaoExameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemSolicitacaoExameRepository.findAll().size();

        // Create the ItemSolicitacaoExame with an existing ID
        itemSolicitacaoExame.setId(1L);
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO = itemSolicitacaoExameMapper.toDto(itemSolicitacaoExame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemSolicitacaoExameMockMvc.perform(post("/api/item-solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemSolicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemSolicitacaoExame in the database
        List<ItemSolicitacaoExame> itemSolicitacaoExameList = itemSolicitacaoExameRepository.findAll();
        assertThat(itemSolicitacaoExameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUrgenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemSolicitacaoExameRepository.findAll().size();
        // set the field null
        itemSolicitacaoExame.setUrgente(null);

        // Create the ItemSolicitacaoExame, which fails.
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO = itemSolicitacaoExameMapper.toDto(itemSolicitacaoExame);


        restItemSolicitacaoExameMockMvc.perform(post("/api/item-solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemSolicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        List<ItemSolicitacaoExame> itemSolicitacaoExameList = itemSolicitacaoExameRepository.findAll();
        assertThat(itemSolicitacaoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataProgramadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemSolicitacaoExameRepository.findAll().size();
        // set the field null
        itemSolicitacaoExame.setDataProgramada(null);

        // Create the ItemSolicitacaoExame, which fails.
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO = itemSolicitacaoExameMapper.toDto(itemSolicitacaoExame);


        restItemSolicitacaoExameMockMvc.perform(post("/api/item-solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemSolicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        List<ItemSolicitacaoExame> itemSolicitacaoExameList = itemSolicitacaoExameRepository.findAll();
        assertThat(itemSolicitacaoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemSolicitacaoExameRepository.findAll().size();
        // set the field null
        itemSolicitacaoExame.setSituacao(null);

        // Create the ItemSolicitacaoExame, which fails.
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO = itemSolicitacaoExameMapper.toDto(itemSolicitacaoExame);


        restItemSolicitacaoExameMockMvc.perform(post("/api/item-solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemSolicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        List<ItemSolicitacaoExame> itemSolicitacaoExameList = itemSolicitacaoExameRepository.findAll();
        assertThat(itemSolicitacaoExameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemSolicitacaoExames() throws Exception {
        // Initialize the database
        itemSolicitacaoExameRepository.saveAndFlush(itemSolicitacaoExame);

        // Get all the itemSolicitacaoExameList
        restItemSolicitacaoExameMockMvc.perform(get("/api/item-solicitacao-exames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemSolicitacaoExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].urgente").value(hasItem(DEFAULT_URGENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataProgramada").value(hasItem(DEFAULT_DATA_PROGRAMADA.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getItemSolicitacaoExame() throws Exception {
        // Initialize the database
        itemSolicitacaoExameRepository.saveAndFlush(itemSolicitacaoExame);

        // Get the itemSolicitacaoExame
        restItemSolicitacaoExameMockMvc.perform(get("/api/item-solicitacao-exames/{id}", itemSolicitacaoExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemSolicitacaoExame.getId().intValue()))
            .andExpect(jsonPath("$.urgente").value(DEFAULT_URGENTE.booleanValue()))
            .andExpect(jsonPath("$.dataProgramada").value(DEFAULT_DATA_PROGRAMADA.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingItemSolicitacaoExame() throws Exception {
        // Get the itemSolicitacaoExame
        restItemSolicitacaoExameMockMvc.perform(get("/api/item-solicitacao-exames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemSolicitacaoExame() throws Exception {
        // Initialize the database
        itemSolicitacaoExameRepository.saveAndFlush(itemSolicitacaoExame);

        int databaseSizeBeforeUpdate = itemSolicitacaoExameRepository.findAll().size();

        // Update the itemSolicitacaoExame
        ItemSolicitacaoExame updatedItemSolicitacaoExame = itemSolicitacaoExameRepository.findById(itemSolicitacaoExame.getId()).get();
        // Disconnect from session so that the updates on updatedItemSolicitacaoExame are not directly saved in db
        em.detach(updatedItemSolicitacaoExame);
        updatedItemSolicitacaoExame
            .urgente(UPDATED_URGENTE)
            .dataProgramada(UPDATED_DATA_PROGRAMADA)
            .situacao(UPDATED_SITUACAO);
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO = itemSolicitacaoExameMapper.toDto(updatedItemSolicitacaoExame);

        restItemSolicitacaoExameMockMvc.perform(put("/api/item-solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemSolicitacaoExameDTO)))
            .andExpect(status().isOk());

        // Validate the ItemSolicitacaoExame in the database
        List<ItemSolicitacaoExame> itemSolicitacaoExameList = itemSolicitacaoExameRepository.findAll();
        assertThat(itemSolicitacaoExameList).hasSize(databaseSizeBeforeUpdate);
        ItemSolicitacaoExame testItemSolicitacaoExame = itemSolicitacaoExameList.get(itemSolicitacaoExameList.size() - 1);
        assertThat(testItemSolicitacaoExame.isUrgente()).isEqualTo(UPDATED_URGENTE);
        assertThat(testItemSolicitacaoExame.getDataProgramada()).isEqualTo(UPDATED_DATA_PROGRAMADA);
        assertThat(testItemSolicitacaoExame.getSituacao()).isEqualTo(UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingItemSolicitacaoExame() throws Exception {
        int databaseSizeBeforeUpdate = itemSolicitacaoExameRepository.findAll().size();

        // Create the ItemSolicitacaoExame
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO = itemSolicitacaoExameMapper.toDto(itemSolicitacaoExame);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemSolicitacaoExameMockMvc.perform(put("/api/item-solicitacao-exames")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemSolicitacaoExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemSolicitacaoExame in the database
        List<ItemSolicitacaoExame> itemSolicitacaoExameList = itemSolicitacaoExameRepository.findAll();
        assertThat(itemSolicitacaoExameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemSolicitacaoExame() throws Exception {
        // Initialize the database
        itemSolicitacaoExameRepository.saveAndFlush(itemSolicitacaoExame);

        int databaseSizeBeforeDelete = itemSolicitacaoExameRepository.findAll().size();

        // Delete the itemSolicitacaoExame
        restItemSolicitacaoExameMockMvc.perform(delete("/api/item-solicitacao-exames/{id}", itemSolicitacaoExame.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemSolicitacaoExame> itemSolicitacaoExameList = itemSolicitacaoExameRepository.findAll();
        assertThat(itemSolicitacaoExameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchItemSolicitacaoExame() throws Exception {
        // Initialize the database
        itemSolicitacaoExameRepository.saveAndFlush(itemSolicitacaoExame);

        // Search the itemSolicitacaoExame
        restItemSolicitacaoExameMockMvc.perform(get("/api/_search/item-solicitacao-exames?query=id:" + itemSolicitacaoExame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemSolicitacaoExame.getId().intValue())))
            .andExpect(jsonPath("$.[*].urgente").value(hasItem(DEFAULT_URGENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataProgramada").value(hasItem(DEFAULT_DATA_PROGRAMADA.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }
}
