import { Leito } from './../models/leito';
import { api } from './../api';
import { Observable } from 'rxjs';
import { HttpParams, HttpClient } from '@angular/common/http';
import { EntityService } from '@shared/entity.service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class LeitoService implements EntityService {
    private readonly resource = `${api}/leitos`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getLeitosNaoDesocupados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this.resource}/nao-desocupados`, {
            params: new HttpParams().set('sort', 'nome'),
        });
    }

    getLeitosNaoDesocupadosPorNome(nome: string): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this.resource}/nao-desocupados`, {
            params: new HttpParams().set('sort', 'nome').set('nome', nome),
        });
    }

    getLeitosDesocupados(): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this.resource}/desocupados`, {
            params: new HttpParams().set('sort', 'nome'),
        });
    }

    getLeitosDesocupadosPorNome(nome: string): Observable<Array<Leito>> {
        return this.client.get<Array<Leito>>(`${this.resource}/desocupados`, {
            params: new HttpParams().set('sort', 'nome').set('nome', nome),
        });
    }
}
