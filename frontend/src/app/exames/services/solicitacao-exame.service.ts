import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Pageable } from '@shared/pageable';
import { Observable } from 'rxjs';
import { SolicitacaoExame } from '../models/subjects/solicitacao-exame';

@Injectable({
    providedIn: 'root',
})
export class SolicitacaoExameService {
    private readonly apiUrl = '/madreexames/api/solicitacao-exames';
    private readonly api = '/madreexames/api';

    constructor(private httpService: HttpClient) {}

    solicitarExame(solicitacao: SolicitacaoExame) {
        console.log(solicitacao);

        return this.httpService.post(this.apiUrl, solicitacao);
    }

    public getSolicitacao(
        id: string,
        pedidoPrimeiroExame: string,
        usoAntimicrobianos24h: string,
    ): Observable<SolicitacaoExame[]> {
        return this.httpService.get<SolicitacaoExame[]>(`${this.api}/_search/solicitacoes-exames`, {
            params: new HttpParams()
                .set('id', id)
                .set('pedidoPrimeiroExame', pedidoPrimeiroExame)
                .set('usoAntimicrobianos24h', usoAntimicrobianos24h)
                
        });
    }

    exportarSolicitacoes(): Observable<Blob> {
        return this.httpService.get(`${this.apiUrl}/exportar`, {responseType: 'blob'});
    }

}
