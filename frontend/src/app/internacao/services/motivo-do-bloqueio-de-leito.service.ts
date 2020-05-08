import { MotivoDoBloqueioDeLeito } from '@internacao/models/motivo-do-bloqueio-de-leito';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class MotivoDoBloqueioDeLeitoService implements EntityService {
    private readonly resource = `${api}/motivos-do-bloqueio-de-leito`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getMotivosDoBloqueioDeLeito(): Observable<Array<MotivoDoBloqueioDeLeito>> {
        return this.client.get<Array<MotivoDoBloqueioDeLeito>>(this.resource, {
            params: new HttpParams().set('sort', 'nome'),
        });
    }

    getMotivosDoBloqueioDeLeitoPorNome(nome: string): Observable<Array<MotivoDoBloqueioDeLeito>> {
        return this.client.get<Array<MotivoDoBloqueioDeLeito>>(this.resource, {
            params: new HttpParams().set('sort', 'nome').set('nome', nome),
        });
    }
}
