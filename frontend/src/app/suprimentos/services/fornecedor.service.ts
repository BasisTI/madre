import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { Fornecedor } from '@suprimentos/models/fornecedor';
import { Observable } from 'rxjs';

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
