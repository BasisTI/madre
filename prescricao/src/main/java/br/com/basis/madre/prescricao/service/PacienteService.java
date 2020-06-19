package br.com.basis.madre.prescricao.service;

import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.madre.prescricao.domain.Paciente;
import br.com.basis.madre.prescricao.repository.search.PacienteRepositorySearch;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class PacienteService {
    private final PacienteRepositorySearch pacienteSearchRepository;

    @Transactional(readOnly = true)
    public Page<Paciente> obterTodosPacientes(Pageable pageable, String query) {
        
        if (Strings.isNotBlank(query) || Strings.isNotEmpty(query)) {
            NativeSearchQuery nativeSearchQueryFuzzy = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("nome", query).operator(Operator.AND).fuzziness(Fuzziness.ONE).prefixLength(3))
                .withPageable(pageable)
                .build();
            return pacienteSearchRepository.search(nativeSearchQueryFuzzy);
        }

        return pacienteSearchRepository.findAll(pageable);
    }

}
