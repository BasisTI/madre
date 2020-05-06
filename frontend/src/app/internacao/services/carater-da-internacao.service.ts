import { CaraterDaInternacao } from '@internacao/models/carater-da-internacao';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class CaraterDaInternacaoService implements EntityService {
    private readonly resource = `${api}/caracteres-da-internacao`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getCaracteresDaInternacao(
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<CaraterDaInternacao>> {
        if (sort) {
            return this.getResource<Array<CaraterDaInternacao>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<CaraterDaInternacao>>();
    }

    getCaracteresDaInternacaoPorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<CaraterDaInternacao>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<CaraterDaInternacao>>(
                params.set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<CaraterDaInternacao>>();
    }
}
