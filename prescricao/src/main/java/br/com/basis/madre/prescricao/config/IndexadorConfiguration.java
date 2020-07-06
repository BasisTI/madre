package br.com.basis.madre.prescricao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import br.com.basis.madre.prescricao.domain.Diluente;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.PrescricaoDieta;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.TipoAprazamento;
import br.com.basis.madre.prescricao.domain.TipoItemDieta;
import br.com.basis.madre.prescricao.domain.TipoMedicamento;
import br.com.basis.madre.prescricao.domain.TipoUnidadeDieta;
import br.com.basis.madre.prescricao.domain.UnidadeDose;
import br.com.basis.madre.prescricao.domain.UnidadeInfusao;
import br.com.basis.madre.prescricao.domain.ViasAdministracao;
import br.com.basis.madre.prescricao.repository.DiluenteRepository;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoDietaRepository;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.PrescricaoDietaRepository;
import br.com.basis.madre.prescricao.repository.PrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.TipoAprazamentoRepository;
import br.com.basis.madre.prescricao.repository.TipoItemDietaRepository;
import br.com.basis.madre.prescricao.repository.TipoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.TipoUnidadeDietaRepository;
import br.com.basis.madre.prescricao.repository.UnidadeDoseRepository;
import br.com.basis.madre.prescricao.repository.UnidadeInfusaoRepository;
import br.com.basis.madre.prescricao.repository.ViasAdministracaoRepository;
import br.com.basis.madre.prescricao.repository.search.DiluenteSearchRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoDietaSearchRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoDietaSearchRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.repository.search.TipoAprazamentoSearchRepository;
import br.com.basis.madre.prescricao.repository.search.TipoItemDietaSearchRepository;
import br.com.basis.madre.prescricao.repository.search.TipoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.repository.search.TipoUnidadeDietaSearchRepository;
import br.com.basis.madre.prescricao.repository.search.UnidadeDoseSearchRepository;
import br.com.basis.madre.prescricao.repository.search.UnidadeInfusaoSearchRepository;
import br.com.basis.madre.prescricao.repository.search.ViasAdministracaoSearchRepository;
import br.gov.nuvem.comum.microsservico.service.reindex.Indexador;
import br.gov.nuvem.comum.microsservico.service.reindex.IndexadorSemMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class IndexadorConfiguration {

    private final ElasticsearchOperations elasticsearchOperations;

    private final DiluenteRepository diluenteRepository;
    private final DiluenteSearchRepository diluenteSearchRepository;

    private final ItemPrescricaoDietaRepository itemPrescricaoDietaRepository;
    private final ItemPrescricaoDietaSearchRepository itemPrescricaoDietaSearchRepository;

    private final ItemPrescricaoMedicamentoRepository itemPrescricaoMedicamentoRepository;
    private final ItemPrescricaoMedicamentoSearchRepository itemPrescricaoMedicamentoSearchRepository;

    private final PrescricaoDietaRepository prescricaoDietaRepository;
    private final PrescricaoDietaSearchRepository prescricaoDietaSearchRepository;

    private final PrescricaoMedicamentoRepository prescricaoMedicamentoRepository;
    private final PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository;

    private final TipoAprazamentoRepository tipoAprazamentoRepository;
    private final TipoAprazamentoSearchRepository tipoAprazamentoSearchRepository;

    private final TipoItemDietaRepository tipoItemDietaRepository;
    private final TipoItemDietaSearchRepository tipoItemDietaSearchRepository;

    private final TipoMedicamentoRepository tipoMedicamentoRepository;
    private final TipoMedicamentoSearchRepository tipoMedicamentoSearchRepository;

    private final TipoUnidadeDietaRepository tipoUnidadeDietaRepository;
    private final TipoUnidadeDietaSearchRepository tipoUnidadeDietaSearchRepository;

    private final UnidadeDoseRepository unidadeDoseRepository;
    private final UnidadeDoseSearchRepository unidadeDoseSearchRepository;

    private final UnidadeInfusaoRepository unidadeInfusaoRepository;
    private final UnidadeInfusaoSearchRepository unidadeInfusaoSearchRepository;

    private final ViasAdministracaoRepository viasAdministracaoRepository;
    private final ViasAdministracaoSearchRepository viasAdministracaoSearchRepository;

    @Bean
    public Indexador indexadorDiluente() {
        return IndexadorSemMapper.<Diluente, Long>builder()
            .codigo("diluente")
            .descricao("Diluente")
            .jpaRepository(diluenteRepository)
            .elasticsearchClassRepository(diluenteSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorItemPrescricaoDieta() {
        return IndexadorSemMapper.<ItemPrescricaoDieta, Long>builder()
            .codigo("itemprescricaodieta")
            .descricao("Item prescrição dieta")
            .jpaRepository(itemPrescricaoDietaRepository)
            .elasticsearchClassRepository(itemPrescricaoDietaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorItemPrescricaoMedicamento() {
        return IndexadorSemMapper.<ItemPrescricaoMedicamento, Long>builder()
            .codigo("itemprescricaomedicamento")
            .descricao("Item prescrição medicamento")
            .jpaRepository(itemPrescricaoMedicamentoRepository)
            .elasticsearchClassRepository(itemPrescricaoMedicamentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorPrescricaoDieta() {
        return IndexadorSemMapper.<PrescricaoDieta, Long>builder()
            .codigo("prescricaodieta")
            .descricao("Prescrição dieta")
            .jpaRepository(prescricaoDietaRepository)
            .elasticsearchClassRepository(prescricaoDietaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorPrescricaoMedicamento() {
        return IndexadorSemMapper.<PrescricaoMedicamento, Long>builder()
            .codigo("prescricaomedicamento")
            .descricao("Prescrição medicamento")
            .jpaRepository(prescricaoMedicamentoRepository)
            .elasticsearchClassRepository(prescricaoMedicamentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTipoAprazamento() {
        return IndexadorSemMapper.<TipoAprazamento, Long>builder()
            .codigo("tipoaprazamento")
            .descricao("Tipo aprazamento")
            .jpaRepository(tipoAprazamentoRepository)
            .elasticsearchClassRepository(tipoAprazamentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTipoItemDieta() {
        return IndexadorSemMapper.<TipoItemDieta, Long>builder()
            .codigo("tipoitemdieta")
            .descricao("Tipo item dieta")
            .jpaRepository(tipoItemDietaRepository)
            .elasticsearchClassRepository(tipoItemDietaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTipoMedicamento() {
        return IndexadorSemMapper.<TipoMedicamento, Long>builder()
            .codigo("tipomedicamento")
            .descricao("Tipo de medicamento")
            .jpaRepository(tipoMedicamentoRepository)
            .elasticsearchClassRepository(tipoMedicamentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTipoUnidadeDieta() {
        return IndexadorSemMapper.<TipoUnidadeDieta, Long>builder()
            .codigo("tipounidadedieta")
            .descricao("Tipo unidade dieta")
            .jpaRepository(tipoUnidadeDietaRepository)
            .elasticsearchClassRepository(tipoUnidadeDietaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorUnidadeDose() {
        return IndexadorSemMapper.<UnidadeDose, Long>builder()
            .codigo("unidadedose")
            .descricao("Unidade dose")
            .jpaRepository(unidadeDoseRepository)
            .elasticsearchClassRepository(unidadeDoseSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorUnidadeInfusao() {
        return IndexadorSemMapper.<UnidadeInfusao, Long>builder()
            .codigo("unidadeinfusao")
            .descricao("Unidade infusão")
            .jpaRepository(unidadeInfusaoRepository)
            .elasticsearchClassRepository(unidadeInfusaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorViasAdministracao() {
        return IndexadorSemMapper.<ViasAdministracao, Long>builder()
            .codigo("viasadministracao")
            .descricao("Vias administração")
            .jpaRepository(viasAdministracaoRepository)
            .elasticsearchClassRepository(viasAdministracaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

}
