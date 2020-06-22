package br.com.basis.suprimentos.repository.search;
import br.com.basis.suprimentos.domain.TempoPorClasse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TempoPorClasse} entity.
 */
public interface TempoPorClasseSearchRepository extends ElasticsearchRepository<TempoPorClasse, Long> {
}
