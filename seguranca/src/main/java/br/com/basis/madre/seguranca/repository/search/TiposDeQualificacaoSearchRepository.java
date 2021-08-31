package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.TiposDeQualificacao;
import br.com.basis.madre.seguranca.service.projection.TiposDeQualificacaoResumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TiposDeQualificacao} entity.
 */
public interface TiposDeQualificacaoSearchRepository extends ElasticsearchRepository<TiposDeQualificacao, Long> {
    Page<TiposDeQualificacaoResumo> findAllProjectedTiposDeQualificacaoResumooByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
}
