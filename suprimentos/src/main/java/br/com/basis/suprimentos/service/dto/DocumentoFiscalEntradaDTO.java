package br.com.basis.suprimentos.service.dto;

import br.com.basis.suprimentos.domain.enumeration.TipoDocumento;
import br.com.basis.suprimentos.domain.enumeration.TipoDocumentoFiscal;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DocumentoFiscalEntradaDTO implements Serializable {
    private Long id;

    @NotNull
    private Long numeroDocumento;

    @NotNull
    @Size(max = 10)
    private String serie;

    @Size(max = 120)
    private String notaEmpenho;

    @NotNull
    @Size(min = 11, max = 14)
    private String cpfCnpj;

    @NotNull
    private LocalDate dataGeracao;

    @NotNull
    private LocalDate dataEmissao;

    @NotNull
    private LocalDate dataEntrada;

    @NotNull
    private LocalDate dataVencimento;

    @NotNull
    private BigDecimal valorTotal;

    private TipoDocumento tipoDocumento;

    @NotNull
    private TipoDocumentoFiscal tipoDocumentoFiscal;

    @Size(max = 500)
    private String observacao;

    private Long fornecedorId;
}
