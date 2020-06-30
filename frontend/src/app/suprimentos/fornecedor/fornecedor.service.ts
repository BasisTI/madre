import { HttpClient, HttpParams } from '@angular/common/http';

import { Fornecedor } from './fornecedor';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class FornecedorService {
    private readonly resource = `${api}/fornecedores`;

    constructor(private client: HttpClient) {}

    public getFornecedoresPorNomeFantasia(nomeFantasia: string): Observable<Fornecedor[]> {
        return this.client.get<Fornecedor[]>(this.resource, {
            params: new HttpParams().set('nomeFantasia', nomeFantasia),
        });
    }
}
