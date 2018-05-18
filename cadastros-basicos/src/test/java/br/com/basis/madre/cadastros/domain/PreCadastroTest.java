package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PreCadastroTest {
    @InjectMocks
    private PreCadastro preCadastro;

    @Test
    public void getIdTest(){
        preCadastro.getId();
    }

    @Test
    public void setIdTest(){
        preCadastro.setId(0L);
    }

    @Test
    public void getNomeDoPacienteTest(){
        preCadastro.getNomeDoPaciente();
    }

    @Test
    public void nomeDoPacienteTest(){
        PreCadastro test = preCadastro.nomeDoPaciente("test");
    }

    @Test
    public void setNomeDoPacienteTest(){
        preCadastro.setNomeDoPaciente("test");
    }


    @Test
    public void getNomeSocialTest(){
        preCadastro.getNomeSocial();

    }

    @Test
    public void nomeSocialTest(){
        PreCadastro teste = preCadastro.nomeSocial("test");
    }

    @Test
    public void setNomeSocialTest(){
        preCadastro.setNomeSocial("test");
    }

    @Test
    public void getNomeDaMaeTest(){
        preCadastro.getNomeDaMae();
    }

    @Test
    public void setNomeDaMaeTest(){
        preCadastro.setNomeDaMae("teste");
    }

    @Test
    public void nomeDaMaeTest(){
        PreCadastro test = preCadastro.nomeDaMae("teste");
    }

    @Test
    public void dataDeNascimentoTest(){
        PreCadastro test = preCadastro.dataDeNascimento(null);
    }

    @Test
    public void getDataDeNascimentoTest(){
        preCadastro.getDataDeNascimento();
    }

    @Test
    public void setDataDeNascimentoTest(){
        preCadastro.setDataDeNascimento(null);
    }

    @Test
    public void getNumCartaoSusTest(){
        preCadastro.getNumCartaoSus();
    }


    @Test
    public void setNumCartaoSusTest(){
        preCadastro.setNumCartaoSus("1");
    }


    @Test
    public void numCartaoSusTest(){
        PreCadastro test = preCadastro.numCartaoSus("0");
    }

    @Test
    public void isAtivoTest(){
        Boolean bool = preCadastro.isAtivo();
    }


    @Test
    public void ativoTest(){
        PreCadastro test = preCadastro.ativo(true);
    }


    @Test
    public void setAtivo(){
        preCadastro.setAtivo(true);
    }


    @Test
    public void hashCodeTest(){
        preCadastro.hashCode();
    }

    @Test
    public void toStringTest(){
        preCadastro.toString();
    }

    @Test
    public void equalsTest(){
        preCadastro.equals(preCadastro);
        preCadastro.equals(null);
        PreCadastro test = new PreCadastro();
        test.setId(null);
        preCadastro.equals(test);
    }

    @Test
    public void getDataNascimentoStringTest(){
        String test = preCadastro.getDataNascimentoString();
    }

    @Test
    public void getStringAtivoTest(){
        preCadastro.setAtivo(true);
        String test = preCadastro.getStringAtivo();
    }



}
