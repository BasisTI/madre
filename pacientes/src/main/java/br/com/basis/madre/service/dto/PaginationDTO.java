package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.PaginationLinks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO<T, P> {

    private T content;
    private Integer currentPage;
    private Integer nextPage;
    private Integer prevPage;
    private Integer lastPage;
    private Integer numberOfElements;
    private PaginationLinks links;
    private P params;

}
