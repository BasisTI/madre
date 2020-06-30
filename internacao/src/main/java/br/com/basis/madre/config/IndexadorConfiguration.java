package br.com.basis.madre.config;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.repository.*;
import br.com.basis.madre.repository.search.*;
import br.gov.nuvem.comum.microsservico.service.reindex.Indexador;
import br.gov.nuvem.comum.microsservico.service.reindex.IndexadorSemMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@AllArgsConstructor
@Configuration
public class IndexadorConfiguration {

    private final ElasticsearchOperations elasticsearchOperations;

    private final CaraterDaInternacaoRepository caraterDaInternacaoRepository;
    private final CaraterDaInternacaoSearchRepository caraterDaInternacaoSearchRepository;

    private final CIDRepository cidRepository;
    private final CIDSearchRepository cidSearchRepository;

    private final ConvenioDeSaudeRepository convenioDeSaudeRepository;
    private final ConvenioDeSaudeSearchRepository convenioDeSaudeSearchRepository;

    private final CRMRepository crmRepository;
    private final CRMSearchRepository crmSearchRepository;

    private final EquipeRepository equipeRepository;
    private final EquipeSearchRepository equipeSearchRepository;

    private final EspecialidadeRepository especialidadeRepository;
    private final EspecialidadeSearchRepository especialidadeSearchRepository;

    private final EventoLeitoRepository eventoLeitoRepository;
    private final EventoLeitoSearchRepository eventoLeitoSearchRepository;

    private final HospitalRepository hospitalRepository;
    private final HospitalSearchRepository hospitalSearchRepository;

    private final InternacaoRepository internacaoRepository;
    private final InternacaoSearchRepository internacaoSearchRepository;

    private final LeitoRepository leitoRepository;
    private final LeitoSearchRepository leitoSearchRepository;

    private final LocalDeAtendimentoRepository localDeAtendimentoRepository;
    private final LocalDeAtendimentoSearchRepository localDeAtendimentoSearchRepository;

    private final ModalidadeAssistencialRepository modalidadeAssistencialRepository;
    private final ModalidadeAssistencialSearchRepository modalidadeAssistencialSearchRepository;

    private final MotivoDoBloqueioDeLeitoRepository motivoDoBloqueioDeLeitoRepository;
    private final MotivoDoBloqueioDeLeitoSearchRepository motivoDoBloqueioDeLeitoSearchRepository;

    private final OrigemDaInternacaoRepository origemDaInternacaoRepository;
    private final OrigemDaInternacaoSearchRepository origemDaInternacaoSearchRepository;

    private final OrigemDaReservaDeLeitoRepository origemDaReservaDeLeitoRepository;
    private final OrigemDaReservaDeLeitoSearchRepository origemDaReservaDeLeitoSearchRepository;

    private final PlanoDeSaudeRepository planoDeSaudeRepository;
    private final PlanoDeSaudeSearchRepository planoDeSaudeSearchRepository;

    private final ProcedenciaRepository procedenciaRepository;
    private final ProcedenciaSearchRepository procedenciaSearchRepository;

    private final ProcedimentoRepository procedimentoRepository;
    private final ProcedimentoSearchRepository procedimentoSearchRepository;

    private final SituacaoDeLeitoRepository situacaoDeLeitoRepository;
    private final SituacaoDeLeitoSearchRepository situacaoDeLeitoSearchRepository;

    private final SolicitacaoDeInternacaoRepository solicitacaoDeInternacaoRepository;
    private final SolicitacaoDeInternacaoSearchRepository solicitacaoDeInternacaoSearchRepository;

    private final TipoDaReservaDeLeitoRepository tipoDaReservaDeLeitoRepository;
    private final TipoDaReservaDeLeitoSearchRepository tipoDaReservaDeLeitoSearchRepository;

    private final TipoDoEventoLeitoRepository tipoDoEventoLeitoRepository;
    private final TipoDoEventoLeitoSearchRepository tipoDoEventoLeitoSearchRepository;

    private final UnidadeFuncionalRepository unidadeFuncionalRepository;
    private final UnidadeFuncionalSearchRepository unidadeFuncionalSearchRepository;

