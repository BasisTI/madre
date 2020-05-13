import { Hospital } from '@internacao/models/hospital';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { EntityService } from '@shared/entity.service';
import { api } from '@internacao/api';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class HospitalService implements EntityService {
    private readonly resource = `${api}/hospitais`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getHospitais(sort?: boolean, sortBy?: string): Observable<Array<Hospital>> {
        if (sort) {
            return this.getResource<Array<Hospital>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<Hospital>>();
    }

    getHospitaisPorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<Hospital>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<Hospital>>(params.set('sort', sortBy ? sortBy : 'nome'));
        }

        return this.getResource<Array<Hospital>>();
    }
}
