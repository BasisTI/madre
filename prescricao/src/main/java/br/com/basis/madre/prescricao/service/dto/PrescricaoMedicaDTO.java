package br.com.basis.madre.prescricao.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.PrescricaoMedica} entity.
 */
@Data
public class PrescricaoMedicaDTO implements Serializable {

    private Long id;

    private Long idLeito;

    private Long idUnidadeFuncional;

    private Long idAtendimento;
    
    private Long idPaciente;
    
    private String observacao;

    private LocalDate dataPrescricao;

}
