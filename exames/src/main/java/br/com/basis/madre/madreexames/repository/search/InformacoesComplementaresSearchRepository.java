package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.InformacoesComplementares;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link InformacoesComplementares} entity.
 */
public interface InformacoesComplementaresSearchRepository extends ElasticsearchRepository<InformacoesComplementares, Long> {
}
