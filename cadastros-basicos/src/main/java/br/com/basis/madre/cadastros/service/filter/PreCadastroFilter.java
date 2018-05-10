package br.com.basis.madre.cadastros.service.filter;

import static org.elasticsearch.index.query.QueryBuilders.matchPhrasePrefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class PreCadastroFilter {

    private static final String SIM = "Sim";
    private static final String NAO = "Não";

    public QueryBuilder filterElasticSearch(String parametro) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        getLongFilter(queryBuilder, parametro);
        queryBuilder
            .should(matchPhrasePrefixQuery("nmTitulo", parametro.trim()))
            .should(matchPhrasePrefixQuery("txParecer", parametro.trim()));

        return queryBuilder;
    }
    private void getLongFilter(BoolQueryBuilder queryBuilder, String parametro) {
        if(parametro.matches("^[0-9]*$")) {
            queryBuilder
                .should(matchQuery("id", parametro));
        }
    }
}
