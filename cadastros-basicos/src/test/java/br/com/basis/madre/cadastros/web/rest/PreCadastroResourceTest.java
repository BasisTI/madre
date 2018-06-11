package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.repository.PreCadastroRepository;
import br.com.basis.madre.cadastros.service.PreCadastroService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class PreCadastroResourceTest{
    @InjectMocks
    PreCadastroResource preCadastroResource;

    @Mock
    PreCadastroService preCadastroService;

    @Mock
    PreCadastroRepository preCadastroRepository;


@Test
public  void tst(){new PreCadastroResource(preCadastroService,preCadastroRepository);}


    @Test
    public void deletePreCadastro(){
        ResponseEntity<Void> teste = preCadastroResource.deletePreCadastro(1l);
    }

    @Test
    public void getPreCadastroTest(){
        ResponseEntity<PreCadastro> teste = preCadastroResource.getPreCadastro(1l);
    }

    @Test
    public void getRelatorioExportacaoTest(){
        preCadastroResource.getRelatorioExportacao("teste","teste");
    }

}
