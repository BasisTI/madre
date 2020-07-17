package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TipoResiduo;
import br.com.basis.suprimentos.repository.TipoResiduoRepository;
import br.com.basis.suprimentos.repository.search.TipoResiduoSearchRepository;
import br.com.basis.suprimentos.service.dto.TipoResiduoDTO;
import br.com.basis.suprimentos.service.mapper.TipoResiduoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TipoResiduoService {
    private final TipoResiduoRepository tipoResiduoRepository;
    private final TipoResiduoMapper tipoResiduoMapper;
    private final TipoResiduoSearchRepository tipoResiduoSearchRepository;

    public TipoResiduoDTO save(TipoResiduoDTO tipoResiduoDTO) {
        log.debug("Request to save TipoResiduo : {}", tipoResiduoDTO);
        TipoResiduo tipoResiduo = tipoResiduoMapper.toEntity(tipoResiduoDTO);
        tipoResiduo = tipoResiduoRepository.save(tipoResiduo);
        TipoResiduoDTO result = tipoResiduoMapper.toDto(tipoResiduo);
        tipoResiduoSearchRepository.save(tipoResiduo);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TipoResiduoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoResiduos");
        return tipoResiduoRepository.findAll(pageable)
            .map(tipoResiduoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TipoResiduoDTO> findOne(Long id) {
        log.debug("Request to get TipoResiduo : {}", id);
        return tipoResiduoRepository.findById(id)
            .map(tipoResiduoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete TipoResiduo : {}", id);
        tipoResiduoRepository.deleteById(id);
        tipoResiduoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TipoResiduoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoResiduos for query {}", query);
        return tipoResiduoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoResiduoMapper::toDto);
    }
}
