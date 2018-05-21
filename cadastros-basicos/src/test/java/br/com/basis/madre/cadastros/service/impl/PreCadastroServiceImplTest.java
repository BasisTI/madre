package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.repository.PreCadastroRepository;
import br.com.basis.madre.cadastros.repository.search.PreCadastroSearchRepository;
import net.sf.jasperreports.repo.InputStreamResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreCadastroServiceImplTest {
    @InjectMocks
    private PreCadastroServiceImpl preCadastroServiceImpl;

    @Mock
    private PreCadastro preCadastro;

    @Mock
    private PreCadastroRepository preCadastroRepository;

    @Mock
    private PreCadastroSearchRepository preCadastroSearchRepository;

    @Mock
    Page<PreCadastro> page;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

    @Test
    public void saveTest() {
        when(preCadastroServiceImpl.save(preCadastro)).thenReturn(preCadastro);
        PreCadastro test = preCadastroServiceImpl.save(preCadastro);
    }

    @Test
    public void deleteTest() {
        preCadastroServiceImpl.delete(anyLong());
    }

    @Test
    public void findAllTest() {
        Page<PreCadastro> test = preCadastroServiceImpl.findAll(java.util.Optional.of("test"), pageable);
    }

    @Test
    public void especialidadeFindOneTest() {
        PreCadastro test = preCadastroServiceImpl.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<PreCadastro> test = preCadastroServiceImpl.search("test", pageable);

    }
}
