import { EntityService } from '@shared/entity.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Procedencia } from '@internacao/models/procedencia';
import { Observable } from 'rxjs';
import { api } from '@internacao/api';

@Injectable({
    providedIn: 'root',
})
export class ProcedenciaService implements EntityService {
    private readonly resource = `${api}/procedencias`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getProcedencias(sort?: boolean, sortBy?: string): Observable<Array<Procedencia>> {
        if (sort) {
            return this.getResource<Array<Procedencia>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'descricao'),
            );
        }

        return this.getResource<Array<Procedencia>>();
    }

    getProcedenciasPorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<Procedencia>> {
        const params = new HttpParams().set('descricao', nome);

        if (sort) {
            return this.getResource<Array<Procedencia>>(
                params.set('sort', sortBy ? sortBy : 'descricao'),
            );
        }

        return this.getResource<Array<Procedencia>>();
    }
}
