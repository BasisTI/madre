package br.com.basis.suprimentos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageableQueryService<T> {
    public TypedQuery<T> usePageableToLimitResults(Pageable pageable, TypedQuery<T> typedQuery) {
        typedQuery.setFirstResult(pageable.getPageNumber());
        typedQuery.setMaxResults(pageable.getPageSize());
        return typedQuery;
    }

    public Page<T> getPageFromPageable(Pageable pageable, List<T> resultList, long count) {
        return new PageImpl<>(resultList, pageable, count);
    }
}
