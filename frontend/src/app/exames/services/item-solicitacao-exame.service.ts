import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ItemSolicitacaoExame } from '../models/subjects/item-solicitacao-exame';

@Injectable({
    providedIn: 'root',
})
export class ItemSolicitacaoExameService {
    private readonly apiUrl = '/madreexames/api/item-solicitacao-exames';

    constructor(private httpService: HttpClient) {}

    adicionarItemExame(itemExame: ItemSolicitacaoExame) {
        console.log(itemExame);

        return this.httpService.post(this.apiUrl, itemExame);
    }
}
