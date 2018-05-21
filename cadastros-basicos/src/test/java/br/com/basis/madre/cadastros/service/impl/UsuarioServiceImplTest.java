package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.repository.UsuarioRepository;
import br.com.basis.madre.cadastros.repository.search.UsuarioSearchRepository;
import net.sf.jasperreports.repo.InputStreamResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
    Page<Usuario> page;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

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
}
