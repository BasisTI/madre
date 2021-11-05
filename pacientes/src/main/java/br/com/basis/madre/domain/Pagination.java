package br.com.basis.madre.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination<T, P> {

    private T content;
    private Integer currentPage;
    private Integer nextPage;
    private Integer prevPage;
    private Integer lastPage;
    private Long numberOfElements;
    private PaginationLinks links;
    private P params;

}
