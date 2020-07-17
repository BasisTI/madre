package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.ClassificacaoMaterial;
import br.com.basis.suprimentos.repository.ClassificacaoMaterialRepository;
import br.com.basis.suprimentos.repository.search.ClassificacaoMaterialSearchRepository;
import br.com.basis.suprimentos.service.dto.ClassificacaoMaterialDTO;
import br.com.basis.suprimentos.service.mapper.ClassificacaoMaterialMapper;
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
public class ClassificacaoMaterialService {
    private final ClassificacaoMaterialRepository classificacaoMaterialRepository;
    private final ClassificacaoMaterialMapper classificacaoMaterialMapper;
    private final ClassificacaoMaterialSearchRepository classificacaoMaterialSearchRepository;

    public ClassificacaoMaterialDTO save(ClassificacaoMaterialDTO classificacaoMaterialDTO) {
        log.debug("Request to save ClassificacaoMaterial : {}", classificacaoMaterialDTO);
        ClassificacaoMaterial classificacaoMaterial = classificacaoMaterialMapper.toEntity(classificacaoMaterialDTO);
        classificacaoMaterial = classificacaoMaterialRepository.save(classificacaoMaterial);
        ClassificacaoMaterialDTO result = classificacaoMaterialMapper.toDto(classificacaoMaterial);
        classificacaoMaterialSearchRepository.save(classificacaoMaterial);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<ClassificacaoMaterialDTO> findAll(Pageable pageable, ClassificacaoMaterialDTO classificacaoMaterialDTO) {
        log.debug("Request to get all ClassificacaoMaterials");
        return classificacaoMaterialRepository.findAll(
            Example.of(classificacaoMaterialMapper.toEntity(classificacaoMaterialDTO), ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING))
            , pageable)
            .map(classificacaoMaterialMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ClassificacaoMaterialDTO> findOne(Long id) {
        log.debug("Request to get ClassificacaoMaterial : {}", id);
        return classificacaoMaterialRepository.findById(id)
            .map(classificacaoMaterialMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ClassificacaoMaterial : {}", id);
        classificacaoMaterialRepository.deleteById(id);
        classificacaoMaterialSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ClassificacaoMaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClassificacaoMaterials for query {}", query);
        return classificacaoMaterialSearchRepository.search(queryStringQuery(query), pageable)
            .map(classificacaoMaterialMapper::toDto);
    }
}
