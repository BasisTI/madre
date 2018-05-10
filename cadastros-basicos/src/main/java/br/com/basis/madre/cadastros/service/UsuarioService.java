package br.com.basis.madre.cadastros.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import net.sf.jasperreports.engine.JRException;
import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.repository.UsuarioRepository;
import br.com.basis.madre.cadastros.repository.search.UsuarioSearchRepository;
import br.com.basis.madre.cadastros.service.UsuarioService;
import br.com.basis.madre.cadastros.service.dto.UsuarioDTO;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.filter.UsuarioFilter;
import br.com.basis.madre.cadastros.service.mapper.UsuarioMapper;
import br.com.basis.madre.cadastros.service.relatorio.colunas.RelatorioUsuarioColunas;
import br.com.basis.madre.cadastros.util.MadreUtil;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


import static org.elasticsearch.index.query.QueryBuilders.*;
/**
 * Service managing Usuario.
 */
@Service
@Transactional
public class UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioSearchRepository usuarioSearchRepository;

    private final DynamicExportsService dynamicExportsService;

    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioSearchRepository usuarioSearchRepository,
	    DynamicExportsService dynamicExportsService, UsuarioMapper usuarioMapper) {
	    this.usuarioRepository = usuarioRepository;
	    this.usuarioSearchRepository = usuarioSearchRepository;
	    this.dynamicExportsService = dynamicExportsService;
	    this.usuarioMapper = usuarioMapper;
    }

    /**
     * Save a Usuario.
     *
     * @param usuarioDTO the entity to save
     * @return the persisted entity
     */
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        UsuarioDTO result = usuarioMapper.toDto(usuario);
        usuarioSearchRepository.save(usuario);
        return result;
    }

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Optional<String> query, Pageable pageable) { // talvez alterar aqui Optional
        log.debug("Request to get all Usuarios");
        UsuarioFilter filter = new UsuarioFilter();
        QueryBuilder queryBuilder;
        Page<Usuario> result;
        if (query.isPresent() && StringUtils.isNotBlank(query.get())) {
            queryBuilder = filter.filterElasticSearch(query.get());
            result = usuarioSearchRepository.search(queryBuilder, pageable);
        } else {
            result =  usuarioRepository.findAll(pageable);
        }
        return result.map(usuarioMapper::toDto);
    }

    /**
     * Get one usuario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UsuarioDTO findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        Usuario usuario = usuarioRepository.findOne(id);
//      UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);
        return usuarioMapper.toDto(usuario);
    }

    /**
     * Delete the usuario by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.delete(id);
        usuarioSearchRepository.delete(id);
    }

    /**
     * Search for the usuario corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Usuarios for query {}", query);
        Page<Usuario> result = usuarioSearchRepository.search(queryStringQuery(query), pageable);
//        return result;
        return result.map(usuarioMapper::toDto);
    }

    /**
     * gera relatório by entity
     *
     * @param tipoRelatorio
     */
    public ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio)
        throws RelatorioException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            Page<UsuarioDTO> result = usuarioRepository.findAll(dynamicExportsService.obterPageableMaximoExportacao()).map(usuarioMapper::toDto);
            byteArrayOutputStream = dynamicExportsService.export(new RelatorioUsuarioColunas(), result, tipoRelatorio, Optional.empty(), Optional.ofNullable(MadreUtil.REPORT_LOGO_PATH), Optional.ofNullable(MadreUtil.getReportFooter()));
        } catch (DRException | ClassNotFoundException | JRException | NoClassDefFoundError e) {
            log.error(e.getMessage(), e);
            throw new RelatorioException(e);
        }
        return DynamicExporter.output(byteArrayOutputStream,
            "relatorio." + tipoRelatorio);
    }
}



