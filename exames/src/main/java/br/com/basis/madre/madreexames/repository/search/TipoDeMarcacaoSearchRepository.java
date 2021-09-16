package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.TipoDeMarcacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TipoDeMarcacao} entity.
 */
public interface TipoDeMarcacaoSearchRepository extends ElasticsearchRepository<TipoDeMarcacao, Long> {
}
