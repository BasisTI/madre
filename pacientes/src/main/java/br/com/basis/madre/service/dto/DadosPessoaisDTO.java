package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.GrauDeInstrucao;
import br.com.basis.madre.domain.enumeration.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosPessoaisDTO implements Serializable {
    private Long id;


    private String nome;
    private String nomeSocial;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private RacaDTO raca;
    private EtniaDTO etnia;
    private EstadoCivilDTO estadoCivil;
    private String prontuarioDaMae;
    private String nomeDaMae;
    private String nomeDoPai;
    private LocalDate dataDeNascimento;
    private Instant horaDoNascimento;
    private NacionalidadeDTO nacionalidade;
    private String naturalidade;

    @Enumerated(EnumType.STRING)
    private GrauDeInstrucao grauDeInstrucao;
    private OcupacaoDTO ocupacao;
    private ReligiaoDTO religiao;
    private String email;


}
