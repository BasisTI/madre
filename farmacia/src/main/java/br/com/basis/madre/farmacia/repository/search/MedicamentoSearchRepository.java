package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.Medicamento;
import br.com.basis.madre.farmacia.service.dto.MedicamentoDTO;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the {@link Medicamento} entity.
 */

public interface MedicamentoSearchRepository extends ElasticsearchRepository<Medicamento, Long> {


}
