package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import br.com.basis.madre.cadastros.domain.TipoPergunta;
import br.com.basis.madre.cadastros.repository.TipoPerguntaRepository;
import br.com.basis.madre.cadastros.service.TipoPerguntaService;
import br.com.basis.madre.cadastros.repository.search.TipoPerguntaSearchRepository;
import br.com.basis.madre.cadastros.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.basis.madre.cadastros.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
* Test class for the TipoPerguntaResource REST controller.
*
* @see TipoPerguntaResource
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class TipoPerguntaResourceIntTest {

   private static final String DEFAULT_ENUNCIADO_PERGUNTA = "AAAAAAAAAA";
   private static final String UPDATED_ENUNCIADO_PERGUNTA = "BBBBBBBBBB";

   @Autowired
   private TipoPerguntaRepository tipoPerguntaRepository;

   @Autowired
   private TipoPerguntaService tipoPerguntaService;

   @Autowired
   private TipoPerguntaSearchRepository tipoPerguntaSearchRepository;

   @Autowired
   private MappingJackson2HttpMessageConverter jacksonMessageConverter;

   @Autowired
   private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

   @Autowired
   private ExceptionTranslator exceptionTranslator;

   @Autowired
   private EntityManager em;

   private MockMvc restTipoPerguntaMockMvc;

   private TipoPergunta tipoPergunta;

   @Before
   public void setup() {
       MockitoAnnotations.initMocks(this);
       final TipoPerguntaResource tipoPerguntaResource = new TipoPerguntaResource(tipoPerguntaService,tipoPerguntaRepository);
       this.restTipoPerguntaMockMvc = MockMvcBuilders.standaloneSetup(tipoPerguntaResource)
           .setCustomArgumentResolvers(pageableArgumentResolver)
           .setControllerAdvice(exceptionTranslator)
           .setConversionService(createFormattingConversionService())
           .setMessageConverters(jacksonMessageConverter).build();
   }

   /**
    * Create an entity for this test.
    *
    * This is a static method, as tests for other entities might also need it,
    * if they test an entity which requires the current entity.
    */
   public static TipoPergunta createEntity(EntityManager em) {
       TipoPergunta tipoPergunta = new TipoPergunta()
           .enunciadoPergunta(DEFAULT_ENUNCIADO_PERGUNTA);
       return tipoPergunta;
   }

   @Before
   public void initTest() {
       tipoPerguntaSearchRepository.deleteAll();
       tipoPergunta = createEntity(em);
   }

   @Test
   @Transactional
   public void createTipoPergunta() throws Exception {
       int databaseSizeBeforeCreate = tipoPerguntaRepository.findAll().size();

       // Create the TipoPergunta
       restTipoPerguntaMockMvc.perform(post("/api/tipo-perguntas")
           .contentType(TestUtil.APPLICATION_JSON_UTF8)
           .content(TestUtil.convertObjectToJsonBytes(tipoPergunta)))
           .andExpect(status().isCreated());

       // Validate the TipoPergunta in the database
       List<TipoPergunta> tipoPerguntaList = tipoPerguntaRepository.findAll();
       assertThat(tipoPerguntaList).hasSize(databaseSizeBeforeCreate + 1);
       TipoPergunta testTipoPergunta = tipoPerguntaList.get(tipoPerguntaList.size() - 1);
       assertThat(testTipoPergunta.getEnunciadoPergunta()).isEqualTo(DEFAULT_ENUNCIADO_PERGUNTA);

       // Validate the TipoPergunta in Elasticsearch
       TipoPergunta tipoPerguntaEs = tipoPerguntaSearchRepository.findOne(testTipoPergunta.getId());
       assertThat(tipoPerguntaEs).isEqualToIgnoringGivenFields(testTipoPergunta);
   }

   @Test
   @Transactional
   public void createTipoPerguntaWithExistingId() throws Exception {
       int databaseSizeBeforeCreate = tipoPerguntaRepository.findAll().size();

       // Create the TipoPergunta with an existing ID
       tipoPergunta.setId(1L);

       // An entity with an existing ID cannot be created, so this API call must fail
       restTipoPerguntaMockMvc.perform(post("/api/tipo-perguntas")
           .contentType(TestUtil.APPLICATION_JSON_UTF8)
           .content(TestUtil.convertObjectToJsonBytes(tipoPergunta)))
           .andExpect(status().isBadRequest());

       // Validate the TipoPergunta in the database
       List<TipoPergunta> tipoPerguntaList = tipoPerguntaRepository.findAll();
       assertThat(tipoPerguntaList).hasSize(databaseSizeBeforeCreate);
   }

   @Test
   @Transactional
   public void checkEnunciadoPerguntaIsRequired() throws Exception {
       int databaseSizeBeforeTest = tipoPerguntaRepository.findAll().size();
       // set the field null
       tipoPergunta.setEnunciadoPergunta(null);

       // Create the TipoPergunta, which fails.

       restTipoPerguntaMockMvc.perform(post("/api/tipo-perguntas")
           .contentType(TestUtil.APPLICATION_JSON_UTF8)
           .content(TestUtil.convertObjectToJsonBytes(tipoPergunta)))
           .andExpect(status().isBadRequest());

       List<TipoPergunta> tipoPerguntaList = tipoPerguntaRepository.findAll();
       assertThat(tipoPerguntaList).hasSize(databaseSizeBeforeTest);
   }

   @Test
   @Transactional
   public void getAllTipoPerguntas() throws Exception {
       // Initialize the database
       tipoPerguntaRepository.saveAndFlush(tipoPergunta);

       // Get all the tipoPerguntaList
       restTipoPerguntaMockMvc.perform(get("/api/tipo-perguntas?sort=id,desc"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPergunta.getId().intValue())))
           .andExpect(jsonPath("$.[*].enunciadoPergunta").value(hasItem(DEFAULT_ENUNCIADO_PERGUNTA.toString())));
   }

   @Test
   @Transactional
   public void getTipoPergunta() throws Exception {
       // Initialize the database
       tipoPerguntaRepository.saveAndFlush(tipoPergunta);

       // Get the tipoPergunta
       restTipoPerguntaMockMvc.perform(get("/api/tipo-perguntas/{id}", tipoPergunta.getId()))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$.id").value(tipoPergunta.getId().intValue()))
           .andExpect(jsonPath("$.enunciadoPergunta").value(DEFAULT_ENUNCIADO_PERGUNTA.toString()));
   }

   @Test
   @Transactional
   public void getNonExistingTipoPergunta() throws Exception {
       // Get the tipoPergunta
       restTipoPerguntaMockMvc.perform(get("/api/tipo-perguntas/{id}", Long.MAX_VALUE))
           .andExpect(status().isNotFound());
   }

   @Test
   @Transactional
   public void updateTipoPergunta() throws Exception {
       // Initialize the database
       tipoPerguntaService.save(tipoPergunta);

       int databaseSizeBeforeUpdate = tipoPerguntaRepository.findAll().size();

       // Update the tipoPergunta
       TipoPergunta updatedTipoPergunta = tipoPerguntaRepository.findOne(tipoPergunta.getId());
       // Disconnect from session so that the updates on updatedTipoPergunta are not directly saved in db
       em.detach(updatedTipoPergunta);
       updatedTipoPergunta
           .enunciadoPergunta(UPDATED_ENUNCIADO_PERGUNTA);

       restTipoPerguntaMockMvc.perform(put("/api/tipo-perguntas")
           .contentType(TestUtil.APPLICATION_JSON_UTF8)
           .content(TestUtil.convertObjectToJsonBytes(updatedTipoPergunta)))
           .andExpect(status().isOk());

       // Validate the TipoPergunta in the database
       List<TipoPergunta> tipoPerguntaList = tipoPerguntaRepository.findAll();
       assertThat(tipoPerguntaList).hasSize(databaseSizeBeforeUpdate);
       TipoPergunta testTipoPergunta = tipoPerguntaList.get(tipoPerguntaList.size() - 1);
       assertThat(testTipoPergunta.getEnunciadoPergunta()).isEqualTo(UPDATED_ENUNCIADO_PERGUNTA);

       // Validate the TipoPergunta in Elasticsearch
       TipoPergunta tipoPerguntaEs = tipoPerguntaSearchRepository.findOne(testTipoPergunta.getId());
       assertThat(tipoPerguntaEs).isEqualToIgnoringGivenFields(testTipoPergunta);
   }

   @Test
   @Transactional
   public void updateNonExistingTipoPergunta() throws Exception {
       int databaseSizeBeforeUpdate = tipoPerguntaRepository.findAll().size();

       // Create the TipoPergunta

       // If the entity doesn't have an ID, it will be created instead of just being updated
       restTipoPerguntaMockMvc.perform(put("/api/tipo-perguntas")
           .contentType(TestUtil.APPLICATION_JSON_UTF8)
           .content(TestUtil.convertObjectToJsonBytes(tipoPergunta)))
           .andExpect(status().isCreated());

       // Validate the TipoPergunta in the database
       List<TipoPergunta> tipoPerguntaList = tipoPerguntaRepository.findAll();
       assertThat(tipoPerguntaList).hasSize(databaseSizeBeforeUpdate + 1);
   }

   @Test
   @Transactional
   public void deleteTipoPergunta() throws Exception {
       // Initialize the database
       tipoPerguntaService.save(tipoPergunta);

       int databaseSizeBeforeDelete = tipoPerguntaRepository.findAll().size();

       // Get the tipoPergunta
       restTipoPerguntaMockMvc.perform(delete("/api/tipo-perguntas/{id}", tipoPergunta.getId())
           .accept(TestUtil.APPLICATION_JSON_UTF8))
           .andExpect(status().isOk());

       // Validate Elasticsearch is empty
       boolean tipoPerguntaExistsInEs = tipoPerguntaSearchRepository.exists(tipoPergunta.getId());
       assertThat(tipoPerguntaExistsInEs).isFalse();

       // Validate the database is empty
       List<TipoPergunta> tipoPerguntaList = tipoPerguntaRepository.findAll();
       assertThat(tipoPerguntaList).hasSize(databaseSizeBeforeDelete - 1);
   }

   @Test
   @Transactional
   public void searchTipoPergunta() throws Exception {
       // Initialize the database
       tipoPerguntaService.save(tipoPergunta);

       // Search the tipoPergunta
       restTipoPerguntaMockMvc.perform(get("/api/_search/tipo-perguntas?query=id:" + tipoPergunta.getId()))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
           .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPergunta.getId().intValue())))
           .andExpect(jsonPath("$.[*].enunciadoPergunta").value(hasItem(DEFAULT_ENUNCIADO_PERGUNTA.toString())));
   }

   @Test
   @Transactional
   public void equalsVerifier() throws Exception {
       TestUtil.equalsVerifier(TipoPergunta.class);
       TipoPergunta tipoPergunta1 = new TipoPergunta();
       tipoPergunta1.setId(1L);
       TipoPergunta tipoPergunta2 = new TipoPergunta();
       tipoPergunta2.setId(tipoPergunta1.getId());
       assertThat(tipoPergunta1).isEqualTo(tipoPergunta2);
       tipoPergunta2.setId(2L);
       assertThat(tipoPergunta1).isNotEqualTo(tipoPergunta2);
       tipoPergunta1.setId(null);
       assertThat(tipoPergunta1).isNotEqualTo(tipoPergunta2);
   }
}
