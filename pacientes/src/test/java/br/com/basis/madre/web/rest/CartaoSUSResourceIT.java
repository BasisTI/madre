package br.com.basis.madre.web.rest;

import br.com.basis.madre.PacientesApp;
import br.com.basis.madre.domain.CartaoSUS;
import br.com.basis.madre.repository.CartaoSUSRepository;
import br.com.basis.madre.repository.search.CartaoSUSSearchRepository;
import br.com.basis.madre.service.CartaoSUSService;
import br.com.basis.madre.service.dto.CartaoSUSDTO;
import br.com.basis.madre.service.mapper.CartaoSUSMapper;

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

import br.com.basis.madre.domain.enumeration.DocumentoDeReferencia;
/**
 * Integration tests for the {@link CartaoSUSResource} REST controller.
 */
@SpringBootTest(classes = PacientesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CartaoSUSResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final DocumentoDeReferencia DEFAULT_DOCUMENTO_DE_REFERENCIA = DocumentoDeReferencia.APAC;
    private static final DocumentoDeReferencia UPDATED_DOCUMENTO_DE_REFERENCIA = DocumentoDeReferencia.AIH;

    private static final String DEFAULT_CARTAO_NACIONAL_SAUDE_MAE = "AAAAAAAAAA";
    private static final String UPDATED_CARTAO_NACIONAL_SAUDE_MAE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DE_ENTRADA_NO_BRASIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_ENTRADA_NO_BRASIL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_DE_NATURALIZACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DE_NATURALIZACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PORTARIA = "AAAAAAAAAA";
    private static final String UPDATED_PORTARIA = "BBBBBBBBBB";

    @Autowired
    private CartaoSUSRepository cartaoSUSRepository;

    @Autowired
    private CartaoSUSMapper cartaoSUSMapper;

    @Autowired
    private CartaoSUSService cartaoSUSService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.CartaoSUSSearchRepositoryMockConfiguration
     */
    @Autowired
    private CartaoSUSSearchRepository mockCartaoSUSSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartaoSUSMockMvc;

    private CartaoSUS cartaoSUS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartaoSUS createEntity(EntityManager em) {
        CartaoSUS cartaoSUS = new CartaoSUS()
            .numero(DEFAULT_NUMERO)
            .documentoDeReferencia(DEFAULT_DOCUMENTO_DE_REFERENCIA)
            .cartaoNacionalSaudeMae(DEFAULT_CARTAO_NACIONAL_SAUDE_MAE)
            .dataDeEntradaNoBrasil(DEFAULT_DATA_DE_ENTRADA_NO_BRASIL)
            .dataDeNaturalizacao(DEFAULT_DATA_DE_NATURALIZACAO)
            .portaria(DEFAULT_PORTARIA);
        return cartaoSUS;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartaoSUS createUpdatedEntity(EntityManager em) {
        CartaoSUS cartaoSUS = new CartaoSUS()
            .numero(UPDATED_NUMERO)
            .documentoDeReferencia(UPDATED_DOCUMENTO_DE_REFERENCIA)
            .cartaoNacionalSaudeMae(UPDATED_CARTAO_NACIONAL_SAUDE_MAE)
            .dataDeEntradaNoBrasil(UPDATED_DATA_DE_ENTRADA_NO_BRASIL)
            .dataDeNaturalizacao(UPDATED_DATA_DE_NATURALIZACAO)
            .portaria(UPDATED_PORTARIA);
        return cartaoSUS;
    }

    @BeforeEach
    public void initTest() {
        cartaoSUS = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartaoSUS() throws Exception {
        int databaseSizeBeforeCreate = cartaoSUSRepository.findAll().size();

        // Create the CartaoSUS
        CartaoSUSDTO cartaoSUSDTO = cartaoSUSMapper.toDto(cartaoSUS);
        restCartaoSUSMockMvc.perform(post("/api/cartao-suses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartaoSUSDTO)))
            .andExpect(status().isCreated());

        // Validate the CartaoSUS in the database
        List<CartaoSUS> cartaoSUSList = cartaoSUSRepository.findAll();
        assertThat(cartaoSUSList).hasSize(databaseSizeBeforeCreate + 1);
        CartaoSUS testCartaoSUS = cartaoSUSList.get(cartaoSUSList.size() - 1);
        assertThat(testCartaoSUS.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCartaoSUS.getDocumentoDeReferencia()).isEqualTo(DEFAULT_DOCUMENTO_DE_REFERENCIA);
        assertThat(testCartaoSUS.getCartaoNacionalSaudeMae()).isEqualTo(DEFAULT_CARTAO_NACIONAL_SAUDE_MAE);
        assertThat(testCartaoSUS.getDataDeEntradaNoBrasil()).isEqualTo(DEFAULT_DATA_DE_ENTRADA_NO_BRASIL);
        assertThat(testCartaoSUS.getDataDeNaturalizacao()).isEqualTo(DEFAULT_DATA_DE_NATURALIZACAO);
        assertThat(testCartaoSUS.getPortaria()).isEqualTo(DEFAULT_PORTARIA);

        // Validate the CartaoSUS in Elasticsearch
        verify(mockCartaoSUSSearchRepository, times(1)).save(testCartaoSUS);
    }

    @Test
    @Transactional
    public void createCartaoSUSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartaoSUSRepository.findAll().size();

        // Create the CartaoSUS with an existing ID
        cartaoSUS.setId(1L);
        CartaoSUSDTO cartaoSUSDTO = cartaoSUSMapper.toDto(cartaoSUS);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartaoSUSMockMvc.perform(post("/api/cartao-suses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartaoSUSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CartaoSUS in the database
        List<CartaoSUS> cartaoSUSList = cartaoSUSRepository.findAll();
        assertThat(cartaoSUSList).hasSize(databaseSizeBeforeCreate);

        // Validate the CartaoSUS in Elasticsearch
        verify(mockCartaoSUSSearchRepository, times(0)).save(cartaoSUS);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoSUSRepository.findAll().size();
        // set the field null
        cartaoSUS.setNumero(null);

        // Create the CartaoSUS, which fails.
        CartaoSUSDTO cartaoSUSDTO = cartaoSUSMapper.toDto(cartaoSUS);

        restCartaoSUSMockMvc.perform(post("/api/cartao-suses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartaoSUSDTO)))
            .andExpect(status().isBadRequest());

        List<CartaoSUS> cartaoSUSList = cartaoSUSRepository.findAll();
        assertThat(cartaoSUSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartaoSUSES() throws Exception {
        // Initialize the database
        cartaoSUSRepository.saveAndFlush(cartaoSUS);

        // Get all the cartaoSUSList
        restCartaoSUSMockMvc.perform(get("/api/cartao-suses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartaoSUS.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].documentoDeReferencia").value(hasItem(DEFAULT_DOCUMENTO_DE_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].cartaoNacionalSaudeMae").value(hasItem(DEFAULT_CARTAO_NACIONAL_SAUDE_MAE)))
            .andExpect(jsonPath("$.[*].dataDeEntradaNoBrasil").value(hasItem(DEFAULT_DATA_DE_ENTRADA_NO_BRASIL.toString())))
            .andExpect(jsonPath("$.[*].dataDeNaturalizacao").value(hasItem(DEFAULT_DATA_DE_NATURALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].portaria").value(hasItem(DEFAULT_PORTARIA)));
    }
    
    @Test
    @Transactional
    public void getCartaoSUS() throws Exception {
        // Initialize the database
        cartaoSUSRepository.saveAndFlush(cartaoSUS);

        // Get the cartaoSUS
        restCartaoSUSMockMvc.perform(get("/api/cartao-suses/{id}", cartaoSUS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cartaoSUS.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.documentoDeReferencia").value(DEFAULT_DOCUMENTO_DE_REFERENCIA.toString()))
            .andExpect(jsonPath("$.cartaoNacionalSaudeMae").value(DEFAULT_CARTAO_NACIONAL_SAUDE_MAE))
            .andExpect(jsonPath("$.dataDeEntradaNoBrasil").value(DEFAULT_DATA_DE_ENTRADA_NO_BRASIL.toString()))
            .andExpect(jsonPath("$.dataDeNaturalizacao").value(DEFAULT_DATA_DE_NATURALIZACAO.toString()))
            .andExpect(jsonPath("$.portaria").value(DEFAULT_PORTARIA));
    }

    @Test
    @Transactional
    public void getNonExistingCartaoSUS() throws Exception {
        // Get the cartaoSUS
        restCartaoSUSMockMvc.perform(get("/api/cartao-suses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartaoSUS() throws Exception {
        // Initialize the database
        cartaoSUSRepository.saveAndFlush(cartaoSUS);

        int databaseSizeBeforeUpdate = cartaoSUSRepository.findAll().size();

        // Update the cartaoSUS
        CartaoSUS updatedCartaoSUS = cartaoSUSRepository.findById(cartaoSUS.getId()).get();
        // Disconnect from session so that the updates on updatedCartaoSUS are not directly saved in db
        em.detach(updatedCartaoSUS);
        updatedCartaoSUS
            .numero(UPDATED_NUMERO)
            .documentoDeReferencia(UPDATED_DOCUMENTO_DE_REFERENCIA)
            .cartaoNacionalSaudeMae(UPDATED_CARTAO_NACIONAL_SAUDE_MAE)
            .dataDeEntradaNoBrasil(UPDATED_DATA_DE_ENTRADA_NO_BRASIL)
            .dataDeNaturalizacao(UPDATED_DATA_DE_NATURALIZACAO)
            .portaria(UPDATED_PORTARIA);
        CartaoSUSDTO cartaoSUSDTO = cartaoSUSMapper.toDto(updatedCartaoSUS);

        restCartaoSUSMockMvc.perform(put("/api/cartao-suses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartaoSUSDTO)))
            .andExpect(status().isOk());

        // Validate the CartaoSUS in the database
        List<CartaoSUS> cartaoSUSList = cartaoSUSRepository.findAll();
        assertThat(cartaoSUSList).hasSize(databaseSizeBeforeUpdate);
        CartaoSUS testCartaoSUS = cartaoSUSList.get(cartaoSUSList.size() - 1);
        assertThat(testCartaoSUS.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCartaoSUS.getDocumentoDeReferencia()).isEqualTo(UPDATED_DOCUMENTO_DE_REFERENCIA);
        assertThat(testCartaoSUS.getCartaoNacionalSaudeMae()).isEqualTo(UPDATED_CARTAO_NACIONAL_SAUDE_MAE);
        assertThat(testCartaoSUS.getDataDeEntradaNoBrasil()).isEqualTo(UPDATED_DATA_DE_ENTRADA_NO_BRASIL);
        assertThat(testCartaoSUS.getDataDeNaturalizacao()).isEqualTo(UPDATED_DATA_DE_NATURALIZACAO);
        assertThat(testCartaoSUS.getPortaria()).isEqualTo(UPDATED_PORTARIA);

        // Validate the CartaoSUS in Elasticsearch
        verify(mockCartaoSUSSearchRepository, times(1)).save(testCartaoSUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCartaoSUS() throws Exception {
        int databaseSizeBeforeUpdate = cartaoSUSRepository.findAll().size();

        // Create the CartaoSUS
        CartaoSUSDTO cartaoSUSDTO = cartaoSUSMapper.toDto(cartaoSUS);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartaoSUSMockMvc.perform(put("/api/cartao-suses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartaoSUSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CartaoSUS in the database
        List<CartaoSUS> cartaoSUSList = cartaoSUSRepository.findAll();
        assertThat(cartaoSUSList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CartaoSUS in Elasticsearch
        verify(mockCartaoSUSSearchRepository, times(0)).save(cartaoSUS);
    }

    @Test
    @Transactional
    public void deleteCartaoSUS() throws Exception {
        // Initialize the database
        cartaoSUSRepository.saveAndFlush(cartaoSUS);

        int databaseSizeBeforeDelete = cartaoSUSRepository.findAll().size();

        // Delete the cartaoSUS
        restCartaoSUSMockMvc.perform(delete("/api/cartao-suses/{id}", cartaoSUS.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CartaoSUS> cartaoSUSList = cartaoSUSRepository.findAll();
        assertThat(cartaoSUSList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CartaoSUS in Elasticsearch
        verify(mockCartaoSUSSearchRepository, times(1)).deleteById(cartaoSUS.getId());
    }

    @Test
    @Transactional
    public void searchCartaoSUS() throws Exception {
        // Initialize the database
        cartaoSUSRepository.saveAndFlush(cartaoSUS);
        when(mockCartaoSUSSearchRepository.search(queryStringQuery("id:" + cartaoSUS.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cartaoSUS), PageRequest.of(0, 1), 1));
        // Search the cartaoSUS
        restCartaoSUSMockMvc.perform(get("/api/_search/cartao-suses?query=id:" + cartaoSUS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartaoSUS.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].documentoDeReferencia").value(hasItem(DEFAULT_DOCUMENTO_DE_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].cartaoNacionalSaudeMae").value(hasItem(DEFAULT_CARTAO_NACIONAL_SAUDE_MAE)))
            .andExpect(jsonPath("$.[*].dataDeEntradaNoBrasil").value(hasItem(DEFAULT_DATA_DE_ENTRADA_NO_BRASIL.toString())))
            .andExpect(jsonPath("$.[*].dataDeNaturalizacao").value(hasItem(DEFAULT_DATA_DE_NATURALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].portaria").value(hasItem(DEFAULT_PORTARIA)));
    }
}
