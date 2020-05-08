import { OrigemDaReservaDeLeito } from '@internacao/models/origem-da-reserva-de-leito';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class OrigemDaReservaDeLeitoService implements EntityService {
    private readonly resource = `${api}/origens-da-reserva-de-leito`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getOrigensDaReservaDeLeito(): Observable<Array<OrigemDaReservaDeLeito>> {
        return this.client.get<Array<OrigemDaReservaDeLeito>>(this.resource, {
            params: new HttpParams().set('sort', 'nome'),
        });
    }

    getOrigensDaReservaDeLeitoPorNome(nome: string): Observable<Array<OrigemDaReservaDeLeito>> {
        return this.client.get<Array<OrigemDaReservaDeLeito>>(this.resource, {
            params: new HttpParams().set('sort', 'nome').set('nome', nome),
        });
    }
}
