package br.com.basis.madre.farmacia.service.reindex;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public class IndexadorSemMapper<A,  B extends Serializable> extends AbstractIndexador {

    private final JpaRepository<A, B> jpaRepository;
    private final ElasticsearchRepository<A, B> elasticsearchClassRepository;
    private final ElasticsearchOperations elasticsearchTemplate;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void indexar() {
        Class<A> classe = elasticsearchClassRepository.getEntityClass();
        elasticsearchTemplate.deleteIndex(classe);
        try {
            elasticsearchTemplate.createIndex(classe);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            log.debug(e.getMessage(), e);
        }

        elasticsearchTemplate.putMapping(classe);

        if (jpaRepository.count() > 0) {
            List<A> all = jpaRepository.findAll();
            elasticsearchClassRepository.saveAll(all);
        }
    }
}
