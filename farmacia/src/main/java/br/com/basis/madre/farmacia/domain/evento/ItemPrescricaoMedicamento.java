package br.com.basis.madre.farmacia.domain.evento;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Document(indexName = "madre-prescricao-itemprescricaomedicamento")
public class ItemPrescricaoMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    private Long idMedicamento;


    private Long idListaMedicamentos;


    private BigDecimal dose;


    private Integer frequencia;


    private Boolean todasVias;


    private Boolean bombaInfusao;


    private BigDecimal velocidadeInfusao;


    private Integer tempoInfusao;





    private LocalDate inicioAdministracao;


    private Boolean condicaoNecessaria;


    private String observacaoCondicao;



}
