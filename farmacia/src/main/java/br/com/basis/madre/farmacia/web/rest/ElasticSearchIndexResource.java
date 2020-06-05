package br.com.basis.madre.farmacia.web.rest;
import java.util.List;

import br.com.basis.madre.farmacia.service.reindex.ElasticSearchIndexService;
import br.com.basis.madre.farmacia.service.reindex.Indexador;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ElasticSearchIndexResource {

    private final ElasticSearchIndexService elasticSearchIndexService;

    @GetMapping("/reindexar")
    public void reindexar(@RequestParam List<String> codigos) {
        this.elasticSearchIndexService.reindexar(codigos);
    }

    @GetMapping("/listar-indexadores")
    public List<Indexador> listarIndexadores() {
        return elasticSearchIndexService.listarIndexadores();
    }

}
