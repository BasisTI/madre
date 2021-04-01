package br.com.basis.consulta.utils;

import org.springframework.data.domain.Sort;

public abstract class PageUtils {

    private PageUtils() {

    }

    public static  Sort.Direction getSortDirection(String order) {
        Sort.Direction sortOrder = null;

        switch(order) {
            case "asc": {
                sortOrder = Sort.Direction.ASC;
            } break;
            case "desc": {
                sortOrder = Sort.Direction.DESC;
            }break;
            default: {
                // Do nothing
            }
        }

        return sortOrder;
    }
}
