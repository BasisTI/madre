import { Procedimento } from '@internacao/models/procedimento';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from '@shared/entity.service';
import { api } from '@internacao/api';

@Injectable({
    providedIn: 'root',
})
export class ProcedimentoService implements EntityService {
    private readonly resource = `${api}/procedimentos`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getProcedimentos(sort?: boolean, sortBy?: string): Observable<Array<Procedimento>> {
        if (sort) {
            return this.getResource<Array<Procedimento>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'Procedimento'),
            );
        }

        return this.getResource<Array<Procedimento>>();
    }

    getProcedimentosPorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<Procedimento>> {
        const params = new HttpParams().set('procedimento', nome);

        if (sort) {
            return this.getResource<Array<Procedimento>>(
                params.set('sort', sortBy ? sortBy : 'procedimento'),
            );
        }

        return this.getResource<Array<Procedimento>>();
    }
}
