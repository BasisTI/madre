package br.com.basis.madre.cadastros.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.PerfilDTO;
import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.repository.PerfilRepository;
import br.com.basis.madre.cadastros.repository.search.PerfilSearchRepository;
import br.com.basis.madre.cadastros.service.PerfilService;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.relatorio.colunas.RelatorioPerfilColunas;
import br.com.basis.madre.cadastros.util.MadreUtil;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;

/**
 * Service Implementation for managing Perfil.
 */
@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private final Logger log = LoggerFactory.getLogger(PerfilServiceImpl.class);

    private final PerfilRepository perfilRepository;

    private final PerfilSearchRepository perfilSearchRepository;

    private final DynamicExportsService dynamicExportsService;


    public PerfilServiceImpl(PerfilRepository perfilRepository, PerfilSearchRepository perfilSearchRepository, DynamicExportsService dynamicExportsService) {
        this.perfilRepository = perfilRepository;
        this.perfilSearchRepository = perfilSearchRepository;
        this.dynamicExportsService = dynamicExportsService;

    }

    /**
     * Save a perfil.
     *
     * @param perfil the entity to save
     * @return the persisted entity
     */
    @Override
    public Perfil save(Perfil perfil) {
        log.debug("Request to save Perfil : {}", perfil);
        Perfil result = perfilRepository.save(perfil);
        perfilSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the perfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Perfil> findAll(Pageable pageable) {
        log.debug("Request to get all Perfils");
        return perfilRepository.findAll(pageable);
    }

    /**
     * Get one perfil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Perfil findOne(Long id) {
        log.debug("Request to get Perfil : {}", id);
        return perfilRepository.findOne(id);
    }

    /**
     * Delete the perfil by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Perfil : {}", id);
        perfilRepository.delete(id);
        perfilSearchRepository.delete(id);
    }

    /**
     * Search for the perfil corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Perfil> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Perfils for query {}", query);
        return perfilSearchRepository.search(queryStringQuery(query), pageable);
    }

    @Override
    public ResponseEntity<InputStreamResource> gerarRelatorioExportacao(String tipoRelatorio, String querry) throws RelatorioException {
        ByteArrayOutputStream byteArrayOutputStream;

        try {
            new NativeSearchQueryBuilder().withQuery(multiMatchQuery(querry)).build();
            Page<Perfil> result = perfilSearchRepository.search(queryStringQuery(querry), dynamicExportsService.obterPageableMaximoExportacao());
            byteArrayOutputStream = dynamicExportsService.export(new RelatorioPerfilColunas(), result, tipoRelatorio, Optional.empty(), Optional.ofNullable(MadreUtil.REPORT_LOGO_PATH), Optional.ofNullable(MadreUtil.getReportFooter()));
        } catch (DRException | ClassNotFoundException | JRException | NoClassDefFoundError e) {
            log.error(e.getMessage(), e);
            throw new RelatorioException(e);
        }
        return DynamicExporter.output(byteArrayOutputStream, "relatorio." + tipoRelatorio);
    }

    @Override
    public Perfil convertAcaoTempToPerfil(PerfilDTO acaoTemp) {

        return new Perfil(acaoTemp.getNomePerfil(), acaoTemp.getDsPerfil());
    }
}
