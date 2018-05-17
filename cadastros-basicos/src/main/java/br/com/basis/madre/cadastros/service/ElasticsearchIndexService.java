package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.repository.PreCadastroRepository;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.UsuarioRepository;
import br.com.basis.madre.cadastros.repository.search.PreCadastroSearchRepository;
import br.com.basis.madre.cadastros.repository.search.UnidadeHospitalarSearchRepository;
import br.com.basis.madre.cadastros.repository.search.UsuarioSearchRepository;
import com.codahale.metrics.annotation.Timed;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ElasticsearchIndexService {

    public static final String INDEX_NAME = "madre";

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final ElasticsearchTemplate elasticsearchTemplate;

    private static final String REINDEX_SUCCESSFULL = "Elasticsearch: Indexed all rows for : {} ";

    private final UsuarioRepository usuarioRepository;

    private final UsuarioSearchRepository usuarioSearchRepository;

    private final UnidadeHospitalarRepository unidadeHospitalarRepository;

    private final UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository;

    private final PreCadastroRepository preCadastroRepository;

    private final PreCadastroSearchRepository preCadastroSearchRepository;


    public ElasticsearchIndexService(
        ElasticsearchTemplate elasticsearchTemplate,
        UsuarioRepository usuarioRepository,
        UsuarioSearchRepository usuarioSearchRepository,
        UnidadeHospitalarRepository unidadeHospitalarRepository,
        UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository,
        PreCadastroRepository preCadastroRepository,
        PreCadastroSearchRepository preCadastroSearchRepository) {

        this.elasticsearchTemplate = elasticsearchTemplate;
        this.usuarioRepository = usuarioRepository;
        this.usuarioSearchRepository = usuarioSearchRepository;
        this.unidadeHospitalarRepository = unidadeHospitalarRepository;
        this.unidadeHospitalarSearchRepository = unidadeHospitalarSearchRepository;
        this.preCadastroRepository = preCadastroRepository;
        this.preCadastroSearchRepository = preCadastroSearchRepository;
    }

    @Async
    @Timed
    public void reindexAll() {
        recreateIndex();
        reindexOne();
        log.info("Elasticsearch: Successfully performed reindexing");
    }

    private void reindexOne() {
        reindexForClass(Usuario.class, usuarioRepository, usuarioSearchRepository);
        reindexForClass(UnidadeHospitalar.class, unidadeHospitalarRepository, unidadeHospitalarSearchRepository);
        reindexForClass(PreCadastro.class, preCadastroRepository, preCadastroSearchRepository);
    }

    private void recreateIndex() {

        elasticsearchTemplate.deleteIndex(INDEX_NAME);
        try {
            elasticsearchTemplate.createIndex(INDEX_NAME);
        } catch (IndexAlreadyExistsException e) {
            log.error(e.getMessage(), e);
        }
    }

    private <T, I extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, I> jpaRepository,
        ElasticsearchRepository<T, I> elasticsearchRepository) {
        log.info("reindexClass: {}", entityClass.getName());
        elasticsearchTemplate.putMapping(entityClass);

        if (jpaRepository.count() > 0) {
            elasticsearchRepository.save(jpaRepository.findAll());
        }
        log.info(REINDEX_SUCCESSFULL, entityClass.getSimpleName());
    }

}
