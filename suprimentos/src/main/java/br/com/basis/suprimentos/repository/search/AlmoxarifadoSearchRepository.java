package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.Almoxarifado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AlmoxarifadoSearchRepository extends ElasticsearchRepository<Almoxarifado, Long> {
}
