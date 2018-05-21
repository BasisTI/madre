package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.repository.UsuarioRepository;
import br.com.basis.madre.cadastros.repository.search.UsuarioSearchRepository;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.relatorio.colunas.RelatorioUsuarioColunas;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import org.elasticsearch.index.query.QueryBuilder;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Optional.class})
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
    private DynamicExporter dynamicExporter;

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
   /* @Test
    public void gerarRelatorioExportacaoTest()
        throws JRException, DRException, ClassNotFoundException, RelatorioException {
        when(usuarioSearchRepository.search(any(QueryBuilder.class),any(Pageable.class))).thenReturn(page);
        PowerMockito.mockStatic(Optional.class);
        when(optional.empty()).thenReturn(optional);
        when(optional.ofNullable("teste")).thenReturn(optional);
        when(dynamicExportsService.export(any(RelatorioUsuarioColunas.class),any(Page.class),anyString(),any(Optional.class),any(Optional.class),any(Optional.class))).thenReturn(byteArrayOutputStream);
        PowerMockito.mockStatic(DynamicExporter.class);
        usuarioServiceImpl.gerarRelatorioExportacao("pdf", "TST");

    }*/
}
