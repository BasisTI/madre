import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SolicitacaoExame } from '../models/subjects/solicitacao-exame';

@Injectable({
    providedIn: 'root',
})
export class SolicitacaoExameService {
    private readonly apiUrl = '/madreexames/api/solicitacao-exames';

    constructor(private httpService: HttpClient) {}

    solicitarExame(solicitacao: SolicitacaoExame) {
        console.log(solicitacao);

        return this.httpService.post(this.apiUrl, solicitacao);
    }
}
