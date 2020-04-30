import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Equipe } from '@internacao/models/equipe';
import { Observable } from 'rxjs';
import { EntityService } from '@shared/entity.service';

@Injectable({
    providedIn: 'root',
})
export class EquipeService implements EntityService {
    private readonly resource = `${api}/equipes`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getEquipes(sort?: boolean, sortBy?: string): Observable<Array<Equipe>> {
        if (sort) {
            return this.getResource<Array<Equipe>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<Equipe>>();
    }

    getEquipesPorNome(nome: string, sort?: boolean, sortBy?: string): Observable<Array<Equipe>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<Equipe>>(params.set('sort', sortBy ? sortBy : 'nome'));
        }

        return this.getResource<Array<Equipe>>();
    }
}
