package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Fornecedor;
import br.com.basis.suprimentos.repository.FornecedorRepository;
import br.com.basis.suprimentos.repository.search.FornecedorSearchRepository;
import br.com.basis.suprimentos.service.dto.FornecedorDTO;
import br.com.basis.suprimentos.service.mapper.FornecedorMapper;
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
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;
    private final FornecedorSearchRepository fornecedorSearchRepository;

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        log.debug("Request to save Fornecedor : {}", fornecedorDTO);
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
        fornecedor = fornecedorRepository.save(fornecedor);
        FornecedorDTO result = fornecedorMapper.toDto(fornecedor);
        fornecedorSearchRepository.save(fornecedor);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fornecedors");
        return fornecedorRepository.findAll(pageable)
            .map(fornecedorMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<FornecedorDTO> findOne(Long id) {
        log.debug("Request to get Fornecedor : {}", id);
        return fornecedorRepository.findById(id)
            .map(fornecedorMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
        fornecedorSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<FornecedorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fornecedors for query {}", query);
        return fornecedorSearchRepository.search(queryStringQuery(query), pageable)
            .map(fornecedorMapper::toDto);
    }
}
