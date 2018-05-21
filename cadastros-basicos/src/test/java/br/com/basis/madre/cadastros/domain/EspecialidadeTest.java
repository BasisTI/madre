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
    public void equalsTest(){
        especialidade.equals(especialidade);
        especialidade.equals(null);
        Especialidade test = new Especialidade();
        test.setId(null);
        especialidade.equals(test);
    }

    @Test
    public void equalsNullTest(){
        Especialidade test = new  Especialidade();
        Especialidade myTest = new Especialidade();
        test.setId(0l);
        myTest.setId(2l);
        test.equals(myTest);

    }

}
