package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PreCadastroTest {
    @InjectMocks
    private PreCadastro preCadastro;

    @InjectMocks
    private PreCadastro test;

    @InjectMocks
    private Usuario usuario;

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
        PreCadastro test = preCadastro.nomeDoPaciente("Danilo Gonçalves");
    }

    @Test
    public void setNomeDoPacienteTest(){
        preCadastro.setNomeDoPaciente("Danilo Gonçalves");
    }


    @Test
    public void getNomeSocialTest(){
        preCadastro.getNomeSocial();

    }

    @Test
    public void nomeSocialTest(){
        PreCadastro test = preCadastro.nomeSocial("Maria");
    }

    @Test
    public void setNomeSocialTest(){
        preCadastro.setNomeSocial("Maria");
    }

    @Test
    public void getNomeDaMaeTest(){
        preCadastro.getNomeDaMae();
    }

    @Test
    public void setNomeDaMaeTest(){
        preCadastro.setNomeDaMae("Rita de Cássia");
    }

    @Test
    public void nomeDaMaeTest(){
        PreCadastro test = preCadastro.nomeDaMae("Rita de Cássia");
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
        preCadastro.setNumCartaoSus("704201205468582");
    }


    @Test
    public void numCartaoSusTest(){
        PreCadastro test = preCadastro.numCartaoSus("704201205468582");
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
    public void equalsTestOne(){
        preCadastro.equals(preCadastro);
    }

    @Test
    public void equalsTesTwo(){
        // o != null e o.getClass != getClass
        preCadastro.equals(usuario);

        // o == null e o.getClass == getClass
        test = null;
        preCadastro.equals(test);


    }

    @Test
    public void equalsTesThree(){
        // true and true
        preCadastro.equals(test);
        // false and true
        test.setId(0l);
        preCadastro.equals(test);
        // true and false
        test.setId(null);
        preCadastro.setId(0l);
        preCadastro.equals(test);
    }

    @Test
    public void equalsTestFour(){
        preCadastro.setId(1l);
        test.setId(0l);
        preCadastro.equals(test);


    }


    @Test
    public void getDataNascimentoStringTest(){
        String test = preCadastro.getDataNascimentoString();
        preCadastro.setDataDeNascimento(null);
        test = preCadastro.getDataNascimentoString();
    }

    @Test
    public void getStringAtivoTest(){
        preCadastro.setAtivo(true);
        String test = preCadastro.getStringAtivo();
        preCadastro.setAtivo(false);
        test = preCadastro.getStringAtivo();
    }



}
