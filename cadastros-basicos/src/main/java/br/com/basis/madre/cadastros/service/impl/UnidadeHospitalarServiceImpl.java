package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.search.UnidadeHospitalarSearchRepository;
import br.com.basis.madre.cadastros.service.UnidadeHospitalarService;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.filter.UnidadeHospitalarFilter;
import br.com.basis.madre.cadastros.service.relatorio.colunas.RelatorioUnidadeHospitalarColunas;
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
public class UnidadeHospitalarServiceImpl implements UnidadeHospitalarService {

    private final Logger log = LoggerFactory.getLogger(UnidadeHospitalarServiceImpl.class);

    private final UnidadeHospitalarRepository unidadeHospitalarRepository;



    private final UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository;


    private final DynamicExportsService dynamicExportsService;

    public UnidadeHospitalarServiceImpl(UnidadeHospitalarRepository unidadeHospitalarRepository, UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository,
    DynamicExportsService dynamicExportsService) {
        this.unidadeHospitalarRepository = unidadeHospitalarRepository;
        this.unidadeHospitalarSearchRepository = unidadeHospitalarSearchRepository;
        this.dynamicExportsService = dynamicExportsService;
    }

    /**
     * Save a unidadeHospitalar.
     *
     * @param unidadeHospitalar the entity to save
     * @return the persisted entity
     */

    @Override
    public UnidadeHospitalar save(UnidadeHospitalar unidadeHospitalar) {
            log.debug("Request to save PreCadastro : {}", unidadeHospitalar);
             UnidadeHospitalar unidadeHospitalarSave = unidadeHospitalar;
            unidadeHospitalar = unidadeHospitalarRepository.save(unidadeHospitalarSave);
            unidadeHospitalarSearchRepository.save(unidadeHospitalarSave);
            return unidadeHospitalarSave;
        }

    /**
     * Get all the unidadeHospitalars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UnidadeHospitalar> findAll(Optional<String> query, Pageable pageable) {
        log.debug("Request to get all UnidadeHospitalars");
        UnidadeHospitalarFilter filter = new UnidadeHospitalarFilter();
        QueryBuilder queryBuilder;
        Page<UnidadeHospitalar> result;
        if (query.isPresent() && StringUtils.isNotBlank(query.get())) {
            queryBuilder = filter.filterElasticSearch(query.get());
            result = unidadeHospitalarSearchRepository.search(queryBuilder, pageable);
        } else {
            result =  unidadeHospitalarRepository.findAll(pageable);
        }
        return result;
    }


    /**
     * Get one unidadeHospitalar by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UnidadeHospitalar findOne(Long id) {
        log.debug("Request to get UnidadeHospitalar : {}", id);
        UnidadeHospitalar unidadeHospitalar = unidadeHospitalarRepository.findOne(id);
        return (unidadeHospitalar);
    }



    /**
     * Delete the unidadeHospitalar by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnidadeHospitalar : {}", id);
        unidadeHospitalarRepository.delete(id);
        unidadeHospitalarSearchRepository.delete(id);
    }

    /**
     * Search for the unidadeHospitalar corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UnidadeHospitalar> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadeHospitalars for query {}", query);
        return unidadeHospitalarSearchRepository.search(queryStringQuery(query), pageable);

    }


    @Override
    public ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio, String query) throws RelatorioException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            new NativeSearchQueryBuilder().withQuery(multiMatchQuery(query)).build();
            Page<UnidadeHospitalar> result =  unidadeHospitalarSearchRepository.search(queryStringQuery(query), dynamicExportsService.obterPageableMaximoExportacao());
            byteArrayOutputStream = dynamicExportsService.export(new RelatorioUnidadeHospitalarColunas(), result, tipoRelatorio, Optional.empty(), Optional.ofNullable(MadreUtil.REPORT_LOGO_PATH), Optional.ofNullable(MadreUtil.getReportFooter()));
        } catch (DRException | ClassNotFoundException | JRException | NoClassDefFoundError e) {
            log.error(e.getMessage(), e);
            throw new RelatorioException(e);
        }
        return DynamicExporter.output(byteArrayOutputStream,
            "relatorio." + tipoRelatorio);
    }



}
