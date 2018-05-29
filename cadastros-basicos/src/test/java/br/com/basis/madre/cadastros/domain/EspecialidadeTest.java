package br.com.basis.madre.cadastros.domain;

import br.com.basis.dynamicexports.pojo.ReportObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;

@RunWith(MockitoJUnitRunner.class)
public class EspecialidadeTest implements Serializable, ReportObject {
    @InjectMocks
    private Especialidade especialidade;

    @InjectMocks
    private Especialidade test;

    @InjectMocks
    private Usuario usuario;

    @Test
    public void getIdTest(){
        especialidade.getId();
    }

    @Test
    public void setIdTest(){
        especialidade.setId(1L);
    }

    @Test
    public void getNomeTest(){
        especialidade.getNome();
    }

    @Test
    public void setNomeTest(){
        especialidade.setNome("teste");
    }

    @Test
    public void nomeTest(){
        especialidade.nome("teste");
    }


    @Test
    public void getDescricaoTest(){
        especialidade.getDescricao();
    }

    @Test
    public void setDescricaoTest(){
        especialidade.setDescricao("teste");
    }

    @Test
    public void descricaoTest(){
        especialidade.descricao("teste");
    }

    @Test
    public void hashCodeTest(){
        especialidade.hashCode();
    }

    @Test
    public void toStringTest(){
        String test = especialidade.toString();
    }

    @Test
    public void equalsTestOne(){
        especialidade.equals(especialidade);
    }

    @Test
    public void equalsTesTwo(){
        // o != null e o.getClass != getClass
        especialidade.equals(usuario);

        // o == null e o.getClass == getClass
        test = null;
        especialidade.equals(test);


    }

    @Test
    public void equalsTesThree(){
        // true and true
        especialidade.equals(test);
        // false and true
        test.setId(0l);
        especialidade.equals(test);
        // true and false
        test.setId(null);
        especialidade.setId(0l);
        especialidade.equals(test);
    }

    @Test
    public void equalsTestFour(){
        especialidade.setId(1l);
        test.setId(0l);
        especialidade.equals(test);


    }

}
