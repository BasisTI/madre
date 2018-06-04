package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.dynamicexports.util.DynamicExporter;
import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.repository.UsuarioRepository;
import br.com.basis.madre.cadastros.repository.search.UsuarioSearchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
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
        usuarioServiceImpl.findAll(optional,pageable);
    }


    @Test
    public void especialidadeFindOneTest() {
        Usuario test = usuarioServiceImpl.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<Usuario> test = usuarioServiceImpl.search("test", pageable);

    }

}
