package br.com.basis.madre.web.rest;

import static br.com.basis.madre.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.basis.madre.InternacaoApp;
import br.com.basis.madre.domain.ModalidadeAssistencial;
import br.com.basis.madre.repository.ModalidadeAssistencialRepository;
import br.com.basis.madre.repository.search.ModalidadeAssistencialSearchRepository;
import br.com.basis.madre.service.ModalidadeAssistencialService;
import br.com.basis.madre.service.dto.ModalidadeAssistencialDTO;
import br.com.basis.madre.service.mapper.ModalidadeAssistencialMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

/**
 * Integration tests for the {@link ModalidadeAssistencialResource} REST controller.
 */
@SpringBootTest(classes = InternacaoApp.class)
public class ModalidadeAssistencialResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ModalidadeAssistencialRepository modalidadeAssistencialRepository;

    @Autowired
    private ModalidadeAssistencialMapper modalidadeAssistencialMapper;

    @Autowired
    private ModalidadeAssistencialService modalidadeAssistencialService;

    /**
     * This repository is mocked in the br.com.basis.madre.repository.search test package.
     *
     * @see br.com.basis.madre.repository.search.ModalidadeAssistencialSearchRepositoryMockConfiguration
     */
    @Autowired
    private ModalidadeAssistencialSearchRepository mockModalidadeAssistencialSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restModalidadeAssistencialMockMvc;

    private ModalidadeAssistencial modalidadeAssistencial;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModalidadeAssistencialResource modalidadeAssistencialResource = new ModalidadeAssistencialResource(
            modalidadeAssistencialService);
        this.restModalidadeAssistencialMockMvc = MockMvcBuilders
            .standaloneSetup(modalidadeAssistencialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static ModalidadeAssistencial createEntity(EntityManager em) {
        ModalidadeAssistencial modalidadeAssistencial = new ModalidadeAssistencial()
            .nome(DEFAULT_NOME);
        return modalidadeAssistencial;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static ModalidadeAssistencial createUpdatedEntity(EntityManager em) {
        ModalidadeAssistencial modalidadeAssistencial = new ModalidadeAssistencial()
            .nome(UPDATED_NOME);
        return modalidadeAssistencial;
    }

    @BeforeEach
    public void initTest() {
        modalidadeAssistencial = createEntity(em);
    }

    @Test
    @Transactional
    public void createModalidadeAssistencial() throws Exception {
        int databaseSizeBeforeCreate = modalidadeAssistencialRepository.findAll().size();

        // Create the ModalidadeAssistencial
        ModalidadeAssistencialDTO modalidadeAssistencialDTO = modalidadeAssistencialMapper
            .toDto(modalidadeAssistencial);
        restModalidadeAssistencialMockMvc.perform(post("/api/modalidade-assistencials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeAssistencialDTO)))
            .andExpect(status().isCreated());

        // Validate the ModalidadeAssistencial in the database
        List<ModalidadeAssistencial> modalidadeAssistencialList = modalidadeAssistencialRepository
            .findAll();
        assertThat(modalidadeAssistencialList).hasSize(databaseSizeBeforeCreate + 1);
        ModalidadeAssistencial testModalidadeAssistencial = modalidadeAssistencialList
            .get(modalidadeAssistencialList.size() - 1);
        assertThat(testModalidadeAssistencial.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the ModalidadeAssistencial in Elasticsearch
        verify(mockModalidadeAssistencialSearchRepository, times(1))
            .save(testModalidadeAssistencial);
    }

    @Test
    @Transactional
    public void createModalidadeAssistencialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modalidadeAssistencialRepository.findAll().size();

        // Create the ModalidadeAssistencial with an existing ID
        modalidadeAssistencial.setId(1L);
        ModalidadeAssistencialDTO modalidadeAssistencialDTO = modalidadeAssistencialMapper
            .toDto(modalidadeAssistencial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModalidadeAssistencialMockMvc.perform(post("/api/modalidade-assistencials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeAssistencialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModalidadeAssistencial in the database
        List<ModalidadeAssistencial> modalidadeAssistencialList = modalidadeAssistencialRepository
            .findAll();
        assertThat(modalidadeAssistencialList).hasSize(databaseSizeBeforeCreate);

        // Validate the ModalidadeAssistencial in Elasticsearch
        verify(mockModalidadeAssistencialSearchRepository, times(0)).save(modalidadeAssistencial);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = modalidadeAssistencialRepository.findAll().size();
        // set the field null
        modalidadeAssistencial.setNome(null);

        // Create the ModalidadeAssistencial, which fails.
        ModalidadeAssistencialDTO modalidadeAssistencialDTO = modalidadeAssistencialMapper
            .toDto(modalidadeAssistencial);

        restModalidadeAssistencialMockMvc.perform(post("/api/modalidade-assistencials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeAssistencialDTO)))
            .andExpect(status().isBadRequest());

        List<ModalidadeAssistencial> modalidadeAssistencialList = modalidadeAssistencialRepository
            .findAll();
        assertThat(modalidadeAssistencialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModalidadeAssistencials() throws Exception {
        // Initialize the database
        modalidadeAssistencialRepository.saveAndFlush(modalidadeAssistencial);

        // Get all the modalidadeAssistencialList
        restModalidadeAssistencialMockMvc.perform(get("/api/modalidade-assistencials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(
                jsonPath("$.[*].id").value(hasItem(modalidadeAssistencial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void getModalidadeAssistencial() throws Exception {
        // Initialize the database
        modalidadeAssistencialRepository.saveAndFlush(modalidadeAssistencial);

        // Get the modalidadeAssistencial
        restModalidadeAssistencialMockMvc
            .perform(get("/api/modalidade-assistencials/{id}", modalidadeAssistencial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modalidadeAssistencial.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingModalidadeAssistencial() throws Exception {
        // Get the modalidadeAssistencial
        restModalidadeAssistencialMockMvc
            .perform(get("/api/modalidade-assistencials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModalidadeAssistencial() throws Exception {
        // Initialize the database
        modalidadeAssistencialRepository.saveAndFlush(modalidadeAssistencial);

        int databaseSizeBeforeUpdate = modalidadeAssistencialRepository.findAll().size();

        // Update the modalidadeAssistencial
        ModalidadeAssistencial updatedModalidadeAssistencial = modalidadeAssistencialRepository
            .findById(modalidadeAssistencial.getId()).get();
        // Disconnect from session so that the updates on updatedModalidadeAssistencial are not directly saved in db
        em.detach(updatedModalidadeAssistencial);
        updatedModalidadeAssistencial
            .nome(UPDATED_NOME);
        ModalidadeAssistencialDTO modalidadeAssistencialDTO = modalidadeAssistencialMapper
            .toDto(updatedModalidadeAssistencial);

        restModalidadeAssistencialMockMvc.perform(put("/api/modalidade-assistencials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeAssistencialDTO)))
            .andExpect(status().isOk());

        // Validate the ModalidadeAssistencial in the database
        List<ModalidadeAssistencial> modalidadeAssistencialList = modalidadeAssistencialRepository
            .findAll();
        assertThat(modalidadeAssistencialList).hasSize(databaseSizeBeforeUpdate);
        ModalidadeAssistencial testModalidadeAssistencial = modalidadeAssistencialList
            .get(modalidadeAssistencialList.size() - 1);
        assertThat(testModalidadeAssistencial.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the ModalidadeAssistencial in Elasticsearch
        verify(mockModalidadeAssistencialSearchRepository, times(1))
            .save(testModalidadeAssistencial);
    }

    @Test
    @Transactional
    public void updateNonExistingModalidadeAssistencial() throws Exception {
        int databaseSizeBeforeUpdate = modalidadeAssistencialRepository.findAll().size();

        // Create the ModalidadeAssistencial
        ModalidadeAssistencialDTO modalidadeAssistencialDTO = modalidadeAssistencialMapper
            .toDto(modalidadeAssistencial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModalidadeAssistencialMockMvc.perform(put("/api/modalidade-assistencials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadeAssistencialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModalidadeAssistencial in the database
        List<ModalidadeAssistencial> modalidadeAssistencialList = modalidadeAssistencialRepository
            .findAll();
        assertThat(modalidadeAssistencialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ModalidadeAssistencial in Elasticsearch
        verify(mockModalidadeAssistencialSearchRepository, times(0)).save(modalidadeAssistencial);
    }

    @Test
    @Transactional
    public void deleteModalidadeAssistencial() throws Exception {
        // Initialize the database
        modalidadeAssistencialRepository.saveAndFlush(modalidadeAssistencial);

        int databaseSizeBeforeDelete = modalidadeAssistencialRepository.findAll().size();

        // Delete the modalidadeAssistencial
        restModalidadeAssistencialMockMvc
            .perform(delete("/api/modalidade-assistencials/{id}", modalidadeAssistencial.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModalidadeAssistencial> modalidadeAssistencialList = modalidadeAssistencialRepository
            .findAll();
        assertThat(modalidadeAssistencialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ModalidadeAssistencial in Elasticsearch
        verify(mockModalidadeAssistencialSearchRepository, times(1))
            .deleteById(modalidadeAssistencial.getId());
    }

    @Test
    @Transactional
    public void searchModalidadeAssistencial() throws Exception {
        // Initialize the database
        modalidadeAssistencialRepository.saveAndFlush(modalidadeAssistencial);
        when(mockModalidadeAssistencialSearchRepository
            .search(queryStringQuery("id:" + modalidadeAssistencial.getId()),
                PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(modalidadeAssistencial),
                PageRequest.of(0, 1), 1));
        // Search the modalidadeAssistencial
        restModalidadeAssistencialMockMvc.perform(
            get("/api/_search/modalidade-assistencials?query=id:" + modalidadeAssistencial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(
                jsonPath("$.[*].id").value(hasItem(modalidadeAssistencial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModalidadeAssistencial.class);
        ModalidadeAssistencial modalidadeAssistencial1 = new ModalidadeAssistencial();
        modalidadeAssistencial1.setId(1L);
        ModalidadeAssistencial modalidadeAssistencial2 = new ModalidadeAssistencial();
        modalidadeAssistencial2.setId(modalidadeAssistencial1.getId());
        assertThat(modalidadeAssistencial1).isEqualTo(modalidadeAssistencial2);
        modalidadeAssistencial2.setId(2L);
        assertThat(modalidadeAssistencial1).isNotEqualTo(modalidadeAssistencial2);
        modalidadeAssistencial1.setId(null);
        assertThat(modalidadeAssistencial1).isNotEqualTo(modalidadeAssistencial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModalidadeAssistencialDTO.class);
        ModalidadeAssistencialDTO modalidadeAssistencialDTO1 = new ModalidadeAssistencialDTO();
        modalidadeAssistencialDTO1.setId(1L);
        ModalidadeAssistencialDTO modalidadeAssistencialDTO2 = new ModalidadeAssistencialDTO();
        assertThat(modalidadeAssistencialDTO1).isNotEqualTo(modalidadeAssistencialDTO2);
        modalidadeAssistencialDTO2.setId(modalidadeAssistencialDTO1.getId());
        assertThat(modalidadeAssistencialDTO1).isEqualTo(modalidadeAssistencialDTO2);
        modalidadeAssistencialDTO2.setId(2L);
        assertThat(modalidadeAssistencialDTO1).isNotEqualTo(modalidadeAssistencialDTO2);
        modalidadeAssistencialDTO1.setId(null);
        assertThat(modalidadeAssistencialDTO1).isNotEqualTo(modalidadeAssistencialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modalidadeAssistencialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modalidadeAssistencialMapper.fromId(null)).isNull();
    }
}
