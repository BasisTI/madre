package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.repository.PreCadastroRepository;
import br.com.basis.madre.cadastros.repository.search.PreCadastroSearchRepository;
import br.com.basis.madre.cadastros.service.PreCadastroService;
import br.com.basis.madre.cadastros.service.dto.PreCadastroDTO;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.filter.PreCadastroFilter;
import br.com.basis.madre.cadastros.service.mapper.PreCadastroMapper;
import br.com.basis.madre.cadastros.service.relatorio.colunas.RelatorioPreCadastroColunas;
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


@Service
@Transactional
public class PreCadastroServiceImpl implements PreCadastroService {

    private final Logger log = LoggerFactory.getLogger(PreCadastroServiceImpl.class);

    private final PreCadastroRepository preCadastroRepository;

    private final PreCadastroSearchRepository preCadastroSearchRepository;

    private final PreCadastroMapper preCadastroMapper;

    private final DynamicExportsService dynamicExportsService;


    public PreCadastroServiceImpl(PreCadastroRepository preCadastroRepository,
        PreCadastroSearchRepository preCadastroSearchRepository, PreCadastroMapper preCadastroMapper, DynamicExportsService dynamicExportsService) {
        this.preCadastroRepository = preCadastroRepository;
        this.preCadastroSearchRepository = preCadastroSearchRepository;
        this.preCadastroMapper = preCadastroMapper;
        this.dynamicExportsService = dynamicExportsService;
    }

    @Override
    public PreCadastroDTO save(PreCadastroDTO preCadastroDTO) {
        log.debug("Request to save PreCadastro : {}", preCadastroDTO);
        PreCadastro preCadastro = preCadastroMapper.toEntity(preCadastroDTO);
        preCadastro = preCadastroRepository.save(preCadastro);
        preCadastroSearchRepository.save(preCadastro);
        return preCadastroMapper.toDto(preCadastro);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreCadastroDTO> findAll(Optional<String> query, Pageable pageable) {
        log.debug("Request to get all PreCadastros");
        PreCadastroFilter filter = new PreCadastroFilter();
        QueryBuilder queryBuilder;
        Page<PreCadastro> result;
        if (query.isPresent() && StringUtils.isNotBlank(query.get())) {
            queryBuilder = filter.filterElasticSearch(query.get());
            result = preCadastroSearchRepository.search(queryBuilder, pageable);
        } else {
            result =  preCadastroRepository.findAll(pageable);
        }
        return result.map(preCadastroMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PreCadastroDTO findOne(Long id) {
        log.debug("Request to get PreCadastro : {}", id);
        PreCadastro preCadastro = preCadastroRepository.findOne(id);
        PreCadastroDTO preCadastroDTO = preCadastroMapper.toDto(preCadastro);
        return (preCadastroDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PreCadastro : {}", id);
        preCadastroRepository.delete(id);
        preCadastroSearchRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreCadastro> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PreCadastros for query {}", query);
        Page<PreCadastro> result = preCadastroSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    /**
     * gera relatório by entity
     *
     * @param tipoRelatorio
     */
    @Override
    public ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio)
        throws RelatorioException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            Page<PreCadastroDTO> result = preCadastroRepository.findAll(dynamicExportsService.obterPageableMaximoExportacao()).map(preCadastroMapper::toDto);
            byteArrayOutputStream = dynamicExportsService.export(new RelatorioPreCadastroColunas(), result, tipoRelatorio, Optional.empty(), Optional.ofNullable(MadreUtil.REPORT_LOGO_PATH), Optional.ofNullable(MadreUtil.getReportFooter()));
        } catch (DRException | ClassNotFoundException | JRException | NoClassDefFoundError e) {
            log.error(e.getMessage(), e);
            throw new RelatorioException(e);
        }
        return DynamicExporter.output(byteArrayOutputStream,
            "relatorio." + tipoRelatorio);
    }


}

