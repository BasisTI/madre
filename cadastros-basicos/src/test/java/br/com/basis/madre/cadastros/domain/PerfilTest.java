package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PerfilTest {
    @InjectMocks
    private Perfil perfil;

    @InjectMocks
    private Perfil test;

    @InjectMocks
    private Usuario usuario;


    @Test
    public void getIdTest(){
        perfil.getId();
    }

    @Test
    public void setIdTest(){
        perfil.setId(1l);
    }

    @Test
    public void getNmPerfilTest(){
        perfil.getNmPerfil();
    }

    @Test
    public void setNmPerfilTest(){
        perfil.setNmPerfil(1);
    }

    @Test
    public void getDsPerfilTest(){
        perfil.getDsPerfil();
    }

    @Test
    public void setDsPerfilTest(){
        perfil.setDsPerfil("teste");
    }

    @Test
    public void dsPerfilTest(){
        Perfil test = perfil.dsPerfil("teste");
    }

    @Test
    public void nmPerfilTest(){
        Perfil test = perfil.nmPerfil(1);
    }

    @Test
    public void isStExcluidoTest(){
        Boolean test = perfil.isStExcluido();
    }

    @Test
    public void stExcluidoTest(){
        Perfil test = perfil.stExcluido(true);
    }

    @Test
    public void setStExcluidoTest(){
        perfil.setStExcluido(true);
    }

    @Test
    public void isStAtivoTest(){
        Boolean bool = perfil.isStAtivo();
    }

    @Test
    public void stAtivoTest(){
        Perfil test = perfil.stAtivo(true);
    }

    @Test
    public void setStAtivoTeste(){
        perfil.setStAtivo(true);
    }

    @Test
    public void getIdFuncionalidadeTest(){
        perfil.getIdFuncionalidade();
    }

    @Test
    public void idFuncionalidade(){
        Perfil teste = perfil.idFuncionalidade(1);
    }

    @Test
    public void setIdFuncionalidadeTest(){
        perfil.setIdFuncionalidade(1);

    }

    @Test
    public void hashCodeTest(){
        perfil.hashCode();
    }

    @Test
    public void toStringTest(){
        perfil.toString();
    }

    @Test
    public void equalsTestOne(){
        perfil.equals(perfil);
    }

    @Test
    public void equalsTesTwo(){
        // o != null e o.getClass != getClass
        perfil.equals(usuario);

        // o == null e o.getClass == getClass
        test = null;
        perfil.equals(test);


    }

    @Test
    public void equalsTesThree(){
        // true and true
        perfil.equals(test);
        // false and true
        test.setId(0l);
        perfil.equals(test);
        // true and false
        test.setId(null);
        perfil.setId(0l);
        perfil.equals(test);
    }

    @Test
    public void equalsTestFour(){
        perfil.setId(1l);
        test.setId(0l);
        perfil.equals(test);


    }


}
