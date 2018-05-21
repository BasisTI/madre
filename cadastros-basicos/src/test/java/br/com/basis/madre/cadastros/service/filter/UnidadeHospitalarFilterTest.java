package br.com.basis.madre.cadastros.service.filter;

import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UnidadeHospitalarFilterTest {
    @InjectMocks
    UnidadeHospitalarFilter unidadeHospitalarFilter;

    @Test
    public void filterElastichSearchTest(){
        QueryBuilder query = unidadeHospitalarFilter.filterElasticSearch("test");
        QueryBuilder test = unidadeHospitalarFilter.filterElasticSearch("12");

    }
}
