import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { EntityService } from '@shared/entity.service';
import { api } from '@internacao/api';
import { Injectable } from '@angular/core';
import { TipoDeReservaDeLeito } from '@internacao/models/tipo-de-reserva-de-leito';

@Injectable({
    providedIn: 'root',
})
export class TipoDaReservaDeLeitoService implements EntityService {
    private readonly resource = `${api}/tipos-de-reserva-de-leito`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getTiposDeReserva(): Observable<Array<TipoDeReservaDeLeito>> {
        return this.client.get<Array<TipoDeReservaDeLeito>>(this.resource, {
            params: new HttpParams().set('sort', 'nome'),
        });
    }

    getTiposDeReservaPorNome(nome: string): Observable<Array<TipoDeReservaDeLeito>> {
        return this.client.get<Array<TipoDeReservaDeLeito>>(this.resource, {
            params: new HttpParams().set('sort', 'nome').set('nome', nome),
        });
    }
}
