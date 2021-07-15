package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.ProjetoDePesquisa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProjetoDePesquisa} entity.
 */
public interface ProjetoDePesquisaSearchRepository extends ElasticsearchRepository<ProjetoDePesquisa, Long> {
}
