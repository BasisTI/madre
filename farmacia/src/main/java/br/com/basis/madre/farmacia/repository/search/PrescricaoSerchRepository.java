package br.com.basis.madre.farmacia.repository.search;

import br.com.basis.madre.farmacia.domain.Prescricao;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


public interface PrescricaoSerchRepository extends ElasticsearchRepository <Prescricao, Long>{
    Page<Prescricao> findByNome(String nome, Pageable pageable);

//    Page<Prescricao> findAvailableBookByName(String nome, Pageable pageable);

}
