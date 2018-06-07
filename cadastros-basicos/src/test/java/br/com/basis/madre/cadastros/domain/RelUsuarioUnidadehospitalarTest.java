package br.com.basis.madre.cadastros.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by pedro-ramalho on 05/06/18.
 */
@RunWith(PowerMockRunner.class)
public class RelUsuarioUnidadehospitalarTest {


    @InjectMocks
    RelUsuarioUnidadehospitalar relUsuarioUnidadehospitalar;

    @Mock
    Long id;

    @Mock
    Usuario usuario;

    @Mock
    UnidadeHospitalar unidadeHospitalar;

    @Mock
     Object o;

//    @Mock
//    RelUsuarioUnidadehospitalar relUsuarioUnidadehospitalartst = (RelUsuarioUnidadehospitalar) o;



    @Test
    public void relUsuarioUnidadehospitalarTest(){new RelUsuarioUnidadehospitalar();}

    @Test
    public void getIdTest(){relUsuarioUnidadehospitalar.getId();Assert.assertEquals(id,relUsuarioUnidadehospitalar.getId());}

    @Test
    public void setIdTest(){relUsuarioUnidadehospitalar.setId(id);}


    @Test
    public void getUsuarioTest(){ relUsuarioUnidadehospitalar.getUsuario();Assert.assertEquals(usuario,relUsuarioUnidadehospitalar.getUsuario());}

    @Test
    public void setUsuarioTest(){relUsuarioUnidadehospitalar.setUsuario(usuario);}

    @Test
    public void usuarioTest(){new RelUsuarioUnidadehospitalar().usuario(usuario);}

    @Test
    public void getUnidadeHospitalTest(){relUsuarioUnidadehospitalar.getUnidadeHospitalar();}

    @Test
    public void unidadeHospitalarTest(){relUsuarioUnidadehospitalar.unidadeHospitalar(unidadeHospitalar);}


    @Test
    public void equalsTest(){

        //True and False Case 1
        UnidadeHospitalar uh = new UnidadeHospitalar();
        RelUsuarioUnidadehospitalar ruu = new RelUsuarioUnidadehospitalar();

        uh.nome("Hospital Santa Helena");
        ruu.setId(1l);

        relUsuarioUnidadehospitalar.equals(new RelUsuarioUnidadehospitalar());
        relUsuarioUnidadehospitalar.equals(new RelUsuarioUnidadehospitalarTest());
        relUsuarioUnidadehospitalar.equals(ruu);
        relUsuarioUnidadehospitalar.equals(null);
        relUsuarioUnidadehospitalar.equals(uh.getNome());


    }

    @Test
    public void hashCodeTest(){ relUsuarioUnidadehospitalar.hashCode();}

    @Test
    public void toStringTest(){ relUsuarioUnidadehospitalar.toString();}


}

