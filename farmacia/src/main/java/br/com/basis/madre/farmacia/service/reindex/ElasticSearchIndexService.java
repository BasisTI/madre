package br.com.basis.madre.farmacia.service.reindex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class ElasticSearchIndexService {

    private static final Lock reindexLock = new ReentrantLock();
    private final List<Indexador> indexadores;
    private Map<String, Indexador> indexadoresPorCodigo;

    public void reindexar(List<String> codigos) {
        if (reindexLock.tryLock()) {
            try {
                codigos.forEach(c -> {
                    indexadoresPorCodigo.get(c).indexar();
                });
            } finally {
                reindexLock.unlock();
            }
        }
    }

    public List<Indexador> listarIndexadores() {
        return SonarUtil.instantiateList(indexadores);
    }

    @PostConstruct
    public void inicializaIndexadoresPorCodigo() {
        indexadoresPorCodigo = new HashMap<>();
        indexadores.forEach(i -> indexadoresPorCodigo.put(i.getCodigo(), i));
    }

}
