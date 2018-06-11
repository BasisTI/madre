package br.com.basis.madre.cadastros.web.rest.vm;

import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.repository.UsuarioRepository;
import br.com.basis.madre.cadastros.service.UsuarioService;
import br.com.basis.madre.cadastros.web.rest.UsuarioResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * Created by pedro-ramalho on 07/06/18.
 */
@RunWith(PowerMockRunner.class)
public class UsuarioResourceTest {

    @Mock
    private static final String ENTITY_NAME = "usuario";

    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    UsuarioService usuarioService;

    @InjectMocks
    UsuarioResource usuarioResource;

    @Mock
    Usuario usuario;

    @Mock
    ResponseEntity<Usuario> responseEntity;

    @Mock
    Pageable pageable;

    @Mock
    Long id = 10l;

    @Mock
    String query;

    @Test
    public void usuarioResourceTest(){new UsuarioResource(usuarioRepository,usuarioService);}

    @Test
    public void deleteUsuarioTest(){usuarioResource.deleteUsuario(1l);}

    @Test
    public void getUsuarioTest(){usuarioResource.getUsuario(1l);}

    @Test
    public void getRelatorioExportacaoTest(){usuarioResource.getRelatorioExportacao("teste","teste");}


}
