package br.com.basis.madre.cadastros.web.rest;
import br.com.basis.madre.cadastros.domain.Usuario;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RunWith(PowerMockRunner.class)
public class UsuarioResourceTest {

@InjectMocks
UsuarioResource usuarioResource;

@Mock
Usuario usuario;
@Mock
String nome;

@Mock
Optional<String>  optinional;

@Mock
Pageable pageable;



//    @Test
//    public void updateUsuarioTest() throws URISyntaxException {usuarioResource.updateUsuario(usuario.nome(null));}
//
//    @Test
//    public void getAllUsuariosTest(){usuarioResource.getAllUsuarios(optinional,pageable);}
//
//    @Test
//    public void createUsuarioTest() throws URISyntaxException {
//        usuario = new Usuario(usuario.nome("Nome"),usuario.);
//        when(usuarioResource.createUsuario(usuario));
//    }

}
