package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.repository.UsuarioRepository;
import br.com.basis.madre.cadastros.repository.search.UsuarioSearchRepository;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
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
public class UsuarioServiceImplTest {


    @InjectMocks
    private UsuarioServiceImpl usuarioServiceImpl;

    @Mock
    private Usuario usuario;

    @Mock
    private UsuarioRepository usuarioRepository;


    @Mock
    private UsuarioSearchRepository usuarioSearchRepository;

    @Mock
    private Page<Usuario> page;

    @Mock
    private Page<Usuario> page2;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

    @Mock
    private DynamicExportsService dynamicExportsService;

    @Mock
    private Optional optional;

    @Mock
    private ByteArrayOutputStream byteArrayOutputStream;

    @Mock
    private Optional<String> query;

    @Mock
    private DynamicExporter dynamicExporter;

    @Mock
    private StringUtils stringUtils;

    @Mock
    private Page<Usuario> result;

    @Mock
    private QueryStringQueryBuilder queryStringQuery;

    @Test
    public void saveTest() {
        when(usuarioServiceImpl.save(usuario)).thenReturn(usuario);
        Usuario test = usuarioServiceImpl.save(usuario);
    }

    @Test
    public void deleteTest() {
        usuarioServiceImpl.delete(anyLong());
    }

    @Test
    public void findAllTest() {
        when(query.isPresent()).thenReturn(true).thenReturn(false);
        when(stringUtils.isNotBlank(query.get())).thenReturn(true);
        Page<Usuario> test = usuarioServiceImpl.findAll(java.util.Optional.of("test"), pageable);
    }

    @Test
    public void findAllTestfalse() {

        when(query.isPresent()).thenReturn(false);
        when(stringUtils.isNotBlank(query.get())).thenReturn(false);
        usuarioServiceImpl.findAll(optional,pageable);
        Page<Usuario> test = usuarioServiceImpl.findAll(java.util.Optional.of("test"), pageable);
    }

    @Test
    public void especialidadeFindOneTest() {
        Usuario test = usuarioServiceImpl.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<Usuario> test = usuarioServiceImpl.search("test", pageable);

    }

    @Test
    public void gerarRelatorioteste() throws RelatorioException, JRException, DRException, ClassNotFoundException {
        ResponseEntity responseEntity = mock(ResponseEntity.class);

        PowerMockito.mockStatic(DynamicExporter.class);

        when(DynamicExporter.output(any(), any())).thenReturn(responseEntity);

        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenReturn(new ByteArrayOutputStream());
        ResponseEntity resultado = usuarioServiceImpl.gerarRelatorioExportacao("", "");

        Assert.assertEquals(resultado, responseEntity);
    }

    @Test(expected = RelatorioException.class)
    public void lanca_exception() throws RelatorioException, JRException, DRException, ClassNotFoundException {
        when(dynamicExportsService.export(any(), any(), any(), any(), any(), any())).thenThrow(DRException.class);
        usuarioServiceImpl.gerarRelatorioExportacao("", "");
    }

}
