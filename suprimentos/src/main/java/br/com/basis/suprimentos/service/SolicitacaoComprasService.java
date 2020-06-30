package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.SolicitacaoCompras;
import br.com.basis.suprimentos.repository.SolicitacaoComprasRepository;
import br.com.basis.suprimentos.repository.search.SolicitacaoComprasSearchRepository;
import br.com.basis.suprimentos.service.dto.SolicitacaoComprasDTO;
import br.com.basis.suprimentos.service.mapper.SolicitacaoComprasMapper;
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
public class SolicitacaoComprasService {
    private final SolicitacaoComprasRepository solicitacaoComprasRepository;
    private final SolicitacaoComprasMapper solicitacaoComprasMapper;
    private final SolicitacaoComprasSearchRepository solicitacaoComprasSearchRepository;

    public SolicitacaoComprasDTO save(SolicitacaoComprasDTO solicitacaoComprasDTO) {
        log.debug("Request to save SolicitacaoCompras : {}", solicitacaoComprasDTO);
        SolicitacaoCompras solicitacaoCompras = solicitacaoComprasMapper.toEntity(solicitacaoComprasDTO);
        solicitacaoCompras = solicitacaoComprasRepository.save(solicitacaoCompras);
        SolicitacaoComprasDTO result = solicitacaoComprasMapper.toDto(solicitacaoCompras);
        solicitacaoComprasSearchRepository.save(solicitacaoCompras);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<SolicitacaoComprasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SolicitacaoCompras");
        return solicitacaoComprasRepository.findAll(pageable)
            .map(solicitacaoComprasMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SolicitacaoComprasDTO> findOne(Long id) {
        log.debug("Request to get SolicitacaoCompras : {}", id);
        return solicitacaoComprasRepository.findById(id)
            .map(solicitacaoComprasMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete SolicitacaoCompras : {}", id);
        solicitacaoComprasRepository.deleteById(id);
        solicitacaoComprasSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<SolicitacaoComprasDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SolicitacaoCompras for query {}", query);
        return solicitacaoComprasSearchRepository.search(queryStringQuery(query), pageable)
            .map(solicitacaoComprasMapper::toDto);
    }
}
