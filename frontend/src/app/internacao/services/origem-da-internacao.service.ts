import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Injectable } from '@angular/core';
import { OrigemDaInternacao } from '@internacao/models/origem-da-internacao';

@Injectable({
    providedIn: 'root',
})
export class OrigemDaInternacaoService implements EntityService {
    private readonly resource = `${api}/origens-das-internacoes`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getOrigensDaInternacao(sort?: boolean, sortBy?: string): Observable<Array<OrigemDaInternacao>> {
        if (sort) {
            return this.getResource<Array<OrigemDaInternacao>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<OrigemDaInternacao>>();
    }

    getOrigensDaInternacaoPorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<OrigemDaInternacao>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<OrigemDaInternacao>>(
                params.set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<OrigemDaInternacao>>();
    }
}
