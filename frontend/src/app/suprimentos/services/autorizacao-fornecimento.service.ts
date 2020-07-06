import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { AutorizacaoFornecimento } from '@suprimentos/models/autorizacao-fornecimento';
import { Observable } from 'rxjs';

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
