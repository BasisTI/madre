package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.dynamicexports.service.DynamicExportsService;
import br.com.basis.madre.cadastros.domain.Especialidade;
import br.com.basis.madre.cadastros.repository.EspecialidadeRepository;
import br.com.basis.madre.cadastros.repository.search.EspecialidadeSearchRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class EspecialidadeServiceImplTest {
    @InjectMocks
    private EspecialidadeServiceImpl especialidadeServiceImpl;

    @Mock
    private Especialidade especialidade;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @Mock
    private EspecialidadeSearchRepository especialidadeSearchRepository;

    @Mock
    Page<Especialidade> page;

    @Mock
    private Pageable pageable;

    @Mock
    private ResponseEntity<InputStreamResource> responseEntity;

    @Mock
    DynamicExportsService dynamicExportsService;

    @Mock
    QueryBuilder queryBuilder;

    @Mock
    String tipoRelatorio;

    @Mock
    ByteArrayOutputStream byteArrayOutputStream;

    @Mock
    String query;

    @Test
    public void saveTest() {
        when(especialidadeServiceImpl.save(especialidade)).thenReturn(especialidade);
        Especialidade test = especialidadeServiceImpl.save(especialidade);
    }

    @Test
    public void deleteTest() {
        especialidadeServiceImpl.delete(anyLong());
    }

    @Test
    public void findAllTest() {
        Page<Especialidade> test = especialidadeServiceImpl.findAll(java.util.Optional.of("test"), pageable);
        especialidadeServiceImpl.findAll(Optional.empty(),pageable);
    }

    @Test
    public void especialidadeFindOneTest() {
        Especialidade test = especialidadeServiceImpl.findOne(anyLong());
    }

    @Test
    public void searchTest() {
        Page<Especialidade> test = especialidadeServiceImpl.search("test", pageable);

    }

//    @Test(expected = RelatorioException.class)
//    public void gerarRelatorioExportacao() throws RelatorioException {
//
//    Assert.assertEquals("ENTIDADE",RelatorioException.getCodeEntidade());
//    //when(especialidadeServiceImpl.gerarRelatorioExportacao(tipoRelatorio,query)).thenThrow(new RelatorioException());
//
//    }

}
