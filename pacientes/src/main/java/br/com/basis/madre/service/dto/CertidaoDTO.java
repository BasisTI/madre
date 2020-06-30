package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.TipoDaCertidao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Certidao} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertidaoDTO implements Serializable {

    private Long id;

    private String registroDeNascimento;

    private TipoDaCertidao tipoDaCertidao;

    private String nomeDoCartorio;

    private String livro;

    private String folhas;

    private String termo;

    private LocalDate dataDeEmissao;

    private String numeroDaDeclaracaoDeNascimento;

}
