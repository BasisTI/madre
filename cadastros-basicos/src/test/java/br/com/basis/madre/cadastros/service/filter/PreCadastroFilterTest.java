package br.com.basis.madre.cadastros.service.filter;

import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PreCadastroFilterTest {
    @InjectMocks
    PreCadastroFilter preCadastroFilter;

    @Test
    public void filterElastichSearchTest(){
        QueryBuilder query = preCadastroFilter.filterElasticSearch("test");
        QueryBuilder test = preCadastroFilter.filterElasticSearch("12");

    }
}
