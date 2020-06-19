import { HttpClient, HttpParams } from '@angular/common/http';

import { AutorizacaoFornecimento } from './autorizacao-fornecimento';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class AutorizacaoFornecimentoService {
    private readonly resource = `${api}/autorizacoes-fornecimento`;

    constructor(private httpClient: HttpClient) {}

    public obterAutorizacaoFornecimentoPeloNumero(
        numero: string,
    ): Observable<AutorizacaoFornecimento[]> {
        return this.httpClient.get<AutorizacaoFornecimento[]>(this.resource, {
            params: new HttpParams().set('numero', numero),
        });
    }
}
