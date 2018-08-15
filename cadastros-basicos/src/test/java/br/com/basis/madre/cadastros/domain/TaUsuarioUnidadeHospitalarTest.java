package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaUsuarioUnidadeHospitalarTest {
    @InjectMocks
    TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar;

    @InjectMocks
    TaUsuarioUnidadeHospitalar test;


    @Test
    public void taUsuarioUnidadeHospitalarTest(){ new TaUsuarioUnidadeHospitalar();}

    @Test
    public void getUnidadeHospitalarIdTest() { taUsuarioUnidadeHospitalar.getUnidadeHospitalarId(); }

    @Test
    public void setUnidadeHospitalarIdTest() { taUsuarioUnidadeHospitalar.setUnidadeHospitalarId(0L); }

    @Test
    public void getIdTest(){
        taUsuarioUnidadeHospitalar.getId();
    }

    @Test
    public void setIdTest(){
        taUsuarioUnidadeHospitalar.setId(0L);
    }

    @Test
    public void getUsuarioIdTest(){
        taUsuarioUnidadeHospitalar.getUsuarioId();
    }

    @Test
    public void setUsuarioIdTest() { taUsuarioUnidadeHospitalar.setUsuarioId(0L); }

    @Test
    public void hashCodeTest(){ taUsuarioUnidadeHospitalar.hashCode(); }

    @Test
    public void toStringTest(){ String test = taUsuarioUnidadeHospitalar.toString();
    }

    @Test
    public void equalsTestOne(){ taUsuarioUnidadeHospitalar.equals(taUsuarioUnidadeHospitalar); }



    @Test
    public void equalsTesTwo(){
        // o != null e o.getClass != getClass
        taUsuarioUnidadeHospitalar.equals(taUsuarioUnidadeHospitalar);

        // o == null e o.getClass == getClass
        test = null;
        taUsuarioUnidadeHospitalar.equals(test);


    }

    @Test
    public void equalsTesThree(){
        // true and true
        taUsuarioUnidadeHospitalar.equals(test);
        // false and true
        test.setId(0l);
        taUsuarioUnidadeHospitalar.equals(test);
        // true and false
        test.setId(null);
        taUsuarioUnidadeHospitalar.setId(0l);
        taUsuarioUnidadeHospitalar.equals(test);
    }

    @Test
    public void equalsTestFour(){
        taUsuarioUnidadeHospitalar.setId(1l);
        test.setId(0l);
        taUsuarioUnidadeHospitalar.equals(test);


    }


}
