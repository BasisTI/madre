import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class EstoqueAlmoxarifadoService {
    private readonly resource = `${api}/estoque-almoxarifados`;

    public getResource(): string {
        return this.resource;
    }
}
