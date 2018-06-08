package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.Especialidade;
import br.com.basis.madre.cadastros.repository.EspecialidadeRepository;
import br.com.basis.madre.cadastros.repository.search.EspecialidadeSearchRepository;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Optional.class, DynamicExporter.class})
public class EspecialidadeServiceImplTest {
    @InjectMocks
    private EspecialidadeServiceImpl especialidadeServiceImpl;

    @Mock
    private Especialidade especialidade;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @Mock
    private EspecialidadeSearchRepository especialidadeSearchRepository;

    @Mock
    Page<Especialidade> page;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

    @Mock
    DynamicExportsService dynamicExportsService;

    @Mock
    QueryBuilder queryBuilder;

    @Mock
    String tipoRelatorio;

    @Mock
    ByteArrayOutputStream byteArrayOutputStream;

    @Mock
    String query;

    @Test
    public void saveTest() {
        when(especialidadeServiceImpl.save(especialidade)).thenReturn(especialidade);
        Especialidade test = especialidadeServiceImpl.save(especialidade);
    }

    @Test
    public void deleteTest() {
        especialidadeServiceImpl.delete(anyLong());
    }

    @Test
    public void findAllTest() {
        Page<Especialidade> test = especialidadeServiceImpl.findAll(java.util.Optional.of("test"), pageable);
        especialidadeServiceImpl.findAll(Optional.empty(),pageable);
    }

    @Test
    public void especialidadeFindOneTest() {
        Especialidade test = especialidadeServiceImpl.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<Especialidade> test = especialidadeServiceImpl.search("test", pageable);

    }

    @Test
    public void gerarRelatorioteste() throws RelatorioException, JRException, DRException, ClassNotFoundException {
        ResponseEntity responseEntity = mock(ResponseEntity.class);

        PowerMockito.mockStatic(DynamicExporter.class);

        when(DynamicExporter.output(any(), any())).thenReturn(responseEntity);

        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenReturn(new ByteArrayOutputStream());
        ResponseEntity resultado = especialidadeServiceImpl.gerarRelatorioExportacao("", "");

        Assert.assertEquals(resultado, responseEntity);
    }

    @Test(expected = RelatorioException.class)
    public void lanca_exception() throws RelatorioException, JRException, DRException, ClassNotFoundException {
        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenThrow(DRException.class);
        especialidadeServiceImpl.gerarRelatorioExportacao("", "");
    }

}
