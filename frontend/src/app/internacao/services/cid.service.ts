import { CID } from './../models/cid';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class CidService implements EntityService {
    private readonly resource = `${api}/cids`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getCIDS(sort?: boolean, sortBy?: string): Observable<Array<CID>> {
        if (sort) {
            return this.getResource<Array<CID>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'descricao'),
            );
        }

        return this.getResource<Array<CID>>();
    }

    getCIDSPorDescricao(
        descricao: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<CID>> {
        const params = new HttpParams().set('descricao', descricao);

        if (sort) {
            return this.getResource<Array<CID>>(params.set('sort', sortBy ? sortBy : 'descricao'));
        }

        return this.getResource<Array<CID>>();
    }
}
