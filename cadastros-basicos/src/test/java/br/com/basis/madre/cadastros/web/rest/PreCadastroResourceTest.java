package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.repository.PreCadastroRepository;
import br.com.basis.madre.cadastros.service.PreCadastroService;
import br.com.basis.madre.cadastros.web.rest.PreCadastroResource;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class PreCadastroResourceTest{
    @InjectMocks
    private PreCadastroResource preCadastroResource;

    @Mock
    private PreCadastroService preCadastroService;

    @Mock
    private PreCadastroRepository preCadastroRepository;

    @Mock
    private PreCadastro preCadastro;

    @Mock
    private Pageable pageable;

    @Mock
    PaginationUtil paginationUtil;


    @Test
    public void preCadastroResourceTest(){
        new PreCadastroResource(preCadastroService,preCadastroRepository);
    }

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

    @Test
    @PowerMockIgnore
    public void searchPreCadastroTest(){
        ResponseEntity<List<PreCadastro>> teste = preCadastroResource.searchPreCadastros("*", null);
    }

}
