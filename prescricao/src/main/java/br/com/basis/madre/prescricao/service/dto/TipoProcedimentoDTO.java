package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimento;
import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.TipoProcedimento} entity.
 */
@Data
public class TipoProcedimentoDTO implements Serializable {


	private Long id;

    /**
     * descrição de procedimentos especiais diversos
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "descrição de procedimentos especiais diversos", required = true)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    private TipoProcedimentoEspecial tipoProcedimentoEspecial;
    


}
