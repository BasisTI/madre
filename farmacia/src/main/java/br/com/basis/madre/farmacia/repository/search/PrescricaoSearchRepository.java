package br.com.basis.madre.farmacia.repository.search;

import br.com.basis.madre.farmacia.domain.Prescricao;
import br.com.basis.madre.farmacia.service.dto.PrescricaoDTO;
import br.com.basis.madre.farmacia.service.projection.PrescricaoLocal;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


public interface PrescricaoSearchRepository extends ElasticsearchRepository <Prescricao, Long>{
    Page<PrescricaoLocal> findAllByLocal(String local, Pageable pageable);
    Page<PrescricaoLocal> findAllPrescricaoLocalBy(Pageable pageable);

}
