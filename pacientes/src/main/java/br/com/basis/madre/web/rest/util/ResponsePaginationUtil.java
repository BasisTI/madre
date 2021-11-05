package br.com.basis.madre.web.rest.util;

import br.com.basis.madre.domain.Pagination;
import br.com.basis.madre.domain.PaginationLinks;
import br.com.basis.madre.service.dto.MunicipioDTO;
import br.com.basis.madre.service.dto.PaginationDTO;
import br.com.basis.madre.service.mapper.PaginationMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">GitHub API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */

@Component
public final class ResponsePaginationUtil {

    private final PaginationMapper paginationMapper;
    private ResponsePaginationUtil(PaginationMapper paginationMapper) {
        this.paginationMapper = paginationMapper;
    }

    public <T, P> PaginationDTO generatePagination(Page<T> page, String baseUrl, List<T> content, P params) {

        Pagination pagination = new Pagination<>();

        pagination.setNumberOfElements(page.getTotalElements());
        pagination.setContent(content);
        pagination.setParams(params);
        PaginationLinks links = new PaginationLinks();
        pagination.setCurrentPage(page.getNumber());
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            links.setNext(generateUri(baseUrl, page.getNumber() + 1, page.getSize()));
            pagination.setNextPage(page.getNumber() + 1);
        }
        // prev link
        if ((page.getNumber()) > 0) {
            links.setPrev(generateUri(baseUrl, page.getNumber() - 1, page.getSize()));
            pagination.setPrevPage(page.getNumber() - 1);
        }
        // last and first link
        int lastPage = 0;
        if (page.getTotalPages() > 0) {
            lastPage = page.getTotalPages() - 1;
            pagination.setLastPage(lastPage);
        }
        links.setLast(generateUri(baseUrl, lastPage, page.getSize()));
        links.setFirst(generateUri(baseUrl, 0, page.getSize()));

        pagination.setLinks(links);
        return (this.paginationMapper.toDto(pagination));
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }

}

