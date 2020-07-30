package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Material;
import br.com.basis.suprimentos.repository.MaterialRepository;
import br.com.basis.suprimentos.repository.search.MaterialSearchRepository;
import br.com.basis.suprimentos.service.dto.MaterialDTO;
import br.com.basis.suprimentos.service.mapper.MaterialMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;
    private final MaterialSearchRepository materialSearchRepository;

    public MaterialDTO save(MaterialDTO materialDTO) {
        log.debug("Request to save Material : {}", materialDTO);
        Material material = materialMapper.toEntity(materialDTO);
        material = materialRepository.save(material);
        MaterialDTO result = materialMapper.toDto(material);
        materialSearchRepository.save(material);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<MaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(pageable)
            .map(materialMapper::toDto);
    }

    @Transactional(readOnly = true)
    public <S extends Material> List<MaterialDTO> findAll(Example<S> example) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(example).stream()
            .map(materialMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public <S extends Material> Optional<MaterialDTO> findOne(Example<S> example) {
        return materialRepository.findOne(example).map(materialMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<MaterialDTO> findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        return materialRepository.findById(id)
            .map(materialMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.deleteById(id);
        materialSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<MaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Materials for query {}", query);
        return materialSearchRepository.search(queryStringQuery(query), pageable)
            .map(materialMapper::toDto);
    }
}
