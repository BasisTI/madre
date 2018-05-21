package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UnidadeHospitalarTest {
    @InjectMocks
    private UnidadeHospitalar unidadeHospitalar;

    @Test
    public void getIdTest(){
        unidadeHospitalar.getId();
    }

    @Test
    public void setIdTest(){
        unidadeHospitalar.setId(0L);
    }

    @Test
    public void getSiglaTest(){
        unidadeHospitalar.getSigla();
    }

    @Test
    public void setSiglaTest(){
        unidadeHospitalar.setSigla("test");
    }

    @Test
    public void siglaTest(){
        UnidadeHospitalar test = unidadeHospitalar.sigla("test");
    }

    @Test
    public void getNomeTest(){
        unidadeHospitalar.getNome();
    }

    @Test
    public void setNomeTest(){
        unidadeHospitalar.setNome("test");
    }

    @Test
    public void nomeTest(){
        UnidadeHospitalar test = unidadeHospitalar.nome("test");
    }

    @Test
    public void getCnpjTest(){
        unidadeHospitalar.getCnpj();
    }

    @Test
    public void setCnpjTest(){
        unidadeHospitalar.setCnpj("61258133000157");
    }

    @Test
    public void cnpjTest(){
        UnidadeHospitalar test = unidadeHospitalar.cnpj("61258133000157");
    }


    @Test
    public void getEnderecoTest(){
        unidadeHospitalar.getEndereco();
    }

    @Test
    public void setEnderecoTest(){
        unidadeHospitalar.setEndereco("test");
    }

    @Test
    public void enderecoTest(){
        UnidadeHospitalar test = unidadeHospitalar.endereco("test");
    }

    @Test
    public void isAtivoTest(){
        Boolean bool = unidadeHospitalar.isAtivo();
    }

    @Test
    public void setAtivoTest(){
        unidadeHospitalar.setAtivo(true);
    }

    @Test
    public void ativoTest(){
        UnidadeHospitalar test = unidadeHospitalar.ativo(true);
    }

    @Test
    public void hashCodeTest(){
        unidadeHospitalar.hashCode();
    }

    @Test
    public void toStringTest(){
        String unidade = unidadeHospitalar.toString();
    }

    @Test
    public void equalsTest(){
        unidadeHospitalar.equals(unidadeHospitalar);
        unidadeHospitalar.equals(null);
        UnidadeHospitalar test = new UnidadeHospitalar();
        test.setId(null);
        unidadeHospitalar.equals(test);
    }

    @Test
    public void getLogoIdTest() {
        unidadeHospitalar.getLogoId();
    }

    @Test
    public void setLogoIdTest() {
        unidadeHospitalar.setLogoId(0);
    }

    @Test
    public void equalsNullTest(){
        UnidadeHospitalar test = new UnidadeHospitalar();
        UnidadeHospitalar myTest = new  UnidadeHospitalar();
        test.setId(0l);
        myTest.setId(2l);
        test.equals(myTest);

    }

}
