package br.com.basis.madre.cadastros.service.filter;

import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioFilterTest {
    @InjectMocks
    UsuarioFilter usuarioFilter;

    @Test
    public void filterElastichSearchTest(){
        QueryBuilder query = usuarioFilter.filterElasticSearch("test");
        QueryBuilder test = usuarioFilter.filterElasticSearch("12");

    }
}
