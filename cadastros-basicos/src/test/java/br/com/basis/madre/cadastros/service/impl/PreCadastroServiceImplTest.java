package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.repository.PreCadastroRepository;
import br.com.basis.madre.cadastros.repository.search.PreCadastroSearchRepository;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.repo.InputStreamResource;
import org.apache.commons.lang3.StringUtils;
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
public class PreCadastroServiceImplTest {
    @InjectMocks
    private PreCadastroServiceImpl preCadastroServiceImpl;

    @Mock
    private PreCadastro preCadastro;

    @Mock
    private PreCadastroRepository preCadastroRepository;

    @Mock
    private PreCadastroSearchRepository preCadastroSearchRepository;

    @Mock
    Page<PreCadastro> page;

    @Mock
    private Optional optional;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

    @Mock
    private ByteArrayOutputStream byteArrayOutputStream;

    @Mock
    private DynamicExporter dynamicExporter;

    @Mock
    private DynamicExportsService dynamicExportsService;

    @Test
    public void saveTest() {
        when(preCadastroServiceImpl.save(preCadastro)).thenReturn(preCadastro);
        PreCadastro test = preCadastroServiceImpl.save(preCadastro);
    }

    @Test
    public void deleteTest() {
        preCadastroServiceImpl.delete(anyLong());
    }

    @Test
    public void findAllTest() {
        Page<PreCadastro> test = preCadastroServiceImpl.findAll(java.util.Optional.of("test"), pageable);
        preCadastroServiceImpl.findAll(optional,pageable);
        StringUtils.isNotBlank(null);


    }
    @Test
    public void findAllCase4Test() {
        preCadastroServiceImpl.findAll(optional.empty(),pageable);
        Assert.assertEquals(false,optional.isPresent());
    }

    @Test
    public void especialidadeFindOneTest() {
        PreCadastro test = preCadastroServiceImpl.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<PreCadastro> test = preCadastroServiceImpl.search("test", pageable);

    }

    @Test
    public void gerarRelatorioteste() throws RelatorioException, JRException, DRException, ClassNotFoundException {
        ResponseEntity responseEntity = mock(ResponseEntity.class);

        PowerMockito.mockStatic(DynamicExporter.class);

        when(DynamicExporter.output(any(), any())).thenReturn(responseEntity);

        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenReturn(new ByteArrayOutputStream());
        ResponseEntity resultado = preCadastroServiceImpl.gerarRelatorioExportacao("", "");

        Assert.assertEquals(resultado, responseEntity);
    }

    @Test(expected = RelatorioException.class)
    public void lanca_exception() throws RelatorioException, JRException, DRException, ClassNotFoundException {
        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenThrow(DRException.class);
        preCadastroServiceImpl.gerarRelatorioExportacao("", "");
    }
}
