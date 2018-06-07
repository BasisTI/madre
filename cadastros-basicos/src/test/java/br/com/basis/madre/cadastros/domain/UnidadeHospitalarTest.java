package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class UnidadeHospitalarTest {

    byte[] bytes = {69, 121, 101, 45, 62, 118, 101, 114, (byte) 196, (byte) 195, 61, 101, 98};

    @InjectMocks
    private UnidadeHospitalar unidadeHospitalar;

    @InjectMocks
    private UnidadeHospitalar test;

    @InjectMocks
    private Usuario usuario;

//    @Test
//    public void getLogoTest() { unidadeHospitalar.getLogo();
//    }

    @Test
    public void setLogoTest() { unidadeHospitalar.setLogo(bytes);
    }

    @Test
    public void logoTest(){
        UnidadeHospitalar test = unidadeHospitalar.logo(bytes);
    }

    @Test
    public void getLogoContentTest() { unidadeHospitalar.getLogoContentType();
    }

    @Test
    public void setLogoContentTest() { unidadeHospitalar.setLogoContentType("imagem/png");
    }

    @Test
    public void logoContentTest(){
        UnidadeHospitalar test = unidadeHospitalar.logoContentType("imagem/png");
    }

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
        unidadeHospitalar.setSigla("HFA");
    }

    @Test
    public void siglaTest(){
        UnidadeHospitalar test = unidadeHospitalar.sigla("HFA");
    }

    @Test
    public void getNomeTest(){
        unidadeHospitalar.getNome();
    }

    @Test
    public void setNomeTest(){
        unidadeHospitalar.setNome("Hospital das forças armadas");
    }

    @Test
    public void nomeTest(){
        UnidadeHospitalar test = unidadeHospitalar.nome("Hospital das forças armadas");
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
        unidadeHospitalar.setEndereco("SQS 17, Lote 500, Setor de teste");
    }

    @Test
    public void enderecoTest(){
        UnidadeHospitalar test = unidadeHospitalar.endereco("SQS 17, Lote 500, Setor de teste");
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
    public void equalsTestOne(){
        unidadeHospitalar.equals(unidadeHospitalar);
    }

    @Test
    public void equalsTesTwo(){
        // o != null e o.getClass != getClass
        unidadeHospitalar.equals(usuario);

        // o == null e o.getClass == getClass
        test = null;
        unidadeHospitalar.equals(test);


    }

    @Test
    public void equalsTesThree(){
        // true and true
        unidadeHospitalar.equals(test);
        // false and true
        test.setId(0l);
        unidadeHospitalar.equals(test);
        // true and false
        test.setId(null);
        unidadeHospitalar.setId(0l);
        unidadeHospitalar.equals(test);
    }

    @Test
    public void equalsTestFour(){
        unidadeHospitalar.setId(1l);
        test.setId(0l);
        unidadeHospitalar.equals(test);


    }

}
