package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.search.UnidadeHospitalarSearchRepository;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.repo.InputStreamResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
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
public class UnidadeHospitalarServiceImplTest {
    @InjectMocks
    private UnidadeHospitalarServiceImpl unidadeHospitalarServiceImpl;

    @Mock
    private UnidadeHospitalar unidadeHospitalar;

    @Mock
    private UnidadeHospitalarRepository unidadeHospitalarRepository;

    @Mock
    private UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository;

    @Mock
    Page<UnidadeHospitalar> page;

    @Mock
    private DynamicExportsService dynamicExportsService;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

    @Test
    public void saveTest() {
        when(unidadeHospitalarServiceImpl.save(unidadeHospitalar)).thenReturn(unidadeHospitalar);
        UnidadeHospitalar test = unidadeHospitalarServiceImpl.save(unidadeHospitalar);
    }

    @Test
    public void deleteTest() {
        unidadeHospitalarServiceImpl.delete(anyLong());
    }

    @Test
    public void findAllTest() {
        Page<UnidadeHospitalar> test = unidadeHospitalarServiceImpl.findAll(java.util.Optional.of("test"), pageable);
        unidadeHospitalarServiceImpl.findAll(Optional.empty(),pageable);
    }

    @Test
    public void unidadeHospitalarFindOneTest() {
        UnidadeHospitalar test = unidadeHospitalarServiceImpl.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<UnidadeHospitalar> test = unidadeHospitalarServiceImpl.search("test", pageable);

    }

    @Test
    public void gerarRelatorioteste() throws RelatorioException, JRException, DRException, ClassNotFoundException {
        ResponseEntity responseEntity = mock(ResponseEntity.class);

        PowerMockito.mockStatic(DynamicExporter.class);

        when(DynamicExporter.output(any(), any())).thenReturn(responseEntity);

        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenReturn(new ByteArrayOutputStream());
        ResponseEntity resultado = unidadeHospitalarServiceImpl.gerarRelatorioExportacao("", "");

        Assert.assertEquals(resultado, responseEntity);
    }

    @Test(expected = RelatorioException.class)
    public void lanca_exception() throws RelatorioException, JRException, DRException, ClassNotFoundException {
        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenThrow(DRException.class);
        unidadeHospitalarServiceImpl.gerarRelatorioExportacao("", "");
    }
}
