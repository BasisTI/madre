import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@suprimentos/api';
import { UnidadeMedida } from '@suprimentos/models/unidade-medida';
import { Observable } from 'rxjs';

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
