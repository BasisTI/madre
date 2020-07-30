import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class RecebimentoService {
    private readonly resource = `${api}/recebimentos`;

    constructor(private httpClient: HttpClient) {}

    public salvarRecebimento(recebimento) {
        return this.httpClient.post(this.resource, recebimento);
    }
}
