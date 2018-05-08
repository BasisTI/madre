package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.service.ElasticsearchIndexService;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ElasticsearchIndexResource {

    private final ElasticsearchIndexService elasticsearchIndexService;

    public ElasticsearchIndexResource(ElasticsearchIndexService elasticsearchIndexService) {
        this.elasticsearchIndexService = elasticsearchIndexService;
    }

    /**
     * POST  /elasticsearch/index -> Reindex all Elasticsearch documents
     */
    @GetMapping("/elasticsearch/index")
    @Timed
    public ResponseEntity<Void> reindexAll() throws URISyntaxException {
        elasticsearchIndexService.reindexAll();
        return ResponseEntity.accepted()
            .headers(HeaderUtil.createAlert("elasticsearch.reindex.accepted", null))
            .build();
    }
}
