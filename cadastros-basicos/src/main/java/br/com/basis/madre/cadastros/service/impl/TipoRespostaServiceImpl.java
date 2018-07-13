package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.service.TipoRespostaService;
import br.com.basis.madre.cadastros.domain.TipoResposta;
import br.com.basis.madre.cadastros.repository.TipoRespostaRepository;
import br.com.basis.madre.cadastros.repository.search.TipoRespostaSearchRepository;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.relatorio.colunas.RelatorioTipoRespotaColunas;
import br.com.basis.madre.cadastros.util.MadreUtil;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.InputStreamResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.management.relation.RelationException;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing TipoResposta.
 */
@Service
@Transactional
public class TipoRespostaServiceImpl implements TipoRespostaService {

    private final Logger log = LoggerFactory.getLogger(TipoRespostaServiceImpl.class);

    private final TipoRespostaRepository tipoRespostaRepository;

    private final TipoRespostaSearchRepository tipoRespostaSearchRepository;

    private final DynamicExportsService dynamicExportsService;

    public TipoRespostaServiceImpl(TipoRespostaRepository tipoRespostaRepository, TipoRespostaSearchRepository tipoRespostaSearchRepository, DynamicExportsService dynamicExportsService) {
        this.tipoRespostaRepository = tipoRespostaRepository;
        this.tipoRespostaSearchRepository = tipoRespostaSearchRepository;
        this.dynamicExportsService = dynamicExportsService;
    }

    /**
     * Save a tipoResposta.
     *
     * @param tipoResposta the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoResposta save(TipoResposta tipoResposta) {
        log.debug("Request to save TipoResposta : {}", tipoResposta);
        TipoResposta result = tipoRespostaRepository.save(tipoResposta);
        tipoRespostaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tipoRespostas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
        public Page<TipoResposta> findAll(Pageable pageable) {
        log.debug("Request to get all TipoRespostas");
        return tipoRespostaRepository.findAll(pageable);
    }

    /**
     * Get one tipoResposta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoResposta findOne(Long id) {
        log.debug("Request to get TipoResposta : {}", id);
        return tipoRespostaRepository.findOne(id);
    }

    /**
     * Delete the tipoResposta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoResposta : {}", id);
        tipoRespostaRepository.delete(id);
        tipoRespostaSearchRepository.delete(id);
    }

    /**
     * Search for the tipoResposta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoResposta> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoRespostas for query {}", query);
        Page<TipoResposta> result = tipoRespostaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    @Override
    public ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio, String querry) throws RelatorioException, RelatorioException {
        ByteArrayOutputStream byteArrayOutputStream;

        try{
            new NativeSearchQueryBuilder().withQuery(multiMatchQuery(querry)).build();
            Page<TipoResposta> result = tipoRespostaSearchRepository.search(queryStringQuery(querry), dynamicExportsService.obterPageableMaximoExportacao());
            byteArrayOutputStream = dynamicExportsService.export(new RelatorioTipoRespotaColunas(),result, tipoRelatorio, Optional.empty(), Optional.ofNullable(MadreUtil.REPORT_LOGO_PATH), Optional.ofNullable(MadreUtil.getReportFooter()));
        }catch(DRException | ClassNotFoundException | JRException | NoClassDefFoundError e){
            log.error(e.getMessage(), e);
            throw new RelatorioException(e);
        }
        return DynamicExporter.output(byteArrayOutputStream,
            "relatorio." + tipoRelatorio);
    }
}
