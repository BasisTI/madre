package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.DocumentoFiscalEntrada;
import br.com.basis.suprimentos.repository.DocumentoFiscalEntradaRepository;
import br.com.basis.suprimentos.repository.search.DocumentoFiscalEntradaSearchRepository;
import br.com.basis.suprimentos.service.dto.DocumentoFiscalEntradaDTO;
import br.com.basis.suprimentos.service.mapper.DocumentoFiscalEntradaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
public class DocumentoFiscalEntradaService {
    private final DocumentoFiscalEntradaRepository documentoFiscalEntradaRepository;
    private final DocumentoFiscalEntradaMapper documentoFiscalEntradaMapper;
    private final DocumentoFiscalEntradaSearchRepository documentoFiscalEntradaSearchRepository;

    public DocumentoFiscalEntradaDTO save(DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO) {
        log.debug("Request to save DocumentoFiscalEntrada : {}", documentoFiscalEntradaDTO);
        DocumentoFiscalEntrada documentoFiscalEntrada = documentoFiscalEntradaMapper.toEntity(documentoFiscalEntradaDTO);
        documentoFiscalEntrada = documentoFiscalEntradaRepository.save(documentoFiscalEntrada);
        DocumentoFiscalEntradaDTO result = documentoFiscalEntradaMapper.toDto(documentoFiscalEntrada);
        documentoFiscalEntradaSearchRepository.save(documentoFiscalEntrada);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<DocumentoFiscalEntradaDTO> findAll(Pageable pageable, DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO) {
        log.debug("Request to get all DocumentoFiscalEntradas");
        return documentoFiscalEntradaRepository.findAll(Example.of(documentoFiscalEntradaMapper.toEntity(documentoFiscalEntradaDTO), ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)), pageable)
            .map(documentoFiscalEntradaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DocumentoFiscalEntradaDTO> findOne(Long id) {
        log.debug("Request to get DocumentoFiscalEntrada : {}", id);
        return documentoFiscalEntradaRepository.findById(id)
            .map(documentoFiscalEntradaMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete DocumentoFiscalEntrada : {}", id);
        documentoFiscalEntradaRepository.deleteById(id);
        documentoFiscalEntradaSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<DocumentoFiscalEntradaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentoFiscalEntradas for query {}", query);
        return documentoFiscalEntradaSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentoFiscalEntradaMapper::toDto);
    }
}
