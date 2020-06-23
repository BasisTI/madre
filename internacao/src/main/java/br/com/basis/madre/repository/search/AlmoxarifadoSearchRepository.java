package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Almoxarifado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AlmoxarifadoSearchRepository extends ElasticsearchRepository<Almoxarifado, Long> {
}
