package br.com.basis.madre.config;

import br.com.basis.madre.domain.CartaoSUS;
import br.com.basis.madre.domain.Certidao;
import br.com.basis.madre.domain.DDD;
import br.com.basis.madre.domain.Documento;
import br.com.basis.madre.domain.Endereco;
import br.com.basis.madre.domain.EstadoCivil;
import br.com.basis.madre.domain.Etnia;
import br.com.basis.madre.domain.Genitores;
import br.com.basis.madre.domain.GrauDeParentesco;
import br.com.basis.madre.domain.Justificativa;
import br.com.basis.madre.domain.MotivoDoCadastro;
import br.com.basis.madre.domain.Municipio;
import br.com.basis.madre.domain.Nacionalidade;
import br.com.basis.madre.domain.Ocupacao;
import br.com.basis.madre.domain.OrgaoEmissor;
import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.PreCadastroPaciente;
import br.com.basis.madre.domain.Raca;
import br.com.basis.madre.domain.Religiao;
import br.com.basis.madre.domain.Responsavel;
import br.com.basis.madre.domain.Telefone;
import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.domain.UF;
import br.com.basis.madre.repository.CartaoSUSRepository;
import br.com.basis.madre.repository.CertidaoRepository;
import br.com.basis.madre.repository.DDDRepository;
import br.com.basis.madre.repository.DocumentoRepository;
import br.com.basis.madre.repository.EnderecoRepository;
import br.com.basis.madre.repository.EstadoCivilRepository;
import br.com.basis.madre.repository.EtniaRepository;
import br.com.basis.madre.repository.GenitoresRepository;
import br.com.basis.madre.repository.GrauDeParentescoRepository;
import br.com.basis.madre.repository.JustificativaRepository;
import br.com.basis.madre.repository.MotivoDoCadastroRepository;
import br.com.basis.madre.repository.MunicipioRepository;
import br.com.basis.madre.repository.NacionalidadeRepository;
import br.com.basis.madre.repository.OcupacaoRepository;
import br.com.basis.madre.repository.OrgaoEmissorRepository;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.repository.PreCadastroPacienteRepository;
import br.com.basis.madre.repository.RacaRepository;
import br.com.basis.madre.repository.ReligiaoRepository;
import br.com.basis.madre.repository.ResponsavelRepository;
import br.com.basis.madre.repository.TelefoneRepository;
import br.com.basis.madre.repository.TriagemRepository;
import br.com.basis.madre.repository.UFRepository;
import br.com.basis.madre.repository.search.CartaoSUSSearchRepository;
import br.com.basis.madre.repository.search.CertidaoSearchRepository;
import br.com.basis.madre.repository.search.DDDSearchRepository;
import br.com.basis.madre.repository.search.DocumentoSearchRepository;
import br.com.basis.madre.repository.search.EnderecoSearchRepository;
import br.com.basis.madre.repository.search.EstadoCivilSearchRepository;
import br.com.basis.madre.repository.search.EtniaSearchRepository;
import br.com.basis.madre.repository.search.GenitoresSearchRepository;
import br.com.basis.madre.repository.search.GrauDeParentescoSearchRepository;
import br.com.basis.madre.repository.search.JustificativaSearchRepository;
import br.com.basis.madre.repository.search.MotivoDoCadastroSearchRepository;
import br.com.basis.madre.repository.search.MunicipioSearchRepository;
import br.com.basis.madre.repository.search.NacionalidadeSearchRepository;
import br.com.basis.madre.repository.search.OcupacaoSearchRepository;
import br.com.basis.madre.repository.search.OrgaoEmissorSearchRepository;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import br.com.basis.madre.repository.search.PreCadastroPacienteSearchRepository;
import br.com.basis.madre.repository.search.RacaSearchRepository;
import br.com.basis.madre.repository.search.ReligiaoSearchRepository;
import br.com.basis.madre.repository.search.ResponsavelSearchRepository;
import br.com.basis.madre.repository.search.TelefoneSearchRepository;
import br.com.basis.madre.repository.search.TriagemSearchRepository;
import br.com.basis.madre.repository.search.UFSearchRepository;
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