    @Bean
    public Indexador indexadorCaraterDaInternacao() {
        return IndexadorSemMapper.<CaraterDaInternacao, Long>builder()
            .codigo("caraterdainternacao")
            .descricao("Carater da internação")
            .jpaRepository(caraterDaInternacaoRepository)
            .elasticsearchClassRepository(caraterDaInternacaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorCID() {
        return IndexadorSemMapper.<CID, Long>builder()
            .codigo("cid")
            .descricao("CID")
            .jpaRepository(cidRepository)
            .elasticsearchClassRepository(cidSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorConvenioDeSaude() {
        return IndexadorSemMapper.<ConvenioDeSaude, Long>builder()
            .codigo("conveniodesaude")
            .descricao("Convenio de aaúde")
            .jpaRepository(convenioDeSaudeRepository)
            .elasticsearchClassRepository(convenioDeSaudeSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorCRM() {
        return IndexadorSemMapper.<CRM, Long>builder()
            .codigo("crm")
            .descricao("CRM")
            .jpaRepository(crmRepository)
            .elasticsearchClassRepository(crmSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorEquipe() {
        return IndexadorSemMapper.<Equipe, Long>builder()
            .codigo("equipe")
            .descricao("Equipe")
            .jpaRepository(equipeRepository)
            .elasticsearchClassRepository(equipeSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorEspecialidade() {
        return IndexadorSemMapper.<Especialidade, Long>builder()
            .codigo("especialidade")
            .descricao("Especialidade")
            .jpaRepository(especialidadeRepository)
            .elasticsearchClassRepository(especialidadeSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorEventoLeito() {
        return IndexadorSemMapper.<EventoLeito, Long>builder()
            .codigo("eventoleito")
            .descricao("Evento leito")
            .jpaRepository(eventoLeitoRepository)
            .elasticsearchClassRepository(eventoLeitoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorHospital() {
        return IndexadorSemMapper.<Hospital, Long>builder()
            .codigo("hospital")
            .descricao("Hospital")
            .jpaRepository(hospitalRepository)
            .elasticsearchClassRepository(hospitalSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorInternacao() {
        return IndexadorSemMapper.<Internacao, Long>builder()
            .codigo("internacao")
            .descricao("Internação")
            .jpaRepository(internacaoRepository)
            .elasticsearchClassRepository(internacaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorLeito() {
        return IndexadorSemMapper.<Leito, Long>builder()
            .codigo("leito")
            .descricao("Leito")
            .jpaRepository(leitoRepository)
            .elasticsearchClassRepository(leitoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorLocalDeAtendimento() {
        return IndexadorSemMapper.<LocalDeAtendimento, Long>builder()
            .codigo("localdeatendimento")
            .descricao("Local de atendimento")
            .jpaRepository(localDeAtendimentoRepository)
            .elasticsearchClassRepository(localDeAtendimentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorModalidadeAssistencial() {
        return IndexadorSemMapper.<ModalidadeAssistencial, Long>builder()
            .codigo("modalidadeassistencial")
            .descricao("Modalidade assistencial")
            .jpaRepository(modalidadeAssistencialRepository)
            .elasticsearchClassRepository(modalidadeAssistencialSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorMotivoDoBloqueioDeLeito() {
        return IndexadorSemMapper.<MotivoDoBloqueioDeLeito, Long>builder()
            .codigo("motivodobloqueiodeleito")
            .descricao("Motivo do bloqueio de leito")
            .jpaRepository(motivoDoBloqueioDeLeitoRepository)
            .elasticsearchClassRepository(motivoDoBloqueioDeLeitoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorOrigemDaInternacao() {
        return IndexadorSemMapper.<OrigemDaInternacao, Long>builder()
            .codigo("origemdainternacao")
            .descricao("OrigemDaInternacao")
            .jpaRepository(origemDaInternacaoRepository)
            .elasticsearchClassRepository(origemDaInternacaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorPlanoDeSaude() {
        return IndexadorSemMapper.<PlanoDeSaude, Long>builder()
            .codigo("planodesaude")
            .descricao("Plano de saúde")
            .jpaRepository(planoDeSaudeRepository)
            .elasticsearchClassRepository(planoDeSaudeSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorProcedencia() {
        return IndexadorSemMapper.<Procedencia, Long>builder()
            .codigo("procedencia")
            .descricao("Procedencia")
            .jpaRepository(procedenciaRepository)
            .elasticsearchClassRepository(procedenciaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorProcedimento() {
        return IndexadorSemMapper.<Procedimento, Long>builder()
            .codigo("procedimento")
            .descricao("Procedimento")
            .jpaRepository(procedimentoRepository)
            .elasticsearchClassRepository(procedimentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorSituacaoDeLeito() {
        return IndexadorSemMapper.<SituacaoDeLeito, Long>builder()
            .codigo("situacaodeleito")
            .descricao("Situacao de leito")
            .jpaRepository(situacaoDeLeitoRepository)
            .elasticsearchClassRepository(situacaoDeLeitoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorSolicitacaoDeInternacao() {
        return IndexadorSemMapper.<SolicitacaoDeInternacao, Long>builder()
            .codigo("solicitacaodeinternacao")
            .descricao("Solicitação de internação")
            .jpaRepository(solicitacaoDeInternacaoRepository)
            .elasticsearchClassRepository(solicitacaoDeInternacaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTipoDaReservaDeLeito() {
        return IndexadorSemMapper.<TipoDaReservaDeLeito, Long>builder()
            .codigo("tipodareservadeleito")
            .descricao("Tipo da reserva de leito")
            .jpaRepository(tipoDaReservaDeLeitoRepository)
            .elasticsearchClassRepository(tipoDaReservaDeLeitoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTipoDoEventoLeito() {
        return IndexadorSemMapper.<TipoDoEventoLeito, Long>builder()
            .codigo("tipodoeventoleito")
            .descricao("Tipo do evento de leito")
            .jpaRepository(tipoDoEventoLeitoRepository)
            .elasticsearchClassRepository(tipoDoEventoLeitoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorUnidadeFuncional() {
        return IndexadorSemMapper.<UnidadeFuncional, Long>builder()
            .codigo("unidadeFuncional")
            .descricao("Unidade funcional")
            .jpaRepository(unidadeFuncionalRepository)
            .elasticsearchClassRepository(unidadeFuncionalSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

}
