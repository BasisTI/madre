import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recebimento } from './recebimento';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class RecebimentoService {
    private readonly resource = `${api}/recebimentos`;

    constructor(private httpClient: HttpClient) {}

    public getResource(): string {
        return this.resource;
    }

    public getRecebimentos(): Observable<Recebimento[]> {
        return this.httpClient.get<Recebimento[]>(this.resource);
    }

    public salvarRecebimento(recebimento) {
        return this.httpClient.post(this.resource, recebimento);
    }
}
