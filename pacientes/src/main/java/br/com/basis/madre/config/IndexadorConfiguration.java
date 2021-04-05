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

    private final CartaoSUSRepository cartaoSUSRepository;
    private final CartaoSUSSearchRepository cartaoSUSSearchRepository;

    private final CertidaoRepository certidaoRepository;
    private final CertidaoSearchRepository certidaoSearchRepository;

    private final DocumentoRepository documentoRepository;
    private final DocumentoSearchRepository documentoSearchRepository;

    private final EnderecoRepository enderecoRepository;
    private final EnderecoSearchRepository enderecoSearchRepository;

    private final EstadoCivilRepository estadoCivilRepository;
    private final EstadoCivilSearchRepository estadoCivilSearchRepository;

    private final EtniaRepository etniaRepository;
    private final EtniaSearchRepository etniaSearchRepository;

    private final GenitoresRepository genitoresRepository;
    private final GenitoresSearchRepository genitoresSearchRepository;

    private final GrauDeParentescoRepository grauDeParentescoRepository;
    private final GrauDeParentescoSearchRepository grauDeParentescoSearchRepository;

    private final JustificativaRepository justificativaRepository;
    private final JustificativaSearchRepository justificativaSearchRepository;

    private final MotivoDoCadastroRepository motivoDoCadastroRepository;
    private final MotivoDoCadastroSearchRepository motivoDoCadastroSearchRepository;

    private final MunicipioRepository municipioRepository;
    private final MunicipioSearchRepository municipioSearchRepository;

    private final NacionalidadeRepository nacionalidadeRepository;
    private final NacionalidadeSearchRepository nacionalidadeSearchRepository;

    private final OcupacaoRepository ocupacaoRepository;
    private final OcupacaoSearchRepository ocupacaoSearchRepository;

    private final OrgaoEmissorRepository orgaoEmissorRepository;
    private final OrgaoEmissorSearchRepository orgaoEmissorSearchRepository;

    private final PacienteRepository pacienteRepository;
    private final PacienteSearchRepository pacienteSearchRepository;

    private final PreCadastroPacienteRepository preCadastroPacienteRepository;
    private final PreCadastroPacienteSearchRepository preCadastroPacienteSearchRepository;

    private final RacaRepository racaRepository;
    private final RacaSearchRepository racaSearchRepository;

    private final DDDRepository dddRepository;
    private final DDDSearchRepository dddSearchRepository;

    private final ReligiaoRepository religiaoRepository;
    private final ReligiaoSearchRepository religiaoSearchRepository;

    private final ResponsavelRepository responsavelRepository;
    private final ResponsavelSearchRepository responsavelSearchRepository;

    private final TelefoneRepository telefoneRepository;
    private final TelefoneSearchRepository telefoneSearchRepository;

    private final TriagemRepository triagemRepository;
    private final TriagemSearchRepository triagemSearchRepository;

    private final UFRepository ufRepository;
    private final UFSearchRepository ufSearchRepository;

    @Bean
    public Indexador indexadorCartaoSUS() {
        return IndexadorSemMapper.<CartaoSUS, Long>builder()
            .codigo("cartaosus")
            .descricao("Cartão SUS")
            .jpaRepository(cartaoSUSRepository)
            .elasticsearchClassRepository(cartaoSUSSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorCertidao() {
        return IndexadorSemMapper.<Certidao, Long>builder()
            .codigo("certidao")
            .descricao("Certidão")
            .jpaRepository(certidaoRepository)
            .elasticsearchClassRepository(certidaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorDocumento() {
        return IndexadorSemMapper.<Documento, Long>builder()
            .codigo("documento")
            .descricao("Documento")
            .jpaRepository(documentoRepository)
            .elasticsearchClassRepository(documentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorEndereco() {
        return IndexadorSemMapper.<Endereco, Long>builder()
            .codigo("endereco")
            .descricao("Endereço")
            .jpaRepository(enderecoRepository)
            .elasticsearchClassRepository(enderecoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorEstadoCivil() {
        return IndexadorSemMapper.<EstadoCivil, Long>builder()
            .codigo("estadocivil")
            .descricao("Estado Civil")
            .jpaRepository(estadoCivilRepository)
            .elasticsearchClassRepository(estadoCivilSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorEtnia() {
        return IndexadorSemMapper.<Etnia, Long>builder()
            .codigo("etnia")
            .descricao("Etnia")
            .jpaRepository(etniaRepository)
            .elasticsearchClassRepository(etniaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorGenitores() {
        return IndexadorSemMapper.<Genitores, Long>builder()
            .codigo("genitores")
            .descricao("Genitores")
            .jpaRepository(genitoresRepository)
            .elasticsearchClassRepository(genitoresSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorGrauDeParentesco() {
        return IndexadorSemMapper.<GrauDeParentesco, Long>builder()
            .codigo("graudeparentesco")
            .descricao("Grau de Parentesco")
            .jpaRepository(grauDeParentescoRepository)
            .elasticsearchClassRepository(grauDeParentescoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorJustificativa() {
        return IndexadorSemMapper.<Justificativa, Long>builder()
            .codigo("justificativa")
            .descricao("Justificativa")
            .jpaRepository(justificativaRepository)
            .elasticsearchClassRepository(justificativaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorMotivoDoCadastro() {
        return IndexadorSemMapper.<MotivoDoCadastro, Long>builder()
            .codigo("motivodocadastro")
            .descricao("Motivo do cadastro")
            .jpaRepository(motivoDoCadastroRepository)
            .elasticsearchClassRepository(motivoDoCadastroSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorMunicipio() {
        return IndexadorSemMapper.<Municipio, Long>builder()
            .codigo("municipio")
            .descricao("Município")
            .jpaRepository(municipioRepository)
            .elasticsearchClassRepository(municipioSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorNacionalidade() {
        return IndexadorSemMapper.<Nacionalidade, Long>builder()
            .codigo("nacionalidade")
            .descricao("Nacionalidade")
            .jpaRepository(nacionalidadeRepository)
            .elasticsearchClassRepository(nacionalidadeSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorOcupacao() {
        return IndexadorSemMapper.<Ocupacao, Long>builder()
            .codigo("ocupacao")
            .descricao("Ocupação")
            .jpaRepository(ocupacaoRepository)
            .elasticsearchClassRepository(ocupacaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorOrgaoEmissor() {
        return IndexadorSemMapper.<OrgaoEmissor, Long>builder()
            .codigo("orgaoemissor")
            .descricao("Orgão Emissor")
            .jpaRepository(orgaoEmissorRepository)
            .elasticsearchClassRepository(orgaoEmissorSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorPaciente() {
        return IndexadorSemMapper.<Paciente, Long>builder()
            .codigo("paciente")
            .descricao("Paciente")
            .jpaRepository(pacienteRepository)
            .elasticsearchClassRepository(pacienteSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorPreCadastroPaciente() {
        return IndexadorSemMapper.<PreCadastroPaciente, Long>builder()
            .codigo("precadastropaciente")
            .descricao("Pré-cadastro de Paciente")
            .jpaRepository(preCadastroPacienteRepository)
            .elasticsearchClassRepository(preCadastroPacienteSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorRaca() {
        return IndexadorSemMapper.<Raca, Long>builder()
            .codigo("raca")
            .descricao("Raça")
            .jpaRepository(racaRepository)
            .elasticsearchClassRepository(racaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorDDD() {
        return IndexadorSemMapper.<DDD, Long>builder()
            .codigo("ddd")
            .descricao("DDD")
            .jpaRepository(dddRepository)
            .elasticsearchClassRepository(dddSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorReligiao() {
        return IndexadorSemMapper.<Religiao, Long>builder()
            .codigo("religiao")
            .descricao("Religião")
            .jpaRepository(religiaoRepository)
            .elasticsearchClassRepository(religiaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorResponsavel() {
        return IndexadorSemMapper.<Responsavel, Long>builder()
            .codigo("responsavel")
            .descricao("Responsável")
            .jpaRepository(responsavelRepository)
            .elasticsearchClassRepository(responsavelSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTelefone() {
        return IndexadorSemMapper.<Telefone, Long>builder()
            .codigo("telefone")
            .descricao("Telefone")
            .jpaRepository(telefoneRepository)
            .elasticsearchClassRepository(telefoneSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTriagem() {
        return IndexadorSemMapper.<Triagem, Long>builder()
            .codigo("triagem")
            .descricao("Triagem")
            .jpaRepository(triagemRepository)
            .elasticsearchClassRepository(triagemSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexador() {
        return IndexadorSemMapper.<UF, Long>builder()
            .codigo("uf")
            .descricao("UF")
            .jpaRepository(ufRepository)
            .elasticsearchClassRepository(ufSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

}
