export interface Pagination<T, P> {
    content: T[];
    totalElements: number;
    numberOfElements: number;
    size: number;
    number: number;
    currentPage: number;
    lastPage: number;
    nextPage: number;
    prevPage: number;
    links: {
        next: string;
        prev: string;
        last: string;
        first: string;
    }
    params: P;
}


