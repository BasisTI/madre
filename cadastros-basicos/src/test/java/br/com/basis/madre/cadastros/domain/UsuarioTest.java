package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioTest {
    @InjectMocks
    Usuario usuario;

    @InjectMocks
    Usuario test;

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
    public void equalsTestOne(){
        usuario.equals(usuario);
    }

    @Test
    public void equalsTesTwo(){
        // o != null e o.getClass != getClass
        usuario.equals(unidadeHospitalar);

        // o == null e o.getClass == getClass
        test = null;
        usuario.equals(test);


    }

    @Test
    public void equalsTesThree(){
        // true and true
        usuario.equals(test);
        // false and true
        test.setId(0l);
        usuario.equals(test);
        // true and false
        test.setId(null);
        usuario.setId(0l);
        usuario.equals(test);
    }

    @Test
    public void equalsTestFour(){
        usuario.setId(1l);
        test.setId(0l);
        usuario.equals(test);


    }

    @Test
    public void getStringAtivoTest(){
        usuario.setAtivo(true);
        String test = usuario.getStringAtivo();
        usuario.setAtivo(false);
        test = usuario.getStringAtivo();
    }

}
