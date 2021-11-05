package br.com.basis.madre.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationLinks {
    private String next;
    private String prev;
    private String last;
    private String first;
}
