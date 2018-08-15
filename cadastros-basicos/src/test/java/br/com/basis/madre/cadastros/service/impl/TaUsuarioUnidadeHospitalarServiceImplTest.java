package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.TaUsuarioUnidadeHospitalar;
import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.TaUsuarioUnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.search.TaUsuarioUnidadeHospitalarSearchRepository;
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
public class TaUsuarioUnidadeHospitalarServiceImplTest {

    @InjectMocks
    private TaUsuarioUnidadeHospitalarServiceImpl taUsuarioUnidadeHospitalarServiceipml;

    @Mock
    private TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar;

    @Mock
    private TaUsuarioUnidadeHospitalarRepository taUsuarioUnidadeHospitalarRepository;

    @Mock
    private TaUsuarioUnidadeHospitalarSearchRepository taUsuarioUnidadeHospitalarSearchRepository;

    @Mock
    Page<TaUsuarioUnidadeHospitalar> page;

    @Mock
    private DynamicExportsService dynamicExportsService;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

    @Test
    public void saveTest() {
        when(taUsuarioUnidadeHospitalarServiceipml.save(taUsuarioUnidadeHospitalar)).thenReturn(taUsuarioUnidadeHospitalar);
        TaUsuarioUnidadeHospitalar test = taUsuarioUnidadeHospitalarServiceipml.save(taUsuarioUnidadeHospitalar);
    }

    @Test
    public void deleteTest() {
        taUsuarioUnidadeHospitalarServiceipml.delete(anyLong());
    }

//    @Test
//    public void findAllTest() {
//        Page<TaUsuarioUnidadeHospitalar> test = taUsuarioUnidadeHospitalarServiceipml.findAll(Optional.of("test"), pageable);
//        taUsuarioUnidadeHospitalarServiceipml.findAll(Optional.empty(),pageable);
//    }

    @Test
    public void taUsuarioUnidadeHospitalarFindOneTest() {
        TaUsuarioUnidadeHospitalar test = taUsuarioUnidadeHospitalarServiceipml.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<TaUsuarioUnidadeHospitalar> test = taUsuarioUnidadeHospitalarServiceipml.search("test", pageable);

    }

//    @Test
//    public void gerarRelatorioteste() throws RelatorioException, JRException, DRException, ClassNotFoundException {
//        ResponseEntity responseEntity = mock(ResponseEntity.class);
//
//        PowerMockito.mockStatic(DynamicExporter.class);
//
//        when(DynamicExporter.output(any(), any())).thenReturn(responseEntity);
//
//        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenReturn(new ByteArrayOutputStream());
//        ResponseEntity resultado = taUsuarioUnidadeHospitalarServiceipml.gerarRelatorioExportacao("", "");
//
//        Assert.assertEquals(resultado, responseEntity);
//    }

//    @Test(expected = RelatorioException.class)
//    public void lanca_exception() throws RelatorioException, JRException, DRException, ClassNotFoundException {
//        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenThrow(DRException.class);
//        taUsuarioUnidadeHospitalarServiceipml.gerarRelatorioExportacao("", "");
//    }
}
