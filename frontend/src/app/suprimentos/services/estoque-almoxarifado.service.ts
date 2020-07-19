import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class EstoqueAlmoxarifadoService {
    private readonly resource = `${api}/estoque-almoxarifados`;

    constructor(private httpClient: HttpClient) {}

    public getResource(): string {
        return this.resource;
    }
}
