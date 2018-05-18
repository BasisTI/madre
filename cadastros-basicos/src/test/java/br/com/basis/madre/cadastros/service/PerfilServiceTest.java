package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.repository.PerfilRepository;
import br.com.basis.madre.cadastros.repository.search.PerfilSearchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PerfilServiceTest {
    @InjectMocks
    PerfilService perfilService;

    @Mock
    PerfilRepository perfilRepository;

    @Mock
    Perfil perfil;

    @Mock
    PerfilSearchRepository perfilSearchRepository;

    @Test
    public void saveTest(){
        when(perfilRepository.save(perfil)).thenReturn(perfil);

        Perfil test = perfilService.save(perfil);
    }
}
