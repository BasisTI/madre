package br.com.basis.madre.cadastros.service;

import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.repository.PerfilRepository;
import br.com.basis.madre.cadastros.repository.search.PerfilSearchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Mock
    Pageable pageable;

    @Test
    public void saveTest(){
        when(perfilRepository.save(perfil)).thenReturn(perfil);
        Perfil test = perfilService.save(perfil);
    }

    @Test
    public void findAllTest(){
        Page<Perfil> test = perfilService.findAll(pageable);
    }

    @Test
    public void findOneTest(){
        Perfil test = perfilService.findOne(0L);

    }

    @Test
    public void deleteTest(){
        perfilService.delete(0L);
    }

    @Test
    public void searchTest(){
        Page<Perfil> test = perfilService.search("query",pageable);
    }

}
