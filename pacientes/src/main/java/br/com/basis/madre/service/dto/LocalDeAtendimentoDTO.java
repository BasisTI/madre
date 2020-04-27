package br.com.basis.madre.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link br.com.basis.madre.domain.LocalDeAtendimento} entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalDeAtendimentoDTO implements Serializable {
    private Long id;
    @NotNull
    private String nome;
}
