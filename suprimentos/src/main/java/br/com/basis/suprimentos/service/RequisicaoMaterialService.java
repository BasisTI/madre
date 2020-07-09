package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.RequisicaoMaterial;
import br.com.basis.suprimentos.repository.RequisicaoMaterialRepository;
import br.com.basis.suprimentos.repository.search.RequisicaoMaterialSearchRepository;
import br.com.basis.suprimentos.service.dto.RequisicaoMaterialDTO;
import br.com.basis.suprimentos.service.mapper.RequisicaoMaterialMapper;
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
public class RequisicaoMaterialService {
    private final RequisicaoMaterialRepository requisicaoMaterialRepository;
    private final RequisicaoMaterialMapper requisicaoMaterialMapper;
    private final RequisicaoMaterialSearchRepository requisicaoMaterialSearchRepository;

    public RequisicaoMaterialDTO save(RequisicaoMaterialDTO requisicaoMaterialDTO) {
        log.debug("Request to save RequisicaoMaterial : {}", requisicaoMaterialDTO);
        RequisicaoMaterial requisicaoMaterial = requisicaoMaterialMapper.toEntity(requisicaoMaterialDTO);
        requisicaoMaterial = requisicaoMaterialRepository.save(requisicaoMaterial);
        RequisicaoMaterialDTO result = requisicaoMaterialMapper.toDto(requisicaoMaterial);
        requisicaoMaterialSearchRepository.save(requisicaoMaterial);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoMaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequisicaoMaterials");
        return requisicaoMaterialRepository.findAll(pageable)
            .map(requisicaoMaterialMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RequisicaoMaterialDTO> findOne(Long id) {
        log.debug("Request to get RequisicaoMaterial : {}", id);
        return requisicaoMaterialRepository.findById(id)
            .map(requisicaoMaterialMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete RequisicaoMaterial : {}", id);
        requisicaoMaterialRepository.deleteById(id);
        requisicaoMaterialSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoMaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequisicaoMaterials for query {}", query);
        return requisicaoMaterialSearchRepository.search(queryStringQuery(query), pageable)
            .map(requisicaoMaterialMapper::toDto);
    }
}
