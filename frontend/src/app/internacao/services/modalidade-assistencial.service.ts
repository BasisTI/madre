import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';
import { ModalidadeAssistencial } from '@internacao/models/modalidade-assistencial';

@Injectable({
    providedIn: 'root',
})
export class ModalidadeAssistencialService implements EntityService {
    private readonly resource = `${api}/modalidades-assistenciais`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getModalidadesAssistenciais(
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<ModalidadeAssistencial>> {
        if (sort) {
            return this.getResource<Array<ModalidadeAssistencial>>(
                new HttpParams().set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<ModalidadeAssistencial>>();
    }

    getModalidadesAssistenciaisPorNome(
        nome: string,
        sort?: boolean,
        sortBy?: string,
    ): Observable<Array<ModalidadeAssistencial>> {
        const params = new HttpParams().set('nome', nome);

        if (sort) {
            return this.getResource<Array<ModalidadeAssistencial>>(
                params.set('sort', sortBy ? sortBy : 'nome'),
            );
        }

        return this.getResource<Array<ModalidadeAssistencial>>();
    }
}
