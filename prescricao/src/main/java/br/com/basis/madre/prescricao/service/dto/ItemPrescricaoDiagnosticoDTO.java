package br.com.basis.madre.prescricao.service.dto;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico} entity.
 */
@Data
public class ItemPrescricaoDiagnosticoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

    @NotNull
    private Long idCid;

    @Size(max = 255)
    private String complemento;
    
    private CidDTO cid;
}
