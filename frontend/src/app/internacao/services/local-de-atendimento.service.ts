import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { LocalDeAtendimento } from '@internacao/models/local-de-atendimento';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class LocalDeAtendimentoService implements EntityService {
    private readonly resource = `${api}/locais-de-atendimento`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getLocaisDeAtendimento(sort?: boolean, sortBy?: string): Observable<Array<LocalDeAtendimento>> {
        if (sort) {
            return this.getResource<Array<LocalDeAtendimento>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<LocalDeAtendimento>>();
    }

    getLocaisDeAtendimentoPorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<LocalDeAtendimento>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<LocalDeAtendimento>>(
                params.set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<LocalDeAtendimento>>();
    }
}
