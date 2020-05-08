import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { CRM } from '@internacao/models/crm';
import { Observable } from 'rxjs';
import { EntityService } from '@shared/entity.service';

@Injectable({
    providedIn: 'root',
})
export class CrmService implements EntityService {
    private readonly resource = `${api}/crms`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getCRMS(sort?: boolean, sortBy?: string): Observable<Array<CRM>> {
        if (sort) {
            return this.getResource<Array<CRM>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<CRM>>();
    }

    getCRMSPorNome(nome: string, sort?: boolean, sortBy?: string): Observable<Array<CRM>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<CRM>>(params.set('sort', sortBy ? sortBy : 'nome'));
        }

        return this.getResource<Array<CRM>>();
    }
}
