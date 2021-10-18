package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.ProcessoAssincronoGrade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;


/**
 * Spring Data Elasticsearch repository for the {@link ProcessoAssincronoGrade} entity.
 */
public interface ProcessoAssincronoGradeSearchRepository extends ElasticsearchRepository<ProcessoAssincronoGrade, String> {
}
