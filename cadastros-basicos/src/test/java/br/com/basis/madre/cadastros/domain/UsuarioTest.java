package br.com.basis.madre.cadastros.domain;

import cucumber.api.java.ca.I;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Objects;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioTest {
    @InjectMocks
    Usuario usuario;
    @InjectMocks
    UnidadeHospitalar unidadeHospitalar;
    @Test
    public void getIdTest(){
        usuario.getId();
    }

    @Test
    public void setIdTest(){
        usuario.setId(0L);
    }

    @Test
    public void getNomeTest(){
        usuario.getNome();
    }

    @Test
    public void setNomeTest(){
        usuario.setNome("test");
    }

    @Test
    public void nomeTest(){
        Usuario test = usuario.nome("test");
    }

    @Test
    public void getLoginTest(){
        usuario.getLogin();
    }

    @Test
    public void setLoginTest(){
        usuario.setLogin("test");
    }

    @Test
    public void loginTest(){
        Usuario test = usuario.login("test");
    }

    @Test
    public void getSenhaTest(){
        usuario.getSenha();
    }

    @Test
    public void setSenhaTest(){
        usuario.setSenha("test");
    }

    @Test
    public void senhaTest(){
        Usuario test = usuario.senha("test");
    }

    @Test
    public void getEmailTest(){
        usuario.getEmail();
    }

    @Test
    public void setEmailTest(){
        usuario.setEmail("test");
    }

    @Test
    public void emailTest(){
        Usuario test = usuario.email("test");
    }

    @Test
    public void getPerfilTest(){
        usuario.getPerfil();
    }

    @Test
    public void setPerfilTest(){
        usuario.setPerfil("test");
    }

    @Test
    public void perfilTest(){
        Usuario test = usuario.perfil("test");
    }

    @Test
    public void getUnidadeDeSaudeTest(){
        usuario.getUnidadeDeSaude();
    }

    @Test
    public void setUnidadeDeSaudeTest(){
        usuario.setUnidadeDeSaude("test");
    }

    @Test
    public void unidadeDeSaudeTest(){
        Usuario test = usuario.unidadeDeSaude("test");
    }

    @Test
    public void isAtivoTest(){
        usuario.isAtivo();
    }

    @Test
    public void setAtivoTest(){
        usuario.setAtivo(true);
    }

    @Test
    public void ativoTest(){
        Usuario test = usuario.ativo(false);
    }


    @Test
    public void hashCodeTest(){
        usuario.hashCode();
    }

    @Test
    public void toStringTest(){
        String test = usuario.toString();
    }

    @Test
    public void equalsTest(){
        usuario.equals(usuario);
        usuario.equals(null);
        Usuario test = new Usuario();
        test.setId(null);
        usuario.equals(test);
    }
    @Test
    public void equalsNullTest(){
        Usuario test = new  Usuario();
        Usuario myTest = new  Usuario();
        test.setId(0l);
        myTest.setId(2l);
        test.equals(myTest);

    }

    @Test
    public void getStringAtivoTest() {
        usuario.setAtivo(true);
        usuario.getStringAtivo();
    }

}
