package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.SituacaoRequisicao;
import br.com.basis.suprimentos.repository.SituacaoRequisicaoRepository;
import br.com.basis.suprimentos.repository.search.SituacaoRequisicaoSearchRepository;
import br.com.basis.suprimentos.service.dto.SituacaoRequisicaoDTO;
import br.com.basis.suprimentos.service.mapper.SituacaoRequisicaoMapper;
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
public class SituacaoRequisicaoService {
    private final SituacaoRequisicaoRepository situacaoRequisicaoRepository;
    private final SituacaoRequisicaoMapper situacaoRequisicaoMapper;
    private final SituacaoRequisicaoSearchRepository situacaoRequisicaoSearchRepository;

    public SituacaoRequisicaoDTO save(SituacaoRequisicaoDTO situacaoRequisicaoDTO) {
        log.debug("Request to save SituacaoRequisicao : {}", situacaoRequisicaoDTO);
        SituacaoRequisicao situacaoRequisicao = situacaoRequisicaoMapper.toEntity(situacaoRequisicaoDTO);
        situacaoRequisicao = situacaoRequisicaoRepository.save(situacaoRequisicao);
        SituacaoRequisicaoDTO result = situacaoRequisicaoMapper.toDto(situacaoRequisicao);
        situacaoRequisicaoSearchRepository.save(situacaoRequisicao);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<SituacaoRequisicaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SituacaoRequisicaos");
        return situacaoRequisicaoRepository.findAll(pageable)
            .map(situacaoRequisicaoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SituacaoRequisicaoDTO> findOne(Long id) {
        log.debug("Request to get SituacaoRequisicao : {}", id);
        return situacaoRequisicaoRepository.findById(id)
            .map(situacaoRequisicaoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete SituacaoRequisicao : {}", id);
        situacaoRequisicaoRepository.deleteById(id);
        situacaoRequisicaoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<SituacaoRequisicaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SituacaoRequisicaos for query {}", query);
        return situacaoRequisicaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(situacaoRequisicaoMapper::toDto);
    }
}
