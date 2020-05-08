import { EntityService } from '@shared/entity.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PlanoDeSaude } from '@internacao/models/plano-de-saude';
import { Observable } from 'rxjs';
import { api } from '@internacao/api';

@Injectable({
    providedIn: 'root',
})
export class PlanoDeSaudeService implements EntityService {
    private readonly resource = `${api}/planos-de-saude`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getPlanosDeSaude(sort?: boolean, sortBy?: string): Observable<Array<PlanoDeSaude>> {
        if (sort) {
            return this.getResource<Array<PlanoDeSaude>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<PlanoDeSaude>>();
    }

    getPlanosDeSaudePorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<PlanoDeSaude>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<PlanoDeSaude>>(
                params.set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<PlanoDeSaude>>();
    }
}
