import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class SolicitacaoDeInternacaoService {
    private readonly resource = `${api}/solicitacoes-de-internacao`;

    constructor(private client: HttpClient) {}

    solicitarInternacao(solicitacao): Observable<any> {
        return this.client.post(this.resource, solicitacao);
    }
}
