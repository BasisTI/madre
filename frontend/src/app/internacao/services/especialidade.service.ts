import { EntityService } from '@shared/entity.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Especialidade } from '@internacao/models/especialidade';
import { Observable } from 'rxjs';
import { api } from '@internacao/api';

@Injectable({
    providedIn: 'root',
})
export class EspecialidadeService implements EntityService {
    private readonly resource = `${api}/especialidades`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getEspecialidades(sort?: boolean, sortBy?: string): Observable<Array<Especialidade>> {
        if (sort) {
            return this.getResource<Array<Especialidade>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'especialidade'),
            );
        }

        return this.getResource<Array<Especialidade>>();
    }

    getEspecialidadesPorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<Especialidade>> {
        const params = new HttpParams().set('especialidade', nome);

        if (sort) {
            return this.getResource<Array<Especialidade>>(
                params.set('sort', sortBy ? sortBy : 'especialidade'),
            );
        }

        return this.getResource<Array<Especialidade>>();
    }
}
