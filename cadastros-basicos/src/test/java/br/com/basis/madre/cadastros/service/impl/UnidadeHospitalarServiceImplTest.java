package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.search.UnidadeHospitalarSearchRepository;
import net.sf.jasperreports.repo.InputStreamResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnidadeHospitalarServiceImplTest {
    @InjectMocks
    private UnidadeHospitalarServiceImpl unidadeHospitalarServiceImpl;

    @Mock
    private UnidadeHospitalar unidadeHospitalar;

    @Mock
    private UnidadeHospitalarRepository unidadeHospitalarRepository;

    @Mock
    private UnidadeHospitalarSearchRepository unidadeHospitalarSearchRepository;

    @Mock
    Page<UnidadeHospitalar> page;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

    @Test
    public void saveTest() {
        when(unidadeHospitalarServiceImpl.save(unidadeHospitalar)).thenReturn(unidadeHospitalar);
        UnidadeHospitalar test = unidadeHospitalarServiceImpl.save(unidadeHospitalar);
    }

    @Test
    public void deleteTest() {
        unidadeHospitalarServiceImpl.delete(anyLong());
    }

    @Test
    public void findAllTest() {
        Page<UnidadeHospitalar> test = unidadeHospitalarServiceImpl.findAll(java.util.Optional.of("test"), pageable);
        unidadeHospitalarServiceImpl.findAll(Optional.empty(),pageable);
    }

    @Test
    public void unidadeHospitalarFindOneTest() {
        UnidadeHospitalar test = unidadeHospitalarServiceImpl.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<UnidadeHospitalar> test = unidadeHospitalarServiceImpl.search("test", pageable);

    }
}
