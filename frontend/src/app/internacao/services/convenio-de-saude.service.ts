import { ConvenioDeSaude } from '@internacao/models/convenio-de-saude';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';
import { HttpParams, HttpClient } from '@angular/common/http';
import { EntityService } from './../../shared/entity.service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class ConvenioDeSaudeService implements EntityService {
    private readonly resource = `${api}/convenios-de-saude`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getConveniosDeSaude(sort?: boolean, sortBy?: string): Observable<Array<ConvenioDeSaude>> {
        if (sort) {
            return this.getResource<Array<ConvenioDeSaude>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<ConvenioDeSaude>>();
    }

    getConveniosDeSaudePorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<ConvenioDeSaude>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<ConvenioDeSaude>>(
                params.set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<ConvenioDeSaude>>();
    }
}
