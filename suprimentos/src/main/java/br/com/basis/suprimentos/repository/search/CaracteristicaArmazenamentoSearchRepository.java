package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.CaracteristicaArmazenamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CaracteristicaArmazenamentoSearchRepository extends ElasticsearchRepository<CaracteristicaArmazenamento, Long> {
}
