package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.pojo.PropriedadesRelatorio;
import br.com.basis.dynamicexports.pojo.ReportObject;
import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.TipoPergunta;
import br.com.basis.madre.cadastros.repository.TipoPerguntaRepository;
import br.com.basis.madre.cadastros.repository.search.TipoPerguntaSearchRepository;
import br.com.basis.madre.cadastros.service.TipoPerguntaService;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.relatorio.colunas.RelatorioTipoPerguntaColunas;
import br.com.basis.madre.cadastros.util.MadreUtil;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
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

/**
 * Service Implementation for managing TipoPergunta.
 */
@Service
@Transactional
public class TipoPerguntaServiceImpl implements TipoPerguntaService {

    private final Logger log = LoggerFactory.getLogger(TipoPerguntaServiceImpl.class);

    private final TipoPerguntaRepository tipoPerguntaRepository;

    private final TipoPerguntaSearchRepository tipoPerguntaSearchRepository;

    private final DynamicExportsService dynamicExportsService;

    public TipoPerguntaServiceImpl(TipoPerguntaRepository tipoPerguntaRepository, TipoPerguntaSearchRepository tipoPerguntaSearchRepository, DynamicExportsService dynamicExportsService) {
        this.tipoPerguntaRepository = tipoPerguntaRepository;
        this.tipoPerguntaSearchRepository = tipoPerguntaSearchRepository;
        this.dynamicExportsService = dynamicExportsService;
    }

    /**
     * Save a tipoPergunta.
     *
     * @param tipoPergunta the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoPergunta save(TipoPergunta tipoPergunta) {
        log.debug("Request to save TipoPergunta : {}", tipoPergunta);
        TipoPergunta result = tipoPerguntaRepository.save(tipoPergunta);
        tipoPerguntaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tipoPerguntas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoPergunta> findAll(Pageable pageable) {
        log.debug("Request to get all TipoPerguntas");
        return tipoPerguntaRepository.findAll(pageable);
    }

    /**
     * Get one tipoPergunta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoPergunta findOne(Long id) {
        log.debug("Request to get TipoPergunta : {}", id);
        return tipoPerguntaRepository.findOne(id);
    }

    /**
     * Delete the tipoPergunta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPergunta : {}", id);
        tipoPerguntaRepository.delete(id);
        tipoPerguntaSearchRepository.delete(id);
    }

    /**
     * Search for the tipoPergunta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoPergunta> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoPerguntas for query {}", query);
        Page<TipoPergunta> result = tipoPerguntaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    @Override
    public ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio, String query) throws RelatorioException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            new NativeSearchQueryBuilder().withQuery(multiMatchQuery(query)).build();
            Page<TipoPergunta> result =  tipoPerguntaSearchRepository.search(queryStringQuery(query), dynamicExportsService.obterPageableMaximoExportacao());
            byteArrayOutputStream =  dynamicExportsService.export(new RelatorioTipoPerguntaColunas(),result,tipoRelatorio,Optional.empty(),Optional.ofNullable(MadreUtil.REPORT_LOGO_PATH),Optional.ofNullable(MadreUtil.getReportFooter()));
        } catch (DRException | ClassNotFoundException | JRException | NoClassDefFoundError e) {
            log.error(e.getMessage(), e);
            throw new RelatorioException(e);
        }
        return DynamicExporter.output(byteArrayOutputStream,
            "relatorio." + tipoRelatorio);
    }
}
