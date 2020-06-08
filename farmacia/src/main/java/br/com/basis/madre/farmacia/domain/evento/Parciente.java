package br.com.basis.madre.farmacia.domain.evento;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Parciente {

    private Long id;

    private String nome;

    private LocalDate dataDeNascimento;
}
