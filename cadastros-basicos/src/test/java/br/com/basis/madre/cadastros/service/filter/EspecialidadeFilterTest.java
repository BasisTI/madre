package br.com.basis.madre.cadastros.service.filter;

import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EspecialidadeFilterTest {
    @InjectMocks
    EspecialidadeFilter especialidadeFilter;
    @Test
    public void filterElastichSearchTest(){
        QueryBuilder query = especialidadeFilter.filterElasticSearch("test");
        QueryBuilder test = especialidadeFilter.filterElasticSearch("12");
    }



}
