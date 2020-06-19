import { HttpClient, HttpParams } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UnidadeMedida } from './unidade-medida';
import { api } from '@suprimentos/api';

@Injectable({
    providedIn: 'root',
})
export class UnidadeMedidaService {
    private readonly resource = `${api}/unidades-medida`;
    constructor(private client: HttpClient) {}

    public obterUnidadeMedidaPorDescricao(descricao: string): Observable<UnidadeMedida[]> {
        return this.client.get<UnidadeMedida[]>(this.resource, {
            params: new HttpParams().set('descricao', descricao),
        });
    }
}
