package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.Telefone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Responsavel} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelDTO implements Serializable {

    private Long id;

    private String nomeDoResponsavel;


    private Set<Telefone> telefones = new HashSet<>();

    private String observacao;

    private Long grauDeParentescoId;


}
