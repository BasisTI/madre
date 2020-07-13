package br.com.basis.madre.prescricao.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.prescricao.domain.Atendimento;

public interface AtendimentoSearchRepository extends ElasticsearchRepository<Atendimento, Long> {

}
