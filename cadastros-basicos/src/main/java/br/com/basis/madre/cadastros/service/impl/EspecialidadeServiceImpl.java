package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.Especialidade;
import br.com.basis.madre.cadastros.repository.EspecialidadeRepository;
import br.com.basis.madre.cadastros.repository.search.EspecialidadeSearchRepository;
import br.com.basis.madre.cadastros.service.EspecialidadeService;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.filter.EspecialidadeFilter;
import br.com.basis.madre.cadastros.service.relatorio.colunas.RelatorioEspecialidadeColunas;
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
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Service
@Transactional
public class EspecialidadeServiceImpl implements EspecialidadeService {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeServiceImpl.class);

    private final EspecialidadeRepository especialidadeRepository;

    private final EspecialidadeSearchRepository especialidadeSearchRepository;


    private final DynamicExportsService dynamicExportsService;


    public EspecialidadeServiceImpl(EspecialidadeRepository especialidadeRepository,
        EspecialidadeSearchRepository especialidadeSearchRepository, DynamicExportsService dynamicExportsService) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeSearchRepository = especialidadeSearchRepository;
        this.dynamicExportsService = dynamicExportsService;
    }

    @Override
    public Especialidade save(Especialidade especialidade) {
        log.debug("Request to save Especialidade : {}", especialidade);
        especialidade = especialidadeRepository.save(especialidade);
        especialidadeSearchRepository.save(especialidade);
        return especialidade;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Especialidade> findAll(Optional<String> query, Pageable pageable) {
        log.debug("Request to get all Especialidades");
        EspecialidadeFilter filter = new EspecialidadeFilter();
        QueryBuilder queryBuilder;
        Page<Especialidade> result;
        if (query.isPresent() && StringUtils.isNotBlank(query.get())) {
            queryBuilder = filter.filterElasticSearch(query.get());
            result = especialidadeSearchRepository.search(queryBuilder, pageable);
        } else {
            result =  especialidadeRepository.findAll(pageable);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Especialidade findOne(Long id) {
        log.debug("Request to get Especialidade : {}", id);
        Especialidade especialidade = especialidadeRepository.findOne(id);
        return (especialidade);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Especialidade : {}", id);
        especialidadeRepository.delete(id);
        especialidadeSearchRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Especialidade> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Especialidades for query {}", query);
        return especialidadeSearchRepository.search(queryStringQuery(query), pageable);
    }

    /**
     * gera relatório by entity a
     *
     * @param tipoRelatorio
     */

    @Override
    public ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio, String query) throws RelatorioException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            new NativeSearchQueryBuilder().withQuery(multiMatchQuery(query)).build();
            Page<Especialidade> result =  especialidadeSearchRepository.search(queryStringQuery(query), dynamicExportsService.obterPageableMaximoExportacao());
            byteArrayOutputStream = dynamicExportsService.export(new RelatorioEspecialidadeColunas(), result, tipoRelatorio, Optional.empty(), Optional.ofNullable(MadreUtil.REPORT_LOGO_PATH), Optional.ofNullable(MadreUtil.getReportFooter()));
        } catch (DRException | ClassNotFoundException | JRException | NoClassDefFoundError e) {
            log.error(e.getMessage(), e);
            throw new RelatorioException(e);
        }
        return DynamicExporter.output(byteArrayOutputStream,
            "relatorio." + tipoRelatorio);
     }


}
